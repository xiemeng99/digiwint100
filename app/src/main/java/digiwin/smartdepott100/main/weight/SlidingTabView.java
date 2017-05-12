package digiwin.smartdepott100.main.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import digiwin.smartdepott100.R;


/**
 * Created by moon.zhong on 2015/5/25.
 * 顶部标题栏+指示器
 */
public class SlidingTabView extends LinearLayout {

    private static final float DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0.5f;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFFffc400;

    private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
    private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 0x20;
    private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;

    private final int mSelectedIndicatorThickness;
    private final Paint mSelectedIndicatorPaint;

    private final float mDividerHeight;
    private final float density ;

    private int mSelectedPosition;
    private float mSelectionOffset;
    private Context context;

    public SlidingTabView(Context context) {
        this(context, null);
    }

    public SlidingTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        setWillNotDraw(false);
        density= getResources().getDisplayMetrics().density;

        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.colorForeground, outValue, true);
        final int themeForegroundColor =  outValue.data;


        mSelectedIndicatorThickness = (int) (SELECTED_INDICATOR_THICKNESS_DIPS * density);
        mSelectedIndicatorPaint = new Paint();
        mSelectedIndicatorPaint.setColor(context.getResources().getColor(R.color.Assist_color));

        mDividerHeight = DEFAULT_DIVIDER_HEIGHT;

        this.setBackgroundColor(context.getResources().getColor(R.color.Base_color));
    }

    void viewPagerChange(int position, float offset){
        this.mSelectedPosition = position ;
        this.mSelectionOffset = offset ;
        if (offset == 0){
            for (int i = 0; i < getChildCount(); i++) {
                TextView child = (TextView) getChildAt(i);
                child.setBackgroundColor(context.getResources().getColor(R.color.Base_color));
                child.setTextColor(Color.WHITE);
            }
            TextView selectedTitle = (TextView) getChildAt(mSelectedPosition);
            selectedTitle.setTextColor(context.getResources().getColor(R.color.Assist_color));
        }
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        final int height = getHeight();
        final int childCount = getChildCount();

        if (childCount > 0) {
            TextView selectedTitle = (TextView) getChildAt(mSelectedPosition);
            int left = selectedTitle.getLeft();
            int right = selectedTitle.getRight();

            if (mSelectionOffset > 0f && mSelectedPosition < (getChildCount() - 1)) {

                TextView nextTitle = (TextView) getChildAt(mSelectedPosition + 1);
                left = (int) (mSelectionOffset * nextTitle.getLeft() +
                        (1.0f - mSelectionOffset) * left);
                right = (int) (mSelectionOffset * nextTitle.getRight() +
                        (1.0f - mSelectionOffset) * right);
            }

            Path path=new Path();
            mSelectedIndicatorPaint.setStyle(Paint.Style.FILL);//充满
            int value = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,7, getResources().getDisplayMetrics());

            //左下角点
            int l=left+(right-left)/2-value;
            int r=left+(right-left)/2+value;

            path.moveTo(l,height);
            path.lineTo(r,height);
            path.lineTo(left+(right-left)/2,height-value);
            path.close();
            canvas.drawPath(path, mSelectedIndicatorPaint);
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
