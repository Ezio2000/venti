# 复习笔记 - mybatis

bundleSql是MappedStatement里面的

0 XBuilder 生成 MapperProxy MappedStatement Configuration 等
1 new Transaction 用于getConnection
2 new Executor(configuration, transaction)
3 new SqlSession(configuration, executor) 这是给用户的API
4 getMapperProxy() 获取Mapper接口的动态代理类，在 XBuilder 时已经创建
5 MapperProxy.MethodInvoker.execute(args) 如insert
6 Executor.doExecute() 如doUpdate() *CachingExecutor缓存 *默认 SimpleExecutor
7 new StatementHandler 可接入拦截器 *内部包含了 new ParameterHandler 和 new ResultSetHandler
8 transaction.getConnection() 获取JDBC原生API
9 StatementHandler.prepare() -> instantiateStatement()
-> 如果是 PrepareStatementHandler ,则调用JDBC的 connection.prepareStatement 预编译
-> 如果是 SimpleStatementHandler ,则调用JDBC的 connection.createStatement 无预编译
10 StatementHandler.parameterize(Statement)
-> 如果是 PrepareStatementHandler ,则委托 TypeHandler 调用JDBC的 prepareStatement.setXX()
-> 如果是 SimpleStatementHandler ,则无处理
11 StatementHandler.execute() update()/query() 调用JDBC的 statement.execute()
IF UPDATE: KeyGenerator.processAfter() 回填
12 IF QUERY: ResultHandler.handleResultSets() ->
...
-> DefaultResultSetHandler.getPropertyMappingValue() -> while(next) obj = TypeHandler.getResult()
-> DefaultResultHandler.handleResult() 处理完后添加结果集
-> multipleResults.add(resultHandler) 把结果集中的结果返回给用户，放在multipleResults中


## 关键类

| 类                                                                                        | 作用                                                                                      
|:-----------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------
| ApplicationStartup                                                                       | 接收Springboot各种组件的初始化信息                                                                  
| BeanEepressionResolver                                                                   | Bean语法解析器，如解析@Value('spring.app.enabled')                                               
| PropertyEditorRegistrar                                                                  | 转换配置中的复杂对象，如spring.app.date='yyyy-mm-dd'                                                
| ApplicationEvent                                                                         | 事件                                                                                      
| ApplicationListener                                                                      | 监听ApplicationEvent事件，注意ApplicationListener初始化前发布的事件都无法正常触发                              
| SpringApplicationRunListener                                                             | 监听Springboot启动生命周期                                                                      
| EventPublishingRunListener im SpringApplicationRunListener                               | 监听Springboot启动生命周期并发布对应ApplicationEvent                                                 
| BootstrapApplicationListener im ApplicationListener                                      | 监听ApplicationEnvironmentPreparedEvent并加载springcloud容器                                   
| BootstrapRegistryInitializer                                                             | 提前实例化到容器中（时间点非常早）                                                                       
| DefaultBootstrapContext im BootstrapContext                                              | Springboot初始化时使用到的上下文，方便各个启动节点联系（不太重要）                                                  
| ConfigurableApplicationContext im ApplicationContext                                     | 应用上下文，就是容器（非常重要）                                                                        
| ServletWebServerApplicationContext ex ConfigurableApplicationContext                     | Servlet模式生成的应用上下文                                                                       
| AnnotationConfigServletWebServerApplicationContext ex ServletWebServerApplicationContext | 应用上下文真实的类                                                                               
| ConfigurableEnvironment                                                                  | 应用上下文的环境，如系统变量、jvm参数等                                                                   
| ApplicationConversionService                                                             | 格式转换器，如：配置属性绑定（app.version是int，转换为LocalDate），http入参转换（@Param("birth")是int，转换为LocalDate） 
| ServletWebServerApplicationContextFactory im ApplicationContextFactory                   | 初始化上下文context的工厂类                                                                       
| ApplicationStartup                                                                       | Springboot启动分析工具，可结合actuator使用                                                          
| ApplicationContextInitializer                                                            | 在应用上下文启动前，进行一些配置操作，如设置属性源、环境变量                                                          
| BeanDefinitionRegistry                                                                   | 注册和管理BeanDefinition的类（context就是一个registry）                                              
| BeanDefinition                                                                           | Bean的元信息                                                                                
| BeanDefinitionLoader                                                                     | Bean的元信息加载器，内设各种Reader，实例化不同格式的Bean（xml等）                                               
| BeanFactory                                                                              | 实例化Bean的工厂类（非常重要）                                                                       
| ConfigurableListableBeanFactory im BeanFactory                                           | 实例化Bean的工厂类（非常重要）                                                                       
| DefaultListableBeanFactory im ConfiguratbleListableBeanFactory                           | 实例化Bean的真实工厂类                                                                           
| BeanPostProcessor                                                                        | 实例化每个Bean前后会调用                                                                          
| BeanFactoryPostProcessor                                                                 | refreshContext时会调用一次                                                                    
| BeanDefinitionRegistryPostProcessor ex BeanFactoryPostProcessor                          | refreshContext时会调用一次，主要是加载BeanDefinition                                                
| ConfigurationClassPostProcessor ex BeanDefinitionRegistryPostProcessor                   | 加载@Configuration的BeanDefinition                                                         
| Aware                                                                                    | 一种标记，让Bean可以拿到Aware对应的资源                                                                

