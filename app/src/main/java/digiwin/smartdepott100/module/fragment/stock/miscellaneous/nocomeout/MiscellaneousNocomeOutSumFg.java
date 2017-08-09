package digiwin.smartdepott100.module.fragment.stock.miscellaneous.nocomeout;


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
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.stock.miscellaneousissues.MiscellaneousNocomeOutActivity;
import digiwin.smartdepott100.module.activity.stock.miscellaneousissues.MiscellaneousissuesOutActivity;
import digiwin.smartdepott100.module.adapter.stock.MiscellaneousNocomeOutSumAdapter;
import digiwin.smartdepott100.module.adapter.stock.MiscellaneousOutSumAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.stock.miscellaneousissues.MiscellaneousNocomeOutLogic;
import digiwin.smartdepott100.module.logic.stock.miscellaneousissues.MiscellaneousissuesOutLogic;


/**
 * @author 唐孟宇
 * @des 杂项发料 数据汇总界面
 */
public class MiscellaneousNocomeOutSumFg extends BaseFragment {
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

    MiscellaneousNocomeOutActivity pactivity;

    MiscellaneousNocomeOutLogic commonLogic;

    private boolean upDateFlag;

    MiscellaneousNocomeOutSumAdapter adapter;

    List<ListSumBean> sumShowBeanList;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_miscellaneous_nocome_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (MiscellaneousNocomeOutActivity) activity;
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        initData();

    }


    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            HashMap<String,String> map = new HashMap<>();
            map.put(AddressContants.WAREHOUSE_NO, LoginLogic.getWare());
            showLoadingDialog();
            commonLogic.getMIISumData(map, new CommonLogic.GetZSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    sumShowBeanList = new ArrayList<ListSumBean>();
                    sumShowBeanList = list;
                    if (list.size() > 0) {
                        adapter = new MiscellaneousNocomeOutSumAdapter(pactivity, sumShowBeanList);
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
                        adapter = new MiscellaneousNocomeOutSumAdapter(pactivity, sumShowBeanList);
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
       final SumShowBean showBean = new SumShowBean();
        showBean.setItem_no(orderSumData.getItem_no());
        showBean.setAvailable_in_qty("0");
        showBean.setItem_name(orderSumData.getItem_name());
        map.put(AddressContants.ITEM_NO, orderSumData.getItem_no());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, showBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
                dismissLoadingDialog();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
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
        commonLogic.commitMIIData(map, new CommonLogic.CommitListListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        sumShowBeanList = new ArrayList<ListSumBean>();
                        adapter = new MiscellaneousNocomeOutSumAdapter(pactivity,sumShowBeanList);
                        ryList.setAdapter(adapter);
                        pactivity.createNewModuleId(pactivity.module);
                        pactivity.mZXVp.setCurrentItem(0);
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
    private void initData() {
        commonLogic = MiscellaneousNocomeOutLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
        upDateFlag = false;
    }

}
