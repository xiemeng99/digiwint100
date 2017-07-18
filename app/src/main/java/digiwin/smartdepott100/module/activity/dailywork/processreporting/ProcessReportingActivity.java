package digiwin.smartdepott100.module.activity.dailywork.processreporting;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.bean.dailywork.ProcessReportingBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcessReportingCommitBean;
import digiwin.smartdepott100.module.bean.dailywork.WorkerPerson;
import digiwin.smartdepott100.module.logic.dailywok.ProcessReportingLogic;

/**
 * @author 赵浩然
 * @module 工序报工
 * @date 2017/3/15
 */

public class ProcessReportingActivity extends BaseTitleActivity {

    private ProcessReportingActivity activity;

    private final int WO_NO = 1001;

    private final int PROCESS_NO = 1002;

    private final int EMPLOYEE_NO = 1003;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    ProcessReportingLogic manager;

    /**
     * show
     */
    @BindView(R.id.ll_show)
    LinearLayout ll_show;

    /**
     * 作业名
     */
    @BindView(R.id.tv_job_name)
    TextView tv_job_name;

    /**
     * 人员名
     */
    @BindView(R.id.tv_person_name)
    TextView tv_person_name;

    /**
     * 本日已转
     */
    @BindView(R.id.tv_day_has_passed)
    TextView tv_day_has_passed;

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView tv_item_name;

    /**
     * 料号
     */
    @BindView(R.id.tv_item_no)
    TextView tv_item_no;

    /**
     * 工单单号
     */
    @BindView(R.id.et_gongDan_no)
    EditText et_gongDan_no;
    @BindView(R.id.tv_gongDan_no_code)
    TextView tv_gongDan_no_code;
    @BindView(R.id.ll_gongDan_no)
    LinearLayout ll_gongDan_no;
    /**
     * 作业号
     */
    @BindView(R.id.et_job_num)
    EditText et_job_num;
    @BindView(R.id.tv_job_num)
    TextView tv_job_num;
    @BindView(R.id.ll_job_num)
    LinearLayout ll_job_num;
    /**
     * 报工人
     */
    @BindView(R.id.et_the_workers)
    EditText et_the_workers;
    @BindView(R.id.tv_the_workers)
    TextView tv_the_workers;
    @BindView(R.id.ll_the_workers)
    LinearLayout ll_the_workers;

    /**
     * 良品量
     */
    @BindView(R.id.et_good_amount)
    EditText et_good_amount;
    @BindView(R.id.tv_good_amount)
    TextView tv_good_amount;
    @BindView(R.id.ll_good_amount)
    LinearLayout ll_good_amount;

    /**
     * 不良品量
     */
    @BindView(R.id.et_not_good_amount)
    EditText et_not_good_amount;
    @BindView(R.id.tv_not_good_amount)
    TextView tv_not_good_amount;
    @BindView(R.id.ll_not_good_amount)
    LinearLayout ll_not_good_amount;

    /**
     * 工时
     */
    @BindView(R.id.et_work_time)
    EditText et_work_time;
    @BindView(R.id.tv_work_time)
    TextView tv_work_time;
    @BindView(R.id.ll_work_time)
    LinearLayout ll_work_time;

    @BindViews({R.id.et_gongDan_no, R.id.et_job_num, R.id.et_the_workers,
            R.id.et_good_amount, R.id.et_not_good_amount, R.id.et_work_time})
    List<EditText> editTexts;
    @BindViews({R.id.tv_gongDan_no_code, R.id.tv_job_num, R.id.tv_the_workers,
            R.id.tv_good_amount, R.id.tv_not_good_amount, R.id.tv_work_time})
    List<TextView> textViews;
    @BindViews({R.id.ll_gongDan_no, R.id.ll_job_num, R.id.ll_the_workers,
            R.id.ll_good_amount, R.id.ll_not_good_amount, R.id.ll_work_time})
    List<View> views;

