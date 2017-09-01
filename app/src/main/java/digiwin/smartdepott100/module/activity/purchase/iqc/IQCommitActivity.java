package digiwin.smartdepott100.module.activity.purchase.iqc;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.purchase.IQCCommitAdapter;
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

public class IQCommitActivity extends BaseFirstModuldeActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_check_no)
    TextView tvCheckNo;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_check_employee)
    TextView tvCheckEmploy;
    @BindView(R.id.tv_check_depart)
    TextView tvCheckDepart;
    @BindView(R.id.rl_top)
    LinearLayout rlTop;
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
        tvCheckNo.setText(filterOneBean.getDoc_no());
        tvDate.setText(filterOneBean.getCreate_date());
        tvCheckEmploy.setText(filterOneBean.getEmployee_name());
        tvCheckDepart.setText(filterOneBean.getDepartment_name());
    }

    public void upDateList() {
        showLoadingDialog();
        iqcLogic.getIQCSum(filterOneBean, new CommonLogic.GetZSumListener() {
            @Override
            public void onSuccess(List<ListSumBean> list) {
                dismissLoadingDialog();
                sumBeanList = list;
                adapter = new IQCCommitAdapter(context, sumBeanList);
                ryList.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                sumBeanList.clear();
                //清空列表
                adapter = new IQCCommitAdapter(context, sumBeanList);
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

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }
}
