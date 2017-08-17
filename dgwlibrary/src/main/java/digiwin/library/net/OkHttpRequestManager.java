package digiwin.library.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ChangQuan.Sun on 2016/12/23
 */

public class OkHttpRequestManager implements IRequestManager {
    public static final int DOWNLOAD_SUCCESS_FILE = 1;
    public static final int DOWNLOAD_FAIL = 2;
    public static final int DOWNLOAD_PROGRESS = 3;
    public static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType TYPE_XML = MediaType.parse("text/xml; charset=utf-8");
    private OkHttpClient okHttpClient;
    private Handler handler;
    private static Context context;

    public OkHttpRequestManager(Context context) {
        //持久化cookie
        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .cookieJar(cookieJar1)
//                .retryOnConnectionFailure(true)//重连
                .build();

        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpRequestManager getInstance(Context context) {
        OkHttpRequestManager.context = context.getApplicationContext();
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OkHttpRequestManager INSTANCE = new OkHttpRequestManager(context);
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

    @Override
    public void post(String url, String requestBodyXml, IRequestCallBack requestCallBack) {
        RequestBody body = RequestBody.create(TYPE_XML, requestBodyXml);
        Request request = new Request.Builder()
                .addHeader("SOAPAction", "\"\"")
                .url(url)
                .post(body)
                .build();
        addCallBack(context, requestCallBack, request);
    }

    @Override
    public void post(String url, Map<String, String> paramsMap, IRequestCallBack requestCallBack) {

    }


    @Override
    public void downLoadFile(String url, String filePath, String apkName, IDownLoadCallBack callBack) {
        Request request = new Request.Builder()
                .addHeader("SOAPAction", "\"\"")
                .url(url)
                .build();
        downLoad(context, request, filePath, apkName, callBack);
    }

    @Override
    public void updateFile(String url, Map<String, Object> maps, IUpdateCallBack callBack) {

    }


    private void addCallBack(final Context context, final IRequestCallBack requestCallback, final Request request) {
        try {
            final   String errmsg="{\n" +
                    "             \"srvver\": \"1.0\",\n" +
                    "              \"srvcode\": \"000\",\n" +
                    "               \"payload\": {\n" +
                    "                 \"std_data\": {\n" +
                    "                    \"execution\": {\n" +
                    "                        \"code\": \"azz-00204\",\n" +
                    "                             \"sqlcode\": \"0\",\n" +
                    "                  \"description\": \"Network connection is failed\"\n" +
                    "                              }\n" +
                    "                       }\n" +
                    "                             }\n" +
                    "                  }";

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            requestCallback.onFailure(context, new Exception("NewWork connection fail"));
                            requestCallback.onResponse(errmsg);
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
//                                requestCallback.onFailure(context, new NullPointerException("Response data is null"));
                                requestCallback.onResponse(errmsg);
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
