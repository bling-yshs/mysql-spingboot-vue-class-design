FROM blingyshs/nginx-node-alpine:nginx1.24.0-node18.17.1-alpine3.18

WORKDIR /app

COPY . /app

RUN npm config set registry https://registry.npmmirror.com\
    && pnpm i\
    && pnpm run build\
    && cp -r dist/* /usr/share/nginx/html \
    && cp -r nginx-config/default.conf /etc/nginx/conf.d/default.conf