package digiwin.smartdepott100.main.logic;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digiwin.library.utils.LogUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.main.activity.MainActivity;
import digiwin.smartdepott100.main.bean.ModuleBean;
import digiwin.smartdepott100.main.bean.TotalMode;

import static android.util.TypedValue.applyDimension;

/**
 * Created by ChangquanSun
 * 2017/2/22
 */

public class MainLogic {
    private final String TAG = "MainLogic";
    private int screenWidth;
    private int titleValue;
    private int oldIndex;
    private MainActivity activity;
    /**
     * 采购管理
     */
    private List<ModuleBean> purchaseItems;
    /**
     * 生产管理
     */
    private List<ModuleBean> produceItems;
    /**
     * 库存管理
     */
    private List<ModuleBean> storageItems;
    /**
     * 销售管理
     */
    private List<ModuleBean> salesItems;
    /**
     * 报工管理
     */
    private List<ModuleBean> dailyworkItems;
    /**
     * 看板管理
     */
    private List<ModuleBean> boardItems;
    /**
     * 用户权限模块
     */
    private List<String> powerItems;

    public MainLogic(Context context) {
        activity = (MainActivity) context;
    }

    public static List<ModuleBean> ModuleList = new ArrayList<>();

    /**
     * 初始化各个模块
     *
     * @param powerItems     权限数据源
     * @param purchaseItems  采购管理数据源
     * @param produceItems   生产管理数据源
     * @param storageItems   库存管理数据源
     * @param salesItems     销售管理数据源
     * @param dailyworkItems 报工管理数据源
     */
    public void initModule(Context context, List<String> powerItems, List<ModuleBean> purchaseItems,
                           List<ModuleBean> produceItems, List<ModuleBean> storageItems
            , List<ModuleBean> salesItems, List<ModuleBean> dailyworkItems, List<ModuleBean> boardItems) {
        this.powerItems = powerItems;
        this.purchaseItems = purchaseItems;
        this.produceItems = produceItems;
        this.storageItems = storageItems;
        this.salesItems = salesItems;
        this.dailyworkItems = dailyworkItems;
        this.boardItems = boardItems;

        //初始化采购管理

        ModuleBean purchaseInStore = new ModuleBean(R.string.purchase_in_store, R.mipmap.saomaruku, ModuleCode.PURCHASEINSTORE, "android.intent.actiont100.digiwin.PurchaseInStoreActivity");

        ModuleBean materialreceipt = new ModuleBean(R.string.title_material_receipt, R.mipmap.material_receipt, ModuleCode.MATERIALRECEIPTCODE, "android.intent.actiont100.digiwin.MaterialReceiptActivity");

        ModuleBean purchaseGoodsScan = new ModuleBean(R.string.title_purchase_goods_scan, R.mipmap.scan_inapection, ModuleCode.PURCHASEGOODSSCAN, "android.intent.actiont100.digiwin.PurchaseGoodsScanActivity");

        ModuleBean purchaseCheck = new ModuleBean(R.string.purchase_check, R.mipmap.inspection_tests, ModuleCode.PURCHASECHECK, "android.intent.actiont100.digiwin.PurchaseCheckActivity");

        ModuleBean storeReturnMaterial = new ModuleBean(R.string.store_return_material, R.mipmap.warehouse_return, ModuleCode.STORERETURNMATERIAL, "android.intent.actiont100.digiwin.StoreReturnMaterialListActivity");

        ModuleBean quickstorage = new ModuleBean(R.string.title_quickstorage, R.mipmap.quickly_storage, ModuleCode.QUICKSTORAGE, "android.intent.actiont100.digiwin.QuickStorageListActivity");

        ModuleBean pqcCheck = new ModuleBean(R.string.pqc_check, R.mipmap.pqc, ModuleCode.PQCCHECK, "android.intent.actiont100.digiwin.PQCCheckActivity");

        purchaseItems.add(materialreceipt);
        purchaseItems.add(quickstorage);
        purchaseItems.add(purchaseCheck);
        purchaseItems.add(purchaseGoodsScan);
        purchaseItems.add(purchaseInStore);
        purchaseItems.add(storeReturnMaterial);
        purchaseItems.add(pqcCheck);


        //初始化生产管理
        ModuleBean finishedStorageActivity = new ModuleBean(R.string.finishedstorage, R.mipmap.finishedstorage, ModuleCode.FINISHEDSTORAGE, "android.intent.actiont100.digiwin.FinishedStorageActivity");

        ModuleBean transfersToReviewActivity = new ModuleBean(R.string.transfers_to_review, R.mipmap.diaobofuhe, ModuleCode.TRANSFERS_TO_REVIEW, "android.intent.actiont100.digiwin.TransfersToReviewActivity");

        ModuleBean accordingMaterialActivity = new ModuleBean(R.string.according_material, R.mipmap.accordingmaterial, ModuleCode.ACCORDINGMATERIALCODE, "android.intent.actiont100.digiwin.AccordingMaterialActivity");

        ModuleBean distribute = new ModuleBean(R.string.distribute, R.mipmap.shengchanpeihuo, ModuleCode.DISTRIBUTE, "android.intent.actiont100.digiwin.DistributeActivity");

        ModuleBean postMaterial = new ModuleBean(R.string.post_material, R.mipmap.lingliaoguozahng, ModuleCode.POSTMATERIAL, "android.intent.actiont100.digiwin.PostMaterialActivity");

        ModuleBean putInStore = new ModuleBean(R.string.put_in_store, R.mipmap.putaway, ModuleCode.PUTINSTORE, "android.intent.actiont100.digiwin.PutInStoreActivity");

        ModuleBean materialReturning = new ModuleBean(R.string.mataerial_returning, R.mipmap.return_of_material, ModuleCode.MATERIALRETURNING, "android.intent.actiont100.digiwin.MaterialReturnListActivity");

        ModuleBean driectStorage = new ModuleBean(R.string.direct_storage, R.mipmap.direct_storage, ModuleCode.DIRECTSTORAGE, "android.intent.actiont100.digiwin.DirectStorageActivity");

        ModuleBean workorder = new ModuleBean(R.string.title_work_order, R.mipmap.work_order, ModuleCode.WORKORDERCODE, "android.intent.actiont100.digiwin.WorkOrderActivity");

        ModuleBean completingstore = new ModuleBean(R.string.title_completing_store, R.mipmap.complete_storage, ModuleCode.COMPLETINGSTORE, "android.intent.actiont100.digiwin.CompletingStoreActivity");

        ModuleBean enchaseprint = new ModuleBean(R.string.enchaseprint, R.mipmap.enchaseprint, ModuleCode.ENCHASEPRINT, "android.intent.actiont100.digiwin.EnchasePrintActivity");

        ModuleBean workorderreturnlistactivity = new ModuleBean(R.string.work_order_return, R.mipmap.work_order_return, ModuleCode.WORKORDERRETURN, "android.intent.actiont100.digiwin.WorkOrderReturnListActivity");

        ModuleBean worksupplementlist = new ModuleBean(R.string.title_worksupplement, R.mipmap.ordermaterial, ModuleCode.WORKSUPPLEMENT, "android.intent.actiont100.digiwin.WorkSupplementListActivity");

        ModuleBean productionleaderlist = new ModuleBean(R.string.title_production_leader, R.mipmap.production_receive, ModuleCode.PRODUCTIONLEADER, "android.intent.actiont100.digiwin.ProductionLeaderListActivity");

        ModuleBean inbinninglist = new ModuleBean(R.string.title_in_binning, R.mipmap.inbox, ModuleCode.INBINNING, "android.intent.actiont100.digiwin.InBinningListActivity");

        ModuleBean endproductAllot = new ModuleBean(R.string.endproduct_allot, R.mipmap.endproduct_allot, ModuleCode.ENDPRODUCTALLOT, "android.intent.actiont100.digiwin.EndProductAllotActivity");
        ModuleBean linesend = new ModuleBean(R.string.line_send, R.mipmap.xianbianfaliao, ModuleCode.LINESEND, "android.intent.actiont100.digiwin.LineSendActivity");

        produceItems.add(finishedStorageActivity);

        produceItems.add(transfersToReviewActivity);

        produceItems.add(accordingMaterialActivity);

        produceItems.add(distribute);

        produceItems.add(postMaterial);

        produceItems.add(putInStore);

        produceItems.add(worksupplementlist);

        produceItems.add(materialReturning);

        produceItems.add(workorder);

        produceItems.add(driectStorage);

        produceItems.add(completingstore);

        produceItems.add(enchaseprint);

        produceItems.add(workorderreturnlistactivity);

        produceItems.add(productionleaderlist);

        produceItems.add(inbinninglist);

        produceItems.add(endproductAllot);

        produceItems.add(linesend);

        //初始化库存管理
        ModuleBean storeallotactivity = new ModuleBean(R.string.nocome_allot, R.mipmap.nocome_alllot, ModuleCode.NOCOMESTOREALLOT, "android.intent.actiont100.digiwin.StoreAllotActivity");
        storageItems.add(storeallotactivity);
        ModuleBean miscellaneousoutactivity = new ModuleBean(R.string.miscellaneous_issues_out, R.mipmap.receiptout, ModuleCode.MISCELLANEOUSISSUESOUT, "android.intent.actiont100.digiwin.MiscellaneousissuesOutActivity");
        storageItems.add(miscellaneousoutactivity);
        ModuleBean miscellaneousainctivity = new ModuleBean(R.string.miscellaneous_issues_in, R.mipmap.shouliao, ModuleCode.MISCELLANEOUSISSUESIN, "android.intent.actiont100.digiwin.MiscellaneousissuesInActivity");
        storageItems.add(miscellaneousainctivity);
        ModuleBean storeQueryActivity = new ModuleBean(R.string.store_query, R.mipmap.store_query, ModuleCode.STOREQUERY, "android.intent.actiont100.digiwin.StoreQueryActivity");
        storageItems.add(storeQueryActivity);
        ModuleBean printLabelActivity = new ModuleBean(R.string.print_label, R.mipmap.bar_code, ModuleCode.PRINTLABEL, "android.intent.actiont100.digiwin.PrintLabelActivity");
        storageItems.add(printLabelActivity);
        ModuleBean movestoreactivity = new ModuleBean(R.string.movestore, R.mipmap.movestore, ModuleCode.MOVESTORE, "android.intent.actiont100.digiwin.MoveStoreActivity");
        storageItems.add(movestoreactivity);
        ModuleBean storetranslockactivity = new ModuleBean(R.string.store_trans_lock, R.mipmap.inventory_lock, ModuleCode.STORETRANSLOCK, "android.intent.actiont100.digiwin.StoreTransLockActivity");
        storageItems.add(storetranslockactivity);
        ModuleBean storetransunlockactivity = new ModuleBean(R.string.store_trans_unlock, R.mipmap.inventory_deblocking, ModuleCode.STORETRANSUNLOCK, "android.intent.actiont100.digiwin.StoreTransUnlockActivity");
        storageItems.add(storetransunlockactivity);
        ModuleBean productbinning = new ModuleBean(R.string.product_binning, R.drawable.encasement, ModuleCode.PRODUCTBINNING, "android.intent.actiont100.digiwin.ProductBinningActivity");
        storageItems.add(productbinning);
        ModuleBean productoutbox = new ModuleBean(R.string.product_outbox, R.mipmap.outbox, ModuleCode.PRODUCTOUTBOX, "android.intent.actiont100.digiwin.ProductOutBoxActivity");
        storageItems.add(productoutbox);
        ModuleBean stockcheck = new ModuleBean(R.string.check_stock, R.mipmap.kucunpandian, ModuleCode.STORECHECK, "android.intent.actiont100.digiwin.StockCheckListActivity");
        storageItems.add(stockcheck);
        ModuleBean postallocateactivity = new ModuleBean(R.string.title_post_allocate, R.mipmap.allot_post, ModuleCode.POSTALLOCATE, "android.intent.actiont100.digiwin.PostAllocateActivity");
        storageItems.add(postallocateactivity);

        //初始化销售管理
        ModuleBean saleoutletactivity = new ModuleBean(R.string.saleoutlet, R.mipmap.saleoutlet, ModuleCode.SALEOUTLET, "android.intent.actiont100.digiwin.SaleOutletListActivity");
        ModuleBean pickupshipment = new ModuleBean(R.string.title_pickupshipment, R.mipmap.pickup_shipment, ModuleCode.PICKUPSHIPMENT, "android.intent.actiont100.digiwin.PickUpShipmentListActivity");
        ModuleBean saleReturnActivity = new ModuleBean(R.string.title_sale_return, R.mipmap.ntsale_return, ModuleCode.SALERETURN, "android.intent.actiont100.digiwin.SaleReturnActivity");
        ModuleBean scanOutStoreActivity = new ModuleBean(R.string.scan_out_store, R.mipmap.scan_shipment, ModuleCode.SCANOUTSTORE, "android.intent.actiont100.digiwin.ScanOutStoreListActivity");
        ModuleBean tranceProductActivity = new ModuleBean(R.string.trace_product_quality, R.mipmap.quality_retrospect, ModuleCode.TRANSPRODUCTQUALITY, "android.intent.actiont100.digiwin.TraceProductActivity");
        ModuleBean orderSaleActivity = new ModuleBean(R.string.ordersale, R.mipmap.ordersale, ModuleCode.ORDERSALE, "开发中");
        salesItems.add(saleoutletactivity);
        salesItems.add(pickupshipment);
        salesItems.add(saleReturnActivity);
        salesItems.add(scanOutStoreActivity);
        salesItems.add(orderSaleActivity);
        salesItems.add(tranceProductActivity);

        //初始化报工管理
        ModuleBean rcttboardactivity = new ModuleBean(R.string.delivery_uncheck_board, R.drawable.receiptout, ModuleCode.RCCTBOARD, "android.intent.actiont100.digiwin.RcttBoardActivity");
        ModuleBean tctsboardactivity = new ModuleBean(R.string.tcts_board, R.drawable.receiptout, ModuleCode.RCCTBOARD, "android.intent.actiont100.digiwin.TctsBoardActivity");


        ModuleBean palletreport = new ModuleBean(R.string.title_pallet_report, R.mipmap.pallet_report, ModuleCode.PROCESSREPORTING, "android.intent.actiont100.digiwin.ProcessReportingActivity");
        dailyworkItems.add(palletreport);
        //开工扫描
        ModuleBean startwork = new ModuleBean(R.string.start_scan, R.mipmap.start_working, ModuleCode.STARTWORKSCAN, "android.intent.actiont100.digiwin.StartWorkActivity");
        dailyworkItems.add(startwork);
        //完工扫描
        ModuleBean finishwork = new ModuleBean(R.string.finish_scan, R.mipmap.compete_working, ModuleCode.FINISHWORKSCAN, "android.intent.actiont100.digiwin.FinishWorkActivity");
        dailyworkItems.add(finishwork);
        //扫入扫描
        ModuleBean entrance = new ModuleBean(R.string.scanin_scan, R.mipmap.entrance, ModuleCode.SCANINSCAN, "android.intent.actiont100.digiwin.EntranceActivity");
        dailyworkItems.add(entrance);
        //扫出扫描
        ModuleBean stockremoval = new ModuleBean(R.string.scanout_scan, R.mipmap.stock_removal, ModuleCode.SCANOUTSCAN, "android.intent.actiont100.digiwin.StockRemovalActivity");
        dailyworkItems.add(stockremoval);


        // TODO: 2017/3/14 暂时屏蔽测试用

        ModuleList.addAll(purchaseItems);
        ModuleList.addAll(produceItems);
        ModuleList.addAll(storageItems);
        ModuleList.addAll(salesItems);
        ModuleList.addAll(dailyworkItems);
        ModuleList.addAll(boardItems);
    }

