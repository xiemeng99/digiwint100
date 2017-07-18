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
import digiwin.smartdepott100.module.bean.purchase.BadReasonBean;
import digiwin.smartdepott100.module.bean.purchase.ImageUrl;
import digiwin.smartdepott100.module.bean.purchase.PurchaseCheckBean;
import digiwin.smartdepott100.module.bean.purchase.PurchaseCheckDetailBean;

/**
 * Created by xiemeng on 2017/2/23.
 */

public class PurcahseCheckLogic {

    private static final String TAG = "CommonLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static PurcahseCheckLogic logic;

    private PurcahseCheckLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;

    }


    public static PurcahseCheckLogic getInstance(Context context, String module, String timestamp) {

        return logic = new PurcahseCheckLogic(context, module, timestamp);
    }

    /**
     * 获取采购收货待检验事项
     */
    public interface GetMaterialToCheckListener {
        public void onSuccess(List<PurchaseCheckBean> purchaseCheckBean);

        public void onFailed(String error);
    }

    /**
     * 获取采购收货待检验事项
     */
    public void getMaterialToCheck(final Map<String, String> map, final GetMaterialToCheckListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETMATERIALTOCHECK, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETMATERIALTOCHECK, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    List<PurchaseCheckBean> locatorBackBeen = xmlResp.getMasterDatas(PurchaseCheckBean.class);
                                    if (locatorBackBeen.size() > 0) {
                                        listener.onSuccess(locatorBackBeen);
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
     * 获取IQC质量记录
     */
    public interface GetIQCListListener {
        public void onSuccess(List<PurchaseCheckDetailBean> purchaseCheckDetailBean);

        public void onFailed(String error);
    }

    /**
     * 获取IQC质量记录
     */
    public void getIQCDetailList(final Map<String, String> map, final GetIQCListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETIQCLIST, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETIQCLIST, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    List<PurchaseCheckDetailBean> purchaseCheckDetailBeen = xmlResp.getMasterDatas(PurchaseCheckDetailBean.class);
                                    if (purchaseCheckDetailBeen.size() > 0) {
                                        listener.onSuccess(purchaseCheckDetailBeen);
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
     * 根据首字母获取不良原因
     */
    public interface GetQCReasonListener {
        public void onSuccess(List<BadReasonBean> badReasonBeenList);

        public void onFailed(String error);
    }

    /**
     * 根据首字母获取不良原因
     */
    public void getQCReasonInfo(final Map<String, String> map, final GetQCReasonListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETQCREASON, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETQCREASON, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    List<BadReasonBean> badReasonBeen = xmlResp.getMasterDatas(BadReasonBean.class);
                                    if (badReasonBeen.size() > 0) {
                                        listener.onSuccess(badReasonBeen);
                                        return;
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
     * 根据料号获取不良原因数量top5
     */
    public interface GetQCReasonTop5Listener {
        public void onSuccess(List<BadReasonBean> badReasonBeenList);

        public void onFailed(String error);
    }

    /**
     * 根据料号获取不良原因数量top5
     */
    public void getQCReasonTop5Info(final Map<String, String> map, final GetQCReasonTop5Listener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETQCREASONTOP5, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETQCREASONTOP5, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    List<BadReasonBean> badReasonBeen = xmlResp.getMasterDatas(BadReasonBean.class);
                                        listener.onSuccess(badReasonBeen);
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
     * 检验单数据提交
     */
    public interface UpdateQCDataListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 检验单数据提交
     */
    public void updateQcData(final List<Map<String, String>> maps, final List<Map<String, String>> details, final UpdateQCDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(mModule, ReqTypeName.UPDATEIQCSTATUS,mTimestamp,maps,details).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.UPDATEIQCSTATUS, string);
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
     * 更新收货单
     */
    public interface UpdateRvbCheckListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 更新收货单
     */
    public void updateRvbCheck(final List<Map<String, String>>maps, final UpdateRvbCheckListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String masterName = "rvb_file";
                    String xml = CreateParaXmlReqIm.getInstance(maps,masterName,mModule, ReqTypeName.UPDRVBCHECKSTATUS, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.UPDRVBCHECKSTATUS, string);
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
     * 获取图纸
     */
    public interface GetDrawingListener {
        public void onSuccess(List<ImageUrl> list);

        public void onFailed(String error);
    }

    /**
     * 获取图纸获取图纸
     */
    public void getDrawing(final Map<String, String> maps, final GetDrawingListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(maps,mModule, ReqTypeName.GETDRAWING, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETDRAWING, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    List<ImageUrl> list = xmlResp.getMasterDatas(ImageUrl.class);
                                    listener.onSuccess(list);
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