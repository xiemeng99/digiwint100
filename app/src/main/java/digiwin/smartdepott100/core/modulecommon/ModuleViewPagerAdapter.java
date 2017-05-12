package digiwin.smartdepott100.core.modulecommon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 毛衡
 * 模块内部ViewpagerAdapter
 * Created by Administrator on 2017/2/20 0020.
 */

public class ModuleViewPagerAdapter extends FragmentPagerAdapter{
    List<Fragment> fragments;
    List<String> strings;

    public ModuleViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> strings) {
        super(fm);
        this.fragments = fragments;
        this.strings = strings;
    }

    public ModuleViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 嵌套TabLayout的方法,页卡标题
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }
}
