# 工程的APP接口模块

## 整体工程相关的模块如下
1. [xxd-api-common](http://gitlab.xxd.com/fe/xxd-api-common)，工程的公共模块。
2. [xxd-api-bizservice](http://gitlab.xxd.com/fe/xxd-api-bizservice)，工程的业务模块。
3. [xxd-api-app](http://gitlab.xxd.com/fe/xxd-api-app)，app的api接口模块。
4. [xxd-api-h5](http://gitlab.xxd.com/fe/xxd-api-h5)，h5的api接口模块。
5. [xxd-web-h5](http://gitlab.xxd.com/fe/xxd-web-h5)，h5的web工程模块。

## 项目的部署步骤

1. 如果是更改**api-common**、**api-bizservice**模块相关的内容需要开发人员升级对应模块
pom.xml文件里面的版本号，发布到公司私有maven仓库，并将新版本的jar维护到对应的工程里，然后
借助公司的CI环境发布。

2. 如果是更改**xxd-api-app**、**xxd-api-h5**、**xxd-web-h5**模块相关的内容，直接走正常
的发布流程即可。

## 版本升级注意事项
1. **api-common**、**api-bizservice**模块开发使用SNAPSHOT版本开发，每次上线时版本向上叠加并且切换为RELEASE。
2. 小版本升级、bug fix末尾版本号叠加(10进1)，大版本升级或者架构调整中间版本号叠加(10进1)。