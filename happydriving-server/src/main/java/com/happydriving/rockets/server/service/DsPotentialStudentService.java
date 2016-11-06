package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.entity.DsPotentialStudent;
import com.happydriving.rockets.server.entity.DsPotentialStudentExample;
import com.happydriving.rockets.server.mapper.DsPotentialStudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoc10 on 2015/10/10 0010.
 */
@Service
public class DsPotentialStudentService {
    @Resource
    DsPotentialStudentMapper dsPotentialStudentMapper;

    public void insertDsPotentialStudent(DsPotentialStudent dsPotentialStudent){
        String id=String.valueOf(new Date().getTime());
        dsPotentialStudent.setId(id);
        dsPotentialStudentMapper.insert(dsPotentialStudent);
    }

    public DsPotentialStudent getDsPotentialStudentByPhone(String phone){
        DsPotentialStudentExample dsPotentialStudentExample=new DsPotentialStudentExample();
        dsPotentialStudentExample.createCriteria().andPhoneEqualTo(phone);
        List<DsPotentialStudent> dsPotentialStudents=dsPotentialStudentMapper.selectByExample(dsPotentialStudentExample);
        return dsPotentialStudents.isEmpty()?null:dsPotentialStudents.get(0);
    }
}
