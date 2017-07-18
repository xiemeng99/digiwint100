package digiwin.smartdepott100.module.activity.sale.tranceproduct;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.library.zxing.MipcaActivityCapture;
import digiwin.library.zxing.camera.GetBarCodeListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.sale.traceproduct.ProcuctProcessBean;
import digiwin.smartdepott100.module.bean.sale.traceproduct.TraceProductDetailBean;
import digiwin.smartdepott100.module.fragment.sale.traceproduct.TraceCurrentInventoryFg;
import digiwin.smartdepott100.module.fragment.sale.traceproduct.TraceOrderInfoFg;
import digiwin.smartdepott100.module.fragment.sale.traceproduct.TraceProductionProcessFg;
import digiwin.smartdepott100.module.fragment.sale.traceproduct.TraceShipmentToFg;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.sale.traceproduct.TraceProductLogic;

/**
 * @author 毛衡
 * @des 产品质量追溯
 * @date 2017/4/6
 */

public class TraceProductActivity extends BaseActivity {

    /**
     * 返回按钮
     */
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @OnClick(R.id.iv_back)
    void ivback() {
        onBackPressed();
    }

    /**
     * 模块名
     */
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;

    @OnClick(R.id.tv_title_name)
    void tvback() {
        onBackPressed();
    }

    /**
     * 扫描框
     */
    @BindView(R.id.ll_et_input)
    LinearLayout ll_et_input;
    /**
     * 扫描框
     */
    @BindView(R.id.et_input)
    EditText et_input;

    private final int BARCODEWHAT = 1000;

    @OnTextChanged(value = R.id.et_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void scanBarcode(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }

    }

    private CommonLogic commonLogic;

