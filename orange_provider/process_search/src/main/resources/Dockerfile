FROM java:8
VOLUME /tmp
ADD orange-provider-solr.jar app.jar
EXPOSE 8001
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]

#设置时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone