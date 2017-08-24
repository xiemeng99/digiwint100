package digiwin.smartdepott100.module.activity.produce.distribute;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.common.CommonItemNoFiFoAdapter;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.produce.DistributeOrderHeadData;
import digiwin.smartdepott100.module.bean.produce.DistributeSumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * 生产配料 扫描界面
 * @author 唐孟宇
 */
public class DistributeScanActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 品名
     */
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    /**
     * 单位
     */
    @BindView(R.id.tv_product_danwei)
    TextView tv_product_danwei;
    /**
     * 规格
     */
    @BindView(R.id.tv_item_model)
    TextView tv_item_model;
    /**
     * 库存
     */
    @BindView(R.id.tv_locator_num)
    TextView tv_locator_num;
    /**
     * 欠料
     */
    @BindView(R.id.tv_left_material_num)
    TextView tv_left_material_num;
    /**
     * 已扫
     */
    @BindView(R.id.tv_scanned_num)
    TextView tv_scanned_num;

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
     * 库位
     */
    @BindView(R.id.tv_locator)
    TextView tvLocator;
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
    @BindView(R.id.ll_scan_locator)
    LinearLayout ll_scan_locator;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;

    @BindView(R.id.iv_title_setting)
    ImageView iv_title_setting;

    @BindViews({R.id.et_scan_barocde, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;

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
    /**
     * 从汇总界面带入的单身数据
     */
    DistributeSumShowBean sumshoubean;
    /**
     * 从汇总界面带入的单头数据
     */
    DistributeOrderHeadData headData;

    CommonItemNoFiFoAdapter adapter;

    List<FifoCheckBean> fiFoList = new ArrayList<FifoCheckBean>();

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    CommonLogic commonLogic;

    DistributeScanActivity pactivity;

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
        if (checked) {
            et_scan_locator.setKeyListener(null);
        } else {
            et_scan_locator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_scan_barcode, views);
        ModuleUtils.etChange(activity, et_scan_barocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_locator)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(ll_scan_locator, views);
        ModuleUtils.etChange(activity, et_scan_locator, editTexts);
        ModuleUtils.tvChange(activity, tvLocator, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, et_input_num, editTexts);
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
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_out_no());
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            //管控建议
                            if(sumshoubean.getFifo_check().equals(AddressContants.FIFOY)){
                                if(null != fiFoList && fiFoList.size()>0){
                                    int n = 0;
                                    for (int i = 0;i<fiFoList.size();i++){
                                        if(barcodeBackBean.getBarcode_no().equals(fiFoList.get(i).getBarcode_no())){
                                            if(locatorFlag){
                                                if(saveBean.getStorage_spaces_out_no().equals(fiFoList.get(i).getStorage_spaces_no())){
                                                    n++;
                                                }
                                            }
                                        }
                                    }
                                    if(n ==0){
                                        showFailedDialog(pactivity.getResources().getString(R.string.barcode_not_in_fifo));
                                        return;
                                    }
                                }
                            }
                            barcodeShow = barcodeBackBean.getShowing();
                            if(StringUtils.isBlank(et_scan_locator.getText().toString())){
                                et_scan_locator.requestFocus();
                            }
                            else if(!StringUtils.isBlank(barcodeBackBean.getBarcode_qty())){
                                et_input_num.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            }
                            barcodeFlag = true;
//                            show();
                            saveBean.setQty(barcodeBackBean.getBarcode_qty());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
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
                                    et_scan_barocde.setText("");
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
                            //管控建议
                            if(null != fiFoList && fiFoList.size()>0){
                                int n = 0;
                                for (int i = 0;i<fiFoList.size();i++){
                                    if(locatorBackBean.getStorage_spaces_no().equals(fiFoList.get(i).getStorage_spaces_no())){
                                        n++;
                                    }
                                }
                                if(n ==0){
                                    showFailedDialog(pactivity.getResources().getString(R.string.locator_not_in_fifo));
                                    return;
                                }
                            }
                            locatorShow = locatorBackBean.getShowing();
                            locatorFlag = true;
//                            show();
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            if (StringUtils.isBlank(et_scan_barocde.getText().toString())){
                                et_scan_barocde.requestFocus();
                            }else {
                                et_input_num.requestFocus();
                            }
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

    private void getFIFO(DistributeSumShowBean sumshoubean ){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(AddressContants.ITEM_NO,sumshoubean.getItem_no());
        map.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
        //欠料量
        float num1 = StringUtils.string2Float(sumshoubean.getShortage_qty());
        //库存量
        float num2 = StringUtils.string2Float(sumshoubean.getStock_qty());
        float num = (num1 - num2)>0 ? num2 : num1;
        num -= StringUtils.string2Float(sumshoubean.getScan_sumqty());
        map.put("qty",String.valueOf(num));
        commonLogic.getFifo(map, new CommonLogic.FIFOGETListener() {
            @Override
            public void onSuccess(List<FifoCheckBean> fiFoBeanList ) {
                if(null != fiFoBeanList && fiFoBeanList.size()>0)
                fiFoList = new ArrayList<FifoCheckBean>();
                fiFoList = fiFoBeanList;
                adapter = new CommonItemNoFiFoAdapter(pactivity,fiFoBeanList);
                ryList.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
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

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.DISTRIBUTE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_distribute_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (DistributeScanActivity) activity;
        commonLogic = CommonLogic.getInstance(pactivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.distribute);
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
        if (StringUtils.isBlank(et_input_num.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        showLoadingDialog();
        initSaveBean();
        saveBean.setQty(et_input_num.getText().toString().trim());
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {

            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                tv_scanned_num.setText(saveBackBean.getScan_sumqty());
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
     * 保存完成之后的操作
     */
    private void clear() {
        //保存成功，重新获取FIFO
        getFIFO(sumshoubean);
        //如果条码类型为1，不清空扫码框
        if(!sumshoubean.getItem_barcode_type().equals("1")){
            et_scan_barocde.setText("");
            if (cb_locatorlock.isChecked()){
                barcodeFlag = false;
                et_scan_barocde.requestFocus();
            }else {
                et_scan_locator.setText("");
                locatorFlag = false;
                et_scan_locator.requestFocus();
            }
        }else{
            if (cb_locatorlock.isChecked()){
                et_input_num.requestFocus();
            }else {
                et_scan_locator.setText("");
                locatorFlag = false;
                et_scan_locator.requestFocus();
            }
        }
        et_input_num.setText("");
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        et_scan_barocde.setText("");
        et_scan_locator.setText("");
        barcodeShow = "";
        locatorShow = "";
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        sumshoubean = (DistributeSumShowBean) getIntent().getExtras().getSerializable("bean");
        headData = (DistributeOrderHeadData) getIntent().getExtras().getSerializable("headData");
        //物料条码类型为1，则进入界面时显示在扫描框
        if(sumshoubean.getItem_barcode_type().equals("1")){
            et_scan_barocde.setText(sumshoubean.getItem_no());
        }
        tv_product_name.setText(sumshoubean.getItem_name());
        tv_item_model.setText(sumshoubean.getItem_spec());
        tv_locator_num.setText(StringUtils.deleteZero(sumshoubean.getStock_qty()));
        tv_left_material_num.setText(StringUtils.deleteZero(sumshoubean.getShortage_qty()));
        tv_scanned_num.setText(StringUtils.deleteZero(sumshoubean.getScan_sumqty()));
        //获取FIFO
        getFIFO(sumshoubean);
        et_scan_locator.requestFocus();
    }

    /**
     * 初始化一些从清单页面带过来的要保存的数据
     */
    private void initSaveBean() {
        saveBean.setUnit_no(sumshoubean.getUnit_no());
        saveBean.setAvailable_in_qty(StringUtils.getMinQty(sumshoubean.getShortage_qty(),sumshoubean.getStock_qty()));
        //单头信息
        saveBean.setWarehouse_out_no(headData.getWareout());
        saveBean.setWarehouse_in_no(headData.getWarein());
        saveBean.setStorage_spaces_in_no(headData.getWarein());
        saveBean.setWorkgroup_no(headData.getWorkgroup_no());
        saveBean.setDepartment_no(headData.getDepartment_no());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
