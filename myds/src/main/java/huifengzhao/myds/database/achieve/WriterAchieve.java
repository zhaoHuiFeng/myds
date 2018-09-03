package huifengzhao.myds.database.achieve;

import huifengzhao.myds.database.base.Base;

/**
 * @author huifengzhao
 * @InterfaceName WriterAchieve
 * @date 2018/9/2
 */
public interface WriterAchieve {
    /**
     * 获取表名称的规则会随着项目的不同而改变,要修改规则需要实现该接口并重写该方法
     *
     * @param base      操作数据库的最顶级父类
     * @return 返回当前表的name
     */
    String getTableName(Base base);

}
