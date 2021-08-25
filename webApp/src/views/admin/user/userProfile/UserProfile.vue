<template>
  <!-- hidden PageHeaderWrapper title demo -->
  <page-header-wrapper :title="false" :content="$t('form.basic-form.basic.description')">
    <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
      <a-form @submit="handleSubmit" :form="form">
        <a-form-item
          :label="$t('form.basic-form.title.label')"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
          <a-input
            name="username"
            v-model="username"
            :placeholder="$t('form.basic-form.title.placeholder')" />
        </a-form-item>
        <a-form-item
          label="可用性"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
          :required="false"
        >
          <a-switch v-model="enable" />
        </a-form-item>
        <a-form-item
          label="锁定"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
          :required="false"
        >
          <a-switch v-model="locked" />
        </a-form-item>
        <a-form-item
          label="最后登陆IP"
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
          :required="false"
        >
          {{ lastloginaddr }}
        </a-form-item>
        <a-form-item
          label="登陆次数 "
          :labelCol="{lg: {span: 7}, sm: {span: 7}}"
          :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
          :required="false"
        >
          {{ logintime }}
        </a-form-item>
        <a-form-item
          :wrapperCol="{ span: 24 }"
          style="text-align: center"
        >
          <a-button @click="handleSubmit" type="primary">{{ $t('form.basic-form.form.submit') }}</a-button>
          <a-button style="margin-left: 8px">{{ $t('form.basic-form.form.save') }}</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { fetchOneUser } from '@/api/admin'

export default {
  name: 'UserProfile',
  data () {
    return {
      id: null,
      username: '',
      locked: false,
      enable: true,
      avatar: '',
      lastloginaddr: '',
      logintime: 0
    }
  },
  mounted () {
    this.id = this.$route.params.id
    console.log(this.id)
    this.fetchProfile(this.id)
  },
  methods: {
    // handler
    handleSubmit (e) {
      e.preventDefault()
    },
    // fetchProfile
    fetchProfile () {
      fetchOneUser({
        id: parseInt(this.id)
      }).then(res => {
        this.username = res.data.username
        this.enable = res.data.enable
        this.logintime = res.data.logintimes
        this.locked = res.data.locked
        this.lastloginaddr = res.data.lastloginaddr
        this.avatar = res.data.avatar
      }).catch(e => {
        console.log(e)
        this.$message.error('请求失败，Request failed!')
      })
    }
  }
}
</script>
