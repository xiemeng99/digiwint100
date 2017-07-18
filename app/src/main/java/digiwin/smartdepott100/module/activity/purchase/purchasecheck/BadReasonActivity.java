package digiwin.smartdepott100.module.activity.purchase.purchasecheck;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.pulltorefreshlibrary.recyclerview.DividerItemDecoration;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseSwipeMenuAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.module.bean.purchase.BadReasonBean;
import digiwin.smartdepott100.module.bean.purchase.PurchaseCheckBean;
import digiwin.smartdepott100.module.bean.purchase.PurchaseCheckDetailBean;
import digiwin.smartdepott100.module.logic.purchase.PurcahseCheckLogic;

/**
 * 收获检验 不良原因维护
 * @author 唐孟宇
 */
public class BadReasonActivity extends BaseActivity {

    /**
     * 返回按钮
     */
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @OnClick(R.id.iv_back)
    void backClick(){
        onBackPressed();
    }
    /**
     * 模块名
     */
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    /**
     * 扫描框
     */
    @BindView(R.id.ll_et_input)
    LinearLayout ll_et_input;

    /**
     * 料号
     */
    @BindView(R.id.tv_item_no)
    TextView tv_item_no;
    /**
     * 检验项目
     */
    @BindView(R.id.tv_check_item)
    TextView tv_check_item;
    /**
     * 品名
     */
    @BindView(R.id.tv_item_name)
    TextView tv_item_name;
    /**
     * 规格
     */
    @BindView(R.id.tv_model)
    TextView tv_model;
    /**
     * AC
     */
    @BindView(R.id.tv_ac)
    TextView tv_ac;
    /**
     * RE
     */
    @BindView(R.id.tv_re)
    TextView tv_re;
    /**
     * 抽验量
     */
    @BindView(R.id.tv_select_check_num)
    TextView tv_select_check_num;
    /**
     * 缺点数
     */
    @BindView(R.id.tv_defect_num)
    TextView tv_defect_num;

    /**
     * 扫描框
     */
    @BindView(R.id.et_input)
    EditText et_input;

    @BindView(R.id.ll_content)
    LinearLayout ll_content;

    /**
     * 根据首字母查询结果
     */
    @BindView(R.id.rc_list_search_result)
    RecyclerView rc_list_search_result;

    final int BARCODEWHAT = 1234;

    final int BARCODEWHAT1 = 1235;

