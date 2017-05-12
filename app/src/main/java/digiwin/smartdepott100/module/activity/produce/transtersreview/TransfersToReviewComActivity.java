package digiwin.smartdepott100.module.activity.produce.transtersreview;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.produce.TransfersReviewComAdapter;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @author xiemeng
 * @des 生产调拨复合提交
 * @date 2017/3/4
 */
public class TransfersToReviewComActivity extends BaseTitleActivity implements View.OnLayoutChangeListener {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_receipt_no)
    TextView tvReceiptNo;
    @BindView(R.id.tv_receipt_date)
    TextView tvReceiptDate;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_class_group)
    TextView tvClassGroup;
    @BindView(R.id.tv_out_warehouse)
    TextView tvOutWarehouse;
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.root)
    View root;
    @BindView(R.id.commit)
    View commit;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @OnClick(R.id.commit)
    void commit() {
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                commitData();
            }

            @Override
            public void onCallback2() {

            }
        });
    }

    private void commitData() {
        List<Map<String, String>> listMap = ObjectAndMapUtils.getListMap(comList);
        showLoadingDialog();
        logic.commitList(listMap, new CommonLogic.CommitListListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        createNewModuleId(module);
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


    public static String DETAIL = "detail";

    List<ListSumBean> comList;

    ListSumBean headBean;

    TransfersReviewComAdapter adapter;

    CommonLogic logic;

    @Override
    public String moduleCode() {
        module = ModuleCode.TRANSFERS_TO_REVIEW;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_transfers_reviewcommit;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.transfers_to_review);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void doBusiness() {
        initData();
        logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
    }

    /**
     * 初始化
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        comList = (List<ListSumBean>) bundle.getSerializable(DETAIL);
        // FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransfersReviewComAdapter(activity, comList);
        ryList.setAdapter(adapter);
        if (comList.size()>0) {
            headBean=comList.get(0);
            tvReceiptNo.setText(headBean.getReceipt_no());
            tvReceiptDate.setText(headBean.getReceipt_date());
            tvClassGroup.setText(headBean.getWorkgroup_name());
            tvDepartment.setText(headBean.getDepartment_name());
            tvPerson.setText(headBean.getEmployee_name());
            tvOutWarehouse.setText(headBean.getWarehouse_out_no());
        }
        tvReceiptNo.setFocusable(true);
        tvReceiptNo.setFocusableInTouchMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        root.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            commit.setVisibility(View.INVISIBLE);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            commit.setVisibility(View.VISIBLE);
        }
    }
}
