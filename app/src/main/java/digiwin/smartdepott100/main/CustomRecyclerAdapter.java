package digiwin.smartdepott100.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.weight.CenterTextView;
import digiwin.smartdepott100.main.bean.ModuleBean;


/**
 * @Author 毛衡
 * Created by Administrator on 2017/1/11 0011.
 */

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.OCViewHolder> {

    private String TAG = "CustomRecyclerAdapter";
    private List<ModuleBean> list;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public CustomRecyclerAdapter(List<ModuleBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public interface OnItemClickListener{
        public void onClick(View view, int position);
    }
    public void onItemClickListener(OnItemClickListener click) {
        this.listener = click;
    }

    @Override
    public OCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gridview_item, null);
        return new OCViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OCViewHolder holder, final int position) {
        ModuleBean bean=list.get(position);
        holder.tvTitle.setText(bean.getNameRes());
        holder.ivImage.setImageResource(bean.getIconRes());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onClick(view,position);
                }else {
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 运营中心
     * RecyclerView.ViewHolder
     * @Author 毛衡
     */
    class OCViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_gridview_item)
        public TextView tvTitle;
        @BindView(R.id.iv_gridview_item)
        public ImageView ivImage;

        public OCViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
