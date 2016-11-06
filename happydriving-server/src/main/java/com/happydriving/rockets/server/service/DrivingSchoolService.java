package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.DrivingSchool;
import com.happydriving.rockets.server.entity.DrivingSchoolExample;
import com.happydriving.rockets.server.entity.ServiceCity;
import com.happydriving.rockets.server.mapper.DrivingSchoolMapper;
import com.happydriving.rockets.server.model.DrivingSchoolBasicInfo;
import com.happydriving.rockets.server.model.DrivingSchoolDetailInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
//import java.util.stream.Collectors;

/**
 * @author mazhiqiang
 */
@Service
public class DrivingSchoolService {

    @Value("#{drivingConfigProperties.jiaxiaoImageUrlPrefix}")
    private String jiaxiaoImageUrlPrefix;//给定图片地址

    @Resource
    private DrivingSchoolMapper drivingSchoolMapper;

   /* @Resource
    private DrivingSchoolDetailMapper drivingSchoolDetailMapper;*/

    @Autowired
    private ServiceCityService serviceCityService;

    @Autowired
    private BaiduMapService baiduMapService;

    public DrivingSchool addDrivingSchool(String cityName, String schoolName, String address) throws BusinessException {
        ServiceCity serviceCity = serviceCityService.getCityByName(cityName);
        DrivingSchool drivingSchool = new DrivingSchool();
        drivingSchool.setSchoolName(schoolName);
        drivingSchool.setAddress(address);
        drivingSchool.setServiceCityId(serviceCity.getId());

        BaiduMapService.BaiduMapLocation location = baiduMapService.getLocation(address, cityName);
        drivingSchool.setLatitude(BigDecimal.valueOf(location.getLag()));
        drivingSchool.setLongitude(BigDecimal.valueOf(location.getLng()));
        drivingSchool.setCanSelect("N");
        drivingSchool.setIsHide("N");
        drivingSchoolMapper.insertAndGetId(drivingSchool);
        return drivingSchool;
    }

    public void updateDrivingSchool(DrivingSchool drivingSchool) throws BusinessException {
        ServiceCity serviceCity = serviceCityService.getCityById(drivingSchool.getServiceCityId());
        BaiduMapService.BaiduMapLocation location =
                baiduMapService.getLocation(drivingSchool.getAddress(), serviceCity.getName());
        drivingSchool.setLatitude(BigDecimal.valueOf(location.getLag()));
        drivingSchool.setLongitude(BigDecimal.valueOf(location.getLng()));
        drivingSchoolMapper.updateByPrimaryKey(drivingSchool);
    }

    public DrivingSchool getSingleDrivingSchoolDetailBySchoolId(int schoolId) {
        DrivingSchool drivingSchool = drivingSchoolMapper.selectByPrimaryKey(schoolId);
        return drivingSchool == null ? null : drivingSchool;
    }

    public void insertUpdateDrivingSchoolDetail(DrivingSchool drivingSchoolDetail) {
        if (drivingSchoolDetail.getId() == null) {
            drivingSchoolMapper.insert(drivingSchoolDetail);
        } else {
            drivingSchoolMapper.updateByPrimaryKey(drivingSchoolDetail);
        }
    }

    public List<DrivingSchool> getDrivingSchoolsByCityName(String cityName) throws BusinessException {
        ServiceCity serviceCity = serviceCityService.getCityByName(cityName);
        if (serviceCity == null) {
            throw new BusinessException(String.format("选择的城市: %s 不存在!", cityName));
        }
        return getDrivingSchoolsByCityId(serviceCity.getId());
    }

    public List<DrivingSchool> getDrivingSchoolsByCityId(int cityId) {
        DrivingSchoolExample example = new DrivingSchoolExample();
        example.createCriteria().andServiceCityIdEqualTo(cityId).andIsHideNotEqualTo("Y");
        return drivingSchoolMapper.selectByExample(example);
    }

