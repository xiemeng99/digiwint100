package digiwin.smartdepott100.module.activity.dailywork.procedurecheck;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.Serializable;
import java.util.ArrayList;
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
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCheckoutBadResBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCheckoutCommitBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCirculationBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureDevMouKniBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureEmployeeBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureNoCheckBackBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureOrderBean;
import digiwin.smartdepott100.module.logic.dailywok.ProcedureCheckLogic;

/**
 * @author xiemeng
 * @des 生产报工 checkout
 * @date 2017/5/21 10:25
 */
public class ProcedureCheckoutActivity extends BaseTitleActivity {
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
    @BindView(R.id.tv_workers)
    TextView tvWorkers;
    @BindView(R.id.et_workers)
    EditText etWorkers;
    @BindView(R.id.ll_workers)
    LinearLayout llWorkers;
    @BindView(R.id.tv_device_no)
    TextView tvDeviceNo;
    @BindView(R.id.et_device_no)
    EditText etDeviceNo;
    @BindView(R.id.ll_device_no)
    LinearLayout llDeviceNo;
    @BindView(R.id.tv_mould_no)
    TextView tvMouldNo;
    @BindView(R.id.et_mould_no)
    EditText etMouldNo;
    @BindView(R.id.ll_mould_no)
    LinearLayout llMouldNo;
    @BindView(R.id.tv_knift_no)
    TextView tvKniftNo;
    @BindView(R.id.et_knift_no)
    EditText etKniftNo;
    @BindView(R.id.ll_knift_no)
    LinearLayout llKniftNo;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    @BindView(R.id.tv_pending_number)
    TextView tvPendingNumber;
    @BindView(R.id.et_pending_number)
    EditText etPendingNumber;
    @BindView(R.id.img_detail)
    ImageView imgDetail;
    @BindView(R.id.ll_pending_number)
    LinearLayout llPendingNumber;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.rl_zx_detail)
    RelativeLayout rlZxDetail;
    @BindView(R.id.cb_mould_no)
    CheckBox cbMouldNo;
    @BindView(R.id.cb_knift_no)
    CheckBox cbKniftNo;

    @BindViews({R.id.et_procedure_no, R.id.et_circulation_number, R.id.et_order_number, R.id.et_workers,
            R.id.et_device_no, R.id.et_mould_no, R.id.et_knift_no, R.id.et_input_num, R.id.et_pending_number})
    List<EditText> editTexts;
    @BindViews({R.id.ll_procedure_no, R.id.ll_circulation_number, R.id.ll_order_number, R.id.ll_workers,
            R.id.ll_device_no, R.id.ll_mould_no, R.id.ll_knift_no, R.id.ll_input_num, R.id.ll_pending_number})
    List<View> views;
    @BindViews({R.id.tv_procedure_no, R.id.tv_circulation_number, R.id.tv_order_number, R.id.tv_workers,
            R.id.tv_device_no, R.id.tv_mould_no, R.id.tv_knift_no, R.id.tv_number, R.id.tv_pending_number})
    List<TextView> textViews;


    @OnFocusChange(R.id.et_procedure_no)
    void procedureNoFocusChange() {
        ModuleUtils.viewChange(llProcedureNo, views);
        ModuleUtils.etChange(activity, etProcedureNo, editTexts);
        ModuleUtils.tvChange(activity, tvProcedureNo, textViews);
    }

    @OnFocusChange(R.id.et_circulation_number)
    void etCirculationNumberFocusChange() {
        ModuleUtils.viewChange(llCirculationNumber, views);
        ModuleUtils.etChange(activity, etCirculationNumber, editTexts);
        ModuleUtils.tvChange(activity, tvCirculationNumber, textViews);
    }

    @OnFocusChange(R.id.et_order_number)
    void orderNumberFocusChange() {
        ModuleUtils.viewChange(llOrderNumber, views);
        ModuleUtils.etChange(activity, etOrderNumber, editTexts);
        ModuleUtils.tvChange(activity, tvOrderNumber, textViews);
    }

    @OnFocusChange(R.id.et_workers)
    void workersFocusChange() {
        ModuleUtils.viewChange(llWorkers, views);
        ModuleUtils.etChange(activity, etWorkers, editTexts);
        ModuleUtils.tvChange(activity, tvWorkers, textViews);
    }

    @OnFocusChange(R.id.et_device_no)
    void deviceNoFocusChange() {
        ModuleUtils.viewChange(llDeviceNo, views);
        ModuleUtils.etChange(activity, etDeviceNo, editTexts);
        ModuleUtils.tvChange(activity, tvDeviceNo, textViews);
    }

    @OnFocusChange(R.id.et_mould_no)
    void mouldNoFocusChange() {
        ModuleUtils.viewChange(llMouldNo, views);
        ModuleUtils.etChange(activity, etMouldNo, editTexts);
        ModuleUtils.tvChange(activity, tvMouldNo, textViews);
    }

    @OnFocusChange(R.id.et_knift_no)
    void kniftNoFocusChange() {
        ModuleUtils.viewChange(llKniftNo, views);
        ModuleUtils.etChange(activity, etKniftNo, editTexts);
        ModuleUtils.tvChange(activity, tvKniftNo, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void inputNumberFocusChange() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnFocusChange(R.id.et_pending_number)
    void pendingNumberChange() {
        ModuleUtils.viewChange(llPendingNumber, views);
        ModuleUtils.etChange(activity, etPendingNumber, editTexts);
        ModuleUtils.tvChange(activity, tvPendingNumber, textViews);
    }

    @OnCheckedChanged(R.id.cb_circulation_number)
    void circulationNumberIsLock(boolean checked) {
        if (checked) {
            etCirculationNumber.setKeyListener(null);
        } else {
            etCirculationNumber.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnCheckedChanged(R.id.cb_order_number)
    void procedureIsLock(boolean checked) {
        if (checked) {
            etOrderNumber.setKeyListener(null);
        } else {
            etOrderNumber.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnCheckedChanged(R.id.cb_mould_no)
    void mouldIsLock(boolean checked) {
        if (checked) {
            etMouldNo.setKeyListener(null);
        } else {
            etMouldNo.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnCheckedChanged(R.id.cb_knift_no)
    void knifeIsLock(boolean checked) {
        if (checked) {
            etKniftNo.setKeyListener(null);
        } else {
            etMouldNo.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
        }
    }

    @OnClick(R.id.img_detail)
    void toBadReson() {
        if (StringUtils.isBlank(etPendingNumber.getText().toString().trim())){
            showFailedDialog(R.string.input_num);
            return;
        }
        Bundle bundle = new Bundle();
        commitBean.setDefect_qty(etPendingNumber.getText().toString().trim());
        bundle.putSerializable(ProcedureCheckBadResActivity.GETHEAD,commitBean);
        bundle.putSerializable(ProcedureCheckBadResActivity.BADLIST,(Serializable)badlist);
        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
        ActivityManagerUtils.startActivityForBundleData(this, ProcedureCheckBadResActivity.class, bundle);

    }

    @OnTextChanged(value = R.id.et_procedure_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void procedureNoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PROCEDUREWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PROCEDUREWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_circulation_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void circulationNumberChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(CIRCULATIONWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(CIRCULATIONWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_order_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void orderNumberChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(ORDERWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(ORDERWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_workers, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void workersChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(WORKERSWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(WORKERSWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_device_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void deviceNoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(DEVICENOWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(DEVICENOWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_mould_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void mouldNoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(MOULDNOWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(MOULDNOWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_knift_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void kniftNoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(KNIFTWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(KNIFTWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

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
     * 生产批次条码
     */
    final int CIRCULATIONWHAT = 1001;
    /**
     * 工序号
     */
    final int PROCEDUREWHAT = 1002;
    /**
     * 工单号
     */
    final int ORDERWHAT = 1003;
    /**
     * 报工人员
     */
    final int WORKERSWHAT = 1004;
    /**
     * 设备号
     */
    final int DEVICENOWHAT = 1005;
    /**
     * 模具号
     */
    final int MOULDNOWHAT = 1006;
    /**
     * 刀具号
     */
    final int KNIFTWHAT = 1007;

    /**
     * 工序号展示
     */
    String procedureShow;
    /**
     * 生产批次扫描
     */
    String circulationShow;
    /**
     * 工单号
     */
    String orderShow;
    /**
     * 报工人员
     */
    String workersShow;
    /**
     * 设备
     */
    String deviceShow;
    /**
     * 模具
     */
    String mouldShow;
    /**
     * 刀具
     */
    String kniftShow;


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
     * 报工人员
     */
    boolean workersFlag;
    /**
     * 设备
     */
    boolean deviceFlag;
    /**
     * 模具
     */
    boolean mouldFlag;
    /**
     * 刀具
     */
    boolean kniftFlag;


    ProcedureCheckLogic procedureCheckLogic;

    private List<ProcedureCheckoutBadResBean> badlist;

    private ProcedureCheckoutCommitBean commitBean;


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
                            procedureFlag = true;
                            commitBean.setSubop_no(procedureBackBean.getSubop_no());
                            commitBean.setOp_seq(procedureBackBean.getOp_seq());
                            etCirculationNumber.requestFocus();
//                            if (AddressContants.N.equals(procedureBackBean.getSubop_status())) {
//                                cbCirculationNumber.setChecked(false);
//                                etCirculationNumber.requestFocus();
//                            } else {
//                                cbOrderNumber.setChecked(false);
//                                etOrderNumber.requestFocus();
//                            }
                            if (AddressContants.Y.equals(procedureBackBean.getSubop_mould_status())) {
                                cbMouldNo.setChecked(false);
                            }
                            if (AddressContants.Y.equals(procedureBackBean.getSubop_knife_status())) {
                                cbKniftNo.setChecked(false);
                            }
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
                            procedureFlag = false;
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
                            circulationFlag = true;
                            commitBean.setWo_no(circulationBackBean.getWo_no());
                            commitBean.setPlot_no(etCirculationNumber.getText().toString());
//                            etWorkers.requestFocus();
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
                            circulationFlag = false;
                        }
                    });
                    break;
                case ORDERWHAT:
                    HashMap<String, String> orderMap = new HashMap<>();
                    orderMap.put(AddressContants.SUBOP_NO, commitBean.getSubop_no());
                    orderMap.put(AddressContants.CHECK_STATUS, AddressContants.CHECKOUT);
                    orderMap.put(AddressContants.WO_NO, String.valueOf(msg.obj));
                    procedureCheckLogic.scanOrder(orderMap, new ProcedureCheckLogic.ScanOrderListener() {
                        @Override
                        public void onSuccess(ProcedureOrderBean orderBean) {
                            orderShow = orderBean.getShowing();
                            orderFlag = true;
                            etWorkers.requestFocus();
                            commitBean.setWo_no(etOrderNumber.getText().toString());
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
                            circulationFlag = false;
                        }
                    });
                    break;
                case WORKERSWHAT:
                    HashMap<String, String> workersMap = new HashMap<>();
                    workersMap.put(AddressContants.EMPLOYEENO, etWorkers.getText().toString());
                    workersMap.put(AddressContants.CHECK_STATUS, AddressContants.CHECKOUT);
                    procedureCheckLogic.scanPerson(workersMap, new ProcedureCheckLogic.ScanPersonListener() {
                        @Override
                        public void onSuccess(ProcedureEmployeeBean employeeBean) {
                            workersShow = employeeBean.getShowing();
                            commitBean.setEmployee_no(etWorkers.getText().toString());
                            workersFlag = true;
                            etDeviceNo.requestFocus();
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etWorkers.setText("");
                                }
                            });
                        }
                    });
                    break;
                case DEVICENOWHAT:
                    final HashMap<String, String> deviceMap = new HashMap<>();
                    deviceMap.put(AddressContants.RESOURCE_NO, etDeviceNo.getText().toString());
                    deviceMap.put(AddressContants.RESOURCE_TYPE, "1");
                    deviceMap.put(AddressContants.SUBOP_NO, commitBean.getSubop_no());
                    deviceMap.put(AddressContants.WO_NO, commitBean.getWo_no());
                    deviceMap.put(AddressContants.CHECK_STATUS, AddressContants.CHECKOUT);
                    procedureCheckLogic.scanResource(deviceMap, new ProcedureCheckLogic.ScanResourceListener() {
                        @Override
                        public void onSuccess(ProcedureDevMouKniBean devMouKniBean) {
                            deviceShow = devMouKniBean.getShowing();
                            deviceFlag = true;
                            commitBean.setMachine_no(etDeviceNo.getText().toString());
                            etInputNum.requestFocus();
                            if (!cbKniftNo.isChecked()) etKniftNo.requestFocus();
                            if (!cbMouldNo.isChecked()) etMouldNo.requestFocus();
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etDeviceNo.setText("");
                                }
                            });
                        }
                    });
                    break;
                case MOULDNOWHAT:
                    HashMap<String, String> mouldMap = new HashMap<>();
                    mouldMap.put(AddressContants.RESOURCE_NO, etMouldNo.getText().toString());
                    mouldMap.put(AddressContants.RESOURCE_TYPE, "3");
                    mouldMap.put(AddressContants.SUBOP_NO, commitBean.getSubop_no());
                    mouldMap.put(AddressContants.WO_NO, etOrderNumber.getText().toString());
                    mouldMap.put(AddressContants.CHECK_STATUS, AddressContants.CHECKOUT);
                    procedureCheckLogic.scanResource(mouldMap, new ProcedureCheckLogic.ScanResourceListener() {
                        @Override
                        public void onSuccess(ProcedureDevMouKniBean devMouKniBean) {
                            mouldShow = devMouKniBean.getShowing();
                            mouldFlag = true;
                            commitBean.setMould_no(etMouldNo.getText().toString());
                            if (!cbKniftNo.isChecked()) {
                                etKniftNo.requestFocus();
                            } else {
                                etInputNum.requestFocus();
                            }
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etMouldNo.setText("");
                                }
                            });
                        }
                    });
                    break;
                case KNIFTWHAT:
                    final HashMap<String, String> kniftMap = new HashMap<>();
                    kniftMap.put(AddressContants.RESOURCE_NO, etKniftNo.getText().toString());
                    kniftMap.put(AddressContants.RESOURCE_TYPE, "4");
                    kniftMap.put(AddressContants.SUBOP_NO,  commitBean.getSubop_no());
                    kniftMap.put(AddressContants.WO_NO, etOrderNumber.getText().toString());
                    kniftMap.put(AddressContants.CHECK_STATUS, AddressContants.CHECKOUT);
                    procedureCheckLogic.scanResource(kniftMap, new ProcedureCheckLogic.ScanResourceListener() {
                        @Override
                        public void onSuccess(ProcedureDevMouKniBean devMouKniBean) {
                            kniftShow = devMouKniBean.getShowing();
                            kniftFlag = true;
                            commitBean.setKnife_no(etKniftNo.getText().toString());
                            etInputNum.requestFocus();
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etKniftNo.setText("");
                                }
                            });
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
        module = ModuleCode.PROCEDUCECHECK;
        return module;

    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.procedure_checkout);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_procedure_checkout;
    }

    @Override
    protected void doBusiness() {
        EventBus.getDefault().register(this);
        commitBean = new ProcedureCheckoutCommitBean();
        badlist=new ArrayList<>();
        initData();

    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(procedureShow + "\\n" + circulationShow + "\\n"
                + orderShow + "\\n" + workersShow + "\\n" + deviceShow + "\\n" + mouldShow + "\\n" + kniftShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            rlZxDetail.setVisibility(View.VISIBLE);
        } else {
            rlZxDetail.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBadReason(List<ProcedureCheckoutBadResBean> message)
    {
        badlist=message;
    }


    private void sureCommit() {
        try{
            if (!procedureFlag) {
                showFailedDialog(getString(R.string.scan_procedure));
                return;
            }
            if (!circulationFlag && !orderFlag) {
                showFailedDialog(R.string.scan_circulationororder);
                return;
            }
            if (!workersFlag) {
                showFailedDialog(R.string.please_scan_workers);
                return;
            }
            if (!deviceFlag) {
                showFailedDialog(R.string.please_scan_device);
                return;
            }
            if (!cbMouldNo.isChecked()&&!mouldFlag){
                showFailedDialog(R.string.please_scan_mould);
                return;
            }
            if (!cbKniftNo.isChecked()&&!kniftFlag){
                showFailedDialog(R.string.please_scan_knift);
                return;
            }

            if (StringUtils.isBlank(etInputNum.getText().toString().trim()) ||
                    StringUtils.isBlank(etPendingNumber.getText().toString().trim())) {
                showFailedDialog(R.string.input_num);
                return;
            }
            commitBean.setUndefect_qty(etInputNum.getText().toString());
            commitBean.setDefect_qty(etPendingNumber.getText().toString());
            float badSum=0;
            for (ProcedureCheckoutBadResBean badResBean:badlist){
                badSum+=StringUtils.string2Float(badResBean.getDefect_reason_qty());
            }
            if (StringUtils.sub(etPendingNumber.getText().toString(),String.valueOf(badSum))!=0)
            {
                showFailedDialog(R.string.pendingnum_no_badsum);
                return;
            }
            commitBean.setDefect_reason_list(badlist);
            showLoadingDialog();
            procedureCheckLogic.commitCheckout(commitBean, new ProcedureCheckLogic.CommitListListener() {
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
        }catch (Exception e){

        }


    }

    private void initData() {
        procedureShow = "";
        circulationShow = "";
        orderShow = "";
        workersShow = "";
        deviceShow = "";
        kniftShow = "";
        mouldShow = "";
        procedureFlag = false;
        circulationFlag = false;
        orderFlag = false;
        workersFlag = false;
        deviceFlag = false;
        mouldFlag = false;
        kniftFlag = false;

        etProcedureNo.setText("");
        etOrderNumber.setText("");
        etCirculationNumber.setText("");
        etWorkers.setText("");
        etDeviceNo.setText("");
        etMouldNo.setText("");
        etKniftNo.setText("");
        etInputNum.setText("");
        etPendingNumber.setText("0");
        etProcedureNo.requestFocus();
//        cbCirculationNumber.setChecked(true);
//        cbCirculationNumber.setEnabled(false);
//        cbOrderNumber.setChecked(true);
//        cbOrderNumber.setEnabled(false);
        cbMouldNo.setChecked(true);
        cbMouldNo.setEnabled(false);
        cbKniftNo.setChecked(true);
        cbKniftNo.setEnabled(false);
        procedureCheckLogic = ProcedureCheckLogic.getInstance(activity, module, mTimestamp.toString());
        Connector.getDatabase();
        DataSupport.deleteAll(ProcedureCheckoutBadResBean.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
    }
}
