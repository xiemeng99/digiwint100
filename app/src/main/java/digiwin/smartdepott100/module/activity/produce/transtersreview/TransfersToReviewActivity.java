package digiwin.smartdepott100.module.activity.produce.transtersreview;

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

import java.io.Serializable;
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
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.produce.TransfersReviewSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @des      生产调拨复合
 * @author  maoheng
 * @date    2017/3/4
 */
public class TransfersToReviewActivity extends BaseTitleActivity {

    @BindView(R.id.ry_list)
    RecyclerView ryList;
    private CommonLogic logic;

    /**
     * 搜索栏
     */
    @BindView(R.id.ll_search_input)
    LinearLayout ll_search_input;

    private boolean isSearching;

    /**
     * toolbar
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    /**
     * 调拨单
     */
    @BindView(R.id.ll_transfers_list)
    LinearLayout ll_transfers_list;
    @BindView(R.id.et_transfers_list)
    EditText et_transfers_list;
    @BindView(R.id.tv_transfers_list)
    TextView tv_transfers_list;
    /**
     * 部门
     */
    @BindView(R.id.ll_department)
    LinearLayout ll_department;
    @BindView(R.id.et_department)
    EditText et_department;
    @BindView(R.id.tv_department)
    TextView tv_department;
    /**
     * 申请人
     */
    @BindView(R.id.ll_applicant)
    LinearLayout ll_applicant;
    @BindView(R.id.et_applicant)
    EditText et_applicant;
    @BindView(R.id.tv_applicant)
    TextView tv_applicant;
    /**
     * 拨入仓
     */
    @BindView(R.id.ll_in_warehouse)
    LinearLayout ll_in_warehouse;
    @BindView(R.id.et_in_warehouse)
    EditText et_in_warehouse;
    @BindView(R.id.tv_in_warehouse)
    TextView tv_in_warehouse;

