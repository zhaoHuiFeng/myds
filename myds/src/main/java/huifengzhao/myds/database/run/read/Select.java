package huifengzhao.myds.database.run.read;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import huifengzhao.myds.database.base.ReadBase;
import huifengzhao.myds.database.utils.ConvertUtil;
import huifengzhao.myds.database.utils.DruidUtil;
import huifengzhao.myds.database.utils.JdbcUtil;
import huifengzhao.myds.database.utils.SqlUtil;
import huifengzhao.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @author huifengzhao
 * @ClassName Select
 * @date 2018/8/29
 */
public class Select<T extends Serializable> extends ReadBase<T> {

    private static final transient Logger log = LoggerFactory.getLogger(Select.class);


    public Select() {
    }

    /**
     * 调用查询
     *
     * @param <T> ....
     * @return 查询结果
     */
    public <T> T run() {
        try {
            String runSql = this.sql;
            if (StringUtil.isEmpty(runSql)) {
                runSql = SqlUtil.getSelectSql(this);
            }
            DataSource dataSource = DruidUtil.getDataSource();
            List<Map<String, Object>> result = JdbcUtil.executeQuery(dataSource, runSql, getParameters());
            Result resultType = getResultType();
            if (resultType == null) {
                resultType = Result.Entity;
            }
            log.info("MySql:  " + sql);
            switch (resultType) {
                case JsonArray:
                    return (T) JSON.toJSON(result);
                case JsonObject:
                    if (result != null && result.size() > 0) {
                        Map map = result.get(0);
                        return (T) new JSONObject(map);
                    }
                    return null;
                case Entity:
                    return (T) ConvertUtil.convertMap(this, result.get(0), getBeanClass());
                case ListEntity:
                    return (T) ConvertUtil.convertList(this, result);
                case ListMap:
                    return (T) result;
                case String:
                    break;
                case Integer:
                    break;
                case PageResultType:
                    break;
                default:
            }
        } catch (Exception e) {
            isThrows(e);
        }
        return null;
    }


}
