package com.bcu.assessment.bean;

import java.util.ArrayList;
import java.util.List;

public class AssessmentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AssessmentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andS_idIsNull() {
            addCriterion("s_id is null");
            return (Criteria) this;
        }

        public Criteria andS_idIsNotNull() {
            addCriterion("s_id is not null");
            return (Criteria) this;
        }

        public Criteria andS_idEqualTo(Integer value) {
            addCriterion("s_id =", value, "s_id");
            return (Criteria) this;
        }

        public Criteria andS_idNotEqualTo(Integer value) {
            addCriterion("s_id <>", value, "s_id");
            return (Criteria) this;
        }

        public Criteria andS_idGreaterThan(Integer value) {
            addCriterion("s_id >", value, "s_id");
            return (Criteria) this;
        }

        public Criteria andS_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("s_id >=", value, "s_id");
            return (Criteria) this;
        }

        public Criteria andS_idLessThan(Integer value) {
            addCriterion("s_id <", value, "s_id");
            return (Criteria) this;
        }

        public Criteria andS_idLessThanOrEqualTo(Integer value) {
            addCriterion("s_id <=", value, "s_id");
            return (Criteria) this;
        }

        public Criteria andS_idIn(List<Integer> values) {
            addCriterion("s_id in", values, "s_id");
            return (Criteria) this;
        }

        public Criteria andS_idNotIn(List<Integer> values) {
            addCriterion("s_id not in", values, "s_id");
            return (Criteria) this;
        }

        public Criteria andS_idBetween(Integer value1, Integer value2) {
            addCriterion("s_id between", value1, value2, "s_id");
            return (Criteria) this;
        }

        public Criteria andS_idNotBetween(Integer value1, Integer value2) {
            addCriterion("s_id not between", value1, value2, "s_id");
            return (Criteria) this;
        }

        public Criteria andC_idIsNull() {
            addCriterion("c_id is null");
            return (Criteria) this;
        }

        public Criteria andC_idIsNotNull() {
            addCriterion("c_id is not null");
            return (Criteria) this;
        }

        public Criteria andC_idEqualTo(Integer value) {
            addCriterion("c_id =", value, "c_id");
            return (Criteria) this;
        }

        public Criteria andC_idNotEqualTo(Integer value) {
            addCriterion("c_id <>", value, "c_id");
            return (Criteria) this;
        }

        public Criteria andC_idGreaterThan(Integer value) {
            addCriterion("c_id >", value, "c_id");
            return (Criteria) this;
        }

        public Criteria andC_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("c_id >=", value, "c_id");
            return (Criteria) this;
        }

        public Criteria andC_idLessThan(Integer value) {
            addCriterion("c_id <", value, "c_id");
            return (Criteria) this;
        }

        public Criteria andC_idLessThanOrEqualTo(Integer value) {
            addCriterion("c_id <=", value, "c_id");
            return (Criteria) this;
        }

        public Criteria andC_idIn(List<Integer> values) {
            addCriterion("c_id in", values, "c_id");
            return (Criteria) this;
        }

        public Criteria andC_idNotIn(List<Integer> values) {
            addCriterion("c_id not in", values, "c_id");
            return (Criteria) this;
        }

        public Criteria andC_idBetween(Integer value1, Integer value2) {
            addCriterion("c_id between", value1, value2, "c_id");
            return (Criteria) this;
        }

        public Criteria andC_idNotBetween(Integer value1, Integer value2) {
            addCriterion("c_id not between", value1, value2, "c_id");
            return (Criteria) this;
        }

        public Criteria andT_idIsNull() {
            addCriterion("t_id is null");
            return (Criteria) this;
        }

        public Criteria andT_idIsNotNull() {
            addCriterion("t_id is not null");
            return (Criteria) this;
        }

        public Criteria andT_idEqualTo(Integer value) {
            addCriterion("t_id =", value, "t_id");
            return (Criteria) this;
        }

        public Criteria andT_idNotEqualTo(Integer value) {
            addCriterion("t_id <>", value, "t_id");
            return (Criteria) this;
        }

        public Criteria andT_idGreaterThan(Integer value) {
            addCriterion("t_id >", value, "t_id");
            return (Criteria) this;
        }

        public Criteria andT_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("t_id >=", value, "t_id");
            return (Criteria) this;
        }

        public Criteria andT_idLessThan(Integer value) {
            addCriterion("t_id <", value, "t_id");
            return (Criteria) this;
        }

        public Criteria andT_idLessThanOrEqualTo(Integer value) {
            addCriterion("t_id <=", value, "t_id");
            return (Criteria) this;
        }

        public Criteria andT_idIn(List<Integer> values) {
            addCriterion("t_id in", values, "t_id");
            return (Criteria) this;
        }

        public Criteria andT_idNotIn(List<Integer> values) {
            addCriterion("t_id not in", values, "t_id");
            return (Criteria) this;
        }

        public Criteria andT_idBetween(Integer value1, Integer value2) {
            addCriterion("t_id between", value1, value2, "t_id");
            return (Criteria) this;
        }

        public Criteria andT_idNotBetween(Integer value1, Integer value2) {
            addCriterion("t_id not between", value1, value2, "t_id");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreIsNull() {
            addCriterion("attendance_score is null");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreIsNotNull() {
            addCriterion("attendance_score is not null");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreEqualTo(Integer value) {
            addCriterion("attendance_score =", value, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreNotEqualTo(Integer value) {
            addCriterion("attendance_score <>", value, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreGreaterThan(Integer value) {
            addCriterion("attendance_score >", value, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("attendance_score >=", value, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreLessThan(Integer value) {
            addCriterion("attendance_score <", value, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreLessThanOrEqualTo(Integer value) {
            addCriterion("attendance_score <=", value, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreIn(List<Integer> values) {
            addCriterion("attendance_score in", values, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreNotIn(List<Integer> values) {
            addCriterion("attendance_score not in", values, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreBetween(Integer value1, Integer value2) {
            addCriterion("attendance_score between", value1, value2, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andAttendance_scoreNotBetween(Integer value1, Integer value2) {
            addCriterion("attendance_score not between", value1, value2, "attendance_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreIsNull() {
            addCriterion("task_score is null");
            return (Criteria) this;
        }

        public Criteria andTask_scoreIsNotNull() {
            addCriterion("task_score is not null");
            return (Criteria) this;
        }

        public Criteria andTask_scoreEqualTo(Integer value) {
            addCriterion("task_score =", value, "task_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreNotEqualTo(Integer value) {
            addCriterion("task_score <>", value, "task_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreGreaterThan(Integer value) {
            addCriterion("task_score >", value, "task_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("task_score >=", value, "task_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreLessThan(Integer value) {
            addCriterion("task_score <", value, "task_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreLessThanOrEqualTo(Integer value) {
            addCriterion("task_score <=", value, "task_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreIn(List<Integer> values) {
            addCriterion("task_score in", values, "task_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreNotIn(List<Integer> values) {
            addCriterion("task_score not in", values, "task_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreBetween(Integer value1, Integer value2) {
            addCriterion("task_score between", value1, value2, "task_score");
            return (Criteria) this;
        }

        public Criteria andTask_scoreNotBetween(Integer value1, Integer value2) {
            addCriterion("task_score not between", value1, value2, "task_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreIsNull() {
            addCriterion("professionalism_score is null");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreIsNotNull() {
            addCriterion("professionalism_score is not null");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreEqualTo(Integer value) {
            addCriterion("professionalism_score =", value, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreNotEqualTo(Integer value) {
            addCriterion("professionalism_score <>", value, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreGreaterThan(Integer value) {
            addCriterion("professionalism_score >", value, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("professionalism_score >=", value, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreLessThan(Integer value) {
            addCriterion("professionalism_score <", value, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreLessThanOrEqualTo(Integer value) {
            addCriterion("professionalism_score <=", value, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreIn(List<Integer> values) {
            addCriterion("professionalism_score in", values, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreNotIn(List<Integer> values) {
            addCriterion("professionalism_score not in", values, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreBetween(Integer value1, Integer value2) {
            addCriterion("professionalism_score between", value1, value2, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andProfessionalism_scoreNotBetween(Integer value1, Integer value2) {
            addCriterion("professionalism_score not between", value1, value2, "professionalism_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreIsNull() {
            addCriterion("performance_score is null");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreIsNotNull() {
            addCriterion("performance_score is not null");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreEqualTo(Integer value) {
            addCriterion("performance_score =", value, "performance_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreNotEqualTo(Integer value) {
            addCriterion("performance_score <>", value, "performance_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreGreaterThan(Integer value) {
            addCriterion("performance_score >", value, "performance_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("performance_score >=", value, "performance_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreLessThan(Integer value) {
            addCriterion("performance_score <", value, "performance_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreLessThanOrEqualTo(Integer value) {
            addCriterion("performance_score <=", value, "performance_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreIn(List<Integer> values) {
            addCriterion("performance_score in", values, "performance_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreNotIn(List<Integer> values) {
            addCriterion("performance_score not in", values, "performance_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreBetween(Integer value1, Integer value2) {
            addCriterion("performance_score between", value1, value2, "performance_score");
            return (Criteria) this;
        }

        public Criteria andPerformance_scoreNotBetween(Integer value1, Integer value2) {
            addCriterion("performance_score not between", value1, value2, "performance_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreIsNull() {
            addCriterion("summary_score is null");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreIsNotNull() {
            addCriterion("summary_score is not null");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreEqualTo(Integer value) {
            addCriterion("summary_score =", value, "summary_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreNotEqualTo(Integer value) {
            addCriterion("summary_score <>", value, "summary_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreGreaterThan(Integer value) {
            addCriterion("summary_score >", value, "summary_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("summary_score >=", value, "summary_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreLessThan(Integer value) {
            addCriterion("summary_score <", value, "summary_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreLessThanOrEqualTo(Integer value) {
            addCriterion("summary_score <=", value, "summary_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreIn(List<Integer> values) {
            addCriterion("summary_score in", values, "summary_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreNotIn(List<Integer> values) {
            addCriterion("summary_score not in", values, "summary_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreBetween(Integer value1, Integer value2) {
            addCriterion("summary_score between", value1, value2, "summary_score");
            return (Criteria) this;
        }

        public Criteria andSummary_scoreNotBetween(Integer value1, Integer value2) {
            addCriterion("summary_score not between", value1, value2, "summary_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreIsNull() {
            addCriterion("practice_result_score is null");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreIsNotNull() {
            addCriterion("practice_result_score is not null");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreEqualTo(Integer value) {
            addCriterion("practice_result_score =", value, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreNotEqualTo(Integer value) {
            addCriterion("practice_result_score <>", value, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreGreaterThan(Integer value) {
            addCriterion("practice_result_score >", value, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("practice_result_score >=", value, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreLessThan(Integer value) {
            addCriterion("practice_result_score <", value, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreLessThanOrEqualTo(Integer value) {
            addCriterion("practice_result_score <=", value, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreIn(List<Integer> values) {
            addCriterion("practice_result_score in", values, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreNotIn(List<Integer> values) {
            addCriterion("practice_result_score not in", values, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreBetween(Integer value1, Integer value2) {
            addCriterion("practice_result_score between", value1, value2, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andPractice_result_scoreNotBetween(Integer value1, Integer value2) {
            addCriterion("practice_result_score not between", value1, value2, "practice_result_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreIsNull() {
            addCriterion("company_score is null");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreIsNotNull() {
            addCriterion("company_score is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreEqualTo(Integer value) {
            addCriterion("company_score =", value, "company_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreNotEqualTo(Integer value) {
            addCriterion("company_score <>", value, "company_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreGreaterThan(Integer value) {
            addCriterion("company_score >", value, "company_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("company_score >=", value, "company_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreLessThan(Integer value) {
            addCriterion("company_score <", value, "company_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreLessThanOrEqualTo(Integer value) {
            addCriterion("company_score <=", value, "company_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreIn(List<Integer> values) {
            addCriterion("company_score in", values, "company_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreNotIn(List<Integer> values) {
            addCriterion("company_score not in", values, "company_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreBetween(Integer value1, Integer value2) {
            addCriterion("company_score between", value1, value2, "company_score");
            return (Criteria) this;
        }

        public Criteria andCompany_scoreNotBetween(Integer value1, Integer value2) {
            addCriterion("company_score not between", value1, value2, "company_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreIsNull() {
            addCriterion("school_score is null");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreIsNotNull() {
            addCriterion("school_score is not null");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreEqualTo(Integer value) {
            addCriterion("school_score =", value, "school_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreNotEqualTo(Integer value) {
            addCriterion("school_score <>", value, "school_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreGreaterThan(Integer value) {
            addCriterion("school_score >", value, "school_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("school_score >=", value, "school_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreLessThan(Integer value) {
            addCriterion("school_score <", value, "school_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreLessThanOrEqualTo(Integer value) {
            addCriterion("school_score <=", value, "school_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreIn(List<Integer> values) {
            addCriterion("school_score in", values, "school_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreNotIn(List<Integer> values) {
            addCriterion("school_score not in", values, "school_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreBetween(Integer value1, Integer value2) {
            addCriterion("school_score between", value1, value2, "school_score");
            return (Criteria) this;
        }

        public Criteria andSchool_scoreNotBetween(Integer value1, Integer value2) {
            addCriterion("school_score not between", value1, value2, "school_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreIsNull() {
            addCriterion("total_score is null");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreIsNotNull() {
            addCriterion("total_score is not null");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreEqualTo(Integer value) {
            addCriterion("total_score =", value, "total_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreNotEqualTo(Integer value) {
            addCriterion("total_score <>", value, "total_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreGreaterThan(Integer value) {
            addCriterion("total_score >", value, "total_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_score >=", value, "total_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreLessThan(Integer value) {
            addCriterion("total_score <", value, "total_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreLessThanOrEqualTo(Integer value) {
            addCriterion("total_score <=", value, "total_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreIn(List<Integer> values) {
            addCriterion("total_score in", values, "total_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreNotIn(List<Integer> values) {
            addCriterion("total_score not in", values, "total_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreBetween(Integer value1, Integer value2) {
            addCriterion("total_score between", value1, value2, "total_score");
            return (Criteria) this;
        }

        public Criteria andTotal_scoreNotBetween(Integer value1, Integer value2) {
            addCriterion("total_score not between", value1, value2, "total_score");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}