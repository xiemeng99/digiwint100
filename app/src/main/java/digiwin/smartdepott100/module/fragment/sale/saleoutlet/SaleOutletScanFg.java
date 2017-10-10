package digiwin.smartdepott100.module.fragment.sale.saleoutlet;

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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.core.coreutil.FiFoCheckUtils;
import digiwin.smartdepott100.module.adapter.common.CommonDocNoFifoAdapter;
import digiwin.smartdepott100.module.logic.sale.saleoutlet.SaleOutLetLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.sale.saleoutlet.SaleOutletActivity;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @author xiemeng
 * @des 销售出库扫描页
 * @date 2017/3/16
 */
public class SaleOutletScanFg extends BaseFragment {

    @BindViews({R.id.et_tray,R.id.et_scan_locator, R.id.et_scan_barocde, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_tray,R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_tray,R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
    List<TextView> textViews;

    @BindView(R.id.tv_locator)
    TextView tvLocator;
    @BindView(R.id.et_scan_locator)
    EditText etScanLocator;
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;
    @BindView(R.id.ll_scan_locator)
    LinearLayout llScanLocator;
    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;
    @BindView(R.id.ry_list)
    RecyclerView ryList;


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

    /**
     * 出通单号
     */
    @BindView(R.id.tv_general_number)
    TextView tvGeneralNumber;

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

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
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
            if (!saleFlag) {
                showFailedDialog(R.string.scan_sale, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        etScanBarocde.setText("");
                    }
                });
                return;
            }
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            if (!saleFlag) {
                showFailedDialog(R.string.scan_sale, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        etScanLocator.setText("");
                    }
                });
                return;
            }
            mHandler.removeMessages(LOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.save)
    void save() {
        if (!saleFlag) {
            showFailedDialog(R.string.scan_sale);
            return;
        }
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
     * 出通单号
     */
    final int FIFOWHAT = 1000;
    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;

    SaleOutletActivity pactivity;

    SaleOutLetLogic logic;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;
    /**
     * fiFoList
     */
    boolean saleFlag;

    SaveBean saveBean;

    private List<FifoCheckBean> fiFoList;

    private BaseRecyclerAdapter adapter;
    /**
     * 仓库
     */
    String ware = "";
    /**
     * 出通单号
     */
    String doc_no;
    /**
     * 日期
     */
    String date;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case FIFOWHAT:
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(AddressContants.DOC_NO, String.valueOf(msg.obj));
                    map.put(AddressContants.WAREHOUSE_NO, ware);
                    ClickItemPutBean itemPutBean = new ClickItemPutBean();
                    itemPutBean.setDoc_no(doc_no);
                    itemPutBean.setWarehouse_no(ware);
                    EventBus.getDefault().post(itemPutBean);
                    logic.getFifoInfo(map, new SaleOutLetLogic.FIFOInfoGETListener() {
                        @Override
                        public void onSuccess(List<FifoCheckBean> fiFoBeanList) {
                            fiFoList.clear();
                            fiFoList = fiFoBeanList;
                            saleFlag = true;
                            adapter = new CommonDocNoFifoAdapter(pactivity, fiFoList);
                            ryList.setAdapter(adapter);
                            if (!cbLocatorlock.isChecked()) {
                                etScanLocator.requestFocus();
                            } else {
                                etScanBarocde.requestFocus();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            saleFlag = false;
                            fiFoList.clear();
                            adapter = new CommonDocNoFifoAdapter(pactivity, fiFoList);
                            ryList.setAdapter(adapter);
                        }
                    });
                    break;
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.DOC_NO, doc_no);
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, ware);
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_out_no());
                    etScanBarocde.setKeyListener(null);
                    logic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etScanBarocde.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            barcodeFlag = true;
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setProduct_no(barcodeBackBean.getProduct_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setDoc_no(doc_no);
                            saveBean.setFifo_check(barcodeBackBean.getFifo_check());
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            etInputNum.requestFocus();
                            if (locatorFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
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
                case LOCATORWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    etScanLocator.setKeyListener(null);
                    logic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            locatorFlag = true;
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            saveBean.setAllow_negative_stock(locatorBackBean.getAllow_negative_stock());
                            etScanBarocde.requestFocus();
                            if (barcodeFlag&&CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
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
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_saleoutlet_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (SaleOutletActivity) activity;
        if (CommonUtils.isUseTray()){
            llTray.setVisibility(View.VISIBLE);
            lineTray.setVisibility(View.VISIBLE);
        }else {
            llTray.setVisibility(View.GONE);
            lineTray.setVisibility(View.GONE);
        }
        initData();
        getFIFo();
    }

    /**
     * 获取 fifo
     */
    public void getFIFo() {
        try {
            FilterResultOrderBean filterBean = (FilterResultOrderBean) pactivity.getIntent().getSerializableExtra(pactivity.filterBean);
            doc_no = filterBean.getDoc_no();
            tvGeneralNumber.setText(doc_no);
            date = filterBean.getCreate_date();
            mHandler.sendMessage(mHandler.obtainMessage(FIFOWHAT, doc_no));
        } catch (Exception e) {
            LogUtils.e(TAG, "fifo获取" + e);
        }
    }


    /**
     * 保存完成之后的操作
     */
    private void clear() {
        etInputNum.setText("");
        barcodeFlag = false;
        etScanBarocde.setText("");
        etScanBarocde.requestFocus();
        if (!cbLocatorlock.isChecked()) {
            locatorFlag = false;
            etScanLocator.setText("");
            etScanLocator.requestFocus();
        }
        getFIFo();
    }


    /**
     * 初始化一些变量
     */
    public void initData() {
        saleFlag = true;
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        fiFoList = new ArrayList<>();
        doc_no = "";
        ware = "";
        date = "";
        logic = SaleOutLetLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null != accoutBean) {
            ware = accoutBean.getWare();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}

