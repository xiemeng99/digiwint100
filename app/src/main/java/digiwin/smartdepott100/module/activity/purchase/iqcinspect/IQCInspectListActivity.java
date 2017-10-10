package digiwin.smartdepott100.module.activity.purchase.iqcinspect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.core.dialog.datepicker.DatePickerUtils;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleHActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.adapter.purchase.IQCInspectScanAdapter;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;
import digiwin.smartdepott100.module.logic.purchase.IQCInspectLogic;

/**
 * Created by lenovo on 2017/8/11.
 */

public class IQCInspectListActivity extends BaseTitleHActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 送货单号
     */
    @BindView(R.id.tv_delivery_note_no)
    TextView tvDeliveryNoteNo;
    @BindView(R.id.et_delivery_note_no)
    EditText etDeliveryNoteNo;
    @BindView(R.id.ll_delivery_note_no)
    LinearLayout llDeliveryNoteNo;
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    @OnFocusChange(R.id.et_delivery_note_no)
    void deliveryNoteNoFocusChanage() {
        ModuleUtils.viewChange(llDeliveryNoteNo, views);
        ModuleUtils.tvChange(activity, tvDeliveryNoteNo, textViews);
        ModuleUtils.etChange(activity, etDeliveryNoteNo, editTexts);
    }

    /**
     * 收货单号
     */
    @BindView(R.id.tv_purchase_order)
    TextView tvPurchaseOrder;
    @BindView(R.id.et_purchase_order)
    EditText etPurchaseOrder;
    @BindView(R.id.ll_purchase_order)
    LinearLayout llPurchaseOrder;

    @OnFocusChange(R.id.et_purchase_order)
    void purchaseOrderFocusChanage() {
        ModuleUtils.viewChange(llPurchaseOrder, views);
        ModuleUtils.tvChange(activity, tvPurchaseOrder, textViews);
        ModuleUtils.etChange(activity, etPurchaseOrder, editTexts);
    }

    /**
     * 物料条码
     */
    @BindView(R.id.tv_material_number)
    TextView tvMaterialNumber;
    @BindView(R.id.et_material_number)
    EditText etMaterialNumber;
    @BindView(R.id.ll_material_number)
    LinearLayout llMaterialNumber;

    @OnFocusChange(R.id.et_material_number)
    void materialNumberFocusChanage() {
        ModuleUtils.viewChange(llMaterialNumber, views);
        ModuleUtils.tvChange(activity, tvMaterialNumber, textViews);
        ModuleUtils.etChange(activity, etMaterialNumber, editTexts);
    }

    /**
     * 供应商
     */
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.et_supplier)
    EditText etSupplier;
    @BindView(R.id.ll_supplier)
    LinearLayout llSupplier;

    @OnFocusChange(R.id.et_supplier)
    void supplierFocusChanage() {
        ModuleUtils.viewChange(llSupplier, views);
        ModuleUtils.tvChange(activity, tvSupplier, textViews);
        ModuleUtils.etChange(activity, etSupplier, editTexts);
    }

    /**
     * 筛选框 计划日
     */
    @BindView(R.id.iv_date)
    ImageView ivDate;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ll_date)
    LinearLayout llDate;
    @BindView(R.id.et_date)
    EditText etDate;

    @OnFocusChange(R.id.et_date)
    void plan_dateFocusChanage() {
        ModuleUtils.viewChange(llDate, views);
        ModuleUtils.tvChange(activity, tvDate, textViews);
        ModuleUtils.etChange(activity, etDate, editTexts);
    }

    @OnClick(R.id.iv_date)
    void dateClick() {
        DatePickerUtils.getDoubleDate(activity, new DatePickerUtils.GetDoubleDateListener() {
            @Override
            public void getTime(String mStartDate, String mEndDate, String showDate) {
                etDate.requestFocus();
                startDate = mStartDate;
                endDate = mEndDate;
                etDate.setText(showDate);
            }
        });
    }

    /**
     * 弹出筛选对话框
     */
    @OnClick(R.id.iv_title_setting)
    void SearchDialog() {
        if (llSearchDialog.getVisibility() == View.VISIBLE) {
            if (null != qcList && qcList.size() > 0) {
                llSearchDialog.setVisibility(View.GONE);
                scrollview.setVisibility(View.VISIBLE);
            }
        } else {
            llSearchDialog.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }
    }

    @BindView(R.id.ll_search_dialog)
    LinearLayout llSearchDialog;
    @BindView(R.id.activity_distribute)
    RelativeLayout activityDistribute;
    private IQCInspectScanAdapter adapter;

    private List<QCScanData> qcList;

    @BindViews({R.id.ll_delivery_note_no, R.id.ll_purchase_order,
            R.id.ll_material_number, R.id.ll_supplier, R.id.ll_date})
    List<View> views;
    @BindViews({R.id.tv_delivery_note_no, R.id.tv_purchase_order,
            R.id.tv_material_number, R.id.tv_supplier, R.id.tv_date})
    List<TextView> textViews;
    @BindViews({R.id.et_delivery_note_no, R.id.et_purchase_order,
            R.id.et_material_number, R.id.et_supplier, R.id.et_date})
    List<EditText> editTexts;

    @BindView(R.id.btn_search_sure)
    Button btnSearchSure;

    @OnClick(R.id.btn_search_sure)
    void search() {
        mHandler.removeMessages(GETLISTWHAT);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(GETLISTWHAT), AddressContants.DELAYTIME);

    }


    String startDate = "";
    String endDate = "";

    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case GETLISTWHAT:
                    HashMap<String, String> map = new HashMap<>();
                    if(!StringUtils.isBlank(etDeliveryNoteNo.getText().toString().trim())){
                        map.put("delivery_bill_no",etDeliveryNoteNo.getText().toString().trim());
                    }
                    if(!StringUtils.isBlank(etPurchaseOrder.getText().toString().trim())){
                        map.put("receipt_no",etPurchaseOrder.getText().toString().trim());
                    }
                    if(!StringUtils.isBlank(etMaterialNumber.getText().toString().trim())){
                        map.put(AddressContants.BARCODE_NO,etMaterialNumber.getText().toString().trim());
                    }
                    if(!StringUtils.isBlank(etSupplier.getText().toString().trim())){
                        map.put("supplier_no",etSupplier.getText().toString().trim());
                    }
                    if(!StringUtils.isBlank(etDate.getText().toString().trim())){
                        map.put(AddressContants.DATEBEGIN,startDate);
                        map.put(AddressContants.DATEEND,endDate);
                    }
                    qcList.clear();
                    adapter = new IQCInspectScanAdapter(activity,qcList);
                    ryList.setAdapter(adapter);
                    showLoadingDialog();
                    logic.getIQCScanDatas(map, new IQCInspectLogic.GetScanListener() {
                        @Override
                        public void onSuccess(List<QCScanData> datas) {
                            llSearchDialog.setVisibility(View.GONE);
                            scrollview.setVisibility(View.VISIBLE);
                            qcList.clear();
                            qcList.addAll(datas);
                            adapter = new IQCInspectScanAdapter(activity, qcList);
                            ryList.setAdapter(adapter);
                            dismissLoadingDialog();
                            adapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View itemView, int position) {
                                    itemClick(qcList.get(position));
                                }
                            });
                        }

                        @Override
                        public void onFailed(String error) {
                            dismissLoadingDialog();
                            showFailedDialog(error);
                        }
                    });
                    break;
            }
            return false;
        }
    };

    public static final String DATA = "data";

    private final int REQUESTCODE = 1234;

    private void itemClick(QCScanData qcScanData){
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA,qcScanData);
        ActivityManagerUtils.startActivityBundleForResult(activity,IQCInspectItemActivity.class,bundle,REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE){
            mHandler.removeMessages(GETLISTWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(GETLISTWHAT), AddressContants.DELAYTIME);
        }
    }

    private Handler mHandler = new WeakRefHandler(mCallback);

    final int GETLISTWHAT = 1234;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    private IQCInspectLogic logic;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_iqcinspect_list;
    }

    @Override
    protected void initNavigationTitle() {
        mName.setText(getString(R.string.iqc_check_pad)+getString(R.string.list));
        ivScan.setVisibility(View.VISIBLE);
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.drawable.search);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void doBusiness() {
        logic = IQCInspectLogic.getInstance(activity, module, mTimestamp.toString());
        qcList = new ArrayList<>();
        adapter = new IQCInspectScanAdapter(activity, qcList);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(activity);
        ryList.setLayoutManager(manager);
        ryList.setAdapter(adapter);
        etDate.setKeyListener(null);
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.IQCINSPECT;
        return module;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mHandler.removeCallbacksAndMessages(null);
    }
}
