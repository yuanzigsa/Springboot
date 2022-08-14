![点击查看源网页](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg-blog.csdnimg.cn%2Fimg_convert%2F174788b2ec1d828d85a0a7ac65bea2cd.png&refer=http%3A%2F%2Fimg-blog.csdnimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1644064714&t=46bead84c9b2fb6e0b65fd4afdaf805e)

# SpringBoot一站式开发

官网：https://spring.io/projects/spring-boot

> Spring Boot可以轻松创建独立的、基于Spring的生产级应用程序，它可以让你“运行即可”。大多数Spring Boot应用程序只需要少量的Spring配置。

SpringBoot功能：

- 创建独立的Spring应用程序
- 直接嵌入Tomcat、Jetty或Undertow（无需部署WAR包，打包成Jar本身就是一个可以运行的应用程序）
- 提供一站式的“starter”依赖项，以简化Maven配置（需要整合什么框架，直接导对应框架的starter依赖）
- 尽可能自动配置Spring和第三方库（除非特殊情况，否则几乎不需要你进行什么配置）
- 提供生产就绪功能，如指标、运行状况检查和外部化配置
- 没有代码生成，也没有XML配置的要求（XML是什么，好吃吗）

SpringBoot是现在最主流的开发框架，它提供了一站式的开发体验，大幅度提高了我们的开发效率。

## 走进SpringBoot

在SSM阶段，当我们需要搭建一个基于Spring全家桶的Web应用程序时，我们不得不做大量的依赖导入和框架整合相关的Bean定义，光是整合框架就花费了我们大量的时间，但是实际上我们发现，整合框架其实基本都是一些固定流程，我们每创建一个新的Web应用程序，基本都会使用同样的方式去整合框架，我们完全可以将一些重复的配置作为约定，只要框架遵守这个约定，为我们提供默认的配置就好，这样就不用我们再去配置了，约定优于配置！

而SpringBoot正是将这些过程大幅度进行了简化，它可以自动进行配置，我们只需要导入对应的启动器（starter）依赖即可。

完成本阶段的学习，基本能够胜任部分网站系统的后端开发工作，也建议同学们学习完SpringBoot之后寻找合适的队友去参加计算机项目相关的高校竞赛。

我们可以通过IDEA来演示如何快速创建一个SpringBoot项目，并且无需任何配置，就可以实现Bean注册。

## SpringBoot项目文件结构

我们在创建SpringBoot项目之后，首先会自动生成一个主类，而主类中的`main`方法中调用了`SpringApplication`类的静态方法来启动整个SpringBoot项目，并且我们可以看到主类的上方有一个`@SpringBootApplication`注解：

```java
@SpringBootApplication
public class SpringBootTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestApplication.class, args);
    }

}
```

同时还自带了一个测试类，测试类的上方仅添加了一个`@SpringBootTest`注解：

```java
@SpringBootTest
class SpringBootTestApplicationTests {

    @Test
    void contextLoads() {
        
    }

}
```

我们接着来看Maven中写了哪些内容：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
  	<!--  父工程  -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>springboot-study</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>SpringBootTest</name>
    <description>SpringBootTest</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <!--  spring-boot-starter SpringBoot核心启动器  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <!--  spring-boot-starter-test SpringBoot测试模块启动器  -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!--  SpringBoot Maven插件，打包Jar都不用你操心了   -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

除了以上这些文件以外，我们的项目目录下还有：

* .gitignore  -  Git忽略名单，下一章我们会专门讲解Git版本控制。
* application.properties   -    SpringBoot的配置文件，所有依赖的配置都在这里编写，但是一般情况下只需要配置必要项即可。

***

## 整合Web相关框架

我们来看一下，既然我们前面提到SpringBoot会内嵌一个Tomcat服务器，也就是说我们的Jar打包后，相当于就是一个可以直接运行的应用程序，我们来看一下如何创建一个SpringBootWeb项目。

这里我们演示使用IDEA来创建一个基于SpringBoot的Web应用程序。

### 它是真的快

创建完成后，直接开启项目，我们就可以直接访问：http://localhost:8080/，我们可以看到，但是由于我们没有编写任何的请求映射，所以没有数据。我们可以来看看日志：

```
2022-01-06 22:17:46.308  INFO 853 --- [           main] c.example.SpringBootWebTestApplication   : Starting SpringBootWebTestApplication using Java 1.8.0_312 on NagodeMacBook-Pro.local with PID 853 (/Users/nagocoler/Downloads/SpringBootWebTest/target/classes started by nagocoler in /Users/nagocoler/Downloads/SpringBootWebTest)
2022-01-06 22:17:46.309  INFO 853 --- [           main] c.example.SpringBootWebTestApplication   : No active profile set, falling back to default profiles: default
2022-01-06 22:17:46.629  INFO 853 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2022-01-06 22:17:46.632  INFO 853 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-01-06 22:17:46.632  INFO 853 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.56]
2022-01-06 22:17:46.654  INFO 853 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-01-06 22:17:46.654  INFO 853 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 325 ms
2022-01-06 22:17:46.780  INFO 853 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-01-06 22:17:46.785  INFO 853 --- [           main] c.example.SpringBootWebTestApplication   : Started SpringBootWebTestApplication in 0.62 seconds (JVM running for 0.999)
2022-01-06 22:18:02.979  INFO 853 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2022-01-06 22:18:02.979  INFO 853 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2022-01-06 22:18:02.980  INFO 853 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
```

我们可以看到，日志中除了最基本的SpringBoot启动日志以外，还新增了内嵌Web服务器（Tomcat）的启动日志，并且显示了当前Web服务器所开放的端口，并且自动帮助我们初始化了DispatcherServlet，但是我们只是创建了项目，导入了web相关的starter依赖，没有进行任何的配置，实际上它使用的是starter提供的默认配置进行初始化的。

由于SpringBoot是自动扫描的，因此我们直接创建一个Controller即可被加载：

```java
@Controller
public class MainController {

  	//直接访问http://localhost:8080/index即可，不用加web应用程序名称了
    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return "你好，欢迎访问主页！";
    }
}
```

我们几乎没有做任何配置，但是可以直接开始配置Controller，SpringBoot创建一个Web项目的速度就是这么快！

它还可以自动识别类型，如果我们返回的是一个对象类型的数据，那么它会自动转换为JSON数据格式，无需配置：

```java
@Data
public class Student {
    int sid;
    String name;
    String sex;
}
```

```java
@RequestMapping("/student")
@ResponseBody
public Student student(){
    Student student = new Student();
    student.setName("小明");
    student.setSex("男");
    student.setSid(10);
    return student;
}
```

最后浏览器能够直接得到`application/json`的响应数据，就是这么方便。

### 修改Web相关配置

如果我们需要修改Web服务器的端口或是一些其他的内容，我们可以直接在`application.properties`中进行修改，它是整个SpringBoot的配置文件：

```properties
# 修改端口为80
server.port=80
```

我们还可以编写自定义的配置项，并在我们的项目中通过`@Value`直接注入：

```properties
test.data=100
```

```java
@Controller
public class MainController {

    @Value("${test.data}")
    int data;
```

通过这种方式，我们就可以更好地将一些需要频繁修改的配置项写在配置文件中，并通过注解方式去获取值。

配置文件除了使用`properties`格式以外，还有一种叫做`yaml`格式，它的语法如下：

```yaml
一级目录:
	二级目录:
	  三级目录1: 值
	  三级目录2: 值
	  三级目录List: 
	  - 元素1
	  - 元素2
	  - 元素3
```

我们可以看到，每一级目录都是通过缩进（不能使用Tab，只能使用空格）区分，并且键和值之间需要添加冒号+空格来表示。

SpringBoot也支持这种格式的配置文件，我们可以将`application.properties`修改为`application.yml`或是`application.yaml`来使用YAML语法编写配置：

```yaml
server:
  port: 80
```

### 整合SpringSecurity依赖

我们接着来整合一下SpringSecurity依赖，继续感受SpringBoot带来的光速开发体验，只需要导入SpringSecurity的Starter依赖即可：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

导入依赖后，我们直接启动SpringBoot应用程序，可以发现SpringSecurity已经生效了。

并且SpringSecurity会自动为我们生成一个默认用户`user`，它的密码会出现在日志中：

```
2022-01-06 23:10:51.329  INFO 2901 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2022-01-06 23:10:51.329  INFO 2901 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.56]
2022-01-06 23:10:51.350  INFO 2901 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2022-01-06 23:10:51.351  INFO 2901 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 341 ms
2022-01-06 23:10:51.469  INFO 2901 --- [           main] .s.s.UserDetailsServiceAutoConfiguration : 

Using generated security password: ff24bee3-e1b7-4309-9609-d32618baf5cb

```

其中`ff24bee3-e1b7-4309-9609-d32618baf5cb`就是随机生成的一个密码，我们可以使用此用户登录。

我们也可以在配置文件中直接配置：

```yaml
spring:
  security:
    user:
      name: test   # 用户名
      password: 123456  # 密码
      roles:   # 角色
      - user
      - admin
```

实际上这样的配置方式就是一个`inMemoryAuthentication`，只是我们可以直接配置而已。

当然，页面的控制和数据库验证我们还是需要提供`WebSecurityConfigurerAdapter`的实现类去完成：

```java
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().hasRole("user")
                .and()
                .formLogin();
    }
}
```

注意这里不需要再添加`@EnableWebSecurity`了，因为starter依赖已经帮我们添加了。

使用了SpringBoot之后，我们发现，需要什么功能，只需要导入对应的starter依赖即可，甚至都不需要你去进行额外的配置，你只需要关注依赖本身的必要设置即可，大大提高了我们的开发效率。

***

## 整合Mybatis框架

我们接着来看如何整合Mybatis框架，同样的，我们只需要导入对应的starter依赖即可：

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.0</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

导入依赖后，直接启动会报错，是因为有必要的配置我们没有去编写，我们需要指定数据源的相关信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
```

再次启动，成功。

我们发现日志中会出现这样一句话：

```
2022-01-07 12:32:09.106  WARN 6917 --- [           main] o.m.s.mapper.ClassPathMapperScanner      : No MyBatis mapper was found in '[com.example]' package. Please check your configuration.
```

这是Mybatis自动扫描输出的语句，导入依赖后，我们不需要再去设置Mybatis的相关Bean了，也不需要添加任何`@MapperSacn`注解，因为starter已经帮助我们做了，它会自动扫描项目中添加了`@Mapper`注解的接口，直接将其注册为Bean，不需要进行任何配置。

```java
@Mapper
public interface MainMapper {
    @Select("select * from users where username = #{username}")
    UserData findUserByName(String username);
}
```

当然，如果你觉得每个接口都去加一个`@Mapper`比较麻烦的话也可以用回之前的方式，直接`@MapperScan`使用包扫描。

添加Mapper之后，使用方法和SSM阶段是一样的，我们可以将其与SpringSecurity结合使用：

```java
@Service
public class UserAuthService implements UserDetailsService {

