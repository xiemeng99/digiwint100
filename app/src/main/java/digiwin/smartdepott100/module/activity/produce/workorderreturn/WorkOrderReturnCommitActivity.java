package digiwin.smartdepott100.module.activity.produce.workorderreturn;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.produce.WorkOrderReturnSumAdapter;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 依工单退料二次提交
 * @date 2017/3/27
 */
public class WorkOrderReturnCommitActivity extends BaseTitleActivity {
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.tv_head_order)
    TextView tvHeadOrder;
    @BindView(R.id.tv_depart_supplier)
    TextView tvDepartSupplier;
    @BindView(R.id.tv_end_product)
    TextView tvEndProduct;
    @BindView(R.id.tv_end_productname)
    TextView tvEndProductname;

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

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
     * 提交数据
     */
    public static final String COMMITLIST = "commitlist";
    public static final String MODULUECODE = "code";
    List<ListSumBean> sumShowBeanList;
    CommonLogic logic;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        Bundle bundle = getIntent().getExtras();
        module = bundle.getString(MODULUECODE, ModuleCode.OTHER);
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_workorderreturn_commit_sum;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.again_commit);
    }

    @Override
    protected void doBusiness() {
        try {
            FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
            ryList.setLayoutManager(linearLayoutManager);
            Bundle bundle = getIntent().getExtras();
            sumShowBeanList = (List<ListSumBean>) bundle.getSerializable(COMMITLIST);
            if (sumShowBeanList.size()>0)
            {
                ListSumBean sumBean = sumShowBeanList.get(0);
                tvHeadOrder.setText(sumBean.getDoc_no());
                tvDepartSupplier.setText(sumBean.getDepartment_name());
                tvEndProduct.setText(sumBean.getItem_no());
                tvEndProductname.setText(sumBean.getItem_name());
            }
            BaseRecyclerAdapter adapter = new WorkOrderReturnSumAdapter(activity, sumShowBeanList);
            ryList.setAdapter(adapter);
            logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
        } catch (Exception e) {
            LogUtils.e(TAG, "doBusiness()" + e);
        }
    }

    private void sureCommit() {
        try {
            if (null == sumShowBeanList || sumShowBeanList.size() == 0) {
                showFailedDialog(R.string.nodate);
                return;
            }
            showLoadingDialog();
            Map<String, String> map = new HashMap<>();
            logic.commit(map, new CommonLogic.CommitListener() {
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

        } catch (Exception e) {
            LogUtils.e(TAG, "commitMovein-->" + e);
        }
    }

}
