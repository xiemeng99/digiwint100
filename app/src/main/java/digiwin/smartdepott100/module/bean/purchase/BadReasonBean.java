package digiwin.smartdepott100.module.bean.purchase;

import java.io.Serializable;

/**
 * Created by MacheNike on 2017/3/22.
 * 不良原因
 */

public class BadReasonBean implements Serializable{

//    defect_reason             string     不良原因编号
//    defect_reason_name       string      不良原因名称

    /**
     * 不良原因编号
     */
    private String defect_reason = null;

    public String getDefect_reason() {
        return defect_reason;
    }

    public void setDefect_reason(String defect_reason) {
        this.defect_reason = defect_reason;
    }

    public String getDefect_reason_name() {
        return defect_reason_name;
    }

    public void setDefect_reason_name(String defect_reason_name) {
        this.defect_reason_name = defect_reason_name;
    }

    /**
     * 不良原因名称
     */

    private String defect_reason_name = null;
    /**
     * 不良数量
     */
    private String defect_reason_qty = null;

    public String getDefect_qty() {
        return defect_reason_qty;
    }

    public void setDefect_qty(String defect_qty) {
        this.defect_reason_qty = defect_qty;
    }

    /**
     * 单号
     */
    private String doc_no;
    /**
     * 行序
     */
    private String seq;
    /**
     * 缺点数量
     */
    private String qty;
    /**
     * 备注
     */
    private String remark;

    public String getDefect_reason_qty() {
        return defect_reason_qty;
    }

    public void setDefect_reason_qty(String defect_reason_qty) {
        this.defect_reason_qty = defect_reason_qty;
    }

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 1:新增/修改 2：删除
     */
    private String statu;

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }
}