    @OnTextChanged(value = R.id.et_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void scanBarcode(CharSequence s){
        //TODO 扫描条码
        if(!StringUtils.isBlank(s.toString().trim())){
            mHandler.removeMessages(BARCODEWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(BARCODEWHAT, s.toString()), AddressContants.DELAYTIME);
        }

    }

    @BindView(R.id.rc_list)
    SwipeMenuRecyclerView rc_list;

    @BindView(R.id.commit)
    Button commit;

    /**
     * 确定按钮点击事件
     */
    @OnClick(R.id.commit)
    void commitSure(){
        if(badReasonList == null){
            showToast(R.string.please_input_bad_num);
            return;
        }
        //TODO 保存临时的不良原因数据，并传递到主界面
        EventBus.getDefault().post(badReasonList);
        pactivity.finish();
    }

    BadReasonActivity pactivity;

    PurcahseCheckLogic logic;

    BadReasonAdapter badReasonAdapter1;

    BaseSwipeMenuAdapter<BadReasonBean> badReasonAdapter;

    PurchaseCheckDetailBean purchaseCheckDetailBean = new PurchaseCheckDetailBean();

    PurchaseCheckBean purchaseCheckBean = new PurchaseCheckBean();

    List<BadReasonBean> badReasonList = new ArrayList<>();

    List<BadReasonBean> badReasonList1;
    @Override
    protected int bindLayoutId() {
        return R.layout.activity_bad_reason;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected void doBusiness() {
        pactivity = (BadReasonActivity) activity;
        logic = PurcahseCheckLogic.getInstance(pactivity,module,mTimestamp.toString());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(pactivity,2);
        rc_list.setLayoutManager(gridLayoutManager);
        LinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(pactivity);
        rc_list_search_result.setLayoutManager(linearLayoutManager);
        et_input.requestFocus();
        purchaseCheckDetailBean = (PurchaseCheckDetailBean)getIntent().getExtras().getSerializable("purchaseCheckDetailBean");
        purchaseCheckBean = (PurchaseCheckBean)getIntent().getExtras().getSerializable("purchaseCheckBean");
        try {
            badReasonList = (List<BadReasonBean>) getIntent().getExtras().getSerializable("badReasonList");
        if (null != badReasonList) {
            if (badReasonList.size() > 0) {
                badReasonAdapter = new BaseSwipeMenuAdapter<BadReasonBean>(pactivity, badReasonList) {
                    @Override
                    protected int getItemLayout(int viewType) {
                        return R.layout.ryitem_bad_reason_detail;
                    }

                    @Override
                    protected void bindData(RecyclerViewHolder holder, int position, BadReasonBean item) {
                        int color = (int) (Math.random() * 5);
                        if (color == 0) {
                            holder.setTextColor(R.id.tv_detail_reason, R.color.RED);
                        } else if (color == 1) {
                            holder.setTextColor(R.id.tv_detail_reason, R.color.green7d);
                        } else if (color == 2) {
                            holder.setTextColor(R.id.tv_detail_reason, R.color.yellow);
                        } else if (color == 3) {
                            holder.setTextColor(R.id.tv_detail_reason, R.color.gray);
                        } else {
                            holder.setTextColor(R.id.tv_detail_reason, R.color.result_points);
                        }
                        final EditText et_bad_num = holder.getEditText(R.id.tv_bad_num);
                        holder.setText(R.id.tv_detail_reason, item.getDefect_reason_name());
                        holder.setText(R.id.tv_bad_num, item.getDefect_qty());
                        holder.setVisibility(R.id.tv_bad_num, View.VISIBLE);
                        et_bad_num.setTag(position);
                        et_bad_num.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (!StringUtils.isBlank(s.toString().trim())) {
                                    int tag = (int) et_bad_num.getTag();
                                    badReasonList.get(tag).setDefect_qty(s.toString());
                                }
                            }
                        });
                    }
                };
                rc_list.setAdapter(badReasonAdapter);
                rc_list.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
                rc_list.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
                // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
                // 设置菜单Item点击监听。
                rc_list.setSwipeMenuItemClickListener(menuItemClickListener);
                // 设置菜单创建器。
                rc_list.setSwipeMenuCreator(swipeMenuCreator);
                rc_list.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
            }
        }else {
            Message msg = new Message();
            msg.what = BARCODEWHAT1;
            msg.obj = purchaseCheckDetailBean.getItem_no();
            mHandler.removeMessages(BARCODEWHAT1);
            mHandler.sendMessageDelayed(msg, AddressContants.DELAYTIME);
        }
        }catch(Exception e){
            e.printStackTrace();
        }

