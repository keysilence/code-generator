FROM hub.c.163.com/library/tomcat:8-jre8-alpine

WORKDIR /usr/local/tomcat

RUN rm -rf /usr/local/tomcat/webapps/ROOT/*

ADD spring-mybatis.war /usr/local/tomcat/webapps/ROOT

RUN cd /usr/local/tomcat/webapps/ROOT && unzip spring-mybatis.war && \
    rm -f spring-mybatis.war && ls -lha && \
    echo `date +%Y-%m-%d:%H:%M:%S` > /etc/docker.build