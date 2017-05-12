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
 * @des 开工扫描
 * @date 2017/4/6
 */

public class StartWorkActivity extends BaseTitleActivity {

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
    @BindView(R.id.tv_start_num)
    TextView tvStartNum;
    @BindView(R.id.et_start_num)
    EditText etStartNum;
    @OnFocusChange(R.id.et_start_num)
    void startNumFocusChange() {
        ModuleUtils.viewChange(llStartNum, views);
        ModuleUtils.etChange(activity, etStartNum, editTexts);
        ModuleUtils.tvChange(activity, tvStartNum, textViews);
    }
    @BindView(R.id.ll_start_num)
    LinearLayout llStartNum;
    @BindView(R.id.ll_zx_input)
    LinearLayout llZxInput;
    @BindView(R.id.commit)
    Button commit;
    @BindViews({R.id.et_LOT_no, R.id.et_technics_number, R.id.et_the_workers, R.id.et_class_other, R.id.et_start_num})
    List<EditText> editTexts;
    @BindViews({R.id.ll_LOT_no, R.id.ll_technics_number, R.id.ll_the_workers, R.id.ll_class_other, R.id.ll_start_num})
    List<View> views;
    @BindViews({R.id.tv_LOT_no, R.id.tv_technics_number, R.id.tv_the_workers, R.id.tv_class_other, R.id.tv_start_num})
    List<TextView> textViews;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.STARTWORKSCAN;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.start_scan);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_startworkscan;
    }

    @Override
    protected void doBusiness() {

    }

}
