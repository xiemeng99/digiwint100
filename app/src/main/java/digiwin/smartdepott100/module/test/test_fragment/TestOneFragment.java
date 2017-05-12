package digiwin.smartdepott100.module.test.test_fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;

/**
 * @author 毛衡
 * 测试用模块
 * Created by Administrator on 2017/2/10 0010.
 */

public class TestOneFragment extends BaseFragment{

    /**
     * 扫描框展示集合
     */
    private List<View> relativeLayouts;
    private List<TextView> textViews;
    private List<EditText> editTexts;

    /**
     * 底部信息展示布局
     */
    @BindView(R.id.rl_zx_detail)
    RelativeLayout rl_zx_detail;
    @BindView(R.id.tv_detail_show)
    TextView tv_detail_show;

    /**
     * 上部扫描
     */
    @BindView(R.id.ll_zx_input)
    LinearLayout ll_zx_input;
    /**
     * 工单单号
     */
    @BindView(R.id.rl_scan_gongDan)
    RelativeLayout rl_scan_gongDan;
    @BindView(R.id.tv_scan_gongDan)
    TextView tv_scan_gongDan;
    @BindView(R.id.et_scan_gongDan)
    EditText et_scan_gongDan;
    @OnFocusChange(R.id.et_scan_gongDan)
    void gongDanGetFocus(){
        ModuleUtils.viewChange(rl_scan_gongDan,relativeLayouts);
        ModuleUtils.tvChange(activity,tv_scan_gongDan,textViews);
        ModuleUtils.etChange(activity,et_scan_gongDan,editTexts);
    }
    @OnClick(R.id.rl_scan_gongDan)
    void gongDanClick(){
        ModuleUtils.viewChange(rl_scan_gongDan,relativeLayouts);
        ModuleUtils.tvChange(activity,tv_scan_gongDan,textViews);
        ModuleUtils.etChange(activity,et_scan_gongDan,editTexts);
        et_scan_gongDan.requestFocus();
    }
    /**
     * 库位
     */
    @BindView(R.id.rl_scan_kuWei)
    RelativeLayout rl_scan_kuWei;
    @BindView(R.id.tv_scan_kuWei)
    TextView tv_scan_kuWei;
    @BindView(R.id.et_scan_kuWei)
    EditText et_scan_kuWei;
    @OnFocusChange(R.id.et_scan_kuWei)
    void kuWeiGetFocus(){
        ModuleUtils.viewChange(rl_scan_kuWei,relativeLayouts);
        ModuleUtils.tvChange(activity,tv_scan_kuWei,textViews);
        ModuleUtils.etChange(activity,et_scan_kuWei,editTexts);
    }
    @OnClick(R.id.rl_scan_kuWei)
    void kuWeiClick(){
        ModuleUtils.viewChange(rl_scan_kuWei,relativeLayouts);
        ModuleUtils.tvChange(activity,tv_scan_kuWei,textViews);
        ModuleUtils.etChange(activity,et_scan_kuWei,editTexts);
        et_scan_kuWei.requestFocus();
    }
    /**
     * 理由码
     */
    @BindView(R.id.rl_scan_reason)
    RelativeLayout rl_scan_reason;
    @BindView(R.id.tv_scan_reason)
    TextView tv_scan_reason;
    @BindView(R.id.et_scan_reason)
    EditText et_scan_reason;
    @OnFocusChange(R.id.et_scan_reason)
    void reasonGetFocus(){
        ModuleUtils.viewChange(rl_scan_reason,relativeLayouts);
        ModuleUtils.tvChange(activity,tv_scan_reason,textViews);
        ModuleUtils.etChange(activity,et_scan_reason,editTexts);
    }
    @OnClick(R.id.rl_scan_reason)
    void reasonClick(){
        ModuleUtils.viewChange(rl_scan_reason,relativeLayouts);
        ModuleUtils.tvChange(activity,tv_scan_reason,textViews);
        ModuleUtils.etChange(activity,et_scan_reason,editTexts);
        et_scan_reason.requestFocus();
    }
    /**
     * 数量
     */
    @BindView(R.id.ll_input_num)
    RelativeLayout rl_input_num;
    @BindView(R.id.tv_input_num)
    TextView tv_input_num;
    @BindView(R.id.et_input_num)
    EditText et_input_num;
    @OnFocusChange(R.id.et_input_num)
    void numGetFocus(){
        ModuleUtils.viewChange(rl_input_num,relativeLayouts);
        ModuleUtils.tvChange(activity,tv_input_num,textViews);
        ModuleUtils.etChange(activity,et_input_num,editTexts);
    }
    @OnClick(R.id.ll_input_num)
    void sumClick(){
        ModuleUtils.viewChange(rl_input_num,relativeLayouts);
        ModuleUtils.tvChange(activity,tv_input_num,textViews);
        ModuleUtils.etChange(activity,et_input_num,editTexts);
        et_input_num.requestFocus();
    }
    /**
     * 保存
     */
    @BindView(R.id.iv_save)
    ImageView iv_save;


    @Override
    protected int bindLayoutId() {
        return R.layout.test_onefragment_layout;
    }

    @Override
    protected void doBusiness() {
        rl_scan_gongDan.setBackgroundResource(R.drawable.focus_get_bg);
        tv_scan_gongDan.setTextColor(getResources().getColor(R.color.outside_yellow));
        et_scan_gongDan.setTextColor(getResources().getColor(R.color.outside_yellow));
        initList();
        initListener();
    }

    private void initList() {
        relativeLayouts = new ArrayList<>();
        relativeLayouts.add(rl_input_num);
        relativeLayouts.add(rl_scan_gongDan);
        relativeLayouts.add(rl_scan_kuWei);
        relativeLayouts.add(rl_scan_reason);

        textViews = new ArrayList<>();
        textViews.add(tv_scan_gongDan);
        textViews.add(tv_input_num);
        textViews.add(tv_scan_kuWei);
        textViews.add(tv_scan_reason);

        editTexts = new ArrayList<>();
        editTexts.add(et_input_num);
        editTexts.add(et_scan_gongDan);
        editTexts.add(et_scan_kuWei);
        editTexts.add(et_scan_reason);
    }

    /**
     * 添加文本监听
     */
    private void initListener() {

    }
}