    public static ScanBarcodeBackBean backBean;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    Map<String, String> map = new HashMap<>();
                    map.put(AddressContants.BARCODE_NO, (String) msg.obj);
                    commonLogic.scanBarcode(map, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            backBean = barcodeBackBean;
                            et_input.setText("");
                            int currentItem = moduleVp.getCurrentItem();
                            switch (currentItem) {
                                case 0:
                                    processFg.initData();
                                    break;
                                case 1:
                                    inventoryFg.initData();
                                    break;
                                case 2:
                                    infoFg.initData();
                                    break;
                                case 3:
                                    shipmentToFg.initData();
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error);
                            et_input.setText("");
                        }
                    });
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @OnClick(R.id.rl_iv_input_scan)
    void ScanCamara() {
        MipcaActivityCapture.startCameraActivity(activity, new GetBarCodeListener() {
            @Override
            public void onSuccess(String msg) {
                View focusView = ViewUtils.getFocusView(activity);
                if (focusView instanceof EditText) {
                    EditText et = (EditText) focusView;
                    et.setText(msg);
                    et.setSelection(msg.length());
                }
            }
        });
    }

    public static int jumbType;

    private void showAllImg(){
        ivLotNo.setVisibility(View.VISIBLE);
        ivWorkGroup.setVisibility(View.VISIBLE);
        ivCheckInfo.setVisibility(View.VISIBLE);
    }
    private void dismissAllImg(){
        ivLotNo.setVisibility(View.INVISIBLE);
        ivWorkGroup.setVisibility(View.INVISIBLE);
        ivCheckInfo.setVisibility(View.INVISIBLE);
    }

    /**
     * 明细点击
     */
    @BindView(R.id.iv_lot_no)
    ImageView ivLotNo;
    @OnClick(R.id.iv_lot_no)
    void ivLotNoClick(){
        jumbType = 1;
        checkSelect();
        map.put(AddressContants.FLAG,"1");
        showDetail(map);
    }
    @BindView(R.id.iv_work_group)
    ImageView ivWorkGroup;
    @OnClick(R.id.iv_work_group)
    void ivWorkGroupClick(){
        jumbType = 2;
        checkSelect();
        map.put(AddressContants.FLAG,"2");
        showDetail(map);
    }
    @BindView(R.id.iv_check_info)
    ImageView ivCheckInfo;
    @OnClick(R.id.iv_check_info)
    void ivCheckInfoClick(){
        jumbType = 3;
        checkSelect();
        map.put(AddressContants.FLAG,"3");
        showDetail(map);
    }

    public interface CheckSelectListener{
        public void checkedListener();
    }

    private  Map<String,String> map;
    private void checkSelect(){
        map = new HashMap<>();
        ProcuctProcessBean selectedData = processFg.getSelectedData();
        map.put(AddressContants.WO_NO,selectedData.getWo_no());
        map.put(AddressContants.PROCESSCARD,selectedData.getProcess_card());
        map.put(AddressContants.REPOSTDATETIME,selectedData.getReport_datetime());
        map.put(AddressContants.LINENO,selectedData.getLine_no());
        map.put(AddressContants.SUBOP_NO,selectedData.getSubop_no());
    }

    private TraceProductLogic productLogic;

    public final static String DETAIL = "detail";
    /**
     * 获取右上角明细
     */
    public void showDetail(Map<String,String> map){
        if(TraceProductionProcessFg.prePosition == -1){
            showToast(R.string.please_select_data);
            return;
        }
        showLoadingDialog();
        productLogic.traceProductDetailGet(map, new TraceProductLogic.TraceProductDetailGetListener() {
            @Override
            public void onSuccess(List<TraceProductDetailBean> datas) {
                dismissLoadingDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(DETAIL, (Serializable) datas);
                ActivityManagerUtils.startActivityForBundleData(tactivity,TraceProductDetailActivity.class,bundle);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.module_vp)
    ViewPager moduleVp;
    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;

    /**
     * 生产过程
     */
    TraceProductionProcessFg processFg;
    /**
     * 当前库存
     */
    TraceCurrentInventoryFg inventoryFg;
    /**
     * 工单信息
     */
    TraceOrderInfoFg infoFg;
    /**
     * 出货流向
     */
    TraceShipmentToFg shipmentToFg;

    ModuleViewPagerAdapter adapter;

    @Override
    public String moduleCode() {
        module = ModuleCode.TRANSPRODUCTQUALITY;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_trace_product;
    }

    @Override
    protected void initNavigationTitle() {
        tv_title_name.setText(R.string.trace_quality);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private TraceProductActivity tactivity;

    @Override
    protected void doBusiness() {
        tactivity = (TraceProductActivity) activity;
        productLogic = TraceProductLogic.getInstance(tactivity,module,mTimestamp.toString());
        commonLogic = CommonLogic.getInstance(tactivity, module, mTimestamp.toString());
        processFg = new TraceProductionProcessFg();
        inventoryFg = new TraceCurrentInventoryFg();
        infoFg = new TraceOrderInfoFg();
        shipmentToFg = new TraceShipmentToFg();
        fragments = new ArrayList<>();
        fragments.add(processFg);
        fragments.add(inventoryFg);
        fragments.add(infoFg);
        fragments.add(shipmentToFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.product_process));
        titles.add(getResources().getString(R.string.current_iventory));
        titles.add(getResources().getString(R.string.order_info));
        titles.add(getResources().getString(R.string.shipment_to));
        fragmentManager = getSupportFragmentManager();
        adapter = new ModuleViewPagerAdapter(fragmentManager, fragments, titles);
        moduleVp.setAdapter(adapter);
        tlTab.addTab(tlTab.newTab().setText(titles.get(0)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(1)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(2)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(3)));
        //Tablayout和ViewPager关联起来
        tlTab.setupWithViewPager(moduleVp);
        tlTab.setTabsFromPagerAdapter(adapter);
        select();
    }

    private void select() {
        moduleVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        processFg.initData();
                        showAllImg();
                        break;
                    case 1:
                        inventoryFg.initData();
                        dismissAllImg();
                        break;
                    case 2:
                        infoFg.initData();
                        dismissAllImg();
                        break;
                    case 3:
                        shipmentToFg.initData();
                        dismissAllImg();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
