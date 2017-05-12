package digiwin.smartdepott100.module.activity.dailywork.dailworkscan;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;

/**
 * @author maoheng
 * @des 完工扫描
 * @date 2017/4/6
 */

public class FinishWorkActivity extends BaseTitleActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_LOT_no)
    TextView tvLOTNo;
    @BindView(R.id.et_LOT_no)
    EditText etLOTNo;
    @OnFocusChange(R.id.et_LOT_no)
    void lotNoFocusChange() {
        ModuleUtils.viewChange(llLOTNo, views);
        ModuleUtils.etChange(activity, etLOTNo, editTexts);
        ModuleUtils.tvChange(activity, tvLOTNo, textViews);
    }
    @BindView(R.id.ll_LOT_no)
    LinearLayout llLOTNo;
    @BindView(R.id.tv_technics_number)
    TextView tvTechnicsNumber;
    @BindView(R.id.et_technics_number)
    EditText etTechnicsNumber;
    @OnFocusChange(R.id.et_technics_number)
    void technicsNumberFocusChange() {
        ModuleUtils.viewChange(llTechnicsNumber, views);
        ModuleUtils.etChange(activity, etTechnicsNumber, editTexts);
        ModuleUtils.tvChange(activity, tvTechnicsNumber, textViews);
    }
    @BindView(R.id.ll_technics_number)
    LinearLayout llTechnicsNumber;
    @BindView(R.id.tv_the_workers)
    TextView tvTheWorkers;
    @BindView(R.id.et_the_workers)
    EditText etTheWorkers;
    @OnFocusChange(R.id.et_the_workers)
    void theWorkersFocusChange() {
        ModuleUtils.viewChange(llTheWorkers, views);
        ModuleUtils.etChange(activity, etTheWorkers, editTexts);
        ModuleUtils.tvChange(activity, tvTheWorkers, textViews);
    }
    @BindView(R.id.ll_the_workers)
    LinearLayout llTheWorkers;
    @BindView(R.id.tv_class_other)
    TextView tvClassOther;
    @BindView(R.id.et_class_other)
    EditText etClassOther;
    @OnFocusChange(R.id.et_class_other)
    void classOtherFocusChange() {
        ModuleUtils.viewChange(llClassOther, views);
        ModuleUtils.etChange(activity, etClassOther, editTexts);
        ModuleUtils.tvChange(activity, tvClassOther, textViews);
    }
    @BindView(R.id.ll_class_other)
    LinearLayout llClassOther;
    @BindView(R.id.tv_finish_num)
    TextView tvFinishNum;
    @BindView(R.id.et_finish_num)
    EditText etFinishNum;
    @OnFocusChange(R.id.et_finish_num)
    void startNumFocusChange() {
        ModuleUtils.viewChange(llFinishNum, views);
        ModuleUtils.etChange(activity, etFinishNum, editTexts);
        ModuleUtils.tvChange(activity, tvFinishNum, textViews);
    }
    @BindView(R.id.ll_finish_num)
    LinearLayout llFinishNum;
    @BindView(R.id.tv_over_num)
    TextView tvOverNum;
    @BindView(R.id.et_over_num)
    EditText etOverNum;
    @OnFocusChange(R.id.et_over_num)
    void overNumFocusChange() {
        ModuleUtils.viewChange(llOverNum, views);
        ModuleUtils.etChange(activity, etOverNum, editTexts);
        ModuleUtils.tvChange(activity, tvOverNum, textViews);
    }
    @BindView(R.id.ll_over_num)
    LinearLayout llOverNum;
    @BindView(R.id.tv_back_num)
    TextView tvBackNum;
    @BindView(R.id.et_back_num)
    EditText etBackNum;
    @OnFocusChange(R.id.et_back_num)
    void backNumFocusChange() {
        ModuleUtils.viewChange(llBackNum, views);
        ModuleUtils.etChange(activity, etBackNum, editTexts);
        ModuleUtils.tvChange(activity, tvBackNum, textViews);
    }
    @BindView(R.id.ll_back_num)
    LinearLayout llBackNum;
    @BindView(R.id.tv_machineNum)
    TextView tvMachineNum;
    @BindView(R.id.et_machineNum)
    EditText etMachineNum;
    @OnFocusChange(R.id.et_machineNum)
    void machineNumFocusChange() {
        ModuleUtils.viewChange(llMachineNum, views);
        ModuleUtils.etChange(activity, etMachineNum, editTexts);
        ModuleUtils.tvChange(activity, tvMachineNum, textViews);
    }
    @BindView(R.id.ll_machineNum)
    LinearLayout llMachineNum;
    @BindView(R.id.tv_people_num)
    TextView tvPeopleNum;
    @BindView(R.id.et_people_num)
    EditText etPeopleNum;
    @OnFocusChange(R.id.et_people_num)
    void peopleNumFocusChange() {
        ModuleUtils.viewChange(llPeopleNum, views);
        ModuleUtils.etChange(activity, etPeopleNum, editTexts);
        ModuleUtils.tvChange(activity, tvPeopleNum, textViews);
    }
    @BindView(R.id.ll_people_num)
    LinearLayout llPeopleNum;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @OnFocusChange(R.id.et_remark)
    void remarkFocusChange() {
        ModuleUtils.viewChange(llRemark, views);
        ModuleUtils.etChange(activity, etRemark, editTexts);
        ModuleUtils.tvChange(activity, tvRemark, textViews);
    }
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;
    @BindView(R.id.commit)
    Button commit;

    @BindViews({R.id.et_LOT_no, R.id.et_technics_number, R.id.et_the_workers, R.id.et_class_other, R.id.et_finish_num, R.id.et_over_num, R.id.et_back_num, R.id.et_machineNum, R.id.et_people_num, R.id.et_remark})
    List<EditText> editTexts;
    @BindViews({R.id.ll_LOT_no, R.id.ll_technics_number, R.id.ll_the_workers, R.id.ll_class_other, R.id.ll_finish_num, R.id.ll_over_num, R.id.ll_back_num, R.id.ll_machineNum, R.id.ll_people_num, R.id.ll_remark})
    List<View> views;
    @BindViews({R.id.tv_LOT_no, R.id.tv_technics_number, R.id.tv_the_workers, R.id.tv_class_other, R.id.tv_finish_num, R.id.tv_over_num, R.id.tv_back_num, R.id.tv_machineNum, R.id.tv_people_num, R.id.tv_remark})
    List<TextView> textViews;


    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.FINISHWORKSCAN;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.finish_scan);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_finishworkscan;
    }

    @Override
    protected void doBusiness() {

    }

}
