package digiwin.smartdepott100.module.fragment.purchase.purchasereceiving;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.activity.purchase.purchasereceiving.PurchaseReceivingActivity;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 采购收货扫码页面
 * @date 2017/5/15 10:16
 */

public class PurchaseReceivingScanFg extends BaseFragment {

    @BindView(R.id.tv_purchase)
    TextView tvPurchase;
    @BindView(R.id.et_scan_purchase)
    EditText etScanPurchase;
    @BindView(R.id.ll_purchase)
    LinearLayout llPurchase;

    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;

    @BindView(R.id.tv_furnace)
    TextView tvFurnace;
    @BindView(R.id.et_scan_furnace)
    EditText etScanFurnace;
    @BindView(R.id.ll_scan_furnace)
    LinearLayout llScanFurnace;
    Unbinder unbinder;

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.includedetail)
    View includeDetail;

    @BindView(R.id.tv_scan_hasScan)
    TextView tvScanHasScan;

    @BindViews({R.id.et_scan_purchase, R.id.et_scan_barocde, R.id.et_scan_furnace, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_purchase, R.id.ll_scan_barcode, R.id.ll_scan_furnace, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_purchase, R.id.tv_barcode, R.id.tv_furnace, R.id.tv_number})
    List<TextView> textViews;


    @OnFocusChange(R.id.et_scan_purchase)
    void purchaseFocusChanage() {
        ModuleUtils.viewChange(llPurchase, views);
        ModuleUtils.etChange(activity, etScanPurchase, editTexts);
        ModuleUtils.tvChange(activity, tvPurchase, textViews);
    }

    @OnFocusChange(R.id.et_scan_barocde)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_scan_furnace)
    void furnaceFocusChanage() {
        ModuleUtils.viewChange(llScanFurnace, views);
        ModuleUtils.etChange(activity, etScanFurnace, editTexts);
        ModuleUtils.tvChange(activity, tvFurnace, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = R.id.et_scan_purchase, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void purchaseChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PURCAHSEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PURCAHSEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_furnace, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(FURNACEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(FURNACEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.save)
    void save() {
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_barcode);
            return;
        }
        if (StringUtils.isBlank(etInputNum.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        showLoadingDialog();
        saveBean.setQty(etInputNum.getText().toString());
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                tvScanHasScan.setText(saveBackBean.getScan_sumqty());
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
     * 采购单号
     */
    final int PURCAHSEWHAT = 1000;
    /**
     * 物料条码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 炉号
     */
    final int FURNACEWHAT = 1002;

    PurchaseReceivingActivity pactivity;


    CommonLogic commonLogic;
    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 库位展示
     */
    String locatorShow;
    /**
     * 采购单扫描
     */
    boolean purchaseflag;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;

    SaveBean saveBean;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    commonLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            barcodeShow = barcodeBackBean.getShowing();
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            tvScanHasScan.setText(barcodeBackBean.getScan_sumqty());
                            barcodeFlag = true;
                            show();
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            etInputNum.requestFocus();
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
                case FURNACEWHAT:
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            locatorShow = locatorBackBean.getShowing();
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            etScanBarocde.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanFurnace.setText("");
                                }
                            });
                        }
                    });
                    break;
            }
            return false;
        }
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_purchasereceiving_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PurchaseReceivingActivity) activity;
        initData();
    }


    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(barcodeShow + "\\n" + locatorShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            includeDetail.setVisibility(View.VISIBLE);
        } else {
            includeDetail.setVisibility(View.GONE);
        }
    }


    /**
     * 保存完成之后的操作
     */
    private void clear() {
        etInputNum.setText("");
        barcodeFlag = false;
        etScanBarocde.setText("");
        barcodeShow = "";
        etScanBarocde.requestFocus();
        etScanFurnace.setText("");
        locatorShow = "";
        show();
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        barcodeShow = "";
        locatorShow = "";
        barcodeFlag = false;
        saveBean = new SaveBean();
        etScanBarocde.setText("");
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
    }


}
