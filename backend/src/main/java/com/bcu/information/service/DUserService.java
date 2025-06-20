package com.bcu.information.service;

import com.bcu.common.result.Result;
import com.bcu.information.bean.*;
import com.bcu.information.dao.DUserRepository;
import com.bcu.student.bean.Student;
import com.bcu.student.bean.StudentExample;
import com.bcu.student.dao.StudentMapper;
import com.bcu.teacher.bean.Teacher;
import com.bcu.teacher.bean.TeacherExample;
import com.bcu.teacher.dao.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DUserService {
    private final DUserRepository repository;
    private final TeacherMapper teacherMapper;
    private final StudentMapper studentMapper;

    // 初始化
    public Result create(DUser user) {
        if (repository.existsById(user.getId())) {
            return Result.error("该学生 ID 已存在！");
        }
        return Result.success(repository.save(user), "初始化成功");
    }

    // 查询全部信息
    public Result getAll() {
        List<DUser> users = repository.findAll();
        return !users.isEmpty() ? Result.success(users, "查询所有成功") : Result.error("未找到任何学生信息");
    }

    // 按id查询全部信息，若不存在则初始化
    public Result getById(String id) {
        DUser user = repository.findById(id).orElse(null);
        if (user != null) {
            return Result.success(user, "按id查询成功");
        } else {
            // 创建新用户
            DUser newUser = new DUser();
            newUser.setId(id);
            return Result.success(repository.save(newUser), "未找到该学生，已初始化新记录");
        }
    }

    // 按id删除全部信息
    public Result deleteById(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return Result.success(null, "删除成功");
        }
        return Result.error("学生不存在");
    }

    // 按id部分更新(按输入的信息进行覆盖，仅覆盖输入的部分)
    public Result patchUpdate(String id, DUser partial) {
        try {
            // 数据验证
            if (id == null || id.trim().isEmpty()) {
                return Result.error("学生ID不能为空");
            }

            // 获取或创建用户
            DUser user = repository.findById(id).orElseGet(() -> {
                DUser newUser = new DUser();
                newUser.setId(id);
                newUser.setCompany(new ArrayList<>());
                newUser.setDiary(new ArrayList<>());
                newUser.setComment(new ArrayList<>());
                newUser.setPlan(new ArrayList<>());
                return newUser;
            });

            // 更新评语相关字段
            if (partial.getPracticeComment() != null) {
                user.setPracticeComment(partial.getPracticeComment());
            }
            if (partial.getTeachingUnitComment() != null) {
                user.setTeachingUnitComment(partial.getTeachingUnitComment());
            }
            if (partial.getPracticeContent() != null) {
                user.setPracticeContent(partial.getPracticeContent());
            }

            // 更新公司信息
            if (partial.getCompany() != null) {
                for (DCompany c : partial.getCompany()) {
                    boolean updated = false;
                    for (DCompany existing : user.getCompany()) {
                        if (existing.getName().equals(c.getName())) {
                            existing.setIntroduction(c.getIntroduction());
                            updated = true;
                            break;
                        }
                    }
                    if (!updated) {
                        user.getCompany().add(c);
                    }
                }
            }

            // 周记：按 week 唯一 → 替换内容或添加（检查审核状态）
            if (partial.getDiary() != null) {
                for (DDiary d : partial.getDiary()) {
                    DDiary existingDiary = user.getDiary().stream()
                            .filter(existing -> existing.getWeek().equals(d.getWeek()))
                            .findFirst()
                            .orElse(null);
                    
                    if (existingDiary != null && "APPROVED".equals(existingDiary.getStatus())) {
                        if (d.getStatus() != null) existingDiary.setStatus(d.getStatus());
                        if (d.getReviewComment() != null) existingDiary.setReviewComment(d.getReviewComment());
                        if (d.getReviewTime() != null) existingDiary.setReviewTime(d.getReviewTime());
                        if (d.getReviewer() != null) existingDiary.setReviewer(d.getReviewer());
                    } else {
                        user.getDiary().removeIf(existing -> existing.getWeek().equals(d.getWeek()));
                        if (d.getStatus() == null) {
                            d.setStatus("DRAFT");
                        }
                        user.getDiary().add(d);
                    }
                }
            }

            // 评语：按 week唯一 → 替换或添加
            if (partial.getComment() != null) {
                for (DComment c : partial.getComment()) {
                    user.getComment().removeIf(existing ->
                            existing.getWeek().equals(c.getWeek())
                    );
                    user.getComment().add(c);
                }
            }
            // 更新校外实习实践计划（基于用户ID，只保留一条记录）
            if (partial.getPlan() != null && !partial.getPlan().isEmpty()) {
                // 每个用户(基于id)只保留一条实习计划记录
                user.setPlan(new ArrayList<>());
                user.getPlan().add(partial.getPlan().get(0));
            }

            // 保存更新
            DUser savedUser = repository.save(user);
            return Result.success(savedUser, "更新成功");
        } catch (Exception e) {
            // 记录错误日志
            System.err.println("更新用户信息失败: " + e.getMessage());
            e.printStackTrace();
            return Result.error("更新失败: " + e.getMessage());
        }
    }

    public Result selectByTNumber(String tNumber) {
        TeacherExample tExample = new TeacherExample();
        tExample.createCriteria().andT_numberEqualTo(tNumber);
        List<Teacher> teacherList = teacherMapper.selectByExample(tExample);

        if (teacherList.size() != 1) {
            return Result.error("未找到教师或工号重复");
        }

        Teacher teacher = teacherList.getFirst();

        StudentExample sExample = new StudentExample();
        sExample.createCriteria()
                .andAcademic_advisor_idEqualTo(teacher.getT_id());
        sExample.or().andIndustry_advisor_idEqualTo(teacher.getT_id());

        List<Student> students = studentMapper.selectByExample(sExample);
        if (students.isEmpty()) {
            return Result.error("该教师没有学生");
        }

        List<StudentView> resultList = new ArrayList<>();
        for (Student student : students) {
            Optional<DUser> dUserOpt = repository.findById(student.getStudent_number());
            if (dUserOpt.isPresent()) {
                resultList.add(new StudentView(student, dUserOpt.get()));
            } else {
                resultList.add(new StudentView(student, null)); // 保留 student，dUser 缺失也展示
            }
        }

        return Result.success(resultList);
    }

    // 提交周记审核
    public Result submitDiaryForReview(String studentId, String week) {
        Optional<DUser> optionalUser = repository.findById(studentId);
        if (optionalUser.isEmpty()) {
            return Result.error("学生不存在");
        }

        DUser user = optionalUser.get();
        DDiary diary = user.getDiary().stream()
                .filter(d -> d.getWeek().equals(week))
                .findFirst()
                .orElse(null);

        if (diary == null) {
            return Result.error("该周周记不存在");
        }

        if ("SUBMITTED".equals(diary.getStatus()) || "APPROVED".equals(diary.getStatus())) {
            return Result.error("该周记已提交或已审核，无法重复提交");
        }

        if (diary.getContent() == null || diary.getContent().trim().isEmpty()) {
            return Result.error("周记内容不能为空");
        }

        diary.setStatus("SUBMITTED");
        diary.setSubmitTime(java.time.LocalDateTime.now().toString());
        repository.save(user);
        return Result.success(diary, "周记提交审核成功");
    }

    // 审核周记
    public Result reviewDiary(String studentId, String week, String status, String reviewComment, String reviewer) {
        if (!"APPROVED".equals(status) && !"REJECTED".equals(status)) {
            return Result.error("审核状态只能是APPROVED或REJECTED");
        }

        Optional<DUser> optionalUser = repository.findById(studentId);
        if (optionalUser.isEmpty()) {
            return Result.error("学生不存在");
        }

        DUser user = optionalUser.get();
        DDiary diary = user.getDiary().stream()
                .filter(d -> d.getWeek().equals(week))
                .findFirst()
                .orElse(null);

        if (diary == null) {
            return Result.error("该周周记不存在");
        }

        if (!"SUBMITTED".equals(diary.getStatus())) {
            return Result.error("只能审核已提交的周记");
        }

        diary.setStatus(status);
        diary.setReviewComment(reviewComment);
        diary.setReviewer(reviewer);
        diary.setReviewTime(java.time.LocalDateTime.now().toString());

        repository.save(user);
        return Result.success(diary, "审核完成");
    }

    // 获取待审核的周记列表
    public Result getPendingDiaries() {
        List<DUser> users = repository.findAll();
        List<Object> pendingDiaries = new ArrayList<>();

        for (DUser user : users) {
            for (DDiary diary : user.getDiary()) {
                if ("SUBMITTED".equals(diary.getStatus())) {
                    Map<String, Object> diaryInfo = new HashMap<>();
                    diaryInfo.put("studentId", user.getId());
                    diaryInfo.put("week", diary.getWeek());
                    diaryInfo.put("content", diary.getContent());
                    diaryInfo.put("status", diary.getStatus());
                    pendingDiaries.add(diaryInfo);
                }
            }
        }

        return Result.success(pendingDiaries, "获取待审核周记成功");
    }


}
