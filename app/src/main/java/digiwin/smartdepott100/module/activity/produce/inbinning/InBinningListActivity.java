package digiwin.smartdepott100.module.activity.produce.inbinning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.produce.InBinningListAdapter;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.InBinningLogic;

/**
 * @author 孙长权
 * @module 装箱入库 清单列表
 * @date 2017/4/3
 */

public class InBinningListActivity extends BaseTitleActivity {

    private InBinningListActivity activity;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 筛选布局
     */
    @BindView(R.id.ll_search_dialog)
    LinearLayout llSearchDialog;
    /**
     * 列表布局
     */
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    InBinningListAdapter adapter;

    InBinningLogic commonLogic;

    @BindViews({R.id.ll_order_number, R.id.ll_item_no, R.id.ll_department})
    List<View> views;
    @BindViews({R.id.tv_order_number, R.id.tv_item_no, R.id.tv_department})
    List<TextView> textViews;
    @BindViews({R.id.et_order_number, R.id.et_item_no, R.id.et_department})
    List<EditText> editTexts;

    /**
     * 工单号
     */
    @BindView(R.id.ll_order_number)
    LinearLayout llOrderNumber;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.et_order_number)
    EditText etOrderNumber;

    @OnFocusChange(R.id.et_order_number)
    void itemNoFocusChanage() {
        ModuleUtils.viewChange(llOrderNumber, views);
        ModuleUtils.tvChange(activity, tvOrderNumber, textViews);
        ModuleUtils.etChange(activity, etOrderNumber, editTexts);
    }

    /**
     * 料号
     */
    @BindView(R.id.ll_item_no)
    LinearLayout llItemno;
    @BindView(R.id.tv_item_no)
    TextView tvItemno;
    @BindView(R.id.et_item_no)
    EditText etItemno;

    @OnFocusChange(R.id.et_item_no)
    void customFocusChanage() {
        ModuleUtils.viewChange(llItemno, views);
        ModuleUtils.tvChange(activity, tvItemno, textViews);
        ModuleUtils.etChange(activity, etItemno, editTexts);
    }

    /**
     * 部门
     */
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.et_department)
    EditText etDepartment;

    @OnFocusChange(R.id.et_department)
    void salesmanFocusChanage() {
        ModuleUtils.viewChange(llDepartment, views);
        ModuleUtils.tvChange(activity, tvDepartment, textViews);
        ModuleUtils.etChange(activity, etDepartment, editTexts);
    }

    private final int SCANCODE = 1234;
    /**
     * 搜索结果
     */
    private List<ListSumBean> dataList;


    @OnClick(R.id.btn_search_sure)
    void search() {
        //待办事项展示
        FilterBean filterBean = new FilterBean();
        showLoadingDialog();

        if (!StringUtils.isBlank(etOrderNumber.getText().toString().trim())) {
            filterBean.setDoc_no(etOrderNumber.getText().toString().trim());
        }

        if (!StringUtils.isBlank(etItemno.getText().toString().trim())) {
            filterBean.setItem_no(etItemno.getText().toString().trim());
        }

        if (!StringUtils.isBlank(etDepartment.getText().toString().trim())) {
            filterBean.setDepartment_no(etDepartment.getText().toString().trim());
        }
        filterBean.setWarehouse_no(LoginLogic.getWare());
        commonLogic.getInBinningList(filterBean, new CommonLogic.GetZSumListener() {
            @Override
            public void onSuccess(final List<ListSumBean> list) {
                dismissLoadingDialog();
                llSearchDialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
                dataList = list;
                adapter = new InBinningListAdapter(activity, false, dataList);
                ryList.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Bundle bundle = new Bundle();
                        ListSumBean data = list.get(position);
                        bundle.putSerializable("data", data);
                        bundle.putString(AddressContants.MODULEID_INTENT, activity.mTimestamp.toString());
                        ActivityManagerUtils.startActivityBundleForResult(activity, InBinningActivity.class, bundle, SCANCODE);
                    }
                });
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
    void searchDialog() {
        if (isVis) {
            llSearchDialog.setVisibility(View.GONE);
            scrollview.setVisibility(View.VISIBLE);
        } else {
            llSearchDialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
        isVis = !isVis;
    }

    @Override
    protected void doBusiness() {
        commonLogic = InBinningLogic.getInstance(activity, moduleCode(), mTimestamp.toString());
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(activity);
        ryList.setLayoutManager(linearlayoutmanager);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getString(R.string.title_in_binning) + getString(R.string.list));
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_inbinninglist;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.INBINNING;
        return module;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANCODE) {
            dataList.clear();
            adapter = new InBinningListAdapter(activity, false, dataList);
            ryList.setAdapter(adapter);
            search();
        }
    }
}
