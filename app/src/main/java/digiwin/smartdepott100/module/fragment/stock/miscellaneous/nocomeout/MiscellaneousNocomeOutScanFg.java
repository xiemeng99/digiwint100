package digiwin.smartdepott100.module.fragment.stock.miscellaneous.nocomeout;

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
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.stock.miscellaneousissues.MiscellaneousNocomeOutActivity;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanEmployeeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.common.ScanReasonCodeBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @author 唐孟宇
 * @des 杂项发料 扫码页面
 */
public class MiscellaneousNocomeOutScanFg extends BaseFragment {

    /**
     * 理由码
     */
    @BindView(R.id.tv_reason_code)
    TextView tv_reason_code;

    /**
     * 理由码
     */
    @BindView(R.id.et_reason_code)
    EditText et_reason_code;
    /**
     * 申请部门
     */
    @BindView(R.id.tv_department)
    TextView tv_department;

    /**
     * 申请部门
     */
    @BindView(R.id.et_department)
    EditText et_department;
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
     * 库位
     */
    @BindView(R.id.tv_locator)
    TextView tv_locator;
    /**
     * 库位
     */
    @BindView(R.id.et_scan_locator)
    EditText et_scan_locator;
    /**
     * 锁定库位
     */
    @BindView(R.id.cb_locatorlock)
    CheckBox cb_locatorlock;
    /**
     * 锁定理由码
     */
    @BindView(R.id.cb_reason_code)
    CheckBox cb_reason_code;
    /**
     * 锁定部门
     */
    @BindView(R.id.cb_department)
    CheckBox cb_department;
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

