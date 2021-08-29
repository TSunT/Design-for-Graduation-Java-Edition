import storage from 'store'
import { login, getInfo } from '@/api/login'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { welcome } from '@/utils/util'
import { getUserAvatar } from '@/api/admin'

const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          const result = response.data
          if (response.status === 200) {
            storage.set(ACCESS_TOKEN, result.access_token, 7 * 24 * 60 * 60 * 1000)
            commit('SET_TOKEN', result.access_token)
            resolve()
          } else {
            throw new Error(response.msg)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const result = response.data

           if (result.roles && result.roles.length > 0) {
            const role = result.roles
            role.permissions = result.roles
            /* role.permissions.map(per => {
              if (per.actionEntitySet != null && per.actionEntitySet.length > 0) {
                const action = per.actionEntitySet.map(action => { return action.action })
                per.actionList = action
              }
            }) */
            role.permissionList = role.permissions.map(permission => { return permission.rname })
            commit('SET_ROLES', role.permissionList)
            commit('SET_INFO', result)
          } else {
            reject(new Error('getInfo: roles must be a non-null array !'))
          }
          commit('SET_NAME', { name: result.username, welcome: welcome() })
          // 获取头像信息
          getUserAvatar(result.avatar).then(res => {
            // 这里就是将得到的图片流转换成blob类型
            const blob = new Blob([res], {
              type: 'image/jpeg'
            })
            const url = window.URL.createObjectURL(blob)
            commit('SET_AVATAR', url)
          }).catch(e => {
            console.log(e)
            this.$message.error('图像上传失败，Image upload failed!')
          })
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit, state }) {
      return new Promise((resolve) => {
        /* logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          storage.remove(ACCESS_TOKEN)
          resolve()
        }).catch(() => {
          resolve()
        }).finally(() => {
        }) */
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        storage.remove(ACCESS_TOKEN)
        resolve()
      })
    }

  }
}

export default user
