package digiwin.smartdepott100.module.bean.sale.scanout;

/**
 * @author maoheng
 * @des 扫码出货(主键)
 * @date 2017/4/3
 */

public class ScanOutDetailKeyBean {
    /**
     * 箱号
     */
    private String package_no;
    /**
     * 储位编号
     */
    private String storage_spaces_no;

    public String getPackage_no() {
        return package_no;
    }

    public void setPackage_no(String package_no) {
        this.package_no = package_no;
    }

    public String getStorage_spaces_no() {
        return storage_spaces_no;
    }

    public void setStorage_spaces_no(String storage_spaces_no) {
        this.storage_spaces_no = storage_spaces_no;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + package_no.hashCode();
        result = prime * result + storage_spaces_no.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ScanOutDetailKeyBean && this.package_no.equals(((ScanOutDetailKeyBean)o).package_no) && this.storage_spaces_no.equals(((ScanOutDetailKeyBean)o).storage_spaces_no);
    }
}
