package digiwin.smartdepott100.module.activity.purchase.pqccheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.RecyclerViewHolder;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseActivity;
import digiwin.smartdepott100.module.bean.purchase.PQCValueBean;
import digiwin.smartdepott100.module.logic.purchase.PQCLogic;

/**
 * @author 毛衡
 * @des pqc 测量值检验
 * @date 2017/4/30
 */

public class PQCValueActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private String doc_no;
    private String seq;

    @OnClick(R.id.iv_back)
    void ivBack(){
        activity.finish();
    }
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @OnClick(R.id.tv_title_name)
    void tvBack(){
        activity.finish();
    }
    @BindView(R.id.tv_check_item)
    TextView tvCheckItem;
    @BindView(R.id.tv_string)
    TextView tvString;
    @BindView(R.id.tv_check_type)
    TextView tvCheckType;
    @BindView(R.id.tv_check_num)
    TextView tvCheckNum;
    @BindView(R.id.iv_add)
    ImageView ivAdd;

    @OnClick(R.id.iv_add)
    /**
     * 添加
     */
    void ivAdd(){
        PQCValueBean value = new PQCValueBean();
        value.setUpper_qty(bean.getUpper_qty());
        value.setLower_qty(bean.getLower_qty());
        value.setQty(bean.getQty());
        values.add(value);
        adapter.notifyDataSetChanged();
    }
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @BindView(R.id.save)
    Button save;
    private final int VALUERESULT = 1000;
    @OnClick(R.id.save)
    void save(){
        List<Map<String,String>> maps = new ArrayList<>();
        Map<String,String> map1 = new HashMap<>();
        map1.put(AddressContants.DOC_NO,doc_no);
        map1.put("seq_num",seq);
        map1.put("inspection_item_no",bean.getInspection_item_no());
        maps.add(map1);
        List<Map<String,String>> details = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Map<String,String> map2 = new HashMap<>();
            map2.put("measured_value",values.get(i).getQty());
            details.add(map2);
        }
        showLoadingDialog();
        pqcLogic.savePQCData(maps, details, new PQCLogic.SavePQCListener() {
            @Override
            public void onSuccess(String msg) {
                dismissLoadingDialog();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("PQCValue", (Serializable) values);
                intent.putExtras(bundle);
                pactivity.setResult(VALUERESULT,intent);
                pactivity.finish();
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });

    }

    private PQCLogic pqcLogic;

    private PQCValueActivity pactivity;

    private PQCValueAdapter adapter;

    private PQCValueBean bean;

    private List<PQCValueBean> values;

    @Override
    public String moduleCode() {
        module = ModuleCode.PQCCHECK;
        return module;
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_pqc_value;
    }

    @Override
    protected void initNavigationTitle() {
        tvTitleName.setText(R.string.title_check_value);
    }

    @Override
    protected void doBusiness() {
        values  = new ArrayList<>();
        bean = new PQCValueBean();
        pactivity = (PQCValueActivity) activity;
        LinearLayoutManager layoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(layoutManager);
        adapter = new PQCValueAdapter(pactivity,values);
        ryList.setAdapter(adapter);
        doc_no = getIntent().getExtras().getString(AddressContants.DOC_NO);
        seq = getIntent().getExtras().getString(AddressContants.SEQ);
        List<PQCValueBean> datas = (List<PQCValueBean>) getIntent().getExtras().getSerializable("valueList");
        if(null!=datas&&datas.size()>0){
            values.addAll(datas);
            adapter = new PQCValueAdapter(pactivity,values);
            ryList.setAdapter(adapter);
        }
        pqcLogic = PQCLogic.getInstance(pactivity,module,pactivity.mTimestamp.toString());
        getValueData();
    }

    private void getValueData() {
        showLoadingDialog();
        Map<String,String> map = new HashMap<>();
        map.put(AddressContants.DOC_NO,doc_no);
        map.put(AddressContants.SEQ,seq);
        pqcLogic.getPQCValueData(map, new PQCLogic.getPQCValueListener() {
            @Override
            public void onSuccess(PQCValueBean data) {
                dismissLoadingDialog();
                bean = data;
                tvCheckItem.setText(data.getInspection_item_no());
                tvString.setText(data.getInspection_item());
                tvCheckNum.setText(StringUtils.deleteZero(data.getQc_qty()));
                tvCheckType.setText(data.getQc_method());
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    class PQCValueAdapter extends BaseRecyclerAdapter<PQCValueBean> {

        public PQCValueAdapter(Context ctx, List<PQCValueBean> list) {
            super(ctx, list);
        }

        @Override
        protected int getItemLayout(int viewType) {
            return R.layout.ryitem_pqc_value;
        }

        @Override
        protected void bindData(RecyclerViewHolder holder, final int position, PQCValueBean item) {
            holder.setText(R.id.item_seq,StringUtils.objToString(position+1));
            holder.setText(R.id.up_value,StringUtils.deleteZero(item.getUpper_qty()));
            holder.setText(R.id.down_value,StringUtils.deleteZero(item.getLower_qty()));
            holder.setText(R.id.check_value,StringUtils.deleteZero(item.getQty()));
            EditText view = (EditText) holder.getView(R.id.check_value);
            view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){
                        view.setBackgroundResource(R.drawable.edit_yellow_bg);
                    }else {
                        view.setBackground(null);
                    }
                }
            });
            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    values.get(position).setQty(editable.toString());
                }
            });
        }
    }

}
