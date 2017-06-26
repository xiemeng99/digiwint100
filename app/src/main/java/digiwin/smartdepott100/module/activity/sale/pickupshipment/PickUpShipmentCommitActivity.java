package digiwin.smartdepott100.module.activity.sale.pickupshipment;

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
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.sale.pickupshipment.PickUpShipmentSumAdapter;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @module 出货过账 二次过账
 * @date 2017/4/4
 */

public class PickUpShipmentCommitActivity extends BaseTitleActivity{

    private PickUpShipmentCommitActivity activity;
    public static final String MODULUECODE = "code";

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    /**
     * 出货单号
     */
    @BindView(R.id.tv_shipping_order)
    TextView tv_shipping_order;

    /**
     * 申请日期
     */
    @BindView(R.id.tv_apply_date)
    TextView tv_apply_date;

    /**
     * 客户
     */
    @BindView(R.id.tv_custom)
    TextView tv_custom;

    PickUpShipmentSumAdapter adapter;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    List<ListSumBean> sumShowBeanList;

    CommonLogic logic;


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

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_pickupshipmentcommit;
    }

    @Override
    protected void doBusiness() {


        FullyLinearLayoutManager linearLayoutManager = null;
        try {
            linearLayoutManager = new FullyLinearLayoutManager(activity);
            ryList.setLayoutManager(linearLayoutManager);
            Bundle bundle = getIntent().getExtras();
            sumShowBeanList = (List<ListSumBean>) bundle.getSerializable(COMMITLIST);
            if(null != sumShowBeanList && sumShowBeanList.size() > 0){
                ListSumBean localData = sumShowBeanList.get(0);
                //获取单号 日期 人员参数
                tv_shipping_order.setText(localData.getDoc_no());
                tv_apply_date.setText(localData.getCreate_date());
                tv_custom.setText(localData.getCustomer_name());
            }
            adapter = new PickUpShipmentSumAdapter(activity,sumShowBeanList);
            ryList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
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

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(R.string.again_commit);
    }

    @Override
    public String moduleCode() {
        Bundle bundle = getIntent().getExtras();
        module = bundle.getString(MODULUECODE, ModuleCode.OTHER);
        return module;
    }
}
