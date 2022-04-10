<template>
  <!-- hidden PageHeaderWrapper title demo -->
  <page-header-wrapper :title="false" :content="$t('form.basic-form.basic.description')">
    <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
      <a-form>
        <a-tabs default-active-key="1" tab-position="left">
          <a-tab-pane key="1" :tab="$t('admin.user.profile.tab1')">
            <a-form-item
              :label="$t('from.user-profile.title.avatar')"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-upload
                name="avatar"
                list-type="picture-card"
                class="avatar-uploader"
                :show-upload-list="false"
                action="/api/admin/api/updateUserAvatar"
                :before-upload="beforeUpload"
                @change="handleUploadAvatar"
              >
                <img v-if="!!avatarUrl" :src="avatarUrl" alt="avatar" style="width:100%; height: 100%;" />
                <div v-else>
                  <a-icon :type="loading ? 'loading' : 'plus'" />
                  <div class="ant-upload-text">
                    Upload
                  </div>
                </div>
              </a-upload>
            </a-form-item>
            <a-form-item
              :label="$t('form.user-profile.title.username')"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-input
                name="username"
                v-model="user.username"
                :placeholder="$t('form.basic-form.title.placeholder')" />
            </a-form-item>
            <a-form-item
              :label="$t('form.user-profile.title.staffname')"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-input
                name="staffname"
                v-model="user.staffname"
                :placeholder="$t('form.basic-form.title.placeholder')" />
            </a-form-item>
            <a-form-item
              label="可用性"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              :required="false"
            >
              <a-switch v-model="user.enable" />
            </a-form-item>
            <a-form-item
              label="锁定"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              :required="false"
            >
              <a-switch v-model="user.locked" />
            </a-form-item>
            <a-form-item
              label="最后登陆IP"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              :required="false"
            >
              {{ user.lastloginaddr }}
            </a-form-item>
            <a-form-item
              label="登陆次数 "
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              :required="false"
            >
              {{ user.logintime }}
            </a-form-item>
            <a-form-item
              :label="$t('user-profile-dep.title')"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              :required="false"
            >
              <a-tree-select
                v-model="user.depid"
                name="parentid"
                style="width: 100%"
                :dropdown-style="{ maxHeight: '400px', overflow: 'auto'}"
                :tree-data="selectDepTreeData"
                tree-default-expand-all/>
            </a-form-item>
          </a-tab-pane>
          <a-tab-pane key="2" :tab="$t('admin.user.profile.tab2')">
            <a-transfer
              :dataSource="allRoles"
              showSearch
              :targetKeys="userRoles"
              :render="item => item.title"
              @change="handleChange"
              :list-style="{
                width: '300px',
                height: '600px',
              }"
            />
          </a-tab-pane>
        </a-tabs>
        <a-form-item
          :wrapperCol="{ span: 24 }"
          style="text-align: center"
        >
          <a-button @click="handleSubmit" type="primary">{{ $t('form.basic-form.form.submit') }}</a-button>
          <a-button @click="toBackDepList" style="margin-left: 8px">{{ $t('form.basic-form.form.back') }}</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { fetchOneUser, saveUserInfo, getUserAvatar, getAllRoles } from '@/api/admin'
import { getListByParentId } from '@/api/dep'

