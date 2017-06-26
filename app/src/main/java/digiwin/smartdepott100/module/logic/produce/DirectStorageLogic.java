package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.ScanPlotNoBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @des 快速完工Logic
 * Created by maoheng on 2017/6/12.
 */

public class DirectStorageLogic extends CommonLogic{

    public static DirectStorageLogic logic;

    protected DirectStorageLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }
    public static DirectStorageLogic getInstance(Context context, String module, String timestamp) {
        return logic = new DirectStorageLogic(context, module, timestamp);
    }
    public interface ScanOrderListener{

        void onSuccess(ListSumBean sumBean);

        void onFailed(String error);
    }
    public void scanOrder(final Map<String,String> map, final ScanOrderListener listener){
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.b005.wo_basis.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ListSumBean sumBean = JsonResp.getParaData(string, ListSumBean.class);
                                    if (null != sumBean){
                                        listener.onSuccess(sumBean);
                                        return;
                                    }else {
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
                    LogUtils.e(TAG, "scanBarcode--->" + e);
                }
            }
        }, null);
    }

    public interface DirectStorageCommitListener{

        void onSuccess(String msg);

        void onFailed(String error);
    }
    public void directStorageCommit(final Map<String,String> map, final DirectStorageCommitListener listener){
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.b005.submit", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    listener.onSuccess(JsonResp.getParaString(string,"doc_no"));
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
