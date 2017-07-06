package digiwin.library.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

/**
 * Created by ChangquanSun
 * 2016/12/31
 */

public class AnimatorSetUtils {
    private int mDuration = 500;
    private static Context context;
    private static AnimatorSetUtils set = new AnimatorSetUtils();
    private static int screenWidth;

    private AnimatorSetUtils() {
    }

    public static AnimatorSetUtils getInstance(Context context) {
        screenWidth = ViewUtils.getScreenWidth((Activity) context);
        return set;
    }

    /**
     * 开启动画
     *
     * @param view
     */
    public void startAnimator(View view) {
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", -screenWidth, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration * 3 / 2)
//                ObjectAnimator.ofFloat(view, "rotation", 360,0).setDuration(mDuration)
//                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration*3/2)
        );
        animatorSet.start();
    }

    /**
     * 关闭动画
     *
     * @param view
     */
    public void endAnimator(final View view, final Dialog dialog) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, screenWidth).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(mDuration * 3 / 2)
        );


        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.clearAnimation();
                dialog.dismiss();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();

    }

}
