package digiwin.smartdepott100.module.activity.dailywork.procedurecheck;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.utils.LogUtils;
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
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCheckoutBadResBean;
import digiwin.smartdepott100.module.bean.dailywork.ProcedureCheckoutCommitBean;
import digiwin.smartdepott100.module.logic.dailywok.ProcedureCheckLogic;

/**
 * @author xiemeng
 * @des 生产报工出不良原因
 * @date 2017/5/21 14:41
 */

public class ProcedureCheckBadResActivity extends BaseTitleActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_circulation_no)
    TextView tvCirculationNo;
    @BindView(R.id.tv_pending_number)
    TextView tvPendingNumber;
    @BindView(R.id.ll_order_head)
    LinearLayout llOrderHead;
    @BindView(R.id.et_scan_barocde)
    EditText etScanBarocde;
    @BindView(R.id.ll_scan_barcode)
    LinearLayout llScanBarcode;
    @BindView(R.id.ll_form_content)
    LinearLayout llFormContent;
//    /**
//     * 搜索返回列表
//     */
//    @BindView(R.id.rc_list_search_result)
//    RecyclerView rcListSearchResult;
    /**
     * 已有列表
     */
    @BindView(R.id.rc_list)
    SwipeMenuRecyclerView rcList;
    @BindView(R.id.ll_content)
    LinearLayout llContent;


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
            for (int i=0;i<badResBeen.size();i++){
                if (s.toString().equals(badResBeen.get(i).getDefect_reason())){
                    showFailedDialog(R.string.badrea_scaned, new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                            etScanBarocde.setText("");
                        }
                    });
                    return;
                }
            }
            mHandler.removeMessages(DEFECTWHAT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(DEFECTWHAT, s.toString()), AddressContants.DELAYTIME);
        }
    }

    private List<ProcedureCheckoutBadResBean> badResBeen;


    private final int DEFECTWHAT=1001;

    public static final  String GETHEAD="head";

    public static final  String BADLIST="list";

    ProcedureCheckLogic procedureCheckLogic;
    /**
     * 搜索列表
     */
    SearchBadReaAdapter searchResultAdapter;
    /**
     * 已有列表
     */
    BaseSwipeMenuAdapter<ProcedureCheckoutBadResBean> hasBadReaAdapter;

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DEFECTWHAT:
                    HashMap<String, String> map = new HashMap<>();
                    map.put("defect_reason",etScanBarocde.getText().toString());
                    procedureCheckLogic.scanDefect(map, new ProcedureCheckLogic.ScanDefectListener() {
                        @Override
                        public void onSuccess(List<ProcedureCheckoutBadResBean> searchBadResBeen) {
                            if (searchBadResBeen.size()>0){
                                ProcedureCheckoutBadResBean clickBean = searchBadResBeen.get(0);
                                float sum=0;
                                for (ProcedureCheckoutBadResBean badResBean:badResBeen){
                                    sum+=StringUtils.string2Float(badResBean.getDefect_reason_qty());
                                }
                                try {
                                    float sub = StringUtils.sub(tvPendingNumber.getText().toString(), String.valueOf(sum));
                                    if (sub>0){
                                        clickBean.setDefect_reason_qty(String.valueOf(sub));
                                    }else {
                                        clickBean.setDefect_reason_qty("0");
                                        showToast(R.string.pendingnum_small_badsum);
                                    }
                                }catch (Exception e){
                                    clickBean.setDefect_reason_qty("0");
                                }
                                clickBean.setDefect_reason(etScanBarocde.getText().toString());
                                badResBeen.add(clickBean);
                                etScanBarocde.setText("");
                                showList();
//                                rcListSearchResult.setVisibility(View.VISIBLE);
//                                searchResultAdapter = new SearchBadReaAdapter(context, searchBadResBeen);
//                                rcListSearchResult.setAdapter(searchResultAdapter);
//                                searchResClick(searchBadResBeen);
                            }

                        }
                        @Override
                        public void onFailed(String error) {
                            showFailedDialog(error, new OnDialogClickListener() {
                                @Override
                                public void onCallback() {
                                    etScanBarocde.setText("");
//                                    rcListSearchResult.setVisibility(View.GONE);
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
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.PROCEDUCECHECK;
        return module;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.procedure_badres_title);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_procedurecheck_bad_reason;
    }

    @Override
    protected void doBusiness() {
        Bundle bundle = getIntent().getExtras();
        ProcedureCheckoutCommitBean commitBean = (ProcedureCheckoutCommitBean) bundle.getSerializable(GETHEAD);
        tvCirculationNo.setText(commitBean.getSubop_no());
        tvPendingNumber.setText(commitBean.getDefect_qty());

     //   GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,2);
      //  rcListSearchResult.setLayoutManager(gridLayoutManager);
        LinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(activity);
        rcList.setLayoutManager(linearLayoutManager);
        badResBeen = (List<ProcedureCheckoutBadResBean>)bundle.getSerializable(BADLIST);
        procedureCheckLogic=ProcedureCheckLogic.getInstance(activity,module,mTimestamp.toString());
        showList();
    }

    /**
     * 点击搜索的返回列表
     * @param searchResBeen
     */
    private void searchResClick(final List<ProcedureCheckoutBadResBean> searchResBeen){
        searchResultAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ProcedureCheckoutBadResBean clickBean = searchResBeen.get(i);
                float sum=0;
                for (ProcedureCheckoutBadResBean badResBean:badResBeen){
                    sum+=StringUtils.string2Float(badResBean.getDefect_reason_qty());
                }
                try {
                    float sub = StringUtils.sub(tvPendingNumber.getText().toString(), String.valueOf(sum));
                    if (sub>=0){
                        clickBean.setDefect_reason_qty(String.valueOf(sub));
                    }else {
                        clickBean.setDefect_reason_qty("0");
                        showToast(R.string.pendingnum_small_badsum);
                    }
                }catch (Exception e){
                    clickBean.setDefect_reason_qty("0");
                }
                clickBean.setDefect_reason(etScanBarocde.getText().toString());
                badResBeen.add(clickBean);
                showList();
                etScanBarocde.setText("");
            }
        });

    }

    /**
     * 展示列表
     */
    private void showList(){
                hasBadReaAdapter = new BaseSwipeMenuAdapter<ProcedureCheckoutBadResBean>(activity, badResBeen) {
                    @Override
                    protected int getItemLayout(int viewType) {
                        return R.layout.ryitem_procedure_checkout_hasbad;
                    }

                    @Override
                    protected void bindData(RecyclerViewHolder holder, final int position,final ProcedureCheckoutBadResBean item) {
                        holder.setText(R.id.tv_badres_name,item.getDefect_reason_name());
                        holder.setText(R.id.et__badres_num,item.getDefect_reason_qty());
                        LogUtils.i(TAG,"position"+position+"--item="+item.toString());
                        EditText editText = holder.getEditText(R.id.et__badres_num);
                        editText.setTag(position);
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (!StringUtils.isBlank(s.toString().trim())) {
                                    item.setDefect_reason_qty(s.toString());
                                }
                            }
                        });

                        holder.getEditText(R.id.et__badres_num).setOnEditorActionListener(new TextView.OnEditorActionListener()
                        {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                            {
                                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE
                                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()))
                                {
                                    etScanBarocde.requestFocus();
                                }
                                return false;
                            }
                        });
                    }
                };


                rcList.setAdapter(hasBadReaAdapter);
                rcList.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
                rcList.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
                // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
                // 设置菜单Item点击监听。
                rcList.setSwipeMenuItemClickListener(menuItemClickListener);
                // 设置菜单创建器。
                rcList.setSwipeMenuCreator(swipeMenuCreator);
                rcList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));

    }
    @Override
    protected void onPause()
    {
        super.onPause();
        EventBus.getDefault().post(badResBeen);
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
                badResBeen.remove(adapterPosition);
                for (int i=0;i<badResBeen.size();i++){
                  LogUtils.i(TAG,i+"badResBeen="+badResBeen.toString());
                }
                showList();
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


    /**
     *  @des搜索 adapter
     */
    class SearchBadReaAdapter extends BaseRecyclerAdapter<ProcedureCheckoutBadResBean> {
        public SearchBadReaAdapter(Context ctx, List<ProcedureCheckoutBadResBean> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_procedure_ser_bad;
        }

        @Override
        protected void bindData(RecyclerViewHolder holder,final int position, final ProcedureCheckoutBadResBean item) {
            int color = (int) (Math.random() * 5);
            if (color == 0) {
                holder.setTextColor(R.id.tv_detail_reason,getResources().getColor(R.color.RED));
            } else if (color == 1) {
                holder.setTextColor(R.id.tv_detail_reason, R.color.green7d);
            } else if (color == 2) {
                holder.setTextColor(R.id.tv_detail_reason, R.color.yellow);
            } else if (color == 3) {
                holder.setTextColor(R.id.tv_detail_reason, R.color.gray);
            } else {
                holder.setTextColor(R.id.tv_detail_reason, R.color.result_points);
            }
            holder.setText(R.id.tv_detail_reason, item.getDefect_reason_name());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
