package digiwin.library.voiceutils;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
/**
 * 震动工具类
 * @author 赵浩然
 *
 */
public class VibratorUtil {
    /** 
     * final Activity activity  ：调用该方法的Activity实例 
     * long milliseconds ：震动的时长，单位是毫秒 
     * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]时长的单位是毫秒 
     * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次 
     * 开始震动有两个接口
     *  1.vibrator.vibrate(2000);//震动指定时间 ，数据类型long，单位为毫秒，一毫秒为1/1000秒
     *  2.vibrator.vibrate(new long[]{100,10,100,1000}, -1);//按照指定的模式去震动。
                     数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
                     第二个参数为重复次数，-1为不重复，0为一直震动    
                     取消震动vibrator.cancel();//取消震动，立即停止震动
                     震动为一直震动的话，如果不取消震动，就算退出，也会一直震动
     */
    /**
     * 震动时间
     */
    public static  final int VIBRATETIME=1000;

    public static void Vibrate(final Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);   
    }   
    
    public static void Vibrate(final Context context, long[] pattern,boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);   
    }
}
