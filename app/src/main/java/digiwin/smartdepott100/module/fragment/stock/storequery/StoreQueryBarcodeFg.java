package digiwin.smartdepott100.module.fragment.stock.storequery;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.stock.storequery.StoreQueryActivity;
import digiwin.smartdepott100.module.adapter.stock.StoreQueryBacodeAdapter;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
/**
 * @author xiemeng
 * @des 库存查询---条码库存
 * @date 2017/3/22
 */
public class StoreQueryBarcodeFg extends BaseFragment {
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    StoreQueryActivity pactivity;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_store_query_barcode;
    }

    @Override
    protected void doBusiness() {
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        pactivity = (StoreQueryActivity) activity;
        LogUtils.e(TAG,"doBusiness");
    }

    /**
     * 更新页面
     * @param sumBeen
     */
    public void onSubscribe(List<ListSumBean> sumBeen) {
        try {
            StoreQueryBacodeAdapter adapter = new StoreQueryBacodeAdapter(pactivity, sumBeen);
            ryList.setAdapter(adapter);
        }catch (Exception e){
            LogUtils.e(TAG,"onSubscribe"+e);
        }

    }

}
