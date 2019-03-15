## MyBatis解析

### 1. MybatisProperties

参数名         | 用处              | memo     |
--------------------|------------------|-----------------------|
mapperLocations |  指定mapper.xml路径，spring boot项目，默认在resource   | mybatis.mapperlocations=classpath:com.nhsoft.share.mybatis.model/*.xml|
checkConfigLocation |  是否校验configLocation路径是否存在   | mybatis.mapperlocations=classpath:com.nhsoft.share.mybatis.model/*.xml|
configLocation       | mybatis-config.xml文件路径，springbooty一般用不到   |  |
org.apache.ibatis.session.Configuration  |这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。下表描述了设置中各项的意图、默认值等      | 具体见下面表格      |
typeAliasesPackage      | 可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean指定别名，默认为类名首字母小写，例如‘Author’别名为‘author’ |  |
typeAliasesSuperType           | simple/batch/resue  |  |mybatis.executor-type=simple
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

~~~
public interface Executor {

  ResultHandler NO_RESULT_HANDLER = null;

  int update(MappedStatement ms, Object parameter) throws SQLException;

  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

  <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

  List<BatchResult> flushStatements() throws SQLException;

  void commit(boolean required) throws SQLException;

  void rollback(boolean required) throws SQLException;

  CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

  boolean isCached(MappedStatement ms, CacheKey key);

  void clearLocalCache();

  void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

  Transaction getTransaction();

  void close(boolean forceRollback);

  boolean isClosed();

  void setExecutorWrapper(Executor executor);

}

~~~

### newExecutor()
~~~
public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
    executorType = executorType == null ? defaultExecutorType : executorType;
    executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
    Executor executor;
    if (ExecutorType.BATCH == executorType) {
      executor = new BatchExecutor(this, transaction);
    } else if (ExecutorType.REUSE == executorType) {
      executor = new ReuseExecutor(this, transaction);
    } else {
      executor = new SimpleExecutor(this, transaction);
    }
    if (cacheEnabled) {
      executor = new CachingExecutor(executor);
    }
    executor = (Executor) interceptorChain.pluginAll(executor);
    return executor;
  }
~~~

### ResueExecutor
顾名思义，就是可复用的执行器，重用的是Statement对象，它会在内部利用一个Map把创建的Statement都缓存起来，每次在执行一条SQL语句时，它都会去判断之前是否存在基于该SQL缓存的Statement对象，存在而且之前缓存的Statement对象对应的Connection还没有关闭的时候就继续用之前的Statement对象，否则将创建一个新的Statement对象，并将其缓存起来。因为每一个新SqlSession都有一个新的Executor对象，所以我们缓存在ReuseExecutor上的Statement的作用域是同一个SqlSession

##### 核心源码见
~~~
private Statement prepareStatement(StatementHandler handler, Log statementLog) throws SQLException {
    Statement stmt;
    BoundSql boundSql = handler.getBoundSql();
    String sql = boundSql.getSql();
    if (hasStatementFor(sql)) { 
      stmt = getStatement(sql);
      applyTransactionTimeout(stmt);
    } else {
      Connection connection = getConnection(statementLog);
      stmt = handler.prepare(connection, transaction.getTimeout());
      putStatement(sql, stmt);
    }
    handler.parameterize(stmt);
    return stmt;
  }
~~~

~~~
private boolean hasStatementFor(String sql) {
    try {
      return statementMap.keySet().contains(sql) && !statementMap.get(sql).getConnection().isClosed();
    } catch (SQLException e) {
      return false;
    }
  }
~~~


### CachingExecutor
二级缓存执行器，需要开启namespace cache=true才有效，（前提：configration cacheEnable=true）使用到***装饰设计模式***

### SimpleExecutor
其中没有过多的封装，只提供了最基本的sql执行操作

### BatchExecutor

BatchExecutor的设计主要是用于做批量更新操作的。其底层会调用Statement的executeBatch()方法实现批量操作，比较简单