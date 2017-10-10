package digiwin.smartdepott100.module.fragment.sale.pickupshipment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.TextKeyListener;
import android.view.View;
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
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.core.coreutil.FiFoCheckUtils;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.sale.pickupshipment.PickUpShipmentActivity;
import digiwin.smartdepott100.module.adapter.common.CommonDocNoFifoAdapter;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.sale.pickupshipment.PickUpShipmentLogic;
import digiwin.smartdepott100.module.logic.sale.saleoutlet.SaleOutLetLogic;

import static digiwin.smartdepott100.R.id.et_barcode;
import static digiwin.smartdepott100.R.id.et_input_num;

/**
 * @author 赵浩然
 * @des 出货过账扫描页
 * @date 2017/3/23
 */
public class PickUpShipmentScanFg extends BaseFragment {

    private PickUpShipmentActivity pactivity;

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
     * FIFO
     */
    final int FIFOWHAT = 1003;


    private List<FifoCheckBean> fiFoList;

    FilterResultOrderBean localData;

    PickUpShipmentLogic logic;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    CommonDocNoFifoAdapter adapter;

    @BindView(R.id.ry_list)
    RecyclerView mRy_list;

    /**
     * 物料条码
     */
    @BindView(R.id.tv_barcode_string)
    TextView tvBarcode;
    @BindView(R.id.et_barcode)
    EditText etScanBarocde;
    @BindView(R.id.ll_barcode)
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
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindViews({R.id.et_barcode, R.id.et_scan_locator, et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode_string, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;

//
    @BindView(R.id.tv_swept_volume)
    TextView tvSweptVolume;

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isLock(boolean checked) {
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

    @OnFocusChange(R.id.et_barcode)
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

    @OnTextChanged(value = R.id.et_barcode, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
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
        String fifoCheck = FiFoCheckUtils.fifoCheck(saveBean, fiFoList);
        if (!StringUtils.isBlank(fifoCheck)) {
            showFailedDialog(fifoCheck);
            return;
        }
        showLoadingDialog();
        logic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                saveBean.setScan_sumqty(saveBackBean.getScan_sumqty());
                if (AddressContants.FIFOY.equals(saveBean.getFifo_check())) {
                    upDateList();
                }
                clear();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    public void clear() {
        upDateList();
        tvSweptVolume.setText(saveBean.getScan_sumqty());
        etInputNum.setText("");
        etScanBarocde.setText("");
        if (cbLocatorlock.isChecked()) {
            if (StringUtils.isBlank(etScanLocator.getText().toString().trim())) {
                locatorFlag = false;
            }
            barcodeFlag = false;
            etScanBarocde.requestFocus();
        } else if (!cbLocatorlock.isChecked()) {
            locatorFlag = false;
            barcodeFlag = false;
            etScanLocator.setText("");
            etScanLocator.requestFocus();
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
                    logic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            dismissLoadingDialog();
                            locatorFlag = true;
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            saveBean.setAllow_negative_stock(locatorBackBean.getAllow_negative_stock());
                            etScanBarocde.requestFocus();
                            if (barcodeFlag&&CommonUtils.isAutoSave(saveBean)) {
                                save();
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

                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.DOC_NO, localData.getDoc_no());
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO, saveBean.getStorage_spaces_out_no());
                    etScanBarocde.setKeyListener(null);
                    logic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etScanBarocde.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            dismissLoadingDialog();
                            showBarcode(barcodeBackBean);
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
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(AddressContants.DOC_NO, String.valueOf(msg.obj));
                    map.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
                    logic.getFifoInfo(map, new SaleOutLetLogic.FIFOInfoGETListener() {
                        @Override
                        public void onSuccess(List<FifoCheckBean> fiFoBeanList) {
                            if (null != fiFoBeanList && fiFoBeanList.size() > 0) {
                                fiFoList = fiFoBeanList;
                                adapter = new CommonDocNoFifoAdapter(context, fiFoBeanList);
                                mRy_list.setAdapter(adapter);
                            } else {
                                fiFoList = new ArrayList<FifoCheckBean>();
                                adapter = new CommonDocNoFifoAdapter(context, fiFoList);
                                mRy_list.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailed(String error) {
//                        showFailedDialog(error);
                        }
                    });
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);
    /**
     * 对比物料条码
     *
     * @param barcodeBackBean
     */
    public void showBarcode(ScanBarcodeBackBean barcodeBackBean) {
        etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
        barcodeFlag = true;
        saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
        saveBean.setProduct_no(barcodeBackBean.getProduct_no());
        saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
        saveBean.setItem_no(barcodeBackBean.getItem_no());
        saveBean.setUnit_no(barcodeBackBean.getUnit_no());
        saveBean.setLot_no(barcodeBackBean.getLot_no());
        saveBean.setDoc_no(localData.getDoc_no());
        saveBean.setFifo_check(barcodeBackBean.getFifo_check());
        saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
        etInputNum.requestFocus();
        tvSweptVolume.setText(barcodeBackBean.getScan_sumqty());
        if (locatorFlag&&CommonUtils.isAutoSave(saveBean)) {
            save();
        }
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_pickupshipment_scan;
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
        pactivity = (PickUpShipmentActivity) activity;
        logic = PickUpShipmentLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        mRy_list.setLayoutManager(linearLayoutManager);

        FilterResultOrderBean data = (FilterResultOrderBean) getActivity().getIntent().getSerializableExtra("data");
        localData = new FilterResultOrderBean();
        localData = data;

        upDateList();
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        etScanBarocde.setText("");
        etScanLocator.setText("");
        etScanLocator.requestFocus();
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
    }

    public void upDateList() {
        mHandler.sendMessageDelayed(mHandler.obtainMessage(FIFOWHAT, localData.getDoc_no()), AddressContants.DELAYTIME);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}

