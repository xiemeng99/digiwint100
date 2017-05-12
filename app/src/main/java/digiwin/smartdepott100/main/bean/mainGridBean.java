package digiwin.smartdepott100.main.bean;

/**
 * Created by ChangquanSun
 * 2017/1/16
 * recyclerview类似gridview效果
 */

public class mainGridBean {

    private int drawableResource;
    private String title;

    public mainGridBean(int drawableResource, String title) {
        this.drawableResource = drawableResource;
        this.title = title;
    }

    public int getDrawableResource() {
        return drawableResource;
    }

    public void setDrawableResource(int drawableResource) {
        this.drawableResource = drawableResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