## 代码解析

### 初始化 Configuration

```java
// [ XMLConfigBuilder ]
public Configuration parse() {
    if (parsed) {
        throw new BuilderException("Each XMLConfigBuilder can only be used once.");
    }
    parsed = true;
    parseConfiguration(parser.evalNode("/configuration"));
    return configuration;
}
```

```java
// [ XMLConfigBuilder ]
private void parseConfiguration(XNode root) {
    try {
        propertiesElement(root.evalNode("properties"));
        Properties settings = settingsAsProperties(root.evalNode("settings"));
        loadCustomVfsImpl(settings);
        loadCustomLogImpl(settings);
        typeAliasesElement(root.evalNode("typeAliases"));
        pluginsElement(root.evalNode("plugins"));
        objectFactoryElement(root.evalNode("objectFactory"));
        objectWrapperFactoryElement(root.evalNode("objectWrapperFactory"));
        reflectorFactoryElement(root.evalNode("reflectorFactory"));
        settingsElement(settings);
        // read it after objectFactory and objectWrapperFactory issue #631
        environmentsElement(root.evalNode("environments"));
        databaseIdProviderElement(root.evalNode("databaseIdProvider"));
        typeHandlersElement(root.evalNode("typeHandlers"));
        mappersElement(root.evalNode("mappers"));
    } catch (Exception e) {
        throw new BuilderException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
    }
}
```

### 初始化Environment

属性源，如：jvm参数、系统变量、活动配置源（application-dev）等...

```java
// [SpringApplication]
public ConfigurableApplicationContext run(String... args) {
    listeners.starting(bootstrapContext, this.mainApplicationClass);
    try {
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        // 初始化environment
        ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);
        Banner printedBanner = printBanner(environment);
    } finally {
        doSomething();
    }
}
```

```java
// [SpringApplication]
protected void configureEnvironment(ConfigurableEnvironment environment, String[] args) {
    // 配置格式转换器
    if (this.addConversionService) {
        environment.setConversionService(new ApplicationConversionService());
    }
    // 配置属性源
    configurePropertySources(environment, args);
    // 配置活动属性源
    configureProfiles(environment, args);
}
```

### 初始化ApplicationContext

一般来说，ApplicationContext的实现类为AnnotationConfigServletWebServerApplicationContext

```java
// [SpringApplication]
public ConfigurableApplicationContext run(String... args) {
    listeners.starting(bootstrapContext, this.mainApplicationClass);
    try {
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);
        Banner printedBanner = printBanner(environment);
        // 初始化ApplicationContext容器
        context = createApplicationContext();
    } finally {
        doSomething();
    }
}
```

```java
// [AnnotationConfigServletWebServerApplicationContext]
public AnnotationConfigServletWebServerApplicationContext() {
    // 初始化BeanDefinition的解析器和扫包器，这里面会注册Reader和Scanner的BeanDefinition，方便后续加载
    this.reader = new AnnotatedBeanDefinitionReader(this);
    this.scanner = new ClassPathBeanDefinitionScanner(this);
}
```

```java
// [GenericApplicationContext]
public GenericApplicationContext() {
    // 初始化BeanFactory
    this.beanFactory = new DefaultListableBeanFactory();
}
```

### 根据配置准备ApplicationContext

设置bean名生成器、资源加载器、格式转换器、对Bean循环依赖的处理、覆盖（同名）、懒加载等...

