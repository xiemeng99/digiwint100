package digiwin.smartdepott100.module.activity.stock.storecheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.module.logic.stock.StockCheckLogic;
import digiwin.library.constant.SharePreKey;
import digiwin.library.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.ObjectAndMapUtils;
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
    RecyclerView ry_list;

    /**
     * 筛选布局
     */
    @BindView(R.id.ll_search_dialog)
    LinearLayout ll_search_dialog;

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
    LinearLayout ll_order_number;
    @BindView(R.id.tv_post_material_order)
    TextView tv_order_number;
    @BindView(R.id.et_post_material_order)
    EditText et_order_number;

    @OnFocusChange(R.id.et_post_material_order)
    void item_noFocusChanage() {
        ModuleUtils.viewChange(ll_order_number, views);
        ModuleUtils.tvChange(activity, tv_order_number, textViews);
        ModuleUtils.etChange(activity, et_order_number, editTexts);
    }

    /**
     * 计划日期
     */
    @BindView(R.id.ll_data)
    LinearLayout ll_data;
    @BindView(R.id.tv_data)
    TextView tv_data;
    @BindView(R.id.et_data)
    public EditText et_data;

    InBinningBean filterBean;

    @OnFocusChange(R.id.et_data)
    void dataFocusChanage() {
        ModuleUtils.viewChange(ll_data, views);
        ModuleUtils.tvChange(activity, tv_data, textViews);
        ModuleUtils.etChange(activity, et_data, editTexts);
    }

    /**
     * 盘点人员
     */
    @BindView(R.id.ll_person)
    LinearLayout ll_person;
    @BindView(R.id.tv_person)
    TextView tv_person;
    @BindView(R.id.et_person)
    EditText et_person;

    @OnFocusChange(R.id.et_person)
    void peopleFocusChanage() {
        ModuleUtils.viewChange(ll_person, views);
        ModuleUtils.tvChange(activity, tv_person, textViews);
        ModuleUtils.etChange(activity, et_person, editTexts);
    }

    @OnClick(R.id.iv_date)
    void dateClick() {
        DatePickerUtils.getDoubleDateV(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                et_data.setText(showDate);
                filterBean.setDate_begin(mStartDate);
                filterBean.setDate_end(mEndDate);
            }
        });
    }

    /**
     * 搜索结果
     */
    private List<FilterResultOrderBean> dataList;

    @BindView(R.id.btn_search_sure)
    Button btn_search_sure;

    @OnClick(R.id.btn_search_sure)
    void search() {
        if (!StringUtils.isBlank(et_order_number.getText().toString().trim())) {
            filterBean.setDoc_no(et_order_number.getText().toString().trim());//单号
        }

        if (!StringUtils.isBlank(et_person.getText().toString().trim())) {
            filterBean.setEmployee_no(et_person.getText().toString().trim());//人员
        }
        showLoadingDialog();
        Map<String, String> map = ObjectAndMapUtils.getValueMap(filterBean);
        stockCheckLogic.getStockCheckList(map, new CommonLogic.GetDataListListener() {
            @Override
            public void onSuccess(final List<FilterResultOrderBean> list) {
                dismissLoadingDialog();
                if (list.size() > 0) {
                    mName.setText(R.string.check_stock_wait);
                    ll_search_dialog.setVisibility(View.GONE);
                    ry_list.setVisibility(View.VISIBLE);
                    dataList = list;
                    upDataUi();
                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            Bundle bundle = new Bundle();
                            FilterResultOrderBean data = list.get(position);
                            bundle.putSerializable("data", data);
                            ActivityManagerUtils.startActivityBundleForResult(activity, StockCheckActivity.class, bundle, SCANCODE);
                        }
                    });
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

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog() {
        if (isVis) {
            ll_search_dialog.setVisibility(View.GONE);
            ry_list.setVisibility(View.VISIBLE);
        } else {
            ll_search_dialog.setVisibility(View.VISIBLE);
            ry_list.setVisibility(View.GONE);
        }
        isVis = !isVis;
    }

    @Override
    protected void doBusiness() {
        stockCheckLogic = StockCheckLogic.getInstance(activity, moduleCode(), mTimestamp.toString());
        dataList = new ArrayList<>();
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(activity);
        ry_list.setLayoutManager(linearlayoutmanager);
        filterBean = new InBinningBean();
        String str = SharedPreferencesUtils.get(context, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM).toString();
        filterBean.setPagesize(str);
    }

    private void upDataUi() {
        adapter = new StockCheckListAdapter(activity, dataList);
        ry_list.setAdapter(adapter);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(R.string.filter_condition);
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
