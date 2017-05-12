package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.produce.AccordingMaterialSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @module 依工单发料管理类
 * @date 2017/3/2
 */

public class WorkOrderlLogic {

    private static final String TAG = "WorkOrderlLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static WorkOrderlLogic logic;

    private WorkOrderlLogic(Context context, String module, String timestamp) {
        mContext = context;
        mModule = module;
        mTimestamp = timestamp;

    }


    public static WorkOrderlLogic getInstance(Context context, String module, String timestamp) {

        return logic = new WorkOrderlLogic(context, module, timestamp);
    }

    /**
     * 扫描工单号监听
     */
    public interface ScanCodeListener {
        public void onSuccess(List<AccordingMaterialSumBean> accordingMaterialSumBeen);

        public void onFailed(String error);
    }

    /**
     * 扫描工单号
     */
    public void scanCode(final Map<String, String> map, final ScanCodeListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.WORKORDER, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.WORKORDER, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<AccordingMaterialSumBean> accordingMaterialSumBeen = xmlResp.getMasterDatas(AccordingMaterialSumBean.class);
                                    if (accordingMaterialSumBeen.size() > 0) {
                                        List<AccordingMaterialSumBean> dataList = new ArrayList<AccordingMaterialSumBean>();
                                        for (int i = 0; i < accordingMaterialSumBeen.size(); i++) {
                                            AccordingMaterialSumBean data = accordingMaterialSumBeen.get(i);
                                            data.setShortage_qty(StringUtils.deleteZero(data.getShortage_qty()));
                                            data.setStock_qty(StringUtils.deleteZero(data.getStock_qty()));
                                            data.setScan_sumqty(StringUtils.deleteZero(data.getScan_sumqty()));
                                            dataList.add(data);
                                        }

                                        listener.onSuccess(dataList);
                                        return;
                                    } else {
                                        error = mContext.getString(R.string.data_null);
                                    }
                                } else {
                                    error = xmlResp.getDescription();
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                } catch (Exception e) {
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                    LogUtils.e(TAG, "scanBarcode--->" + e);
                }
            }
        }, null);
    }

    /**
     * 扫描库位
     */
    public interface ScanLocatorListener {
        public void onSuccess(ScanLocatorBackBean locatorBackBean);

        public void onFailed(String error);
    }

    /**
     * 扫描库位
     */
    public void scanLocator(final Map<String, String> map, final CommonLogic.ScanLocatorListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.STORAGE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.STORAGE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    List<ScanLocatorBackBean> locatorBackBeen = xmlResp.getParameterDatas(ScanLocatorBackBean.class);
                                    if (locatorBackBeen.size() > 0) {
                                        listener.onSuccess(locatorBackBeen.get(0));
                                        return;
                                    } else {
                                        error = mContext.getString(R.string.data_null);
                                    }
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
     * 提交
     */
    public interface CommitListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 提交
     */
    public void commit(final Map<String, String> map, final CommonLogic.CommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.COMMIT, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.COMMIT, string);
                            String error= mContext.getString(R.string.unknow_error);
                            if (null!=xmlResp){
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())){
                                    listener.onSuccess(xmlResp.getFieldString());
                                    return;
                                }else {
                                    error=xmlResp.getDescription();
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                }catch (Exception e){
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        },null);
    }


}
