package digiwin.smartdepott100.login.bean;

import org.litepal.crud.DataSupport;

import digiwin.smartdepott100.core.appcontants.AddressContants;

/**
 * @author xiemeng
 * @des 集团据点
 * @date 2017/5/10
 */
public class EntSiteBean extends DataSupport{
    /**
     * 集团编号
     */
    private String enterprise_no;
    /**
     * 集团名称
     */
    private String enterprise_lang;
    /**
     * 集团展示
     */
    private String enterprise_show;
    /**
     * 据点编号
     */
    private String site_no;
    /**
     * 据点名称
     */
    private String site_lang;
    /**
     *  据点展示
     */
    private String site_show;
    /**
     * 用户
     */
    private String account;

    public String getEnterprise_no() {
        return enterprise_no;
    }

    public void setEnterprise_no(String enterprise_no) {
        this.enterprise_no = enterprise_no;
    }

    public String getEnterprise_lang() {
        return enterprise_lang;
    }

    public void setEnterprise_lang(String enterprise_lang) {
        this.enterprise_lang = enterprise_lang;
    }

    public String getSite_no() {
        return site_no;
    }

    public void setSite_no(String site_no) {
        this.site_no = site_no;
    }

    public String getSite_lang() {
        return site_lang;
    }

    public void setSite_lang(String site_lang) {
        this.site_lang = site_lang;
    }

    public String getEnterprise_show() {
        return getEnterprise_no()+ AddressContants.SPLITFLAG+getEnterprise_lang();
    }

    public String getSite_show() {
        return getSite_no()+ AddressContants.SPLITFLAG+ getSite_lang();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
