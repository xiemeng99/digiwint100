package digiwin.smartdepott100.module.fragment.produce.productionleader;

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
import digiwin.smartdepott100.module.logic.produce.productionleader.ProductionLeaderLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.produce.productionleader.ProductionLeaderActivity;
import digiwin.smartdepott100.module.adapter.produce.ProductionLeaderAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @des 生产超领 汇总页
 * @date 2017/3/30
 */
public class ProductionLeaderSumFg extends BaseFragment {

    /**
     * 超领单号
     */
    @BindView(R.id.tv_super_number)
    TextView tv_super_number;

    /**
     * 日期
     */
    @BindView(R.id.tv_date)
    TextView tv_date;

    /**
     * 部门
     */
    @BindView(R.id.tv_department)
    TextView tv_department;

    /**
     * 申请人
     */
    @BindView(R.id.tv_applicant)
    TextView tv_applicant;

    ProductionLeaderAdapter adapter;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @OnClick(R.id.commit)
    void commit() {
        sureCommit();
    }

    FilterResultOrderBean localData = new FilterResultOrderBean();

    ProductionLeaderActivity pactivity;
    ProductionLeaderLogic commonLogic;

    boolean upDateFlag;

    List<ListSumBean> sumBeanList;

    @Override
    protected void doBusiness() {
        pactivity = (ProductionLeaderActivity) activity;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
        upDateFlag = false;
        localData = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable("data");

    }

    public void upDateList() {
        try {
            ClickItemPutBean putBean = new ClickItemPutBean();
            putBean.setDoc_no(localData.getDoc_no());
            putBean.setWarehouse_no(LoginLogic.getWare());

            commonLogic = ProductionLeaderLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());

            showLoadingDialog();
            commonLogic.getPLSumData(putBean, new ProductionLeaderLogic.GetZSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    if (list.size() > 0) {
                        dismissLoadingDialog();
                        upDateFlag = true;
                        adapter = new ProductionLeaderAdapter(getActivity(), list);
                        ryList.setAdapter(adapter);
                        //获取单号 日期 人员参数
                        tv_super_number.setText(list.get(0).getDoc_no());
                        tv_date.setText(list.get(0).getCreate_date());
                        tv_department.setText(list.get(0).getDepartment_name());
                        tv_applicant.setText(list.get(0).getEmployee_name());
                        sumBeanList = new ArrayList<ListSumBean>();
                        sumBeanList = list;
                        adapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                getDetail(sumBeanList.get(position));
                            }
                        });
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
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put(AddressContants.ITEM_NO, orderSumData.getItem_no());
        final SumShowBean sumShowBean = new SumShowBean();
        sumShowBean.setItem_no(orderSumData.getItem_no());
        sumShowBean.setItem_name(orderSumData.getItem_name());
        sumShowBean.setAvailable_in_qty(orderSumData.getApply_qty());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
//                bundle.putSerializable(CommonDetailActivity.A,);
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

        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                HashMap<String, String> map = new HashMap<>();
                map.put(AddressContants.DOC_NO, tv_super_number.getText().toString().trim());
                commonLogic.commitPLData(map, new CommonLogic.CommitListener() {
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
        return R.layout.fg_productionleadersum;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
