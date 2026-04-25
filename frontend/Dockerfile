ARG NODE_IMAGE=node:20-alpine
ARG NGINX_IMAGE=nginx:1.27-alpine
ARG NPM_REGISTRY=https://registry.npmmirror.com

FROM ${NODE_IMAGE} AS build
WORKDIR /workspace/site

COPY site/package.json site/package-lock.json ./
RUN npm ci --registry=${NPM_REGISTRY}

COPY site ./
RUN npm run build

FROM ${NGINX_IMAGE}
WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

COPY admin ./admin
COPY --from=build /workspace/public ./public
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