```java
// [SpringApplication]
public ConfigurableApplicationContext run(String... args) {
    listeners.starting(bootstrapContext, this.mainApplicationClass);
    try {
        Banner printedBanner = printBanner(environment);
        context = createApplicationContext();
        // 根据配置准备ApplicationContext
        prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
    } finally {
        doSomething();
    }
}
```

### 更新ApplicationContext

```java
// [SpringApplication]
public ConfigurableApplicationContext run(String... args) {
    listeners.starting(bootstrapContext, this.mainApplicationClass);
    try {
        Banner printedBanner = printBanner(environment);
        context = createApplicationContext();
        // 更新ApplicationContext容器
        refreshContext(context);
        afterRefresh(context, applicationArguments);
    } finally {
        doSomething();
    }
}
```

准备BeanFactory，主要是配置一些通用的BeanDefinition

```java
// [AbstractApplicationContext]
protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    // 设置解析器
    beanFactory.setBeanClassLoader(getClassLoader());
    beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
    beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));
    // 添加处理Aware的bpp，可以让Bean拿到标记对应资源，但是Aware无法在其它Bean通过@Autowired注入
    beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
    beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
    beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
    beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
    beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
    beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);
    beanFactory.ignoreDependencyInterface(ApplicationStartupAware.class);
    // 在每个Bean中自动注入
    beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
    beanFactory.registerResolvableDependency(ResourceLoader.class, this);
    beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
    beanFactory.registerResolvableDependency(ApplicationContext.class, this);
    // 添加处理ApplicationListener的bpp，将它们注册到发布-监听模式
    beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));
}
```

实例化所有BeanFactoryPostProcessor并执行

```java
// [AbstractApplicationContext]
public void refresh() throws BeansException, IllegalStateException {
    this.startupShutdownLock.lock();
    try {
        invokeBeanFactoryPostProcessors(beanFactory);
    } finally {
        doSomething();
    }
}
```

PostProcessorRegistrationDelegate会实例化所有BeanFactoryPostProcessor并执行

```java
// [PostProcessorRegistrationDelegate]
public static void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {
    // 实例化所有BeanDefinitionRegistryPostProcessor
    String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
    for (String ppName : postProcessorNames) {
        if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
            currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
            processedBeans.add(ppName);
        }
    }
    sortPostProcessors(currentRegistryProcessors, beanFactory);
    registryProcessors.addAll(currentRegistryProcessors);
    // 执行所有BeanDefinitionRegistryPostProcessor，包括ConfigurationClassPostProcessor
    invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry, beanFactory.getApplicationStartup());
    currentRegistryProcessors.clear();
}
```

ConfigurationClassPostProcessor[im BeanDefinitionRegistryPostProcessor ex BeanFactoryPostProcessor]中实例化所有@Configuration
- @Configuration的加载细节不讨论

```java
// [ConfigurationClassPostProcessor]
public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
    // 初始化解析器
    ConfigurationClassParser parser = new ConfigurationClassParser(
            this.metadataReaderFactory, this.problemReporter, this.environment,
            this.resourceLoader, this.componentScanBeanNameGenerator, registry);
    do {
        StartupStep processConfig = this.applicationStartup.start("spring.context.config-classes.parse");
        // 从@SpringBootApplication开始迭代解析BeanDefinition并加载到容器
        parser.parse(candidates);
        parser.validate();
    }
    while (!candidates.isEmpty());
}
```

实例化并加载所有BeanPostProcessor

```java
// [AbstractApplicationContext]
public void refresh() throws BeansException, IllegalStateException {
    this.startupShutdownLock.lock();
    try {
        registerBeanPostProcessors(beanFactory);
    } finally {
        doSomething();
    }
}
```

实例化WebServer，启动Tomcat

```java
// [AbstractApplicationContext]
public void refresh() throws BeansException, IllegalStateException {
    this.startupShutdownLock.lock();
    try {
        onRefresh();
    } finally {
        doSomething();
    }
}
```

```java
// [ServletWebServerApplicationContext]
protected void onRefresh() {
    super.onRefresh();
    try {
        // 此处会初始化Tomcat
        createWebServer();
    }
    catch (Throwable ex) {
        throw new ApplicationContextException("Unable to start web server", ex);
    }
}
```

根据BeanDefinition实例化Bean

```java
// [AbstractApplicationContext]
public void refresh() throws BeansException, IllegalStateException {
    this.startupShutdownLock.lock();
    try {
        finishBeanFactoryInitialization(beanFactory);
    } finally {
        doSomething();
    }
}
```