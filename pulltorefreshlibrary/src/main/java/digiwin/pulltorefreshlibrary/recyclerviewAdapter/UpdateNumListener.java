package digiwin.pulltorefreshlibrary.recyclerviewAdapter;

/**
 * @des     明细中修改数量使用
 * @author  xiemeng
 * @date    2017/3/10
 */
public  interface UpdateNumListener<T>{
    public void update(T item, int pos, RecyclerViewHolder holder);
}
