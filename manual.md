# Express Server-side manual

## Deployment

### Requirement

要完成部署, 你需要以下这些组件:

1. 一个 JDK, OpenJDK 或者 Oracle 的均可, 1.6 以上.
2. 一个 JavaWeb 容器, Tomcat 或者 Jetty 均可, 需要支持 JSP 2.5
3. 一个关系数据库, 随便哪种都可以.
4. (可选)git, 如果不打算从 Github 拷贝代码, 则不需要.
5. (可选)ant, 如果使用预编译的二进制包部署, 则不需要.

### Environtment Preparation

1. 首先, 安装 JDK, 配置 `JAVA_HOME` 和 `CLASS_PATH` 环境变量, 详请自行 Google.
2. 安装 JavaWeb 容器, 此处以 Tomcat@Windows 为例:
  2.1. 下载对应解压式安装包(通常选最新的), 注意位宽要与 JDK 的位宽一致
  2.2. 随便解压到什么地方, 比如 `D:\Programs`
  2.3. 配置环境变量 `CATALINA_HOME=D:\Programs\apache-tomcat-x.y.z`
  2.4. 添加环境变量 `PATH+=%CATALINA_HOME%\bin`
  2.5. (验证) 执行 `catalina version`
3. 安装关系数据库, [附录1](#appendix-1)以MySQL为例
4. 安装 git (可选)
  4.1. 去 [http://git-scm.org](http://git-scm.org) 下载 Windows 用安装包.
  4.2. 接受默认设定即可
  4.3. (验证) 执行 `git version`
5. 安装 ant (可选)
  5.1. 去 [http://ant.apache.org](http://ant.apache.org) 下载 Windows 的 archive.
  5.2. 随便解压到什么地方, 比如 `D:\Programs`
  5.3. 配置环境变量 `ANT_HOME=D:\Programs\apache-ant-x.y.z`
  5.4. 田间环境变量 `PATH+=%ANT_HOME%\bin`
  5.5. (验证) 执行 `ant -version`

Install from source

1. 选个很帅的项目文件夹, 比如 `D:\Project`
2. `cmd` 到那里
3. 执行 `git clone https://github.com/kevenkid/express.git`
4. 配置数据库连接
  4.1.
4. 执行 `ant `