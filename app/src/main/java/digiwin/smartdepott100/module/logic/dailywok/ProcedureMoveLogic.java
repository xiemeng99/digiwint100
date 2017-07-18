package digiwin.smartdepott100.module.logic.dailywok;

import android.content.Context;

import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCirculationBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureEmployeeBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureMoveCommitBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureNoMoveBean;

/**
 * @author xiemeng
 * @des 富钛工序报工
 * @date 2017/5/16
 */

public class ProcedureMoveLogic {

    private static final String TAG = "ProcedureMoveLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static ProcedureMoveLogic logic;

    private ProcedureMoveLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;
    }


    public static ProcedureMoveLogic getInstance(Context context, String module, String timestamp) {

        return logic = new ProcedureMoveLogic(context, module, timestamp);
    }

    /**
     * 扫描对接人员
     */
    public interface ScanPersonListener {
        public void onSuccess(ProcedureEmployeeBean WorkerPerson);

        public void onFailed(String error);
    }

    /**
     * 扫描对接人员
     */
    public void scanPerson(final Map<String, String> map, final ScanPersonListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.SCANEMPMOVE, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProcedureEmployeeBean workerPerson = JsonResp.getParaData(string, ProcedureEmployeeBean.class);
                                    if (null != workerPerson) {
                                        listener.onSuccess(workerPerson);
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
                    LogUtils.e(TAG, "scanPerson--->" + e);
                }
            }
        }, null);
    }


    /**
     * 扫描工序号
     */
    public interface ScanProcedureListener {
        public void onSuccess(ProcedureNoMoveBean procedureNoMoveBean);

        public void onFailed(String error);
    }

    /**
     * 扫描工序号
     *
     * @param map
     * @param listener
     */
    public void scanProcedure(final Map<String, String> map, final ScanProcedureListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.SCANPROCEMOVE, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProcedureNoMoveBean procedureNoMoveBean = JsonResp.getParaData(string, ProcedureNoMoveBean.class);
                                    if (null != procedureNoMoveBean) {
                                        listener.onSuccess(procedureNoMoveBean);
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
     * 扫描生产批号
     */
    public interface ScanCirculationListener {
        public void onSuccess(ProcedureCirculationBean circulationBackBean);

        public void onFailed(String error);
    }

    /**
     * 扫描生产批号
     *
     * @param map
     * @param listener
     */
    public void scanCirculation(final Map<String, String> map, final ScanCirculationListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.SCANCIR, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProcedureCirculationBean circulationBackBean = JsonResp.getParaData(string, ProcedureCirculationBean.class);
                                    if (null != circulationBackBean) {
                                        listener.onSuccess(circulationBackBean);
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
                    LogUtils.e(TAG, "scanCirculation--->" + e);
                }
            }
        }, null);
    }


    /**
     * 提交监听
     */
    public interface CommitListListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * move in 提交
     */
    public void commitMovein(final ProcedureMoveCommitBean commitBean, final CommitListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, ReqTypeName.MOVEINCOMMIT, mTimestamp, commitBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProcedureMoveCommitBean data = JsonResp.getParaData(string, ProcedureMoveCommitBean.class);
                                    if (null != data) {
                                        listener.onSuccess(data.getReport_no());
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
                    LogUtils.e(TAG, "scanBarcode--->" + e);
                }
            }
        }, null);
    }

    /**
     * move out 提交
     */
    public void commitMoveout(final ProcedureMoveCommitBean commitBean, final CommitListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, ReqTypeName.MOVEOUTCOMMIT, mTimestamp, commitBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProcedureMoveCommitBean data = JsonResp.getParaData(string, ProcedureMoveCommitBean.class);
                                    if (null != data) {
                                        listener.onSuccess(data.getReport_no());
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
                    LogUtils.e(TAG, "scanBarcode--->" + e);
                }
            }
        }, null);
    }
}
