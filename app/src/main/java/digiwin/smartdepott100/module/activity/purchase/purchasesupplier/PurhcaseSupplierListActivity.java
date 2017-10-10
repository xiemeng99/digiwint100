package digiwin.smartdepott100.module.activity.purchase.purchasesupplier;

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
import digiwin.smartdepott100.module.adapter.purchase.PurchaseSupplierAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.purchase.PurchaseGoodScanLogic;
import digiwin.smartdepott100.module.logic.purchase.PurchaseSupplierLogic;

/**
 * @author xiemeng
 * @des 扫码收货，供应商
 * @date 2017/6/12 20:04
 */

public class PurhcaseSupplierListActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindViews({R.id.et_send_goods_order,R.id.et_barcode_no, R.id.et_provider,  R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_send_goods_order,R.id.ll_barcode_no, R.id.ll_provider,  R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_send_goods_order,R.id.tv_barcode_no, R.id.tv_provider,  R.id.tv_date})
    List<TextView> textViews;

    /**
     * 筛选框 物料条码
     */
    @BindView(R.id.et_barcode_no)
    EditText et_barcode_no;
    /**
     * 筛选框 物料条码
     */
    @BindView(R.id.ll_barcode_no)
    LinearLayout ll_barcode_no;
    /**
     * 筛选框 物料条码
     */
    @BindView(R.id.tv_barcode_no)
    TextView tv_barcode_no;

    @OnFocusChange(R.id.et_barcode_no)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(ll_barcode_no, views);
        ModuleUtils.etChange(activity, et_barcode_no, editTexts);
        ModuleUtils.tvChange(activity, tv_barcode_no, textViews);
    }

    /**
     * 筛选框 供应商代码
     */
    @BindView(R.id.et_provider)
    EditText et_provider;

    /**
     * 筛选框 供应商代码
     */
    @BindView(R.id.ll_provider)
    LinearLayout ll_provider;
    /**
     * 筛选框 供应商代码
     */
    @BindView(R.id.tv_provider)
    TextView tv_provider;

    @OnFocusChange(R.id.et_provider)
    void departmentFocusChanage() {
        ModuleUtils.viewChange(ll_provider, views);
        ModuleUtils.etChange(activity, et_provider, editTexts);
        ModuleUtils.tvChange(activity, tv_provider, textViews);
    }

    /**
     * 筛选框 发货单号
     */
    @BindView(R.id.et_send_goods_order)
    EditText et_send_goods_order;

    /**
     * 筛选框 发货单号
     */
    @BindView(R.id.ll_send_goods_order)
    LinearLayout ll_send_goods_order;
    /**
     * 筛选框 发货单号
     */
    @BindView(R.id.tv_send_goods_order)
    TextView tv_send_goods_order;

    @OnFocusChange(R.id.et_send_goods_order)
    void sendGoodsOrderFocusChanage() {
        ModuleUtils.viewChange(ll_send_goods_order, views);
        ModuleUtils.etChange(activity, et_send_goods_order, editTexts);
        ModuleUtils.tvChange(activity, tv_send_goods_order, textViews);
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
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
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
    LinearLayout llSearchDialog;

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    /**
     * 跳转扫描使用
     */
    public static final int SUMCODE = 1212;

    PurchaseSupplierLogic supplierLogic;


    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog() {
        if (llSearchDialog.getVisibility() == View.VISIBLE) {
            if (null != sumShowBeanList && sumShowBeanList.size() > 0) {
                llSearchDialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
                adapter = new PurchaseSupplierAdapter(activity, sumShowBeanList);
                ryList.setAdapter(adapter);
                onItemClick();
            }
        } else {
            llSearchDialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
    }

    /**
     * 点击确定，筛选
     */
    @OnClick(R.id.btn_search_sure)
    void Search() {
        //TODO
        upDateList();
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PURCHASESUPPLIERSCAN;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_purchase_supplier_list;
    }

    @Override
    protected void doBusiness() {
        etDate.setKeyListener(null);
        supplierLogic = PurchaseSupplierLogic.getInstance(activity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        SearchDialog();
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
                ActivityManagerUtils.startActivityBundleForResult(activity, PurchaseSupplierActivity.class, bundle, SUMCODE);
            }
        });
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.purchase_scan_supplier)+getString(R.string.list));
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    PurchaseSupplierAdapter adapter;

    List<FilterResultOrderBean> sumShowBeanList;

    /**
     * 汇总展示
     */
    public void upDateList() {
        FilterBean filterBean = new FilterBean();
        try {
            Map<String, String> map = new HashMap<>();
            //供应商
            if (!StringUtils.isBlank(et_provider.getText().toString())) {
                filterBean.setSupplier_no(et_provider.getText().toString());
            }
            //物料编码
            if (!StringUtils.isBlank(et_barcode_no.getText().toString())) {
                filterBean.setBarcode_no(et_barcode_no.getText().toString());
            }
            //发货单号
            if (!StringUtils.isBlank(et_send_goods_order.getText().toString())) {
                filterBean.setDoc_no(et_send_goods_order.getText().toString());
            }
            //日期
            if (!StringUtils.isBlank(etDate.getText().toString())) {
                filterBean.setDate_begin(startDate);
                filterBean.setDate_end(endDate);
            }
            showLoadingDialog();
            supplierLogic.getPSList(filterBean, new PurchaseGoodScanLogic.GetDataListListener() {
                @Override
                public void onSuccess(List<FilterResultOrderBean> list) {
                    dismissLoadingDialog();
                    if (null != list && list.size() > 0) {
                        //查询成功隐藏筛选界面，展示汇总信息
                        llSearchDialog.setVisibility(View.GONE);
                        scrollview.setVisibility(View.VISIBLE);
                        iv_title_setting.setVisibility(View.VISIBLE);
                        sumShowBeanList = new ArrayList<FilterResultOrderBean>();
                        sumShowBeanList = list;
                        adapter = new PurchaseSupplierAdapter(activity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        onItemClick();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<FilterResultOrderBean>();
                        adapter = new PurchaseSupplierAdapter(activity, sumShowBeanList);
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
                adapter = new PurchaseSupplierAdapter(activity,new ArrayList<FilterResultOrderBean>());
                ryList.setAdapter(adapter);
                upDateList();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
