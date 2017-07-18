package digiwin.smartdepott100.module.logic.board;

import android.content.Context;

import java.util.List;
import java.util.Map;

import digiwin.library.utils.LogUtils;
import digiwin.library.xml.ParseXmlResp;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ReqTypeName;
import digiwin.smartdepott100.core.net.IRequestCallbackImp;
import digiwin.smartdepott100.core.net.OkhttpRequest;
import digiwin.smartdepott100.core.xml.CreateParaXmlReqIm;
import digiwin.smartdepott100.module.bean.board.TctsBoardBean;

/**
 * @author xiemeng
 * @des 检验完成待入库看板
 * @date 2017/3/8
 */
public class TctsboardLogic {
    private static final String TAG = "TctsboardLogic";

    private Context mContext;
    /**
     * 模组名
     */
    private String mModule = "";
    /**
     * 设备号+模组+时间
     */
    private String mTimestamp = "";

    private static TctsboardLogic logic;

    private TctsboardLogic(Context context, String module, String timestamp) {
        mContext = context.getApplicationContext();
        mModule = module;
        mTimestamp = timestamp;

    }

    public static TctsboardLogic getInstance(Context context, String module, String timestamp) {

        return logic = new TctsboardLogic(context, module, timestamp);
    }

    /**
     * 获取看板资料
     */
    public interface GetTctsBoardListener {
        public void onSuccess(List<TctsBoardBean> list,String msg);

        public void onFailed(String error);
    }

    /**
     * 获取看板资料
     */
    public void getTctsBoard(final Map<String, String> map, final GetTctsBoardListener listener) {
        try {
            String xml = CreateParaXmlReqIm.getInstance(map, mModule, ReqTypeName.TCTSBOARD, mTimestamp).toXml();
            OkhttpRequest.getInstance(mContext).post(xml, new IRequestCallbackImp() {
                @Override
                public void onResponse(String string) {
                    ParseXmlResp xmlResp = ParseXmlResp.fromXml(ReqTypeName.TCTSBOARD, string);
                    String error = mContext.getString(R.string.unknow_error);
                    if (null != xmlResp) {
                        if (ReqTypeName.SUCCCESSCODE.equals(xmlResp.getCode())) {
                            String fieldString = xmlResp.getFieldString();
                            List<TctsBoardBean> boardBeen = xmlResp.getMasterDatas(TctsBoardBean.class);
                            listener.onSuccess(boardBeen,fieldString);
                            return;
                        } else {
                            error = xmlResp.getDescription();
                        }
                    }
                    listener.onFailed(error);
                }
            });
        } catch (Exception e) {
            listener.onFailed(mContext.getString(R.string.unknow_error));
            LogUtils.e(TAG, "getTctsBoard" + e);

        }


    }

}
