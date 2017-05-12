package digiwin.smartdepott100.module.activity.produce.inbinning;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.fragment.produce.inbinning.InBinningScanFg;
import digiwin.smartdepott100.module.fragment.produce.inbinning.InBinningSumFg;

/**
 * @author 孙长权
 * @des 装箱入库
 */
public class InBinningActivity extends BaseFirstModuldeActivity {
    /**
     * 标题
     */
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    /**
     * tab
     */
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    /**
     * ViewPager
     */
    @BindView(R.id.module_vp)
    public ViewPager mZXVp;
    @BindView(R.id.un_com)
    ImageView unCom;

    /**
     * 包装箱号
     */
    public String packBoxNumber;

    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;
    /**
     * 扫码
     */
   public InBinningScanFg scanFg;
    /**
     * 明细
     */
    public InBinningSumFg detailFg;

    ModuleViewPagerAdapter adapter;

    /**
     * 跳转明细使用
     */
    public static final int DETAILCODE = 1234;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.INBINNING;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.title_in_binning);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_finished_storage;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {

        scanFg = new InBinningScanFg();
        detailFg = new InBinningSumFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(detailFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.ScanCode));
        titles.add(getResources().getString(R.string.SumData));
        fragmentManager = getSupportFragmentManager();
        adapter = new ModuleViewPagerAdapter(fragmentManager, fragments, titles);
        mZXVp.setAdapter(adapter);
        mZXVp.setOffscreenPageLimit(fragments.size());
        //Tablayout和ViewPager关联起来
        tlTab.setupWithViewPager(mZXVp);
        select();
    }

    /**
     * 滑动
     */
    private void select() {
        mZXVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    detailFg.upDateList();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == DETAILCODE) {
                detailFg.upDateList();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "onActivityResult-->" + e);
        }
    }
    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITISD;
    }
}