    @OnFocusChange(R.id.et_gongDan_no)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_gongDan_no, views);
        ModuleUtils.etChange(activity, et_gongDan_no, editTexts);
        ModuleUtils.tvChange(activity, tv_gongDan_no_code, textViews);
    }

    @OnFocusChange(R.id.et_job_num)
    void jobNumFocusChanage() {
        ModuleUtils.viewChange(ll_job_num, views);
        ModuleUtils.etChange(activity, et_job_num, editTexts);
        ModuleUtils.tvChange(activity, tv_job_num, textViews);
    }

    @OnFocusChange(R.id.et_the_workers)
    void workersFocusChanage() {
        ModuleUtils.viewChange(ll_the_workers, views);
        ModuleUtils.etChange(activity, et_the_workers, editTexts);
        ModuleUtils.tvChange(activity, tv_the_workers, textViews);
    }

    @OnFocusChange(R.id.et_good_amount)
    void numFocusChanage() {
        ModuleUtils.viewChange(ll_good_amount, views);
        ModuleUtils.etChange(activity, et_good_amount, editTexts);
        ModuleUtils.tvChange(activity, tv_good_amount, textViews);
    }

    @OnFocusChange(R.id.et_not_good_amount)
    void notNumFocusChanage() {
        ModuleUtils.viewChange(ll_not_good_amount, views);
        ModuleUtils.etChange(activity, et_not_good_amount, editTexts);
        ModuleUtils.tvChange(activity, tv_not_good_amount, textViews);
    }

    @OnFocusChange(R.id.et_work_time)
    void timeFocusChanage() {
        ModuleUtils.viewChange(ll_work_time, views);
        ModuleUtils.etChange(activity, et_work_time, editTexts);
        ModuleUtils.tvChange(activity, tv_work_time, textViews);
    }

    @OnTextChanged(value = R.id.et_gongDan_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void gongDanChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(WO_NO);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(WO_NO, s.toString().trim()),
                    AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_job_num, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void jobChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PROCESS_NO);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PROCESS_NO, s.toString().trim()),
                    AddressContants.DELAYTIME);
        }
    }

    @OnTextChanged(value = R.id.et_the_workers, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void workersChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(EMPLOYEE_NO);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(EMPLOYEE_NO, s.toString().trim()),
                    AddressContants.DELAYTIME);
        }
    }

    @BindView(R.id.commit)
    Button commit;

    @OnClick(R.id.commit)
    void commit() {
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                if (StringUtils.isBlank(et_gongDan_no.getText().toString())) {
                    showFailedDialog(R.string.scan_work_order);
                    return;
                }
                if (StringUtils.isBlank(et_job_num.getText().toString())) {
                    showFailedDialog(R.string.job_num_scan);
                    return;
                }
                if (StringUtils.isBlank(et_the_workers.getText().toString())) {
                    showFailedDialog(R.string.the_workers_scan);
                    return;
                }
                showLoadingDialog();

                List<ProcessReportingCommitBean> list = new ArrayList<ProcessReportingCommitBean>();
                ProcessReportingCommitBean bean = new ProcessReportingCommitBean();
                bean.setWo_no(et_gongDan_no.getText().toString());
                bean.setProcess_no(et_job_num.getText().toString());
                bean.setEmplyee_no(et_the_workers.getText().toString());
                bean.setUndefect_qty(et_good_amount.getText().toString());
                bean.setDefect_qty(et_not_good_amount.getText().toString());
                bean.setReport_hours(et_work_time.getText().toString());
                bean.setItem_no(tv_item_no.getText().toString());
                list.add(bean);
                List<Map<String, String>> listMap = ObjectAndMapUtils.getListMap(list);
                manager.commitList(listMap, new ProcessReportingLogic.CommitListListener() {
                    @Override
                    public void onSuccess(String msg) {
                        dismissLoadingDialog();
                        showCommitSuccessDialog(msg, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                clear();
                                createNewModuleId(module);
                                manager = ProcessReportingLogic
                                        .getInstance(activity, activity.module, activity.mTimestamp.toString());
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

            @Override
            public void onCallback2() {

            }
        });
    }

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WO_NO) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(AddressContants.WO_NO, String.valueOf(msg.obj));
                map.put(AddressContants.PROCESSNO, "");
                manager.scanCode(map, new ProcessReportingLogic.ScanCodeListener() {
                    @Override
                    public void onSuccess(ProcessReportingBean data) {
                        ll_show.setVisibility(View.VISIBLE);
                        tv_item_name.setText(data.getItem_name());
                        tv_item_no.setText(data.getItem_no());
                        et_job_num.requestFocus();
                    }

                    @Override
                    public void onFailed(String error) {
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                clear();
                            }
                        });
                    }
                });
            }

            if (msg.what == PROCESS_NO) {
                Map<String, String> map = new HashMap<String, String>();
                if (StringUtils.isBlank(et_gongDan_no.getText().toString().trim())) {
                    showFailedDialog(R.string.scan_work_order, new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                            return;
                        }
                    });
                }
                map.put(AddressContants.WO_NO, et_gongDan_no.getText().toString().trim());
                map.put(AddressContants.PROCESSNO, String.valueOf(msg.obj));
                manager.scanCode(map, new ProcessReportingLogic.ScanCodeListener() {
                    @Override
                    public void onSuccess(ProcessReportingBean data) {
                        tv_job_name.setText(data.getProcess_name());
                        tv_day_has_passed.setText(data.getWorking_num());
                        et_the_workers.requestFocus();
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                clearJob();
                            }
                        });
                    }
                });
            }

            if (msg.what == EMPLOYEE_NO) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(AddressContants.EMPLOYEENO, String.valueOf(msg.obj));
                manager.scanPerson(map, new ProcessReportingLogic.ScanPersonListener() {
                    @Override
                    public void onSuccess(WorkerPerson WorkerPerson) {
                        tv_person_name.setText(WorkerPerson.getEmployee_name());
                        et_good_amount.requestFocus();
                    }

                    @Override
                    public void onFailed(String error) {
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                clearPerson();
                            }
                        });
                    }
                });
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    public void clear() {
        tv_item_name.setText("");
        tv_item_no.setText("");
        tv_job_name.setText("");
        tv_day_has_passed.setText("");
        et_good_amount.setText("");
        et_not_good_amount.setText("");
        et_work_time.setText("");
        tv_person_name.setText("");
        et_gongDan_no.setText("");
        et_job_num.setText("");
        et_the_workers.setText("");
        ll_show.setVisibility(View.GONE);
        et_gongDan_no.requestFocus();
    }

    public void clearJob() {
        tv_job_name.setText("");
        tv_day_has_passed.setText("");
        tv_person_name.setText("");
        et_job_num.setText("");
        et_job_num.requestFocus();
    }

    public void clearPerson() {
        tv_person_name.setText("");
        et_the_workers.setText("");
        et_the_workers.requestFocus();
    }

    @Override
    protected void doBusiness() {
        manager = ProcessReportingLogic
                .getInstance(activity, activity.module, activity.mTimestamp.toString());
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_processreporting;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PROCESSREPORTING;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getResources().getString(R.string.title_pallet_report));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);

    }
}
