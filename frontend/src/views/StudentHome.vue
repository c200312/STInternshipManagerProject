<template>
  <el-container>
    <el-aside width="200px">
      <el-menu @select="currentTab = $event" :default-active="currentTab">
        <el-menu-item index="diary">填写周记</el-menu-item>
        <el-menu-item index="info">填写基本信息</el-menu-item>
        <el-menu-item index="company">填写企业信息</el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header>
        <el-select
            v-if="currentTab === 'diary'"
            v-model="selectedWeek"
            placeholder="选择周数"
            @change="loadDiary"
        >
          <el-option
              v-for="week in 13"
              :key="week"
              :label="week === 13 ? '总结' : '第 ' + week + ' 周'"
              :value="week"
          />
        </el-select>

      </el-header>

      <el-main>
        <DiaryForm
            v-if="currentTab === 'diary'"
            :userId="userId"
            :week="selectedWeek"
            :initContent="diaryContent"
            @submit="saveDiary"
        />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '../utils/request'
import DiaryForm from '../components/DiaryForm.vue'
import { ElMessage } from 'element-plus'

const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
const userId = userInfo.username
const currentTab = ref('diary')
const selectedWeek = ref(1)
const diaryContent = ref('')

const loadDiary = async () => {
  try {
    const res = await axios.get(`/duser/${userId}`)
    const diaries = res.data.data.diary || []
    const found = diaries.find(d => d.week === selectedWeek.value)
    diaryContent.value = found ? found.content : ''
  } catch (err) {
    console.error('加载失败', err)
    ElMessage.error('加载周记失败')
  }
}

const saveDiary = async (content) => {
  try {
    await axios.patch(`/duser/patch/${userId}`, {
      diary: [{week: selectedWeek.value, content}]
    })
    ElMessage.success('周记保存成功')
  } catch (err) {
    console.error('保存失败', err)
    ElMessage.error('保存失败')
  }
}

onMounted(() => {
  loadDiary()
})
</script>
