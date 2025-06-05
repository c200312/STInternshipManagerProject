<template>
  <div class="diary-container">
    <!-- 周选择 -->
    <el-select v-model="selectedWeek" placeholder="请选择周" class="week-select">
      <el-option
          v-for="week in allWeeks"
          :key="week"
          :label="'第 ' + week + ' 周'"
          :value="week">
      </el-option>
    </el-select>

    <!-- 班级选择 -->
    <el-select v-model="selectedClass" placeholder="请选择班级" class="class-select">
      <el-option
          v-for="stuClass in allClasses"
          :key="stuClass"
          :label="stuClass"
          :value="stuClass">
      </el-option>
    </el-select>

    <!-- 学生列表展示 -->
    <ul class="student-list">
      <li
          v-for="student in filteredStudents.completed"
          :key="student.student.s_id"
          class="student-item completed"
      >
        {{ student.student.student_name }} - 已提交
        <span class="details">
          <el-tooltip
              class="item"
              effect="dark"
              placement="top-start"
              :content="renderDiaryTooltip(student)"
              raw-content
          >
            （详情）
          </el-tooltip>
        </span>
      </li>
      <li
          v-for="student in filteredStudents.notCompleted"
          :key="student.student.s_id"
          class="student-item not-completed"
      >
        {{ student.student.student_name }} - 未提交
        <span class="details">（详情）</span>
      </li>
    </ul>
  </div>
</template>
<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from '@/utils/request';
import { ElMessage } from 'element-plus';

const studentData = ref([]);
const selectedWeek = ref('');
const selectedClass = ref('');

// 加载学生数据
async function loadStudentInfo() {
  try {
    const res = await axios.get(`/admin/stinfo`);
    studentData.value = res.data?.data || [];
    if (studentData.value.length > 0) {
      // 默认选第一个学生的班级
      selectedClass.value = studentData.value[0].student.stu_class;

      // 默认选第一个学生拥有的第一个周
      const firstStudentDiaryWeeks = studentData.value[0].duser?.diary?.map(d => d.week);
      if (firstStudentDiaryWeeks && firstStudentDiaryWeeks.length > 0) {
        selectedWeek.value = firstStudentDiaryWeeks[0];
      }
    }
  } catch (err) {
    ElMessage.error('加载学生数据失败');
    console.error(err);
  }
}

onMounted(() => {
  loadStudentInfo();
});

// 获取所有存在的周
const allWeeks = computed(() => {
  const weeks = new Set();
  studentData.value.forEach(student => {
    if (student.duser && student.duser.diary) {
      student.duser.diary.forEach(diary => {
        weeks.add(diary.week);
      });
    }
  });
  return Array.from(weeks).sort(); // 确保周数按顺序排列
});

// 获取所有存在的班级
const allClasses = computed(() => {
  const classes = new Set();
  studentData.value.forEach(student => {
    if (student.student && student.student.stu_class) {
      classes.add(student.student.stu_class);
    }
  });
  return Array.from(classes);
});

// 根据 selectedWeek 和 selectedClass 过滤学生数据
const filteredStudents = computed(() => {
  const result = {
    completed: [],
    notCompleted: []
  };

  // 如果没有选择周或班级，则返回空列表
  if (!selectedWeek.value || !selectedClass.value) {
    return result;
  }

  studentData.value.forEach(student => {
    // 判断是否是当前选中的班级
    const isSameClass = student.student.stu_class === selectedClass.value;

    if (isSameClass) {
      const hasDiaryForSelectedWeek = student.duser?.diary?.some(
          diary => diary.week === selectedWeek.value
      );

      if (hasDiaryForSelectedWeek) {
        result.completed.push(student);
      } else {
        result.notCompleted.push(student);
      }
    }
  });

  return result;
});

// 渲染周记内容的悬浮提示
function renderDiaryTooltip(student) {
  const diaries = student.duser?.diary || [];
  if (!diaries.length) return '无周记记录';

  return diaries
      .filter(diary => diary.week === selectedWeek.value)
      .map(d => `<p><b>第${d.week}周：</b>${d.content}</p>`)
      .join('');
}
</script>
<style scoped>
.diary-container {
  padding: 20px;
}
.week-select,
.class-select {
  margin-bottom: 20px;
  width: 200px;
}
.student-list {
  list-style-type: none;
  padding-left: 0;
}
.student-item {
  padding: 8px 12px;
  margin-bottom: 4px;
  border-radius: 4px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.student-item.completed {
  background-color: #f0f9eb;
  color: #135e0c;
}
.student-item.not-completed {
  background-color: #fef0f0;
  color: #991818;
}
.details {
  font-size: 12px;
  color: #666;
  cursor: pointer;
  margin-left: 10px;
}
</style>
