package digiwin.smartdepott100.module.activity.stock.printlabel;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.core.printer.BlueToothManager;
import digiwin.smartdepott100.main.logic.ToSettingLogic;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.bean.stock.PrintBarcodeBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * 标签补打
 * @author wangyu
 */

public class PrintLabelFillActivity extends BaseTitleActivity {

    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    /**
     * 来源条码
     */
    @BindView(R.id.et_resource_barcode)
    EditText et_resource_barcode;
    /**
     * 总数量
     */
    @BindView(R.id.et_sum_num)
    EditText et_sum_num;
    /**
     * 单包数量
     */
    @BindView(R.id.et_single_package_num)
    EditText et_single_package_num;

    /**
     * 来源条码
     */
    @BindView(R.id.tv_resource_barcode)
    TextView tv_resource_barcode;
    /**
     * 总数量
     */
    @BindView(R.id.tv_sum_num)
    TextView tv_sum_num;
    /**
     * 单包数量
     */
    @BindView(R.id.tv_single_package_num)
    TextView tv_single_package_num;
    /**
     * 来源条码
     */
    @BindView(R.id.ll_resource_barcode)
    LinearLayout ll_resource_barcode;
    /**
     * 总数量
     */
    @BindView(R.id.ll_sum_num)
    LinearLayout ll_sum_num;
    /**
     * 单包数量
     */
    @BindView(R.id.ll_single_package_num)
    LinearLayout ll_single_package_num;

    @BindViews({R.id.et_resource_barcode,R.id.et_sum_num,R.id.et_single_package_num})
    List<EditText> editTexts;
    @BindViews({R.id.tv_resource_barcode,R.id.tv_sum_num,R.id.tv_single_package_num})
    List<TextView> textViews;
    @BindViews({R.id.ll_resource_barcode,R.id.ll_sum_num,R.id.ll_single_package_num})
    List<View> views;

    /**
     * 焦点颜色变化
     */
    @OnFocusChange(R.id.et_single_package_num)
    void singlePackageFocusChanage() {
        ModuleUtils.viewChange(ll_single_package_num, views);
        ModuleUtils.etChange(activity, et_single_package_num, editTexts);
        ModuleUtils.tvChange(activity, tv_single_package_num, textViews);
    }
    @OnFocusChange(R.id.et_sum_num)
    void sumNumFocusChanage() {
        ModuleUtils.viewChange(ll_sum_num, views);
        ModuleUtils.etChange(activity, et_sum_num, editTexts);
        ModuleUtils.tvChange(activity, tv_sum_num, textViews);
    }
    @OnFocusChange(R.id.et_resource_barcode)
    void barcodeFocusChanage() {
        ModuleUtils.viewChange(ll_resource_barcode, views);
        ModuleUtils.etChange(activity, et_resource_barcode, editTexts);
        ModuleUtils.tvChange(activity, tv_resource_barcode, textViews);
    }

    /**
     * 扫描条码
     */
    final int BARCODEWHAT = 1234;
    /**
     * 更新打印张数
     */
    final int UPDATENUM = 1235;

