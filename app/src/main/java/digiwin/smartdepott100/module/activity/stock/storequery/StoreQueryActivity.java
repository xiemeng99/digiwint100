package digiwin.smartdepott100.module.activity.stock.storequery;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleHActivity;
import digiwin.smartdepott100.core.modulecommon.ModuleUtils;
import digiwin.smartdepott100.core.modulecommon.ModuleViewPagerAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.fragment.stock.storequery.StoreQueryBarcodeFg;
import digiwin.smartdepott100.module.fragment.stock.storequery.StoreQueryItemNoFg;
import digiwin.smartdepott100.module.logic.stock.StoreQueryLogic;

/**
 * @author xiemeng
 * @des 库存查询
 * @date 2017/3/22
 */
public class StoreQueryActivity extends BaseTitleHActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.tv_barcode)
    TextView tvBarcode;
    @BindView(R.id.et_barcode)
    EditText etBarcode;
    @BindView(R.id.ll_barcode)
    LinearLayout llBarcode;
    @BindView(R.id.tv_item_no)
    TextView tvItemNo;
    @BindView(R.id.et_item_no)
    EditText etItemNo;
    @BindView(R.id.ll_item_no)
    LinearLayout llItemNo;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.et_item_name)
    EditText etItemName;
    @BindView(R.id.ll_item_name)
    LinearLayout llItemName;
    @BindView(R.id.tv_storage)
    TextView tvStorage;
    @BindView(R.id.et_storage)
    EditText etStorage;
    @BindView(R.id.ll_storage)
    LinearLayout llStorage;
    @BindView(R.id.tv_locator)
    TextView tvLocator;
    @BindView(R.id.et_locator)
    EditText etLocator;
    @BindView(R.id.ll_locator)
    LinearLayout llLocator;
    @BindView(R.id.btn_search_sure)
    Button btnSearchSure;
    @BindView(R.id.ll_search_input)
    LinearLayout llSearchInput;
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.module_vp)
    ViewPager moduleVp;
    @BindView(R.id.ll_taylayout)
    LinearLayout llTaylayout;
    /**
     * 控件集合
     */
    @BindViews({R.id.et_barcode, R.id.et_item_no, R.id.et_item_name, R.id.et_storage, R.id.et_locator})
    List<EditText> editTexts;
    @BindViews({R.id.ll_barcode, R.id.ll_item_no, R.id.ll_item_name, R.id.ll_storage, R.id.ll_locator})
    List<View> views;
    @BindViews({R.id.tv_barcode, R.id.tv_item_no, R.id.tv_item_name, R.id.tv_storage, R.id.tv_locator})
    List<TextView> textViews;

    @OnFocusChange(R.id.et_barcode)
    void barcodeFocusChange() {
        ModuleUtils.viewChange(llBarcode, views);
        ModuleUtils.etChange(activity, etBarcode, editTexts);
        ModuleUtils.tvChange(activity, tvBarcode, textViews);
    }

    @OnFocusChange(R.id.et_item_no)
    void itemNoFocusChange() {
        ModuleUtils.viewChange(llItemNo, views);
        ModuleUtils.etChange(activity, etItemNo, editTexts);
        ModuleUtils.tvChange(activity, tvItemNo, textViews);
    }

    @OnFocusChange(R.id.et_item_name)
    void itemNameFocusChange() {
        ModuleUtils.viewChange(llItemName, views);
        ModuleUtils.etChange(activity, etItemName, editTexts);
        ModuleUtils.tvChange(activity, tvItemName, textViews);
    }

    @OnFocusChange(R.id.et_storage)
    void storageFocusChange() {
        ModuleUtils.viewChange(llStorage, views);
        ModuleUtils.etChange(activity, etStorage, editTexts);
        ModuleUtils.tvChange(activity, tvStorage, textViews);
    }

    @OnFocusChange(R.id.et_locator)
    void locatorFocusChange() {
        ModuleUtils.viewChange(llLocator, views);
        ModuleUtils.etChange(activity, etLocator, editTexts);
        ModuleUtils.tvChange(activity, tvLocator, textViews);
    }

    /**
     * 筛选按钮
     */
    @BindView(R.id.iv_title_setting)
    ImageView search;

    @OnClick(R.id.iv_title_setting)
    void isShowSearch() {
        if (isSearching) {
            llTaylayout.setVisibility(View.VISIBLE);
            llSearchInput.setVisibility(View.GONE);
            isSearching = false;
        } else {
            llTaylayout.setVisibility(View.GONE);
            llSearchInput.setVisibility(View.VISIBLE);
            isSearching = true;
        }
    }

    ClickItemPutBean clickItemPutBean;

    @OnClick(R.id.btn_search_sure)
    void search() {
        clickItemPutBean = new ClickItemPutBean();
        clickItemPutBean.setBarcode_no(etBarcode.getText().toString());
        clickItemPutBean.setItem_no(etItemNo.getText().toString());
        clickItemPutBean.setItem_name(etItemName.getText().toString());
        clickItemPutBean.setWarehouse_no(etStorage.getText().toString());
        clickItemPutBean.setStorage_space_no(etLocator.getText().toString());
        showLoadingDialog();
        logic.getStoreList(clickItemPutBean, new StoreQueryLogic.GetStoreListListener() {
            @Override
            public void onSuccess(List<ListSumBean> showNoBarcodeList,List<ListSumBean> showHasBarcodeList) {
                dismissLoadingDialog();
//                if (null==list||list.size()==0){
//                    showFailedDialog(R.string.nodate);
//                    return;
//                }
                llTaylayout.setVisibility(View.VISIBLE);
                llSearchInput.setVisibility(View.GONE);
                isSearching = false;
                if (null!=showNoBarcodeList){
                    barcodeFg.onSubscribe(showNoBarcodeList);
                }
                if (null!=showHasBarcodeList) {
                    itemNoFg.upDateList(showHasBarcodeList);
                }

            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    /**
     * 是否在搜索
     */
    private boolean isSearching;

    /**
     * Fragment设置
     */
    private List<Fragment> fragments;
    private List<String> titles;
    private FragmentManager fragmentManager;
    ModuleViewPagerAdapter adapter;
    /**
     * 条码库存
     */
    StoreQueryBarcodeFg barcodeFg;
    /**
     * 料件库存
     */
    StoreQueryItemNoFg itemNoFg;
    StoreQueryLogic logic;
    @Override
    protected int bindLayoutId() {
        return R.layout.activity_store_query;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.store_query);
        toolbarTitle.setBackgroundResource(R.drawable.title_bg);
        ivScan.setVisibility(View.VISIBLE);
        search.setVisibility(View.VISIBLE);
        search.setImageResource(R.drawable.search);
        isSearching = true;
    }

    @Override
    protected void doBusiness() {
        barcodeFg = new StoreQueryBarcodeFg();
        itemNoFg = new StoreQueryItemNoFg();
        logic = StoreQueryLogic.getInstance(context, module, mTimestamp.toString());
        fragments = new ArrayList<>();
        fragments.add(barcodeFg);
        fragments.add(itemNoFg);
        titles = new ArrayList<>();
        titles.add(getResources().getString(R.string.store_query_barcode));
        titles.add(getResources().getString(R.string.store_query_item_no));
        fragmentManager = getSupportFragmentManager();
        adapter = new ModuleViewPagerAdapter(fragmentManager, fragments, titles);
        moduleVp.setAdapter(adapter);
        tlTab.addTab(tlTab.newTab().setText(titles.get(0)));
        tlTab.addTab(tlTab.newTab().setText(titles.get(1)));
        //Tablayout和ViewPager关联起来
        tlTab.setupWithViewPager(moduleVp);
        tlTab.setTabsFromPagerAdapter(adapter);
        select();
        btnSearchSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    /**
     * 滑动
     */
    private void select() {
        moduleVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                 if (position == 1&&null!=clickItemPutBean) {
//                    itemNoFg.updateList(clickItemPutBean);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        module = ModuleCode.STOREQUERY;
        return module;
    }

}
