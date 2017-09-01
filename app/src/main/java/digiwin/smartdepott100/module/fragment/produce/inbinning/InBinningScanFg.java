package digiwin.smartdepott100.module.fragment.produce.inbinning;

import android.os.Handler;
import android.os.Message;
import android.text.method.TextKeyListener;
import android.view.View;
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
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.produce.inbinning.InBinningActivity;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.stock.ProductBinningBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.InBinningLogic;
import digiwin.smartdepott100.module.logic.stock.ProductBinningLogic;


/**
 * @author 孙长权
 * @des 装箱入库--扫描
 * @date 2017/3/23
 */
public class InBinningScanFg extends BaseFragment {

    private InBinningActivity pactivity;

    SaveBean saveBean;

    /**
     * 箱码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;

    ListSumBean listSumBean;

    InBinningLogic commonLogic;
    /**
     * 箱码扫描
     */
    boolean barcodeFlag;
    /**
     * 库位扫描
     */
    boolean locatorFlag;

    /**
     * show
     */
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;

    /**
     * show布局
     */
    @BindView(R.id.rl_zx_detail)
    RelativeLayout includeDetail;

    @BindViews({R.id.et_box_code, R.id.et_scan_locator})
    List<EditText> editTexts;
    @BindViews({R.id.ll_box_code, R.id.ll_scan_locator})
    List<View> views;
    @BindViews({R.id.tv_box_code, R.id.tv_locator})
    List<TextView> textViews;

    /**
     * 箱码
     */
    @BindView(R.id.tv_box_code)
    TextView tv_box_code;
    @BindView(R.id.et_box_code)
    EditText etBoxCode;
    @BindView(R.id.ll_box_code)
    LinearLayout ll_box_code;
    /**
     * 库位
     */
    @BindView(R.id.tv_locator)
    TextView tvLocator;
    @BindView(R.id.et_scan_locator)
    EditText etScanLocator;
    @BindView(R.id.ll_scan_locator)
    LinearLayout llScanLocator;

    @BindView(R.id.cb_locatorlock)
    CheckBox cbLocatorlock;

    @OnFocusChange(R.id.et_box_code)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_box_code, views);
        ModuleUtils.etChange(activity, etBoxCode, editTexts);
        ModuleUtils.tvChange(activity, tv_box_code, textViews);
    }

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

    @OnTextChanged(value = R.id.et_box_code, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_scan_locator, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
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
            showFailedDialog(R.string.scan_box_number);
            return;
        }
        showLoadingDialog();
        commonLogic.scanBinningSave(saveBean, new InBinningLogic.SaveBinningListener() {
            @Override
            public void onSuccess() {
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

    String barcodeShow;
    /**
     * 库位展示
     */
    String locatorShow;


    public void clear() {
        etBoxCode.setText("");
        barcodeShow="";
        barcodeFlag = false;
        if (!cbLocatorlock.isChecked()) {
            locatorShow = "";
            locatorFlag = false;
            etScanLocator.setText("");
            etScanLocator.requestFocus();
        }
        show();
    }

    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT://扫描箱号
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.PACKAGE_NO, String.valueOf(msg.obj));
                    barcodeMap.put(AddressContants.DOC_NO, listSumBean.getDoc_no());
                    barcodeMap.put(AddressContants.ITEM_NO, listSumBean.getItem_no());
                    //保存箱条码和单号
                    commonLogic.scanProdut(barcodeMap, new InBinningLogic.ScanPackBoxNumberListener() {
                        @Override
                        public void onSuccess(ProductBinningBean productBinningBean) {
                            barcodeFlag = true;
                            barcodeShow=productBinningBean.getShowing();
                            saveBean.setPackage_no(productBinningBean.getPackage_no());
                            saveBean.setDoc_no(listSumBean.getDoc_no());
                            saveBean.setUnit_no(productBinningBean.getUnit_no());
                            saveBean.setAvailable_in_qty(productBinningBean.getAvailable_in_qty());
                            saveBean.setBarcode_no(productBinningBean.getBarcode_no());
                            saveBean.setItem_no(productBinningBean.getItem_no());
                            saveBean.setBarcode_qty(productBinningBean.getBarcode_qty());
                            saveBean.setScan_sumqty(productBinningBean.getScan_sumqty());
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            barcodeFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etBoxCode.setText("");
                                    etBoxCode.requestFocus();
                                }
                            });
                        }
                    });
                    break;

                case LOCATORWHAT://扫描库位
                    HashMap<String, String> locatorMap = new HashMap<>();
                    locatorMap.put(AddressContants.STORAGE_SPACES_BARCODE, String.valueOf(msg.obj));
                    commonLogic.scanLocator(locatorMap, new CommonLogic.ScanLocatorListener() {
                        @Override
                        public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                            //判断返回仓库是否与全局仓库一致
                            if (!LoginLogic.getWare().equals(locatorBackBean.getWarehouse_no())) {
                                showFailedDialog(activity.getString(R.string.wareuse_error));
                                return;
                            }
                            locatorShow = locatorBackBean.getShowing();
                            locatorFlag = true;
                            //入仓库，入仓位
                            saveBean.setStorage_spaces_in_no(locatorBackBean.getStorage_spaces_no());
                            saveBean.setWarehouse_in_no(locatorBackBean.getWarehouse_no());
                            etBoxCode.requestFocus();
                            show();
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
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_in_binning_scan;
    }

    @Override
    protected void doBusiness() {

        pactivity = (InBinningActivity) activity;
        commonLogic = InBinningLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        initData();
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        etBoxCode.setText("");
        etScanLocator.setText("");
        tvDetailShow.setText("");

        barcodeFlag = false;
        locatorFlag = false;
        barcodeShow = "";
        locatorShow = "";
        saveBean = new SaveBean();
        etScanLocator.requestFocus();
        listSumBean = (ListSumBean) getActivity().getIntent().getSerializableExtra("data");
    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(locatorShow+"\\n" + barcodeShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            includeDetail.setVisibility(View.VISIBLE);
        } else {
            includeDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
