# circle 

技术栈： 

SpringBootmybatis plus

spring security

lombok

redis

jwt

开发工具与环境：

idea 

mysql 5.0.87-community-nt

jdk 8

maven 3.3.9

redis 3.2.100

##
项目运行起来需要
###
更改配置文件的yml
    spring:
    datasource:
  
      driver-class-name: com.mysql.jdbc.Driver    添加数据库驱动
    
      url: jdbc:mysql:数据库连接路径?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
    
      username: 用户名
    
      password: 密码
  
    redis:
   
      database: 数据库
    
      host: 地址
   
      port: 端口号
  
      password:密码

     mybatis-pluse:
        mapper-locations: classpath*:/mapper/*Mapper.xml
        type-aliases-package: com.circlett.pojo
        global-config:
          logic-ont-delete-value: 1
          logic-delete-value: 0
    server:
       port: 8082
    circle:
       jwt:
       expire: 604800 #7天，秒单位
       secret: ji8n3439n439n43ld9ne9343fdfer49h
       header: Authorization

    
     #服务器图片上传映射地址
     uploadPathImg:   xxx.xxx    
     #服务器的IP地址
     ip:  xxx.xxx


用户注册接口
- http://服务器地址:8082/api/user/register  
登录接口 
- http://121.4.90.69:8082/login
