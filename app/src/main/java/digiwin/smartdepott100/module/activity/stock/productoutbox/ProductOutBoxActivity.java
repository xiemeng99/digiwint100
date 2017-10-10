package digiwin.smartdepott100.module.activity.stock.productoutbox;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.fragment.stock.productoutbox.ProductOutBoxDetailFg;
import digiwin.smartdepott100.module.fragment.stock.productoutbox.ProductOutBoxScanFg;

/**
 * @author 孙长权
 * @des 产品出箱
 */
public class ProductOutBoxActivity extends BaseFirstModuldeActivity {
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
   public ProductOutBoxScanFg scanFg;
    /**
     * 明细
     */
    public ProductOutBoxDetailFg detailFg;

    ModuleViewPagerAdapter adapter;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PRODUCTOUTBOX;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.product_outbox);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_product_outbox;
    }

    @Override
    protected void doBusiness() {
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        scanFg = new ProductOutBoxScanFg();
        detailFg = new ProductOutBoxDetailFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(detailFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.ScanCode));
        titles.add(getResources().getString(R.string.scandetail));
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
                }else if(position==0){
                    scanFg.upDate();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITISD;
    }
}
