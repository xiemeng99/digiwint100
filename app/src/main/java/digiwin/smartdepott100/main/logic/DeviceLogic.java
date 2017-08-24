package digiwin.smartdepott100.main.logic;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.main.bean.DeviceInfoBean;

/**
 * @author xiemeng
 * @des 设备解绑
 * @date 2017/4/10 11:32
 */

public class DeviceLogic {

    private static final String TAG = "DeviceLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private  String mModule="";
    /**
     * 设备号+模组+时间
     */
    private  String mTimestamp="";

    private static DeviceLogic logic;

    private DeviceLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule=module;
        mTimestamp=timestamp;

    }

    /**
     * 获取单例
     */
    public static DeviceLogic getInstance(Context context, String module, String timestamp) {
//        if (null == logic) {
//
//        }
        return logic = new DeviceLogic(context,module,timestamp);
    }

    /**
     * 解绑设备相关操作
     */
    public interface DeviceListener {
        public void onSuccess(List<DeviceInfoBean> deviceInfoBeen);

        public void onFailed(String msg);
    }

    /**
     * 解绑设备相关操作
     * 传入statu  0:获取量 1:按设备解绑 2:按用户解绑
     */
    public void getDevice(Map<String, String> map, final DeviceListener listener) {
        try {
            String createJson;
            if (!StringUtils.isBlank(map.get("account"))){
                createJson= JsonReqForERP.createJsonForBind(mModule, ReqTypeName.GETAP, mTimestamp, map,map.get("account"));
            }else{
                createJson= JsonReqForERP.mapCreateJson(mModule, ReqTypeName.GETAP, mTimestamp, map);
            }
            OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    String error = mContext.getString(R.string.unknow_error);
                    if (null != string) {
                        if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                            DeviceInfoBean deviceInfoBean =JsonResp.getParaData(string,DeviceInfoBean.class);
                            ArrayList<DeviceInfoBean> list = new ArrayList<>();
                            list.add(deviceInfoBean);
                            listener.onSuccess(list);
                            return;
                        }else {
                            error=JsonResp.getDescription(string);
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "getDevice"+e);
            listener.onFailed(mContext.getString(R.string.unknow_error));
        }
    }
}
