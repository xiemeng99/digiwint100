package digiwin.smartdepott100.module.activity.purchase.materialreceipt;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
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
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.adapter.purchase.MaterialReceiptAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.QuickReceiptLogic;

import static android.R.attr.keyHeight;


/**
 * @author 赵浩然
 * @module 快速收货
 * @date 2017/3/8
 */

public class MaterialReceiptActivity extends BaseFirstModuldeActivity implements
        View.OnLayoutChangeListener {

    private MaterialReceiptActivity activity;

    /**
     * 扫描入库单
     */
    public final int DELIVERY_NOTE_NO = 1003;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    MaterialReceiptAdapter adapter;

    /**
     * 送货单号
     */
    @BindView(R.id.tv_delivery_note_no)
    TextView tv_delivery_note_no;

    /**
     * 送货日期
     */
    @BindView(R.id.tv_delivery_date)
    TextView tv_delivery_date;

    /**
     * 供应商
     */
    @BindView(R.id.tv_supplier)
    TextView tv_supplier;

    @BindView(R.id.ry_list)
    RecyclerView ry_list;

    /**
     * 隐藏的布局
     */
    @BindView(R.id.rl_top)
    LinearLayout rl_top;

    QuickReceiptLogic commonLogic;

    /**
     * 送货单
     */
    @BindView(R.id.et_delivery_note)
    EditText et_delivery_note;
    @BindView(R.id.tv_delivery_note)
    TextView tv_delivery_note;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanLocator;

    @BindViews({R.id.et_delivery_note})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode})
    List<View> views;
    @BindViews({R.id.tv_delivery_note})
    List<TextView> textViews;

    @OnFocusChange(R.id.et_delivery_note)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llScanLocator, views);
        ModuleUtils.etChange(activity, et_delivery_note, editTexts);
        ModuleUtils.tvChange(activity, tv_delivery_note, textViews);
    }

    /**
     * 提交
     */
    @BindView(R.id.commit)
    Button commit;

    @OnClick(R.id.commit)
    void commit() {
        final List<ListSumBean> checkedList = adapter.getCheckData();
        if (checkedList.size() > 0) {
            showCommitSureDialog(new OnDialogTwoListener() {
                @Override
                public void onCallback1() {
                    showLoadingDialog();
                    if (StringUtils.isBlank(et_delivery_note.getText().toString())) {
                        dismissLoadingDialog();
                        showFailedDialog(getResources().getString(R.string.please_delivery_note));
                        return;
                    }
                    commitData(checkedList);
                }

                @Override
                public void onCallback2() {

                }
            });
        } else {
            showFailedDialog(getResources().getString(R.string.please_delivery_note_num));
        }
    }

    @OnTextChanged(value = R.id.et_delivery_note, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(DELIVERY_NOTE_NO);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(DELIVERY_NOTE_NO, s.toString().trim()),
                    AddressContants.DELAYTIME);
        }
    }

    private Handler.Callback mCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == DELIVERY_NOTE_NO) {
                ClickItemPutBean putBean = new ClickItemPutBean();
                putBean.setDoc_no(String.valueOf(msg.obj));
                putBean.setWarehouse_no(LoginLogic.getWare());
                Map<String, String> map = ObjectAndMapUtils.getValueMap(putBean);
                et_delivery_note.setKeyListener(null);
                commonLogic.getMRSumData(map, new CommonLogic.GetOrderSumListener() {
                    @Override
                    public void onSuccess(List<ListSumBean> list) {
                        et_delivery_note.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                        for (int i = 0; i < list.size(); i++) {
                            ListSumBean bean = list.get(i);
                            bean.setCheck("1");
                        }
                        rl_top.setVisibility(View.VISIBLE);
                        ListSumBean bean = list.get(0);
                        tv_delivery_note_no.setText(bean.getDoc_no());
                        tv_delivery_date.setText(bean.getCreate_date());
                        tv_supplier.setText(bean.getSupplier_name());
                        adapter = new MaterialReceiptAdapter(activity, list);
                        ry_list.setAdapter(adapter);
                        dismissLoadingDialog();
                    }

                    @Override
                    public void onFailed(String error) {
                        et_delivery_note.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                        dismissLoadingDialog();
                        showFailedDialog(error, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                clearData();
                            }
                        });
                    }
                });
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    protected void doBusiness() {

        commonLogic = QuickReceiptLogic
                .getInstance(activity, activity.module, activity.mTimestamp.toString());
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(activity);
        ry_list.setLayoutManager(fullyLinearLayoutManager);
        ArrayList<ListSumBean> list = new ArrayList<ListSumBean>();
        adapter = new MaterialReceiptAdapter(activity, list);
        ry_list.setAdapter(adapter);
    }

    public void clearData() {
        tv_delivery_note_no.setText("");
        tv_delivery_date.setText("");
        tv_supplier.setText("");
        et_delivery_note.setText("");
        et_delivery_note.requestFocus();
        ArrayList<ListSumBean> list = new ArrayList<ListSumBean>();
        adapter = new MaterialReceiptAdapter(activity, list);
        ry_list.setAdapter(adapter);
        rl_top.setVisibility(View.GONE);
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.MATERIALRECEIPTCODE;
        return module;
    }

    public void commitData(final List<ListSumBean> checkedList) {
        for (int i=0;i<checkedList.size();i++){
            ListSumBean sumBean = checkedList.get(i);
            float sub = StringUtils.sub(sumBean.getApply_qty(), sumBean.getScan_sumqty());
            if (sub<0){
                showFailedDialog(sumBean.getItem_no()+getString(R.string.scan_big_applynum));
                return;
            }
        }
        final List<Map<String, String>> listMap = ObjectAndMapUtils.getListMap(checkedList);
        Map<String, Object> map = new HashMap<>();
        map.put("data", listMap);
        commonLogic.commitMRData(map, new CommonLogic.CommitListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        createNewModuleId(module);
                        clearData();
                        commonLogic = QuickReceiptLogic
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
    protected int bindLayoutId() {
        return R.layout.activity_material_receipt;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(getResources().getString(R.string.title_material_receipt));
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                               int oldTop, int oldRight, int oldBottom) {
        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            commit.setVisibility(View.INVISIBLE);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            commit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }
}
