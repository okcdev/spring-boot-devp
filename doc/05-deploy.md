# 软件发布
## devp-main发布
### 发布环境说明
devp-main是一个spring-boot工程，可以编译成可执行文件，
建议发布到Linux系统中，使用nginx进行WEB代理，
并采用systemd脚本实现自动启动，建议环境如下：

| 序号 | 类型 | 建议环境 | 备选环境 | 备注 |
| --- | --- | ------- | ------- | ---- |
| 1   | 操作系统 | CentOS7+ | uBuntu(LTS) | 不推荐使用windows |
| 2   | 数据库 | MySQL5.6+ | MariaDB10.0+ | |
| 3   | JDK | OracleJDK8 | OpenJDK8 | 暂时不推荐使用JDK9 |
| 4 | WEB代理 | nginx | Apache | |

### 打包发布流程
#### 程序打包
##### 程序打包前的必要配置
主要修改 devp-main/src/main/resources/application.properties文件，具体要修改的配置如下：

* spring.datasource.url：服务器数据库URL
* spring.datasource.username: 服务器数据库用户名
* spring.datasource.password: 服务器数据库密码
* logging.level.com.rbs.cn.rest: 本工程的日志级别，生产系统建议配置为INFO，测试建议使用DEBUG
* logging.file: 服务器上日志文件地址
* server.port：使用的端口
* server.context-path: 本应用的context-path（URI）

##### 程序打包
在工程主文件夹下直接使用mvn命令即可完成打包：

~~~bash
mvn package
~~~

> 打包后生成的jar文件位于：devp-main/target文件夹下

#### 发布前的环境准备
##### 安装JDK（以CentOS下安装openjdk为例）

~~~bash
sudo yum install java-1.8.0-openjdk-devel
~~~

##### 安装nginx（以CentOS为例）

~~~bash
sudo yum install nginx
~~~

##### 配置防火墙，允许80端口访问

~~~bash
sudo firewall-cmd --zone=public --add-service=http
sudo firewall-cmd --zone=public --add-service=http --permanent
~~~

> 注：第一条命令为打开80端口，第二条将配置写入到配置文件中

##### 关闭selinux

~~~
sudo setenforce 0
sudo sed -i 's/SELINUX=enforcing/SELINUX=disabled/'
~~~
> 注1：第一条命令临时关闭selinux，第二条将配置写入到配置文件中

> 注2：关闭selinux主要是默认限制了nginx的端口转发，也可以通过修改selinux配置实现，
但是因为selinux经常引起一系列的其他权限问题，故此一般都将selinux禁用

#### 程序发布
将jar包复制到服务器

~~~bash
cd devp-main/target
scp devp-mian-<version>.jar  <user.name>@<server.name>:/home/<user.name>/
ssh <user.name>@<server.name>
sudo cp devp-mian-<version>.jar /usr/local/bin/devp-mian.jar
sudo chmod a+x /usr/local/bin/devp-mian.jar
~~~

#### 发布后的配置

##### 配置systemd配置文件
在服务器/usr/lib/systemd/system下创建devp-mian.service文件，内容如下：

~~~
[Unit]
Description=DEVP_MAIN

[Service]
User=nginx
ExecStart=/usr/local/bin/devp-mian.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
~~~

##### 配置nginx代理
修改/etc/nginx/nginx.conf文件，server章节内添加以下代码，实现代理程序：

~~~
        location /devp-main/ {
            proxy_pass http://localhost:8003/devp-main/ ;
        }
~~~

> 注：此配置中的8003端口和devp-main分别对应于前文application.properites中配置的端口和context-path


配置后的完整示例如下所示：

~~~
# For more information on configuration, see:
#   * Official English Documentation: http://nginx.org/en/docs/
#   * Official Russian Documentation: http://nginx.org/ru/docs/

user nginx;
worker_processes auto;
error_log /var/log/nginx/error.log;
pid /run/nginx.pid;

# Load dynamic modules. See /usr/share/nginx/README.dynamic.
include /usr/share/nginx/modules/*.conf;

events {
    worker_connections 1024;
}

http {
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile            on;
    tcp_nopush          on;
    tcp_nodelay         on;
    keepalive_timeout   65;
    types_hash_max_size 2048;

    include             /etc/nginx/mime.types;
    default_type        application/octet-stream;

    # Load modular configuration files from the /etc/nginx/conf.d directory.
    # See http://nginx.org/en/docs/ngx_core_module.html#include
    # for more information.
    include /etc/nginx/conf.d/*.conf;

    server {
        listen       80 default_server;
        listen       [::]:80 default_server;
        server_name  _;
        root         /usr/share/nginx/html;

        # Load configuration files for the default server block.
        include /etc/nginx/default.d/*.conf;

        location / {
        }

	location /devp-main/ {
		proxy_pass http://localhost:8003/devp-main/ ;
	}

        error_page 404 /404.html;
            location = /40x.html {
        }

        error_page 500 502 503 504 /50x.html;
            location = /50x.html {
        }
    }

}

~~~

##### 配置log日志目录文件夹权限

~~~
sudo mkdir /var/log/devp-main/
sudo chmod 777 /var/log/devp-main/
~~~

>此处的目录需要与前文application.properites中配置的log文件所在目录一致


#### 启动服务
##### 启动服务

~~~bash
sudo systemctl start nginx
sudo systemctl start devp-main
~~~

##### 将服务设置为自启动

~~~bash
sudo systemctl enable nginx
sudo systemctl enable devp-main
~~~