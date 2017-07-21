package digiwin.smartdepott100.module.activity.produce.endproductallot;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.adapter.produce.EndProductAllotSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;

import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.EndProductAllotLogic;

/**
 * @author xiemeng
 * @module 依成品调拨
 * @date 2017/4/13
 */

public class EndProductAllotActivity extends BaseFirstModuldeActivity {

    BaseRecyclerAdapter adapter;

    List<ListSumBean> listSumBean;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    /**
     * 跳转扫描页面使用
     */
    public final int SCANCODE = 0123;
    /**
     * 是否展示搜索
     */
    private boolean isSearching;

    EndProductAllotLogic commonLogic;

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    /**
     * 筛选框
     */
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.et_item_no)
    EditText etItemNo;
    @BindView(R.id.ll_item_no)
    LinearLayout llItemNo;

    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.et_department)
    EditText etDepartment;
    @BindView(R.id.ll_department)
    LinearLayout llDepartment;

    @BindView(R.id.tv_target_store)
    TextView tvTargetStore;
    @BindView(R.id.et_target_store)
    EditText etTargetStore;
    @BindView(R.id.ll_target_store)
    LinearLayout llTargetStore;

    @BindView(R.id.ll_search_dialog)
    LinearLayout llSearchInput;
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @BindViews({R.id.et_item_no, R.id.et_department, R.id.et_target_store})
    List<EditText> editTexts;
    @BindViews({R.id.tv_item_no, R.id.tv_department, R.id.tv_target_store})
    List<TextView> textViews;
    @BindViews({R.id.ll_item_no, R.id.ll_department, R.id.ll_target_store})
    List<View> views;

    /**
     * 汇总界面
     */
    @BindView(R.id.tv_head_item_no)
    TextView tvHeaditemNo;
    @BindView(R.id.tv_head_depart)
    TextView tvHeadDepart;
    @BindView(R.id.tv_head_source)
    TextView tvHeadSource;
    @BindView(R.id.tv_head_target)
    TextView tvHeadTarget;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    /**
     *筛选框 焦点颜色变化
     */
    @OnFocusChange(R.id.et_item_no)
    void itemFocusChange() {
        ModuleUtils.viewChange(llItemNo, views);
        ModuleUtils.etChange(activity, etItemNo, editTexts);
        ModuleUtils.tvChange(activity, tvItemNo, textViews);
    }

    @OnFocusChange(R.id.et_department)
    void departmentFocusChange() {
        ModuleUtils.viewChange(llDepartment, views);
        ModuleUtils.etChange(activity, etDepartment, editTexts);
        ModuleUtils.tvChange(activity, tvDepartment, textViews);
    }

    @OnFocusChange(R.id.et_target_store)
    void locatorFocusChange() {
        ModuleUtils.viewChange(llTargetStore, views);
        ModuleUtils.etChange(activity, etTargetStore, editTexts);
        ModuleUtils.tvChange(activity, tvTargetStore, textViews);
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
            mName.setText(getString(R.string.endproduct_allot)+getString(R.string.SumData));
            ivScan.setVisibility(View.GONE);
            commit.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            llSearchInput.setVisibility(View.GONE);
            return;
        } else {
            isSearching = true;
            ivScan.setVisibility(View.VISIBLE);
            commit.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            llSearchInput.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 确定按钮
     */
    @BindView(R.id.btn_search_sure)
    Button btnSearchSure;

    @OnClick(R.id.btn_search_sure)
    void search_sure(){
        Map<String, String> map = new HashMap<>();
        map.put(AddressContants.FLAG, ExitMode.EXITD.getName());
        commonLogic.exit(map, new CommonLogic.ExitListener() {
            @Override
            public void onSuccess(String msg) {
                onUpdate();
            }

            @Override
            public void onFailed(String error) {
                showFailedDialog(error);
            }
        });

    }

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    /**
     * 提交按钮
     */
    @BindView(R.id.commit)
    Button commit;

    @OnClick(R.id.commit)
    void commit() {
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                if (null==listSumBean||listSumBean.size()==0)
                {
                    showFailedDialog(R.string.nodate);
                    return;
                }
                commitData();
            }

            @Override
            public void onCallback2() {

            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.endproduct_allot)+getString(R.string.SumData));
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching = true;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.ENDPRODUCTALLOT;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_endproduct_allot;
    }

    @Override
    protected void doBusiness() {
        listSumBean =new ArrayList<>();
        commonLogic = EndProductAllotLogic.getInstance(activity,module,mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANCODE) {
            if (null!=listSumBean && listSumBean.size()>0) {
               onUpdate();
            };
        }else if (requestCode==DETAILCODE){
            onUpdate();
        }
    }

    /**
     * 更新
     */
    private void onUpdate() {
        showLoadingDialog();
        //先清空数据
        listSumBean.clear();
        adapter = new EndProductAllotSumAdapter(this, listSumBean);
        ryList.setAdapter(adapter);
        ClickItemPutBean putBean = new ClickItemPutBean();
        putBean.setItem_no(etItemNo.getText().toString());
        putBean.setDepartment_no(etDepartment.getText().toString());
        putBean.setWarehouse_in_no(etTargetStore.getText().toString());
        putBean.setWarehouse_out_no(LoginLogic.getWare());
        Map<String, String> map = ObjectAndMapUtils.getValueMap(putBean);
        commonLogic.getEndProductSumData(map, new CommonLogic.GetZSumListener() {
            @Override
            public void onSuccess(List<ListSumBean> list) {
                dismissLoadingDialog();
                listSumBean = list;
                showData();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                try {
                    showFailedDialog(error);
                    listSumBean.clear();
                    adapter = new EndProductAllotSumAdapter(activity, listSumBean);
                    ryList.setAdapter(adapter);
                } catch (Exception e) {
                    LogUtils.e(TAG, "updateList--getSum--onFailed" + e);
                }
            }
        });
    }

    /**
     * 展示数据
     */
    private void showData() {
        try {
            isSearching = true;
            ivScan.setVisibility(View.GONE);
            llSearchInput.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            commit.setVisibility(View.VISIBLE);
            if (listSumBean.size()>0){
                ListSumBean sumBean = listSumBean.get(0);
                tvHeadDepart.setText(sumBean.getDepartment_name());
                tvHeaditemNo.setText(sumBean.getItem_no());
                tvHeadSource.setText(LoginLogic.getWare());
                tvHeadTarget.setText(etTargetStore.getText().toString());
            }
            adapter = new EndProductAllotSumAdapter(activity, listSumBean);
            ryList.setAdapter(adapter);
            itemClick();
            //滚动到原点
            scrollView.smoothScrollTo(0,0);
        } catch (Exception e) {
            LogUtils.e(TAG, "showDates---Exception>" + e);
        }
    }

    /**
     * 条目点击 跳转至扫码页面
     */
    private void itemClick(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Bundle bundle = new Bundle();
                ListSumBean data = listSumBean.get(position);
                data.setWarehouse_in_no(tvHeadTarget.getText().toString());
                data.setWarehouse_out_no(LoginLogic.getWare());
                bundle.putSerializable("sumdata", data);
                bundle.putString(AddressContants.MODULEID_INTENT,mTimestamp.toString());
                ActivityManagerUtils.startActivityBundleForResult(activity,EndProductAllotScanActivity.class,bundle,SCANCODE);
            }
        });
    }

    /**
     * 跳转到明细页面
     */
    public void ToDetailAct(final SumShowBean bean) {
        HashMap<String, String> map = new HashMap<String, String>();
        showLoadingDialog();
        map.put(AddressContants.ITEM_NO, bean.getItem_no());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(final List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(CommonDetailActivity.ONESUM, bean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, module);
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, DETAILCODE);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    /**
     * 提交
     */
    public void commitData() {
        HashMap<String, String> barcodeMap = new HashMap<String, String>();
        commonLogic.endProductCommit(barcodeMap, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg);
                createNewModuleId(module);
                commonLogic = EndProductAllotLogic.getInstance(activity, module, mTimestamp.toString());
                isSearching=false;
                search();
                listSumBean.clear();
                tvHeadDepart.setText("");
                tvHeaditemNo.setText("");
                tvHeadSource.setText("");
                tvHeadTarget.setText("");
                listSumBean.clear();
                adapter=new EndProductAllotSumAdapter(activity,listSumBean);
                ryList.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }

}