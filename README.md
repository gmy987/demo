##SpringBoot与Mybatis-Plus整合

####1. IDE
> 使用 Intellij IDEA

####2. 创建Spring Boot项目
- New -> Project -> Spring Initializer -> `Next`
![](https://github.com/gmy987/demo/raw/master/img/1.png)
- 填写项目ArtifactId、GroupId等信息 -> `Next`
![](https://github.com/gmy987/demo/raw/master/img/2.png)
- 根据选择项目依赖，这里我们选择以下四个依赖，然后点击`Next` -> `Finish`
	- DevTools
	- Web
	- Mybatis
	- MySql
![](https://github.com/gmy987/demo/raw/master/img/3.png)
- 一个简单的Spring Boot项目就创建完成了
![](https://github.com/gmy987/demo/raw/master/img/4.png)

####3. pom.xml
- 项目创建完成后可以看到pom.xml中自动为我们添加了一些依赖

```xml
	<!--mybatis-->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.1.1</version>
    </dependency>
    <!--Spring MVC-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!--热部署 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!--mysql驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!--test-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
```
- 在pom.xml中添加以下额外依赖

```xml
	<!--HiKariCP数据库连接池-->
	<dependency>
		<groupId>com.zaxxer</groupId>
		<artifactId>HikariCP</artifactId>
	</dependency>
	<!--mybatis-plus依赖-->
	<dependency>
		<groupId>com.baomidou</groupId>
		<artifactId>mybatis-plus</artifactId>
		<version>2.1.4</version>
	</dependency>
	<!--mybatis-plus代码生成需要velocity引擎支持-->
	<dependency>
		<groupId>org.apache.velocity</groupId>
		<artifactId>velocity-engine-core</artifactId>
		<version>2.0</version>
	</dependency>
	<!--mybatis-plus与springboot整合配置依赖-->
	<dependency>
		<groupId>com.baomidou</groupId>
		<artifactId>mybatisplus-spring-boot-starter</artifactId>
		<version>1.0.5</version>
	</dependency>
```
- 创建MpGenerator.java文件, 用于逆向工程根据数据表生成代码。在项目中任意位置创建文件并运行。

> 代码生成有java代码和maven插件两种方式，这里我们选择java代码方。另一种方式可参照mybatis-plus[文档](https://gitee.com/baomidou/mybatisplus-maven-plugin)

```java
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class MpGenerator {

    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("/Users/imac/IdeaProjects/javastudy/springboot-test/src/main/java");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        // .setKotlin(true) 是否生成 kotlin 代码
        gc.setAuthor("daisy");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert(){
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("");
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/springlearn?characterEncoding=utf8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
        strategy.setTablePrefix(new String[] { "tlog_", "tsys_" });// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
         strategy.setInclude(new String[] { "user" }); // 需要生成的表
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.tju");
        pc.setModuleName("scs");
        mpg.setPackageInfo(pc);

//        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//            }
//        };
//
//        // 自定义 xxList.jsp 生成
//        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
//        focList.add(new FileOutConfig("/template/list.jsp.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return "D://my_" + tableInfo.getEntityName() + ".jsp";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);
//
//        // 调整 xml 生成目录演示
//        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return "/develop/code/xml/" + tableInfo.getEntityName() + ".xml";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//        mpg.setCfg(cfg);

//        // 关闭默认 xml 生成，调整生成 至 根目录
//        TemplateConfig tc = new TemplateConfig();
//        tc.setXml(null);
//        mpg.setTemplate(tc);

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        // mpg.setTemplate(tc);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
//        System.err.println(mpg.getCfg().getMap().get("abc"));
    }
}
```

- idea中运行`MpGenerator.java`文件，生成的代码结构如下图.
<img src="https://github.com/gmy987/demo/raw/master/img/6.png" width = "300" height = "400"/>

- 由于idea默认不会将src/java中的xml文件编译,所以在pom.xml的`<build>`标签中添加如下配置, 这样xml文件就会编译到target中。

```xml
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.xml</include>
            </includes>
        </resource>
    </resources>
```

####4. application.properties
> application.properties是spring boot的配置文件，spring boot会自动扫描并加载该文件中的配置, 我们添加如下配置:

```bash
# JDBC Config
spring.datasource.url=jdbc:mysql://localhost:3306/springlearn?characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.type=com.zaxxer.hikari.HikariDataSource

# Mybatis Config
mybatis.mapper-locations=classpath*:xml/*Mapper.xml
mybatis.type-aliases-package=cn.tju.scs.domain
```

####5. 在SpringBoot入口类`DemoApplication.java`中添加mapper扫描
```java
@MapperScan("cn.tju.scs.mapper")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

```
####6. 编写Controller进行测试
```java
@RestController
@RequestMapping("/scs/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping("select")
    Object selectUser() {
        User user = new User();
        return userService.selectList(new EntityWrapper<>(user));
    }
}
```

