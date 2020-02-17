##middleground规范及简介

1、所有应用的端口都需以800开头，如网关端口为8000,admin端口为8001，以此类推，后期方便管理，应用的端口可统一写在该文件中

2、所有应用的nacos和sentinel、日志、zipkin、adminClient、端口、应用名称、相对路径这些通用的配置都需要写在bootstrap.yml中
   方便统一管理

3、hanyi-common该工程中整理好了全局异常类、Feign的拦截器、请求和响应的包装类;nacos的服务发现和配置中心的依赖
   还有sentinel、swagger、zipkin、hystrix、log4j、lombok等相关依赖全部在这里面，其他工程无需重复引入

4、hanyi-gateway该工程整理好了网关，网关这块做了统一的处理，通过应用的服务名称进行调整，可以进行统一的管理;望
   后期尽量不要改动，容易造成混乱

5、hanyi-utils该工程整合了通用的一些工具类和相关依赖，后期可以进行完善;主要有分布式id生成器：雪花算法、IdWorker等
   md5工具类、httpClient工具类、反射工具类、Bcrypt加密工具类、cookie工具类、zip工具类等

6、hanyi-demo集成了mybatisplus、redis、ehcache、zookeeper、七牛云、mysql、mongodb、sleuth和elk。
   实现二级缓存、分布式锁、seata

7、hanyi-daily未集成三方依赖供日常使用，hanyi-privoder已集成三方依赖为生产者

### seata应用配置

1. 每个应用的resource里需要配置一个registry.conf ，demo中与seata-server里的配置相同

2. application.propeties 的各个配置项，注意spring.cloud.alibaba.seata.tx-service-group 是服务组名称，与nacos-config.txt 配置的service.vgroup_mapping.${your-service-gruop}具有对应关系


### 测试

1. 分布式事务成功，模拟正常下单、扣库存

   localhost:8003/hanyi-demo/order/placeOrder/commit   

2. 分布式事务失败，模拟下单成功、扣库存失败，最终同时回滚

   localhost:8003/hanyi-demo/order/placeOrder/rollback 
   
   
### 三方依赖应用配置   
   
1、nacos
    
    Mac/Linux
        启动：sh startup.sh -m standalone
   
    windows
        启动：双击startup.cmd
   
    参考文档地址：
        https://nacos.io/zh-cn/docs/quick-start.html
   
2、seata
    
    Mac/Linux
        启动：nohup ./seata-server.sh -p 8091 -h 114.67.102.137 -m file >/dev/null 2>&1 &
    
    windows
            启动：双击seata-server.bat
    
    参考文档地址：
        https://seata.io/zh-cn/docs/overview/what-is-seata.html
    
3、sentinel

    Mac/Linux/windows
        启动：nohup java -Dserver.port=8488 -Dcsp.sentinel.dashboard.server=114.67.102.137:8488 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.6.3.jar >/dev/null 2>&1 &   
    
    参考文档地址：
        https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D
       
4、xxl-job

    Mac/Linux/windows
        启动：nohup java -jar xxl-job-admin-2.1.0.jar >/dev/null 2>&1 &
    
    参考文档地址：
        https://www.xuxueli.com/xxl-job/       
       
5、SkyWalking

    Mac/Linux
        启动：sh ./startup.sh
        
    windows:
        启动: 双击startup.bat    
        
    参考文档地址：
        https://skywalking.apache.org/zh/    
       
       
### maven 打包跳过测试命令
    
1、方式一
      
    mvn clean package -DskipTests    
    mvn clean install -DskipTests
    
2、 方式二
    
    mvn clean package -Dmaven.test.skip=true
    mvn clean install -Dmaven.test.skip=true


### docker 部署数据库

1、mysql

    docker run -p 3306:3306 --name mysql -v /opt/data/mysql/conf:/etc/mysql/conf.d -v /opt/data/mysql/logs:/logs -v /opt/data/mysql/data:/mysql_data -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7

2、mongo

    docker run -p 27017:27017 -v /opt/data/mongo/db:/data/db -v /opt/data/mongo:/data/configdb -d mongo:3.6

3、redis
    
    docker run -d --privileged=true -p 6379:6379 --restart always -v /opt/data/redis/conf/redis.conf:/etc/redis/redis.conf -v /opt/data/redis/data:/data --name redis redis:5.0.7 redis-server /etc/redis/redis.conf --appendonly yes

    --restart always   -> 开机启动
    --privileged=true  -> 提升容器内权限
    --appendonly yes   -> 开启数据持久化   
    
4、zookeeper

    docker run --privileged=true -d --name zookeeper --publish 2181:2181 -d zookeeper:3.5.6    
    
    
    