    @Resource
    MainMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData data = mapper.findUserByName(username);
        if(data == null) throw new UsernameNotFoundException("用户 "+username+" 登录失败，用户名不存在！");
        return User
                .withUsername(data.getUsername())
                .password(data.getPassword())
                .roles(data.getRole())
                .build();
    }
}
```

最后配置一下自定义验证即可，注意这样之前配置文件里面配置的用户就失效了：

```java
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
            .userDetailsService(service)
            .passwordEncoder(new BCryptPasswordEncoder());
}
```

在首次使用时，我们发现日志中输出以以下语句：

```
2022-01-07 12:39:40.559  INFO 6930 --- [nio-8080-exec-3] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2022-01-07 12:39:41.033  INFO 6930 --- [nio-8080-exec-3] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
```

实际上，SpringBoot会自动为Mybatis配置数据源，默认使用的就是`HikariCP`数据源。

***

## 整合Thymeleaf框架

整合Thymeleaf也只需导入对应的starter即可：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

接着我们只需要直接使用即可：

```java
@RequestMapping("/index")
public String index(){
    return "index";
}
```

但是注意，这样只能正常解析HTML页面，但是js、css等静态资源我们需要进行路径指定，不然无法访问，我们在配文件中配置一下静态资源的访问前缀：

```yaml
spring:
	mvc:
  	static-path-pattern: /static/**
```

接着我们像之前一样，把登陆页面实现一下吧。

```html
<html lang="en" xmlns:th=http://www.thymeleaf.org
xmlns:sec=http://www.thymeleaf.org/extras/spring-security>
```

***

## 日志系统

SpringBoot为我们提供了丰富的日志系统，它几乎是开箱即用的。

### 日志门面和日志实现

我们首先要区分一下，什么是日志门面（Facade）什么是日志实现，我们之前学习的JUL实际上就是一种日志实现，我们可以直接使用JUL为我们提供的日志框架来规范化打印日志，而日志门面，如Slf4j，是把不同的日志系统的实现进行了具体的抽象化，只提供了统一的日志使用接口，使用时只需要按照其提供的接口方法进行调用即可，由于它只是一个接口，并不是一个具体的可以直接单独使用的日志框架，所以最终日志的格式、记录级别、输出方式等都要通过接口绑定的具体的日志系统来实现，这些具体的日志系统就有log4j、logback、java.util.logging等，它们才实现了具体的日志系统的功能。

日志门面和日志实现就像JDBC和数据库驱动一样，一个是画大饼的，一个是真的去做饼的。

![img](https://upload-images.jianshu.io/upload_images/2909474-b5127a18b3eda3ec.png?imageMogr2/auto-orient/strip|imageView2/2/w/888)

但是现在有一个问题就是，不同的框架可能使用了不同的日志框架，如果这个时候出现众多日志框架并存的情况，我们现在希望的是所有的框架一律使用日志门面（Slf4j）进行日志打印，这时该怎么去解决？我们不可能将其他框架依赖的日志框架替换掉，直接更换为Slf4j吧，这样显然不现实。

这时，可以采取类似于偷梁换柱的做法，只保留不同日志框架的接口和类定义等关键信息，而将实现全部定向为Slf4j调用。相当于有着和原有日志框架一样的外壳，对于其他框架来说依然可以使用对应的类进行操作，而具体如何执行，真正的内心已经是Slf4j的了。

![img](https://upload-images.jianshu.io/upload_images/2909474-512f5cca92e05e59.png?imageMogr2/auto-orient/strip|imageView2/2/w/928)

所以，SpringBoot为了统一日志框架的使用，做了这些事情：

* 直接将其他依赖以前的日志框架剔除
* 导入对应日志框架的Slf4j中间包
* 导入自己官方指定的日志实现，并作为Slf4j的日志实现层

### 在SpringBoot中打印日志信息

SpringBoot使用的是Slf4j作为日志门面，Logback（[Logback](http://logback.qos.ch/) 是log4j 框架的作者开发的新一代日志框架，它效率更高、能够适应诸多的运行环境，同时天然支持SLF4J）作为日志实现，对应的依赖为：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

此依赖已经被包含了，所以我们如果需要打印日志，可以像这样：

```java
@RequestMapping("/login")
public String login(){
    Logger logger = LoggerFactory.getLogger(MainController.class);
    logger.info("用户访问了一次登陆界面");
    return "login";
}
```

因为我们使用了Lombok，所以直接一个注解也可以搞定哦：

```java
@Slf4j
@Controller
public class MainController {

    @RequestMapping("/login")
    public String login(){
        log.info("用户访问了一次登陆界面");
        return "login";
    }
```

日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，SpringBoot默认只会打印INFO以上级别的信息。

### 配置Logback日志

Logback官网：https://logback.qos.ch

和JUL一样，Logback也能实现定制化，我们可以编写对应的配置文件，SpringBoot推荐将配置文件名称命名为`logback-spring.xml`表示这是SpringBoot下Logback专用的配置，可以使用SpringBoot 的高级Proﬁle功能，它的内容类似于这样：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 配置 -->
</configuration>
```

最外层由`configuration`包裹，一旦编写，那么就会替换默认的配置，所以如果内部什么都不写的话，那么会导致我们的SpringBoot项目没有配置任何日志输出方式，控制台也不会打印日志。

我们接着来看如何配置一个控制台日志打印，我们可以直接导入并使用SpringBoot为我们预设好的日志格式，在`org/springframework/boot/logging/logback/defaults.xml`中已经帮我们把日志的输出格式定义好了，我们只需要设置对应的`appender`即可：

```xml
<included>
   <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
   <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
   <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />

   <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>
   <property name="CONSOLE_LOG_CHARSET" value="${CONSOLE_LOG_CHARSET:-${file.encoding:-UTF-8}}"/>
   <property name="FILE_LOG_PATTERN" value="${FILE_LOG_PATTERN:-%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>
   <property name="FILE_LOG_CHARSET" value="${FILE_LOG_CHARSET:-${file.encoding:-UTF-8}}"/>

   <logger name="org.apache.catalina.startup.DigesterFactory" level="ERROR"/>
   <logger name="org.apache.catalina.util.LifecycleBase" level="ERROR"/>
   <logger name="org.apache.coyote.http11.Http11NioProtocol" level="WARN"/>
   <logger name="org.apache.sshd.common.util.SecurityUtils" level="WARN"/>
   <logger name="org.apache.tomcat.util.net.NioSelectorPool" level="WARN"/>
   <logger name="org.eclipse.jetty.util.component.AbstractLifeCycle" level="ERROR"/>
   <logger name="org.hibernate.validator.internal.util.Version" level="WARN"/>
   <logger name="org.springframework.boot.actuate.endpoint.jmx" level="WARN"/>
</included>
```

导入后，我们利用预设的日志格式创建一个控制台日志打印：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!--  导入其他配置文件，作为预设  -->
    <include resource="org/springframework/boot/logging/logback/defaults.xml" />

    <!--  Appender作为日志打印器配置，这里命名随意  -->
    <!--  ch.qos.logback.core.ConsoleAppender是专用于控制台的Appender  -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>${CONSOLE_LOG_CHARSET}</charset>
        </encoder>
    </appender>

    <!--  指定日志输出级别，以及启用的Appender，这里就使用了我们上面的ConsoleAppender  -->
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
    </root>
</configuration>
```

配置完成后，我们发现控制台已经可以正常打印日志信息了。

接着我们来看看如何开启文件打印，我们只需要配置一个对应的Appender即可：

```xml
<!--  ch.qos.logback.core.rolling.RollingFileAppender用于文件日志记录，它支持滚动  -->
<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <encoder>
        <pattern>${FILE_LOG_PATTERN}</pattern>
        <charset>${FILE_LOG_CHARSET}</charset>
    </encoder>
    <!--  自定义滚动策略，防止日志文件无限变大，也就是日志文件写到什么时候为止，重新创建一个新的日志文件开始写  -->
    <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
        <!--  文件保存位置以及文件命名规则，这里用到了%d{yyyy-MM-dd}表示当前日期，%i表示这一天的第N个日志  -->
        <FileNamePattern>log/%d{yyyy-MM-dd}-spring-%i.log</FileNamePattern>
        <!--  到期自动清理日志文件  -->
        <cleanHistoryOnStart>true</cleanHistoryOnStart>
        <!--  最大日志保留时间  -->
        <maxHistory>7</maxHistory>
        <!--  最大单个日志文件大小  -->
        <maxFileSize>10MB</maxFileSize>
    </rollingPolicy>
</appender>

<!--  指定日志输出级别，以及启用的Appender，这里就使用了我们上面的ConsoleAppender  -->
<root level="INFO">
    <appender-ref ref="CONSOLE"/>
    <appender-ref ref="FILE"/>
</root>
```

配置完成后，我们可以看到日志文件也能自动生成了。

我们也可以魔改官方提供的日志格式，官方文档：https://logback.qos.ch/manual/layouts.html

这里需要提及的是MDC机制，Logback内置的日志字段还是比较少，如果我们需要打印有关业务的更多的内容，包括自定义的一些数据，需要借助logback MDC机制，MDC为“Mapped Diagnostic Context”（映射诊断上下文），即将一些运行时的上下文数据通过logback打印出来；此时我们需要借助org.sl4j.MDC类。

比如我们现在需要记录是哪个用户访问我们网站的日志，只要是此用户访问我们网站，都会在日志中携带该用户的ID，我们希望每条日志中都携带这样一段信息文本，而官方提供的字段无法实现此功能，这时就需要使用MDC机制：

```java
@Slf4j
@Controller
public class MainController {

    @RequestMapping("/login")
    public String login(){
      	//这里就用Session代替ID吧
        MDC.put("reqId", request.getSession().getId());
        log.info("用户访问了一次登陆界面");
        return "login";
    }
```

通过这种方式，我们就可以向日志中传入自定义参数了，我们日志中添加这样一个占位符`%X{键值}`，名字保持一致：

```xml
 %clr([%X{reqId}]){faint} 
```

这样当我们向MDC中添加信息后，只要是当前线程（本质是ThreadLocal实现）下输出的日志，都会自动替换占位符。

### 自定义Banner

我们在之前发现，实际上Banner部分和日志部分是独立的，SpringBoot启动后，会先打印Banner部分，那么这个Banner部分是否可以自定义呢？答案是可以的。

我们可以直接来配置文件所在目录下创建一个名为`banner.txt`的文本文档，内容随便你：

```txt
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//             佛祖保佑          永无BUG         永不修改             //
```

可以使用在线生成网站进行生成自己的个性Banner：https://www.bootschool.net/ascii

我们甚至还可以使用颜色代码来为文本切换颜色：

```
${AnsiColor.BRIGHT_GREEN}  //绿色
```

也可以获取一些常用的变量信息：

```
${AnsiColor.YELLOW} 当前 Spring Boot 版本：${spring-boot.version}
```

玩的开心！

***

## 多环境配置

在日常开发中，我们项目会有多个环境。例如开发环境（develop）也就是我们研发过程中疯狂敲代码修BUG阶段，生产环境（production ）项目开发得差不多了，可以放在服务器上跑了。不同的环境下，可能我们的配置文件也存在不同，但是我们不可能切换环境的时候又去重新写一次配置文件，所以我们可以将多个环境的配置文件提前写好，进行自由切换。

由于SpringBoot只会读取`application.properties`或是`application.yml`文件，那么怎么才能实现自由切换呢？SpringBoot给我们提供了一种方式，我们可以通过配置文件指定：

```yaml
spring:
  profiles:
    active: dev
```

接着我们分别创建两个环境的配置文件，`application-dev.yml`和`application-prod.yml`分别表示开发环境和生产环境的配置文件，比如开发环境我们使用的服务器端口为8080，而生产环境下可能就需要设置为80或是443端口，那么这个时候就需要不同环境下的配置文件进行区分：

```yaml
server:
  port: 8080
```

```yaml
server:
  port: 80
```

这样我们就可以灵活切换生产环境和开发环境下的配置文件了。

SpringBoot自带的Logback日志系统也是支持多环境配置的，比如我们想在开发环境下输出日志到控制台，而生产环境下只需要输出到文件即可，这时就需要进行环境配置：

```xml
<springProfile name="dev">
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>
</springProfile>

<springProfile name="prod">
    <root level="INFO">
        <appender-ref ref="FILE"/>
    </root>
</springProfile>
```

注意`springProfile`是区分大小写的！

那如果我们希望生产环境中不要打包开发环境下的配置文件呢，我们目前虽然可以切换开发环境，但是打包的时候依然是所有配置文件全部打包，这样总感觉还欠缺一点完美，因此，打包的问题就只能找Maven解决了，Maven也可以设置多环境：

```xml
<!--分别设置开发，生产环境-->
<profiles>
    <!-- 开发环境 -->
    <profile>
        <id>dev</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <environment>dev</environment>
        </properties>
    </profile>
    <!-- 生产环境 -->
    <profile>
        <id>prod</id>
        <activation>
            <activeByDefault>false</activeByDefault>
        </activation>
        <properties>
            <environment>prod</environment>
        </properties>
    </profile>
</profiles>
```

接着，我们需要根据环境的不同，排除其他环境的配置文件：

```xml
<resources>
<!--排除配置文件-->
    <resource>
        <directory>src/main/resources</directory>
        <!--先排除所有的配置文件-->
        <excludes>
            <!--使用通配符，当然可以定义多个exclude标签进行排除-->
            <exclude>application*.yml</exclude>
        </excludes>
    </resource>

    <!--根据激活条件引入打包所需的配置和文件-->
    <resource>
        <directory>src/main/resources</directory>
        <!--引入所需环境的配置文件-->
        <filtering>true</filtering>
        <includes>
            <include>application.yml</include>
            <!--根据maven选择环境导入配置文件-->
            <include>application-${environment}.yml</include>
        </includes>
    </resource>
</resources>
```

接着，我们可以直接将Maven中的`environment`属性，传递给SpringBoot的配置文件，在构建时替换为对应的值：

```yaml
spring:
  profiles:
    active: '@environment@'  #注意YAML配置文件需要加单引号，否则会报错
```

这样，根据我们Maven环境的切换，SpringBoot的配置文件也会进行对应的切换。

最后我们打开Maven栏目，就可以自由切换了，直接勾选即可，注意切换环境之后要重新加载一下Maven项目，不然不会生效！

***

## 打包运行

现在我们的SpringBoot项目编写完成了，那么如何打包运行呢？非常简单，只需要点击Maven生命周期中的`package`即可，它会自动将其打包为可直接运行的Jar包，第一次打包可能会花费一些时间下载部分依赖的源码一起打包进Jar文件。

我们发现在打包的过程中还会完整的将项目跑一遍进行测试，如果我们不想测试直接打包，可以手动使用以下命令：

```shell
mvn package  -DskipTests
```

打包后，我们会直接得到一个名为`springboot-study-0.0.1-SNAPSHOT.jar`的文件，这时在CMD窗口中输入命令：

```shell
java -jar springboot-study-0.0.1-SNAPSHOT.jar
```

输入后，可以看到我们的Java项目成功运行起来了，如果手动关闭窗口会导致整个项目终止运行。

***

## 再谈Spring框架

**注意：**开始本部分前，建议先完成SSM阶段的Spring源码讲解部分。

我们在SpringBoot阶段，需要继续扩充Spring框架的相关知识，来巩固和强化对于Spring框架的认识。

### 任务调度

为了执行某些任务，我们可能需要一些非常规的操作，比如我们希望使用多线程来处理我们的结果或是执行一些定时任务，到达指定时间再去执行。

这时我们首先想到的就是创建一个新的线程来处理，或是使用TimerTask来完成定时任务，但是我们有了Spring框架之后，就不用这样了，因为Spring框架为我们提供了更加便捷的方式进行任务调度。

#### 异步任务

需要使用Spring异步任务支持，我们需要在配置类上添加`@EnableAsync`或是在SpringBoot的启动类上添加也可以。

```java
@EnableAsync
@SpringBootApplication
public class SpringBootWebTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebTestApplication.class, args);
    }
}
```

接着我们只需要在需要异步执行的方法上，添加`@Async`注解即可将此方法标记为异步，当此方法被调用时，会异步执行，也就是新开一个线程执行，不是在当前线程执行。

```java
@Service
public class TestService {

    @Async
    public void test(){
        try {
            Thread.sleep(3000);
            System.out.println("我是异步任务！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

```java
@RequestMapping("/login")
public String login(HttpServletRequest request){
    service.test();
    System.out.println("我是同步任务！");
    return "login";
}
```

实际上这也是得益于AOP机制，通过线程池实现，但是也要注意，正是因为它是AOP机制的产物，所以它只能是在Bean中才会生效！

使用 @Async 注释的方法可以返回 'void' 或 "Future" 类型，Future是一种用于接收任务执行结果的一种类型，我们会在Java并发编程中进行讲解，这里暂时不做介绍。

#### 定时任务

看完了异步任务，我们接着来看定时任务，定时任务其实就是指定在哪个时候再去执行，在JavaSE阶段我们使用过TimerTask来执行定时任务。

Spring中的定时任务是全局性质的，当我们的Spring程序启动后，那么定时任务也就跟着启动了，我们可以在配置类上添加`@EnableScheduling`或是在SpringBoot的启动类上添加也可：

```java
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class SpringBootWebTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebTestApplication.class, args);
    }
}
```

接着我们可以创建一个定时任务配置类，在配置类里面编写定时任务：

```java
@Configuration
public class ScheduleConfiguration {

    @Scheduled(fixedRate = 2000)
    public void task(){
        System.out.println("我是定时任务！"+new Date());
    }
}
```

我们注意到` @Scheduled`中有很多参数，我们需要指定'cron', 'fixedDelay(String)', or 'fixedRate(String)'的其中一个，否则无法创建定时任务，他们的区别如下：

* fixedDelay：在上一次定时任务执行完之后，间隔多久继续执行。
* fixedRate：无论上一次定时任务有没有执行完成，两次任务之间的时间间隔。
* cron：使用cron表达式来指定任务计划。

这里重点讲解一下cron表达式：https://blog.csdn.net/sunnyzyq/article/details/98597252

### 监听器

监听器对我们来说也是一个比较陌生的概念，那么何谓监听呢？

监听实际上就是等待某个事件的触发，当事件触发时，对应事件的监听器就会被通知。

```java
@Component
public class TestListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(event.getApplicationContext());
    }
}
```

通过监听事件，我们就可以在对应的时机进行一些额外的处理，我们可以通过断点调试来查看一个事件是如何发生，以及如何通知监听器的。

通过阅读源码，我们得知，一个事件实际上就是通过`publishEvent`方法来进行发布的，我们也可以自定义我们自己项目中的事件，并注册对应的监听器进行处理。

```java
public class TestEvent extends ApplicationEvent {   //需要继承ApplicationEvent
    public TestEvent(Object source) {
        super(source);
    }
}
```

```java
@Component
public class TestListener implements ApplicationListener<TestEvent> {

    @Override
    public void onApplicationEvent(TestEvent event) {
        System.out.println("自定义事件发生了："+event.getSource());
    }
}
```

```java
@Resource
ApplicationContext context;

@RequestMapping("/login")
public String login(HttpServletRequest request){
    context.publishEvent(new TestEvent("有人访问了登录界面！"));
    return "login";
}
```

这样，我们就实现了自定义事件发布和监听。

### Aware系列接口

我们在之前讲解Spring源码时，经常会发现某些类的定义上，除了我们当时讲解的继承关系以外，还实现了一些接口，他们的名称基本都是`xxxxAware`，比如我们在讲解SpringSecurity的源码中，AbstractAuthenticationProcessingFilter类就是这样：

```java
public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean implements ApplicationEventPublisherAware, MessageSourceAware {
    protected ApplicationEventPublisher eventPublisher;
    protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private AuthenticationManager authenticationManager;
    ...
```

我们发现它除了继承自GenericFilterBean之外，还实现了ApplicationEventPublisherAware和MessageSourceAware接口，那么这些Aware接口到底是干嘛的呢？

Aware的中文意思为**感知**。简单来说，他就是一个标识，实现此接口的类会获得某些感知能力，Spring容器会在Bean被加载时，根据类实现的感知接口，会调用类中实现的对应感知方法。

比如AbstractAuthenticationProcessingFilter就实现了ApplicationEventPublisherAware接口，此接口的感知功能为事件发布器，在Bean加载时，会调用实现类中的`setApplicationEventPublisher`方法，而AbstractAuthenticationProcessingFilter类则利用此方法，在Bean加载阶段获得了容器的事件发布器，以便之后发布事件使用。

```java
public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;   //直接存到成员变量
}
```

```java
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    if (this.logger.isDebugEnabled()) {
        this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
    }

    this.rememberMeServices.loginSuccess(request, response, authResult);
  	//在这里使用
    if (this.eventPublisher != null) {
        this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
    }

    this.successHandler.onAuthenticationSuccess(request, response, authResult);
}
```

同样的，除了ApplicationEventPublisherAware接口外，我们再来演示一个接口，比如：

```java
@Service
public class TestService implements BeanNameAware {
    @Override
    public void setBeanName(String s) {
        System.out.println(s);
    }
}
```

BeanNameAware就是感知Bean名称的一个接口，当Bean被加载时，会调用`setBeanName`方法并将Bean名称作为参数传递。

有关所有的Aware这里就不一一列举了。

***

## 探究SpringBoot实现原理

**注意：**难度较大，本版块作为选学内容，在开始前，必须完成SSM阶段源码解析部分的学习。

我们在前面的学习中切实感受到了SpringBoot为我们带来的便捷，那么它为何能够实现如此快捷的开发模式，starter又是一个怎样的存在，它是如何进行自动配置的，我们现在就开始研究。

### 启动原理

首先我们来看看，SpringBoot项目启动之后，做了什么事情，SpringApplication中的静态`run`方法：

```java
public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
    return run(new Class[]{primarySource}, args);
}
```

套娃如下：

```java
public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
    return (new SpringApplication(primarySources)).run(args);
}
```

我们发现，这里直接new了一个新的SpringApplication对象，传入我们的主类作为构造方法参数，并调用了非static的`run`方法，我们先来看看构造方法里面做了什么事情：

```java
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
    ...
    this.resourceLoader = resourceLoader;
    Assert.notNull(primarySources, "PrimarySources must not be null");
    this.primarySources = new LinkedHashSet(Arrays.asList(primarySources));
  	//这里是关键，这里会判断当前SpringBoot应用程序是否为Web项目，并返回当前的项目类型
  	//deduceFromClasspath是根据类路径下判断是否包含SpringBootWeb依赖，如果不包含就是NONE类型，包含就是SERVLET类型
    this.webApplicationType = WebApplicationType.deduceFromClasspath();
    this.bootstrapRegistryInitializers = new ArrayList(this.getSpringFactoriesInstances(BootstrapRegistryInitializer.class));
  	//创建所有ApplicationContextInitializer实现类的对象
    this.setInitializers(this.getSpringFactoriesInstances(ApplicationContextInitializer.class));
    this.setListeners(this.getSpringFactoriesInstances(ApplicationListener.class));
    this.mainApplicationClass = this.deduceMainApplicationClass();
}
```

关键就在这里了，它是如何知道哪些类是ApplicationContextInitializer的实现类的呢？

这里就要提到spring.factories了，它是 Spring 仿造Java SPI实现的一种类加载机制。它在 META-INF/spring.factories 文件中配置接口的实现类名称，然后在程序中读取这些配置文件并实例化。这种自定义的SPI机制是 Spring Boot Starter 实现的基础。

SPI的常见例子：

- 数据库驱动加载接口实现类的加载：JDBC加载不同类型数据库的驱动
- 日志门面接口实现类加载：SLF4J加载不同提供商的日志实现类

说白了就是人家定义接口，但是实现可能有很多种，但是核心只提供接口，需要我们按需选择对应的实现，这种方式是高度解耦的。

我们来看看`getSpringFactoriesInstances`方法做了什么：

```java
private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args) {
  	//获取当前的类加载器
    ClassLoader classLoader = this.getClassLoader();
  	//获取所有依赖中 META-INF/spring.factories 中配置的对应接口类的实现类列表
    Set<String> names = new LinkedHashSet(SpringFactoriesLoader.loadFactoryNames(type, classLoader));
  	//根据上方列表，依次创建实例对象  
  List<T> instances = this.createSpringFactoriesInstances(type, parameterTypes, classLoader, args, names);
  	//根据对应类上的Order接口或是注解进行排序
    AnnotationAwareOrderComparator.sort(instances);
  	//返回实例
    return instances;
}
```

其中`SpringFactoriesLoader.loadFactoryNames`正是读取配置的核心部分，我们后面还会遇到。

接着我们来看run方法里面做了什么事情。

```java
public ConfigurableApplicationContext run(String... args) {
    long startTime = System.nanoTime();
    DefaultBootstrapContext bootstrapContext = this.createBootstrapContext();
    ConfigurableApplicationContext context = null;
    this.configureHeadlessProperty();
  	//获取所有的SpringApplicationRunListener，并通知启动事件，默认只有一个实现类EventPublishingRunListener
  	//EventPublishingRunListener会将初始化各个阶段的事件转发给所有监听器
    SpringApplicationRunListeners listeners = this.getRunListeners(args);
    listeners.starting(bootstrapContext, this.mainApplicationClass);

    try {
      	//环境配置
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        ConfigurableEnvironment environment = this.prepareEnvironment(listeners, bootstrapContext, applicationArguments);
        this.configureIgnoreBeanInfo(environment);
      	//打印Banner
        Banner printedBanner = this.printBanner(environment);
      	//创建ApplicationContext，注意这里会根据是否为Web容器使用不同的ApplicationContext实现类
        context = this.createApplicationContext();
        context.setApplicationStartup(this.applicationStartup);
      	//初始化ApplicationContext
        this.prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
      	//执行ApplicationContext的refresh方法
        this.refreshContext(context);
        this.afterRefresh(context, applicationArguments);
        Duration timeTakenToStartup = Duration.ofNanos(System.nanoTime() - startTime);
        if (this.logStartupInfo) {
            (new StartupInfoLogger(this.mainApplicationClass)).logStarted(this.getApplicationLog(), timeTakenToStartup);
        }
        ....
}
```

我们发现，实际上SpringBoot就是Spring的一层壳罢了，离不开最关键的ApplicationContext，也就是说，在启动后会自动配置一个ApplicationContext，只不过是进行了大量的扩展。

我们来看ApplicationContext是怎么来的，打开`createApplicationContext`方法：

```java
protected ConfigurableApplicationContext createApplicationContext() {
    return this.applicationContextFactory.create(this.webApplicationType);
}
```

我们发现在构造方法中`applicationContextFactory`直接使用的是DEFAULT：

```java
this.applicationContextFactory = ApplicationContextFactory.DEFAULT;
```

```java
ApplicationContextFactory DEFAULT = (webApplicationType) -> {
    try {
        switch(webApplicationType) {
        case SERVLET:
            return new AnnotationConfigServletWebServerApplicationContext();
        case REACTIVE:
            return new AnnotationConfigReactiveWebServerApplicationContext();
        default:
            return new AnnotationConfigApplicationContext();
        }
    } catch (Exception var2) {
        throw new IllegalStateException("Unable create a default ApplicationContext instance, you may need a custom ApplicationContextFactory", var2);
    }
};

ConfigurableApplicationContext create(WebApplicationType webApplicationType);
```

DEFAULT是直接编写的一个匿名内部类，其实已经很明确了，正是根据`webApplicationType`类型进行判断，如果是SERVLET，那么久返回专用于Web环境的AnnotationConfigServletWebServerApplicationContext对象（SpringBoot中新增的），否则返回普通的AnnotationConfigApplicationContext对象，也就是到这里为止，Spring的容器就基本已经确定了。

注意AnnotationConfigApplicationContext是Spring框架提供的类，从这里开始相当于我们在讲Spring的底层源码了，我们继续深入，AnnotationConfigApplicationContext对象在创建过程中会创建`AnnotatedBeanDefinitionReader`，它是用于通过注解解析Bean定义的工具类：

```java
public AnnotationConfigApplicationContext() {
    StartupStep createAnnotatedBeanDefReader = this.getApplicationStartup().start("spring.context.annotated-bean-reader.create");
    this.reader = new AnnotatedBeanDefinitionReader(this);
    createAnnotatedBeanDefReader.end();
    this.scanner = new ClassPathBeanDefinitionScanner(this);
}
```

其构造方法：

```java
public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
    ...
    //这里会注册很多的后置处理器
    AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
}
```

```java
public static Set<BeanDefinitionHolder> registerAnnotationConfigProcessors(BeanDefinitionRegistry registry, @Nullable Object source) {
    DefaultListableBeanFactory beanFactory = unwrapDefaultListableBeanFactory(registry);
    ....
    Set<BeanDefinitionHolder> beanDefs = new LinkedHashSet(8);
    RootBeanDefinition def;
    if (!registry.containsBeanDefinition("org.springframework.context.annotation.internalConfigurationAnnotationProcessor")) {
      	//注册了ConfigurationClassPostProcessor用于处理@Configuration、@Import等注解
      	//注意这里是关键，之后Selector还要讲到它
      	//它是继承自BeanDefinitionRegistryPostProcessor，所以它的执行时间在Bean定义加载完成后，Bean初始化之前
        def = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
        def.setSource(source);
        beanDefs.add(registerPostProcessor(registry, def, "org.springframework.context.annotation.internalConfigurationAnnotationProcessor"));
    }

    if (!registry.containsBeanDefinition("org.springframework.context.annotation.internalAutowiredAnnotationProcessor")) {
      	//AutowiredAnnotationBeanPostProcessor用于处理@Value等注解自动注入
        def = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
        def.setSource(source);
        beanDefs.add(registerPostProcessor(registry, def, "org.springframework.context.annotation.internalAutowiredAnnotationProcessor"));
    }
  
  	...
```

回到SpringBoot，我们最后来看，`prepareContext`方法中又做了什么事情：

```java
private void prepareContext(DefaultBootstrapContext bootstrapContext, ConfigurableApplicationContext context, ConfigurableEnvironment environment, SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments, Banner printedBanner) {
  	//环境配置
    context.setEnvironment(environment);
    this.postProcessApplicationContext(context);
    this.applyInitializers(context);
    listeners.contextPrepared(context);
    bootstrapContext.close(context);
    if (this.logStartupInfo) {
        this.logStartupInfo(context.getParent() == null);
        this.logStartupProfileInfo(context);
    }

  	//将Banner注册为Bean
    ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
    beanFactory.registerSingleton("springApplicationArguments", applicationArguments);
    if (printedBanner != null) {
        beanFactory.registerSingleton("springBootBanner", printedBanner);
    }

    if (beanFactory instanceof AbstractAutowireCapableBeanFactory) {
        ((AbstractAutowireCapableBeanFactory)beanFactory).setAllowCircularReferences(this.allowCircularReferences);
        if (beanFactory instanceof DefaultListableBeanFactory) {
            ((DefaultListableBeanFactory)beanFactory).setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
        }
    }

    if (this.lazyInitialization) {
        context.addBeanFactoryPostProcessor(new LazyInitializationBeanFactoryPostProcessor());
    }

  	//这里会获取我们一开始传入的项目主类
    Set<Object> sources = this.getAllSources();
    Assert.notEmpty(sources, "Sources must not be empty");
  	//这里会将我们的主类直接注册为Bean，这样就可以通过注解加载了
    this.load(context, sources.toArray(new Object[0]));
    listeners.contextLoaded(context);
}
```

因此，在`prepareContext`执行完成之后，我们的主类成功完成Bean注册，接下来，就该类上注解大显身手了。

### 自动配置原理

既然主类已经在初始阶段注册为Bean，那么在加载时，就会根据注解定义，进行更多的额外操作。所以我们来看看主类上的`@SpringBootApplication`注解做了什么事情。

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
```

我们发现，`@SpringBootApplication`上添加了`@ComponentScan`注解，此注解我们此前已经认识过了，但是这里并没有配置具体扫描的包，因此它会自动将声明此接口的类所在的包作为basePackage，因此当添加`@SpringBootApplication`之后也就等于直接开启了自动扫描，但是一定注意不能在主类之外的包进行Bean定义，否则无法扫描到，需要手动配置。

接着我们来看第二个注解`@EnableAutoConfiguration`，它就是自动配置的核心了，我们来看看它是如何定义的：

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
```

老套路了，直接一手`@Import`，通过这种方式来将一些外部的Bean加载到容器中。我们来看看AutoConfigurationImportSelector做了什么事情：

```java
public class AutoConfigurationImportSelector implements DeferredImportSelector, BeanClassLoaderAware, ResourceLoaderAware, BeanFactoryAware, EnvironmentAware, Ordered {
		...
}
```

我们看到它实现了很多接口，包括大量的Aware接口，实际上就是为了感知某些必要的对象，并将其存到当前类中。

其中最核心的是`DeferredImportSelector`接口，它是`ImportSelector`的子类，它定义了`selectImports`方法，用于返回需要加载的类名称，在Spring加载ImportSelector类型的Bean时，会调用此方法来获取更多需要加载的类，并将这些类一并注册为Bean：

```java
public interface ImportSelector {
    String[] selectImports(AnnotationMetadata importingClassMetadata);

    @Nullable
    default Predicate<String> getExclusionFilter() {
        return null;
    }
}
```

到目前为止，我们了解了两种使用`@Import`有特殊机制的接口：ImportSelector（这里用到的）和ImportBeanDefinitionRegistrar（之前Mybatis-spring源码有讲）当然还有普通的`@Configuration`配置类。

我们可以来阅读一下`ConfigurationClassPostProcessor`的源码，看看它到底是如何处理`@Import`的：

```java
public void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
    List<BeanDefinitionHolder> configCandidates = new ArrayList();
  	//注意这个阶段仅仅是已经完成扫描了所有的Bean，得到了所有的BeanDefinition，但是还没有进行任何区分
  	//candidate是候选者的意思，一会会将标记了@Configuration的类作为ConfigurationClass加入到configCandidates中
    String[] candidateNames = registry.getBeanDefinitionNames();
    String[] var4 = candidateNames;
    int var5 = candidateNames.length;

    for(int var6 = 0; var6 < var5; ++var6) {
        String beanName = var4[var6];
        BeanDefinition beanDef = registry.getBeanDefinition(beanName);
        if (beanDef.getAttribute(ConfigurationClassUtils.CONFIGURATION_CLASS_ATTRIBUTE) != null) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Bean definition has already been processed as a configuration class: " + beanDef);
            }
        } else if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)) {   //判断是否添加了@Configuration注解
            configCandidates.add(new BeanDefinitionHolder(beanDef, beanName));
        }
    }

    if (!configCandidates.isEmpty()) {
        //...省略

      	//这里创建了一个ConfigurationClassParser用于解析配置类
        ConfigurationClassParser parser = new ConfigurationClassParser(this.metadataReaderFactory, this.problemReporter, this.environment, this.resourceLoader, this.componentScanBeanNameGenerator, registry);
      	//所有配置类的BeanDefinitionHolder列表
        Set<BeanDefinitionHolder> candidates = new LinkedHashSet(configCandidates);
      	//已经解析完成的类
        HashSet alreadyParsed = new HashSet(configCandidates.size());

        do {
            //这里省略，直到所有的配置类全部解析完成
          	//注意在循环过程中可能会由于@Import新增更多的待解析配置类，一律丢进candidates集合中
        } while(!candidates.isEmpty());

        ...

    }
}
```

我们接着来看，`ConfigurationClassParser`是如何进行解析的：

```java
protected void processConfigurationClass(ConfigurationClass configClass, Predicate<String> filter) throws IOException {
  	//@Conditional相关注解处理
  	//后面会讲
    if (!this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
        ...
        }

        ConfigurationClassParser.SourceClass sourceClass = this.asSourceClass(configClass, filter);

        do {
          	//核心
            sourceClass = this.doProcessConfigurationClass(configClass, sourceClass, filter);
        } while(sourceClass != null);

        this.configurationClasses.put(configClass, configClass);
    }
}
```

最后我们再来看最核心的`doProcessConfigurationClass`方法：

```java
protected final SourceClass doProcessConfigurationClass(ConfigurationClass configClass, SourceClass sourceClass)
    ...

    processImports(configClass, sourceClass, getImports(sourceClass), true);    // 处理Import注解

		...

    return null;
}
```

```java
private void processImports(ConfigurationClass configClass, ConfigurationClassParser.SourceClass currentSourceClass, Collection<ConfigurationClassParser.SourceClass> importCandidates, Predicate<String> exclusionFilter, boolean checkForCircularImports) {
    if (!importCandidates.isEmpty()) {
        if (checkForCircularImports && this.isChainedImportOnStack(configClass)) {
            this.problemReporter.error(new ConfigurationClassParser.CircularImportProblem(configClass, this.importStack));
        } else {
            this.importStack.push(configClass);

            try {
                Iterator var6 = importCandidates.iterator();

                while(var6.hasNext()) {
                    ConfigurationClassParser.SourceClass candidate = (ConfigurationClassParser.SourceClass)var6.next();
                    Class candidateClass;
                  	//如果是ImportSelector类型，继续进行运行
                    if (candidate.isAssignable(ImportSelector.class)) {
                        candidateClass = candidate.loadClass();
                        ImportSelector selector = (ImportSelector)ParserStrategyUtils.instantiateClass(candidateClass, ImportSelector.class, this.environment, this.resourceLoader, this.registry);
                        Predicate<String> selectorFilter = selector.getExclusionFilter();
                        if (selectorFilter != null) {
                            exclusionFilter = exclusionFilter.or(selectorFilter);
                        }
									//如果是DeferredImportSelector的实现类，那么会走deferredImportSelectorHandler的handle方法
                        if (selector instanceof DeferredImportSelector) {
                            this.deferredImportSelectorHandler.handle(configClass, (DeferredImportSelector)selector);
                          //否则就按照正常的ImportSelector类型进行加载
                        } else {
                          	//调用selectImports方法获取所有需要加载的类
                            String[] importClassNames = selector.selectImports(currentSourceClass.getMetadata());
                            Collection<ConfigurationClassParser.SourceClass> importSourceClasses = this.asSourceClasses(importClassNames, exclusionFilter);
                          	//递归处理，直到没有
                            this.processImports(configClass, currentSourceClass, importSourceClasses, exclusionFilter, false);
                        }
                      //判断是否为ImportBeanDefinitionRegistrar类型
                    } else if (candidate.isAssignable(ImportBeanDefinitionRegistrar.class)) {
                        candidateClass = candidate.loadClass();
                        ImportBeanDefinitionRegistrar registrar = (ImportBeanDefinitionRegistrar)ParserStrategyUtils.instantiateClass(candidateClass, ImportBeanDefinitionRegistrar.class, this.environment, this.resourceLoader, this.registry);
                      	//往configClass丢ImportBeanDefinitionRegistrar信息进去，之后再处理
                        configClass.addImportBeanDefinitionRegistrar(registrar, currentSourceClass.getMetadata());
                      //否则按普通的配置类进行处理
                    } else {
                        this.importStack.registerImport(currentSourceClass.getMetadata(), candidate.getMetadata().getClassName());
                        this.processConfigurationClass(candidate.asConfigClass(configClass), exclusionFilter);
                    }
                }
            } catch (BeanDefinitionStoreException var17) {
                throw var17;
            } catch (Throwable var18) {
                throw new BeanDefinitionStoreException("Failed to process import candidates for configuration class [" + configClass.getMetadata().getClassName() + "]", var18);
            } finally {
                this.importStack.pop();
            }
        }

    }
}
```

不难注意到，虽然这里额外处理了`ImportSelector`对象，但是还针对`ImportSelector`的子接口`DeferredImportSelector`进行了额外处理，Deferred是延迟的意思，它是一个延迟执行的`ImportSelector`，并不会立即进处理，而是丢进DeferredImportSelectorHandler，并且在`parse`方法的最后进行处理：

```java
public void parse(Set<BeanDefinitionHolder> configCandidates) {
     ...

    this.deferredImportSelectorHandler.process();
}
```

我们接着来看`DeferredImportSelector`正好就有一个`process`方法：

```java
public interface DeferredImportSelector extends ImportSelector {
    @Nullable
    default Class<? extends DeferredImportSelector.Group> getImportGroup() {
        return null;
    }

    public interface Group {
        void process(AnnotationMetadata metadata, DeferredImportSelector selector);

        Iterable<DeferredImportSelector.Group.Entry> selectImports();

        public static class Entry {
          ...
```

最后经过ConfigurationClassParser处理完成后，通过`parser.getConfigurationClasses()`就能得到通过配置类导入了哪些额外的配置类。最后将这些配置类全部注册BeanDefinition，然后就可以交给接下来的Bean初始化过程去处理了。

```java
this.reader.loadBeanDefinitions(configClasses);
```

最后我们再去看`loadBeanDefinitions`是如何运行的：

```java
public void loadBeanDefinitions(Set<ConfigurationClass> configurationModel) {
    ConfigurationClassBeanDefinitionReader.TrackedConditionEvaluator trackedConditionEvaluator = new ConfigurationClassBeanDefinitionReader.TrackedConditionEvaluator();
    Iterator var3 = configurationModel.iterator();

    while(var3.hasNext()) {
        ConfigurationClass configClass = (ConfigurationClass)var3.next();
        this.loadBeanDefinitionsForConfigurationClass(configClass, trackedConditionEvaluator);
    }

}

private void loadBeanDefinitionsForConfigurationClass(ConfigurationClass configClass, ConfigurationClassBeanDefinitionReader.TrackedConditionEvaluator trackedConditionEvaluator) {
    if (trackedConditionEvaluator.shouldSkip(configClass)) {
        String beanName = configClass.getBeanName();
        if (StringUtils.hasLength(beanName) && this.registry.containsBeanDefinition(beanName)) {
            this.registry.removeBeanDefinition(beanName);
        }

        this.importRegistry.removeImportingClass(configClass.getMetadata().getClassName());
    } else {
        if (configClass.isImported()) {
            this.registerBeanDefinitionForImportedConfigurationClass(configClass);  //注册配置类自己
        }

        Iterator var3 = configClass.getBeanMethods().iterator();

        while(var3.hasNext()) {
            BeanMethod beanMethod = (BeanMethod)var3.next();
            this.loadBeanDefinitionsForBeanMethod(beanMethod); //注册@Bean注解标识的方法
        }

      	//注册`@ImportResource`引入的XML配置文件中读取的bean定义
        this.loadBeanDefinitionsFromImportedResources(configClass.getImportedResources());
     	 //注册configClass中经过解析后保存的所有ImportBeanDefinitionRegistrar，注册对应的BeanDefinition
        this.loadBeanDefinitionsFromRegistrars(configClass.getImportBeanDefinitionRegistrars());
    }
}
```

这样，整个`@Configuration`配置类的底层配置流程我们就大致了解了。接着我们来看AutoConfigurationImportSelector是如何实现自动配置的，可以看到内部类AutoConfigurationGroup的process方法，它是父接口的实现，因为父接口是`DeferredImportSelector`，那么很容易得知，实际上最后会调用`process`方法获取所有的自动配置类：

```java
public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector) {
    Assert.state(deferredImportSelector instanceof AutoConfigurationImportSelector, () -> {
        return String.format("Only %s implementations are supported, got %s", AutoConfigurationImportSelector.class.getSimpleName(), deferredImportSelector.getClass().getName());
    });
  	//获取所有的Entry，其实就是，读取spring.factories来查看有哪些自动配置类
    AutoConfigurationImportSelector.AutoConfigurationEntry autoConfigurationEntry = ((AutoConfigurationImportSelector)deferredImportSelector).getAutoConfigurationEntry(annotationMetadata);
    this.autoConfigurationEntries.add(autoConfigurationEntry);
    Iterator var4 = autoConfigurationEntry.getConfigurations().iterator();

    while(var4.hasNext()) {
        String importClassName = (String)var4.next();
        this.entries.putIfAbsent(importClassName, annotationMetadata);
    }

}
```

我们接着来看`getAutoConfigurationEntry`方法：

```java
protected AutoConfigurationImportSelector.AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
  	//判断是否开启了自动配置，是的，自动配置可以关
    if (!this.isEnabled(annotationMetadata)) {
        return EMPTY_ENTRY;
    } else {
      	//根据注解定义获取一些属性
        AnnotationAttributes attributes = this.getAttributes(annotationMetadata);
      	//得到spring.factories文件中所有需要自动配置的类
        List<String> configurations = this.getCandidateConfigurations(annotationMetadata, attributes);
        ... 这里先看前半部分
    }
}
```

注意这里并不是spring.factories文件中所有的自动配置类都会被加载，它会根据@Condition注解的条件进行加载。这样就能实现我们需要什么模块添加对应依赖就可以实现自动配置了。

所有的源码看不懂，都源自于你的心中没有形成一个完整的闭环！一旦一条线推到头，闭环形成，所有疑惑迎刃而解。

### 自定义Starter

我们仿照Mybatis来编写一个自己的starter，Mybatis的starter包含两个部分：

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot</artifactId>
    <version>2.2.0</version>
  </parent>
  <!--  starter本身只做依赖集中管理，不编写任何代码  -->
  <artifactId>mybatis-spring-boot-starter</artifactId>
  <name>mybatis-spring-boot-starter</name>
  <properties>
    <module.name>org.mybatis.spring.boot.starter</module.name>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <!--  编写的专用配置模块   -->
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-autoconfigure</artifactId>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
    </dependency>
  </dependencies>
</project>
```

因此我们也将我们自己的starter这样设计：

我们设计三个模块：

* spring-boot-hello：基础业务功能模块
* spring-boot-starter-hello：启动器
* spring-boot-autoconifgurer-hello：自动配置依赖

首先是基础业务功能模块，这里我们随便创建一个类就可以了：

```java
public class HelloWorldService {
	
}
```

启动器主要做依赖管理，这里就不写任何代码，只写pom文件：

```xml
<dependencies>
    <dependency>
        <groupId>org.example</groupId>
        <artifactId>spring-boot-autoconfigurer-hello</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

导入autoconfigurer模块作为依赖即可，接着我们去编写autoconfigurer模块，首先导入依赖：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-autoconfigure</artifactId>
        <version>2.6.2</version>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <version>2.6.2</version>
        <optional>true</optional>
    </dependency>
    
    <dependency>
        <groupId>org.example</groupId>
        <artifactId>spring-boot-hello</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

接着创建一个HelloWorldAutoConfiguration作为自动配置类：

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication
@ConditionalOnClass(HelloWorldService.class)
@EnableConfigurationProperties(HelloWorldProperties.class)
public class HelloWorldAutoConfiguration {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    HelloWorldProperties properties;

    @Bean
    public HelloWorldService helloWorldService(){
        logger.info("自定义starter项目已启动！");
        logger.info("读取到自定义配置："+properties.getValue());
        return new HelloWorldService();
    }
}
```

对应的配置读取类：

```java
@ConfigurationProperties("hello.world")
public class HelloWorldProperties {

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
```

最后再编写`spring.factories`文件，并将我们的自动配置类添加即可：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.hello.autoconfigurer.HelloWorldAutoConfiguration
```

最后再Maven根项目执行`install`安装到本地仓库，完成。接着就可以在其他项目中使用我们编写的自定义starter了。

### Runner接口

在项目中，可能会遇到这样一个问题：我们需要在项目启动完成之后，紧接着执行一段代码。

我们可以编写自定义的ApplicationRunner来解决，它会在项目启动完成后执行：

```java
@Component
public class TestRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("我是自定义执行！");
    }
}
```

当然也可以使用CommandLineRunner，它也支持使用@Order或是实现Ordered接口来支持优先级执行。

实际上它就是run方法的最后：

```java
public ConfigurableApplicationContext run(String... args) {
    ....

        listeners.started(context, timeTakenToStartup);
  			//这里已经完成整个SpringBoot项目启动，所以执行所有的Runner
        this.callRunners(context, applicationArguments);
    } catch (Throwable var12) {
        this.handleRunFailure(context, var12, listeners);
        throw new IllegalStateException(var12);
    }

    try {
        Duration timeTakenToReady = Duration.ofNanos(System.nanoTime() - startTime);
        listeners.ready(context, timeTakenToReady);
        return context;
    } catch (Throwable var11) {
        this.handleRunFailure(context, var11, (SpringApplicationRunListeners)null);
        throw new IllegalStateException(var11);
    }
}
```

下一章，我们将继续讲解几乎程序员必会的Git版本控制。

# Git版本控制

**注意：**开始学习之前，确保自己的网络可以畅通的连接Github：https://github.com，这个是一个国外网站，连起来特别卡，至于用什么方式实现流畅访问，懂的都懂。

其实版本控制在我们的生活中无处不在，比如你的期末或是毕业答辩论文，由于你写得不规范或是老师不满意，你的老师可能会让你改了又改，于是就会出现下面这种情况：

![点击查看源网页](https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20200417%2F1e63ac0f4d8442cb8c9ab1cb73f510c4.jpeg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1644370473&t=fa8742db0b4f8db635ec003e37bca76c)

我们手里的论文可能会经过多次版本迭代，最终我们会选取一个最好的版本作为最终提交的论文。使用版本控制不仅仅是为了去记录版本迭代历史，更是为了能够随时回退到之前的版本，实现时间回溯。同时，可能我们的论文是多个人一同完成，那么多个人如何去实现同步，如何保证每个人提交的更改都能够正常汇总，如何解决冲突，这些问题都需要一个优秀的版本控制系统来解决。

## 走进Git

我们开发的项目，也需要一个合适的版本控制系统来协助我们更好地管理版本迭代，而Git正是因此而诞生的（有关Git的历史，这里就不多做阐述了，感兴趣的小伙伴可以自行了解，是一位顶级大佬在一怒之下只花了2周时间用C语言开发的，之后的章节还会遇到他）

首先我们来了解一下Git是如何工作的：

![点击查看源网页](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg2020.cnblogs.com%2Fblog%2F932856%2F202004%2F932856-20200423143251346-796113044.jpg&refer=http%3A%2F%2Fimg2020.cnblogs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1644374894&t=7c2044128f7851ecd92de3c01f0187ca)

可以看到，它大致分为4个板块：

* 工作目录：存放我们正在写的代码（当我们新版本开发完成之后，就可以进行新版本的提交）
* 暂存区：暂时保存待提交的内容（新版本提交后会存放到本地仓库）
* 本地仓库：位于我们电脑上的一个版本控制仓库（存放的就是当前项目各个版本代码的增删信息）
* 远程仓库：位于服务器上的版本控制仓库（服务器上的版本信息可以由本地仓库推送上去，也可以从服务器抓取到本地仓库）

它是一个分布式的控制系统，因此一般情况下我们每个人的电脑上都有一个本地仓库，由大家共同向远程仓库去推送版本迭代信息。

通过这一系列操作，我们就可以实现每开发完一个版本或是一个功能，就提交一次新版本，这样，我们就可以很好地控制项目的版本迭代，想回退到之前的版本随时都可以回退，想查看新版本添加或是删除了什么代码，随时都可以查看。

## 安装Git

首先请前往Git官网去下载最新的安装包：https://git-scm.com/download/win

这手把手演示一下如何安装Git环境。

安装完成后，需要设定用户名和邮箱来区分不同的用户：

```shell
git config --global user.name "Your Name"
git config --global user.email "email@example.com"
```

## 基本命令介绍

### 创建本地仓库

我们可以将任意一个文件夹作为一个本地仓库，输入：

```shell
git init
```

输入后，会自动生成一个`.git`目录，注意这个目录是一个隐藏目录，而当前目录就是我们的工作目录。

创建成功后，我们可以查看一下当前的一个状态，输入：

```shell
git status
```

如果已经成功配置为Git本地仓库，那么输入后可以看到：

```shell
On branch master

No commits yet
```

这表示我们还没有向仓库中提交任何内容，也就是一个空的状态。

### 添加和提交

接着我们来看看，如何使用git来管理我们文档的版本，我们创建一个文本文档，随便写入一点内容，接着输入：

```shell
git status
```

我们会得到如下提示：

```shell
Untracked files:
  (use "git add <file>..." to include in what will be committed)
	hello.txt

nothing added to commit but untracked files present (use "git add" to track)
```

其中Untracked files是未追踪文件的意思，也就是说，如果一个文件处于未追踪状态，那么git不会记录它的变化，始终将其当做一个新创建的文件，这里我们将其添加到暂存区，那么它会自动变为被追踪状态：

```shell
git add hello.txt #也可以 add . 一次性添加目录下所有的
```

再次查看当前状态：

```sh
Changes to be committed:
  (use "git rm --cached <file>..." to unstage)
	new file:   hello.txt
```

现在文件名称的颜色变成了绿色，并且是处于Changes to be committed下面，因此，我们的hello.txt现在已经被添加到暂存区了。

接着我们来尝试将其提交到Git本地仓库中，注意需要输入提交的描述以便后续查看，比如你这次提交修改了或是新增了哪些内容：

```sh
git commit -m 'Hello World'
```

接着我们可以查看我们的提交记录：

```sh
git log
git log --graph
```

我们还可以查看最近一次变更的详细内容：

```sh
git show [也可以加上commit ID查看指定的提交记录]
```

再次查看当前状态，已经是清空状态了：

```sh
On branch master
nothing to commit, working tree clean
```

接着我们可以尝试修改一下我们的文本文档，由于当前文件已经是被追踪状态，那么git会去跟踪它的变化，如果说文件发生了修改，那么我们再次查看状态会得到下面的结果：

```sh
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git restore <file>..." to discard changes in working directory)
	modified:   hello.txt
```

也就是说现在此文件是处于已修改状态，我们如果修改好了，就可以提交我们的新版本到本地仓库中：

```sh
git add .
git commit -m 'Modify Text'
```

接着我们来查询一下提交记录，可以看到一共有两次提交记录。

我们可以创建一个`.gitignore`文件来确定一个文件忽略列表，如果忽略列表中的文件存在且不是被追踪状态，那么git不会对其进行任何检查：

```yaml
# 这样就会匹配所有以txt结尾的文件
*.txt
# 虽然上面排除了所有txt结尾的文件，但是这个不排除
!666.txt
# 也可以直接指定一个文件夹，文件夹下的所有文件将全部忽略
test/
# 目录中所有以txt结尾的文件，但不包括子目录
xxx/*.txt
# 目录中所有以txt结尾的文件，包括子目录
xxx/**/*.txt
```

创建后，我们来看看是否还会检测到我们忽略的文件。

### 回滚

当我们想要回退到过去的版本时，就可以执行回滚操作，执行后，可以将工作空间的内容恢复到指定提交的状态：

```sh
git reset --hard commitID
```

执行后，会直接重置为那个时候的状态。再次查看提交日志，我们发现之后的日志全部消失了。

那么要是现在我又想回去呢？我们可以通过查看所有分支的所有操作记录：

```sh
git reflog
```

这样就能找到之前的commitID，再次重置即可。

## 分支

分支就像我们树上的一个树枝一样，它们可能一开始的时候是同一根树枝，但是长着长着就开始分道扬镳了，这就是分支。我们的代码也是这样，可能一开始写基础功能的时候使用的是单个分支，但是某一天我们希望基于这些基础的功能，把我们的项目做成两个不同方向的项目，比如一个方向做Web网站，另一个方向做游戏服务端。

因此，我们可以在一个主干上分出N个分支，分别对多个分支的代码进行维护。

### 创建分支

我们可以通过以下命令来查看当前仓库中存在的分支：

```sh
git branch
```

我们发现，默认情况下是有一个master分支的，并且我们使用的也是master分支，一般情况下master分支都是正式版本的更新，而其他分支一般是开发中才频繁更新的。我们接着来基于当前分支创建一个新的分支：

```sh
git branch test
# 对应的删除分支是
git branch -d yyds
```

现在我们修改一下文件，提交，再查看一下提交日志：

```sh
git commit -a -m 'branch master commit'
```

通过添加-a来自动将未放入暂存区的已修改文件放入暂存区并执行提交操作。查看日志，我们发现现在我们的提交只生效于master分支，而新创建的分支并没有发生修改。

我们将分支切换到另一个分支：

```sh
git checkout test
```

我们会发现，文件变成了此分支创建的时的状态，也就是说，在不同分支下我们的文件内容是相互隔离的。

我们现在再来提交一次变更，会发现它只生效在yyds分支上。我们可以看看当前的分支状态：

```sh
git log --all --graph
```

### 合并分支

我们也可以将两个分支更新的内容最终合并到同一个分支上，我们先切换回主分支：

```sh
git checkout master
```

接着使用分支合并命令：

```sh
git merge test
```

会得到如下提示：

```
Auto-merging hello.txt
CONFLICT (content): Merge conflict in hello.txt
Automatic merge failed; fix conflicts and then commit the result.
```

在合并过程中产生了冲突，因为两个分支都对hello.txt文件进行了修改，那么现在要合并在一起，到底保留谁的hello文件呢？

我们可以查看一下是哪里发生了冲突：

```sh
git diff
```

因此，现在我们将master分支的版本回退到修改hello.txt之前或是直接修改为最新版本的内容，这样就不会有冲突了，接着再执行一次合并操作，现在两个分支成功合并为同一个分支。

### 变基分支

除了直接合并分支以外，我们还可以进行变基操作，它跟合并不同，合并是分支回到主干的过程，而变基是直接修改分支开始的位置，比如我们希望将yyds变基到master上，那么yyds会将分支起点移动到master最后一次提交位置：

```sh
git rebase master
```

变基后，yyds分支相当于同步了此前master分支的全部提交。

### 优选

我们还可以选择其将他分支上的提交作用于当前分支上，这种操作称为cherrypick：

```sh
git cherry-pick <commit id>:单独合并一个提交
```

这里我们在master分支上创建一个新的文件，提交此次更新，接着通过cherry-pick的方式将此次更新作用于test分支上。

***

## 使用IDEA版本控制

虽然前面我们基本讲解了git的命令行使用方法，但是没有一个图形化界面，始终会感觉到很抽象，所以这里我们使用IDEA来演示，IDEA内部集成了git模块，它可以让我们的git版本管理图形化显示，当然除了IDEA也有一些独立的软件比如：SourceTree（挺好用）

打开IDEA后，找到版本控模块，我们直接点击创建本地仓库，它会自动将当前项目的根目录作为我们的本地仓库，而我们编写的所有代码和项目目录下其他的文件都可以进行版本控制。

我们发现所有项目中正在编写的类文件全部变红了，也就是处于未追踪状态，接着我们进行第一次初始化提交，提交之后我们可以在下方看到所有的本地仓库提交记录。

接着我们来整合一下Web环境，创建新的类之后，IDEA会提示我们是否将文件添加到Git，也就是是否放入暂存区并开启追踪，我们可以直接对比两次代码的相同和不同之处。

接着我们来演示一下分支创建和分支管理。

***

## 远程仓库

远程仓库实际上就是位于服务器上的仓库，它能在远端保存我们的版本历史，并且可以实现多人同时合作编写项目，每个人都能够同步他人的版本，能够看到他人的版本提交，相当于将我们的代码放在服务器上进行托管。

远程仓库有公有和私有的，公有的远程仓库有GitHub、码云、Coding等，他们都是对外开放的，我们注册账号之后就可以使用远程仓库进行版本控制，其中最大的就是GitHub，但是它服务器在国外，我们国内连接可能会有一点卡。私有的一般是GitLab这种自主搭建的远程仓库私服，在公司中比较常用，它只对公司内部开放，不对外开放。

这里我们以GitHub做讲解，官网：https://github.com，首先完成用户注册。

### 远程账户认证和推送

接着我们就可以创建一个自定义的远程仓库了。

创建仓库后，我们可以通过推送来将本地仓库中的内容推送到远程仓库。

```sh
git remote add 名称 远程仓库地址
git push 远程仓库名称 本地分支名称[:远端分支名称]
```

注意`push`后面两个参数，一个是远端名称，还有一个就是本地分支名称，但是如果本地分支名称和远端分支名称一致，那么不用指定远端分支名称，但是如果我们希望推送的分支在远端没有同名的，那么需要额外指定。推送前需要登陆账户，GitHub现在不允许使用用户名密码验证，只允许使用个人AccessToken来验证身份，所以我们需要先去生成一个Token才可以。

推送后，我们发现远程仓库中的内容已经与我们本地仓库中的内容保持一致了，注意，远程仓库也可以有很多个分支。

但是这样比较麻烦，我们每次都需要去输入用户名和密码，有没有一劳永逸的方法呢？当然，我们也可以使用SSH来实现一次性校验，我们可以在本地生成一个rsa公钥：

```sh
ssh-keygen -t rsa
cat ~/.ssh/github.pub
```

接着我们需要在GitHub上上传我们的公钥，当我们再次去访问GitHub时，会自动验证，就无需进行登录了，之后在Linux部分我们会详细讲解SSH的原理。

接着我们修改一下工作区的内容，提交到本地仓库后，再推送到远程仓库，提交的过程中我们注意观察提交记录：

```sh
git commit -a -m 'Modify files'
git log --all --oneline --graph
git push origin master 
git log --all --oneline --graph
```

我们可以将远端和本地的分支进行绑定，绑定后就不需要指定分支名称了：

```sh
git push --set-upstream origin master:master
git push origin
```

在一个本地仓库对应一个远程仓库的情况下，远程仓库基本上就是纯粹的代码托管了（云盘那种感觉，就纯粹是存你代码的）

### 克隆项目

如果我们已经存在一个远程仓库的情况下，我们需要在远程仓库的代码上继续编写代码，这个时候怎么办呢？

我们可以使用克隆操作来将远端仓库的内容全部复制到本地：

```sh
git clone 远程仓库地址
```

这样本地就能够直接与远程保持同步。

### 抓取、拉取和冲突解决

我们接着来看，如果这个时候，出现多个本地仓库对应一个远程仓库的情况下，比如一个团队里面，N个人都在使用同一个远程仓库，但是他们各自只负责编写和推送自己业务部分的代码，也就是我们常说的协同工作，那么这个时候，我们就需要协调。

比如程序员A完成了他的模块，那么他就可以提交代码并推送到远程仓库，这时程序员B也要开始写代码了，由于远程仓库有其他程序员的提交记录，因此程序员B的本地仓库和远程仓库不一致，这时就需要有先进行pull操作，获取远程仓库中最新的提交：

```sh
git fetch 远程仓库 #抓取：只获取但不合并远端分支，后面需要我们手动合并才能提交
git pull 远程仓库 #拉取：获取+合并
```

在程序员B拉取了最新的版本后，再编写自己的代码然后提交就可以实现多人合作编写项目了，并且在拉取过程中就能将别人提交的内容同步到本地，开发效率大大提升。

如果工作中存在不协调的地方，比如现在我们本地有两个仓库，一个仓库去修改hello.txt并直接提交，另一个仓库也修改hello.txt并直接提交，会得到如下错误：

```
To https://github.com/xx/xxx.git
 ! [rejected]        master -> master (fetch first)
