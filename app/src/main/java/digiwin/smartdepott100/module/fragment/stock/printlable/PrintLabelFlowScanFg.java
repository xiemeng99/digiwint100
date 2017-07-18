package digiwin.smartdepott100.module.fragment.stock.printlable;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.SharePreferenceKey;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.core.printer.WiFiPrintManager;
import digiwin.smartdepott100.main.activity.SettingActivity;
import digiwin.smartdepott100.module.activity.stock.printlabel.PrintLabelFlowActivity;
import digiwin.smartdepott100.module.activity.stock.printlabel.PrintLableFlowDialog;
import digiwin.smartdepott100.module.adapter.stock.printlable.PrintLableFlowAdapter;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureOrderBean;
import digiwin.smartdepott100.module.bean.stock.PrintLabelFlowBean;
import digiwin.smartdepott100.module.logic.stock.printer.PrinterFlowLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;

import static digiwin.smartdepott100.R.id.et_resource_barcode;

/**
 * Created by qGod on 2017/5/28
 * Thank you for watching my code
 */

public class PrintLabelFlowScanFg extends BaseFragment {
    /**
     * 工单单号
     */
    @BindView(R.id.ll_resource_barcode)
    LinearLayout llWorkOrderNo;
    @BindView(R.id.tv_resource_barcode)
    TextView tvWorkOrderNo;
    @BindView(et_resource_barcode)
    EditText etWorkOrderNo;
    /**
     * 设备编号
     */
    @BindView(R.id.ll_device_number)
    LinearLayout llDeviceNo;
    @BindView(R.id.tv_device_number)
    TextView tvDeviceNo;
    @BindView(R.id.et_device_number)
    EditText etDeviceNo;
    /**
     * 模具编号
     */
    @BindView(R.id.ll_mould_no)
    LinearLayout llMouldNo;
    @BindView(R.id.tv_mould_no)
    TextView tvMouldNo;
    @BindView(R.id.et_mould_no)
    EditText etMouldNo;
    /**
     * 作业人员
     */
    @BindView(R.id.ll_work_people)
    LinearLayout llWorkPeople;
    @BindView(R.id.tv_work_people)
    TextView tvWorkPeople;
    @BindView(R.id.et_work_people)
    EditText etWorkPeople;

    @BindViews({et_resource_barcode, R.id.et_device_number, R.id.et_mould_no, R.id.et_work_people})
    List<EditText> editTexts;
    @BindViews({R.id.tv_resource_barcode, R.id.tv_device_number, R.id.tv_mould_no, R.id.tv_work_people})
    List<TextView> textViews;
    @BindViews({R.id.ll_resource_barcode, R.id.ll_device_number, R.id.ll_mould_no, R.id.ll_work_people})
    List<View> views;

