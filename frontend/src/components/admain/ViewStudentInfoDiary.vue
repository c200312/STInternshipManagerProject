<template>
  <div class="diary-container">
    <!-- 筛选条件 -->
    <div class="filter-container">
      <!-- 周选择 -->
      <el-select v-model="selectedWeek" placeholder="请选择周" class="week-select">
        <el-option
            v-for="week in allWeeks"
            :key="week"
            :label="getWeekLabel(week)"
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

      <!-- 完成状态选择 -->
      <el-select v-model="selectedStatus" placeholder="请选择完成状态" class="status-select">
        <el-option label="全部" value="all"></el-option>
        <el-option label="已完成" value="completed"></el-option>
        <el-option label="未完成" value="notCompleted"></el-option>
      </el-select>
    </div>

    <!-- 主要内容区域：左右分栏 -->
    <div class="main-content">
      <!-- 左侧：学生列表 -->
      <div class="student-list-container">
        <h3>学生完成情况</h3>
        <ul class="student-list">
          <!-- 已完成的学生 -->
          <li
              v-for="student in displayedStudents.completed"
              :key="student.student.s_id"
              class="student-item completed"
              :class="{ active: selectedStudent?.student.s_id === student.student.s_id }"
              @click="selectStudent(student)"
          >
            {{ student.student.student_name }} - 已提交
          </li>
          <!-- 未完成的学生 -->
          <li
              v-for="student in displayedStudents.notCompleted"
              :key="student.student.s_id"
              class="student-item not-completed"
              :class="{ active: selectedStudent?.student.s_id === student.student.s_id }"
              @click="selectStudent(student)"
          >
            {{ student.student.student_name }} - 未提交
          </li>
        </ul>
      </div>

      <!-- 右侧：详情显示 -->
      <div class="detail-container">
        <h3>周记详情</h3>
        <div v-if="selectedStudent" class="detail-content">
          <div class="student-info">
            <p><strong>学生姓名：</strong>{{ selectedStudent.student.student_name }}</p>
            <p><strong>学号：</strong>{{ selectedStudent.student.student_number }}</p>
            <p><strong>班级：</strong>{{ selectedStudent.student.stu_class }}</p>
            <p><strong>周期：</strong>{{ getWeekLabel(selectedWeek) }}</p>
          </div>
          <div class="diary-content">
            <div v-if="getSelectedStudentDiary()">
              <h4>周记内容：</h4>
              <div class="content-text">{{ getSelectedStudentDiary().content }}</div>
            </div>
            <div v-else class="no-diary">
              <p>该学生未提交此周期的周记</p>
            </div>
          </div>
        </div>
        <div v-else class="no-selection">
          <p>请点击左侧学生列表查看详情</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from '@/utils/request';
import { ElMessage } from 'element-plus';

const studentData = ref([]);
const selectedWeek = ref('');
const selectedClass = ref('');
const selectedStatus = ref('all'); // 默认显示全部
const selectedStudent = ref(null); // 当前选中的学生

// 加载学生数据
async function loadStudentInfo() {
  try {
    const res = await axios.get(`/admin/stinfo`);
    studentData.value = res.data?.data || [];
    if (studentData.value.length > 0) {
      // 默认选第一个学生的班级
      selectedClass.value = studentData.value[0].student.stu_class;

      // 默认选择第1周
      selectedWeek.value = 1;
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
  // 默认包含第1周到第16周
  const defaultWeeks = [];
  for (let i = 1; i <= 16; i++) {
    defaultWeeks.push(i);
  }
  
  // 添加总结类型
  const summaryTypes = ['achievement', 'practice'];
  
  // 合并默认周期和总结类型
  return [...defaultWeeks, ...summaryTypes];
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
          diary => String(diary.week) === String(selectedWeek.value)
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

// 根据完成状态筛选显示的学生
const displayedStudents = computed(() => {
  const result = {
    completed: [],
    notCompleted: []
  };

  if (selectedStatus.value === 'all') {
    // 显示全部
    result.completed = filteredStudents.value.completed;
    result.notCompleted = filteredStudents.value.notCompleted;
  } else if (selectedStatus.value === 'completed') {
    // 只显示已完成
    result.completed = filteredStudents.value.completed;
    result.notCompleted = [];
  } else if (selectedStatus.value === 'notCompleted') {
    // 只显示未完成
    result.completed = [];
    result.notCompleted = filteredStudents.value.notCompleted;
  }

  return result;
});

// 获取周期显示标签
function getWeekLabel(week) {
  if (typeof week === 'number') {
    return `第 ${week} 周`;
  } else if (week === 'achievement') {
    return '实习成果总结';
  } else if (week === 'practice') {
    return '实习实践总结';
  }
  return week;
}

// 选择学生
function selectStudent(student) {
  selectedStudent.value = student;
}

// 获取选中学生的周记内容
function getSelectedStudentDiary() {
  if (!selectedStudent.value || !selectedWeek.value) return null;
  
  const diaries = selectedStudent.value.duser?.diary || [];
  return diaries.find(diary => String(diary.week) === String(selectedWeek.value));
}
</script>
<style scoped>
.diary-container {
  padding: 20px;
  width: 100%;
  box-sizing: border-box;
}
.filter-container {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}
.week-select,
.class-select,
.status-select {
  width: 200px;
}
.main-content {
  display: flex;
  gap: 20px;
  height: 600px;
}
.student-list-container {
  flex: 1;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 16px;
  overflow-y: auto;
}
.detail-container {
  flex: 1;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  padding: 16px;
  overflow-y: auto;
}
.student-list {
  list-style-type: none;
  padding-left: 0;
  margin: 0;
}
.student-item {
  padding: 8px 12px;
  margin-bottom: 4px;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}
.student-item:hover {
  transform: translateX(2px);
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.student-item.completed {
  background-color: #f0f9eb;
  color: #135e0c;
}
.student-item.not-completed {
  background-color: #fef0f0;
  color: #991818;
}
.student-item.active {
  border: 2px solid #409eff;
  background-color: #ecf5ff;
}
.detail-content h3, .student-list-container h3, .detail-container h3 {
  margin-top: 0;
  color: #303133;
  border-bottom: 1px solid #e4e7ed;
  padding-bottom: 8px;
}
.student-info {
  background-color: #f8f9fa;
  padding: 12px;
  border-radius: 4px;
  margin-bottom: 16px;
}
.student-info p {
  margin: 4px 0;
}
.diary-content {
  background-color: #fff;
}
.content-text {
  background-color: #f8f9fa;
  padding: 12px;
  border-radius: 4px;
  border-left: 4px solid #409eff;
  line-height: 1.6;
  white-space: pre-wrap;
}
.no-diary, .no-selection {
  text-align: center;
  color: #909399;
  padding: 40px 20px;
}
.no-selection {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}
</style>
