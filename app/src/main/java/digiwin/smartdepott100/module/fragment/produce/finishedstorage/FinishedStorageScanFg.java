package digiwin.smartdepott100.module.fragment.produce.finishedstorage;

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

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.activity.produce.finishstorage.FinishedStorageActivity;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.FinishedStorageLogic;


/**
 * @author xiemeng
 * @des 生产完工入库扫码页面
 * @date 2017/2/23
 */
public class FinishedStorageScanFg extends BaseFragment {

    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;
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

    @BindView(R.id.tv_scan_hasScan)
    TextView tvScanHasScan;

    @BindViews({R.id.et_tray,R.id.et_scan_barocde, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_tray,R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_tray,R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;


    @BindView(R.id.tv_tray)
    TextView tvTray;
    @BindView(R.id.et_tray)
    EditText etTray;
    @BindView(R.id.ll_tray)
    LinearLayout llTray;
    @BindView(R.id.line_tray)
    View lineTray;
    @OnFocusChange(R.id.et_tray)
    void trayFocusChanage() {
        ModuleUtils.viewChange(llTray, views);
        ModuleUtils.etChange(activity, etTray, editTexts);
        ModuleUtils.tvChange(activity, tvTray, textViews);
    }

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
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

    @OnTextChanged(value = R.id.et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
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
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
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
        showLoadingDialog();
        saveBean.setQty(etInputNum.getText().toString());
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                tvScanHasScan.setText(saveBackBean.getScan_sumqty());
                dismissLoadingDialog();
                clear();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;

    FinishedStorageActivity pactivity;


    FinishedStorageLogic commonLogic;
    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 库位展示
     */
    String locatorShow;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    SaveBean saveBean;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_in_no());
                    etScanBarocde.setKeyListener(null);
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etScanBarocde.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeShow = barcodeBackBean.getShowing();
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            tvScanHasScan.setText(barcodeBackBean.getScan_sumqty());
                            barcodeFlag = true;
                            show();
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setProduct_no(barcodeBackBean.getProduct_no());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            etInputNum.requestFocus();
                            if (locatorFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanBarocde.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanBarocde.setText("");
                                }
                            });
                        }
                    });
                    break;
                case LOCATORWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    etScanLocator.setKeyListener(null);
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            locatorShow = locatorBackBean.getShowing();
                            locatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            etScanBarocde.requestFocus();
                            if (barcodeFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
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
    };

    private Handler mHandler = new WeakRefHandler(mCallback);


    @Override
    protected int bindLayoutId() {
        return R.layout.fg_finishedstorage_scan;
    }

    @Override
    protected void doBusiness()      {
        pactivity = (FinishedStorageActivity) activity;
        if (CommonUtils.isUseTray()){
            llTray.setVisibility(View.VISIBLE);
            lineTray.setVisibility(View.VISIBLE);
        }else {
            llTray.setVisibility(View.GONE);
            lineTray.setVisibility(View.GONE);
        }
        initData();
    }


    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(barcodeShow + "\\n" + locatorShow));
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
        barcodeFlag=false;
        etScanBarocde.setText("");
        barcodeShow="";
        etScanBarocde.requestFocus();
        if (!cbLocatorlock.isChecked()){
            locatorFlag=false;
            etScanLocator.setText("");
            locatorShow="";
            etScanLocator.requestFocus();
        }
        show();
    }

    /**
     * 初始化一些变量
     */
    public  void initData() {
        barcodeShow = "";
        locatorShow = "";
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        cbLocatorlock.setChecked(false);
        etScanLocator.setText("");
        etScanLocator.requestFocus();
        tvScanHasScan.setText("");
        commonLogic = FinishedStorageLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
