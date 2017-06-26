package digiwin.smartdepott100.module.fragment.purchase.rawmaterial;

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
import butterknife.OnTextChanged;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.SharePreferenceKey;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.core.printer.WiFiPrintManager;
import digiwin.smartdepott100.main.activity.SettingActivity;
import digiwin.smartdepott100.module.activity.purchase.rawmaterial.RawMaterialPrintActivity;
import digiwin.smartdepott100.module.activity.stock.printlabel.PrintLableFlowDialog;
import digiwin.smartdepott100.module.bean.purchase.RawMaterialPrintBean;
import digiwin.smartdepott100.module.logic.purchase.RawMaterialPrintLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;

import static digiwin.smartdepott100.R.id.et_item_no;

/**
 * @author xiemeng
 * @des 原材料标签补打
 * @date 2017/6/8 09:23
 */

public class RawMaterialReprintFg extends BaseFragment {

    @BindView(R.id.ll_item_no)
    LinearLayout llItemNo;
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(et_item_no)
    EditText etItemNo;

    @BindView(R.id.ll_luhao)
    LinearLayout llLuHao;
    @BindView(R.id.tv_luhao)
    TextView tvLuHao;
    @BindView(R.id.et_luhao)
    EditText etLuHao;

    @BindView(R.id.ll_supplier)
    LinearLayout llSupplier;
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.et_supplier)
    EditText etSupplier;

    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.et_date)
    EditText etDate;

    private RawMaterialPrintActivity parentActivity;

    @BindViews({R.id.et_item_no, R.id.et_luhao,R.id.et_supplier,R.id.et_date})
    List<EditText> editTexts;
    @BindViews({R.id.tv_item_no, R.id.tv_luhao,R.id.tv_supplier,R.id.tv_date})
    List<TextView> textViews;
    @BindViews({R.id.ll_item_no, R.id.ll_luhao,R.id.ll_supplier,R.id.ll_date})
    List<View> views;

    @OnFocusChange(et_item_no)
    void itemNoFocusChanage() {
        ModuleUtils.viewChange(llItemNo, views);
        ModuleUtils.etChange(activity, etItemNo, editTexts);
        ModuleUtils.tvChange(activity, tvItemNo, textViews);
    }

    @OnFocusChange(R.id.et_luhao)
    void luHaoNoFocusChanage() {
        ModuleUtils.viewChange(llLuHao, views);
        ModuleUtils.etChange(activity, etLuHao, editTexts);
        ModuleUtils.tvChange(activity, tvLuHao, textViews);
    }
    @OnFocusChange(R.id.et_supplier)
    void etsupplierFocusChanage() {
        ModuleUtils.viewChange(llSupplier, views);
        ModuleUtils.etChange(activity, etLuHao, editTexts);
        ModuleUtils.tvChange(activity, tvLuHao, textViews);
    }
    @OnFocusChange(R.id.et_date)
    void etDateocusChanage() {
        ModuleUtils.viewChange(llLuHao, views);
        ModuleUtils.etChange(activity, etLuHao, editTexts);
        ModuleUtils.tvChange(activity, tvLuHao, textViews);
    }

    private String oldBarcode;
    private boolean isBarcode;

    //工单单号
    @OnTextChanged(value = R.id.et_item_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void mouldNoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            oldBarcode = s.toString().trim();
            isBarcode = true;
        }
    }

    private String oldLuHao;
    private boolean isLuhao;

    @OnTextChanged(value = R.id.et_luhao, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void luHaoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            oldLuHao = s.toString().trim();
            isLuhao = true;
        }
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_rawmaterial_reprint;
    }

    WiFiPrintManager wifiManager;
    RawMaterialPrintLogic logic;

    @Override
    protected void doBusiness() {
        wifiManager = WiFiPrintManager.getManager();
        parentActivity = (RawMaterialPrintActivity) getActivity();
        logic = RawMaterialPrintLogic.getInstance(parentActivity, parentActivity.module, parentActivity.mTimestamp.toString());
        initData();

    }

    private void initData() {
        etItemNo.setText("");
        etLuHao.setText("");
        isBarcode = false;
        isLuhao = false;
    }

    /**
     * 物料批号
     */
    StringBuffer sbPlotNo;

    @OnClick(R.id.btn_print)
    void printLable() {
        //传入工单号进行校验,默认先查询后打印
        HashMap<String, String> map = new HashMap<>();
        map.put(AddressContants.ITEM_NO, etItemNo.getText().toString().trim());
        map.put(AddressContants.FURNACE_NO, etLuHao.getText().toString().trim());
        map.put(AddressContants.SUPPLIER,etSupplier.getText().toString().trim());
        map.put("create_date",etDate.getText().toString().trim());
        map.put(AddressContants.PRINT_TYPE, "2");
        logic.scanOrder(map, new RawMaterialPrintLogic.ScanOrderListener() {
            @Override
            public void onSuccess(List<RawMaterialPrintBean> seletorLists) {
                showPrintDialog(seletorLists);
            }

            @Override
            public void onFailed(String error) {
                showFailedDialog(error);
            }
        });
    }

    private void showPrintDialog(final  List<RawMaterialPrintBean> seletorLists) {
        //字符串拼接
        sbPlotNo = new StringBuffer();
        for (int i = 0; i < seletorLists.size(); i++) {
            RawMaterialPrintBean bean = seletorLists.get(i);
            sbPlotNo.append(bean.getLot_no());
            if (i != seletorLists.size() - 1) sbPlotNo.append("/n");
        }

        PrintLableFlowDialog.showRePrinterDailog(parentActivity, new PrintLableFlowDialog.PrinterFlowListener() {
            @Override
            public void bindByDevice(final String labelNumber, final String printNumber) {
                //获取默认ip
                String defaultIp = (String) SharedPreferencesUtils.get(parentActivity, SharePreferenceKey.PRINTER_IP, "");
                if(StringUtils.isBlank(defaultIp)){
                    showFailedDialog(getString(R.string.ip_setting), new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                            startActivity(new Intent(parentActivity, SettingActivity.class));
                        }
                    });
                }else {
                    wifiManager.openWiFi(defaultIp, new WiFiPrintManager.OpenWiFiPrintListener() {
                        @Override
                        public void isOpen(boolean isOpen) {
                            if (isOpen) {
                                int parseInt = StringUtils.parseInt(printNumber);
                                for (int i = 0; i < seletorLists.size(); i++) {
                                    for (int j = 0; j < parseInt; j++) {
                                        RawMaterialPrintBean flowBean = seletorLists.get(i);
                                        wifiManager.printRawMaterial(flowBean,labelNumber);
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
