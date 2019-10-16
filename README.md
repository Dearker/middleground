#middleground
#所有应用的端口都需以800开头，如网关端口为8000,admin端口为8001，以此类推，后期方便管理，应用的端口可统一写在该文件中

#所有应用的nacos和sentinel、日志、zipkin、adminClient、端口、应用名称、相对路径这些通用的配置都需要写在bootstrap.yml中
#方便统一管理


#hanyi-common该工程中整理好了全局异常类、Feign的拦截器、请求和响应的包装类;nacos的服务发现和配置中心的依赖
#还有sentinel、swagger、zipkin、hystrix、log4j、lombok等相关依赖全部在这里面，其他工程无需重复引入


#hanyi-gateway该工程整理好了网关，网关这块做了统一的处理，通过应用的服务名称进行调整，可以进行统一的管理;望
#后期尽量不要改动，容易造成混乱

#hanyi-utils该工程整合了通用的一些工具类和相关依赖，后期可以进行完善;主要有分布式id生成器：雪花算法、IdWorker等
#md5工具类、httpClient工具类、反射工具类、Bcrypt加密工具类、cookie工具类、zip工具类等