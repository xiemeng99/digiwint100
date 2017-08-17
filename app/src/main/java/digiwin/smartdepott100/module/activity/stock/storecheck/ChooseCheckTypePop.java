package digiwin.smartdepott100.module.activity.stock.storecheck;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import digiwin.library.popupwindow.CustomPopWindow;
import digiwin.library.utils.ViewUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;

/**
 * @author xiemeng
 * @des 选择盘点类型
 * @date 2017/8/12 11:33
 */

public class ChooseCheckTypePop {

    public interface GetChooseListener {
        public void getChoose(String choose, int position);
    }

    /**
     * 展示数据
     *
     * @param context
     * @param list
     */
    public static void showPop(Context context, final List<String> list, View clickView, final GetChooseListener listener) {

        View view = LayoutInflater.from(context).inflate(R.layout.pop_choose_check_type, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        ChooseCheckTypeAdapter checkTypeAdapter = new ChooseCheckTypeAdapter(context, list);
        recyclerView.setAdapter(checkTypeAdapter);

        CustomPopWindow.PopupWindowBuilder popupWindowBuilder = new CustomPopWindow.PopupWindowBuilder(context);
        final CustomPopWindow customPopWindow = popupWindowBuilder
                .setView(view)
//               .size((int) (ViewUtils.getScreenWidth(context) * 0.8),ViewGroup.LayoutParams.WRAP_CONTENT)
                .size(clickView.getWidth() - 30, clickView.getHeight() * (list.size() + 2))
                .create()
                .showAsDropDown(clickView,0,-80);
        checkTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                listener.getChoose(list.get(position), position);
                customPopWindow.dissmiss();
            }
        });

    }


    static class ChooseCheckTypeAdapter extends BaseRecyclerAdapter<String> {
        private List<String> list;

        public ChooseCheckTypeAdapter(Context ctx, List<String> list) {
            super(ctx, list);
            list = this.list;
        }

        @Override
        protected int getItemLayout(int i) {
            return R.layout.ryitem_choose_check_type;
        }

        @Override
        protected void bindData(RecyclerViewHolder holder, int position, String item) {
            holder.setText(R.id.tv_check_type, item);
        }
    }


}
