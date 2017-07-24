package digiwin.smartdepott100.module.activity.produce.workorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
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
import digiwin.library.utils.WeakRefHandler;
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
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.WorkOrderlLogic;


/**
 * @author songjie
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
    EditText etJobNumberScan;

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView mTvItemName;

    @BindView(R.id.ry_list)
    RecyclerView mRcList;

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

    WorkOrderlLogic commonLogic;

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
                if(StringUtils.isBlank(etJobNumberScan.getText().toString().trim())){
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
        ModuleUtils.etChange(activity, etJobNumberScan, editTexts);
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

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WORKORDERWHAT:
                    updateList(String.valueOf(msg.obj));
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getResources().getString(R.string.title_work_order_sum));
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
        commonLogic = WorkOrderlLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        mRcList.setLayoutManager(fullyLinearLayoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCANCODE){
            if(!StringUtils.isBlank(etJobNumberScan.getText().toString().trim())){
                List<ListSumBean> list = new ArrayList<ListSumBean>();
                adapter = new WorkOrderSumAdapter(activity,list);
                mRcList.setAdapter(adapter);
                showLoadingDialog();
                updateList(etJobNumberScan.getText().toString().trim());
            }
        }
    }

    /**
     * 清楚栏位
     */
    public  void clearData(){
        mTvItemName.setText("");
        etJobNumberScan.setText("");
        etJobNumberScan.requestFocus();
        List<ListSumBean> list = new ArrayList<ListSumBean>();
        adapter = new WorkOrderSumAdapter(activity,list);
        mRcList.setAdapter(adapter);
    }

    /**
     * 根据工单号跟新汇总数据
     * @param wo_no
     */
    void updateList(String wo_no){
        Map<String,String> map=new HashMap<>();
        map.put(AddressContants.WO_NO,wo_no);
        map.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
        etJobNumberScan.setKeyListener(null);
        commonLogic.scanCode(map, new WorkOrderlLogic.ScanCodeListener() {
            @Override
            public void onSuccess(final List<ListSumBean> list) {
                etJobNumberScan.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                if (list.size() > 0) {
                    mTvItemName.setText(list.get(0).getItem_name());
                }
                adapter = new WorkOrderSumAdapter(activity,list);
                mRcList.setAdapter(adapter);
                dismissLoadingDialog();
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Bundle bundle = new Bundle();
                        ListSumBean data = list.get(position);
                        bundle.putSerializable(SUMDATA, data);
                        bundle.putString(AddressContants.WORKNO, etJobNumberScan.getText().toString().trim());
                        bundle.putString(AddressContants.MODULEID_INTENT,mTimestamp.toString());
                        ActivityManagerUtils.startActivityBundleForResult(activity,WorkOrderScanActivity.class,bundle,SCANCODE);
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                etJobNumberScan.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
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
                commonLogic = WorkOrderlLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}