package digiwin.smartdepott100.module.activity.produce.inbinning;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.CustomDialog;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseDetailRecyclerAdapter;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.produce.InBinningDetailAdapter;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.produce.InBinningLogic;

/**
 * @des  装箱入库明细
 * @date 2017/9/1
 * @author xiemeng
 */
public class InBinningDetailActivity extends BaseTitleActivity {
    /**
     * 页面传输头部数据
     */
    public static final String ONESUM = "onesum";
    /**
     * 模组名
     */
    public static final String MODULECODE = "code";

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.cb_all)
    public CheckBox cbAll;
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @OnClick(R.id.delete)
    void delete() {
        Map<Integer, Boolean> map = adapter.getMap();
        Set<Map.Entry<Integer, Boolean>> sets = map.entrySet();
        List<DetailShowBean> deletelist = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> entry : sets) {
            Integer key = entry.getKey();
            Boolean val = entry.getValue();
            if (null != val && val) {
                deletelist.add(mDetailShowBeen.get(key));
            }
        }
        if (deletelist.size() == 0) {
            showFailedDialog(R.string.delete_choose);
            return;
        }
        toDelete(deletelist, AddressContants.DELETETPYE);
    }

    @OnClick(R.id.cb_all)
    void onCheckChange() {
        boolean checked = cbAll.isChecked();
        HashMap<Integer, Boolean> map = new HashMap<>();
        for (int i = 0; i < mDetailShowBeen.size(); i++) {
            map.put(i, checked);
        }
        adapter.setMap(map);
        ryList.setLayoutManager(new LinearLayoutManager(activity));
        ryList.setAdapter(adapter);
    }

    public InBinningLogic commonLogic;

    BaseDetailRecyclerAdapter adapter = null;
    public List<DetailShowBean> mDetailShowBeen;
    public SumShowBean sumBean;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.scandetail);
    }

    @Override
    public String moduleCode() {
        Bundle bundle = getIntent().getExtras();
        module = bundle.getString(MODULECODE, ModuleCode.OTHER);
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_inbinning_detail;
    }

    @Override
    protected void doBusiness() {
        Bundle bundle = getIntent().getExtras();
        sumBean = (SumShowBean) bundle.getSerializable(ONESUM);
        commonLogic = InBinningLogic.getInstance(activity, module, mTimestamp.toString());
//        if (null != sumBean) {
//            tvItemNo.setText(sumBean.getItem_no());
//        }
        mDetailShowBeen=new ArrayList<>();
        adapter=new InBinningDetailAdapter(activity,mDetailShowBeen);
        Map<Integer, Boolean> map = new HashMap<>();
        updateUI(map);

    }


    /**
     * 更新界面
     * TODO:可能会存在不同的明细页面使用不同的adapter
     */
    private void updateUI(final Map<Integer, Boolean> map) {
        Map<String, String> reqMap = new HashMap<>();
        showLoadingDialog();
        reqMap.put(AddressContants.ITEM_NO, sumBean.getItem_no());
        commonLogic.getInBinningDetailData(reqMap, new InBinningLogic.GetInBinnigDetailDataListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {

                dismissLoadingDialog();
                mDetailShowBeen=detailShowBeen;
                adapter=new InBinningDetailAdapter(activity,mDetailShowBeen);
                adapter.setMap(map);
                ryList.setLayoutManager(new LinearLayoutManager(activity));
                ryList.setAdapter(adapter);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showCommitFailDialog(error);
            }
        });
    }


    /**
     * 删除
     */
    private void toDelete(List<DetailShowBean> deletelist, String type) {
            deleteAndUpdate(deletelist, null);
    }

    /**
     * 修改删除
     *
     * @param list
     */
    private void deleteAndUpdate(List<DetailShowBean> list, CustomDialog editdialog) {
        showLoadingDialog();
        commonLogic.deleteInBinningDetailData(ObjectAndMapUtils.getListMap(list), new InBinningLogic.DeleteInBinningDetailDataListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                Map<Integer, Boolean> map = new HashMap<>();
                updateUI(map);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }


}
