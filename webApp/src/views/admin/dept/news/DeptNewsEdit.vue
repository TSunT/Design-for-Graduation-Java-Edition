<template>
  <div>
    <a-form :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }" @submit="handleSubmitNews">
      <a-row :gutter="24">
        <a-col :span="8">
          <a-form-item
            :label="$t('dep-profile-news.edit.title')"
            :labelCol="{lg: {span: 7}, sm: {span: 7}}"
            :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
            <a-input
              name="newsTitle"
              v-model="depnews.newstitle"
              :placeholder="$t('form.basic-form.title.placeholder')" />
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item
            :label="$t('dep-profile-news.edit.newsDate')"
            :labelCol="{lg: {span: 7}, sm: {span: 7}}"
            :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
            <a-date-picker @change="onChangeDate" :value="depnews.newsdate"/>
          </a-form-item>
        </a-col>
        <a-col :span="8">
          <a-form-item>
            <a-button type="primary" :style="{ marginLeft: '8px' }" @click="handleSubmitNews">
              {{ $t('form.basic-form.form.submit') }}
            </a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <editor id="tinymce" v-model="depnews.newsdetail" :init="einit"></editor>
  </div>
</template>

<script>
// 导入富文本
import tinymce from 'tinymce/tinymce'
import Editor from '@tinymce/tinymce-vue'

// import 'tinymce/skins/ui/oxide/skin.min.css' // 富文本样式
import 'tinymce/icons/default' // 富文本样式
// 配置富文本
import 'tinymce/themes/silver' // 引入富文本的主要脚本

import { getOneNewsById, saveOneDepNews } from '@/api/dep'
import moment from 'moment/moment'

export default {
  name: 'DeptNewsEdit',
  data () {
    return {
      depnews: {
        id: '',
        newstitle: '',
        depid: '',
        newsdetail: '',
        newsdate: null
      },
      einit: {
        skin_url: require('@/assets/tinymce/skins/ui/oxide/skin.min.css'),
        language_url: require('@/assets/tinymce/langs/zh_CN.js'),
        // skin_url: '/assets/tinymce/skins/ui/oxide/skin.min.css',
        // language_url: '/assets/tinymce/langs/zh_CN.js',
        // branding: false, // 是否禁用“Powered by TinyMCE”
        language: 'zh_CN',
        toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media template link anchor codesample | ltr rtl',
        height: 500
      }
    }
  },
  components: { Editor },
  mounted () {
    this.depnews.depid = this.$route.params.depid
    this.depnews.id = this.$route.params.newsid
    tinymce.init({})
    if (this.depnews.id !== 'null') this.fetchNews()
  },
  methods: {
    handleSubmitNews () {
      saveOneDepNews(this.depnews)
    },
    onChangeDate (date, dateString) {
      console.log(date, dateString)
      this.depnews.newsdate = dateString
    },
    fetchNews () {
      getOneNewsById({ id: this.depnews.id }).then(res => {
        this.depnews.newsdate = moment(res.data.newsdate, 'YYYY-MM-DD')
        this.depnews.newstitle = res.data.newstitle
        this.depnews.newsdetail = res.data.newsdetail
        this.depnews.depid = res.data.depid
      }).catch(e => {
        console.log(e)
      })
    }
  }
}
</script>

<style scoped>

</style>
