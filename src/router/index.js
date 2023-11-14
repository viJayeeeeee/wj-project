import Vue from 'vue'
import Router from 'vue-router'
// import AppIndex from '../components/home/Appindex'
// import Login from '../components/Login'
// import Home from '../components/Home'
// import LibraryIndex from '../components/library/LibraryIndex'
// import Register from '../components/Register'
// import AdminIndex from '@/components/admin/AdminIndex.vue'
// import ArticleEditor from '@/components/admin/content/ArticleEditor.vue'
// import Articles from '@/components/jotter/Articles.vue'
// import ArticleDetails from '@/components/jotter/ArticleDetails.vue'

Vue.use(Router)

const CreateRouter = () => new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      redirect: '/index',
      component: () => import('../components/home/Appindex'),
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/home',
      name: 'Home',
      component: () => import('@/components/home'),
      redirect: '/index',
      children: [
        {
          path: '/index',
          name: 'AppIndex',
          component: () => import('../components/home/Appindex'),
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/jotter',
          name: 'Jotter',
          component: () => import('@/components/jotter/Articles'),
          meta: {
            requireAuth: true
          }
        },
        {
          path: '/jotter/article',
          name: 'Article',
          component: () => import('@/components/jotter/ArticleDetails')
        },
        {
          path: '/library',
          name: 'Library',
          component: () => import('@/components/library/LibraryIndex'),
          meta: {
            requireAuth: true
          }
        }
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/components/Login')
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('@/components/Register')
    },
    {
      path: '/admin',
      name: 'Admin',
      component: () => import('@/components/admin/AdminIndex.vue'),
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/admin/content/editor',
      name: 'Editor',
      component: () => import('@/components/admin/content/ArticleEditor.vue'),
      meta: {
        requireAuth: true
      }
    }
  ]
})

const router = new CreateRouter()

export function resetRouter() {
  const newRouter = CreateRouter()
  router.matcher = newRouter.matcher
}

export default router
