package digiwin.smartdepott100.module.fragment.produce.wipstorage;

import android.os.Handler;
import android.os.Message;
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
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.produce.finishstorage.WipStorageActivity;
import digiwin.smartdepott100.module.bean.common.SaveBackBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.common.ScanPlotNoBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.WipCompleteLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;

/**
 * @author xiemeng
 * @des 完工入库扫描
 * @date 2017/5/25 10:21
 */

public class WipStorageScanFg extends BaseFragment {

    @BindView(R.id.tv_plot_no)
    TextView tvPlotNo;
    @BindView(R.id.et_plot_no)
    EditText etPlotNo;
    @BindView(R.id.ll_plot_no)
    LinearLayout llPlotNo;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_input_num)
    EditText etInputNum;
    @BindView(R.id.ll_input_num)
    LinearLayout llInputNum;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;
    @BindView(R.id.tv_detail_show)
    TextView tvDetailShow;
    @BindView(R.id.includedetail)
    View includeDetail;

    @BindView(R.id.tv_scan_hasScan)
    TextView tvScanHasScan;

    @BindViews({R.id.et_plot_no, R.id.et_input_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_plot_no, R.id.ll_input_num})
    List<View> views;
    @BindViews({R.id.tv_plot_no, R.id.tv_number})
    List<TextView> textViews;


    @OnFocusChange(R.id.et_plot_no)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(llPlotNo, views);
        ModuleUtils.etChange(activity, etPlotNo, editTexts);
        ModuleUtils.tvChange(activity, tvPlotNo, textViews);
    }

    @OnFocusChange(R.id.et_input_num)
    void numFocusChanage() {
        ModuleUtils.viewChange(llInputNum, views);
        ModuleUtils.etChange(activity, etInputNum, editTexts);
        ModuleUtils.tvChange(activity, tvNumber, textViews);
    }

    @OnTextChanged(value = R.id.et_plot_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void plotNoChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(PLOTNOWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(PLOTNOWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }


    @OnClick(R.id.save)
    void save() {
        if (!plotNoFlag) {
            showFailedDialog(R.string.scan_circulation);
            return;
        }
        if (StringUtils.isBlank(etInputNum.getText().toString())) {
            showFailedDialog(R.string.input_num);
            return;
        }
        showLoadingDialog();
        saveBean.setWarehouse_in_no(LoginLogic.getWare());
        saveBean.setQty(etInputNum.getText().toString());
        commonLogic.scanSave(saveBean, new CommonLogic.SaveListener() {
            @Override
            public void onSuccess(SaveBackBean saveBackBean) {
                tvScanHasScan.setText(saveBackBean.getScan_sumqty());
                dismissLoadingDialog();
                clear();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    /**
     * 物料条码
     */
    final int PLOTNOWHAT = 1001;

    WipStorageActivity pactivity;


    WipCompleteLogic commonLogic;
    /**
     * 条码展示
     */
    String barcodeShow;
    /**
     * 条码扫描
     */
    boolean plotNoFlag;

    SaveBean saveBean;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PLOTNOWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.PLOTNO, String.valueOf(msg.obj));
                    commonLogic.scanPlotNo(barcodeMap, new CommonLogic.ScanPoltNoListener() {
                        @Override
                        public void onSuccess(ScanPlotNoBackBean barcodeBackBean) {
                            barcodeShow = barcodeBackBean.getShowing();
                            etInputNum.setText(StringUtils.deleteZero(barcodeBackBean.getAvailable_in_qty()));
                            tvScanHasScan.setText(barcodeBackBean.getScan_sumqty());
                            plotNoFlag = true;
                            show();
                            saveBean.setBarcode_no(etPlotNo.getText().toString());
                            saveBean.setAvailable_in_qty(barcodeBackBean.getAvailable_in_qty());
                            saveBean.setItem_no(barcodeBackBean.getItem_no());
                            saveBean.setUnit_no(barcodeBackBean.getUnit_no());
                            saveBean.setWo_no(barcodeBackBean.getWo_no());
                            etInputNum.requestFocus();
                        }

                        @Override
                        public void onFailed(String error) {
                            plotNoFlag =false;
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etPlotNo.setText("");
                                }
                            });
                        }
                    });
                    break;
            }
            return false;
        }
    });

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_wipcomplete_scan;
    }

    @Override
    protected void doBusiness() {
        pactivity = (WipStorageActivity) activity;
        initData();
    }


    /**
     * 公共区域展示
     */
    private void show() {
        tvDetailShow.setText(StringUtils.lineChange(barcodeShow));
        if (!StringUtils.isBlank(tvDetailShow.getText().toString())) {
            includeDetail.setVisibility(View.VISIBLE);
        } else {
            includeDetail.setVisibility(View.GONE);
        }
    }


    /**
     * 保存完成之后的操作
     */
    private void clear() {
        etInputNum.setText("");
        plotNoFlag = false;
        etPlotNo.setText("");
        barcodeShow = "";
        etPlotNo.requestFocus();
        show();
    }

    /**
     * 初始化一些变量
     */
    public void initData() {
        barcodeShow = "";
        plotNoFlag = false;
        saveBean = new SaveBean();
        commonLogic = WipCompleteLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
    }

}
