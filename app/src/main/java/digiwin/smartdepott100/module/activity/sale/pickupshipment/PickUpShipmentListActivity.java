package digiwin.smartdepott100.module.activity.sale.pickupshipment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.constant.SharePreKey;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.sale.pickupshipment.PickUpShipmentListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.sale.pickupshipment.PickUpShipmentLogic;

/**
 * @author 赵浩然
 * @module 捡料出货清单 出货过账
 * @date 2017/3/22
 */

public class PickUpShipmentListActivity extends BaseTitleActivity {

    private PickUpShipmentListActivity activity;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 筛选布局
     */
    @BindView(R.id.ll_search_dialog)
    LinearLayout llSearchDialog;
    /**
     * 列表布局
     */
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    @BindViews({R.id.ll_shipping_order, R.id.ll_item_no, R.id.ll_custom,
            R.id.ll_salesman, R.id.ll_operating_department, R.id.ll_plan_date})
    List<View> views;
    @BindViews({R.id.tv_shipping_order, R.id.tv_item_no, R.id.tv_custom,
            R.id.tv_salesman, R.id.tv_operating_department, R.id.tv_plan_date})
    List<TextView> textViews;
    @BindViews({R.id.et_shipping_order, R.id.et_item_no, R.id.et_custom,
            R.id.et_salesman, R.id.et_operating_department, R.id.et_plan_date})
    List<EditText> editTexts;

    /**
     * 出货单号
     */
    @BindView(R.id.ll_shipping_order)
    LinearLayout llShippingOrder;
    @BindView(R.id.tv_shipping_order)
    TextView tvShippingOrder;
    @BindView(R.id.et_shipping_order)
    EditText etShippingOrder;

    @OnFocusChange(R.id.et_shipping_order)
    void shipping_orderFocusChanage() {
        ModuleUtils.viewChange(llShippingOrder, views);
        ModuleUtils.tvChange(activity, tvShippingOrder, textViews);
        ModuleUtils.etChange(activity, etShippingOrder, editTexts);
    }

