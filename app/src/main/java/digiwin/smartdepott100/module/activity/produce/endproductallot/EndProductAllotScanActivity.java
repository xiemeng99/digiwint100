package digiwin.smartdepott100.module.activity.produce.endproductallot;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
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
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.core.coreutil.FiFoCheckUtils;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.common.CommonItemNoFiFoAdapter;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

import static digiwin.library.utils.StringUtils.sum;


/**
 * @author xiemeng
 * @module 依成品调拨
 * @date 2017/4/13
 */
public class EndProductAllotScanActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindView(R.id.ry_list)
    RecyclerView mRcList;

    /**
     * 物料条码
     */
    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;

    /**
     * 储位
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

    /**
     * 已扫量
     */
    @BindView(R.id.tv_scan_hasScan)
    TextView tvScanHasScan;
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

    @BindViews({R.id.et_tray,R.id.et_scan_barocde, R.id.et_scan_locator,R.id. et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_tray,R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_tray,R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;


    /**
     * 条码类型 料号类型
     */
    private String codetype = "1";

    CommonItemNoFiFoAdapter adapter;

    SaveBean saveBean;

    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;
    /**
     * FIFO
     */
    final int FIFOWHAT = 1003;
    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    /**
     * 返回汇总页面使用
     */
    public final int SCANCODE = 0123;

    CommonLogic commonLogic;

    List<FifoCheckBean> localFifoList;

    //弹出汇总的对话框
    @OnClick(R.id.iv_title_setting)
    void sumDialog(){
        EndProductAllotDialog.showSumDataDialog(context,localData);
    }

    ListSumBean localData;

    /**
     * 保存
     */
    @OnClick(R.id.save)
    void saveData(){
        if(!locatorFlag){
            showFailedDialog(R.string.scan_locator);
            return;
        }

        if(!barcodeFlag){
            showFailedDialog(R.string.scan_barcode);
            return;
        }

        if (StringUtils.isBlank(etInputNum.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setQty(etInputNum.getText().toString());
        saveBean.setAvailable_in_qty(localData.getApply_qty());
        saveBean.setStorage_spaces_in_no(localData.getWarehouse_in_no());
        saveBean.setWarehouse_in_no(localData.getWarehouse_in_no());
        String fifoCheck = FiFoCheckUtils.fifoCheck(saveBean,localFifoList);
        if(!StringUtils.isBlank(fifoCheck)){
            showFailedDialog(fifoCheck);
            return;
        }
        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                //已扫量
                tvScanHasScan.setText(saveBackBean.getScan_sumqty());
                dismissLoadingDialog();
                //匹配量
                localData.setScan_sumqty(tvScanHasScan.getText().toString());
                getFifo();
                clearData(type);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }
    /**
     * 物料类型
     */
    String type;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanBarocde.requestFocus();
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    /**
     * 焦点颜色变化
     */
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

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case LOCATORWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    etScanLocator.setKeyListener(null);
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            dismissLoadingDialog();
                            locatorFlag = true;
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            saveBean.setAllow_negative_stock(locatorBackBean.getAllow_negative_stock());
                            //如果传入的料号为物料级，则跳过扫码，数量获取焦点
                            if(type.equals(codetype)){
                                etInputNum.requestFocus();
                            }else{
                                etScanBarocde.requestFocus();
                            }
                            if (barcodeFlag&&CommonUtils.isAutoSave(saveBean)){
                                saveData();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            dismissLoadingDialog();
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
                //条码
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_out_no());
                    etScanBarocde.setKeyListener(null);
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etScanBarocde.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            try {
                                if (!localData.getLow_order_item_no().equals(barcodeBackBean.getItem_no())) {
                                    barcodeFlag = false;
                                    showFailedDialog(R.string.scanbarcode_nomatch_item, new OnDialogClickListener() {
                                        @Override
                                        public void onCallback() {
                                            etScanBarocde.setText("");
                                        }
                                    });
                                    return;
                                }
                                tvScanHasScan.setText(barcodeBackBean.getScan_sumqty());
                                showBarcode(barcodeBackBean);
                            }catch (Exception e){
                                showFailedDialog(R.string.scanbarcode_nomatch_item, new OnDialogClickListener() {
                                    @Override
                                    public void onCallback() {
                                        etScanBarocde.setText("");
                                    }
                                });
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

                case FIFOWHAT:
                    HashMap<String,String> map = (HashMap<String, String>) msg.obj;
                    commonLogic.getFifo(map, new CommonLogic.FIFOGETListener() {
                        @Override
                        public void onSuccess(List<FifoCheckBean> fiFoBeanList) {
                            dismissLoadingDialog();
                            if(null != fiFoBeanList && fiFoBeanList.size() > 0){
                                localFifoList = new ArrayList<FifoCheckBean>();
                                localFifoList = fiFoBeanList;
                                adapter = new CommonItemNoFiFoAdapter(activity,fiFoBeanList);
                                mRcList.setAdapter(adapter);
                            }else {
                                localFifoList = new ArrayList<FifoCheckBean>();
                                adapter = new CommonItemNoFiFoAdapter(activity,fiFoBeanList);
                                mRcList.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error);
                        }
                    });
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getString(R.string.endproduct_allot)+getString(R.string.barcode_scan));
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.dankeliao);
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.ENDPRODUCTALLOT;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_endproduct_allotscan;
    }

    @Override
    protected void doBusiness() {
        if (CommonUtils.isUseTray()){
            llTray.setVisibility(View.VISIBLE);
            lineTray.setVisibility(View.VISIBLE);
        }else {
            llTray.setVisibility(View.GONE);
            lineTray.setVisibility(View.GONE);
        }
        initData();
        localData= (ListSumBean) getIntent().getSerializableExtra("sumdata");
        type = localData.getItem_barcode_type();
        if(type.equals(codetype)){
            etScanBarocde.setText(localData.getLow_order_item_no());
        }
        commonLogic = CommonLogic.getInstance(context, module, mTimestamp.toString());
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        mRcList.setLayoutManager(fullyLinearLayoutManager);
        getFifo();
    }

    public void getFifo(){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(AddressContants.ITEM_NO,localData.getLow_order_item_no());
        map.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
        map.put(AddressContants.QTY,localData.getApply_qty());
        map.put(AddressContants.UNIT_NO,localData.getUnit_no());
        mHandler.sendMessageDelayed(mHandler.obtainMessage(FIFOWHAT, map), AddressContants.DELAYTIME);
    }

    /**
     * 保存后 根据条码类型 以及是否锁定库位
     */
    public void clearData(String type){

        if(cbLocatorlock.isChecked()){
            if(StringUtils.isBlank(etScanLocator.getText().toString().trim())){
                locatorFlag = false;
            }
        }
        if(type.equals(codetype)){
            barcodeFlag = true;
            etInputNum.setText("");
            if(!cbLocatorlock.isChecked()){
                locatorFlag = false;
                etScanLocator.setText("");
                etScanLocator.requestFocus();
            }else{
                etInputNum.requestFocus();
            }
        }else{
            barcodeFlag = false;
            etInputNum.setText("");
            etScanBarocde.setText("");
            if(!cbLocatorlock.isChecked()){
                locatorFlag = false;
                etScanLocator.setText("");
                etScanLocator.requestFocus();
            }else{
                etScanBarocde.requestFocus();
            }
        }
    }

    /**
     * 对比物料条码 存入参数
     * @param barcodeBackBean
     */
    public void showBarcode(ScanBarcodeBackBean barcodeBackBean){
        etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
        barcodeFlag = true;
        saveBean.setFifo_check(barcodeBackBean.getFifo_check());
        saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
        saveBean.setProduct_no(barcodeBackBean.getProduct_no());
        saveBean.setItem_no(barcodeBackBean.getItem_no());
        saveBean.setUnit_no(localData.getUnit_no());//抓取下阶料单位不取条码单位
        saveBean.setLot_no(barcodeBackBean.getLot_no());
        saveBean.setCustomer_no(barcodeBackBean.getCol1());
        saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
        etInputNum.requestFocus();
        if (locatorFlag&&CommonUtils.isAutoSave(saveBean)){
            saveData();
        }
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        etScanBarocde.setText("");
        etScanLocator.setText("");
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        etScanLocator.requestFocus();
        localFifoList = new ArrayList<FifoCheckBean>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
