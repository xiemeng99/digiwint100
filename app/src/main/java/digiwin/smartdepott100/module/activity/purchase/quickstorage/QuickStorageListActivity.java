package digiwin.smartdepott100.module.activity.purchase.quickstorage;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.constant.SharePreKey;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.adapter.stock.QuickStorageListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.QuickStorageLogic;

/**
 * @author 孙长权
 * @module 快速入库 -- 清单
 * @date 2017/6/15
 */

public class QuickStorageListActivity extends BaseTitleActivity {
    private QuickStorageListActivity activity;

    QuickStorageListAdapter adapter;

    QuickStorageLogic quickStorageLogic;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar_title;

    @BindView(R.id.ry_list)
    RecyclerView ry_list;

    /**
     * 筛选布局
     */
    @BindView(R.id.ll_search_dialog)
    LinearLayout ll_search_dialog;
    /**
     * 列表布局
     */
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    @BindViews({R.id.ll_provider_code, R.id.ll_barcode_no, R.id.ll_item_name, R.id.ll_plan_date})
    List<View> views;
    @BindViews({R.id.tv_provider_code, R.id.tv_barcode_no, R.id.tv_item_name, R.id.tv_plan_date})
    List<TextView> textViews;
    @BindViews({R.id.et_provider_code, R.id.et_barcode_no, R.id.et_item_name, R.id.et_plan_date})
    List<EditText> editTexts;

    /**
     * 供应商代码
     */
    @BindView(R.id.ll_provider_code)
    LinearLayout ll_provider_code;
    @BindView(R.id.tv_provider_code)
    TextView tv_provider_code;
    @BindView(R.id.et_provider_code)
    EditText et_provider_code;

    @OnFocusChange(R.id.et_provider_code)
    void provider_codeFocusChanage() {
        ModuleUtils.viewChange(ll_provider_code, views);
        ModuleUtils.tvChange(activity, tv_provider_code, textViews);
        ModuleUtils.etChange(activity, et_provider_code, editTexts);
    }

    /**
     * 物料条码
     */
    @BindView(R.id.ll_barcode_no)
    LinearLayout ll_barcode_no;
    @BindView(R.id.tv_barcode_no)
    TextView tv_barcode_no;
    @BindView(R.id.et_barcode_no)
    EditText et_barcode_no;

    @OnFocusChange(R.id.et_barcode_no)
    void barcode_noFocusChanage() {
        ModuleUtils.viewChange(ll_barcode_no, views);
        ModuleUtils.tvChange(activity, tv_barcode_no, textViews);
        ModuleUtils.etChange(activity, et_barcode_no, editTexts);
    }

    /**
     * 品名
     */
    @BindView(R.id.ll_item_name)
    LinearLayout ll_item_name;
    @BindView(R.id.tv_item_name)
    TextView tv_item_name;
    @BindView(R.id.et_item_name)
    EditText et_item_name;

    @OnFocusChange(R.id.et_item_name)
    void customFocusChanage() {
        ModuleUtils.viewChange(ll_item_name, views);
        ModuleUtils.tvChange(activity, tv_item_name, textViews);
        ModuleUtils.etChange(activity, et_item_name, editTexts);
    }

    /**
     * 筛选框 计划日
     */
    @BindView(R.id.ll_plan_date)
    LinearLayout ll_plan_date;

    @BindView(R.id.iv_plan_date)
    ImageView iv_plan_date;
    @BindView(R.id.tv_plan_date)
    TextView tv_plan_date;
    @BindView(R.id.et_plan_date)
    EditText et_plan_date;

    @OnFocusChange(R.id.et_plan_date)
    void plan_dateFocusChanage() {
        ModuleUtils.viewChange(ll_plan_date, views);
        ModuleUtils.tvChange(activity, tv_plan_date, textViews);
        ModuleUtils.etChange(activity, et_plan_date, editTexts);
    }

    String startDate = "";
    String endDate = "";

    @OnClick(R.id.iv_plan_date)
    void dateClick() {
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                et_plan_date.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                et_plan_date.setText(showDate);
            }
        });
    }

    private final int SCANCODE = 1234;

    private final String DATA = "data";

    private List<FilterResultOrderBean> dataList;

    @BindView(R.id.btn_search_sure)
    Button btn_search_sure;

    @OnClick(R.id.btn_search_sure)
    void search() {
        FilterBean FilterBean = new FilterBean();
        try {
            showLoadingDialog();
            FilterBean.setPagesize((String) SharedPreferencesUtils.get(this, SharePreKey.PAGE_SETTING, "10"));
            if (!StringUtils.isBlank(et_provider_code.getText().toString().trim())) {
                FilterBean.setSupplier_no(et_provider_code.getText().toString().trim());//供应商
            }

            if (!StringUtils.isBlank(et_barcode_no.getText().toString().trim())) {
                FilterBean.setItem_no(et_barcode_no.getText().toString().trim());//料号
            }

            if (!StringUtils.isBlank(et_item_name.getText().toString().trim())) {
                FilterBean.setItem_name(et_item_name.getText().toString().trim());
            }

            if (!StringUtils.isBlank(et_plan_date.getText().toString())) {
                FilterBean.setDate_begin(startDate);
                FilterBean.setDate_end(endDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        quickStorageLogic.getQuickStorageOrderData(FilterBean, new CommonLogic.GetOrderListener() {
            @Override
            public void onSuccess(final List<FilterResultOrderBean> list) {
                dismissLoadingDialog();
                if (list.size() > 0) {
                    ll_search_dialog.setVisibility(View.GONE);
                    scrollview.setVisibility(View.VISIBLE);
                    dataList = new ArrayList<FilterResultOrderBean>();
                    dataList = list;
                    adapter = new QuickStorageListAdapter(activity, list);
                    ry_list.setAdapter(adapter);
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            itemClick(dataList, position);
                        }
                    });
                    if (autoSkip&&list.size() == 1) {
                        itemClick(dataList, 0);
                    }
                    autoSkip=true;
                } else {
                    showFailedDialog(getResources().getString(R.string.nodate));
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
                ArrayList dataList = new ArrayList<FilterResultOrderBean>();
                adapter = new QuickStorageListAdapter(activity, dataList);
                ry_list.setAdapter(adapter);
            }
        });
    }

    /**
     * 只有一笔时自动跳入
     * 跳转到扫描页面
     */
    private void itemClick(List<FilterResultOrderBean> clickBeen, int position) {
        Bundle bundle = new Bundle();
        FilterResultOrderBean data = clickBeen.get(position);
        bundle.putSerializable(DATA, data);
        ActivityManagerUtils.startActivityBundleForResult(activity, QuickStorageActivity.class, bundle, SCANCODE);
    }

    @Override
    protected void doBusiness() {
        startDate = "";
        endDate = "";
        et_plan_date.setKeyListener(null);
        quickStorageLogic = QuickStorageLogic.getInstance(activity, module, mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ry_list.setLayoutManager(linearLayoutManager);

    }

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog() {
        if (ll_search_dialog.getVisibility() == View.VISIBLE) {
            if (null != dataList && dataList.size() > 0) {
                ll_search_dialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
            }
        } else {
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == SCANCODE) {
                dataList.clear();
                adapter = new QuickStorageListAdapter(activity, dataList);
                ry_list.setAdapter(adapter);
                search();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_quickstoragelist;
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar_title;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.QUICKSTORAGE;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.title_quickstorage) + getString(R.string.list));
        activity = this;
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }
}
