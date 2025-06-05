<template>
  <h3>填写周记</h3>
  <el-form @submit.prevent="handleSubmit">
    <el-form-item label="选择周数">
      <el-select
          v-model="selectedWeek"
          placeholder="选择周数"
          @change="loadDiary"
      >
        <el-option
            v-for="week in 17"
            :key="week"
            :label="week === 17 ? '总结' : `第 ${week} 周`"
            :value="week"
        />
      </el-select>
    </el-form-item>

    <template v-if="selectedWeek !== 17">
      <el-form-item :label="`第 ${selectedWeek} 周周记`">
        <el-input
            type="textarea"
            :rows="10"
            v-model="content"
            placeholder="实习实践工作内容、任务与完成情况：

实习实践收获：

存在问题与改进措施："
            style="width: 100%"
        />
      </el-form-item>
    </template>

    <template v-else>
      <el-form-item label="成果总结">
        <div class="flex gap-3">
          <el-input
              type="textarea"
              rows=10
              v-model="achievementContent"
              placeholder="不少于5条，对成果进行总结"
              style="flex: 1; min-height: 200px; resize: none; width: 1000px"
          />
          <el-tooltip
              effect="light"
              placement="right"
              :width="300"
          >
            <template #content>
              <div class="p-3">
                <h4 class="font-bold mb-2">成果总结模板：</h4>
                <p class="mb-1">1. 掌握了XX技能，能够独立完成XX任务</p>
                <p class="mb-1">2. 完成了XX项目/任务，取得了XX成果</p>
                <p class="mb-1">3. 提升了XX方面的能力，具体表现为XX</p>
                <p class="mb-1">4. 获得了XX方面的经验，包括XX</p>
                <p>5. 实现了XX目标，解决了XX问题</p>
              </div>
            </template>
            <el-button circle style="margin-top: 8px;">
              <el-icon><QuestionFilled /></el-icon>
            </el-button>
          </el-tooltip>
        </div>
      </el-form-item>

      <el-form-item label="实践总结">
        <div class="flex gap-3">
          <el-input
              type="textarea"
              rows=10
              v-model="practiceContent"
              placeholder="实习实践的整体总结与感悟"
              style="flex: 1; min-height: 200px; resize: none; width: 1000px"
          />
          <el-tooltip
              effect="light"
              placement="right"
              :width="300"
          >
            <template #content>
              <div class="p-3 max-h-[400px] overflow-y-auto w-full">
                <h4 class="font-bold mb-2">一、校外实习实践基本情况介绍</h4>
                <p class="mb-3">介绍校外实习实践内容，实习实践时间、地点，实习实践的目的，校内外指导教师、学生管理教师及实习实践计划完成情况等。</p>

                <h4 class="font-bold mb-2">二、校外实习实践过程介绍</h4>
                <p class="mb-3">详细叙述校外实习实践过程，如本人在校外实习实践中的态度表现，如何开展每个环节的实习实践，如何完成实习实践任务、实现实习实践目标等。</p>

                <h4 class="font-bold mb-2">三、校外实习实践成果介绍</h4>
                <p class="mb-1">1．实习实践成果介绍</p>
                <p class="mb-1">介绍实习实践成果名称，简洁且准确概括此次实习实践所达成的主要成果；介绍主要内容，涵盖所负责的工作环节、执行的任务流程等；描述该成果的特色及创新点，突出该实习实践成果相较于常规做法所具备的独特之处，可能体现在工作模式、执行方法等方面；此外，着重阐述实践过程中运用的新颖思路、创新举措等。</p>

                <p class="mb-1">2．项目任务执行及决策方法运用</p>
                <p class="mb-1">介绍完成项目责任情况，罗列在整个实习实践项目中承担并完成的各项具体任务，明确任务完成的进度与质量要求；描述项目管理学方法运用情况，说明在项目执行过程中如何运用各类管理学方法进行团队管理、沟通协调、风险管理等；描述项目经济决策方法运用情况，阐述在面对项目中的经济决策问题（如定价、资源分配等）时所采用的具体经济决策方法及其应用效果。</p>

                <p class="mb-1">3．列举标准等专业要素并例举其积极影响</p>
                <p class="mb-3">列举2-3个专业相关技术标准体系、知识产权、产业政策和法律法规等，并例举标准、政策和法规等在项目中产生的积极影响。</p>

                <h4 class="font-bold mb-2">四、收获和感悟</h4>
                <p class="mb-1">1．对专业应用能力的培养</p>
                <p class="mb-1">如何通过校外实习实践提高对专业的认知水平、提高专业技能、提高专业应用能力、培养职业岗位素养等，如遇到问题、困难时是如何通过相关途径自行解决和克服的，如何提高分析问题解决问题的能力，如何运用所学理论知识解决实际问题等。</p>

                <p class="mb-1">2．对独立工作能力、团队合作精神的培养</p>
                <p class="mb-1">如何通过校外实习实践提高独立工作能力及团队合作精神等，如在校外实习实践中如何独立思考解决问题，如何与他人配合、协作解决问题等。</p>

                <p class="mb-1">3．对创新精神的提升</p>
                <p class="mb-1">如何通过校外实习实践提升创新精神，如在校外实习实践中如何寻找创新点，如何开展创新训练等。</p>

                <p class="mb-1">4．对不足之处的反思</p>
                <p class="mb-1">校外实习实践中暴露出哪些不足。如在校外实习实践中遇到哪些问题处理不当，知识层面有哪些不足，哪些东西以前学过没有掌握等。</p>

                <p class="mb-1">5．对教学安排的意见和建议</p>
                <p class="mb-3">对学校、教学单位及专业在教学安排方面的意见和建议等。</p>

                <p class="text-sm text-gray-500">(不少于3000字)</p>
              </div>
            </template>
            <el-button circle style="margin-top: 8px;">
              <el-icon><QuestionFilled /></el-icon>
            </el-button>
          </el-tooltip>
        </div>
      </el-form-item>
    </template>

    <el-form-item>
      <el-button type="primary" @click="handleSubmit">保存</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from '../../utils/request'
