package digiwin.smartdepott100.module.fragment.stock.miscellaneous.in;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.stock.miscellaneousissues.MiscellaneousissuesInLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.stock.miscellaneousissues.MiscellaneousissuesInActivity;
import digiwin.smartdepott100.module.adapter.stock.MiscellaneousInSumAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @author 唐孟宇
 * @des 杂项收料 数据汇总界面
 */
public class MiscellaneousIssueInSumFg extends BaseFragment {
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 杂收单号
     */
    @BindView(R.id.tv_head_miscellaneous_in_no)
    TextView tv_head_miscellaneous_in_no;
    /**
     * 日期
     */
    @BindView(R.id.tv_head_plan_date)
    TextView tv_head_plan_date;
    /**
     * 申请人
     */
    @BindView(R.id.tv_head_person)
    TextView tv_head_person;
    /**
     * 部门
     */
    @BindView(R.id.tv_head_department)
    TextView tv_head_department;

    @OnClick(R.id.commit)
    void commit() {
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                sureCommit();
            }

            @Override
            public void onCallback2() {

            }
        });
    }

    MiscellaneousissuesInActivity mactivity;

    MiscellaneousissuesInLogic commonLogic;

    private boolean upDateFlag;

    MiscellaneousInSumAdapter adapter;

    List<ListSumBean> sumShowBeanList;
    FilterResultOrderBean orderData;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_miscellaneous_in_sum;
    }

    @Override
    protected void doBusiness() {
        mactivity = (MiscellaneousissuesInActivity) activity;
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
        Bundle bundle = getActivity().getIntent().getExtras();
        orderData = (FilterResultOrderBean) bundle.getSerializable("orderData");
        tv_head_plan_date.setText(orderData.getCreate_date());
        tv_head_miscellaneous_in_no.setText(orderData.getDoc_no());
        tv_head_person.setText(orderData.getEmployee_name());
        tv_head_department.setText(orderData.getDepartment_name());
    }

    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put(AddressContants.DOC_NO,orderData.getDoc_no());
            map.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
            showLoadingDialog();
            commonLogic.getMIISumData(map, new CommonLogic.GetZSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    sumShowBeanList = new ArrayList<ListSumBean>();
                    sumShowBeanList = list;
                    if (list.size() > 0) {
                        adapter = new MiscellaneousInSumAdapter(mactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        upDateFlag = true;
                        toDetail();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    upDateFlag = false;
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<ListSumBean>();
                        adapter = new MiscellaneousInSumAdapter(mactivity, sumShowBeanList);
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

    /**
     * 查看单笔料明细
     */
    public void toDetail() {
        try {
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int pos) {
                    getDetail(sumShowBeanList.get(pos));
                }
            });
        } catch (Exception e) {
            LogUtils.e(TAG, "toDetail-->" + e);
        }
    }


    /**
     * 查看明细
     */
    private void getDetail(final ListSumBean orderSumData) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put(AddressContants.ITEM_NO, orderSumData.getItem_no());
        map.put(AddressContants.SIGNCODE, orderSumData.getSigncode());
        final SumShowBean sumShowBean = new SumShowBean();
        sumShowBean.setItem_no(orderSumData.getItem_no());
        sumShowBean.setItem_name(orderSumData.getItem_name());
        sumShowBean.setAvailable_in_qty(orderSumData.getApply_qty());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, mactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, mactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                dismissLoadingDialog();
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, mactivity.DETAILCODE);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    private void sureCommit() {
        if (!upDateFlag) {
            showFailedDialog(R.string.nodate);
            return;
        }
        showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        commonLogic.commitMIIData(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        sumShowBeanList = new ArrayList<ListSumBean>();
                        adapter = new MiscellaneousInSumAdapter(mactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                        mactivity.createNewModuleId(mactivity.module);
                        tv_head_plan_date.setText("");
                        tv_head_miscellaneous_in_no.setText("");
                        tv_head_person.setText("");
                        tv_head_department.setText("");
                        mactivity.mZXVp.setCurrentItem(0);
                        mactivity.scanFg.initData();
                        initData();
                        mactivity.finish();
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });

    }

    public void initData() {
        commonLogic = MiscellaneousissuesInLogic.getInstance(mactivity, mactivity.module, mactivity.mTimestamp.toString());
        upDateFlag = false;
    }

}
