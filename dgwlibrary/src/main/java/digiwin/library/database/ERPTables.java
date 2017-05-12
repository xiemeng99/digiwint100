package digiwin.library.database;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 数据库表
 * 
 * @author xiemeng
 * @version [V1.00, 2016-5-16]
 * @see [相关类/方法]
 * @since V1.00
 */
public class ERPTables
{
    /**
     * 存放实体类的集合
     */
    final static List<Class<?>> tableClasses = new ArrayList<>();

    public static void addClass(Class<?> cls){
        tableClasses.add(cls);
    }
}
