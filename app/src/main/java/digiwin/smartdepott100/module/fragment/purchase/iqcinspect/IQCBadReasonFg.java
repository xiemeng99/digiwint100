package digiwin.smartdepott100.module.fragment.purchase.iqcinspect;

import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
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
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.module.activity.purchase.iqcinspect.IQCBadReasonDialog;
import digiwin.smartdepott100.module.activity.purchase.iqcinspect.IQCInspectActivity;
import digiwin.smartdepott100.module.activity.purchase.iqcinspect.IQCInspectItemActivity;
import digiwin.smartdepott100.module.bean.purchase.BadReasonBean;
import digiwin.smartdepott100.module.bean.purchase.BadReasonCommitBean;
import digiwin.smartdepott100.module.bean.purchase.QCScanData;
import digiwin.smartdepott100.module.logic.purchase.IQCInspectLogic;

/**
 * Created by maoheng on 2017/8/12.
 */

public class IQCBadReasonFg extends BaseFragment {

    @BindView(R.id.iv_addBadReason)
    ImageView ivAddBadReason;
    @OnClick(R.id.iv_addBadReason)
    void addBadReason(){
        IQCBadReasonDialog.showBadResonDialog(activity,logic,qcData);
        IQCBadReasonDialog.setListener(new IQCBadReasonDialog.GoBackListener() {
            @Override
            public void goBack() {
                upDateList();
            }
        });
    }
    @BindView(R.id.rv_swipemenu)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    BaseSwipeMenuAdapter<BadReasonBean> adapter;

    private IQCInspectLogic logic;

    private IQCInspectActivity iActivity;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_iqc_badreason;
    }

    private QCScanData qcData;

    private List<BadReasonBean> badReasonList;

    @Override
    protected void doBusiness() {
        iActivity = (IQCInspectActivity) activity;
        logic = IQCInspectLogic.getInstance(iActivity, iActivity.module, iActivity.mTimestamp.toString());
        qcData = (QCScanData) iActivity.getIntent().getExtras().getSerializable(IQCInspectItemActivity.INTENTTAG);
        badReasonList = new ArrayList<>();
        upDateList();
    }

    public void upDateList() {
        showLoadingDialog();
        HashMap<String,String> map = new HashMap<>();
        map.put("qc_no",qcData.getQc_no());
        map.put("seq",qcData.getSeq());
        badReasonList.clear();
        showList();
        showLoadingDialog();
        logic.getIQCBadReason(map, new IQCInspectLogic.IQCGetBadReasonListener() {
            @Override
            public void onSuccess(List<BadReasonBean> datas) {
                badReasonList.clear();
                badReasonList.addAll(datas);
                if(badReasonList.size()>0){
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
        adapter = new BaseSwipeMenuAdapter<BadReasonBean>(context, badReasonList) {
            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.ryitem_iqc_badreason;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, BadReasonBean item) {
                holder.setText(R.id.tv_defectrea,item.getDefect_reason());
                holder.setText(R.id.tv_defectrea_instruction,item.getDefect_reason_name());
                holder.setText(R.id.tv_defect_num,item.getQty());
                holder.setText(R.id.tv_bad_num,item.getDefect_reason_qty());
                holder.setText(R.id.tv_remark,item.getRemark() );
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
                IQCBadReasonDialog.showBadResonDialog(activity,badReasonList.get(position),logic,qcData);
                IQCBadReasonDialog.setListener(new IQCBadReasonDialog.GoBackListener() {
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
                BadReasonBean badReasonBean = badReasonList.get(adapterPosition);
                badReasonBean.setStatu("2");
                List<BadReasonBean> list = new ArrayList<>();
                list.add(badReasonBean);
                BadReasonCommitBean commitBean = new BadReasonCommitBean();
                commitBean.setData(list);
                showLoadingDialog();
                logic.upDateIQCBadReason(commitBean, new IQCInspectLogic.IQCUpDateBadReasonListener() {
                    @Override
                    public void onSuccess(List<BadReasonBean> datas) {
                        dismissLoadingDialog();
                        badReasonList.remove(adapterPosition);
                        showList();
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
