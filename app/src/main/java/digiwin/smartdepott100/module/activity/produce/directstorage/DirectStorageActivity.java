package digiwin.smartdepott100.module.activity.produce.directstorage;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.method.TextKeyListener;
import android.view.View;
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
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.main.logic.GetStorageLogic;
import digiwin.smartdepott100.module.activity.common.WareHouseDialog;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.DirectStorageLogic;

/**
 * @author 孙长权
 * @des 直接入库--快速完工
 */
public class DirectStorageActivity extends BaseFirstModuldeActivity {
    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    //数量
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    //工单单号
    @BindView(R.id.tv_gongDan_no)
    TextView tvWorkOrder;
    @BindView(R.id.et_gongDan)
    EditText etWorkOrder;
    @BindView(R.id.ll_gongDan_no)
    LinearLayout llWorkOrder;
    @BindView(R.id.tv_warehouse)
    TextView tvWarehouse;

    @BindView(R.id.includedetail)
    View includeDetail;

    /**
     * 工单号
     */
    final int WORKORDER = 1002;

    DirectStorageLogic logic;

    /**
     * 工单号
     */
    String workOrderShow;
    /**
     * 工单号
     */
    boolean workOrderFlag;

    SaveBean saveBean;

    @BindViews({ R.id.et_gongDan,  R.id.et_input_num,})
    List<EditText> editTexts;
    @BindViews({ R.id.ll_gongDan_no, R.id.ll_input_num})
    List<View> views;
    @BindViews({ R.id.tv_gongDan_no,  R.id.tv_number})
    List<TextView> textViews;

    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;


    @OnFocusChange(R.id.et_gongDan)
    void workOrderFocusChanage() {
        ModuleUtils.viewChange(llWorkOrder, views);
        ModuleUtils.etChange(activity, etWorkOrder, editTexts);
        ModuleUtils.tvChange(activity, tvWorkOrder, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    /**
     * 工单号扫描
     */
    @OnTextChanged(value = R.id.et_gongDan, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void workOrderNumberChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(WORKORDER);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(WORKORDER, s.toString()), AddressContants.DELAYTIME);
        }
    }
    @OnClick(R.id.tv_warehouse)
    void chooseWare(){
        String text = tvWarehouse.getText().toString();
        List<String> ware = GetStorageLogic.getWareString();
        WareHouseDialog.showWareHouseDialog(activity,text,ware);
    }


    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.DIRECTSTORAGE;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.direct_storage);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_direct_storage;
    }

    @Override
    protected void doBusiness() {
        logic = DirectStorageLogic.getInstance(context, module, mTimestamp.toString());
        initData();
        WareHouseDialog.setCallBack(new WareHouseDialog.WareHouseCallBack() {
            @Override
            public void wareHouseCallBack(String wareHouse) {
                tvWarehouse.setText(wareHouse);
            }
        });

    }

    @OnClick(R.id.commit)
    void commit() {
        if (!workOrderFlag) {
            showFailedDialog(R.string.scan_work_order);
            return;
        }
        if (StringUtils.isBlank(tvWarehouse.getText().toString())) {
            showFailedDialog(R.string.store_failed_be_empty);
            return;
        }
        if (StringUtils.isBlank(etInputNum.getText().toString().trim())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        saveBean.setWarehouse_no(tvWarehouse.getText().toString());
        saveBean.setReq_qty(etInputNum.getText().toString().trim());
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                Map<String, String> map = ObjectAndMapUtils.getValueMap(saveBean);
                logic.directStorageCommit(map, new DirectStorageLogic.DirectStorageCommitListener() {
                    @Override
                    public void onSuccess(String msg) {
                        dismissLoadingDialog();
                        showCommitSuccessDialog(msg, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                clear();
                            }
                        });
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

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case WORKORDER:
                    Map<String,String> map = new HashMap<>();
                    saveBean.setWo_no(StringUtils.objToString(msg.obj));
                    map.put(AddressContants.WO_NO,StringUtils.objToString(msg.obj));
                    etWorkOrder.setKeyListener(null);
                    logic.scanOrder(map, new DirectStorageLogic.ScanOrderListener() {
                        @Override
                        public void onSuccess(ListSumBean sumBean) {
                            etWorkOrder.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            workOrderShow = sumBean.getShowing();
                            workOrderFlag = true;
                            show();
                            tvWarehouse.setText(sumBean.getWarehouse_no());
                            etInputNum.setText(StringUtils.deleteZero(sumBean.getQty()));
                            etInputNum.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            etWorkOrder.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.CHARACTERS, true));
                            workOrderFlag = false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etWorkOrder.setText("");
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

    /**
     * 提交完成之后的操作
     */
    private void clear() {
        workOrderShow = "";
        saveBean = new SaveBean();
        etInputNum.setText("");
        etWorkOrder.setText("");
        tvWarehouse.setText("");
        etWorkOrder.requestFocus();
        workOrderFlag=false;
        show();
    }

    /**
     * 初始化一些变量
     */
    private void initData() {
        workOrderShow = "";
        workOrderFlag = false;
        saveBean = new SaveBean();
    }

    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(workOrderShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            includeDetail.setVisibility(View.VISIBLE);
        } else {
            includeDetail.setVisibility(View.GONE);
        }
    }


    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
