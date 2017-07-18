package digiwin.smartdepott100.main.logic;

import android.content.Context;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import digiwin.library.json.JsonResp;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.json.JsonReqForERP;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.main.bean.StorageBean;

/**
 * @author xiemeng
 * @des 获取仓库
 * @date 2017/2/10
 */
public class GetStorageLogic {
    private static final String TAG = "GetStorageLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static GetStorageLogic logic;

    private GetStorageLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;

    }

    /**
     * 获取单例
     */
    public static GetStorageLogic getInstance(Context context, String module, String timestamp) {
//        if (null == logic) {
//
//        }
        return logic = new GetStorageLogic(context, module, timestamp);
    }

    /**
     * 获取仓库信息
     */
    public interface GetStorageListener {
        public void onSuccess(List<String> wares);

        public void onFailed(String msg);
    }

    /**
     * 获取仓库信息
     */
    public void getStorage(Map<String, String> map, final GetStorageListener listener) {
        try {
            String createJson = JsonReqForERP.mapCreateJson(mModule, ReqTypeName.GETWARE, mTimestamp, map);
            OkhttpRequest.getInstance(mContext).post(createJson, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    String error = mContext.getString(R.string.unknow_error);
                    if (null != string) {
                        if (ReqTypeName.SUCCCESSCODE.equals(JsonResp.getCode(string))) {
                            StorageBean paraData = JsonResp.getParaData(string, StorageBean.class);
                            List<String> split = new ArrayList<String>();
                            String storages = paraData.getWarehouse_no();
                            if (!StringUtils.isBlank(storages)) {
                                split = StringUtils.split(storages);
                                List<StorageBean> storagesaveBeen = new ArrayList<StorageBean>();
                                for (int i = 0; i < split.size(); i++) {
                                    StorageBean bean = new StorageBean();
                                    bean.setWarehouse_no(split.get(i));
                                    storagesaveBeen.add(bean);
                                }
                                Connector.getDatabase();
                                DataSupport.deleteAll(StorageBean.class);
                                DataSupport.saveAll(storagesaveBeen);
                            }
                            listener.onSuccess(split);
                            return;

                        } else {
                            error = JsonResp.getDescription(string);
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e)
        {
            LogUtils.e(TAG, "getStorage" + e);
            listener.onFailed(mContext.getString(R.string.unknow_error));
        }

    }

    /**
     * 获取所有仓库
     */
    public static List<StorageBean> getWare() {
        Connector.getDatabase();
        List<StorageBean> storageBeen = DataSupport.findAll(StorageBean.class);
        return storageBeen;
    }

    /**
     * 获取仓库编号
     */
    public static List<String> getWareString() {
        Connector.getDatabase();
        List<String> list = new ArrayList<>();
        List<StorageBean> storageBeen = DataSupport.findAll(StorageBean.class);
        if (null != storageBeen)
            for (int i = 0; i < storageBeen.size(); i++) {
                list.add(storageBeen.get(i).getWarehouse_no());
            }
        return list;
    }
}
