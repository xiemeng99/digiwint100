package digiwin.smartdepott100.module.activity.stock.postallocate;

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
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.logic.stock.PostAllocateLogic;
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
import digiwin.smartdepott100.module.adapter.stock.postallocate.PostAllocateOrderAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @des  调拨过账
 * @date 2017/6/9
 * @author xiemeng
 */
public class PostAllocateActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindViews({R.id.et_transfers_list_no, R.id.et_applicant, R.id.et_department, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_transfers_list_no, R.id.ll_applicant, R.id.ll_department, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_transfers_list_no, R.id.tv_applicant, R.id.tv_department, R.id.tv_date})
    List<TextView> textViews;

    /**
     * 筛选框 调拨单号
     */
    @BindView(R.id.et_transfers_list_no)
    EditText etTransfersListNo;
    /**
     * 筛选框 调拨单号
     */
    @BindView(R.id.ll_transfers_list_no)
    LinearLayout llTransfersListNo;
    /**
     * 筛选框 调拨单号
     */
    @BindView(R.id.tv_transfers_list_no)
    TextView tvTransfersListNo;

    @OnFocusChange(R.id.et_transfers_list_no)
    void allocateOrderFocusChanage() {
        ModuleUtils.viewChange(llTransfersListNo, views);
        ModuleUtils.etChange(activity, etTransfersListNo, editTexts);
        ModuleUtils.tvChange(activity, tvTransfersListNo, textViews);
    }

    /**
     * 筛选框 申请人
     */
    @BindView(R.id.et_applicant)
    EditText etApplicant;

    /**
     * 筛选框 申请人
     */
    @BindView(R.id.ll_applicant)
    LinearLayout llApplicant;
    /**
     * 筛选框 申请人
     */
    @BindView(R.id.tv_applicant)
    TextView tvApplicant;

    @OnFocusChange(R.id.et_applicant)
    void applicantFocusChanage() {
        ModuleUtils.viewChange(llApplicant, views);
        ModuleUtils.etChange(activity, etApplicant, editTexts);
        ModuleUtils.tvChange(activity, tvApplicant, textViews);
    }

    /**
     * 筛选框 部门
     */
    @BindView(R.id.et_department)
    EditText etDepartment;

    /**
     * 筛选框 部门
     */
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    /**
     * 筛选框 部门
     */
    @BindView(R.id.tv_department)
    TextView tvDepartment;

    @OnFocusChange(R.id.et_department)
    void departmentFocusChanage() {
        ModuleUtils.viewChange(llDepartment, views);
        ModuleUtils.etChange(activity, etDepartment, editTexts);
        ModuleUtils.tvChange(activity, tvDepartment, textViews);
    }


    /**
     * 筛选框 日期
     */
    @BindView(R.id.et_date)
    EditText etDate;

    /**
     * 筛选框 日期
     */
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    /**
     * 筛选框 日期
     */
    @BindView(R.id.tv_date)
    TextView tvDate;

    /**
     * 筛选框 日期
     */
    @BindView(R.id.iv_date)
    ImageView ivDate;

    String startDate = "";
    String endDate = "";

    @OnClick(R.id.iv_date)
    void dateClick() {
        DatePickerUtils.getDoubleDate(pactivity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                etDate.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                etDate.setText(showDate);
            }
        });
    }

    @OnFocusChange(R.id.et_date)
    void planDateFocusChanage() {
        ModuleUtils.viewChange(llDate, views);
        ModuleUtils.etChange(activity, etDate, editTexts);
        ModuleUtils.tvChange(activity, tvDate, textViews);
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

    PostAllocateLogic commonLogic;

    PostAllocateActivity pactivity;
    /**
     * 拨入拨出状态
     */
    //private String doc_stus;

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void searchDialog() {
        if (ll_search_dialog.getVisibility() == View.VISIBLE) {
            if (null != sumShowBeanList && sumShowBeanList.size() > 0) {
                ll_search_dialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);

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
        upDateList();
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.POSTALLOCATE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_post_allocate_order;
    }

    @Override
    protected void doBusiness() {
        startDate="";
        endDate="";
        //doc_stus = "N";//默认拨出
        etDate.setKeyListener(null);
        pactivity = (PostAllocateActivity) activity;
        commonLogic = PostAllocateLogic.getInstance(pactivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
      //  showOutOrIn();
    }

//    private void showOutOrIn() {
//        ChooseAllotDailog.showChooseAllotDailog(context, new OnDialogTwoListener() {
//            @Override
//            public void onCallback1() {
//                doc_stus = "O";
//                initNavigationTitle();
//            }
//
//            @Override
//            public void onCallback2() {
//                doc_stus = "N";
//                initNavigationTitle();
//            }
//        });
//    }


    /**
     * 点击item跳转到汇总界面
     */
    private void itemClick(List<FilterResultOrderBean> clickBeen, int position) {
        FilterResultOrderBean orderData = clickBeen.get(position);
        Bundle bundle = new Bundle();
//        bundle.putString(AddressContants.DOC_NO, doc_stus);
        bundle.putSerializable(AddressContants.ORDERDATA, orderData);
        ActivityManagerUtils.startActivityBundleForResult(pactivity, PostAllocateScanActivity.class, bundle, SUMCODE);
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
//        if ("N".equals(doc_stus)) {
//            mName.setText(getString(R.string.allocate_out) + getString(R.string.list));
//        } else {
//            mName.setText(getString(R.string.allocate_in) + getString(R.string.list));
//        }
        mName.setText(getString(R.string.title_post_allocate) + getString(R.string.list));
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    PostAllocateOrderAdapter adapter;

    List<FilterResultOrderBean> sumShowBeanList;

    /**
     * 清单展示
     */
    public void upDateList() {
        FilterBean filterBean = new FilterBean();
        try {
            //仓库
            filterBean.setWarehouse_in_no(LoginLogic.getWare());
            //调拨单号
            if (!StringUtils.isBlank(etTransfersListNo.getText().toString())) {
                filterBean.setDoc_no(etTransfersListNo.getText().toString());
            }
            //申请人
            if (!StringUtils.isBlank(etApplicant.getText().toString())) {
                filterBean.setEmployee_no(etApplicant.getText().toString());
            }
            //部门
            if (!StringUtils.isBlank(etDepartment.getText().toString())) {
                filterBean.setDepartment_no(etDepartment.getText().toString());
            }
            //日期
            if (!StringUtils.isBlank(etDate.getText().toString())) {
                filterBean.setDate_begin(startDate);
                filterBean.setDate_end(endDate);
            }
//            filterBean.setDoc_stus(doc_stus);
            showLoadingDialog();
            commonLogic.getPostAllocateList(filterBean, new CommonLogic.GetDataListListener() {
                @Override
                public void onSuccess(List<FilterResultOrderBean> list) {
                    dismissLoadingDialog();
                    if (null != list && list.size() > 0) {
                        //查询成功隐藏筛选界面，展示汇总信息
                        ll_search_dialog.setVisibility(View.GONE);
                        scrollview.setVisibility(View.VISIBLE);
                        iv_title_setting.setVisibility(View.VISIBLE);
                        sumShowBeanList = new ArrayList<FilterResultOrderBean>();
                        sumShowBeanList = list;
                        adapter = new PostAllocateOrderAdapter(pactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        adapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                itemClick(sumShowBeanList, position);
                            }
                        });
                        if (autoSkip&&sumShowBeanList.size() == 1) {
                            itemClick(sumShowBeanList, 0);
                        }
                        autoSkip=true;
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<FilterResultOrderBean>();
                        adapter = new PostAllocateOrderAdapter(pactivity, sumShowBeanList);
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
                adapter = new PostAllocateOrderAdapter(pactivity, new ArrayList<FilterResultOrderBean>());
                ryList.setAdapter(adapter);
                upDateList();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