    public List<DrivingSchoolDetailInfo> getDrivingSchoolDetailByCityId(int cityId) {
        List<DrivingSchoolDetailInfo> drivingSchoolDetails =
                drivingSchoolMapper.getDrivingSchoolDetailByCityId(cityId);
        for (DrivingSchoolDetailInfo drivingSchoolDetail : drivingSchoolDetails) {
           transferDrivingSchool(drivingSchoolDetail);
        }
        return drivingSchoolDetails;
    }

    public DrivingSchoolDetailInfo getDrivingSchoolDetailBySchoolId(int schoolId) {
        DrivingSchoolDetailInfo drivingSchoolDetail =
                drivingSchoolMapper.getDrivingSchoolDetailBySchoolId(schoolId);
        if (drivingSchoolDetail != null) {
            transferDrivingSchool(drivingSchoolDetail);
        }
        return drivingSchoolDetail;
    }

    private void  transferDrivingSchool(DrivingSchoolDetailInfo drivingSchoolDetail){
        String schoolImgUrl =
                drivingSchoolDetail.getSchoolImgUrl() == null ? "" : drivingSchoolDetail.getSchoolImgUrl();
        String[] imgurls = schoolImgUrl.split(";");
        List<String> schoolImgUrls = new ArrayList<>();
        for (String imgurl : imgurls) {
            if (!StringUtils.isBlank(imgurl)) {
                schoolImgUrls.add(jiaxiaoImageUrlPrefix+ drivingSchoolDetail.getCityId()+"/"+drivingSchoolDetail.getSchoolId()+"/thumb_495/" + imgurl);//
            }
        }
        drivingSchoolDetail.setSchoolImgUrls(schoolImgUrls);
        drivingSchoolDetail.setSchoolImgUrl(schoolImgUrls.get(0));
        //获取对应的hd文件地址
        String schoolHdurl = drivingSchoolDetail.getSchoolHd()==null?"":drivingSchoolDetail.getSchoolHd();
        String[] hdurls = schoolHdurl.split(";");
        List<String> schoolHdurls = new ArrayList<>();
        for (String hdurl : hdurls) {
            if (!StringUtils.isBlank(hdurl)) {
                schoolHdurls.add(jiaxiaoImageUrlPrefix +drivingSchoolDetail.getCityId()+"/"+drivingSchoolDetail.getSchoolId()+"/hd/ "+hdurl);
            }
        }
        drivingSchoolDetail.setSchoolHdurls(schoolHdurls);
        // drivingSchoolDetail.setSchoolHd(schoolHdurls.size()>0?null:schoolHdurls.get(0));
        drivingSchoolDetail.setSchoolIndex(jiaxiaoImageUrlPrefix + drivingSchoolDetail.getCityId() + "/" + drivingSchoolDetail.getSchoolId() + "/thumb_320/" + drivingSchoolDetail.getSchoolIndex());
        drivingSchoolDetail.setSchoolAvator(jiaxiaoImageUrlPrefix+drivingSchoolDetail.getCityId()+"/"+drivingSchoolDetail.getSchoolId()+"/avator/" + drivingSchoolDetail.getSchoolAvator());
    }

    public DrivingSchool getDrivingSchoolByName(int serviceCityId, String schoolName) {
        DrivingSchoolExample example = new DrivingSchoolExample();
        example.createCriteria().andSchoolNameEqualTo(schoolName).andServiceCityIdEqualTo(serviceCityId);
        List<DrivingSchool> drivingSchools = drivingSchoolMapper.selectByExample(example);
        return drivingSchools.isEmpty() ? null : drivingSchools.get(0);
    }

    public List<DrivingSchoolBasicInfo> getNearestSchool(int cityId) {
        ServiceCity city = serviceCityService.getCityById(cityId);
        final double cityLat = city.getLatitude().doubleValue();
        final double cityLng = city.getLongitude().doubleValue();
        return getNearestSchool(cityId, cityLat, cityLng);
    }

