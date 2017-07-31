package digiwin.smartdepott100.module.activity.stock.storecheck;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
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
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.stock.StockCheckLogic;


/**
 * @author 孙长权
 * @des 库存盘点
 */
public class StockCheckActivity extends BaseFirstModuldeActivity {
    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindViews({R.id.ll_scan_locator, R.id.ll_barcode_sp, R.id.ll_chcek_number})
    List<View> views;
    @BindViews({R.id.tv_scan_locator, R.id.tv_barcode_sp, R.id.tv_chcek_number})
    List<TextView> textViews;
    @BindViews({R.id.et_scan_locator, R.id.et_barcode_sp, R.id.et_chcek_number})
    List<EditText> editTexts;

    /**
     * 库位
     */
    @BindView(R.id.ll_scan_locator)
    LinearLayout llScanLocator;
    @BindView(R.id.tv_scan_locator)
    TextView tvScanLocator;
    @BindView(R.id.et_scan_locator)
    EditText etScanLocator;

    @OnFocusChange(R.id.et_scan_locator)
    void scanLocatorFocusChanage() {
        ModuleUtils.viewChange(llScanLocator, views);
        ModuleUtils.tvChange(activity, tvScanLocator, textViews);
        ModuleUtils.etChange(activity, etScanLocator, editTexts);
    }
    /**
     * 库位锁定
     */
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isCardNumberLock(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    /**
     * 盘点量
     */
    @BindView(R.id.ll_chcek_number)
    LinearLayout llChcekNumber;
    @BindView(R.id.tv_chcek_number)
    TextView tvChcekNumber;
    @BindView(R.id.et_chcek_number)
    EditText etChcekNumber;

    @OnFocusChange(R.id.et_chcek_number)
    void checkNumberFocusChanage() {
        ModuleUtils.viewChange(llChcekNumber, views);
        ModuleUtils.tvChange(activity, tvChcekNumber, textViews);
        ModuleUtils.etChange(activity, etChcekNumber, editTexts);
    }

    /**
     * 物料
     */
    @BindView(R.id.ll_barcode_sp)
    LinearLayout llBarcodeSp;
    @BindView(R.id.tv_barcode_sp)
    TextView tvBarcodeSp;
    @BindView(R.id.et_barcode_sp)
    EditText etBarcodeSp;

    @OnFocusChange(R.id.et_barcode_sp)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llBarcodeSp, views);
        ModuleUtils.tvChange(activity, tvBarcodeSp, textViews);
        ModuleUtils.etChange(activity, etBarcodeSp, editTexts);
    }
    /**
     * 物料累积量
     */
    @BindView(R.id.tv_material_sumnumber)
    TextView tvMaterialSumnumber;
    /**
     * 盘点笔数
     */
    @BindView(R.id.tv_material_checknumber)
    TextView tvCheckNumber;

    //show界面
    @BindView(R.id.includedetail)
    View includeDetail;
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    /**
     * 上个界面传递过来的
     */
    FilterResultOrderBean data;

    SaveBean saveBean;

    StockCheckLogic stockCheckLogic;

    private int number;

    private final int LOCATORWHAT = 1000;

    private final int BARCODEWHAT = 1001;

    //库位扫描
    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(LOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    //物料
    @OnTextChanged(value = R.id.et_barcode_sp, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    //盘点量
    @OnTextChanged(value = R.id.et_chcek_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void checkNumberChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            saveBean.setQty(s.toString().trim());//赋值数量
        }
    }

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT://物料
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.DOC_NO, data.getDoc_no());
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, saveBean.getWarehouse_no());
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_no());
                    etBarcodeSp.setKeyListener(null);
                    stockCheckLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etBarcodeSp.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeShow = barcodeBackBean.getShowing();
                            barcodeFlag = true;
                            show();
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());//料号
                            saveBean.setItem_no(barcodeBackBean.getItem_no());//料号
                            saveBean.setLot_no(barcodeBackBean.getLot_no());//批号
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            //赋值累积量
                            tvMaterialSumnumber.setText(barcodeBackBean.getScan_sumqty());
                            etChcekNumber.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            etBarcodeSp.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etBarcodeSp.setText("");
                                    etBarcodeSp.requestFocus();
                                }
                            });

                        }
                    });
                    break;
                case LOCATORWHAT://库位
                    saveBean.setDoc_no(data.getDoc_no());
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.DOC_NO, data.getDoc_no());
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, msg.obj.toString());
                    etScanLocator.setKeyListener(null);
                    stockCheckLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            locatorShow = locatorBackBean.getShowing();
                            locatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_no(locatorBackBean.getStorage_spaces_no());//库位
                            saveBean.setWarehouse_no(locatorBackBean.getWarehouse_no());//仓库
                            etBarcodeSp.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            locatorFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanLocator.setText("");
                                    locatorFlag = false;
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

    @OnClick(R.id.commit)
    void commit() {
        if (!locatorFlag) {
            showFailedDialog(R.string.scan_locator);
            return;
        }
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (StringUtils.isBlank(etChcekNumber.getText().toString())) {
            showFailedDialog(R.string.check_input_num);
            return;
        }
        showLoadingDialog();
        Map<String, String> map = ObjectAndMapUtils.getValueMap(saveBean);
        stockCheckLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessNoClickDialog(getResources().getString(R.string.material_sum_number)+":" + msg);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       dismissLoadingDialog();
                    }
                },500);
                //赋值累积量
                tvMaterialSumnumber.setText(msg);
                tvCheckNumber.setText(String.valueOf(++number));
                initData();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.STORECHECK;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.check_stock);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_stockcheck;
    }

    @Override
    protected void doBusiness() {
        data = (FilterResultOrderBean) getIntent().getExtras().get("data");
        number = 0;
        tvCheckNumber.setText(String.valueOf(number));
        tvMaterialSumnumber.setText("");
        saveBean = new SaveBean();

        initData();
    }

    private boolean locatorFlag;
    private String locatorShow;

    private boolean barcodeFlag;
    private String barcodeShow;

    /**
     * 保存完成之后的操作
     */
    private void initData() {
        //库位
        if (!cbLocatorlock.isChecked()) {
            locatorFlag = false;
            etScanLocator.setText("");
            locatorShow = "";
            etScanLocator.requestFocus();
        } else {
            etBarcodeSp.requestFocus();
        }
        //重置物料
        etBarcodeSp.setText("");
        barcodeFlag = false;
        barcodeShow = "";

        etChcekNumber.setText("");

        //重置show内容
        tvDetailShow.setText("");
        show();

        stockCheckLogic = StockCheckLogic.getInstance(activity, moduleCode(), mTimestamp.toString());
    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(locatorShow + "\\n" + barcodeShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            includeDetail.setVisibility(View.VISIBLE);
        } else {
            includeDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITISD;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