    /**
     * 判断权限小模块
     */
    private void showDetailModule() {
        List<ModuleBean> tempPurchaseItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < purchaseItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (purchaseItems.get(i).getId().equals(powerItems.get(j))) {
                    tempPurchaseItems.add(purchaseItems.get(i));
                }
            }
        }
        purchaseItems = tempPurchaseItems;

        List<ModuleBean> tempProduceItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < produceItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (produceItems.get(i).getId().equals(powerItems.get(j))) {
                    tempProduceItems.add(produceItems.get(i));
                }
            }
        }
        produceItems = tempProduceItems;

        List<ModuleBean> tempStorageItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < storageItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (storageItems.get(i).getId().equals(powerItems.get(j))) {
                    tempStorageItems.add(storageItems.get(i));
                }
            }
        }
        storageItems = tempStorageItems;
        List<ModuleBean> tempSalesItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < salesItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (salesItems.get(i).getId().equals(powerItems.get(j))) {
                    tempSalesItems.add(salesItems.get(i));
                }
            }
        }
        salesItems = tempSalesItems;

        List<ModuleBean> tempDailyworkItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < dailyworkItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (dailyworkItems.get(i).getId().equals(powerItems.get(j))) {
                    tempDailyworkItems.add(dailyworkItems.get(i));
                }
            }
        }
        dailyworkItems = tempDailyworkItems;

        List<ModuleBean> tempBoardItems = new ArrayList<ModuleBean>();
        for (int i = 0; i < boardItems.size(); i++) {
            for (int j = 0; j < powerItems.size(); j++) {
                if (boardItems.get(i).getId().equals(powerItems.get(j))) {
                    tempBoardItems.add(boardItems.get(i));
                }
            }
        }
        boardItems = tempBoardItems;

    }

    /**
     * 显示标题栏
     */
    public void showTitle(List<TotalMode> totalModes, List<String> titles) {
        try {
            //添加采购管理
            if (purchaseItems.size() > 0 && purchaseItems != null) {
                TotalMode totalMode = new TotalMode(activity.getString(R.string.purchasemanger), purchaseItems);
                totalModes.add(totalMode);
            }
            //添加生产管理
            if (produceItems.size() > 0 && produceItems != null) {
                TotalMode totalMode = new TotalMode(activity.getString(R.string.producemanager), produceItems);
                totalModes.add(totalMode);
            }
            //添加库存管理
            if (storageItems.size() > 0 && storageItems != null) {
                TotalMode totalMode = new TotalMode(activity.getString(R.string.storagemanager), storageItems);
                totalModes.add(totalMode);
            }

            //销售管理
            if (salesItems.size() > 0 && salesItems != null) {
                TotalMode totalMode = new TotalMode(activity.getString(R.string.salesmanager), salesItems);
                totalModes.add(totalMode);
            }
            //报工管理
            if (dailyworkItems.size() > 0 && dailyworkItems != null) {
                TotalMode totalMode = new TotalMode(activity.getString(R.string.dailyworkmanager), dailyworkItems);
                totalModes.add(totalMode);
            }
            //看板管理
            if (boardItems.size() > 0 && boardItems != null) {
                TotalMode totalMode = new TotalMode(activity.getString(R.string.boardmanager), boardItems);
                totalModes.add(totalMode);
            }

            for (TotalMode mode : totalModes) {
                titles.add(mode.name);
            }

        } catch (Exception e) {
            LogUtils.e(TAG, "MainActivity--showTitle-----error");
        }
    }

    public void setCustomTab(Context context, TabLayout tablayout, List<String> titles) {
        //设置tab布局样式
        for (int i = 0; i < tablayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = tablayout.getTabAt(i);
            if (tabAt != null) {
                tabAt.setCustomView(setTabStyle(context, i, tablayout, titles));
            }
        }

        if ((titles.size() + 1) * titleValue >= screenWidth) {
            tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            tablayout.setTabMode(TabLayout.MODE_FIXED);
        }
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private View setTabStyle(Context context, int i, TabLayout tablayout, List<String> titles) {
        titleValue = ViewUtils.Dp2Px(context, 75);
        screenWidth = ViewUtils.getScreenWidth(context);
//        LogUtils.e("sunchangquan", "titleValue==" + titleValue + ",screenWidth--" + screenWidth);
        View view = LayoutInflater.from(context).inflate(R.layout.tablayout_item, tablayout, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(titleValue, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        TextView tv = (TextView) view.findViewById(R.id.tablayout_tv);
//        ImageView iv = (ImageView) view.findViewById(R.id.tablayout_im);
        View iv = (View) view.findViewById(R.id.tablayout_im);
        tv.setText(titles.get(i));
        if (i == tablayout.getSelectedTabPosition()) {
            tv.setTextColor(context.getResources().getColor(R.color.tab_title_selected_color));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            iv.setVisibility(View.VISIBLE);
        } else {
            tv.setTextColor(context.getResources().getColor(R.color.tab_title_unselected_color));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            iv.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    public void setPonit(Context context, List<String> titles, List<View> points, LinearLayout linearLayoutPoints) {
        int value = (int) applyDimension(TypedValue.COMPLEX_UNIT_SP, 7, context.getResources().getDisplayMetrics());
        for (int i = 0; i < titles.size(); i++) {
            //小点
            View view = new View(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(value, value);
            layoutParams.leftMargin = value;
            if (i == 0) {
                view.setBackgroundResource(R.drawable.point_seletor_yes);
            } else {
                view.setBackgroundResource(R.drawable.point_seletor_no);
            }
            view.setLayoutParams(layoutParams);
            linearLayoutPoints.addView(view);
            points.add(view);
        }
    }

    public void initListener(final Context context, final List<View> points, final ViewPager viewPager, TabLayout tablayout) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                points.get(position).setBackgroundResource(R.drawable.point_seletor_yes);
                points.get(oldIndex).setBackgroundResource(R.drawable.point_seletor_no);
                oldIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tablayout_tv)).
                        setTextColor(context.getResources().getColor(R.color.tab_title_selected_color));
                ((TextView) tab.getCustomView().findViewById(R.id.tablayout_tv)).
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                ((View) tab.getCustomView().findViewById(R.id.tablayout_im)).setVisibility(View.VISIBLE);
//                ((ImageView) tab.getCustomView().findViewById(R.id.tablayout_im)).setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tablayout_tv))
                        .setTextColor(context.getResources().getColor(R.color.tab_title_unselected_color));
                ((TextView) tab.getCustomView().findViewById(R.id.tablayout_tv)).
                        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                ((View) tab.getCustomView().findViewById(R.id.tablayout_im)).setVisibility(View.INVISIBLE);
//                ((ImageView) tab.getCustomView().findViewById(R.id.tablayout_im)).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}