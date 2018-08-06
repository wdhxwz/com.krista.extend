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

2. 提供ExcelWriter组件，封装简单的表格导出；可以基于注解导出Excel

3. 提供ExcelReader组件，基于注解将Excel的Sheet表格读取成指定类型的List

4. 包含的注解：`ExcelSheet:标识某个sheet表格`、`SheetColumn:标识某个sheet列`

5. 通用的Bean：`ExcelBean:包含数据列表、sheet名称、sheet列与属性映射`

### 读写例子

#### 导出Excel

    public static void main(String[] args) throws IOException {
        ExcelWriter poiTool = new ExcelWriter()
                .setNumberFormat("#")
                .setTimeFormat("yyyy-MM-dd")
                .setFontSize((short) 12)
                .config();

        LinkedHashMap<String, String> columnMap = new LinkedHashMap<>();
        columnMap.put("id", "编号");
        columnMap.put("name", "姓名");
        columnMap.put("age", "年龄");
        columnMap.put("birthDay", "生日");

        List<Object> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setAge(i);
            user.setBirthDay(new Date());
            user.setCreateTime(new Date());
            user.setId(i + 0L);
            user.setName("user" + i);
            users.add(user);
        }
        logger.info("开始导出数据");

        List<ExcelBean> excelBeans = new ArrayList<>();
        ExcelBean excelBean = new ExcelBean();
        excelBean.setColumnNameMap(columnMap);
        excelBean.setSheetName("测试Sheet");
        excelBean.setContentList(users);
        excelBeans.add(excelBean);

		// System.out.println(poiTool.bean(columnMap, users, "C:\\Users\\Administrator\\Desktop\\test.xlsx"));
		// System.out.println(poiTool.bean(excelBeans, "C:\\Users\\Administrator\\Desktop\\test.xlsx"));
        System.out.println(poiTool.exportByAnnotation( "C:\\Users\\wdhcxx\\Desktop\\test.xlsx",users));
        logger.info("结束导出数据");

        System.out.println("Hello World!");
    }


#### 读取Excel

	public static void main(String[] args) throws Exception {
        Map<String, String> columnMap = new HashMap<>();
        columnMap.put( "用户编号","id");
        columnMap.put( "用户姓名","name");
        columnMap.put( "用户年龄","age");
        columnMap.put( "用户生日","birthDay");
        String path = "C:\\Users\\wdhcxx\\Desktop\\test.xlsx";
        ExcelReader excelReader = new ExcelReader(path).setColumnHeaderRow(0);
        // ExcelBean<User>  excelBean = excelReader.read(0,columnMap,User.class);
        ExcelBean<User> excelBean = excelReader.readByAnnotation(User.class);
        System.out.println(excelBean.getContentList() == null ? 0: excelBean.getContentList().size());
    }

#### 用到的User类

	@ExcelSheet(name = "用户")
	public class User {
    	@SheetColumn(name = "用户编号")
    	private Long id;

    	@SheetColumn(name = "用户姓名")
    	private String name;

    	@SheetColumn(name = "用户年龄")
    	private Integer age;

    	@SheetColumn(name = "用户生日")
    	private Date birthDay;

    	@SheetColumn(name = "创建时间",timeFormat = "yyyy-MM-dd HH:mm:ss")
    	private Date createTime;
	}



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
8. 提供文件下载View：DownloadView、json视图：JsonView、验证码视图：CaptchaView
9. 提供文件上传接口：/fileUpload/handleFileUpload.do,并提供文件保存服务接口：StorageService



## extend-utils