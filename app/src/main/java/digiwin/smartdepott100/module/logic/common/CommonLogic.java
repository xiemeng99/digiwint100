package digiwin.smartdepott100.module.logic.common;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ThreadPoolManager;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanEmployeeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.common.ScanReasonCodeBackBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.bean.common.UnCompleteBean;
import digiwin.smartdepott100.module.bean.produce.InBinningBean;
import digiwin.smartdepott100.module.bean.stock.ProductBinningBean;

/**
 * Created by xiemeng on 2017/2/23.
 */

public class CommonLogic {

    private static final String TAG = "CommonLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static CommonLogic logic;

    private CommonLogic(Context context, String module, String timestamp) {
        mTimestamp = timestamp;
        mContext = context;
        mModule = module;

    }

    public static CommonLogic getInstance(Context context, String module, String timestamp) {

        return logic = new CommonLogic(context, module, timestamp);
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
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ScanLocatorBackBean> locatorBackBeen = xmlResp.getParameterDatas(ScanLocatorBackBean.class);
                                    if (locatorBackBeen.size() > 0) {
                                        if (!ModuleCode.NOCOMESTOREALLOT.equals(mModule)&&
                                       ! ModuleCode.TRANSFERS_TO_REVIEW.equals(mModule)&&
                                       ! ModuleCode.POSTALLOCATE.equals(mModule)
                                                && !locatorBackBeen.get(0).getWarehouse_no().equals(LoginLogic.getWare())) {
                                            error = mContext.getString(R.string.ware_error);
                                        } else {
                                            listener.onSuccess(locatorBackBeen.get(0));
                                            return;
                                        }
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
        public void onSuccess(SaveBackBean saveBackBean);

        public void onFailed(String error);
    }

    /**
     * 保存
     */
    public void scanSave(final SaveBean saveBean, final SaveListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = ObjectAndMapUtils.getValueMap(saveBean);
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.SCANINFOSAVE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.SCANINFOSAVE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<SaveBackBean> savebackBeanList = xmlResp.getMasterDatas(SaveBackBean.class);
                                    SaveBackBean saveBackBean = savebackBeanList.get(0);
                                    saveBackBean.setScan_sumqty(StringUtils.deleteZero(saveBackBean.getScan_sumqty()));
                                    listener.onSuccess(saveBackBean);
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
    public void getSum(final Map<String, String> map, final GetSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.SUMUPDATE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.SUMUPDATE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<SumShowBean> showBeanList = xmlResp.getMasterDatas(SumShowBean.class);
                                    listener.onSuccess(showBeanList);
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
     *
     * @param map map可以直接为空
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
                    LogUtils.e(TAG, "getSum--->" + e);
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
     * 提交
     * recordset 提交请求
     */
    public void commitList(final List<Map<String, String>> listMap, final CommitListListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(listMap, mModule, ReqTypeName.COMMIT, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.COMMIT, string);
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

    /**
     * 获取扫描明细
     */
    public interface GetDetailListener {
        public void onSuccess(List<DetailShowBean> detailShowBeen);

        public void onFailed(String error);
    }

    /**
     * 获取扫描明细
     */
    public void getDetail(final Map<String, String> map, final GetDetailListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETDETAIL, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETDETAIL, string);
                        String error = mContext.getString(R.string.unknow_error);
                        if (null != xmlResp) {
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                List<DetailShowBean> showBeanList = xmlResp.getMasterDatas(DetailShowBean.class);
                                listener.onSuccess(showBeanList);
                                return;
                            } else {
                                error = xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        }, null);
    }

    /**
     * 修改和删除
     */
    public interface UpdateAndDeleteListener {
        public void onSuccess(List<DetailShowBean> detailShowBeen);

        public void onFailed(String error);
    }

    /**
     * 修改和删除
     */
    public void upDateAndDelete(final List<Map<String, String>> list, final UpdateAndDeleteListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(list, mModule, ReqTypeName.UPDATEDELETE, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.UPDATEDELETE, string);
                        String error = mContext.getString(R.string.unknow_error);
                        if (null != xmlResp) {
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                List<DetailShowBean> showBeanList = xmlResp.getMasterDatas(DetailShowBean.class);
                                listener.onSuccess(showBeanList);
                                return;
                            } else {
                                error = xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        }, null);
    }

    /**
     * 退出时调用
     */
    public interface ExitListener {
        public void onSuccess(String msg);

        public void onFailed(String error);
    }

    /**
     * 退出时调用
     */
    public void exit(final Map<String, String> map, final ExitListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.EXIT, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.EXIT, string);
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
            }
        }, null);
    }

    /**
     * 获取未完事项
     */
    public interface GetUnComListener {
        public void onSuccess(List<UnCompleteBean> list);

        public void onFailed(String error);
    }

    /**
     * 获取未完事项
     */
    public void getUnCom(final Map<String, String> map, final GetUnComListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.UNCOMGET, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.UNCOMGET, string);
                        String error = mContext.getString(R.string.unknow_error);
                        if (null != xmlResp) {
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                List<UnCompleteBean> unCompleteBeen = xmlResp.getMasterDatas(UnCompleteBean.class);
                                listener.onSuccess(unCompleteBeen);
                                return;
                            } else {
                                error = xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        }, null);
    }

    /**
     * 删除未完事项
     */
    public interface DeleteUnComListener {
        public void onSuccess(List<UnCompleteBean> list);

        public void onFailed(String error);
    }

    /**
     * 删除未完事项
     */
    public void deleteUnCom(final Map<String, String> map, final DeleteUnComListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.UNCOMDELETE, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.UNCOMDELETE, string);
                        String error = mContext.getString(R.string.unknow_error);
                        if (null != xmlResp) {
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                List<UnCompleteBean> unCompleteBeen = xmlResp.getMasterDatas(UnCompleteBean.class);
                                listener.onSuccess(unCompleteBeen);
                                return;
                            } else {
                                error = xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        }, null);
    }

    /**
     * 获取FIFO
     */
    public interface FIFOGETListener {
        public void onSuccess(List<FifoCheckBean> fiFoBeanList);

        public void onFailed(String error);
    }

    /**
     * 获取FIFO
     */
    public void getFifo(final Map<String, String> map, final FIFOGETListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETFIFO, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETFIFO, string);
                        String error = mContext.getString(R.string.unknow_error);
                        if (null != xmlResp) {
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                List<FifoCheckBean> fiFoBeanList = xmlResp.getMasterDatas(FifoCheckBean.class);
                                if(null != fiFoBeanList && fiFoBeanList.size() > 0){
                                    for (int i = 0; i < fiFoBeanList.size(); i++) {
                                        FifoCheckBean fifoBean = fiFoBeanList.get(i);
                                        fifoBean.setRecommended_qty(StringUtils.deleteZero(fifoBean.getRecommended_qty()));
                                        fifoBean.setScan_sumqty(StringUtils.deleteZero(fifoBean.getScan_sumqty()));
                                    }
                                    listener.onSuccess(fiFoBeanList);
                                }else{
                                    List<FifoCheckBean> list = new ArrayList<FifoCheckBean>();
                                    listener.onSuccess(list);
                                }
                                return;
                            } else {
                                error = xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        }, null);
    }

    /**
     * 筛选  获取待办事项
     */
    public interface GetOrderListener {
        public void onSuccess(List<FilterResultOrderBean> list);

        public void onFailed(String error);
    }

    /**
     * 筛选  获取待办事项
     */
    public void getOrderData(final FilterBean filterBean, final GetOrderListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = ObjectAndMapUtils.getValueMap(filterBean);
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.POSTMATERIALGETORDERLIST, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.POSTMATERIALGETORDERLIST, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<FilterResultOrderBean> showBeanList = xmlResp.getMasterDatas(FilterResultOrderBean.class);
                                    listener.onSuccess(showBeanList);
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
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        }, null);
    }
    /**
     * 筛选  获取待办事项
     */
    public void getOrderData(final Map<String, String> map, final GetOrderListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.POSTMATERIALGETORDERLIST, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.POSTMATERIALGETORDERLIST, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<FilterResultOrderBean> showBeanList = xmlResp.getMasterDatas(FilterResultOrderBean.class);
                                    listener.onSuccess(showBeanList);
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
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        }, null);
    }

    /**
     * 从待办事项进入汇总页面
     */
    public interface GetOrderSumListener {
        public void onSuccess(List<ListSumBean> list);

        public void onFailed(String error);
    }

    /**
     * 从待办事项进入汇总页面
     */
    public void getOrderSumData(final ClickItemPutBean clickItemPutBean, final GetOrderSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = ObjectAndMapUtils.getValueMap(clickItemPutBean);
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.POSTMATERIALSUMDATA, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.POSTMATERIALSUMDATA, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ListSumBean> showBeanList = xmlResp.getMasterDatas(ListSumBean.class);
                                    listener.onSuccess(showBeanList);
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
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        }, null);
    }
    /**
     * 从待办事项进入汇总页面
     */
    public void getOrderSumData(final InBinningBean clickItemPutBean, final GetOrderSumListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = ObjectAndMapUtils.getValueMap(clickItemPutBean);
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.POSTMATERIALSUMDATA, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.POSTMATERIALSUMDATA, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ListSumBean> showBeanList = xmlResp.getMasterDatas(ListSumBean.class);
                                    listener.onSuccess(showBeanList);
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
                    LogUtils.e(TAG, "getSum--->" + e);
                }
            }
        }, null);
    }

    /**
     * 领料过账 获取FIFO
     */
    public interface PostMaterialFIFOListener {
        public void onSuccess(List<FifoCheckBean> fiFoBeanList);

        public void onFailed(String error);
    }

    /**
     * 领料过账 获取FIFO
     */
    public void postMaterialFIFO(final Map<String, String> map, final PostMaterialFIFOListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.POSTMATERIALFIFO, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.POSTMATERIALFIFO, string);
                        String error = mContext.getString(R.string.unknow_error);
                        if (null != xmlResp) {
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                List<FifoCheckBean> fiFoBeanList = xmlResp.getMasterDatas(FifoCheckBean.class);
                                if(null != fiFoBeanList && fiFoBeanList.size() > 0){
                                    for (int i = 0; i < fiFoBeanList.size(); i++) {
                                        FifoCheckBean fifoBean = fiFoBeanList.get(i);
                                        fifoBean.setRecommended_qty(StringUtils.deleteZero(fifoBean.getRecommended_qty()));
                                        fifoBean.setScan_sumqty(StringUtils.deleteZero(fifoBean.getScan_sumqty()));
                                    }
                                    listener.onSuccess(fiFoBeanList);
                                }else{
                                    List<FifoCheckBean> list = new ArrayList<FifoCheckBean>();
                                    listener.onSuccess(list);
                                }

                                return;
                            } else {
                                error = xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        }, null);
    }

    /**
     * 获取FIFO
     */
    public interface FIFOAccordingGETListener {
        public void onSuccess(List<FifoCheckBean> fiFoBeanList);

        public void onFailed(String error);
    }

    /**
     * 获取FIFO
     */
    public void getFifoAccording(final Map<String, String> map, final FIFOAccordingGETListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETACCORDINGFIFO, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETACCORDINGFIFO, string);
                        String error = mContext.getString(R.string.unknow_error);
                        if (null != xmlResp) {
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                List<FifoCheckBean> fiFoBeanList = xmlResp.getMasterDatas(FifoCheckBean.class);
                                if(null != fiFoBeanList && fiFoBeanList.size() >0){
                                    for (int i = 0; i < fiFoBeanList.size(); i++) {
                                        FifoCheckBean fifoBean = fiFoBeanList.get(i);
                                        fifoBean.setRecommended_qty(StringUtils.deleteZero(fifoBean.getRecommended_qty()));
                                        fifoBean.setScan_sumqty(StringUtils.deleteZero(fifoBean.getScan_sumqty()));
                                    }
                                    listener.onSuccess(fiFoBeanList);
                                }else {
                                    List<FifoCheckBean> list = new ArrayList<FifoCheckBean>();
                                    listener.onSuccess(fiFoBeanList);
                                }
                                return;
                            } else {
                                error = xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        }, null);
    }

    /**
     * 扫描理由码
     */
    public interface ScanReasonCodeListener {
        public void onSuccess(ScanReasonCodeBackBean reasonCodeBackBean);

        public void onFailed(String error);
    }

    /**
     * 扫描理由码
     */
    public void scanReasonCode(final Map<String, String> map, final ScanReasonCodeListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.SCANREASONCODE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.SCANREASONCODE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ScanReasonCodeBackBean> locatorBackBeen = xmlResp.getParameterDatas(ScanReasonCodeBackBean.class);
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
     * 扫描员工编号
     */
    public interface ScanEmployeementListener {
        public void onSuccess(ScanEmployeeBackBean scanDepartmentBackBeann);

        public void onFailed(String error);
    }

    /**
     * 扫描员工编号
     */
    public void scanEmployeeCode(final Map<String, String> map, final ScanEmployeementListener listener) {
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
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ScanEmployeeBackBean> locatorBackBeen = xmlResp.getParameterDatas(ScanEmployeeBackBean.class);
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
     * 扫描包装箱号
     */
    public interface ScanPackBoxNumberListener {
        public void onSuccess(List<ProductBinningBean> productBinningBeans);

        public void onFailed(String error);
    }

    /**
     * 扫描包装箱号
     */
    public void scanPackBoxNumber(final Map<String, String> map, final ScanPackBoxNumberListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.GETPACKBOXNUMBER, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.GETPACKBOXNUMBER, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                    List<ProductBinningBean> beens = xmlResp.getMasterDatas(ProductBinningBean.class);
                                    if (beens.size() > 0) {
                                        listener.onSuccess(beens);
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
     * 产品装箱修改和删除（出箱）
     */
    public interface InsertAndDeleteListener {
        public void onSuccess(String show);

        public void onFailed(String error);
    }

    /**
     * 产品装箱修改和删除（出箱）
     */
    public void insertAndDelete(final List<Map<String, String>> list, final InsertAndDeleteListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                String xml = CreateParaXmlReqIm.getInstance(list, mModule, ReqTypeName.INSERTDELETE, mTimestamp).toXml();
                OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                    @Override
                    public void onResponse(String string) {
                        ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.INSERTDELETE, string);
                        String error = mContext.getString(R.string.unknow_error);
                        if (null != xmlResp) {
                            if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                                String show = xmlResp.getFieldString();
                                listener.onSuccess(show);
                                return;
                            } else {
                                error = xmlResp.getDescription();
                            }
                        }
                        listener.onFailed(error);
                    }
                });
            }
        }, null);
    }

    /**
     * 装箱保存
     */
    public interface SaveBinningListener {
        public void onSuccess();

        public void onFailed(String error);
    }

    /**
     * 装箱保存
     */
    public void scanBinningSave(final SaveBean saveBean, final SaveBinningListener listener) {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> map = ObjectAndMapUtils.getValueMap(saveBean);
                    final String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.BINNINGSAVE, mTimestamp).toXml();
                    OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                        @Override
                        public void onResponse(String string) {
                            ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.BINNINGSAVE, string);
                            String error = mContext.getString(R.string.unknow_error);
                            if (null != xmlResp) {
                                if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
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
                    LogUtils.e(TAG, "scanBinningSave--->" + e);
                }
            }
        }, null);
    }
}