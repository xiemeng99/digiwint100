package digiwin.smartdepott100.module.fragment.produce.putinstore;

import android.os.Handler;
import android.os.Message;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.produce.putinstore.ZPutInStoreSecondActivity;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.common.ScanPlotNoBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.ZPutInStoreLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;

/**
 * @author xiemeng
 * @des
 * @date 2017/5/26 14:30
 */

public class ZPutInStoreScanFg extends BaseFragment {

    @BindView(R.id.tv_plot_no)
    TextView tvPlotNo;
    @BindView(R.id.et_plot_no)
    EditText etPlotNo;
    @BindView(R.id.ll_plot_no)
    LinearLayout llPlotNo;
    @BindView(R.id.tv_locator)
    TextView tvLocator;
    @BindView(R.id.et_scan_locator)
    EditText etScanLocator;
    @BindView(R.id.ll_scan_locator)
    LinearLayout llScanLocator;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.includedetail)
    View includeDetail;

    @BindViews({R.id.et_plot_no, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_plot_no,R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_plot_no, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_locatorlock2)
    CheckBox cb_locatorlock2;

    /**
     * 已扫描量
     */
    @BindView(R.id.tv_scan_hasScan)
    TextView tvScanHasScan;

    @OnCheckedChanged(R.id.cb_locatorlock2)
    void isLock2(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_plot_no)

    void plotNoFocusChanage() {
        ModuleUtils.viewChange(llPlotNo, views);
        ModuleUtils.etChange(activity, etPlotNo, editTexts);
        ModuleUtils.tvChange(activity, tvPlotNo, textViews);
    }
    @OnFocusChange(R.id.et_scan_locator)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(llScanLocator, views);
        ModuleUtils.etChange(activity, etScanLocator, editTexts);
        ModuleUtils.tvChange(activity, tvLocator, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = R.id.et_plot_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void plotNoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PLOTWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PLOTWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(LOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.save)
    void save() {
        if (!plotFlag) {
            showFailedDialog(R.string.scan_circulation);
            return;
        }
        if (!locatorFlag) {
            showFailedDialog(R.string.scan_locator);
            return;
        }
        if (StringUtils.isBlank(etInputNum.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setDoc_no(orderBean.getStock_in_no());
        saveBean.setQty(etInputNum.getText().toString());
        showLoadingDialog();
        zPutInStoreLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                clear();
                tvScanHasScan.setText(saveBackBean.getScan_sumqty());
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    /**
     * 物料批号
     */
    final int PLOTWHAT = 1001;
    /**
     * 储位
     */
    final int LOCATORWHAT = 1002;

    ZPutInStoreSecondActivity pactivity;

    ZPutInStoreLogic zPutInStoreLogic;
    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 储位展示
     */
    String locatorShow;
    /**
     * 条码扫描
     */
    boolean plotFlag;
    /**
     * 储位扫描
     */
    boolean locatorFlag;

    SaveBean saveBean;

    FilterResultOrderBean orderBean = new FilterResultOrderBean();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PLOTWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.PLOTNO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.DOC_NO,orderBean.getStock_in_no());
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
                    zPutInStoreLogic.scanPlotNo(barcodeMap, new CommonLogic.ScanPoltNoListener() {
                        @Override
                        public void onSuccess(ScanPlotNoBackBean plotNoBackBean) {
                            barcodeShow = plotNoBackBean.getShowing();
                            etInputNum.setText(StringUtils.deleteZero(plotNoBackBean.getAvailable_in_qty()));
                            tvScanHasScan.setText(plotNoBackBean.getScan_sumqty());
                            plotFlag = true;
                            show();
                            saveBean.setAvailable_in_qty(plotNoBackBean.getAvailable_in_qty());
                            saveBean.setBarcode_no(etPlotNo.getText().toString());
                            saveBean.setItem_no(plotNoBackBean.getItem_no());
                            saveBean.setUnit_no(plotNoBackBean.getUnit_no());
                            saveBean.setWo_no(plotNoBackBean.getWo_no());
                            saveBean.setScan_sumqty(plotNoBackBean.getScan_sumqty());
                            etInputNum.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            plotFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etPlotNo.setText("");
                                }
                            });
                        }
                    });
                    break;
                case LOCATORWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    zPutInStoreLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            locatorShow = locatorBackBean.getShowing();
                            locatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            etPlotNo.requestFocus();
                            if (CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanLocator.setText("");
                                }
                            });
                            locatorFlag = false;
                        }
                    });
                    break;

            }
            return false;
        }
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_zputinstore_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (ZPutInStoreSecondActivity) activity;
        initData();
    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(locatorShow + "\\n" + barcodeShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())){
            includeDetail.setVisibility(View.VISIBLE);}else {
            includeDetail.setVisibility(View.GONE);
        }
    }

    /**
     * 保存完成之后的操作
     */
    private void clear() {
        etInputNum.setText("");
        plotFlag =false;
        etPlotNo.setText("");
        barcodeShow="";
        if (!cb_locatorlock2.isChecked()){
            locatorFlag=false;
            etScanLocator.setText("");
            locatorShow="";
            etScanLocator.requestFocus();
        }else{
            etPlotNo.requestFocus();
        }
        show();
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        etPlotNo.setText("");
        etScanLocator.setText("");
        barcodeShow = "";
        locatorShow = "";
        show();
        cb_locatorlock2.setChecked(false);
        plotFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        orderBean = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable(AddressContants.ORDERDATA);
        zPutInStoreLogic = ZPutInStoreLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        delete();
    }

    /**
     * 进入界面先清空后台存的表
     */
    private void delete() {
        Map<String,String> map = new HashMap<>();
        map.put(AddressContants.FLAG, BaseFirstModuldeActivity.ExitMode.EXITD.getName());
        zPutInStoreLogic.exit(map, new CommonLogic.ExitListener() {
            @Override
            public void onSuccess(String msg) {

            }

            @Override
            public void onFailed(String error) {

            }
        });
    }
}
