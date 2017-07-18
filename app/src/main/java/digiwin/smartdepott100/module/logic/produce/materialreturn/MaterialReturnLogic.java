package digiwin.smartdepott100.module.logic.produce.materialreturn;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Map;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;

/**
 * Created by MengyuTang on 2017/5/28.
 * 生产退料logic
 */

public class MaterialReturnLogic extends CommonLogic {

    private static MaterialReturnLogic logic;

    private String TAG = "MaterialReturnLogic";

    protected MaterialReturnLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static MaterialReturnLogic getInstance(Context context, String module, String timestamp) {
        logic = new MaterialReturnLogic(context, module, timestamp);
        return logic;
    }

    /**
     * 生产退料获取列表数据
     *
     * @param filterBean
     */
    public void getMRListData(final FilterBean filterBean, final GetDataListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.b010.list.get", mTimestamp, filterBean);
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
     * 获取汇总数据
     *
     * @param clickItemPutBean
     * @param listener
     */
    public void getMRSumData(final ClickItemPutBean clickItemPutBean, final GetZSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.b010.list.detail.get", mTimestamp, clickItemPutBean);
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
    public void commitMRData(final Map<String, String> map, final CommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.b010.submit", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    String doc_no = JsonResp.getParaString(string, "doc_no");
                                    Log.d(TAG, "doc_no:" + doc_no);
                                    if (null != doc_no) {
                                        listener.onSuccess(doc_no);
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
}

