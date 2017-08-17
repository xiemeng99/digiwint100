package digiwin.smartdepott100.module.fragment.stock.stockcheck;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.activity.stock.storecheck.ChooseCheckTypePop;
import digiwin.smartdepott100.module.activity.stock.storecheck.StockCheckActivity;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.stock.StockCheckLogic;

/**
 * @author xiemeng
 * @des
 * @date 2017/8/12 10:49
 */

public class StockCheckScanFg extends BaseFragment {


    @BindViews({R.id.ll_check_type, R.id.ll_scan_locator, R.id.ll_barcode_sp, R.id.ll_chcek_number})
    List<View> views;
    @BindViews({R.id.tv_check_type, R.id.tv_scan_locator, R.id.tv_barcode_sp, R.id.tv_chcek_number})
    List<TextView> textViews;
    @BindViews({R.id.et_check_type, R.id.et_scan_locator, R.id.et_barcode_sp, R.id.et_chcek_number})
    List<EditText> editTexts;

    @BindView(R.id.tv_check_type)
    TextView tvCheckType;
    @BindView(R.id.et_check_type)
    public EditText etCheckType;
    @BindView(R.id.ll_check_type)
    LinearLayout llCheckType;

    @BindView(R.id.ll_scan_locator)
    LinearLayout llScanLocator;
    @BindView(R.id.tv_scan_locator)
    TextView tvScanLocator;
    @BindView(R.id.et_scan_locator)
    EditText etScanLocator;


    @OnFocusChange(R.id.et_check_type)
    void checkTypeFocusChanage() {
        ModuleUtils.viewChange(llCheckType, views);
        ModuleUtils.tvChange(activity, tvCheckType, textViews);
        ModuleUtils.etChange(activity, etCheckType, editTexts);
    }

    @OnFocusChange(R.id.et_scan_locator)
    void scanLocatorFocusChanage() {
        ModuleUtils.viewChange(llScanLocator, views);
        ModuleUtils.tvChange(activity, tvScanLocator, textViews);
        ModuleUtils.etChange(activity, etScanLocator, editTexts);
    }

    /**
     * 库位锁定
     */
    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;

    @OnCheckedChanged(R.id.cb_locatorlock)
    void isCardNumberLock(boolean checked) {
        if (checked) {
            etScanLocator.setKeyListener(null);
        } else {
            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    /**
     * 盘点量
     */
    @BindView(R.id.ll_chcek_number)
    LinearLayout llChcekNumber;
    @BindView(R.id.tv_chcek_number)
    TextView tvChcekNumber;
    @BindView(R.id.et_chcek_number)
    EditText etChcekNumber;

    @OnFocusChange(R.id.et_chcek_number)
    void checkNumberFocusChanage() {
        ModuleUtils.viewChange(llChcekNumber, views);
        ModuleUtils.tvChange(activity, tvChcekNumber, textViews);
        ModuleUtils.etChange(activity, etChcekNumber, editTexts);
    }

    /**
     * 物料
     */
    @BindView(R.id.ll_barcode_sp)
    LinearLayout llBarcodeSp;
    @BindView(R.id.tv_barcode_sp)
    TextView tvBarcodeSp;
    @BindView(R.id.et_barcode_sp)
    EditText etBarcodeSp;

    @OnFocusChange(R.id.et_barcode_sp)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llBarcodeSp, views);
        ModuleUtils.tvChange(activity, tvBarcodeSp, textViews);
        ModuleUtils.etChange(activity, etBarcodeSp, editTexts);
    }

    //show界面
    @BindView(R.id.includedetail)
    View rlZxDetail;
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    /**
     * 上个界面传递过来的
     */
    FilterResultOrderBean data;

    SaveBean saveBean;

    StockCheckLogic stockCheckLogic;


    private final int LOCATORWHAT = 1000;

    private final int BARCODEWHAT = 1001;

    private List<String> chooseList;

    public  String checkType;

