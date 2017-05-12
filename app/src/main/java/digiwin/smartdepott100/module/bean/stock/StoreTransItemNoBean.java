package digiwin.smartdepott100.module.bean.stock;

/**
 * @author maoheng
 * @des 库存交易锁定料号获取javabean
 * @date 2017/3/28
 */

public class StoreTransItemNoBean {
    /**
     * 料号
     */
    private String item_no = null;
    /**
     * 品名
     */
    private String item_name = null;
    /**
     * 规格
     */
    private String item_spec = null;

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
}
