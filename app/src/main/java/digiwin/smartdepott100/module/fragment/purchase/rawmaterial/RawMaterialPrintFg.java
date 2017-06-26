package digiwin.smartdepott100.module.fragment.purchase.rawmaterial;

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
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.SharePreferenceKey;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.core.printer.WiFiPrintManager;
import digiwin.smartdepott100.main.activity.SettingActivity;
import digiwin.smartdepott100.module.activity.purchase.rawmaterial.RawMaterialPrintActivity;
import digiwin.smartdepott100.module.activity.stock.printlabel.PrintLableFlowDialog;
import digiwin.smartdepott100.module.adapter.purchase.RawMaterialAdapter;
import digiwin.smartdepott100.module.bean.purchase.RawMaterialPrintBean;
import digiwin.smartdepott100.module.logic.purchase.RawMaterialPrintLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;

import static digiwin.smartdepott100.R.id.et_purchase_order;

/**
 * @author xiemeng
 * @des 原材料标签打印
 * @date 2017/6/8 09:23
 */

public class RawMaterialPrintFg extends BaseFragment {
    /**
     * 工单单号
     */
    @BindView(R.id.ll_purchase_order)
    LinearLayout llPurchaseOrder;
    @BindView(R.id.tv_purchase_order)
    TextView tvPurchaseOrder;
    @BindView(et_purchase_order)
    EditText etPurchaseOrder;

    @BindViews({et_purchase_order,})
    List<EditText> editTexts;
    @BindViews({R.id.tv_purchase_order,})
    List<TextView> textViews;
    @BindViews({R.id.ll_purchase_order,})
    List<View> views;

    //工单单号
    @OnFocusChange(et_purchase_order)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(llPurchaseOrder, views);
        ModuleUtils.etChange(activity, etPurchaseOrder, editTexts);
        ModuleUtils.tvChange(activity, tvPurchaseOrder, textViews);
    }

    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.includeDetail)
    View includeDetail;

    /**
     * 收货单号
     */
    final int BARCODE = 1000;

    private String oldBarcode;

    @OnTextChanged(value = R.id.et_purchase_order, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODE);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODE, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.btn_print)
    Button button;

    RawMaterialPrintLogic logic;

    private RawMaterialPrintActivity parentActivity;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_rawmaterialprint;
    }

    @Override
    protected void doBusiness() {
        parentActivity = (RawMaterialPrintActivity) getActivity();
        logic = RawMaterialPrintLogic.getInstance(parentActivity, parentActivity.module, parentActivity.mTimestamp.toString());
        initData();
    }

    private RawMaterialAdapter adapter;


    private List<RawMaterialPrintBean> listDatas;

    WiFiPrintManager wifiManager;

    public void initData() {
        wifiManager = WiFiPrintManager.getManager();
        listDatas = new ArrayList<>();
        upListData();
        etPurchaseOrder.requestFocus();
        etPurchaseOrder.setText("");
        oldBarcode = "";
    }
    public void upListData() {
        recyclerview.setLayoutManager(new LinearLayoutManager(parentActivity));
        adapter = new RawMaterialAdapter(parentActivity, listDatas);
        recyclerview.setAdapter(adapter);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BARCODE://收货单号
                    HashMap<String, String> map = new HashMap<>();
                    map.put(AddressContants.DOC_NO, msg.obj.toString().trim());
                    map.put(AddressContants.PRINT_TYPE, "1");
                    logic.scanOrder(map, new RawMaterialPrintLogic.ScanOrderListener() {
                        @Override
                        public void onSuccess(List<RawMaterialPrintBean> sumBeen) {
                            listDatas=sumBeen;
                            upListData();
                            if (listDatas.size()>0){
                                tvSupplier.setText(listDatas.get(0).getSupplier_no()+
                                AddressContants.SPLITFLAG+listDatas.get(0).getSupplier_name());
                            }
                        }
                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etPurchaseOrder.requestFocus();
                                    etPurchaseOrder.setText("");
                                }
                            });

                        }
                    });
                    break;
            }
        }
    };

    /**
     * 物料条码
     */
    StringBuffer sbPlotNo;

    /**
     * 按钮
     */
    @OnClick(R.id.btn_print)
    void print() {
        final List<RawMaterialPrintBean> seletorLists = adapter.getSeletor();
        if (seletorLists.size() == 0) {
            showFailedDialog(getString(R.string.no_prenter_data));
            return;
        }
        //字符串拼接
        sbPlotNo = new StringBuffer();
        for (int i = 0; i < seletorLists.size(); i++) {
            RawMaterialPrintBean bean = seletorLists.get(i);
            sbPlotNo.append(bean.getItem_no());
            if (i != seletorLists.size() - 1) sbPlotNo.append("/n");
        }
        PrintLableFlowDialog.showRawMaterialPrint(parentActivity, sbPlotNo.toString(), new PrintLableFlowDialog.PrinterFlowListener() {
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
                                int parseInt = StringUtils.parseInt(printNumber);
                                for (int i = 0; i < seletorLists.size(); i++) {
                                    for (int j = 0; j < parseInt; j++) {
                                        RawMaterialPrintBean flowBean = seletorLists.get(i);
                                        wifiManager.printRawMaterial(flowBean, labelNumber);
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
