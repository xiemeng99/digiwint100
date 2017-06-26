package digiwin.smartdepott100.module.fragment.produce.suitpickinghalf;

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
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.core.coreutil.FiFoCheckUtils;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.produce.suitpickinghalf.SuitPickingHalfActivity;
import digiwin.smartdepott100.module.adapter.common.CommonDocNoFifoAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.SuitPickingHalfLogic;

/**
 * @author xiemeng
 * @des 生产成套领料扫描页面
 * @date 2017/5/28 16:25
 */

public class SuitPickingHalfScanFg extends BaseFragment {
    @BindViews({R.id.et_scan_locator, R.id.et_scan_barocde, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_scan_locator, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_locator, R.id.tv_number})
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
            if (!pickingFlag) {
                showFailedDialog(R.string.scan_picking_no, new OnDialogClickListener() {
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
            if (!pickingFlag) {
                showFailedDialog(R.string.scan_picking_no, new OnDialogClickListener() {
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
        if (!pickingFlag) {
            showFailedDialog(R.string.scan_picking_no);
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
        suitPickingHalfLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
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
     * fifo
     */
    final int PICKINGWHAT = 1000;
    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;

    SuitPickingHalfActivity pactivity;

    SuitPickingHalfLogic suitPickingHalfLogic;
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
    boolean pickingFlag;

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
    String issuingNo;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PICKINGWHAT:
                    //TODO:fifo待完善
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(AddressContants.DOC_NO, String.valueOf(msg.obj));
                    map.put(AddressContants.WAREHOUSE_NO, ware);
                    ClickItemPutBean itemPutBean = new ClickItemPutBean();
                    itemPutBean.setNotice_no(issuingNo);
                    itemPutBean.setWarehouse_no(ware);
                    EventBus.getDefault().post(itemPutBean);
                    suitPickingHalfLogic.docNoFIFO(map, new CommonLogic.PostMaterialFIFOListener() {
                        @Override
                        public void onSuccess(List<FifoCheckBean> fiFoBeanList) {
                            fiFoList.clear();
                            fiFoList = fiFoBeanList;
                            pickingFlag = true;
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
                            pickingFlag = false;
                            fiFoList.clear();
                            adapter = new CommonDocNoFifoAdapter(pactivity, fiFoList);
                            ryList.setAdapter(adapter);
                        }
                    });
                    break;
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.DOC_NO, issuingNo);
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, ware);
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO,saveBean.getStorage_spaces_out_no());
                    suitPickingHalfLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            barcodeFlag = true;
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setDoc_no(issuingNo);
                            saveBean.setFifo_check(barcodeBackBean.getFifo_check());
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            etInputNum.requestFocus();
                            if (CommonUtils.isAutoSave(saveBean)){
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
                    suitPickingHalfLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            locatorFlag = true;
                            saveBean.setStorage_spaces_out_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_out_no(locatorBackBean.getWarehouse_no());
                            saveBean.setAllow_negative_stock(locatorBackBean.getAllow_negative_stock());
                            etScanBarocde.requestFocus();
                            if (CommonUtils.isAutoSave(saveBean)){
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
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
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_suitpickinghalf_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (SuitPickingHalfActivity) activity;
        initData();
        getFIFo();
    }

    /**
     * 获取 fifo
     */
    public void getFIFo() {
        try {
            FilterResultOrderBean filterBean = (FilterResultOrderBean) pactivity.getIntent().getSerializableExtra(pactivity.filterBean);
            issuingNo = filterBean.getIssuing_no();
            mHandler.sendMessage(mHandler.obtainMessage(PICKINGWHAT, issuingNo));
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
        pickingFlag = false;
        barcodeFlag = false;
        locatorFlag = false;
        saveBean = new SaveBean();
        fiFoList = new ArrayList<>();
        ware = LoginLogic.getWare();
        issuingNo = "";
        suitPickingHalfLogic = SuitPickingHalfLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
    }

}
