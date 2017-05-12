package digiwin.smartdepott100.module.fragment.produce.inbinning;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.produce.inbinning.InBinningActivity;
import digiwin.smartdepott100.module.activity.sale.scanout.ScanOutDetailActivity;
import digiwin.smartdepott100.module.adapter.produce.InBinningListAdapter;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.produce.InBinningBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 孙长权
 * @des 装箱入库--汇总
 * @date 2017/3/23
 */
public class InBinningSumFg extends BaseFragment {

    InBinningListAdapter adapter;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @OnClick(R.id.commit)
    void commit() {
        sureCommit();
    }

    InBinningActivity pactivity;

    CommonLogic commonLogic;

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
        commonLogic = CommonLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
    }

    public void upDateList() {
        try {
            InBinningBean putBean = new InBinningBean();
            putBean.setWo_no(listSumBean.getWo_no());
            putBean.setItem_no(listSumBean.getItem_no());
            putBean.setDepartment_no(listSumBean.getDepartment_no());

            showLoadingDialog();
            commonLogic.getOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    if(list.size() > 0){
                        dismissLoadingDialog();
                        upDateFlag = true;
                        adapter = new InBinningListAdapter(activity,true,list);
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
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.ITEM_NO, orderSumData.getItem_no());
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                ActivityManagerUtils.startActivityBundleForResult(activity, ScanOutDetailActivity.class,bundle,pactivity.DETAILCODE);
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
