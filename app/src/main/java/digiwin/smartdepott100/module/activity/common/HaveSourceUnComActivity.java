package digiwin.smartdepott100.module.activity.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.LogUtils;
import digiwin.pulltorefreshlibrary.recyclerview.DividerItemDecoration;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseSwipeMenuAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.produce.workorderreturn.WorkOrderReturnCommitActivity;
import digiwin.smartdepott100.module.activity.sale.pickupshipment.PickUpShipmentCommitActivity;
import digiwin.smartdepott100.module.activity.sale.scanout.ScanOutCommitActiivty;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.UnCompleteBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @des    有来源未完事项
 * @author  xiemeng
 * @date    2017/3/24
 */
public class HaveSourceUnComActivity extends BaseTitleActivity {
    @BindView(R.id.rv_swipemenu)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private BaseSwipeMenuAdapter<UnCompleteBean> adapter;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    CommonLogic logic;
    /**
     * 模组
     */
    public final static String MODULECODE = "code";
    /**
     * 数据
     */
    List<UnCompleteBean> uncomList;

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        Bundle bundle = getIntent().getExtras();
        module=  bundle.getString(MODULECODE, ModuleCode.OTHER);
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.un_complete);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_nocome_un_com;
    }

    @Override
    protected void doBusiness() {
        logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
        uncomList=new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();

    }

    /**
     * 获取未完事项
     */
    private void getList() {
        try {
            Bundle bundle = getIntent().getExtras();
            String string = bundle.getString(AddressContants.MODULEID_INTENT);
            logic = CommonLogic.getInstance(activity, module, string);
            mSwipeMenuRecyclerView.setVisibility(View.GONE);
            Map<String, String> map = new HashMap<>();
            showLoadingDialog();
            logic.getUnCom(map, new CommonLogic.GetUnComListener() {
                @Override
                public void onSuccess(List<UnCompleteBean> list) {
                    uncomList = list;
                    if (list.size() > 0) {
                        showList();
                    }
                    dismissLoadingDialog();
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    showFailedDialog(error, new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                            uncomList = new ArrayList<UnCompleteBean>();
                            showList();
                            activity.finish();
                        }
                    });
                }
            });
        }catch (Exception e){
            LogUtils.e(TAG,"getList---"+e);
        }
    }


    /**
     * 显示数据
     */
    private void showList() {
        mSwipeMenuRecyclerView.setVisibility(View.VISIBLE);
        adapter = new BaseSwipeMenuAdapter<UnCompleteBean>(this, uncomList) {
            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.ryitem_havesource_no_finish;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, UnCompleteBean item) {
                holder.setText(R.id.tv_doc_no, item.getDoc_no());
                holder.setText(R.id.tv_name, item.getEmployee_no());
                holder.setText(R.id.tv_date, item.getTransaction_date() + " " + item.getTransaction_time());
                if (AddressContants.N.equals(item.getTransfer_status())) {
                    holder.setText(R.id.tv_state, context.getString(R.string.un_com));
                    holder.setTextColor(R.id.tv_state, context.getResources().getColor(R.color.red));
                } else {
                    holder.setText(R.id.tv_state, context.getString(R.string.commit_failed));
                    holder.setTextColor(R.id.tv_state, context.getResources().getColor(R.color.Base_color));
                }

            }
        };
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单Item点击监听。
        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        // 设置菜单创建器。
        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mSwipeMenuRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        mSwipeMenuRecyclerView.setAdapter(adapter);
        onitemClick();
    }

    /**
     * 点击列表
     */
    private void onitemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                mTimestamp = new StringBuilder();
                mTimestamp.append(uncomList.get(position).getApp_reqno());
                showLoadingDialog();
                ClickItemPutBean clickItemPutBean = new ClickItemPutBean();
                clickItemPutBean.setDoc_no(uncomList.get(position).getDoc_no());
                clickItemPutBean.setWarehouse_no(LoginLogic.getWare());
                logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
                logic.getOrderSumData(clickItemPutBean, new CommonLogic.GetOrderSumListener() {
                    @Override
                    public void onSuccess(List<ListSumBean> list) {
                        toCommit((Serializable) list);
                    }
                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showFailedDialog(error);
                    }
                });
            }
        });
    }

    /**
     * 跳转至不同的二次提交页面
     * @param list
     */
    private void toCommit(Serializable list) {
        Bundle bundle = new Bundle();
        bundle.putString(AddressContants.MODULEID_INTENT, mTimestamp.toString());
        bundle.putString(this.MODULECODE,module);
        switch (module){
            case ModuleCode.WORKORDERRETURN:
                bundle.putSerializable(WorkOrderReturnCommitActivity.COMMITLIST, list);
                ActivityManagerUtils.startActivityForBundleData(activity, WorkOrderReturnCommitActivity.class, bundle);
                break;

            case ModuleCode.SCANOUTSTORE:
                bundle.putSerializable(WorkOrderReturnCommitActivity.COMMITLIST, list);
                ActivityManagerUtils.startActivityForBundleData(activity, ScanOutCommitActiivty.class, bundle);
                break;

            case ModuleCode.PICKUPSHIPMENT:
                bundle.putSerializable(PickUpShipmentCommitActivity.COMMITLIST, list);
                ActivityManagerUtils.startActivityForBundleData(activity, PickUpShipmentCommitActivity.class, bundle);
                break;
        }
        dismissLoadingDialog();
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
                showLoadingDialog();
                mTimestamp = new StringBuilder();
                mTimestamp.append(uncomList.get(adapterPosition).getApp_reqno());
                logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
                HashMap<String, String> map = new HashMap<>();
                logic.deleteUnCom(map, new CommonLogic.DeleteUnComListener() {
                    @Override
                    public void onSuccess(List<UnCompleteBean> list) {
                        dismissLoadingDialog();
                        uncomList=list;
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

