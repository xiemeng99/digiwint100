package digiwin.smartdepott100.module.logic.purchase;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.stock.PQCCheckOutBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;

/**
 * Created by qGod on 2017/5/29
 * Thank you for watching my code
 * PQC检验
 */

public class PQCCheckOutLogic extends CommonLogic {
    private static PQCCheckOutLogic instance;

    protected PQCCheckOutLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static PQCCheckOutLogic getInstance(Context context, String module, String timestamp) {
        return instance = new PQCCheckOutLogic(context, module, timestamp);
    }

    /**
     * 筛选
     */
    public interface FiltrateListener {
        public void onSuccess(List<PQCCheckOutBean> datas);

        public void onFailed(String error);
    }

    /**
     * 筛选
     */
    public void getFiltrateDatas(final Map<String, String> map, final FiltrateListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.FILTRATE, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<PQCCheckOutBean> datas = JsonResp.getParaDatas(string, "list", PQCCheckOutBean.class);
                                    if (null != datas) {
                                        listener.onSuccess(datas);
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
                    LogUtils.e(TAG, "scanProcedure--->" + e);
                }
            }
        }, null);
    }

    /**
     * 提交
     */
    public interface CommitListener {
        public void onSuccess(String qcNo);

        public void onFailed(String error);
    }

    /**
     * 提交
     */
    public void commitData(final Map<String, String> map, final CommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.PQCCOMMIT, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    String str = JsonResp.getParaString(string, AddressContants.DOC_NO);
                                    if (null != str) {
                                        listener.onSuccess(str);
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
                    LogUtils.e(TAG, "scanProcedure--->" + e);
                }
            }
        }, null);
    }


}
