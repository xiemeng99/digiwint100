package digiwin.smartdepott100.module.activity.produce.workorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.adapter.produce.WorkOrderSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;


/**
 * @author 赵浩然
 * @module 依工单发料
 * @date 2017/3/10
 */

public class WorkOrderActivity extends BaseFirstModuldeActivity {

    WorkOrderActivity activity;

    /**
     * 工单号扫描
     */
    final int WORKORDERWHAT = 1001;

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 工单号
     */
    @BindView(R.id.et_job_number_scan)
    EditText et_job_number_scan;

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView mTv_item_name;

    @BindView(R.id.ry_list)
    RecyclerView mRc_list;

    WorkOrderSumAdapter adapter;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    /**
     * 跳转扫描页面使用
     */
    public final int SCANCODE = 0123;

    private final String SUMDATA = "sumdata";

    CommonLogic commonLogic;

    @BindViews({R.id.et_job_number_scan})
    List<EditText> editTexts;
/*    @BindViews({R.id.tv_item_no})
    List<TextView> textViews;*/

    /**
     * 提交按钮
     */
    @BindView(R.id.commit)
    Button mBtn_commit;

    @OnClick(R.id.commit)
    void commit(){
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                if(StringUtils.isBlank(et_job_number_scan.getText().toString().trim())){
                    dismissLoadingDialog();
                    showFailedDialog(getResources().getString(R.string.please_scan_job_number));
                    return;
                }
                commitData();
            }
            @Override
            public void onCallback2() {

            }
        });

    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @OnFocusChange(R.id.et_job_number_scan)
    void barcodeFocusChange() {
        ModuleUtils.etChange(activity, et_job_number_scan, editTexts);
    }

    @OnTextChanged(value = R.id.et_job_number_scan, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(final CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            Map<String, String> map = new HashMap<>();
            map.put(AddressContants.FLAG, ExitMode.EXITD.getName());
            commonLogic.exit(map, new CommonLogic.ExitListener() {
                @Override
                public void onSuccess(String msg) {
                    mHandler.removeMessages(WORKORDERWHAT);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(WORKORDERWHAT, s.toString().trim()), AddressContants.DELAYTIME);
                }

                @Override
                public void onFailed(String error) {
                    showFailedDialog(error);
                }
            });
        }
    }

    private android.os.Handler mHandler = new android.os.Handler(new android.os.Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case WORKORDERWHAT:
                updateList(String.valueOf(msg.obj));
                break;
        }
        return false;
    }
});

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getResources().getString(R.string.title_work_order));
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.WORKORDERCODE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_work_order;
    }

    @Override
    protected void doBusiness() {
        commonLogic = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        mRc_list.setLayoutManager(fullyLinearLayoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCANCODE){
            if(!StringUtils.isBlank(et_job_number_scan.getText().toString().trim())){
                List<ListSumBean> list = new ArrayList<ListSumBean>();
                adapter = new WorkOrderSumAdapter(activity,list);
                mRc_list.setAdapter(adapter);
                showLoadingDialog();
                updateList(et_job_number_scan.getText().toString().trim());
            }
        }
    }

    /**
     * 清楚栏位
     */
    public  void clearData(){
        mTv_item_name.setText("");
        et_job_number_scan.setText("");
        et_job_number_scan.requestFocus();
        List<ListSumBean> list = new ArrayList<ListSumBean>();
        adapter = new WorkOrderSumAdapter(activity,list);
        mRc_list.setAdapter(adapter);
    }

    /**
     * 根据工单号跟新汇总数据
     * @param item_no
     */
    void updateList(String item_no){
        ClickItemPutBean putBean = new ClickItemPutBean();
        putBean.setWo_no(item_no);
        putBean.setWarehouse_no(LoginLogic.getUserInfo().getWare());

        commonLogic.getOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
            @Override
            public void onSuccess(final List<ListSumBean> list) {
                if (list.size() > 0) {
                    mTv_item_name.setText(list.get(0).getItem_name());
                }
                adapter = new WorkOrderSumAdapter(activity,list);
                mRc_list.setAdapter(adapter);
                dismissLoadingDialog();

                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Bundle bundle = new Bundle();
                        ListSumBean data = list.get(position);
                        bundle.putSerializable(SUMDATA, data);
                        bundle.putString(AddressContants.WORKNO,et_job_number_scan.getText().toString().trim());
                        bundle.putString(AddressContants.MODULEID_INTENT,mTimestamp.toString());
                        ActivityManagerUtils.startActivityBundleForResult(activity,WorkOrderScanActivity.class,bundle,SCANCODE);
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        clearData();
                    }
                });
            }
        });
    }

    /**
     * 跳转到明细页面
     */
    public void ToDetailAct(final SumShowBean bean){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(AddressContants.ITEM_NO,bean.getItem_no());
        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(final List<DetailShowBean> detailShowBeen) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(CommonDetailActivity.ONESUM,bean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                bundle.putString(AddressContants.MODULEID_INTENT,mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, activity.module);
                ActivityManagerUtils.startActivityBundleForResult(activity,CommonDetailActivity.class,bundle,SCANCODE);
            }

            @Override
            public void onFailed(String error) {
                showFailedDialog(error);
            }
        });
    }

    public void commitData(){
        HashMap<String, String> barcodeMap = new HashMap<String, String>();
        commonLogic.commit(barcodeMap, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg);
                clearData();
                createNewModuleId(module);
                commonLogic = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
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