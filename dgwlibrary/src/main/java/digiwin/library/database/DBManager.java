package digiwin.library.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import digiwin.library.utils.LogUtils;


/**
 * 
 * 增删改查
 * 
 * @author xiemeng
 * @version [V1.00, 2016-5-16]
 * @see [相关类/方法]
 * @since V1.00
 */

public class DBManager
{
    
    private final String TAG = "DBManager";
    
    private static DBManager sInstance = null;
    
    private DBHelper helper;
    
    private SQLiteDatabase db;
    
    private DBManager(Context context)
    {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
    
    public synchronized static DBManager getInstance(Context context)
    {
        if (sInstance == null)
        {
            sInstance = new DBManager(context);
        }
        return sInstance;
    }
    
    /**
     * 删除指定对象的某条记录
     * 
     * @param
     * @param whereClause
     * @param whereArgs
     */
    public void deleteData(Class<?> clazz, String whereClause, String[] whereArgs)
    {
        try
        {
            db.delete(clazz.getSimpleName(), whereClause, whereArgs);
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "Exception----------------deleteData");
        }
    }
    
    public void deleteAllData(Class<?> clazz)
    {
        try
        {
            db.delete(clazz.getSimpleName(), null, null);
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "Exception----------------deleteAllData");
        }
    }
    
    /**
     * 更新某一条数据
     * 
     * @param data
     * @param whereClause
     * @param whereArgs
     */
    public void updateData(Object data, String whereClause, String[] whereArgs)
    {
        ContentValues values = new ContentValues();
        try
        {
            putContentValues(data, values);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        db.update(data.getClass().getSimpleName(), values, whereClause, whereArgs);
    }
    
    public void updateAllData(Class<?> clazz, List<?> data)
    {
        try
        {
            deleteAllData(clazz);
            insertListData(data);
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "Exception----------------updateAllData");
        }
    }
    
    /**
     * 
     * 根据条件查询结果
     * 
     */
    public ArrayList<Object> queryData(Class<?> clazz, String selection, String[] selectionArgs)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        try
        {
            Cursor cursor = db.query(clazz.getSimpleName(), null, selection, selectionArgs, null, null, null);
            LogUtils.i("DBManager", "queryData_cursor:" + cursor.getCount());
            while (cursor.moveToNext())
            {
                list.add(getDataFromCursor(clazz, cursor));
            }
            cursor.close();
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LogUtils.e(TAG, "Exception----------------queryData");
            
        }
        return list;
    }
    
    /**
     * 排序查询数据
     */
    public ArrayList<Object> queryDataOrderBy(Class<?> clazz, String selection, String[] selectionArgs, String order)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        try
        {
            Cursor cursor = db.query(clazz.getSimpleName(), null, selection, selectionArgs, null, null, order);
            LogUtils.i("DBManager", "queryDataOrderBy cursor:" + cursor.getCount());
            while (cursor.moveToNext())
            {
                list.add(getDataFromCursor(clazz, cursor));
                
            }
            cursor.close();
            
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "Exception----------------queryDataOrderBy");
            
        }
        return list;
    }
    
    /**
     * 
     * 排序查询全部数据
     * */
    public ArrayList<Object> queryAllDataOrderBy(Class<?> clazz, String order)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        try
        {
            
            Cursor cursor = db.query(clazz.getSimpleName(), null, null, null, null, null, order);
            LogUtils.i("DBManager", "cursor:" + cursor.getCount());
            while (cursor.moveToNext())
            {
                list.add(getDataFromCursor(clazz, cursor));
                
            }
            cursor.close();
            
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "Exception----------------queryAllDataOrderBy");
        }
        return list;
    }
    
    /**
     * 查询所有数据
     */
    public List<Object> queryAllData(Class<?> clazz)
    {
        List<Object> dataList = new ArrayList<Object>();
        try
        {
            Cursor cursor = db.query(clazz.getSimpleName(), null, null, null, null, null, null);
            while (cursor.moveToNext())
            {
                dataList.add(getDataFromCursor(clazz, cursor));
                
            }
            cursor.close();
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "Exception----------------queryAllData");
            
        }
        return dataList;
    }
    
  //  public int pagecount = numb();
    
    // ErpApplication..getSharedPreferences("pagesize", Context.MODE_WORLD_WRITEABLE).getString("pageSize", null);
    
   
    
//    /**
//     * 分页查询本地数据库
//     *
//     * @return
//     * @see [类、类#方法、类#成员]
//     */
//    public List<Object> findByPage(Class<?> clazz, String where, int pageNumber, String order)
//    {
//
//        List<Object> dataList = new ArrayList<Object>();
//        try
//        {
//
//            int startPage = (pageNumber - 1) *  DBUtils.numb() ;
//            // int endPage = pagecount * pageNumber;
//            String sql = "select * from " + clazz.getSimpleName() + " where " + where + " order  by " + order + " limit " + startPage + "," +   DBUtils.numb() ;
//            LogUtils.i(TAG, "分页查询的sql:" + sql);
//            Cursor cursor = db.rawQuery(sql, null);
//            LogUtils.i(TAG, "----------cursor-------------");
//            while (cursor.moveToNext())
//            {
//                dataList.add(getDataFromCursor(clazz, cursor));
//            }
//            cursor.close();
//
//        }
//        catch (Exception e)
//        {
//            LogUtils.i(TAG, "排序查找发生异常");
//        }
//        return dataList;
//    }
    
    /**
     * 将对象插入到数据
     * 
     * @param data
     */
    public void insertData(Object data)
    {
        try
        {
            ContentValues values = new ContentValues();
            putContentValues(data, values);
            db.insert(data.getClass().getSimpleName(), null, values);
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 将对象集合插入数据库
     * 
     * @param data
     */
    public void insertListData(List<?> data)
    {
        db.beginTransaction();// 开始事务
        try
        {
            for (Object obj : data)
            {
                ContentValues values = new ContentValues();
                putContentValues(obj, values);
                db.insert(obj.getClass().getSimpleName(), null, values);
            }
            db.setTransactionSuccessful();// 设置事务成功完成
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 结束事务, 将事务成功点前面的代码提�?
            db.endTransaction();// 结束事务
        }
    }
    
    /**
     * 将插入数据库的对象封装为数据库的集合对象�?
     * 
     * @param values 数据库的集合对象�?
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private void putContentValues(Object data, ContentValues values)
        throws IllegalArgumentException, IllegalAccessException
    {
        try
        {
            
            Field[] fields = data.getClass().getFields();
            for (Field field : fields)
            {
                String key = field.getName();
                String value = (String)field.get(data);
                values.put(key, value);
            }
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "Exception----------------putContentValues");
        }
    }
    
    /**
     * 从Cursor中获取数据对象
     * 
     * @param clazz
     * @param c
     */
    private Object getDataFromCursor(Class<?> clazz, Cursor c)
    {
        Object data = null;
        try
        {
            data = clazz.newInstance();
            Field[] fields = clazz.getFields();
            for (Field field : fields)
            {
                field.set(data, c.getString(c.getColumnIndex(field.getName())));
            }
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return data;
    }
    
    public void closeDB()
    {
        db.close();
    }
}
