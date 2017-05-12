package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

import java.util.ArrayList;
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
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.bean.common.UnCompleteBean;
import digiwin.smartdepott100.module.bean.produce.AccordingMaterialSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @module 依成品发料管理类
 * @date 2017/3/2
 */

public class AccordingMaterialLogic {

    private static final String TAG = "AccordingMaterialLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static AccordingMaterialLogic logic;

    private AccordingMaterialLogic(Context context, String module, String timestamp) {
        mContext = context;
        mModule = module;
        mTimestamp = timestamp;

    }


    public static AccordingMaterialLogic getInstance(Context context, String module, String timestamp) {

        return logic = new AccordingMaterialLogic(context, module, timestamp);
    }

    /**
     * 扫描料号监听
     */
    public interface ScanCodeListener {
        public void onSuccess(List<AccordingMaterialSumBean> accordingMaterialSumBeen);

        public void onFailed(String error);
    }

    /**
     * 扫描料号
     */
    public void scanCode(final Map<String, String> map, final ScanCodeListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.ITEM_NO, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.ITEM_NO, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<AccordingMaterialSumBean> accordingMaterialSumBeen = xmlResp.getMasterDatas(AccordingMaterialSumBean.class);
                                    if (accordingMaterialSumBeen.size() > 0) {
                                        List<AccordingMaterialSumBean> dataList = new ArrayList<AccordingMaterialSumBean>();
                                        for (int i = 0; i < accordingMaterialSumBeen.size(); i++) {
                                            AccordingMaterialSumBean data = accordingMaterialSumBeen.get(i);
                                            data.setShortage_qty(StringUtils.deleteZero(data.getShortage_qty()));
                                            data.setStock_qty(StringUtils.deleteZero(data.getStock_qty()));
                                            data.setScan_sumqty(StringUtils.deleteZero(data.getScan_sumqty()));
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
     * 扫描库位
     */
    public interface ScanLocatorListener {
        public void onSuccess(ScanLocatorBackBean locatorBackBean);

        public void onFailed(String error);
    }

    /**
     * 扫描库位
     */
    public void scanLocator(final Map<String, String> map, final CommonLogic.ScanLocatorListener listener) {
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
        public void onSuccess(List<SumShowBean> list);

        public void onFailed(String error);
    }

    /**
     * 汇总展示
     */
    public void getSum(final Map<String, String> map, final CommonLogic.GetSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.SUMUPDATE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.SUMUPDATE, string);
                            String error= mContext.getString(R.string.unknow_error);
                            if (null!=xmlResp){
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())){
                                    List<SumShowBean> showBeanList = xmlResp.getMasterDatas(SumShowBean.class);
                                    listener.onSuccess(showBeanList);
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
    public void commit(final Map<String, String> map, final CommonLogic.CommitListener listener) {
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
    public void getDetail(final Map<String,String> map, final CommonLogic.GetDetailListener listener){
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
    public  void upDateAndDelete(final List<Map<String,String>> list,final CommonLogic.UpdateAndDeleteListener listener){
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
    public void exit(final  Map<String,String> map,final CommonLogic.ExitListener listener){
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

    public  void getUnCom(final Map<String,String> map,final CommonLogic.GetUnComListener listener){
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
    public  void deleteUnCom(final Map<String,String> map,final CommonLogic.DeleteUnComListener listener){
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
