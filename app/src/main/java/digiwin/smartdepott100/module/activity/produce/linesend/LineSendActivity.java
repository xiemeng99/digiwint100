package digiwin.smartdepott100.module.activity.produce.linesend;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import digiwin.library.utils.WeakRefHandler;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;

/**
 * @author xiemeng
 * @des 线边发料
 * @date 2017/4/20 14:59
 */

public class LineSendActivity extends BaseTitleActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.et_item_no)
    EditText etItemNo;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_produce_num)
    TextView tvProduceNum;
    @BindView(R.id.tv_locator_num)
    TextView tvLocatorNum;
    @BindView(R.id.tv_order_max_num)
    TextView tvOrderMaxNum;
    @BindView(R.id.tv_line_max_num)
    TextView tvLineMaxNum;
    @BindView(R.id.et_current_num)
    EditText etCurrentNum;

    @OnTextChanged(value = R.id.et_item_no, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void barcodeChange(final CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            Map<String, String> map = new HashMap<>();
            map.put(AddressContants.FLAG, BaseFirstModuldeActivity.ExitMode.EXITD.getName());
            commonLogic.exit(map, new CommonLogic.ExitListener() {
                @Override
                public void onSuccess(String msg) {
                    mHandler.removeMessages(ITEMWHAT);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(ITEMWHAT, s.toString().trim()), AddressContants.DELAYTIME);
                }

                @Override
                public void onFailed(String error) {
                    showFailedDialog(error);
                }
            });
        }
    }

    @OnClick(R.id.commit)
    void commit() {
        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                sureCommit();
            }
            @Override
            public void onCallback2() {

            }
        });
    }

    CommonLogic commonLogic;
    /**
     * 料号
     */
    final int ITEMWHAT = 1001;

    List<ListSumBean> sumBeanList;

    String minQty;
    boolean itemFlag;
    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case ITEMWHAT:
                    updateList(String.valueOf(msg.obj));
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_line_send;
    }

    @Override
    protected void doBusiness() {
        initData();
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.line_send);
        //显示默认仓库
       // tvWarehouse.setVisibility(View.VISIBLE);
        //tvWarehouse.setText(LoginLogic.getWarehouse_no());
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.LINESEND;
        return module;
    }

    /**
     * 根据料号跟新汇总数据
     *
     * @param item_no
     */
    void updateList(String item_no) {
        ClickItemPutBean putBean = new ClickItemPutBean();
        putBean.setItem_no(item_no);
        putBean.setWarehouse_no(LoginLogic.getWare());
        commonLogic.getOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
            @Override
            public void onSuccess(final List<ListSumBean> list) {
                if (list.size() == 0) {
                    showFailedDialog(R.string.nodate);
                    return;
                }
                sumBeanList=list;
                show(sumBeanList.get(0));
                itemFlag=true;
                etCurrentNum.requestFocus();
            }

            @Override
            public void onFailed(String error) {
                showFailedDialog(error, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        etItemNo.setText("");
                        sumBeanList.clear();
                        ListSumBean sumBean = new ListSumBean();
                        show(sumBean);
                        itemFlag=false;
                    }
                });
            }
        });
    }

    /**
     * 展示内容
     * @param showBean
     */
    private void show(ListSumBean showBean){
        tvOrderNumber.setText(showBean.getWo_no());
        tvItemName.setText(showBean.getItem_name());
        tvModel.setText(showBean.getItem_spec());
        tvProduceNum.setText(StringUtils.deleteZero(showBean.getShortage_qty()));
        tvLocatorNum.setText(StringUtils.deleteZero(showBean.getStock_qty()));
        tvOrderMaxNum.setText(StringUtils.deleteZero(showBean.getWo_set_qty()));
        tvLineMaxNum.setText(StringUtils.deleteZero(showBean.getSet_qty()));
         minQty = StringUtils.getMinQty(showBean.getShortage_qty(), showBean.getSet_qty());
        etCurrentNum.setText(StringUtils.deleteZero(minQty));
    }
    private void sureCommit(){
        if (!itemFlag||sumBeanList.size()==0) {
            showFailedDialog(R.string.nodate);
            return;
        }
        showLoadingDialog();
        sumBeanList.get(0).setDoc_no(sumBeanList.get(0).getWo_no());
        String current = etCurrentNum.getText().toString();
        if (StringUtils.isBlank(current)){
            showFailedDialog(R.string.input_num);
            return;
        }
        float sub = StringUtils.sub(minQty, current);
        if (sub<0){
            showFailedDialog(R.string.input_num_toobig);
            return;
        }
        sumBeanList.get(0).setWarehouse_no(LoginLogic.getWare());
        sumBeanList.get(0).setQty(current);
        List<Map<String, String>> listMap = ObjectAndMapUtils.getListMap(sumBeanList);
        commonLogic.commitList(listMap, new CommonLogic.CommitListListener() {
            @Override
            public void onSuccess(String msg) {
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        ListSumBean listSumBean = new ListSumBean();
                        show(listSumBean);
                        etItemNo.setText("");
                        createNewModuleId(module);
                        initData();
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                showCommitFailDialog(error);
            }
        });

    }

   private void initData(){
       itemFlag=false;
       minQty="";
       etItemNo.requestFocus();
       commonLogic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
       sumBeanList = new ArrayList<>();
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
