package digiwin.smartdepott100.module.fragment.dailywork.procedurecheckin;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerview.DividerItemDecoration;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseSwipeMenuAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.activity.dailywork.procedurecheck.ProcedureCheckinSetActivity;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCheckinCommitBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureDevMouKniBean;
import digiwin.smartdepott100.module.logic.dailywok.ProcedureCheckLogic;

/**
 * @author xiemeng
 * @des 生产报工checkin 扫描模具
 * @date 2017/5/18 17:27
 */

public class ProcedureCheckin4KniftFg extends BaseFragment {
    @BindView(R.id.tv_procedure_no)
    TextView tvProcedureNo;
    @BindView(R.id.tv_procedure_name)
    TextView tvProcedureName;
    @BindView(R.id.tv_circulation_no)
    TextView tvCirculationNo;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.item_ll)
    LinearLayout itemLl;
    @BindView(R.id.ry_list)
    SwipeMenuRecyclerView ryList;
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;
    private List<ProcedureDevMouKniBean> resourceBeen;

    @BindViews({R.id.et_scan_barocde})
    List<EditText> editTexts;
    @BindViews({R.id.ll_scan_barcode})
    List<View> views;

    @OnFocusChange(R.id.et_scan_barocde)
    void etScanBarcodeFocusChange() {
        ModuleUtils.viewChange(llScanBarcode, views);
        ModuleUtils.etChange(activity, etScanBarocde, editTexts);
    }

    @OnTextChanged(value = R.id.et_scan_barocde, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void etScanBarcodeChange(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            Connector.getDatabase();
            int count = DataSupport.where("resource_type = ? and resource_no=?", "4", s.toString()).count(ProcedureDevMouKniBean.class);
            if (count > 0) {
                showFailedDialog(R.string.knift_scaned, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        etScanBarocde.setText("");
                    }
                });
                return;
            }
            mHandler.removeMessages(KNIFTWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(KNIFTWHAT, s.toString()), AddressContants.DELAYTIME);
        }


    }

    /**
     * 扫描刀具
     */
    private final int KNIFTWHAT = 1001;
    /**
     * 扫描数据
     */

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case KNIFTWHAT:
                    HashMap<String, String> map = new HashMap<>();
                    map.put(AddressContants.RESOURCE_NO, etScanBarocde.getText().toString());
                    map.put(AddressContants.RESOURCE_TYPE, "4");
                    map.put(AddressContants.SUBOP_NO, tvProcedureNo.getText().toString());
                    map.put(AddressContants.WO_NO, tvOrderNumber.getText().toString());
                    map.put(AddressContants.CHECK_STATUS, AddressContants.CHECKIN);
                    procedureCheckLogic.scanResource(map, new ProcedureCheckLogic.ScanResourceListener() {
                        @Override
                        public void onSuccess(ProcedureDevMouKniBean devMouKniBean) {
                            ProcedureDevMouKniBean mouKniBean = new ProcedureDevMouKniBean();
                            mouKniBean.setResource_type("4");
                            mouKniBean.setResource_no(etScanBarocde.getText().toString());
                            mouKniBean.setResource_name(devMouKniBean.getResource_name());
                            Connector.getDatabase();
                            mouKniBean.save();
                            resourceBeen.add(mouKniBean);
                            showList();
                            etScanBarocde.setText("");
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanBarocde.setText("");
                                }
                            });
                        }
                    });
                    break;
            }
            return false;
        }
    });

    private BaseSwipeMenuAdapter<ProcedureDevMouKniBean> adapter;
    private ProcedureCheckinSetActivity pactivity;
    ProcedureCheckLogic procedureCheckLogic;

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_procedure_checkin4_knift;
    }

    @Override
    protected void doBusiness() {
        resourceBeen = new ArrayList<>();
        SQLiteDatabase db = Connector.getDatabase();
        List<ProcedureDevMouKniBean> resourceBeensql = DataSupport.where("resource_type=?", "4").find(ProcedureDevMouKniBean.class);
        if (null != resourceBeensql) resourceBeen = resourceBeensql;
        pactivity = (ProcedureCheckinSetActivity) activity;
        procedureCheckLogic = ProcedureCheckLogic.getInstance(context, pactivity.module, pactivity.mTimestamp.toString());
        Bundle bundle = pactivity.getIntent().getExtras();
        ProcedureCheckinCommitBean commitBean = (ProcedureCheckinCommitBean) bundle.getSerializable(pactivity.GETHEAD);
        tvProcedureNo.setText(commitBean.getSubop_no());
        tvProcedureName.setText(commitBean.getSubop_name());
        tvCirculationNo.setText(commitBean.getPlot_no());
        tvOrderNumber.setText(commitBean.getWo_no());
        showList();
        etScanBarocde.requestFocus();
    }


    /**
     * 显示数据
     */
    private void showList() {
        adapter = new BaseSwipeMenuAdapter<ProcedureDevMouKniBean>(context, resourceBeen) {
            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.ryitem_procedure_checkin2_devmoukni;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, ProcedureDevMouKniBean item) {
                holder.setText(R.id.tv_resource_no, item.getResource_no());
                holder.setText(R.id.tv_resource_name, item.getResource_name());
            }
        };
        ryList.setLayoutManager(new LinearLayoutManager(context));// 布局管理器。
        ryList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        ryList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单Item点击监听。
        ryList.setSwipeMenuItemClickListener(menuItemClickListener);
        // 设置菜单创建器。
        ryList.setSwipeMenuCreator(swipeMenuCreator);
        ryList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        ryList.setAdapter(adapter);
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
                ProcedureDevMouKniBean devMouKniBean = resourceBeen.get(adapterPosition);
                Connector.getDatabase();
                DataSupport.deleteAll(ProcedureDevMouKniBean.class,
                        "resource_type=? and resource_no=?", devMouKniBean.getResource_type(), devMouKniBean.getResource_no());
                resourceBeen.remove(adapterPosition);
                adapter.notifyDataSetChanged();
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
