package digiwin.smartdepott100.module.fragment.purchase.purchaseinstore;


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
import digiwin.smartdepott100.module.activity.purchase.purchaseinstore.PurchaseInStoreSecondActivity;
import digiwin.smartdepott100.module.adapter.purchase.PurchaseInStorageSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @author 唐孟宇
 * @des 采购入库 数据汇总界面
 */
public class PurchaseInStoreSumFg extends BaseFragment {
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 单号
     */
    @BindView(R.id.tv_head_post_order)
    TextView tv_head_post_order;
    /**
     * 日期
     */
    @BindView(R.id.tv_head_plan_date)
    TextView tv_head_plan_date;
    /**
     * 部门
     */
    @BindView(R.id.tv_head_provider)
    TextView tv_head_provider;

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

    PurchaseInStoreSecondActivity pactivity;

    CommonLogic commonLogic;

    private boolean upDateFlag;

    PurchaseInStorageSumAdapter adapter;

    List<ListSumBean> sumShowBeanList;

    FilterResultOrderBean orderData;
    @Override
    protected int bindLayoutId() {
        return R.layout.activity_purchase_in_store_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PurchaseInStoreSecondActivity) activity;
        commonLogic = CommonLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        upDateFlag = false;
        Bundle bundle = getActivity().getIntent().getExtras();
        orderData = (FilterResultOrderBean) bundle.getSerializable("orderData");
        tv_head_plan_date.setText("");
        tv_head_post_order.setText("");
        tv_head_provider.setText("");
    }

    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            ClickItemPutBean clickItemPutData = new ClickItemPutBean();
            clickItemPutData.setDoc_no(orderData.getDoc_no());
            AccoutBean accoutBean = LoginLogic.getUserInfo();
            if(null != accoutBean){
                clickItemPutData.setWarehouse_in_no(accoutBean.getWare());
            }
            clickItemPutData.setReceipt_date(orderData.getCreate_date());
            showLoadingDialog();
            commonLogic.getOrderSumData(clickItemPutData, new CommonLogic.GetOrderSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    sumShowBeanList = list;
                    if(list.size()>0){
                        tv_head_plan_date.setText(list.get(0).getReceipt_date());
                        tv_head_post_order.setText(list.get(0).getReceipt_no());
                        tv_head_provider.setText(list.get(0).getSupplier_name());
                        adapter = new PurchaseInStorageSumAdapter(pactivity, sumShowBeanList);
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
                        adapter = new PurchaseInStorageSumAdapter(pactivity, sumShowBeanList);
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
        final SumShowBean sumShowBean = new SumShowBean();
        sumShowBean.setItem_name(orderSumData.getItem_name());
        sumShowBean.setItem_no(orderSumData.getItem_no());
        sumShowBean.setAvailable_in_qty(orderSumData.getReq_qty());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
                dismissLoadingDialog();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }

    private void sureCommit(){
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
                        tv_head_plan_date.setText("");
                        tv_head_post_order.setText("");
                        tv_head_provider.setText("");
                        pactivity.scanFg.initData();
                        activity.finish();                    }
                });
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });

    }


}