        tv_item_no.setText(purchaseCheckDetailBean.getItem_no());
        tv_check_item.setText(purchaseCheckDetailBean.getInspection_item());
        tv_item_name.setText(purchaseCheckBean.getItem_name());
        tv_model.setText(purchaseCheckBean.getItem_spec());
        tv_ac.setText(purchaseCheckDetailBean.getAc_qty());
        tv_re.setText(purchaseCheckDetailBean.getRe_qty());
        tv_select_check_num.setText(purchaseCheckDetailBean.getSample_qty());
        tv_defect_num.setText(purchaseCheckDetailBean.getDefect_qty());
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PURCHASECHECK;
        return module;
    }

    protected void initNavigationTitle() {
        tv_title_name.setText(R.string.bad_reason_maintenance);
    }

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case BARCODEWHAT:
                    Map<String, String> map = new HashMap<>();
                    map.put("defect_reason_zm", msg.obj.toString());
                    logic.getQCReasonInfo(map, new PurcahseCheckLogic.GetQCReasonListener() {
                        @Override
                        public void onSuccess(final List<BadReasonBean> badReasonBeenList) {
                            et_input.setText("");
                            if (badReasonBeenList.size() > 0) {
                                ll_content.setVisibility(View.GONE);
                                commit.setVisibility(View.GONE);
                                rc_list_search_result.setVisibility(View.VISIBLE);
                                badReasonList1 = new ArrayList<BadReasonBean>();
                                badReasonList1 = badReasonBeenList;
                                for (int i = 0; i < badReasonList1.size(); i++) {
                                    badReasonList1.get(i).setDefect_qty("1");
                                }
                                badReasonAdapter1 = new BadReasonAdapter(pactivity, badReasonList1);
                                rc_list_search_result.setAdapter(badReasonAdapter1);
                                badReasonAdapter1.setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View itemView, int position) {
                                        if (null != badReasonList) {
                                            if (badReasonList.size() > 0) {
                                                for (int i = 0; i < badReasonList.size(); i++) {
                                                    if (badReasonList.get(i).getDefect_reason_name().equals(badReasonList1.get(position).getDefect_reason_name())) {
                                                        showToast(R.string.this_reason_existed);
                                                        return;
                                                    }
                                                }
                                            }
                                            badReasonList.add(badReasonList1.get(position));
                                        } else {
                                            badReasonList = new ArrayList<BadReasonBean>();
                                            badReasonList.add(badReasonList1.get(position));
                                        }
                                        badReasonAdapter.notifyDataSetChanged();
                                        badReasonAdapter1.notifyDataSetChanged();
                                        ll_content.setVisibility(View.VISIBLE);
                                        commit.setVisibility(View.VISIBLE);
                                        rc_list_search_result.setVisibility(View.GONE);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error);
                        }
                    });
                    break;

                case BARCODEWHAT1:
                    try {
                        showLoadingDialog();
                        Map<String, String> hashMap = new HashMap<>();
                        hashMap.put(AddressContants.ITEM_NO, msg.obj.toString());
                        logic.getQCReasonTop5Info(hashMap, new PurcahseCheckLogic.GetQCReasonTop5Listener() {
                            @Override
                            public void onSuccess(List<BadReasonBean> badReasonBeenList) {
                                dismissLoadingDialog();
                                badReasonList = new ArrayList<BadReasonBean>();
                                badReasonList = badReasonBeenList;
                                if (badReasonList.size() > 0) {
                                    for (int i = 0; i < badReasonList.size(); i++) {
                                        badReasonList.get(i).setDefect_qty("0");
                                    }
                                }
                                badReasonAdapter = new BaseSwipeMenuAdapter<BadReasonBean>(pactivity, badReasonList) {
                                    @Override
                                    protected int getItemLayout(int viewType) {
                                        return R.layout.ryitem_bad_reason_detail;
                                    }

                                    @Override
                                    protected void bindData(RecyclerViewHolder holder, int position, BadReasonBean item) {
                                        int color = (int) (Math.random() * 5);
                                        if (color == 0) {
                                            holder.setTextColor(R.id.tv_detail_reason, R.color.RED);
                                        } else if (color == 1) {
                                            holder.setTextColor(R.id.tv_detail_reason, R.color.green7d);
                                        } else if (color == 2) {
                                            holder.setTextColor(R.id.tv_detail_reason, R.color.yellow);
                                        } else if (color == 3) {
                                            holder.setTextColor(R.id.tv_detail_reason, R.color.gray);
                                        } else {
                                            holder.setTextColor(R.id.tv_detail_reason, R.color.result_points);
                                        }
                                        final EditText et_bad_num = holder.getEditText(R.id.tv_bad_num);
                                        holder.setText(R.id.tv_detail_reason, item.getDefect_reason_name());
                                        holder.setText(R.id.tv_bad_num, item.getDefect_qty());
                                        holder.setVisibility(R.id.tv_bad_num, View.VISIBLE);
                                        et_bad_num.setTag(position);
                                        et_bad_num.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {
                                                if (!StringUtils.isBlank(s.toString().trim())) {
                                                    int tag = (int) et_bad_num.getTag();
                                                    badReasonList.get(tag).setDefect_qty(s.toString());
                                                }
                                            }
                                        });
                                    }
                                };
                                rc_list.setAdapter(badReasonAdapter);
                                rc_list.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
                                rc_list.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
                                // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
                                // 设置菜单Item点击监听。
                                rc_list.setSwipeMenuItemClickListener(menuItemClickListener);
                                // 设置菜单创建器。
                                rc_list.setSwipeMenuCreator(swipeMenuCreator);
                                rc_list.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
                            }

                            @Override
                            public void onFailed(String error) {
                                dismissLoadingDialog();
                                showFailedDialog(error);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    /**
     *  @des 不良原因 adapter
     */
    class BadReasonAdapter extends BaseRecyclerAdapter<BadReasonBean> {
        RecyclerViewHolder viewHolder = null;
        public BadReasonAdapter(Context ctx, List<BadReasonBean> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_bad_reason;
        }

        @Override
        protected void bindData(RecyclerViewHolder holder, int position, final BadReasonBean item) {
            viewHolder = holder;
            holder.setVisibility(R.id.tv_bad_num, View.GONE);
            holder.setText(R.id.tv_detail_reason,item.getDefect_reason_name());
        }

        public RecyclerViewHolder getHolder(){
            if(null != viewHolder){
                return viewHolder;
            }else{
                return null;
            }
        }

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
                badReasonList.remove(menuPosition);
                badReasonAdapter.notifyDataSetChanged();
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
//            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
