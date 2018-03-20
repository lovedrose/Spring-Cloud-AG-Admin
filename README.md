### mvn命令
- 清理
```
mvn clean
```
- 编译
```
mvn install
```
- 打包（docker）
```
mvn package docker:build
```

###启动步骤
- 启动redis
```
cd /usr/local/redis/bin
./redis-server
```
- 启动rabbitMQ
```
cd /usr/local/rabbitmq/sbin
./rabbitmq-server
```
- 启动zipkin(readme)
```
RABBIT_URI=amqp://guest:guest@localhost:5672  STORAGE_TYPE=mysql MYSQL_DB=ag_zipkin MYSQL_USER=root MYSQL_PASS=love100200 MYSQL_HOST=localhost MYSQL_USE_SSL=false java -jar zipkin.jar
```
- 启动服务器

```
docker-compose up -d
docker-compose logs -f
```
monitor(可选, port:8764)、generator(可选, port:7777)、sidecar(可选, port:5680)

- 启动前端
```
cd ~/Documents/Hins/Projects/web-admin
npm run dev
```
