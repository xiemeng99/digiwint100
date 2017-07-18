package digiwin.smartdepott100.module.logic.stock.printer;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureOrderBean;
import digiwin.smartdepott100.module.bean.stock.PrintLabelFlowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;

/**
 * Created by qGod on 2017/5/28
 * Thank you for watching my code
 */

public class PrinterFlowLogic extends CommonLogic {
    public static PrinterFlowLogic logic;

    protected PrinterFlowLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static PrinterFlowLogic getInstance(Context context, String module, String timestamp) {
        return logic = new PrinterFlowLogic(context, module, timestamp);
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
     * 扫描设备编号和模具编号
     */
    public interface ScanNoListener {
        public void onSuccess(String resource);

        public void onFailed(String error);
    }

    /**
     * 扫描设备编号和模具编号
     *
     * @param map
     * @param listener
     */
    public void scanNo(final Map<String, String> map, final ScanNoListener listener) {
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
                                    String resource = JsonResp.getParaString(string, "showing");
                                    if (null != resource) {
                                        listener.onSuccess(resource);
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
     * 扫描工作人员
     */
    public interface ScanPeopleListener {
        public void onSuccess(String resource);

        public void onFailed(String error);
    }

    /**
     * 扫描设备编号和模具编号
     *
     * @param map
     * @param listener
     */
    public void scanPeople(final Map<String, String> map, final ScanPeopleListener listener) {
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
                                    String resource = JsonResp.getParaString(string, "employee_name");
                                    if (null != resource) {
                                        listener.onSuccess(resource);
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
     * 查询
     */
    public interface queryDataListener {
        public void onSuccess(List<PrintLabelFlowBean> datas);

        public void onFailed(String error);
    }

    /**
     * 查询（流转标签补打）
     */
    public void queryData(final Map<String, String> map, final queryDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.PRINTERQUERY, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
//                            String string = JsonText.readAssertResource();
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<PrintLabelFlowBean> datas = JsonResp.getParaDatas(string, "list", PrintLabelFlowBean.class);
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
     * 查询(成品标签补打)
     */
    public void queryFinishData(final Map<String, String> map, final queryDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.PRINTEFINISHRQUERY, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<PrintLabelFlowBean> datas = JsonResp.getParaDatas(string, "list", PrintLabelFlowBean.class);
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

}
