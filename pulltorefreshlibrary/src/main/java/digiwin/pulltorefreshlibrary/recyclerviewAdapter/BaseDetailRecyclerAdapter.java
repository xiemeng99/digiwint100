package digiwin.pulltorefreshlibrary.recyclerviewAdapter;

import android.content.Context;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @des      item中有全选按钮和修改时使用
 * @author  xiemeng
 * @date    2017/3/10
 */
public abstract class BaseDetailRecyclerAdapter<T>  extends BaseRecyclerAdapter<T>{

    public UpdateNumListener listener;

    public UpdateNumListener getListener() {
        return listener;
    }

    public void setListener(UpdateNumListener listener) {
        this.listener = listener;
    }

    public Map<Integer, Boolean> map;

    public Map<Integer, Boolean> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Boolean> map) {
        this.map = map;
    }
    public BaseDetailRecyclerAdapter(Context ctx, List list) {
        super(ctx, list);
    }
    /**
     * 是否全选
     */
    public  void isCheckAll(){
        try {
            Set<Map.Entry<Integer, Boolean>> sets = map.entrySet();
            List<Object> deletelist = new ArrayList<>();
            int i=0;
            for (Map.Entry<Integer, Boolean> entry : sets) {
                Boolean val = entry.getValue();
                if (null != val&&val) {
                    i++;
                }
            }
            if (i==mItems.size()){
                getPCheckBox().setChecked(true);
            }else{
                getPCheckBox().setChecked(false);
            }
        }catch (Exception e){

        }
    }

    /**
     * 获取使用actvitity的全选按钮
     * @return
     */
    public  abstract CheckBox getPCheckBox();

}
