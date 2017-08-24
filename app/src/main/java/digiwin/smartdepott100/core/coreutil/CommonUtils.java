package digiwin.smartdepott100.core.coreutil;


import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.module.bean.common.SaveBean;
import digiwin.smartdepott100.module.bean.stock.ProductBinningBean;

/**
 * @author xiemeng
 * @des  模块公共方法
 * @date 2017/6/26 09:41
 */

public class CommonUtils {

    /**
     * @param saveBean 保存对象
     * @return true时可以自动保存
     */
    public static boolean isAutoSave(SaveBean saveBean){
        boolean isAutoSave=false;
        if (null!=saveBean&&null!=saveBean.getItem_barcode_type()){
            if ("3".equals(saveBean.getItem_barcode_type())
                    ||"4".equals(saveBean.getItem_barcode_type())){
                isAutoSave=true;
            }
        }
        return  isAutoSave;
    }
    /**
     * @param saveBean 保存对象
     * @return true时可以自动保存
     */
    public static boolean isAutoSave(ProductBinningBean saveBean){
        boolean isAutoSave=false;
        if (null!=saveBean&&null!=saveBean.getItem_barcode_type()){
            if ("3".equals(saveBean.getItem_barcode_type())
                    ||"4".equals(saveBean.getItem_barcode_type())){
                isAutoSave=true;
            }
        }
        return  isAutoSave;
    }


    /**
     * 是否启用托盘
     * @return
     */
    public static boolean isUseTray(){
        BaseApplication instance = BaseApplication.getInstance();
        boolean tray = (boolean) SharedPreferencesUtils.get(instance, SharePreKey.TRAY_SETTING, false);
        return  tray;
    }
}
