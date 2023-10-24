# Mysql-SpringBoot-Vue3

### 使用

#### 方式一：

1. 安装 `JDK 17`，`Node.js 18`，`Mysql 8`，
2. 安装 `pnpm`
   ```bash
   npm install pnpm -g
   ```
3. 运行 `Mysql` ，导入 `./mysql-init/init.sql`
4. 运行 `SpringBoot` 后端
   ```bash
   java -jar ./release/backend-0.0.1-SNAPSHOT.jar
   ```
5. 运行 `Vue` 前端
   ```bash
   cd ./forntend
   pnpm install
   pnpm run dev
   ```
6. 访问 `http://localhost:8081`

#### 方式二：
1. 安装 `Docker`，并配置相关加速镜像源
2. 运行 `Docker` 部署命令
   ```bash
   docker compose up
   ```
3. 访问 `http://localhost:80`