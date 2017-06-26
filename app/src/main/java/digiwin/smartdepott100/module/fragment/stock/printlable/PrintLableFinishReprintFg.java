package digiwin.smartdepott100.module.fragment.stock.printlable;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.SharePreferenceKey;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.core.printer.WiFiPrintManager;
import digiwin.smartdepott100.main.activity.SettingActivity;
import digiwin.smartdepott100.module.activity.stock.printlabel.PrintLabelFinishActivity;
import digiwin.smartdepott100.module.activity.stock.printlabel.PrintLableFlowDialog;
import digiwin.smartdepott100.module.bean.stock.PrintLabelFlowBean;
import digiwin.smartdepott100.module.logic.stock.printer.PrinterFlowLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;

import static digiwin.smartdepott100.R.id.et_resource_barcode;

/**
 * Created by qGod on 2017/5/28
 * Thank you for watching my code
 * 成品标签打印——标签补打
 */

public class PrintLableFinishReprintFg extends BaseFragment {
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
     * 物料批次
     */
    @BindView(R.id.ll_material_batche)
    LinearLayout llMaterialBatche;
    @BindView(R.id.tv_material_batche)
    TextView tvMaterialBatche;
    @BindView(R.id.et_material_batche)
    EditText etMaterialBatche;
    private PrintLabelFinishActivity parentActivity;

    @BindViews({et_resource_barcode, R.id.et_material_batche})
    List<EditText> editTexts;
    @BindViews({R.id.tv_resource_barcode, R.id.tv_material_batche})
    List<TextView> textViews;
    @BindViews({R.id.ll_resource_barcode, R.id.ll_material_batche})
    List<View> views;

    //工单单号
    @OnFocusChange(et_resource_barcode)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(llWorkOrderNo, views);
        ModuleUtils.etChange(activity, etWorkOrderNo, editTexts);
        ModuleUtils.tvChange(activity, tvWorkOrderNo, textViews);
    }

    //物料批次
    @OnFocusChange(R.id.et_material_batche)
    void deviceNoFocusChanage() {
        ModuleUtils.viewChange(llMaterialBatche, views);
        ModuleUtils.etChange(activity, etMaterialBatche, editTexts);
        ModuleUtils.tvChange(activity, tvMaterialBatche, textViews);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_printlabel_finish_reprint;
    }

    WiFiPrintManager wifiManager;

    PrinterFlowLogic logic;

    /**
     * 批号
     */
    StringBuffer sbPlotNo;

    @Override
    protected void doBusiness() {
        wifiManager = WiFiPrintManager.getManager();
        parentActivity = (PrintLabelFinishActivity) getActivity();
        logic = PrinterFlowLogic.getInstance(parentActivity, parentActivity.module, parentActivity.mTimestamp.toString());
        initData();
    }

    private void initData() {
        etWorkOrderNo.setText("");
        etMaterialBatche.setText("");
    }

    @OnClick(R.id.btn_print)
    void printLable() {
        //默认先查询后打印
        HashMap<String, String> map = new HashMap<>();
        map.put(AddressContants.WO_NO, etWorkOrderNo.getText().toString().trim());
        map.put(AddressContants.PLOTNO, etMaterialBatche.getText().toString().trim());
        map.put(AddressContants.PRINT_TYPE, "2");
        logic.queryFinishData(map, new PrinterFlowLogic.queryDataListener() {
            @Override
            public void onSuccess(List<PrintLabelFlowBean> seletorLists) {
                showPrintDialog(seletorLists);
            }

            @Override
            public void onFailed(String error) {
                showFailedDialog(error);
            }
        });
    }

    private void showPrintDialog(final List<PrintLabelFlowBean> seletorLists) {
        //字符串拼接
        sbPlotNo = new StringBuffer();
        for (int i = 0; i < seletorLists.size(); i++) {
            PrintLabelFlowBean bean = seletorLists.get(i);
            sbPlotNo.append(bean.getPlot_no());
            if (i != seletorLists.size() - 1) sbPlotNo.append("/n");
        }

        PrintLableFlowDialog.showRePrinterDailog(parentActivity, new PrintLableFlowDialog.PrinterFlowListener() {
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
                                // TODO: 2017/5/28 测试 打印成功后续操作没写，清空数据之类，可直接调用initdata方法
                                int parseInt = StringUtils.parseInt(printNumber);
                                for (int i = 0; i < seletorLists.size(); i++) {
                                    for (int j = 0; j < parseInt; j++) {
                                        PrintLabelFlowBean flowBean = seletorLists.get(i);
                                        wifiManager.printFinishText(flowBean,labelNumber);
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
