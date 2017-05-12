package digiwin.smartdepott100.core.coreutil;

import android.content.Context;

import digiwin.library.constant.SharePreKey;
import digiwin.library.constant.SystemConstant;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepott100.R;

/**
 * @des     获取当前声音类型
 * @author  xiemeng
 * @date    2017/3/24
 */
public class GetVoicer {

    public static  String getVoice(Context context){
        String voicer = "";
        String chooseVoiceType = (String) SharedPreferencesUtils.get(context, SharePreKey.VOICER_SELECTED, context.getString(R.string.voicer_not));
        if (chooseVoiceType.equals(context.getString(R.string.voicer_male))) {
            voicer = SystemConstant.VIXF;
        } else if (chooseVoiceType.equals(context.getString(R.string.voicer_female))) {
            voicer = SystemConstant.VIXY;
        } else {
            voicer = SystemConstant.NOMETION;
        }
        return  voicer;
    }
}
