# 使用khipu/openjdk17-alpine作为基础镜像
FROM maven:3.9.4-eclipse-temurin-17-alpine

# 在容器中创建一个目录来存放我们的backend.jar
WORKDIR /app

# 将backend.jar复制到容器中的/app目录
COPY . /app

RUN cp ./maven-settings/settings.xml /usr/share/maven/conf/settings.xml \
    && mvn package -P docker

CMD java -jar ./target/backend-0.0.1-SNAPSHOT.jar


# docker build -t tt01:v0.0.1 ./backend


# docker build -t backend-t01:0.0.1 .

# docker run -d --name bk01 -p 8080:8080 backend-t01:0.0.1

# docker rm bk01 -f

# docker rmi backend-t01:0.0.1