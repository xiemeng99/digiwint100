package digiwin.smartdepott100.module.activity.dailywork.proceduremove;

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
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.bean.AccoutBean;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCirculationBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureEmployeeBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureMoveCommitBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureNoMoveBean;
import digiwin.smartdepott100.module.logic.dailywok.ProcedureMoveLogic;

/**
 * @author xiemeng
 * @des 工序转移movein
 * @date 2017/5/16 09:48
 */

public class ProcedureMoveinActivity extends BaseTitleActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_docking_per)
    TextView tvDockingPer;
    @BindView(R.id.et_docking_per)
    EditText etDockingPer;
    @BindView(R.id.cb_docking_per)
    CheckBox cbDockingPer;
    @BindView(R.id.ll_docking_per)
    LinearLayout llDockingPer;
    @BindView(R.id.tv_circulation_number)
    TextView tvCirculationNumber;
    @BindView(R.id.et_circulation_number)
    EditText etCirculationNumber;
    @BindView(R.id.ll_circulation_number)
    LinearLayout llCirculationNumber;
    @BindView(R.id.tv_procedure_no)
    TextView tvProcedureNo;
    @BindView(R.id.et_procedure_no)
    EditText etProcedureNo;
    @BindView(R.id.cb_procedure_no)
    CheckBox cbProcedureNo;
    @BindView(R.id.ll_procedure_no)
    LinearLayout llProcedureNo;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;
    @BindView(R.id.tv_accept_per)
    TextView tvAcceptPer;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.rl_zx_detail)
    RelativeLayout rlZxDetail;

    @BindViews({R.id.et_docking_per, R.id.et_circulation_number, R.id.et_procedure_no, R.id.et_input_num })
    List<EditText> editTexts;
    @BindViews({R.id.ll_docking_per, R.id.ll_circulation_number, R.id.ll_procedure_no, R.id.ll_input_num })
    List<View> views;
    @BindViews({R.id.tv_docking_per, R.id.tv_circulation_number, R.id.tv_procedure_no, R.id.tv_number })
    List<TextView> textViews;

    @OnFocusChange(R.id.et_docking_per)
    void dockingPerFocusChange(){
        ModuleUtils.viewChange(llDockingPer, views);
        ModuleUtils.etChange(activity, etDockingPer, editTexts);
        ModuleUtils.tvChange(activity, tvDockingPer, textViews);
    }
    @OnFocusChange(R.id.et_circulation_number)
    void etCirculationNumberFocusChange(){
        ModuleUtils.viewChange(llCirculationNumber, views);
        ModuleUtils.etChange(activity, etCirculationNumber, editTexts);
        ModuleUtils.tvChange(activity, tvCirculationNumber, textViews);
    }
    @OnFocusChange(R.id.et_procedure_no)
    void procedureNoFocusChange(){
        ModuleUtils.viewChange(llProcedureNo, views);
        ModuleUtils.etChange(activity, etProcedureNo, editTexts);
        ModuleUtils.tvChange(activity, tvProcedureNo, textViews);
    }
    @OnFocusChange(R.id.et_input_num)
    void inputNumberFocusChange(){
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnCheckedChanged(R.id.cb_docking_per)
    void dockingIsLock(boolean checked) {
        if (checked) {
            etDockingPer.setKeyListener(null);
        } else {
            etDockingPer.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnCheckedChanged(R.id.cb_procedure_no)
    void procedureIsLock(boolean checked) {
        if (checked) {
            etProcedureNo.setKeyListener(null);
        } else {
            etProcedureNo.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnTextChanged(value = R.id.et_docking_per, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void dockingperChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(DOCKINGPERWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(DOCKINGPERWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_circulation_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void circulationnumberChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(CIRCULATIONWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(CIRCULATIONWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_procedure_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void procedurenoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PROCEDUREWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PROCEDUREWHAT, s.toString()), AddressContants.DELAYTIME);
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
     * 对接人员
     */
    final int DOCKINGPERWHAT=1000;

    /**
     * 生产批次条码
     */
    final int CIRCULATIONWHAT = 1001;
    /**
     * 工序号
     */
    final int PROCEDUREWHAT = 1002;



    ProcedureMoveLogic procedureMoveLogic;
    /**
     * 对接人员
     */
    String dockingperShow;
    /**
     * 流转批次扫描
     */
    String circulationShow;
    /**
     * 工序号展示
     */
    String procedureShow;

    /**
     * 对接人员
     */
    boolean dockingperFlag;
    /**
     * 流转批次扫描
     */
    boolean circulationFlag;
    /**
     * 工序号扫描
     */
    boolean procedureFlag;


    ProcedureMoveCommitBean commitBean;


    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DOCKINGPERWHAT:
                    HashMap<String, String> dockingperMap = new HashMap<>();
                    dockingperMap.put(AddressContants.EMPLOYEENO, String.valueOf(msg.obj));
                    procedureMoveLogic.scanPerson(dockingperMap, new ProcedureMoveLogic.ScanPersonListener() {
                        @Override
                        public void onSuccess(ProcedureEmployeeBean workerPerson) {
                            dockingperFlag = true;
                            dockingperShow = workerPerson.getShowing();
                            commitBean.setEmployee_no(etDockingPer.getText().toString());
                            show();
                            etCirculationNumber.requestFocus();
                        }
                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etDockingPer.setText("");
                                }
                            });
                            dockingperFlag=false;
                        }
                    });
                    break;
                case CIRCULATIONWHAT:
                    HashMap<String, String> circulationMap = new HashMap<>();
                    circulationMap.put(AddressContants.PLOTNO, String.valueOf(msg.obj));
                    circulationMap.put(AddressContants.CHECK_STATUS, AddressContants.MOVEIN);
                    procedureMoveLogic.scanCirculation(circulationMap, new ProcedureMoveLogic.ScanCirculationListener() {
                        @Override
                        public void onSuccess(ProcedureCirculationBean circulationBackBean) {
                            circulationShow = circulationBackBean.getShowing();
                            commitBean.setWo_no(circulationBackBean.getWo_no());
                            circulationFlag= true;
                            show();
                            etProcedureNo.requestFocus();
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
                case PROCEDUREWHAT:
                    HashMap<String, String> procedureMap = new HashMap<>();
                    procedureMap.put(AddressContants.WO_NO, commitBean.getWo_no());
                    procedureMap.put(AddressContants.PROCEDURE, String.valueOf(msg.obj));
                    procedureMap.put(AddressContants.MOVE_STATUS, AddressContants.MOVEIN);
                    procedureMoveLogic.scanProcedure(procedureMap, new ProcedureMoveLogic.ScanProcedureListener() {
                        @Override
                        public void onSuccess(ProcedureNoMoveBean procedureMoveBean) {
                            procedureShow = procedureMoveBean.getShowing();
                            commitBean.setSubop_no(procedureMoveBean.getSubop_no());
                            commitBean.setOp_seq(procedureMoveBean.getOp_seq());
                            commitBean.setBefore_set_qty(procedureMoveBean.getBefore_set_qty());
                            etInputNum.setText(StringUtils.deleteZero(procedureMoveBean.getBefore_set_qty()));
                            procedureFlag= true;
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
        module = ModuleCode.PROCEDUCEMOVE;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_procedure_movein;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.procedure_movein);
    }

    @Override
    protected void doBusiness() {
        initData();
    }



    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(dockingperShow + "\\n" + circulationShow+ "\\n" +procedureShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())){
            rlZxDetail.setVisibility(View.VISIBLE);}else {
            rlZxDetail.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化一些变量
     */
    public  void initData() {
        dockingperShow="";
        circulationShow = "";
        procedureShow = "";
        circulationFlag = false;
        dockingperFlag=false;
        procedureFlag = false;
        cbDockingPer.setChecked(false);
        cbProcedureNo.setChecked(false);
        etDockingPer.setText("");
        etCirculationNumber.setText("");
        etProcedureNo.setText("");
        etInputNum.setText("");
        etDockingPer.requestFocus();
        commitBean=new ProcedureMoveCommitBean();
        procedureMoveLogic = procedureMoveLogic.getInstance(context, module, mTimestamp.toString());
        AccoutBean userInfo = LoginLogic.getUserInfo();
        if (null!=userInfo){
            tvAcceptPer.setText(userInfo.getEmployee_no());
            tvName.setText(userInfo.getEmployee_name());
        }
    }

    /**
     * 提交
     */
    private void sureCommit() {
//        if (!dockingperFlag){
//            showFailedDialog(R.string.scan_dockingper);
//            return;
//        }
        if (!circulationFlag){
            showFailedDialog(R.string.scan_circulation);
            return;
        }
        if (!procedureFlag){
            showFailedDialog(getString(R.string.scan_procedure));
            return;
        }
        if (StringUtils.isBlank(etInputNum.getText().toString().trim())){
            showFailedDialog(getString(R.string.input_num));
            return;
        }
        commitBean.setUndefect_qty(etInputNum.getText().toString().trim());
        showLoadingDialog();
        try {
            float sub = StringUtils.sub(commitBean.getUndefect_qty(), commitBean.getBefore_set_qty());
            if (sub > 0) {
                showFailedDialog(getString(R.string.input_num_toobig));
                return;
            }
            procedureMoveLogic.commitMovein(commitBean, new ProcedureMoveLogic.CommitListListener() {
                @Override
                public void onSuccess(String msg) {
                    createNewModuleId(module);
                    dismissLoadingDialog();
                    showCommitSuccessDialog(msg, new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                            afterCommit();
                        }
                    });
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    showFailedDialog(error);
                }
            });
        }catch (Exception e){
            showFailedDialog(R.string.unknow_error);
        }
    }


    private void afterCommit(){
        if (!cbProcedureNo.isChecked()){
            procedureShow = "";
            procedureFlag = false;
            cbProcedureNo.setChecked(false);
            etProcedureNo.setText("");
            etProcedureNo.requestFocus();
        }
        circulationShow = "";
        circulationFlag = false;
        etCirculationNumber.setText("");
        etCirculationNumber.requestFocus();
        if (!cbDockingPer.isChecked()){
            dockingperShow="";
            dockingperFlag=false;
            etDockingPer.setText("");
            cbDockingPer.setChecked(false);
            etDockingPer.requestFocus();
        }
        etInputNum.setText("");
        procedureMoveLogic = procedureMoveLogic.getInstance(context, module, mTimestamp.toString());
        AccoutBean userInfo = LoginLogic.getUserInfo();
        if (null!=userInfo){
            tvAcceptPer.setText(userInfo.getEmployee_no());
            tvName.setText(userInfo.getEmployee_name());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
