package digiwin.smartdepott100.module.fragment.stock.storequery;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
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
    CommonLogic logic;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_store_query_item_no;
    }

    @Override
    protected void doBusiness() {
        EventBus.getDefault().register(this);
        pactivity = (StoreQueryActivity) activity;
        logic = CommonLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
        ryList.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void upDateList(ClickItemPutBean bean) {
        try {
            bean.setFlag(AddressContants.ONE);
            logic.getOrderSumData(bean, new CommonLogic.GetOrderSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
//                    if (null == list || list.size() == 0) {
//                        showFailedDialog(R.string.nodate);
//                        return;
//                    }
                    StoreQueryItemNoAdapter adapter = new StoreQueryItemNoAdapter(context, list);
                    ryList.setAdapter(adapter);
                }

                @Override
                public void onFailed(String error) {
//                    showFailedDialog(error);
                }
            });
        }catch (Exception e){
            LogUtils.e(TAG,"updateList----->"+e);
        }


    }


}
