package digiwin.pulltorefreshlibrary.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @des     自定义imageview
 * @author  maoheng
 * @date    2017/1/3
 */

public class RecyclerImageView extends ImageView {
    public RecyclerImageView(Context context) {
        super(context);
    }

    public RecyclerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}
