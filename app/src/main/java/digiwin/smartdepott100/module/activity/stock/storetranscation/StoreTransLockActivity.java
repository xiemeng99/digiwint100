package digiwin.smartdepott100.module.activity.stock.storetranscation;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.constant.SharePreKey;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.library.voiceutils.VoiceUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.common.ScanLocatorBackBean;
import digiwin.smartdepott100.module.bean.stock.StoreTransItemNoBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.stock.storetranscation.StoreTransLogic;

/**
 * @author maoheng
 * @des 库存交易锁定
 * @date 2017/3/27
 */

public class StoreTransLockActivity extends BaseTitleActivity{
    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    /**
     * 作业编号
     */
    public String module;

    /**
     * 锁定仓库
     */
    @BindView(R.id.ll_lock_store)
    LinearLayout ll_lock_store;
    @BindView(R.id.tv_lock_store)
    TextView tv_lock_store;
    @BindView(R.id.et_lock_store)
    EditText et_lock_store;
    /**
     * 库位
     */
    @BindView(R.id.ll_loctor_no)
    LinearLayout ll_loctor_no;
    @BindView(R.id.tv_loctor_no)
    TextView tv_loctor_no;
    @BindView(R.id.et_loctor_no)
    EditText et_loctor_no;
    /**
     * 仓储条码
     */
    @BindView(R.id.ll_barcode_store)
    LinearLayout ll_barcode_store;
    @BindView(R.id.tv_barcode_store)
    TextView tv_barcode_store;
    @BindView(R.id.et_barcode_store)
    EditText et_barcode_store;
    /**
     * 物料条码
     */
    @BindView(R.id.ll_materiel_barcode)
    LinearLayout ll_materiel_barcode;
    @BindView(R.id.tv_materiel_barcode)
    TextView tv_materiel_barcode;
    @BindView(R.id.et_materiel_barcode)
    EditText et_materiel_barcode;
    /**
     * 料号
     */
    @BindView(R.id.ll_item_no)
    LinearLayout ll_item_no;
    @BindView(R.id.tv_item_no)
    TextView tv_item_no;
    @BindView(R.id.et_item_no)
    EditText et_item_no;
    /**
     * 批号
     */
    @BindView(R.id.ll_batch_no)
    LinearLayout ll_batch_no;
    @BindView(R.id.tv_batch_no)
    TextView tv_batch_no;
    @BindView(R.id.et_batch_no)
    EditText et_batch_no;
    /**
     * 锁定原因
     */
    @BindView(R.id.ll_lock_reason)
    LinearLayout ll_lock_reason;
    @BindView(R.id.tv_lock_reason)
    TextView tv_lock_reason;
    @BindView(R.id.et_lock_reason)
    EditText et_lock_reason;
    @BindView(R.id.lock_reason)
    ImageView lock_reason;

    /**
     * 语音点击
     */
    @OnClick(R.id.lock_reason)
    void getReasonText(){
        et_lock_reason.requestFocus();
        String voicer = (String) SharedPreferencesUtils.get(this, SharePreKey.VOICER_SELECTED,"voicer");
        VoiceUtils.getInstance(this,voicer).voiceToText(new VoiceUtils.GetVoiceTextListener() {
            @Override
            public String getVoiceText(String str) {
                if(!"。".equals(str)){
                    et_lock_reason.setText(str);
                }
                return null;
            }
        });
    }
    /**
     * 品名
     */
    @BindView(R.id.tv_name_value)
    TextView tv_name_value;
    /**
     * 规格
     */
    @BindView(R.id.tv_spc_value)
    TextView tv_spc_value;


    @BindViews({R.id.et_lock_store, R.id.et_loctor_no, R.id.et_barcode_store, R.id.et_materiel_barcode, R.id.et_item_no, R.id.et_batch_no, R.id.et_lock_reason})
    List<EditText> editTexts;
    @BindViews({R.id.ll_lock_store, R.id.ll_loctor_no, R.id.ll_barcode_store, R.id.ll_materiel_barcode, R.id.ll_item_no, R.id.ll_batch_no, R.id.ll_lock_reason})
    List<View> views;
    @BindViews({R.id.tv_lock_store, R.id.tv_loctor_no, R.id.tv_barcode_store, R.id.tv_materiel_barcode, R.id.tv_item_no, R.id.tv_batch_no, R.id.tv_lock_reason})
    List<TextView> textViews;

