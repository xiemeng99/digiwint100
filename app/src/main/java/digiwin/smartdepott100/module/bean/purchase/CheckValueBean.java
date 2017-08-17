package digiwin.smartdepott100.module.bean.purchase;

/**
 * Created by maoheng on 2017/8/15.
 */

public class CheckValueBean {
    /**
     * 单号
     */
    private String doc_no;
    /**
     * 行序
     */
    private String seq;
    /**
     * 序号
     */
    private String order_seq;
    /**
     * 测量值
     */
    private String measure_value;
    /**
     * 合格
     */
    private String result_type;

    /**
     * 状态
     */
    private String statu;

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
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

    public String getOrder_seq() {
        return order_seq;
    }

    public void setOrder_seq(String order_seq) {
        this.order_seq = order_seq;
    }

    public String getMeasure_value() {
        return measure_value;
    }

    public void setMeasure_value(String measure_value) {
        this.measure_value = measure_value;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }
}
