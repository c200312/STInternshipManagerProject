<template>
  <div>
    <!-- 顶部头像 + 姓名 -->
    <div style="display: flex; align-items: center; justify-content: flex-end; padding: 10px 20px; border-bottom: 1px solid #eee;">
      <el-dropdown @command="handleUserCommand">
        <span class="el-dropdown-link" style="display: flex; align-items: center; cursor: pointer;">
          <el-avatar
              :size="32"
              src="https://cdn-icons-png.flaticon.com/512/847/847969.png"
          />
          <span style="margin-left: 8px; font-weight: bold;">{{ userInfo.realname || userName }}</span>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <!-- 页面主体部分 -->
    <el-container>
      <el-aside width="200px">
        <el-menu @select="currentTab = $event" :default-active="currentTab">
          <el-menu-item index="diary">填写周记</el-menu-item>
          <el-menu-item index="info">填写基本信息</el-menu-item>
          <el-menu-item index="company">填写企业信息</el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="display: flex; justify-content: space-between; align-items: center;">
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
              :userName="userName"
              :week="selectedWeek"
              :initContent="diaryContent"
              @submit="saveDiary"
          />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../utils/request'
import DiaryForm from '../components/DiaryForm.vue'
import { ElMessage } from 'element-plus'

const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}')
const userName = userInfo.username
const userId = userInfo.id
const router = useRouter()

const currentTab = ref('diary')
const selectedWeek = ref(1)
const diaryContent = ref('')

const loadDiary = async () => {
  try {
    const res = await axios.get(`/duser/${userName}`)
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
    await axios.patch(`/duser/patch/${userName}`, {
      diary: [{ week: selectedWeek.value, content }]
    })
    ElMessage.success('周记保存成功')
  } catch (err) {
    console.error('保存失败', err)
    ElMessage.error('保存失败')
  }
}

const handleUserCommand = (command) => {
  if (command === 'logout') {
    localStorage.removeItem('userInfo')
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}

onMounted(() => {
  loadDiary()
})
</script>
