#!/bin/bash

# 应用名称可自定义
AppName=sentinel-dashboard-1.7.2.jar

#JVM启动参数
JVM_OPTS="-Dname=$AppName -Duser.timezone=Asia/Shanghai -Xms512M -Xmx512M -Xmn256M -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=256M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+UseParallelGC -XX:+UseParallelOldGC"

if [ "$1" = "" ]; then
  #加 -e 表示允许字符串中的内容进行转义
  echo -e "\033[0;31m Please input your operation \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
  exit 1
fi

#判断当前系统
darwin=false
linux=false
case "$(uname)" in
Darwin)
  darwin=true
  echo "current environment is Darwin , the darwin is $darwin"
  ;;
Linux)
  linux=true
  echo "current environment is Linux , the linux is $linux"
  ;;
esac

# getops 表示脚本启动时可指定的参数标识
MODE="cluster"
FILE="all"
while getopts ":m:f:" opt; do
  case $opt in
  m)
    MODE=$OPTARG
    ehco "MODE is $MODE"
    ;;
  f)
    FILE=$OPTARG
    echo "FILE is $FILE"
    ;;
  # ？表示输入的不是m和f则走着一行
  ?)
    echo "Unknown parameter"
    exit 1
    ;;
  esac
done

function start() {
  if [ x"$PID" != x"" ]; then
    echo "$AppName is running..."
  else
    nohup java -jar $JVM_OPTS $AppName >/dev/null 2>&1 &
    echo "Start $AppName success..."
  fi
}

function stop() {
  echo "Stop $AppName"

  if [ x"$PID" != x"" ]; then
    kill -TERM $PID
    echo "$AppName (pid:$PID) exiting..."
    while [ x"$PID" != x"" ]; do
      sleep 1
      query
    done
  else
    echo "$AppName already stopped."
  fi
}

function restart() {
  stop
  sleep 2
  start
}

function status() {
  # -n 表示字符串是否为非空
  if [ -n "$PID" ]; then
    echo "$AppName is running..."
  else
    echo "$AppName is not running..."
  fi
}

function error_exit() {
  echo "ERROR: $1 !!"
  exit 1
}

#进程id变量,函数需要先声明，再调用，shell脚本是从上向下执行
PID=""
JAVA_PATH=""
function query() {
  PID=$(ps ax | grep -i $AppName | grep java | grep -v grep | awk '{print $1}')
  if $darwin; then
    JAVA_PATH=$(/usr/libexec/java_home)
  else
    DIR_PATH=$(dirname $(readlink -f $(which javac)))
    if [ "x$DIR_PATH" != "x" ]; then
      JAVA_PATH=$(dirname $DIR_PATH 2>/dev/null)
    fi
  fi
}

query

#判断jdk路径是否为空
if [ -z "$JAVA_PATH" ]; then
  error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better!"
fi

case $1 in
start)
  start
  ;;
stop)
  stop
  ;;
restart)
  restart
  ;;
status)
  status
  ;;
*)
  echo -e "\033[0;31m Please input your operation \033[0m  \033[0;34m {start|stop|restart|status} \033[0m"
  ;;
esac
