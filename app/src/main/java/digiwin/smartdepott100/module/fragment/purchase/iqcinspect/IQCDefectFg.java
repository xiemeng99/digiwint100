package digiwin.smartdepott100.module.fragment.purchase.iqcinspect;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.pulltorefreshlibrary.recyclerview.DividerItemDecoration;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseSwipeMenuAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.purchase.iqcinspect.IQCCheckValueDialog;
import digiwin.smartdepott100.module.activity.purchase.iqcinspect.IQCInspectActivity;
import digiwin.smartdepott100.module.activity.purchase.iqcinspect.IQCInspectItemActivity;
import digiwin.smartdepott100.module.bean.purchase.CheckValueBean;
import digiwin.smartdepott100.module.bean.purchase.CheckValueCommitBean;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;
import digiwin.smartdepott100.module.logic.purchase.IQCInspectLogic;

/**
 * Created by maoheng on 2017/8/12.
 */

public class IQCDefectFg extends BaseFragment {

    @BindView(R.id.iv_addCheckValue)
    ImageView ivAddCheckValue;
    @OnClick(R.id.iv_addCheckValue)
    void clcikIvAddCheckValue(){
        IQCCheckValueDialog.showBadResonDialog(activity,logic,qcData,checkValueList.size()+1);
        IQCCheckValueDialog.setListener(new IQCCheckValueDialog.GoBackListener() {
            @Override
            public void goBack() {
                upDateList();
            }
        });
    }
    @BindView(R.id.rv_swipemenu)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    BaseSwipeMenuAdapter<CheckValueBean> adapter;

    private IQCInspectLogic logic;

    private IQCInspectActivity iActivity;

    private QCScanData qcData;

    private List<CheckValueBean> checkValueList;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_iqc_defect;
    }

    @Override
    protected void doBusiness() {
        iActivity = (IQCInspectActivity) activity;
        logic = IQCInspectLogic.getInstance(iActivity, iActivity.module, iActivity.mTimestamp.toString());
        qcData = (QCScanData) iActivity.getIntent().getExtras().getSerializable(IQCInspectItemActivity.INTENTTAG);
        checkValueList = new ArrayList<>();
    }

    public void upDateList() {
        showLoadingDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("qc_no",qcData.getQc_no());
        map.put("seq",qcData.getSeq());
        checkValueList.clear();
        showList();
        showLoadingDialog();
        logic.searchIQCCheckValue(map, new IQCInspectLogic.IQCSearchCheckValueListener() {
            @Override
            public void onSuccess(List<CheckValueBean> datas) {
                checkValueList.clear();
                checkValueList.addAll(datas);
                if(checkValueList.size()>0){
                    showList();
                }
                dismissLoadingDialog();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }
    /**
     * 显示数据
     */
    private void showList() {
        mSwipeMenuRecyclerView.setVisibility(View.VISIBLE);
        adapter = new BaseSwipeMenuAdapter<CheckValueBean>(context, checkValueList) {
            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.ryitem_iqc_checkvalue;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, CheckValueBean item) {
                holder.setText(R.id.tv_seq_no,item.getOrder_seq());
                holder.setText(R.id.tv_check_value,item.getMeasure_value());
                CheckBox checkBox = (CheckBox) holder.getView(R.id.cb_isOK);
                checkBox.setKeyListener(null);
                if(AddressContants.Y.equals(item.getResult_type())){
                    checkBox.setChecked(true);
                }else {
                    checkBox.setChecked(false);
                }

            }
        };
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(context));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        // 设置菜单创建器。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mSwipeMenuRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        mSwipeMenuRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                IQCCheckValueDialog.showBadResonDialog(activity,checkValueList.get(position),logic,qcData);
                IQCCheckValueDialog.setListener(new IQCCheckValueDialog.GoBackListener() {
                    @Override
                    public void goBack() {
                        upDateList();
                    }
                });
            }
        });
    }
    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, final int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            // TODO 推荐调用Adapter.notifyItemRemoved(position)，也可以Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                CheckValueBean checkValueBean = checkValueList.get(adapterPosition);
                checkValueBean.setStatu("2");
                List<CheckValueBean> list = new ArrayList<>();
                list.add(checkValueBean);
                CheckValueCommitBean commitBean = new CheckValueCommitBean();
                commitBean.setData(list);
                showLoadingDialog();
                logic.updateIQCCheckValue(commitBean, new IQCInspectLogic.IQCSearchCheckValueListener() {
                    @Override
                    public void onSuccess(List<CheckValueBean> datas) {
                        dismissLoadingDialog();
                        upDateList();
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showFailedDialog(error);
                    }
                });
            }
        }
    };

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_width);
            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = getResources().getDimensionPixelSize(R.dimen.item_height);

            SwipeMenuItem deleteItem = new SwipeMenuItem(activity)
                    .setBackgroundDrawable(R.drawable.swipe_delete)
                    .setText(R.string.delete)// 文字，还可以设置文字颜色，大小等
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };
}
