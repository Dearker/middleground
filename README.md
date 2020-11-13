## middleground规范及简介

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
        单机模式启动：sh startup.sh -m standalone
        集群模式启动：sh startup.sh
        
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
        启动：nohup java -Xms128m -Xmx128m -Dserver.port=8488 -Dcsp.sentinel.dashboard.server=114.67.102.117:8488 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.7.2.jar >/dev/null 2>&1 &   
    
    参考文档地址：
        https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D
        
    sentinel整合nacos进行持久化规则,需要在nacos中配置对应的规则，具体规则定义参考地址
    http://www.itmuch.com/spring-cloud-alibaba/sentinel-configuration-rule/
    案例格式如下：
    
        [
            {
                "resource": "/rateLimit/byUrl",
                "limitApp": "default",
                "grade": 1,
                "count": 1,
                "strategy": 0,
                "controlBehavior": 0,
                "clusterMode": false
            }
        ]
        
        - resource：资源名
        - limitApp：流控针对的调用来源，default不区分来源
        - grade：限流阈值类型(0-根据并发数量来限流 1-根据QPS来进行流量控制)
        - count：限流阈值
        - strategy：调用关系限流策略
        - controlBehavior：流量控制效果(直接拒绝、WarmUP、匀速排队)
        - clusterMode：是否集群模式
             
       
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


### docker 部署数据库及三方依赖

    注：docker中强制删除未使用的镜像文件命令：docker image prune -a -f

1、mysql

    docker run -p 3306:3306 --name mysql -v /opt/data/mysql/conf:/etc/mysql/conf.d -v /opt/data/mysql/logs:/logs -v /opt/data/mysql/data:/mysql_data -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7

2、mongo

    docker run -p 27017:27017 -v /opt/data/mongo/db:/data/db -v /opt/data/mongo:/data/configdb -d mongo:3.6

    Mac本机mongo启动到mongo的bin路径下执行命令：./mongod

3、redis
    
    docker run -d --privileged=true -p 6379:6379 --restart always -v /opt/data/redis/conf/redis.conf:/etc/redis/redis.conf -v /opt/data/redis/data:/data --name redis redis:5.0.7 redis-server /etc/redis/redis.conf --appendonly yes

    --restart always   -> 开机启动
    --privileged=true  -> 提升容器内权限
    --appendonly yes   -> 开启数据持久化   
    
4、zookeeper

    docker run --privileged=true -d --name zookeeper --publish 2181:2181 -d zookeeper:3.5.6    
    
5、sonarQube

    docker run -d -p 9000:9000 -m 2048M --cpus=0.3 -e "SONARQUBE_JDBC_URL=jdbc:postgresql://114.67.102.137:5432/sonarDB" -e "SONARQUBE_JDBC_USERNAME=sonarUser" -e "SONARQUBE_JDBC_PASSWORD=asdzxc789" --name sonarqube sonarqube:lts
    
    参考文档地址：https://blog.csdn.net/wojiushiwo945you/article/details/100699885
    该项目maven检查命令：mvn sonar:sonar -Dsonar.projectKey=middleground -Dsonar.host.url=http://114.67.102.137:9000 -Dsonar.login=b991bccecb557e57d03b13193dfdb2dc0e4d5316
    
6、elasticSearch
    
    docker run --name elasticsearch -p 9200:9200 \
     -p 9300:9300 \
     -e "discovery.type=single-node" \
     -e ES_JAVA_OPTS="-Xms64m -Xmx128m" \
      -v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
     -v /mydata/elasticsearch/data:/usr/share/elasticsearch/data \
     -v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
     -d elasticsearch:7.9.2
    
    参考文档地址：https://www.cnblogs.com/chinda/p/13125625.html
    参数说明：
        --name elasticsearch：将容器命名为 elasticsearch
        -p 9200:9200：将容器的9200端口映射到宿主机9200端口
        -p 9300:9300：将容器的9300端口映射到宿主机9300端口，目的是集群互相通信
        -e "discovery.type=single-node"：单例模式
        -e ES_JAVA_OPTS="-Xms64m -Xmx128m"：配置内存大小
        -v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml：将配置文件挂载到宿主机
        -v /mydata/elasticsearch/data:/usr/share/elasticsearch/data：将数据文件夹挂载到宿主机
        -v /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins：将插件目录挂载到宿主机(需重启)
        -d elasticsearch:7.9.2 后台运行容器，并返回容器ID
        elasticsearch.yml中定义的信息为http.host: 0.0.0.0
    
