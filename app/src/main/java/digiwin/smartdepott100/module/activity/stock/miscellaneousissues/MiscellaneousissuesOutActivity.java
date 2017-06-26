package digiwin.smartdepott100.module.activity.stock.miscellaneousissues;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.fragment.stock.miscellaneous.out.MiscellaneousIssueScanFg;
import digiwin.smartdepott100.module.fragment.stock.miscellaneous.out.MiscellaneousIssueSumFg;

/**
 * @author 唐孟宇
 * @des 杂项发料
 */
public class MiscellaneousissuesOutActivity extends BaseFirstModuldeActivity {
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
     * 未完事项
     */
/*    @BindView(R.id.un_com)
    ImageView iv_un_com;*/

    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;
    /**
     * 扫码
     */
  public   MiscellaneousIssueScanFg scanFg;
    /**
     * 汇总提交
     */
    MiscellaneousIssueSumFg sumFg;

    ModuleViewPagerAdapter adapter;
    /**
     * 跳转明细使用
     */
    public final int DETAILCODE = 1234;
    /**
     * 跳转明细使用
     */
    public final int CLEAR = 1005;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.MISCELLANEOUSISSUESOUT;
        return module;
    }


    /**
     * 未完事项
     */
/*    @OnClick(R.id.un_com)
    void unComaffair(){
        Bundle bundle = new Bundle();
        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
        bundle.putString(NoComeUnComActivity.MODULECODE, module);
        ActivityManagerUtils.startActivityForBundleData(activity, NoComeUnComActivity.class, bundle);
    }*/

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.miscellaneous_issues_out);

      //  iv_un_com.setVisibility(View.INVISIBLE);
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

        scanFg = new MiscellaneousIssueScanFg();
        sumFg = new MiscellaneousIssueSumFg();
        fragments = new ArrayList<>();
        fragments.add(scanFg);
        fragments.add(sumFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.barcode_scan));
        titles.add(getResources().getString(R.string.SumData));
        fragmentManager = getSupportFragmentManager();
        adapter = new ModuleViewPagerAdapter(fragmentManager, fragments, titles);
        mZXVp.setAdapter(adapter);
        tlTab.addTab(tlTab.newTab().setText(titles.get(0)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(1)));
        //Tablayout和ViewPager关联起来
        tlTab.setupWithViewPager(mZXVp);
        tlTab.setTabsFromPagerAdapter(adapter);
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
                    sumFg.upDateList();
                    ivScan.setVisibility(View.INVISIBLE);
                }
                else{
                    ivScan.setVisibility(View.VISIBLE);
                   // scanFg.initFocus();
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
                sumFg.upDateList();
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
