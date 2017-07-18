package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.produce.AccordingMaterialSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author songjie
 * @module 依工单发料管理类
 * @date 2017/3/2
 */

public class WorkOrderlLogic extends CommonLogic {

    private static final String TAG = "WorkOrderlLogic";

    private static WorkOrderlLogic logic;

    private WorkOrderlLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);

    }


    public static WorkOrderlLogic getInstance(Context context, String module, String timestamp) {

        return logic = new WorkOrderlLogic(context, module, timestamp);
    }

    /**
     * 扫描工单号监听
     */
    public interface ScanCodeListener {
        public void onSuccess(List<ListSumBean> accordingMaterialSumBeen);

        public void onFailed(String error);
    }

    /**
     * 扫描工单号
     */
    public void scanCode(final Map<String, String> map, final ScanCodeListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.b003.list.detail.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String s) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != s) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(s))) {
                                    List<ListSumBean> accordingMaterialSumBeen = JsonResp.getParaDatas(s, "list_detail", ListSumBean.class);
                                    if (accordingMaterialSumBeen.size() > 0) {
                                        List<ListSumBean> dataList = new ArrayList<ListSumBean>();
                                        for (int i = 0; i < accordingMaterialSumBeen.size(); i++) {
                                            ListSumBean data = accordingMaterialSumBeen.get(i);
                                            data.setStock_qty(StringUtils.deleteZero(data.getStock_qty()));
                                            data.setApply_qty(StringUtils.deleteZero(data.getApply_qty()));
                                            data.setScan_sumqty(StringUtils.deleteZero(data.getScan_sumqty()));
                                            dataList.add(data);
                                        }
                                        listener.onSuccess(dataList);
                                        return;
                                    }

                                } else {
                                    error = JsonResp.getDescription(s);
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


    /**
     * 提交
     */
    public interface CommitListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 提交
     */
    public void commit(final Map<String, String> map, final CommonLogic.CommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.b003.submit", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    String doc_no = JsonResp.getParaString(string, "doc_no");
                                    if (null != doc_no) {
                                        listener.onSuccess(JsonResp.getParaString(string, "doc_no"));
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
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        }, null);
    }
//            public void run() {
//                try {
//                    String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.COMMIT, mTimestamp, map);
//                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
//                        @Override
//                        public void onResponse(String string) {
//                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.COMMIT, string);
//                            String error= mContext.getString(R.string.unknow_error);
//                            if (null!=xmlResp){
//                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())){
//                                    listener.onSuccess(xmlResp.getFieldString());
//                                    return;
//                                }else {
//                                    error=xmlResp.getDescription();
//                                }
//                            }
//                            listener.onFailed(error);
//                        }
//                    });
//                }catch (Exception e){
//                    listener.onFailed(mContext.getString(R.string.unknow_error));
//                    LogUtils.e(TAG, "getSum--->" + e);
//                }
//            }

}
