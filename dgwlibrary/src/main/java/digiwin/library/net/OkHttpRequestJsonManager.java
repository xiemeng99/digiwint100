package digiwin.library.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.TimeUtils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import digiwin.library.R;
import digiwin.library.utils.LogUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by ChangQuan.Sun on 2016/12/23
 */

public class OkHttpRequestJsonManager implements IRequestManager {

    private static final String TAG = "OkHttpRequestJsonManager";
    public static final int DOWNLOAD_SUCCESS_FILE = 1;
    public static final int DOWNLOAD_FAIL = 2;
    public static final int DOWNLOAD_PROGRESS = 3;
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");
    private OkHttpClient okHttpClient;
    private Handler handler;
    private static Context context;

    public OkHttpRequestJsonManager(Context context) {
        //持久化cookie
        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cookieJar(cookieJar1)
//                .retryOnConnectionFailure(true)//重连
                .build();

        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpRequestJsonManager getInstance(Context context) {
        OkHttpRequestJsonManager.context = context.getApplicationContext();
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OkHttpRequestJsonManager INSTANCE = new OkHttpRequestJsonManager(context);
    }

    @Override
    public void get(String url, IRequestCallBack requestCallBack) {
        Request request = new Request.Builder()
                .addHeader("SOAPAction", "\"\"")
                .url(url)
                .get()
                .build();
        addCallBack(context, requestCallBack, request);
    }

    /**
     * 主要xml使用
     */
    @Override
    public void post(String url, String requestBody, IRequestCallBack requestCallBack) {
        RequestBody formBody = new FormBody.Builder()
                .add("token", "token")
                .add("params", requestBody)
                .build();
        Request request = new Request.Builder()
                .addHeader("SOAPAction", "\"\"")
                .url(url)
                .post(formBody)
                .build();
        addCallBack(context, requestCallBack, request);
    }

    @Override
    public void post(String url, Map<String, String> paramsMap, IRequestCallBack requestCallBack) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            Object object = paramsMap.get(key);
            builder.add(key, object.toString());
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .addHeader("SOAPAction", "\"\"")
                .url(url)
                .post(body)
                .build();
        addCallBack(context, requestCallBack, request);
    }

    /**
     * 上传文件
     */
    @Override
    public void updateFile(String url, Map<String, Object> paramsMap, final IUpdateCallBack updateCallBack) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //设置类型
        builder.setType(MultipartBody.FORM);
        //追加参数
        for (String key : paramsMap.keySet()) {
            Object object = paramsMap.get(key);
            if (!(object instanceof File)) {
                builder.addFormDataPart(key, object.toString());
            } else {
                File file = (File) object;
                LogUtils.i(TAG, "updateFile--->" + file);
                builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_OBJECT_STREAM, file, updateCallBack));
            }
        }
        //创建RequestBody

        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .addHeader("SOAPAction", "\"\"")
                .url(url)
                .post(body)
                .build();
        //创建Request,单独写出call，设置不同的超时时间和回调接口
        final Call call = okHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateCallBack.onFailure(context, context.getString(R.string.update_failed));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    updateCallBack.onResponse(string);
                } else {
                    updateCallBack.onFailure(context, context.getString(R.string.update_failed));
                }
            }
        });
    }

    @Override
    public void downLoadFile(String url, String filePath, String apkName, IDownLoadCallBack callBack) {
        Request request = new Request.Builder()
                .addHeader("SOAPAction", "\"\"")
                .url(url)
                .build();
        downLoad(context, request, filePath, apkName, callBack);
    }


    private void addCallBack(final Context context, final IRequestCallBack requestCallback, final Request request) {
        try {
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCallback.onFailure(context, new Exception("Network connection is failed"));
                        }
                    });
                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    final String string = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (string.equals("") || string == null) {
                                requestCallback.onFailure(context, new NullPointerException("Response data is null"));
                                return;
                            }
                            requestCallback.onResponse(string);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 上传进度
     */
    long current = 0;

    /**
     * 上传进度
     */
    public RequestBody createProgressRequestBody(final MediaType contentType, final File file, final IUpdateCallBack callBack) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    final long remaining = contentLength();
                    current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (callBack != null) {
                                    callBack.onProgressCallBack(remaining, current);
                                }
                            }
                        });
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 带进度条下载
     */
    private void downLoad(final Context context, Request request, final String filePath, final String apkName, final IDownLoadCallBack callBack) {

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(context, new Exception("NewWork connection fail"));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                FileOutputStream fos = null;
                byte[] bytes = new byte[1024 * 2];
                int length = 0;
                try {
                    is = response.body().byteStream();
                    File dir = new File(filePath);
                    if (!dir.exists()) dir.mkdirs();
                    final File file = new File(dir, apkName);

                    fos = new FileOutputStream(file);
                    long sum = 0;
                    //总长度
                    final long total = response.body().contentLength();

                    while ((length = is.read(bytes)) != -1) {
                        fos.write(bytes, 0, length);
                        sum += length;
                        final long finalSum = sum;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onProgressCallBack(finalSum, total);
                            }
                        });
                    }
                    fos.flush();
                    //回调文件
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onResponse(file);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();

                }
            }
        });
    }

}
