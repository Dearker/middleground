#!/bin/sh
echo "======== start clean docker containers logs ========"

logs=$(find /var/lib/docker/containers/ -name *-json.log)

for log in $logs
        do
                echo "clean logs : $log"
                cat /dev/null > $log
        done

echo "======== end clean docker containers logs ========"

##sh脚本创建之后，通过如下目录修改sh脚本的执行权限，再执行./clean_docker_log.sh
## chmod +x clean_docker_log.sh

##参考文档地址：http://www.voidcn.com/article/p-tqhqiaif-bsb.html
