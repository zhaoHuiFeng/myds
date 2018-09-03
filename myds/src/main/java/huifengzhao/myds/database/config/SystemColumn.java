package huifengzhao.myds.database.config;

import com.alibaba.druid.util.StringUtils;
import huifengzhao.myds.utils.DbReflectUtil;
import huifengzhao.utils.StringUtil;

import java.util.*;

/**
 * @author huifengzhao
 * @ClassName SystemColumn
 * @date 2018/9/1
 */
public class SystemColumn {

    private static String pwdColumn = "";
    private static final List<String> NOT_PUT_UPDATE = new ArrayList<>();
    private static final HashMap<String, String> COLUMN_DEFAULT_VALUE = new HashMap<>();
    private static final List<String> WRITE_DEFAULT_REMOVE = new ArrayList<>();
    private static final List<String> READ_DEFAULT_REMOVE = new ArrayList<>();
    private static String defaultSelectColumns = "*";
    private static String defaultRefKeyName = "id";
    private static String defaultKeyName = "id";

    public SystemColumn() {
    }

    public static String getDefaultRefKeyName() {
        return defaultRefKeyName;
    }

    public static String getDefaultKeyName() {
        return defaultKeyName;
    }

    public static String getDefaultSelectColumns() {
        return defaultSelectColumns;
    }

    public static boolean isWriteRemove(String name) {
        return name != null && WRITE_DEFAULT_REMOVE.contains(name.toLowerCase());
    }

    public static boolean isReadRemove(String name) {
        return name != null && READ_DEFAULT_REMOVE.contains(name.toLowerCase());
    }

    public static String getDefaultValue(String name) {
        return StringUtils.isEmpty(name) ? null : (String)COLUMN_DEFAULT_VALUE.get(name.toLowerCase());
    }

    public static boolean notCanUpdate(String name) {
        return StringUtils.isEmpty(name) || NOT_PUT_UPDATE.contains(name.toLowerCase());
    }

    public static String getPwdColumn() {
        return pwdColumn;
    }

    static void init(Properties properties) {
        String tempPwdColumn = properties.getProperty("systemColumn.pwd");
        pwdColumn = StringUtil.convertNULL(tempPwdColumn);
        String column = properties.getProperty("systemColumn.active");
        SystemColumn.Active.column = StringUtil.convertNULL(column);
        if (!StringUtils.isEmpty(SystemColumn.Active.column)) {
            SystemColumn.Active.activeValue = Integer.parseInt(properties.getProperty("systemColumn.active.value"));
            SystemColumn.Active.inActiveValue = Integer.parseInt(properties.getProperty("systemColumn.inActive.value"));
        }

        String status = properties.getProperty("systemColumn.modify.status");
        String notPutUpdate;
        String defaultValue;
        if (Boolean.valueOf(status)) {
            SystemColumn.Modify.status = true;
            notPutUpdate = properties.getProperty("systemColumn.modify.column");
            if (StringUtils.isEmpty(notPutUpdate)) {
                throw new IllegalArgumentException("systemColumn.modify.column is null");
            }

            SystemColumn.Modify.column = notPutUpdate;
            defaultValue = properties.getProperty("systemColumn.modify.time");
            if (StringUtils.isEmpty(defaultValue)) {
                throw new IllegalArgumentException("systemColumn.modify.time is null");
            }

            SystemColumn.Modify.time = defaultValue;
        }

        notPutUpdate = properties.getProperty("systemColumn.notPutUpdate");
        if (!StringUtils.isEmpty(notPutUpdate)) {
            String[] array = StringUtil.stringToArray(notPutUpdate.toLowerCase());
            if (array != null) {
                NOT_PUT_UPDATE.addAll(Arrays.asList(array));
            }
        }

        defaultValue = properties.getProperty("systemColumn.columnDefaultValue");
        String[] array;
        String keyDef;
        if (!StringUtils.isEmpty(defaultValue)) {
            array = StringUtil.stringToArray(defaultValue, ",");
            if (array != null) {
                for (String anArray : array) {
                    keyDef = anArray;
                    String[] value = keyDef.split(":");
                    COLUMN_DEFAULT_VALUE.put(value[0].toLowerCase(), value[1]);
                }
            }
        }

        String writeDef = properties.getProperty("systemColumn.writeDefaultRemove");
        if (!StringUtils.isEmpty(writeDef)) {
            array = StringUtil.stringToArray(writeDef.toLowerCase());
            if (array != null) {
                WRITE_DEFAULT_REMOVE.addAll(Arrays.asList(array));
            }
        }

        String readDef = properties.getProperty("systemColumn.readDefaultRemove");
        if (!StringUtils.isEmpty(readDef)) {
            array = StringUtil.stringToArray(readDef.toLowerCase());
            if (array != null) {
                READ_DEFAULT_REMOVE.addAll(Arrays.asList(array));
            }
        }

        String columnsDef = properties.getProperty("systemColumn.selectDefaultColumns");
        if (!StringUtils.isEmpty(columnsDef)) {
            defaultSelectColumns = columnsDef;
        }

        String refKeyDef = properties.getProperty("systemColumn.defaultRefKeyName");
        if (!StringUtils.isEmpty(refKeyDef)) {
            defaultRefKeyName = refKeyDef;
        }

        keyDef = properties.getProperty("systemColumn.defaultKeyName");
        if (!StringUtils.isEmpty(keyDef)) {
            defaultKeyName = keyDef;
        }

    }

    public static class Modify {
        private static boolean status = false;
        private static String column;
        private static String time;

        public Modify() {
        }

        public static String getColumn() {
            return column;
        }

        public static String getTime() {
            return time;
        }

        public static boolean isStatus() {
            return status;
        }
    }

    public static class Active {
        public static final int NO_ACTIVE = -100;
        private static String column = "";
        private static int activeValue = -100;
        private static int inActiveValue;

        public Active() {
        }

        public static int getInActiveValue() {
            return inActiveValue;
        }

        public static String getColumn() {
            return column;
        }

        public static int getActiveValue() {
            return activeValue;
        }
    }
}
