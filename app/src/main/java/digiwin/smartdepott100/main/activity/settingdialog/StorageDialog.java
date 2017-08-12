package digiwin.smartdepott100.main.activity.settingdialog;

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

/**
 * @author xiemeng
 * @des 仓库
 * @date 2017/2/10
 */
public class StorageDialog {

    private static String TAG = "StorageDialog";
    /**
     * 仓库 变化回调接口
     */
    private static StorageCallBack callBack;

    public static void setCallBack(StorageCallBack callBack) {
        StorageDialog.callBack = callBack;
    }

    public StorageDialog() {

    }

    /**
     * 弹出仓库Dialog
     *
     * @Author 毛衡
     */
    public static void showStorageDialog(final Activity context, final String storage, final List<String> list) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login_entidcompany, null);
        final DialogUtils mDialog = new DialogUtils(context, view);
        final RecyclerView rv_operatingCenter = (RecyclerView) view.findViewById(R.id.rv_entid_company);
        if (!StringUtils.isBlank(storage)) {
            if (list.contains(storage)) {
                list.remove(storage);
                list.add(0, storage);
            }
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv_operatingCenter.setLayoutManager(manager);
        StorageAdapter adapter = new StorageAdapter(list, context);
        rv_operatingCenter.setAdapter(adapter);
        //弹出Dialog
        int width = (int) (ViewUtils.getScreenWidth(context) * 0.75);
        int height = (int) (ViewUtils.getScreenHeight(context) * 0.4);
        mDialog.showDialog(width, height);
        //设置监听
        adapter.setClick(new StorageAdapter.OperatingCenterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDialog.dismissDialog();
                //营运中心变化,刷新UI
                if (callBack != null) {
                    callBack.storageCallBack(list.get(position));
                } else {
                    LogUtils.i(TAG, "接口回调对象为空");
                }
            }
        });
    }
    /**
     * 弹出仓库Dialog
     *
     * @Author 毛衡
     */
    public static void showStorageDialog(final Activity context, final String storage, final List<String> list,final StorageCallBack call) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login_entidcompany, null);
        final DialogUtils mDialog = new DialogUtils(context, view);
        final RecyclerView rv_operatingCenter = (RecyclerView) view.findViewById(R.id.rv_entid_company);
        if (!StringUtils.isBlank(storage)) {
            if (list.contains(storage)) {
                list.remove(storage);
                list.add(0, storage);
            }
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv_operatingCenter.setLayoutManager(manager);
        StorageAdapter adapter = new StorageAdapter(list, context);
        rv_operatingCenter.setAdapter(adapter);
        //弹出Dialog
        int width = (int) (ViewUtils.getScreenWidth(context) * 0.75);
        int height = (int) (ViewUtils.getScreenHeight(context) * 0.4);
        mDialog.showDialog(width, height);
        //设置监听
        adapter.setClick(new StorageAdapter.OperatingCenterOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDialog.dismissDialog();
                //营运中心变化,刷新UI
                if (call != null) {
                    call.storageCallBack(list.get(position));
                } else {
                    LogUtils.i(TAG, "接口回调对象为空");
                }
            }
        });
    }

    /**
     * 营运中心变化回调接口
     */
    public interface StorageCallBack {
        void storageCallBack(String chooseStorage);
    }
}
