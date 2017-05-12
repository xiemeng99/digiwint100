package digiwin.smartdepott100.module.fragment.produce.productionleader;

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
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.produce.productionleader.ProductionLeaderActivity;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @author 赵浩然
 * @des 生产超领 扫描页
 * @date 2017/3/30
 */
public class ProductionLeaderScanFg extends BaseFragment {

    private ProductionLeaderActivity pactivity;

    SaveBean saveBean;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;

    FilterResultOrderBean localData;

    CommonLogic commonLogic;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    /**
     * show
     */
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;

    /**
     *show布局
     */
    @BindView(R.id.rl_zx_detail)
    RelativeLayout includeDetail;

    /**
     * 已扫量
     */
    @BindView(R.id.tv_swept_volume)
    TextView tv_swept_volume;

    @BindViews({R.id.et_barcode_no, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_barcode_no, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode_no_string, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;

    /**
     * 物料条码
     */
    @BindView(R.id.tv_barcode_no_string)
    TextView tv_barcode_no_string;
    @BindView(R.id.et_barcode_no)
    EditText et_barcode_no;
    @BindView(R.id.ll_barcode_no)
    LinearLayout ll_barcode_no;

    /**
     * 库位
     */
    @BindView(R.id.tv_locator)
    TextView tvLocator;
    @BindView(R.id.et_scan_locator)
    EditText etScanLocator;
    @BindView(R.id.ll_scan_locator)
    LinearLayout llScanLocator;
    /**
     * 数量
     */
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;

    @OnFocusChange(R.id.et_barcode_no)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_barcode_no, views);
        ModuleUtils.etChange(activity, et_barcode_no, editTexts);
        ModuleUtils.tvChange(activity, tv_barcode_no_string, textViews);
    }

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_locator)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(llScanLocator, views);
        ModuleUtils.etChange(activity, etScanLocator, editTexts);
        ModuleUtils.tvChange(activity, tvLocator, textViews);
    }

    @OnTextChanged(value = R.id.et_barcode_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
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

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnClick(R.id.save)
    void save() {
        if (!locatorFlag) {
            showFailedDialog(R.string.scan_locator);
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
        saveBean.setQty(etInputNum.getText().toString());

        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                tv_swept_volume.setText(saveBackBean.getScan_sumqty());
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
     * 条码展示
     */
    String barcodeShow = "";
    /**
     * 库位展示
     */
    String locatorShow = "";


    public void clear(){
        includeDetail.setVisibility(View.GONE);
        barcodeShow = "";
        locatorShow = "";
        etInputNum.setText("");
        et_barcode_no.setText("");

        if(cbLocatorlock.isChecked()){
            if(StringUtils.isBlank(etScanLocator.getText().toString().trim())){
                locatorFlag = false;
            }

            barcodeFlag = false;
            et_barcode_no.requestFocus();
        }else if(!cbLocatorlock.isChecked()){
            locatorFlag = false;
            barcodeFlag = false;
            etScanLocator.setText("");
            etScanLocator.requestFocus();
        }
        show();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
        switch (msg.what){

            case LOCATORWHAT:
                HashMap<String, String> locatorMap = new HashMap<>();
                locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                    @Override
                    public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                        locatorFlag = true;
                        saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                        saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                        et_barcode_no.requestFocus();
                        locatorShow = locatorBackBean.getShow();
                        show();
                    }

                    @Override
                    public void onFailed(String error) {
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                etScanLocator.setText("");
                                etScanLocator.requestFocus();
                            }
                        });
                        locatorFlag = false;
                    }
                });
                break;

            case BARCODEWHAT:
                HashMap<String, String> barcodeMap = new HashMap<>();
                barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                barcodeMap.put(AddressContants.DOC_NO, localData.getDoc_no());
                barcodeMap.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());

                commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                    @Override
                    public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                        if(StringUtils.isBlank(etScanLocator.getText().toString())){
                            showFailedDialog(getResources().getString(R.string.scan_locator));
                            return;
                        }
                        showBarcode(barcodeBackBean);
                    }

                    @Override
                    public void onFailed(String error) {
                        barcodeFlag = false;
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                et_barcode_no.setText("");
                                et_barcode_no.requestFocus();
                            }
                        });
                    }
                });
                break;
        }
        return false;
        }
    });

    /**
     * 对比物料条码
     * @param barcodeBackBean
     */
    public void showBarcode(ScanBarcodeBackBean barcodeBackBean){
        locatorShow = barcodeBackBean.getShow();

        tv_swept_volume.setText(StringUtils.deleteZero(barcodeBackBean.getScan_sumqty()));
        etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
        barcodeFlag = true;
        saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
        saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
        saveBean.setItem_no(barcodeBackBean.getItem_no());
        saveBean.setUnit_no(barcodeBackBean.getUnit_no());
        saveBean.setLot_no(barcodeBackBean.getLot_no());
        saveBean.setDoc_no(localData.getDoc_no());
        saveBean.setFifo_check(barcodeBackBean.getFifo_check());
        show();
        etInputNum.requestFocus();
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_productionleaderscan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (ProductionLeaderActivity) activity;
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        initData();
    }

    public void upDateList() {
        if(cbLocatorlock.isChecked()){
            et_barcode_no.requestFocus();
        }
        if(!cbLocatorlock.isChecked() && StringUtils.isBlank(etScanLocator.getText().toString())){
            etScanLocator.requestFocus();
        }else if(StringUtils.isBlank(et_barcode_no.getText().toString().trim())){
            et_barcode_no.requestFocus();
        }else if(StringUtils.isBlank(etInputNum.getText().toString().trim())){
            etInputNum.requestFocus();
        }
        tv_swept_volume.setText("");
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        et_barcode_no.setText("");
        etScanLocator.setText("");
        tvDetailShow.setText("");
        tv_swept_volume.setText("");

        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        etScanLocator.requestFocus();

        FilterResultOrderBean data = (FilterResultOrderBean) getActivity().getIntent().getSerializableExtra("data");
        localData = new FilterResultOrderBean();
        localData = data;
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
}

