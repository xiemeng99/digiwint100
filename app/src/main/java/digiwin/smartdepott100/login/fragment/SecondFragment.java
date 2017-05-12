package digiwin.smartdepott100.login.fragment;

import android.widget.ImageView;

import butterknife.BindView;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.base.BaseFragment;

/**
 * @des      引导页面使用单图片的fragment
 * @author  xiemeng
 * @date    2017/1/3
 */

public class SecondFragment extends BaseFragment {

    @BindView(R.id.welcome_02)
    ImageView welcome_02;

    static SecondFragment pane=null;
 
    public static SecondFragment newInstance() {
        if (null==pane){
            pane = new SecondFragment();
        }
        return pane;
    }
    @Override
    protected int bindLayoutId() {
        return R.layout.welcome_fragment02;
    }

    @Override
    protected void doBusiness() {
    }

}