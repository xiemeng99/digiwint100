package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

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
import digiwin.smartdepott100.module.bean.purchase.IQCCommitBean;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;
import digiwin.smartdepott100.module.logic.purchase.IQCInspectLogic;

/**
 * @des  fqc逻辑
 * @date 2017/8/17
 * @author xiemeng
 */
public class FQCInspectLogic extends IQCInspectLogic {

    protected FQCInspectLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    private static FQCInspectLogic logic;

    public static FQCInspectLogic getInstance(Context context, String module, String timestamp){
        return logic = new FQCInspectLogic(context, module, timestamp);
    }

    /**
     * 获取IQC扫描信息
     */
    public interface GetScanListener{

        public void onSuccess(List<QCScanData> datas);

        public void onFailed(String error);
    }

    /**
     * 筛选IQC检验列表
     */
    public void getFQCScanDatas(final Map<String,String> map, final GetScanListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.b002.list.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<QCScanData> datas = JsonResp.getParaDatas(string, "list", QCScanData.class);
                                    LogUtils.e("par",datas.size()+"");
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
     * 获取IQC扫描item信息
     */
    public interface IQCInspectListener{

        public void onSuccess(List<QCScanData> datas);

        public void onFailed(String error);
    }

    /**
     * 筛选fQC检验列表item信息
     */
    public void getFQCInspectDatas(final Map<String,String> map, final IQCInspectListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.b002.test.item.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<QCScanData> datas = JsonResp.getParaDatas(string, "list", QCScanData.class);
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
     * FQC提交
     */
    public interface FQCCommitListener {

        public void onSuccess(String result);

        public void onFailed(String error);
    }

    /**
     * FQC提交
     */
    public void commitFQC(final IQCCommitBean commitBean, final FQCCommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.dataCreateJson(mModule,"als.b002.submit", mTimestamp, commitBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    String doc_no = JsonResp.getParaString(string, "doc_no");
                                    if (null != doc_no) {
                                        listener.onSuccess(doc_no);
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
