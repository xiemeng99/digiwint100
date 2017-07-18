package digiwin.smartdepott100.module.activity.produce.enchaseprint;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.pulltorefreshlibrary.recyclerview.DividerItemDecoration;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseSwipeMenuAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.core.printer.BlueToothManager;
import digiwin.smartdepott100.main.logic.ToSettingLogic;
import digiwin.smartdepott100.module.bean.common.ScanBarcodeBackBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;

/**
 * @author xiemeng
 * @des 产线装箱
 * @date 2017/3/21
 */
public class EnchasePrintActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.img_printer)
    ImageView imgPrinter;
    @BindView(R.id.cb_ischoose)
    CheckBox cbIschoose;
    @BindView(R.id.tv_print_count)
    TextView tvPrintCount;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.et_barcode)
    TextView etBarcode;
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_box_code)
    TextView tvBoxCode;
    @BindView(R.id.et_boxcode)
    EditText etBoxcode;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.ll_box_code)
    LinearLayout llBoxCode;
    @BindView(R.id.rv_swipemenu)
    SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    @OnClick(R.id.iv_back)
    void ivBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_title_name)
    void tvTitleBack() {
        onBackPressed();
    }

    @OnTextChanged(value = R.id.et_barcode, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void scanBarcode(CharSequence s) {
        if (!StringUtils.isBlank(s.toString())) {
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    @OnClick(R.id.img_printer)
    void clickPrint() {
        print();
    }

    /**
     * 数据
     */
    List<ScanBarcodeBackBean> scanList;

    CommonLogic logic;

    private BaseSwipeMenuAdapter<ScanBarcodeBackBean> adapter;

    private final int BARCODEWHAT = 1001;

    private int printCout;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    HashMap<String, String> barcodeMap = new HashMap<>();
                    barcodeMap.put(AddressContants.BARCODE_NO, String.valueOf(msg.obj));

                    logic.scanBarcode(barcodeMap, new CommonLogic.ScanBarcodeListener() {
                        @Override
                        public void onSuccess(ScanBarcodeBackBean barcodeBackBean) {
                            scanList.add(barcodeBackBean);
                            adapter.notifyDataSetChanged();
                            etBarcode.setText("");
                            tvNum.setText(scanList.size() + "/6");
                            if (cbIschoose.isChecked() && scanList.size() == 6) {
                                print();
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etBarcode.setText("");
                                }
                            });
                        }
                    });
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_enchaseprint;
    }

    @Override
    protected void initNavigationTitle() {
        toolbarTitle.setBackgroundResource(R.color.toolBar_color);
        setSupportActionBar(toolbarTitle);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void doBusiness() {
        etBoxcode.setKeyListener(null);
        printCout = 0;
        etBoxcode.setText(String.valueOf(printCout + 1));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        logic = CommonLogic.getInstance(activity, module, mTimestamp.toString());
        scanList = new ArrayList<>();
        showList();
        tvPrintCount.setText(String.valueOf(printCout));
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.ENCHASEPRINT;
        return module;
    }

    /**
     * 显示数据
     */
    private void showList() {
        adapter = new BaseSwipeMenuAdapter<ScanBarcodeBackBean>(this, scanList) {
            @Override
            protected int getItemLayout(int viewType) {
                return R.layout.ryitem_enchaseprint;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, ScanBarcodeBackBean item) {
                holder.setText(R.id.tv_item_seq, String.valueOf(position + 1));
                holder.setText(R.id.tv_item_type, item.getItem_barcode_type());
                holder.setText(R.id.tv_barcode, item.getBarcode_no());
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
                try {
                    scanList.remove(adapterPosition);
                    adapter.notifyDataSetChanged();
                    tvNum.setText(scanList.size() + "/6");
                } catch (Exception e) {
                    LogUtils.e(TAG, "menuItemClickListener错误" + e);
                }
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
            SwipeMenuItem deleteItem = new SwipeMenuItem(activity)
                    .setBackgroundDrawable(R.drawable.swipe_delete)
                    .setText(R.string.delete)// 文字，还可以设置文字颜色，大小等
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    /**
     * 打印
     */
    private void print() {
        boolean isOpen = BlueToothManager.getManager(activity).isOpen();
        if (!isOpen) {
            ToSettingLogic.showToSetdialog(activity, R.string.title_set_bluttooth);
        } else {
            BlueToothManager.getManager(activity).printBarocde(String.valueOf(printCout + 1));
        }
        scanList.clear();
        adapter.notifyDataSetChanged();
        printCout++;
        etBoxcode.setText(String.valueOf(printCout + 1));
        tvPrintCount.setText(String.valueOf(printCout));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