error: failed to push some refs to 'https://github.com/xx/xxx.git'
hint: Updates were rejected because the remote contains work that you do
hint: not have locally. This is usually caused by another repository pushing
hint: to the same ref. You may want to first integrate the remote changes
hint: (e.g., 'git pull ...') before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.
```

一旦一个本地仓库推送了代码，那么另一个本地仓库的推送会被拒绝，原因是当前文件已经被其他的推送给修改了，我们这边相当于是另一个版本，和之前两个分支合并一样，产生了冲突，因此我们只能去解决冲突问题。

如果远程仓库中的提交和本地仓库中的提交没有去编写同一个文件，那么就可以直接拉取：

```sh
git pull 远程仓库
```

拉取后会自动进行合并，合并完成之后我们再提交即可。

但是如果两次提交都修改了同一个文件，那么就会遇到和多分支合并一样的情况，在合并时会产生冲突，这时就需要我们自己去解决冲突了。

我们可以在IDEA中演示一下，实际开发场景下可能会遇到的问题。

***

至此，Git版本控制就讲解到这里，下一章我们会继续认识一个全新的数据库：Redis。

![点击查看源网页](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic4.zhimg.com%2Fv2-054d8ff6135b3638aca543eff7424f98_1200x500.jpg&refer=http%3A%2F%2Fpic4.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1644402012&t=79c30b8002d088850e33bd90492419b2)



![点击查看源网页](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic4.zhimg.com%2Fv2-054d8ff6135b3638aca543eff7424f98_1200x500.jpg&refer=http%3A%2F%2Fpic4.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1644500043&t=72a4f8ecfca9ff5a2a0b3896edef4be7)

# Redis数据库

**灵魂拷问：**不是学了MySQL吗，存数据也能存了啊，又学一个数据库干嘛？

在前面我们学习了MySQL数据库，它是一种传统的关系型数据库，我们可以使用MySQL来更好地管理和组织我们的数据，虽然在小型Web应用下，只需要一个MySQL+Mybatis自带的缓存系统就可以胜任大部分的数据存储工作。但是MySQL的缺点也很明显，它的数据始终是存储在硬盘上的，对于我们的用户信息这种不需要经常发生修改的内容，使用MySQL存储确实可以，但是如果是快速更新或是频繁使用的数据，比如微博热搜、双十一秒杀，这些数据不仅要求服务器需要提供更高的响应速度，而且还需要面对短时间内上百万甚至上千万次访问，而MySQL的磁盘IO读写性能完全不能满足上面的需求，能够满足上述需求的只有内存，因为速度远高于磁盘IO。

因此，我们需要寻找一种更好的解决方案，来存储上述这类特殊数据，弥补MySQL的不足，以应对大数据时代的重重考验。

## NoSQL概论

NoSQL全称是Not Only SQL（不仅仅是SQL）它是一种非关系型数据库，相比传统SQL关系型数据库，它：

* 不保证关系数据的ACID特性
* 并不遵循SQL标准
* 消除数据之间关联性

乍一看，这玩意不比MySQL垃圾？我们再来看看它的优势：

* 远超传统关系型数据库的性能
* 非常易于扩展
* 数据模型更加灵活
* 高可用

这样，NoSQL的优势一下就出来了，这不就是我们正要寻找的高并发海量数据的解决方案吗！

NoSQL数据库分为以下几种：

* **键值存储数据库：**所有的数据都是以键值方式存储的，类似于我们之前学过的HashMap，使用起来非常简单方便，性能也非常高。
* **列存储数据库：**这部分数据库通常是用来应对分布式存储的海量数据。键仍然存在，但是它们的特点是指向了多个列。
* **文档型数据库：**它是以一种特定的文档格式存储数据，比如JSON格式，在处理网页等复杂数据时，文档型数据库比传统键值数据库的查询效率更高。
* **图形数据库：**利用类似于图的数据结构存储数据，结合图相关算法实现高速访问。

其中我们要学习的Redis数据库，就是一个开源的**键值存储数据库**，所有的数据全部存放在内存中，它的性能大大高于磁盘IO，并且它也可以支持数据持久化，他还支持横向扩展、主从复制等。

实际生产中，我们一般会配合使用Redis和MySQL以发挥它们各自的优势，取长补短。

## Redis安装和部署

我们这里还是使用Windows安装Redis服务器，但是官方指定是安装到Linux服务器上，我们后面学习了Linux之后，再来安装到Linux服务器上。由于官方并没有提供Windows版本的安装包，我们需要另外寻找：

* 官网地址：https://redis.io
* GitHub Windows版本维护地址：https://github.com/tporadowski/redis/releases

***

## 基本操作

在我们之前使用MySQL时，我们需要先在数据库中创建一张表，并定义好表的每个字段内容，最后再通过`insert`语句向表中添加数据，而Redis并不具有MySQL那样的严格的表结构，Redis是一个键值数据库，因此，可以像Map一样的操作方式，通过键值对向Redis数据库中添加数据（操作起来类似于向一个HashMap中存放数据）

在Redis下，数据库是由一个整数索引标识，而不是由一个数据库名称。 默认情况下，我们连接Redis数据库之后，会使用0号数据库，我们可以通过Redis配置文件中的参数来修改数据库总数，默认为16个。

我们可以通过`select`语句进行切换：

```sql
select 序号;
```

### 数据操作

我们来看看，如何向Redis数据库中添加数据：

```sql
set <key> <value>
-- 一次性多个
mset [<key> <value>]...
```

所有存入的数据默认会以**字符串**的形式保存，键值具有一定的命名规范，以方便我们可以快速定位我们的数据属于哪一个部分，比如用户的数据：

```sql
-- 使用冒号来进行板块分割，比如下面表示用户XXX的信息中的name属性，值为lbw
set user:info:用户ID:name lbw
```

我们可以通过键值获取存入的值：

```sql
get <key>
```

你以为Redis就仅仅只是存取个数据吗？它还支持数据的过期时间设定：

```sql
set <key> <value> EX 秒
set <key> <value> PX 毫秒
```

当数据到达指定时间时，会被自动删除。我们也可以单独为其他的键值对设置过期时间：

```sql
expire <key> 秒
```

通过下面的命令来查询某个键值对的过期时间还剩多少：

```sql
ttl <key>
-- 毫秒显示
pttl <key>
-- 转换为永久
persist <key>
```

那么当我们想直接删除这个数据时呢？直接使用：

```sql
del <key>...
```

删除命令可以同时拼接多个键值一起删除。

当我们想要查看数据库中所有的键值时：

```sql
keys *
```

也可以查询某个键是否存在：

```sql
exists <key>...
```

还可以随机拿一个键：

```sql
randomkey
```

我们可以将一个数据库中的内容移动到另一个数据库中：

```sql
move <key> 数据库序号
```

修改一个键为另一个键：

```sql
rename <key> <新的名称>
-- 下面这个会检查新的名称是否已经存在
renamex <key> <新的名称>
```

如果存放的数据是一个数字，我们还可以对其进行自增自减操作：

```sql
-- 等价于a = a + 1
incr <key>
-- 等价于a = a + b
incrby <key> b
-- 等价于a = a - 1
decr <key>
```

最后就是查看值的数据类型：

```sql
type <key>
```

Redis数据库也支持多种数据类型，但是它更偏向于我们在Java中认识的那些数据类型。

## 数据类型介绍

一个键值对除了存储一个String类型的值以外，还支持多种常用的数据类型。

### Hash

这种类型本质上就是一个HashMap，也就是嵌套了一个HashMap罢了，在Java中就像这样：

```java
#Redis默认存String类似于这样：
Map<String, String> hash = new HashMap<>();
#Redis存Hash类型的数据类似于这样：
Map<String, Map<String, String>> hash = new HashMap<>();
```

它比较适合存储类这样的数据，由于值本身又是一个Map，因此我们可以在此Map中放入类的各种属性和值，以实现一个Hash数据类型存储一个类的数据。

我们可以像这样来添加一个Hash类型的数据：

```sql
hset <key> [<字段> <值>]...
```

我们可以直接获取：

```sql
hget <key> <字段>
-- 如果想要一次性获取所有的字段和值
hgetall <key>
```

同样的，我们也可以判断某个字段是否存在：

```sql
hexists <key> <字段>
```

删除Hash中的某个字段：

```sql
hdel <key>
```

我们发现，在操作一个Hash时，实际上就是我们普通操作命令前面添加一个`h`，这样就能以同样的方式去操作Hash里面存放的键值对了，这里就不一一列出所有的操作了。我们来看看几个比较特殊的。

我们现在想要知道Hash中一共存了多少个键值对：

```sql
hlen <key>
```

我们也可以一次性获取所有字段的值：

```sql
hvals <key>
```

唯一需要注意的是，Hash中只能存放字符串值，不允许出现嵌套的的情况。

### List

我们接着来看List类型，实际上这个猜都知道，它就是一个列表，而列表中存放一系列的字符串，它支持随机访问，支持双端操作，就像我们使用Java中的LinkedList一样。

我们可以直接向一个已存在或是不存在的List中添加数据，如果不存在，会自动创建：

```sql
-- 向列表头部添加元素
lpush <key> <element>...
-- 向列表尾部添加元素
rpush <key> <element>...
-- 在指定元素前面/后面插入元素
linsert <key> before/after <指定元素> <element>
```

同样的，获取元素也非常简单：

```sql
-- 根据下标获取元素
lindex <key> <下标>
-- 获取并移除头部元素
lpop <key>
-- 获取并移除尾部元素
rpop <key>
-- 获取指定范围内的
lrange <key> start stop
```

注意下标可以使用负数来表示从后到前数的数字（Python：搁这儿抄呢是吧）:

```sql
-- 获取列表a中的全部元素
lrange a 0 -1
```

没想到吧，push和pop还能连着用呢：

```sql
-- 从前一个数组的最后取一个数出来放到另一个数组的头部，并返回元素
rpoplpush 当前数组 目标数组
```

它还支持阻塞操作，类似于生产者和消费者，比如我们想要等待列表中有了数据后再进行pop操作：

```sql
-- 如果列表中没有元素，那么就等待，如果指定时间（秒）内被添加了数据，那么就执行pop操作，如果超时就作废，支持同时等待多个列表，只要其中一个列表有元素了，那么就能执行
blpop <key>... timeout
```

### Set和SortedSet

Set集合其实就像Java中的HashSet一样（我们在JavaSE中已经讲解过了，HashSet本质上就是利用了一个HashMap，但是Value都是固定对象，仅仅是Key不同）它不允许出现重复元素，不支持随机访问，但是能够利用Hash表提供极高的查找效率。

向Set中添加一个或多个值：

```sql
sadd <key> <value>...
```

查看Set集合中有多少个值：

```sql
scard <key>
```

判断集合中是否包含：

```sql
-- 是否包含指定值
sismember <key> <value>
-- 列出所有值
smembers <key>
```

集合之间的运算：

```sql
-- 集合之间的差集
sdiff <key1> <key2>
-- 集合之间的交集
sinter <key1> <key2>
-- 求并集
sunion <key1> <key2>
-- 将集合之间的差集存到目标集合中
sdiffstore 目标 <key1> <key2>
-- 同上
sinterstore 目标 <key1> <key2>
-- 同上
sunionstore 目标 <key1> <key2>
```

移动指定值到另一个集合中：

```sql
smove <key> 目标 value 
```

移除操作：

```sql
-- 随机移除一个幸运儿
spop <key>
-- 移除指定
srem <key> <value>...
```

那么如果我们要求Set集合中的数据按照我们指定的顺序进行排列怎么办呢？这时就可以使用SortedSet，它支持我们为每个值设定一个分数，分数的大小决定了值的位置，所以它是有序的。

我们可以添加一个带分数的值：

```sql
zadd <key> [<value> <score>]...
```

同样的：

```sql
-- 查询有多少个值
zcard <key>
-- 移除
zrem <key> <value>...
-- 获取区间内的所有
zrange <key> start stop
```

由于所有的值都有一个分数，我们也可以根据分数段来获取：

``` sql
-- 通过分数段查看
zrangebyscore <key> start stop [withscores] [limit]
-- 统计分数段内的数量
zcount <key>  start stop
-- 根据分数获取指定值的排名
zrank <key> <value>
```

https://www.jianshu.com/p/32b9fe8c20e1

有关Bitmap、HyperLogLog和Geospatial等数据类型，这里暂时不做介绍，感兴趣可以自行了解。

***

## 持久化

我们知道，Redis数据库中的数据都是存放在内存中，虽然很高效，但是这样存在一个非常严重的问题，如果突然停电，那我们的数据不就全部丢失了吗？它不像硬盘上的数据，断电依然能够保存。

这个时候我们就需要持久化，我们需要将我们的数据备份到硬盘上，防止断电或是机器故障导致的数据丢失。

持久化的实现方式有两种方案：一种是直接保存当前**已经存储的数据**，相当于复制内存中的数据到硬盘上，需要恢复数据时直接读取即可；还有一种就是保存我们存放数据的**所有过程**，需要恢复数据时，只需要将整个过程完整地重演一遍就能保证与之前数据库中的内容一致。

### RDB

RDB就是我们所说的第一种解决方案，那么如何将数据保存到本地呢？我们可以使用命令：

```sql
save
-- 注意上面这个命令是直接保存，会占用一定的时间，也可以单独开一个子进程后台执行保存
bgsave
```

执行后，会在服务端目录下生成一个dump.rdb文件，而这个文件中就保存了内存中存放的数据，当服务器重启后，会自动加载里面的内容到对应数据库中。保存后我们可以关闭服务器：

```sql
shutdown
```

重启后可以看到数据依然存在。

![点击查看图片来源](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fqqe2.com%2Fjava%2Fzb_users%2Fupload%2F2020%2F04%2F202004281588086055367603.png&refer=http%3A%2F%2Fqqe2.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1644843952&t=ec4cd6eb2c6d47a10aff5b9f264d2f16)

虽然这种方式非常方便，但是由于会完整复制所有的数据，如果数据库中的数据量比较大，那么复制一次可能就需要花费大量的时间，所以我们可以每隔一段时间自动进行保存；还有就是，如果我们基本上都是在进行读操作，而没有进行写操作，实际上只需要偶尔保存一次即可，因为数据几乎没有怎么变化，可能两次保存的都是一样的数据。

我们可以在配置文件中设置自动保存，并设定在一段时间内写入多少数据时，执行一次保存操作：

```
save 300 10 # 300秒（5分钟）内有10个写入
save 60 10000 # 60秒（1分钟）内有10000个写入
```

配置的save使用的都是bgsave后台执行。

### AOF

虽然RDB能够很好地解决数据持久化问题，但是它的缺点也很明显：每次都需要去完整地保存整个数据库中的数据，同时后台保存过程中也会产生额外的内存开销，最严重的是它并不是实时保存的，如果在自动保存触发之前服务器崩溃，那么依然会导致少量数据的丢失。

而AOF就是另一种方式，它会以日志的形式将我们每次执行的命令都进行保存，服务器重启时会将所有命令依次执行，通过这种重演的方式将数据恢复，这样就能很好解决实时性存储问题。

![rdb和aof区别](https://qqe2.com/java/zb_users/upload/2020/04/202004281588086068660716.png)

但是，我们多久写一次日志呢？我们可以自己配置保存策略，有三种策略：

* always：每次执行写操作都会保存一次
* everysec：每秒保存一次（默认配置），这样就算丢失数据也只会丢一秒以内的数据
* no：看系统心情保存

可以在配置文件中配置：

```sql
# 注意得改成也是
appendonly yes

