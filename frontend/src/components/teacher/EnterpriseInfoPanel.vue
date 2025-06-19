<template>
  <el-card>
    <h2>企业信息</h2>
    <template v-if="!editMode">
      <el-descriptions v-if="hasInfo" column="1" border>
        <el-descriptions-item label="实习单位名称">{{ enterpriseInfo.company_name }}</el-descriptions-item>
        <el-descriptions-item label="是否校外实践基地">{{ enterpriseInfo.is_practice_base ? '是' : '否' }}</el-descriptions-item>
        <el-descriptions-item label="所属实践基地名称">{{ enterpriseInfo.practice_base_name }}</el-descriptions-item>
        <el-descriptions-item label="统一社会信用代码">{{ enterpriseInfo.credit_code }}</el-descriptions-item>
        <el-descriptions-item label="实习地区">{{ enterpriseInfo.practice_region }}</el-descriptions-item>
        <el-descriptions-item label="实习时间范围">{{ formattedDateRange }}</el-descriptions-item>
        <el-descriptions-item label="实际实习天数">{{ enterpriseInfo.actual_days }}</el-descriptions-item>
        <el-descriptions-item label="实习岗位">{{ enterpriseInfo.position }}</el-descriptions-item>
        <el-descriptions-item label="实习方式">{{ enterpriseInfo.internship_mode }}</el-descriptions-item>
        <el-descriptions-item label="实习类型">{{ enterpriseInfo.internship_type }}</el-descriptions-item>
        <el-descriptions-item label="企业指导人员姓名">{{ enterpriseInfo.company_advisor_name }}</el-descriptions-item>
        <el-descriptions-item label="企业指导人员职务">{{ enterpriseInfo.company_advisor_position }}</el-descriptions-item>
        <el-descriptions-item label="实习单位地址">{{ enterpriseInfo.address }}</el-descriptions-item>
        <el-descriptions-item label="单位联系人">{{ enterpriseInfo.contact_person }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ enterpriseInfo.company_phone }}</el-descriptions-item>
      </el-descriptions>
      <el-empty v-else description="暂无学生填写的企业信息" />
      <el-button type="primary" style="margin-top: 20px;" @click="editMode = true">编辑</el-button>
    </template>
    <template v-else>
      <el-form :model="enterpriseInfo" label-width="140px" style="max-width: 700px;">
        <el-form-item label="实习单位名称">
          <el-input v-model="enterpriseInfo.company_name" placeholder="请输入实习单位全称" />
        </el-form-item>
        <el-form-item label="是否校外实践基地">
          <el-switch v-model="enterpriseInfo.is_practice_base" active-text="是" inactive-text="否" />
        </el-form-item>
        <el-form-item label="所属实践基地名称">
          <el-input v-model="enterpriseInfo.practice_base_name" placeholder="请输入实践基地名称" />
        </el-form-item>
        <el-form-item label="统一社会信用代码">
          <el-input v-model="enterpriseInfo.credit_code" placeholder="请输入18位统一社会信用代码" />
        </el-form-item>
        <el-form-item label="实习地区">
          <el-input v-model="enterpriseInfo.practice_region" placeholder="例：北京市-朝阳区" />
        </el-form-item>
        <el-form-item label="实习时间范围">
          <el-date-picker v-model="enterpriseInfo.start_date" type="date" placeholder="开始日期" style="width: 48%" />
          <span class="date-separator">-</span>
          <el-date-picker v-model="enterpriseInfo.end_date" type="date" placeholder="结束日期" style="width: 48%" />
        </el-form-item>
        <el-form-item label="实际实习天数">
          <el-input v-model="enterpriseInfo.actual_days" type="number" placeholder="自动计算" min="0" />
        </el-form-item>
        <el-form-item label="实习岗位">
          <el-input v-model="enterpriseInfo.position" placeholder="例：软件测试实习生" />
        </el-form-item>
        <el-form-item label="实习方式">
          <el-select v-model="enterpriseInfo.internship_mode" placeholder="请选择实习方式">
            <el-option label="集中实习" value="集中实习" />
            <el-option label="分散实习" value="分散实习" />
            <el-option label="跟岗实习" value="跟岗实习" />
          </el-select>
        </el-form-item>
        <el-form-item label="实习类型">
          <el-select v-model="enterpriseInfo.internship_type" placeholder="请选择实习类型">
            <el-option label="专业实习" value="专业实习" />
            <el-option label="毕业实习" value="毕业实习" />
            <el-option label="认知实习" value="认知实习" />
          </el-select>
        </el-form-item>
        <el-form-item label="企业指导人员姓名">
          <el-input v-model="enterpriseInfo.company_advisor_name" placeholder="请输入指导人员姓名" />
        </el-form-item>
        <el-form-item label="企业指导人员职务">
          <el-input v-model="enterpriseInfo.company_advisor_position" placeholder="例：技术主管" />
        </el-form-item>
        <el-form-item label="实习单位地址">
          <el-input v-model="enterpriseInfo.address" type="textarea" rows="2" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="单位联系人">
          <el-input v-model="enterpriseInfo.contact_person" placeholder="请输入联系人姓名" />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="enterpriseInfo.company_phone" placeholder="请输入联系电话" maxlength="13" show-word-limit />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveEnterpriseInfo" :loading="saving">保存</el-button>
          <el-button @click="cancelEdit">取消</el-button>
        </el-form-item>
      </el-form>
    </template>
  </el-card>