    @OnFocusChange(R.id.et_lock_store)
    void lockStoreFocusChange() {
        ModuleUtils.viewChange(ll_lock_store, views);
        ModuleUtils.etChange(activity, et_lock_store, editTexts);
        ModuleUtils.tvChange(activity, tv_lock_store, textViews);
    }
    @OnFocusChange(R.id.et_loctor_no)
    void loctorNoFocusChange() {
        ModuleUtils.viewChange(ll_loctor_no, views);
        ModuleUtils.etChange(activity, et_loctor_no, editTexts);
        ModuleUtils.tvChange(activity, tv_loctor_no, textViews);
    }
    @OnFocusChange(R.id.et_barcode_store)
    void barcodeStoreFocusChange() {
        ModuleUtils.viewChange(ll_barcode_store, views);
        ModuleUtils.etChange(activity, et_barcode_store, editTexts);
        ModuleUtils.tvChange(activity, tv_barcode_store, textViews);
    }
    @OnFocusChange(R.id.et_materiel_barcode)
    void barcodeMaterielFocusChange() {
        ModuleUtils.viewChange(ll_materiel_barcode, views);
        ModuleUtils.etChange(activity, et_materiel_barcode, editTexts);
        ModuleUtils.tvChange(activity, tv_materiel_barcode, textViews);
    }
    @OnFocusChange(R.id.et_item_no)
    void itemFocusChange() {
        ModuleUtils.viewChange(ll_item_no, views);
        ModuleUtils.etChange(activity, et_item_no, editTexts);
        ModuleUtils.tvChange(activity, tv_item_no, textViews);
    }
    @OnFocusChange(R.id.et_batch_no)
    void batchFocusChange() {
        ModuleUtils.viewChange(ll_batch_no, views);
        ModuleUtils.etChange(activity, et_batch_no, editTexts);
        ModuleUtils.tvChange(activity, tv_batch_no, textViews);
    }
    @OnFocusChange(R.id.et_lock_reason)
    void reasonFocusChange() {
        ModuleUtils.viewChange(ll_lock_reason, views);
        ModuleUtils.etChange(activity, et_lock_reason, editTexts);
        ModuleUtils.tvChange(activity, tv_lock_reason, textViews);
    }

    /**
     * 料号条码
     */
    private final int ITEMWHAT = 100;
    /**
     * 扫工单条码
     */
    private final int BARCODEWHAT = 1001;
    /**
     * 扫仓储条码
     */
    private final int WAREHOUSEWHAT = 1002;

    private StoreTransLogic transLogic;