# appendfsync always
appendfsync everysec
# appendfsync no
```

重启服务器后，可以看到服务器目录下多了一个`appendonly.aof`文件，存储的就是我们执行的命令。

 AOF的缺点也很明显，每次服务器启动都需要进行过程重演，相比RDB更加耗费时间，并且随着我们的操作变多，不断累计，可能到最后我们的aof文件会变得无比巨大，我们需要一个改进方案来优化这些问题。

Redis有一个AOF重写机制进行优化，比如我们执行了这样的语句：

```
lpush test 666
lpush test 777
lpush test 888
```

实际上用一条语句也可以实现：

```
lpush test 666 777 888
```

正是如此，只要我们能够保证最终的重演结果和原有语句的结果一致，无论语句如何修改都可以，所以我们可以通过这种方式将多条语句进行压缩。

我们可以输入命令来手动执行重写操作：

```sql
bgrewriteaof
```

或是在配置文件中配置自动重写：

```
# 百分比计算，这里不多介绍
auto-aof-rewrite-percentage 100
# 当达到这个大小时，触发自动重写
auto-aof-rewrite-min-size 64mb
```

至此，我们就完成了两种持久化方案的介绍，最后我们再来进行一下总结：

* AOF：
  * 优点：存储速度快、消耗资源少、支持实时存储
  * 缺点：加载速度慢、数据体积大
* RDB：
  * 优点：加载速度快、数据体积小
  * 缺点：存储速度慢大量消耗资源、会发生数据丢失

***

## 事务和锁机制

和MySQL一样，在Redis中也有事务机制，当我们需要保证多条命令一次性完整执行而中途不受到其他命令干扰时，就可以使用事务机制。

我们可以使用命令来直接开启事务：

```sql
multi
```

当我们输入完所有要执行的命令时，可以使用命令来立即执行事务：

```sql
exec
```

我们也可以中途取消事务：

```sql
discard
```

实际上整个事务是创建了一个命令队列，它不像MySQL那种在事务中也能单独得到结果，而是我们提前将所有的命令装在队列中，但是并不会执行，而是等我们提交事务的时候再统一执行。

### 锁

又提到锁了，实际上这个概念对我们来说已经不算是陌生了。实际上在Redis中也会出现多个命令同时竞争同一个数据的情况，比如现在有两条命令同时执行，他们都要去修改a的值，那么这个时候就只能动用锁机制来保证同一时间只能有一个命令操作。

虽然Redis中也有锁机制，但是它是一种乐观锁，不同于MySQL，我们在MySQL中认识的锁是悲观锁，那么什么是乐观锁什么是悲观锁呢？

* 悲观锁：时刻认为别人会来抢占资源，禁止一切外来访问，直到释放锁，具有强烈的排他性质。
* 乐观锁：并不认为会有人来抢占资源，所以会直接对数据进行操作，在操作时再去验证是否有其他人抢占资源。

Redis中可以使用watch来监视一个目标，如果执行事务之前被监视目标发生了修改，则取消本次事务：

```sql
watch
```

我们可以开两个客户端进行测试。

取消监视可以使用：

```sql
unwatch
```

至此，Redis的基础内容就讲解完毕了，在之后的SpringCloud阶段，我们还会去讲解集群相关的知识，包括主从复制、哨兵模式等。

***

## 使用Java与Redis交互

既然了解了如何通过命令窗口操作Redis数据库，那么我们如何使用Java来操作呢？

这里我们需要使用到Jedis框架，它能够实现Java与Redis数据库的交互，依赖：

```xml
<dependencies>
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>4.0.0</version>
    </dependency>
</dependencies>
```

### 基本操作

我们来看看如何连接Redis数据库，非常简单，只需要创建一个对象即可：

```java
public static void main(String[] args) {
    //创建Jedis对象
    Jedis jedis = new Jedis("localhost", 6379);
  	
  	//使用之后关闭连接
  	jedis.close();
}
```

通过Jedis对象，我们就可以直接调用命令的同名方法来执行Redis命令了，比如：

```java
public static void main(String[] args) {
    //直接使用try-with-resouse，省去close
    try(Jedis jedis = new Jedis("192.168.10.3", 6379)){
        jedis.set("test", "lbwnb");   //等同于 set test lbwnb 命令
        System.out.println(jedis.get("test"));  //等同于 get test 命令
    }
}
```

Hash类型的数据也是这样：

```java
public static void main(String[] args) {
    try(Jedis jedis = new Jedis("192.168.10.3", 6379)){
        jedis.hset("hhh", "name", "sxc");   //等同于 hset hhh name sxc
        jedis.hset("hhh", "sex", "19");    //等同于 hset hhh age 19
        jedis.hgetAll("hhh").forEach((k, v) -> System.out.println(k+": "+v));
    }
}
```

我们接着来看看列表操作：

```java
public static void main(String[] args) {
    try(Jedis jedis = new Jedis("192.168.10.3", 6379)){
        jedis.lpush("mylist", "111", "222", "333");  //等同于 lpush mylist 111 222 333 命令
        jedis.lrange("mylist", 0, -1)
                .forEach(System.out::println);    //等同于 lrange mylist 0 -1
    }
}
```

实际上我们只需要按照对应的操作去调用同名方法即可，所有的类型封装Jedis已经帮助我们完成了。

### SpringBoot整合Redis

我们接着来看如何在SpringBoot项目中整合Redis操作框架，只需要一个starter即可，但是它底层没有用Jedis，而是Lettuce：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

starter提供的默认配置会去连接本地的Redis服务器，并使用0号数据库，当然你也可以手动进行修改：

```yaml
spring:
  redis:
  	#Redis服务器地址
    host: 192.168.10.3
    #端口
    port: 6379
    #使用几号数据库
    database: 0
```

starter已经给我们提供了两个默认的模板类：

```java
@Configuration(
    proxyBeanMethods = false
)
@ConditionalOnClass({RedisOperations.class})
@EnableConfigurationProperties({RedisProperties.class})
@Import({LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class})
public class RedisAutoConfiguration {
    public RedisAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean(
        name = {"redisTemplate"}
    )
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }
}
```

那么如何去使用这两个模板类呢？我们可以直接注入`StringRedisTemplate`来使用模板：

```java
@SpringBootTest
class SpringBootTestApplicationTests {

    @Autowired
    StringRedisTemplate template;

    @Test
    void contextLoads() {
        ValueOperations<String, String> operations = template.opsForValue();
        operations.set("c", "xxxxx");   //设置值
        System.out.println(operations.get("c"));   //获取值
      	
        template.delete("c");    //删除键
        System.out.println(template.hasKey("c"));   //判断是否包含键
    }

}
```

实际上所有的值的操作都被封装到了`ValueOperations`对象中，而普通的键操作直接通过模板对象就可以使用了，大致使用方式其实和Jedis一致。

我们接着来看看事务操作，由于Spring没有专门的Redis事务管理器，所以只能借用JDBC提供的，只不过无所谓，正常情况下反正我们也要用到这玩意：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

```java
@Service
public class RedisService {

    @Resource
    StringRedisTemplate template;

    @PostConstruct
    public void init(){
        template.setEnableTransactionSupport(true);   //需要开启事务
    }

    @Transactional    //需要添加此注解
    public void test(){
        template.multi();
        template.opsForValue().set("d", "xxxxx");
        template.exec();
    }
}
```

我们还可以为RedisTemplate对象配置一个Serializer来实现对象的JSON存储：

```java
@Test
void contextLoad2() {
    //注意Student需要实现序列化接口才能存入Redis
    template.opsForValue().set("student", new Student());
    System.out.println(template.opsForValue().get("student"));
}
```

***

## 使用Redis做缓存

我们可以轻松地使用Redis来实现一些框架的缓存和其他存储。

### Mybatis二级缓存

还记得我们在学习Mybatis讲解的缓存机制吗，我们当时介绍了二级缓存，它是Mapper级别的缓存，能够作用与所有会话。但是当时我们提出了一个问题，由于Mybatis的默认二级缓存只能是单机的，如果存在多台服务器访问同一个数据库，实际上二级缓存只会在各自的服务器上生效，但是我们希望的是多台服务器都能使用同一个二级缓存，这样就不会造成过多的资源浪费。

![img](https://img-blog.csdnimg.cn/img_convert/5afd7713f9a97615dc3a0b1d3bc7db27.png)

我们可以将Redis作为Mybatis的二级缓存，这样就能实现多台服务器使用同一个二级缓存，因为它们只需要连接同一个Redis服务器即可，所有的缓存数据全部存储在Redis服务器上。我们需要手动实现Mybatis提供的Cache接口，这里我们简单编写一下：

```java
//实现Mybatis的Cache接口
public class RedisMybatisCache implements Cache {

    private final String id;
    private static RedisTemplate<Object, Object> template;

   	//注意构造方法必须带一个String类型的参数接收id
    public RedisMybatisCache(String id){
        this.id = id;
    }

  	//初始化时通过配置类将RedisTemplate给过来
    public static void setTemplate(RedisTemplate<Object, Object> template) {
        RedisMybatisCache.template = template;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object o, Object o1) {
      	//这里直接向Redis数据库中丢数据即可，o就是Key，o1就是Value，60秒为过期时间
        template.opsForValue().set(o, o1, 60, TimeUnit.SECONDS);
    }

    @Override
    public Object getObject(Object o) {
      	//这里根据Key直接从Redis数据库中获取值即可
        return template.opsForValue().get(o);
    }

    @Override
    public Object removeObject(Object o) {
      	//根据Key删除
        return template.delete(o);
    }

    @Override
    public void clear() {
      	//由于template中没封装清除操作，只能通过connection来执行
				template.execute((RedisCallback<Void>) connection -> {
          	//通过connection对象执行清空操作
            connection.flushDb();
            return null;
        });
    }

    @Override
    public int getSize() {
      	//这里也是使用connection对象来获取当前的Key数量
        return template.execute(RedisServerCommands::dbSize).intValue();
    }
}
```

缓存类编写完成后，我们接着来编写配置类：

```java
@Configuration
public class MainConfiguration {
    @Resource
    RedisTemplate<Object, Object> template;

    @PostConstruct
    public void init(){
      	//把RedisTemplate给到RedisMybatisCache
        RedisMybatisCache.setTemplate(template);
    }
}
```

最后我们在Mapper上启用此缓存即可：

```java
//只需要修改缓存实现类implementation为我们的RedisMybatisCache即可
@CacheNamespace(implementation = RedisMybatisCache.class)
@Mapper
public interface MainMapper {

    @Select("select name from student where sid = 1")
    String getSid();
}
```

最后我们提供一个测试用例来查看当前的二级缓存是否生效：

```java
@SpringBootTest
class SpringBootTestApplicationTests {


    @Resource
    MainMapper mapper;

    @Test
    void contextLoads() {
        System.out.println(mapper.getSid());
        System.out.println(mapper.getSid());
        System.out.println(mapper.getSid());
    }

}
```

手动使用客户端查看Redis数据库，可以看到已经有一条Mybatis生成的缓存数据了。

### Token持久化存储

我们之前使用SpringSecurity时，remember-me的Token是支持持久化存储的，而我们当时是存储在数据库中，那么Token信息能否存储在缓存中呢，当然也是可以的，我们可以手动实现一个：

```java
//实现PersistentTokenRepository接口
@Component
public class RedisTokenRepository implements PersistentTokenRepository {
  	//Key名称前缀，用于区分
    private final static String REMEMBER_ME_KEY = "spring:security:rememberMe:";
    @Resource
    RedisTemplate<Object, Object> template;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
      	//这里要放两个，一个存seriesId->Token，一个存username->seriesId，因为删除时是通过username删除
        template.opsForValue().set(REMEMBER_ME_KEY+"username:"+token.getUsername(), token.getSeries());
        template.expire(REMEMBER_ME_KEY+"username:"+token.getUsername(), 1, TimeUnit.DAYS);
        this.setToken(token);
    }

  	//先获取，然后修改创建一个新的，再放入
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken token = this.getToken(series);
        if(token != null)
           this.setToken(new PersistentRememberMeToken(token.getUsername(), series, tokenValue, lastUsed));
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return this.getToken(seriesId);
    }

  	//通过username找seriesId直接删除这两个
    @Override
    public void removeUserTokens(String username) {
        String series = (String) template.opsForValue().get(REMEMBER_ME_KEY+"username:"+username);
        template.delete(REMEMBER_ME_KEY+series);
        template.delete(REMEMBER_ME_KEY+"username:"+username);
    }

  
  	//由于PersistentRememberMeToken没实现序列化接口，这里只能用Hash来存储了，所以单独编写一个set和get操作
    private PersistentRememberMeToken getToken(String series){
        Map<Object, Object> map = template.opsForHash().entries(REMEMBER_ME_KEY+series);
        if(map.isEmpty()) return null;
        return new PersistentRememberMeToken(
                (String) map.get("username"),
                (String) map.get("series"),
                (String) map.get("tokenValue"),
                new Date(Long.parseLong((String) map.get("date"))));
    }

    private void setToken(PersistentRememberMeToken token){
        Map<String, String> map = new HashMap<>();
        map.put("username", token.getUsername());
        map.put("series", token.getSeries());
        map.put("tokenValue", token.getTokenValue());
        map.put("date", ""+token.getDate().getTime());
        template.opsForHash().putAll(REMEMBER_ME_KEY+token.getSeries(), map);
        template.expire(REMEMBER_ME_KEY+token.getSeries(), 1, TimeUnit.DAYS);
    }
}
```

接着把验证Service实现了：

```java
@Service
public class AuthService implements UserDetailsService {

    @Resource
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = mapper.getAccountByUsername(username);
        if(account == null) throw new UsernameNotFoundException("");
        return User
                .withUsername(username)
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }
}
```

Mapper也安排上：

```java
@Data
public class Account implements Serializable {
    int id;
    String username;
    String password;
    String role;
}
```

```java
@CacheNamespace(implementation = MybatisRedisCache.class)
@Mapper
public interface UserMapper {

    @Select("select * from users where username = #{username}")
    Account getAccountByUsername(String username);
}
```

最后配置文件配一波：

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .rememberMe()
            .tokenRepository(repository);
}

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
            .userDetailsService(service)
            .passwordEncoder(new BCryptPasswordEncoder());
}
```

OK，启动服务器验证一下吧。

***

## 三大缓存问题

**注意：**这部分内容作为选学内容。

虽然我们可以利用缓存来大幅度提升我们程序的数据获取效率，但是使用缓存也存在着一些潜在的问题。

### 缓存穿透

