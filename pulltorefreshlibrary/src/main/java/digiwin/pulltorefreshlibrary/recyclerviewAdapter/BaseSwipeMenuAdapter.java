/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package digiwin.pulltorefreshlibrary.recyclerviewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by ChangquanSun on 2016/7/22
 * 侧滑菜单Adapter
 */
public abstract class BaseSwipeMenuAdapter<T> extends SwipeMenuAdapter<RecyclerViewHolder> {
    protected final List<T> mItems;
    protected final Context mContext;
    protected LayoutInflater mInflater;

    private OnItemClickListener mClickListener;

    public BaseSwipeMenuAdapter(Context ctx, List<T> list) {
        mItems = (list != null) ? list : new ArrayList<T>();
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
    }

    final public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.setOnItemClickListener(mClickListener);
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
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(getItemLayout(0), parent, false);
    }

    @Override
    public RecyclerViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new RecyclerViewHolder(realContentView);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

}
