package digiwin.smartdepott100.module.fragment.stock.productbinning;

import android.os.Handler;
import android.os.Message;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.stock.productbinning.ProductBinningActivity;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.stock.ProductBinningBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.stock.ProductBinningLogic;


/**
 * @author 孙长权
 * @des 产品装箱--扫描
 * @date 2017/3/23
 */
public class ProductBinningScanFg extends BaseFragment {

    @BindViews({R.id.et_pack_boxno, R.id.et_product_no, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_pack_boxno, R.id.ll_product_no, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_pack_boxno, R.id.tv_product_no, R.id.tv_number})
    List<TextView> textViews;

    /**
     * 箱内数量
     */
    @BindView(R.id.tv_box_number)
    TextView tvBoxNumber;
    /**
     * 包装箱号
     */
    @BindView(R.id.tv_pack_boxno)
    TextView tvPackBoxNumber;
    @BindView(R.id.et_pack_boxno)
    EditText etPackBoxNumber;
    @BindView(R.id.ll_pack_boxno)
    LinearLayout llPackBoxNumber;

    @BindView(R.id.cb_inlocatorlock)
    CheckBox cbInlocatorlock;

    /**
     * 产品条码
     */
    @BindView(R.id.tv_product_no)
    TextView tvProductBarcode;
    @BindView(R.id.et_product_no)
    EditText etProductBarcode;
    @BindView(R.id.ll_product_no)
    LinearLayout llProductBarcode;

    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;

    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.includedetail)
    View includeDetail;

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;

    @BindView(R.id.tv_scaned_num)
    TextView tvScanedNum;
    @BindView(R.id.ll_scaned_num)
    LinearLayout llScanedNum;

    @OnFocusChange(R.id.et_pack_boxno)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llPackBoxNumber, views);
        ModuleUtils.etChange(activity, etPackBoxNumber, editTexts);
        ModuleUtils.tvChange(activity, tvPackBoxNumber, textViews);
    }

    @OnFocusChange(R.id.et_product_no)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(llProductBarcode, views);
        ModuleUtils.etChange(activity, etProductBarcode, editTexts);
        ModuleUtils.tvChange(activity, tvProductBarcode, textViews);
    }
    @OnFocusChange(R.id.et_input_num)
    void numberFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnCheckedChanged(R.id.cb_inlocatorlock)
    void isLock(boolean checked) {
        if (checked) {
            etPackBoxNumber.setKeyListener(null);
        } else {
            etPackBoxNumber.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }


    @OnTextChanged(value = R.id.et_pack_boxno, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PACKBOXWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PACKBOXWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_product_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void locatorChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.save)
    void save() {
        if (!boxFlag) {
            showFailedDialog(R.string.scan_box_number);
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
        if (currentPackages>=maxPackages){
            showFailedDialog(R.string.current_more_max);
            return;
        }
        showLoadingDialog();
        commonLogic.saveBean(saveBean, new ProductBinningLogic.SaveBinningListener() {
            @Override
            public void onSuccess(ProductBinningBean backBean) {
                dismissLoadingDialog();
                etProductBarcode.requestFocus();
                etProductBarcode.setText("");
                barcodeShow = "";
                barcodeFlag = false;
                show();
                upDateNum(backBean);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }


    /**
     * 包装箱号
     */
    final int PACKBOXWHAT = 1001;
    /**
     * 产品条码
     */
    final int BARCODEWHAT = 1002;
    /**
     * 条码展示
     */
    String barcodeShow;

    ProductBinningActivity pactivity;

    /**
     * 包装箱号扫描
     */
    boolean boxFlag;

    boolean barcodeFlag;

    ProductBinningLogic commonLogic;

    private ProductBinningBean saveBean;
    /**
     * 当前笔数
     */
    private float currentPackages;
    /**
     * 最大笔数
     */
    private float maxPackages;


    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PACKBOXWHAT://扫描包装箱号
                    final String barcodeNumber = msg.obj.toString().trim();
                    HashMap<String, String> map = new HashMap<>();
                    map.put(AddressContants.PACKAGENO, barcodeNumber);
                    commonLogic.scanProdut(map, new ProductBinningLogic.ScanPackBoxNumberListener() {
                        @Override
                        public void onSuccess(List<ProductBinningBean> productBinningBeans) {
                            ProductBinningBean tempBean = productBinningBeans.get(0);
                            pactivity.packBoxNumber = tempBean.getPackage_no();
                            etProductBarcode.requestFocus();
                            boxFlag = true;
                            saveBean.setPackage_no(productBinningBeans.get(0).getPackage_no());
                            cbInlocatorlock.setChecked(true);
                            upDateNum(tempBean);
                        }

                        @Override
                        public void onFailed(String error) {
                            boxFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etPackBoxNumber.setText("");
                                }
                            });
                        }
                    });
                    break;
                case BARCODEWHAT://扫描产品条码
                    final String barcodeno = msg.obj.toString().trim();
                    //赋值，用于明细界面调接口得数据
                    HashMap<String, String> map2 = new HashMap<>();
                    map2.put(AddressContants.DOC_NO, etPackBoxNumber.getText().toString());
                    map2.put(AddressContants.BARCODE_NO, barcodeno);
                    map2.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
                    commonLogic.scanBarcode(map2, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            barcodeFlag = true;
                            saveBean.setBarcode_no(barcodeBackBean.getBarcode_no());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setItem_barcode_type(barcodeBackBean.getItem_barcode_type());
                            saveBean.setQty(barcodeBackBean.getBarcode_qty());
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getBarcode_qty()));
                            if (boxFlag && CommonUtils.isAutoSave(saveBean)) {
                                save();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            barcodeFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etProductBarcode.setText("");
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

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_productbinning_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (ProductBinningActivity) activity;
        initData();
    }

    public void upDate() {
        if (boxFlag) {
            etProductBarcode.requestFocus();
        }
    }

    public void upDateNum(ProductBinningBean tempBean){
        currentPackages=StringUtils.string2Float(tempBean.getPackages());
        maxPackages=StringUtils.string2Float(tempBean.getMax_package_qty());
        tvBoxNumber.setText(StringUtils.deleteZero(tempBean.getPackages()) + "/" + StringUtils.deleteZero(tempBean.getMax_package_qty()));
        tvScanedNum.setText(StringUtils.deleteZero(tempBean.getQty()));
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        saveBean = new ProductBinningBean();
        tvBoxNumber.setText("");
        etPackBoxNumber.setText("");
        etProductBarcode.setText("");
        boxFlag = false;
        barcodeFlag = false;
        cbInlocatorlock.setChecked(false);
        commonLogic = ProductBinningLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        barcodeShow = "";
        etPackBoxNumber.requestFocus();
        show();
    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(barcodeShow));
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
