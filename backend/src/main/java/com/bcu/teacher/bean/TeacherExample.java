package com.bcu.teacher.bean;

import java.util.ArrayList;
import java.util.List;

public class TeacherExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TeacherExample() {
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

        public Criteria andTeacher_nameIsNull() {
            addCriterion("teacher_name is null");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameIsNotNull() {
            addCriterion("teacher_name is not null");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameEqualTo(String value) {
            addCriterion("teacher_name =", value, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameNotEqualTo(String value) {
            addCriterion("teacher_name <>", value, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameGreaterThan(String value) {
            addCriterion("teacher_name >", value, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameGreaterThanOrEqualTo(String value) {
            addCriterion("teacher_name >=", value, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameLessThan(String value) {
            addCriterion("teacher_name <", value, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameLessThanOrEqualTo(String value) {
            addCriterion("teacher_name <=", value, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameLike(String value) {
            addCriterion("teacher_name like", value, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameNotLike(String value) {
            addCriterion("teacher_name not like", value, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameIn(List<String> values) {
            addCriterion("teacher_name in", values, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameNotIn(List<String> values) {
            addCriterion("teacher_name not in", values, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameBetween(String value1, String value2) {
            addCriterion("teacher_name between", value1, value2, "teacher_name");
            return (Criteria) this;
        }

        public Criteria andTeacher_nameNotBetween(String value1, String value2) {
            addCriterion("teacher_name not between", value1, value2, "teacher_name");
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

        public Criteria andAdvisor_typeIsNull() {
            addCriterion("advisor_type is null");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeIsNotNull() {
            addCriterion("advisor_type is not null");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeEqualTo(String value) {
            addCriterion("advisor_type =", value, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeNotEqualTo(String value) {
            addCriterion("advisor_type <>", value, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeGreaterThan(String value) {
            addCriterion("advisor_type >", value, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeGreaterThanOrEqualTo(String value) {
            addCriterion("advisor_type >=", value, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeLessThan(String value) {
            addCriterion("advisor_type <", value, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeLessThanOrEqualTo(String value) {
            addCriterion("advisor_type <=", value, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeLike(String value) {
            addCriterion("advisor_type like", value, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeNotLike(String value) {
            addCriterion("advisor_type not like", value, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeIn(List<String> values) {
            addCriterion("advisor_type in", values, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeNotIn(List<String> values) {
            addCriterion("advisor_type not in", values, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeBetween(String value1, String value2) {
            addCriterion("advisor_type between", value1, value2, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAdvisor_typeNotBetween(String value1, String value2) {
            addCriterion("advisor_type not between", value1, value2, "advisor_type");
            return (Criteria) this;
        }

        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Integer value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Integer value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Integer value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Integer value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Integer value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Integer value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Integer> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Integer> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Integer value1, Integer value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Integer value1, Integer value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andEducationIsNull() {
            addCriterion("education is null");
            return (Criteria) this;
        }

        public Criteria andEducationIsNotNull() {
            addCriterion("education is not null");
            return (Criteria) this;
        }

        public Criteria andEducationEqualTo(String value) {
            addCriterion("education =", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotEqualTo(String value) {
            addCriterion("education <>", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThan(String value) {
            addCriterion("education >", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationGreaterThanOrEqualTo(String value) {
            addCriterion("education >=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThan(String value) {
            addCriterion("education <", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLessThanOrEqualTo(String value) {
            addCriterion("education <=", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationLike(String value) {
            addCriterion("education like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotLike(String value) {
            addCriterion("education not like", value, "education");
            return (Criteria) this;
        }

        public Criteria andEducationIn(List<String> values) {
            addCriterion("education in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotIn(List<String> values) {
            addCriterion("education not in", values, "education");
            return (Criteria) this;
        }

        public Criteria andEducationBetween(String value1, String value2) {
            addCriterion("education between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andEducationNotBetween(String value1, String value2) {
            addCriterion("education not between", value1, value2, "education");
            return (Criteria) this;
        }

        public Criteria andPositionIsNull() {
            addCriterion("position is null");
            return (Criteria) this;
        }

        public Criteria andPositionIsNotNull() {
            addCriterion("position is not null");
            return (Criteria) this;
        }

        public Criteria andPositionEqualTo(String value) {
            addCriterion("position =", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotEqualTo(String value) {
            addCriterion("position <>", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThan(String value) {
            addCriterion("position >", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThanOrEqualTo(String value) {
            addCriterion("position >=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThan(String value) {
            addCriterion("position <", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThanOrEqualTo(String value) {
            addCriterion("position <=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLike(String value) {
            addCriterion("position like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotLike(String value) {
            addCriterion("position not like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionIn(List<String> values) {
            addCriterion("position in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotIn(List<String> values) {
            addCriterion("position not in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionBetween(String value1, String value2) {
            addCriterion("position between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotBetween(String value1, String value2) {
            addCriterion("position not between", value1, value2, "position");
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

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andSignature_pathIsNull() {
            addCriterion("signature_path is null");
            return (Criteria) this;
        }

        public Criteria andSignature_pathIsNotNull() {
            addCriterion("signature_path is not null");
            return (Criteria) this;
        }

        public Criteria andSignature_pathEqualTo(String value) {
            addCriterion("signature_path =", value, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathNotEqualTo(String value) {
            addCriterion("signature_path <>", value, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathGreaterThan(String value) {
            addCriterion("signature_path >", value, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathGreaterThanOrEqualTo(String value) {
            addCriterion("signature_path >=", value, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathLessThan(String value) {
            addCriterion("signature_path <", value, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathLessThanOrEqualTo(String value) {
            addCriterion("signature_path <=", value, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathLike(String value) {
            addCriterion("signature_path like", value, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathNotLike(String value) {
            addCriterion("signature_path not like", value, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathIn(List<String> values) {
            addCriterion("signature_path in", values, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathNotIn(List<String> values) {
            addCriterion("signature_path not in", values, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathBetween(String value1, String value2) {
            addCriterion("signature_path between", value1, value2, "signature_path");
            return (Criteria) this;
        }

        public Criteria andSignature_pathNotBetween(String value1, String value2) {
            addCriterion("signature_path not between", value1, value2, "signature_path");
            return (Criteria) this;
        }

        public Criteria andT_numberIsNull() {
            addCriterion("t_number is null");
            return (Criteria) this;
        }

        public Criteria andT_numberIsNotNull() {
            addCriterion("t_number is not null");
            return (Criteria) this;
        }

        public Criteria andT_numberEqualTo(String value) {
            addCriterion("t_number =", value, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberNotEqualTo(String value) {
            addCriterion("t_number <>", value, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberGreaterThan(String value) {
            addCriterion("t_number >", value, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberGreaterThanOrEqualTo(String value) {
            addCriterion("t_number >=", value, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberLessThan(String value) {
            addCriterion("t_number <", value, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberLessThanOrEqualTo(String value) {
            addCriterion("t_number <=", value, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberLike(String value) {
            addCriterion("t_number like", value, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberNotLike(String value) {
            addCriterion("t_number not like", value, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberIn(List<String> values) {
            addCriterion("t_number in", values, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberNotIn(List<String> values) {
            addCriterion("t_number not in", values, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberBetween(String value1, String value2) {
            addCriterion("t_number between", value1, value2, "t_number");
            return (Criteria) this;
        }

        public Criteria andT_numberNotBetween(String value1, String value2) {
            addCriterion("t_number not between", value1, value2, "t_number");
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