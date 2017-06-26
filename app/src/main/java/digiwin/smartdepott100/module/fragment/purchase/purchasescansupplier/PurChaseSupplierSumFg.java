package digiwin.smartdepott100.module.fragment.purchase.purchasescansupplier;

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
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.purchase.purchasesupplier.PurchaseSupplierActivity;
import digiwin.smartdepott100.module.adapter.purchase.PurchaseSupplierSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.purchase.PurchaseGoodScanLogic;
import digiwin.smartdepott100.module.logic.purchase.PurchaseSupplierLogic;

/**
 * @author xiemeng
 * @des 扫码收货 供应商
 * @date 2017/6/13 09:52
 */

public class PurChaseSupplierSumFg extends BaseFragment {
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    /**
     * 送货单号
     */
    @BindView(R.id.tv_head_post_order)
    TextView tvHeadPostOrder;
    /**
     * 日期
     */
    @BindView(R.id.tv_head_plan_date)
    TextView tvHeadPlanDate;
    /**
     * 供应商
     */
    @BindView(R.id.tv_head_provider)
    TextView tvHeadProvider;

    @BindView(R.id.tv_delivery_address)
    TextView tvDeliveryAddress;

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

    PurchaseSupplierActivity pactivity;

    PurchaseSupplierLogic supplierLogic;

    private boolean upDateFlag;

    PurchaseSupplierSumAdapter adapter;

    List<ListSumBean> sumShowBeanList;

    FilterResultOrderBean orderData;
    @Override
    protected int bindLayoutId() {
        return R.layout.fg_purchasesupplier_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (PurchaseSupplierActivity) activity;
        supplierLogic = PurchaseSupplierLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        upDateFlag = false;
        Bundle bundle = getActivity().getIntent().getExtras();
        orderData = (FilterResultOrderBean) bundle.getSerializable("orderData");
        tvHeadPlanDate.setText(orderData.getCreate_date());
        tvHeadPostOrder.setText(orderData.getDoc_no());
        tvHeadProvider.setText(orderData.getSupplier_name());
        tvDeliveryAddress.setText(orderData.getDelivery_address());
    }

    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            ClickItemPutBean clickItemPutData = new ClickItemPutBean();
            clickItemPutData.setDoc_no(orderData.getDoc_no());
            //clickItemPutData.setWarehouse_no(LoginLogic.getWare());
            clickItemPutData.setCreate_date(orderData.getCreate_date());
            showLoadingDialog();
            supplierLogic.getPGSSumData(clickItemPutData, new PurchaseGoodScanLogic.GetZSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    sumShowBeanList = list;
                    if(list.size()>0){
                        adapter = new PurchaseSupplierSumAdapter(pactivity, sumShowBeanList);
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
                        adapter = new PurchaseSupplierSumAdapter(pactivity, sumShowBeanList);
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
        sumShowBean.setAvailable_in_qty(orderSumData.getApply_qty());
        supplierLogic.getDetail(map, new CommonLogic.GetDetailListener() {
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
                showFailedDialog(error, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {

                    }
                });
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
        supplierLogic.commitPGSData(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        pactivity.mZXVp.setCurrentItem(0);
                        pactivity.createNewModuleId(pactivity.module);
                        tvHeadPlanDate.setText("");
                        tvHeadPostOrder.setText("");
                        tvHeadProvider.setText("");
                        tvDeliveryAddress.setText("");
                        pactivity.scanFg.initData();
                        activity.finish();
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


}
