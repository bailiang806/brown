package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.entity.*;
import com.happydriving.rockets.server.mapper.PaymentResultMapper;
import com.happydriving.rockets.server.mapper.StudentInfoMapper;
import com.happydriving.rockets.server.mapper.StudentScheduleMapper;
import com.happydriving.rockets.server.model.CoachScheduleDetailInfo;
import com.happydriving.rockets.server.model.StudentQueryInfo;
import com.happydriving.rockets.server.model.StudentScheduleDetailInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author mazhiqiang
 */
@Service
public class StudentService {

    public static final int PAY_SUCCESS = 1;

    @Resource
    private StudentInfoMapper studentInfoMapper;

    @Resource
    private StudentScheduleMapper studentScheduleMapper;

    @Resource
    private PaymentResultMapper paymentResultMapper;

    public void insertStudent(int userId){
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setUserId(userId);
        studentInfo.setCreatedAt(new Date());
        studentInfo.setUpdatedAt(new Date());
        studentInfoMapper.insert(studentInfo);
    }

//    public StudentInfo getStudentInfoByPhone(String phone) {
//        StudentInfoExample example = new StudentInfoExample();
//        example.createCriteria().andPhoneEqualTo(phone);
//        List<StudentInfo> studentInfos = studentInfoMapper.selectByExample(example);
//        return studentInfos.isEmpty() ? null : studentInfos.get(0);
//    }
//
//    public void registerStudent(StudentInfo studentInfo) throws BusinessException {
//        if (getStudentInfoByPhone(studentInfo.getPhone()) != null) {
//            throw new BusinessException(String.format("该手机号: %s 已经被注册!", studentInfo.getPhone()));
//        }
//        studentInfoMapper.insertSelective(studentInfo);
//    }

    public void updateStudent(StudentInfo studentInfo) {
        studentInfoMapper.updateByPrimaryKey(studentInfo);
    }

    public void deleteStudent(StudentInfo studentInfo) {
        studentInfoMapper.deleteByPrimaryKey(studentInfo.getId());
    }

    public StudentInfo getStudentInfoByUserId(int userId) {
        StudentInfoExample studentInfoExample = new StudentInfoExample();
        studentInfoExample.createCriteria().andUserIdEqualTo(userId);
        List<StudentInfo> studentInfos = studentInfoMapper.selectByExample(studentInfoExample);
        return studentInfos.isEmpty() ? null : studentInfos.get(0);
    }

    public List<Map> getCoachByStudentId(int studentId) {
        return studentInfoMapper.getCoachByStudentId(studentId);
    }

    public List<StudentScheduleDetailInfo> getStudentScheduleDetailById(int studentId) {
        return studentScheduleMapper.getStudentScheduleDetailById(studentId);
    }

    public void addStudentSchedule(StudentSchedule studentSchedule) {
        studentScheduleMapper.insertAndGetId(studentSchedule);
    }

    public List<CoachScheduleDetailInfo> getCoachScheduleByStudentId(int studentId) {
        return studentInfoMapper.getCoachScheduleByStudentId(studentId);
    }

    public boolean isStudentSelectCoachAndPayed(int studentId) {
        StudentInfo studentInfo = getStudentInfoByUserId(studentId);
        Integer productId = studentInfo.getProductId();
        if (productId == null || productId == 0) {
            return false;
        }
        PaymentResultExample example = new PaymentResultExample();
        PaymentResultExample.Criteria criteria = example.createCriteria();
        criteria.andXueyuanIdEqualTo(studentId);
        criteria.andCoachProductIdEqualTo(productId);
        criteria.andTradeStateEqualTo(PAY_SUCCESS);
        List<PaymentResult> paymentResultByStudentId =
                paymentResultMapper.selectByExample(example);
        return !paymentResultByStudentId.isEmpty();
    }

    public List<StudentQueryInfo> getStudentInfoListByCoachId(int coachId) {
        return studentInfoMapper.getStudentInfoListByCoachId(coachId);
    }
}
