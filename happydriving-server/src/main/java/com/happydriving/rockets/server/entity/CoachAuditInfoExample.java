package com.happydriving.rockets.server.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoachAuditInfoExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public CoachAuditInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
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

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andAuditAdminIsNull() {
            addCriterion("audit_admin is null");
            return (Criteria) this;
        }

        public Criteria andAuditAdminIsNotNull() {
            addCriterion("audit_admin is not null");
            return (Criteria) this;
        }

        public Criteria andAuditAdminEqualTo(String value) {
            addCriterion("audit_admin =", value, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminNotEqualTo(String value) {
            addCriterion("audit_admin <>", value, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminGreaterThan(String value) {
            addCriterion("audit_admin >", value, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminGreaterThanOrEqualTo(String value) {
            addCriterion("audit_admin >=", value, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminLessThan(String value) {
            addCriterion("audit_admin <", value, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminLessThanOrEqualTo(String value) {
            addCriterion("audit_admin <=", value, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminLike(String value) {
            addCriterion("audit_admin like", value, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminNotLike(String value) {
            addCriterion("audit_admin not like", value, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminIn(List<String> values) {
            addCriterion("audit_admin in", values, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminNotIn(List<String> values) {
            addCriterion("audit_admin not in", values, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminBetween(String value1, String value2) {
            addCriterion("audit_admin between", value1, value2, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditAdminNotBetween(String value1, String value2) {
            addCriterion("audit_admin not between", value1, value2, "auditAdmin");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNull() {
            addCriterion("audit_status is null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIsNotNull() {
            addCriterion("audit_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStatusEqualTo(String value) {
            addCriterion("audit_status =", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotEqualTo(String value) {
            addCriterion("audit_status <>", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThan(String value) {
            addCriterion("audit_status >", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusGreaterThanOrEqualTo(String value) {
            addCriterion("audit_status >=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThan(String value) {
            addCriterion("audit_status <", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLessThanOrEqualTo(String value) {
            addCriterion("audit_status <=", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusLike(String value) {
            addCriterion("audit_status like", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotLike(String value) {
            addCriterion("audit_status not like", value, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusIn(List<String> values) {
            addCriterion("audit_status in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotIn(List<String> values) {
            addCriterion("audit_status not in", values, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusBetween(String value1, String value2) {
            addCriterion("audit_status between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditStatusNotBetween(String value1, String value2) {
            addCriterion("audit_status not between", value1, value2, "auditStatus");
            return (Criteria) this;
        }

        public Criteria andAuditMessageIsNull() {
            addCriterion("audit_message is null");
            return (Criteria) this;
        }

        public Criteria andAuditMessageIsNotNull() {
            addCriterion("audit_message is not null");
            return (Criteria) this;
        }

        public Criteria andAuditMessageEqualTo(String value) {
            addCriterion("audit_message =", value, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageNotEqualTo(String value) {
            addCriterion("audit_message <>", value, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageGreaterThan(String value) {
            addCriterion("audit_message >", value, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageGreaterThanOrEqualTo(String value) {
            addCriterion("audit_message >=", value, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageLessThan(String value) {
            addCriterion("audit_message <", value, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageLessThanOrEqualTo(String value) {
            addCriterion("audit_message <=", value, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageLike(String value) {
            addCriterion("audit_message like", value, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageNotLike(String value) {
            addCriterion("audit_message not like", value, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageIn(List<String> values) {
            addCriterion("audit_message in", values, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageNotIn(List<String> values) {
            addCriterion("audit_message not in", values, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageBetween(String value1, String value2) {
            addCriterion("audit_message between", value1, value2, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditMessageNotBetween(String value1, String value2) {
            addCriterion("audit_message not between", value1, value2, "auditMessage");
            return (Criteria) this;
        }

        public Criteria andAuditAtIsNull() {
            addCriterion("audit_at is null");
            return (Criteria) this;
        }

        public Criteria andAuditAtIsNotNull() {
            addCriterion("audit_at is not null");
            return (Criteria) this;
        }

        public Criteria andAuditAtEqualTo(Date value) {
            addCriterion("audit_at =", value, "auditAt");
            return (Criteria) this;
        }

        public Criteria andAuditAtNotEqualTo(Date value) {
            addCriterion("audit_at <>", value, "auditAt");
            return (Criteria) this;
        }

        public Criteria andAuditAtGreaterThan(Date value) {
            addCriterion("audit_at >", value, "auditAt");
            return (Criteria) this;
        }

        public Criteria andAuditAtGreaterThanOrEqualTo(Date value) {
            addCriterion("audit_at >=", value, "auditAt");
            return (Criteria) this;
        }

        public Criteria andAuditAtLessThan(Date value) {
            addCriterion("audit_at <", value, "auditAt");
            return (Criteria) this;
        }

        public Criteria andAuditAtLessThanOrEqualTo(Date value) {
            addCriterion("audit_at <=", value, "auditAt");
            return (Criteria) this;
        }

        public Criteria andAuditAtIn(List<Date> values) {
            addCriterion("audit_at in", values, "auditAt");
            return (Criteria) this;
        }

        public Criteria andAuditAtNotIn(List<Date> values) {
            addCriterion("audit_at not in", values, "auditAt");
            return (Criteria) this;
        }

        public Criteria andAuditAtBetween(Date value1, Date value2) {
            addCriterion("audit_at between", value1, value2, "auditAt");
            return (Criteria) this;
        }

        public Criteria andAuditAtNotBetween(Date value1, Date value2) {
            addCriterion("audit_at not between", value1, value2, "auditAt");
            return (Criteria) this;
        }

        public Criteria andMarkIsNull() {
            addCriterion("mark is null");
            return (Criteria) this;
        }

        public Criteria andMarkIsNotNull() {
            addCriterion("mark is not null");
            return (Criteria) this;
        }

        public Criteria andMarkEqualTo(Boolean value) {
            addCriterion("mark =", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotEqualTo(Boolean value) {
            addCriterion("mark <>", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkGreaterThan(Boolean value) {
            addCriterion("mark >", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkGreaterThanOrEqualTo(Boolean value) {
            addCriterion("mark >=", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkLessThan(Boolean value) {
            addCriterion("mark <", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkLessThanOrEqualTo(Boolean value) {
            addCriterion("mark <=", value, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkIn(List<Boolean> values) {
            addCriterion("mark in", values, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotIn(List<Boolean> values) {
            addCriterion("mark not in", values, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkBetween(Boolean value1, Boolean value2) {
            addCriterion("mark between", value1, value2, "mark");
            return (Criteria) this;
        }

        public Criteria andMarkNotBetween(Boolean value1, Boolean value2) {
            addCriterion("mark not between", value1, value2, "mark");
            return (Criteria) this;
        }

        public Criteria andLabelIsNull() {
            addCriterion("label is null");
            return (Criteria) this;
        }

        public Criteria andLabelIsNotNull() {
            addCriterion("label is not null");
            return (Criteria) this;
        }

        public Criteria andLabelEqualTo(String value) {
            addCriterion("label =", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotEqualTo(String value) {
            addCriterion("label <>", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThan(String value) {
            addCriterion("label >", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelGreaterThanOrEqualTo(String value) {
            addCriterion("label >=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThan(String value) {
            addCriterion("label <", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLessThanOrEqualTo(String value) {
            addCriterion("label <=", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelLike(String value) {
            addCriterion("label like", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotLike(String value) {
            addCriterion("label not like", value, "label");
            return (Criteria) this;
        }

        public Criteria andLabelIn(List<String> values) {
            addCriterion("label in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotIn(List<String> values) {
            addCriterion("label not in", values, "label");
            return (Criteria) this;
        }

        public Criteria andLabelBetween(String value1, String value2) {
            addCriterion("label between", value1, value2, "label");
            return (Criteria) this;
        }

        public Criteria andLabelNotBetween(String value1, String value2) {
            addCriterion("label not between", value1, value2, "label");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table coach_auditinfo
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table coach_auditinfo
     *
     * @mbggenerated
     */
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