package digiwin.smartdepott100.module.activity.produce.postmaterial;

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
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
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
import digiwin.smartdepott100.module.activity.common.NoComeUnComActivity;
import digiwin.smartdepott100.module.adapter.produce.PostMaterialOrderAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

import static digiwin.smartdepott100.login.loginlogic.LoginLogic.getUserInfo;

/**
 * 领料过账 待办事项
 * @author 唐孟宇
 */
public class PostMaterialActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindViews({R.id.et_post_order, R.id.et_material_code, R.id.et_department, R.id.et_applicant,R.id.et_plan_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_post_order, R.id.ll_material_code, R.id.ll_department, R.id.ll_applicant, R.id.ll_plan_date})
    List<View> views;
    @BindViews({R.id.tv_post_order, R.id.tv_material_code, R.id.tv_department, R.id.tv_applicant, R.id.tv_plan_date})
    List<TextView> textViews;

    /**
     * 筛选框 发料单号
     */
    @BindView(R.id.et_post_order)
    EditText et_post_order;
    /**
     * 筛选框 发料单号
     */
    @BindView(R.id.ll_post_order)
    LinearLayout ll_post_order;
    /**
     * 筛选框 发料单号
     */
    @BindView(R.id.tv_post_order)
    TextView tv_post_order;

    @OnFocusChange(R.id.et_post_order)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(ll_post_order, views);
        ModuleUtils.etChange(activity, et_post_order, editTexts);
        ModuleUtils.tvChange(activity, tv_post_order, textViews);
    }
    /**
     * 筛选框 料号
     */
    @BindView(R.id.et_material_code)
    EditText et_material_code;
    /**
     * 筛选框 料号
     */
    @BindView(R.id.ll_material_code)
    LinearLayout ll_material_code;
    /**
     * 筛选框 料号
     */
    @BindView(R.id.tv_material_code)
    TextView tv_material_code;

    @OnFocusChange(R.id.et_material_code)
    void materialCodeFocusChanage() {
        ModuleUtils.viewChange(ll_material_code, views);
        ModuleUtils.etChange(activity, et_material_code, editTexts);
        ModuleUtils.tvChange(activity, tv_material_code, textViews);
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
     * 筛选框 计划日
     */
    @BindView(R.id.et_plan_date)
    EditText et_plan_date;

    /**
     * 筛选框 计划日
     */
    @BindView(R.id.ll_plan_date)
    LinearLayout ll_plan_date;
    /**
     * 筛选框 计划日
     */
    @BindView(R.id.tv_plan_date)
    TextView tv_plan_date;

    /**
     * 筛选框 计划日
     */
    @BindView(R.id.iv_plan_date)
    ImageView iv_plan_date;

    String startDate = "";
    String endDate = "";

    @OnClick(R.id.iv_plan_date)
    void dateClick(){
        DatePickerUtils.getDoubleDate(pactivity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                et_plan_date.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                et_plan_date.setText(showDate);
            }
        });
    }

    @OnFocusChange(R.id.et_plan_date)
    void planDateFocusChanage() {
        ModuleUtils.viewChange(ll_plan_date, views);
        ModuleUtils.etChange(activity, et_plan_date, editTexts);
        ModuleUtils.tvChange(activity, tv_plan_date, textViews);
    }

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @BindView(R.id.ll_search_dialog)
    LinearLayout ll_search_dialog;

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    /**
     * 未完事项
     */
    @BindView(R.id.un_com)
    ImageView iv_un_com;

    /**
     * 跳转第二个页面使用
     */
    public static final int SECONDCODE = 1234;

    PostMaterialActivity pactivity;

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.post_material);
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
        iv_un_com.setVisibility(View.VISIBLE);
    }

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog(){
        if(ll_search_dialog.getVisibility() == View.VISIBLE){
            if(null != dataList && dataList.size()>0){
                ll_search_dialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
                iv_un_com.setVisibility(View.VISIBLE);
                adapter = new PostMaterialOrderAdapter(pactivity,dataList);
                ryList.setAdapter(adapter);
                onItemClick();
            }
        }else{
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
            iv_un_com.setVisibility(View.GONE);
        }
    }

    /**
     *  未完事项
     */
    @OnClick(R.id.un_com)
    void unComActivity(){
        Bundle bundle = new Bundle();
        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
        ActivityManagerUtils.startActivityForBundleData(pactivity, NoComeUnComActivity.class, bundle);
    }
    /**
     * 点击确定，筛选
     */
    @OnClick(R.id.btn_search_sure)
    void Search(){
        upDateList();
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.POSTMATERIAL;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_post_material;
    }

    @Override
    protected void doBusiness() {
        et_plan_date.setKeyListener(null);
        pactivity = (PostMaterialActivity) activity;
        logic = CommonLogic.getInstance(pactivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        SearchDialog();
    }

    /**
     * 点击item跳转到汇总界面
     */
    private void onItemClick(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                final FilterResultOrderBean orderData = dataList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddressContants.ORDERDATA,orderData);
                ActivityManagerUtils.startActivityBundleForResult(pactivity,PostMaterialSecondActivity.class,bundle,SECONDCODE);
            }
        });
    }

    private List<FilterResultOrderBean> dataList = new ArrayList<>();

    PostMaterialOrderAdapter adapter;

    CommonLogic logic;
    /**
     * 汇总展示
     */
    public void upDateList() {
        FilterBean filterBean  = new FilterBean();
        try {
            AccoutBean accoutBean = getUserInfo();
            if(null == accoutBean){
                return;
            }
            //仓库
            filterBean.setWarehouse_out_no(accoutBean.getWare());
            //发料单号
            if(!StringUtils.isBlank(et_post_order.getText().toString())){
                filterBean.setDoc_no(et_post_order.getText().toString());
            }
            //料号
            if(!StringUtils.isBlank(et_material_code.getText().toString())){
                filterBean.setItem_no(et_material_code.getText().toString());
            }
            //部门
            if(!StringUtils.isBlank(et_department.getText().toString())){
                filterBean.setDepartment_no(et_department.getText().toString());
            }
            //申请人
            if(!StringUtils.isBlank(et_applicant.getText().toString())){
                filterBean.setEmployee_no(et_applicant.getText().toString());
            }
            //计划日
            if(!StringUtils.isBlank(et_plan_date.getText().toString())){
                filterBean.setDate_begin(startDate);
                filterBean.setDate_end(endDate);
            }
            showLoadingDialog();
            logic.getOrderData(filterBean, new CommonLogic.GetOrderListener(){
                @Override
                public void onSuccess(List<FilterResultOrderBean> list) {
                    if(null != list && list.size()>0){
                    dismissLoadingDialog();
                    //查询成功隐藏筛选界面，展示汇总信息
                    ll_search_dialog.setVisibility(View.GONE);
                    scrollview.setVisibility(View.VISIBLE);
                    iv_title_setting.setVisibility(View.VISIBLE);
                    iv_un_com.setVisibility(View.VISIBLE);
                    //TODO setAdapter
                    dataList.clear();
                    dataList = list;
                    adapter = new PostMaterialOrderAdapter(pactivity,dataList);
                    ryList.setAdapter(adapter);
                    onItemClick();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    try {
                        showFailedDialog(error);
                        //TODO setAdapter
                        dataList = new ArrayList<FilterResultOrderBean>();
                        adapter = new PostMaterialOrderAdapter(pactivity,dataList);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == SECONDCODE){
                adapter = new PostMaterialOrderAdapter(pactivity,new ArrayList<FilterResultOrderBean>());
                ryList.setAdapter(adapter);
                upDateList();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
