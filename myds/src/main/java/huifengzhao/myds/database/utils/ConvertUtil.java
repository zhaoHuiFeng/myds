package huifengzhao.myds.database.utils;


import huifengzhao.myds.database.base.ReadBase;
import huifengzhao.myds.utils.DbReflectUtil;
import huifengzhao.myds.utils.KeyMap;
import huifengzhao.utils.StringUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author huifengzhao
 * @ClassName ConvertUtil
 * @date 2018/8/30
 */
public class ConvertUtil {
    public ConvertUtil() {
    }

    /**
     * 将获取到的结果集mapList封装
     *
     * @param readBase 操作对象
     * @param mapList  结果集对象
     * @param <T>      泛型,也就是需要封装对象的类型
     * @return bean对象的list集合
     * @throws Exception 封装过程中产生异常
     */
    public static <T> List<T> convertList(ReadBase<T> readBase, List<Map<String, Object>> mapList) throws Exception {
        Objects.requireNonNull(mapList, "list map");
        Objects.requireNonNull(readBase, "readBase");
        List<T> list = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            list.add(convertMap(readBase, map, null));
        }
        return list;

    }

    /**
     * 将map对象封装成bean对象
     *
     * @param readBase  操作的对象
     * @param map       map类型的结果
     * @param beanClass bean对象的类型
     * @param <T>       泛型,封装后对象的类型
     * @return 封装后的bean对象
     * @throws Exception 封装过程中产生的异常
     */
    public static <T> T convertMap(ReadBase<T> readBase, Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (beanClass == null) {
            beanClass = readBase.getBeanClass();
        }

        T obj = (T) beanClass.newInstance();
        if (obj == null) {
            return null;
        } else {
            KeyMap<String, Object> keyMap = new KeyMap<>(map);

            List<Method> setMethods = DbReflectUtil.getAllSetMethods(obj.getClass());
            for (Method method : setMethods) {
                String name = method.getName();
                if (name.startsWith("set")) {
                    String variable = StringUtil.setMethodToVariable(name);
                    Object value = keyMap.get(variable);
                    if (value != null) {
                        method.invoke(obj, value);
                    }
                }
            }
            return obj;
        }
    }

}
