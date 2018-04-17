#!/bin/sh

echo "############## start ##############"

# 拉取代码
git pull

# 编译
mvn clean install -Dmaven.test.skip=true

# 拷贝新编译的spring-mybatis.war
cp -f spring-mybatis/target/spring-mybatis.war ./

# 设置镜像标签名
tag_name=code-generator:latest

# 删除本地已打镜像code-generator:latest
docker rmi -f ${tag_name}

# 构建镜像
docker build -f ./Dockerfile -t ${tag_name} .

# 登录网易云镜像服务器
docker login -u chenmohaha_2000@163.com -p cm13811666786 hub.c.163.com

# 修改标签
docker tag ${tag_name}:latest hub.c.163.com/keysilence/store/docker/${tag_name}:1.0.0

# 推到网易云
docker push hub.c.163.com/keysilence/store/docker/${tag_name}:1.0.0

#git clean -df

echo "############## end ##############"
