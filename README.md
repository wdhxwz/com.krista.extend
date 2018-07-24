# com.krista.extend

## extend-log4j

### 项目引用

        <dependency>
            <groupId>com.krista</groupId>
            <artifactId>extend-log4j</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

### 提供功能

1. 封装对Log相关jar包的引用。

2. 提供一个LogFilter，基于Log4j的MDC记录每次请求的编号，需要在web.xml添加下面内容：

		<absolute-ordering>
        	<name>log4j_webfragment</name>
    	</absolute-ordering>


> 允许从请求参数中获取对应的值进行记录，需要配置log.key的值为对应参数名，例如像记录requestId字段的值，则配置log.key=requestId;如果不配置，则每次随机生成。

> 默认日志的文件的保存路径为：web根目录，修改默认路径可通过配置log.root.path。

> 配置的取值方式为：classpath:app.properties > web.xml的context-param




## extend-poi

### 项目引用

		<dependency>
            <groupId>com.krista</groupId>
            <artifactId>extend-poi</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

### 提供功能

1. 基于Apache-poi组件进行封装

2. 基于注解导出Excel

3. 封装简单的表格导出


## extend-springmvc

### 项目引用

        <dependency>
            <groupId>com.krista</groupId>
            <artifactId>extend-springmvc</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>


### 提供功能

1. 封装Spring MVC的默认配置，添加引用后，只需要的对应的web.xml加入下面内容即可引入Spring MVC：

		<absolute-ordering>
        	<name>default_webfragment</name>
    	</absolute-ordering>


2. 基本原理是Servlet 3.0之后的webfragment模块化
3. 提供编码过滤器，默认编码是utf-8
4. 提供跨域过滤器，默认的域为*
5. 修改默认MVC路径映射，默认取方法名加上.do
6. 提供@RestRequestMapping，封装路径映射和json数据返回
7. 提供ControllerLogAop，记录请求的入参，出参和耗时日志



## extend-utils