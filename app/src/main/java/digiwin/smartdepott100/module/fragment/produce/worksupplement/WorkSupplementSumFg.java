package digiwin.smartdepott100.module.fragment.produce.worksupplement;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogClickListener;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.library.utils.ActivityManagerUtils;
import digiwin.library.utils.StringUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseFragment;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;
import digiwin.smartdepott100.module.activity.common.CommonDetailActivity;
import digiwin.smartdepott100.module.activity.produce.worksupplement.WorkSupplementActivity;
import digiwin.smartdepott100.module.adapter.produce.WorkSupplementSumAdapter;
import digiwin.smartdepott100.module.bean.common.ClickItemPutBean;
import digiwin.smartdepott100.module.bean.common.DetailShowBean;
import digiwin.smartdepott100.module.bean.common.FilterResultOrderBean;
import digiwin.smartdepott100.module.bean.common.ListSumBean;
import digiwin.smartdepott100.module.bean.common.SumShowBean;
import digiwin.smartdepott100.module.logic.common.CommonLogic;
import digiwin.smartdepott100.module.logic.produce.WorkSupplementLogic;

/**
 * @author 赵浩然
 * @des 依退料补料 汇总页
 * @date 2017/3/23
 */
public class WorkSupplementSumFg extends BaseFragment {

    /**
     * 退料单号
     */
    @BindView(R.id.tv_material_returning_number)
    TextView tv_material_returning_number;

    /**
     * 日期
     */
    @BindView(R.id.tv_date)
    TextView tv_date;

    /**
     * 退料部门
     */
    @BindView(R.id.tv_returned_material_department)
    TextView tv_returned_material_department;

    /**
     * 退料人员
     */
    @BindView(R.id.tv_returned_person)
    TextView tv_returned_person;

    WorkSupplementSumAdapter adapter;

    @BindView(R.id.ry_list)
    RecyclerView ryList;

    @OnClick(R.id.commit)
    void commit() {
        sureCommit();
    }

    FilterResultOrderBean localData;

    WorkSupplementActivity pactivity;
    WorkSupplementLogic commonLogic;

    boolean upDateFlag;

    List<ListSumBean>  sumBeanList;

    @Override
    protected void doBusiness() {
        localData = new FilterResultOrderBean();
        pactivity = (WorkSupplementActivity) activity;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(pactivity);
        ryList.setLayoutManager(linearLayoutManager);
        upDateFlag = false;
        localData = (FilterResultOrderBean) pactivity.getIntent().getExtras().getSerializable("data");
        //获取单号 日期 人员参数
        tv_material_returning_number.setText(localData.getDoc_no());
        tv_date.setText(localData.getCreate_date());
        tv_returned_material_department.setText(localData.getDepartment_name());
        tv_returned_person.setText(localData.getEmployee_name());

    }

    public void upDateList() {
        try {
            ClickItemPutBean putBean = new ClickItemPutBean();
            putBean.setDoc_no(localData.getDoc_no());
            putBean.setWarehouse_no(LoginLogic.getWare());
            commonLogic = WorkSupplementLogic.getInstance(pactivity, pactivity.module, pactivity.mTimestamp.toString());
            showLoadingDialog();
            commonLogic.getWSSum(putBean, new CommonLogic.GetZSumListener() {
                @Override
                public void onSuccess(List<ListSumBean> list) {
                    if(list.size() > 0){
                        dismissLoadingDialog();
                        upDateFlag = true;
                        adapter = new WorkSupplementSumAdapter(getActivity(),list);
                        ryList.setAdapter(adapter);
                        sumBeanList = new ArrayList<ListSumBean>();
                        sumBeanList = list;
                        adapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                getDetail(sumBeanList.get(position));
                            }
                        });
                    }
                }

                @Override
                public void onFailed(String error) {
                    dismissLoadingDialog();
                    showFailedDialog(error, new OnDialogClickListener() {
                        @Override
                        public void onCallback() {
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查看明细
     */
    public void getDetail(final ListSumBean orderSumData) {
        Map<String, String> map = new HashMap<>();
        showLoadingDialog();
        map.put(AddressContants.ITEM_NO, orderSumData.getItem_no());

        final SumShowBean sumShowBean = new SumShowBean();
        float numb1 = StringUtils.string2Float(orderSumData.getReturn_qty());
        float numb2 = StringUtils.string2Float(orderSumData.getIssue_qty());
        if(numb1 > numb2){
            sumShowBean.setAvailable_in_qty(orderSumData.getQty());
        }else if(numb1 < numb2){
            sumShowBean.setAvailable_in_qty(orderSumData.getReturn_qty());
        }else if(numb1 == numb2){
            sumShowBean.setAvailable_in_qty(orderSumData.getReturn_qty());
        }
        sumShowBean.setItem_no(orderSumData.getItem_no());
        sumShowBean.setItem_name(orderSumData.getItem_name());

        commonLogic.getDetail(map, new CommonLogic.GetDetailListener() {
            @Override
            public void onSuccess(List<DetailShowBean> detailShowBeen) {

                Bundle bundle = new Bundle();
                bundle.putString(AddressContants.MODULEID_INTENT, pactivity.mTimestamp.toString());
                bundle.putString(CommonDetailActivity.MODULECODE, pactivity.module);
                bundle.putSerializable(CommonDetailActivity.ONESUM, sumShowBean);
                bundle.putSerializable(CommonDetailActivity.DETAIL, (Serializable) detailShowBeen);
                dismissLoadingDialog();
                ActivityManagerUtils.startActivityBundleForResult(activity, CommonDetailActivity.class, bundle, pactivity.DETAILCODE);
            }

            @Override
            public void onFailed(String error) {
                dismissLoadingDialog();
                showFailedDialog(error);
            }
        });
    }

    private void sureCommit(){
        if (!upDateFlag){
            showFailedDialog(R.string.nodate);
            return;
        }

        showCommitSureDialog(new OnDialogTwoListener() {
            @Override
            public void onCallback1() {
                showLoadingDialog();
                HashMap<String, String> map = new HashMap<>();
                map.put("doc_no",tv_material_returning_number.getText().toString().trim());
                commonLogic.commit(map, new CommonLogic.CommitListener() {
                    @Override
                    public void onSuccess(String msg) {
                        dismissLoadingDialog();
                        showCommitSuccessDialog(msg, new OnDialogClickListener() {
                            @Override
                            public void onCallback() {
                                activity.finish();
                            }
                        });
                    }

                    @Override
                    public void onFailed(String error) {
                        dismissLoadingDialog();
                        showCommitFailDialog(error);
                    }
                });
            }
            @Override
            public void onCallback2() {

            }
        });
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.fg_worksupplementsum;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
