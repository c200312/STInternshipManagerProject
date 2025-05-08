package com.bcu.company.bean;

import java.util.ArrayList;
import java.util.List;

public class CompanyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CompanyExample() {
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

        public Criteria andCompany_nameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompany_nameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompany_nameEqualTo(String value) {
            addCriterion("company_name =", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameGreaterThan(String value) {
            addCriterion("company_name >", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameLessThan(String value) {
            addCriterion("company_name <", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameLike(String value) {
            addCriterion("company_name like", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameNotLike(String value) {
            addCriterion("company_name not like", value, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameIn(List<String> values) {
            addCriterion("company_name in", values, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "company_name");
            return (Criteria) this;
        }

        public Criteria andCompany_nameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "company_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameIsNull() {
            addCriterion("practice_base_name is null");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameIsNotNull() {
            addCriterion("practice_base_name is not null");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameEqualTo(String value) {
            addCriterion("practice_base_name =", value, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameNotEqualTo(String value) {
            addCriterion("practice_base_name <>", value, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameGreaterThan(String value) {
            addCriterion("practice_base_name >", value, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameGreaterThanOrEqualTo(String value) {
            addCriterion("practice_base_name >=", value, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameLessThan(String value) {
            addCriterion("practice_base_name <", value, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameLessThanOrEqualTo(String value) {
            addCriterion("practice_base_name <=", value, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameLike(String value) {
            addCriterion("practice_base_name like", value, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameNotLike(String value) {
            addCriterion("practice_base_name not like", value, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameIn(List<String> values) {
            addCriterion("practice_base_name in", values, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameNotIn(List<String> values) {
            addCriterion("practice_base_name not in", values, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameBetween(String value1, String value2) {
            addCriterion("practice_base_name between", value1, value2, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andPractice_base_nameNotBetween(String value1, String value2) {
            addCriterion("practice_base_name not between", value1, value2, "practice_base_name");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseIsNull() {
            addCriterion("is_practice_base is null");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseIsNotNull() {
            addCriterion("is_practice_base is not null");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseEqualTo(Boolean value) {
            addCriterion("is_practice_base =", value, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseNotEqualTo(Boolean value) {
            addCriterion("is_practice_base <>", value, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseGreaterThan(Boolean value) {
            addCriterion("is_practice_base >", value, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_practice_base >=", value, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseLessThan(Boolean value) {
            addCriterion("is_practice_base <", value, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseLessThanOrEqualTo(Boolean value) {
            addCriterion("is_practice_base <=", value, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseIn(List<Boolean> values) {
            addCriterion("is_practice_base in", values, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseNotIn(List<Boolean> values) {
            addCriterion("is_practice_base not in", values, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseBetween(Boolean value1, Boolean value2) {
            addCriterion("is_practice_base between", value1, value2, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andIs_practice_baseNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_practice_base not between", value1, value2, "is_practice_base");
            return (Criteria) this;
        }

        public Criteria andCredit_codeIsNull() {
            addCriterion("credit_code is null");
            return (Criteria) this;
        }

        public Criteria andCredit_codeIsNotNull() {
            addCriterion("credit_code is not null");
            return (Criteria) this;
        }

        public Criteria andCredit_codeEqualTo(String value) {
            addCriterion("credit_code =", value, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeNotEqualTo(String value) {
            addCriterion("credit_code <>", value, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeGreaterThan(String value) {
            addCriterion("credit_code >", value, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeGreaterThanOrEqualTo(String value) {
            addCriterion("credit_code >=", value, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeLessThan(String value) {
            addCriterion("credit_code <", value, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeLessThanOrEqualTo(String value) {
            addCriterion("credit_code <=", value, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeLike(String value) {
            addCriterion("credit_code like", value, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeNotLike(String value) {
            addCriterion("credit_code not like", value, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeIn(List<String> values) {
            addCriterion("credit_code in", values, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeNotIn(List<String> values) {
            addCriterion("credit_code not in", values, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeBetween(String value1, String value2) {
            addCriterion("credit_code between", value1, value2, "credit_code");
            return (Criteria) this;
        }

        public Criteria andCredit_codeNotBetween(String value1, String value2) {
            addCriterion("credit_code not between", value1, value2, "credit_code");
            return (Criteria) this;
        }

        public Criteria andPractice_regionIsNull() {
            addCriterion("practice_region is null");
            return (Criteria) this;
        }

        public Criteria andPractice_regionIsNotNull() {
            addCriterion("practice_region is not null");
            return (Criteria) this;
        }

        public Criteria andPractice_regionEqualTo(String value) {
            addCriterion("practice_region =", value, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionNotEqualTo(String value) {
            addCriterion("practice_region <>", value, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionGreaterThan(String value) {
            addCriterion("practice_region >", value, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionGreaterThanOrEqualTo(String value) {
            addCriterion("practice_region >=", value, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionLessThan(String value) {
            addCriterion("practice_region <", value, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionLessThanOrEqualTo(String value) {
            addCriterion("practice_region <=", value, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionLike(String value) {
            addCriterion("practice_region like", value, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionNotLike(String value) {
            addCriterion("practice_region not like", value, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionIn(List<String> values) {
            addCriterion("practice_region in", values, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionNotIn(List<String> values) {
            addCriterion("practice_region not in", values, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionBetween(String value1, String value2) {
            addCriterion("practice_region between", value1, value2, "practice_region");
            return (Criteria) this;
        }

        public Criteria andPractice_regionNotBetween(String value1, String value2) {
            addCriterion("practice_region not between", value1, value2, "practice_region");
            return (Criteria) this;
        }

        public Criteria andApproval_statusIsNull() {
            addCriterion("approval_status is null");
            return (Criteria) this;
        }

        public Criteria andApproval_statusIsNotNull() {
            addCriterion("approval_status is not null");
            return (Criteria) this;
        }

        public Criteria andApproval_statusEqualTo(Byte value) {
            addCriterion("approval_status =", value, "approval_status");
            return (Criteria) this;
        }

        public Criteria andApproval_statusNotEqualTo(Byte value) {
            addCriterion("approval_status <>", value, "approval_status");
            return (Criteria) this;
        }

        public Criteria andApproval_statusGreaterThan(Byte value) {
            addCriterion("approval_status >", value, "approval_status");
            return (Criteria) this;
        }

        public Criteria andApproval_statusGreaterThanOrEqualTo(Byte value) {
            addCriterion("approval_status >=", value, "approval_status");
            return (Criteria) this;
        }

        public Criteria andApproval_statusLessThan(Byte value) {
            addCriterion("approval_status <", value, "approval_status");
            return (Criteria) this;
        }

        public Criteria andApproval_statusLessThanOrEqualTo(Byte value) {
            addCriterion("approval_status <=", value, "approval_status");
            return (Criteria) this;
        }

        public Criteria andApproval_statusIn(List<Byte> values) {
            addCriterion("approval_status in", values, "approval_status");
            return (Criteria) this;
        }

        public Criteria andApproval_statusNotIn(List<Byte> values) {
            addCriterion("approval_status not in", values, "approval_status");
            return (Criteria) this;
        }

        public Criteria andApproval_statusBetween(Byte value1, Byte value2) {
            addCriterion("approval_status between", value1, value2, "approval_status");
            return (Criteria) this;
        }

        public Criteria andApproval_statusNotBetween(Byte value1, Byte value2) {
            addCriterion("approval_status not between", value1, value2, "approval_status");
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