    //工单单号
    @OnFocusChange(et_resource_barcode)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(llWorkOrderNo, views);
        ModuleUtils.etChange(activity, etWorkOrderNo, editTexts);
        ModuleUtils.tvChange(activity, tvWorkOrderNo, textViews);
    }

    //设备编号
    @OnFocusChange(R.id.et_device_number)
    void deviceNoFocusChanage() {
        ModuleUtils.viewChange(llDeviceNo, views);
        ModuleUtils.etChange(activity, etDeviceNo, editTexts);
        ModuleUtils.tvChange(activity, tvDeviceNo, textViews);
    }

    //模具编号
    @OnFocusChange(R.id.et_mould_no)
    void mouldNoFocusChanage() {
        ModuleUtils.viewChange(llMouldNo, views);
        ModuleUtils.etChange(activity, etMouldNo, editTexts);
        ModuleUtils.tvChange(activity, tvMouldNo, textViews);
    }

    //作业人员
    @OnFocusChange(R.id.et_work_people)
    void peopleFocusChanage() {
        ModuleUtils.viewChange(llWorkPeople, views);
        ModuleUtils.etChange(activity, etWorkPeople, editTexts);
        ModuleUtils.tvChange(activity, tvWorkPeople, textViews);
    }

    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.includeDetail)
    View includeDetail;

    /**
     * 工单单号
     */
    final int BARCODE = 1000;
    /**
     * 设备编号
     */
    final int DEVICENO = 1001;
    /**
     * 模具编号
     */
    final int MOULDNO = 1002;
    /**
     * 作业人员
     */
    final int WORKPEOPLE = 1003;

    private String oldBarcode;

    //扫描工单单号
    @OnTextChanged(value = R.id.et_resource_barcode, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODE);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODE, s.toString().trim()), AddressContants.DELAYTIME);
            if (!oldBarcode.equals(s.toString().trim())) {
                isQuery = true;
            } else {
                isQuery = false;
            }
            upDateBtnText();
            oldBarcode = s.toString().trim();
        }
    }

    private String oldDevice;

    //扫描设备编号
    @OnTextChanged(value = R.id.et_device_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void deviceChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(DEVICENO);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(DEVICENO, s.toString().trim()), AddressContants.DELAYTIME);
            if (!oldDevice.equals(s.toString().trim())) {
                isQuery = true;
            } else {
                isQuery = false;
            }
            upDateBtnText();
            oldDevice = s.toString().trim();
        }
    }

    private String oldMouldNo;

    //扫描模具编号
    @OnTextChanged(value = R.id.et_mould_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void mouldNoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(MOULDNO);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(MOULDNO, s.toString().trim()), AddressContants.DELAYTIME);
            if (!oldMouldNo.equals(s.toString().trim())) {
                isQuery = true;
            } else {
                isQuery = false;
            }
            upDateBtnText();
            oldMouldNo = s.toString().trim();
        }
    }

    private String oldWorkPeople;

    //扫描工作人员
    @OnTextChanged(value = R.id.et_work_people, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void workPeopleChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(WORKPEOPLE);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(WORKPEOPLE, s.toString().trim()), AddressContants.DELAYTIME);
            if (!oldWorkPeople.equals(s.toString().trim())) {
                isQuery = true;
            } else {
                isQuery = false;
            }
            upDateBtnText();
            oldWorkPeople = s.toString().trim();
        }
    }

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.btn_print)
    Button button;

    PrinterFlowLogic logic;

    private PrintLabelFlowActivity parentActivity;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_printlabel_scan_flow;
    }

    @Override
    protected void doBusiness() {
        parentActivity = (PrintLabelFlowActivity) getActivity();
        logic = PrinterFlowLogic.getInstance(parentActivity, parentActivity.module, parentActivity.mTimestamp.toString());
        initData();
    }

    private Boolean isQuery;

    private PrintLableFlowAdapter adapter;

    public void upDateBtnText() {
        if (isQuery) {
            button.setText(parentActivity.getResources().getText(R.string.query));
        } else {//打印
            button.setText(parentActivity.getResources().getText(R.string.print));
        }
    }

    private List<PrintLabelFlowBean> listDatas;

    public void upListData() {
        recyclerview.setLayoutManager(new LinearLayoutManager(parentActivity));
        adapter = new PrintLableFlowAdapter(parentActivity, listDatas);
        recyclerview.setAdapter(adapter);
        upDateRv();
    }

    WiFiPrintManager wifiManager;

    public void initData() {
        wifiManager = WiFiPrintManager.getManager();
        listDatas = new ArrayList<>();
        upListData();
        etWorkOrderNo.requestFocus();
        etWorkOrderNo.setText("");
        etDeviceNo.setText("");
        etMouldNo.setText("");
        etWorkPeople.setText("");
        deviceShow = "";
        moduleShow = "";
        peopleShow = "";
        oldBarcode = "";
        oldDevice = "";
        oldMouldNo = "";
        oldWorkPeople = "";
        isQuery = true;
        upDateBtnText();
    }

    /**
     * 判断recyclerview是否显示
     */
    private void upDateRv() {
        if (listDatas.size() > 0) {
            recyclerview.setVisibility(View.VISIBLE);
        } else {
            recyclerview.setVisibility(View.GONE);
        }
    }

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODE://工单单号
                    HashMap<String, String> map = new HashMap<>();
                    map.put(AddressContants.WO_NO, msg.obj.toString().trim());
                    map.put(AddressContants.CHECK_STATUS, "print");
                    logic.scanOrder(map, new PrinterFlowLogic.ScanOrderListener() {
                        @Override
                        public void onSuccess(ProcedureOrderBean procedureOrderBean) {
                            etDeviceNo.requestFocus();
                            isQuery = true;
                            upDateBtnText();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error);
                            etWorkOrderNo.requestFocus();
                            etWorkOrderNo.setText("");
                        }
                    });
                    break;
                case DEVICENO://设备编号
                    HashMap<String, String> map1 = new HashMap<>();
                    map1.put(AddressContants.RESOURCE_NO, msg.obj.toString().trim());
                    map1.put(AddressContants.RESOURCE_TYPE, "1");
                    map1.put(AddressContants.WO_NO, etWorkOrderNo.getText().toString().trim());
                    map1.put(AddressContants.MOVE_STATUS, "print");
                    logic.scanNo(map1, new PrinterFlowLogic.ScanNoListener() {
                        @Override
                        public void onSuccess(String resource) {
                            deviceShow = resource;
                            etMouldNo.requestFocus();
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error);
                            etDeviceNo.requestFocus();
                            etDeviceNo.setText("");
                        }
                    });
                    break;
                case MOULDNO://模具编号
                    HashMap<String, String> map2 = new HashMap<>();
                    map2.put(AddressContants.RESOURCE_NO, msg.obj.toString().trim());
                    map2.put(AddressContants.WO_NO, etWorkOrderNo.getText().toString().trim());
                    map2.put(AddressContants.RESOURCE_TYPE, "3");
                    map2.put(AddressContants.CHECK_STATUS, "print");
                    logic.scanNo(map2, new PrinterFlowLogic.ScanNoListener() {
                        @Override
                        public void onSuccess(String resource) {
                            moduleShow = resource;
                            etWorkPeople.requestFocus();
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error);
                            etMouldNo.requestFocus();
                            etMouldNo.setText("");
                        }
                    });
                    break;
                case WORKPEOPLE://工作人员
                    HashMap<String, String> map3 = new HashMap<>();
                    map3.put(AddressContants.EMPLOYEENO, msg.obj.toString().trim());
                    logic.scanPeople(map3, new PrinterFlowLogic.ScanPeopleListener() {
                        @Override
                        public void onSuccess(String resource) {
                            peopleShow = resource;
                            show();
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error);
                            etWorkPeople.requestFocus();
                            etWorkPeople.setText("");
                        }
                    });
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    /**
     * 物料批号
     */
    StringBuffer sbPlotNo;

    /**
     * 按钮
     */
    @OnClick(R.id.btn_print)
    void print() {
        //查询
        if (button.getText().equals(parentActivity.getResources().getText(R.string.query))) {
            HashMap<String, String> map = new HashMap<>();
            map.put(AddressContants.WO_NO, etWorkOrderNo.getText().toString().trim());
            map.put(AddressContants.MACHINENO, etDeviceNo.getText().toString().trim());
            map.put(AddressContants.MOULDNO, etMouldNo.getText().toString().trim());
            map.put(AddressContants.EMPLOYEENO, etWorkPeople.getText().toString().trim());
            map.put(AddressContants.PRINT_TYPE, "1");
            logic.queryData(map, new PrinterFlowLogic.queryDataListener() {
                @Override
                public void onSuccess(List<PrintLabelFlowBean> datas) {
                    listDatas = datas;
                    if (listDatas.size() == 0) {
                        showFailedDialog(R.string.data_null);
                        return;
                    }
                    upListData();
                    //更新button界面
                    isQuery = false;
                    upDateBtnText();
                }

                @Override
                public void onFailed(String error) {
                    showFailedDialog(error);
                }
            });
        } else {//打印
            final List<PrintLabelFlowBean> seletorLists = adapter.getSeletor();
            if (seletorLists.size() == 0) {
                showFailedDialog(getString(R.string.no_prenter_data));
                return;
            }
            //字符串拼接
            sbPlotNo = new StringBuffer();
            for (int i = 0; i < seletorLists.size(); i++) {
                PrintLabelFlowBean bean = seletorLists.get(i);
                sbPlotNo.append(bean.getPlot_no());
                if (i != seletorLists.size() - 1) sbPlotNo.append("/n");
            }
            PrintLableFlowDialog.showPrinterDailog(parentActivity, sbPlotNo.toString(), new PrintLableFlowDialog.PrinterFlowListener() {
                @Override
                public void bindByDevice(final String labelNumber, final String printNumber) {
                    //获取默认ip
                    String defaultIp = (String) SharedPreferencesUtils.get(parentActivity, SharePreferenceKey.PRINTER_IP, "");
                    if (StringUtils.isBlank(defaultIp)) {
                        showFailedDialog(getString(R.string.ip_setting), new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                startActivity(new Intent(parentActivity, SettingActivity.class));
                            }
                        });
                    } else {
                        wifiManager.openWiFi(defaultIp, new WiFiPrintManager.OpenWiFiPrintListener() {
                            @Override
                            public void isOpen(boolean isOpen) {
                                if (isOpen) {
                                    //TODO: 2017/5/28 测试 打印成功后续操作没写，清空数据之类，可直接调用initdata方法
                                    int parseInt = StringUtils.parseInt(printNumber);
                                    for (int i = 0; i < seletorLists.size(); i++) {
                                        for (int j = 0; j < parseInt; j++) {
                                            PrintLabelFlowBean flowBean = seletorLists.get(i);
                                            wifiManager.printFlowText(flowBean,labelNumber);
                                        }
                                    }
                                    wifiManager.close();
                                } else {
                                    showFailedDialog(getString(R.string.printer_connect_failed));
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    /**
     * 设备显示
     */
    private String deviceShow;
    /**
     * 模具显示
     */
    private String moduleShow;
    /**
     * 作业人员显示
     */
    private String peopleShow;

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(deviceShow + "\\n" + moduleShow + "\\n" + peopleShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            includeDetail.setVisibility(View.VISIBLE);
        } else {
            includeDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
