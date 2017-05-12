package digiwin.smartdepott100.module.test.test_fragment;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class TestTwoFragment extends BaseFragment{

    @BindView(R.id.ZX_recyclerView)
    RecyclerView ZX_recyclerView;

    private TestTwoAdapter adapter;
    private List<TestTwoBean> list;

    @Override
    protected int bindLayoutId() {
        return R.layout.test_twofragment_layout;
    }

    @Override
    protected void doBusiness() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
//        linearLayoutManager.setSmoothScrollbarEnabled(true);
        list = new ArrayList<>();
        ZX_recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TestTwoAdapter(activity);
        ZX_recyclerView.setAdapter(adapter);
        getDatas();
    }

    private void getDatas() {
        for (int i = 0; i < 5; i++) {
            TestTwoBean bean = new TestTwoBean();
            bean.inWarehouse = "20";
            bean.matchingNo = "20";
            bean.materialNo = "20";
            bean.productName = "20";
            list.add(bean);
        }
        for (int i = 0; i < 5; i++) {
            TestTwoBean bean = new TestTwoBean();
            bean.inWarehouse = "20";
            bean.matchingNo = "0";
            bean.materialNo = "20";
            bean.productName = "20";
            list.add(bean);
        }
        for (int i = 0; i < 5; i++) {
            TestTwoBean bean = new TestTwoBean();
            bean.inWarehouse = "20";
            bean.matchingNo = "10";
            bean.materialNo = "20";
            bean.productName = "20";
            list.add(bean);
        }
        adapter.setList(list);
    }
}
