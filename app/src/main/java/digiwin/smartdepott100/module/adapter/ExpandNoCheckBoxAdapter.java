package digiwin.smartdepott100.module.adapter;

import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 只当做显示用
 */
public abstract class ExpandNoCheckBoxAdapter<T, M> extends BaseExpandableListAdapter {
    private LinkedHashMap<T, List<M>> data;

    public List<T> parentList;

    /**
     * 获取所有父容器的集合list
     */
    public List<T> getParentList() {
        return parentList;
    }

    /**
     * 获取所有的数据 以父类为key 子类list为value的 hashmap
     */
    public LinkedHashMap<T, List<M>> getAllData() {
        return data;
    }

    public ExpandNoCheckBoxAdapter(LinkedHashMap<T, List<M>> data) {
        this.data = data;
        parentList = new ArrayList<T>(data.keySet());
    }
    @Override
    public Object getChild(int arg0, int arg1) {
        return data.get(parentList.get(arg0)).get(arg1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getChildView(int arg0, int arg1, boolean arg2, View arg3, ViewGroup arg4) {
        View view = setChildView(arg0, arg1, arg2, arg3, arg4);
        return view;
    }

    /**
     * 设置子布局
     *
     * @param groupPosition 父布局position
     * @param childPosition 子布局position
     * @param isLastChild   boolean
     * @param convertView
     * @param parent
     * @return
     */
    public abstract View setChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent);

    @Override
    public int getChildrenCount(int arg0) {
        return data.get(parentList.get(arg0)).size();
    }

    @Override
    public Object getGroup(int arg0) {
        return parentList.get(arg0);
    }

    @Override
    public int getGroupCount() {
        return parentList.size();
    }

    @Override
    public long getGroupId(int arg0) {
        return setGroupId(arg0);
    }

    public abstract long setGroupId(int arg0);

    /**
     * 设置父组件布局
     */
    @SuppressWarnings("unchecked")
    @Override
    public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
        View view = setGroupView(arg0, arg1, arg2, arg3);
        return view;

    }
    public abstract View setGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent);

    @Override
    public boolean hasStableIds() {
        return setStableIds();
    }

    public abstract boolean setStableIds();

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    abstract class MyTextWatch<T, M> implements TextWatcher {
        private EditText et;

        private int groupId;

        private int childId;

        private ExpandAdapter<T, M> adapter;

        public MyTextWatch(EditText et, ExpandAdapter<T, M> adapter, int groupId, int childId) {
            this.et = et;
            this.adapter = adapter;
            this.groupId = groupId;
            this.childId = childId;
        }

        public int getGroupId() {
            return groupId;
        }

        public int getChildId() {
            return childId;
        }

        public EditText getEditText() {
            return et;
        }

        public ExpandAdapter<T, M> getAdapter() {
            return adapter;
        }

    }
}