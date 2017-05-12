package digiwin.pulltorefreshlibrary.recyclerviewAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sunchangquan
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
    private SparseArray<View> mViews;

    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;

    final public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }
    final public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public View getItemView() {
        return itemView;
    }

    public <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemClick(itemView,getAdapterPosition());
        }
    }
    @Override
    public boolean onLongClick(View view) {
        if (mLongClickListener != null) {
            mLongClickListener.onItemLongClick(itemView,getAdapterPosition());
        }
        return true;
    }
    public View getView(int viewId) {
        return findViewById(viewId);
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewId) {
        return (ImageView) getView(viewId);
    }

    public ImageButton getImageButton(int viewId) {
        return (ImageButton) getView(viewId);
    }

    public EditText getEditText(int viewId) {
        return (EditText) getView(viewId);
    }

    public RecyclerViewHolder setText(int viewId, String value) {
        TextView view = findViewById(viewId);
        view.setText(value);
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, int resId) {
        TextView view = findViewById(viewId);
        view.setTextColor(resId);
        return this;
    }

    public RecyclerViewHolder setBackground(int viewId, int resId) {
        View view = findViewById(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public RecyclerViewHolder setClickListener(int viewId, View.OnClickListener listener) {
        View view = findViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }
    public RecyclerViewHolder setLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = findViewById(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public void setVisibility(int viewId,int visible){
        View view = findViewById(viewId);
        view.setVisibility(visible);
    }

}