    /**
     * 料号
     */
    @BindView(R.id.ll_item_no)
    LinearLayout llItemNo;
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.et_item_no)
    EditText etItemNo;

    @OnFocusChange(R.id.et_item_no)
    void item_noFocusChanage() {
        ModuleUtils.viewChange(llItemNo, views);
        ModuleUtils.tvChange(activity, tvItemNo, textViews);
        ModuleUtils.etChange(activity, etItemNo, editTexts);
    }

    /**
     * 客户
     */
    @BindView(R.id.ll_custom)
    LinearLayout llCustom;
    @BindView(R.id.tv_custom)
    TextView tvCustom;
    @BindView(R.id.et_custom)
    EditText etCustom;

    @OnFocusChange(R.id.et_custom)
    void customFocusChanage() {
        ModuleUtils.viewChange(llCustom, views);
        ModuleUtils.tvChange(activity, tvCustom, textViews);
        ModuleUtils.etChange(activity, etCustom, editTexts);
    }

    /**
     * 业务员
     */
    @BindView(R.id.ll_salesman)
    LinearLayout llSalesman;
    @BindView(R.id.tv_salesman)
    TextView tvSalesman;
    @BindView(R.id.et_salesman)
    EditText etSalesman;

    @OnFocusChange(R.id.et_salesman)
    void salesmanFocusChanage() {
        ModuleUtils.viewChange(llSalesman, views);
        ModuleUtils.tvChange(activity, tvSalesman, textViews);
        ModuleUtils.etChange(activity, etSalesman, editTexts);
    }

    /**
     * 业务部门
     */
    @BindView(R.id.ll_operating_department)
    LinearLayout llOperatingDepartment;
    @BindView(R.id.tv_operating_department)
    TextView tvOperatingDepartment;
    @BindView(R.id.et_operating_department)
    EditText etOperatingDepartment;

    @OnFocusChange(R.id.et_operating_department)
    void operating_departmentFocusChanage() {
        ModuleUtils.viewChange(llOperatingDepartment, views);
        ModuleUtils.tvChange(activity, tvOperatingDepartment, textViews);
        ModuleUtils.etChange(activity, etOperatingDepartment, editTexts);
    }

    /**
     * 筛选框 计划日
     */
    @BindView(R.id.iv_plan_date)
    ImageView ivPlanDate;
    @BindView(R.id.tv_plan_date)
    TextView tvPlanDate;
    @BindView(R.id.ll_plan_date)
    LinearLayout llPlanDate;

    @BindView(R.id.et_plan_date)
    EditText etPlanDate;

    @OnFocusChange(R.id.et_plan_date)
    void plan_dateFocusChanage() {
        ModuleUtils.viewChange(llPlanDate, views);
        ModuleUtils.tvChange(activity, tvPlanDate, textViews);
        ModuleUtils.etChange(activity, etPlanDate, editTexts);
    }

    String startDate = "";
    String endDate = "";

    @OnClick(R.id.iv_plan_date)
    void dateClick() {
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                etPlanDate.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                etPlanDate.setText(showDate);
            }
        });
    }

    PickUpShipmentListAdapter adapter;

    PickUpShipmentLogic logic;

    private final int SCANCODE = 1234;

    private List<FilterResultOrderBean> dataList;

    @BindView(R.id.btn_search_sure)
    Button btn_search_sure;

    @OnClick(R.id.btn_search_sure)
    void search() {
        //待办事项展示
        FilterBean filterBean = new FilterBean();
        showLoadingDialog();
        filterBean.setWarehouse_no(LoginLogic.getWare());
        if (!StringUtils.isBlank(etShippingOrder.getText().toString().trim())) {
            filterBean.setDoc_no(etShippingOrder.getText().toString().trim());
        }
        filterBean.setPagesize((String) SharedPreferencesUtils.get(activity, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM));
        if (!StringUtils.isBlank(etItemNo.getText().toString().trim())) {
            filterBean.setItem_no(etItemNo.getText().toString().trim());
        }

        if (!StringUtils.isBlank(etCustom.getText().toString().trim())) {
            filterBean.setCustomer_no(etCustom.getText().toString().trim());
        }

        if (!StringUtils.isBlank(etSalesman.getText().toString().trim())) {
            filterBean.setEmployee_no(etSalesman.getText().toString().trim());
        }

        if (!StringUtils.isBlank(etOperatingDepartment.getText().toString().trim())) {
            filterBean.setDepartment_no(etOperatingDepartment.getText().toString().trim());
        }
        if (!StringUtils.isBlank(etPlanDate.getText().toString())) {
            filterBean.setDate_begin(startDate);
            filterBean.setDate_end(endDate);
        }
        filterBean.setPagesize((String) SharedPreferencesUtils.get(activity, SharePreKey.PAGE_SETTING, "10"));
        logic.getSearchListData(filterBean, new PickUpShipmentLogic.GetSearchListDataListener() {
            @Override
            public void onSuccess(final List<FilterResultOrderBean> list) {
                dismissLoadingDialog();
                if (null != list && list.size() > 0) {
                    llSearchDialog.setVisibility(View.GONE);
                    scrollview.setVisibility(View.VISIBLE);
                    dataList = new ArrayList<FilterResultOrderBean>();
                    dataList = list;
                    adapter = new PickUpShipmentListAdapter(activity, list);
                    ryList.setAdapter(adapter);
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            itemClick(dataList, position);
                        }
                    });
                    if (autoSkip && list.size() == 1) {
                        itemClick(dataList, 0);
                    }
                    autoSkip = true;
                }
            }

            @Override
            public void onFailed(String errmsg) {
                dismissLoadingDialog();
                showFailedDialog(errmsg);
                dataList = new ArrayList<FilterResultOrderBean>();
                adapter = new PickUpShipmentListAdapter(activity, dataList);
                ryList.setAdapter(adapter);
            }
        });
    }

    private void itemClick(List<FilterResultOrderBean> clickBeen, int position) {
        Bundle bundle = new Bundle();
        FilterResultOrderBean data = clickBeen.get(position);
        bundle.putSerializable("data", data);
        ActivityManagerUtils.startActivityBundleForResult(activity, PickUpShipmentActivity.class, bundle, SCANCODE);
    }

    @Override
    protected void doBusiness() {
        etPlanDate.setKeyListener(null);
        logic = PickUpShipmentLogic.getInstance(activity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_pickupshipmentlist;
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PICKUPSHIPMENT;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getString(R.string.title_pickupshipment_list) + getString(R.string.list));
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog() {
        if (llSearchDialog.getVisibility() == View.VISIBLE) {
            if (null != dataList && dataList.size() > 0) {
                llSearchDialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
            }
        } else {
            llSearchDialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SCANCODE) {
                dataList.clear();
                adapter = new PickUpShipmentListAdapter(activity, dataList);
                ryList.setAdapter(adapter);
                search();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
