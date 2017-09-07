package digiwin.smartdepott100.module.logic.sale.scanout;

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
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanEmployeeBackBean;
import digiwin.smartdepott100.module.bean.sale.scanout.ScanOutDetailData;
import digiwin.smartdepott100.module.bean.stock.ProductBinningBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * Created by 唐孟宇 on 2017/6/5.
 * 装箱出货(扫码出货) logic
 */

public class ScanOutLogic extends CommonLogic {

    private static ScanOutLogic logic;

    private String TAG = "ScanOutLogic";

    protected ScanOutLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static ScanOutLogic getInstance(Context context, String module, String timestamp) {
        logic = new ScanOutLogic(context, module, timestamp);
        return logic;
    }

    public interface GetScanOutListListener {

        void onSuccess(List<FilterResultOrderBean> list);

        void onFailed(String errmsg);
    }

    /**
     * 扫码出货获取列表数据
     *使用d001
     * @param filterBean
     */
    public void getSOList(final FilterBean filterBean, final GetScanOutListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.d001.list.get", mTimestamp, filterBean);
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
     * 扫码出货获取汇总数据
     *
     * @param
     * @param listener
     */
    public void getSOSumData(final Map<String, String> map, final GetZSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.d005.list.detail.get", mTimestamp, map);
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
    public void commitSOData(final Map<String, String> map, final CommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.d005.submit", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    String doc_no = JsonResp.getParaString(string, AddressContants.DOC_NO);
                                    if (null != doc_no) {
                                        listener.onSuccess(JsonResp.getParaString(string,AddressContants.DOC_NO));
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
     * 获取FIFO  json
     */
    public interface FIFOInfoGETListener {

        public void onSuccess(List<FifoCheckBean> fiFoBeanList);

        public void onFailed(String error);
    }

    public void getFifoInfo(final Map<String, String> map, final FIFOInfoGETListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String createJson = JsonReqForERP.mapCreateJson(mModule, "als.d005.fifo.get", mTimestamp, map);
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

    /**
     * 扫描箱条码
     * @param map
     * @param listener
     */
    public void scanPackBox(final Map<String, String> map, final ScanPackBoxNumberListener1 listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.GETPACKAGENO, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProductBinningBean productBinningBean = JsonResp.getParaData(string, ProductBinningBean.class);
                                    if(null != productBinningBean){
                                        listener.onSuccess(productBinningBean);
                                        return;
                                    }
                                } else {
                                    error = JsonResp.getDescription(string);
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
     * 保存
     */
    public void saveBox(final SaveBean saveBean, final SaveListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.package.data.save", mTimestamp, saveBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    SaveBackBean saveBackBean = JsonResp.getParaData(string, SaveBackBean.class);
                                    saveBackBean.setScan_sumqty(StringUtils.deleteZero(saveBackBean.getScan_sumqty()));
                                    listener.onSuccess(saveBackBean);
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
                    LogUtils.e(TAG, "scanSave--->" + e);
                }
            }
        }, null);
    }

    /**
     * 点击获取明细
     */
    public interface GetScanOutDetailDataListener {

        public void onSuccess(List<ScanOutDetailData> datas);

        public void onFailed(String error);
    }

    /**
     * 点击获取明细
     */
    public void getScanOutDetailData(final Map<String, String> map, final GetScanOutDetailDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.package.scan.list.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<ScanOutDetailData> masterDatas = JsonResp.getParaDatas(string,"list_detail",ScanOutDetailData.class);
                                    if (masterDatas.size() == 0) {
                                        listener.onFailed(mContext.getResources().getString(R.string.data_null));
                                        return;
                                    }
                                    listener.onSuccess(masterDatas);
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }

    /**
     * 点击删除明细
     */
    public interface DeleteScanOutDetailDataListener {

        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 点击删除明细
     */
    public void deleteScanOutDetailData(final List<Map<String,String>>  maps, final DeleteScanOutDetailDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("data",maps);
                    String createJson = JsonReqForERP.dataCreateJson(mModule,"als.pack.scan.list.del",mTimestamp.toString(),hashMap);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    listener.onSuccess(JsonResp.getDescription(string));
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }
}