import { ElMessage } from 'element-plus'
import { QuestionFilled } from '@element-plus/icons-vue'

// 定义组件属性
const props = defineProps(['userName'])

// 定义组件事件
const emit = defineEmits(['submit'])

// 周数选择
const selectedWeek = ref(1)

// 周记内容响应式变量
const content = ref('')
// 总结内容响应式变量
const achievementContent = ref('')
const practiceContent = ref('')

// 加载周记数据
const loadDiary = async () => {
  try {
    const res = await axios.get(`/duser/${props.userName}`)
    const diaries = res.data?.data?.diary || []
    if (selectedWeek.value !== 17) {
      const found = diaries.find(d => d.week === `${selectedWeek.value}`)
      content.value = found ? found.content : ''
    } else {
      // 总结周，需要同时处理成果和实践内容
      const achievementFound = diaries.find(d => d.week === 'achievement')
      const practiceFound = diaries.find(d => d.week === 'practice')
      achievementContent.value = achievementFound ? achievementFound.content : ''
      practiceContent.value = practiceFound ? practiceFound.content : ''
    }
  } catch (error) {
    console.error('加载周记数据失败:', error)
    ElMessage.error('加载周记数据失败，请稍后重试')
  }
}

// 保存周记
const saveDiary = async () => {
  try {
    if (selectedWeek.value !== 17) {
      await axios.patch(`/duser/${props.userName}`, {
        diary: [{ week: selectedWeek.value, content: content.value }]
      })
    } else {
      // 总结周，保存成果和实践内容
      await axios.patch(`/duser/${props.userName}`, {
        diary: [
          { week: 'achievement', content: achievementContent.value },
          { week: 'practice', content: practiceContent.value }
        ]
      })
    }

    ElMessage.success(selectedWeek.value !== 17 ? '周记保存成功' : '总结保存成功')
    emit('submit', selectedWeek.value !== 17
        ? content.value
        : `${achievementContent.value}\n\n${practiceContent.value}`)
  } catch (error) {
    console.error('保存周记失败:', error)
    ElMessage.error('保存失败，请稍后重试')
  }
}

// 提交处理函数
const handleSubmit = () => {
  saveDiary()
}

// 页面加载时获取周记数据
onMounted(() => {
  loadDiary()
})
</script>

<style scoped>
.el-textarea {
  min-height: 200px;
}
</style>