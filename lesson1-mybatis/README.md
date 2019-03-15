## MyBatis解析

### 1. MybatisProperties

参数名         | 用处              | memo     |
--------------------|------------------|-----------------------|
mapperLocations |  指定mapper.xml路径，spring boot项目，默认在resource   | mybatis.mapperlocations=classpath:com.nhsoft.share.mybatis.model/*.xml|
checkConfigLocation |  是否校验configLocation路径是否存在   | mybatis.mapperlocations=classpath:com.nhsoft.share.mybatis.model/*.xml|
configLocation       | mybatis-config.xml文件路径，springbooty一般用不到   |  |
org.apache.ibatis.session.Configuration  |这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。下表描述了设置中各项的意图、默认值等      | 具体见下面表格      |
typeAliasesPackage      | 可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean指定别名，默认为类名首字母小写，例如‘Author’别名为‘author’ |  |
typeAliasesSuperType           |   |  |
org.apache.ibatis.session.ExecutorType         |      |    |
typeHandlersPackage           |      |       |


#### org.apache.ibatis.session.Configuration
出自：<http://www.mybatis.org/mybatis-3/zh/configuration.html#settings>

设置参数        | 描述              | 有效值    | 默认值 |
--------------------|------------------|-----------------------|-----------------------|
cacheEnabled	| 全局地开启或关闭配置文件中的所有映射器已经配置的任何缓存。| true/false	| true
lazyLoadingEnabled | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置fetchType属性来覆盖该项的开关状态。	| true/false | false
ggressiveLazyLoading | 当开启时，任何方法的调用都会加载该对象的所有属性。否则，每个属性会按需加载（参考lazyLoadTriggerMethods) | true/false | false (true in ≤3.4.1)


### 2. 核心类
类        | 摘要             | 
--------------------|------------------|
org.apache.ibatis.session.Configuration类 | MyBatis全局配置信息类
org.apache.ibatis.session.SqlSessionFactory接口 | 操作SqlSession的工厂接口，具体的实现类是DefaultSqlSessionFactory
org.apache.ibatis.session.SqlSession接口 | 执行sql，管理事务的接口，具体的实现类是DefaultSqlSession
org.apache.ibatis.executor.Executor接口 | sql执行器，SqlSession执行sql最终是通过该接口实现的，常用的实现类有SimpleExecutor,ResueExecutor, BatchExecutor,这些实现类都使用了装饰者设计模式

### 3. Executor
