package digiwin.smartdepott100.core.coreutil;

import java.util.List;

import digiwin.library.utils.StringUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.module.bean.common.FifoCheckBean;
import digiwin.smartdepott100.module.bean.common.SaveBean;

/**
 * @author xiemeng
 * @des 有fifo模块使用
 * @date 2017/4/15 16:28
 */

public class FiFoCheckUtils {
    /**
     * @param saveBean 保存对象
     * @param fifoList fifo列表
     * @return 返回报错 为空则可以报错
     */
    public static String fifoCheck(SaveBean saveBean, List<FifoCheckBean> fifoList) {
        String fifocheck = "";
        if (null != saveBean&&null!=fifoList) {
            //fifochek为Y的时候需要判断物料条码和库位是否存在
            if (AddressContants.FIFOY.equals(saveBean.getFifo_check())) {

                for (int i = 0; i < fifoList.size(); i++) {
                    //保存对象存在于fifolist集合中
                    if (saveBean.getBarcode_no().equals(fifoList.get(i).getBarcode_no())
                            && saveBean.getStorage_spaces_out_no().equals(fifoList.get(i).getStorage_spaces_no())) {
                        if (i!=0){
                            fifocheck=BaseApplication.getInstance().getString(R.string.scan_byfifo_order);
                            return fifocheck;
                        }
                        //保存对象存在fifolist中，进一步判断是否允许负库存
                        //不允许负库存情况判断了保存数量是否大于建议量，若大于则报错
                        if (AddressContants.N.equals(saveBean.getAllow_negative_stock())
                                &&StringUtils.sub(saveBean.getQty(),fifoList.get(i).getRecommended_qty())>0){
                            fifocheck= BaseApplication.getInstance().getString(R.string.input_num_toobig);
                        }
                        //允许负库存情况无需比较数量直接保存
                        else {
                            fifocheck="";
                            return fifocheck;
                        }
                    }
                    //保存对象不存在于fifolist集合中
                    else {
                        fifocheck=BaseApplication.getInstance().getString(R.string.fifo_scan_error);
                    }
                }
            }
            //fifochek为N直接保存
            else {
                fifocheck= "";
                return fifocheck;
            }
        }

        return fifocheck;
    }
}
