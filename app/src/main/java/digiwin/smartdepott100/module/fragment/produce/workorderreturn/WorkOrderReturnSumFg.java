package digiwin.smartdepott100.module.fragment.produce.workorderreturn;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.produce.workorderreturn.WorkOrderReturnActivity;
import digiwin.smartdepott100.module.activity.produce.workorderreturn.WorkOrderReturnListActivity;
import digiwin.smartdepott100.module.adapter.produce.WorkOrderReturnSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 依工单退料汇总提交界面
 * @date 2017/3/24
 */
public class WorkOrderReturnSumFg extends BaseFragment {

    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.tv_head_order)
    TextView tvHeadOrder;
    @BindView(R.id.tv_depart_supplier)
    TextView tvDepartSupplier;
    @BindView(R.id.tv_end_product)
    TextView tvEndProduct;
    @BindView(R.id.tv_end_productname)
    TextView tvEndProductname;

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

    /**
     * 单头数据
     */
    private ClickItemPutBean mPutBean;


    WorkOrderReturnActivity pactivity;
    CommonLogic commonLogic;

    boolean upDateFlag;

    List<ListSumBean> sumBeanList;

    private BaseRecyclerAdapter adapter;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_workorderreturn_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (WorkOrderReturnActivity) this.activity;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }

    /**
     * 获取数据
     */
    public void updatelist() {
        try{
            showLoadingDialog();
            sumBeanList.clear();
            adapter = new WorkOrderReturnSumAdapter(context, sumBeanList);
            ryList.setAdapter(adapter);
            commonLogic.getOrderSumData(mPutBean, new CommonLogic.GetOrderSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    sumBeanList = list;
                    adapter = new WorkOrderReturnSumAdapter(context, sumBeanList);
                    ryList.setAdapter(adapter);
                    if (null != list && list.size() > 0) {
                        upDateFlag = true;
                        toDetail();
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    upDateFlag = false;
                    sumBeanList.clear();
                    adapter = new WorkOrderReturnSumAdapter(context, sumBeanList);
                    ryList.setAdapter(adapter);
                    showFailedDialog(error);
                }
            });
        }catch (Exception e){
            LogUtils.e(TAG,"updatelist"+e);
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
                    getDetail(sumBeanList.get(pos));
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
        sumShowBean.setAvailable_in_qty(StringUtils.getMinQty(orderSumData.getStock_qty(), orderSumData.getReq_qty()));
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
                        pactivity.finish();
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

    private void initData() {
        commonLogic = CommonLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        upDateFlag = false;
        sumBeanList = new ArrayList<>();
        try {
            FilterResultOrderBean orderHeadBean = (FilterResultOrderBean) activity.getIntent().getSerializableExtra(WorkOrderReturnListActivity.filterBean);
            tvHeadOrder.setText(orderHeadBean.getDoc_no());
            tvDepartSupplier.setText(orderHeadBean.getDepartment_name());
            tvEndProduct.setText(orderHeadBean.getItem_no());
            tvEndProductname.setText(orderHeadBean.getItem_name());
            mPutBean=new ClickItemPutBean();
            mPutBean.setDoc_no(orderHeadBean.getDoc_no());
        }catch (Exception e){
            LogUtils.e(TAG,"initData()"+ e);
        }
    }

}