export default {
  name: 'UserProfile',
  data () {
    return {
      user: {
        id: -1,
        username: '',
        staffname: '',
        locked: false,
        enable: true,
        avatar: '',
        lastloginaddr: '',
        logintime: 0,
        roles: [],
        depid: 0
      },
      selectDepTreeData: [],
      avatarUrl: '',
      allRoles: [],
      userRoles: [],
      loading: false
    }
  },
  mounted () {
    this.user.id = this.$route.params.id
    if (this.user.id) {
      this.fetchProfile(this.user.id)
    }
    getAllRoles().then(res => {
      this.allRoles = []
      if (res.status === 200) {
        let tempRoles = []
        res.data.sort((a, b) => a.rid - b.rid).forEach(role => {
          tempRoles = [...tempRoles, { key: role.rid.toString(), title: role.rname }]
        })
        if (this.allRoles) {
          this.allRoles = tempRoles
        }
      }
    })
    this.fetchDepList(null) // 部门树
  },
  methods: {
    // handler
    handleSubmit () {
      saveUserInfo(this.user).then(res => {
        // console.log(res)
        if (res.status === 200) {
          this.$message.success(`成功保存${res.data}条记录`)
          this.$router.push('/systemConfiguration/userlist')
        } else {
          this.$message.error(`保存失败`)
        }
      }).catch(e => {
        console.log(e)
        this.$message.error(`保存失败`)
      })
    },
    // fetchProfile
    fetchProfile () {
      fetchOneUser({
        id: parseInt(this.user.id)
      }).then(res => {
        if (res.data) {
          this.user.username = res.data.username
          this.user.enable = res.data.enable
          this.user.logintime = res.data.logintimes
          this.user.locked = res.data.locked
          this.user.lastloginaddr = res.data.lastloginaddr
          this.user.avatar = res.data.avatar
          this.user.roles = res.data.roles
          this.user.staffname = res.data.staffname
          this.userRoles = res.data.roles.map(role => role.rid.toString())
          this.user.depid = res.data.depid
          // 获取头像信息
          getUserAvatar(res.data.avatar).then(res => {
            // 这里就是将得到的图片流转换成blob类型
            const blob = new Blob([res], {
              type: 'image/jpeg'
            })
            const url = window.URL.createObjectURL(blob)
            this.avatarUrl = url
          }).catch(e => {
            console.log(e)
            this.$message.error('图像上传失败，Image upload failed!')
          })
        }
      }).catch(e => {
        console.log(e)
        this.$message.error('请求失败，Request failed!')
      })
    },
    // 上传图片
    handleUploadAvatar (info) {
      console.log(info)
      if (info.file.status !== 'uploading') {
        const resp = info.file.response
        if (resp.status !== 200) {
          this.loading = true
          this.$message.error('图像上传失败，Image upload failed!')
        } else {
          this.user.avatar = resp.data
          getUserAvatar(resp.data).then(res => {
            // 这里就是将得到的图片流转换成blob类型
            const blob = new Blob([res], {
              type: 'image/jpeg'
            })
            const url = window.URL.createObjectURL(blob)
            this.avatarUrl = url
          }).catch(e => {
            console.log(e)
            this.$message.error('图像上传失败，Image upload failed!')
          })
        }
      } else {
        this.loading = true
      }
    },
    beforeUpload (file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('Image must smaller than 2MB!')
      }
      return isJpgOrPng && isLt2M
    },
    handleChange (targetKeys, direction, moveKeys) {
      console.log(targetKeys, direction, moveKeys)
      this.userRoles = targetKeys
      this.user.roles = []
      targetKeys.forEach(keys => {
        this.user.roles = [...this.user.roles, { rid: Number(keys) }]
      })
    },
    fetchDepList (parentId) {
      getListByParentId({
        parentid: parentId
      }).then(res => {
        if (res.status === 200) {
          this.selectDepTreeData = this.genDepTreeNode(res.data, parentId)
        }
      }).catch(e => {
        console.log(e)
      })
    },
    genDepTreeNode (oriList, parentId) {
      var resList = []
      const filterList = oriList.filter(dep => dep.parentid === parentId)
      filterList.forEach(dep => {
        const treeNode = {
          title: dep.name,
          value: dep.id,
          key: dep.id,
          children: this.genDepTreeNode(oriList, dep.id)
        }
        resList.push(treeNode)
      })
      return resList
    },
    toBackDepList () {
      this.$router.push('/systemConfiguration/userlist')
    }
  }
}
</script>
