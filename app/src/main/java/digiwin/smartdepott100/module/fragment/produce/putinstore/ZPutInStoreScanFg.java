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
import digiwin.library.utils.WeakRefHandler;
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
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.ZPutInStoreLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;

/**
 * @author xiemeng
 * @des 入库上架
 * @date 2017/5/26 14:30
 */

public class ZPutInStoreScanFg extends BaseFragment {

    @BindView(R.id.tv_barcode_no)
    TextView tvBarcodeNo;
    @BindView(R.id.et_barcode_no)
    EditText etBarcodeNo;
    @BindView(R.id.ll_barcode_no)
    LinearLayout llBarcodeNo;
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

    @BindViews({R.id.et_tray,R.id.et_barcode_no, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_tray,R.id.ll_barcode_no,R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_tray,R.id.tv_barcode_no, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_locatorlock2)
    CheckBox cb_locatorlock2;

    FilterResultOrderBean orderData;

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

    @OnFocusChange(R.id.et_barcode_no)
    void barcdoeNoFocusChanage() {
        ModuleUtils.viewChange(llBarcodeNo, views);
        ModuleUtils.etChange(activity, etBarcodeNo, editTexts);
        ModuleUtils.tvChange(activity, tvBarcodeNo, textViews);
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

    @OnTextChanged(value = R.id.et_barcode_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeNoNoChange(CharSequence s) {
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
        if (!locatorFlag) {
            showFailedDialog(R.string.scan_locator);
            return;
        }
        if (!barcodeNoFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (StringUtils.isBlank(etInputNum.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setDoc_no(orderBean.getDoc_no());
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
     * 条码
     */
    final int BARCODEWHAT = 1001;
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
    boolean barcodeNoFlag;
    /**
     * 储位扫描
     */
    boolean locatorFlag;

    SaveBean saveBean;

    FilterResultOrderBean orderBean ;
    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    final HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.DOC_NO,orderBean.getDoc_no());
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
                    barcodeMap.put(AddressContants.STORAGE_SPACES_BARCODE,saveBean.getStorage_spaces_in_no());
                    etBarcodeNo.setKeyListener(null);
                    zPutInStoreLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etBarcodeNo.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeShow = barcodeBackBean.getShowing();
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getAvailable_in_qty()));
                            tvScanHasScan.setText(barcodeBackBean.getScan_sumqty());
                            barcodeNoFlag = true;
                            show();
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setProduct_no(barcodeBackBean.getProduct_no());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setDoc_no(orderData.getDoc_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                           // saveBean.setWo_no(barcodeBackBean.getWo_no());
                            saveBean.setScan_sumqty(barcodeBackBean.getScan_sumqty());
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            etInputNum.requestFocus();
                            if (locatorFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etBarcodeNo.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeNoFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etBarcodeNo.setText("");
                                }
                            });
                        }
                    });
                    break;
                case LOCATORWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    etScanLocator.setKeyListener(null);
                    zPutInStoreLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            locatorShow = locatorBackBean.getShowing();
                            locatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            etBarcodeNo.requestFocus();
                            if (barcodeNoFlag&&CommonUtils.isAutoSave(saveBean)){
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
        return R.layout.fg_zputinstore_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (ZPutInStoreSecondActivity) activity;
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
        barcodeNoFlag =false;
        etBarcodeNo.setText("");
        barcodeShow="";
        if (!cb_locatorlock2.isChecked()){
            locatorFlag=false;
            etScanLocator.setText("");
            locatorShow="";
            etScanLocator.requestFocus();
        }else{
            etBarcodeNo.requestFocus();
        }
        show();
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        orderBean= new FilterResultOrderBean();
        etBarcodeNo.setText("");
        etScanLocator.setText("");
        barcodeShow = "";
        locatorShow = "";
        show();
        cb_locatorlock2.setChecked(false);
        barcodeNoFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        orderBean = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable(AddressContants.ORDERDATA);
        zPutInStoreLogic = ZPutInStoreLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        orderData = (FilterResultOrderBean) getActivity().getIntent().getExtras().getSerializable("orderData");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
