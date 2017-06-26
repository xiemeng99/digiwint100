package digiwin.smartdepott100.module.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public abstract class ExpandAdapter<T, M> extends BaseExpandableListAdapter {
    private LinkedHashMap<T, List<M>> data;

    private List<T> parentList;

    private boolean[][] status;

    private boolean b;


    /**
     * 子布局中EditText的Id
     */
    public abstract int getChildEditTextLayoutId();

    /**
     * 获取所有父容器的集合list
     *
     * @return
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

    public ExpandAdapter(LinkedHashMap<T, List<M>> data, boolean b) {
        this.data = data;
        this.b = b;
        initData(b);
    }

    public boolean[][] getStatus() {
        return status;
    }

    public void initData(boolean b) {
        parentList = new ArrayList<T>(data.keySet());
        status = new boolean[parentList.size()][];
        for (int i = 0; i < parentList.size(); i++) {
            boolean[] ints = new boolean[data.get(parentList.get(i)).size() + 1];
            for (int j = 0; j < data.get(parentList.get(i)).size() + 1; j++) {
                if (b) {
                    ints[j] = true;
                } else {
                    ints[j] = false;
                }
            }
            status[i] = ints;
        }
    }


    /**
     * 获得所有选中要被删除的子成员并且更新数据
     * <p>
     * 所有选中的list集合
     */
    public List<M> getCheckedList() {
        List<M> lists = new ArrayList<M>();
        LinkedHashMap<T, List<M>> data2 = new LinkedHashMap<T, List<M>>();
        for (int i = 0; i < status.length; i++) {
            if (status[i][0]) {// 父容器被选中
                lists.addAll(data.get(parentList.get(i)));
            } else {
                List<M> list = new ArrayList<M>();
                for (int j = 1; j < status[i].length; j++) {
                    if (status[i][j]) {
                        lists.add(data.get(parentList.get(i)).get(j - 1));
                    } else {
                        list.add(data.get(parentList.get(i)).get(j - 1));
                    }
                }
                data2.put(parentList.get(i), list);
            }
        }
        data = data2;
        initData(b);
        notifyDataSetChanged();
        return lists;
    }

    /**
     * 父布局中checkbox的ID
     *
     * @return
     */
    public abstract int getGroupCheckBoxId();

    /**
     * 子布局中checkbox的ID
     *
     * @return
     */
    public abstract int getChildCheckBoxId();

    @Override
    public Object getChild(int arg0, int arg1) {
        return data.get(parentList.get(arg0)).get(arg1);
    }

    @Override
    public long getChildId(int arg0, int arg1) {
        return setChildId(arg0, arg1);
    }

    public abstract long setChildId(int parentId, int childId);

    @SuppressWarnings("unchecked")
    @Override
    public View getChildView(int arg0, int arg1, boolean arg2, View arg3, ViewGroup arg4) {

        View view = setChildView(arg0, arg1, arg2, arg3, arg4);
        CheckBox mCheckBox = (CheckBox) view.findViewById(getChildCheckBoxId());

        mCheckBox.setTag(arg0 + "%" + (arg1 + 1));

        if (status[arg0][0]) {
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(status[arg0][arg1 + 1]);
        }

        mCheckBox.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view;
                String[] indexs = ((String) cb.getTag()).split("%");
                int arg0 = Integer.parseInt(indexs[0]);
                int arg1 = Integer.parseInt(indexs[1]);
                if (cb.isChecked()) {
                    status[arg0][arg1] = true;
                } else {
                    status[arg0][arg1] = false;
                }
                boolean flag = true;
                for (int i = 1; i < status[arg0].length; i++) {
                    flag = flag && status[arg0][i];
                }
                status[arg0][0] = flag;
                notifyDataSetChanged();

            }
        });

        EditText et = (EditText) view.findViewById(getChildEditTextLayoutId());
        et.setTag(getChild(arg0, arg1));

        et.addTextChangedListener(new MyTextWatch(et, this, arg0, arg1) {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                EditText et = this.getEditText();
                editChildTextChanged(arg0, et, (M) (et.getTag()), getGroupId(), getChildId());

            }
        });

        return view;
    }

    public abstract void editChildTextChanged(Editable arg0, EditText et, M t, int groupId, int childId);

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
        CheckBox cb = (CheckBox) view.findViewById(getGroupCheckBoxId());
        cb.setChecked(status[arg0][0]);
        cb.setTag(arg0);
        cb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CheckBox cb = (CheckBox) arg0;

                int index = (int) cb.getTag();
                if (cb.isChecked()) {
                    for (int i = 0; i < status[index].length; i++) {
                        status[index][i] = true;
                    }
                } else {
                    for (int i = 0; i < status[index].length; i++) {
                        status[index][i] = false;
                    }
                }
                notifyDataSetChanged();

            }
        });
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
        return setChildSelectable(arg0, arg1);
    }

    public abstract boolean setChildSelectable(int groupPosition, int childPosition);


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