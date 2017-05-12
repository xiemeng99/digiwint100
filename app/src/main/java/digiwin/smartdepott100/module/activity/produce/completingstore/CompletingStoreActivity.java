package digiwin.smartdepott100.module.activity.produce.completingstore;

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
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author 赵浩然
 * @module 完工入库
 * @date 2017/3/13
 */

public class CompletingStoreActivity extends BaseFirstModuldeActivity{

    private CompletingStoreActivity activity;

    /**
     * 扫工单条码
     */
    private final int BARCODEWHAT = 1001;

    private final String STOCKINQTY = "stock_in_qty";

    CommonLogic commonLogic;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    /**
     * 料号
     */
    @BindView(R.id.tv_item_no)
    TextView tv_item_no;

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView tv_item_name;

    /**
     * 仓库
     */
    @BindView(R.id.tv_label_storage)
    TextView tv_label_storage;

    /**
     * 可入库量
     */
    @BindView(R.id.tv_storage_capacity)
    TextView tv_storage_capacity;

    /**
     * 工单条码
     */
    @BindView(R.id.tv_work_order_code)
    TextView tv_work_order_code;
    @BindView(R.id.et_work_order_code)
    EditText et_work_order_code;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;

    /**
     * 数量
     */
    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.et_input_num)
    EditText et_input_num;
    @BindView(R.id.ll_input_num)
    LinearLayout ll_input_num;

    @BindViews({R.id.et_work_order_code, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_work_order_code, R.id.tv_number})
    List<TextView> textViews;

    @BindView(R.id.commit)
    Button btn_Commit;

    @OnClick(R.id.commit)
    void commit(){
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();

                if(StringUtils.isBlank(et_work_order_code.getText().toString().trim())){
                    dismissLoadingDialog();
                    showFailedDialog(R.string.please_scan_job_number);
                    return;
                }

                if(StringUtils.isBlank(et_input_num.getText().toString().trim())){
                    dismissLoadingDialog();
                    showFailedDialog(R.string.input_num);
                    return;
                }
                float num = StringUtils.sub(et_input_num.getText().toString().trim(),tv_storage_capacity.getText().toString().trim());
                if(num > 0){
                    dismissLoadingDialog();
                    showFailedDialog(R.string.storage_capacity_large);
                    return;
                }
//                wo_no                string        工单号
//                item_no                string        料件编码
//                warehouse_no        string        仓库
//                qty            number(15,3)      数量
                Map<String,String> map = new HashMap<String, String>();
                map.put(AddressContants.WO_NO,et_work_order_code.getText().toString().trim());
                map.put(AddressContants.ITEM_NO,tv_item_no.getText().toString().trim());
                map.put(AddressContants.WAREHOUSE_NO,tv_label_storage.getText().toString().trim());
                map.put(STOCKINQTY,et_input_num.getText().toString().trim());

                List<ClickItemPutBean> checkedList = new ArrayList<ClickItemPutBean>();
                ClickItemPutBean data = new ClickItemPutBean();
                data.setWo_no(et_work_order_code.getText().toString().trim());
                data.setItem_no(tv_item_no.getText().toString().trim());
                data.setWarehouse_no(tv_label_storage.getText().toString().trim());
                data.setQty(et_input_num.getText().toString().trim());
                checkedList.add(data);
                final List<Map<String, String>> listMap = ObjectAndMapUtils.getListMap(checkedList);
                commonLogic.commitList(listMap, new CommonLogic.CommitListListener() {
                    @Override
                    public void onSuccess(String msg) {
                        dismissLoadingDialog();
                        createNewModuleId(module);
                        showCommitSuccessDialog(msg);
                        clearData();
                        commonLogic = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
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

    @OnFocusChange(R.id.et_work_order_code)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, et_work_order_code, editTexts);
        ModuleUtils.tvChange(activity, tv_work_order_code, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void locatorFocusChanage() {
        ModuleUtils.viewChange(ll_input_num, views);
        ModuleUtils.etChange(activity, et_input_num, editTexts);
        ModuleUtils.tvChange(activity, tv_number, textViews);
    }

    @OnTextChanged(value = R.id.et_input_num, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void num(CharSequence s) {
        if ((".").equals(s.toString())) {
            et_input_num.setText("");
        }
    }

    @OnTextChanged(value = R.id.et_work_order_code, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == BARCODEWHAT){
                ClickItemPutBean putBean = new ClickItemPutBean();
                putBean.setWo_no(String.valueOf(msg.obj));
                putBean.setWarehouse_no(LoginLogic.getUserInfo().getWare());

                commonLogic.getOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
                    @Override
                    public void onSuccess(List<ListSumBean> list) {
                        ListSumBean data = list.get(0);
                        tv_item_no.setText(data.getItem_no());
                        tv_item_name.setText(data.getItem_name());
                        tv_label_storage.setText(data.getWarehouse_no());
                        tv_storage_capacity.setText(StringUtils.deleteZero(data.getAvailable_in_qty()));
                        et_input_num.requestFocus();
                    }

                    @Override
                    public void onFailed(String error) {
                        showFailedDialog(error);
                        clearData();
                    }
                });
            }
            return false;
        }
    });

    @Override
    protected void doBusiness() {
        commonLogic = CommonLogic.getInstance(activity,activity.module,activity.mTimestamp.toString());
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.COMPLETINGSTORE;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(R.string.title_completing_store);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_completingstore;
    }

    public void clearData(){
        tv_item_no.setText("");
        tv_item_name.setText("");
        tv_label_storage.setText("");
        tv_storage_capacity.setText("");
        et_work_order_code.setText("");
        et_input_num.setText("");
        et_work_order_code.requestFocus();
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }
}
