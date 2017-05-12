package digiwin.library.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;

import digiwin.library.constant.SystemConstant;
import digiwin.library.utils.LogUtils;


/**
 * 
 * 数据库基本方法
 * 
 * @author xiemeng
 * @version [V1.00, 2016-5-16]
 * @see [相关类/方法]
 * @since V1.00
 */
public class DBHelper extends SQLiteOpenHelper
{
    public final String TAG = "DBHelper";
    
    public DBHelper(Context context)
    {
        super(context, SystemConstant.DATABASE_NAME, null, SystemConstant.DATABASE_VERSION);
    }
    
    /**
     * 创建数据库�??
     * 
     * @param db SQLiteDatabase对象�?
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            for (Class<?> clazz : ERPTables.tableClasses)
            {
                createTable(db, clazz);
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LogUtils.e(TAG, "Exception-------onCreate");
        }
    }
    
    /**
     * 升级数据库�??
     * 
     * @param db SQLiteDatabase对象�?
     * @param oldVersion 旧数据库版本
     * @param newVersion 新数据库版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        try
        {
            for (Class<?> clazz : ERPTables.tableClasses)
            {
                dropTable(db, clazz);
            }
            onCreate(db);
        }
        catch (Exception e)
        {

            LogUtils.e(TAG, "Exception-------onUpgrade");
        }
    }
    
    /**
     * 根据对象创建对应的表�?
     * 
     * @param db SQLiteDatabase对象�?
     * @param clazz �?要生成的表对象�??
     */
    private void createTable(SQLiteDatabase db, Class<?> clazz)
    {
        try
        {
            
            StringBuffer sqlBuffer = new StringBuffer();
            String tableName = clazz.getSimpleName();
            sqlBuffer.append("create table if not exists " + tableName + "(_id integer primary key autoincrement,");
            generateCreateTableSql(clazz, sqlBuffer);
            String createSql = sqlBuffer.substring(0, sqlBuffer.length() - 1) + ");";
            db.execSQL(createSql);
        }
        catch (Exception e)
        {
            LogUtils.e(TAG, "Exception-------createTable");
        }
    }
    
    /**
     * 根据对象的字段生成建表语句�??
     * 
     * @param clazz 对象的类型�??
     * @param sqlBuffer 建表语句�?
     */
    private void generateCreateTableSql(Class<?> clazz, StringBuffer sqlBuffer)
    {
        try
        {
            
            Field[] fields = clazz.getFields();
            for (Field field : fields)
            {
                String fieldName = field.getName();
                sqlBuffer.append(fieldName + " TEXT,");
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LogUtils.e(TAG, "Exception-------generateCreateTableSql");
        }
    }
    
    /**
     * 根据对象删除对应的表�?
     * 
     * @param db SQLiteDatabase对象�?
     * @param clazz �?要删除的表对象�??
     */
    private void dropTable(SQLiteDatabase db, Class<?> clazz)
    {
        try
        {
            String sql = "drop table if exists " + clazz.getSimpleName();
            db.execSQL(sql);
        }
        catch (Exception e)
        {
            // TODO: handle exception
            LogUtils.e(TAG, "Exception-------dropTable");

        }
    }
}
