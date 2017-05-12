package digiwin.pulltorefreshlibrary.headerandfooter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import digiwin.pulltorefreshlibrary.R;
import digiwin.pulltorefreshlibrary.loadrefresh.SwipeLoadMoreFooterLayout;

/**
 * Created by Aspsine on 2015/9/2.
 * 上拉加载更多
 */
public class ClassicLoadMoreFooterView extends SwipeLoadMoreFooterLayout {
    private TextView tvLoadMore;
    private ImageView ivSuccess;
    private ProgressBar progressBar;

    private int mFooterHeight;

    public ClassicLoadMoreFooterView(Context context) {
        this(context, null);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicLoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFooterHeight = getResources().getDimensionPixelOffset(R.dimen.load_more_footer_height_classic);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvLoadMore = (TextView) findViewById(R.id.tvLoadMore);
        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onPrepare() {
        ivSuccess.setVisibility(GONE);
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            ivSuccess.setVisibility(GONE);
            progressBar.setVisibility(GONE);
            if (-y >= mFooterHeight) {
                tvLoadMore.setText("释放加载");
            } else {
                tvLoadMore.setText("上拉加载");
            }
        }
    }

    @Override
    public void onLoadMore() {
        tvLoadMore.setText("正在加载中...");
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onComplete() {
        progressBar.setVisibility(GONE);
        tvLoadMore.setText("加载完成");
        ivSuccess.setVisibility(VISIBLE);
    }

    @Override
    public void onReset() {
        ivSuccess.setVisibility(GONE);
    }
}
