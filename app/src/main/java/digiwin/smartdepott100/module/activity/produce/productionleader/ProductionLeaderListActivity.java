package digiwin.smartdepott100.module.activity.produce.productionleader;

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
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.produce.ProductionLeaderListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @module 生产超领 清单列表
 * @date 2017/3/30
 */

public class ProductionLeaderListActivity extends BaseTitleActivity {

    private ProductionLeaderListActivity activity;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    @BindView(R.id.ry_list)
    RecyclerView ry_list;

    /**
     * 筛选布局
     */
    @BindView(R.id.ll_search_dialog)
    LinearLayout ll_search_dialog;
    /**
     * 列表布局
     */
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    ProductionLeaderListAdapter adapter;

    CommonLogic commonLogic;

    @BindViews({R.id.ll_super_number,R.id.ll_order_number,R.id.ll_applicant,
            R.id.ll_department,R.id.ll_plan_date})
    List<View> views;
    @BindViews({R.id.tv_super_number,R.id.tv_order_number,R.id.tv_applicant,
            R.id.tv_department,R.id.tv_plan_date})
    List<TextView> textViews;
    @BindViews({R.id.et_super_number,R.id.et_order_number,R.id.et_applicant,
            R.id.et_department,R.id.et_plan_date})
    List<EditText> editTexts;

    /**
     * 超领单号
     */
    @BindView(R.id.ll_super_number)
    LinearLayout ll_super_number;
    @BindView(R.id.tv_super_number)
    TextView tv_super_number;
    @BindView(R.id.et_super_number)
    EditText et_super_number;

    @OnFocusChange(R.id.et_super_number)
    void shipping_orderFocusChanage() {
        ModuleUtils.viewChange(ll_super_number, views);
        ModuleUtils.tvChange(activity, tv_super_number, textViews);
        ModuleUtils.etChange(activity, et_super_number, editTexts);
    }

    /**
     * 工单号
     */
    @BindView(R.id.ll_order_number)
    LinearLayout ll_order_number;
    @BindView(R.id.tv_order_number)
    TextView tv_order_number;
    @BindView(R.id.et_order_number)
    EditText et_order_number;

    @OnFocusChange(R.id.et_order_number)
    void item_noFocusChanage() {
        ModuleUtils.viewChange(ll_order_number, views);
        ModuleUtils.tvChange(activity, tv_order_number, textViews);
        ModuleUtils.etChange(activity, et_order_number, editTexts);
    }

    /**
     * 申请人
     */
    @BindView(R.id.ll_applicant)
    LinearLayout ll_applicant;
    @BindView(R.id.tv_applicant)
    TextView tv_applicant;
    @BindView(R.id.et_applicant)
    EditText et_applicant;

    @OnFocusChange(R.id.et_applicant)
    void customFocusChanage() {
        ModuleUtils.viewChange(ll_applicant, views);
        ModuleUtils.tvChange(activity, tv_applicant, textViews);
        ModuleUtils.etChange(activity, et_applicant, editTexts);
    }

    /**
     * 部门
     */
    @BindView(R.id.ll_department)
    LinearLayout ll_department;
    @BindView(R.id.tv_department)
    TextView tv_department;
    @BindView(R.id.et_department)
    EditText et_department;

    @OnFocusChange(R.id.et_department)
    void salesmanFocusChanage() {
        ModuleUtils.viewChange(ll_department, views);
        ModuleUtils.tvChange(activity, tv_department, textViews);
        ModuleUtils.etChange(activity, et_department, editTexts);
    }

    /**
     * 筛选框 计划日
     */
    @BindView(R.id.iv_plan_date)
    ImageView iv_plan_date;
    @BindView(R.id.tv_plan_date)
    TextView tv_plan_date;
    @BindView(R.id.ll_plan_date)
    LinearLayout ll_plan_date;
    @BindView(R.id.et_plan_date)
    EditText et_plan_date;
    @OnFocusChange(R.id.et_plan_date)
    void plan_dateFocusChanage() {
        ModuleUtils.viewChange(ll_plan_date, views);
        ModuleUtils.tvChange(activity, tv_plan_date, textViews);
        ModuleUtils.etChange(activity, et_plan_date, editTexts);
    }


    String startDate = "";
    String endDate = "";

    @OnClick(R.id.iv_plan_date)
    void dateClick(){
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                et_plan_date.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                et_plan_date.setText(showDate);
            }
        });
    }

    private final int SCANCODE = 1234;

    private String DATA = "data";

    private List<FilterResultOrderBean> dataList;

    @BindView(R.id.btn_search_sure)
    Button btn_search_sure;
    @OnClick(R.id.btn_search_sure)
    void search(){
        //待办事项展示
        FilterBean FilterBean = new FilterBean();
        try {
            showLoadingDialog();

            AccoutBean accoutBean = LoginLogic.getUserInfo();
            if(null == accoutBean){
                return;
            }
            FilterBean.setWarehouse_out_no(accoutBean.getWare());

            if(!StringUtils.isBlank(et_super_number.getText().toString().trim())){
                FilterBean.setDoc_no(et_super_number.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_order_number.getText().toString().trim())){
                FilterBean.setWo_no(et_order_number.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_applicant.getText().toString().trim())){
                FilterBean.setEmployee_no(et_applicant.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_department.getText().toString().trim())){
                FilterBean.setDepartment_no(et_department.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_plan_date.getText().toString())){
                FilterBean.setDate_begin(startDate);
                FilterBean.setDate_end(endDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        commonLogic.getOrderData(FilterBean, new CommonLogic.GetOrderListener() {
            @Override
            public void onSuccess(final List<FilterResultOrderBean> list) {
                dismissLoadingDialog();
                if(list.size() > 0){
                    mName.setText(R.string.title_production_leader);
                    ll_search_dialog.setVisibility(View.GONE);
                    scrollview.setVisibility(View.VISIBLE);
                    dataList = new ArrayList<FilterResultOrderBean>();
                    dataList = list;
                    adapter = new ProductionLeaderListAdapter(activity,list);
                    ry_list.setAdapter(adapter);

                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            Bundle bundle = new Bundle();
                            FilterResultOrderBean data = list.get(position);
                            bundle.putSerializable(DATA,data);
                            ActivityManagerUtils.startActivityBundleForResult(activity,ProductionLeaderActivity.class,bundle,SCANCODE);
                        }
                    });
                }else{
                    showFailedDialog(getResources().getString(R.string.nodate));
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
                ArrayList dataList = new ArrayList<FilterResultOrderBean>();
                adapter = new ProductionLeaderListAdapter(activity,dataList);
                ry_list.setAdapter(adapter);
            }
        });
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
//                adapter = new PickUpShipmentListAdapter(activity,dataList);
//                ryList.setAdapter(adapter);
//                onItemClick(dataList);
            }
        }else{
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
    }

    @Override
    protected void doBusiness() {
        et_plan_date.setKeyListener(null);
        commonLogic =  CommonLogic.getInstance(activity,ModuleCode.PRODUCTIONLEADER,mTimestamp.toString());
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(activity);
        ry_list.setLayoutManager(linearlayoutmanager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == SCANCODE){
                search();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(R.string.filter_condition);
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_productionleaderlist;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PRODUCTIONLEADER;
        return module;
    }
}