    /**
     * Handler
     */
    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == ITEMWHAT){
                HashMap<String,String> map = new HashMap<>();
                String str = (String) msg.obj;
                map.put(AddressContants.ITEM_NO,str);
                transLogic.getScanBarcode(map, new StoreTransLogic.ScanItemNoListener() {
                    @Override
                    public void onSuccess(StoreTransItemNoBean transItemNoBean) {
                        if(null!=transItemNoBean){
                            tv_name_value.setText(transItemNoBean.getItem_name());
                            tv_spc_value.setText(transItemNoBean.getItem_spec());
                        }else{
                            showFailedDialog(R.string.data_null);
                        }
                    }

                    @Override
                    public void onFailed(String error) {
                        showFailedDialog(error);
                        et_item_no.setText("");
                    }
                });
            }
            if(msg.what == BARCODEWHAT){
                HashMap<String,String> map = new HashMap<>();
                String str = (String) msg.obj;
                map.put(AddressContants.BARCODE_NO,str);
                commonLogic.scanBarcode(map, new CommonLogic.ScanBarcodeListener() {
                    @Override
                    public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                        if(null!=barcodeBackBean){
                            et_item_no.setText(barcodeBackBean.getItem_no());
                            et_batch_no.setText(barcodeBackBean.getLot_no());
                        }
                    }

                    @Override
                    public void onFailed(String error) {
                        showFailedDialog(error);
                        et_materiel_barcode.setText("");
                    }
                });
            }
            if(msg.what == WAREHOUSEWHAT){
                HashMap<String,String> map = new HashMap<>();
                String str = (String) msg.obj;
                map.put(AddressContants.STORAGE_SPACES_BARCODE,str);
                commonLogic.scanLocator(map, new CommonLogic.ScanLocatorListener() {
                    @Override
                    public void onSuccess(ScanLocatorBackBean locatorBackBean) {
                        if(null!=locatorBackBean){
                            et_loctor_no.setText(locatorBackBean.getStorage_spaces_no());
                            et_lock_store.setText(locatorBackBean.getWarehouse_no());
                        }
                    }

                    @Override
                    public void onFailed(String error) {
                        showFailedDialog(error);
                        et_barcode_store.setText("");
                    }
                });
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @OnTextChanged(value = R.id.et_materiel_barcode, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_barcode_store, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void warehouseChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(WAREHOUSEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(WAREHOUSEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
    @OnTextChanged(value = R.id.et_item_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void itemChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(ITEMWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(ITEMWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }
    private CommonLogic commonLogic;
    @OnClick(R.id.commit)
    void commit() {
        //提交判断条件(锁定仓库不能为空,其余除锁定原因至少有一个不能为空)
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                if(StringUtils.isBlank(et_lock_store.getText().toString().trim())){
                    dismissLoadingDialog();
                    showFailedDialog(R.string.store_failed_be_empty);
                    return;
                }
                if(StringUtils.isBlank(et_lock_store.getText().toString().trim())&&
                        StringUtils.isBlank(et_barcode_store.getText().toString().trim())&&
                        StringUtils.isBlank(et_batch_no.getText().toString().trim())&&
                        StringUtils.isBlank(et_item_no.getText().toString().trim())&&
                        StringUtils.isBlank(et_materiel_barcode.getText().toString().trim())
                        ){
                    dismissLoadingDialog();
                    showFailedDialog(R.string.lock_failed_other_empty);
                    return;
                }
                HashMap<String,String> map = new HashMap<String, String>();
                map.put(AddressContants.WAREHOUSE_NO,et_lock_store.getText().toString().trim());
                map.put(AddressContants.WAREHOUSE_STORAGE,et_loctor_no.getText().toString().trim());
                map.put(AddressContants.ITEM_NO,et_item_no.getText().toString().trim());
                map.put(AddressContants.ITEMLOTNO,et_batch_no.getText().toString().trim());
//                map.put(AddressContants.EMPLOYEENO,"");
//                map.put(AddressContants.RECEIPTDATE,"");
                map.put(AddressContants.LOCKREASON,et_lock_reason.getText().toString().trim());
                commonLogic.commit(map, new CommonLogic.CommitListener() {
                    @Override
                    public void onSuccess(String msg) {
                        dismissLoadingDialog();
                        showCommitSuccessDialog(msg);
                        clear();
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showCommitFailDialog(error);
                    }
                });
            }
            @Override
            public void onCallback2() {

            }
        });

    }

    /**
     * 清除信息
     */
    public void clear(){
        et_batch_no.setText("");
        et_item_no.setText("");
        et_lock_reason.setText("");
        et_materiel_barcode.setText("");
        et_lock_store.setText("");
        et_barcode_store.setText("");
        et_loctor_no.setText("");
        tv_name_value.setText("");
        tv_spc_value.setText("");
        et_lock_store.requestFocus();
    }
    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module= ModuleCode.STORETRANSLOCK;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_storetranslock_list;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.store_trans_lock);
//        ivScan.setVisibility(View.GONE);
    }

    @Override
    protected void doBusiness() {
        transLogic = StoreTransLogic.getInstance(activity,module,mTimestamp.toString());
        commonLogic = CommonLogic.getInstance(activity,module,mTimestamp.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
