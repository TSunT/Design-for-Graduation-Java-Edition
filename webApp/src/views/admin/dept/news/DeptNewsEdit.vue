<template>
  <div>
    <editor id="tinymce" v-model="tinymceHtml" :init="einit"></editor>
    {{ tinymceHtml }}
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
      tinymceHtml: '请输入内容',
      einit: {
        skin_url: require('@/assets/tinymce/skins/ui/oxide/skin.min.css'),
        language_url: require('@/assets/tinymce/langs/zh_CN.js'),
        // skin_url: '/assets/tinymce/skins/ui/oxide/skin.min.css',
        // language_url: '/assets/tinymce/langs/zh_CN.js',
        // branding: false, // 是否禁用“Powered by TinyMCE”
        language: 'zh_CN',
        toolbar: 'undo redo | bold italic underline strikethrough | fontselect fontsizeselect formatselect | alignleft aligncenter alignright alignjustify | outdent indent |  numlist bullist | forecolor backcolor removeformat | pagebreak | charmap emoticons | fullscreen  preview save print | insertfile image media template link anchor codesample | ltr rtl',
        height: 300
      }
    }
  },
  components: { Editor },
  mounted () {
    this.depnews.depid = this.$route.params.depid
    this.depnews.id = this.$route.params.newsid
    tinymce.init({})
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
