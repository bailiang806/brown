package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.CarType;
import com.happydriving.rockets.server.entity.CarTypeExample;
import com.happydriving.rockets.server.mapper.CarTypeMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Service
public class CarTypeService {

    private static final Log LOG = LogFactory.getLog(CarTypeService.class);

    @Resource
    private CarTypeMapper carTypeMapper;

    public List<CarType> getAllCarTypes() {
        return carTypeMapper.selectByExample(new CarTypeExample());
    }

    public void insertCarType(String carTypeName) throws BusinessException {
        CarType carTypeByName = getSingleCarTypeByName(carTypeName);
        if (carTypeByName != null) {
            throw new BusinessException(String.format("car type : %s already exist!", carTypeName));
        }
        CarType carType = new CarType();
        carType.setCarType(carTypeName);
        carType.setCreatedAt(new Date());
        carType.setUpdatedAt(new Date());
        carTypeMapper.insert(carType);
    }

    public CarType getCarTypeByName(String carTypeName) throws BusinessException {
        CarType result = getSingleCarTypeByName(carTypeName);
        if (result == null) {
            throw new BusinessException(String.format("car type : %s already exist!", carTypeName));
        }
        return result;
    }

    private CarType getSingleCarTypeByName(String carTypeName) throws BusinessException {
        CarTypeExample example = new CarTypeExample();
        CarTypeExample.Criteria criteria = example.createCriteria();
        criteria.andCarTypeEqualTo(carTypeName);
        List<CarType> carTypes = carTypeMapper.selectByExample(example);
        if (carTypes.size() > 1) {
            throw new BusinessException(String.format("Car type name : %s more than 1!", carTypeName));
        }
        return carTypes.size() == 1 ? carTypes.get(0) : null;
    }
}
