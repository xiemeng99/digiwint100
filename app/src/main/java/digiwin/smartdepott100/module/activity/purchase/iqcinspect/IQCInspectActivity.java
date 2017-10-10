package digiwin.smartdepott100.module.activity.purchase.iqcinspect;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.core.base.BaseTitleHActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;
import digiwin.smartdepott100.module.fragment.produce.materialreturn.MaterialReturnScanFg;
import digiwin.smartdepott100.module.fragment.produce.materialreturn.MaterialReturnSumFg;
import digiwin.smartdepott100.module.fragment.purchase.iqcinspect.IQCBadReasonFg;
import digiwin.smartdepott100.module.fragment.purchase.iqcinspect.IQCDefectFg;

/**
 * Created by maoheng on 2017/8/12.
 * iqc 和fqc通用
 */

public class IQCInspectActivity extends BaseTitleHActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;

    @BindView(R.id.tv_check_item)
    TextView tvCheckItem;
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_sample_num)
    TextView tvSampleNum;

    @BindView(R.id.module_vp)
    ViewPager moduleVp;
    @BindView(R.id.main_rg)
    RadioGroup mainRg;
    @OnClick({ R.id.iqc_tab1, R.id.iqc_tab2 })
    public void onRadioButtonClicked(RadioButton radioButton) {

        boolean checked = radioButton.isChecked();

        switch (radioButton.getId()) {
            case R.id.iqc_tab1:
                if (checked) {
                    TypedArray typedArray = activity.obtainStyledAttributes(new int[]{R.attr.Base_color});
                    iqcTab1.setTextColor(activity.getResources().getColor(R.color.white));
                    iqcTab2.setTextColor(typedArray.getColor(0,activity.getResources().getColor(R.color.Base_color)));
                }
                break;
            case R.id.iqc_tab2:
                if (checked) {
                    TypedArray typedArray = activity.obtainStyledAttributes(new int[]{R.attr.Base_color});
                    iqcTab2.setTextColor(activity.getResources().getColor(R.color.white));
                    iqcTab1.setTextColor(typedArray.getColor(0,activity.getResources().getColor(R.color.Base_color)));
                }
                break;
        }
    }
    @BindView(R.id.iqc_tab1)
    RadioButton iqcTab1;
    @BindView(R.id.iqc_tab2)
    RadioButton iqcTab2;

    @OnClick(R.id.iv_title_setting)
    void clickSetting(){
        showToast(R.string.hard_working);
    }

    @OnClick(R.id.iv_scan)
    void clickScan(){
        showToast(R.string.hard_working);
    }

    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;

    /**
     * 不良原因
     */
    private IQCBadReasonFg badReasonFg;

    /**
     * 缺点数
     */
    private IQCDefectFg defectFg;
    ModuleViewPagerAdapter adapter;


    @Override
    protected int bindLayoutId() {
        return R.layout.activity_iqc_inspect;
    }

    @Override
    protected void initNavigationTitle() {
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        mName.setText(title);
        ivScan.setVisibility(View.VISIBLE);
        iv_title_setting.setVisibility(View.VISIBLE);
        iv_title_setting.setImageResource(R.mipmap.check_pic);
        ivScan.setImageResource(R.mipmap.take_photo);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void doBusiness() {
        badReasonFg = new IQCBadReasonFg();
        defectFg = new IQCDefectFg();
        fragments = new ArrayList<>();
        fragments.add(badReasonFg);
        fragments.add(defectFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.bad_reason_maintenance));
        titles.add(getResources().getString(R.string.defect_maintenance));
        fragmentManager = getSupportFragmentManager();
        moduleVp.setAdapter(adapter);
        moduleVp.setAdapter(new FragmentPagerAdapter(fragmentManager)
        {
            @Override
            public int getCount()
            {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return fragments.get(arg0);
            }
        });

        select();
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        QCScanData qcData = (QCScanData) bundle.getSerializable(IQCInspectItemActivity.INTENTTAG);
        tvItemNo.setText(qcData.getItem_no());
        tvItemName.setText(qcData.getItem_name());
        tvCheckItem.setText(qcData.getInspection_item());
        tvSampleNum.setText(qcData.getSample_qty());
    }

    /**
     * 滑动
     */
    private void select() {
        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.iqc_tab1:
                        moduleVp.setCurrentItem(0);
                        TypedArray typedArray = activity.obtainStyledAttributes(new int[]{R.attr.Base_color});
                        iqcTab1.setTextColor(activity.getResources().getColor(R.color.white));
                        iqcTab2.setTextColor(typedArray.getColor(0,activity.getResources().getColor(R.color.Base_color)));
                        break;
                    case R.id.iqc_tab2:
                        moduleVp.setCurrentItem(1);
                        TypedArray typedArray2 = activity.obtainStyledAttributes(new int[]{R.attr.Base_color});
                        iqcTab2.setTextColor(activity.getResources().getColor(R.color.white));
                        iqcTab1.setTextColor(typedArray2.getColor(0,activity.getResources().getColor(R.color.Base_color)));
                        break;
                    default:
                        break;
                }
            }
        });
        moduleVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        badReasonFg.upDateList();
                        iqcTab1.setChecked(true);
                        break;
                    case 1:
                        defectFg.upDateList();
                        iqcTab2.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        iqcTab1.setChecked(true);
    }

    @Override
    public String moduleCode() {
        Bundle bundle = getIntent().getExtras();
        String code = bundle.getString("code");
        module = code;
        return module;
    }

}
