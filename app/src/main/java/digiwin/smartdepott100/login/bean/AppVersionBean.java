package digiwin.smartdepott100.login.bean;


import java.io.Serializable;

/**
 * @des      版本更新
 * @author  xiemeng
 * @date    2017/1/18
 */

public class AppVersionBean  implements Serializable{
    /**
     * varchar2(100)   更新地址
     */
   private String  verurl   ="";
    /**
     *  number(10,2)   版本号
     */
    private String  vernum   ="";
    /**
     *  varchar2(100)   更新说明
     */
    private String   verwhat ="";

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
}