</template>

<script setup>
import { ref, watch, computed } from 'vue'
import { ElMessage } from 'element-plus'
import axios from '@/utils/request'

const props = defineProps({
  student: {
    type: Object,
    default: null
  }
})

const saving = ref(false)
const editMode = ref(false)

const enterpriseInfo = ref({})

const hasInfo = computed(() => {
  // 只要有一个主要字段有值就算有信息
  return enterpriseInfo.value && Object.keys(enterpriseInfo.value).length > 0 && (
      enterpriseInfo.value.company_name || enterpriseInfo.value.address || enterpriseInfo.value.contact_person || enterpriseInfo.value.company_phone
  )
})

// 格式化日期显示
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  return `${year}年${month}月${day}日`
}

const formattedDateRange = computed(() => {
  const startDate = formatDate(enterpriseInfo.value.start_date)
  const endDate = formatDate(enterpriseInfo.value.end_date)
  if (startDate && endDate) {
    return `${startDate} - ${endDate}`
  } else if (startDate) {
    return startDate
  } else if (endDate) {
    return endDate
  }
  return ''
})

const loadEnterpriseInfo = () => {
  if (props.student && props.student.s_id) {
    axios.get(`/internship/${props.student.s_id}`).then(res => {
      const data = res.data?.data?.[0] || {}
      enterpriseInfo.value = {
        internship_id: data.internship_id || '',
        company_name: data.company_name || '',
        is_practice_base: data.is_practice_base || false,
        practice_base_name: data.practice_base_name || '',
        credit_code: data.credit_code || '',
        practice_region: data.practice_region || '',
        start_date: data.start_date || '',
        end_date: data.end_date || '',
        actual_days: data.actual_days || '',
        position: data.position || '',
        internship_mode: data.internship_mode || '',
        internship_type: data.internship_type || '',
        company_advisor_name: data.company_advisor_name || '',
        company_advisor_position: data.company_advisor_position || '',
        address: data.address || '',
        contact_person: data.contact_person || '',
        company_phone: data.company_phone || ''
      }
    })
  } else {
    enterpriseInfo.value = {}
  }
}

watch(
    () => props.student,
    () => {
      loadEnterpriseInfo()
      editMode.value = false
    },
    { immediate: true }
)

const saveEnterpriseInfo = async () => {
  if (!props.student) {
    ElMessage.warning('未选择学生')
    return
  }
  saving.value = true
  try {
    const payload = {
      internship_id: enterpriseInfo.value.internship_id,
      s_id: props.student.s_id,
      company_name: enterpriseInfo.value.company_name,
      is_practice_base: enterpriseInfo.value.is_practice_base,
      practice_base_name: enterpriseInfo.value.practice_base_name,
      credit_code: enterpriseInfo.value.credit_code,
      practice_region: enterpriseInfo.value.practice_region,
      start_date: enterpriseInfo.value.start_date,
      end_date: enterpriseInfo.value.end_date,
      actual_days: enterpriseInfo.value.actual_days,
      position: enterpriseInfo.value.position,
      internship_mode: enterpriseInfo.value.internship_mode,
      internship_type: enterpriseInfo.value.internship_type,
      company_advisor_name: enterpriseInfo.value.company_advisor_name,
      company_advisor_position: enterpriseInfo.value.company_advisor_position,
      address: enterpriseInfo.value.address,
      contact_person: enterpriseInfo.value.contact_person,
      company_phone: enterpriseInfo.value.company_phone
    }
    await axios.put(`/internship`, payload)
    ElMessage.success('企业信息保存成功！')
    editMode.value = false
    loadEnterpriseInfo()
  } catch (e) {
    ElMessage.error('保存失败')
  } finally {
    saving.value = false
  }
}

const cancelEdit = () => {
  loadEnterpriseInfo()
  editMode.value = false
}
</script>

<style scoped>
.el-card {
  margin: 20px;
  max-width: 800px;
  height: 1800px;
  width: 100%;
}
.date-separator {
  display: inline-block;
  width: 4%;
  text-align: center;
}
</style>