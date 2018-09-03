package huifengzhao.myds.database.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ReadBase<T> extends Base<T> {
    protected String colums;
    private List<Object> parameters;
    private Result resultType;
    private int isDelete;
    private Object keyValue;
    private String keyColumn;
    private String where;

    protected ReadBase() {
        this.resultType = ReadBase.Result.Entity;
        this.isDelete = -100;
        this.setThrows(true);
    }

    public String getColums() {
        return colums;
    }

    public void setColums(String colums) {
        this.colums = colums;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public Object getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(Object keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    /**
     * 获取结果集对象
     *
     * @return 返回一个结果集对象
     */
    public List<Object> getParameters() {
        return this.parameters == null ? new ArrayList() : this.parameters;
    }

    /**
     * 设置结果集对象
     *
     * @param parameters 可变参数,结果集对象,可以传一个或者多个结果集
     */
    public void setParameters(Object... parameters) {
        if (this.parameters == null) {
            this.parameters = new LinkedList<>();
        }

        if (parameters != null) {
            Collections.addAll(this.parameters, parameters);
        }

    }

    /**
     * 获取结果集对象的类型
     *
     * @return 结果集对象的类型
     */
    public Result getResultType() {
        return resultType;
    }

    /**
     * 设置结果集对象的类型
     *
     * @param resultType 结果集对象的类型
     */
    public void setResultType(Result resultType) {
        this.resultType = resultType;
    }

    public static enum Result {
        /**
         * 返回对象的类型
         */
        JsonArray,
        JsonObject,
        Entity,
        ListEntity,
        ListMap,
        String,
        Integer,
        PageResultType;

        private Result() {
        }
    }
}