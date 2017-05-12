package digiwin.smartdepott100.module.adapter.stock;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.main.logic.GetStorageLogic;
import digiwin.smartdepott100.module.activity.common.WareHouseDialog;
import digiwin.smartdepott100.module.bean.common.ListSumBean;

/**
 * @author 赵浩然
 * @module 快速入库 适配器
 * @date 2017/3/31
 */

public class QuickStorageAdapter extends BaseRecyclerAdapter<ListSumBean> {
    public List<ListSumBean> listData = new ArrayList<ListSumBean>();

    public Context context;

    public QuickStorageAdapter(final Context ctx, List<ListSumBean> list) {
        super(ctx, list);
        this.context = ctx;
        listData.clear();
        listData.addAll(list);
        notifyDataSetChanged();
    }

    public List<ListSumBean> getCheckData(){
        List<ListSumBean> checkedList = new ArrayList<ListSumBean>();
        for(ListSumBean detailData : listData){
            if(StringUtils.string2Float(detailData.getMatch_qty()) > 0){
                checkedList.add(detailData);
            }else{
                checkedList.clear();
                return checkedList;
            }
        }

        return checkedList;
    }

    @Override
    protected int getItemLayout(int viewType) {
        return R.layout.ryitem_quickstorage;
    }

    @Override
    protected void bindData(final RecyclerViewHolder holder, final int position, final ListSumBean item) {

        holder.setText(R.id.tv_item_seq, item.getReceipt_seq());
        holder.setText(R.id.tv_item_name, item.getItem_name());
        holder.setText(R.id.tv_unit,item.getUnit_no());
        holder.setText(R.id.tv_item_format, item.getItem_spec());
        holder.setText(R.id.tv_item_no, item.getItem_no());
        holder.setText(R.id.tv_in_storage_number, StringUtils.deleteZero(item.getReq_qty()));
        holder.setText(R.id.tv_match_number, StringUtils.deleteZero(item.getMatch_qty()));

        final TextView wareHouseTv = holder.findViewById(R.id.tv_storage);
        wareHouseTv.setTag(position);

        final List<String> list = GetStorageLogic.getWareString();
        holder.setText(R.id.tv_storage,LoginLogic.getWare());
        holder.setClickListener(R.id.tv_storage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WareHouseDialog.showWareHouseDialog((Activity) context,LoginLogic.getWare(),list);
            }
        });

        //更新库位
        WareHouseDialog.setCallBack(new WareHouseDialog.WareHouseCallBack() {
            @Override
            public void wareHouseCallBack(String wareHouse) {
                LogUtils.i("wareHouse===:",wareHouse);
                wareHouseTv.getTag();
                wareHouseTv.setText(wareHouse);
                item.setWarehouse_no(wareHouse);
                notifyDataSetChanged();
            }
        });

        holder.getEditText(R.id.tv_match_number).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(StringUtils.isBlank(s.toString().trim())){
                    item.setQty("0");
                    holder.setText(R.id.tv_match_number, "0");
                }else if(StringUtils.string2Float(item.getReq_qty()) < StringUtils.string2Float(item.getMatch_qty())){
                    item.setQty(item.getMatch_qty());
                    holder.setText(R.id.tv_match_number, StringUtils.deleteZero(item.getMatch_qty()));
                }else{
                    item.setQty(s.toString().trim());
                }
            }
        });
    }
}
