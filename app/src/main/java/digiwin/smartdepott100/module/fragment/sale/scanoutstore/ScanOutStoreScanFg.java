package digiwin.smartdepott100.module.fragment.sale.scanoutstore;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.sale.scanout.ScanOutStoreActivity;
import digiwin.smartdepott100.module.adapter.common.CommonDocNoFifoAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.stock.ProductBinningBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.sale.scanoutstore.ScanOutStoreLogic;

/**
 * @author maoheng
 * @des 扫码出货
 * @date 2017/4/3
 */

public class ScanOutStoreScanFg extends BaseFragment {

    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;
    @OnTextChanged(value = R.id.et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;
    @BindView(R.id.tv_locator_string)
    TextView tvLocatorString;
    @BindView(R.id.tv_scan_locator)
    TextView tvScanLocator;
    @BindView(R.id.ll_scan_locator)
    LinearLayout llScanLocator;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.save)
    Button save;
    @OnClick(R.id.save)
    void save() {
        if (!barcodeFlag) {
            showFailedDialog(R.string.scan_case_first);
            return;
        }

        showLoadingDialog();
        storeLogic.saveScanOutStore(saveBean, new ScanOutStoreLogic.ScanOutSaveListener() {
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
    public void clear(){
        etScanBarocde.setText("");
        tvScanLocator.setText("");
        getFIFo();
    }

    /**
     * fifo
     */
    final int SALEWHAT = 1000;
    /**
     * 箱码
     */
    final int BARCODEWHAT = 1001;
    /**
     * 库位
     */
    final int LOCATORWHAT = 1002;
    /**
     * 条码扫描
     */
    boolean barcodeFlag;

    SaveBean saveBean;

    private List<FifoCheckBean> fiFoList;

    private BaseRecyclerAdapter adapter;

    /**
     * 仓库
     */
    String ware = "";
    /**
     * 箱码
     */
    String notice_no;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SALEWHAT:
                    fiFoList.clear();
                    adapter = new CommonDocNoFifoAdapter(sActivity,fiFoList);
                    ryList.setAdapter(adapter);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(AddressContants.RECEIPT_NO, String.valueOf(msg.obj));
                    map.put(AddressContants.WAREHOUSEOUTNO, ware);
                    ClickItemPutBean itemPutBean = new ClickItemPutBean();
                    itemPutBean.setNotice_no(notice_no);
                    itemPutBean.setWarehouse_no(ware);
                    EventBus.getDefault().post(itemPutBean);
                    commonLogic.docNoFIFO(map, new CommonLogic.PostMaterialFIFOListener() {
                        @Override
                        public void onSuccess(List<FifoCheckBean> fiFoBeanList) {
                            fiFoList.clear();
                            fiFoList = fiFoBeanList;
                            adapter = new CommonDocNoFifoAdapter(sActivity, fiFoList);
                            ryList.setAdapter(adapter);
                            etScanBarocde.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            fiFoList.clear();
                            adapter = new CommonDocNoFifoAdapter(sActivity, fiFoList);
                            ryList.setAdapter(adapter);
                        }
                    });
                    break;
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.RECEIPTNO, notice_no);
//                    barcodeMap.put(AddressContants.ITEM_NO, "");
                    barcodeMap.put(AddressContants.PACKAGENO, String.valueOf(msg.obj));
                    commonLogic.scanPackBoxNumber(barcodeMap, new CommonLogic.ScanPackBoxNumberListener() {
                        @Override
                        public void onSuccess(List<ProductBinningBean> productBinningBeans) {
                            ProductBinningBean productBinningBean = productBinningBeans.get(0);
                            if(!productBinningBean.getWarehouse_no().equals(LoginLogic.getWare())){
                                barcodeFlag = false;
                                showFailedDialog(R.string.warehouse_failed, new OnDialogClickListener() {
                                    @Override
                                    public void onCallback() {
                                        etScanBarocde.setText("");
                                    }
                                });
                                return;
                            }
                            barcodeFlag = true;
                            saveBean.setWarehouse_out_no(productBinningBean.getWarehouse_no());
                            saveBean.setStorage_spaces_out_no(productBinningBean.getStorage_spaces_no());
                            saveBean.setPackage_no(productBinningBean.getPackage_no());
                            saveBean.setDoc_no(notice_no);
                            tvScanLocator.setText(productBinningBean.getStorage_spaces_no());
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
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    private CommonLogic commonLogic;

    private ScanOutStoreLogic storeLogic;

    private ScanOutStoreActivity sActivity;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_scanout_store_scan;
    }

    @Override
    protected void doBusiness() {
        sActivity = (ScanOutStoreActivity) activity;
        commonLogic = CommonLogic.getInstance(activity,sActivity.module,sActivity.mTimestamp.toString());
        storeLogic = ScanOutStoreLogic.getInstance(activity,sActivity.module,sActivity.mTimestamp.toString());
        initData();
        getFIFo();
    }


    /**
     * 初始化一些变量
     */
    public void initData() {
        barcodeFlag = false;
        saveBean = new SaveBean();
        fiFoList = new ArrayList<>();
        notice_no="";
        ware = "";
        commonLogic = CommonLogic.getInstance(context, sActivity.module, sActivity.mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(sActivity);
        ryList.setLayoutManager(linearLayoutManager);
        AccoutBean accoutBean = LoginLogic.getUserInfo();
        if (null != accoutBean) {
            ware = accoutBean.getWare();
        }
    }

    /**
     *获取 fifo
     */
    public  void getFIFo(){
        try {
            notice_no=sActivity.getIntent().getExtras().getString(AddressContants.DOC_NO);
            mHandler.sendMessage(mHandler.obtainMessage(SALEWHAT,notice_no));
        }catch (Exception e){
            LogUtils.e(TAG,"fifo获取"+e);
        }
    }
    public void upDateList(){
        getFIFo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
