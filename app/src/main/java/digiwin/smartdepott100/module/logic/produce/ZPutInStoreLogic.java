package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

import java.util.List;
import java.util.Map;

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
import digiwin.library.constant.SharePreKey;
import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.ThreadPoolManager;

/**
 * @author xiemeng
 * @des t100入库上架
 * @date 2017/5/28 11:01
 */

public class ZPutInStoreLogic extends CommonLogic {

    public static ZPutInStoreLogic logic;

    protected ZPutInStoreLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static ZPutInStoreLogic getInstance(Context context, String module, String timestamp) {

        return logic = new ZPutInStoreLogic(context, module, timestamp);
    }


    /**
     * 入库上架获取列表数据
     */
    public void getPutInStoreList(final FilterBean filterBean, final GetDataListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    filterBean.setPagesize((String) SharedPreferencesUtils.get(mContext, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM));
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.b007.list.get", mTimestamp, filterBean);
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
                    LogUtils.e(TAG, "getPutInStoreList--->" + e);
                }
            }
        }, null);
    }


    /**
     * 富钛入库上架获取汇总列表
     */
    public void getPutInStoreSum(final ClickItemPutBean clickItemPutBean, final GetZSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.b007.list.detail.get", mTimestamp, clickItemPutBean);
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
                    LogUtils.e(TAG, "getPutInStoreSum--->" + e);
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
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.b007.submit", mTimestamp, map);
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
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        }, null);
    }

}
