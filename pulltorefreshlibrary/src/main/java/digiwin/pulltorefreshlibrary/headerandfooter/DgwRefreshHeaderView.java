package digiwin.pulltorefreshlibrary.headerandfooter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import digiwin.pulltorefreshlibrary.R;
import digiwin.pulltorefreshlibrary.loadrefresh.SwipeRefreshTrigger;
import digiwin.pulltorefreshlibrary.loadrefresh.SwipeTrigger;

/**
 * Created by sunchangquan on 2015/11/5.
 * 人形加载更多布局（幁动画实现）
 */
public class DgwRefreshHeaderView extends RelativeLayout implements SwipeTrigger, SwipeRefreshTrigger {

    private ImageView ivSpeed;

    private ImageView ivRefresh;

    private AnimationDrawable mAnimDrawable;

    private Animation mTwinkleAnim;

    private int mTriggerOffset;


    public DgwRefreshHeaderView(Context context) {
        super(context);
    }

    public DgwRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DgwRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTriggerOffset = context.getResources().getDimensionPixelOffset(R.dimen.refresh_header_height_jd);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
        mAnimDrawable = (AnimationDrawable) ivRefresh.getBackground();
        mTwinkleAnim = AnimationUtils.loadAnimation(getContext(), R.anim.twinkle);
    }

    @Override
    public void onRefresh() {
        if (!mAnimDrawable.isRunning()){
            mAnimDrawable.start();
        }
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
    }

    @Override
    public void onRelease() {
        if (!mAnimDrawable.isRunning()){
            mAnimDrawable.start();
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
        mAnimDrawable.stop();
    }
}
