package huifengzhao.myds.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huifengzhao
 * @ClassName DbReflectUtil
 * @date 2018/8/30
 */
public class DbReflectUtil {
    public DbReflectUtil() {
    }

    public static Object getFieldValue(Object obj, String fieldName) throws IllegalAccessException {
        Objects.requireNonNull(obj);
        Field field = getField(obj.getClass(), fieldName);
        return field.get(obj);
    }

    public static List<Field> getDeclaredFields(Class<?> cls) {
        String key = cls.getName() + "_DeclaredFields";
        Object object = ReflectCache.get(key);
        if (object instanceof List) {
            return (List) object;
        } else {
            Map<String, Field> map = getFieldMap(cls);
            List<Field> fieldList = new ArrayList(map.values());
            ReflectCache.put(key, fieldList);
            return fieldList;
        }
    }

    private static Map<String, Field> getFieldMap(Class<?> cls) {
        String key = cls.getName() + "_DeclaredFields_Map";
        Object object = ReflectCache.get(key);
        if (object instanceof List) {
            return (Map) object;
        } else {
            Map<String, Field> map = new HashMap();

            for (Class clazz = cls; clazz != Object.class; clazz = clazz.getSuperclass()) {
                Field[] var5 = clazz.getDeclaredFields();
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    Field item = var5[var7];
                    String name = item.getName().toLowerCase();
                    if (!map.containsKey(name)) {
                        item.setAccessible(true);
                        map.put(name, item);
                    }
                }
            }

            ReflectCache.put(key, map);
            return map;
        }
    }

    public static Field getField(Class<?> cls, String fieldName) {
        Objects.requireNonNull(fieldName);
        Map<String, Field> map = getFieldMap(cls);
        return (Field) map.get(fieldName.toLowerCase());
    }

    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) throws IllegalAccessException {
        Field field = getField(obj.getClass(), fieldName);
        Class type = field.getType();
        if (fieldValue == null) {
            field.set(obj, (Object) null);
        } else {
            fieldValue = convertType(fieldValue, type);
            field.set(obj, fieldValue);
        }
    }

    public static Object convertType(Object object, Class needType) {
        if (object == null) {
            return null;
        } else if (object.getClass() == needType) {
            return object;
        } else if (needType != Integer.TYPE && needType != Integer.class) {
            if (needType != Long.TYPE && needType != Long.class) {
                if (needType != Double.TYPE && needType != Double.class) {
                    if (needType != Float.TYPE && needType != Float.class) {
                        if (needType == BigDecimal.class) {
                            return BigDecimal.valueOf(Long.valueOf(String.valueOf(object)));
                        } else if (needType != Short.class && Short.TYPE != needType) {
                            return needType != Boolean.TYPE && Boolean.class != needType ? object : Boolean.valueOf(String.valueOf(object));
                        } else {
                            return Short.valueOf(String.valueOf(object));
                        }
                    } else {
                        return Float.valueOf(String.valueOf(object));
                    }
                } else {
                    return Double.valueOf(String.valueOf(object));
                }
            } else {
                return Long.valueOf(String.valueOf(object));
            }
        } else {
            return Integer.valueOf(String.valueOf(object));
        }
    }

    /**
     * 获取泛型对象的class
     *
     * @param beanClass beanClass
     * @return 第一个泛型参数的类型类
     */
    public static Class<?> getBeanClass(Class<?> beanClass) {
        Type type = beanClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            return null;
        }
    }

    /**
     * 利用反射的原理,获取bean类中所有的方法对象的集合
     *
     * @param beanClass bean类型的class对象
     * @return bean的方法对象集合
     */
    public static List<Method> getAllSetMethods(Class beanClass) {
        return getAllMethods(beanClass, "set");
    }

    /**
     * 获取bean类中所有的方法对象的集合,说白了就是把setXxx()转换一下
     *
     * @param beanClass bean类型的class对象
     * @param prefix    前缀
     * @return bean的方法对象集合
     */
    private static List<Method> getAllMethods(Class beanClass, String prefix) {
        Objects.requireNonNull(beanClass);
        String key = beanClass.getName() + "_" + prefix;
        Object object = ReflectCache.get(key);
        if (object instanceof List) {
            return (List<Method>) object;
        } else {
            List<Method> list = new ArrayList<>();

            for (Class clazz = beanClass; clazz != Object.class; clazz = clazz.getSuperclass()) {
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().startsWith(prefix)) {
                        list.add(method);
                    }
                }
            }

            ReflectCache.put(key, list);
            return list;
        }
    }

    private static final class ReflectCache {
        private static final ConcurrentHashMap<String, Object> CACHE = new ConcurrentHashMap<>();

        private ReflectCache() {
        }

        static void put(String key, Object object) {
            CACHE.put(key, object);
        }

        static Object get(String key) {
            return CACHE.get(key);
        }
    }
}