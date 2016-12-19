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
	<!--mybatis-plus-->
	<dependency>
	    <groupId>com.baomidou</groupId>
	    <artifactId>mybatis-plus</artifactId>
	    <version>2.0</version>
	</dependency>
```
- 配置mybatis-plus Maven代码生成插件, 用于逆向工程根据数据表生成代码。在pom.xml文件中添加如下代码

> 代码生成有java代码和maven插件两种方式，这里我们选择maven插件的方式。另一种方式可参照mybatis-plus[文档](http://mp.baomidou.com/docs/code-generator.html)

```xml
<plugin>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatisplus-maven-plugin</artifactId>
    <version>1.0</version>
    <configuration>
        <!-- 输出目录(设置生成代码的位置，默认java.io.tmpdir) -->
        <outputDir>/Users/imac/IdeaProjects/javastudy/demo/src/main/java</outputDir>

        <!-- 是否覆盖同名文件(默认false) -->
        <fileOverride>true</fileOverride>
        <!-- mapper.cn.tju.scs.mapper.xml 中添加二级缓存配置(默认true) -->
        <enableCache>false</enableCache>
        <!-- 开发者名称 -->
        <author>daisygao</author>
        <!-- 数据源配置，( **必配** ) -->
        <dataSource>
            <driverName>com.mysql.jdbc.Driver</driverName>
            <url>jdbc:mysql://localhost:3306/springlearn?characterEncoding=utf8</url>
            <username>root</username>
            <password>root</password>
        </dataSource>
        <strategy>
            <!-- 字段生成策略，四种类型，从名称就能看出来含义 nochange(默认), underline_to_camel,(下划线转驼峰)
                remove_prefix,(去除第一个下划线的前部分，后面保持不变) remove_prefix_and_camel(去除第一个下划线的前部分，后面转驼峰) -->
            <naming>underline_to_camel</naming>
            <!--Entity中的ID生成策略（默认 id_worker） -->
            <idGenType>auto</idGenType>
            <!--指定Serice接口和实现类的超类 -->
            <superServiceClass>com.baomidou.mybatisplus.service.IService</superServiceClass>
            <superServiceImplClass>com.baomidou.mybatisplus.service.impl.ServiceImpl</superServiceImplClass>
            <!-- 要包含的表 与exclude 二选一配置 -->
            <include>
                <property>user</property>
            </include>
            <!-- 要排除的表 -->
            <!--<exclude> -->
            <!--<property>schema_version</property> -->
            <!--</exclude> -->
        </strategy>
        <packageInfo>
            <!-- 父级包名称，如果不写，下面的service等就需要写全包名(默认com.baomidou) -->
            <parent>cn.tju.scs</parent>
            <!--service包名(默认service) -->
            <service>service</service>
            <!--serviceImpl包名(默认service.impl) -->
            <serviceImpl>service.impl</serviceImpl>
            <!--entity包名(默认entity) -->
            <entity>domain</entity>
            <!--mapper包名(默认mapper) -->
            <mapper>mapper</mapper>
            <!--controller包名(默认web)-->
            <controller>controller</controller>
            <!--xml包名(默认mapper.xml) -->
            <xml>mapper.xml</xml>
        </packageInfo>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.34</version>
        </dependency>
    </dependencies>
</plugin>
```

- idea中右侧`Maven Projects`->`Plugins`->`mp`->`mp:code`,双击运行maven插件生成代码，生成的代码结构如下图.
<img src="https://github.com/gmy987/demo/raw/master/img/5.png" width = "350" height = "400"/> &nbsp;&nbsp;
<img src="https://github.com/gmy987/demo/raw/master/img/6.png" width = "300" height = "400"/>

> 也可以通过命令`mvn mp:code`来执行maven插件

![](https://github.com/gmy987/demo/raw/master/img/7.png)

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
####5. 添加MybatisConfig.java配置类

```java
@Configuration
public class MybatisConfig {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MybatisProperties properties;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired(required = false)
    private Interceptor[] interceptors;

    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

    /**
     * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
     * 配置文件和mybatis-boot的配置文件同步
     * @return
     */
    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(dataSource);
        mybatisPlus.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }
        mybatisPlus.setConfiguration(properties.getConfiguration());
        if (!ObjectUtils.isEmpty(this.interceptors)) {
            mybatisPlus.setPlugins(this.interceptors);
        }
        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisPlus.setConfiguration(mc);
        if (this.databaseIdProvider != null) {
            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
        }
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
        }
        return mybatisPlus;
    }

}
```
####6. 在SpringBoot入口类`DemoApplication.java`中添加mapper扫描
```java
@MapperScan("cn.tju.scs.mapper")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

```
####7. 编写Controller进行测试
```java
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

	@RequestMapping("insert")
	public Object insert(User user) {
        userService.insert(user);
        return user;
    }
}
```

