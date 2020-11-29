# 性能调优参数列表

| 参数名称             | 含义                   | 默认值                           |
| -------------------- | ---------------------- | -------------------------------- |
| -Xms                 | 初始堆大小             | 物理内存的1/64(<1GB)             |
| -Xmx                 | 最大堆大小             | 物理内存的1/4(<1GB)              |
| -XX:MetaspaceSize    | 元空间 (永久代) 初始值 | 默认范围在12M~20M                |
| -XX:MaxMetaspaceSize | 元空间 (永久代) 最大值 | 默认无上限                       |
| -Xmn                 | 年轻代大小             | Xmx 的 1/4,推荐配置为整个堆的3/8 |
| -XX:-UseLargePages   | 使用大页面内存         |                                  |
| -XX:+UseG1GC         | 使用G1垃圾回收器       |                                  |

注：jvm内存分区：堆内存 = 年轻代(Eden 、 2个Survivor)  + 老年代 + 元空间 



# 调试参数列表

| 参数名称                        | 含义                                | 默认值                                                       |
| ------------------------------- | ----------------------------------- | ------------------------------------------------------------ |
| -XX:+HeapDumpOnOutOfMemoryError | 当首次遭遇OOM时导出此时堆中相关信息 | 禁用                                                         |
| -XX:+PrintGCDateStamps          | 允许在每个GC上打印日期戳            | 禁用                                                         |
| -XX:HeapDumpPath                | 堆转储的路径和文件名                | 该文件在当前工作目录中创建，并且名为java_pidpid.hprof，其中pid是导致错误的进程的标识符 |
| -XX:+PrintGCDetails             | 允许在每个GC上打印详细消息          | 禁用                                                         |
| -XX:-OmitStackTraceInFastThrow  | 始终抛出含堆栈的异常                | 开启，即对于一些频繁抛出的异常，在一段时间内，只抛出没有堆栈的异常信息 |
| -XX:+PrintGCTimeStamps          | 打印每次GC的时间戳                  | 禁用                                                         |
| -XX:+UseGCLogFileRotation       | GC日志文件的自动转储                | 禁用                                                         |
| -XX:NumberOfGCLogFiles=10       | 设置滚动日志文件个数                |                                                              |
| -XX:GCLogFileSize=100M          | 设置滚动日志文件大小                |                                                              |

注：加号表示启用，减号表示关闭

