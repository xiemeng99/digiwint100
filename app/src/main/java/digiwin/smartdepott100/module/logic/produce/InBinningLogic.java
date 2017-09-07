package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digiwin.library.constant.SharePreKey;
import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.produce.InBinningBean;
import digiwin.smartdepott100.module.bean.sale.scanout.ScanOutDetailData;
import digiwin.smartdepott100.module.bean.stock.ProductBinningBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.sale.scanoutstore.ScanOutStoreLogic;

/**
 * @author xiemeng
 * @des 装箱入库
 * @date 2017/8/28
 */
public class InBinningLogic extends CommonLogic {

    public static InBinningLogic logic;

    private InBinningLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static InBinningLogic getInstance(Context context, String module, String timestamp) {

        return logic = new InBinningLogic(context, module, timestamp);
    }


    /**
     * 装箱入库 列表
     */
    public void getInBinningList(final FilterBean filterBean, final GetZSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    filterBean.setPagesize((String) SharedPreferencesUtils.get(mContext, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM));
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.b028.list.get", mTimestamp, filterBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<ListSumBean> showBeanList = JsonResp.getParaDatas(string, "list", ListSumBean.class);
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
                    LogUtils.e(TAG, "getInBinningList--->" + e);
                }
            }
        }, null);
    }

    /**
     * 扫描包装箱号
     */
    public interface ScanPackBoxNumberListener {
        public void onSuccess(ProductBinningBean productBinningBean);

        public void onFailed(String error);
    }

    /**
     * 扫描箱码
     */
    public void scanProdut(final Map<String, String> map, final ScanPackBoxNumberListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.package.no.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProductBinningBean productBinningBean = JsonResp.getParaData(string, ProductBinningBean.class);
                                    listener.onSuccess(productBinningBean);
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
                    LogUtils.e(TAG, "getIqcList--->" + e);
                }
            }
        }, null);
    }


//    /**
//     * 获取汇总列表
//     */
//    public void getInBinningSum(final InBinningBean clickItemPutBean, final GetZSumListener listener) {
//        ThreadPoolManager.getInstance().executeTask(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.b028.list.get", mTimestamp, clickItemPutBean);
//                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
//                        @Override
//                        public void onResponse(String string) {
//                            String error = mContext.getString(R.string.unknow_error);
//                            if (null != string) {
//                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
//                                    ListSumBean sumBean = JsonResp.getParaData(string,ListSumBean.class);
//                                    ArrayList<ListSumBean> showBeanList = new ArrayList<>();
//                                    showBeanList.add(sumBean);
//                                    listener.onSuccess(showBeanList);
//                                    return;
//                                } else {
//                                    error = JsonResp.getDescription(string);
//                                }
//                            }
//                            listener.onFailed(error);
//                        }
//                    });
//                } catch (Exception e) {
//                    listener.onFailed(mContext.getString(R.string.unknow_error));
//                    LogUtils.e(TAG, "getPutInStoreSum--->" + e);
//                }
//            }
//        }, null);
//    }

    /**
     * 装箱保存
     */
    public interface SaveBinningListener {
        public void onSuccess();

        public void onFailed(String error);
    }

    /**
     * 保存
     */
    public void scanBinningSave(final SaveBean saveBean, final SaveBinningListener listener) {
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
                                    listener.onSuccess();
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
                    LogUtils.e(TAG, "getPutInStoreSum--->" + e);
                }
            }
        }, null);
    }
    /**
     * 点击获取明细
     */
    public interface GetInBinnigDetailDataListener {

        public void onSuccess(List<DetailShowBean> datas);

        public void onFailed(String error);
    }

    /**
     * 点击获取明细
     */
    public void getInBinningDetailData(final Map<String, String> map, final GetInBinnigDetailDataListener listener) {
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
                                    List<DetailShowBean> showBeanList = JsonResp.getParaDatas(string, "list_detail", DetailShowBean.class);
                                    if (showBeanList.size()>0) {
                                        listener.onSuccess(showBeanList);
                                        return;
                                    }else{
                                        error=mContext.getString(R.string.data_null);
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }

    /**
     * 删除
     */
    public interface DeleteInBinningDetailDataListener {

        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 删除明细
     */
    public void deleteInBinningDetailData(final List<Map<String, String>> maps, final DeleteInBinningDetailDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("data", maps);
                    String createJson = JsonReqForERP.dataCreateJson(mModule, "als.pack.scan.list.del", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    String str = JsonResp.getDescription(string);
                                    listener.onSuccess(str);
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
                    LogUtils.e(TAG, "deleteInBinningDetailData--->" + e);
                }
            }
        }, null);
    }

    /**
     * 提交
     *
     * @param map map可以直接为空
     */
    public void commit(final Map<String, String> map, final CommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.b028.submit", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    listener.onSuccess(JsonResp.getParaString(string, AddressContants.DOC_NO));
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
                    LogUtils.e(TAG, "commit--->" + e);
                }
            }
        }, null);
    }


}
