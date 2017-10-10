package digiwin.smartdepott100.module.activity.sale.scanout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.sale.scanout.ScanOutStoreListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.sale.scanout.ScanOutLogic;

/**
 * @author maoheng
 * @des 扫箱码出货
 * @date 2017/4/3
 */

public class ScanOutStoreListActivity extends BaseTitleActivity {

    ScanOutStoreListActivity activity;

    /**
     * 返回刷新页面
     */
    int TOCOMMIT = 1001;

//    @BindView(R.id.tv_locator)
//    TextView tvLocator;
//    @BindView(R.id.et_locator)
//    EditText etLocator;
//    @OnFocusChange(R.id.et_locator)
//    void locatorOrderFocusChange() {
//        ModuleUtils.viewChange(llLocator, views);
//        ModuleUtils.etChange(activity, etLocator, editTexts);
//        ModuleUtils.tvChange(activity, tvLocator, textViews);
//    }
//    @BindView(R.id.ll_locator)
//    LinearLayout llLocator;


    @BindView(R.id.tv_shipping_order)
    TextView tvShippingOrder;
    @BindView(R.id.et_shipping_order)
    EditText etShippingOrder;
    @OnFocusChange(R.id.et_shipping_order)
    void shippingOrderFocusChange() {
        ModuleUtils.viewChange(llShippingOrder, views);
        ModuleUtils.etChange(activity, etShippingOrder, editTexts);
        ModuleUtils.tvChange(activity, tvShippingOrder, textViews);
    }
    @BindView(R.id.ll_shipping_order)
    LinearLayout llShippingOrder;


    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.et_item_no)
    EditText etItemNo;
    @OnFocusChange(R.id.et_item_no)
    void itemNoFocusChange() {
        ModuleUtils.viewChange(llItemNo, views);
        ModuleUtils.etChange(activity, etItemNo, editTexts);
        ModuleUtils.tvChange(activity, tvItemNo, textViews);
    }
    @BindView(R.id.ll_item_no)
    LinearLayout llItemNo;


    @BindView(R.id.tv_custom)
    TextView tvCustom;
    @BindView(R.id.et_custom)
    EditText etCustom;
    @OnFocusChange(R.id.et_custom)
    void customFocusChange() {
        ModuleUtils.viewChange(llCustom, views);
        ModuleUtils.etChange(activity, etCustom, editTexts);
        ModuleUtils.tvChange(activity, tvCustom, textViews);
    }
    @BindView(R.id.ll_custom)
    LinearLayout llCustom;


    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.et_salesman)
    EditText etSalesman;
    @OnFocusChange(R.id.et_salesman)
    void salesmanFocusChange() {
        ModuleUtils.viewChange(llSalesman, views);
        ModuleUtils.etChange(activity, etSalesman, editTexts);
        ModuleUtils.tvChange(activity, tvSalesman, textViews);
    }
    @BindView(R.id.ll_salesman)
    LinearLayout llSalesman;


    @BindView(R.id.tv_operating_department)
    TextView tvOperatingDepartment;
    @BindView(R.id.et_operating_department)
    EditText etOperatingDepartment;
    @OnFocusChange(R.id.et_operating_department)
    void operatingDepartmentFocusChange() {
        ModuleUtils.viewChange(llOperatingDepartment, views);
        ModuleUtils.etChange(activity, etOperatingDepartment, editTexts);
        ModuleUtils.tvChange(activity, tvOperatingDepartment, textViews);
    }
    @BindView(R.id.ll_operating_department)
    LinearLayout llOperatingDepartment;

    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_date)
    EditText etDate;
    @OnFocusChange(R.id.et_date)
    void dateFocusChange() {
        ModuleUtils.viewChange(llDate, views);
        ModuleUtils.etChange(activity, etDate, editTexts);
        ModuleUtils.tvChange(activity, tvDate, textViews);
    }
    @BindView(R.id.date)
    ImageView date;
    @OnClick(R.id.date)
    void getDate(){
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate,String mShowDate) {
                startDate=mStartDate;
                endDate=mEndDate;
                etDate.setText(mShowDate);
                etDate.requestFocus();
            }
        });
    }
    @BindView(R.id.ll_date)
    LinearLayout llDate;

    @BindViews({R.id.et_locator, R.id.et_shipping_order, R.id.et_item_no, R.id.et_custom, R.id.et_salesman, R.id.et_operating_department, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_locator, R.id.ll_shipping_order, R.id.ll_item_no, R.id.ll_custom, R.id.ll_salesman, R.id.ll_operating_department, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_locator, R.id.tv_shipping_order, R.id.tv_item_no, R.id.tv_custom, R.id.tv_salesman, R.id.tv_operating_department, R.id.tv_date})
    List<TextView> textViews;

    @BindView(R.id.btn_search_sure)
    Button btnSearchSure;
    @OnClick(R.id.btn_search_sure)
    void searchSure(){
        searchData();
    }
    private ScanOutLogic commonLogic;
    private List<FilterResultOrderBean> list;
    private ScanOutStoreListAdapter adapter;
    private void searchData() {
        updateUI();
        showLoadingDialog();
        FilterBean bean = new FilterBean();
        bean.setWarehouse_no(LoginLogic.getWare());
        bean.setDoc_no(etShippingOrder.getText().toString().trim());
        bean.setItem_no(etItemNo.getText().toString().trim());
        bean.setCustomer_no(etCustom.getText().toString().trim());
        bean.setEmployee_no(etSalesman.getText().toString().trim());
        bean.setDepartment_no(etOperatingDepartment.getText().toString().trim());
        bean.setDate_begin(startDate);
        bean.setDate_end(endDate);
        commonLogic.getSOList(bean, new ScanOutLogic.GetScanOutListListener() {
            @Override
            public void onSuccess(List<FilterResultOrderBean> beanList) {
                dismissLoadingDialog();
                list = beanList;
                showData();
                updateUI();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }
    /**
     * 展示信息
     */
    private void showData() {
        try {
            isSearching=true;
            ryList.setVisibility(View.VISIBLE);
            llSearchInput.setVisibility(View.GONE);
        } catch (Exception e) {
            LogUtils.e(TAG, "showDates---Exception>" + e);
        }
    }
    /**
     * 更新界面
     */
    private void updateUI() {
        adapter = new ScanOutStoreListAdapter(activity,list);
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        ryList.setAdapter(adapter);
        itemClick();
    }
    /**
     * 条目点击
     */
    private void itemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.DOC_NO,list.get(position).getDoc_no());
                bundle.putString(AddressContants.DATE,list.get(position).getCreate_date());
                bundle.putString(AddressContants.CUSTOM,list.get(position).getCustomer_name());
                bundle.putString(AddressContants.MODULEID_INTENT,activity.mTimestamp.toString());
                ActivityManagerUtils.startActivityBundleForResult(activity,ScanOutStoreActivity.class,bundle,TOCOMMIT);
            }
        });
    }
    @BindView(R.id.ll_search_input)
    LinearLayout llSearchInput;
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbar;
    /**
     * 筛选按钮
     */
    @BindView(R.id.iv_title_setting)
    ImageView search;
    private boolean isSearching;
    @OnClick(R.id.iv_title_setting)
    void search(){
        if (isSearching) {
            isSearching=false;
            ryList.setVisibility(View.VISIBLE);
            llSearchInput.setVisibility(View.GONE);
            return;
        } else {
            isSearching=true;
            ryList.setVisibility(View.GONE);
            llSearchInput.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.SCANOUTSTORE;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.scan_out_store)+getString(R.string.list));
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching = true;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_scanoutstore_list;
    }

    @Override
    protected void doBusiness() {
        etDate.setKeyListener(null);
        activity = ScanOutStoreListActivity.this;
        list = new ArrayList<>();
        commonLogic = ScanOutLogic.getInstance(activity,module,mTimestamp.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TOCOMMIT){
            list.clear();
            searchData();
        }
    }
}