    public List<DrivingSchoolBasicInfo> getNearestSchool(int cityId, double latitude, double longitude) {
        final double userLat = latitude;
        final double userLng = longitude;
        List<DrivingSchoolDetailInfo> schools = drivingSchoolMapper.getDrivingSchoolDetailByCityId(cityId);
        List<DrivingSchoolDetailInfo> results = new ArrayList<>(3);
        for (DrivingSchoolDetailInfo school : schools) {
            double schoolLat = school.getSchoolLatitude().doubleValue();
            double schoolLng = school.getSchoolLongtitude().doubleValue();
            double schoolDistance = getDistance(schoolLat, schoolLng, userLat, userLng);
            if (results.size() < 3) {
                results.add(school);
            } else {
                DrivingSchoolDetailInfo maxDrivingSchool = null;
                double maxDistance = 0;
                for (DrivingSchoolDetailInfo schoolInResult : results) {
                    double schoolInResultDistance = getDistance(schoolInResult.getSchoolLatitude().doubleValue(),
                            schoolInResult.getSchoolLongtitude().doubleValue(), userLat, userLng);
                    if (schoolInResultDistance > maxDistance) {
                        maxDrivingSchool = schoolInResult;
                        maxDistance = schoolInResultDistance;
                    }
                }
                if (schoolDistance < maxDistance) {
                    results.remove(maxDrivingSchool);
                    results.add(school);
                }
            }
        }
        for(DrivingSchoolDetailInfo drivingSchoolDetailInfo:results){
            transferDrivingSchool(drivingSchoolDetailInfo);
        }
        Collections.sort(results, new Comparator<DrivingSchoolDetailInfo>() {
            @Override
            public int compare(DrivingSchoolDetailInfo o1, DrivingSchoolDetailInfo o2) {
                double distance1 =
                        getDistance(userLat, userLng, o1.getSchoolLatitude().doubleValue(),
                                o1.getSchoolLongtitude().doubleValue());
                double distance2 =
                        getDistance(userLat, userLng, o2.getSchoolLatitude().doubleValue(),
                                o2.getSchoolLongtitude().doubleValue());
                if (distance1 < distance2) {
                    return 1;
                } else if (distance1 == distance2) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
//        for (DrivingSchool school : results) {
//        }
        List<DrivingSchoolBasicInfo> basicInfos=new ArrayList<>(3);
        for(DrivingSchoolDetailInfo school:results){
            DrivingSchoolBasicInfo basicInfo= new DrivingSchoolBasicInfo();
            basicInfo.setSchoolId(school.getSchoolId());
            basicInfo.setSchoolName(school.getSchoolName());
            basicInfo.setSchoolAddress(school.getSchoolAddress());
            basicInfo.setImgURL(school.getSchoolIndex());
            basicInfos.add(basicInfo);
        }
        return basicInfos;
    }

    public List<DrivingSchoolBasicInfo> getMajorSchool(int cityId) {
        List<DrivingSchoolDetailInfo> schools = drivingSchoolMapper.getDrivingSchoolDetailByCityId(cityId);
        int number ;
        if(schools.size()<12) {
            number = schools.size() / 3 * 3;
        }
        else {
            number = 12;
        }
        List<DrivingSchoolDetailInfo> results = schools.subList(0,number);
        for(DrivingSchoolDetailInfo drivingSchoolDetailInfo:results){
            transferDrivingSchool(drivingSchoolDetailInfo);
        }
        List<DrivingSchoolBasicInfo> basicInfos=new ArrayList<>();
        for(DrivingSchoolDetailInfo school:results){
            DrivingSchoolBasicInfo basicInfo= new DrivingSchoolBasicInfo();
            basicInfo.setSchoolId(school.getSchoolId());
            basicInfo.setSchoolName(school.getSchoolName());
            basicInfo.setSchoolAddress(school.getSchoolAddress());
            basicInfo.setImgURL(school.getSchoolIndex());
            basicInfos.add(basicInfo);
        }
        return basicInfos;
    }

    private final double EARTH_RADIUS = 6378.137;//地球半径

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    private double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) +
                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

}
