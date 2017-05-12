package digiwin.smartdepott100.main.bluetooth;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import digiwin.smartdepott100.R;


/**
 * @des      蓝牙选择适配
 * @author  xiemeng
 * @date    2017/1/16
 */

public class BlueToothChooseAdapter extends RecyclerView.Adapter<BlueToothChooseAdapter.mViewHolder> {

    private List<String> list;
    private Context context;
    private LayoutInflater inflater;
    private BlueToothOnItemClickListener listener;

    public BlueToothChooseAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public interface BlueToothOnItemClickListener {
        public void onClick(View view, int position, mViewHolder holder);
    }
    public void setClick(BlueToothOnItemClickListener click) {
        this.listener = click;
    }
    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ryitem_bluetooth, null);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=listener) {
                    listener.onClick(view, position,holder);
                }
            }
        });
        if(list.get(position)!=null){
            holder.tv_bluename.setText(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class mViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_bluename;
        public  ImageView imageView;
        public mViewHolder(View itemView) {
            super(itemView);
            tv_bluename = (TextView) itemView.findViewById(R.id.tv_bluename);
            imageView = (ImageView) itemView.findViewById(R.id.ischoose);
        }
    }
}
