package digiwin.smartdepott100.module.fragment.stock.storeallot;

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
import digiwin.smartdepott100.module.activity.stock.storeallot.StoreAllotActivity;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.stock.StoreAllotLogic;

/**
 * @author xiemeng
 * @des 无来源调拨扫码页面
 * @date 2017/2/23
 */
public class StoreAllotScanFg extends BaseFragment {

    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;

    @BindView(R.id.tv_inlocator)
    TextView tvInLocator;
    @BindView(R.id.et_scan_inlocator)
    public EditText etScanInLocator;
    @BindView(R.id.ll_scan_inlocator)
    LinearLayout llScanInLocator;

    @BindView(R.id.tv_outlocator)
    TextView tvOutLocator;
    @BindView(R.id.et_scan_outlocator)
    public EditText etScanOutLocator;
    @BindView(R.id.ll_scan_outlocator)
    LinearLayout llScanOutLocator;

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

    /**
     * 已扫描量
     */
    @BindView(R.id.tv_scaned_num)
    TextView tv_scaned_num;

    @BindViews({R.id.et_tray,R.id.et_scan_barocde, R.id.et_scan_inlocator, R.id.et_scan_outlocator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_tray,R.id.ll_scan_barcode, R.id.ll_scan_inlocator, R.id.ll_scan_outlocator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_tray,R.id.tv_barcode, R.id.tv_inlocator, R.id.tv_outlocator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_inlocatorlock)
    CheckBox cbInLocatorlock;

    @OnCheckedChanged(R.id.cb_inlocatorlock)
    void inLocatorIsLock(boolean checked) {
        if (checked) {
            etScanInLocator.setKeyListener(null);
        } else {
            etScanInLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @BindView(R.id.cb_outlocatorlock)
    CheckBox cbOutLocatorlock;

    @OnCheckedChanged(R.id.cb_outlocatorlock)
    void outLocatorIsLock(boolean checked) {
        if (checked) {
            etScanOutLocator.setKeyListener(null);
        } else {
            etScanOutLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }
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
    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_inlocator)
    void inlocatorFocusChanage() {
        ModuleUtils.viewChange(llScanInLocator, views);
        ModuleUtils.etChange(activity, etScanInLocator, editTexts);
        ModuleUtils.tvChange(activity, tvInLocator, textViews);
    }

    @OnFocusChange(R.id.et_scan_outlocator)
    void outlocatorFocusChanage() {
        ModuleUtils.viewChange(llScanOutLocator, views);
        ModuleUtils.etChange(activity, etScanOutLocator, editTexts);
        ModuleUtils.tvChange(activity, tvOutLocator, textViews);
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

    @OnTextChanged(value = R.id.et_scan_inlocator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inlocatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(INLOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(INLOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_outlocator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void outlocatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(OUTLOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(OUTLOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.save)
    void save() {
        if (!inlocatorFlag) {
            showFailedDialog(R.string.scan_inlocator);
            return;
        }
        if (!outlocatorFlag) {
            showFailedDialog(R.string.scan_outlocator);
            return;
        }
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (StringUtils.isBlank(etInputNum.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        showLoadingDialog();
        saveBean.setQty(etInputNum.getText().toString());
        storeAllotLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                saveBean.setScan_sumqty(saveBackBean.getScan_sumqty());
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
     * 拨入库位
     */
    final int INLOCATORWHAT = 1002;
    /**
     * 拨出库位
     */
    final int OUTLOCATORWHAT = 1003;

    StoreAllotActivity pactivity;

    StoreAllotLogic storeAllotLogic;
    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 拨入库位展示
     */
    String inlocatorShow;
    /**
     * 拨出库位展示
     */
    String outlocatorShow;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 拨入库位扫描
     */
    boolean inlocatorFlag;
    /**
     * 拨出库位扫描
     */
    boolean outlocatorFlag;

    SaveBean saveBean;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case INLOCATORWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    locatorMap.put("statu","in");
                    etScanInLocator.setKeyListener(null);
                    storeAllotLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanInLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            inlocatorShow = locatorBackBean.getShowing();
                            inlocatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            if (pactivity.INOROUT.equals(pactivity.IN)) {
//                                cbInLocatorlock.setChecked(true);
                                pactivity.INOROUT = "";
                            }
                            if (cbOutLocatorlock.isChecked()) {
                                etScanBarocde.requestFocus();
                            } else {
                                etScanOutLocator.requestFocus();
                            }
                            if (barcodeFlag&&outlocatorFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanInLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanInLocator.setText("");
                                }
                            });
                            inlocatorFlag = false;
                        }
                    });
                    break;
                case OUTLOCATORWHAT:
                    HashMap<String, String> outlocatorMap = new HashMap<>();
                    outlocatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    outlocatorMap.put("statu","out");
                    etScanOutLocator.setKeyListener(null);
                    storeAllotLogic.scanLocator(outlocatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanOutLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            outlocatorShow = locatorBackBean.getShowing();
                            outlocatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            if (pactivity.INOROUT.equals(pactivity.OUT)) {
//                                cbOutLocatorlock.setChecked(true);
                                pactivity.INOROUT = "";
                            }
                            etScanBarocde.requestFocus();
                            if (inlocatorFlag&&barcodeFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanOutLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanOutLocator.setText("");
                                }
                            });
                            outlocatorFlag = false;
                        }
                    });
                    break;
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    etScanBarocde.setKeyListener(null);
                    storeAllotLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etScanBarocde.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeShow = barcodeBackBean.getShowing();
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            tv_scaned_num.setText(barcodeBackBean.getScan_sumqty());
                            barcodeFlag = true;
                            show();
                            saveBean.setAvailable_in_qty("0");
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setProduct_no(barcodeBackBean.getProduct_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            etInputNum.requestFocus();
                            if (inlocatorFlag&&outlocatorFlag&&CommonUtils.isAutoSave(saveBean)){
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
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_storeallot_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (StoreAllotActivity) activity;
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
        pactivity.ImgChooseallot.setEnabled(false);
        if (!StringUtils.isBlank(inlocatorShow) && !inlocatorShow.startsWith(context.getResources().getString(R.string.allotin))) {
            inlocatorShow = context.getResources().getString(R.string.allotin) + inlocatorShow;
        }
        if (!StringUtils.isBlank(outlocatorShow) && !outlocatorShow.startsWith(context.getResources().getString(R.string.allotout))) {
            outlocatorShow = context.getResources().getString(R.string.allotout) + outlocatorShow;
        }
        tvDetailShow.setText(StringUtils.lineChange(inlocatorShow + "\\n" + outlocatorShow + "\\n" + barcodeShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            includeDetail.setVisibility(View.VISIBLE);
        } else {
            includeDetail.setVisibility(View.GONE);
        }
    }


    /**
     * 保存完成之后的操作
     */
    private void clear() {
        tv_scaned_num.setText(saveBean.getScan_sumqty());
        etInputNum.setText("");
        barcodeFlag = false;
        barcodeShow = "";
        etScanBarocde.setText("");
        etScanBarocde.requestFocus();
        if (!cbOutLocatorlock.isChecked()) {
            outlocatorFlag = false;
            outlocatorShow = "";
            etScanOutLocator.setText("");
            etScanOutLocator.requestFocus();
        }
        if (!cbInLocatorlock.isChecked()) {
            inlocatorFlag = false;
            inlocatorShow = "";
            etScanInLocator.setText("");
            etScanInLocator.requestFocus();
        }
        show();
    }


    /**
     * 初始化一些变量
     */
    public void initData() {
        tv_scaned_num.setText("");
        barcodeShow = "";
        inlocatorShow = "";
        outlocatorShow = "";
        barcodeFlag = false;
        inlocatorFlag = false;
        outlocatorFlag = false;
        saveBean = new SaveBean();
        cbOutLocatorlock.setChecked(false);
        cbInLocatorlock.setChecked(false);
        etScanInLocator.setText("");
        etScanOutLocator.setText("");
        includeDetail.setVisibility(View.GONE);
        storeAllotLogic = StoreAllotLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}