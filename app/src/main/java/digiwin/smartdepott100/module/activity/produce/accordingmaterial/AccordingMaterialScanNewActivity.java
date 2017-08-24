package digiwin.smartdepott100.module.activity.produce.accordingmaterial;

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
import java.util.Map;

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
import digiwin.smartdepott100.module.adapter.produce.AccordingMaterialFiFo_Adapter;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

import static digiwin.library.utils.StringUtils.sum;
import static digiwin.smartdepott100.R.id.et_input_num;
import static digiwin.smartdepott100.R.id.et_scan_barocde;


/**
 * @author 赵浩然
 * @module 依成品发料扫描 新FIFO
 * @date 2017/3/2
 */

public class AccordingMaterialScanNewActivity extends BaseTitleActivity {

    AccordingMaterialScanNewActivity activity;

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
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView mTv_item_name;

    /**
     * 规格
     */
    @BindView(R.id.et_format)
    TextView et_format;

    /**
     * 库存量
     */
    @BindView(R.id.tv_material_return_big)
    TextView tv_stock_balance;

    /**
     * 欠料量
     */
    @BindView(R.id.tv_material_return)
    TextView tv_under_feed;

    /**
     * 已扫
     */
    @BindView(R.id.tv_feeding_amount)
    TextView tv_actual_yield;

    @BindView(R.id.ry_list)
    RecyclerView mRc_list;

    /**
     * 物料条码
     */
    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;

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
    @BindView(et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindViews({et_scan_barocde, R.id.et_scan_locator, et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;

    AccordingMaterialFiFo_Adapter adapter;

    SaveBean saveBean;

    ListSumBean localdata;

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

    /**
     * 提交按钮
     */
    @BindView(R.id.save)
    Button mBtn_save;

    /**
     * 物料类型
     */
    String type;

    /**
     * 条码类型 料号类型
     */
    private String codetype = "1";

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
        //判断库存 欠料数量  哪个小取哪一个
        saveBean.setAvailable_in_qty(StringUtils.getMinQty(tv_stock_balance.getText().toString(),tv_under_feed.getText().toString()));
        String fifoCheck = FiFoCheckUtils.fifoCheck(saveBean,localFifoList);
        if(!StringUtils.isBlank(fifoCheck)){
            showFailedDialog(fifoCheck);
            return;
        }

        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                showToast(getResources().getString(R.string.save_success));
                float scan_sum = sum(etInputNum.getText().toString(),tv_actual_yield.getText().toString());
                tv_actual_yield.setText(StringUtils.deleteZero(String.valueOf(scan_sum)));
                if(null != localFifoList){
                    if(localFifoList.size() > 0 && AddressContants.FIFOY.equals(saveBean.getFifo_check())){
                        getFifo();
                    }
                }
                clearData(type);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        clearData(type);
                    }
                });
            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(et_scan_barocde)
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

    @OnFocusChange(et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
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
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            dismissLoadingDialog();
                            locatorFlag = true;
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            saveBean.setAllow_negative_stock(locatorBackBean.getAllow_negative_stock());
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

                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_out_no());
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            try {
                                if (!localdata.getLow_order_item_no().equals(barcodeBackBean.getItem_no())) {
                                    barcodeFlag = false;
                                    showFailedDialog(R.string.scanbarcode_nomatch_item, new OnDialogClickListener() {
                                        @Override
                                        public void onCallback() {
                                            etScanBarocde.setText("");
                                        }
                                    });
                                    return;
                                }
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
                    Map<String,String> map = (Map<String, String>) msg.obj;
                    commonLogic.getFifoAccording(map, new CommonLogic.FIFOAccordingGETListener() {
                        @Override
                        public void onSuccess(List<FifoCheckBean> fiFoBeanList) {
                            dismissLoadingDialog();
                            localFifoList = new ArrayList<FifoCheckBean>();
                            if(null != fiFoBeanList && fiFoBeanList.size() > 0){
                                localFifoList = fiFoBeanList;
                                adapter = new AccordingMaterialFiFo_Adapter(activity,fiFoBeanList);
                                mRc_list.setAdapter(adapter);
                            }else{
                                adapter = new AccordingMaterialFiFo_Adapter(activity,localFifoList);
                                mRc_list.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            dismissLoadingDialog();
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
        mName.setText(getResources().getString(R.string.according_material_scan));
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.ACCORDINGMATERIALCODE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_accordingmaterialscannew;
    }

    @Override
    protected void doBusiness() {

        initData();
        localdata = new ListSumBean();
        ListSumBean data = (ListSumBean) getIntent().getSerializableExtra("sumdata");
        localdata = data;
        mTv_item_name.setText(data.getItem_name());
        et_format.setText(data.getLow_order_item_spec());
        tv_under_feed.setText(StringUtils.deleteZero(data.getShortage_qty()));
        tv_stock_balance.setText(StringUtils.deleteZero(data.getStock_qty()));
        tv_actual_yield.setText(StringUtils.deleteZero(data.getScan_sumqty()));

        String code = getIntent().getExtras().getString("modilecode");

        type = data.getItem_barcode_type();

        if(type.equals(codetype)){
            etScanBarocde.setText(data.getLow_order_item_no());
        }

        commonLogic = CommonLogic.getInstance(context, module, code);
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        mRc_list.setLayoutManager(fullyLinearLayoutManager);
        getFifo();
    }

    public void getFifo(){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(AddressContants.ITEM_NO,localdata.getLow_order_item_no());
        map.put("lot_no","");
        map.put(AddressContants.WAREHOUSE_NO, LoginLogic.getUserInfo().getWare());
        map.put(AddressContants.QTY,tv_under_feed.getText().toString());
        mHandler.removeMessages(FIFOWHAT);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(FIFOWHAT,map), AddressContants.DELAYTIME);
    }

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
     * 初始化一些变量
     */
    private void initData() {
        etScanBarocde.setText("");
        etScanLocator.setText("");
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        etScanLocator.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 对比物料条码
     * @param barcodeBackBean
     */
    public void showBarcode(ScanBarcodeBackBean barcodeBackBean){
        etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
        barcodeFlag = true;
        saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
        saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
        saveBean.setItem_no(barcodeBackBean.getItem_no());
        saveBean.setUnit_no(barcodeBackBean.getUnit_no());
        saveBean.setLot_no(barcodeBackBean.getLot_no());
        saveBean.setLot_date(barcodeBackBean.getLot_date());
        saveBean.setFifo_check(barcodeBackBean.getFifo_check());
        saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
        if(StringUtils.isBlank(etScanLocator.getText().toString()) && !cbLocatorlock.isChecked()){
            etScanLocator.requestFocus();
        }else{
            etInputNum.requestFocus();
        }
        if (locatorFlag&&CommonUtils.isAutoSave(saveBean)){
            saveData();
        }
    }
}
