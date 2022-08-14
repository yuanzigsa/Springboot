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

​```sh
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











