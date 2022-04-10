<template>
  <page-header-wrapper :title="false" :content="$t('form.basic-form.basic.description')">
    <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
      <a-form>
        <a-tree
          v-model="checkedKeys"
          checkable
          :expanded-keys="expandedKeys"
          :auto-expand-parent="autoExpandParent"
          :selected-keys="selectedKeys"
          :tree-data="menuTree"
          @expand="onExpand"
          @select="onSelect"
          @check="onCheck"
        />
        <a-form-item
          :wrapperCol="{ span: 24 }"
          style="text-align: center"
        >
          <a-button @click="handleSubmit" type="primary">{{ $t('form.basic-form.form.submit') }}</a-button>
          <a-button @click="backToRoleList" style="margin-left: 8px">{{ $t('form.basic-form.form.back') }}</a-button>
        </a-form-item>
      </a-form>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import { getAllMenus, getOneRoleSelectedMenu, saveRoleMenus } from '@/api/admin'

export default {
  name: `RoleSource`,
  data () {
    return {
      rid: null,
      checkedKeys: [], // 显示全选中的key
      selectedKeys: [],
      expandedKeys: [],
      autoExpandParent: true,
      menuTree: [],
      checkedResult: [] // 最终结果
    }
  },
  mounted () {
    this.rid = this.$route.params.id
    getAllMenus().then(res => {
      // console.log(res)
      if (res.status === 200) {
        this.menuTreeHandler(res.data, this.menuTree, 0)
      }
      getOneRoleSelectedMenu({ rid: this.rid }).then(res => {
        console.log(res)
        if (res.status === 200) {
          const originalData = res.data
          this.checkedResult = res.data.map(menu => menu.id.toString())
          // 留下叶子节点
          const menuParentIds = originalData.map(menu => menu.parentId)
          this.checkedKeys = originalData.filter(menu => menuParentIds.indexOf(menu.id) === -1).map(menu => menu.id.toString())
        }
      })
    })
  },
  methods: {
    handleSubmit () {
      // 处理所选menu数组
      let submitData = []
      this.checkedResult.forEach(id => {
        submitData = [...submitData, Number(id)]
      })
      // 提交数据
      saveRoleMenus({ rid: this.rid, mids: submitData }).then(res => {
        if (res.status === 200) {
          this.$message.success(`角色资源成功`)
          this.$router.push('/systemConfiguration/rolelist')
        } else {
          this.$message.error(`保存失败`)
        }
      }).catch(e => {
        console.log(e)
        this.$message.error(`保存失败`)
      })
    },
    onExpand (expandedKeys) {
      // console.log('onExpand', expandedKeys)
      // if not set autoExpandParent to false, if children expanded, parent can not collapse.
      // or, you can remove all expanded children keys.
      this.expandedKeys = expandedKeys
      this.autoExpandParent = false
    },
    onCheck (checkedKeys, event) {
      // console.log('onCheck', checkedKeys)
      // console.log('halfCheck', event)
      this.checkedResult = [...event.halfCheckedKeys, ...checkedKeys]
      // console.log('checkedResult', this.checkedResult)
    },
    onSelect (selectedKeys, info) {
      // console.log('onSelect', info)
      this.selectedKeys = selectedKeys
    },
    menuTreeHandler (menuList, treeData, parentId) {
      menuList.forEach(item => {
        // 判断是否为父级菜单
        if (item.parentId === parentId) {
          let titleName = item.meta.title
          if (titleName.search('.') !== -1) {
            titleName = this.$i18n.t(titleName)
          }
          const child = {
            title: titleName,
            key: item.id.toString(),
            children: []
          }
          // 迭代 list， 找到当前菜单相符合的所有子菜单
          this.menuTreeHandler(menuList, child.children, item.id)
          // 删掉不存在 children 值的属性
          if (child.children.length <= 0) {
            delete child.children
          }
          // 加入到树中
          treeData.push(child)
        }
      })
    },
    backToRoleList () {
      this.$router.push('/systemConfiguration/rolelist')
    }
  }
}
</script>

<style scoped>

</style>
