# 使用官方OpenJDK基础镜像（Alpine精简版）
FROM openjdk:11

# 设置工作目录
WORKDIR /app

# 复制构建好的JAR文件（需替换your-app.jar为实际文件名）
COPY target/self_warehouse-1.0.0-SNAPSHOT.jar selfWarehouse.jar

# 暴露端口（按需修改）
EXPOSE 8088

# 启动应用（可添加JVM参数）
CMD ["java", "-jar", "selfWarehouse.jar"]
