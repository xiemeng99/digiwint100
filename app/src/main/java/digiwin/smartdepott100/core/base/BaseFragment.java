package digiwin.smartdepott100.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qGod
 * 2017/1/9
 */

public abstract class BaseFragment extends BaseAppFragment {
    protected String TAG;
    protected View mFragmentView;
    private Unbinder unBind;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            TAG = this.getClass().getSimpleName();
            mFragmentView = inflater.inflate(bindLayoutId(), null);
        }
        unBind = ButterKnife.bind(this, mFragmentView);
        doBusiness();
        return mFragmentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBind.unbind();
        if (null != mFragmentView) {
            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
        }
    }

}
