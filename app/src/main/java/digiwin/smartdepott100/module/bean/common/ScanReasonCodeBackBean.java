package digiwin.smartdepott100.module.bean.common;


/**
 * @author xiemeng
 * @des 扫描理由码共同使用
 * @date 2017/2/23
 */
public class ScanReasonCodeBackBean {
//    reason_code_no	           String		理由码编号
//    reason_code_name	       String		理由码名称
//    showing                       string    app显示


    /**
     * 理由码编号
     */
    private String reason_code_no;
    /**
     *  理由码名称
     */
    private String reason_code_name;
    /**
     * 展示
     */
    private String showing;

    public String getReason_code_no() {
        return reason_code_no;
    }

    public void setReason_code_no(String reason_code_no) {
        this.reason_code_no = reason_code_no;
    }

    public String getReason_code_name() {
        return reason_code_name;
    }

    public void setReason_code_name(String reason_code_name) {
        this.reason_code_name = reason_code_name;
    }

    public String getShowing() {
        return showing;
    }

    public void setShowing(String showing) {
        this.showing = showing;
    }
}
