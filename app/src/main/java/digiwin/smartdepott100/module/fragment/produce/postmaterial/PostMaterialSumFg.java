package digiwin.smartdepott100.module.fragment.produce.postmaterial;


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
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.produce.postmaterial.PostMaterialSecondActivity;
import digiwin.smartdepott100.module.adapter.produce.PostMaterialSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @author 唐孟宇
 * @des 领料过账 数据汇总界面
 * @date 2017/2/23
 */
public class PostMaterialSumFg extends BaseFragment {
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 单号
     */
    @BindView(R.id.tv_head_post_order)
    TextView tv_head_post_order;
    /**
     * 部门
     */
    @BindView(R.id.tv_head_department)
    TextView tv_head_department;
    /**
     * 日期
     */
    @BindView(R.id.tv_head_date)
    TextView tv_head_date;

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

    PostMaterialSecondActivity pactivity;

    CommonLogic commonLogic;

    private boolean upDateFlag;

    PostMaterialSumAdapter adapter;

    List<ListSumBean> sumShowBeanList;

    /**
     * 从汇总界面带入的单头数据
     */
    FilterResultOrderBean orderData;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_putinstore_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PostMaterialSecondActivity) activity;
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }

    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            ClickItemPutBean clickItemPutBean = new ClickItemPutBean();
            clickItemPutBean.setDoc_no(orderData.getDoc_no());
            clickItemPutBean.setWarehouse_no(LoginLogic.getWare());
            showLoadingDialog();
            commonLogic.getOrderSumData(clickItemPutBean, new CommonLogic.GetOrderSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    tv_head_post_order.setText(orderData.getDoc_no());
                    tv_head_date.setText(orderData.getCreate_date());
                    tv_head_department.setText(orderData.getDepartment_name());
                    sumShowBeanList = list;
                    adapter = new PostMaterialSumAdapter(activity, sumShowBeanList);
                    ryList.setAdapter(adapter);
                    upDateFlag = true;
                    toDetail();
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    upDateFlag = false;
                    try {
                        showFailedDialog(error);
                        sumShowBeanList = new ArrayList<ListSumBean>();
                        adapter = new PostMaterialSumAdapter(pactivity, sumShowBeanList);
                        ryList.setAdapter(adapter);
                    } catch (Exception e) {
                        LogUtils.e(TAG, "updateList--getSum--onFailed" + e);
                    }
                }
            });
//            HashMap<String,String> map = new HashMap<>();
//            productBinningLogic.getSum(map, new CommonLogic.GetSumListener() {
//                @Override
//                public void onSuccess(List<SumShowBean> list) {
//                    dismissLoadingDialog();
//                    sumShowBeanList = list;
//                    adapter = new PostMaterialSumAdapter(activity, sumShowBeanList);
//                    ryList.setAdapter(adapter);
//                    upDateFlag = true;
//                    toDetail();
//                }
//
//                @Override
//                public void onFailed(String error) {
//                    dismissLoadingDialog();
//                    upDateFlag = false;
//                    try {
//                        showFailedDialog(error);
//                        sumShowBeanList = new ArrayList<SumShowBean>();
//                        adapter = new PostMaterialSumAdapter(pactivity, sumShowBeanList);
//                        ryList.setAdapter(adapter);
//                    } catch (Exception e) {
//                        LogUtils.e(TAG, "updateList--getSum--onFailed" + e);
//                    }
//                }
//            });
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
        final SumShowBean sumShowBean = new SumShowBean();
        sumShowBean.setItem_no(orderSumData.getItem_no());
        sumShowBean.setItem_name(orderSumData.getItem_name());
        sumShowBean.setAvailable_in_qty(orderSumData.getShortage_qty());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                dismissLoadingDialog();
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
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
        commonLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        pactivity.mZXVp.setCurrentItem(0);
                        pactivity.createNewModuleId(pactivity.module);
                        pactivity.scanFg.initData();
                        initData();
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
        commonLogic = CommonLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
        upDateFlag = false;
        orderData = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable("orderData");
    }

}
