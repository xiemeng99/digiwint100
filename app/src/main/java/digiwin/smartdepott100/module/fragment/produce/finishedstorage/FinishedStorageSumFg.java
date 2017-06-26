package digiwin.smartdepott100.module.fragment.produce.finishedstorage;


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
import digiwin.smartdepott100.module.activity.produce.finishstorage.FinishedStorageActivity;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.adapter.produce.FinishedStorageSumAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.FinishedStorageLogic;


/**
 * @author xiemeng
 * @des 生产完工入库数据汇总界面
 * @date 2017/2/23
 */
public class FinishedStorageSumFg extends BaseFragment {
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

    FinishedStorageActivity pactivity;

    FinishedStorageLogic commonLogic;

    private boolean upDateFlag;

    FinishedStorageSumAdapter adapter;

    List<SumShowBean> sumShowBeanList;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_finishedstorage_sum;
    }

    @Override
    protected void doBusiness() {
        sumShowBeanList=new ArrayList<>();
        pactivity = (FinishedStorageActivity) activity;
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
       initData();
    }


    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            Map<String, String> map = new HashMap<>();
            sumShowBeanList.clear();
            adapter = new FinishedStorageSumAdapter(activity, sumShowBeanList);
            ryList.setAdapter(adapter);
            showLoadingDialog();
            commonLogic.getFinishStorageSum(map, new CommonLogic.GetSumListener() {
                    @Override
                    public void onSuccess(List<SumShowBean> list) {
                        sumShowBeanList = list;
                        adapter = new FinishedStorageSumAdapter(activity, sumShowBeanList);
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
    private void getDetail(final SumShowBean sumShowBean) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put(AddressContants.ITEM_NO, sumShowBean.getItem_no());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable)detailShowBeen);
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
                        pactivity.moduleVp.setCurrentItem(0);
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
        commonLogic = FinishedStorageLogic.getInstance(activity, pactivity.module, pactivity.mTimestamp.toString());
        upDateFlag = false;
    }

}
