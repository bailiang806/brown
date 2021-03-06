package com.happydriving.rockets.server.entity;

import java.util.ArrayList;
import java.util.List;

public class DrivingSchoolDetailExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    public DrivingSchoolDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driving_school_detail
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
     * This method corresponds to the database table driving_school_detail
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
     * This method corresponds to the database table driving_school_detail
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driving_school_detail
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
     * This class corresponds to the database table driving_school_detail
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNull() {
            addCriterion("school_id is null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIsNotNull() {
            addCriterion("school_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolIdEqualTo(Integer value) {
            addCriterion("school_id =", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotEqualTo(Integer value) {
            addCriterion("school_id <>", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThan(Integer value) {
            addCriterion("school_id >", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("school_id >=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThan(Integer value) {
            addCriterion("school_id <", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdLessThanOrEqualTo(Integer value) {
            addCriterion("school_id <=", value, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdIn(List<Integer> values) {
            addCriterion("school_id in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotIn(List<Integer> values) {
            addCriterion("school_id not in", values, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdBetween(Integer value1, Integer value2) {
            addCriterion("school_id between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolIdNotBetween(Integer value1, Integer value2) {
            addCriterion("school_id not between", value1, value2, "schoolId");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailIsNull() {
            addCriterion("school_detail is null");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailIsNotNull() {
            addCriterion("school_detail is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailEqualTo(String value) {
            addCriterion("school_detail =", value, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailNotEqualTo(String value) {
            addCriterion("school_detail <>", value, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailGreaterThan(String value) {
            addCriterion("school_detail >", value, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailGreaterThanOrEqualTo(String value) {
            addCriterion("school_detail >=", value, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailLessThan(String value) {
            addCriterion("school_detail <", value, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailLessThanOrEqualTo(String value) {
            addCriterion("school_detail <=", value, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailLike(String value) {
            addCriterion("school_detail like", value, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailNotLike(String value) {
            addCriterion("school_detail not like", value, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailIn(List<String> values) {
            addCriterion("school_detail in", values, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailNotIn(List<String> values) {
            addCriterion("school_detail not in", values, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailBetween(String value1, String value2) {
            addCriterion("school_detail between", value1, value2, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolDetailNotBetween(String value1, String value2) {
            addCriterion("school_detail not between", value1, value2, "schoolDetail");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlIsNull() {
            addCriterion("school_imgurl is null");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlIsNotNull() {
            addCriterion("school_imgurl is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlEqualTo(String value) {
            addCriterion("school_imgurl =", value, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlNotEqualTo(String value) {
            addCriterion("school_imgurl <>", value, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlGreaterThan(String value) {
            addCriterion("school_imgurl >", value, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlGreaterThanOrEqualTo(String value) {
            addCriterion("school_imgurl >=", value, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlLessThan(String value) {
            addCriterion("school_imgurl <", value, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlLessThanOrEqualTo(String value) {
            addCriterion("school_imgurl <=", value, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlLike(String value) {
            addCriterion("school_imgurl like", value, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlNotLike(String value) {
            addCriterion("school_imgurl not like", value, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlIn(List<String> values) {
            addCriterion("school_imgurl in", values, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlNotIn(List<String> values) {
            addCriterion("school_imgurl not in", values, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlBetween(String value1, String value2) {
            addCriterion("school_imgurl between", value1, value2, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolImgurlNotBetween(String value1, String value2) {
            addCriterion("school_imgurl not between", value1, value2, "schoolImgurl");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorIsNull() {
            addCriterion("school_avator is null");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorIsNotNull() {
            addCriterion("school_avator is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorEqualTo(String value) {
            addCriterion("school_avator =", value, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorNotEqualTo(String value) {
            addCriterion("school_avator <>", value, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorGreaterThan(String value) {
            addCriterion("school_avator >", value, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorGreaterThanOrEqualTo(String value) {
            addCriterion("school_avator >=", value, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorLessThan(String value) {
            addCriterion("school_avator <", value, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorLessThanOrEqualTo(String value) {
            addCriterion("school_avator <=", value, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorLike(String value) {
            addCriterion("school_avator like", value, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorNotLike(String value) {
            addCriterion("school_avator not like", value, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorIn(List<String> values) {
            addCriterion("school_avator in", values, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorNotIn(List<String> values) {
            addCriterion("school_avator not in", values, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorBetween(String value1, String value2) {
            addCriterion("school_avator between", value1, value2, "schoolAvator");
            return (Criteria) this;
        }

        public Criteria andSchoolAvatorNotBetween(String value1, String value2) {
            addCriterion("school_avator not between", value1, value2, "schoolAvator");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table driving_school_detail
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
     * This class corresponds to the database table driving_school_detail
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