# docker-user-service

用户服务，依赖`MySQL`及`Redis`服务

#### 参考资料
- https://docs.docker.com/compose/
- https://docs.docker.com/compose/compose-file/

#### MySQL初始化
> MySQL官方镜像中提供了容器启动时自动执行 `/docker-entrypoint-initdb.d` 文件夹下的脚本的功能(包括`shell`脚本和`sql`脚本)，因此只需要将脚本文件放在该目录下，即可自动初始化

#### WEB URI
- http://192.168.0.200:10086/actuator/health
- http://192.168.0.200:10086/1