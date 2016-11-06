package com.happydriving.rockets.campaign.spread.service;

import com.happydriving.rockets.campaign.spread.entity.SpreadStudent;
import com.happydriving.rockets.campaign.spread.entity.SpreadStudentExample;
import com.happydriving.rockets.campaign.spread.mapper.SpreadStudentMapper;
import com.happydriving.rockets.campaign.spread.model.SpreadStudentDetailInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Service
public class SpreadStudentService {

    @Resource
    private SpreadStudentMapper spreadStudentMapper;

    public SpreadStudent getSpreadStudentByPhone(String phone) {
        SpreadStudentExample example = new SpreadStudentExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<SpreadStudent> spreadStudents = spreadStudentMapper.selectByExample(example);
        return spreadStudents.isEmpty() ? null : spreadStudents.get(0);
    }

    public SpreadStudent getSpreadStudentById(int studentId) {
        return spreadStudentMapper.selectByPrimaryKey(studentId);
    }

    public void insertSpreadStudent(SpreadStudent spreadStudent){
        spreadStudent.setCreatedAt(new Date());
        spreadStudentMapper.insert(spreadStudent);
    }

    public List<SpreadStudent> getAllSpreadStudents() {
        return spreadStudentMapper.selectByExample(new SpreadStudentExample());
    }

    public void updateSpreadStudent(SpreadStudent spreadStudent) {
        spreadStudentMapper.updateByPrimaryKey(spreadStudent);
    }

    public List<SpreadStudent> getSpreadStudentByReferrerId(int referredId) {
        SpreadStudentExample example = new SpreadStudentExample();
        example.createCriteria().andReferrerIdEqualTo(referredId);
        return spreadStudentMapper.selectByExample(example);
    }

    public List<SpreadStudentDetailInfo> getAllSpreadStudentsByCityId(int cityId) {
        return spreadStudentMapper.getAllSpreadStudentsByCityId(cityId);
    }

    public List<SpreadStudentDetailInfo> getSpreadStudentDetailsByCondition(int cityId, Date startDate, Date endDate) {
        return spreadStudentMapper.getSpreadStudentDetailsByCondition(cityId, startDate, endDate);
    }

    public int insertAndGetId(SpreadStudent spreadStudent) {
        spreadStudent.setCreatedAt(new Date());
        return spreadStudentMapper.insertAndGetId(spreadStudent);
    }

    public SpreadStudentDetailInfo getSpreadStudentDetailsById(int studentId) {
        return spreadStudentMapper.getSpreadStudentDetailsById(studentId);
    }


}
