package digiwin.smartdepott100.module.activity.produce.accordingmaterial;

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
import android.widget.LinearLayout;
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
import digiwin.smartdepott100.core.coreutil.CommonUtils;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.adapter.produce.AccordingMaterialSumAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.AccordingMaterialLogic;

/**
 * @des  依成品发料
 * @date 2017/6/21
 * @author xiemeng
 */

public class AccordingMaterialActivity extends BaseFirstModuldeActivity {

    AccordingMaterialActivity activity;

    /**
     * 料号
     */
    final int BARCODEWHAT = 1001;

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 料号条码
     */
    @BindView(R.id.et_item_no_scan)
    EditText etItemNoScan;

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView mTvItemName;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    AccordingMaterialSumAdapter adapter;

    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;

    /**
     * 跳转扫描页面使用
     */
    public final int SCANCODE = 0123;

    AccordingMaterialLogic commonLogic;

    @BindViews({R.id.et_tray,R.id.et_item_no_scan})
    List<EditText> editTexts;
    @BindViews({R.id.tv_tray,R.id.tv_item_no})
    List<TextView> textViews;
    @BindViews({R.id.ll_tray,R.id.ll_item_no})
    List<View> views;

    @BindView(R.id.tv_tray)
    TextView tvTray;
    @BindView(R.id.et_tray)
    EditText etTray;
    @BindView(R.id.ll_tray)
    LinearLayout llTray;
    @BindView(R.id.line_tray)
    View lineTray;

    /**
     * 页面展示的数据
     */
    private List<ListSumBean>  showList;

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
                if(StringUtils.isBlank(etItemNoScan.getText().toString().trim())){
                    showFailedDialog(getResources().getString(R.string.please_scan_item_no));
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

    @OnFocusChange(R.id.et_item_no_scan)
    void barcodeFocusChange() {
        ModuleUtils.etChange(activity, etItemNoScan, editTexts);
    }

    @OnTextChanged(value = R.id.et_item_no_scan, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(final  CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            Map<String, String> map = new HashMap<>();
            map.put(AddressContants.FLAG, ExitMode.EXITD.getName());
            commonLogic.exit(map, new CommonLogic.ExitListener() {
                @Override
                public void onSuccess(String msg) {
                    mHandler.removeMessages(BARCODEWHAT);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
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
                case BARCODEWHAT:
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
        mName.setText(getResources().getString(R.string.according_material));
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.ACCORDINGMATERIALCODE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_according_material;
    }

    @Override
    protected void doBusiness() {
        commonLogic = AccordingMaterialLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(fullyLinearLayoutManager);
        if (CommonUtils.isUseTray()){
            llTray.setVisibility(View.VISIBLE);
            lineTray.setVisibility(View.VISIBLE);
        }else {
            llTray.setVisibility(View.GONE);
            lineTray.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCANCODE){
            if(!StringUtils.isBlank(etItemNoScan.getText().toString().trim())){
                List<ListSumBean> list = new ArrayList<ListSumBean>();
                adapter = new AccordingMaterialSumAdapter(activity,list);
                ryList.setAdapter(adapter);
                showLoadingDialog();
                updateList(etItemNoScan.getText().toString().trim());
            }
        }
    }

    /**
     * 清楚栏位
     */
    public  void clearData(){
        mTvItemName.setText("");
        etItemNoScan.setText("");
        etItemNoScan.requestFocus();
        List<ListSumBean> list = new ArrayList<ListSumBean>();
        adapter = new AccordingMaterialSumAdapter(activity,list);
        ryList.setAdapter(adapter);
    }

    /**
     * 根据料号跟新汇总数据
     * @param item_no
     */
    void updateList(String item_no){
        Map<String, String> map = new HashMap<>();
        map.put(AddressContants.ITEM_NO,item_no);
        map.put(AddressContants.WAREHOUSE_NO,LoginLogic.getWare());
        etItemNoScan.setKeyListener(null);
        commonLogic.getSum(map, new CommonLogic.GetZSumListener() {
            @Override
            public void onSuccess(final List<ListSumBean> list) {
                etItemNoScan.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                mTvItemName.setText(list.get(0).getItem_name());
                showList=list;
                adapter = new AccordingMaterialSumAdapter(activity,list);
                ryList.setAdapter(adapter);
                dismissLoadingDialog();
                adapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View itemView, int position) {
                        Bundle bundle = new Bundle();
                        ListSumBean data = list.get(position);
                        bundle.putSerializable("sumdata", data);
                        bundle.putString(AddressContants.MODULEID_INTENT,mTimestamp.toString());
                        ActivityManagerUtils.startActivityBundleForResult(activity,AccordingMaterialScanActivity.class,bundle,SCANCODE);
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                etItemNoScan.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
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
                showToast(error);
            }
        });
    }

    public void commitData(){
        try {
            for (int i=0;i<showList.size();i++){
                ListSumBean tempBean = showList.get(i);
                float sub = StringUtils.sub(tempBean.getStock_qty(), tempBean.getScan_sumqty());
                if (sub<0){
                    showFailedDialog(tempBean.getLow_order_item_no()+getResources().getString(R.string.scan_big_storenum));
                    return;
                }
            }
        }catch (Exception e){

        }
        showLoadingDialog();
        HashMap<String, String> barcodeMap = new HashMap<String, String>();
        commonLogic.commit(barcodeMap, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg);
                createNewModuleId(module);
                clearData();
                commonLogic = AccordingMaterialLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
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