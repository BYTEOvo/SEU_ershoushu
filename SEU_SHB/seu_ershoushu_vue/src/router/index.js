import Vue from 'vue'
import Router from 'vue-router'
import AppIndex from '../components/home/AppIndex'
import AdminIndex from '../components/admin/AdminIndex'
import Login from '../components/Login'
import Register from '../components/Register'
import Home from '../components/Home'
import HelloWorld from '../components/HelloWorld'
import LibraryIndex from '../components/library/LibraryIndex'
import ManageBook from '../components/home/managebook'
import PersonalInformation from '../components/home/personalinformation'
import BuyList from '../components/home/BuyList'
import SellList from '../components/home/SellList'
import Order from '../components/library/Order'
import HandleComplaint from '../components/admin/HandleComplaint'
import UserControl from '../components/admin/UserControl'
import OrderControl from '../components/admin/OrderControl'
import BookControl from '../components/admin/BookControl'
import DashBoard from '../components/admin/DashBoard'

Vue.use(Router)
const originalPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/home',
      name: 'Home',
      component: Home,
      redirect: '/index',
      children: [
        {
          path: '/userindex',
          name: 'AppIndex',
          component: AppIndex,
          children: [
            {
              path: '/managebook',
              name: 'ManageBook',
              component: ManageBook
            },
            {
              path: '/personalinformation',
              name: 'PersonalInformation',
              component: PersonalInformation
            },
            {
              path: '/orderlist/buy',
              name: 'BuyList',
              component: BuyList,
              meta: {
                roles: ['admin']
              }
            },
            {
              path: '/orderlist/sell',
              name: 'SellList',
              component: SellList
            }
          ]
        },
        {
          path: '/library',
          name: 'Library',
          component: LibraryIndex
        },
        {
          path: '/adminindex',
          name: 'AdminIndex',
          component: AdminIndex,
          children: [
            {
              path: '/dashboard',
              name: DashBoard,
              component: DashBoard
            },
            {
              path: '/bookcontrol',
              name: 'BookControl',
              component: BookControl
            },
            {
              path: '/ordercontrol',
              name: 'OrderControl',
              component: OrderControl
            },
            {
              path: '/usercontrol',
              name: 'UserControl',
              component: UserControl
            },
            {
              path: '/handlecomplaint',
              name: 'HandleComplaint',
              component: HandleComplaint
            }
          ]
        }
      ]
    },
    {
      path: '/order',
      name: 'Ordrer',
      component: Order
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    }
  ]
})
