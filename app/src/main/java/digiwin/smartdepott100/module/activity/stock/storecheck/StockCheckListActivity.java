package digiwin.smartdepott100.module.activity.stock.storecheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.logic.stock.StockCheckLogic;
import digiwin.library.constant.SharePreKey;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.adapter.stock.StockCheckListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.produce.InBinningBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 孙长权
 * @module 库存盘点 代办事项清单列表
 * @date 2017/4/3
 */

public class StockCheckListActivity extends BaseTitleActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    //列表布局
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 筛选布局
     */
    @BindView(R.id.ll_search_dialog)
    LinearLayout llSearchDialog;

    StockCheckListAdapter adapter;

    StockCheckLogic stockCheckLogic;

    @BindViews({R.id.ll_post_material_order, R.id.ll_data, R.id.ll_person})
    List<View> views;
    @BindViews({R.id.tv_post_material_order, R.id.tv_data, R.id.tv_person})
    List<TextView> textViews;
    @BindViews({R.id.et_post_material_order, R.id.et_data, R.id.et_person})
    List<EditText> editTexts;

    /**
     * 盘点单号
     */
    @BindView(R.id.ll_post_material_order)
    LinearLayout llOrderNumber;
    @BindView(R.id.tv_post_material_order)
    TextView tvOrderNumber;
    @BindView(R.id.et_post_material_order)
    EditText etOrderNumber;

    @OnFocusChange(R.id.et_post_material_order)
    void item_noFocusChanage() {
        ModuleUtils.viewChange(llOrderNumber, views);
        ModuleUtils.tvChange(activity, tvOrderNumber, textViews);
        ModuleUtils.etChange(activity, etOrderNumber, editTexts);
    }

    /**
     * 计划日期
     */
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.et_data)
    public EditText etData;

    InBinningBean filterBean;

    @OnFocusChange(R.id.et_data)
    void dataFocusChanage() {
        ModuleUtils.viewChange(llData, views);
        ModuleUtils.tvChange(activity, tvData, textViews);
        ModuleUtils.etChange(activity, etData, editTexts);
    }

    /**
     * 盘点人员
     */
    @BindView(R.id.ll_person)
    LinearLayout llPerson;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.et_person)
    EditText etPerson;

    @OnFocusChange(R.id.et_person)
    void peopleFocusChanage() {
        ModuleUtils.viewChange(llPerson, views);
        ModuleUtils.tvChange(activity, tvPerson, textViews);
        ModuleUtils.etChange(activity, etPerson, editTexts);
    }

    @OnClick(R.id.iv_date)
    void dateClick() {
        DatePickerUtils.getDoubleDateV(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                etData.setText(showDate);
                filterBean.setDate_begin(mStartDate);
                filterBean.setDate_end(mEndDate);
            }
        });
    }

    /**
     * 搜索结果
     */
    private List<FilterResultOrderBean> dataList;


    @OnClick(R.id.btn_search_sure)
    void search() {
        if (!StringUtils.isBlank(etOrderNumber.getText().toString().trim())) {
            filterBean.setDoc_no(etOrderNumber.getText().toString().trim());//单号
        }

        if (!StringUtils.isBlank(etPerson.getText().toString().trim())) {
            filterBean.setEmployee_no(etPerson.getText().toString().trim());//人员
        }
        filterBean.setWarehouse_no(LoginLogic.getWare());
        showLoadingDialog();
        stockCheckLogic.getStockCheckList(filterBean, new CommonLogic.GetDataListListener() {
            @Override
            public void onSuccess(final List<FilterResultOrderBean> list) {
                dismissLoadingDialog();
                if (list.size() > 0) {
                    llSearchDialog.setVisibility(View.GONE);
                    ryList.setVisibility(View.VISIBLE);
                    dataList = list;
                    upDataUi();
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            itemClick(dataList, position);
                        }
                    });
                    if (autoSkip && list.size() == 1) {
                        itemClick(dataList, 0);
                    }
                    autoSkip = true;
                } else {
                    showFailedDialog(getResources().getString(R.string.nodate));
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    boolean isVis = true;

    private void itemClick(List<FilterResultOrderBean> clickBeen, int position) {
        Bundle bundle = new Bundle();
        FilterResultOrderBean data = clickBeen.get(position);
        bundle.putSerializable("data", data);
        ActivityManagerUtils.startActivityBundleForResult(activity, StockCheckActivity.class, bundle, SCANCODE);
    }

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog() {
        if (isVis) {
            llSearchDialog.setVisibility(View.GONE);
            ryList.setVisibility(View.VISIBLE);
        } else {
            llSearchDialog.setVisibility(View.VISIBLE);
            ryList.setVisibility(View.GONE);
        }
        isVis = !isVis;
    }

    @Override
    protected void doBusiness() {
        etData.setKeyListener(null);
        stockCheckLogic = StockCheckLogic.getInstance(activity, moduleCode(), mTimestamp.toString());
        dataList = new ArrayList<>();
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(activity);
        ryList.setLayoutManager(linearlayoutmanager);
        filterBean = new InBinningBean();
        String str = SharedPreferencesUtils.get(context, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM).toString();
        filterBean.setPagesize(str);
    }

    private void upDataUi() {
        adapter = new StockCheckListAdapter(activity, dataList);
        ryList.setAdapter(adapter);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(R.string.store_check);
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_stockchecklist;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.STORECHECK;
        return module;
    }

    public static int SCANCODE = 1000;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANCODE) {
            dataList.clear();
            search();
        }
    }
}
