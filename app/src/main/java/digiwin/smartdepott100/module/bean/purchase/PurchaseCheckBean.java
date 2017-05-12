package digiwin.smartdepott100.module.bean.purchase;

import java.io.Serializable;

/**
 * @author 唐孟宇
 * @module 收获检验
 * @date 2017/3/8
 */

public class PurchaseCheckBean implements Serializable{
//    receipt_no            string        收货单号
//    suppier_name         string        供应商
//    delivery_bill_no        string        送货单号
//    turn_order            string        分批顺序
//    receipt_date           string        收货时间
//    wait_min              number        等待分钟
//    seq                   string        项次
//    item_no               string        料号
//    item_name             string        品名
//    item_spec             string        规格
//    drawing_no            string        图号
//    qty					  string        待检数量

    /**
     * 收货单号
     */
    public String receipt_no;
    /**
     * 供应商
     */
    public String suppier_name;
    /**
     *  送货单号
     */
    public String delivery_bill_no;
    /**
     * 分批顺序
     */
    public String turn_order;
    /**
     * 收货时间
     */
    public String receipt_date;
    /**
     * 等待分钟
     */
    public String wait_min;

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getSuppier_name() {
        return suppier_name;
    }

    public void setSuppier_name(String suppier_name) {
        this.suppier_name = suppier_name;
    }

    public String getDelivery_bill_no() {
        return delivery_bill_no;
    }

    public void setDelivery_bill_no(String delivery_bill_no) {
        this.delivery_bill_no = delivery_bill_no;
    }

    public String getTurn_order() {
        return turn_order;
    }

    public void setTurn_order(String turn_order) {
        this.turn_order = turn_order;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }

    public String getWait_min() {
        return wait_min;
    }

    public void setWait_min(String wait_min) {
        this.wait_min = wait_min;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_spec() {
        return item_spec;
    }

    public void setItem_spec(String item_spec) {
        this.item_spec = item_spec;
    }

    public String getDrawing_no() {
        return drawing_no;
    }

    public void setDrawing_no(String drawing_no) {
        this.drawing_no = drawing_no;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     * 项次

     */
    public String seq;
    /**
     * 料号
     */
    public String item_no;
    /**
     * 品名
     */
    public String item_name;
    /**
     * 规格
     */
    public String item_spec;
    /**
     * 图号
     */
    public String drawing_no;
    /**
     * 待检数量
     */
    public String qty;

    public String getQc_state() {
        return qc_state;
    }

    public void setQc_state(String qc_state) {
        this.qc_state = qc_state;
    }

    /**
     * 判定状态
     */
    public String qc_state;
    /**
     * 判定状态
     */
    public String ok_qty;

    public String getOk_qty() {
        return ok_qty;
    }

    public void setOk_qty(String ok_qty) {
        this.ok_qty = ok_qty;
    }

    /**
     * pqc检验
     */
    /**
     * doc_no                 LIKE rvb_file.rvb01,  #PQC单号
     * wo_no                  LIKE type_file.chr100,#工单
     * receipt_no             LIKE type_file.chr100,#Run Card
     * workstation_no         LIKE sgm_file.sgm06,  #工作组编号
     * workstation_name       LIKE eca_file.eca02,  #工作组名称
     * subop_no               LIKE qcm_file.qcm05,  #工序
     * item_no                LIKE type_file.chr100,#料号
     * item_name              LIKE ima_file.ima02,  #品名
     * item_spec              LIKE ima_file.ima021, #规格
     * drawing_no             LIKE ima_file.ima04,  #图号
     * qty										LIKE qcm_file.qcm22,  #待检数量
     * qc_state               LIKE type_file.chr1    #qc单检验否
     */

    /**
     * PQC单号
     */
    public String doc_no;
    /**
     * 工单
     */
    public String wo_no;
    /**
     * 工作组编号
     */
    public String workstation_no;
    /**
     * 工作组名称
     */
    public String workstation_name;
    /**
     * 工序
     */
    public String subop_no;

    public String getDoc_no() {
        return doc_no;
    }

    public void setDoc_no(String doc_no) {
        this.doc_no = doc_no;
    }

    public String getWo_no() {
        return wo_no;
    }

    public void setWo_no(String wo_no) {
        this.wo_no = wo_no;
    }

    public String getWorkstation_no() {
        return workstation_no;
    }

    public void setWorkstation_no(String workstation_no) {
        this.workstation_no = workstation_no;
    }

    public String getWorkstation_name() {
        return workstation_name;
    }

    public void setWorkstation_name(String workstation_name) {
        this.workstation_name = workstation_name;
    }

    public String getSubop_no() {
        return subop_no;
    }

    public void setSubop_no(String subop_no) {
        this.subop_no = subop_no;
    }

    /**
     * 重写hashcode和equals方法
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + seq.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PurchaseCheckBean && this.seq.equals(((PurchaseCheckBean)o).seq);
    }
}
