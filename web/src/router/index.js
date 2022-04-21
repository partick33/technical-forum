/*
 * @Author: your name
 * @Date: 2022-04-17 18:45:47
 * @LastEditTime: 2022-04-21 11:54:55
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \web\src\router\index.js
 */
import { createRouter, createWebHashHistory } from 'vue-router'
import index from '../views/index.vue'
import search from '../views/search.vue'
import dataStatistics from '../views/dataStatistics.vue'

const routes = [
  {
    path: '/',
    name: 'index',
    component: index
  },
  {
    path: '/search',
    name: 'search',
    component: search
  },
  {
    path:'/dataStatistics',
    name: 'dataStatistics',
    component: dataStatistics
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
