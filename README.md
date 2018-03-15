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
RABBIT_URI=amqp://guest:guest@localhost:5672  STORAGE_TYPE=mysql MYSQL_DB=ag_zipkin MYSQL_USER=root MYSQL_PASS=love100200 MYSQL_HOST=localhost MYSQL_USE_SSL=false nohup java -jar zipkin.jar > ~/Desktop/test.txt &
```
- 启动服务器（依次）

register-center : 8761
```
docker run -p 8761:8761 -d sc/register-center
```

auth-server : 9777
```
docker run -p 9777:9777 -d sc/auth-server
```

web-admin : 8762
```
docker run -p 8762:8762 -d sc/web-admin
```

gateway-server : 8765
```
docker run -p 8765:8765 -d sc/gateway-server
```

monitor(可选, port:8764)、generator(可选, port:7777)、sidecar(可选, port:5680)

- 启动前端
```
cd ~/Documents/Hins/Projects/web-admin
npm run dev
```
