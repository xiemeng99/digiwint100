package digiwin.smartdepott100.module.logic.sale.pickupshipment;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.sale.saleoutlet.SaleOutLetLogic;

/**
 * @des 出货过账logic
 * Created by 毛衡 on 2017/6/15.
 */

public class PickUpShipmentLogic extends CommonLogic {

    private static PickUpShipmentLogic logic;

    private String TAG = "PickUpShipmentLogic";

    protected PickUpShipmentLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static PickUpShipmentLogic getInstance(Context context, String module, String timestamp) {
        logic = new PickUpShipmentLogic(context, module, timestamp);
        return logic;
    }

    public interface GetSearchListDataListener {

        void onSuccess(List<FilterResultOrderBean> list);

        void onFailed(String errmsg);
    }

    /**
     * 出货过账扫描获取列表数据
     *
     * @param filterBean
     */
    public void getSearchListData(final FilterBean filterBean, final GetSearchListDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.d003.list.get", mTimestamp, filterBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String s) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != s) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(s))) {
                                    List<FilterResultOrderBean> showBeanList = JsonResp.getParaDatas(s, "list", FilterResultOrderBean.class);
                                    listener.onSuccess(showBeanList);
                                    return;
                                } else {
                                    error = JsonResp.getDescription(s);
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                } catch (Exception e) {
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        }, null);
    }

    /**
     * 获取出货过账汇总数据监听
     */

    public interface GetSumDataListener {

        void onSuccess(List<ListSumBean> list);

        void onFailed(String errmsg);
    }

    /**
     * 出货过账扫描 获取汇总数据
     *
     * @param
     * @param listener
     */
    public void getSOLSumData(final HashMap<String, String> map, final GetSumDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.d003.list.detail.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<ListSumBean> showBeanList = JsonResp.getParaDatas(string, "list_detail", ListSumBean.class);
                                    listener.onSuccess(showBeanList);
                                    return;
                                } else {
                                    error = JsonResp.getDescription(string);
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                } catch (Exception e) {
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        }, null);
    }


    /**
     * 提交
     *
     * @param map map可以直接为空
     */
    public void commitSOLData(final Map<String, String> map, final CommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.d003.submit", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    String doc_no = JsonResp.getParaString(string, "doc_no");
                                    if (null != doc_no) {
                                        listener.onSuccess(JsonResp.getParaString(string, "doc_no"));
                                    }
                                    return;
                                } else {
                                    error = JsonResp.getDescription(string);
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                } catch (Exception e) {
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        }, null);
    }

    /**
     * 出货过账 获取FIFO  json
     */
    public interface FIFOInfoGETListener {

        public void onSuccess(List<FifoCheckBean> fiFoBeanList);

        public void onFailed(String error);
    }

    public void getFifoInfo(final Map<String, String> map, final SaleOutLetLogic.FIFOInfoGETListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String createJson = JsonReqForERP.mapCreateJson(mModule, "als.d003.fifo.get", mTimestamp, map);
                OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        String error = mContext.getString(R.string.unknow_error);
                        if (null != string) {
                            LogUtils.e(TAG, string);
                            if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                List<FifoCheckBean> fiFoBeanList = JsonResp.getParaDatas(string, "list_fifo", FifoCheckBean.class);
                                if (null != fiFoBeanList && fiFoBeanList.size() > 0) {
                                    for (int i = 0; i < fiFoBeanList.size(); i++) {
                                        FifoCheckBean fifoBean = fiFoBeanList.get(i);
                                        fifoBean.setRecommended_qty(StringUtils.deleteZero(fifoBean.getRecommended_qty()));
                                        fifoBean.setScan_sumqty(StringUtils.deleteZero(fifoBean.getScan_sumqty()));
                                    }
                                    listener.onSuccess(fiFoBeanList);
                                } else {
                                    List<FifoCheckBean> list = new ArrayList<FifoCheckBean>();
                                    listener.onSuccess(fiFoBeanList);
                                }
                                return;
                            } else {
                                error = JsonResp.getDescription(string);
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        }, null);
    }

}
