package digiwin.smartdepott100.module.logic.purchase;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.module.bean.purchase.MaterialReceiptBean;

/**
 * @author 赵浩然
 * @module 采购收货管理类
 * @date 2017/3/8
 */

public class MaterialReceiptLogic {

    private static final String TAG = "QuickReceiptLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static MaterialReceiptLogic logic;

    private MaterialReceiptLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;
    }

    public static MaterialReceiptLogic getInstance(Context context, String module, String timestamp) {

        return logic = new MaterialReceiptLogic(context, module, timestamp);
    }

    /**
     * 扫描入库单监听
     */
    public interface scanDeliveryNoteListener {
        public void onSuccess(List<MaterialReceiptBean> beanList);

        public void onFailed(String error);
    }

    /**
     * 扫描入库单请求
     */
    public void scanDeliveryNote(final HashMap<String, String> map, final scanDeliveryNoteListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.MATERIALRECEIPT, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.MATERIALRECEIPT, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<MaterialReceiptBean> list = xmlResp.getMasterDatas(MaterialReceiptBean.class);
                                    if (list.size() > 0) {
                                        List<MaterialReceiptBean> dataList = new ArrayList<MaterialReceiptBean>();
                                        for (int i = 0; i < list.size(); i++) {
                                            MaterialReceiptBean data = list.get(i);
                                            data.setCheck("1");
                                            data.setShortage_qty(StringUtils.deleteZero(data.getShortage_qty()));
                                            data.setQty(StringUtils.deleteZero(data.getQty()));
                                            dataList.add(data);
                                        }
                                        listener.onSuccess(dataList);
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
     * 扫描入库单 提交监听
     */
    public interface commitListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 扫描入库单 提交请求
     */
    public void commit(final List<Map<String, String>> listMap, final commitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(listMap, mModule, ReqTypeName.MATERIALRECEIPTCOMMIT, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.MATERIALRECEIPTCOMMIT, string);
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
