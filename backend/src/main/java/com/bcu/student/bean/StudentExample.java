package com.bcu.student.bean;

import java.util.ArrayList;
import java.util.List;

public class StudentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StudentExample() {
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

        public Criteria andStudent_numberIsNull() {
            addCriterion("student_number is null");
            return (Criteria) this;
        }

        public Criteria andStudent_numberIsNotNull() {
            addCriterion("student_number is not null");
            return (Criteria) this;
        }

        public Criteria andStudent_numberEqualTo(String value) {
            addCriterion("student_number =", value, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberNotEqualTo(String value) {
            addCriterion("student_number <>", value, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberGreaterThan(String value) {
            addCriterion("student_number >", value, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberGreaterThanOrEqualTo(String value) {
            addCriterion("student_number >=", value, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberLessThan(String value) {
            addCriterion("student_number <", value, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberLessThanOrEqualTo(String value) {
            addCriterion("student_number <=", value, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberLike(String value) {
            addCriterion("student_number like", value, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberNotLike(String value) {
            addCriterion("student_number not like", value, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberIn(List<String> values) {
            addCriterion("student_number in", values, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberNotIn(List<String> values) {
            addCriterion("student_number not in", values, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberBetween(String value1, String value2) {
            addCriterion("student_number between", value1, value2, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_numberNotBetween(String value1, String value2) {
            addCriterion("student_number not between", value1, value2, "student_number");
            return (Criteria) this;
        }

        public Criteria andStudent_nameIsNull() {
            addCriterion("student_name is null");
            return (Criteria) this;
        }

        public Criteria andStudent_nameIsNotNull() {
            addCriterion("student_name is not null");
            return (Criteria) this;
        }

        public Criteria andStudent_nameEqualTo(String value) {
            addCriterion("student_name =", value, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameNotEqualTo(String value) {
            addCriterion("student_name <>", value, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameGreaterThan(String value) {
            addCriterion("student_name >", value, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameGreaterThanOrEqualTo(String value) {
            addCriterion("student_name >=", value, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameLessThan(String value) {
            addCriterion("student_name <", value, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameLessThanOrEqualTo(String value) {
            addCriterion("student_name <=", value, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameLike(String value) {
            addCriterion("student_name like", value, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameNotLike(String value) {
            addCriterion("student_name not like", value, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameIn(List<String> values) {
            addCriterion("student_name in", values, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameNotIn(List<String> values) {
            addCriterion("student_name not in", values, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameBetween(String value1, String value2) {
            addCriterion("student_name between", value1, value2, "student_name");
            return (Criteria) this;
        }

        public Criteria andStudent_nameNotBetween(String value1, String value2) {
            addCriterion("student_name not between", value1, value2, "student_name");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(String value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(String value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(String value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(String value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(String value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(String value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLike(String value) {
            addCriterion("gender like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotLike(String value) {
            addCriterion("gender not like", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<String> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<String> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(String value1, String value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(String value1, String value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneIsNull() {
            addCriterion("parent_phone is null");
            return (Criteria) this;
        }

        public Criteria andParent_phoneIsNotNull() {
            addCriterion("parent_phone is not null");
            return (Criteria) this;
        }

        public Criteria andParent_phoneEqualTo(String value) {
            addCriterion("parent_phone =", value, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneNotEqualTo(String value) {
            addCriterion("parent_phone <>", value, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneGreaterThan(String value) {
            addCriterion("parent_phone >", value, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneGreaterThanOrEqualTo(String value) {
            addCriterion("parent_phone >=", value, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneLessThan(String value) {
            addCriterion("parent_phone <", value, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneLessThanOrEqualTo(String value) {
            addCriterion("parent_phone <=", value, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneLike(String value) {
            addCriterion("parent_phone like", value, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneNotLike(String value) {
            addCriterion("parent_phone not like", value, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneIn(List<String> values) {
            addCriterion("parent_phone in", values, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneNotIn(List<String> values) {
            addCriterion("parent_phone not in", values, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneBetween(String value1, String value2) {
            addCriterion("parent_phone between", value1, value2, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andParent_phoneNotBetween(String value1, String value2) {
            addCriterion("parent_phone not between", value1, value2, "parent_phone");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearIsNull() {
            addCriterion("enrollment_year is null");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearIsNotNull() {
            addCriterion("enrollment_year is not null");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearEqualTo(Integer value) {
            addCriterion("enrollment_year =", value, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearNotEqualTo(Integer value) {
            addCriterion("enrollment_year <>", value, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearGreaterThan(Integer value) {
            addCriterion("enrollment_year >", value, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearGreaterThanOrEqualTo(Integer value) {
            addCriterion("enrollment_year >=", value, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearLessThan(Integer value) {
            addCriterion("enrollment_year <", value, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearLessThanOrEqualTo(Integer value) {
            addCriterion("enrollment_year <=", value, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearIn(List<Integer> values) {
            addCriterion("enrollment_year in", values, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearNotIn(List<Integer> values) {
            addCriterion("enrollment_year not in", values, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearBetween(Integer value1, Integer value2) {
            addCriterion("enrollment_year between", value1, value2, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andEnrollment_yearNotBetween(Integer value1, Integer value2) {
            addCriterion("enrollment_year not between", value1, value2, "enrollment_year");
            return (Criteria) this;
        }

        public Criteria andDepartmentIsNull() {
            addCriterion("department is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIsNotNull() {
            addCriterion("department is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentEqualTo(String value) {
            addCriterion("department =", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotEqualTo(String value) {
            addCriterion("department <>", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThan(String value) {
            addCriterion("department >", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThanOrEqualTo(String value) {
            addCriterion("department >=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThan(String value) {
            addCriterion("department <", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThanOrEqualTo(String value) {
            addCriterion("department <=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLike(String value) {
            addCriterion("department like", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotLike(String value) {
            addCriterion("department not like", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentIn(List<String> values) {
            addCriterion("department in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotIn(List<String> values) {
            addCriterion("department not in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentBetween(String value1, String value2) {
            addCriterion("department between", value1, value2, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotBetween(String value1, String value2) {
            addCriterion("department not between", value1, value2, "department");
            return (Criteria) this;
        }

        public Criteria andMajorIsNull() {
            addCriterion("major is null");
            return (Criteria) this;
        }

        public Criteria andMajorIsNotNull() {
            addCriterion("major is not null");
            return (Criteria) this;
        }

        public Criteria andMajorEqualTo(String value) {
            addCriterion("major =", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotEqualTo(String value) {
            addCriterion("major <>", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThan(String value) {
            addCriterion("major >", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorGreaterThanOrEqualTo(String value) {
            addCriterion("major >=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThan(String value) {
            addCriterion("major <", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLessThanOrEqualTo(String value) {
            addCriterion("major <=", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorLike(String value) {
            addCriterion("major like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotLike(String value) {
            addCriterion("major not like", value, "major");
            return (Criteria) this;
        }

        public Criteria andMajorIn(List<String> values) {
            addCriterion("major in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotIn(List<String> values) {
            addCriterion("major not in", values, "major");
            return (Criteria) this;
        }

        public Criteria andMajorBetween(String value1, String value2) {
            addCriterion("major between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andMajorNotBetween(String value1, String value2) {
            addCriterion("major not between", value1, value2, "major");
            return (Criteria) this;
        }

        public Criteria andClubIsNull() {
            addCriterion("stu_class is null");
            return (Criteria) this;
        }

        public Criteria andClubIsNotNull() {
            addCriterion("stu_class is not null");
            return (Criteria) this;
        }

        public Criteria andClubEqualTo(String value) {
            addCriterion("stu_class =", value, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubNotEqualTo(String value) {
            addCriterion("stu_class <>", value, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubGreaterThan(String value) {
            addCriterion("stu_class >", value, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubGreaterThanOrEqualTo(String value) {
            addCriterion("stu_class >=", value, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubLessThan(String value) {
            addCriterion("stu_class <", value, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubLessThanOrEqualTo(String value) {
            addCriterion("stu_class <=", value, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubLike(String value) {
            addCriterion("stu_class like", value, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubNotLike(String value) {
            addCriterion("stu_class not like", value, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubIn(List<String> values) {
            addCriterion("stu_class in", values, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubNotIn(List<String> values) {
            addCriterion("stu_class not in", values, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubBetween(String value1, String value2) {
            addCriterion("stu_class between", value1, value2, "stu_class");
            return (Criteria) this;
        }

        public Criteria andClubNotBetween(String value1, String value2) {
            addCriterion("stu_class not between", value1, value2, "stu_class");
            return (Criteria) this;
        }

        public Criteria andCounselorIsNull() {
            addCriterion("counselor is null");
            return (Criteria) this;
        }

        public Criteria andCounselorIsNotNull() {
            addCriterion("counselor is not null");
            return (Criteria) this;
        }

        public Criteria andCounselorEqualTo(String value) {
            addCriterion("counselor =", value, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorNotEqualTo(String value) {
            addCriterion("counselor <>", value, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorGreaterThan(String value) {
            addCriterion("counselor >", value, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorGreaterThanOrEqualTo(String value) {
            addCriterion("counselor >=", value, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorLessThan(String value) {
            addCriterion("counselor <", value, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorLessThanOrEqualTo(String value) {
            addCriterion("counselor <=", value, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorLike(String value) {
            addCriterion("counselor like", value, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorNotLike(String value) {
            addCriterion("counselor not like", value, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorIn(List<String> values) {
            addCriterion("counselor in", values, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorNotIn(List<String> values) {
            addCriterion("counselor not in", values, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorBetween(String value1, String value2) {
            addCriterion("counselor between", value1, value2, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselorNotBetween(String value1, String value2) {
            addCriterion("counselor not between", value1, value2, "counselor");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneIsNull() {
            addCriterion("counselor_phone is null");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneIsNotNull() {
            addCriterion("counselor_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneEqualTo(String value) {
            addCriterion("counselor_phone =", value, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneNotEqualTo(String value) {
            addCriterion("counselor_phone <>", value, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneGreaterThan(String value) {
            addCriterion("counselor_phone >", value, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneGreaterThanOrEqualTo(String value) {
            addCriterion("counselor_phone >=", value, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneLessThan(String value) {
            addCriterion("counselor_phone <", value, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneLessThanOrEqualTo(String value) {
            addCriterion("counselor_phone <=", value, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneLike(String value) {
            addCriterion("counselor_phone like", value, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneNotLike(String value) {
            addCriterion("counselor_phone not like", value, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneIn(List<String> values) {
            addCriterion("counselor_phone in", values, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneNotIn(List<String> values) {
            addCriterion("counselor_phone not in", values, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneBetween(String value1, String value2) {
            addCriterion("counselor_phone between", value1, value2, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andCounselor_phoneNotBetween(String value1, String value2) {
            addCriterion("counselor_phone not between", value1, value2, "counselor_phone");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idIsNull() {
            addCriterion("academic_advisor_id is null");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idIsNotNull() {
            addCriterion("academic_advisor_id is not null");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idEqualTo(Integer value) {
            addCriterion("academic_advisor_id =", value, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idNotEqualTo(Integer value) {
            addCriterion("academic_advisor_id <>", value, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idGreaterThan(Integer value) {
            addCriterion("academic_advisor_id >", value, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("academic_advisor_id >=", value, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idLessThan(Integer value) {
            addCriterion("academic_advisor_id <", value, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idLessThanOrEqualTo(Integer value) {
            addCriterion("academic_advisor_id <=", value, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idIn(List<Integer> values) {
            addCriterion("academic_advisor_id in", values, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idNotIn(List<Integer> values) {
            addCriterion("academic_advisor_id not in", values, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idBetween(Integer value1, Integer value2) {
            addCriterion("academic_advisor_id between", value1, value2, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andAcademic_advisor_idNotBetween(Integer value1, Integer value2) {
            addCriterion("academic_advisor_id not between", value1, value2, "academic_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idIsNull() {
            addCriterion("industry_advisor_id is null");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idIsNotNull() {
            addCriterion("industry_advisor_id is not null");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idEqualTo(Integer value) {
            addCriterion("industry_advisor_id =", value, "industry_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idNotEqualTo(Integer value) {
            addCriterion("industry_advisor_id <>", value, "industry_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idGreaterThan(Integer value) {
            addCriterion("industry_advisor_id >", value, "industry_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idGreaterThanOrEqualTo(Integer value) {
            addCriterion("industry_advisor_id >=", value, "industry_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idLessThan(Integer value) {
            addCriterion("industry_advisor_id <", value, "industry_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idLessThanOrEqualTo(Integer value) {
            addCriterion("industry_advisor_id <=", value, "industry_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idIn(List<Integer> values) {
            addCriterion("industry_advisor_id in", values, "industry_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idNotIn(List<Integer> values) {
            addCriterion("industry_advisor_id not in", values, "industry_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idBetween(Integer value1, Integer value2) {
            addCriterion("industry_advisor_id between", value1, value2, "industry_advisor_id");
            return (Criteria) this;
        }

        public Criteria andIndustry_advisor_idNotBetween(Integer value1, Integer value2) {
            addCriterion("industry_advisor_id not between", value1, value2, "industry_advisor_id");
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