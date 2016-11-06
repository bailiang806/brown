package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.ClassType;
import com.happydriving.rockets.server.entity.ClassTypeExample;
import com.happydriving.rockets.server.mapper.ClassTypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Service
public class ClassTypeService {

    @Resource
    private ClassTypeMapper classTypeMapper;

    public List<ClassType> getAllClassTypes() {
        return classTypeMapper.selectByExample(new ClassTypeExample());
    }

    public void insertClassType(String classTypeName) throws BusinessException {
        ClassType classTypeByName = getSingleClassTypeByName(classTypeName);
        if (classTypeByName != null) {
            throw new BusinessException(String.format("Class type name: %s NOT exist!", classTypeName));
        }
        ClassType classType = new ClassType();
        classType.setClassType(classTypeName);
        classType.setCreatedAt(new Date());
        classType.setUpdatedAt(new Date());
        classTypeMapper.insert
                (classType);
    }

    public ClassType getClassTypeByName(String classTypeName) throws BusinessException {
        ClassType result = getSingleClassTypeByName(classTypeName);
        if (result == null) {
            throw new BusinessException(String.format("Class type name: %s NOT exist!", classTypeName));
        }
        return result;
    }

    private ClassType getSingleClassTypeByName(String classTypeName) throws BusinessException {
        ClassTypeExample example = new ClassTypeExample();
        ClassTypeExample.Criteria criteria = example.createCriteria();
        criteria.andClassTypeEqualTo(classTypeName);
        List<ClassType> classTypes = classTypeMapper.selectByExample(example);
        if (classTypes.size() > 1) {
            throw new BusinessException(String.format("Car type name : %s more than 1!", classTypeName));
        }
        return classTypes.size() == 1 ? classTypes.get(0) : null;
    }

}
