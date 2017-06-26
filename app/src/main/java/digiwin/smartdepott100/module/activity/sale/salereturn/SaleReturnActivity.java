package digiwin.smartdepott100.module.activity.sale.salereturn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.module.logic.sale.salereturn.SaleReturnLogic;
import digiwin.library.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.module.adapter.sale.salereturn.SaleReturnAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

import static digiwin.smartdepott100.login.loginlogic.LoginLogic.getUserInfo;

/**
 * 销售退货 汇总界面
 *
 * @author 唐孟宇
 */
public class SaleReturnActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindViews({R.id.et_return_order_no, R.id.et_customer_no, R.id.et_applicant,R.id.et_department, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_return_order_no, R.id.ll_customer_no, R.id.ll_applicant, R.id.ll_department, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_return_order_no, R.id.tv_customer_no, R.id.tv_applicant, R.id.tv_department,R.id.tv_date})
    List<TextView> textViews;

    /**
     * 筛选框 调拨单号
     */
    @BindView(R.id.et_return_order_no)
    EditText et_return_order_no;
    /**
     * 筛选框 调拨单号
     */
    @BindView(R.id.ll_return_order_no)
    LinearLayout ll_return_order_no;
    /**
     * 筛选框 调拨单号
     */
    @BindView(R.id.tv_return_order_no)
    TextView tv_return_order_no;

    @OnFocusChange(R.id.et_return_order_no)
    void returnOrderFocusChanage() {
        ModuleUtils.viewChange(ll_return_order_no, views);
        ModuleUtils.etChange(activity, et_return_order_no, editTexts);
        ModuleUtils.tvChange(activity, tv_return_order_no, textViews);
    }

    /**
     * 筛选框 客户编码
     */
    @BindView(R.id.et_customer_no)
    EditText et_customer_no;

    /**
     * 筛选框 客户编码
     */
    @BindView(R.id.ll_customer_no)
    LinearLayout ll_customer_no;
    /**
     * 筛选框 客户编码
     */
    @BindView(R.id.tv_customer_no)
    TextView tv_customer_no;

    @OnFocusChange(R.id.et_customer_no)
    void customerFocusChanage() {
        ModuleUtils.viewChange(ll_customer_no, views);
        ModuleUtils.etChange(activity, et_customer_no, editTexts);
        ModuleUtils.tvChange(activity, tv_customer_no, textViews);
    }

    /**
     * 筛选框 申请人
     */
    @BindView(R.id.et_applicant)
    EditText et_applicant;

    /**
     * 筛选框 申请人
     */
    @BindView(R.id.ll_applicant)
    LinearLayout ll_applicant;
    /**
     * 筛选框 申请人
     */
    @BindView(R.id.tv_applicant)
    TextView tv_applicant;

    @OnFocusChange(R.id.et_applicant)
    void applicantFocusChanage() {
        ModuleUtils.viewChange(ll_applicant, views);
        ModuleUtils.etChange(activity, et_applicant, editTexts);
        ModuleUtils.tvChange(activity, tv_applicant, textViews);
    }
    /**
     * 筛选框 部门
     */
    @BindView(R.id.et_department)
    EditText et_department;

    /**
     * 筛选框 部门
     */
    @BindView(R.id.ll_department)
    LinearLayout ll_department;
    /**
     * 筛选框 部门
     */
    @BindView(R.id.tv_department)
    TextView tv_department;

    @OnFocusChange(R.id.et_department)
    void departmentFocusChanage() {
        ModuleUtils.viewChange(ll_department, views);
        ModuleUtils.etChange(activity, et_department, editTexts);
        ModuleUtils.tvChange(activity, tv_department, textViews);
    }

    /**
     * 筛选框 日期
     */
    @BindView(R.id.et_date)
    EditText et_date;

    /**
     * 筛选框 日期
     */
    @BindView(R.id.ll_date)
    LinearLayout ll_date;
    /**
     * 筛选框 日期
     */
    @BindView(R.id.tv_date)
    TextView tv_date;

    /**
     * 筛选框 日期
     */
    @BindView(R.id.iv_date)
    ImageView iv_date;

    String startDate = "";
    String endDate = "";

    @OnClick(R.id.iv_date)
    void dateClick() {
        DatePickerUtils.getDoubleDate(pactivity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                et_date.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                et_date.setText(showDate);
            }
        });
    }

    @OnFocusChange(R.id.et_date)
    void planDateFocusChanage() {
        ModuleUtils.viewChange(ll_date, views);
        ModuleUtils.etChange(activity, et_date, editTexts);
        ModuleUtils.tvChange(activity, tv_date, textViews);
    }

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @BindView(R.id.ll_search_dialog)
    LinearLayout ll_search_dialog;

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    /**
     * 跳转扫描使用
     */
    public static final int SUMCODE = 1212;

    SaleReturnLogic logic;

    SaleReturnActivity pactivity;

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog() {
        if (ll_search_dialog.getVisibility() == View.VISIBLE) {
            if (null != sumShowBeanList && sumShowBeanList.size() > 0) {
                ll_search_dialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
                adapter = new SaleReturnAdapter(pactivity, sumShowBeanList);
                ryList.setAdapter(adapter);
                onItemClick();
            }
        } else {
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
    }

    /**
     * 点击确定，筛选
     */
    @OnClick(R.id.btn_search_sure)
    void Search() {
        //TODO
        upDateList();
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.SALERETURN;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_sale_return_order;
    }

    @Override
    protected void doBusiness() {
        et_date.setKeyListener(null);
        pactivity = (SaleReturnActivity) activity;
        logic = SaleReturnLogic.getInstance(pactivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        SearchDialog();
    }

    /**
     * 点击item跳转到汇总界面
     */
    private void onItemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                final FilterResultOrderBean orderData = sumShowBeanList.get(position);
                Bundle bundle = new Bundle();
                        bundle.putSerializable(AddressContants.ORDERDATA, orderData);
                        ActivityManagerUtils.startActivityBundleForResult(pactivity, SaleReturnSecondActivity.class, bundle, SUMCODE);
            }
        });
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.title_sale_return);
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    SaleReturnAdapter adapter;

    List<FilterResultOrderBean> sumShowBeanList;

    /**
     * 汇总展示
     */
    public void upDateList() {
        FilterBean filterBean = new FilterBean();
        try {
            AccoutBean accoutBean = getUserInfo();
            if (null == accoutBean) {
                return;
            }
            Map<String, String> map = new HashMap<>();
            //仓库
            filterBean.setWarehouse_in_no(accoutBean.getWare());
            //退料单号
            if (!StringUtils.isBlank(et_return_order_no.getText().toString())) {
                filterBean.setDoc_no(et_return_order_no.getText().toString());
            }
            //客户编码
            if (!StringUtils.isBlank(et_customer_no.getText().toString())) {
                filterBean.setCustomer_no(et_customer_no.getText().toString());
            }
            //申请人
            if (!StringUtils.isBlank(et_applicant.getText().toString())) {
                filterBean.setEmployee_no(et_applicant.getText().toString());
            }
            //部门
            if (!StringUtils.isBlank(et_department.getText().toString())) {
                filterBean.setDepartment_no(et_department.getText().toString());
            }
            //日期
            if (!StringUtils.isBlank(et_date.getText().toString())) {
                filterBean.setDate_begin(startDate);
                filterBean.setDate_end(endDate);
            }
            showLoadingDialog();
            logic.getSOLListData(filterBean, new SaleReturnLogic.GetSaleRetrunListDataListener() {
                @Override
                public void onSuccess(List<FilterResultOrderBean> list) {
                    if (null != list && list.size() > 0) {
                        dismissLoadingDialog();
                        //查询成功隐藏筛选界面，展示汇总信息
                        ll_search_dialog.setVisibility(View.GONE);
                        scrollview.setVisibility(View.VISIBLE);
                        iv_title_setting.setVisibility(View.VISIBLE);
                        sumShowBeanList = new ArrayList<FilterResultOrderBean>();
                        sumShowBeanList = list;
                        adapter = new SaleReturnAdapter(pactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        onItemClick();
                    }
                }

                @Override
                public void onFailed(String errmsg) {
                    dismissLoadingDialog();
                    try {
                        showFailedDialog(errmsg);
                        sumShowBeanList = new ArrayList<FilterResultOrderBean>();
                        adapter = new SaleReturnAdapter(pactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "updateList--getSum--onFailed" + e);
                    }
                }
            });

        } catch (Exception e) {
            LogUtils.e(TAG, "updateList--getSum--Exception" + e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == SUMCODE) {
                adapter = new SaleReturnAdapter(pactivity,new ArrayList<FilterResultOrderBean>());
                ryList.setAdapter(adapter);
                upDateList();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
