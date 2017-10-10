package digiwin.smartdepott100.module.bean.sale;

import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.base.BaseApplication;
import digiwin.smartdepott100.login.loginlogic.LoginLogic;

/**
 * @author xiemeng
 * @des oqc获取列表请求
 * @date 2017/10/3 11:24
 */

public class OQCReqBean {

    public OQCReqBean() {
        BaseApplication instance = BaseApplication.getInstance();
        this.pagesize = (String) SharedPreferencesUtils.get(instance, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM);
    }

    /**
     * 每页笔数
     */
    private String pagesize;
    /**
     * 来源单号
     */
    private String source_no;
    /**
     * 参考单号
     */
    private String refer_no;
    /**
     * 条码
     */
    private String barcode_no;
    /**
     * 客户编码
     */
    private String customer_no;
    /**
     * 开始日期
     */
    private String date_begin;
    /**
     * 结束日期
     */
    private String date_end;

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getSource_no() {
        return source_no;
    }

    public void setSource_no(String source_no) {
        this.source_no = source_no;
    }

    public String getRefer_no() {
        return refer_no;
    }

    public void setRefer_no(String refer_no) {
        this.refer_no = refer_no;
    }

    public String getBarcode_no() {
        return barcode_no;
    }

    public void setBarcode_no(String barcode_no) {
        this.barcode_no = barcode_no;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(String date_begin) {
        this.date_begin = date_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }
}
