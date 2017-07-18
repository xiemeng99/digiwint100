package digiwin.smartdepott100.module.logic.purchase;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.module.bean.purchase.PQCValueBean;

/**
 * @author 毛衡
 * @module PQC检验
 * @date 2017/4/30
 */

public class PQCLogic {

    private static final String TAG = "PQCLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static PQCLogic logic;

    private PQCLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;
    }

    public static PQCLogic getInstance(Context context, String module, String timestamp) {

        return logic = new PQCLogic(context, module, timestamp);
    }
    /**
     * 获取PQC测量值
     */
    public interface getPQCValueListener {

        public void onSuccess(PQCValueBean data);

        public void onFailed(String error);
    }

    public void getPQCValueData(final Map<String,String> map, final getPQCValueListener listener){

        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.PQCCHECKVALUE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.PQCCHECKVALUE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<PQCValueBean> list = xmlResp.getMasterDatas(PQCValueBean.class);
                                    if (list.size() > 0) {
                                        listener.onSuccess(list.get(0));
                                        return;
                                    } else {
                                        error = mContext.getString(R.string.data_null);
                                    }
                                    return;
                                } else {
                                    error = xmlResp.getDescription();
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                } catch (Exception e) {
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                }
            }
        }, null);
    }

    /**
     * 提交
     */
    public interface postPQCListener {

        public void onSuccess(String msg);

        public void onFailed(String error);
    }
    public void postPQCData(final List<Map<String, String>> maps, final List<Map<String, String>> details, final postPQCListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(mModule, ReqTypeName.POSTPQC,mTimestamp,maps,details).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.POSTPQC, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    String msg = xmlResp.getFieldString();
                                    listener.onSuccess(msg);
                                    return;
                                } else {
                                    error = xmlResp.getDescription();
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                } catch (Exception e) {
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                    LogUtils.e(TAG, "scanLocator--->" + e);
                }
            }
        }, null);
    }

    /**
     * 检测值保存
     */
    public interface SavePQCListener {

        public void onSuccess(String msg);

        public void onFailed(String error);
    }
    public void savePQCData(final List<Map<String, String>> maps, final List<Map<String, String>> details, final SavePQCListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(mModule, ReqTypeName.SAVEPQC,mTimestamp,maps,details).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.SAVEPQC, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    String msg = xmlResp.getFieldString();
                                    listener.onSuccess(msg);
                                    return;
                                } else {
                                    error = xmlResp.getDescription();
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                } catch (Exception e) {
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                    LogUtils.e(TAG, "scanLocator--->" + e);
                }
            }
        }, null);
    }
}
