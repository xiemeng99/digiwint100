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
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.stock.miscellaneousissues.MiscellaneousissuesInAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.stock.miscellaneousissues.MiscellaneousissuesInLogic;
import digiwin.library.constant.SharePreKey;
import digiwin.library.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;

import static digiwin.smartdepott100.login.loginlogic.LoginLogic.getUserInfo;

/**
 * 杂项收料  前置页面
 * @author wangyu
 */
public class MiscellaneousissuesInListActivity extends BaseTitleActivity {
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
     * 筛选框 杂收单号
     */
    @BindView(R.id.et_miscellaneous_in_no)
    EditText et_miscellaneous_in_no;
    /**
     * 筛选框 杂收单号
     */
    @BindView(R.id.ll_miscellaneous_in_no)
    LinearLayout ll_miscellaneous_in_no;
    /**
     * 筛选框 杂收单号
     */
    @BindView(R.id.tv_miscellaneous_in_no)
    TextView tv_miscellaneous_in_no;

    @OnFocusChange(R.id.et_miscellaneous_in_no)
    void workOrderFocusChange() {
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
    void personFocusChange() {
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
    void departmentFocusChange() {
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
    void planDateFocusChange() {
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
        module = ModuleCode.MISCELLANEOUSISSUESIN;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_miscellaneousissues_in_list;
    }

    @Override
    protected void doBusiness() {
        et_date.setKeyListener(null);
        sumShowBeanList=new ArrayList<>();
        mactivity = (MiscellaneousissuesInListActivity) activity;
        commonLogic = MiscellaneousissuesInLogic.getInstance(mactivity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
    }

    /**
     * 跳转扫描使用
     */
    public static final int SUMCODE = 1212;

    MiscellaneousissuesInLogic commonLogic;
    MiscellaneousissuesInListActivity mactivity;

    MiscellaneousissuesInAdapter adapter;
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
    void SearchDialog() {
        if (ll_search_dialog.getVisibility() == View.VISIBLE) {
            if (null != sumShowBeanList && sumShowBeanList.size() > 0) {
                ivScan.setVisibility(View.INVISIBLE);
                mName.setText(R.string.filter_result_list);
                ll_search_dialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
            }
        } else {
            ivScan.setVisibility(View.VISIBLE);
            mName.setText(getString(R.string.miscellaneous_issues_in)+getString(R.string.condition_filter));
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
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
                ActivityManagerUtils.startActivityBundleForResult(mactivity,MiscellaneousissuesInActivity.class, bundle, SUMCODE);
            }
        });
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.miscellaneous_issues_in)+getString(R.string.condition_filter));
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    /**
     * 清单展示
     */
    public void upDateList() {
        sumShowBeanList.clear();
        adapter = new MiscellaneousissuesInAdapter(activity, sumShowBeanList);
        ryList.setAdapter(adapter);
        showLoadingDialog();
        FilterBean filterBean = new FilterBean();
        Map<String,String> map=new HashMap<>();
        try {
            //仓库
            filterBean.setWarehouse_no(LoginLogic.getWare());
            //页数
            String o = (String) SharedPreferencesUtils.get(activity, SharePreKey.PAGE_SETTING, "10");
            filterBean.setPagesize(o);
            //杂收单号
            filterBean.setDoc_no(et_miscellaneous_in_no.getText().toString());
            //日期
            filterBean.setDate_begin(startDate);
            filterBean.setDate_end(endDate);
            //申请人
            filterBean.setEmployee_no(et_person.getText().toString());
            //部门
            filterBean.setDepartment_no(et_department.getText().toString());
            map = ObjectAndMapUtils.getValueMap(filterBean);
            showLoadingDialog();
            commonLogic.getMIIListData(map, new CommonLogic.GetDataListListener() {
                @Override
                public void onSuccess(List<FilterResultOrderBean> list) {
                    if (null != list && list.size() > 0) {
                        dismissLoadingDialog();
                        sumShowBeanList = list;
                        showData();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    try {
                        showFailedDialog(error);
                        adapter = new MiscellaneousissuesInAdapter(mactivity, sumShowBeanList);
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

    /**
     * 展示数据
     */
    private void showData() {
        try {
            //查询成功隐藏筛选界面，展示清单信息
            ivScan.setVisibility(View.INVISIBLE);
            mName.setText(getString(R.string.filter_result_list));
            ll_search_dialog.setVisibility(View.GONE);
            scrollview.setVisibility(View.VISIBLE);
            iv_title_setting.setVisibility(View.VISIBLE);
            adapter = new MiscellaneousissuesInAdapter(mactivity, sumShowBeanList);
            ryList.setAdapter(adapter);
            onItemClick();
        } catch (Exception e) {
            LogUtils.e(TAG, "showDates---Exception>" + e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 返回时刷新数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SUMCODE) {
                upDateList();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
