package digiwin.smartdepott100.module.activity.purchase.purchasestore;

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
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.stock.store.StoreReturnMaterialListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.purchase.PurchaseStoreLogic;
import digiwin.library.constant.SharePreKey;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;

/**
 * sunchangquan
 * 采购仓库退料清单
 * 2017/5/30
 */

public class PurchaseStoreListActivity extends BaseTitleActivity {
    PurchaseStoreListActivity activity;

    /**
     * 返回刷新页面
     */
    int TOCOMMIT = 1001;

    /**
     * 作业编号
     */
    public String module;

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    private PurchaseStoreLogic commonLogic;

    private List<FilterResultOrderBean> list;

    private StoreReturnMaterialListAdapter adapter;

    private boolean isSearching;

    /**
     * 退货单号
     */
    @BindView(R.id.tv_return_material)
    TextView tvReturnMaterial;
    @BindView(R.id.et_return_material)
    EditText etReturnMaterial;

    @OnFocusChange(R.id.et_return_material)
    void storeReturnMaterialFocusChange() {
        ModuleUtils.viewChange(llReturnMaterial, views);
        ModuleUtils.etChange(activity, etReturnMaterial, editTexts);
        ModuleUtils.tvChange(activity, tvReturnMaterial, textViews);
    }

    @BindView(R.id.ll_return_material)
    LinearLayout llReturnMaterial;

    /**
     * 供应商
     */
    @BindView(R.id.tv_supplier_no)
    TextView tvSupplierNo;
    @BindView(R.id.et_supplier_no)
    EditText etSupplierNo;

    @OnFocusChange(R.id.et_supplier_no)
    void supplierNoFocusChange() {
        ModuleUtils.viewChange(llSupplierNo, views);
        ModuleUtils.etChange(activity, etSupplierNo, editTexts);
        ModuleUtils.tvChange(activity, tvSupplierNo, textViews);
    }

    @BindView(R.id.ll_supplier_no)
    LinearLayout llSupplierNo;
    /**
     * 申请人
     */
    @BindView(R.id.tv_applicant)
    TextView tvApplicant;
    @BindView(R.id.et_applicant)
    EditText etApplicant;

    @OnFocusChange(R.id.et_applicant)
    void applicationFocusChange() {
        ModuleUtils.viewChange(llApplicant, views);
        ModuleUtils.etChange(activity, etApplicant, editTexts);
        ModuleUtils.tvChange(activity, tvApplicant, textViews);
    }

    @BindView(R.id.ll_applicant)
    LinearLayout llApplicant;

    /**
     * 部门
     */
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.et_department)
    EditText etDepartment;

    @OnFocusChange(R.id.et_department)
    void departmentFocusChange() {
        ModuleUtils.viewChange(llDepartment, views);
        ModuleUtils.etChange(activity, etDepartment, editTexts);
        ModuleUtils.tvChange(activity, tvDepartment, textViews);
    }

    @BindView(R.id.ll_department)
    LinearLayout llDepartment;

    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 日期
     */
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_date)
    EditText etDate;

    @OnFocusChange(R.id.et_date)
    void dateFocusChange() {
        ModuleUtils.viewChange(llDate, views);
        ModuleUtils.etChange(activity, etDate, editTexts);
        ModuleUtils.tvChange(activity, tvDate, textViews);
    }

    @BindView(R.id.date)
    ImageView date;

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

    @BindView(R.id.ll_date)
    LinearLayout llDate;

    @BindViews({R.id.et_return_material, R.id.et_supplier_no, R.id.et_applicant, R.id.et_department, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_return_material, R.id.ll_supplier_no, R.id.ll_applicant, R.id.ll_department, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_return_material, R.id.tv_supplier_no, R.id.tv_applicant, R.id.tv_department, R.id.tv_date})
    List<TextView> textViews;

    @BindView(R.id.btn_search_sure)
    Button btnSearchSure;

    @OnClick(R.id.btn_search_sure)
    void searchSure() {
        searchData();
    }

    private void clearDialogText() {
        etReturnMaterial.setText("");
        etApplicant.setText("");
        etDepartment.setText("");
        etSupplierNo.setText("");
        etDate.setText("");
    }

    private void searchData() {
        showLoadingDialog();
        list.clear();
        adapter = new StoreReturnMaterialListAdapter(activity, list);
        ryList.setAdapter(adapter);
        FilterBean bean = new FilterBean();
        bean.setDoc_no(etReturnMaterial.getText().toString().trim());
        bean.setEmployee_no(etApplicant.getText().toString().trim());
        bean.setDepartment_no(etDepartment.getText().toString().trim());
        bean.setSupplier_no(etSupplierNo.getText().toString().trim());
        bean.setPagesize((String) SharedPreferencesUtils.get(this, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM));
        bean.setDate_begin(startDate);
        bean.setDate_end(endDate);
        bean.setWarehouse_no(LoginLogic.getWare());
        commonLogic.getFiltrateDatas(bean, new PurchaseStoreLogic.FiltrateListener() {
            @Override
            public void onSuccess(List<FilterResultOrderBean> beanList) {
                dismissLoadingDialog();
                list = beanList;
                showData();
                updateUI();
                clearDialogText();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    @BindView(R.id.ll_search_input)
    LinearLayout llSearchInput;
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 展示信息
     */
    private void showData() {
        try {
            isSearching = true;
            ryList.setVisibility(View.VISIBLE);
            llSearchInput.setVisibility(View.GONE);
        } catch (Exception e) {
            LogUtils.e(TAG, "showDates---Exception>" + e);
        }
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


    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getString(R.string.store_returnmaterial) + getString(R.string.list));
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching = true;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.STORERETURNMATERIAL;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_storereturnmaterial_list;
    }

    @Override
    protected void doBusiness() {
        etDate.setKeyListener(null);
        commonLogic = PurchaseStoreLogic.getInstance(activity, module, mTimestamp.toString());
        list = new ArrayList<>();
        ryList.setLayoutManager(new LinearLayoutManager(activity));
    }

    /**
     * 更新界面
     */
    private void updateUI() {
        adapter = new StoreReturnMaterialListAdapter(activity, list);
        ryList.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                itemClick(list, position);
            }
        });
        if (autoSkip && list.size() == 1) {
            itemClick(list, 0);
        }
        autoSkip = true;
    }

    /**
     * 条目点击
     */
    private void itemClick(List<FilterResultOrderBean> clickBeen, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(AddressContants.DOC_NO, clickBeen.get(position).getDoc_no());
        bundle.putString(AddressContants.DATE, clickBeen.get(position).getCreate_date());
        bundle.putString(AddressContants.SUPPLIER, clickBeen.get(position).getSupplier_name());
        ActivityManagerUtils.startActivityBundleForResult(activity, PurchaseStoreActivity.class, bundle, TOCOMMIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TOCOMMIT) {
            list.clear();
            searchData();
        }
    }
}