![img](https://mydlq-club.oss-cn-beijing.aliyuncs.com/images/springboot-cache-redis-1004.png?x-oss-process=style/shuiyin)

当我们去查询一个一定不存在的数据，比如Mybatis在缓存是未命中的情况下需要从数据库查询，查不到数据则不写入缓存，这将导致这个不存在的数据每次请求都要到数据库去查询，造成缓存穿透。

这显然是很浪费资源的，我们希望的是，如果这个数据不存在，为什么缓存这一层不直接返回空呢，这时就不必再去查数据库了，但是也有一个问题，缓存不去查数据库怎么知道数据库里面到底有没有这个数据呢？

这时我们就可以使用布隆过滤器来进行判断。什么是布隆过滤器？（当然不是打辅助的那个布隆，只不过也挺像，辅助布隆也是挡子弹的）

![点击查看图片来源](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.bubuko.com%2Finfo%2F201903%2F20190321142642446276.png&refer=http%3A%2F%2Fimage.bubuko.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1644902390&t=4f0440b0357965ead1fa34fb27513927)

使用布隆过滤器，能够告诉你某样东西一定不存在或是某样东西可能存在。

布隆过滤器本质是一个存放二进制位的bit数组，如果我们要添加一个值到布隆过滤器中，我们需要使用N个不同的哈希函数来生成N个哈希值，并对每个生成的哈希值指向的bit位置1，如上图所示，一共添加了三个值abc。

接着我们给一个d，那么这时就可以进行判断，如果说d计算的N个哈希值的位置上都是1，那么就说明d可能存在；这时候又来了个e，计算后我们发现有一个位置上的值是0，这时就可以直接断定e一定不存在。

### 缓存击穿

![img](https://mydlq-club.oss-cn-beijing.aliyuncs.com/images/springboot-cache-redis-1005.png?x-oss-process=style/shuiyin)

某个 Key 属于热点数据，访问非常频繁，同一时间很多人都在访问，在这个Key失效的瞬间，大量的请求到来，这时发现缓存中没有数据，就全都直接请求数据库，相当于击穿了缓存屏障，直接攻击整个系统核心。

这种情况下，最好的解决办法就是不让Key那么快过期，如果一个Key处于高频访问，那么可以适当地延长过期时间。

### 缓存雪崩

![img](https://mydlq-club.oss-cn-beijing.aliyuncs.com/images/springboot-cache-redis-1006.png?x-oss-process=style/shuiyin)

当你的Redis服务器炸了或是大量的Key在同一时间过期，这时相当于缓存直接GG了，那么如果这时又有很多的请求来访问不同的数据，同一时间内缓存服务器就得向数据库大量发起请求来重新建立缓存，很容易把数据库也搞GG。

解决这种问题最好的办法就是设置高可用，也就是搭建Redis集群，当然也可以采取一些服务熔断降级机制，这些内容我们会在SpringCloud阶段再进行探讨。



# SpringBoot其他框架

通过了解其他的SpringBoot框架，我们就可以在我们自己的Web服务器上实现更多更高级的功能。

## 邮件发送：Mail

我们在注册很多的网站时，都会遇到邮件或是手机号验证，也就是通过你的邮箱或是手机短信去接受网站发给你的注册验证信息，填写验证码之后，就可以完成注册了，同时，网站也会绑定你的手机号或是邮箱。

那么，像这样的功能，我们如何实现呢？SpringBoot已经给我们提供了封装好的邮件模块使用：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

### 邮件发送

在学习邮件发送之前，我们需要先了解一下什么是电子邮件。

电子邮件也是一种通信方式，是互联网应用最广的服务。通过网络的电子邮件系统，用户可以以非常低廉的价格（不管发送到哪里，都只需负担网费，实际上就是把信息发送到对方服务器而已）、非常快速的方式，与世界上任何一个地方的电子邮箱用户联系。

虽说方便倒是方便，虽然是曾经的霸主，不过现在这个时代，QQ微信横行，手机短信和电子邮箱貌似就只剩收验证码这一个功能了。

要在Internet上提供电子邮件功能，必须有专门的电子邮件服务器。例如现在Internet很多提供邮件服务的厂商：新浪、搜狐、163、QQ邮箱等，他们都有自己的邮件服务器。这些服务器类似于现实生活中的邮局，它主要负责接收用户投递过来的邮件，并把邮件投递到邮件接收者的电子邮箱中。

所有的用户都可以在电子邮件服务器上申请一个账号用于邮件发送和接收，那么邮件是以什么样的格式发送的呢？实际上和Http一样，邮件发送也有自己的协议，也就是约定邮件数据长啥样以及如何通信。

![img](https://images2015.cnblogs.com/blog/851491/201612/851491-20161202143243756-1715308358.png)

比较常用的协议有两种：

1. SMTP协议（主要用于发送邮件 Simple Mail Transfer Protocol）
2. POP3协议（主要用于接收邮件 Post Office Protocol 3）

整个发送/接收流程大致如下：

![img](https://img2.baidu.com/it/u=3675146129,445744702&fm=253&fmt=auto&app=138&f=JPG?w=812&h=309)

实际上每个邮箱服务器都有一个smtp发送服务器和pop3接收服务器，比如要从QQ邮箱发送邮件到163邮箱，那么我们只需要通过QQ邮箱客户端告知QQ邮箱的smtp服务器我们需要发送邮件，以及邮件的相关信息，然后QQ邮箱的smtp服务器就会帮助我们发送到163邮箱的pop3服务器上，163邮箱会通过163邮箱客户端告知对应用户收到一封新邮件。

而我们如果想要实现给别人发送邮件，那么就需要连接到对应电子邮箱的smtp服务器上，并告知其我们要发送邮件。而SpringBoot已经帮助我们将最基本的底层通信全部实现了，我们只需要关心smtp服务器的地址以及我们要发送的邮件长啥样即可。

这里以163邮箱 https://mail.163.com 为例，我们需要在配置文件中告诉SpringBootMail我们的smtp服务器的地址以及你的邮箱账号和密码，首先我们要去设置中开启smtp/pop3服务才可以，开启后会得到一个随机生成的密钥，这个就是我们的密码。

```yaml
spring:
  mail:
  	# 163邮箱的地址为smtp.163.com，直接填写即可
    host: smtp.163.com
    # 你申请的163邮箱
    username: javastudy111@163.com
    # 注意密码是在开启smtp/pop3时自动生成的，记得保存一下，不然就找不到了
    password: AZJTOAWZESLMHTNI
```

配置完成后，接着我们来进行一下测试：

```java
@SpringBootTest
class SpringBootTestApplicationTests {

  	//JavaMailSender是专门用于发送邮件的对象，自动配置类已经提供了Bean
    @Autowired
    JavaMailSender sender;

    @Test
    void contextLoads() {
      	//SimpleMailMessage是一个比较简易的邮件封装，支持设置一些比较简单内容
        SimpleMailMessage message = new SimpleMailMessage();
      	//设置邮件标题
        message.setSubject("【电子科技大学教务处】关于近期学校对您的处分决定");
      	//设置邮件内容
        message.setText("XXX同学您好，经监控和教务巡查发现，您近期存在旷课、迟到、早退、上课刷抖音行为，" +
                "现已通知相关辅导员，请手写5000字书面检讨，并在2022年4月1日17点前交到辅导员办公室。");
      	//设置邮件发送给谁，可以多个，这里就发给你的QQ邮箱
        message.setTo("你的QQ号@qq.com");
      	//邮件发送者，这里要与配置文件中的保持一致
        message.setFrom("javastudy111@163.com");
      	//OK，万事俱备只欠发送
        sender.send(message);
    }

}
```

如果需要添加附件等更多功能，可以使用MimeMessageHelper来帮助我们完成：

```java
@Test
void contextLoads() throws MessagingException {
  	//创建一个MimeMessage
    MimeMessage message = sender.createMimeMessage();
  	//使用MimeMessageHelper来帮我们修改MimeMessage中的信息
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setSubject("Test");
    helper.setText("lbwnb");
    helper.setTo("你的QQ号@qq.com");
    helper.setFrom("javastudy111@163.com");
  	//发送修改好的MimeMessage
    sender.send(message);
}
```

### 邮件注册

既然我们已经了解了邮件发送，那么我们接着来看如何在我们的项目中实现邮件验证。

首先明确验证流程：请求验证码 -> 生成验证码（临时有效，注意设定过期时间） -> 用户输入验证码并填写注册信息 -> 验证通过注册成功！

***

![点击查看图片来源](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2Fv2-6f0b9bb234b2534ec295ff195bad183a_1440w.jpg%3Fsource%3D172ae18b&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645108924&t=d40aa5dc6be398725b4ff21ef5895454)

## 持久层框架：JPA

* 用了Mybatis之后，你看那个JDBC，真是太逊了。
* 这么说，你的项目很勇哦？
* 开玩笑，我的写代码超勇的好不好。
* 阿伟，你可曾幻想过有一天你的项目里不再有SQL语句？
* 不再有SQL语句？那我怎么和数据库交互啊？
* 我看你是完全不懂哦
* 懂，懂什么啊？
* 你想懂？来，到我项目里来，我给你看点好康的。
* 好康？是什么新框架哦？
* 什么新框架，比新框架还刺激，还可以让你的项目登duang郎哦。
* 哇，杰哥，你项目里面都没SQL语句诶，这是用的什么框架啊？

在我们之前编写的项目中，我们不难发现，实际上大部分的数据库交互操作，到最后都只会做一个事情，那就是把数据库中的数据映射为Java中的对象。比如我们要通过用户名去查找对应的用户，或是通过ID查找对应的学生信息，在使用Mybatis时，我们只需要编写正确的SQL语句就可以直接将获取的数据映射为对应的Java对象，通过调用Mapper中的方法就能直接获得实体类，这样就方便我们在Java中数据库表中的相关信息了。

但是以上这些操作都有一个共性，那就是它们都是通过某种条件去进行查询，而最后的查询结果，都是一个实体类，所以你会发现你写的很多SQL语句都是一个套路`select * from xxx where xxx=xxx`，那么能否有一种框架，帮我们把这些相同的套路给封装起来，直接把这类相似的SQL语句给屏蔽掉，不再由我们编写，而是让框架自己去组合拼接。

### 认识SpringDataJPA

首先我们来看一个国外的统计：

![image-20220119140326867](/Users/nagocoler/Library/Application Support/typora-user-images/image-20220119140326867.png)

不对吧，为什么Mybatis这么好用，这么强大，却只有10%的人喜欢呢？然而事实就是，在国外JPA几乎占据了主导地位，而Mybatis并不像国内那样受待见，所以你会发现，JPA都有SpringBoot的官方直接提供的starter，而Mybatis没有。

至于为啥SSM阶段不讲这个，而是放到现在来讲也是因为，在微服务场景下它的优势才能更多的发挥出来。

那么，什么是JPA？

JPA（Java Persistence API）和JDBC类似，也是官方定义的一组接口，但是它相比传统的JDBC，它是为了实现ORM而生的，即Object-Relationl Mapping，它的作用是在关系型数据库和对象之间形成一个映射，这样，我们在具体的操作数据库的时候，就不需要再去和复杂的SQL语句打交道，只要像平时操作对象一样操作它就可以了。

在之前，我们使用JDBC或是Mybatis来操作数据，通过直接编写对应的SQL语句来实现数据访问，但是我们发现实际上我们在Java中大部分操作数据库的情况都是读取数据并封装为一个实体类，因此，为什么不直接将实体类直接对应到一个数据库表呢？也就是说，一张表里面有什么属性，那么我们的对象就有什么属性，所有属性跟数据库里面的字段一一对应，而读取数据时，只需要读取一行的数据并封装为我们定义好的实体类既可以，而具体的SQL语句执行，完全可以交给框架根据我们定义的映射关系去生成，不再由我们去编写，因为这些SQL实际上都是千篇一律的。

而实现JPA规范的框架一般最常用的就是`Hibernate`，它是一个重量级框架，学习难度相比Mybatis也更高一些，而SpringDataJPA也是采用Hibernate框架作为底层实现，并对其加以封装。

官网：https://spring.io/projects/spring-data-jpa

### 使用JPA

同样的，我们只需要导入stater依赖即可：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

接着我们可以直接创建一个类，比如账户类，我们只需要把一个账号对应的属性全部定义好即可：

```java
@Data
public class Account {
    int id;
    String username;
    String password;
}
```

接着，我们可以通过注解形式，在属性上添加数据库映射关系，这样就能够让JPA知道我们的实体类对应的数据库表长啥样。

```java
@Data
@Entity   //表示这个类是一个实体类
@Table(name = "users")    //对应的数据库中表名称
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)   //生成策略，这里配置为自增
    @Column(name = "id")    //对应表中id这一列
    @Id     //此属性为主键
    int id;

    @Column(name = "username")   //对应表中username这一列
    String username;

    @Column(name = "password")   //对应表中password这一列
    String password;
}
```

接着我们来修改一下配置文件：

```yaml
spring:
  jpa:
		#开启SQL语句执行日志信息
    show-sql: true
    hibernate:
    	#配置为自动创建
      ddl-auto: create
```

`ddl-auto`属性用于设置自动表定义，可以实现自动在数据库中为我们创建一个表，表的结构会根据我们定义的实体类决定，它有4种

* create 启动时删数据库中的表，然后创建，退出时不删除数据表 
* create-drop 启动时删数据库中的表，然后创建，退出时删除数据表 如果表不存在报错 
* update 如果启动时表格式不一致则更新表，原有数据保留 
* validate 项目启动表结构进行校验 如果不一致则报错

我们可以在日志中发现，在启动时执行了如下SQL语句：

```
Hibernate: create table users (id integer not null auto_increment, password varchar(255), username varchar(255), primary key (id)) engine=InnoDB
```

而我们的数据库中对应的表已经创建好了。

我们接着来看如何访问我们的表，我们需要创建一个Repository实现类：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    
}
```

注意JpaRepository有两个泛型，前者是具体操作的对象实体，也就是对应的表，后者是ID的类型，接口中已经定义了比较常用的数据库操作。编写接口继承即可，我们可以直接注入此接口获得实现类：

```java
@SpringBootTest
class JpaTestApplicationTests {

    @Resource
    AccountRepository repository;

    @Test
    void contextLoads() {
      	//直接根据ID查找
        repository.findById(1).ifPresent(System.out::println);
    }

}
```

运行后，成功得到查询结果。我们接着来测试增删操作：

```java
@Test
void addAccount(){
    Account account = new Account();
    account.setUsername("Admin");
    account.setPassword("123456");
    account = repository.save(account);  //返回的结果会包含自动生成的主键值
    System.out.println("插入时，自动生成的主键ID为："+account.getId());
}
```

```java
@Test
void deleteAccount(){
    repository.deleteById(2);   //根据ID删除对应记录
}
```

```java
@Test
void pageAccount() {
    repository.findAll(PageRequest.of(0, 1)).forEach(System.out::println);  //直接分页
}
```

我们发现，使用了JPA之后，整个项目的代码中没有出现任何的SQL语句，可以说是非常方便了，JPA依靠我们提供的注解信息自动完成了所有信息的映射和关联。

相比Mybatis，JPA几乎就是一个全自动的ORM框架，而Mybatis则顶多算是半自动ORM框架。

### 方法名称拼接自定义SQL

虽然接口预置的方法使用起来非常方便，但是如果我们需要进行条件查询等操作或是一些判断，就需要自定义一些方法来实现，同样的，我们不需要编写SQL语句，而是通过方法名称的拼接来实现条件判断，这里列出了所有支持的条件判断名称：

| `Distinct`             | `findDistinctByLastnameAndFirstname`                         | `select distinct … where x.lastname = ?1 and x.firstname = ?2` |
| ---------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `And`                  | `findByLastnameAndFirstname`                                 | `… where x.lastname = ?1 and x.firstname = ?2`               |
| `Or`                   | `findByLastnameOrFirstname`                                  | `… where x.lastname = ?1 or x.firstname = ?2`                |
| `Is`，`Equals`         | `findByFirstname`,`findByFirstnameIs`,`findByFirstnameEquals` | `… where x.firstname = ?1`                                   |
| `Between`              | `findByStartDateBetween`                                     | `… where x.startDate between ?1 and ?2`                      |
| `LessThan`             | `findByAgeLessThan`                                          | `… where x.age < ?1`                                         |
| `LessThanEqual`        | `findByAgeLessThanEqual`                                     | `… where x.age <= ?1`                                        |
| `GreaterThan`          | `findByAgeGreaterThan`                                       | `… where x.age > ?1`                                         |
| `GreaterThanEqual`     | `findByAgeGreaterThanEqual`                                  | `… where x.age >= ?1`                                        |
| `After`                | `findByStartDateAfter`                                       | `… where x.startDate > ?1`                                   |
| `Before`               | `findByStartDateBefore`                                      | `… where x.startDate < ?1`                                   |
| `IsNull`，`Null`       | `findByAge(Is)Null`                                          | `… where x.age is null`                                      |
| `IsNotNull`，`NotNull` | `findByAge(Is)NotNull`                                       | `… where x.age not null`                                     |
| `Like`                 | `findByFirstnameLike`                                        | `… where x.firstname like ?1`                                |
| `NotLike`              | `findByFirstnameNotLike`                                     | `… where x.firstname not like ?1`                            |
| `StartingWith`         | `findByFirstnameStartingWith`                                | `… where x.firstname like ?1`（参数与附加`%`绑定）           |
| `EndingWith`           | `findByFirstnameEndingWith`                                  | `… where x.firstname like ?1`（参数与前缀`%`绑定）           |
| `Containing`           | `findByFirstnameContaining`                                  | `… where x.firstname like ?1`（参数绑定以`%`包装）           |
| `OrderBy`              | `findByAgeOrderByLastnameDesc`                               | `… where x.age = ?1 order by x.lastname desc`                |
| `Not`                  | `findByLastnameNot`                                          | `… where x.lastname <> ?1`                                   |
| `In`                   | `findByAgeIn(Collection<Age> ages)`                          | `… where x.age in ?1`                                        |
| `NotIn`                | `findByAgeNotIn(Collection<Age> ages)`                       | `… where x.age not in ?1`                                    |
| `True`                 | `findByActiveTrue()`                                         | `… where x.active = true`                                    |
| `False`                | `findByActiveFalse()`                                        | `… where x.active = false`                                   |
| `IgnoreCase`           | `findByFirstnameIgnoreCase`                                  | `… where UPPER(x.firstname) = UPPER(?1)`                     |

比如我们想要实现根据用户名模糊匹配查找用户：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
		//按照表中的规则进行名称拼接，不用刻意去记，IDEA会有提示
    List<Account> findAllByUsernameLike(String str);
}
```

```java
@Test
void test() {
    repository.findAllByUsernameLike("%T%").forEach(System.out::println);
}
```

又比如我们想同时根据用户名和ID一起查询：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByIdAndUsername(int id, String username);
  //可以使用Optional类进行包装，Optional<Account> findByIdAndUsername(int id, String username);
    
    List<Account> findAllByUsernameLike(String str);
}
```

```java
@Test
void test() {
    System.out.println(repository.findByIdAndUsername(1, "Test"));
}
```

比如我们想判断数据库中是否存在某个ID的用户：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByIdAndUsername(int id, String username);

    List<Account> findAllByUsernameLike(String str);

    boolean existsAccountById(int id);
}
```

```java
@Test
void test() {
    System.out.println(repository.existsAccountByUsername("Test"));
}
```

注意自定义条件操作的方法名称一定要遵循规则，不然会出现异常：

```
Caused by: org.springframework.data.repository.query.QueryCreationException: Could not create query for public abstract  ...
```

### 关联查询

在实际开发中，比较常见的场景还有关联查询，也就是我们会在表中添加一个外键字段，而此外键字段又指向了另一个表中的数据，当我们查询数据时，可能会需要将关联数据也一并获取，比如我们想要查询某个用户的详细信息，一般用户简略信息会单独存放一个表，而用户详细信息会单独存放在另一个表中。当然，除了用户详细信息之外，可能在某些电商平台还会有用户的购买记录、用户的购物车，交流社区中的用户帖子、用户评论等，这些都是需要根据用户信息进行关联查询的内容。

![img](https://img1.baidu.com/it/u=292198351,4011695440&fm=253&fmt=auto&app=138&f=JPEG?w=404&h=436)

我们知道，在JPA中，每张表实际上就是一个实体类的映射，而表之间的关联关系，也可以看作对象之间的依赖关系，比如用户表中包含了用户详细信息的ID字段作为外键，那么实际上就是用户表实体中包括了用户详细信息实体对象：

```java
@Data
@Entity
@Table(name = "users_detail")
public class AccountDetail {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @Column(name = "address")
    String address;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "real_name")
    String realName;
}
```

而用户信息和用户详细信息之间形成了一对一的关系，那么这时我们就可以直接在类中指定这种关系：

```java
@Data
@Entity
@Table(name = "users")
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    int id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @JoinColumn(name = "detail_id")   //指定存储外键的字段名称
    @OneToOne    //声明为一对一关系
    AccountDetail detail;
}
```

在修改实体类信息后，我们发现在启动时也进行了更新，日志如下：

```
Hibernate: alter table users add column detail_id integer
Hibernate: create table users_detail (id integer not null auto_increment, address varchar(255), email varchar(255), phone varchar(255), real_name varchar(255), primary key (id)) engine=InnoDB
Hibernate: alter table users add constraint FK7gb021edkxf3mdv5bs75ni6jd foreign key (detail_id) references users_detail (id)
```

是不是感觉非常方便！都懒得去手动改表结构了。

接着我们往用户详细信息中添加一些数据，一会我们可以直接进行查询：

```java
@Test
void pageAccount() {
    repository.findById(1).ifPresent(System.out::println);
}
```

查询后，可以发现，得到如下结果：

```
Hibernate: select account0_.id as id1_0_0_, account0_.detail_id as detail_i4_0_0_, account0_.password as password2_0_0_, account0_.username as username3_0_0_, accountdet1_.id as id1_1_1_, accountdet1_.address as address2_1_1_, accountdet1_.email as email3_1_1_, accountdet1_.phone as phone4_1_1_, accountdet1_.real_name as real_nam5_1_1_ from users account0_ left outer join users_detail accountdet1_ on account0_.detail_id=accountdet1_.id where account0_.id=?
Account(id=1, username=Test, password=123456, detail=AccountDetail(id=1, address=四川省成都市青羊区, email=8371289@qq.com, phone=1234567890, realName=本伟))
```

也就是，在建立关系之后，我们查询Account对象时，会自动将关联数据的结果也一并进行查询。

那要是我们只想要Account的数据，不想要用户详细信息数据怎么办呢？我希望在我要用的时候再获取详细信息，这样可以节省一些网络开销，我们可以设置懒加载，这样只有在需要时才会向数据库获取：

```java
@JoinColumn(name = "detail_id")
@OneToOne(fetch = FetchType.LAZY)    //将获取类型改为LAZY
AccountDetail detail;
```

接着我们测试一下：

```java
@Transactional   //懒加载属性需要在事务环境下获取，因为repository方法调用完后Session会立即关闭
@Test
void pageAccount() {
    repository.findById(1).ifPresent(account -> {
        System.out.println(account.getUsername());   //获取用户名
        System.out.println(account.getDetail());  //获取详细信息（懒加载）
    });
}
```

接着我们来看看控制台输出了什么：

```
Hibernate: select account0_.id as id1_0_0_, account0_.detail_id as detail_i4_0_0_, account0_.password as password2_0_0_, account0_.username as username3_0_0_ from users account0_ where account0_.id=?
Test
Hibernate: select accountdet0_.id as id1_1_0_, accountdet0_.address as address2_1_0_, accountdet0_.email as email3_1_0_, accountdet0_.phone as phone4_1_0_, accountdet0_.real_name as real_nam5_1_0_ from users_detail accountdet0_ where accountdet0_.id=?
AccountDetail(id=1, address=四川省成都市青羊区, email=8371289@qq.com, phone=1234567890, realName=卢本)
```

可以看到，获取用户名之前，并没有去查询用户的详细信息，而是当我们获取详细信息时才进行查询并返回AccountDetail对象。

那么我们是否也可以在添加数据时，利用实体类之间的关联信息，一次性添加两张表的数据呢？可以，但是我们需要稍微修改一下级联关联操作设定：

```java
@JoinColumn(name = "detail_id")
@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //设置关联操作为ALL
AccountDetail detail;
```

* ALL：所有操作都进行关联操作
* PERSIST：插入操作时才进行关联操作
* REMOVE：删除操作时才进行关联操作
* MERGE：修改操作时才进行关联操作

可以多个并存，接着我们来进行一下测试：

```java
@Test
void addAccount(){
    Account account = new Account();
    account.setUsername("Nike");
    account.setPassword("123456");
    AccountDetail detail = new AccountDetail();
    detail.setAddress("重庆市渝中区解放碑");
    detail.setPhone("1234567890");
    detail.setEmail("73281937@qq.com");
    detail.setRealName("张三");
  	account.setDetail(detail);
    account = repository.save(account);
    System.out.println("插入时，自动生成的主键ID为："+account.getId()+"，外键ID为："+account.getDetail().getId());
}
```

可以看到日志结果：

```
Hibernate: insert into users_detail (address, email, phone, real_name) values (?, ?, ?, ?)
Hibernate: insert into users (detail_id, password, username) values (?, ?, ?)
插入时，自动生成的主键ID为：6，外键ID为：3
```

结束后会发现数据库中两张表都同时存在数据。

接着我们来看一对多关联，比如每个用户的成绩信息：

```java
@JoinColumn(name = "uid")  //注意这里的name指的是Score表中的uid字段对应的就是当前的主键，会将uid外键设置为当前的主键
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)   //在移除Account时，一并移除所有的成绩信息，依然使用懒加载
List<Score> scoreList;
```

```java
@Data
@Entity
@Table(name = "users_score")   //成绩表，注意只存成绩，不存学科信息，学科信息id做外键
public class Score {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    int id;

    @OneToOne   //一对一对应到学科上
    @JoinColumn(name = "cid")
    Subject subject;

    @Column(name = "socre")
    double score;

    @Column(name = "uid")
    int uid;
}
```

```java
@Data
@Entity
@Table(name = "subjects")   //学科信息表
public class Subject {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    @Id
    int cid;

    @Column(name = "name")
    String name;

    @Column(name = "teacher")
    String teacher;

    @Column(name = "time")
    int time;
}
```

在数据库中填写相应数据，接着我们就可以查询用户的成绩信息了：

```java
@Transactional
@Test
void test() {
    repository.findById(1).ifPresent(account -> {
        account.getScoreList().forEach(System.out::println);
    });
}
```

成功得到用户所有的成绩信息，包括得分和学科信息。

同样的，我们还可以将对应成绩中的教师信息单独分出一张表存储，并建立多对一的关系，因为多门课程可能由同一个老师教授（千万别搞晕了，一定要理清楚关联关系，同时也是考验你的基础扎不扎实）：

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "tid")   //存储教师ID的字段，和一对一是一样的，也会当前表中创个外键
Teacher teacher;
```

接着就是教师实体类了：

```java
@Data
@Entity
@Table(name = "teachers")
public class Teacher {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "sex")
    String sex;
}
```

最后我们再进行一下测试：

```java
@Transactional
@Test
void test() {
    repository.findById(3).ifPresent(account -> {
        account.getScoreList().forEach(score -> {
            System.out.println("课程名称："+score.getSubject().getName());
            System.out.println("得分："+score.getScore());
            System.out.println("任课教师："+score.getSubject().getTeacher().getName());
        });
    });
}
```

成功得到多对一的教师信息。

最后我们再来看最复杂的情况，现在我们一门课程可以由多个老师教授，而一个老师也可以教授多个课程，那么这种情况就是很明显的多对多场景，现在又该如何定义呢？我们可以像之前一样，插入一张中间表表示教授关系，这个表中专门存储哪个老师教哪个科目：

```java
@ManyToMany(fetch = FetchType.LAZY)   //多对多场景
@JoinTable(name = "teach_relation",     //多对多中间关联表
        joinColumns = @JoinColumn(name = "cid"),    //当前实体主键在关联表中的字段名称
        inverseJoinColumns = @JoinColumn(name = "tid")   //教师实体主键在关联表中的字段名称
)
List<Teacher> teacher;
```

接着，JPA会自动创建一张中间表，并自动设置外键，我们就可以将多对多关联信息编写在其中了。

### JPQL自定义SQL语句

虽然SpringDataJPA能够简化大部分数据获取场景，但是难免会有一些特殊的场景，需要使用复杂查询才能够去完成，这时你又会发现，如果要实现，只能用回Mybatis了，因为我们需要自己手动编写SQL语句，过度依赖SpringDataJPA会使得SQL语句不可控。

使用JPA，我们也可以像Mybatis那样，直接编写SQL语句，不过它是JPQL语言，与原生SQL语句很类似，但是它是面向对象的，当然我们也可以编写原生SQL语句。

比如我们要更新用户表中指定ID用户的密码：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Transactional    //DML操作需要事务环境，可以不在这里声明，但是调用时一定要处于事务环境下
    @Modifying     //表示这是一个DML操作
    @Query("update Account set password = ?2 where id = ?1") //这里操作的是一个实体类对应的表，参数使用?代表，后面接第n个参数
    int updatePasswordById(int id, String newPassword);
}
```

```java
@Test
void updateAccount(){
    repository.updatePasswordById(1, "654321");
}
```

现在我想使用原生SQL来实现根据用户名称修改密码：

```java
@Transactional
@Modifying
@Query(value = "update users set password = :pwd where username = :name", nativeQuery = true) //使用原生SQL，和Mybatis一样，这里使用 :名称 表示参数，当然也可以继续用上面那种方式。
int updatePasswordByUsername(@Param("name") String username,   //我们可以使用@Param指定名称
                             @Param("pwd") String newPassword);
```

```java
@Test
void updateAccount(){
    repository.updatePasswordByUsername("Admin", "654321");
}
```

通过编写原生SQL，在一定程度上弥补了SQL不可控的问题。

虽然JPA能够为我们带来非常便捷的开发体验，但是正式因为太便捷了，保姆级的体验有时也会适得其反，可能项目开发到后期特别庞大时，就只能从底层SQL语句开始进行优化，而由于JPA尽可能地在屏蔽我们对SQL语句的编写，所以后期优化是个大问题，并且Hibernate相对于Mybatis来说，更加重量级。不过，在微服务的时代，单体项目一般不会太大，而JPA的劣势并没有太明显地体现出来。

有关Mybatis和JPA的对比，可以参考：https://blog.csdn.net/u010253246/article/details/105731204

***

## Extra. 前后端分离跨域处理

我们的项目已经处于前后端分离状态了，那么前后端分离状态和我们之前的状态有什么区别的呢？

* **不分离：**前端页面看到的都是由后端控制，由后端渲染页面或重定向，后端需要控制前端的展示，前端与后端的耦合度很高。比如我们之前都是使用后端来执行重定向操作或是使用Thymeleaf来填充数据，而最终返回的是整个渲染好的页。

![img](https://img2018.cnblogs.com/blog/1394466/201809/1394466-20180916231510365-285933655.png)

* **分离：**后端仅返回前端所需的数据，不再渲染HTML页面，不再控制前端的效果。至于前端用户看到什么效果，从后端请求的数据如何加载到前端中，都由前端通过JS等进行动态数据填充和渲染。这样后端只返回JSON数据，前端处理JSON数据并展示，这样前后端的职责就非常明确了。

![img](https://img2018.cnblogs.com/blog/1394466/201809/1394466-20180916231716242-1862208927.png)

实现前后端分离有两种方案，一种是直接放入SpringBoot的资源文件夹下，但是这样实际上还是在依靠SpringBoot内嵌的Tomcat服务器进行页面和静态资源的发送，我们现在就是这种方案。

另一种方案就是直接将所有的页面和静态资源单独放到代理服务器上（如Nginx），这样我们后端服务器就不必再处理静态资源和页面了，专心返回数据即可，而前端页面就需要访问另一个服务器来获取，虽然逻辑和明确，但是这样会出现跨域问题，实际上就是我们之前所说的跨站请求伪造，为了防止这种不安全的行为发生，所以对异步请求会进行一定的限制。

这里，我们将前端页面和后端页面直接分离进行测试，在登陆时得到如下错误：

```
Access to XMLHttpRequest at 'http://localhost:8080/api/auth/login' from origin 'http://localhost:63342' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

可以很清楚地看到，在Ajax发送异步请求时，我们的请求被阻止，原因是在响应头中没有包含`Access-Control-Allow-Origin`，也就表示，如果服务端允许跨域请求，那么会在响应头中添加一个`Access-Control-Allow-Origin`字段，如果不允许跨域，就像现在这样。那么，什么才算是跨域呢：

1. 请求协议`如http、https`不同
2. 请求的地址/域名不同
3. 端口不同

因为我们现在相当于前端页面访问的是静态资源服务器，而后端数据是由我们的SpringBoot项目提供，它们是两个不同的服务器，所以在垮服务器请求资源时，会被判断为存在安全风险。

但是现在，由于我们前后端是分离状态，我们希望的是能够实现跨域请求，这时我们就需要添加一个过滤器来处理跨域问题：

```java
@Bean
public CorsFilter corsFilter() {
    //创建CorsConfiguration对象后添加配置
    CorsConfiguration config = new CorsConfiguration();
    //设置放行哪些原始域，这里直接设置为所有
    config.addAllowedOriginPattern("*");
  	//你可以单独设置放行哪些原始域 config.addAllowedOrigin("http://localhost:2222");
    //放行哪些原始请求头部信息
    config.addAllowedHeader("*");
    //放行哪些请求方式，*代表所有
    config.addAllowedMethod("*");
    //是否允许发送Cookie，必须要开启，因为我们的JSESSIONID需要在Cookie中携带
    config.setAllowCredentials(true);
    //映射路径
    UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
    corsConfigurationSource.registerCorsConfiguration("/**", config);
    //返回CorsFilter
    return new CorsFilter(corsConfigurationSource);
}
```

这样，我们的SpringBoot项目就支持跨域访问了，接着我们再来尝试进行登陆，可以发现已经能够正常访问了，并且响应头中包含了以下信息：

```
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Access-Control-Allow-Origin: http://localhost:63342
Access-Control-Expose-Headers: *
Access-Control-Allow-Credentials: true
```

可以看到我们当前访问的原始域已经被放行了。

但是还有一个问题，我们的Ajax请求中没有携带Cookie信息（这个按理说属于前端知识了）这里我们稍微改一下，不然我们的请求无法确认身份：

```js
function get(url, success){
    $.ajax({
        type: "get",
        url: url,
        async: true,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}

function post(url, data, success){
    $.ajax({
        type: "post",
        url: url,
        async: true,
        data: data,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}
```

添加两个封装好的方法，并且将`withCredentials`开启，这样在发送异步请求时，就会携带Cookie信息了。

 在学习完成Linux之后，我们会讲解如何在Linux服务器上部署Nginx反向代理服务器。

***

## 接口管理：Swagger

在前后端分离项目中，前端人员需要知道我们后端会提供什么数据，根据后端提供的数据来进行前端页面渲染（在之前我们也演示过）这个时候，我们就需要编写一个API文档，以便前端人员随时查阅。

但是这样的一个文档，我们也不可能单独写一个项目去进行维护，并且随着我们的后端项目不断更新，文档也需要跟随更新，这显然是很麻烦的一件事情，那么有没有一种比较好的解决方案呢？

当然有，那就是丝袜哥：Swagger

### 走进Swagger

Swagger的主要功能如下：

- 支持 API 自动生成同步的在线文档：使用 Swagger 后可以直接通过代码生成文档，不再需要自己手动编写接口文档了，对程序员来说非常方便，可以节约写文档的时间去学习新技术。
- 提供 Web 页面在线测试 API：光有文档还不够，Swagger 生成的文档还支持在线测试。参数和格式都定好了，直接在界面上输入参数对应的值即可在线测试接口。

结合Spring框架（Spring-fox），Swagger可以很轻松地利用注解以及扫描机制，来快速生成在线文档，以实现当我们项目启动之后，前端开发人员就可以打开Swagger提供的前端页面，查看和测试接口。依赖如下：

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

SpringBoot 2.6以上版本修改了路径匹配规则，但是Swagger3还不支持，这里换回之前的，不然启动直接报错：

```yaml
spring:
	mvc:
		pathmatch:
      matching-strategy: ant_path_matcher
```

项目启动后，我们可以直接打开：http://localhost:8080/swagger-ui/index.html，这个页面（要是觉得丑，UI是可以换的，支持第三方）会显示所有的API文档，包括接口的路径、支持的方法、接口的描述等，并且我们可以直接对API接口进行测试，非常方便。

我们可以创建一个配置类去配置页面的相关信息：

```java
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30).apiInfo(
                new ApiInfoBuilder()
                        .contact(new Contact("你的名字", "https://www.bilibili.com", "javastudy111*@163.com"))
                        .title("图书管理系统 - 在线API接口文档")
                        .build()
        );
    }
}
```

### 接口信息配置

虽然Swagger的UI界面已经可以很好地展示后端提供的接口信息了，但是非常的混乱，我们来看看如何配置接口的一些描述信息。

首先我们的页面中完全不需要显示ErrorController相关的API，所以我们配置一下选择哪些Controller才会生成API信息：

```java
@Bean
public Docket docket() {
    ApiInfo info = new ApiInfoBuilder()
            .contact(new Contact("你的名字", "https://www.bilibili.com", "javastudy111@163.com"))
            .title("图书管理系统 - 在线API接口文档")
            .description("这是一个图书管理系统的后端API文档，欢迎前端人员查阅！")
            .build();
    return new Docket(DocumentationType.OAS_30)
            .apiInfo(info)
            .select()       //对项目中的所有API接口进行选择
            .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
            .build();
}
```

接着我们来看看如何为一个Controller编写API描述信息：

```java
@Api(tags = "账户验证接口", description = "包括用户登录、注册、验证码请求等操作。")
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
```

我们可以直接在类名称上面添加`@Api`注解，并填写相关信息，来为当前的Controller设置描述信息。

接着我们可以为所有的请求映射配置描述信息：

```java
@ApiResponses({
        @ApiResponse(code = 200, message = "邮件发送成功"),  
        @ApiResponse(code = 500, message = "邮件发送失败")   //不同返回状态码描述
})
@ApiOperation("请求邮件验证码")   //接口描述
@GetMapping("/verify-code")
public RestBean<Void> verifyCode(@ApiParam("邮箱地址")   //请求参数的描述
                                 @RequestParam("email") String email){
```

```java
@ApiIgnore     //忽略此请求映射
@PostMapping("/login-success")
public RestBean<Void> loginSuccess(){
    return new RestBean<>(200, "登陆成功");
}
```

我们也可以为实体类配置相关的描述信息：

```java
@Data
@ApiModel(description = "响应实体封装类")
@AllArgsConstructor
public class RestBean<T> {

    @ApiModelProperty("状态码")
    int code;
    @ApiModelProperty("状态码描述")
    String reason;
    @ApiModelProperty("数据实体")
    T data;

    public RestBean(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
```

这样，我们就可以在文档中查看实体类简介以及各个属性的介绍了。

最后我们再配置一下多环境：

```xml
<profiles>
    <profile>
        <id>dev</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <environment>dev</environment>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <activation>
            <activeByDefault>false</activeByDefault>
        </activation>
        <properties>
            <environment>prod</environment>
        </properties>
    </profile>
</profiles>
```

```xml
<resources>
    <resource>
        <directory>src/main/resources</directory>
        <excludes>
            <exclude>application*.yaml</exclude>
        </excludes>
    </resource>
    <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
        <includes>
            <include>application.yaml</include>
            <include>application-${environment}.yaml</include>
        </includes>
    </resource>
</resources>
```

首先在Maven中添加两个环境，接着我们配置一下不同环境的配置文件：

```yaml
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: update
springfox:
  documentation:
    enabled: false
```

在生产环境下，我们选择不开启Swagger文档以及JPA的数据库操作日志，这样我们就可以根据情况选择两套环境了。





# SpringBoot其他框架

通过了解其他的SpringBoot框架，我们就可以在我们自己的Web服务器上实现更多更高级的功能。

## 邮件发送：Mail

我们在注册很多的网站时，都会遇到邮件或是手机号验证，也就是通过你的邮箱或是手机短信去接受网站发给你的注册验证信息，填写验证码之后，就可以完成注册了，同时，网站也会绑定你的手机号或是邮箱。

那么，像这样的功能，我们如何实现呢？SpringBoot已经给我们提供了封装好的邮件模块使用：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

### 邮件发送

在学习邮件发送之前，我们需要先了解一下什么是电子邮件。

电子邮件也是一种通信方式，是互联网应用最广的服务。通过网络的电子邮件系统，用户可以以非常低廉的价格（不管发送到哪里，都只需负担网费，实际上就是把信息发送到对方服务器而已）、非常快速的方式，与世界上任何一个地方的电子邮箱用户联系。

虽说方便倒是方便，虽然是曾经的霸主，不过现在这个时代，QQ微信横行，手机短信和电子邮箱貌似就只剩收验证码这一个功能了。

要在Internet上提供电子邮件功能，必须有专门的电子邮件服务器。例如现在Internet很多提供邮件服务的厂商：新浪、搜狐、163、QQ邮箱等，他们都有自己的邮件服务器。这些服务器类似于现实生活中的邮局，它主要负责接收用户投递过来的邮件，并把邮件投递到邮件接收者的电子邮箱中。

所有的用户都可以在电子邮件服务器上申请一个账号用于邮件发送和接收，那么邮件是以什么样的格式发送的呢？实际上和Http一样，邮件发送也有自己的协议，也就是约定邮件数据长啥样以及如何通信。

![img](https://images2015.cnblogs.com/blog/851491/201612/851491-20161202143243756-1715308358.png)

比较常用的协议有两种：

1. SMTP协议（主要用于发送邮件 Simple Mail Transfer Protocol）
2. POP3协议（主要用于接收邮件 Post Office Protocol 3）

整个发送/接收流程大致如下：

![img](https://img2.baidu.com/it/u=3675146129,445744702&fm=253&fmt=auto&app=138&f=JPG?w=812&h=309)

实际上每个邮箱服务器都有一个smtp发送服务器和pop3接收服务器，比如要从QQ邮箱发送邮件到163邮箱，那么我们只需要通过QQ邮箱客户端告知QQ邮箱的smtp服务器我们需要发送邮件，以及邮件的相关信息，然后QQ邮箱的smtp服务器就会帮助我们发送到163邮箱的pop3服务器上，163邮箱会通过163邮箱客户端告知对应用户收到一封新邮件。

而我们如果想要实现给别人发送邮件，那么就需要连接到对应电子邮箱的smtp服务器上，并告知其我们要发送邮件。而SpringBoot已经帮助我们将最基本的底层通信全部实现了，我们只需要关心smtp服务器的地址以及我们要发送的邮件长啥样即可。

这里以163邮箱 https://mail.163.com 为例，我们需要在配置文件中告诉SpringBootMail我们的smtp服务器的地址以及你的邮箱账号和密码，首先我们要去设置中开启smtp/pop3服务才可以，开启后会得到一个随机生成的密钥，这个就是我们的密码。

```yaml
spring:
  mail:
  	# 163邮箱的地址为smtp.163.com，直接填写即可
    host: smtp.163.com
    # 你申请的163邮箱
    username: javastudy111@163.com
    # 注意密码是在开启smtp/pop3时自动生成的，记得保存一下，不然就找不到了
    password: AZJTOAWZESLMHTNI
```

配置完成后，接着我们来进行一下测试：

```java
@SpringBootTest
class SpringBootTestApplicationTests {

  	//JavaMailSender是专门用于发送邮件的对象，自动配置类已经提供了Bean
    @Autowired
    JavaMailSender sender;

    @Test
    void contextLoads() {
      	//SimpleMailMessage是一个比较简易的邮件封装，支持设置一些比较简单内容
        SimpleMailMessage message = new SimpleMailMessage();
      	//设置邮件标题
        message.setSubject("【电子科技大学教务处】关于近期学校对您的处分决定");
      	//设置邮件内容
        message.setText("XXX同学您好，经监控和教务巡查发现，您近期存在旷课、迟到、早退、上课刷抖音行为，" +
                "现已通知相关辅导员，请手写5000字书面检讨，并在2022年4月1日17点前交到辅导员办公室。");
      	//设置邮件发送给谁，可以多个，这里就发给你的QQ邮箱
        message.setTo("你的QQ号@qq.com");
      	//邮件发送者，这里要与配置文件中的保持一致
        message.setFrom("javastudy111@163.com");
      	//OK，万事俱备只欠发送
        sender.send(message);
    }

}
```

如果需要添加附件等更多功能，可以使用MimeMessageHelper来帮助我们完成：

```java
@Test
void contextLoads() throws MessagingException {
  	//创建一个MimeMessage
    MimeMessage message = sender.createMimeMessage();
  	//使用MimeMessageHelper来帮我们修改MimeMessage中的信息
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setSubject("Test");
    helper.setText("lbwnb");
    helper.setTo("你的QQ号@qq.com");
    helper.setFrom("javastudy111@163.com");
  	//发送修改好的MimeMessage
    sender.send(message);
}
```

### 邮件注册

既然我们已经了解了邮件发送，那么我们接着来看如何在我们的项目中实现邮件验证。

首先明确验证流程：请求验证码 -> 生成验证码（临时有效，注意设定过期时间） -> 用户输入验证码并填写注册信息 -> 验证通过注册成功！

***

![点击查看图片来源](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2Fv2-6f0b9bb234b2534ec295ff195bad183a_1440w.jpg%3Fsource%3D172ae18b&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645108924&t=d40aa5dc6be398725b4ff21ef5895454)

## 持久层框架：JPA

* 用了Mybatis之后，你看那个JDBC，真是太逊了。
* 这么说，你的项目很勇哦？
* 开玩笑，我的写代码超勇的好不好。
* 阿伟，你可曾幻想过有一天你的项目里不再有SQL语句？
* 不再有SQL语句？那我怎么和数据库交互啊？
* 我看你是完全不懂哦
* 懂，懂什么啊？
* 你想懂？来，到我项目里来，我给你看点好康的。
* 好康？是什么新框架哦？
* 什么新框架，比新框架还刺激，还可以让你的项目登duang郎哦。
* 哇，杰哥，你项目里面都没SQL语句诶，这是用的什么框架啊？

在我们之前编写的项目中，我们不难发现，实际上大部分的数据库交互操作，到最后都只会做一个事情，那就是把数据库中的数据映射为Java中的对象。比如我们要通过用户名去查找对应的用户，或是通过ID查找对应的学生信息，在使用Mybatis时，我们只需要编写正确的SQL语句就可以直接将获取的数据映射为对应的Java对象，通过调用Mapper中的方法就能直接获得实体类，这样就方便我们在Java中数据库表中的相关信息了。

但是以上这些操作都有一个共性，那就是它们都是通过某种条件去进行查询，而最后的查询结果，都是一个实体类，所以你会发现你写的很多SQL语句都是一个套路`select * from xxx where xxx=xxx`，那么能否有一种框架，帮我们把这些相同的套路给封装起来，直接把这类相似的SQL语句给屏蔽掉，不再由我们编写，而是让框架自己去组合拼接。

### 认识SpringDataJPA

首先我们来看一个国外的统计：

![image-20220119140326867](/Users/nagocoler/Library/Application Support/typora-user-images/image-20220119140326867.png)

不对吧，为什么Mybatis这么好用，这么强大，却只有10%的人喜欢呢？然而事实就是，在国外JPA几乎占据了主导地位，而Mybatis并不像国内那样受待见，所以你会发现，JPA都有SpringBoot的官方直接提供的starter，而Mybatis没有。

至于为啥SSM阶段不讲这个，而是放到现在来讲也是因为，在微服务场景下它的优势才能更多的发挥出来。

那么，什么是JPA？

JPA（Java Persistence API）和JDBC类似，也是官方定义的一组接口，但是它相比传统的JDBC，它是为了实现ORM而生的，即Object-Relationl Mapping，它的作用是在关系型数据库和对象之间形成一个映射，这样，我们在具体的操作数据库的时候，就不需要再去和复杂的SQL语句打交道，只要像平时操作对象一样操作它就可以了。

在之前，我们使用JDBC或是Mybatis来操作数据，通过直接编写对应的SQL语句来实现数据访问，但是我们发现实际上我们在Java中大部分操作数据库的情况都是读取数据并封装为一个实体类，因此，为什么不直接将实体类直接对应到一个数据库表呢？也就是说，一张表里面有什么属性，那么我们的对象就有什么属性，所有属性跟数据库里面的字段一一对应，而读取数据时，只需要读取一行的数据并封装为我们定义好的实体类既可以，而具体的SQL语句执行，完全可以交给框架根据我们定义的映射关系去生成，不再由我们去编写，因为这些SQL实际上都是千篇一律的。

而实现JPA规范的框架一般最常用的就是`Hibernate`，它是一个重量级框架，学习难度相比Mybatis也更高一些，而SpringDataJPA也是采用Hibernate框架作为底层实现，并对其加以封装。

官网：https://spring.io/projects/spring-data-jpa

### 使用JPA

同样的，我们只需要导入stater依赖即可：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

接着我们可以直接创建一个类，比如账户类，我们只需要把一个账号对应的属性全部定义好即可：

```java
@Data
public class Account {
    int id;
    String username;
    String password;
}
```

接着，我们可以通过注解形式，在属性上添加数据库映射关系，这样就能够让JPA知道我们的实体类对应的数据库表长啥样。

```java
@Data
@Entity   //表示这个类是一个实体类
@Table(name = "users")    //对应的数据库中表名称
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)   //生成策略，这里配置为自增
    @Column(name = "id")    //对应表中id这一列
    @Id     //此属性为主键
    int id;

    @Column(name = "username")   //对应表中username这一列
    String username;

    @Column(name = "password")   //对应表中password这一列
    String password;
}
```

接着我们来修改一下配置文件：

```yaml
spring:
  jpa:
		#开启SQL语句执行日志信息
    show-sql: true
    hibernate:
    	#配置为自动创建
      ddl-auto: create
```

`ddl-auto`属性用于设置自动表定义，可以实现自动在数据库中为我们创建一个表，表的结构会根据我们定义的实体类决定，它有4种

* create 启动时删数据库中的表，然后创建，退出时不删除数据表 
* create-drop 启动时删数据库中的表，然后创建，退出时删除数据表 如果表不存在报错 
* update 如果启动时表格式不一致则更新表，原有数据保留 
* validate 项目启动表结构进行校验 如果不一致则报错

我们可以在日志中发现，在启动时执行了如下SQL语句：

```
Hibernate: create table users (id integer not null auto_increment, password varchar(255), username varchar(255), primary key (id)) engine=InnoDB
```

而我们的数据库中对应的表已经创建好了。

我们接着来看如何访问我们的表，我们需要创建一个Repository实现类：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    
}
```

注意JpaRepository有两个泛型，前者是具体操作的对象实体，也就是对应的表，后者是ID的类型，接口中已经定义了比较常用的数据库操作。编写接口继承即可，我们可以直接注入此接口获得实现类：

```java
@SpringBootTest
class JpaTestApplicationTests {

    @Resource
    AccountRepository repository;

    @Test
    void contextLoads() {
      	//直接根据ID查找
        repository.findById(1).ifPresent(System.out::println);
    }

}
```

运行后，成功得到查询结果。我们接着来测试增删操作：

```java
@Test
void addAccount(){
    Account account = new Account();
    account.setUsername("Admin");
    account.setPassword("123456");
    account = repository.save(account);  //返回的结果会包含自动生成的主键值
    System.out.println("插入时，自动生成的主键ID为："+account.getId());
}
```

```java
@Test
void deleteAccount(){
    repository.deleteById(2);   //根据ID删除对应记录
}
```

```java
@Test
void pageAccount() {
    repository.findAll(PageRequest.of(0, 1)).forEach(System.out::println);  //直接分页
}
```

我们发现，使用了JPA之后，整个项目的代码中没有出现任何的SQL语句，可以说是非常方便了，JPA依靠我们提供的注解信息自动完成了所有信息的映射和关联。

相比Mybatis，JPA几乎就是一个全自动的ORM框架，而Mybatis则顶多算是半自动ORM框架。

### 方法名称拼接自定义SQL

虽然接口预置的方法使用起来非常方便，但是如果我们需要进行条件查询等操作或是一些判断，就需要自定义一些方法来实现，同样的，我们不需要编写SQL语句，而是通过方法名称的拼接来实现条件判断，这里列出了所有支持的条件判断名称：

| `Distinct`             | `findDistinctByLastnameAndFirstname`                         | `select distinct … where x.lastname = ?1 and x.firstname = ?2` |
| ---------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `And`                  | `findByLastnameAndFirstname`                                 | `… where x.lastname = ?1 and x.firstname = ?2`               |
| `Or`                   | `findByLastnameOrFirstname`                                  | `… where x.lastname = ?1 or x.firstname = ?2`                |
| `Is`，`Equals`         | `findByFirstname`,`findByFirstnameIs`,`findByFirstnameEquals` | `… where x.firstname = ?1`                                   |
| `Between`              | `findByStartDateBetween`                                     | `… where x.startDate between ?1 and ?2`                      |
| `LessThan`             | `findByAgeLessThan`                                          | `… where x.age < ?1`                                         |
| `LessThanEqual`        | `findByAgeLessThanEqual`                                     | `… where x.age <= ?1`                                        |
| `GreaterThan`          | `findByAgeGreaterThan`                                       | `… where x.age > ?1`                                         |
| `GreaterThanEqual`     | `findByAgeGreaterThanEqual`                                  | `… where x.age >= ?1`                                        |
| `After`                | `findByStartDateAfter`                                       | `… where x.startDate > ?1`                                   |
| `Before`               | `findByStartDateBefore`                                      | `… where x.startDate < ?1`                                   |
| `IsNull`，`Null`       | `findByAge(Is)Null`                                          | `… where x.age is null`                                      |
| `IsNotNull`，`NotNull` | `findByAge(Is)NotNull`                                       | `… where x.age not null`                                     |
| `Like`                 | `findByFirstnameLike`                                        | `… where x.firstname like ?1`                                |
| `NotLike`              | `findByFirstnameNotLike`                                     | `… where x.firstname not like ?1`                            |
| `StartingWith`         | `findByFirstnameStartingWith`                                | `… where x.firstname like ?1`（参数与附加`%`绑定）           |
| `EndingWith`           | `findByFirstnameEndingWith`                                  | `… where x.firstname like ?1`（参数与前缀`%`绑定）           |
| `Containing`           | `findByFirstnameContaining`                                  | `… where x.firstname like ?1`（参数绑定以`%`包装）           |
| `OrderBy`              | `findByAgeOrderByLastnameDesc`                               | `… where x.age = ?1 order by x.lastname desc`                |
| `Not`                  | `findByLastnameNot`                                          | `… where x.lastname <> ?1`                                   |
| `In`                   | `findByAgeIn(Collection<Age> ages)`                          | `… where x.age in ?1`                                        |
| `NotIn`                | `findByAgeNotIn(Collection<Age> ages)`                       | `… where x.age not in ?1`                                    |
| `True`                 | `findByActiveTrue()`                                         | `… where x.active = true`                                    |
| `False`                | `findByActiveFalse()`                                        | `… where x.active = false`                                   |
| `IgnoreCase`           | `findByFirstnameIgnoreCase`                                  | `… where UPPER(x.firstname) = UPPER(?1)`                     |

比如我们想要实现根据用户名模糊匹配查找用户：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
		//按照表中的规则进行名称拼接，不用刻意去记，IDEA会有提示
    List<Account> findAllByUsernameLike(String str);
}
```

```java
@Test
void test() {
    repository.findAllByUsernameLike("%T%").forEach(System.out::println);
}
```

又比如我们想同时根据用户名和ID一起查询：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByIdAndUsername(int id, String username);
  //可以使用Optional类进行包装，Optional<Account> findByIdAndUsername(int id, String username);
    
    List<Account> findAllByUsernameLike(String str);
}
```

```java
@Test
void test() {
    System.out.println(repository.findByIdAndUsername(1, "Test"));
}
```

比如我们想判断数据库中是否存在某个ID的用户：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByIdAndUsername(int id, String username);

    List<Account> findAllByUsernameLike(String str);

    boolean existsAccountById(int id);
}
```

```java
@Test
void test() {
    System.out.println(repository.existsAccountByUsername("Test"));
}
```

注意自定义条件操作的方法名称一定要遵循规则，不然会出现异常：

```
Caused by: org.springframework.data.repository.query.QueryCreationException: Could not create query for public abstract  ...
```

### 关联查询

在实际开发中，比较常见的场景还有关联查询，也就是我们会在表中添加一个外键字段，而此外键字段又指向了另一个表中的数据，当我们查询数据时，可能会需要将关联数据也一并获取，比如我们想要查询某个用户的详细信息，一般用户简略信息会单独存放一个表，而用户详细信息会单独存放在另一个表中。当然，除了用户详细信息之外，可能在某些电商平台还会有用户的购买记录、用户的购物车，交流社区中的用户帖子、用户评论等，这些都是需要根据用户信息进行关联查询的内容。

![img](https://img1.baidu.com/it/u=292198351,4011695440&fm=253&fmt=auto&app=138&f=JPEG?w=404&h=436)

我们知道，在JPA中，每张表实际上就是一个实体类的映射，而表之间的关联关系，也可以看作对象之间的依赖关系，比如用户表中包含了用户详细信息的ID字段作为外键，那么实际上就是用户表实体中包括了用户详细信息实体对象：

```java
@Data
@Entity
@Table(name = "users_detail")
public class AccountDetail {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @Column(name = "address")
    String address;

    @Column(name = "email")
    String email;

    @Column(name = "phone")
    String phone;

    @Column(name = "real_name")
    String realName;
}
```

而用户信息和用户详细信息之间形成了一对一的关系，那么这时我们就可以直接在类中指定这种关系：

```java
@Data
@Entity
@Table(name = "users")
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    int id;

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @JoinColumn(name = "detail_id")   //指定存储外键的字段名称
    @OneToOne    //声明为一对一关系
    AccountDetail detail;
}
```

在修改实体类信息后，我们发现在启动时也进行了更新，日志如下：

```
Hibernate: alter table users add column detail_id integer
Hibernate: create table users_detail (id integer not null auto_increment, address varchar(255), email varchar(255), phone varchar(255), real_name varchar(255), primary key (id)) engine=InnoDB
Hibernate: alter table users add constraint FK7gb021edkxf3mdv5bs75ni6jd foreign key (detail_id) references users_detail (id)
```

是不是感觉非常方便！都懒得去手动改表结构了。

接着我们往用户详细信息中添加一些数据，一会我们可以直接进行查询：

```java
@Test
void pageAccount() {
    repository.findById(1).ifPresent(System.out::println);
}
```

查询后，可以发现，得到如下结果：

```
Hibernate: select account0_.id as id1_0_0_, account0_.detail_id as detail_i4_0_0_, account0_.password as password2_0_0_, account0_.username as username3_0_0_, accountdet1_.id as id1_1_1_, accountdet1_.address as address2_1_1_, accountdet1_.email as email3_1_1_, accountdet1_.phone as phone4_1_1_, accountdet1_.real_name as real_nam5_1_1_ from users account0_ left outer join users_detail accountdet1_ on account0_.detail_id=accountdet1_.id where account0_.id=?
Account(id=1, username=Test, password=123456, detail=AccountDetail(id=1, address=四川省成都市青羊区, email=8371289@qq.com, phone=1234567890, realName=本伟))
```

也就是，在建立关系之后，我们查询Account对象时，会自动将关联数据的结果也一并进行查询。

那要是我们只想要Account的数据，不想要用户详细信息数据怎么办呢？我希望在我要用的时候再获取详细信息，这样可以节省一些网络开销，我们可以设置懒加载，这样只有在需要时才会向数据库获取：

```java
@JoinColumn(name = "detail_id")
@OneToOne(fetch = FetchType.LAZY)    //将获取类型改为LAZY
AccountDetail detail;
```

接着我们测试一下：

```java
@Transactional   //懒加载属性需要在事务环境下获取，因为repository方法调用完后Session会立即关闭
@Test
void pageAccount() {
    repository.findById(1).ifPresent(account -> {
        System.out.println(account.getUsername());   //获取用户名
        System.out.println(account.getDetail());  //获取详细信息（懒加载）
    });
}
```

接着我们来看看控制台输出了什么：

```
Hibernate: select account0_.id as id1_0_0_, account0_.detail_id as detail_i4_0_0_, account0_.password as password2_0_0_, account0_.username as username3_0_0_ from users account0_ where account0_.id=?
Test
Hibernate: select accountdet0_.id as id1_1_0_, accountdet0_.address as address2_1_0_, accountdet0_.email as email3_1_0_, accountdet0_.phone as phone4_1_0_, accountdet0_.real_name as real_nam5_1_0_ from users_detail accountdet0_ where accountdet0_.id=?
AccountDetail(id=1, address=四川省成都市青羊区, email=8371289@qq.com, phone=1234567890, realName=卢本)
```

可以看到，获取用户名之前，并没有去查询用户的详细信息，而是当我们获取详细信息时才进行查询并返回AccountDetail对象。

那么我们是否也可以在添加数据时，利用实体类之间的关联信息，一次性添加两张表的数据呢？可以，但是我们需要稍微修改一下级联关联操作设定：

```java
@JoinColumn(name = "detail_id")
@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //设置关联操作为ALL
AccountDetail detail;
```

* ALL：所有操作都进行关联操作
* PERSIST：插入操作时才进行关联操作
* REMOVE：删除操作时才进行关联操作
* MERGE：修改操作时才进行关联操作

可以多个并存，接着我们来进行一下测试：

```java
@Test
void addAccount(){
    Account account = new Account();
    account.setUsername("Nike");
    account.setPassword("123456");
    AccountDetail detail = new AccountDetail();
    detail.setAddress("重庆市渝中区解放碑");
    detail.setPhone("1234567890");
    detail.setEmail("73281937@qq.com");
    detail.setRealName("张三");
  	account.setDetail(detail);
    account = repository.save(account);
    System.out.println("插入时，自动生成的主键ID为："+account.getId()+"，外键ID为："+account.getDetail().getId());
}
```

可以看到日志结果：

```
Hibernate: insert into users_detail (address, email, phone, real_name) values (?, ?, ?, ?)
Hibernate: insert into users (detail_id, password, username) values (?, ?, ?)
插入时，自动生成的主键ID为：6，外键ID为：3
```

结束后会发现数据库中两张表都同时存在数据。

接着我们来看一对多关联，比如每个用户的成绩信息：

```java
@JoinColumn(name = "uid")  //注意这里的name指的是Score表中的uid字段对应的就是当前的主键，会将uid外键设置为当前的主键
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)   //在移除Account时，一并移除所有的成绩信息，依然使用懒加载
List<Score> scoreList;
```

```java
@Data
@Entity
@Table(name = "users_score")   //成绩表，注意只存成绩，不存学科信息，学科信息id做外键
public class Score {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Id
    int id;

    @OneToOne   //一对一对应到学科上
    @JoinColumn(name = "cid")
    Subject subject;

    @Column(name = "socre")
    double score;

    @Column(name = "uid")
    int uid;
}
```

```java
@Data
@Entity
@Table(name = "subjects")   //学科信息表
public class Subject {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    @Id
    int cid;

    @Column(name = "name")
    String name;

    @Column(name = "teacher")
    String teacher;

    @Column(name = "time")
    int time;
}
```

在数据库中填写相应数据，接着我们就可以查询用户的成绩信息了：

```java
@Transactional
@Test
void test() {
    repository.findById(1).ifPresent(account -> {
        account.getScoreList().forEach(System.out::println);
    });
}
```

成功得到用户所有的成绩信息，包括得分和学科信息。

同样的，我们还可以将对应成绩中的教师信息单独分出一张表存储，并建立多对一的关系，因为多门课程可能由同一个老师教授（千万别搞晕了，一定要理清楚关联关系，同时也是考验你的基础扎不扎实）：

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "tid")   //存储教师ID的字段，和一对一是一样的，也会当前表中创个外键
Teacher teacher;
```

接着就是教师实体类了：

```java
@Data
@Entity
@Table(name = "teachers")
public class Teacher {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "sex")
    String sex;
}
```

最后我们再进行一下测试：

```java
@Transactional
@Test
void test() {
    repository.findById(3).ifPresent(account -> {
        account.getScoreList().forEach(score -> {
            System.out.println("课程名称："+score.getSubject().getName());
            System.out.println("得分："+score.getScore());
            System.out.println("任课教师："+score.getSubject().getTeacher().getName());
        });
    });
}
```

成功得到多对一的教师信息。

最后我们再来看最复杂的情况，现在我们一门课程可以由多个老师教授，而一个老师也可以教授多个课程，那么这种情况就是很明显的多对多场景，现在又该如何定义呢？我们可以像之前一样，插入一张中间表表示教授关系，这个表中专门存储哪个老师教哪个科目：

```java
@ManyToMany(fetch = FetchType.LAZY)   //多对多场景
@JoinTable(name = "teach_relation",     //多对多中间关联表
        joinColumns = @JoinColumn(name = "cid"),    //当前实体主键在关联表中的字段名称
        inverseJoinColumns = @JoinColumn(name = "tid")   //教师实体主键在关联表中的字段名称
)
List<Teacher> teacher;
```

接着，JPA会自动创建一张中间表，并自动设置外键，我们就可以将多对多关联信息编写在其中了。

### JPQL自定义SQL语句

虽然SpringDataJPA能够简化大部分数据获取场景，但是难免会有一些特殊的场景，需要使用复杂查询才能够去完成，这时你又会发现，如果要实现，只能用回Mybatis了，因为我们需要自己手动编写SQL语句，过度依赖SpringDataJPA会使得SQL语句不可控。

使用JPA，我们也可以像Mybatis那样，直接编写SQL语句，不过它是JPQL语言，与原生SQL语句很类似，但是它是面向对象的，当然我们也可以编写原生SQL语句。

比如我们要更新用户表中指定ID用户的密码：

```java
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Transactional    //DML操作需要事务环境，可以不在这里声明，但是调用时一定要处于事务环境下
    @Modifying     //表示这是一个DML操作
    @Query("update Account set password = ?2 where id = ?1") //这里操作的是一个实体类对应的表，参数使用?代表，后面接第n个参数
    int updatePasswordById(int id, String newPassword);
}
```

```java
@Test
void updateAccount(){
    repository.updatePasswordById(1, "654321");
}
```

现在我想使用原生SQL来实现根据用户名称修改密码：

```java
@Transactional
@Modifying
@Query(value = "update users set password = :pwd where username = :name", nativeQuery = true) //使用原生SQL，和Mybatis一样，这里使用 :名称 表示参数，当然也可以继续用上面那种方式。
int updatePasswordByUsername(@Param("name") String username,   //我们可以使用@Param指定名称
                             @Param("pwd") String newPassword);
```

```java
@Test
void updateAccount(){
    repository.updatePasswordByUsername("Admin", "654321");
}
```

通过编写原生SQL，在一定程度上弥补了SQL不可控的问题。

虽然JPA能够为我们带来非常便捷的开发体验，但是正式因为太便捷了，保姆级的体验有时也会适得其反，可能项目开发到后期特别庞大时，就只能从底层SQL语句开始进行优化，而由于JPA尽可能地在屏蔽我们对SQL语句的编写，所以后期优化是个大问题，并且Hibernate相对于Mybatis来说，更加重量级。不过，在微服务的时代，单体项目一般不会太大，而JPA的劣势并没有太明显地体现出来。

有关Mybatis和JPA的对比，可以参考：https://blog.csdn.net/u010253246/article/details/105731204

***

## Extra. 前后端分离跨域处理

我们的项目已经处于前后端分离状态了，那么前后端分离状态和我们之前的状态有什么区别的呢？

* **不分离：**前端页面看到的都是由后端控制，由后端渲染页面或重定向，后端需要控制前端的展示，前端与后端的耦合度很高。比如我们之前都是使用后端来执行重定向操作或是使用Thymeleaf来填充数据，而最终返回的是整个渲染好的页。

![img](https://img2018.cnblogs.com/blog/1394466/201809/1394466-20180916231510365-285933655.png)

* **分离：**后端仅返回前端所需的数据，不再渲染HTML页面，不再控制前端的效果。至于前端用户看到什么效果，从后端请求的数据如何加载到前端中，都由前端通过JS等进行动态数据填充和渲染。这样后端只返回JSON数据，前端处理JSON数据并展示，这样前后端的职责就非常明确了。

![img](https://img2018.cnblogs.com/blog/1394466/201809/1394466-20180916231716242-1862208927.png)

实现前后端分离有两种方案，一种是直接放入SpringBoot的资源文件夹下，但是这样实际上还是在依靠SpringBoot内嵌的Tomcat服务器进行页面和静态资源的发送，我们现在就是这种方案。

另一种方案就是直接将所有的页面和静态资源单独放到代理服务器上（如Nginx），这样我们后端服务器就不必再处理静态资源和页面了，专心返回数据即可，而前端页面就需要访问另一个服务器来获取，虽然逻辑和明确，但是这样会出现跨域问题，实际上就是我们之前所说的跨站请求伪造，为了防止这种不安全的行为发生，所以对异步请求会进行一定的限制。

这里，我们将前端页面和后端页面直接分离进行测试，在登陆时得到如下错误：

```
Access to XMLHttpRequest at 'http://localhost:8080/api/auth/login' from origin 'http://localhost:63342' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

可以很清楚地看到，在Ajax发送异步请求时，我们的请求被阻止，原因是在响应头中没有包含`Access-Control-Allow-Origin`，也就表示，如果服务端允许跨域请求，那么会在响应头中添加一个`Access-Control-Allow-Origin`字段，如果不允许跨域，就像现在这样。那么，什么才算是跨域呢：

1. 请求协议`如http、https`不同
2. 请求的地址/域名不同
3. 端口不同

因为我们现在相当于前端页面访问的是静态资源服务器，而后端数据是由我们的SpringBoot项目提供，它们是两个不同的服务器，所以在垮服务器请求资源时，会被判断为存在安全风险。

但是现在，由于我们前后端是分离状态，我们希望的是能够实现跨域请求，这时我们就需要添加一个过滤器来处理跨域问题：

```java
@Bean
public CorsFilter corsFilter() {
    //创建CorsConfiguration对象后添加配置
    CorsConfiguration config = new CorsConfiguration();
    //设置放行哪些原始域，这里直接设置为所有
    config.addAllowedOriginPattern("*");
  	//你可以单独设置放行哪些原始域 config.addAllowedOrigin("http://localhost:2222");
    //放行哪些原始请求头部信息
    config.addAllowedHeader("*");
    //放行哪些请求方式，*代表所有
    config.addAllowedMethod("*");
    //是否允许发送Cookie，必须要开启，因为我们的JSESSIONID需要在Cookie中携带
    config.setAllowCredentials(true);
    //映射路径
    UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
    corsConfigurationSource.registerCorsConfiguration("/**", config);
    //返回CorsFilter
    return new CorsFilter(corsConfigurationSource);
}
```

这样，我们的SpringBoot项目就支持跨域访问了，接着我们再来尝试进行登陆，可以发现已经能够正常访问了，并且响应头中包含了以下信息：

```
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Access-Control-Allow-Origin: http://localhost:63342
Access-Control-Expose-Headers: *
Access-Control-Allow-Credentials: true
```

可以看到我们当前访问的原始域已经被放行了。

但是还有一个问题，我们的Ajax请求中没有携带Cookie信息（这个按理说属于前端知识了）这里我们稍微改一下，不然我们的请求无法确认身份：

```js
function get(url, success){
    $.ajax({
        type: "get",
        url: url,
        async: true,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}

function post(url, data, success){
    $.ajax({
        type: "post",
        url: url,
        async: true,
        data: data,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}
```

添加两个封装好的方法，并且将`withCredentials`开启，这样在发送异步请求时，就会携带Cookie信息了。

 在学习完成Linux之后，我们会讲解如何在Linux服务器上部署Nginx反向代理服务器。

***

## 接口管理：Swagger

在前后端分离项目中，前端人员需要知道我们后端会提供什么数据，根据后端提供的数据来进行前端页面渲染（在之前我们也演示过）这个时候，我们就需要编写一个API文档，以便前端人员随时查阅。

但是这样的一个文档，我们也不可能单独写一个项目去进行维护，并且随着我们的后端项目不断更新，文档也需要跟随更新，这显然是很麻烦的一件事情，那么有没有一种比较好的解决方案呢？

当然有，那就是丝袜哥：Swagger

### 走进Swagger

Swagger的主要功能如下：

- 支持 API 自动生成同步的在线文档：使用 Swagger 后可以直接通过代码生成文档，不再需要自己手动编写接口文档了，对程序员来说非常方便，可以节约写文档的时间去学习新技术。
- 提供 Web 页面在线测试 API：光有文档还不够，Swagger 生成的文档还支持在线测试。参数和格式都定好了，直接在界面上输入参数对应的值即可在线测试接口。

结合Spring框架（Spring-fox），Swagger可以很轻松地利用注解以及扫描机制，来快速生成在线文档，以实现当我们项目启动之后，前端开发人员就可以打开Swagger提供的前端页面，查看和测试接口。依赖如下：

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

SpringBoot 2.6以上版本修改了路径匹配规则，但是Swagger3还不支持，这里换回之前的，不然启动直接报错：

```yaml
spring:
	mvc:
		pathmatch:
      matching-strategy: ant_path_matcher
```

项目启动后，我们可以直接打开：http://localhost:8080/swagger-ui/index.html，这个页面（要是觉得丑，UI是可以换的，支持第三方）会显示所有的API文档，包括接口的路径、支持的方法、接口的描述等，并且我们可以直接对API接口进行测试，非常方便。

我们可以创建一个配置类去配置页面的相关信息：

```java
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30).apiInfo(
                new ApiInfoBuilder()
                        .contact(new Contact("你的名字", "https://www.bilibili.com", "javastudy111*@163.com"))
                        .title("图书管理系统 - 在线API接口文档")
                        .build()
        );
    }
}
```

### 接口信息配置

虽然Swagger的UI界面已经可以很好地展示后端提供的接口信息了，但是非常的混乱，我们来看看如何配置接口的一些描述信息。

首先我们的页面中完全不需要显示ErrorController相关的API，所以我们配置一下选择哪些Controller才会生成API信息：

```java
@Bean
public Docket docket() {
    ApiInfo info = new ApiInfoBuilder()
            .contact(new Contact("你的名字", "https://www.bilibili.com", "javastudy111@163.com"))
            .title("图书管理系统 - 在线API接口文档")
            .description("这是一个图书管理系统的后端API文档，欢迎前端人员查阅！")
            .build();
    return new Docket(DocumentationType.OAS_30)
            .apiInfo(info)
            .select()       //对项目中的所有API接口进行选择
            .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
            .build();
}
```

接着我们来看看如何为一个Controller编写API描述信息：

```java
@Api(tags = "账户验证接口", description = "包括用户登录、注册、验证码请求等操作。")
@RestController
@RequestMapping("/api/auth")
public class AuthApiController {
```

我们可以直接在类名称上面添加`@Api`注解，并填写相关信息，来为当前的Controller设置描述信息。

接着我们可以为所有的请求映射配置描述信息：

```java
@ApiResponses({
        @ApiResponse(code = 200, message = "邮件发送成功"),  
        @ApiResponse(code = 500, message = "邮件发送失败")   //不同返回状态码描述
})
@ApiOperation("请求邮件验证码")   //接口描述
@GetMapping("/verify-code")
public RestBean<Void> verifyCode(@ApiParam("邮箱地址")   //请求参数的描述
                                 @RequestParam("email") String email){
```

```java
@ApiIgnore     //忽略此请求映射
@PostMapping("/login-success")
public RestBean<Void> loginSuccess(){
    return new RestBean<>(200, "登陆成功");
}
```

我们也可以为实体类配置相关的描述信息：

```java
@Data
@ApiModel(description = "响应实体封装类")
@AllArgsConstructor
public class RestBean<T> {

    @ApiModelProperty("状态码")
    int code;
    @ApiModelProperty("状态码描述")
    String reason;
    @ApiModelProperty("数据实体")
    T data;

    public RestBean(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
```

这样，我们就可以在文档中查看实体类简介以及各个属性的介绍了。

最后我们再配置一下多环境：

```xml
<profiles>
    <profile>
        <id>dev</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            <environment>dev</environment>
        </properties>
    </profile>
    <profile>
        <id>prod</id>
        <activation>
            <activeByDefault>false</activeByDefault>
        </activation>
        <properties>
            <environment>prod</environment>
        </properties>
    </profile>
</profiles>
```

```xml
<resources>
    <resource>
        <directory>src/main/resources</directory>
        <excludes>
            <exclude>application*.yaml</exclude>
        </excludes>
    </resource>
    <resource>
        <directory>src/main/resources</directory>
        <filtering>true</filtering>
        <includes>
            <include>application.yaml</include>
            <include>application-${environment}.yaml</include>
        </includes>
    </resource>
</resources>
```

首先在Maven中添加两个环境，接着我们配置一下不同环境的配置文件：

```yaml
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: update
springfox:
  documentation:
    enabled: false
```

在生产环境下，我们选择不开启Swagger文档以及JPA的数据库操作日志，这样我们就可以根据情况选择两套环境了。



![点击查看图片来源](https://gimg2.baidu.com/image_search/src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171116%2F4f98ec55839b434cba335319b5ebf963.jpeg&refer=http%3A%2F%2F5b0988e595225.cdn.sohucs.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645350734&t=099b0e4fb640617498cc40939e7e2070)

# Linux操作系统与项目部署

**注意：**本版块会涉及到`操作系统`相关知识。

现在，几乎所有智能设备都有一个自己的操作系统，比如我们的家用个人电脑，基本都是预装Windows操作系统，我们的手机也有Android和iOS操作系统，还有程序员比较青睐的MacBook，预装MacOS操作系统，甚至连Macbook的Touchbar都有一个自己的小型操作系统。

> 操作系统是管理计算机硬件与软件资源的计算机程序，操作系统可以对计算机系统的各项资源板块开展调度工作，运用计算机操作系统可以减少人工资源分配的工作强度。

在我们的电脑没有操作系统的情况下，它就是一堆电子元器件组合而成的机器，就像我们有了一具完整的身体，但是现在缺少的是一个大脑，来控制我们的身体做出各种动作和行为，而安装了操作系统，就像为电脑注入了灵魂，操作系统会帮助我们对所有的硬件进行调度和管理。

比如我们现在最常用的Windows操作系统，我们可以在系统中做各种各样的事情，包括游戏、看片、学习、编程等，而所有的程序正是基于操作系统之上运行的，操作系统帮助我们与底层硬件进行交互，而在程序中我们只需要告诉操作系统我们需要做什么就可以了，操作系统知道该如何使用和调度底层的硬件，来完成我们程序中指定的任务。

（如果你在自己电脑上安装过Windows操作系统，甚至自己打过驱动程序，或是使用安装过Linux任意发行版本，那么本章学习起来会比较轻松）

## 发展简史

这是整个SpringBoot阶段的最后部分了，为了不让学习那么枯燥，我们先来讲点小故事。

在1965年，当时还处于批处理操作系统的时代，但是它只能同时供一个用户使用，而当时人们正希望能够开发一种交互式的、具有多道程序处理能力的分时操作系统。于是，贝尔实验室、美国麻省理工学院和通用电气公司联合发起了一项名为 Multics 的工程计划，而目的也是希望能够开发出这样的一个操作系统，但是最终由于各种原因以失败告终。

以肯•汤普森为首的贝尔实验室研究人员吸取了 Multics 工程计划失败的经验教训，于 1969 年实现了分时操作系统的雏形，在1970 年该操作系统正式取名为**UNIX**，它是一个强大的多用户、多任务操作系统，支持多种处理器架构，1973 年，也就是C语言问世不久后，UNIX操作系统的绝大部分源代码都用C语言进行了重写。

从这之后，大量的UNIX发行版本涌现（基于Unix进行完善的系统）比如 FreeBSD 就是美国加利福尼亚大学伯克利分校开发的 UNIX 版本，它由来自世界各地的志愿者开发和维护，为不同架构的计算机系统提供了不同程度的支持。

 而后来1984年苹果公司发布的的MacOS（在Macintosh电脑上搭载）操作系统，正是在 FreeBSD 基础之上开发的全新操作系统，这是首次计算机正式跨进图形化时代，具有里程碑的意义。

![Mac OS](https://www.webdesignerdepot.com/cdn-origin/uploads/2009/03/mac-os-1.gif)

同年，乔布斯非常高兴地将自家的图形化MacOS界面展示给微软创始人比尔盖茨，并且希望微软可以为MacOS开发一些软件。比尔盖茨一看，woc，这玩意牛逼啊，咱们自己也给安排一个。于是，在1985年，微软仿造MacOS并基于MS-DOS操作系统，开发出了名为Windows的操作系统：

![img](https://pics5.baidu.com/feed/b7003af33a87e950dd561c8ef220e945faf2b4ab.jpeg?token=b2063459ad19e976ed42754e5f9caea8)

Windows操作系统的问世，无疑是对MacOS的一次打击，因为MacOS只能搭载在Mac上，但是售价实在太贵，并且软件生态也不尽人意，同时代的Windows却能够安装到各种各样的DIY电脑上，称其为PC，尤其是后来的Windows95，几乎是封神的存在，各种各样基于Windows的软件、游戏层出不穷，以至于到今天为止，MacOS的市场占有率依然远低于Windows，不过Apple这十几年一直在注重自家软件生态的发展，总体来说在办公领域体验感其实和Windows差不多，甚至可能还更好，但是打游戏，别想了。

说了这么多，Linux呢，怎么一句都没提它呢？最牛逼的当然放最后说（不是

Unix虽然强大但是有着昂贵的授权费用，并且不开放源代码，于是有人发起了GNU运动（GNU IS NOT UNIX，带有那么一丝嘲讽），模仿 Unix 的界面和使用方式，从头做一个开源的版本。在1987年荷兰有个大学教授安德鲁写了一个Minix，类似于Unix，专用于教学。当Minix流传开来之后，世界各地的黑客们纷纷开始使用并改进，希望把改进的东西合并到Minix中，但是安德鲁觉得他的系统是用于教学的，不能破坏纯净性，于是拒绝了。

在1991年，林纳斯.托瓦兹（Linus Torvalds）认为Minix不够开放，自己又写了一个全新的开源操作系统，它希望这个系统由全世界的爱好者一同参与开发，并且不收费，于是Linux内核就被公开发布到互联网上。一经发布，便引起了社会强烈的反响，在大家的努力下，于1994年Linux的1.0版本正式发布。结合当时的GNU运动，最终合在一起称为了GNU/Linux，以一只企鹅Tux作为吉祥物。

![点击查看图片来源](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%3A%2F%2Fdingyue.ws.126.net%2F2021%2F0914%2F1833179ap00qzeyx5000cc000go009qg.png%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645451358&t=082f4539e97a1a3c9dc8002847e9ad5a)

没错，Git也是林纳斯.托瓦兹只花了2周时间开发的。不过林纳斯非常讨厌C++，他认为C++只会让一个项目变得混乱。

从此以后，各式各样的基于Linux发行版就开始出现：

![点击查看图片来源](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fqnam.smzdm.com%2F202002%2F08%2F5e3e59003360f5288.jpg_e680.jpg&refer=http%3A%2F%2Fqnam.smzdm.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645451543&t=f2471b52851d92920194d2ef2acbab6f)

这些发行版都是在Linux内核的基础之上，添加了大量的额外功能，包括开发环境、图形化桌面、包管理等。包括我们的安卓系统，也是基于Linux之上的，而我们要重点介绍的就是基于Debian之上的Ubuntu操作系统。

最后，2022年了，我们再来看一下各大操作系统的市场占有率：

* Windows11/10/7：80%
* MacOS：11%
* Linux：5%
* 其他：4%

Windows无疑是现在最广泛的操作系统，尤其是Windows XP，是多少00后的青春，很多游戏都是基于Windows平台。当然，如果你已经厌倦了游戏，一心只读圣贤书的话，那么还是建议直接使用任意Linux桌面版或是Mac，因为它们能够为你提供极致和纯粹的开发体验（貌似之前华为也出过Linux笔记本？）

***

![点击查看图片来源](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.iasptk.com%2Fwp-content%2Fuploads%2Fsites%2F12%2F2017%2F03%2FUbuntu-02804350.jpg&refer=http%3A%2F%2Fwww.iasptk.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645453389&t=d7908bc10978585c9ce374cc8013ff64)

## 安装Ubuntu系统

这里我们就以安装虚拟机的方式在我们的电脑上安装Linux操作系统，我们选用Ubuntu作为教程，如果有经济实力，可以在腾讯云、阿里云之类的服务商购买一台云服务器，并选择预装Ubuntu系统；如果你还想搞嵌入式开发之类的工作，可以购买一台树莓派服务器，也可以在上面安装Ubuntu系统，相当于一台迷你主机。在你已经有云服务器的情况下，可以直接跳过虚拟机安装教学。

官网下载：https://cn.ubuntu.com/download/server/step1

注意是下载服务器版本，不是桌面版本。

### 在虚拟机中安装

这里我们使用VMware进行安装，VMware是一个虚拟化应用程序，它可以在我们当前运行的操作系统之上，创建一个虚线的主机，相当于创建了一台电脑，而我们就可以在这台电脑上安装各种各样的操作系统，并且我们可以自由为其分配CPU核心和内存以及硬盘容量（如果你接触过云计算相关内容，应该会对虚拟化技术有所了解）

官网下载：https://www.vmware.com/cn/products/workstation-pro/workstation-pro-evaluation.html

安装完成后，会出现一个类似于CMD的命令窗口，而我们就是通过输入命令来操作我们的操作系统。

### 使用SSH远程连接

如果你使用的是树莓派或是云服务器，那么你会得到一个公网的IP地址，以及默认的用户名和密码，由于服务器安装的Ubuntu并不是在我们的电脑上运行的，那么我们怎么去远程操作呢？

比如我们要远程操作一台Windows电脑，直接使用远程桌面连接即可，但是Ubuntu上来就是命令行，这种情况下要实现远程连接就只能使用SSH终端。

SSH是一种网络协议，用于计算机之间的加密登录。如果一个用户从本地计算机，使用SSH协议登录另一台远程计算机，我们就可以认为，这种登录是安全的，即使被中途截获，密码也不会泄露。最早的时候，互联网通信都是明文通信，一旦被截获，内容就暴露无疑。1995年，芬兰学者Tatu Ylonen设计了SSH协议，将登录信息全部加密，成为互联网安全的一个基本解决方案，迅速在全世界获得推广，目前已经成为Linux系统的标准配置。

云服务器上安装的Ubuntu默认都是自带了OpenSSH服务端的，我们可以直接连接，如果你的Ubuntu服务器上没有安装OpenSSH服务器端，那么可以输入命令进行安装：

```shell
sudo apt install openssh-server
#输入后还需要你输入当前用户的密码才可以执行，至于为什么我们后面会说
```

这里我们使用XShell来进行SSH登陆，官网：https://www.netsarang.com/zh/free-for-home-school/

### 文件系统介绍

在Windows下，我们的整个硬盘实际上可以被分为多个磁盘驱动器：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190415191752939.png)

我们一般习惯将软件装到D盘，文件数据存在E盘，系统和一些环境安装在C盘，根据不同的盘符进行划分，并且每个盘都有各自的存储容量大小。而在Linux中，没有这个概念，所有的文件都是位于根目录下的：

![img](https://upload-images.jianshu.io/upload_images/17163728-8d41eb59e5cfb7ee.png?imageMogr2/auto-orient/strip|imageView2/2/w/904)

我们可以看到根目录下有很多个文件夹，它们都有着各自的划分：

* /bin 可执行二进制文件的目录，如常用的命令 ls、tar、mv、cat 等实际上都是一些小的应用程序
* /home 普通用户的主目录，对应Windows下的C:/Users/用户名/
* /root root用户的主目录（root用户是具有最高权限的用户，之后会讲）
* /boot 内核文件的引导目录, 放置 linux 系统启动时用到的一些文件
* /sbing 超级用户使用的指令文件
* /tmp 临时文件目录，一般用户或正在执行的程序临时存放文件的目录，任何人都可以访问，重要数据不可放置在此目录下。
* /dev 设备文件目录，在Linux中万物皆文件，实际上你插入的U盘等设备都会在dev目录下生成一个文件，我们可以很方便地通过文件IO方式去操作外设，对嵌入式开发极为友好。
* /lib 共享库，系统使用的函数库的目录，程序在执行过程中，需要调用一些额外的参数时需要函数库的协助。
* /usr 第三方 程序目录
* /etc 配置程序目录，系统配置文件存放的目录
* /var 可变文件，放置系统执行过程中经常变化的文件
* /opt 用户使用目录，给主机额外安装软件所摆放的目录。

我们可以直接输入命令来查看目录下的所有文件：

```shell
#只显示文件名称，且不显示隐藏文件
ls
#显示隐藏文件以及文件详细信息
ll
```

那么我们如何才能像Windows那样方便的管理Linux中的文件呢？我们可以使用FTP管理工具，默认情况下Ubuntu是安装了SFTP服务器的。

这里我们使用Xftp来进行管理，官网：https://www.netsarang.com/zh/free-for-home-school/

***

## 用户和用户组

我们整个Linux阶段的学习主要是以实操为主，大量的命令需要大量的使用才能记得更牢固。

Linux系统是一个多用户多任务的分时操作系统，任何一个要使用系统的用户，都必须申请一个账号，然后以这个账号的身份进入系统。比如我们之前就是使用我们在创建服务器时申请的初始用户test，通过输入用户名和密码登录到系统中，之后才能使用各种命令进行操作。其实用户机制和我们的Windows比较类似。一般的普通用户只能做一些比较基本的操作，并且只能在自己的目录（如/home/test）中进行文件的创建和删除操作。

我们可以看到，当前状态信息分为三段：

```shell
test@ubuntu-server:~$
```

格式为：用户名@服务器名称:当前所处的目录$，其中~代表用户目录，如果不是用户目录，会显示当前的绝对路径地址。我们也可以使用`pwd`命令来直接查看当前所处的目录。

在Linux中默认存在一个超级用户root，而此用户拥有最高执行权限，它能够修改任何的内容，甚至可以删除整个Linux内核，正常情况下不会使用root用户进行登陆，只有在特殊情况下才会使用root用户来进行一些操作，root用户非常危险，哪怕一个小小的命令都能够毁掉整个Linux系统，比如`rm -rf /*`，感兴趣的话我们可以放在最后来演示（在以前老是听说安卓手机root，实际上就是获取安卓系统底层Linux系统的root权限，以实现修改系统文件的目的）

我们可以使用`sudo -s`并输入当前用户的密码切换到root用户，可以看到出现了一些变化：

```shell
test@ubuntu-server:~$

root@ubuntu-server:/home/test#
```

我们发现`$`符号变成了`#`符号，注意此符号表示当前的用户权限等级，并且test也变为了root，在此用户下，我们可以随意修改test用户文件夹以外的内容，而test用户下则无法修改。如果需要退出root用户，直接输入`exit`即可。

接着我们来看一下，如何进行用户的管理操作，进行用户管理，包括添加用户和删除用户都需要root权限才可以执行，但是现在我们是test用户，我们可以在命令前面添加`sudo`来暂时以管理员身份执行此命令，比如说我们现在想要添加一个新的用户：

```shell
sudo useradd study
```

其中`study`就是我们想要创建的新用户，`useradd`命令就是创建新用户的命令，同样的，删除用户：

```sh
sudo userdel study
```

Linux中的命令一般都可以携带一些参数来以更多特地的方式执行，我们可以在创建用户时，添加一些额外的参数来进行更多高级操作：

- -d<登录目录> 　指定用户登录时的起始目录。
- -g<群组> 　指定用户所属的群组。
- -G<群组> 　指定用户所属的附加群组。
- -m 　自动建立用户的登入目录。
- -M 　不要自动建立用户的登入目录。
- -s  指定Shell，一般指定为/bin/bash

如果还想查看更多命令，可以直接使用`man`来查看命令的详细参数列表，比如：

```shell
man useradd
```

比如我们现在需要在用户创建时顺便创建用户的文件夹，并指定shell（任意一种命令解释程序，用于处理我们输入的命令）为bash：

```shell
sudo useradd study -m -s /bin/bash
```

可以看到已经自动在home目录下创建了study文件夹（这里..表示上一级目录，.表示当前目录）：

```shell
test@ubuntu-server:~$ ls ..
study  test
```

用户创建完成之后，我们可以为此用户设定密码（如果不指定用户，那么会设置当前用户的密码）：

```shell
sudo passwd study
```

输入密码之后，我们可以使用命令来切换用户：

```shell
test@ubuntu-server:~$ su - study
Password: 
study@ubuntu-server:~$ 
```

可以看到，切换用户后名称已经修改为study了，我们使用`exit`即可退出当前用户回到test。

输入`who`可以查看当前登录账号（注意是登录的账号）输入`whoami`可以查看当前的操作账号：

```shell
test@ubuntu-server:~$ su study
Password: 
study@ubuntu-server:/home/test$ cd ~
study@ubuntu-server:~$ who
test     pts/0        2022-01-24 03:57 (192.168.10.3)
study@ubuntu-server:~$ whoami
study
study@ubuntu-server:~$ 
```

接着我们来看用户组，每个用户都有一个用户组，系统可以对一个用户组中的所有用户进行集中管理。我们可以输入`groups`来查看当前用户所有的用户组：

```sh
test@ubuntu-server:~$ groups
test adm cdrom sudo dip plugdev lxd
```

我们可以输入`id`来查看用户所属的用户相关信息：

```sh
test@ubuntu-server:~$ id
uid=1000(test) gid=1000(test) groups=1000(test),4(adm),24(cdrom),27(sudo),30(dip),46(plugdev),116(lxd)
```

我们可以看到test用户默认情况下主要用户组为同名的test用户组，并且还属于一些其他的用户组，其中sudo用户组就表示可以执行`sudo`命令，我们发现我们创建的study用户没有sudo的执行权限：

```sh
study@ubuntu-server:~$ sudo -s
[sudo] password for study: 
study is not in the sudoers file.  This incident will be reported.
```

正是因为没有加入到sudo用户组，这里我们来尝试将其添加到sudo用户组：

```sh
test@ubuntu-server:~$ id study
uid=1001(study) gid=1001(study) groups=1001(study)
```

使用`usermod`命令来对用户的相关设置进行修改，参数与useradd大致相同：

```sh
test@ubuntu-server:~$ sudo usermod study -G sudo
test@ubuntu-server:~$ id study
uid=1001(study) gid=1001(study) groups=1001(study),27(sudo)
```

接着切换到study用户就可以使用sudo命令了：

```sh
To run a command as administrator (user "root"), use "sudo <command>".
See "man sudo_root" for details.

study@ubuntu-server:/home/test$ sudo -s
[sudo] password for study: 
root@ubuntu-server:/home/test# 
```

实际上，我们的用户信息是存储在配置文件中的，我们之前说了，配置文件一般都放在etc目录下，而用户和用户组相关的配置文件，存放在`/etc/passwd`和`/etc/group`中，我们可以使用cat命令将文件内容打印到控制台：

```bash
test@ubuntu-server:~$ cat /etc/passwd
root:x:0:0:root:/root:/bin/bash
daemon:x:1:1:daemon:/usr/sbin:/usr/sbin/nologin
bin:x:2:2:bin:/bin:/usr/sbin/nologin
sys:x:3:3:sys:/dev:/usr/sbin/nologin
sync:x:4:65534:sync:/bin:/bin/sync
```

格式为：`注册名:口令:用户标识号:组标识号:用户名:用户主目录:命令解释程序 `，而我们的密码则存放在`/etc/shadow`中，是以加密形式存储的，并且需要root权限才能查看。

***

## 常用命令

接着我们来看一下Linux系统中一些比较常用的命令。

### 文件操作

文件是最基本的内容，我们可以使用ls命令列出当前目录中所有的文件，参数-a表示包含所有的隐藏文件，-l表示列出详细信息：

```sh
test@ubuntu-server:~$ ls -al
total 44
drwxr-xr-x 4 test test 4096 Jan 24 08:55 .
drwxr-xr-x 4 root root 4096 Jan 24 04:24 ..
-rw------- 1 test test 2124 Jan 24 04:29 .bash_history
-rw-r--r-- 1 test test  220 Feb 25  2020 .bash_logout
-rw-r--r-- 1 test test 3771 Feb 25  2020 .bashrc
drwx------ 2 test test 4096 Jan 21 15:48 .cache
drwx------ 3 test test 4096 Jan 23 14:49 .config
-rw-r--r-- 1 test test  807 Feb 25  2020 .profile
-rw------- 1 test test   34 Jan 24 04:17 .python_history
-rw-r--r-- 1 test test    0 Jan 21 15:52 .sudo_as_admin_successful
-rw------- 1 test test 7201 Jan 24 08:55 .viminfo
```

可以看到当前目录下的整个文件列表，那么这些信息各种代表什么意思呢，尤其是最前面那一栏类似于`drwxr-xr-x`的字符串。

它表示文件的属性，其中第1个字符表示此文件的类型：`-`表示普通文件，`l`为链接文件，`d`表示目录（文件夹），`c`表示字符设备、`b`表示块设备，还有`p`有名管道、`f`堆栈文件、`s`套接字等，这些一般都是用于进程之间通信使用的。

第2-4个字符表示文件的拥有者（User）对该文件的权限，第5-7个字符表示文件所属用户组（Group）内用户对该文件的权限，最后8-10个字符表示其他用户（Other）对该文件的权限。其中`r`为读权限、`w`为写权限、`x`为执行权限，为了方便记忆，直接记UGO就行了。

比如`drwxr-xr-x`就表示这是一个目录，文件的拥有者可以在目录中读、写和执行，而同一用户组的其他用户只能读和执行，其他用户也是一样。

第二栏数据可以看到是一列数字，它表示文件创建的链接文件（快捷方式）数量，一般只有1表示只有当前文件，我们也可以尝试创建一个链接文件：

```sh
test@ubuntu-server:~$ ln .bash_logout kk
```

创建后，会生成一个名为kk的文件，我们对此文件的操作相当于直接操作.bash_logout，跟Windows中的快捷方式比较类似，了解一下即可。再次执行`ll`命令，可以看到.bash_logout的链接数变成了2。

第三栏数据为该文件或是目录的拥有者。

第四栏数据表示所属的组。

第五栏数据表示文件大小，以字节为单位。

第六栏数据为文件的最后一次修改时间

最后一栏就是文件名称了，就不多说了，再次提及..表示上级目录，.表示当前目录，最前面有一个.开头的文件为隐藏文件。可以看到上级目录（也就是/home目录）所有者为root，并且非root用户无法进行写操作，只能执行读操作，而当前目录以及目录下所有文件则属于test用户，test用户可以随意进行修改。

在了解了Linux的文件查看之后再去看Windows的文件管理，会觉得Windows的太拉了：

![img](https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg0.pconline.com.cn%2Fpconline%2F2107%2F02%2F14306786_05_thumb.jpg&refer=http%3A%2F%2Fimg0.pconline.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645608268&t=75ea125c46cb38b22268156794b986d4)

那么，如果我们希望对文件的属性进行修改，比如我们现在希望将某个文件的写权限给关闭，可以使用`chmod`命令来进行文件属性修改，我们先创建一个test文件，使用`touch`命令来创建文件，使用`mkdir`命令来创建目录：

```shell
test@ubuntu-server:~$ touch test
test@ubuntu-server:~$ ll test
-rw-rw-r-- 1 test test 0 Jan 24 09:32 test
```

可以看到文件创建之后的默认权限为可读可写，接着我们来将其修改为只读，chmod的使用方法如下：

* chmod (u/g/o/a)(+/-)(r/w/x) 文件名称

我们可以从ugo中选择或是直接a表示所有，+和-表示添加和删除权限，最后rwx不用我说了吧

```sh
test@ubuntu-server:~$ chmod a-w test
test@ubuntu-server:~$ ll test
-r--r--r-- 1 test test 0 Jan 24 09:32 test
```

除了这种方式之外，我们也可以使用数字来代替，比如现在我要给前两个添加读权限，那么：

约定：r=4，w=2，x=1，需要什么权限就让对应权限的数字相加，一个数字表示一个rwx的权限状态，比如我们想修改为`-rw-rw-r--`，那么对应的数字就是`664`，对应的命令为：

```sh
test@ubuntu-server:~$ chmod 664 test
test@ubuntu-server:~$ ll test
-rw-rw-r-- 1 test test 0 Jan 24 09:32 test
```

如果我们想修改文件的拥有者或是所属组，可以使用`chown`和`chgrp`命令：

```sh
test@ubuntu-server:~$ sudo chown root test 
test@ubuntu-server:~$ ls -l
total 0
-rw-rw-r-- 1 root test 0 Jan 24 10:43 test
test@ubuntu-server:~$ sudo chgrp root test 
test@ubuntu-server:~$ ls -l
total 0
-rw-rw-r-- 1 root root 0 Jan 24 10:43 test
```

再次操作该文件，会发现没权限：

```sh
test@ubuntu-server:~$ chmod 777 test 
chmod: changing permissions of 'test': Operation not permitted
```

接着我们来看文件的复制、移动和删除，这里我们先创建一个新的目录并进入到此目录用于操作：

```sh
test@ubuntu-server:~$ mkdir study
test@ubuntu-server:~$ cd study
test@ubuntu-server:~/study$
```

首先我们演示文件的复制操作，文件的复制使用`cp`命令，比如现在我们想把上一级目录中的test文件复制到当前目录中：

```sh
test@ubuntu-server:~/study$ cp ../test test
test@ubuntu-server:~/study$ ls
test
```

那么如果我们想要将一整个目录进行复制呢？我们需要添加一个`-r`参数表示将目录中的文件递归复制：

```sh
test@ubuntu-server:~/study$ cd ~
test@ubuntu-server:~$ cp -r study study_copied
test@ubuntu-server:~$ ls -l
total 8
drwxrwxr-x 2 test test 4096 Jan 24 10:16 study
drwxrwxr-x 2 test test 4096 Jan 24 10:20 study_copied
-rw-rw-r-- 1 test test    0 Jan 24 09:32 test
```

可以看到我们的整个目录中所有的文件也一起被复制了。

接着我们来看看移动操作，相当于是直接将一个文件转移到另一个目录中了，我们再创建一个目录用于文件的移动，并将test文件移动到此目录中，我们使用`mv`命令进行文件的移动：

```sh
test@ubuntu-server:~$ mkdir study2
test@ubuntu-server:~$ mv test study2
test@ubuntu-server:~$ ls
study  study2  study_copied
test@ubuntu-server:~$ cd study2
test@ubuntu-server:~/study2$ ls
test
```

现在我们想要移动个目录到另一个目录中，比如我们想将study目录移动到study2目录中：

```sh
test@ubuntu-server:~$ mv study study2
test@ubuntu-server:~$ ls
study2  study_copied
test@ubuntu-server:~$ cd study2
test@ubuntu-server:~/study2$ ls
study  test
```

`mv`命令不仅能实现文件的移动，还可以实现对文件重命名操作，比如我们想将文件test重命名为yyds，那么直接将其进行移动操作即可：

```sh
test@ubuntu-server:~/study2$ ls
study  test
test@ubuntu-server:~/study2$ mv test yyds
test@ubuntu-server:~/study2$ ls
study  yyds
```

最后就是删除命令了，使用`rm`进行删除操作，比如现在我们想删除study2目录（注意需要添加-r参数表示递归删除文件夹中的内容）：

```sh
test@ubuntu-server:~$ rm -r study2
test@ubuntu-server:~$ ls
study_copied
```

而最常提到的`rm -rf /*`正是删除根目录下所有的文件（非常危险的操作），-f表示忽略不存在的文件，不进行任何提示，*是一个通配符，表示任意文件。这里我们演示一下删除所有.txt结尾的文件：

```sh
test@ubuntu-server:~$ touch 1.txt 2.txt 3.txt
test@ubuntu-server:~$ ls
1.txt  2.txt  3.txt
test@ubuntu-server:~$ rm *.txt
test@ubuntu-server:~$ ls
test@ubuntu-server:~$ 
```

最后我们再来看文件的搜索，我们使用find命令来进行搜索，比如我想搜索/etc目录下名为passwd的文件：

```
test@ubuntu-server:~$ sudo find /etc -name passwd
[sudo] password for test: 
/etc/pam.d/passwd
/etc/passwd
```

它还支持通配符，比如搜索以s开头的文件：

```
test@ubuntu-server:~$ sudo find /etc -name s*
/etc/subuid
/etc/screenrc
/etc/sensors3.conf
/etc/sysctl.conf
/etc/sudoers
/etc/shadow
/etc/skel
/etc/pam.d/su
/etc/pam.d/sshd
/etc/pam.d/sudo
...
```

### 系统管理

接着我们来查看一些系统管理相关的命令，比如我们Windows中的任务管理器，我们可以使用`top`命令来打开：

```
top - 10:48:46 up  5:52,  1 user,  load average: 0.00, 0.00, 0.00
Tasks: 191 total,   2 running, 189 sleeping,   0 stopped,   0 zombie
%Cpu(s):  0.0 us,  0.2 sy,  0.0 ni, 99.8 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
MiB Mem :   3919.1 total,   2704.2 free,    215.0 used,    999.9 buff/cache
MiB Swap:   3923.0 total,   3923.0 free,      0.0 used.   3521.4 avail Mem 

    PID USER      PR  NI    VIRT    RES    SHR S  %CPU  %MEM     TIME+ COMMAND                       
  10528 test      20   0    8944   3072   2652 R   0.7   0.1   0:00.07 top                           
   9847 root      20   0       0      0      0 I   0.3   0.0   0:00.87 kworker/0:0-events            
      1 root      20   0  102760  10456   7120 S   0.0   0.3   0:02.02 systemd                       
      2 root      20   0       0      0      0 S   0.0   0.0   0:00.01 kthreadd                      
      3 root       0 -20       0      0      0 I   0.0   0.0   0:00.00 rcu_gp                        
      4 root       0 -20       0      0      0 I   0.0   0.0   0:00.00 rcu_par_gp                    
      6 root       0 -20       0      0      0 I   0.0   0.0   0:00.00 kworker/0:0H-kblockd          
      8 root       0 -20       0      0      0 I   0.0   0.0   0:00.00 mm_percpu_wq                  
      9 root      20   0       0      0      0 S   0.0   0.0   0:00.15 ksoftirqd/0                   
     10 root      20   0       0      0      0 R   0.0   0.0   0:01.49 rcu_sched                     
     11 root      rt   0       0      0      0 S   0.0   0.0   0:00.24 migration/0                   
     12 root     -51   0       0      0      0 S   0.0   0.0   0:00.00 idle_inject/0                 
     14 root      20   0       0      0      0 S   0.0   0.0   0:00.00 cpuhp/0                       
     15 root      20   0       0      0      0 S   0.0   0.0   0:00.00 cpuhp/1                       
     16 root     -51   0       0      0      0 S   0.0   0.0   0:00.00 idle_inject/1                 
     17 root      rt   0       0      0      0 S   0.0   0.0   0:00.30 migration/1                   
     18 root      20   0       0      0      0 S   0.0   0.0   0:00.07 ksoftirqd/1                   
     20 root       0 -20       0      0      0 I   0.0   0.0   0:00.00 kworker/1:0H-kblockd  
```

可以很清楚地看到当前CPU的使用情况以及内存的占用情况。

按下数字键1，可以展示所有CPU核心的使用情况：

```
%Cpu0  :  0.0 us,  0.0 sy,  0.0 ni,100.0 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
%Cpu1  :  0.0 us,  0.0 sy,  0.0 ni,100.0 id,  0.0 wa,  0.0 hi,  0.0 si,  0.0 st
```

按下f键可以设置以哪一列进行排序或是显示那些参数：

```
Fields Management for window 1:Def, whose current sort field is %MEM
   Navigate with Up/Dn, Right selects for move then <Enter> or Left commits,
   'd' or <Space> toggles display, 's' sets sort.  Use 'q' or <Esc> to end!
```

按下q键即可退出监控界面。

我们可以直接输入free命令来查看当前系统的内存使用情况：

```
test@ubuntu-server:~$ free -m
              total        used        free      shared  buff/cache   available
Mem:           3919         212        2706           1         999        3523
Swap:          3922           0        3922
```

其中-m表示以M为单位，也可以-g表示以G为单位，默认是kb为单位。

最后就是磁盘容量，我们可以使用`lsblk`来查看所有块设备的信息，其中就包括我们的硬盘、光驱等：

```
test@ubuntu-server:~$ lsblk
NAME                      MAJ:MIN RM  SIZE RO TYPE MOUNTPOINT
loop0                       7:0    0 48.9M  1 loop /snap/core18/2127
loop1                       7:1    0 28.1M  1 loop /snap/snapd/12707
loop2                       7:2    0   62M  1 loop /snap/lxd/21032
sr0                        11:0    1 1024M  0 rom  
nvme0n1                   259:0    0   20G  0 disk 
├─nvme0n1p1               259:1    0  512M  0 part /boot/efi
├─nvme0n1p2               259:2    0    1G  0 part /boot
└─nvme0n1p3               259:3    0 18.5G  0 part 
  └─ubuntu--vg-ubuntu--lv 253:0    0 18.5G  0 lvm  /
```

可以看到nvme开头的就是我们的硬盘（这个因人而异，可能你们的是sda，磁盘类型不同名称就不同）可以看到`nvme0n1  `容量为20G，并且512M用作存放EFI文件，1G存放启动文件，剩余容量就是存放系统文件和我们的用户目录。

这里要提到一个挂载的概念：

> 挂载，指的就是将设备文件中的顶级目录连接到 Linux 根目录下的某一目录（最好是空目录），访问此目录就等同于访问设备文件。

比如我们的主硬盘，挂载点就被设定为`/`根目录，而我们所有保存的文件都会存储在硬盘中，如果你有U盘（最好将U盘的文件格式改为ExFat，可以直接在Windows中进行格式化，然后随便放入一些文件即可）之类的东西，我们可以演示一下对U盘进行挂载：

```sh
test@ubuntu-server:~$ sudo fdisk -l
...
Disk /dev/sda: 60 GiB, 64424509440 bytes, 125829120 sectors
Disk model: USB DISK        
Units: sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disklabel type: dos
Disk identifier: 0x4a416279

Device     Boot     Start       End   Sectors  Size Id Type
/dev/sda1  *       614400 125214719 124600320 59.4G  7 HPFS/NTFS/exFAT
/dev/sda2       125214720 125825022    610303  298M  6 FAT16
```

将U盘插入电脑，选择连接到Linux，输入`sudo fdisk -l`命令来查看硬盘实体情况，可以看到有一个USB DISK设备，注意观察一下是不是和自己的U盘容量一致，可以看到设备名称为`/dev/sda1`。

接着我们设备挂载到一个目录下：

```sh
test@ubuntu-server:~$ mkdir u-test
test@ubuntu-server:~$ sudo mount /dev/sda1 u-test/
test@ubuntu-server:~$ cd u-test/
test@ubuntu-server:~/u-test$ ls
 CGI
 cn_windows_10_enterprise_ltsc_2019_x64_dvd_9c09ff24.iso
 cn_windows_7_professional_x64_dvd_x15-65791.iso
 cn_windows_8.1_enterprise_with_update_x64_dvd_6050374.iso
 cn_windows_8.1_professional_vl_with_update_x64_dvd_4050293.iso
 cn_windows_server_2019_updated_july_2020_x64_dvd_2c9b67da.iso
'System Volume Information'
 zh-cn_windows_10_consumer_editions_version_21h1_updated_sep_2021_x64_dvd_991b822f.iso
 zh-cn_windows_11_consumer_editions_x64_dvd_904f13e4.iso
```

最后进入到此目录中，就能看到你U盘中的文件了，如果你不想使用U盘了，可以直接取消挂载：

```
test@ubuntu-server:~/u-test$ cd ..
test@ubuntu-server:~$ sudo umount /dev/sda1
```

最后我们可以通过`df`命令查看当前磁盘使用情况：

```
test@ubuntu-server:~$ df -m
Filesystem                        1M-blocks  Used Available Use% Mounted on
udev                                   1900     0      1900   0% /dev
tmpfs                                   392     2       391   1% /run
/dev/mapper/ubuntu--vg-ubuntu--lv     18515  6544     11009  38% /
tmpfs                                  1960     0      1960   0% /dev/shm
tmpfs                                     5     0         5   0% /run/lock
tmpfs                                  1960     0      1960   0% /sys/fs/cgroup
/dev/nvme0n1p2                          976   109       800  12% /boot
/dev/nvme0n1p1                          511     4       508   1% /boot/efi
/dev/loop0                               49    49         0 100% /snap/core18/2127
/dev/loop1                               29    29         0 100% /snap/snapd/12707
/dev/loop2                               62    62         0 100% /snap/lxd/21032
tmpfs                                   392     0       392   0% /run/user/1000
```

输入`ps`可以查看当前运行的一些进程，其实和top有点类似，但是没有监控功能，只能显示当前的。

```
test@ubuntu-server:~$ ps
    PID TTY          TIME CMD
  11438 pts/0    00:00:00 bash
  11453 pts/0    00:00:00 ps
```

添加-ef查看所有的进程：

```
test@ubuntu-server:~$ ps -ef
UID          PID    PPID  C STIME TTY          TIME CMD
root           1       0  0 04:55 ?        00:00:02 /sbin/init
root           2       0  0 04:55 ?        00:00:00 [kthreadd]
root           3       2  0 04:55 ?        00:00:00 [rcu_gp]
root           4       2  0 04:55 ?        00:00:00 [rcu_par_gp]
root           6       2  0 04:55 ?        00:00:00 [kworker/0:0H-kblockd]
...
```

我们可以找到对应的进程ID（PID），使用kill命令将其强制终止：

```
test@ubuntu-server:~$ ps
    PID TTY          TIME CMD
  11438 pts/0    00:00:00 bash
  11455 pts/0    00:00:00 ps
test@ubuntu-server:~$ kill -9 11438
Connection to 192.168.10.6 closed.
```

比如我们可以将当前会话的bash给杀死，那么会导致我们的连接直接断开，其中-9是一个信号，表示杀死进程：

- 1 (HUP)：重新加载进程。
- 9 (KILL)：杀死一个进程。
- 15 (TERM)：正常停止一个进程。

最后如果我们想要正常关机，只需要输入shutdown即可，系统会创建一个关机计划，并在指定时间关机，或是添加now表示立即关机：

```
test@ubuntu-server:~$ sudo shutdown
[sudo] password for test: 
Shutdown scheduled for Mon 2022-01-24 11:46:18 UTC, use 'shutdown -c' to cancel.
test@ubuntu-server:~$ sudo shutdown now
Connection to 192.168.10.6 closed by remote host.
Connection to 192.168.10.6 closed.
```

### 压缩解压

比较常用的压缩和解压也是重点，我们在Windows中经常需要下载一些压缩包，并且将压缩包解压才能获得里面的文件，而Linux中也支持文件的压缩和解压。

这里我们使用`tar`命令来完成文件亚索和解压操作，在Linux中比较常用的是gzip格式，后缀名一般为.gz，tar命令的参数-c表示对文件进行压缩，创建新的压缩文件，-x表示进行解压操作，-z表示以gzip格式进行操作，-v可以在处理过程中输出一些日志信息，-f表示对普通文件进行操作，这里我们创建三个文件并对这三个文件进行打包：

```sh
test@ubuntu-server:~$ tar -zcvf test.tar.gz *.txt
1.txt
2.txt
3.txt
test@ubuntu-server:~$ ls
1.txt  2.txt  3.txt  test.tar.gz
test@ubuntu-server:~$ 
```

接着我们删除刚刚三个文件，再执行解压操作，得到压缩包中文件：

```sh
test@ubuntu-server:~$ rm *.txt
test@ubuntu-server:~$ ls
test.tar.gz
test@ubuntu-server:~$ tar -zxvf test.tar.gz 
1.txt
2.txt
3.txt
test@ubuntu-server:~$ ls
1.txt  2.txt  3.txt  test.tar.gz
```

同样的，我们也可以对一个文件夹进行打包：

```sh
test@ubuntu-server:~$ mv *.txt test
test@ubuntu-server:~$ tar -zcvf test.tar.gz test/
test/
test/1.txt
test/2.txt
test/3.txt
test@ubuntu-server:~$ rm -r test
test@ubuntu-server:~$ ls
test.tar.gz 
test@ubuntu-server:~$ tar -zxvf test.tar.gz 
test/
test/1.txt
test/2.txt
test/3.txt
test@ubuntu-server:~$ ls
test  test.tar.gz
test@ubuntu-server:~$ ls test
1.txt  2.txt  3.txt
```

到此，Linux的一些基本命令就讲解为止。

***

## vim文本编辑器

和Windows中的记事本一样，Linux中也有文本编辑器，叫做Vi编辑器，Ubuntu中内置了Vi编辑器的升级版Vim，我们这里就讲解Vim编辑器的使用。

我们可以直接输入`vim 文件名称`来使用Vim编辑器对文本文件进行编辑：

```sh
test@ubuntu-server:~$ vim hello.txt
```

进入编辑器之后，我们发现界面变成了：

```
~                                                                                                                                                                                         
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
"hello.txt" [New File]                                                            0,0-1         All
```

这时我们直接输入内容是无法完成的，因为默认进入之后为`命令模式`，Vim编辑器默认有三种模式：

![img](http://c.biancheng.net/uploads/allimg/180914/1-1P9141F35R32.jpg)

* 命令模式：此模式下可以输入任意的命令进行操作，所有的输入都被看做是命令输入，而不是文本编辑输入。
* 编辑模式：此模式下输入的任何内容都会以文本编辑方式写入到文件中，就像我们直接在Windows的记事本中写内容一样。
* 末行模式：此模式下用于输入一些复杂命令，会在最后一行进行复杂命令的输入。

在命令模式下，我们可以直接按下键盘上的`i`，此命令表示进行插入操作，会自动切换到编辑模式，这时可以看到最下方变为：

```
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
~                                                                                                   
-- INSERT --                                                                      1,1           All
```

而这时我们所有的输入内容都可以直接写到文件中了，如果我们想回到命令模式，按下`Esc`键即可。

除了`i`以外，我们也可以按下`a`表示从当前光标所在位置之后继续写，与`i`不同的是，`i`会在光标之前继续写，`o`会直接跳到下一行，而`A`表示在当前行的最后继续写入，`I`表示在当前行的最前面继续写入。

这里我们随便粘贴一段文本信息进去（不要用Ctrl+V，Linux中没这操作，XShell右键点粘贴）：

```
I was hard on people sometimes, probably harder than I needed to be. 
I remember the time when Reed was six years old, coming home, and I had just fired somebody that day.
And I imagined what it was like for that person to tell his family and his young son that he had lost his job. 
It was hard. 
But　somebody’s got to do it. 
I figured that it was always my job to make sure that the team was excellent, and if I didn’t do it, nobody was going to do it.
You always have to keep pushing to innovate.
Dylan could have sung protest songs forever and probably made a lot of money, but he didn’t. 
He had to move on, and when he did, by going electric in 1965, he alienated a lot of people.
```

在我们编辑完成之后，需要进入到末行模式进行文件的保存并退出，按下`:`进入末行模式，再输入wq即可保存退出。

接着我们来看一些比较常用的命令，首先是命令模式下的光标移动命令：

* ^   直接调到本行最前面
* $   直接跳到本行最后面
* gg  直接跳到第一行
* [N]G    跳转到第N行
* [N]方向键    向一个方向跳转N个字符

在末行模式下，常用的复杂命令有：

* :set number    开启行号
* :w	保存
* :wq或:x	保存并关闭
* :q	关闭
* :q!	强制关闭

我们可以输入`/`或是`?`在末行模式中使用搜索功能，比如我们要搜索单词`it`：

```
/it  
```

接着会在文本中出现高亮，按`n`跳转到下一个搜索结果，?是从后向前搜索，/是从前向后搜索。

它还支持替换功能，但是使用起来稍微比较复杂，语法如下：

```
:[addr]s/源字符串/目的字符串/[option]
```

addr表示第几行或是一个范围，option表示操作类型：

* g: globe,表示全局替换
* c: confirm,表示进行确认
* p: 表示替代结果逐行显示(Ctrl + L恢复屏幕)
* i:  ignore,不区分大小写

比如我们要将当前行中的`it`全部替换为`he`，那么可以这样写：

```
:s/it/he/g
```

实际上除了以上三种模式外，还有一种模式叫做可视化模式，按下键盘上的`v`即可进入，它能够支持选取一段文本，选取后，我们可以对指定段落的文本内容快速进行复制、剪切、删除、插入等操作，非常方便。在此模式下，我们可以通过上下左右键进行选取，以进入可视化模式时的位置作为基本位置，通过移动另一端来进行选取。

我们可以使用以下命令来对选中区域进行各种操作：

* y   复制选中区域
* d/x   剪切（删除）选中区域
* p  粘贴
* u  撤销上一步

当然，这些命令在命令模式下也可以使用，但是可视化模式下使用更适合一些。

***

## 环境安装和项目部署

在学习完了Linux操作系统的一些基本操作之后，我们接着来看如何进行项目的环境安装和部署，包括安装JDK、Nginx服务器，以及上传我们的SpringBoot项目并运行。

我们可以直接使用apt进行软件的安装，它是一个高级的安装包管理工具，我们可以直接寻找对应的软件进行安装，无需再去官网进行下载，非常方便，软件仓库中默认已经帮助我们存放了大量实用软件的安装包，只需要一个安装命令就可以进行安装了。

实际上Ubuntu系统已经为我们自带了一些环境了，比如Python3：

```
test@ubuntu-server:~$ python3
Python 3.8.10 (default, Nov 26 2021, 20:14:08) 
[GCC 9.3.0] on linux
Type "help", "copyright", "credits" or "license" for more information.
>>> print("HelloWorld！")
HelloWorld！
>>> exit()
```

C语言的编译工具GCC可以通过APT进行安装：

```sh
sudo apt install gcc
```

安装后，可以编写一个简单的C语言程序并且编译为可执行文件：

```c
#include<stdio.h>

int main(){
        printf("Hello World!\n");
}  
```

```sh
test@ubuntu-server:~$ vim hello.c
test@ubuntu-server:~$ gcc hello.c -o hello
test@ubuntu-server:~$ ./hello 
Hello World!
```

而JDK实际上安装也非常简单，通过APT即可：

```sh
test@ubuntu-server:~$ sudo apt install openjdk-8-j
openjdk-8-jdk           openjdk-8-jre           openjdk-8-jre-zero      
openjdk-8-jdk-headless  openjdk-8-jre-headless  
test@ubuntu-server:~$ sudo apt install openjdk-8-jdk
```

接着我们来测试一下编译和运行，首先编写一个Java程序：

```
test@ubuntu-server:~$ vim Main.java
```

```
public class Main{
        public static void main(String[] args){
                System.out.println("Hello World！");
        }
}
```

```
test@ubuntu-server:~$ javac Main.java 
test@ubuntu-server:~$ ls
Main.class  Main.java
test@ubuntu-server:~$ java Main 
Hello World！
```

接着我们来部署一下Redis服务器：

```
test@ubuntu-server:~$ sudo apt install redis
```

安装完成后，可以直接使用`redis-cli`命令打开Redis客户端连接本地的服务器：

```
test@ubuntu-server:~$ redis-cli
127.0.0.1:6379> keys *
(empty list or set)
```

使用和之前Windows下没有区别。

接着我们安装一下MySQL服务器，同样的，直接使用apt即可：

```
sudo apt install mysql-server-8.0 
```

我们直接直接登录MySQL服务器，注意要在root权限下使用，这样就不用输入密码了：

```
sudo mysql -u root -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 11
Server version: 8.0.27-0ubuntu0.20.04.1 (Ubuntu)

Copyright (c) 2000, 2021, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> exit
```

可以发现实际上就是我们之前在Windows的CMD中使用的样子，接着我们就创建一个生产环境下使用的数据库：

```
mysql> create database book_manage;
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| book_manage        |
| information_schema |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
5 rows in set (0.01 sec)
```

接着我们创建一个用户来使用这个数据，一会我们就可以将SpringBoot配置文件进行修改并直接放到此服务器上进行部署。

```
mysql> create user test identified by '123456';
Query OK, 0 rows affected (0.01 sec)

mysql> grant all on book_manage.* to test;
Query OK, 0 rows affected (0.00 sec)
```

如果觉得这样很麻烦不是可视化的，可以使用Navicat连接进行操作，注意开启一下MySQL的外网访问。

```
test@ubuntu-server:~$ mysql -u test -p
Enter password: 
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 13
Server version: 8.0.27-0ubuntu0.20.04.1 (Ubuntu)

Copyright (c) 2000, 2021, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| book_manage        |
| information_schema |
+--------------------+
2 rows in set (0.01 sec)
```

使用test用户登录之后，查看数据库列表，有book_manage就OK了。

最后我们修改一下SpringBoot项目的生产环境配置即可：

```yaml
spring:
  mail:
    host: smtp.163.com
    username: javastudy111@163.com
    password: TKPGLAPDSWKGJOWK
  datasource:
    url: jdbc:mysql://localhost:3306/book_manage
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: test
    password: 123456
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: update
springfox:
  documentation:
    enabled: false
```

然后启动我们的项目：

```sh
test@ubuntu-server:~$ java -jar springboot-project-0.0.1-SNAPSHOT.jar 
```

现在我们将前端页面的API访问地址修改为我们的SpringBoot服务器地址，即可正常使用了。

我们也可以将我们的静态资源使用Nginx服务器进行代理：

> Nginx("engine x")是一款是由俄罗斯的程序设计师Igor Sysoev所开发高性能的 Web和 反向代理 服务器，也是一个 IMAP/POP3/SMTP 代理服务器。 在高连接并发的情况下，Nginx是Apache服务器不错的替代品。

Nginx非常强大，它能够通提供非常方便的反向代理服务，并且支持负载均衡，不过我们这里用一下反向代理就可以了，实际上就是代理我们的前端页面，然后我们访问Nginx服务器即可访问到静态资源，这样我们前后端都放在了服务器上（你也可以搞两台服务器，一台挂静态资源一台挂SpringBoot服务器，实现真正意义上的分离，有条件的还能上个域名和证书啥的）。

安装如下：

```
test@ubuntu-server:~$ sudo apt install nginx
```

安装完成后，我们可以直接访问：http://192.168.10.4/，能够出现Nginx页面表示安装成功！

接着我们将静态资源上传到Linux服务器中，然后对Nginx进行反向代理配置：

```
test@ubuntu-server:~$ cd /etc/nginx/
test@ubuntu-server:/etc/nginx$ ls
conf.d		koi-utf     modules-available  proxy_params	sites-enabled  win-utf
fastcgi.conf	koi-win     modules-enabled    scgi_params	snippets
fastcgi_params	mime.types  nginx.conf	       sites-available	uwsgi_params
test@ubuntu-server:/etc/nginx$ sudo vim nginx.conf
```

```
server {
                listen       80;
                server_name  192.168.10.4;
                add_header Access-Control-Allow-Origin *;
                location / {
                        root /home/test/static;
                        charset utf-8;
                        add_header 'Access-Control-Allow-Origin' '*';
                        add_header 'Access-Control-Allow-Credentials' 'true';
                        add_header 'Access-Control-Allow-Methods' '*';
                        add_header 'Access-Control-Allow-Headers' 'Content-Type,*';
                }
        }
```

然后就可以直接访问到我们的前端页面了，这时再开启SpringBoot服务器即可，可以在最后添加&符号表示后台启动。











