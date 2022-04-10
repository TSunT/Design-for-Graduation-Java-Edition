<template>
  <div>
    <a-row :gutter="24">
      <a-col :xl="16" :lg="24" :md="24" :sm="24" :xs="24">
        <a-card :loading="loading" :title="depNews.newstitle" :bordered="false">
          <span slot="extra">{{ depNews.newsdate }}</span>
          <p v-html="depNews.newsdetail">
            {{ depNews.newsdetail }}
          </p>
        </a-card>
        <a-button @click="toBack" style="margin: 8px">{{ $t('form.basic-form.form.back') }}</a-button>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import { getOneDepNewsById } from '@/api/workplace'
export default {
  name: 'WorkPlaceDeptNewsDetail',
  data () {
    return {
      depNews: {
        id: null,
        newstitle: null,
        newsdetail: null,
        newsdate: null
      },
      loading: true
    }
  },
  mounted () {
    this.depNews.id = this.$route.params.id
    if (this.depNews.id) {
      this.fetchNews(this.depNews.id)
    }
  },
  methods: {
    fetchNews (id) {
      getOneDepNewsById({
        id: id
      }).then(res => {
        this.depNews.id = res.data.id
        this.depNews.newstitle = res.data.newstitle
        this.depNews.newsdate = res.data.newsdate
        this.depNews.newsdetail = res.data.newsdetail
        this.loading = false
      }).catch(e => {
        this.$message.error(this.$t('result.fetch.failed'))
      })
    },
    toBack () {
      this.$router.push('/dashboard/workplace')
    }
  }
}
</script>

<style scoped>

</style>
