package digiwin.smartdepott100.module.logic.stock;

import android.content.Context;

import java.util.List;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;

/**
 * @author xiemeng
 * @des 库存查询
 * @date 2017/6/8
 */
public class StoreQueryLogic extends CommonLogic {
    public static StoreQueryLogic logic;

    private StoreQueryLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static StoreQueryLogic getInstance(Context context, String module, String timestamp) {

        return logic = new StoreQueryLogic(context, module, timestamp);
    }

    public interface GetStoreListListener {
        public void onSuccess(List<ListSumBean> showNoBarcodeList, List<ListSumBean> showHasBarocdeList);

        public void onFailed(String error);

    }

    /**
     * 库存查询
     */
    public void getStoreList(final ClickItemPutBean clickItemPutBean, final GetStoreListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.c004.list.get", mTimestamp, clickItemPutBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<ListSumBean> showNoBarcodeList = JsonResp.getParaDatas(string, "list", ListSumBean.class);
                                    List<ListSumBean> showHasBarocdeList = JsonResp.getParaDatas(string, "list1", ListSumBean.class);
                                    listener.onSuccess(showNoBarcodeList, showHasBarocdeList);
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


}
