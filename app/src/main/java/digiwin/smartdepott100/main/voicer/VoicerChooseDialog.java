package digiwin.smartdepott100.main.voicer;

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
 * 声音选择对话框
 * @author 毛衡
 */

public class VoicerChooseDialog {

    private static String TAG = "StorageDialog";
    /**
     * 声音   */
    private static VoicerChooseCallBack callBack;

    public static void setCallBack(VoicerChooseCallBack callBack) {
        VoicerChooseDialog.callBack = callBack;
    }
    public VoicerChooseDialog() {

    }
    /**
     * 弹出声音选择Dialog
     * @Author 毛衡
     */
    public static void showVoicerChooseDialog(final Activity context,final String voicerType, final List<String> voicerList){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_login_entidcompany, null);
        final DialogUtils mDialog = new DialogUtils(context,view);
        final RecyclerView rv_voicer = (RecyclerView) view.findViewById(R.id.rv_entid_company);
        if (!StringUtils.isBlank(voicerType)) {
            voicerList.remove(voicerType);
            voicerList.add(0, voicerType);
        }
        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv_voicer.setLayoutManager(manager);
        VoicerChooseAdapter adapter = new VoicerChooseAdapter(voicerList,context);
        rv_voicer.setAdapter(adapter);
        //弹出Dialog
        int width = (int) (ViewUtils.getScreenWidth(context)*0.75);
        int height = (int) (ViewUtils.getScreenHeight(context)*0.4);
        mDialog.showDialog(width,height);
        //设置监听
        adapter.setClick(new VoicerChooseAdapter.VoicerChooseOnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDialog.dismissDialog();
                if(callBack != null){
                    callBack.VoicerChooseCallBack( voicerList.get(position));
                }else {
                    LogUtils.i(TAG,"接口回调对象为空");
                }
            }
        });
    }
    /**
     * 声音选择变化回调接口
     */
    public interface VoicerChooseCallBack{
        void VoicerChooseCallBack(String voicerType);
    }
}
