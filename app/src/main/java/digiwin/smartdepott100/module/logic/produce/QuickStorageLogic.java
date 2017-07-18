package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 孙长权
 * @module 快速入库管理类
 * @date 2017/6/16
 */

public class QuickStorageLogic extends CommonLogic {

    private static final String TAG = "QuickStorageLogic";

    private static QuickStorageLogic logic;

    private QuickStorageLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);

    }

    public static QuickStorageLogic getInstance(Context context, String module, String timestamp) {

        return logic = new QuickStorageLogic(context, module, timestamp);
    }

    /**
     * 筛选  获取待办事项
     */
    public void getQuickStorageOrderData(final FilterBean filterBean, final GetOrderListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = ObjectAndMapUtils.getValueMap(filterBean);
                    final String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.QUICKSTORAGELIST, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<FilterResultOrderBean> showBeanList = JsonResp.getParaDatas(string, "list", FilterResultOrderBean.class);
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
     * 从待办事项进入汇总页面
     */
    public void getQuickStorageOrderSumData(final ClickItemPutBean clickItemPutBean, final GetOrderSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = ObjectAndMapUtils.getValueMap(clickItemPutBean);
                    final String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.QUICKSTORAGESUMDATA, mTimestamp, map);
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
     * recordset 提交请求
     */
    public void commitQuickStorageList(final List<Map<String, String>> listMap, final CommitListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("data", listMap);
                    String createJson = JsonReqForERP.dataCreateJson(mModule, ReqTypeName.QUICKSTORAGE_COMMIT, mTimestamp.toString(), map);
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
                    LogUtils.e(TAG, "scanBarcode--->" + e);
                }
            }
        }, null);
    }

}
