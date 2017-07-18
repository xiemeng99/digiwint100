package digiwin.smartdepott100.module.logic.stock;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;

/**
 * @author xiemeng
 * @des 移动储位
 * @date 2017/6/8
 */
public class MoveStoreLogic extends CommonLogic {
    public static MoveStoreLogic logic;

    private MoveStoreLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static MoveStoreLogic getInstance(Context context, String module, String timestamp) {

        return logic = new MoveStoreLogic(context, module, timestamp);
    }

    /**
     * 汇总查询
     */
    public void getMoveStoreList(final Map<String, String> map, final GetZSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.c003.list.detail.get", mTimestamp, map);
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
                    LogUtils.e(TAG, "getIqcList--->" + e);
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
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.c003.submit", mTimestamp, map);
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
