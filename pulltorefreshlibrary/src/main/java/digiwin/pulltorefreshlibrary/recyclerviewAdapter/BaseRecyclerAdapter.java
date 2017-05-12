package digiwin.pulltorefreshlibrary.recyclerviewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sunchangquan
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected final List<T> mItems;
    protected final Context mContext;
    protected LayoutInflater mInflater;
    private OnItemClickListener mClickListener;

    private OnItemLongClickListener mLongClickListener;
    private AdapterView.OnItemSelectedListener mSelectedListener;

    public BaseRecyclerAdapter(Context ctx, List<T> list) {
        mItems = (list != null) ? list : new ArrayList<T>();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final RecyclerViewHolder holder = new RecyclerViewHolder
                (mInflater.inflate(getItemLayout(viewType), parent, false));
        if (mClickListener != null) {
            holder.setOnItemClickListener(mClickListener);
        }
        if (mLongClickListener != null) {
            holder.setOnItemLongClickListener(mLongClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindData(holder, position, mItems.get(position));
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(int pos, T item) {
        mItems.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mItems.remove(pos);
        notifyItemRemoved(pos);
    }

    public void swap(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    final public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }
    final public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }
    final public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        mSelectedListener = listener;
    }
    /**
     * 重写该方法，根据viewType设置item的layout
     *
     * @param viewType 通过重写getItemViewType（）设置，默认item是0
     * @return
     */
    abstract protected int getItemLayout(int viewType);

    /**
     * 重写该方法进行item数据项视图的数据绑定
     *
     * @param holder   通过holder获得item中的子View，进行数据绑定
     * @param position 该item的position
     * @param item     映射到该item的数据
     */
    abstract protected void bindData(RecyclerViewHolder holder, int position, T item);

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }
}
