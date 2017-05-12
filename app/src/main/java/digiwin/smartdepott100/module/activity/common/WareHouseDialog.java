package digiwin.smartdepott100.module.activity.common;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import digiwin.library.utils.DialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.ViewUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.module.adapter.common.WareHouseAdapter;

/**
 * @author 赵浩然
 * @module ITEM中选择仓库
 * @date 2017/3/31
 */

public class WareHouseDialog {

    private static String TAG = "WareHouseDialog";
    /**
     * 营运中心变化回调接口
     */
    private static WareHouseCallBack callBack;

    public static void setCallBack(WareHouseCallBack callBack) {
        WareHouseDialog.callBack = callBack;
    }

    public WareHouseDialog() {

    }

    /**
     * 弹出仓库Dialog
     * @Author 赵浩然
     */
    public static void showWareHouseDialog(final Activity context, final String warehouse, final List<String> list){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login_entidcompany, null);
        final DialogUtils mDialog = new DialogUtils(context,view);
        final RecyclerView rv_operatingCenter = (RecyclerView) view.findViewById(R.id.rv_entid_company);

        if (!StringUtils.isBlank(warehouse)) {
            if (list.contains(warehouse)) {
                list.remove(warehouse);
                list.add(0, warehouse);
            }
        }

        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv_operatingCenter.setLayoutManager(manager);
        WareHouseAdapter adapter = new WareHouseAdapter(list,context);
        rv_operatingCenter.setAdapter(adapter);
        //弹出Dialog
        int width = (int) (ViewUtils.getScreenWidth(context)*0.75);
        int height = (int) (ViewUtils.getScreenHeight(context)*0.4);
        mDialog.showDialog(width,height);
        //设置监听
        adapter.setClick(new WareHouseAdapter.OperatingCenterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDialog.dismissDialog();
                //营运中心变化,刷新UI
                if(callBack != null){
                    callBack.wareHouseCallBack( list.get(position));
                }else {
                    LogUtils.i(TAG,"接口回调对象为空");
                }
            }
        });
    }
    /**
     * 营运中心变化回调接口
     */
    public interface WareHouseCallBack{
        void wareHouseCallBack(String wareHouse);
    }
}
