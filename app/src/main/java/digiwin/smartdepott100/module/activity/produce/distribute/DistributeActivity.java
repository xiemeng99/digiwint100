package digiwin.smartdepott100.module.activity.produce.distribute;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.adapter.produce.DistributeSumAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.bean.produce.DistributeOrderHeadData;
import digiwin.smartdepott100.module.bean.produce.DistributeSumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.distribute.DistributeLogic;

import static digiwin.smartdepott100.login.loginlogic.LoginLogic.getUserInfo;

/**
 * 生产配料 汇总界面
 * @author 唐孟宇
 */
public class DistributeActivity extends BaseFirstModuldeActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindViews({R.id.et_work_order, R.id.et_department, R.id.et_material_code,R.id.et_class_group,R.id.et_plan_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_workorder, R.id.ll_department, R.id.ll_material_code, R.id.ll_class_group, R.id.ll_plan_date})
    List<View> views;
    @BindViews({R.id.tv_work_order, R.id.tv_department, R.id.tv_material_code, R.id.tv_class_group, R.id.tv_plan_date})
    List<TextView> textViews;

    /**
     * 筛选框 工单号
     */
    @BindView(R.id.et_work_order)
    EditText et_work_order;
    /**
     * 筛选框 工单号
     */
    @BindView(R.id.ll_workorder)
    LinearLayout ll_workorder;
    /**
     * 筛选框 工单号
     */
    @BindView(R.id.tv_work_order)
    TextView tv_work_order;

    @OnFocusChange(R.id.et_work_order)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(ll_workorder, views);
        ModuleUtils.etChange(activity, et_work_order, editTexts);
        ModuleUtils.tvChange(activity, tv_work_order, textViews);
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
     * 筛选框 班组
     */
    @BindView(R.id.et_class_group)
    EditText et_class_group;

    /**
     * 筛选框 班组
     */
    @BindView(R.id.ll_class_group)
    LinearLayout ll_class_group;
    /**
     * 筛选框 班组
     */
    @BindView(R.id.tv_class_group)
    TextView tv_class_group;

    @OnFocusChange(R.id.et_class_group)
    void classGroupFocusChanage() {
        ModuleUtils.viewChange(ll_class_group, views);
        ModuleUtils.etChange(activity, et_class_group, editTexts);
        ModuleUtils.tvChange(activity, tv_class_group, textViews);
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
     * 拨出仓库
     */
    @BindView(R.id.tv_head_out_warehouse)
    TextView tv_head_out_warehouse;
    /**
     * 拨入仓库
     */
    @BindView(R.id.tv_head_in_warehouse)
    TextView tv_head_in_warehouse;
    /**
     * 需求班组
     */
    @BindView(R.id.tv_head_class_group)
    TextView tv_head_class_group;
    /**
     * 需求部门
     */
    @BindView(R.id.tv_head_department)
    TextView tv_head_department;

    @BindView(R.id.commit)
    Button commt;

    DistributeActivity distributeActivity;

    /**
     * 跳转明细使用
     */
    public static final int DETAILCODE = 1234;

    /**
     * 跳转扫描使用
     */
    public static final int  SCANCODE = 1235;

    CommonLogic commonLogic;

    DistributeActivity pactivity;
    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog(){
        if(ll_search_dialog.getVisibility() == View.VISIBLE){
            if(null != sumShowBeanList && sumShowBeanList.size()>0){
                ll_search_dialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
                commt.setVisibility(View.VISIBLE);
                adapter = new DistributeSumAdapter(pactivity,sumShowBeanList);
                ryList.setAdapter(adapter);
                onItemClick();
            }
        }else {
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
            commt.setVisibility(View.GONE);
        }
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
        module = ModuleCode.DISTRIBUTE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_distribute;
    }

    @Override
    protected void doBusiness() {
        et_plan_date.setKeyListener(null);
        distributeActivity = this;
        pactivity = (DistributeActivity) activity;
        logic = DistributeLogic.getInstance(distributeActivity,module,pactivity.mTimestamp.toString());
        commonLogic = CommonLogic.getInstance(distributeActivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        SearchDialog();
        upDateFlag = false;
    }

    /**
     * 点击item跳转到扫描界面
     */
    private void onItemClick(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                DistributeSumShowBean bean = sumShowBeanList.get(position);
                DistributeOrderHeadData headData = headDataLists.get(0);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean",bean);
                bundle.putSerializable("headData",headData);
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                ActivityManagerUtils.startActivityBundleForResult(pactivity,DistributeScanActivity.class,bundle,SCANCODE);
            }
        });
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.distribute);
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    @OnClick(R.id.commit)
    void commit() {
        if (!upDateFlag) {
            showFailedDialog(R.string.nodate);
            return;
        }
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                commitData();
            }

            @Override
            public void onCallback2() {

            }
        });
    }

    private void commitData() {
        showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        commonLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        adapter = new DistributeSumAdapter(pactivity,new ArrayList<DistributeSumShowBean>());
                        ryList.setAdapter(adapter);
                        mTimestamp = new StringBuilder();
                        pactivity.createNewModuleId(module);
                        commonLogic = CommonLogic.getInstance(distributeActivity, module, mTimestamp.toString());
                        logic = DistributeLogic.getInstance(distributeActivity,module,mTimestamp.toString());
                        tv_head_class_group.setText("");
                        tv_head_department.setText("");
                        tv_head_in_warehouse.setText("");
                        tv_head_out_warehouse.setText("");
                        ll_search_dialog.setVisibility(View.VISIBLE);
                        scrollview.setVisibility(View.GONE);
                        commt.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }

    DistributeLogic logic;
    private boolean upDateFlag;

    DistributeSumAdapter adapter;

    List<DistributeSumShowBean> sumShowBeanList;
    List<DistributeOrderHeadData> headDataLists;

    /**
     * 汇总展示
     */
    public void upDateList() {

        FilterBean filterBean = new FilterBean();
        try {
            AccoutBean accoutBean = getUserInfo();
            if(null == accoutBean){
                return;
            }
            Map<String, String> map = new HashMap<>();
            //仓库
            filterBean.setWarehouse_no(accoutBean.getWare());
            //工单号
            if(!StringUtils.isBlank(et_work_order.getText().toString())){
                filterBean.setWo_no(et_work_order.getText().toString());
            }
            //部门
            if(!StringUtils.isBlank(et_department.getText().toString())){
                filterBean.setDepartment_no(et_department.getText().toString());
            }
            //料号
            if(!StringUtils.isBlank(et_material_code.getText().toString())){
                filterBean.setItem_no(et_material_code.getText().toString());
            }
            //班组
            if(!StringUtils.isBlank(et_class_group.getText().toString())){
                filterBean.setWorkgroup_no(et_class_group.getText().toString());
            }
            //计划日
            if(!StringUtils.isBlank(et_plan_date.getText().toString())){
                filterBean.setDate_begin(startDate);
                filterBean.setDate_end(endDate);
            }
            map = ObjectAndMapUtils.getValueMap(filterBean);
            showLoadingDialog();
            logic.getSum(map, new DistributeLogic.GetSumListener(){
                @Override
                public void onSuccess(List<DistributeOrderHeadData> headDataList,List<DistributeSumShowBean> list) {
                    if(null != list && list.size()>0){
                    dismissLoadingDialog();
                    //查询成功隐藏筛选界面，展示汇总信息
                    ll_search_dialog.setVisibility(View.GONE);
                    scrollview.setVisibility(View.VISIBLE);
                    commt.setVisibility(View.VISIBLE);
                    iv_title_setting.setVisibility(View.VISIBLE);
                    sumShowBeanList = new ArrayList<DistributeSumShowBean>();
                    sumShowBeanList = list;
                    //单头信息
                    if(headDataList.size() >0){
                        headDataLists = new ArrayList<DistributeOrderHeadData>();
                        headDataLists = headDataList;
                        DistributeOrderHeadData headData = headDataList.get(0);
                        tv_head_class_group.setText(headData.getWorkgroup_no());
                        tv_head_department.setText(headData.getDepartment_no());
                        tv_head_in_warehouse.setText(headData.getWarein());
                        tv_head_out_warehouse.setText(headData.getWareout());
                    }
                    adapter = new DistributeSumAdapter(distributeActivity, sumShowBeanList);
                    ryList.setAdapter(adapter);
                    upDateFlag = true;
                    onItemClick();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    upDateFlag = false;
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<DistributeSumShowBean>();
                        adapter = new DistributeSumAdapter(distributeActivity, sumShowBeanList);
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
    /**
     * 查看明细
     */
    public void getDetail(final DistributeSumShowBean sumShowBean) {
        Map<String, String> map = new HashMap<String, String>();
        showLoadingDialog();
        map.put(AddressContants.ITEM_NO, sumShowBean.getItem_no());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                if(null != detailShowBeen && detailShowBeen.size()>0){
                    Bundle bundle = new Bundle();
                    SumShowBean bean = new SumShowBean();
                    bean.setItem_no(sumShowBean.getItem_no());
                    bean.setItem_name(sumShowBean.getItem_name());
                    bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                    bundle.putString(CommonDetailActivity.MODULECODE, module);
                    bundle.putSerializable(CommonDetailActivity.ONESUM, bean);
                    bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                    dismissLoadingDialog();
                    ActivityManagerUtils.startActivityBundleForResult(distributeActivity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{

            if(requestCode == DETAILCODE || requestCode ==SCANCODE){
                adapter = new DistributeSumAdapter(pactivity,new ArrayList<DistributeSumShowBean>());
                ryList.setAdapter(adapter);
                upDateList();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }
}
