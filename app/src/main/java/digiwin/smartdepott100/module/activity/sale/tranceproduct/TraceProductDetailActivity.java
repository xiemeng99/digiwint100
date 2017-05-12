package digiwin.smartdepott100.module.activity.sale.tranceproduct;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.module.adapter.sale.traceproduct.DetailOneAdapter;
import digiwin.smartdepott100.module.adapter.sale.traceproduct.DetailThreeAdapter;
import digiwin.smartdepott100.module.adapter.sale.traceproduct.DetailTwoAdapter;
import digiwin.smartdepott100.module.bean.sale.traceproduct.TraceProductDetailBean;

/**
 * @author maoheng
 * @des 产品质量追溯 生产过程明细
 * @date 2017/4/6
 */

public class TraceProductDetailActivity extends BaseActivity {

    private TraceProductDetailActivity tactivity;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @OnClick(R.id.iv_back)
    void ivBack(){
        tactivity.finish();
    }
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @OnClick(R.id.tv_title_name)
    void tvBack(){
        tactivity.finish();
    }
    @BindView(R.id.tv_text_one)
    TextView tvTextOne;
    @BindView(R.id.tv_text_two)
    TextView tvTextTwo;
    @BindView(R.id.tv_text_three)
    TextView tvTextThree;
    @BindView(R.id.tv_text_four)
    TextView tvTextFour;
    @BindView(R.id.rc_list)
    RecyclerView rcList;

    @Override
    public String moduleCode() {
        module = ModuleCode.TRANSPRODUCTQUALITY;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_traceproduct_detail;
    }

    @Override
    protected void initNavigationTitle() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private BaseRecyclerAdapter adapter;

    private List<TraceProductDetailBean> list;

    @Override
    protected void doBusiness() {
        tactivity = (TraceProductDetailActivity) activity;
        list = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        list = (List<TraceProductDetailBean>) bundle.getSerializable(TraceProductActivity.DETAIL);
        switch (TraceProductActivity.jumbType){
            case 1:
                tvTextOne.setText(R.string.barcode_no);
                tvTextTwo.setText(R.string.item_no);
                tvTextThree.setText(R.string.item_name);
                tvTextFour.setText(R.string.model);
                tvTitleName.setText(R.string.feeding_batche);
                tvTextFour.setVisibility(View.VISIBLE);
                adapter = new DetailOneAdapter(tactivity,list);
                break;
            case 2:
                tvTextOne.setText(R.string.employee_no);
                tvTextTwo.setText(R.string.employee_name);
                tvTextThree.setText(R.string.blone_department);
                tvTitleName.setText(R.string.work_group);
                tvTextFour.setVisibility(View.GONE);
                adapter = new DetailTwoAdapter(tactivity,list);
                break;
            case 3:
                tvTextOne.setText(R.string.bad_no);
                tvTextTwo.setText(R.string.bad_string);
                tvTextThree.setText(R.string.bad_qty);
                tvTextFour.setText(R.string.check_employee);
                tvTitleName.setText(R.string.check_detail);
                tvTextFour.setVisibility(View.VISIBLE);
                adapter = new DetailThreeAdapter(tactivity,list);
                break;
            default:
                break;
        }
        rcList.setLayoutManager(new LinearLayoutManager(tactivity));
        rcList.setAdapter(adapter);

    }

}
