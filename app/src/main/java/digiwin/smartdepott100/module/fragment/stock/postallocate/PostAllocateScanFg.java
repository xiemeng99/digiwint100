package digiwin.smartdepott100.module.fragment.stock.postallocate;

import android.os.Handler;
import android.os.Message;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.module.logic.stock.PostAllocateLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.stock.postallocate.PostAllocateScanActivity;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @des  调拨 拨出，拨入
 * @date 2017/6/7
 * @author xiemeng
 */

public class PostAllocateScanFg extends BaseFragment {

    /**
     * 条码
     */
    @BindView(R.id.tv_barcode)
    TextView tvBarcode;

    /**
     * 条码
     */
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;

    /**
     * 拨出库位
     */
    @BindView(R.id.tv_locator_out)
    TextView tvLocatorOut;
    /**
     * 拨出库位
     */
    @BindView(R.id.et_scan_locator_out)
    EditText etScanLocatorOut;
    /**
     * 锁定拨出库位
     */
    @BindView(R.id.cb_locatorlock_out)
    CheckBox cbLocatorlockOut;
    /**
     * 拨入库位
     */
    @BindView(R.id.tv_locator_in)
    TextView tvLocatorIn;
    /**
     * 拨入库位
     */
    @BindView(R.id.et_scan_locator_in)
    EditText etScanLocatorIn;
    /**
     * 锁定拨入库位
     */
    @BindView(R.id.cb_locatorlock_in)
    CheckBox cbLocatorlockIn;
    /**
     * 数量
     */
    @BindView(R.id.et_input_num)
    EditText etInputNum;


    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;
    @BindView(R.id.ll_scan_locator_in)
    LinearLayout llScanLocatorIn;
    @BindView(R.id.ll_scan_locator_out)
    LinearLayout llScanLocatorOut;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindViews({R.id.et_tray,R.id.et_scan_barocde, R.id.et_scan_locator_in,R.id.et_scan_locator_out, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_tray,R.id.ll_scan_barcode, R.id.ll_scan_locator_in, R.id.ll_scan_locator_out, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_tray,R.id.tv_barcode, R.id.tv_locator_in, R.id.tv_locator_out, R.id.tv_number})
    List<TextView> textViews;

    /**
     * 公共区域展示
     */
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;

    @BindView(R.id.includedetail)
    RelativeLayout includeDetail;

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

    /**
     * 已扫描量
     */
    @BindView(R.id.tv_scaned_num)
    TextView tvScanedNum;
    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 拨出库位展示
     */
    String outLocatorShow;
    /**
     * 拨入库位展示
     */
    String inLocatorShow;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 拨出库位扫描
     */
    boolean outLocatorFlag;
    /**
     * 拨入库位扫描
     */
    boolean inLocatorFlag;

    SaveBean saveBean;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHATIN = 1002;
    /**
     * 库位
     */
    final int LOCATORWHATOUT = 1003;

    PostAllocateLogic commonLogic;

