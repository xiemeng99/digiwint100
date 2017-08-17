package digiwin.smartdepott100.module.fragment.produce.suitpicking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.produce.suitpicking.SuitPickingActivity;
import digiwin.smartdepott100.module.adapter.produce.SuitPickingSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.SuitPickingLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;

/**
 * @author xiemeng
 * @des 更名领料过账汇总页面
 * @date 2017/5/28 16:25
 */

public class SuitPickingSumFg extends BaseFragment {
    @BindView(R.id.tv_head_picking_no)
    TextView tvHeadPickingNo;
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.tv_applicant)
    TextView tvApplicant;
    @BindView(R.id.tv_depart)
    TextView tvDepart;

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
     * 出通单号
     */
    private ClickItemPutBean mPutBean;

    /**
     * 订阅成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(ClickItemPutBean bean) {
        mPutBean = bean;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    SuitPickingActivity pactivity;
    SuitPickingLogic suitPickingLogic;

    boolean upDateFlag;

    List<ListSumBean> sumBeanList;
    private BaseRecyclerAdapter adapter;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_suitpicking_sum;
    }

    @Override
    protected void doBusiness() {
        upDateFlag = false;
        sumBeanList = new ArrayList<>();
        pactivity = (SuitPickingActivity) activity;
        suitPickingLogic = SuitPickingLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
    }

    public void upDateList() {
        if (null == mPutBean) {
            showFailedDialog(R.string.scan_picking_no, new OnDialogClickListener() {
                @Override
                public void onCallback() {
                    pactivity.mZXVp.setCurrentItem(0);
                }
            });
            return;
        }
        mPutBean.setWarehouse_no(LoginLogic.getWare());
        showLoadingDialog();
        suitPickingLogic.getSuitPickingSum(mPutBean, new CommonLogic.GetZSumListener() {
            @Override
            public void onSuccess(List<ListSumBean> list) {
                dismissLoadingDialog();
                sumBeanList = list;
                adapter = new SuitPickingSumAdapter(context, sumBeanList);
                ryList.setAdapter(adapter);
                if (null != list && list.size() > 0) {
                    upDateFlag = true;
                    toDetail();
                    tvHeadPickingNo.setText(list.get(0).getDoc_no());
                    tvData.setText(list.get(0).getCreate_date());
                    tvApplicant.setText(list.get(0).getEmployee_name());
                    tvDepart.setText(list.get(0).getDepartment_name());
                }
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                upDateFlag = false;
                sumBeanList.clear();
                adapter = new SuitPickingSumAdapter(context, sumBeanList);
                ryList.setAdapter(adapter);
                showFailedDialog(error);
            }
        });

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
        sumShowBean.setAvailable_in_qty(orderSumData.getApply_qty());
        suitPickingLogic.getDetail(map, new CommonLogic.GetDetailListener() {
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
        suitPickingLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        tvData.setText("");
                        tvApplicant.setText("");
                        tvDepart.setText("");
                        tvHeadPickingNo.setText("");
                        pactivity.scanFg.initData();
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
}
