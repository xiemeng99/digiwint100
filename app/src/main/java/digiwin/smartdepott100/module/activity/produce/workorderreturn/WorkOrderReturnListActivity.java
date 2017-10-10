package digiwin.smartdepott100.module.activity.produce.workorderreturn;

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
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.produce.WorkOrderReturnListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.WorkOrderReturnLogic;


/**
 * @author xiemeng
 * @des 依工单退料
 * @date 2017/3/24
 */

public class WorkOrderReturnListActivity extends BaseTitleActivity {
    /**
     * 控件集合
     */
    @BindViews({R.id.et_workorder, R.id.et_endprduct, R.id.et_lower_item_no, R.id.et_depart_supplier, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_workorder, R.id.ll_endprduct, R.id.ll_lower_item_no, R.id.ll_depart_supplier, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_workorder, R.id.tv_endprduct, R.id.tv_lower_item_no, R.id.tv_depart_supplier, R.id.tv_date})
    List<TextView> textViews;

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_workorder)
    TextView tvWorkorder;
    @BindView(R.id.et_workorder)
    EditText etWorkorder;
    @BindView(R.id.ll_workorder)
    LinearLayout llWorkorder;
    @BindView(R.id.tv_endprduct)
    TextView tvEndprduct;
    @BindView(R.id.et_endprduct)
    EditText etEndprduct;
    @BindView(R.id.ll_endprduct)
    LinearLayout llEndprduct;
    @BindView(R.id.tv_lower_item_no)
    TextView tvLowerItemNo;
    @BindView(R.id.et_lower_item_no)
    EditText etLowerItemNo;
    @BindView(R.id.ll_lower_item_no)
    LinearLayout llLowerItemNo;
    @BindView(R.id.tv_depart_supplier)
    TextView tvDepartSupplier;
    @BindView(R.id.et_depart_supplier)
    EditText etDepartSupplier;
    @BindView(R.id.ll_depart_supplier)
    LinearLayout llDepartSupplier;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.btn_search_sure)
    Button btnSearchSure;
    @BindView(R.id.ll_search_input)
    LinearLayout llSearchInput;
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 监听焦点变化
     */
    @OnFocusChange(R.id.et_workorder)
    void workOrderFocusChange() {
        ModuleUtils.viewChange(llWorkorder, views);
        ModuleUtils.etChange(activity, etWorkorder, editTexts);
        ModuleUtils.tvChange(activity, tvWorkorder, textViews);
    }

    @OnFocusChange(R.id.et_endprduct)
    void endProductFocusChange() {
        ModuleUtils.viewChange(llEndprduct, views);
        ModuleUtils.etChange(activity, etEndprduct, editTexts);
        ModuleUtils.tvChange(activity, tvEndprduct, textViews);
    }

    @OnFocusChange(R.id.et_lower_item_no)
    void lowerItemNoFocusChange() {
        ModuleUtils.viewChange(llLowerItemNo, views);
        ModuleUtils.etChange(activity, etLowerItemNo, editTexts);
        ModuleUtils.tvChange(activity, tvLowerItemNo, textViews);
    }

    @OnFocusChange(R.id.et_depart_supplier)
    void departSupplierFocusChange() {
        ModuleUtils.viewChange(llDepartSupplier, views);
        ModuleUtils.etChange(activity, etDepartSupplier, editTexts);
        ModuleUtils.tvChange(activity, tvDepartSupplier, textViews);
    }

    @OnFocusChange(R.id.et_date)
    void dateFocusChange() {
        ModuleUtils.viewChange(llDate, views);
        ModuleUtils.etChange(activity, etDate, editTexts);
        ModuleUtils.tvChange(activity, tvDate, textViews);
    }

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

    @OnClick(R.id.btn_search_sure)
    void search_sure() {
        onUpdate();
    }
    @BindView(R.id.un_com)
    ImageView unCom;
//    @OnClick(R.id.un_com)
//    void toUnCom() {
//        Bundle bundle = new Bundle();
//        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
//        bundle.putString(HaveSourceUnComActivity.MODULECODE, module);
//        ActivityManagerUtils.startActivityForBundleData(activity, HaveSourceUnComActivity.class, bundle);
//    }


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

    List<FilterResultOrderBean> list;
    /**
     * 是否正在搜索
     */
    private boolean isSearching;

    WorkOrderReturnLogic logic;

    BaseRecyclerAdapter adapter;

    public static  String filterBean="filterBean";

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_workorderreturn_list;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.work_order_return)+""+getString(R.string.list));
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching = true;
        unCom.setVisibility(View.GONE);
    }

    @Override
    protected void doBusiness() {
        initData();
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.WORKORDERRETURN;
        return module;
    }

    private void initData() {
        etDate.setKeyListener(null);
        list = new ArrayList<>();
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        logic = WorkOrderReturnLogic.getInstance(context, module, mTimestamp.toString());
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
        try {
            list.clear();
            adapter = new WorkOrderReturnListAdapter(activity, list);
            ryList.setAdapter(adapter);
            showLoadingDialog();
            FilterBean filterBean = new FilterBean();
            filterBean.setWarehouse_no(LoginLogic.getWare());
            filterBean.setDoc_no(etWorkorder.getText().toString());
            filterBean.setItem_no(etEndprduct.getText().toString());
            filterBean.setLow_order_item_no(etLowerItemNo.getText().toString());
            filterBean.setDepartment_no(etDepartSupplier.getText().toString());
            filterBean.setDate_begin(startDate);
            filterBean.setDate_end(endDate);
            logic.getWORList(filterBean, new CommonLogic.GetDataListListener() {
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
        }catch (Exception e){
            LogUtils.e(TAG,"onUpdate"+e);
        }
    }

    /**
     * 展示数据
     */
    private void showData() {
        try {
            isSearching = true;
            ryList.setVisibility(View.VISIBLE);
            llSearchInput.setVisibility(View.GONE);
            adapter = new WorkOrderReturnListAdapter(activity, list);
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
        } catch (Exception e) {
            LogUtils.e(TAG, "showDatas---Exception>" + e);
        }
    }

    /**
     * 点击条目
     */
    private void itemClick(List<FilterResultOrderBean> clickBeen, int position) {
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable(WorkOrderReturnListActivity.filterBean, clickBeen.get(position));
            ActivityManagerUtils.startActivityBundleForResult(activity, WorkOrderReturnActivity.class, bundle, TOCOMMIT);
            dismissLoadingDialog();
        } catch (Exception e) {
            LogUtils.e(TAG, "itemClick" + e);
        }
    }
}
