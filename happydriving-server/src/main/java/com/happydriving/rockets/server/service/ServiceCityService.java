package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.entity.ServiceCity;
import com.happydriving.rockets.server.entity.ServiceCityExample;
import com.happydriving.rockets.server.mapper.ServiceCityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 当前ejiapei提供服务的对应城市
 *
 * @author mazhiqiang
 */
@Service
public class ServiceCityService {

    @Resource
    private ServiceCityMapper serviceCityMapper;

    public List<ServiceCity> getAllServiceCities() {
        return serviceCityMapper.selectByExample(new ServiceCityExample());
    }

    public ServiceCity getCityById(int cityId) {
        return serviceCityMapper.selectByPrimaryKey(cityId);
    }

    public ServiceCity getCityByName(String name) {
        ServiceCityExample example = new ServiceCityExample();
        example.createCriteria().andNameEqualTo(name);
        List<ServiceCity> serviceCities = serviceCityMapper.selectByExample(example);
        return serviceCities.isEmpty() ? null : serviceCities.get(0);
    }

}
