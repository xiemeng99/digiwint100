package digiwin.smartdepott100.module.activity.stock.miscellaneousissues;

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
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.module.adapter.stock.miscellaneousissues.MiscellaneousissuesOutAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.stock.miscellaneousissues.MiscellaneousissuesOutLogic;
import digiwin.library.constant.SharePreKey;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;


import static digiwin.smartdepott100.login.loginlogic.LoginLogic.getUserInfo;

/**
 * 杂项发料  前置页面
 * @author sj
 */
public class MiscellaneousissuesOutListActivity extends BaseTitleActivity {
    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindViews({R.id.et_miscellaneous_in_no, R.id.et_person, R.id.et_department, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_miscellaneous_in_no, R.id.ll_person, R.id.ll_department, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_miscellaneous_in_no, R.id.tv_person, R.id.tv_department, R.id.tv_date})
    List<TextView> textViews;

    /**
     * 筛选框 杂发单号
     */
    @BindView(R.id.et_miscellaneous_in_no)
    EditText et_miscellaneous_in_no;
    /**
     * 筛选框 杂发单号
     */
    @BindView(R.id.ll_miscellaneous_in_no)
    LinearLayout ll_miscellaneous_in_no;
    /**
     * 筛选框 杂发单号
     */
    @BindView(R.id.tv_miscellaneous_in_no)
    TextView tv_miscellaneous_in_no;

    @OnFocusChange(R.id.et_miscellaneous_in_no)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(ll_miscellaneous_in_no, views);
        ModuleUtils.etChange(activity, et_miscellaneous_in_no, editTexts);
        ModuleUtils.tvChange(activity, tv_miscellaneous_in_no, textViews);
    }

    /**
     * 筛选框 人员
     */
    @BindView(R.id.et_person)
    EditText et_person;
    /**
     * 筛选框 人员
     */
    @BindView(R.id.ll_person)
    LinearLayout ll_person;
    /**
     * 筛选框 人员
     */
    @BindView(R.id.tv_person)
    TextView tv_person;

    @OnFocusChange(R.id.et_person)
    void personFocusChanage() {
        ModuleUtils.viewChange(ll_person, views);
        ModuleUtils.etChange(activity, et_person, editTexts);
        ModuleUtils.tvChange(activity, tv_person, textViews);
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
        DatePickerUtils.getDoubleDate(mactivity, new DatePickerUtils.GetDoubleDateListener() {
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


    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.MISCELLANEOUSISSUESOUT;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_miscellaneousissues_out_list;
    }

    @Override
    protected void doBusiness() {
        et_date.setKeyListener(null);
        mactivity = (MiscellaneousissuesOutListActivity) activity;
        commonLogic = MiscellaneousissuesOutLogic.getInstance(mactivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);

        searchDialog();
    }

    /**
     * 跳转扫描使用
     */
    public static final int SUMCODE = 1212;

    MiscellaneousissuesOutLogic commonLogic;
    MiscellaneousissuesOutListActivity mactivity;

    MiscellaneousissuesOutAdapter adapter;
    List<FilterResultOrderBean> sumShowBeanList;

    /**
     * 点击确定，筛选
     */
    @OnClick(R.id.btn_search_sure)
    void Search() {
        upDateList();
    }

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
            ivScan.setVisibility(View.VISIBLE);
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
    }

    /**
     * 点击item跳转到汇总界面
     */
    private void itemClick(List<FilterResultOrderBean> clickBeen, int position) {
        final FilterResultOrderBean orderData = clickBeen.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AddressContants.ORDERDATA, orderData);
        ActivityManagerUtils.startActivityBundleForResult(mactivity,MiscellaneousissuesOutActivity.class, bundle, SUMCODE);
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.miscellaneous_issues_out_list)+getString(R.string.list));
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
        ivScan.setVisibility(View.VISIBLE);
    }

    /**
     * 清单展示
     */
    public void upDateList() {
        FilterBean filterBean = new FilterBean();
        Map<String,String> map=new HashMap<>();
        try {
            AccoutBean accoutBean = getUserInfo();
            if (null == accoutBean) {
                return;
            }
            //仓库
            filterBean.setWarehouse_no(accoutBean.getWare());
            //页数
            String o = (String) SharedPreferencesUtils.get(activity, SharePreKey.PAGE_SETTING, "10");
            //杂发单号
            if (!StringUtils.isBlank(et_miscellaneous_in_no.getText().toString())) {
                filterBean.setDoc_no(et_miscellaneous_in_no.getText().toString());
            }
            //日期
            if (!StringUtils.isBlank(et_date.getText().toString())) {
                filterBean.setDate_begin(startDate);
                filterBean.setDate_end(endDate);
            }
            //申请人
            if (!StringUtils.isBlank(et_person.getText().toString())){
                filterBean.setEmployee_no(et_person.getText().toString());
            }
            //部门
            if (!StringUtils.isBlank(et_department.getText().toString())){
                filterBean.setDepartment_no(et_department.getText().toString());
            }
            map= ObjectAndMapUtils.getValueMap(filterBean);
            showLoadingDialog();
            commonLogic.getMIIListData(map, new CommonLogic.GetDataListListener() {
                @Override
                public void onSuccess(List<FilterResultOrderBean> list) {
                    dismissLoadingDialog();
                    if (null != list && list.size() > 0) {
                        //查询成功隐藏筛选界面，展示清单信息
                        ll_search_dialog.setVisibility(View.GONE);
                        scrollview.setVisibility(View.VISIBLE);
                        iv_title_setting.setVisibility(View.VISIBLE);
                        ivScan.setVisibility(View.INVISIBLE);
                        sumShowBeanList = new ArrayList<FilterResultOrderBean>();
                        sumShowBeanList = list;
                        adapter = new MiscellaneousissuesOutAdapter(mactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        adapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                itemClick(sumShowBeanList, position);
                            }
                        });
                        if (autoSkip&&list.size() == 1) {
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
                        adapter = new MiscellaneousissuesOutAdapter(mactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "updateList--getSum--onFailed" + e);
                    }
                }
            });

        }catch(Exception e) {
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
                adapter = new MiscellaneousissuesOutAdapter(mactivity,new ArrayList<FilterResultOrderBean>());
                ryList.setAdapter(adapter);
                upDateList();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
