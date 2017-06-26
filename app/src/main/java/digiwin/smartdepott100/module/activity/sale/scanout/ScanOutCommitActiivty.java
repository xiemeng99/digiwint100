package digiwin.smartdepott100.module.activity.sale.scanout;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
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
import digiwin.smartdepott100.module.adapter.sale.scanout.ScanOutStoreSumAdapter;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 毛衡
 * @des 扫码出货 未完成事项明细
 * @date 2017/4/5
 */

public class ScanOutCommitActiivty extends BaseTitleActivity {

    /**
     * 提交数据
     */
    public static final String COMMITLIST = "commitlist";
    public static final String MODULUECODE = "code";
    List<ListSumBean> sumShowBeanList;
    CommonLogic logic;

    @BindView(R.id.tv_head_post_order)
    TextView tvHeadPostOrder;
    @BindView(R.id.tv_head_date)
    TextView tvHeadDate;
    @BindView(R.id.tv_custom)
    TextView tvCustom;
    @BindView(R.id.commit)
    Button commit;
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

    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

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
        return R.layout.activity_scanout_commit;
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
                tvCustom.setText(sumBean.getCustomer_name());
                tvHeadDate.setText(sumBean.getCreate_date());
                tvHeadPostOrder.setText(sumBean.getDoc_no());
            }
            BaseRecyclerAdapter adapter = new ScanOutStoreSumAdapter(activity, sumShowBeanList);
            ryList.setAdapter(adapter);
            logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
        } catch (Exception e) {
            LogUtils.e(TAG, "doBusiness()" + e);
        }
    }

}
