package digiwin.smartdepott100.login.activity.entIdcom;

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
import digiwin.smartdepott100.login.bean.EntSiteBean;

/**
 * @des 集团选择
 * @date 2017/5/10
 * @author xiemeng
 */
public class EntIdDialog {

    private static String TAG = "EntIdDialog";
    private static EntIdCallBack callBack;

    public static void setCallBack(EntIdCallBack callBack) {
        EntIdDialog.callBack = callBack;
    }

    public EntIdDialog() {

    }

    /**
     * 弹出集团
     *
     * @param context
     * @param entId
     * @param list
     */
    public static void showEntDialog(final Activity context, final String entId, final List<EntSiteBean> list) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login_entidcompany, null);
        final DialogUtils mDialog = new DialogUtils(context, view);
        final RecyclerView rvEntId = (RecyclerView) view.findViewById(R.id.rv_entid_company);
        if (!StringUtils.isBlank(entId)) {
           for (EntSiteBean entSiteBean :list){
              if (entSiteBean.getEnterprise_show().equals(entId)){
                  list.remove(entSiteBean);
                  list.add(0, entSiteBean);
                  break;
              }
           }
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rvEntId.setLayoutManager(manager);
        EntIdAdapter adapter = new EntIdAdapter(list, context);
        rvEntId.setAdapter(adapter);
        //弹出Dialog
        int width = (int) (ViewUtils.getScreenWidth(context) * 0.75);
        int height = (int) (ViewUtils.getScreenHeight(context) * 0.4);
        mDialog.showDialog(width, height);
        //设置监听
        adapter.setClick(new EntIdAdapter.EntOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDialog.dismissDialog();
                if (callBack != null) {
                     callBack.entIdCallBack(list.get(position).getEnterprise_show(),
                             list.get(position).getEnterprise_no());
                } else {
                    LogUtils.i(TAG, "接口回调对象为空");
                }
            }
        });
    }

    /**
     * 营运中心变化回调接口
     */
    public interface EntIdCallBack {
        void entIdCallBack(String chooseEntShow,String chooseEnt_no);
    }
}
