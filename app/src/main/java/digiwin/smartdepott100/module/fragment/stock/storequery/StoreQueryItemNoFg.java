package digiwin.smartdepott100.module.fragment.stock.storequery;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import digiwin.smartdepott100.module.logic.stock.StoreQueryLogic;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.stock.storequery.StoreQueryActivity;
import digiwin.smartdepott100.module.adapter.stock.StoreQueryItemNoAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 库存查询---条码库存
 * @date 2017/3/22
 */
public class StoreQueryItemNoFg extends BaseFragment {
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    StoreQueryActivity pactivity;
    StoreQueryLogic logic;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_store_query_item_no;
    }

    @Override
    protected void doBusiness() {
        pactivity = (StoreQueryActivity) activity;
        ryList.setLayoutManager(new LinearLayoutManager(activity));
    }

    public void upDateList(List<ListSumBean> list) {
        try {
            StoreQueryItemNoAdapter adapter = new StoreQueryItemNoAdapter(context, list);
            ryList.setAdapter(adapter);
        }catch (Exception e){
            LogUtils.e(TAG,"upDateList"+e);
        }

    }

}
