FROM java:8
VOLUME /tmp
# 将打包好的 jar 复制到 app.jar 中
ADD nathan-api.jar CooperationProject.jar
EXPOSE 9987
# 以下为 jar 包启动命令配置
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+8", "-jar", "CooperationProject.jar"]