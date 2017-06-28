package digiwin.smartdepott100.module.activity.produce.inbinning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
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
import digiwin.smartdepott100.module.adapter.produce.InBinningListAdapter;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.produce.InBinningBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

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

    InBinningListAdapter adapter;

    CommonLogic commonLogic;

    @BindViews({R.id.ll_order_number,R.id.ll_item_no,R.id.ll_department})
    List<View> views;
    @BindViews({R.id.tv_order_number,R.id.tv_item_no,R.id.tv_department})
    List<TextView> textViews;
    @BindViews({R.id.et_order_number,R.id.et_item_no,R.id.et_department})
    List<EditText> editTexts;

    /**
     * 工单号
     */
    @BindView(R.id.ll_order_number)
    LinearLayout ll_order_number;
    @BindView(R.id.tv_order_number)
    TextView tv_order_number;
    @BindView(R.id.et_order_number)
    EditText et_order_number;

    @OnFocusChange(R.id.et_order_number)
    void item_noFocusChanage() {
        ModuleUtils.viewChange(ll_order_number, views);
        ModuleUtils.tvChange(activity, tv_order_number, textViews);
        ModuleUtils.etChange(activity, et_order_number, editTexts);
    }

    /**
     * 料号
     */
    @BindView(R.id.ll_item_no)
    LinearLayout ll_itemno;
    @BindView(R.id.tv_item_no)
    TextView tv_itemno;
    @BindView(R.id.et_item_no)
    EditText et_itemno;

    @OnFocusChange(R.id.et_item_no)
    void customFocusChanage() {
        ModuleUtils.viewChange(ll_itemno, views);
        ModuleUtils.tvChange(activity, tv_itemno, textViews);
        ModuleUtils.etChange(activity, et_itemno, editTexts);
    }

    /**
     * 部门
     */
    @BindView(R.id.ll_department)
    LinearLayout ll_department;
    @BindView(R.id.tv_department)
    TextView tv_department;
    @BindView(R.id.et_department)
    EditText et_department;

    @OnFocusChange(R.id.et_department)
    void salesmanFocusChanage() {
        ModuleUtils.viewChange(ll_department, views);
        ModuleUtils.tvChange(activity, tv_department, textViews);
        ModuleUtils.etChange(activity, et_department, editTexts);
    }

    private final int SCANCODE = 1234;
    /**
     * 搜索结果
     */
    private List<ListSumBean> dataList;

    @BindView(R.id.btn_search_sure)
    Button btn_search_sure;

    @OnClick(R.id.btn_search_sure)
    void search(){
        //待办事项展示
        InBinningBean FilterBean = new InBinningBean();
        try {
            showLoadingDialog();

            if(!StringUtils.isBlank(et_order_number.getText().toString().trim())){
                FilterBean.setWo_no(et_order_number.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_itemno.getText().toString().trim())){
                FilterBean.setItem_no(et_itemno.getText().toString().trim());
            }

            if(!StringUtils.isBlank(et_department.getText().toString().trim())){
                FilterBean.setDepartment_no(et_department.getText().toString().trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        commonLogic.getOrderSumData(FilterBean, new CommonLogic.GetOrderSumListener() {
            @Override
            public void onSuccess(final List<ListSumBean> list) {
                dismissLoadingDialog();
                if(list.size() > 0){
                    ll_search_dialog.setVisibility(View.GONE);
                    scrollview.setVisibility(View.VISIBLE);
                    dataList = list;
                    adapter = new InBinningListAdapter(activity,false,dataList);
                    ry_list.setAdapter(adapter);

                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            Bundle bundle = new Bundle();
                            ListSumBean data = list.get(position);
                            bundle.putSerializable("data",data);
                            bundle.putString(AddressContants.MODULEID_INTENT,activity.mTimestamp.toString());
                            ActivityManagerUtils.startActivityBundleForResult(activity,InBinningActivity.class,bundle,SCANCODE);
                        }
                    });
                }else{
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

    boolean isVis=true;
    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog(){
        if (isVis){
            ll_search_dialog.setVisibility(View.GONE);
            scrollview.setVisibility(View.VISIBLE);
        }else{
            ll_search_dialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
        isVis=!isVis;
    }

    @Override
    protected void doBusiness() {
        commonLogic =  CommonLogic.getInstance(activity,moduleCode(),mTimestamp.toString());
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(activity);
        ry_list.setLayoutManager(linearlayoutmanager);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getString(R.string.title_in_binning)+getString(R.string.list));
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
        if(requestCode == SCANCODE){
            dataList.clear();
            adapter = new InBinningListAdapter(activity,false,dataList);
            ry_list.setAdapter(adapter);
            search();
        }
    }
}