7、kibana
    
    docker run -d  --name kibana -m 1024M --cpus=0.3 -e ELASTICSEARCH_URL=http://192.168.0.4:9200  -e ELASTICSEARCH_USERNAME="elastic"  -e ELASTICSEARCH_PASSWORD="asdzxc789"   -p 5601:5601  kibana:7.9.2
    docker run -d  --name kibana -m 1024M --cpus=0.3 -e ELASTICSEARCH_HOSTS=http://192.168.0.4:9200  -e ELASTICSEARCH_USERNAME="elastic"  -e ELASTICSEARCH_PASSWORD="asdzxc789"   -p 5601:5601  kibana:7.9.2
    注：如果上述命令中指定的环境变量失效，则需要到容器中直接修改kibana.yml文件，配置内容如下：
            server.name: kibana
            server.host: "0"
            elasticsearch.hosts: [ "http://192.168.0.4:9200" ]
            monitoring.ui.container.elasticsearch.enabled: true
            
            elasticsearch.username: "elastic"
            elasticsearch.password: "asdzxc789"
            
            i18n.locale: "zh-CN"
            xpack.security.enabled: true
        参考文档地址：https://blog.csdn.net/qq_41631365/article/details/109181240
    
### 使用kubernetes命令
 
1、查看dashboard令牌(最高权限)
       
     在node01节点上执行命令：kubectl describe secret admin-token-nr9r4 -n kube-system   
       
2、向master中添加节点
    
    先将master中的~/.kube/config文件的内容复制到需要添加的node中，让node和master可以使用秘钥通信
    在执行命令添加节点：
        kubeadm join 192.168.0.3:6443 --token 8ln737.77grxng35yn6b263 --discovery-token-ca-cert-hash sha256:aada718a2aafdad65bd3dc241d394d64846a38047db10bf45f59c70d587ced74 

3、清理docker占用的磁盘空间
    
    docker system prune: 用于清理磁盘，删除关闭的容器、无用的数据卷和网络，无tag的镜像
    
    docker system prune -a: 可以将没有容器使用Docker镜像删掉
    
    查看文档：https://blog.csdn.net/ujm097/article/details/90402158
    
### 其他组件

6、Arthas
       
    修改启动端口(默认3658): java -jar arthas-boot.jar --telnet-port 9998 --http-port -1    
       
7、Jmeter
    
    新增聚合报告：线程组->添加->监听器->聚合报告（Aggregate Report）            
    lable: sampler的名称，接口名称            
    Samples: 一共发出去多少请求,例如10个用户，循环10次，则是 100            
    Average: 平均响应时间            
    Median: 中位数，也就是 50％ 用户的响应时间            
    90% Line : 90％ 用户的响应不会超过该时间,单位秒             
    95% Line : 95％ 用户的响应不会超过该时间            
    99% Line : 99％ 用户的响应不会超过该时间            
    min : 最小响应时间            
    max : 最大响应时间                 
    Error%：错误的请求的数量/请求的总数            
    Throughput： 吞吐量——默认情况下表示每秒完成的请求数（Request per Second) 可类比为qps            
    KB/Sec: 每秒接收数据量
    
    注：并发数自定义，一般标准为异常率低于0.1%，且响应时间小于2秒

7、Mac连接Linux

    命令：ssh root@服务器地址  ，然后输入密码
    