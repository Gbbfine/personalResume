# Personal Resume Website

前后端分离的个人简历网站（单页展示 + 简易后台管理）。

## 1. 项目结构

- `backend/` Spring Boot 后端（Java 17）
- `frontend/public/` 前台展示页面（原生 HTML/CSS/JS）
- `frontend/admin/` 后台管理页面（原生 HTML/CSS/JS）
- `database/` MySQL 建表与初始化脚本（8 张基础表）
- `docs/` API 文档

## 2. 快速启动

### 2.1 准备数据库

1. 创建数据库：`personal_resume`
2. 执行脚本：`database/schema.sql`
3. 可选执行示例数据：`database/seed.sql`
4. 如需将现有头像/简历链接切换到文件托管仓库，执行：`database/update_profile_asset_urls.sql`
5. 数据库使用说明：`database/USAGE.md`

### 2.2 启动后端

```bash
cd backend
mvn spring-boot:run
```

默认端口：`8080`

如果你本机 Maven 默认仓库路径不可用，可使用项目内设置文件：

```bash
cd backend
mvn -s maven-settings.xml spring-boot:run
```

默认管理员账号（首次启动自动创建）：

- 用户名：`admin`
- 密码：`admin123`

可通过环境变量覆盖：

- `APP_ADMIN_DEFAULT_USERNAME`
- `APP_ADMIN_DEFAULT_PASSWORD`
- `APP_JWT_SECRET`
- `DB_DDL_AUTO`（建议默认 `none`，仅本地快速调试可设为 `update`）

### 2.3 打开前端

公开页已升级为 React + Tailwind + GSAP + Framer Motion + Three.js（轻量 3D 背景层），源码在 `frontend/site`。

先安装依赖并构建：

```bash
cd frontend/site
npm install
npm run build
```

构建产物会输出到 `frontend/public`。

然后用静态服务器托管 `frontend/`，例如：

```bash
# 在 frontend 目录
python -m http.server 5500
```

访问：

- 前台：`http://localhost:5500/public/index.html`
- 后台：`http://localhost:5500/admin/login.html`

### 2.4 前台开发与视觉验证（可选）

```bash
cd frontend/site
npm run dev
```

Playwright 桌面视觉截图：

```bash
cd frontend/site
npm run test:visual
```

该脚本会同时验证桌面端与移动端降级布局。

## 3. API 概览

- 鉴权：`/api/auth/*`
- 公开展示：`/api/public/*`
- 管理后台：`/api/admin/*`

统一返回：

```json
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

详细接口见：`docs/api.md`

部署说明见：`docs/deploy.md`
