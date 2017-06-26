package digiwin.smartdepott100.core.coreutil;


import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.module.bean.common.SaveBean;

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
        BaseApplication instance = BaseApplication.getInstance();
        if (null!=saveBean&&null!=saveBean.getItem_barcode_type()){
            if ("3".equals(saveBean.getItem_barcode_type())
                    ||"4".equals(saveBean.getItem_barcode_type())){
                isAutoSave=true;
            }
        }
        return  isAutoSave;
    }
}
