package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.entity.PotentialStudent;
import com.happydriving.rockets.server.entity.PotentialStudentExample;
import com.happydriving.rockets.server.mapper.PotentialStudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 由于均需要使用推荐人的方式进行推荐，所以该服务已停用
 * @author mazhiqiang
 */
@Service
@Deprecated
public class PotentialStudentService {

    @Resource
    private PotentialStudentMapper potentialStudentMapper;

    public void insertPotentialStudent(PotentialStudent potentialStudent) {
        potentialStudent.setTimestamp(new Date());
        potentialStudentMapper.insert(potentialStudent);
    }

    public List<PotentialStudent> getPotentialStudents() {
        return potentialStudentMapper.selectByExample(new PotentialStudentExample());
    }

    public List<PotentialStudent> getPotentialStudentByCondition(int cityId, Date startDate, Date endDate) {
        PotentialStudentExample example = new PotentialStudentExample();
        example.createCriteria().andCityEqualTo(String.valueOf(cityId))
            .andTimestampBetween(startDate, endDate);
        return potentialStudentMapper.selectByExample(example);
    }
}