    @BindView(R.id.ll_reason_code)
    LinearLayout ll_reason_code;
    @BindView(R.id.ll_department)
    LinearLayout ll_department;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout ll_scan_barcode;
    @BindView(R.id.ll_scan_locator)
    LinearLayout ll_scan_locator;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindViews({R.id.et_reason_code,R.id.et_department,R.id.et_scan_barocde, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_reason_code,R.id.ll_department,R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_reason_code,R.id.tv_department, R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
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
    TextView tv_scaned_num;

    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 库位展示
     */
    String locatorShow;
    /**
     * 理由码展示
     */
    String reasonCodeShow;
    /**
     * 申请部门展示
     */
    String departmentShow;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;
    /**
     * 理由码扫描
     */
    boolean reasonCodeFlag;
    /**
     * 部门扫描
     */
    boolean departmentFlag;

    SaveBean saveBean;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;
    /**
     * 理由码
     */
    final int REASONCODEWHAT = 1003;
    /**
     * 申请部门
     */
    final int DEPARTMENTWHAT = 1004;

    /**
     * 员工编号 汇总界面提交使用
     */
    static String employee_no;
    /**
     * 部门编号 汇总界面提交使用
     */
    static String department_no;

    CommonLogic commonLogic;

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            et_scan_locator.setKeyListener(null);
        } else {
            et_scan_locator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }
    @OnCheckedChanged(R.id.cb_reason_code)
    void isLock1(boolean checked) {
        if (checked) {
            et_reason_code.setKeyListener(null);
        } else {
            et_reason_code.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
}
    @OnCheckedChanged(R.id.cb_department)
    void isLock2(boolean checked) {
        if (checked) {
            et_department.setKeyListener(null);
        } else {
            et_department.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_reason_code)
    void reasonCodeFocusChanage() {
        ModuleUtils.viewChange(ll_reason_code, views);
        ModuleUtils.etChange(activity, et_reason_code, editTexts);
        ModuleUtils.tvChange(activity, tv_reason_code, textViews);
    }

    @OnFocusChange(R.id.et_department)
    void departmentFocusChanage() {
        ModuleUtils.viewChange(ll_department, views);
        ModuleUtils.etChange(activity, et_department, editTexts);
        ModuleUtils.tvChange(activity, tv_department, textViews);
    }
    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_scan_barcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_locator)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(ll_scan_locator, views);
        ModuleUtils.etChange(activity, et_scan_locator, editTexts);
        ModuleUtils.tvChange(activity, tv_locator, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, et_input_num, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = R.id.et_reason_code, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void reasonCodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(REASONCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(REASONCODEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_department, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void departmentChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(DEPARTMENTWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(DEPARTMENTWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(LOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.save)
    void save() {
        if (!reasonCodeFlag) {
            showFailedDialog(R.string.scan_reason_code);
            return;
        }
        if (!departmentFlag) {
            showFailedDialog(R.string.scan_dockingper);
            return;
        }
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (!locatorFlag) {
            showFailedDialog(R.string.scan_locator);
            return;
        }
        if (StringUtils.isBlank(et_input_num.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setReason_code_no(et_reason_code.getText().toString());
        saveBean.setDepartment_no(department_no);
        saveBean.setEmployee_no(employee_no);
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

    MiscellaneousNocomeOutActivity pactivity;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REASONCODEWHAT:
                    HashMap<String, String> reasonMap = new HashMap<>();
                    reasonMap.put(AddressContants.REASONCODENO, String.valueOf(msg.obj));
                    commonLogic.scanReasonCode(reasonMap, new CommonLogic.ScanReasonCodeListener() {
                        @Override
                        public void onSuccess(ScanReasonCodeBackBean barcodeBackBean) {
                            reasonCodeShow = barcodeBackBean.getShowing();
                            reasonCodeFlag = true;
                            show();
                            saveBean.setReason_code_no(barcodeBackBean.getReason_code_no());
                            cb_reason_code.setChecked(true);
                            if (cb_department.isChecked()){
                                if(cb_locatorlock.isChecked()){
                                    etScanBarocde.requestFocus();
                                }else{
                                    et_scan_locator.requestFocus();
                                }
                            }else {
                                et_department.requestFocus();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            reasonCodeFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_reason_code.setText("");
                                }
                            });
                        }
                    });
                    break;
                case DEPARTMENTWHAT:
                    final HashMap<String, String> departmentMap = new HashMap<>();
                    departmentMap.put(AddressContants.EMPLOYEENO, String.valueOf(msg.obj));
                    commonLogic.scanEmployeeCode(departmentMap, new CommonLogic.ScanEmployeementListener() {
                        @Override
                        public void onSuccess(ScanEmployeeBackBean barcodeBackBean) {
                            departmentShow = barcodeBackBean.getShowing();
                            department_no = barcodeBackBean.getDepartment_no();
                            employee_no = barcodeBackBean.getEmployee_no();
                            saveBean.setDepartment_no(department_no);
                            saveBean.setEmployee_no(employee_no);
                            departmentFlag = true;
                            show();
                            cb_department.setChecked(true);
                            if(cb_locatorlock.isChecked()){
                                etScanBarocde.requestFocus();
                            }else{
                                et_scan_locator.requestFocus();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            departmentFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_department.setText("");
                                }
                            });
                        }
                    });
                    break;
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_out_no());
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            barcodeShow = barcodeBackBean.getShowing();
                            if(!StringUtils.isBlank(barcodeBackBean.getBarcode_qty())){
                                et_input_num.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            }
                            tv_scaned_num.setText(barcodeBackBean.getScan_sumqty());
                            barcodeFlag = true;
                            show();
                            saveBean.setQty(barcodeBackBean.getBarcode_qty());
                            saveBean.setProduct_no(barcodeBackBean.getProduct_no());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setScan_sumqty(barcodeBackBean.getScan_sumqty());
                            saveBean.setAvailable_in_qty("0");
                            et_input_num.requestFocus();
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            if (locatorFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
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
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            etScanBarocde.requestFocus();
                            if (barcodeFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_scan_locator.setText("");
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
        return R.layout.fg_miscellaneous_nocome_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (MiscellaneousNocomeOutActivity) activity;
        initData();
    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(barcodeShow + "\\n" + locatorShow + "\\n" +departmentShow+ "\\n" + reasonCodeShow ));
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
        etScanBarocde.setText("");
        et_input_num.setText("");
        barcodeFlag = false;
        if(!cb_reason_code.isChecked()){
            reasonCodeFlag = false;
            et_reason_code.setText("");
            reasonCodeShow = "";
        }
        if(!cb_department.isChecked()){
            departmentFlag = false;
            et_department.setText("");
            departmentShow = "";
        }
        if (!cb_locatorlock.isChecked()){
            locatorFlag = false;
            locatorShow = "";
            et_scan_locator.setText("");
        }
        barcodeShow = "";
        show();
        initFocus();
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        tv_scaned_num.setText("");
        et_reason_code.setText("");
        et_department.setText("");
        etScanBarocde.setText("");
        et_scan_locator.setText("");
        cb_department.setChecked(false);
        cb_reason_code.setChecked(false);
        cb_locatorlock.setChecked(false);
        reasonCodeShow = "";
        departmentShow = "";
        barcodeShow = "";
        locatorShow = "";
        show();
        reasonCodeFlag = false;
        departmentFlag = false;
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        et_reason_code.requestFocus();
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        }

    public void initFocus(){
        try{
            if(!cb_reason_code.isChecked()){
                et_reason_code.requestFocus();
            }
            else {
                if(!cb_department.isChecked()){
                    et_department.requestFocus();
                }
                else{
                    if(!cb_locatorlock.isChecked()){
                        et_scan_locator.requestFocus();
                    }else{
                        etScanBarocde.requestFocus();
                    }
                }
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
