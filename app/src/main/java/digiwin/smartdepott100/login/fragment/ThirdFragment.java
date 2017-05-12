package digiwin.smartdepott100.login.fragment;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.activity.LoginActivity;

/**
 * @des      引导页面使用单图片的fragment
 * @author  xiemeng
 * @date    2017/1/3
 */

public class ThirdFragment extends BaseFragment {

    static ThirdFragment pane=null;

    public static ThirdFragment newInstance() {
        if (null==pane){
            pane = new ThirdFragment();
        }
        return pane;
    }
    @Override
    protected int bindLayoutId() {
        return R.layout.welcome_fragment04;
    }

    @Override
    protected void doBusiness() {
    }






}