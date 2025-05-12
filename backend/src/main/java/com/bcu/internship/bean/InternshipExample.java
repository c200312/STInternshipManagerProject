package com.bcu.internship.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class InternshipExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InternshipExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andStart_dateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStart_dateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStart_dateEqualTo(Date value) {
            addCriterionForJDBCDate("start_date =", value, "start_date");
            return (Criteria) this;
        }

        public Criteria andStart_dateNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <>", value, "start_date");
            return (Criteria) this;
        }

        public Criteria andStart_dateGreaterThan(Date value) {
            addCriterionForJDBCDate("start_date >", value, "start_date");
            return (Criteria) this;
        }

        public Criteria andStart_dateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date >=", value, "start_date");
            return (Criteria) this;
        }

        public Criteria andStart_dateLessThan(Date value) {
            addCriterionForJDBCDate("start_date <", value, "start_date");
            return (Criteria) this;
        }

        public Criteria andStart_dateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_date <=", value, "start_date");
            return (Criteria) this;
        }

        public Criteria andStart_dateIn(List<Date> values) {
            addCriterionForJDBCDate("start_date in", values, "start_date");
            return (Criteria) this;
        }

        public Criteria andStart_dateNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_date not in", values, "start_date");
            return (Criteria) this;
        }

        public Criteria andStart_dateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date between", value1, value2, "start_date");
            return (Criteria) this;
        }

        public Criteria andStart_dateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_date not between", value1, value2, "start_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEnd_dateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEnd_dateEqualTo(Date value) {
            addCriterionForJDBCDate("end_date =", value, "end_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <>", value, "end_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateGreaterThan(Date value) {
            addCriterionForJDBCDate("end_date >", value, "end_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date >=", value, "end_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateLessThan(Date value) {
            addCriterionForJDBCDate("end_date <", value, "end_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_date <=", value, "end_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateIn(List<Date> values) {
            addCriterionForJDBCDate("end_date in", values, "end_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_date not in", values, "end_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date between", value1, value2, "end_date");
            return (Criteria) this;
        }

        public Criteria andEnd_dateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_date not between", value1, value2, "end_date");
            return (Criteria) this;
        }

        public Criteria andActual_daysIsNull() {
            addCriterion("actual_days is null");
            return (Criteria) this;
        }

        public Criteria andActual_daysIsNotNull() {
            addCriterion("actual_days is not null");
            return (Criteria) this;
        }

        public Criteria andActual_daysEqualTo(Integer value) {
            addCriterion("actual_days =", value, "actual_days");
            return (Criteria) this;
        }

        public Criteria andActual_daysNotEqualTo(Integer value) {
            addCriterion("actual_days <>", value, "actual_days");
            return (Criteria) this;
        }

        public Criteria andActual_daysGreaterThan(Integer value) {
            addCriterion("actual_days >", value, "actual_days");
            return (Criteria) this;
        }

        public Criteria andActual_daysGreaterThanOrEqualTo(Integer value) {
            addCriterion("actual_days >=", value, "actual_days");
            return (Criteria) this;
        }

        public Criteria andActual_daysLessThan(Integer value) {
            addCriterion("actual_days <", value, "actual_days");
            return (Criteria) this;
        }

        public Criteria andActual_daysLessThanOrEqualTo(Integer value) {
            addCriterion("actual_days <=", value, "actual_days");
            return (Criteria) this;
        }

        public Criteria andActual_daysIn(List<Integer> values) {
            addCriterion("actual_days in", values, "actual_days");
            return (Criteria) this;
        }

        public Criteria andActual_daysNotIn(List<Integer> values) {
            addCriterion("actual_days not in", values, "actual_days");
            return (Criteria) this;
        }

        public Criteria andActual_daysBetween(Integer value1, Integer value2) {
            addCriterion("actual_days between", value1, value2, "actual_days");
            return (Criteria) this;
        }

        public Criteria andActual_daysNotBetween(Integer value1, Integer value2) {
            addCriterion("actual_days not between", value1, value2, "actual_days");
            return (Criteria) this;
        }

        public Criteria andCompany_countIsNull() {
            addCriterion("company_count is null");
            return (Criteria) this;
        }

        public Criteria andCompany_countIsNotNull() {
            addCriterion("company_count is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_countEqualTo(Integer value) {
            addCriterion("company_count =", value, "company_count");
            return (Criteria) this;
        }

        public Criteria andCompany_countNotEqualTo(Integer value) {
            addCriterion("company_count <>", value, "company_count");
            return (Criteria) this;
        }

        public Criteria andCompany_countGreaterThan(Integer value) {
            addCriterion("company_count >", value, "company_count");
            return (Criteria) this;
        }

        public Criteria andCompany_countGreaterThanOrEqualTo(Integer value) {
            addCriterion("company_count >=", value, "company_count");
            return (Criteria) this;
        }

        public Criteria andCompany_countLessThan(Integer value) {
            addCriterion("company_count <", value, "company_count");
            return (Criteria) this;
        }

        public Criteria andCompany_countLessThanOrEqualTo(Integer value) {
            addCriterion("company_count <=", value, "company_count");
            return (Criteria) this;
        }

        public Criteria andCompany_countIn(List<Integer> values) {
            addCriterion("company_count in", values, "company_count");
            return (Criteria) this;
        }

        public Criteria andCompany_countNotIn(List<Integer> values) {
            addCriterion("company_count not in", values, "company_count");
            return (Criteria) this;
        }

        public Criteria andCompany_countBetween(Integer value1, Integer value2) {
            addCriterion("company_count between", value1, value2, "company_count");
            return (Criteria) this;
        }

        public Criteria andCompany_countNotBetween(Integer value1, Integer value2) {
            addCriterion("company_count not between", value1, value2, "company_count");
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

        public Criteria andSalaryIsNull() {
            addCriterion("salary is null");
            return (Criteria) this;
        }

        public Criteria andSalaryIsNotNull() {
            addCriterion("salary is not null");
            return (Criteria) this;
        }

        public Criteria andSalaryEqualTo(Integer value) {
            addCriterion("salary =", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotEqualTo(Integer value) {
            addCriterion("salary <>", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryGreaterThan(Integer value) {
            addCriterion("salary >", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryGreaterThanOrEqualTo(Integer value) {
            addCriterion("salary >=", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryLessThan(Integer value) {
            addCriterion("salary <", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryLessThanOrEqualTo(Integer value) {
            addCriterion("salary <=", value, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryIn(List<Integer> values) {
            addCriterion("salary in", values, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotIn(List<Integer> values) {
            addCriterion("salary not in", values, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryBetween(Integer value1, Integer value2) {
            addCriterion("salary between", value1, value2, "salary");
            return (Criteria) this;
        }

        public Criteria andSalaryNotBetween(Integer value1, Integer value2) {
            addCriterion("salary not between", value1, value2, "salary");
            return (Criteria) this;
        }

        public Criteria andInternship_modeIsNull() {
            addCriterion("internship_mode is null");
            return (Criteria) this;
        }

        public Criteria andInternship_modeIsNotNull() {
            addCriterion("internship_mode is not null");
            return (Criteria) this;
        }

        public Criteria andInternship_modeEqualTo(String value) {
            addCriterion("internship_mode =", value, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeNotEqualTo(String value) {
            addCriterion("internship_mode <>", value, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeGreaterThan(String value) {
            addCriterion("internship_mode >", value, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeGreaterThanOrEqualTo(String value) {
            addCriterion("internship_mode >=", value, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeLessThan(String value) {
            addCriterion("internship_mode <", value, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeLessThanOrEqualTo(String value) {
            addCriterion("internship_mode <=", value, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeLike(String value) {
            addCriterion("internship_mode like", value, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeNotLike(String value) {
            addCriterion("internship_mode not like", value, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeIn(List<String> values) {
            addCriterion("internship_mode in", values, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeNotIn(List<String> values) {
            addCriterion("internship_mode not in", values, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeBetween(String value1, String value2) {
            addCriterion("internship_mode between", value1, value2, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_modeNotBetween(String value1, String value2) {
            addCriterion("internship_mode not between", value1, value2, "internship_mode");
            return (Criteria) this;
        }

        public Criteria andInternship_typeIsNull() {
            addCriterion("internship_type is null");
            return (Criteria) this;
        }

        public Criteria andInternship_typeIsNotNull() {
            addCriterion("internship_type is not null");
            return (Criteria) this;
        }

        public Criteria andInternship_typeEqualTo(String value) {
            addCriterion("internship_type =", value, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeNotEqualTo(String value) {
            addCriterion("internship_type <>", value, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeGreaterThan(String value) {
            addCriterion("internship_type >", value, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeGreaterThanOrEqualTo(String value) {
            addCriterion("internship_type >=", value, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeLessThan(String value) {
            addCriterion("internship_type <", value, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeLessThanOrEqualTo(String value) {
            addCriterion("internship_type <=", value, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeLike(String value) {
            addCriterion("internship_type like", value, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeNotLike(String value) {
            addCriterion("internship_type not like", value, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeIn(List<String> values) {
            addCriterion("internship_type in", values, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeNotIn(List<String> values) {
            addCriterion("internship_type not in", values, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeBetween(String value1, String value2) {
            addCriterion("internship_type between", value1, value2, "internship_type");
            return (Criteria) this;
        }

        public Criteria andInternship_typeNotBetween(String value1, String value2) {
            addCriterion("internship_type not between", value1, value2, "internship_type");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameIsNull() {
            addCriterion("company_advisor_name is null");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameIsNotNull() {
            addCriterion("company_advisor_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameEqualTo(String value) {
            addCriterion("company_advisor_name =", value, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameNotEqualTo(String value) {
            addCriterion("company_advisor_name <>", value, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameGreaterThan(String value) {
            addCriterion("company_advisor_name >", value, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameGreaterThanOrEqualTo(String value) {
            addCriterion("company_advisor_name >=", value, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameLessThan(String value) {
            addCriterion("company_advisor_name <", value, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameLessThanOrEqualTo(String value) {
            addCriterion("company_advisor_name <=", value, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameLike(String value) {
            addCriterion("company_advisor_name like", value, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameNotLike(String value) {
            addCriterion("company_advisor_name not like", value, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameIn(List<String> values) {
            addCriterion("company_advisor_name in", values, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameNotIn(List<String> values) {
            addCriterion("company_advisor_name not in", values, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameBetween(String value1, String value2) {
            addCriterion("company_advisor_name between", value1, value2, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_nameNotBetween(String value1, String value2) {
            addCriterion("company_advisor_name not between", value1, value2, "company_advisor_name");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionIsNull() {
            addCriterion("company_advisor_position is null");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionIsNotNull() {
            addCriterion("company_advisor_position is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionEqualTo(String value) {
            addCriterion("company_advisor_position =", value, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionNotEqualTo(String value) {
            addCriterion("company_advisor_position <>", value, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionGreaterThan(String value) {
            addCriterion("company_advisor_position >", value, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionGreaterThanOrEqualTo(String value) {
            addCriterion("company_advisor_position >=", value, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionLessThan(String value) {
            addCriterion("company_advisor_position <", value, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionLessThanOrEqualTo(String value) {
            addCriterion("company_advisor_position <=", value, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionLike(String value) {
            addCriterion("company_advisor_position like", value, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionNotLike(String value) {
            addCriterion("company_advisor_position not like", value, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionIn(List<String> values) {
            addCriterion("company_advisor_position in", values, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionNotIn(List<String> values) {
            addCriterion("company_advisor_position not in", values, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionBetween(String value1, String value2) {
            addCriterion("company_advisor_position between", value1, value2, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andCompany_advisor_positionNotBetween(String value1, String value2) {
            addCriterion("company_advisor_position not between", value1, value2, "company_advisor_position");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andContact_personIsNull() {
            addCriterion("contact_person is null");
            return (Criteria) this;
        }

        public Criteria andContact_personIsNotNull() {
            addCriterion("contact_person is not null");
            return (Criteria) this;
        }

        public Criteria andContact_personEqualTo(String value) {
            addCriterion("contact_person =", value, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personNotEqualTo(String value) {
            addCriterion("contact_person <>", value, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personGreaterThan(String value) {
            addCriterion("contact_person >", value, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personGreaterThanOrEqualTo(String value) {
            addCriterion("contact_person >=", value, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personLessThan(String value) {
            addCriterion("contact_person <", value, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personLessThanOrEqualTo(String value) {
            addCriterion("contact_person <=", value, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personLike(String value) {
            addCriterion("contact_person like", value, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personNotLike(String value) {
            addCriterion("contact_person not like", value, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personIn(List<String> values) {
            addCriterion("contact_person in", values, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personNotIn(List<String> values) {
            addCriterion("contact_person not in", values, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personBetween(String value1, String value2) {
            addCriterion("contact_person between", value1, value2, "contact_person");
            return (Criteria) this;
        }

        public Criteria andContact_personNotBetween(String value1, String value2) {
            addCriterion("contact_person not between", value1, value2, "contact_person");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneIsNull() {
            addCriterion("company_phone is null");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneIsNotNull() {
            addCriterion("company_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneEqualTo(String value) {
            addCriterion("company_phone =", value, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneNotEqualTo(String value) {
            addCriterion("company_phone <>", value, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneGreaterThan(String value) {
            addCriterion("company_phone >", value, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneGreaterThanOrEqualTo(String value) {
            addCriterion("company_phone >=", value, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneLessThan(String value) {
            addCriterion("company_phone <", value, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneLessThanOrEqualTo(String value) {
            addCriterion("company_phone <=", value, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneLike(String value) {
            addCriterion("company_phone like", value, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneNotLike(String value) {
            addCriterion("company_phone not like", value, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneIn(List<String> values) {
            addCriterion("company_phone in", values, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneNotIn(List<String> values) {
            addCriterion("company_phone not in", values, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneBetween(String value1, String value2) {
            addCriterion("company_phone between", value1, value2, "company_phone");
            return (Criteria) this;
        }

        public Criteria andCompany_phoneNotBetween(String value1, String value2) {
            addCriterion("company_phone not between", value1, value2, "company_phone");
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