package digiwin.smartdepott100.module.logic.purchase;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.purchase.BadReasonBean;
import digiwin.smartdepott100.module.bean.purchase.BadReasonCommitBean;
import digiwin.smartdepott100.module.bean.purchase.CheckValueBean;
import digiwin.smartdepott100.module.bean.purchase.CheckValueCommitBean;
import digiwin.smartdepott100.module.bean.purchase.IQCCommitBean;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * Created by lenovo on 2017/8/11.
 */

public class IQCInspectLogic extends CommonLogic {

    protected IQCInspectLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    private static IQCInspectLogic logic;

    public static IQCInspectLogic getInstance(Context context, String module, String timestamp){
        return logic = new IQCInspectLogic(context, module, timestamp);
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
    public void getIQCScanDatas(final Map<String,String> map, final GetScanListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.IQCINSPECTSCAN, mTimestamp, map);
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
     * 筛选IQC检验列表item信息
     */
    public void getIQCInspectDatas(final Map<String,String> map, final IQCInspectListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.IQCINSPECTSCANITEM, mTimestamp, map);
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
     * 获取IQC不良原因
     * fqc通用
     */
    public interface IQCGetBadReasonListener{

        public void onSuccess(List<BadReasonBean> datas);

        public void onFailed(String error);
    }

    /**
     * 获取IQC不良原因
     * fqc通用
     */
    public void getIQCBadReason(final Map<String,String> map, final IQCGetBadReasonListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String reqName="";
                    if (ModuleCode.FQC.equals(mModule)){
                        reqName="als.b002.reason.get";
                    }
                    else if (ModuleCode.OQC.equals(mModule)){
                        reqName="als.d002.reason.get";
                    }
                    else{
                        reqName="als.a009.reason.get";
                    }
                    String createJson = JsonReqForERP.mapCreateJson(mModule,reqName, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<BadReasonBean> datas = JsonResp.getParaDatas(string, "list", BadReasonBean.class);
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
     * 新增/删除IQC不良原因
     * fqc通用
     */
    public interface IQCUpDateBadReasonListener{

        public void onSuccess(List<BadReasonBean> datas);

        public void onFailed(String error);
    }

    /**
     * 新增/删除IQC不良原因
     * fqc通用
     */
    public void upDateIQCBadReason(final BadReasonCommitBean commitData, final IQCUpDateBadReasonListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String reqName="";
                    if (ModuleCode.FQC.equals(mModule)){
                        reqName="als.b002.reason.save";
                    }
                    else if (ModuleCode.OQC.equals(mModule)){
                        reqName="als.d002.reason.save";
                    }else{
                        reqName="als.a009.reason.save";
                    }
                    String createJson = JsonReqForERP.dataCreateJson(mModule, reqName, mTimestamp, commitData);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<BadReasonBean> datas = JsonResp.getParaDatas(string, "list", BadReasonBean.class);
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
     * IQC不良原因首字母查询
     * fqc通用
     */
    public interface IQCSearchBadReasonListener{

        public void onSuccess(List<BadReasonBean> datas);

        public void onFailed(String error);
    }

    /**
     * IQC不良原因首字母查询
     * fqc通用
     */
    public void searchIQCBadReason(final HashMap<String,String> map, final IQCSearchBadReasonListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String reqName="";
                    if (ModuleCode.FQC.equals(mModule)){
                        reqName="als.b002.initial.reason.get";
                    }
                    else if (ModuleCode.OQC.equals(mModule)){
                        reqName="als.d002.initial.reason.get";
                    }else{
                        reqName="als.a009.initial.reason.get";
                    }
                    String createJson = JsonReqForERP.mapCreateJson(mModule, reqName, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<BadReasonBean> datas = JsonResp.getParaDatas(string, "list", BadReasonBean.class);
                                    if (datas.size()>0) {
                                        listener.onSuccess(datas);
                                        return;
                                    }else {
                                        error=mContext.getString(R.string.data_null);
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
     * IQC测量值查询
     * fqc通用
     */
    public interface IQCSearchCheckValueListener{

        public void onSuccess(List<CheckValueBean> datas);

        public void onFailed(String error);
    }

    /**
     * IQC测量值查询
     * fqc通用
     */
    public void searchIQCCheckValue(final HashMap<String,String> map, final IQCSearchCheckValueListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String reqName="";
                    if (ModuleCode.FQC.equals(mModule)){
                        reqName="als.b002.measure.get";
                    }
                    else if (ModuleCode.OQC.equals(mModule)){
                        reqName="als.d002.measure.get";
                    }else{
                        reqName="als.a009.measure.get";
                    }
                    String createJson = JsonReqForERP.mapCreateJson(mModule,reqName, mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<CheckValueBean> datas = JsonResp.getParaDatas(string, "list", CheckValueBean.class);
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
     * IQC测量值更新
     * fqc通用
     */
    public interface IQCUpdateCheckValueListener{

        public void onSuccess(List<CheckValueBean> datas);

        public void onFailed(String error);
    }

    /**
     * IQC测量值更新
     * fqc通用
     */
    public void updateIQCCheckValue(final CheckValueCommitBean commitBean, final IQCSearchCheckValueListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String reqName="";
                    if (ModuleCode.FQC.equals(mModule)){
                        reqName="als.b002.measure.save";
                    }
                    else if (ModuleCode.OQC.equals(mModule)){
                        reqName="als.d002.measure.save";
                    }else{
                        reqName="als.a009.measure.save";
                    }
                    String createJson = JsonReqForERP.dataCreateJson(mModule, reqName, mTimestamp, commitBean);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<CheckValueBean> datas = JsonResp.getParaDatas(string, "list", CheckValueBean.class);
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
     * IQC提交
     */
    public interface IQCCommitListener{

        public void onSuccess(String result);

        public void onFailed(String error);
    }

    /**
     * IQC提交
     */
    public void commitIQC(final IQCCommitBean commitBean, final IQCCommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.dataCreateJson(mModule, ReqTypeName.IQCINSPECTCOMMIT, mTimestamp, commitBean);
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
