package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

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
import digiwin.smartdepott100.module.bean.produce.CompletingStoreBean;

/**
 * @author 赵浩然
 * @module 完工入库管理类
 * @date 2017/3/13
 */

public class CompletingStoreLogic {

    private static final String TAG = "WorkOrderlLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static CompletingStoreLogic logic;

    private CompletingStoreLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;

    }


    public static CompletingStoreLogic getInstance(Context context, String module, String timestamp) {

        return logic = new CompletingStoreLogic(context, module, timestamp);
    }

    /**
     * 扫描工单号监听
     */
    public interface ScanCodeListener {
        public void onSuccess(CompletingStoreBean saveBean);

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
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.COMPLETINGSTORE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.COMPLETINGSTORE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<CompletingStoreBean> accordingMaterialSumBeen = xmlResp.getMasterDatas(CompletingStoreBean.class);
                                    if (accordingMaterialSumBeen.size() > 0) {
                                        CompletingStoreBean data = new CompletingStoreBean();
                                        for (int i = 0; i < accordingMaterialSumBeen.size(); i++) {
                                            data = accordingMaterialSumBeen.get(i);
                                            data.setAvailable_in_qty(StringUtils.deleteZero(data.getAvailable_in_qty()));
                                        }
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
     * 提交
     */
    public interface CommitListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 提交
     */
    public void commit(final Map<String, String> map, final CommitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.COMPLETINGSTORECOMMIT, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.COMPLETINGSTORECOMMIT, string);
                            String error= mContext.getString(R.string.unknow_error);
                            if (null!=xmlResp){
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())){
                                    listener.onSuccess(xmlResp.getFieldString());
                                    return;
                                }else {
                                    error=xmlResp.getDescription();
                                }
                            }
                            listener.onFailed(error);
                        }
                    });
                }catch (Exception e){
                    listener.onFailed(mContext.getString(R.string.unknow_error));
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        },null);
    }
}
