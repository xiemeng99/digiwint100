package digiwin.smartdepott100.module.bean.common;

import java.io.Serializable;

/**
 * @author xiemeng
 * @des  本地图片--选择图片使用
 * @date 2017/4/19 15:16
 */

public class ChoosePicBean implements Serializable{
    /**
     * 图片sd卡路径
     */
    private String picPath;
    /**
     * 项目中drawable资源
     */
    private int drawId;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getDrawId() {
        return drawId;
    }

    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }
}