    /**
     * 条码 编辑框
     * @param s
     */
    @OnTextChanged(value = R.id.et_resource_barcode, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString().trim())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString().trim()), AddressContants.DELAYTIME);
        }
    }

    /**
     * 总数量 编辑框
     * @param s
     */
    @OnTextChanged(value = R.id.et_sum_num, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void sumNumChange(CharSequence s) {
        boolean isFlag=judgeNum();
        if (isFlag){
            mHandler.removeMessages(UPDATENUM);
            Message msg = new Message();
            msg.what = UPDATENUM;
            mHandler.sendMessageDelayed(msg,AddressContants.DELAYTIME);
        }else  {
            tv_num_of_print_pieces.setText("");
        }
    }

    /**
     * 单包数量 编辑框
     * @param s
     */
    @OnTextChanged(value = R.id.et_single_package_num, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void singlePackageChange(CharSequence s) {
        boolean isFlag=judgeNum();
        if (isFlag){
            Message msg = new Message();
            msg.what = UPDATENUM;
            mHandler.sendMessageDelayed(msg,AddressContants.DELAYTIME);
        }else if(!StringUtils.isBlank(et_sum_num.getText().toString().trim())) {
            tv_num_of_print_pieces.setText("");
            if (!StringUtils.isBlank(et_single_package_num.getText().toString().trim())){
                judgeSingleNum();
            }
        }
    }

    /**
     * 总数量和单包数量均合理
     * @return
     */
    boolean judgeNum(){
        if (!StringUtils.isBlank(et_sum_num.getText().toString().trim())){
            if (!StringUtils.isBlank(et_single_package_num.getText().toString().trim())){
                int sum_num = (int)StringUtils.string2Float(et_sum_num.getText().toString());
                int single_num = (int)StringUtils.string2Float(et_single_package_num.getText().toString());
                if(sum_num >=single_num && single_num!=0){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 对总数量和单包数量进行判断
     */
    void judgeSingleNum(){
                tv_num_of_print_pieces.setText("");
                int sum_num = (int) StringUtils.string2Float(et_sum_num.getText().toString());
                int single_num = (int) StringUtils.string2Float(et_single_package_num.getText().toString());
                if (single_num > sum_num) {
                    showFailedDialog(R.string.single_package_lager, new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                            et_single_package_num.setText("");
                            et_single_package_num.requestFocus();
                            tv_num_of_print_pieces.setText("");
                        }
                    });
                } else if (single_num == 0) {
                    showFailedDialog(R.string.single_no_zero, new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                            et_single_package_num.setText("");
                            et_single_package_num.requestFocus();
                            tv_num_of_print_pieces.setText("");
                        }
                    });
                }
    }

    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView tv_item_name;
    /**
     * 规格
     */
    @BindView(R.id.tv_model)
    TextView tv_model;
    /**
     * 料号
     */
    @BindView(R.id.tv_item_no)
    TextView tv_item_no;
    /**
     * 条码类型
     */
    @BindView(R.id.tv_barcode_type)
    TextView tv_barcode_type;
    /**
     * 打印张数
     */
    @BindView(R.id.tv_num_of_print_pieces)
    TextView tv_num_of_print_pieces;

    CommonLogic logic;

    String [] barcodeType = new String[]{"1:物料级","2：批次级","3：单箱级","4:单件级"};
    PrintBarcodeBean printBarcodeBean;
    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.LABLEPRINTING;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.print_label);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_print_label_fill;
    }

    @Override
    protected void doBusiness() {
        logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
        initData();
    }

    public void initData(){
        et_resource_barcode.requestFocus();
        et_resource_barcode.setText("");
        tv_item_name.setText("");
        tv_model.setText("");
        tv_item_no.setText("");
        tv_barcode_type.setText("");
        et_sum_num.setText("");
        et_single_package_num.setText("");
        tv_num_of_print_pieces.setText("");
        et_single_package_num.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch(msg.what){
                case BARCODEWHAT:
                    Map<String,String> map = new HashMap<>();
                    final String barcode = msg.obj.toString().trim();
                    map.put(AddressContants.BARCODE_NO,msg.obj.toString());
                    logic.scanBarcode(map, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            tv_item_name.setText(barcodeBackBean.getItem_name());
                            tv_model.setText(barcodeBackBean.getItem_spec());
                            tv_item_no.setText(barcodeBackBean.getItem_no());
                            String type = barcodeBackBean.getItem_barcode_type();
                            try{
                                if(null != type){
                                    tv_barcode_type.setText(barcodeType[StringUtils.parseInt(type) -1]);
                                }
                            }catch (Exception e){
                            }
                            et_sum_num.setText(barcodeBackBean.getBarcode_qty());
                            if (printBarcodeBean==null){
                                printBarcodeBean = new PrintBarcodeBean();
                            }
                            printBarcodeBean.setBarcode(barcode);
                            printBarcodeBean.setItem_name(barcodeBackBean.getItem_name());
                            printBarcodeBean.setItem_no(barcodeBackBean.getItem_no());
                            printBarcodeBean.setItem_spec(barcodeBackBean.getItem_spec());
                            printBarcodeBean.setBarcode_type(barcodeBackBean.getItem_barcode_type());
                            printBarcodeBean.setSum_qty(barcodeBackBean.getBarcode_qty());
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    et_resource_barcode.setText("");
                                    et_resource_barcode.requestFocus();
                                }
                            });
                        }
                    });
                    break;
                case UPDATENUM:
                    int sum_num = (int)StringUtils.string2Float(et_sum_num.getText().toString());
                    int single_num = (int)StringUtils.string2Float(et_single_package_num.getText().toString());
                    int num = StringUtils.div(sum_num,single_num);
                    tv_num_of_print_pieces.setText(String.valueOf(num));
                    if (printBarcodeBean==null){
                        printBarcodeBean = new PrintBarcodeBean();
                    }
                    printBarcodeBean.setQty(String.valueOf(single_num));
                    printBarcodeBean.setPrint_num(String.valueOf(num));
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    /**
     * 打印
     */
    @OnClick(R.id.btn_print)
    void print(){
//        if(StringUtils.isBlank(et_resource_barcode.getText().toString())){
//            showFailedDialog(R.string.please_scan_barcode_first);
//            return;
//        }
//        if(StringUtils.isBlank(et_single_package_num.getText().toString())){
//            showFailedDialog(R.string.input_num);
//            return;
//        }
        boolean isOpen = BlueToothManager.getManager(activity).isOpen();
        if (!isOpen){
            ToSettingLogic.showToSetdialog(activity,R.string.title_set_bluttooth);
            return;
        }else {
//            BlueToothManager.getManager(activity).printMaterialCode(printBarcodeBean,StringUtils.parseInt(printBarcodeBean.getSum_qty()));
            BlueToothManager.getManager(activity).printSmartLable(new PrintBarcodeBean(),10);
        }
        printBarcodeBean = new PrintBarcodeBean();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