    /**
     * 料号
     */
    @BindView(R.id.ll_item_no)
    LinearLayout ll_item_no;
    @BindView(R.id.et_item_no)
    EditText et_item_no;
    @BindView(R.id.tv_item_no)
    TextView tv_item_no;
    /**
     * 日期
     */
    @BindView(R.id.ll_date)
    LinearLayout ll_date;
    @BindView(R.id.et_date)
    EditText et_date;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @OnClick(R.id.date)
    void getDate() {
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate,String mShowDate) {
                startDate=mStartDate;
                endDate=mEndDate;
                et_date.setText(mShowDate);
                et_date.requestFocus();
            }
        });
    }

    /**
     * 确定按钮
     */
    @BindView(R.id.btn_search_sure)
    Button btn_search_sure;

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
            isSearching=false;
            ryList.setVisibility(View.VISIBLE);
            ll_search_input.setVisibility(View.GONE);
            return;
        } else {
            isSearching=true;
            ryList.setVisibility(View.GONE);
            ll_search_input.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 控件集合
     */
    @BindViews({R.id.et_transfers_list, R.id.et_department, R.id.et_applicant, R.id.et_in_warehouse, R.id.et_item_no, R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.ll_transfers_list, R.id.ll_department, R.id.ll_applicant, R.id.ll_in_warehouse, R.id.ll_item_no, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_transfers_list, R.id.tv_department, R.id.tv_applicant, R.id.tv_in_warehouse,  R.id.tv_item_no, R.id.tv_date})
    List<TextView> textViews;

    /**
     * 监听焦点变化
     */
    @OnFocusChange(R.id.et_transfers_list)
    void transfers_listFocusChange() {
        ModuleUtils.viewChange(ll_transfers_list, views);
        ModuleUtils.etChange(activity, et_transfers_list, editTexts);
        ModuleUtils.tvChange(activity, tv_transfers_list, textViews);
    }

    @OnFocusChange(R.id.et_department)
    void departmentFocusChange() {
        ModuleUtils.viewChange(ll_department, views);
        ModuleUtils.etChange(activity, et_department, editTexts);
        ModuleUtils.tvChange(activity, tv_department, textViews);
    }

    @OnFocusChange(R.id.et_applicant)
    void applicationFocusChange() {
        ModuleUtils.viewChange(ll_applicant, views);
        ModuleUtils.etChange(activity, et_applicant, editTexts);
        ModuleUtils.tvChange(activity, tv_applicant, textViews);
    }

    @OnFocusChange(R.id.et_in_warehouse)
    void in_warehouseFocusChange() {
        ModuleUtils.viewChange(ll_in_warehouse, views);
        ModuleUtils.etChange(activity, et_in_warehouse, editTexts);
        ModuleUtils.tvChange(activity, tv_in_warehouse, textViews);
    }



    @OnFocusChange(R.id.et_item_no)
    void item_noFocusChange() {
        ModuleUtils.viewChange(ll_item_no, views);
        ModuleUtils.etChange(activity, et_item_no, editTexts);
        ModuleUtils.tvChange(activity, tv_item_no, textViews);
    }

    @OnFocusChange(R.id.et_date)
    void dateFocusChange() {
        ModuleUtils.viewChange(ll_date, views);
        ModuleUtils.etChange(activity, et_date, editTexts);
        ModuleUtils.tvChange(activity, tv_date, textViews);
    }

    TransfersReviewSumAdapter adapter;

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
    int TOCOMMIT=1001;

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.TRANSFERS_TO_REVIEW;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(getString(R.string.transfers_to_review)+getString(R.string.list));
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching=true;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_transfers_review;
    }

    @Override
    protected void doBusiness() {
        et_date.setKeyListener(null);
        list=new ArrayList<>();
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==TOCOMMIT){
            onUpdate();
        }
    }

    /**
     * 更新
     */
    private  void  onUpdate(){
        showLoadingDialog();
        list.clear();
        adapter = new TransfersReviewSumAdapter(activity, list);
        ryList.setAdapter(adapter);
        FilterBean filterBean = new FilterBean();
        filterBean.setDoc_no(et_transfers_list.getText().toString().trim());
        filterBean.setDepartment_no(et_department.getText().toString().trim());
        filterBean.setEmployee_no(et_applicant.getText().toString().trim());
        filterBean.setWarehouse_in_no(et_in_warehouse.getText().toString().trim());
        filterBean.setWarehouse_out_no(LoginLogic.getWare());
        filterBean.setDate_begin(startDate);
        filterBean.setDate_end(endDate);
        filterBean.setBarcode_no(et_item_no.getText().toString().trim());
        logic.getOrderData(filterBean, new CommonLogic.GetOrderListener() {
            @Override
            public void onSuccess(List<FilterResultOrderBean> masterDatas) {
                list=masterDatas;
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
            isSearching=true;
            ryList.setVisibility(View.VISIBLE);
            ll_search_input.setVisibility(View.GONE);
            adapter = new TransfersReviewSumAdapter(activity, list);
            ryList.setAdapter(adapter);
            itemClick();
        } catch (Exception e) {
            LogUtils.e(TAG, "showDates---Exception>" + e);
        }
    }

    /**
     * 点击条目
     */
    private void itemClick(){
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, final int position) {
                try{
                    ClickItemPutBean putBean = new ClickItemPutBean();
                    putBean.setReceipt_no(list.get(position).getDoc_no());
                    showLoadingDialog();
                    logic.getOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
                        @Override
                        public void onSuccess(List<ListSumBean> masterDatas) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(TransfersToReviewComActivity.DETAIL, (Serializable) masterDatas);
                            ActivityManagerUtils.startActivityBundleForResult(activity, TransfersToReviewComActivity.class, bundle,TOCOMMIT);
                            dismissLoadingDialog();
                        }
                        @Override
                        public void onFailed(String error) {
                            dismissLoadingDialog();
                            showFailedDialog(error);
                        }
                    });
                }catch (Exception e){
                    LogUtils.e(TAG,"itemClick"+ e);
                }
            }
        });
    }

}
