package digiwin.smartdepott100.module.activity.purchase.iqc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.adapter.purchase.IQCListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.purchase.IQCLogic;

/**
 * @author xiemeng
 * @des IQC列表
 * @date 2017/5/30 09:31
 */

public class IQCListActivity extends BaseTitleActivity {

    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_purchase_order)
    TextView tvPurchaseOrder;
    @BindView(R.id.et_purchase_order)
    EditText etPurchaseOrder;
    @BindView(R.id.ll_purchase_order)
    LinearLayout llPurchaseOrder;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
    @BindView(R.id.et_depart)
    EditText etDepart;
    @BindView(R.id.ll_depart)
    LinearLayout llDepart;

    @BindView(R.id.ll_search_input)
    LinearLayout llSearchInput;
    @BindView(R.id.tv_check_no)
    TextView tvCheckNo;
    @BindView(R.id.et_check_no)
    EditText etCheckNo;
    @BindView(R.id.ll_check_no)
    LinearLayout llCheckNo;
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.et_item_no)
    EditText etItemNo;
    @BindView(R.id.ll_item_no)
    LinearLayout llItemNo;
    @BindView(R.id.tv_barcode_no)
    TextView tvBarcodeNo;
    @BindView(R.id.et_barcode_no)
    EditText etBarcodeNo;
    @BindView(R.id.ll_barcode_no)
    LinearLayout llBarcodeNo;
    @BindView(R.id.tv_check_employ)
    TextView tvCheckEmploy;
    @BindView(R.id.et_check_employ)
    EditText etCheckEmploy;
    @BindView(R.id.ll_check_employ)
    LinearLayout llCheckEmploy;
    private IQCLogic logic;

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
    @BindViews({R.id.et_purchase_order,R.id.et_check_no, R.id.et_item_no, R.id.et_barcode_no,R.id.et_check_employ, R.id.et_depart, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_purchase_order,R.id.ll_check_no, R.id.ll_item_no, R.id.ll_barcode_no,R.id.ll_check_employ,  R.id.ll_depart, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_purchase_order,R.id.tv_check_no, R.id.tv_item_no, R.id.tv_barcode_no,R.id.tv_check_employ,  R.id.tv_depart, R.id.tv_date})
    List<TextView> textViews;

    /**
     * 监听焦点变化
     */
    @OnFocusChange(R.id.et_purchase_order)
    void purchaseOrderFocusChange() {
        ModuleUtils.viewChange(llPurchaseOrder, views);
        ModuleUtils.etChange(activity, etPurchaseOrder, editTexts);
        ModuleUtils.tvChange(activity, tvPurchaseOrder, textViews);
    }
    @OnFocusChange(R.id.et_check_no)
    void checkNoFocusChange() {
        ModuleUtils.viewChange(llCheckNo, views);
        ModuleUtils.etChange(activity, etCheckNo, editTexts);
        ModuleUtils.tvChange(activity, tvCheckNo, textViews);
    }
    @OnFocusChange(R.id.et_item_no)
    void itemNoFocusChange() {
        ModuleUtils.viewChange(llItemNo, views);
        ModuleUtils.etChange(activity, etItemNo, editTexts);
        ModuleUtils.tvChange(activity, tvItemNo, textViews);
    }
    @OnFocusChange(R.id.et_barcode_no)
    void barcodeNoFocusChange() {
        ModuleUtils.viewChange(llBarcodeNo, views);
        ModuleUtils.etChange(activity, etBarcodeNo, editTexts);
        ModuleUtils.tvChange(activity, tvBarcodeNo, textViews);
    }
    @OnFocusChange(R.id.et_check_employ)
    void checkEmployFocusChange() {
        ModuleUtils.viewChange(llCheckEmploy, views);
        ModuleUtils.etChange(activity, etCheckEmploy, editTexts);
        ModuleUtils.tvChange(activity, tvCheckEmploy, textViews);
    }

    @OnFocusChange(R.id.et_depart)
    void departFocusChange() {
        ModuleUtils.viewChange(llDepart, views);
        ModuleUtils.etChange(activity, etDepart, editTexts);
        ModuleUtils.tvChange(activity, tvDepart, textViews);
    }


    @OnFocusChange(R.id.et_date)
    void dateFocusChange() {
        ModuleUtils.viewChange(llDate, views);
        ModuleUtils.etChange(activity, etDate, editTexts);
        ModuleUtils.tvChange(activity, tvDate, textViews);
    }

    IQCListAdapter adapter;

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
        module = ModuleCode.PURCHASECHECK;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.iqc_check) + getString(R.string.list));
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching = true;
    }

    @Override

    protected int bindLayoutId() {
        return R.layout.activity_iqc_list;
    }

    @Override
    protected void doBusiness() {
        etDate.setKeyListener(null);
        list = new ArrayList<>();
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        logic = IQCLogic.getInstance(activity, module, mTimestamp.toString());
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
        adapter = new IQCListAdapter(activity, list);
        ryList.setAdapter(adapter);
        showLoadingDialog();
        FilterBean filterBean = new FilterBean();
        //  filterBean.setWarehouse_in_no(LoginLogic.getWare());
        filterBean.setReceipt_no(etPurchaseOrder.getText().toString());
        filterBean.setDoc_no(etCheckNo.getText().toString());
        filterBean.setItem_no(etItemNo.getText().toString());
        filterBean.setBarcode_no(etBarcodeNo.getText().toString());
        filterBean.setEmployee_no(etCheckEmploy.getText().toString());
        filterBean.setDepartment_no(etDepart.getText().toString());
        filterBean.setDate_begin(startDate);
        filterBean.setDate_end(endDate);
        logic.getIqcList(filterBean, new CommonLogic.GetDataListListener() {
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
            adapter = new IQCListAdapter(activity, list);
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
            LogUtils.e(TAG, "showDates---Exception>" + e);
        }
    }

    /**
     * 点击条目
     */
    private void itemClick(List<FilterResultOrderBean> clickBeen, int position) {
        try {
            Bundle bundle = new Bundle();
            bundle.putSerializable(IQCommitActivity.filterBean, clickBeen.get(position));
            ActivityManagerUtils.startActivityBundleForResult(activity, IQCommitActivity.class, bundle, TOCOMMIT);
            dismissLoadingDialog();
        } catch (Exception e) {
            LogUtils.e(TAG, "itemClick" + e);
        }
    }

}