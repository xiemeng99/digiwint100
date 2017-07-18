package digiwin.smartdepott100.login.loginlogic;

import android.content.Context;

import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.login.bean.AppVersionBean;

/**
 * @author xiemeng
 * @des 版本更新
 * @date 2017/1/17
 */
public class AppVersionLogic {
    private static final String TAG = "GetStorageLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private  String mModule="";
    /**
     * 设备号+模组+时间
     */
    private  String mTimestamp="";

    private static AppVersionLogic logic;

    private AppVersionLogic(Context context,String module,String timestamp) {
        mContext = context.getApplicationContext();
        mModule=module;
        mTimestamp=timestamp;

    }

    /**
     * 获取单例
     */
    public static AppVersionLogic getInstance(Context context,String module,String timestamp) {
//        if (null == logic) {
//
//        }
        return logic = new AppVersionLogic(context,module,timestamp);
    }

    /**
     * 获取服务器版本信息
     */
    public interface GetNewVersionListener {
        public void onSuccess(AppVersionBean bean);

        public void onFailed(String msg);
    }

    /**
     * 获取服务器版本信息
     */
    public void getNewVersion(Map<String, String> map, final GetNewVersionListener listener) {
        try {
            String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.GETVERUP, mTimestamp, map);
            OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    String error =mContext.getString(R.string.unknow_error);
                    if (null != string) {
                        if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                            AppVersionBean versionBean = JsonResp.getParaData(string, AppVersionBean.class);
                                listener.onSuccess(versionBean);
                            return;
                        } else {
                            error = JsonResp.getDescription(string);
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "getNewVersion异常");
            listener.onFailed(mContext.getString(R.string.unknow_error));
        }
    }
}
