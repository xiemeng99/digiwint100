package digiwin.smartdepott100.module.logic.dailywok;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.module.bean.dailywork.ProcessReportingBean;
import digiwin.smartdepott100.module.bean.dailywork.WorkerPerson;

/**
 * @author 赵浩然
 * @module 工序报工管理类
 * @date 2017/3/15
 */

public class ProcessReportingLogic {

    private static final String TAG = "ProcessReportingLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static ProcessReportingLogic logic;

    private ProcessReportingLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;
    }


    public static ProcessReportingLogic getInstance(Context context, String module, String timestamp) {

        return logic = new ProcessReportingLogic(context, module, timestamp);
    }

    /**
     * 扫描工单 作业号监听
     */
    public interface ScanCodeListener {
        public void onSuccess(ProcessReportingBean data);

        public void onFailed(String error);
    }

    /**
     *  扫描工单 作业号返回
     */
    public void scanCode(final Map<String, String> map, final ScanCodeListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.PROCESSREPORT, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.PROCESSREPORT, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ProcessReportingBean> accordingMaterialSumBeen = xmlResp.getMasterDatas(ProcessReportingBean.class);
                                    if (accordingMaterialSumBeen.size() > 0) {
                                        ProcessReportingBean data = accordingMaterialSumBeen.get(0);
                                        listener.onSuccess(data);
                                        return;
                                    } else {
                                        error = mContext.getString(R.string.data_null);
                                    }
                                } else {
                                    error = xmlResp.getDescription();
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
     * 扫描报工人监听
     */
    public interface ScanPersonListener {
        public void onSuccess(WorkerPerson WorkerPerson);

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
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETWORKPERSON, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETWORKPERSON, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    List<WorkerPerson> locatorBackBeen = xmlResp.getParameterDatas(WorkerPerson.class);
                                    if (locatorBackBeen.size() > 0) {
                                        listener.onSuccess(locatorBackBeen.get(0));
                                        return;
                                    } else {
                                        error = mContext.getString(R.string.data_null);
                                    }
                                } else {
                                    error = xmlResp.getDescription();
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                } catch (Exception e) {
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                    LogUtils.e(TAG, "scanLocator--->" + e);
                }
            }
        }, null);
    }


    /**
     * 扫描入库单 提交监听
     */
    public interface CommitListListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 需要先使用ObjectAndMapUtils.getListMap(checkedList);转换LIST
     * recordset 提交请求
     */
    public void commitList(final List<Map<String, String>> listMap, final CommitListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(listMap, mModule, ReqTypeName.PROCESSREPORTCOMMIT, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.PROCESSREPORTCOMMIT, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    listener.onSuccess(xmlResp.getFieldString());
                                    return;
                                } else {
                                    error = xmlResp.getDescription();
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
