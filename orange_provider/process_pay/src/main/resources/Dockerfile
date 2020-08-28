FROM java:8
# 挂载/usr/local/bhhello目录到主机
COPY cert /usr/resources
ADD *.jar app.jar
EXPOSE 8005
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]

#设置时区
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone