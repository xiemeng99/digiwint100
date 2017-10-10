package digiwin.smartdepott100.module.activity.purchase.quickstorage;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ObjectAndMapUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.WeakRefHandler;
import digiwin.pulltorefreshlibrary.recyclerview.FullyLinearLayoutManager;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseFirstModuldeActivity;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.main.activity.settingdialog.StorageDialog;
import digiwin.smartdepott100.main.logic.GetStorageLogic;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.QuickStorageLogic;



/**
 * @author 孙长权
 * @module 快速入库
 * @date 2017/6/21
 */

public class QuickStorageActivity extends BaseFirstModuldeActivity {

    private QuickStorageActivity activity;

    /**
     * 扫描入库单
     */
    public final int QUICKSTORAGECODE = 1003;

    @BindView(R.id.toolbar_title)
    Toolbar toolbar;

    /**
     * 单号
     */
    @BindView(R.id.tv_post_material_order)
    TextView tv_post_material_order;

    /**
     * 日期
     */
    @BindView(R.id.tv_date)
    TextView tv_date;

    /**
     * 供应商
     */
    @BindView(R.id.tv_supplier)
    TextView tv_supplier;

    @BindView(R.id.ry_list)
    RecyclerView recyclerView;

    QuickStorageLogic quickStorageLogic;

    List<ListSumBean> checkedList;

    /**
     * 提交
     */
    @BindView(R.id.commit)
    Button commit;

    @OnClick(R.id.commit)
    void commit() {
        try {
            if (checkedList.size() > 0) {
                showCommitSureDialog(new OnDialogTwoListener() {
                    @Override
                    public void onCallback1() {
                        showLoadingDialog();
                        List<ListSumBean> commitList = new ArrayList<ListSumBean>();
                        for (int i = 0; i < checkedList.size(); i++) {
                            if (!StringUtils.isBlank(checkedList.get(i).getScan_sumqty()) ||
                                    StringUtils.string2Float(checkedList.get(i).getScan_sumqty()) > 0) {
                                checkedList.get(i).setReceipt_qty(StringUtils.deleteZero(checkedList.get(i).getApply_qty()));
                                checkedList.get(i).setQty(StringUtils.deleteZero(checkedList.get(i).getScan_sumqty()));
                                commitList.add(checkedList.get(i));
                            }
//                            if (!StringUtils.isBlank(checkedList.get(i).getMatch_qty()) ||
//                                    StringUtils.string2Float(checkedList.get(i).getMatch_qty()) > 0) {
//                                checkedList.get(i).setReceipt_qty(StringUtils.deleteZero(checkedList.get(i).getReq_qty()));
//                                checkedList.get(i).setQty(StringUtils.deleteZero(checkedList.get(i).getMatch_qty()));
//                                commitList.add(checkedList.get(i));
//                            }
                        }
                        commitData(commitList);
                    }

                    @Override
                    public void onCallback2() {

                    }
                });
            } else {
                showFailedDialog(getResources().getString(R.string.nodate));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showFailedDialog(getResources().getString(R.string.nodate));
        }
    }

    QuickStorageAdapter adapter;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_quickstorage;
    }

    @Override
    protected void doBusiness() {
        checkedList = new ArrayList<ListSumBean>();
        quickStorageLogic = QuickStorageLogic.getInstance(activity, activity.module, activity.mTimestamp.toString());
        FullyLinearLayoutManager fullylinearlayoutmanager = new FullyLinearLayoutManager(activity);
        recyclerView.setLayoutManager(fullylinearlayoutmanager);

        FilterResultOrderBean data = (FilterResultOrderBean) getIntent().getSerializableExtra("data");
        tv_post_material_order.setText(data.getDoc_no());
        tv_date.setText(data.getReceipt_date());
        tv_supplier.setText(data.getSupplier_name());

        mHandler.removeMessages(QUICKSTORAGECODE);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(QUICKSTORAGECODE, data.getDoc_no()), AddressContants.DELAYTIME);

    }

    private Handler.Callback mCallback= new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == QUICKSTORAGECODE) {//获取汇总数据
                showLoadingDialog();
                ClickItemPutBean putBean = new ClickItemPutBean();
                putBean.setDoc_no(String.valueOf(msg.obj));
//                putBean.setWarehouse_in_no(LoginLogic.getWare());
                quickStorageLogic.getQuickStorageOrderSumData(putBean, new CommonLogic.GetOrderSumListener() {
                    @Override
                    public void onSuccess(List<ListSumBean> list) {
                        dismissLoadingDialog();
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).setWarehouse_no(LoginLogic.getWare());
                        }
                        checkedList = list;
                        adapter = new QuickStorageAdapter(activity, checkedList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showFailedDialog(error);
                    }
                });
            }
            return false;
        }
    };

    private Handler mHandler = new WeakRefHandler(mCallback);

    public void clearData() {
        tv_post_material_order.setText("");
        tv_date.setText("");
        tv_supplier.setText("");
        checkedList.clear();
        adapter = new QuickStorageAdapter(activity, checkedList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
    }

    public void commitData(final List<ListSumBean> checkedList) {
        final List<Map<String, String>> listMap = ObjectAndMapUtils.getListMap(checkedList);
        quickStorageLogic.commitQuickStorageList(listMap, new CommonLogic.CommitListListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                showCommitSuccessDialog(msg, new OnDialogClickListener() {
                    @Override
                    public void onCallback() {
                        quickStorageLogic = QuickStorageLogic.getInstance(activity, activity.module, activity.mTimestamp.toString());
                        activity.finish();
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbar;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.QUICKSTORAGE;
        return module;
    }

    @Override
    public ExitMode exitOrDel() {
        return ExitMode.EXITD;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        activity = this;
        mName.setText(R.string.title_quickstorage);
    }

    public class QuickStorageAdapter extends BaseRecyclerAdapter<ListSumBean> {

        public QuickStorageAdapter(final Context ctx, List<ListSumBean> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_quickstorage;
        }

        @Override
        protected void bindData(final RecyclerViewHolder holder, final int position, final ListSumBean item) {
            //默认申请量等于入库量
            float numb1 = StringUtils.string2Float(item.getApply_qty());
            //默认匹配量等于实际量
            float numb2 = StringUtils.string2Float(item.getScan_sumqty());
//            float numb1 = StringUtils.string2Float(item.getReq_qty());
//            float numb2 = StringUtils.string2Float(item.getMatch_qty());

            numChange(numb1, numb2, holder);

            holder.setText(R.id.tv_item_seq, item.getSeq());
            holder.setText(R.id.tv_item_name, item.getItem_name());
            holder.setText(R.id.tv_unit, item.getUnit_no());
            holder.setText(R.id.tv_item_format, item.getItem_spec());
            holder.setText(R.id.tv_item_no, item.getItem_no());
            holder.setText(R.id.tv_storage, item.getWarehouse_no());
            holder.setText(R.id.tv_in_storage_number, StringUtils.deleteZero(item.getApply_qty()));//入库量
            holder.setText(R.id.tv_match_number, StringUtils.deleteZero(item.getScan_sumqty()));//实际量

//            holder.setText(R.id.tv_in_storage_number, StringUtils.deleteZero(item.getReq_qty()));//入库量
//            holder.setText(R.id.tv_match_number, StringUtils.deleteZero(item.getMatch_qty()));

            final TextView wareHouseTv = holder.findViewById(R.id.tv_storage);
            wareHouseTv.setTag(position);
            final List<String> list = GetStorageLogic.getWareString();

            final LinearLayout warehouse_img_ll = holder.findViewById(R.id.warehouse_img_ll);
            warehouse_img_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StorageDialog.showStorageDialog(activity, LoginLogic.getWare(), list, new StorageDialog.StorageCallBack() {
                        @Override
                        public void storageCallBack(String chooseStorage) {
                            item.setWarehouse_no(chooseStorage);
                            holder.setText(R.id.tv_storage, chooseStorage);
                        }
                    });
                }
            });

            final EditText match_numberEt = holder.findViewById(R.id.tv_match_number);
            match_numberEt.setTag(position);
            holder.getEditText(R.id.tv_match_number).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(final Editable s) {
                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (StringUtils.isBlank(s.toString().trim()) || (".").equals(s.toString().trim())) {
                                checkedList.get((int) match_numberEt.getTag()).setScan_sumqty("0");
                                numChange(StringUtils.string2Float(item.getApply_qty()), StringUtils.string2Float("0"), holder);
                                notifyItemChanged(position);
                            } else if (StringUtils.string2Float(item.getApply_qty()) < StringUtils.string2Float(s.toString())) {
                                showFailedDialog(getResources().getString(R.string.match_so_big));
                                checkedList.get((int) match_numberEt.getTag()).setScan_sumqty(item.getScan_sumqty());
                                numChange(StringUtils.string2Float(item.getApply_qty()), StringUtils.string2Float(s.toString()), holder);
                                notifyItemChanged(position);
                            } else {
                                checkedList.get((int) match_numberEt.getTag()).setScan_sumqty(s.toString());
                                numChange(StringUtils.string2Float(item.getApply_qty()), StringUtils.string2Float(s.toString()), holder);
                            }
                        }
                    },1000);
                }
            });
        }

        @SuppressWarnings("ResourceType")
        public void numChange(float numb1, float numb2, RecyclerViewHolder holder) {

            TypedArray a = mContext.obtainStyledAttributes(new int[]{R.attr.sumColor_1, R.attr.sumColor_2, R.attr.sumColor_3});


            if (numb2 == 0) {
                holder.setBackground(R.id.item_ll, R.drawable.red_scandetail_bg);
                holder.setTextColor(R.id.tv_item_seq, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_item_name, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_unit, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_item_format, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_item_no, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_storage, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_in_storage_number, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
                holder.setTextColor(R.id.tv_match_number, a.getColor(0,mContext.getResources().getColor(R.color.Base_color)));
//                holder.setBackground(R.id.warehouse_img_ll, R.drawable.numchange_bg_red);
//                holder.setBackground(R.id.match_num_ll, R.drawable.numchange_bg_red);

            } else if (numb1 > numb2) {

                holder.setBackground(R.id.item_ll, R.drawable.yellow_scandetail_bg);
                holder.setTextColor(R.id.tv_item_seq, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_item_name, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_unit, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_item_format, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_item_no, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_storage, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_in_storage_number, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
                holder.setTextColor(R.id.tv_match_number, a.getColor(1,mContext.getResources().getColor(R.color.outside_yellow)));
//                holder.setBackground(R.id.warehouse_img_ll, R.drawable.numchange_bg_yellow);
//                holder.setBackground(R.id.match_num_ll, R.drawable.numchange_bg_yellow);
            } else if (numb1 == numb2) {
                holder.setBackground(R.id.item_ll, R.drawable.green_scandetail_bg);
                holder.setTextColor(R.id.tv_item_seq, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_item_name, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_unit, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_item_format, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_item_no, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_storage, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_in_storage_number, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
                holder.setTextColor(R.id.tv_match_number, a.getColor(2,mContext.getResources().getColor(R.color.green1b)));
//                holder.setBackground(R.id.warehouse_img_ll, R.drawable.numchange_bg_green);
//                holder.setBackground(R.id.match_num_ll, R.drawable.numchange_bg_green);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
