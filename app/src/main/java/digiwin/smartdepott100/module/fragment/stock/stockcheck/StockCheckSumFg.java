package digiwin.smartdepott100.module.fragment.stock.stockcheck;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.stock.storecheck.StockCheckActivity;
import digiwin.smartdepott100.module.adapter.stock.StockCheckSumAdapter;
import digiwin.smartdepott100.module.adapter.stock.storeallot.StoreAllotSumAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.stock.StockCheckLogic;
import digiwin.smartdepott100.module.logic.stock.StoreAllotLogic;

/**
 * @author xiemeng
 * @des 盘点汇总
 * @date 2017/8/12
 */
public class StockCheckSumFg extends BaseFragment {
    @BindView(R.id.ry_list)
    RecyclerView ryList;

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

    StockCheckActivity pactivity;

    StockCheckLogic stockCheckLogic;

    private boolean upDateFlag;

    BaseRecyclerAdapter adapter;

    List<ListSumBean> sumShowBeanList;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_stockcheck_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (StockCheckActivity) activity;
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();
    }

    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            sumShowBeanList.clear();
            adapter = new StockCheckSumAdapter(activity, sumShowBeanList);
            ryList.setAdapter(adapter);
            Map<String, String> map = new HashMap<>();
            FilterResultOrderBean  data = (FilterResultOrderBean) activity.getIntent().getExtras().get("data");
            map.put(AddressContants.WAREHOUSEOUTNO, LoginLogic.getWare());
            map.put(AddressContants.DOC_NO,data.getDoc_no());
            showLoadingDialog();
            stockCheckLogic.getStockCheckSum(map, new CommonLogic.GetZSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    sumShowBeanList = list;
                    adapter = new StockCheckSumAdapter(activity, sumShowBeanList);
                    ryList.setAdapter(adapter);
                    upDateFlag = true;
                    toDetail();
                    dismissLoadingDialog();
                }

                @Override
                public void onFailed(String error) {
                    upDateFlag = false;
                    dismissLoadingDialog();
                    showFailedDialog(error);
                    sumShowBeanList = new ArrayList<ListSumBean>();
                    adapter = new StockCheckSumAdapter(activity, sumShowBeanList);
                    ryList.setAdapter(adapter);
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
    private void getDetail(final ListSumBean sumShowBean) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        final SumShowBean toDetailBean = new SumShowBean();
        toDetailBean.setItem_no(sumShowBean.getItem_no());
        toDetailBean.setAvailable_in_qty("0");
        map.put(AddressContants.ITEM_NO, sumShowBean.getItem_no());
        stockCheckLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, toDetailBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                dismissLoadingDialog();
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
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
        map.put("checkType",pactivity.scanFg.checkType);
        stockCheckLogic.commit(map, new CommonLogic.CommitListener() {
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

    public void initData() {
        sumShowBeanList = new ArrayList<>();
        stockCheckLogic = StockCheckLogic.getInstance(activity, pactivity.module, pactivity.mTimestamp.toString());
        upDateFlag = false;
    }

}
