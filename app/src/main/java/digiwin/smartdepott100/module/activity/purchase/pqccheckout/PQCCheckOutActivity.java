package digiwin.smartdepott100.module.activity.purchase.pqccheckout;

import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.bean.stock.PQCCheckOutBean;
import digiwin.smartdepott100.module.logic.purchase.PQCCheckOutLogic;
import digiwin.library.dialog.OnDialogClickListener;

/**
 * Created by qGod on 2017/5/29
 * Thank you for watching my code
 * pqc检验
 */

public class PQCCheckOutActivity extends BaseTitleActivity {
    PQCCheckOutLogic commonLogic;
    /**
     * 检验时间
     */
    @BindView(R.id.tv_worktime)
    TextView tvWorkTime;
    /**
     * 工单单号
     */
    @BindView(R.id.tv_gongDan_no)
    TextView tvGongDan;
    /**
     * 报工单号
     */
    @BindView(R.id.tv_model)
    TextView tvBaoGong;
    /**
     * 工序
     */
    @BindView(R.id.tv_pallet)
    TextView tvPallet;
    /**
     * 检验类型
     */
    @BindView(R.id.tv_check_mold)
    TextView tvCheckMold;
    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    /**
     * 料号
     */
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    /**
     * 物料批号
     */
    @BindView(R.id.tv_circulation_no)
    TextView tvCirculationNo;
    /**
     * 数量
     */
    @BindView(R.id.tv_num)
    TextView tvNum;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar_title;
    
    @BindView(R.id.cb_OK)
    CheckBox cbOk;
    @BindView(R.id.cb_seletor_NG)
    CheckBox cbNg;

    //是否ok
    @OnCheckedChanged(R.id.cb_OK)
    void isOk(boolean checked) {
        if (checked) {
            cbNg.setChecked(false);
        } else {
            cbNg.setChecked(true);        }
    }
    //是否ng
    @OnCheckedChanged(R.id.cb_seletor_NG)
    void isNg(boolean checked) {
        if (checked) {
            cbOk.setChecked(false);
        } else {
            cbOk.setChecked(true);        }
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar_title;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PQCCHECKOUT;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.pqc_check);
    }


    @Override
    protected int bindLayoutId() {
        return R.layout.activity_pqc_checkout;
    }
    PQCCheckOutBean checkOutBean;
    @Override
    protected void doBusiness() {
        //获取上个页面传递过来的值
        checkOutBean= (PQCCheckOutBean) getIntent().getExtras().getSerializable(PQCCheckOutListActivity.DATA);
        commonLogic = PQCCheckOutLogic.getInstance(activity,module,mTimestamp.toString());
        //默认cbOk选中
        cbOk.setChecked(true);
        cbNg.setChecked(false);
        initData();
    }

    /**
     * 初始化数值
     */
    private void initData() {
        tvWorkTime.setText(checkOutBean.getReport_time());
        tvGongDan.setText(checkOutBean.getWo_no());
        tvBaoGong.setText(checkOutBean.getReport_no());
        tvPallet.setText(checkOutBean.getSubop_no());
        tvCheckMold.setText(checkOutBean.getQc_type());
        tvItemName.setText(checkOutBean.getItem_name());
        tvItemNo.setText(checkOutBean.getItem_no());
        tvCirculationNo.setText(checkOutBean.getPlot_no());
        tvNum.setText(checkOutBean.getUndefect_qty());
    }

    @OnClick(R.id.commit)
    void commit(){
        showLoadingDialog();
        HashMap<String,String> map=new HashMap<>();
        map.put(AddressContants.REPORT_NO,tvBaoGong.getText().toString().trim());//报工单号
        map.put(AddressContants.QC_DATE,checkOutBean.getReport_date());//检验日期
        map.put(AddressContants.QC_TIME,tvWorkTime.getText().toString().trim());//检验时间
        map.put(AddressContants.WO_NO,tvGongDan.getText().toString().trim());//工单号
        map.put(AddressContants.ITEM_NO,tvItemNo.getText().toString().trim());//料号
        map.put(AddressContants.PROCEDURE,tvPallet.getText().toString().trim());//工序
        map.put(AddressContants.OP_SEP,checkOutBean.getOp_seq());//作业序
        map.put(AddressContants.QTY_UNDEFECT,tvNum.getText().toString().trim());//作业序
        map.put(AddressContants.RESULT,cbOk.isSelected()?"1":"3");
        commonLogic.commitData(map, new PQCCheckOutLogic.CommitListener() {
            @Override
            public void onSuccess(String qcNo) {
                dismissLoadingDialog();
                showCommitSuccessDialog(qcNo, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        PQCCheckOutActivity.this.finish();
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

}
