package digiwin.smartdepott100.module.activity.purchase.iqc;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.purchase.IQCCommitAdapter;
import digiwin.smartdepott100.module.adapter.purchase.IQCOutCommitAdapter;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.purchase.IQCLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;

/**
 * @author xiemeng
 * @des iqc提交列表
 * @date 2017/5/30 09:57
 */

public class IQCommitActivity extends BaseTitleActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_purchase_order)
    TextView tvPurchaseOrder;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.rl_top)
    LinearLayout rlTop;
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.line_technics_name)
    View lineTechnicsName;
    @BindView(R.id.tv_technics_name)
    TextView tvTechnicsName;
    @BindView(R.id.ll_technics_name)
    LinearLayout llTechnicsName;

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

    public static final String filterBean = "filterBean";

    private List<ListSumBean> sumBeanList;

    private IQCLogic iqcLogic;

    private FilterResultOrderBean filterOneBean;

    private BaseRecyclerAdapter adapter;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PURCHASECHECK;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.iqc_check);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_iqc_commit;
    }

    @Override
    protected void doBusiness() {
        sumBeanList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        filterOneBean = (FilterResultOrderBean) bundle.getSerializable(filterBean);
        iqcLogic = IQCLogic.getInstance(context, module, mTimestamp.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        ryList.setLayoutManager(linearLayoutManager);
        upDateList();
        tvPurchaseOrder.setText(filterOneBean.getDoc_no());
        tvDate.setText(filterOneBean.getCreate_date());
        tvSupplier.setText(filterOneBean.getSupplier_name());
        if ("1".equals(filterOneBean.getPurchase_type())) {
            llTechnicsName.setVisibility(View.GONE);
            lineTechnicsName.setVisibility(View.GONE);
        }
    }

    public void upDateList() {
        showLoadingDialog();
        iqcLogic.getIQCSum(filterOneBean, new CommonLogic.GetZSumListener() {
            @Override
            public void onSuccess(List<ListSumBean> list) {
                dismissLoadingDialog();
                sumBeanList = list;
                if ("2".equals(filterOneBean.getPurchase_type())) {
                    adapter = new IQCOutCommitAdapter(context, sumBeanList);
                } else {
                    adapter = new IQCCommitAdapter(context, sumBeanList);
                }
                ryList.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                sumBeanList.clear();
                //清空列表
                if ("2".equals(filterOneBean.getPurchase_type())) {
                    adapter = new IQCOutCommitAdapter(context, sumBeanList);
                } else {
                    adapter = new IQCCommitAdapter(context, sumBeanList);
                }
                ryList.setAdapter(adapter);
                showFailedDialog(error);
            }
        });

    }


    private void sureCommit() {
        if (null == sumBeanList || sumBeanList.size() == 0) {
            showFailedDialog(R.string.nodate);
            return;
        }
//        Map<Integer,Boolean> flagMap=new HashMap<>();
//        if (adapter instanceof IQCCommitAdapter) {
//            IQCCommitAdapter iqcCommitAdapter = (IQCCommitAdapter)adapter;
//            flagMap = iqcCommitAdapter.getmFlagMap();
//        }
//        else if (adapter instanceof IQCOutCommitAdapter) {
//            IQCOutCommitAdapter iqcCommitAdapter = (IQCOutCommitAdapter)adapter;
//            flagMap = iqcCommitAdapter.getmFlagMap();
//        }
//        for (int i = 0; i < sumBeanList.size(); i++) {
//            if (flagMap.get(i)){
//                sumBeanList.get(i).setResult_type("1");
//            }else {
//                sumBeanList.get(i).setResult_type("2");
//            }
//
//        }
        showLoadingDialog();
        iqcLogic.commit(sumBeanList, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        finish();
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
