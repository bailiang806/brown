package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.entity.ServiceStore;
import com.happydriving.rockets.server.entity.ServiceStoreExample;
import com.happydriving.rockets.server.mapper.ServiceStoreMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bailiang on 2015/11/10.
 */
@Service
public class ServiceStoreService {
    @Resource
    private ServiceStoreMapper serviceStoreMapper;
    //根据城市id进行服务门店查询
    public List<ServiceStore> getServiceStoreByCityId(int cityId) {
        ServiceStoreExample serviceStoreExample = new ServiceStoreExample();
        serviceStoreExample.createCriteria().andServiceCityIdEqualTo(cityId);
        return serviceStoreMapper.selectByExample(serviceStoreExample);
    }
    //根据id进行门店查询
    public ServiceStore getServiceStoreById(int id){
        return serviceStoreMapper.selectByPrimaryKey(id);
    }
    //根据id进行门店删除
    public void deleteServiceStoreById(int id){
        serviceStoreMapper.deleteByPrimaryKey(id);
    }
    //修改
    public void updateServiceStore(ServiceStore serviceStore){
        ServiceStoreExample serviceStoreExample = new ServiceStoreExample();
        serviceStoreExample.createCriteria().andServiceCityIdEqualTo(serviceStore.getServiceCityId());
        serviceStoreMapper.updateByExample(serviceStore,serviceStoreExample);
    }
    //插入门店信息
    public void insertServiceStore(ServiceStore serviceStore){
        serviceStoreMapper.insert(serviceStore);
    }

}
