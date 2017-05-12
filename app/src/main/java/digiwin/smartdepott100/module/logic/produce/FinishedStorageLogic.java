package digiwin.smartdepott100.module.logic.produce;

import android.content.Context;

/**
 * @des      生产完工入库
 * @author  xiemeng
 * @date    2017/2/23
 */
public class FinishedStorageLogic {
    private static final String TAG = "FinishedStorageLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private  String mModule="";
    /**
     * 设备号+模组+时间
     */
    private  String mTimestamp="";

    private static FinishedStorageLogic logic;

    private FinishedStorageLogic(Context context, String module, String timestamp) {
        mContext = context;
        mModule=module;
        mTimestamp=timestamp;

    }

    /**
     * 获取单例
     */
    public static FinishedStorageLogic getInstance(Context context, String module, String timestamp) {
        if (null == logic) {
            logic = new FinishedStorageLogic(context,module,timestamp);
        }
        return logic;
    }


}
