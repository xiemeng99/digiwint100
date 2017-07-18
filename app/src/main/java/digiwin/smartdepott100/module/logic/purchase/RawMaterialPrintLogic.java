package digiwin.smartdepott100.module.logic.purchase;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.module.bean.purchase.RawMaterialPrintBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ThreadPoolManager;

/**
 * @author xiemeng
 * @des 原材料标签打印
 * @date 2017/6/8
 */
public class RawMaterialPrintLogic extends CommonLogic {
    public static RawMaterialPrintLogic logic;

    protected RawMaterialPrintLogic(Context context, String module, String timestamp) {
        super(context, module, timestamp);
    }

    public static RawMaterialPrintLogic getInstance(Context context, String module, String timestamp) {
        return logic = new RawMaterialPrintLogic(context, module, timestamp);
    }

    /**
     * 扫描收货单
     */
    public interface ScanOrderListener {
        public void onSuccess(List<RawMaterialPrintBean> sumBeen);

        public void onFailed(String error);
    }

    /**
     * @param map
     * @param listener
     */
    public void scanOrder(final Map<String, String> map, final ScanOrderListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String createJson = JsonReqForERP.mapCreateJson(mModule, "als.a009.print.get", mTimestamp, map);
                    OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != string) {
                                if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                                    List<RawMaterialPrintBean> sumBeen = JsonResp.getParaDatas(string, "list", RawMaterialPrintBean.class);
                                    if (null != sumBeen) {
                                        listener.onSuccess(sumBeen);
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
