<template>
  <!-- <a-result status="404" title="404" sub-title="Sorry, the page you visited does not exist.">
    <template #extra>
      <a-button type="primary" @click="toHome">
        Back Home
      </a-button>
    </template>
  </a-result> -->
  <div>
    <a-card style="margin-top: 24px" :bordered="false" title="单据详情">
      <a-descriptions>
        <a-descriptions-item label="用户姓名">付晓晓</a-descriptions-item>
        <a-descriptions-item label="workflowId">{{ workflowId }}</a-descriptions-item>
        <a-descriptions-item label="身份证">3321944288191034921</a-descriptions-item>
        <a-descriptions-item label="联系方式">18112345678</a-descriptions-item>
        <a-descriptions-item label="联系地址">浙江省杭州市西湖区黄姑山路工专路交叉路口</a-descriptions-item>
      </a-descriptions>
      <a-descriptions title="信息组">
        <a-descriptions-item label="某某数据">725</a-descriptions-item>
        <a-descriptions-item label="该数据更新时间">2018-08-08</a-descriptions-item>
        <a-descriptions-item ></a-descriptions-item>
        <a-descriptions-item label="某某数据">725</a-descriptions-item>
        <a-descriptions-item label="该数据更新时间">2018-08-08</a-descriptions-item>
        <a-descriptions-item ></a-descriptions-item>
      </a-descriptions>
      <a-card type="inner" title="多层信息组">
        <a-descriptions title="组名称" size="small">
          <a-descriptions-item label="负责人">林东东</a-descriptions-item>
          <a-descriptions-item label="角色码">1234567</a-descriptions-item>
          <a-descriptions-item label="所属部门">XX公司-YY部</a-descriptions-item>
          <a-descriptions-item label="过期时间">2018-08-08</a-descriptions-item>
          <a-descriptions-item label="描述">这段描述很长很长很长很长很长很长很长很长很长很长很长很长很长很长...</a-descriptions-item>
        </a-descriptions>
        <a-divider style="margin: 16px 0" />
        <a-descriptions title="组名称" size="small" :col="1">
          <a-descriptions-item label="学名">	Citrullus lanatus (Thunb.) Matsum. et Nakai一年生蔓生藤本；茎、枝粗壮，具明显的棱。卷须较粗..</a-descriptions-item>
        </a-descriptions>
        <a-divider style="margin: 16px 0" />
        <a-descriptions title="组名称" size="small" :col="2">
          <a-descriptions-item label="负责人">付小小</a-descriptions-item>
          <a-descriptions-item label="角色码">1234567</a-descriptions-item>
        </a-descriptions>
      </a-card>
    </a-card>
    <a-card style="margin-top: 24px" :bordered="false" title="用户近半年来电记录">
      <div class="no-data"><a-icon type="frown-o"/>暂无数据</div>
    </a-card>
  </div>
</template>
<script>
  export default {
    name: 'Exception404',
    props: {
      workflowId: {
        type: String,
        default: ''
      }
    },
    methods: {
        toHome () {
        this.$router.push({ path: '/' })
        }
    }
  }
</script>
