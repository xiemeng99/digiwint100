package digiwin.smartdepott100.module.logic.sale.traceproduct;

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
import digiwin.smartdepott100.module.bean.sale.traceproduct.CurrentInventoryBean;
import digiwin.smartdepott100.module.bean.sale.traceproduct.OrderInfoBean;
import digiwin.smartdepott100.module.bean.sale.traceproduct.ProcuctProcessBean;
import digiwin.smartdepott100.module.bean.sale.traceproduct.ShipmentToBean;
import digiwin.smartdepott100.module.bean.sale.traceproduct.TraceProductDetailBean;

/**
 * @author maoheng
 * @des 产品质量追溯
 * @date 2017/4/7
 */

public class TraceProductLogic {

    private static final String TAG = "TraceProductLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static TraceProductLogic logic;

    private TraceProductLogic(Context context, String module, String timestamp) {
        mTimestamp = timestamp;
        mContext = context.getApplicationContext();
        mModule = module;

    }

    public static TraceProductLogic getInstance(Context context, String module, String timestamp) {

        return logic = new TraceProductLogic(context, module, timestamp);
    }

    /**
     * 生产过程列表
     */
    public interface ProductProcessGetListener {

        public void onSuccess(List<ProcuctProcessBean> datas);

        public void onFailed(String error);
    }

    public void productProcessGet(final Map<String, String> map, final ProductProcessGetListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.PRODUCTPROCESS, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.PRODUCTPROCESS, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ProcuctProcessBean> masterDatas = xmlResp.getMasterDatas(ProcuctProcessBean.class);
                                    if (masterDatas.size() == 0) {
                                        listener.onFailed(mContext.getResources().getString(R.string.data_null));
                                        return;
                                    }
                                    listener.onSuccess(masterDatas);
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }

    /**
     * 当前库存列表
     */
    public interface CurrentInventoryGetListener {

        public void onSuccess(List<CurrentInventoryBean> datas);

        public void onFailed(String error);
    }

    public void currentInventoryGet(final Map<String, String> map, final CurrentInventoryGetListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.CURRENTINVENTORY, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.CURRENTINVENTORY, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<CurrentInventoryBean> masterDatas = xmlResp.getMasterDatas(CurrentInventoryBean.class);
                                    if (masterDatas.size() == 0) {
                                        listener.onFailed(mContext.getResources().getString(R.string.data_null));
                                        return;
                                    }
                                    listener.onSuccess(masterDatas);
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }

    /**
     * 工单信息列表
     */
    public interface OrderInfoGetListener {

        public void onSuccess(List<OrderInfoBean> datas);

        public void onFailed(String error);
    }

    public void orderInfoGet(final Map<String, String> map, final OrderInfoGetListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.ORDERINFO, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.ORDERINFO, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<OrderInfoBean> masterDatas = xmlResp.getMasterDatas(OrderInfoBean.class);
                                    if (masterDatas.size() == 0) {
                                        listener.onFailed(mContext.getResources().getString(R.string.data_null));
                                        return;
                                    }
                                    listener.onSuccess(masterDatas);
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }

    /**
     * 生产过程列表
     */
    public interface ShipmentToGetListener {

        public void onSuccess(List<ShipmentToBean> datas);

        public void onFailed(String error);
    }

    public void shipmentToGet(final Map<String, String> map, final ShipmentToGetListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.SHIPMENTTO, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.SHIPMENTTO, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ShipmentToBean> masterDatas = xmlResp.getMasterDatas(ShipmentToBean.class);
                                    if (masterDatas.size() == 0) {
                                        listener.onFailed(mContext.getResources().getString(R.string.data_null));
                                        return;
                                    }
                                    listener.onSuccess(masterDatas);
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }

    /**
     * 投料明细、工作组明细、检验明细查询
     */
    public interface TraceProductDetailGetListener {

        public void onSuccess(List<TraceProductDetailBean> datas);

        public void onFailed(String error);
    }

    public void traceProductDetailGet(final Map<String, String> map, final TraceProductDetailGetListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.TRACEPRODUCT, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.TRACEPRODUCT, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<TraceProductDetailBean> masterDatas = xmlResp.getMasterDatas(TraceProductDetailBean.class);
                                    if (masterDatas.size() == 0) {
                                        listener.onFailed(mContext.getResources().getString(R.string.data_null));
                                        return;
                                    }
                                    listener.onSuccess(masterDatas);
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }
}
