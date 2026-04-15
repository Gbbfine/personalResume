# 部署说明（最简版）

## 1. 后端部署

1. 准备 MySQL 8，执行 `database/schema.sql`（可选再执行 `database/seed.sql`）。
2. 配置环境变量：
   - `DB_URL`
   - `DB_USERNAME`
   - `DB_PASSWORD`
   - `APP_JWT_SECRET`（建议 32 位以上）
3. 打包并启动：

```bash
cd backend
mvn clean package -DskipTests
java -jar target/resume-backend-1.0.0.jar
```

## 2. 前端部署

将 `frontend/public` 与 `frontend/admin` 作为静态资源目录部署到 Nginx。

示例：
- `https://your-domain.com/` -> `frontend/public/index.html`
- `https://your-domain.com/admin/login.html` -> 后台入口

## 3. 反向代理（可选）

若前后端同域部署，可将 `/api/` 代理到后端 `8080` 端口，减少跨域配置。

## 4. 上线前检查

- 登录后台可成功获取 token。
- 后台新增/修改内容后，前台能刷新看到最新数据。
- 公开接口不需要 token，管理接口必须带 token。
