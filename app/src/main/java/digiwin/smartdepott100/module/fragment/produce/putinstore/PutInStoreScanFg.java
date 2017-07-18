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
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.produce.putinstore.PutInStoreSecondActivity;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @author 唐孟宇
 * @des 入库上架 扫码页面
 */
public class PutInStoreScanFg extends BaseFragment {

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

    @BindViews({R.id.et_scan_barocde, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode,R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
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
        saveBean.setDoc_no(orderBean.getDoc_no());
        saveBean.setQty(etInputNum.getText().toString());
        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
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
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;

    PutInStoreSecondActivity pactivity;

    CommonLogic commonLogic;
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

    FilterResultOrderBean orderBean = new FilterResultOrderBean();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.DOC_NO,orderBean.getDoc_no());
                    barcodeMap.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            barcodeShow = barcodeBackBean.getShowing();
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            tvScanHasScan.setText(barcodeBackBean.getScan_sumqty());
                            barcodeFlag = true;
                            show();
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setScan_sumqty(barcodeBackBean.getScan_sumqty());
                            etInputNum.requestFocus();
                        }

                        @Override
                public void onFailed(String error) {
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
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            locatorShow = locatorBackBean.getShowing();
                            locatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            etScanBarocde.requestFocus();
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
        return R.layout.fg_putinstore_scan;
    }

    @Override
    protected void doBusiness() {
        putInStoreScanFg = this;
        pactivity = (PutInStoreSecondActivity) activity;
        initData();
    }
    private static PutInStoreScanFg putInStoreScanFg;
    public static PutInStoreScanFg getInstance(){
        if(putInStoreScanFg == null){
            putInStoreScanFg = new PutInStoreScanFg();
        }
        return putInStoreScanFg;
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
        if (!cb_locatorlock2.isChecked()){
            locatorFlag=false;
            etScanLocator.setText("");
            locatorShow="";
            etScanLocator.requestFocus();
        }else{
            etScanBarocde.requestFocus();
        }
        show();
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        etScanBarocde.setText("");
        etScanLocator.setText("");
        barcodeShow = "";
        locatorShow = "";
        show();
        cb_locatorlock2.setChecked(false);
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        orderBean = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable(AddressContants.ORDERDATA);
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
    }
}
