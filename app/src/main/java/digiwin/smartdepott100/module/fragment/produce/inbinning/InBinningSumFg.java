package digiwin.smartdepott100.module.fragment.produce.inbinning;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.produce.inbinning.InBinningActivity;
import digiwin.smartdepott100.module.activity.produce.inbinning.InBinningDetailActivity;
import digiwin.smartdepott100.module.activity.sale.scanout.ScanOutDetailActivity;
import digiwin.smartdepott100.module.adapter.produce.InBinningSumAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.InBinningLogic;

/**
 * @author 孙长权
 * @des 装箱入库--汇总
 * @date 2017/3/23
 */
public class InBinningSumFg extends BaseFragment {

    InBinningSumAdapter adapter;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @OnClick(R.id.commit)
    void commit() {
        sureCommit();
    }

    InBinningActivity pactivity;

    InBinningLogic commonLogic;

    boolean upDateFlag;
    /**
     * 提交数据源
     */
    List<ListSumBean>  sumBeanList;
    /**
     * 从列表页传回来的信息
     */
    ListSumBean listSumBean;

    @Override
    protected void doBusiness() {
        pactivity = (InBinningActivity) activity;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
        upDateFlag = false;
        listSumBean = (ListSumBean) pactivity.getIntent().getExtras().getSerializable("data");
        commonLogic = InBinningLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
    }

    public void upDateList() {
        try {
            FilterBean putBean = new FilterBean();
            putBean.setDoc_no(listSumBean.getDoc_no());
            putBean.setItem_no(listSumBean.getItem_no());
            showLoadingDialog();
            commonLogic.getInBinningList(putBean, new CommonLogic.GetZSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    dismissLoadingDialog();
                    if(list.size() > 0){
                        upDateFlag = true;
                        adapter = new InBinningSumAdapter(activity,list);
                        ryList.setAdapter(adapter);
                        sumBeanList = list;
                        adapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                getDetail(sumBeanList.get(position));
                            }
                        });
                    }else{
                        showFailedDialog(getResources().getString(R.string.nodate));
                    }
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查看明细
     */
    public void getDetail(final ListSumBean orderSumData) {
        final SumShowBean sumShowBean = new SumShowBean();
        sumShowBean.setItem_name(listSumBean.getItem_name());
        sumShowBean.setItem_no(listSumBean.getItem_no());
        sumShowBean.setWo_no(listSumBean.getWo_no());
        sumShowBean.setAvailable_in_qty(listSumBean.getApply_qty());
        Bundle bundle = new Bundle();
        bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
        bundle.putString(InBinningDetailActivity.MODULECODE, pactivity.module);
        bundle.putSerializable(InBinningDetailActivity.ONESUM, sumShowBean);
        ActivityManagerUtils.startActivityBundleForResult(activity, InBinningDetailActivity.class, bundle, pactivity.DETAILCODE);


    }

    private void sureCommit(){
        if (!upDateFlag){
            showFailedDialog(R.string.nodate);
            return;
        }

        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                HashMap<String, String> map = new HashMap<>();
                commonLogic.commit(map, new CommonLogic.CommitListener() {
                    @Override
                    public void onSuccess(String msg) {
                        dismissLoadingDialog();
                        showCommitSuccessDialog(msg, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
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
            @Override
            public void onCallback2() {

            }
        });
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_in_binning_sum;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
