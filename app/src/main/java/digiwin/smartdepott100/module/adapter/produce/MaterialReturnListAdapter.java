package digiwin.smartdepott100.module.adapter.produce;
import android.content.Context;
import java.util.List;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;

/**
 * @des  退料过账列表
 * @author  xiemeng
 * @date    2017/3/30
 */
public class MaterialReturnListAdapter extends BaseRecyclerAdapter<FilterResultOrderBean> {

    public MaterialReturnListAdapter(Context ctx, List<FilterResultOrderBean> list) {
        super(ctx, list);
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_materialreturnlist;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, int position, final FilterResultOrderBean item) {
        holder.setText(R.id.tv_material_return_number, item.getDoc_no());
        holder.setText(R.id.tv_item_date,item.getCreate_date());
        holder.setText(R.id.tv_depart, item.getDepartment_name());
        holder.setText(R.id.tv_applicant, item.getEmployee_name());
    }
}
