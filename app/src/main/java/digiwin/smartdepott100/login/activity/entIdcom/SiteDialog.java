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
 * @des 据点选择
 * @date 2017/5/10
 * @author xiemeng
 */
public class SiteDialog {

    private static String TAG = "SiteDialog";
    /**
     * 据点
     */
    private static SiteCallBack callBack;

    public static void setCallBack(SiteCallBack callBack) {
        SiteDialog.callBack = callBack;
    }

    public SiteDialog() {

    }

    /**
     * 据点集团
     *
     * @param context
     * @param siteShow
     * @param list
     */
    public static void showSiteDialog(final Activity context, final String siteShow, final List<EntSiteBean> list) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login_entidcompany, null);
        final DialogUtils mDialog = new DialogUtils(context, view);
        final RecyclerView rv_company = (RecyclerView) view.findViewById(R.id.rv_entid_company);
        if (!StringUtils.isBlank(siteShow)) {
           for (EntSiteBean entSiteBean :list){
              if (entSiteBean.getSite_show().equals(siteShow)){
                  list.remove(entSiteBean);
                  list.add(0, entSiteBean);
                  break;
              }
           }
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv_company.setLayoutManager(manager);
        CompanyAdapter adapter = new CompanyAdapter(list, context);
        rv_company.setAdapter(adapter);
        //弹出Dialog
        int width = (int) (ViewUtils.getScreenWidth(context) * 0.75);
        int height = (int) (ViewUtils.getScreenHeight(context) * 0.4);
        mDialog.showDialog(width, height);
        //设置监听
        adapter.setClick(new CompanyAdapter.CompanyOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDialog.dismissDialog();
                if (callBack != null) {
                     callBack.siteCallBack(list.get(position).getSite_show(), list.get(position).getSite_no());
                } else {
                    LogUtils.i(TAG, "接口回调对象为空");
                }
            }
        });
    }

    /**
     *据点
     */
    public interface SiteCallBack {
        void siteCallBack(String site_show,String site_no);
    }
}
