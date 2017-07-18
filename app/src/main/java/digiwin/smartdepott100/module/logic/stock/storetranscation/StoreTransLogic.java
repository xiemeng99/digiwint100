package digiwin.smartdepott100.module.logic.stock.storetranscation;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.module.bean.stock.StoreTransItemNoBean;

/**
 * @author maoheng
 * @des 库存交易锁定管理器
 * @date 2017/3/28
 */

public class StoreTransLogic {
    private static final String TAG = "StoreTransLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static StoreTransLogic logic;

    private StoreTransLogic(Context context, String module, String timestamp) {
        mTimestamp = timestamp;
        mContext = context.getApplicationContext();
        mModule = module;

    }


    public static StoreTransLogic getInstance(Context context, String module, String timestamp) {

        return logic = new StoreTransLogic(context, module, timestamp);
    }

    /**
     * 扫描料号带出信息
     */
    public interface ScanItemNoListener {

        public void onSuccess(StoreTransItemNoBean transItemNoBean);

        public void onFailed(String error);
    }

    /**
     * 扫描料号带出信息
     */
    public void getScanBarcode(final HashMap<String, String> map, final ScanItemNoListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETITEMDETAIL, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETITEMDETAIL, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<StoreTransItemNoBean> masterDatas = xmlResp.getMasterDatas(StoreTransItemNoBean.class);
                                    if (masterDatas.size() != 0) {
                                        listener.onSuccess(masterDatas.get(0));
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
}
