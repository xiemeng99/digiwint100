package digiwin.smartdepott100.module.logic.sale.scanoutstore;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.sale.scanout.ScanOutDetailData;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.InBinningLogic;

/**
 * @author maoheng
 * @des 扫码出货
 * @date 2017/4/3
 */

public class ScanOutStoreLogic extends CommonLogic {
    public static ScanOutStoreLogic logic;

    private ScanOutStoreLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static ScanOutStoreLogic getInstance(Context context, String module, String timestamp) {

        return logic = new ScanOutStoreLogic(context, module, timestamp);
    }
    /**
     * 保存
     */
    public interface ScanOutSaveListener {

        public void onSuccess();

        public void onFailed(String error);
    }

    /**
     * 保存
     */
    public void saveScanOutStore(final SaveBean saveBean, final ScanOutSaveListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = ObjectAndMapUtils.getValueMap(saveBean);
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.SCANOUTSAVE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.SCANOUTSAVE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
//                                    List<ScanOutSaveBackBean> masterDatas = xmlResp.getMasterDatas(ScanOutSaveBackBean.class);
//                                    if (masterDatas.size() == 0){
//                                        listener.onFailed(mContext.getResources().getString(R.string.data_null));
//                                        return;
//                                    }
                                    listener.onSuccess();
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }

    /**
     * 点击获取明细
     */
    public interface GetScanOutDetailDataListener {

        public void onSuccess(List<ScanOutDetailData> datas);

        public void onFailed(String error);
    }

    /**
     * 点击获取明细
     */
    public void getScanOutDetailData(final Map<String, String> map, final GetScanOutDetailDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.package.scan.list.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<ScanOutDetailData> showBeanList = JsonResp.getParaDatas(string, "list_detail", ScanOutDetailData.class);
                                    if (showBeanList.size()>0) {
                                        listener.onSuccess(showBeanList);
                                        return;
                                    }else{
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
                    LogUtils.e(TAG, "scan out save--->" + e);
                }
            }
        }, null);
    }

    /**
     * 删除
     */
    public interface DeleteScanOutDetailDataListener {

        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 删除明细
     */
    public void deleteScanOutDetailData(final List<Map<String, String>> maps, final DeleteScanOutDetailDataListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.objCreateJson(mModule, "als.pack.scan.list.del", mTimestamp, maps);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    String str = JsonResp.getDescription(string);
                                    listener.onSuccess(str);
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
                    LogUtils.e(TAG, "deleteInBinningDetailData--->" + e);
                }
            }
        }, null);
    }
}
