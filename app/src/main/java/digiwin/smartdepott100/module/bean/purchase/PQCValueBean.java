package digiwin.smartdepott100.module.bean.purchase;

import java.io.Serializable;

/**
 * @des PQC检验bean
 * @author maoheng
 * @date 2017/4/30
 */

public class PQCValueBean implements Serializable {
    /**
     * 检验项目
     */
    private String inspection_item_no = "";
    /**
     * 检验项目说明
     */
    private String inspection_item = "";
    /**
     * 检验量
     */
    private String qc_qty = "";
    /**
     * 检验方式
     */
    private String qc_method = "";
    /**
     * 下值
     */
    private String lower_qty = "";
    /**
     * 上值
     */
    private String upper_qty = "";
    /**
     * 默认测量量
     */
    private String qty = "";

    public String getInspection_item_no() {
        return inspection_item_no;
    }

    public void setInspection_item_no(String inspection_item_no) {
        this.inspection_item_no = inspection_item_no;
    }

    public String getInspection_item() {
        return inspection_item;
    }

    public void setInspection_item(String inspection_item) {
        this.inspection_item = inspection_item;
    }

    public String getQc_qty() {
        return qc_qty;
    }

    public void setQc_qty(String qc_qty) {
        this.qc_qty = qc_qty;
    }

    public String getQc_method() {
        return qc_method;
    }

    public void setQc_method(String qc_method) {
        this.qc_method = qc_method;
    }

    public String getLower_qty() {
        return lower_qty;
    }

    public void setLower_qty(String lower_qty) {
        this.lower_qty = lower_qty;
    }

    public String getUpper_qty() {
        return upper_qty;
    }

    public void setUpper_qty(String upper_qty) {
        this.upper_qty = upper_qty;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
