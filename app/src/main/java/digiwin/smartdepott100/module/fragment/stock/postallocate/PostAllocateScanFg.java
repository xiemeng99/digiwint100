package digiwin.smartdepott100.module.fragment.stock.postallocate;

import android.os.Handler;
import android.os.Message;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.Button;
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
    EditText et_scan_barocde;

    /**
     * 拨出库位
     */
    @BindView(R.id.tv_locator_out)
    TextView tv_locator_out;
    /**
     * 拨出库位
     */
    @BindView(R.id.et_scan_locator_out)
    EditText et_scan_locator_out;
    /**
     * 锁定拨出库位
     */
    @BindView(R.id.cb_locatorlock_out)
    CheckBox cb_locatorlock_out;
    /**
     * 拨入库位
     */
    @BindView(R.id.tv_locator_in)
    TextView tv_locator_in;
    /**
     * 拨入库位
     */
    @BindView(R.id.et_scan_locator_in)
    EditText et_scan_locator_in;
    /**
     * 锁定拨入库位
     */
    @BindView(R.id.cb_locatorlock_in)
    CheckBox cb_locatorlock_in;
    /**
     * 数量
     */
    @BindView(R.id.et_input_num)
    EditText et_input_num;

    /**
     * 保存按钮
     */
    @BindView( R.id.save)
    Button save;

    @BindView(R.id.ll_scan_barcode)
    LinearLayout ll_scan_barcode;
    @BindView(R.id.ll_scan_locator_in)
    LinearLayout ll_scan_locator_in;
    @BindView(R.id.ll_scan_locator_out)
    LinearLayout ll_scan_locator_out;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindViews({R.id.et_scan_barocde, R.id.et_scan_locator_in,R.id.et_scan_locator_out, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_scan_locator_in, R.id.ll_scan_locator_out, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator_in, R.id.tv_locator_out, R.id.tv_number})
    List<TextView> textViews;

    /**
     * 公共区域展示
     */
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;

    @BindView(R.id.includedetail)
    RelativeLayout includeDetail;

    /**
     * 已扫描量
     */
    @BindView(R.id.tv_scaned_num)
    TextView  tv_scaned_num;
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
            et_scan_locator_in.setKeyListener(null);
        } else {
            et_scan_locator_in.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }
    @OnCheckedChanged(R.id.cb_locatorlock_out)
    void isLock_out(boolean checked) {
        if (checked) {
            et_scan_locator_out.setKeyListener(null);
        } else {
            et_scan_locator_out.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_scan_barcode, views);
        ModuleUtils.etChange(activity, et_scan_barocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_locator_in)
    void inLocatorFocusChanage() {
        ModuleUtils.viewChange(ll_scan_locator_in, views);
        ModuleUtils.etChange(activity, et_scan_locator_in, editTexts);
        ModuleUtils.tvChange(activity, tv_locator_in, textViews);
    }
    @OnFocusChange(R.id.et_scan_locator_out)
    void outLocatorFocusChanage() {
        ModuleUtils.viewChange(ll_scan_locator_out, views);
        ModuleUtils.etChange(activity, et_scan_locator_out, editTexts);
        ModuleUtils.tvChange(activity, tv_locator_out, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, et_input_num, editTexts);
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
    void Save() {
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
        if (StringUtils.isBlank(et_input_num.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setDoc_no(orderBean.getDoc_no());
        saveBean.setQty(et_input_num.getText().toString());
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

    FilterResultOrderBean orderBean = new FilterResultOrderBean();

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
                    barcodeMap.put(AddressContants.DOC_NO,orderBean.getDoc_no());
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            barcodeShow = barcodeBackBean.getShowing();
                            if(!StringUtils.isBlank(barcodeBackBean.getBarcode_qty())){
                                et_input_num.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            }
                            if(!StringUtils.isBlank(barcodeBackBean.getScan_sumqty())){
                                tv_scaned_num.setText(StringUtils.deleteZero(barcodeBackBean.getScan_sumqty()));
                            }
                            barcodeFlag = true;
                            show();
                            saveBean.setQty(barcodeBackBean.getBarcode_qty());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setScan_sumqty(barcodeBackBean.getScan_sumqty());
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            et_input_num.requestFocus();
                            if (CommonUtils.isAutoSave(saveBean)){
                                Save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            barcodeFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_barocde.setText("");
                                }
                            });
                        }
                    });
                    break;
                case LOCATORWHATIN:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            inLocatorShow = locatorBackBean.getShowing();
                            inLocatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            et_scan_barocde.requestFocus();
                            if (CommonUtils.isAutoSave(saveBean)){
                                Save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_locator_in.setText("");
                                }
                            });
                            inLocatorFlag = false;
                        }
                    });
                    break;
                case LOCATORWHATOUT:
                    HashMap<String, String> locatorMap1 = new HashMap<>();
                    locatorMap1.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    commonLogic.scanLocator(locatorMap1, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            outLocatorShow = locatorBackBean.getShowing();
                            outLocatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            if(cb_locatorlock_in.isChecked()){
                                et_scan_barocde.requestFocus();
                            }else{
                                et_scan_locator_in.requestFocus();
                            }
                            if (CommonUtils.isAutoSave(saveBean)){
                                Save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_locator_out.setText("");
                                }
                            });
                            outLocatorFlag = false;
                        }
                    });
                    break;
            }
            return false;
        }
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_post_allocate_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PostAllocateScanActivity) activity;
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
        tv_scaned_num.setText(saveBean.getScan_sumqty());
        et_scan_barocde.setText("");
        et_input_num.setText("");
        barcodeFlag = false;
        if (!cb_locatorlock_out.isChecked())
        {
            outLocatorShow = "";
            outLocatorFlag = false;
            et_scan_locator_out.setText("");
            et_scan_locator_out.requestFocus();
        }
        if(cb_locatorlock_in.isChecked()){
            et_scan_barocde.requestFocus();
        }else{
            inLocatorShow = "";
            inLocatorFlag = false;
            et_scan_locator_in.setText("");
            et_scan_locator_in.requestFocus();
        }
        barcodeShow = "";
        show();
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        et_scan_barocde.setText("");
        et_scan_locator_in.setText("");
        et_scan_locator_out.setText("");
        et_input_num.setText("");
        tv_scaned_num.setText("");
        barcodeShow = "";
        inLocatorShow = "";
        outLocatorShow = "";
        show();
        barcodeFlag = false;
        outLocatorFlag = false;
        inLocatorFlag = false;
        cb_locatorlock_in.setChecked(false);
        cb_locatorlock_out.setChecked(false);
        saveBean = new SaveBean();
        commonLogic = PostAllocateLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        orderBean = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable(AddressContants.ORDERDATA);
        et_scan_locator_out.requestFocus();
        }
        }
