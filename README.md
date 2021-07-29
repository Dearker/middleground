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
        启动：nohup java -Xms128m -Xmx128m -Dserver.port=8858 -Dcsp.sentinel.dashboard.server=114.67.102.117:8858 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.7.2.jar >/dev/null 2>&1 &   
    
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

    docker run -p 3306:3306 --name mysql -v /opt/canal/mysql/conf:/etc/mysql/conf.d -v /opt/canal/mysql/logs:/logs -v /opt/canal/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=asdzxc789 -d mysql:5.7
    docker run -p 3307:3306 --name mysql8.0 --restart=always -e MYSQL_ROOT_PASSWORD=asdzxc789 -d mysql:8.0
    
2、mongo

    docker run -p 27017:27017 -v /opt/data/mongo/db:/data/db -v /opt/data/mongo:/data/configdb -d mongo:3.6

    Mac本机mongo启动到mongo的bin路径下执行命令：./mongod

3、redis
    
    docker run -d --privileged=true -p 6379:6379 --restart always -v /opt/data/redis/conf/redis.conf:/etc/redis/redis.conf -v /opt/data/redis/data:/data --name redis redis:6.2.5 redis-server /etc/redis/redis.conf --appendonly yes

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
    
8、rabbitmq

    docker run -d --name rabbit -p 15672:15672 -p 5672:5672 rabbitmq:management    

9、clickhouse
       
    docker run -d --name some-clickhouse-server -p 8123:8123 --ulimit nofile=262144:262144 yandex/clickhouse-server

10、docker拷贝文件命令
     
    docker cp 容器名:要拷贝的文件在容器里面的路径 要拷贝到宿主机的相应路径     将容器里面文件拷贝到宿主机
    docker cp 要拷贝到宿主机的相应路径 容器名:要拷贝的文件在容器里面的路径     将宿主机文件拷贝到容器中

    注：不管容器有没有启动，拷贝命令都会生效

11、docker-compose常用命令

      docker-compose up -d nginx            构建启动nignx容器
      docker-compose exec nginx bash        登录到nginx容器中
      docker-compose down                   停止并删除运行中的Compose应用
      docker-compose ps                     列出项目中目前的所有容器
      docker-compose start nginx            启动nginx容器
      docker-compose stop nginx             停止nginx容器 
      docker-compose restart nginx          重新启动nginx容器
      docker-compose rm                     用于删除已停止的 Compose 应用
      docker-compose top                    查看各个服务容器内运行的进程
      docker-compose logs -f nginx          查看nginx的实时日志

      注：docker-compose后面的命令如果不指定具体的容器名称，则会对docker-compose.yaml文件中的所有容器进行操作

12、Nginx常用命令
     
      ./nginx -t           验证nginx配置文件是否正确
      ./nginx -s reload    重启nginx服务
      ./nginx              启动nginx服务
      ./nginx -s stop      停止nginx服务
      ./nginx -c /etc/nginx/nginx.conf     使用指定的配置文件启动nginx
      ./nginx -t -c /etc/nginx/nginx.conf  测试检查配置文件是否存在语法错误
      ./nginx -v             显示版本信息   
      whereis nginx          查找nginx路径
      ps -ef | grep nginx    在进程列表中的master进程，即主进程号
 
13、TiDB快速安装命令
      
      #推荐集群安装：https://docs.pingcap.com/zh/tidb/dev/quick-start-with-tidb#Linux
      参考配置文件：topo.yaml 
      集群启动命令：tiup cluster start tidb-cluster  (tidb-cluster为集群名称)
      
      注：tiup playground只能运行一次，部署后session端口即服务停止
      
      #下载并安装TiUP
      curl --proto '=https' --tlsv1.2 -sSf https://tiup-mirrors.pingcap.com/install.sh | sh     

      #声明全局变量
      source ~/.bash_profile
      
      #在当前session执行以下命令启动集群
      tiup playground --host 0.0.0.0
      
      #也可以指定TiDB版本以及各组件实例个数，monitor表示同时部署监控组件
      #tiup playground v5.1.0 --db 2 --pd 3 --kv 3 --monitor 

### 使用kubernetes命令
 
1、查看dashboard令牌(最高权限)
       
     在node01节点上执行命令：kubectl describe secret admin-token-nr9r4 -n kube-system   
       
2、向master中添加节点
    
    使用命令查询当前token集合：kubeadm token list
    如果token都已经过期则创建新的token并打印出具体的添加命令：
        kubeadm token create --print-join-command
        
        注：该token的有效时间为2个小时，2小时内，您可以使用此token初始化任意数量的worker节点
    
    先将master中的~/.kube/config文件的内容复制到需要添加的node中，让node和master可以使用秘钥通信
    在执行命令添加节点：
        kubeadm join 192.168.0.3:6443 --token 8ln737.77grxng35yn6b263 --discovery-token-ca-cert-hash sha256:aada718a2aafdad65bd3dc241d394d64846a38047db10bf45f59c70d587ced74 

3、清理docker占用的磁盘空间
    
    docker system prune: 用于清理磁盘，删除关闭的容器、无用的数据卷和网络，无tag的镜像
    
    docker system prune -a: 可以将没有容器使用Docker镜像删掉
    
    查看文档：https://blog.csdn.net/ujm097/article/details/90402158

4、DockerFile
     
    VOLUME /tmp /usr/tmp     前者为宿主机目录，后者为容器目录；如果只指定一个目录，则会将宿主机的目录挂载到容器中相同的目录
        
    
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
    
### Linux 常用命令

    1、文件中查询关键字
        使用vi命令进入文件，输入 / 然后输入关键字，按回车即可查询，继续查找此关键字，敲字符n，敲字符N向前搜索  
    
    2、文件中显示行号
        显示所有行号： :set nu
    
    3、根据文件名称查询文件路径
        find / -name hanyi-web.jar    精确查询
        find / -name hanyi-web*       模糊匹配
    
    4、过滤出文件中关键字并显示行号,file指文件名称
        cat  file | grep -n 关键字 或者 tail -f file | grep -n 关键字
        查看日志文件：tail -1000f 日志文件名称
    
    5、使用端口号查询进程号
        lsof -i:端口号  或者 netstat -nlp | grep 端口号
    
    6、使用进程号查询路径,cwd后面的就是进程的路径
        ll /proc/进程号/cwd       
        
    7、查看系统资源
        磁盘空间： df -h
        内存：free -h 
        查看当前文件夹下各文件大小：du -sh *
    
    8、使用名称查询进程号,第一个数字为进程号
        ps -ef | grep hanyi-web | grep -v grep  
        
    9、在文件中删除行数据    
        删除单行数据：dd 
        删除光标下的多行数据：Ndd ,N表示需要删除的行数
        
            