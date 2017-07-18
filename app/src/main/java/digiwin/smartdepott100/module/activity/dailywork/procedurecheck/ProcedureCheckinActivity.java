package digiwin.smartdepott100.module.activity.dailywork.procedurecheck;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCheckinCommitBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCirculationBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureDevMouKniBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureEmployeeBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureNoCheckBackBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureOrderBean;
import digiwin.smartdepott100.module.logic.dailywok.ProcedureCheckLogic;

/**
 * @author xiemeng
 * @des  生产报工
 * @date 2017/5/18 15:27
 */

public class ProcedureCheckinActivity extends BaseTitleActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_procedure_no)
    TextView tvProcedureNo;
    @BindView(R.id.et_procedure_no)
    EditText etProcedureNo;
    @BindView(R.id.ll_procedure_no)
    LinearLayout llProcedureNo;
    @BindView(R.id.tv_circulation_number)
    TextView tvCirculationNumber;
    @BindView(R.id.et_circulation_number)
    EditText etCirculationNumber;
    @BindView(R.id.cb_circulation_number)
    CheckBox cbCirculationNumber;
    @BindView(R.id.ll_circulation_number)
    LinearLayout llCirculationNumber;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.et_order_number)
    EditText etOrderNumber;
    @BindView(R.id.cb_order_number)
    CheckBox cbOrderNumber;
    @BindView(R.id.ll_order_number)
    LinearLayout llOrderNumber;
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.rl_zx_detail)
    RelativeLayout rlZxDetail;

    @BindViews({R.id.et_procedure_no, R.id.et_circulation_number, R.id.et_order_number})
    List<EditText> editTexts;
    @BindViews({R.id.ll_procedure_no, R.id.ll_circulation_number, R.id.ll_order_number })
    List<View> views;
    @BindViews({R.id.tv_procedure_no, R.id.tv_circulation_number, R.id.tv_order_number })
    List<TextView> textViews;

    @OnFocusChange(R.id.et_procedure_no)
    void procedureNoFocusChange(){
        ModuleUtils.viewChange(llProcedureNo, views);
        ModuleUtils.etChange(activity, etProcedureNo, editTexts);
        ModuleUtils.tvChange(activity, tvProcedureNo, textViews);
    }
    @OnFocusChange(R.id.et_circulation_number)
    void etCirculationNumberFocusChange(){
        ModuleUtils.viewChange(llCirculationNumber, views);
        ModuleUtils.etChange(activity, etCirculationNumber, editTexts);
        ModuleUtils.tvChange(activity, tvCirculationNumber, textViews);
    }
    @OnFocusChange(R.id.et_order_number)
    void orderNumberFocusChange(){
        ModuleUtils.viewChange(llOrderNumber, views);
        ModuleUtils.etChange(activity, etOrderNumber, editTexts);
        ModuleUtils.tvChange(activity, tvOrderNumber, textViews);
    }

    @OnCheckedChanged(R.id.cb_circulation_number)
    void circulationIsLock(boolean checked) {
        if (checked) {
            etCirculationNumber.setKeyListener(null);
        } else {
            etCirculationNumber.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnCheckedChanged(R.id.cb_order_number)
    void orderNumberIsLock(boolean checked) {
        if (checked) {
            etOrderNumber.setKeyListener(null);
        } else {
            etOrderNumber.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }
    @OnClick(R.id.iv_title_setting)
    void toProcedureSet(){
//        if (!procedureFlag){
//            showFailedDialog(getString(R.string.scan_procedure));
//            return;
//        }
//        if (!circulationFlag&&!orderFlag){
//            showFailedDialog(R.string.scan_circulationororder);
//            return;
//        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ProcedureCheckinSetActivity.GETHEAD,commitBean);
        bundle.putString(AddressContants.MODULEID_INTENT,mTimestamp.toString());
        ActivityManagerUtils.startActivityForBundleData(this,ProcedureCheckinSetActivity.class,bundle);
    }

    @OnTextChanged(value = R.id.et_procedure_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void procedurenoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PROCEDUREWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PROCEDUREWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_circulation_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void circulationChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(CIRCULATIONWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(CIRCULATIONWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_order_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void orderChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(ORDERWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(ORDERWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.commit)
    void commit(){
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
     * 工序号
     */
    final int PROCEDUREWHAT = 1002;

    /**
     * 生产批次条码
     */
    final int CIRCULATIONWHAT = 1001;

    /**
     * 工单号
     */
    final  int ORDERWHAT=1003;
    /**
     * 工序号扫描
     */
    boolean procedureFlag;
    /**
     * 生产批次扫描
     */
    boolean circulationFlag;
    /**
     * 工单号
     */
    boolean orderFlag;
    /**
     * 工序号
     */
    String procedureShow;

    /**
     * 生产批次条码
     */
    String circulationShow;
    /**
     * 工单号
     */
    String orderShow;

    ProcedureCheckLogic procedureCheckLogic;


    ProcedureCheckinCommitBean commitBean;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PROCEDUREWHAT:
                    HashMap<String, String> procedureMap = new HashMap<>();
                    procedureMap.put(AddressContants.PROCEDURE, String.valueOf(msg.obj));
                    procedureCheckLogic.scanProcedure(procedureMap, new ProcedureCheckLogic.ScanProcedureListener() {
                        @Override
                        public void onSuccess(ProcedureNoCheckBackBean procedureBackBean) {
                            procedureShow = procedureBackBean.getShowing();
                            procedureFlag= true;
                            commitBean.setSubop_no(procedureBackBean.getSubop_no());
                            commitBean.setOp_seq(procedureBackBean.getOp_seq());
                            commitBean.setSubop_name(procedureBackBean.getSubop_name());
                            etCirculationNumber.requestFocus();
//                            if (AddressContants.N.equals(procedureBackBean.getSubop_status()))
//                            {
//                                cbCirculationNumber.setChecked(false);
//                                etCirculationNumber.requestFocus();
//                            }else {
//                                cbOrderNumber.setChecked(false);
//                                etOrderNumber.requestFocus();
//                            }
                            show();
                        }
                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etProcedureNo.setText("");
                                }
                            });
                            procedureFlag=false;
                        }
                    });
                    break;
                case CIRCULATIONWHAT:
                    HashMap<String, String> circulationMap = new HashMap<>();
                    circulationMap.put(AddressContants.PLOTNO, String.valueOf(msg.obj));
                    procedureCheckLogic.scanCirculation(circulationMap, new ProcedureCheckLogic.ScanCirculationListener() {
                        @Override
                        public void onSuccess(ProcedureCirculationBean circulationBackBean) {
                            circulationShow = circulationBackBean.getShowing();
                            circulationFlag= true;
                            commitBean.setItem_no(circulationBackBean.getItem_no());
                            commitBean.setWo_no(circulationBackBean.getWo_no());
                            commitBean.setPlot_no(etCirculationNumber.getText().toString());
                            etOrderNumber.requestFocus();
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etCirculationNumber.setText("");
                                }
                            });
                            circulationFlag=false;
                        }
                    });
                    break;
                case ORDERWHAT:
                    HashMap<String, String> orderMap = new HashMap<>();
                    orderMap.put(AddressContants.SUBOP_NO, commitBean.getSubop_no());
                    orderMap.put(AddressContants.CHECK_STATUS,AddressContants.CHECKIN);
                    orderMap.put(AddressContants.WO_NO, String.valueOf(msg.obj));
                    procedureCheckLogic.scanOrder(orderMap, new ProcedureCheckLogic.ScanOrderListener() {
                        @Override
                        public void onSuccess(ProcedureOrderBean orderBean) {
                            orderShow = orderBean.getShowing();
                            commitBean.setWo_no(etOrderNumber.getText().toString());
                            commitBean.setItem_no(orderBean.getItem_no());
                            orderFlag= true;
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etOrderNumber.setText("");
                                }
                            });
                            circulationFlag=false;
                        }
                    });
                    break;

            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);



    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module= ModuleCode.PROCEDUCECHECK;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.procedure_set);
        mName.setText(R.string.procedure_checkin);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_procedure_checkin;
    }

    @Override
    protected void doBusiness() {
        initData();
    }

    /**
     * 提交
     */
    private void sureCommit() {
        if (!procedureFlag){
            showFailedDialog(getString(R.string.scan_procedure));
            return;
        }
        if (!circulationFlag&&!orderFlag){
            showFailedDialog(R.string.scan_circulationororder);
            return;
        }
        Connector.getDatabase();
        List<ProcedureEmployeeBean> employeeBeen = DataSupport.findAll(ProcedureEmployeeBean.class);
        List<ProcedureDevMouKniBean> devMouKniBeen = DataSupport.findAll(ProcedureDevMouKniBean.class);
        if (employeeBeen.size()==0){
            showFailedDialog(getString(R.string.please_scan_workers));
            return;
        }
        Connector.getDatabase();
        List<ProcedureDevMouKniBean> deviceBeeen = DataSupport.where("resource_type=?", "1").find(ProcedureDevMouKniBean.class);
        if (deviceBeeen.size()==0){
            showFailedDialog(getString(R.string.please_scan_device));
            return;
        }
        commitBean.setEmployee_list(employeeBeen);
        commitBean.setResource_list(devMouKniBeen);
        showLoadingDialog();
        procedureCheckLogic.commitCheckin(commitBean, new ProcedureCheckLogic.CommitListListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                createNewModuleId(module);
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        initData();
                    }
                });

            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }

        });
    }




    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(procedureShow + "\\n" + circulationShow+ "\\n" +orderShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())){
            rlZxDetail.setVisibility(View.VISIBLE);}else {
            rlZxDetail.setVisibility(View.GONE);
        }
    }

    private  void initData(){
        procedureFlag=false;
        circulationFlag=false;
        orderFlag=false;
        procedureShow="";
        circulationShow="";
        orderShow="";
        show();
//        cbCirculationNumber.setChecked(true);
//        cbCirculationNumber.setEnabled(false);
//        cbOrderNumber.setChecked(true);
//        cbOrderNumber.setEnabled(false);
        etProcedureNo.setText("");
        etOrderNumber.setText("");
        etCirculationNumber.setText("");
        etProcedureNo.requestFocus();
        commitBean=new ProcedureCheckinCommitBean();
        procedureCheckLogic = ProcedureCheckLogic.getInstance(activity,module,mTimestamp.toString());
        Connector.getDatabase();
        DataSupport.deleteAll(ProcedureEmployeeBean.class);
        DataSupport.deleteAll(ProcedureDevMouKniBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
