package digiwin.smartdepott100.module.activity.sale.saleoutlet;

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
import digiwin.library.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.sale.SaleOutletListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;



/**
 * @author xiemeng
 * @des 销售出库列表
 * @date 2017/3/19
 */
public class SaleOutletListActivity extends BaseTitleActivity {

    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_general_number)
    TextView tvGeneralNumber;
    @BindView(R.id.et_general_number)
    EditText etGeneralNumber;
    @BindView(R.id.ll_general_number_list)
    LinearLayout llGeneralNumberList;
    @BindView(R.id.tv_custom_no)
    TextView tvCustomNo;
    @BindView(R.id.et_custom_no)
    EditText etCustomNo;
    @BindView(R.id.ll_custom_no)
    LinearLayout llCustomNo;
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
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.et_item_no)
    EditText etItemNo;
    @BindView(R.id.ll_item_no)
    LinearLayout llItemNo;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.et_item_name)
    EditText etItemName;
    @BindView(R.id.ll_item_name)
    LinearLayout llItemName;
    @BindView(R.id.date)
    ImageView date;
    @BindView(R.id.btn_search_sure)
    Button btnSearchSure;
    @BindView(R.id.ll_search_input)
    LinearLayout llSearchInput;
    private CommonLogic logic;

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
    @BindViews({R.id.et_general_number,R.id.et_custom_no, R.id.et_department, R.id.et_person, R.id.et_item_no, R.id.et_item_name, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_general_number_list, R.id.ll_custom_no, R.id.ll_department, R.id.ll_person, R.id.ll_item_name, R.id.ll_item_no, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_general_number, R.id.tv_custom_no, R.id.tv_department, R.id.tv_person, R.id.tv_item_name,R.id.tv_item_no, R.id.tv_date})
    List<TextView> textViews;

    /**
     * 监听焦点变化
     */
    @OnFocusChange(R.id.et_general_number)
    void generalFocusChange() {
        ModuleUtils.viewChange(llGeneralNumberList, views);
        ModuleUtils.etChange(activity, etGeneralNumber, editTexts);
        ModuleUtils.tvChange(activity, tvGeneralNumber, textViews);
    }

    @OnFocusChange(R.id.et_custom_no)
    void customFocusChange() {
        ModuleUtils.viewChange(llCustomNo, views);
        ModuleUtils.etChange(activity, etCustomNo, editTexts);
        ModuleUtils.tvChange(activity, tvCustomNo, textViews);
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

    @OnFocusChange(R.id.et_item_name)
    void in_warehouseFocusChange() {
        ModuleUtils.viewChange(llItemName, views);
        ModuleUtils.etChange(activity, etItemName, editTexts);
        ModuleUtils.tvChange(activity, tvItemName, textViews);
    }


    @OnFocusChange(R.id.et_item_no)
    void item_noFocusChange() {
        ModuleUtils.viewChange(llItemNo, views);
        ModuleUtils.etChange(activity, etItemNo, editTexts);
        ModuleUtils.tvChange(activity, tvItemNo, textViews);
    }

    @OnFocusChange(R.id.et_date)
    void dateFocusChange() {
        ModuleUtils.viewChange(llDate, views);
        ModuleUtils.etChange(activity, etDate, editTexts);
        ModuleUtils.tvChange(activity, tvDate, textViews);
    }

    SaleOutletListAdapter adapter;

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
        module = ModuleCode.SALEOUTLET;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.saleoutlet);
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching = true;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_saleoutlet_list;
    }

    @Override
    protected void doBusiness() {
        etDate.setKeyListener(null);
        list = new ArrayList<>();
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
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
        adapter = new SaleOutletListAdapter(activity, list);
        ryList.setAdapter(adapter);
        showLoadingDialog();
        FilterBean filterBean = new FilterBean();
        filterBean.setWarehouse_out_no(LoginLogic.getWare());
        filterBean.setDoc_no(etGeneralNumber.getText().toString());
        filterBean.setCustomer_no(etCustomNo.getText().toString());
        filterBean.setDepartment_no(etDepartment.getText().toString());
        filterBean.setEmployee_no(etPerson.getText().toString());
        filterBean.setItem_name(etItemName.getText().toString());
        filterBean.setItem_no(etItemNo.getText().toString());
        filterBean.setDate_begin(startDate);
        filterBean.setDate_end(endDate);
        logic.getOrderData(filterBean, new CommonLogic.GetOrderListener() {
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
        try {
            isSearching = true;
            ryList.setVisibility(View.VISIBLE);
            llSearchInput.setVisibility(View.GONE);
            adapter = new SaleOutletListAdapter(activity, list);
            ryList.setAdapter(adapter);
            itemClick();
        } catch (Exception e) {
            LogUtils.e(TAG, "showDates---Exception>" + e);
        }
    }

    /**
     * 点击条目
     */
    private void itemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, final int position) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(SaleOutletActivity.filterBean,list.get(position));
                    ActivityManagerUtils.startActivityBundleForResult(activity, SaleOutletActivity.class, bundle, TOCOMMIT);
                    dismissLoadingDialog();
                } catch (Exception e) {
                    LogUtils.e(TAG, "itemClick" + e);
                }
            }
        });
    }


}