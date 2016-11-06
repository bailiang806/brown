package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.*;
import com.happydriving.rockets.server.mapper.CityMapper;
import com.happydriving.rockets.server.mapper.CountyMapper;
import com.happydriving.rockets.server.mapper.ProvinceMapper;
import com.happydriving.rockets.server.mapper.TownMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mazhiqiang
 */
@Service
public class RegionService {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CountyMapper countyMapper;

    @Autowired
    private TownMapper townMapper;

    public List<Province> getAllProvinces() {
        ProvinceExample example = new ProvinceExample();
        example.createCriteria().andIdEqualTo(13);
        return provinceMapper.selectByExample(example);
    }

    public List<City> getAllCitys() {
        return cityMapper.selectByExample(new CityExample());
    }

    public List<County> getAllCountrys() {
        return countyMapper.selectByExample(new CountyExample());
    }

    public List<Town> getAllTowns() {
        return townMapper.selectByExample(new TownExample());
    }

    public Province getProvinceById(int provinceId) {
        return provinceMapper.selectByPrimaryKey(provinceId);
    }

    public City getCityById(int cityId) {
        return cityMapper.selectByPrimaryKey(cityId);
    }

    public County getCountyById(int countyId) {
        return countyMapper.selectByPrimaryKey(countyId);
    }

    public Town getTownById(int townId) {
        return townMapper.selectByPrimaryKey(townId);
    }

    public List<City> getCity(int provinceId) {
        CityExample cityExample = new CityExample();
        CityExample.Criteria criteria = cityExample.createCriteria();
        criteria.andProvinceidEqualTo(provinceId);
        return cityMapper.selectByExample(cityExample);
    }

    public List<County> getCountry(int cityId) {
        CountyExample countyExample = new CountyExample();
        CountyExample.Criteria criteria = countyExample.createCriteria();
        criteria.andCityidEqualTo(cityId);
        return countyMapper.selectByExample(countyExample);
    }

    public List<Town> getTown(int countyId){
        TownExample townExample = new TownExample();
        TownExample.Criteria criteria = townExample.createCriteria();
        criteria.andCountyidEqualTo(countyId);
        return townMapper.selectByExample(townExample);
    }

    public Province getSingleProvinceByName(String provinceName) throws BusinessException {
        ProvinceExample provinceExample = new ProvinceExample();
        ProvinceExample.Criteria criteria = provinceExample.createCriteria();
        criteria.andNameEqualTo(provinceName);
        List<Province> provinces = provinceMapper.selectByExample(provinceExample);
        if (provinces.isEmpty()) {
            throw new BusinessException(String.format("Province Name: %s not exist!", provinceName));
        }
        return provinces.get(0);
    }

    public City getSingleCityByName(String cityName) throws BusinessException {
        CityExample cityExample = new CityExample();
        cityExample.createCriteria().andNameEqualTo(cityName);
        List<City> cities = cityMapper.selectByExample(cityExample);
        if (cities.isEmpty()) {
            throw new BusinessException(String.format("City Name: %s not exist!", cityName));
        }
        return cities.get(0);
    }

    public County getSingleCountryByName(String countyName) throws BusinessException {
        CountyExample countyExample = new CountyExample();
        countyExample.createCriteria().andNameEqualTo(countyName);
        List<County> countries = countyMapper.selectByExample(countyExample);
        if (countries.isEmpty()) {
            throw new BusinessException(String.format("Country Name: %s not exist!", countyName));
        }
        return countries.get(0);
    }

    public Town getSingleTownByName(String townName) throws BusinessException {
        TownExample townExample = new TownExample();
        townExample.createCriteria().andNameEqualTo(townName);
        List<Town> towns = townMapper.selectByExample(townExample);
        if (towns.isEmpty()) {
            throw new BusinessException(String.format("Tow Name: %s not exist!", townName));
        }
        return towns.get(0);
    }

}
