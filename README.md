##### 1.项目架构图
  
- ├─technical-forum-category #博客分类模块 （文章信息爬取包含在内，后面打算拆分出来）
- ├─technical-forum-common #项目通用模块
- ├─technical-forum-gateway #gateway网关模块
- ├─technical-forum-article #文章模块  
- ├─technical-forum-user # 用户模块（正在完善）

##### 2.技术选型

- 微服务框架：SpringCloud Alibaba
  
- 配置中心和注册中心：Nacos
- 网关：Gateway
- 持久层：Mybatis-plus
- 数据库：Mysql、Mongodb集群
- 消息中间件：Rocketmq
- 缓存数据库：Redis哨兵集群
- 搜索引擎：Elasticsearch集群
- 前端框架&UI框架：Vue、Ant Design Vue 

##### 3.使用说明

- 配置好Mongodb和Mysql的地址
- 目前主要完善的是博客分类模块，通用模块以及网关模块，可以先拉取网关模块
- 运行web文件夹内的前端项目，打开运行后台
- 依次点击同步博客分类信息、同步文章概要信息、同步文章信息
  - ![image](https://github.com/partick33/technical-forum/blob/master/img/img.png)

##### 4.效果演示
- ![image](https://github.com/partick33/technical-forum/blob/master/img/img_1.png)
- ![image](https://github.com/partick33/technical-forum/blob/master/img/img_2.png)
- ![image](https://github.com/partick33/technical-forum/blob/master/img/img_3.png)
- ![image](https://github.com/partick33/technical-forum/blob/master/img/img_4.png)
- ![image](https://github.com/partick33/technical-forum/blob/master/img/img_5.png)

#### 5.最后
- 本项目仅供自身学习使用
