package digiwin.smartdepott100.module.logic.dailywok;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.json.JsonText;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCheckinCommitBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCheckoutBadResBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCheckoutCommitBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCirculationBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureDevMouKniBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureEmployeeBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureNoCheckBackBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureOrderBean;

/**
 * @author xiemeng
 * @des 富钛生产报工check
 * @date 2017/5/16
 */

public class ProcedureCheckLogic {

    private static final String TAG = "ProcedureCheckLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static ProcedureCheckLogic logic;

    private ProcedureCheckLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;
    }


    public static ProcedureCheckLogic getInstance(Context context, String module, String timestamp) {

        return logic = new ProcedureCheckLogic(context, module, timestamp);
    }

    /**
     * 扫描工序号
     */
    public interface ScanProcedureListener {
        public void onSuccess(ProcedureNoCheckBackBean procedureBackBean);

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
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.SCANPROCECHECK, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProcedureNoCheckBackBean procedureBackBean = JsonResp.getParaData(string, ProcedureNoCheckBackBean.class);
                                    if (null != procedureBackBean) {
                                        listener.onSuccess(procedureBackBean);
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
     * 扫描工单号
     */
    public interface ScanOrderListener {
        public void onSuccess(ProcedureOrderBean procedureOrderBean);

        public void onFailed(String error);
    }

    /**
     * 扫描工单号
     *
     * @param map
     * @param listener
     */
    public void scanOrder(final Map<String, String> map, final ScanOrderListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.SCANORDER, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProcedureOrderBean procedureOrderBean = JsonResp.getParaData(string, ProcedureOrderBean.class);
                                    if (null != procedureOrderBean) {
                                        listener.onSuccess(procedureOrderBean);
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
     * 扫描报工人监听
     */
    public interface ScanPersonListener {
        public void onSuccess(ProcedureEmployeeBean WorkerPerson);

        public void onFailed(String error);
    }

    /**
     * 扫描报工人
     */
    public void scanPerson(final Map<String, String> map, final ScanPersonListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.SCANEMPLYEECHECK, mTimestamp, map);
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
     * 扫描资源
     */
    public interface ScanResourceListener {
        public void onSuccess(ProcedureDevMouKniBean devMouKniBean);

        public void onFailed(String error);
    }

    /**
     * 扫描资源
     *
     * @param map
     * @param listener
     */
    public void scanResource(final Map<String, String> map, final ScanResourceListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.SCANRESOURCE, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    ProcedureDevMouKniBean devMouKniBean = JsonResp.getParaData(string, ProcedureDevMouKniBean.class);
                                    if (null != devMouKniBean) {
                                        listener.onSuccess(devMouKniBean);
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
     * 扫描不良原因
     */
    public interface ScanDefectListener {
        public void onSuccess(List<ProcedureCheckoutBadResBean> searchBadResBeen);

        public void onFailed(String error);
    }

    /**
     * 扫描不良原因
     *
     * @param map
     * @param listener
     */
    public void scanDefect1(final Map<String, String> map, final ScanDefectListener listener) {
        String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.DEFECTRES, mTimestamp, map);
        String error = mContext.getString(R.string.unknow_error);
        String string = JsonText.readAssertResource();
        if (null != string) {
            if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                List<ProcedureCheckoutBadResBean> badResBeen = JsonResp.getParaDatas(string, "reason_list", ProcedureCheckoutBadResBean.class);
                if (null != badResBeen) {
                    listener.onSuccess(badResBeen);
                    return;
                }
            } else {
                error = JsonResp.getDescription(string);
            }
        }

    }

    /**
     * 扫描不良原因
     *
     * @param map
     * @param listener
     */
    public void scanDefect(final Map<String, String> map, final ScanDefectListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.DEFECTRES, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
//                                    List<ProcedureCheckoutBadResBean> badResBeen = JsonResp.getParaDatas(string, "reason_list", ProcedureCheckoutBadResBean.class);
                                    //"reason_list",
                                    List<ProcedureCheckoutBadResBean> list = new ArrayList<>();
                                    ProcedureCheckoutBadResBean badResBeen = JsonResp.getParaData(string, ProcedureCheckoutBadResBean.class);
                                    list.add(badResBeen);
                                    if (null != badResBeen) {
                                        listener.onSuccess(list);
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
     * 提交监听
     */
    public interface CommitListListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * check in 提交
     */
    public void commitCheckin(final ProcedureCheckinCommitBean commitBean, final CommitListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, ReqTypeName.CHECKINCOMMIT, mTimestamp, commitBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    listener.onSuccess(JsonResp.getParaString(string, ""));
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

    /**
     * check out 提交
     */
    public void commitCheckout(final ProcedureCheckoutCommitBean commitBean, final CommitListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, ReqTypeName.CHECKOUTCOMMIT, mTimestamp, commitBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    listener.onSuccess(JsonResp.getParaString(string, ""));
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
