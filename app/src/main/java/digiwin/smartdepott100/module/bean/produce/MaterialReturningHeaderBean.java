package digiwin.smartdepott100.module.bean.produce;

import java.io.Serializable;

/**
 * Created by ChangquanSun
 * 2017/3/13
 * 生产退料汇总头部bean
 */

public class MaterialReturningHeaderBean implements Serializable{
    /**
     * 退料单号
     */
    private String materialNumber;
    /**
     * 申请部门
     */
    private String baranch;
    /**
     * 申请日
     */
    private String date;

    public MaterialReturningHeaderBean() {
    }

    public MaterialReturningHeaderBean(String materialNumber, String baranch, String date) {
        this.materialNumber = materialNumber;
        this.baranch = baranch;
        this.date = date;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getBaranch() {
        return baranch;
    }

    public void setBaranch(String baranch) {
        this.baranch = baranch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
