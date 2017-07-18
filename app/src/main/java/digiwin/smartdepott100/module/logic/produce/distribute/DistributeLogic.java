package digiwin.smartdepott100.module.logic.produce.distribute;

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
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.common.UnCompleteBean;
import digiwin.smartdepott100.module.bean.produce.DistributeOrderHeadData;
import digiwin.smartdepott100.module.bean.produce.DistributeSumShowBean;

/**
 * 生产配货 logic
 */

public class DistributeLogic {

    private static DistributeLogic logic;

    private static final String TAG = "DistributeLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private DistributeLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;

    }

    public static DistributeLogic getInstance(Context context, String module, String timestamp) {
        logic = new DistributeLogic(context, module, timestamp);
        return logic;

    }

    /**
     * 扫描物料条码
     */
    public interface ScanBarcodeListener {
        public void onSuccess(ScanBarcodeBackBean barcodeBackBean);

        public void onFailed(String error);
    }

    /**
     * 扫描物料条码
     */
    public void scanBarcode(final Map<String, String> map, final ScanBarcodeListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.BARCODE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.BARCODE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ScanBarcodeBackBean> barcodeBackBeen = xmlResp.getParameterDatas(ScanBarcodeBackBean.class);
                                    if (barcodeBackBeen.size() > 0) {
                                        listener.onSuccess(barcodeBackBeen.get(0));
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
     * 扫描库位
     */
    public interface ScanLocatorListener {
        public void onSuccess(ScanLocatorBackBean locatorBackBean);

        public void onFailed(String error);
    }

    /**
     * 扫描库位
     */
    public void scanLocator(final Map<String, String> map, final ScanLocatorListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.STORAGE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.STORAGE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE .equals( xmlResp.getCode())) {
                                    List<ScanLocatorBackBean> locatorBackBeen = xmlResp.getParameterDatas(ScanLocatorBackBean.class);
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
     * 保存
     */
    public interface SaveListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 保存
     */
    public void scanSave(final Map<String, String> map, final SaveListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.SCANINFOSAVE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.SCANINFOSAVE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    String fieldString = xmlResp.getFieldString();
                                    listener.onSuccess(fieldString);
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
                    LogUtils.e(TAG, "scanSave--->" + e);
                }
            }
        }, null);
    }

    /**
     * 汇总展示
     */
    public interface GetSumListener {
        public void onSuccess(List<DistributeOrderHeadData> headDataList,List<DistributeSumShowBean> list);

        public void onFailed(String error);
    }

    /**
     * 汇总展示
     */
    public void getSum(final Map<String, String> map, final GetSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.POSTMATERIALSUMDATA, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.POSTMATERIALSUMDATA, string);
                            String error= mContext.getString(R.string.unknow_error);
                            if (null!=xmlResp){
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())){
                                    List<DistributeOrderHeadData> headDataList = xmlResp.getParameterDatas(DistributeOrderHeadData.class);
                                    List<DistributeSumShowBean> showBeanList = xmlResp.getMasterDatas(DistributeSumShowBean.class);
                                    listener.onSuccess(headDataList,showBeanList);
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
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.COMMIT, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.COMMIT, string);
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

    /**
     * 获取扫描明细
     */
    public interface  GetDetailListener{
        public void onSuccess(List<DetailShowBean> detailShowBeen);
        public void onFailed(String error);
    }
    /**
     * 获取扫描明细
     */
    public void getDetail(final Map<String,String> map, final GetDetailListener listener){
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETDETAIL, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETDETAIL, string);
                        String error= mContext.getString(R.string.unknow_error);
                        if (null!=xmlResp){
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())){
                                List<DetailShowBean> showBeanList = xmlResp.getMasterDatas(DetailShowBean.class);
                                listener.onSuccess(showBeanList);
                                return;
                            }else {
                                error=xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        },null);
    }

    /**
     * 修改和删除
     */
    public interface UpdateAndDeleteListener{
        public void onSuccess(List<DetailShowBean> detailShowBeen);
        public void onFailed(String error);
    }

    /**
     * 修改和删除
     */
    public  void upDateAndDelete(final List<Map<String,String>> list,final UpdateAndDeleteListener listener){
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(list, mModule, ReqTypeName.UPDATEDELETE, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.UPDATEDELETE, string);
                        String error= mContext.getString(R.string.unknow_error);
                        if (null!=xmlResp){
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())){
                                List<DetailShowBean> showBeanList = xmlResp.getMasterDatas(DetailShowBean.class);
                                listener.onSuccess(showBeanList);
                                return;
                            }else {
                                error=xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        },null);
    }

    /**
     * 退出时调用
     */
    public interface  ExitListener{
        public void onSuccess(String msg);
        public void onFailed(String error);
    }
    /**
     * 退出时调用
     */
    public void exit(final  Map<String,String> map,final  ExitListener listener){
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.EXIT, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.EXIT, string);
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
            }
        },null);
    }

    /**
     * 获取未完事项
     */
    public interface GetUnComListener{
        public void onSuccess(List<UnCompleteBean> list);
        public void onFailed(String error);
    }

    public  void getUnCom(final Map<String,String> map,final GetUnComListener listener){
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.UNCOMGET, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.UNCOMGET, string);
                        String error= mContext.getString(R.string.unknow_error);
                        if (null!=xmlResp){
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())){
                                List<UnCompleteBean> unCompleteBeen = xmlResp.getMasterDatas(UnCompleteBean.class);
                                listener.onSuccess(unCompleteBeen);
                                return;
                            }else {
                                error=xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        },null);
    }

    /**
     * 删除未完事项
     */
    public interface DeleteUnComListener{
        public void onSuccess(List<UnCompleteBean> list);
        public void onFailed(String error);
    }
    /**
     * 删除未完事项
     */
    public  void deleteUnCom(final Map<String,String> map,final DeleteUnComListener listener){
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.UNCOMDELETE, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.UNCOMDELETE, string);
                        String error= mContext.getString(R.string.unknow_error);
                        if (null!=xmlResp){
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())){
                                List<UnCompleteBean> unCompleteBeen = xmlResp.getMasterDatas(UnCompleteBean.class);
                                listener.onSuccess(unCompleteBeen);
                                return;
                            }else {
                                error=xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        },null);
    }

}