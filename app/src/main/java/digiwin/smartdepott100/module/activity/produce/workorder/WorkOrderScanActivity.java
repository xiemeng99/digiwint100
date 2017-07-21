package digiwin.smartdepott100.module.activity.produce.workorder;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
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
import digiwin.smartdepott100.module.logic.produce.WorkOrderlLogic;

import static digiwin.smartdepott100.R.id.et_input_num;
import static digiwin.smartdepott100.R.id.et_scan_barocde;


/**
 * @author songjie
 * @module 依工单发料扫描
 * @date 2017/3/2
 */

public class WorkOrderScanActivity extends BaseTitleActivity {

    WorkOrderScanActivity activity;

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
     * 工单单号
     */
    final int FIFOWHAT = 1003;

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;


    @BindView(R.id.ry_list)
    RecyclerView mRcList;


    @BindView(R.id.tv_scaned_numb)
    TextView tvScanedNumb;


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

    @BindViews({R.id.et_tray,R.id.et_scan_barocde, R.id.et_scan_locator, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_tray,R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_tray,R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;



    CommonItemNoFiFoAdapter adapter;

    SaveBean saveBean;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    /**
     * 返回汇总页面使用
     */
    public final int SCANCODE = 0123;

    WorkOrderlLogic commonLogic;

    List<FifoCheckBean> localFifoList;

    ListSumBean localData;

    /**
     * 提交按钮
     */
    @BindView(R.id.save)
    Button mBtn_save;

    /**
     * 物料类型
     */
    String type = null;

    /**
     * 条码类型 料号类型
     */
    private String codetype = "1";
    /**
     * 工单号
     */
    private String work_no;

    /**
     * 已扫量
     */
    private String scan_sumqty = "0";

    CustomDialog dialog;

    @OnClick(R.id.iv_title_setting)
    void showDialog() {
        final ListSumBean data = (ListSumBean) getIntent().getSerializableExtra("sumdata");
        String match_num="0";
            if(scan_sumqty.equals("0")){
                match_num=data.getScan_sumqty();
            }else {
                match_num = scan_sumqty;
            }
            CustomDialog.Builder builder = new CustomDialog.Builder(context)
                    .view(R.layout.dialog_workorder)
                    .style(R.style.CustomDialog)
                    .widthpx((int) (ViewUtils.getScreenWidth(context) * 0.95))
                    .heightpx(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .addViewOnclick(R.id.close, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });
            final float numb1 = StringUtils.string2Float(data.getApply_qty());
            final float numb2 = StringUtils.string2Float(data.getStock_qty());
            final float numb3 = StringUtils.string2Float(match_num);
            if (numb3 == 0) {
                builder.setViewTextColor(R.id.tv_item_name, data.getLow_order_item_name(), context.getResources().getColor(R.color.Base_color)); //品名
                builder.setViewTextColor(R.id.tv_item_format, data.getLow_order_item_spec(), context.getResources().getColor(R.color.Base_color));//规格
                builder.setViewTextColor(R.id.tv_item_no, data.getLow_order_item_no(), context.getResources().getColor(R.color.Base_color));//料号
                builder.setViewTextColor(R.id.tv_apply_number, data.getApply_qty(), context.getResources().getColor(R.color.Base_color)); //申请量
                builder.setViewTextColor(R.id.tv_match_number, data.getStock_qty(), context.getResources().getColor(R.color.Base_color)); //库存量
                builder.setViewTextColor(R.id.tv_locator_num, StringUtils.deleteZero(match_num), context.getResources().getColor(R.color.Base_color));//匹配量
            } else if (numb1 > numb3) {
                builder.setViewTextColor(R.id.tv_item_name, data.getLow_order_item_name(), context.getResources().getColor(R.color.textfous_yellow));
                builder.setViewTextColor(R.id.tv_item_format, data.getLow_order_item_spec(), context.getResources().getColor(R.color.textfous_yellow));
                builder.setViewTextColor(R.id.tv_item_no, data.getLow_order_item_no(), context.getResources().getColor(R.color.textfous_yellow));
                builder.setViewTextColor(R.id.tv_apply_number, data.getApply_qty(), context.getResources().getColor(R.color.textfous_yellow));
                builder.setViewTextColor(R.id.tv_match_number, data.getStock_qty(), context.getResources().getColor(R.color.textfous_yellow));
                builder.setViewTextColor(R.id.tv_locator_num, StringUtils.deleteZero(match_num), context.getResources().getColor(R.color.textfous_yellow));
            } else if (numb1 == numb3) {
                builder.setViewTextColor(R.id.tv_item_name, data.getLow_order_item_name(), context.getResources().getColor(R.color.green1b));
                builder.setViewTextColor(R.id.tv_item_format, data.getLow_order_item_spec(), context.getResources().getColor(R.color.green1b));
                builder.setViewTextColor(R.id.tv_item_no, data.getLow_order_item_no(), context.getResources().getColor(R.color.green1b));
                builder.setViewTextColor(R.id.tv_apply_number, data.getApply_qty(), context.getResources().getColor(R.color.green1b));
                builder.setViewTextColor(R.id.tv_match_number, data.getStock_qty(), context.getResources().getColor(R.color.green1b));
                builder.setViewTextColor(R.id.tv_locator_num, StringUtils.deleteZero(match_num), context.getResources().getColor(R.color.green1b));
            }
            dialog = builder.build();
            dialog.show();
        }


    @OnClick(R.id.save)
    void saveData() {
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
        saveBean.setQty(etInputNum.getText().toString());
        saveBean.setDoc_no(work_no);

        String fifoCheck = FiFoCheckUtils.fifoCheck(saveBean, localFifoList);
        if (!StringUtils.isBlank(fifoCheck)) {
            showFailedDialog(fifoCheck);
            return;
        }
        showLoadingDialog();
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                scan_sumqty = saveBackBean.getScan_sumqty();
                tvScanedNumb.setText(scan_sumqty);
                if (null != localFifoList) {
                    if (localFifoList.size() > 0 && AddressContants.FIFOY.equals(saveBean.getFifo_check())) {
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

    @OnFocusChange(R.id.et_scan_locator)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(llScanLocator, views);
        ModuleUtils.etChange(activity, etScanLocator, editTexts);
        ModuleUtils.tvChange(activity, tvLocator, textViews);
    }

    @OnFocusChange(et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(LOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
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
                            locatorFlag = true;
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            saveBean.setAllow_negative_stock(locatorBackBean.getAllow_negative_stock());
                            if (StringUtils.isBlank(etScanBarocde.getText().toString().trim())) {
                                etScanBarocde.requestFocus();
                            } else {
                                etInputNum.requestFocus();
                            }
                            if (CommonUtils.isAutoSave(saveBean)){
                                saveData();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
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
                    barcodeMap.put(AddressContants.DOC_NO, work_no);
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
                                showBarcode(barcodeBackBean);
                            } catch (Exception e) {
                                barcodeFlag = false;
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
                    HashMap<String, String> map = (HashMap<String, String>) msg.obj;
                    commonLogic.getFifo(map, new CommonLogic.FIFOGETListener() {
                        @Override
                        public void onSuccess(List<FifoCheckBean> fiFoBeanList) {
                            localFifoList = new ArrayList<FifoCheckBean>();
                            localFifoList = fiFoBeanList;
                            adapter = new CommonItemNoFiFoAdapter(activity, fiFoBeanList);
                            mRcList.setAdapter(adapter);
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
    protected int bindLayoutId() {
        return R.layout.activity_workorderscan;
    }

    @Override
    protected void doBusiness() {
        initData();
        localData = new ListSumBean();
        ListSumBean data = (ListSumBean) getIntent().getSerializableExtra("sumdata");
        localData = data;
        work_no = getIntent().getExtras().getString("work_no");
        type = data.getItem_barcode_type();
        saveBean = new SaveBean();
        if (type.equals(codetype)) {
            etScanBarocde.setText(data.getLow_order_item_no());
            etScanLocator.requestFocus();
        }

        commonLogic = WorkOrderlLogic.getInstance(context, module, mTimestamp.toString());
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        mRcList.setLayoutManager(fullyLinearLayoutManager);
        getFifo();
    }

    public void getFifo() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(AddressContants.QTY, localData.getApply_qty());
        LogUtils.d("QTY", String.valueOf(StringUtils.sub(localData.getApply_qty(), localData.getScan_sumqty())));
        map.put(AddressContants.ITEM_NO, localData.getLow_order_item_no());
        map.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
        map.put("lot_no", "");
        mHandler.removeMessages(FIFOWHAT);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(FIFOWHAT, map), AddressContants.DELAYTIME);
    }

    public void clearData(String type) {
        if (type.equals(codetype)) {
            barcodeFlag = true;
            etInputNum.setText("");
            etInputNum.requestFocus();
        } else {
            barcodeFlag = false;
            etInputNum.setText("");
            etScanBarocde.setText("");
            etScanBarocde.requestFocus();
        }
    }

    /**
     * 对比物料条码
     *
     * @param barcodeBackBean
     */
    public void showBarcode(ScanBarcodeBackBean barcodeBackBean) {
        etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
        barcodeFlag = true;

        saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
        saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
        saveBean.setItem_no(barcodeBackBean.getItem_no());
        saveBean.setUnit_no(barcodeBackBean.getUnit_no());
        saveBean.setLot_no(barcodeBackBean.getLot_no());
        saveBean.setFifo_check(barcodeBackBean.getFifo_check());
        saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
        if (CommonUtils.isAutoSave(saveBean)){
            saveData();
        }

    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        scan_sumqty="0";
        etScanBarocde.setText("");
        etScanLocator.setText("");
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        etScanLocator.requestFocus();
        if (CommonUtils.isUseTray()){
            llTray.setVisibility(View.VISIBLE);
            lineTray.setVisibility(View.VISIBLE);
        }else {
            llTray.setVisibility(View.GONE);
            lineTray.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.dankeliao);
        activity = this;
        mName.setText(getResources().getString(R.string.title_work_order_scan));
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.WORKORDERCODE;
        return module;
    }

}
