package digiwin.smartdepott100.main.logic;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
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
        mContext = context;
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
            AccoutBean info = LoginLogic.getUserInfo();
            String plant="SYSTEM";
            if (null!=info){
                plant=info.getEnterprise_no();
            }
            String xml = CreateParaXmlReqIm.getInstance(map,plant, mModule, ReqTypeName.GETAP,mTimestamp).toXml();
            OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    String error = mContext.getString(R.string.unknow_error);
                    ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETAP, string);
                    if (null != xmlResp) {
                        if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                            DeviceInfoBean deviceInfoBean = new DeviceInfoBean();
                            List<DeviceInfoBean> deviceInfoBeen = xmlResp.getParameterDatas(DeviceInfoBean.class);
                            listener.onSuccess(deviceInfoBeen);
                            return;
                        }else {
                            error=xmlResp.getDescription();
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
