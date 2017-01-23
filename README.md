# 项目框架模板

### 将自己常用的框架、工具类提取出来的一个小demo，可以当做项目模板，在项目启动时，快速构建
     
#### 各个模块负责各自自己的依赖以及一些公共约定和工具类
          
###各公共模块功能
+ common 包含一些公共约定，如Exception、返回值
+ common-logger 包含日志工具类
+ common-mybatis 定义了mybatis的公共接口BaseMapper 以及基于BaseMapper的BaseService
+ common-spring 包含一些spring工具类
+ common-springmvc 定义了公共Controller，进行了全局的异常拦截;RateLimitInterceptor限流拦截器
+ common-metrics 集成了dropwizard metrics,可以使用注解对应用进行监控，如果需要使用servlet
查看监控数据的话,需要在web.xml中配置metrics servlet和listener。例子可见web-springmvc-mybatis web.xml

###项目模板
+ web-springmvc-mybatis 一个springmvc+mybatis的模板
