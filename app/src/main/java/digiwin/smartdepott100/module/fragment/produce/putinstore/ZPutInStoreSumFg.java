package digiwin.smartdepott100.module.fragment.produce.putinstore;

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
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.produce.putinstore.ZPutInStoreSecondActivity;
import digiwin.smartdepott100.module.adapter.produce.ZPutInStoreSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.ZPutInStoreLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;

/**
 * @author xiemeng
 * @des  入库上架
 * @date 2017/5/26 14:30
 */

public class ZPutInStoreSumFg extends BaseFragment {
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
    @BindView(R.id.tv_head_date)
    TextView tv_head_date;
    /**
     * 部门
     */
    @BindView(R.id.tv_head_department)
    TextView tv_head_department;
    /**
     * 人员
     */
    @BindView(R.id.tv_head_person)
    TextView tv_head_person;
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

    ZPutInStoreSecondActivity pactivity;

    ZPutInStoreLogic zPutInStoreLogic;

    private boolean upDateFlag;

    ZPutInStoreSumAdapter adapter;

    List<ListSumBean> sumShowBeanList;

    FilterResultOrderBean orderData;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_zputinstore_sum;
    }

    @Override
    protected void doBusiness() {
        pactivity = (ZPutInStoreSecondActivity) activity;
        orderData = (FilterResultOrderBean) getActivity().getIntent().getExtras().getSerializable("orderData");
        zPutInStoreLogic = ZPutInStoreLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        tv_head_post_order.setText(orderData.getDoc_no());
        tv_head_date.setText(orderData.getCreate_date());
        tv_head_person.setText(orderData.getEmployee_name());
        tv_head_department.setText(orderData.getDepartment_name());
    }

    /**
     * 汇总展示
     */
    public void upDateList() {
        try {
            sumShowBeanList=new ArrayList<>();
            adapter = new ZPutInStoreSumAdapter(activity, sumShowBeanList);
            ryList.setAdapter(adapter);
            ClickItemPutBean clickItemPutData = new ClickItemPutBean();
            clickItemPutData.setDoc_no(orderData.getDoc_no());
            showLoadingDialog();
            zPutInStoreLogic.getPutInStoreSum(clickItemPutData, new CommonLogic.GetZSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    sumShowBeanList = list;
                    adapter = new ZPutInStoreSumAdapter(activity, sumShowBeanList);
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
                        adapter = new ZPutInStoreSumAdapter(activity, sumShowBeanList);
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
        zPutInStoreLogic.getDetail(map, new CommonLogic.GetDetailListener() {
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
        zPutInStoreLogic.commit(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        pactivity.mZXVp.setCurrentItem(0);
                        pactivity.createNewModuleId(pactivity.module);
                        initData();
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
        upDateFlag = false;
        tv_head_date.setText("");
        tv_head_post_order.setText("");
        tv_head_department.setText("");
    }
}
