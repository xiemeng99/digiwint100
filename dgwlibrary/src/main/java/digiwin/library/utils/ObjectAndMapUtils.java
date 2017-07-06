package digiwin.library.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/14
 */

public class ObjectAndMapUtils {
    /**
     * JavaBean-->Map
     *
     * @param obj
     * @return
     */
    public static <T> Map<String, String> getValueMap(T obj) {

        Map<String, String> map = new HashMap<String, String>();
        if (null != obj) {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (int i = 0, len = fields.length; i < len; i++) {
                String varName = fields[i].getName();
                try {
                    boolean accessFlag = fields[i].isAccessible();
                    fields[i].setAccessible(true);
                    Object o = fields[i].get(obj);
                    if (o != null)
                        map.put(varName, o.toString());
                    fields[i].setAccessible(accessFlag);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * List<JavaBean>-->List<Map>
     *
     * @param list
     * @return
     */
    public static <T> List<Map<String, String>> getListMap(List<T> list) {
        List<Map<String, String>> values = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, String> valueMap = getValueMap(list.get(i));
            values.add(valueMap);
        }
        return values;
    }

    /**
     * Map-->JavaBean
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
        if (map == null)
            return null;

        Object obj = null;
        try {
            obj = beanClass.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }

                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }

    /**
     * List<Map>-->List<JavaBean>
     *
     * @param maps
     * @param beanClass
     * @return
     */
    public static List<Object> mapsToObjects(List<Map<String, Object>> maps, Class<?> beanClass) {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            Object o = mapToObject(maps.get(i), beanClass);
            objects.add(o);
        }
        return objects;
    }
}
