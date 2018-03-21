# 运行下载命令
```
$ curl -sSL https://zipkin.io/quickstart.sh | bash -s
$ java -jar zipkin.jar
```
# 启动命令
```
RABBIT_URI=amqp://guest:guest@localhost:5672  STORAGE_TYPE=mysql MYSQL_DB=ag_zipkin MYSQL_USER=root MYSQL_PASS=love100200 MYSQL_HOST=localhost MYSQL_USE_SSL=false java -jar zipkin.jar
```
# docker 启动命令
```
RABBIT_URI=amqp://lovedrose:love100200@localhost:5672  STORAGE_TYPE=mysql MYSQL_DB=ag_zipkin MYSQL_USER=root MYSQL_PASS=axJkamsUdvF3 MYSQL_HOST=gz-cdb-oamjd7cp.sql.tencentcdb.com MYSQL_PORT=63242 MYSQL_USE_SSL=false nohup java -jar zipkin.jar > /opt/logs/zipkin/log.txt &
```