    @OnCheckedChanged(R.id.cb_locatorlock_in)
    void isLock_in(boolean checked) {
        if (checked) {
            etScanLocatorIn.setKeyListener(null);
        } else {
            etScanLocatorIn.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }
    @OnCheckedChanged(R.id.cb_locatorlock_out)
    void isLock_out(boolean checked) {
        if (checked) {
            etScanLocatorOut.setKeyListener(null);
        } else {
            etScanLocatorOut.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_locator_in)
    void inLocatorFocusChanage() {
        ModuleUtils.viewChange(llScanLocatorIn, views);
        ModuleUtils.etChange(activity, etScanLocatorIn, editTexts);
        ModuleUtils.tvChange(activity, tvLocatorIn, textViews);
    }
    @OnFocusChange(R.id.et_scan_locator_out)
    void outLocatorFocusChanage() {
        ModuleUtils.viewChange(llScanLocatorOut, views);
        ModuleUtils.etChange(activity, etScanLocatorOut, editTexts);
        ModuleUtils.tvChange(activity, tvLocatorOut, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = R.id.et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator_in, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void inLocatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(LOCATORWHATIN);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHATIN, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_scan_locator_out, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void outLocatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(LOCATORWHATOUT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHATOUT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.save)
    void save() {
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (!outLocatorFlag) {
            showFailedDialog(R.string.scan_outlocator);
            return;
        }
        if (!inLocatorFlag) {
            showFailedDialog(R.string.scan_inlocator);
            return;
        }
        if (StringUtils.isBlank(etInputNum.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setDoc_no(orderBean.getDoc_no());
        saveBean.setQty(etInputNum.getText().toString());
        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
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

    PostAllocateScanActivity pactivity;

    FilterResultOrderBean orderBean;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
                    barcodeMap.put(AddressContants.DOC_NO,orderBean.getDoc_no());
                    etScanBarocde.setKeyListener(null);
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etScanBarocde.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeShow = barcodeBackBean.getShowing();
                            if(!StringUtils.isBlank(barcodeBackBean.getBarcode_qty())){
                                etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            }
                            if(!StringUtils.isBlank(barcodeBackBean.getScan_sumqty())){
                                tvScanedNum.setText(StringUtils.deleteZero(barcodeBackBean.getScan_sumqty()));
                            }
                            barcodeFlag = true;
                            show();
                            saveBean.setQty(barcodeBackBean.getBarcode_qty());
                            saveBean.setProduct_no(barcodeBackBean.getProduct_no());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setScan_sumqty(barcodeBackBean.getScan_sumqty());
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            etInputNum.requestFocus();
                            if (inLocatorFlag&&outLocatorFlag&&CommonUtils.isAutoSave(saveBean)){
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
                case LOCATORWHATIN:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    locatorMap.put("statu","in");
                    etScanLocatorIn.setKeyListener(null);
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanLocatorIn.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            inLocatorShow = locatorBackBean.getShowing();
                            inLocatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            etScanBarocde.requestFocus();
                            if (barcodeFlag&&outLocatorFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanLocatorIn.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanLocatorIn.setText("");
                                }
                            });
                            inLocatorFlag = false;
                        }
                    });
                    break;
                case LOCATORWHATOUT:
                    HashMap<String, String> locatorMap1 = new HashMap<>();
                    locatorMap1.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    locatorMap1.put("statu","out");
                    etScanLocatorOut.setKeyListener(null);
                    commonLogic.scanLocator(locatorMap1, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanLocatorOut.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            outLocatorShow = locatorBackBean.getShowing();
                            outLocatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            if(cbLocatorlockIn.isChecked()){
                                etScanBarocde.requestFocus();
                            }else{
                                etScanLocatorIn.requestFocus();
                            }
                            if (inLocatorFlag&&barcodeFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanLocatorOut.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanLocatorOut.setText("");
                                }
                            });
                            outLocatorFlag = false;
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
        return R.layout.fg_post_allocate_scan;
    }

    @Override
    protected void doBusiness() {
        orderBean= new FilterResultOrderBean();
        pactivity = (PostAllocateScanActivity) activity;
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
        if (!StringUtils.isBlank(inLocatorShow)&&!inLocatorShow.startsWith(context.getResources().getString(R.string.allotin))) {
            inLocatorShow = context.getResources().getString(R.string.allotin) + inLocatorShow;
        }
        if (!StringUtils.isBlank(outLocatorShow)&&!outLocatorShow.startsWith(context.getResources().getString(R.string.allotout))) {
            outLocatorShow = context.getResources().getString(R.string.allotout) + outLocatorShow;
        }
        tvDetailShow.setText(StringUtils.lineChange(inLocatorShow + "\\n" + outLocatorShow + "\\n" + barcodeShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())){
            includeDetail.setVisibility(View.VISIBLE);}else {
            includeDetail.setVisibility(View.GONE);
        }
    }

    /**
     * 保存完成之后的操作
     */
    private void clear() {
        tvScanedNum.setText(saveBean.getScan_sumqty());
        etScanBarocde.setText("");
        etInputNum.setText("");
        barcodeFlag = false;
        if (!cbLocatorlockOut.isChecked())
        {
            outLocatorShow = "";
            outLocatorFlag = false;
            etScanLocatorOut.setText("");
            etScanLocatorOut.requestFocus();
        }
        if(cbLocatorlockIn.isChecked()){
            etScanBarocde.requestFocus();
        }else{
            inLocatorShow = "";
            inLocatorFlag = false;
            etScanLocatorIn.setText("");
            etScanLocatorIn.requestFocus();
        }
        barcodeShow = "";
        show();
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        etScanBarocde.setText("");
        etScanLocatorIn.setText("");
        etScanLocatorOut.setText("");
        etInputNum.setText("");
        tvScanedNum.setText("");
        barcodeShow = "";
        inLocatorShow = "";
        outLocatorShow = "";
        show();
        barcodeFlag = false;
        outLocatorFlag = false;
        inLocatorFlag = false;
        cbLocatorlockIn.setChecked(false);
        cbLocatorlockOut.setChecked(false);
        saveBean = new SaveBean();
        commonLogic = PostAllocateLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        orderBean = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable(AddressContants.ORDERDATA);
        etScanLocatorOut.requestFocus();
        }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
