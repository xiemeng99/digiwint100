package digiwin.smartdepott100.module.activity.produce.suitpickinghalf;

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
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.produce.SuitPickingHalfListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.SuitPickingHalfLogic;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;

/**
 * @author xiemeng
 * @des 生产成套领料 半成品列表
 * @date 2017/5/28 15:34
 */

public class SuitPickingHalfListAcitivity extends BaseTitleActivity {

    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_picking_no)
    TextView tvPickingNo;
    @BindView(R.id.et_picking_no)
    EditText etPickingNo;
    @BindView(R.id.ll_picking_no)
    LinearLayout llPickingNo;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.et_department)
    EditText etDepartment;
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.et_person)
    EditText etPerson;
    @BindView(R.id.ll_person)
    LinearLayout llPerson;

    @BindView(R.id.date)
    ImageView date;
    @BindView(R.id.btn_search_sure)
    Button btnSearchSure;
    @BindView(R.id.ll_search_input)
    LinearLayout llSearchInput;
    private SuitPickingHalfLogic logic;

    private boolean isSearching;


    /**
     * 日期
     */
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.tv_date)
    TextView tvDate;

    @OnClick(R.id.date)
    void getDate() {
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String mShowDate) {
                startDate = mStartDate;
                endDate = mEndDate;
                etDate.setText(mShowDate);
                etDate.requestFocus();
            }
        });
    }


    @OnClick(R.id.btn_search_sure)
    void search_sure() {
        onUpdate();
    }

    /**
     * 筛选按钮
     */
    @BindView(R.id.iv_title_setting)
    ImageView search;

    @OnClick(R.id.iv_title_setting)
    void search() {
        if (isSearching) {
            isSearching = false;
            ryList.setVisibility(View.VISIBLE);
            llSearchInput.setVisibility(View.GONE);
            return;
        } else {
            isSearching = true;
            ryList.setVisibility(View.GONE);
            llSearchInput.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 控件集合
     */
    @BindViews({R.id.et_picking_no, R.id.et_department, R.id.et_person, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_picking_no, R.id.ll_department, R.id.ll_person, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_picking_no, R.id.tv_department, R.id.tv_person, R.id.tv_date})
    List<TextView> textViews;

    /**
     * 监听焦点变化
     */
    @OnFocusChange(R.id.et_picking_no)
    void generalFocusChange() {
        ModuleUtils.viewChange(llPickingNo, views);
        ModuleUtils.etChange(activity, etPickingNo, editTexts);
        ModuleUtils.tvChange(activity, tvPickingNo, textViews);
    }


    @OnFocusChange(R.id.et_department)
    void departmentFocusChange() {
        ModuleUtils.viewChange(llDepartment, views);
        ModuleUtils.etChange(activity, etDepartment, editTexts);
        ModuleUtils.tvChange(activity, tvDepartment, textViews);
    }

    @OnFocusChange(R.id.et_person)
    void applicationFocusChange() {
        ModuleUtils.viewChange(llPerson, views);
        ModuleUtils.etChange(activity, etPerson, editTexts);
        ModuleUtils.tvChange(activity, tvPerson, textViews);
    }


    @OnFocusChange(R.id.et_date)
    void dateFocusChange() {
        ModuleUtils.viewChange(llDate, views);
        ModuleUtils.etChange(activity, etDate, editTexts);
        ModuleUtils.tvChange(activity, tvDate, textViews);
    }

    SuitPickingHalfListAdapter adapter;

    List<FilterResultOrderBean> list;
    /**
     * 开始日期
     */
    String startDate;
    /**
     * 结束日期
     */
    String endDate;
    /**
     * 返回刷新页面
     */
    int TOCOMMIT = 1001;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.SUITPICKINGHALF;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.suitpickinghalf) + getString(R.string.list));
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching = true;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_suitpickinghalf_list;
    }

    @Override
    protected void doBusiness() {
        etDate.setKeyListener(null);
        list = new ArrayList<>();
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        logic = SuitPickingHalfLogic.getInstance(activity, module, mTimestamp.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TOCOMMIT) {
            onUpdate();
        }
    }

    /**
     * 更新
     */
    private void onUpdate() {
        list.clear();
        adapter = new SuitPickingHalfListAdapter(activity, list);
        ryList.setAdapter(adapter);
        showLoadingDialog();
        FilterBean filterBean = new FilterBean();
        filterBean.setWarehouse_out_no(LoginLogic.getWare());
        filterBean.setDoc_no(etPickingNo.getText().toString());
        filterBean.setDepartment_no(etDepartment.getText().toString());
        filterBean.setEmployee_no(etPerson.getText().toString());
        filterBean.setDate_begin(startDate);
        filterBean.setDate_end(endDate);
        logic.getSuitPickingList(filterBean, new CommonLogic.GetDataListListener() {
            @Override
            public void onSuccess(List<FilterResultOrderBean> masterDatas) {
                list = masterDatas;
                showData();
                dismissLoadingDialog();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    /**
     * 展示数据
     */
    private void showData() {
        isSearching = true;
        ryList.setVisibility(View.VISIBLE);
        llSearchInput.setVisibility(View.GONE);
        adapter = new SuitPickingHalfListAdapter(activity, list);
        ryList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, final int position) {
                itemClick(list,position);
            }
        });
        if (autoSkip&&list.size() == 1) {
            itemClick(list, 0);
        }
        autoSkip=true;
    }

    /**
     * 点击条目
     */
    private void itemClick(List<FilterResultOrderBean> clickBeen, int position) {
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable(SuitPickingHalfActivity.filterBean, clickBeen.get(position));
            ActivityManagerUtils.startActivityBundleForResult(activity, SuitPickingHalfActivity.class, bundle, TOCOMMIT);
            dismissLoadingDialog();
        } catch (Exception e) {
            LogUtils.e(TAG, "itemClick" + e);
        }
    }


}