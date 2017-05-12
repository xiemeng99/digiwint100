package digiwin.smartdepott100.login.bean;

import org.litepal.crud.DataSupport;

/**
 * @author xiemeng
 * @des 用户信息
 * @date 2017/1/18
 */
public class AccoutBean extends DataSupport {

    /**
     * id

     */
    private  int id;
    /**
     * varchar2(10)  集团
     */
    private String enterprise_no;

    /**
     * 据点
     */
    private String site_no;

    /**
     * varchar2(10)  集团展示
     */
    private String enterpriseShow;
    /**
     * 据点展示
     */
    private String siteShow;
    /**
     * 登录账号
     */
    public String account;
    /**
     * varchar2(10)  用户编码
     */
    private String employee_no;
    /**
     * varchar2(80)  用户名称
     */
    private String employee_name;
    /**
     * varchar2(10)  部门编码
     */
    private String department_no;
    /**
     * varchar2(80)   部门名称
     */
    private String department_name;
    /**
     * varchar2(4000) 权限模组
     */
    private String access;
    /**
     * varchar2(4000) 更新地址
     */
    private String verurl;
    /**
     * varchar2(10)   服务器版本号
     */
    private String vernum;
    /**
     * varchar2(10)   本次更新说明
     */
    private String verwhat;

    /**
     * 是否记住密码。Y为记住，N为否
     */
    private String isRemeberPassWord;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 仓库
     */
    private String ware;
    /**
     * 语言
     */
    private String language;

    public String getEnterprise_no() {
        return enterprise_no;
    }

    public void setEnterprise_no(String enterprise_no) {
        this.enterprise_no = enterprise_no;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getDepartment_no() {
        return department_no;
    }

    public void setDepartment_no(String department_no) {
        this.department_no = department_no;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getVerurl() {
        return verurl;
    }

    public void setVerurl(String verurl) {
        this.verurl = verurl;
    }

    public String getVernum() {
        return vernum;
    }

    public void setVernum(String vernum) {
        this.vernum = vernum;
    }

    public String getVerwhat() {
        return verwhat;
    }

    public void setVerwhat(String verwhat) {
        this.verwhat = verwhat;
    }

    public String getIsRemeberPassWord() {
        return isRemeberPassWord;
    }

    public void setIsRemeberPassWord(String isRemeberPassWord) {
        this.isRemeberPassWord = isRemeberPassWord;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWare() {
        return ware;
    }

    public void setWare(String ware) {
        this.ware = ware;
    }

    public String getSite_no() {
        return site_no;
    }

    public void setSite_no(String site_no) {
        this.site_no = site_no;
    }

    public String getEnterpriseShow() {
        return enterpriseShow;
    }

    public void setEnterpriseShow(String enterpriseShow) {
        this.enterpriseShow = enterpriseShow;
    }

    public String getSiteShow() {
        return siteShow;
    }

    public void setSiteShow(String siteShow) {
        this.siteShow = siteShow;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
