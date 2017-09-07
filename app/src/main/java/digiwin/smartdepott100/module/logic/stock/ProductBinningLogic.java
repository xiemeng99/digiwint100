package digiwin.smartdepott100.module.logic.stock;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.stock.ProductBinningBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 产品装箱
 * @date 2017/8/23
 */
public class ProductBinningLogic extends CommonLogic {
    public static ProductBinningLogic logic;

    private ProductBinningLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static ProductBinningLogic getInstance(Context context, String module, String timestamp) {

        return logic = new ProductBinningLogic(context, module, timestamp);
    }

    /**
     * 扫描包装箱号
     */
    public interface ScanPackBoxNumberListener {
        public void onSuccess(List<ProductBinningBean> productBinningBeans);

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
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.package.list.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<ProductBinningBean> showBeanList = JsonResp.getParaDatas(string, "list", ProductBinningBean.class);
                                    if (showBeanList.size() > 0) {
                                        listener.onSuccess(showBeanList);
                                    } else {
                                        error = mContext.getString(R.string.data_null);
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
                    LogUtils.e(TAG, "getIqcList--->" + e);
                }
            }
        }, null);
    }

    /**
     * 装箱保存
     */
    public interface SaveBinningListener {
        public void onSuccess(ProductBinningBean backBean);

        public void onFailed(String error);
    }

    /**
     * 保存
     */
    public void saveBean(final ProductBinningBean saveBean, final SaveBinningListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.c016.save", mTimestamp, saveBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProductBinningBean productbinningbean = JsonResp.getParaData(string, ProductBinningBean.class);
                                    listener.onSuccess(productbinningbean);
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
     * 产品装箱删除（出箱）
     */
    public interface InsertAndDeleteListener {
        public void onSuccess(ProductBinningBean show);

        public void onFailed(String error);
    }
    /**
     * 产品装箱删除（出箱）
     */
    public void delete(final List<ProductBinningBean> list, final InsertAndDeleteListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("data",list);
                    String createJson = JsonReqForERP.dataCreateJson(mModule, "als.package.del", mTimestamp, hashMap);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProductBinningBean productbinningbean = JsonResp.getParaData(string, ProductBinningBean.class);
                                    listener.onSuccess(productbinningbean);
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


}