    @OnClick(R.id.et_check_type)
    void chooseCheckType() {
        ChooseCheckTypePop.showPop(context, chooseList, etBarcodeSp, new ChooseCheckTypePop.GetChooseListener() {
            @Override
            public void getChoose(String choose, int position) {
                etCheckType.setText(choose);
                checkType=String.valueOf(position+1);
            }
        });
    }

    //库位扫描
    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(LOCATORWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(LOCATORWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    //物料
    @OnTextChanged(value = R.id.et_barcode_sp, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    //盘点量
    @OnTextChanged(value = R.id.et_chcek_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void checkNumberChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            saveBean.setQty(s.toString().trim());//赋值数量
        }
    }

    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT://物料
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.DOC_NO, data.getDoc_no());
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.WAREHOUSE_NO, saveBean.getWarehouse_in_no());
                    barcodeMap.put(AddressContants.STORAGE_SPACES_NO, saveBean.getStorage_spaces_in_no());
                    etBarcodeSp.setKeyListener(null);
                    stockCheckLogic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            etBarcodeSp.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeShow = barcodeBackBean.getShowing();
                            barcodeFlag = true;
                            show();
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());//料号
                            saveBean.setItem_no(barcodeBackBean.getItem_no());//料号
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setLot_no(barcodeBackBean.getLot_no());
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            //赋值累积量
                            etChcekNumber.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            etBarcodeSp.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            barcodeFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etBarcodeSp.setText("");
                                    etBarcodeSp.requestFocus();
                                }
                            });

                        }
                    });
                    break;
                case LOCATORWHAT://库位
                    saveBean.setDoc_no(data.getDoc_no());
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.DOC_NO, data.getDoc_no());
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, msg.obj.toString());
                    etScanLocator.setKeyListener(null);
                    stockCheckLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            locatorShow = locatorBackBean.getShowing();
                            locatorFlag = true;
                            show();
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());//库位
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());//仓库
                            etBarcodeSp.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            etScanLocator.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            locatorFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanLocator.setText("");
                                    locatorFlag = false;
                                }
                            });

                        }
                    });
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

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
        if (StringUtils.isBlank(etChcekNumber.getText().toString())) {
            showFailedDialog(R.string.check_input_num);
            return;
        }
        showLoadingDialog();
        Map<String, String> map = ObjectAndMapUtils.getValueMap(saveBean);
        stockCheckLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                dismissLoadingDialog();
                initData();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }


    @Override
    protected int bindLayoutId() {
        return R.layout.fg_stockcheck_scan;
    }

    @Override
    protected void doBusiness() {
        barcodeFlag=false;
        locatorFlag=false;
        locatorShow="";
        barcodeShow="";
        chooseList = new ArrayList<>();
        chooseList.add(getString(R.string.first_check));
        chooseList.add(getString(R.string.second_check));
        etCheckType.setText(R.string.first_check);
        checkType="1";
        etCheckType.setKeyListener(null);
        data = (FilterResultOrderBean) activity.getIntent().getExtras().get("data");
        saveBean = new SaveBean();
        pactivity = (StockCheckActivity) activity;
        stockCheckLogic = StockCheckLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());

    }

    private boolean locatorFlag;
    private String locatorShow;

    private boolean barcodeFlag;
    private String barcodeShow;
    private StockCheckActivity pactivity;

    /**
     * 保存完成之后的操作
     */
    private void initData() {
        //库位
        if (!cbLocatorlock.isChecked()) {
            locatorFlag = false;
            etScanLocator.setText("");
            locatorShow = "";
            etScanLocator.requestFocus();
        } else {
            etBarcodeSp.requestFocus();
        }
        //重置物料
        etBarcodeSp.setText("");
        barcodeFlag = false;
        barcodeShow = "";
        etChcekNumber.setText("");
        //重置show内容
        show();
    }

    /**
     * 公共区域展示
     */
    private void show() {
        etCheckType.setEnabled(false);
        tvDetailShow.setText(StringUtils.lineChange(locatorShow + "\\n" + barcodeShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            rlZxDetail.setVisibility(View.VISIBLE);
        } else {
            rlZxDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

}
