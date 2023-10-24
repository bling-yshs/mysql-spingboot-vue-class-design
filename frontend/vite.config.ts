import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import Components from 'unplugin-vue-components/vite';
import {AntDesignVueResolver} from 'unplugin-vue-components/resolvers';
import vue from '@vitejs/plugin-vue'

export default defineConfig({
    plugins: [
        vue(),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    server: {
        port: 8081,
        open: true,
        proxy: {
            "/api": {
                target: "http://localhost:8080",
                changeOrigin: true,
            }
        }
    }
});