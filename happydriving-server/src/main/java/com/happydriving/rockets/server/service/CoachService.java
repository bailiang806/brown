package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.PaginationDaoUtils;
import com.happydriving.rockets.server.entity.*;
import com.happydriving.rockets.server.mapper.*;
import com.happydriving.rockets.server.model.*;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Service
public class CoachService {

    @Value("#{drivingConfigProperties.coachImageUrlPrefix}")
    private String coachImageUrlPrefix;

    @Resource
    private CoachMapper coachMapper;

    @Resource
    private CoachCertificatePhotoMapper coachCertificatePhotoMapper;

    @Resource
    private CoachProductMapper coachProductMapper;

    @Resource
    private CoachScheduleMapper coachScheduleMapper;

    @Resource
    private CoachUploadPhotoMapper coachUploadPhotoMapper;

    /**
     * @param userId
     */
    public void insertCoach(int userId) {
        Coach coach = new Coach();
        coach.setUserId(userId);
        coach.setCreatedAt(new Date());
        coach.setUpdatedAt(new Date());
        coachMapper.insert(coach);
    }

    public Coach getCoachByCoachId(int CoachId) {
        CoachExample coachExample = new CoachExample();
        coachExample.createCriteria().andIdEqualTo(CoachId);
        List<Coach> coaches = coachMapper.selectByExample(coachExample);
        return coaches.isEmpty() ? null : coaches.get(0);
    }
//    public void insertUpdateCoachProduct(Coach coach,
//                                         CoachProduct coachProduct) throws BusinessException {
//        coachMapper.updateByPrimaryKey(coach);
//        insertUpdateCoachProduct(coachProduct);
//    }

//    public void insertUpdateCoachProducts(Coach coach,
//                                          List<CoachProduct> coachProducts)
//            throws BusinessException {
//        coachMapper.updateByPrimaryKey(coach);
//        insertUpdateCoachProducts(coachProducts);
//    }

    public void insertCoach(Coach coach) throws BusinessException {
//        coach.setUpdatedAt(new Date());
//        coachMapper.updateByPrimaryKey(coach);
        coachMapper.insertSelective(coach);
    }

//    public void insertUpdateCoachProduct(CoachProduct coachProduct) throws BusinessException {
//        CoachProductExample example = new CoachProductExample();
//        CoachProductExample.Criteria criteria = example.createCriteria();
//        criteria.andUserIdEqualTo(coachProduct.getUserId());
//        List<CoachProduct> coachProducts = coachProductMapper.selectByExample(example);
//        if (coachProducts.size() == 1) {
//            coachProduct.setId(coachProducts.get(0).getId());
//            coachProduct.setUpdatedAt(new Date());
//            coachProductMapper.updateByPrimaryKey(coachProduct);
//        } else {
//            coachProduct.setCreatedAt(new Date());
//            coachProduct.setUpdatedAt(new Date());
//            coachProductMapper.insert(coachProduct);
//        }
//    }

//    public void insertUpdateCoachProducts(List<CoachProduct> coachProducts) throws BusinessException {
//        for (CoachProduct coachProduct : coachProducts) {
//            if (coachProduct.getId() == 0) {
//                coachProduct.setCreatedAt(new Date());
//                coachProduct.setUpdatedAt(new Date());
//                coachProductMapper.insert(coachProduct);
//            } else {
//                coachProductMapper.updateByPrimaryKey(coachProduct);
//            }
//        }
//    }

    public CoachCertificatePhoto getCoachCertificatePhotoByCoachId(int coachId) {
        CoachCertificatePhotoExample example = new CoachCertificatePhotoExample();
        CoachCertificatePhotoExample.Criteria criteria = example.createCriteria();
        criteria.andCoachIdEqualTo(coachId);
        List<CoachCertificatePhoto> coachCertificatePhotos = coachCertificatePhotoMapper.selectByExample(example);
        return coachCertificatePhotos.isEmpty() ? null : coachCertificatePhotos.get(0);
    }

    public void insertUpdateCoachIdCardPhoto(Integer coachId, String positiveImgUrl, String negativeImgUrl)
            throws BusinessException {
        CoachCertificatePhoto coachCertificatePhoto = getCoachCertificatePhotoByCoachId(coachId);
        if (coachCertificatePhoto == null) {
            coachCertificatePhoto = new CoachCertificatePhoto();
            coachCertificatePhoto.setIdCardPositivePhoto(positiveImgUrl);
            coachCertificatePhoto.setIdCardNegativePhoto(negativeImgUrl);
            coachCertificatePhoto.setCoachId(coachId);
            coachCertificatePhoto.setCreatedAt(new Date());
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.insert(coachCertificatePhoto);
        } else {
            coachCertificatePhoto.setIdCardPositivePhoto(positiveImgUrl);
            coachCertificatePhoto.setIdCardNegativePhoto(negativeImgUrl);
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.updateByPrimaryKey(coachCertificatePhoto);
        }
    }

    public void insertUpdateCoachPersonalPhoto(Integer coachId, String personalPhotoUrl)
            throws BusinessException {
        CoachCertificatePhoto coachCertificatePhoto = getCoachCertificatePhotoByCoachId(coachId);
        if (coachCertificatePhoto == null) {
            coachCertificatePhoto = new CoachCertificatePhoto();
            coachCertificatePhoto.setPersonalPhoto(personalPhotoUrl);
            coachCertificatePhoto.setCoachId(coachId);
            coachCertificatePhoto.setCreatedAt(new Date());
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.insert(coachCertificatePhoto);
        } else {
            coachCertificatePhoto.setPersonalPhoto(personalPhotoUrl);
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.updateByPrimaryKey(coachCertificatePhoto);
        }
    }

    public void insertUpdateCoachDrivingLicencePhoto(Integer coachId, String drivingLicencePhotoUrl)
            throws BusinessException {
        CoachCertificatePhoto coachCertificatePhoto = getCoachCertificatePhotoByCoachId(coachId);
        if (coachCertificatePhoto == null) {
            coachCertificatePhoto = new CoachCertificatePhoto();
            coachCertificatePhoto.setDrivingLicencePhoto(drivingLicencePhotoUrl);
            coachCertificatePhoto.setCreatedAt(new Date());
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhoto.setCoachId(coachId);
            coachCertificatePhotoMapper.insert(coachCertificatePhoto);
        } else {
            coachCertificatePhoto.setDrivingLicencePhoto(drivingLicencePhotoUrl);
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.updateByPrimaryKey(coachCertificatePhoto);
        }
    }

    public void insertUpdateCoachCarRunningPhoto(Integer coachId, String carRunningPhotoUrl)
            throws BusinessException {
        CoachCertificatePhoto coachCertificatePhoto = getCoachCertificatePhotoByCoachId(coachId);
        if (coachCertificatePhoto == null) {
            coachCertificatePhoto = new CoachCertificatePhoto();
            coachCertificatePhoto.setCarRunningPhoto(carRunningPhotoUrl);
            coachCertificatePhoto.setCoachId(coachId);
            coachCertificatePhoto.setCreatedAt(new Date());
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.insert(coachCertificatePhoto);
        } else {
            coachCertificatePhoto.setCarRunningPhoto(carRunningPhotoUrl);
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.updateByPrimaryKey(coachCertificatePhoto);
        }
    }

    public void insertUpdateCoachCertificatePhoto(Integer coachId, String coachCertificatePhotoUrl)
            throws BusinessException {
        CoachCertificatePhoto coachCertificatePhoto = getCoachCertificatePhotoByCoachId(coachId);
        if (coachCertificatePhoto == null) {
            coachCertificatePhoto = new CoachCertificatePhoto();
            coachCertificatePhoto.setCoachCertificatePhoto(coachCertificatePhotoUrl);
            coachCertificatePhoto.setCoachId(coachId);
            coachCertificatePhoto.setCreatedAt(new Date());
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.insert(coachCertificatePhoto);
        } else {
            coachCertificatePhoto.setCoachCertificatePhoto(coachCertificatePhotoUrl);
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.updateByPrimaryKey(coachCertificatePhoto);
        }
    }

    public void insertUpdateCoachYunyingzhengInfo(Integer coachId, String operationCertificatePhoto)
            throws BusinessException {
        CoachCertificatePhoto coachCertificatePhoto = getCoachCertificatePhotoByCoachId(coachId);
        if (coachCertificatePhoto == null) {
            coachCertificatePhoto = new CoachCertificatePhoto();
            coachCertificatePhoto.setOperationCertificatePhoto(operationCertificatePhoto);
            coachCertificatePhoto.setCoachId(coachId);
            coachCertificatePhoto.setCreatedAt(new Date());
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.insert(coachCertificatePhoto);
        } else {
            coachCertificatePhoto.setOperationCertificatePhoto(operationCertificatePhoto);
            coachCertificatePhoto.setUpdatedAt(new Date());
            coachCertificatePhotoMapper.updateByPrimaryKey(coachCertificatePhoto);
        }
    }

//    public CoachCertificateInfo getCoachCertificateInfo(int userId) throws BusinessException {
//        CoachCertificateInfo coachCertificateInfo = new CoachCertificateInfo();
//        coachCertificateInfo.setCoachId(userId);
//
//        CoachGeren coachGeren = coachGerenDao.getSingleCoachGeren(userId);
//        coachCertificateInfo.setPersonalImgUrl(coachGeren.getUrl());
//
//        CoachShenfenzhengMaterials singleCoachShenfenzhengMaterials =
//                coachShenfenzhengMaterialsDao.getSingleCoachShenfenzhengMaterials(userId);
//        coachCertificateInfo.setShenfenzhengImgUrl(singleCoachShenfenzhengMaterials.getUrl());
//
//        CoachJiashizhengMaterials singleCoachJiashizhengMaterials =
//                coachJiashizhengMaterialsDao.getSingleCoachJiashizhengMaterials(userId);
//        coachCertificateInfo.setJiashizhengImgUrl(singleCoachJiashizhengMaterials.getUrl());
//
//        CoachJiaolianzhengMaterials singleCoachJiaolianzhengMaterials =
//                coachJiaolianzhengMaterialsDao.getSingleCoachJiaolianzhengMaterials(userId);
//        coachCertificateInfo.setJiaolianzhengImgUrl(singleCoachJiaolianzhengMaterials.getUrl());
//
//        CoachXingshizhengMaterials singleCoachXingshizhengMaterials =
//                coachXingshizhengMaterialsDao.getSingleCoachXingshizhengMaterials(userId);
//        coachCertificateInfo.setXingshizhengImgUrl(singleCoachXingshizhengMaterials.getUrl());
//
//        CoachYunyingzhengMaterials singleCoachYunyingzhengMaterials =
//                coachYunyingzhengMaterialsDao.getSingleCoachYunyingzhengMaterials(userId);
//
//        coachCertificateInfo.setYunyingzhengImgUrl(singleCoachYunyingzhengMaterials.getUrl());
//
//        return coachCertificateInfo;
//    }
//
//    public List<CoachProductInfo> getCoachProductInfos(int coachId) {
//        return coachProductMapper.getAllCoachProduceInfo(coachId);
//    }

//    public CoachDetailInfo getCoachDetailInfoByUserId(int userId) {
//        List<CoachDetailInfo> coachDetailInfos = coachMapper.getCoachDetailInfoByUserId(userId);
//        if (coachDetailInfos.size() == 1 && coachDetailInfos.get(0) != null) {
//            CoachDetailInfo coachDetailInfo = coachDetailInfos.get(0);
//            coachDetailInfo.setPersonalImgUrl(StringUtils.isNotBlank(coachDetailInfo.getPersonalImgUrl()) ?
//                    coachDetailInfo.getPersonalImgUrl() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//            coachDetailInfo.setShenfenzhengPositiveImgUrl(
//                    StringUtils.isNotBlank(coachDetailInfo.getShenfenzhengPositiveImgUrl()) ?
//                            coachDetailInfo.getShenfenzhengPositiveImgUrl() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//            coachDetailInfo.setShenfenzhengNegativeImgUrl(
//                    StringUtils.isNotBlank(coachDetailInfo.getShenfenzhengNegativeImgUrl()) ?
//                            coachDetailInfo.getShenfenzhengNegativeImgUrl() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//            coachDetailInfo.setJiashizhengImgUrl(StringUtils.isNotBlank(coachDetailInfo.getJiashizhengImgUrl()) ?
//                    coachDetailInfo.getJiashizhengImgUrl() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//            coachDetailInfo.setXingshizhengImgUrl(StringUtils.isNotBlank(coachDetailInfo.getXingshizhengImgUrl()) ?
//                    coachDetailInfo.getXingshizhengImgUrl() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//            coachDetailInfo.setJiaolianzhengImgUrl(StringUtils.isNotBlank(coachDetailInfo.getJiaolianzhengImgUrl()) ?
//                    coachDetailInfo.getJiaolianzhengImgUrl() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//            coachDetailInfo.setYunyingzhengImgUrl(StringUtils.isNotBlank(coachDetailInfo.getYunyingzhengImgUrl()) ?
//                    coachDetailInfo.getYunyingzhengImgUrl() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//
//            List<CoachProductInfo> coachProductInfos = getCoachProductInfos(userId);
//            List<CoachDetailInfo.CoachDetailProductInfo> coachDetailProductInfos = new ArrayList<>();
//            for (CoachProductInfo coachProductInfo : coachProductInfos) {
//                CoachDetailInfo.CoachDetailProductInfo coachDetailProductInfo =
//                        new CoachDetailInfo.CoachDetailProductInfo();
//                coachDetailProductInfo.setId(coachProductInfo.getId());
//                coachDetailProductInfo.setCarTypeId(coachProductInfo.getCarTypeId());
//                coachDetailProductInfo.setCarTypeName(coachProductInfo.getCarTypeName());
//                coachDetailProductInfo.setClassTypeId(coachProductInfo.getClassTypeId());
//                coachDetailProductInfo.setClassTypeName(coachProductInfo.getClassTypeName());
//                coachDetailProductInfo.setPrice(coachProductInfo.getPrice());
//                coachDetailProductInfos.add(coachDetailProductInfo);
//            }
//            coachDetailInfo.setCoachProductInfos(coachDetailProductInfos);
////            if (!coachProductInfos.isEmpty()) {
////                coachDetailInfo.setCarTypeName(coachProductInfos.get(0).getCarTypeName());
////                coachDetailInfo.setClassTypeName(coachProductInfos.get(0).getClassTypeName());
////                coachDetailInfo.setPrice(coachDetailInfos.get(0).getPrice());
////            }
//
//            return coachDetailInfo;
//        }
//        return null;
//    }

//    public void addCoachProduct(CoachProduct coachProduct) throws BusinessException {
//        Integer userId = coachProduct.getUserId();
//        List<CoachProduct> coachProductList = getCoachProductByUserId(userId);
//        for (CoachProduct product : coachProductList) {
//            if (product.getCartypeId().equals(coachProduct.getCartypeId())
//                    && product.getClasstypeId().equals(coachProduct.getClasstypeId())) {
//                throw new BusinessException(String.format("该教练相同的车型: %s 班型: %s 已存在!", coachProduct.getCartypeId(),
//                        coachProduct.getClasstypeId()));
//            }
//        }
//        coachProduct.setCreatedAt(new Date());
//        coachProduct.setUpdatedAt(new Date());
//        coachProductMapper.insert(coachProduct);
//    }

//    public void editCoachProduct(CoachProduct coachProduct) throws BusinessException {
//        coachProduct.setUpdatedAt(new Date());
//        coachProductMapper.updateByPrimaryKey(coachProduct);
//    }

//    public void deleteCoachProduct(CoachProduct coachProduct) throws BusinessException {
//        coachProductMapper.deleteByPrimaryKey(coachProduct.getId());
//    }

//    public List<CoachProduct> getCoachProductByUserId(int coachId) {
//        CoachProductExample example = new CoachProductExample();
//        CoachProductExample.Criteria criteria = example.createCriteria();
//        criteria.andUserIdEqualTo(coachId);
//        return coachProductMapper.selectByExample(example);
//    }

    public List<CoachDetailInfo> getAllCoachDetailInfos() {
        return coachMapper.getCoachDetailInfoList();
    }

//    public List<CoachDetailInfo> getAllSubmitCoachDetailInfos() {
//        return coachMapper.getSubmitCoachDetailInfoList();
//    }

//    public List<CoachProductInfo> getCoachProductInfoByCondition(CoachSelectCondition coachSelectCondition) {
//        return coachProductMapper.getCoachProductsByCondition(coachSelectCondition);
//    }

//    public CoachProductInfo getCoachProductDetailById(int productId) {
//        return coachProductMapper.getCoachProductDetailById(productId);
//    }

    public List<CoachTopCountQueryInfo> getCoachTopCountBasicInfos(PaginationInfo paginationInfo) {
        return PaginationDaoUtils.executePaginationQuery(paginationInfo,
                new PaginationDaoUtils.IPaginationMethod<List<CoachTopCountQueryInfo>>() {
                    @Override
                    public List<CoachTopCountQueryInfo> wrapperPagination() {
                        return coachMapper.getCoachTopCountBasicInfos();
                    }
                });
//        return coachBasicInfosMapper.getCoachTopCountBasicInfos(paginationInfo);
    }


    /**
     * 比较传入的时间是否在当前服务器时刻之后
     *
     * @param date
     * @return
     */
//    public boolean isDateLaterThanNow(Date date) {
//        if (date == null) {
//            throw new IllegalArgumentException("Date object is null");
//        }
//        return (new Date().compareTo(date) < 0 ? true : false);
//    }

//    public CoachSchedule getCoachScheduleById(int coachScheduleId) {
//        return coachScheduleMapper.selectByPrimaryKey(coachScheduleId);
//    }

    /**
     * 在主页中显示的教练信息查看对应的教练详情
     *
     * @param coachId
     * @return
     */
    public CoachTopCountQueryInfo getCoachQueryInfoByCoachId(int coachId) {
        return coachMapper.getCoachQueryInfo(coachId);
    }

    public List<CoachLocations> getCoachLocations() {
        return coachMapper.getCoachLocations();
    }

    /**
     * 根据userId来获得上传的照片列表
     *
     * @param userId
     * @return
     */
//    public CoachPersonalDisplayInfo getCoachPersonalDisplayInfoByUserId(int userId) {
//        return coachMapper.getCoachPersonalDisplayInfo(userId);
//    }
    public List<CoachDisplayDetailInfo> getCoachDisplayDetailInfoByCity(int cityId) {
        List<CoachDisplayDetailInfo> coachDisplayDetailInfos = coachMapper.getCoachDisplayDetailInfoByCity(cityId);
        if (CollectionUtils.isNotEmpty(coachDisplayDetailInfos)) {
            for (CoachDisplayDetailInfo coachDisplayDetailInfo : coachDisplayDetailInfos) {
                String coachUploadPhoto = coachDisplayDetailInfo.getCoachUploadPhoto() == null ? "" : coachDisplayDetailInfo.getCoachUploadPhoto();
                String[] coachUploadPhotos = coachUploadPhoto.split(";");
                List<String> coachImgUrls = new ArrayList<>();
                for (String photo : coachUploadPhotos) {
                    if (!StringUtils.isBlank(photo)) {
                        coachImgUrls.add(coachImageUrlPrefix  +coachDisplayDetailInfo.getCityId()+"/"+coachDisplayDetailInfo.getSchoolId()+"/coach/_thumb_495/"+ photo);
                    }
                }
                String coachHdUrl = coachDisplayDetailInfo.getCoachHd() == null?"":coachDisplayDetailInfo.getCoachHd();
                String[] coachHdUrls = coachHdUrl.split(";");
                List<String>  coachHdImgUrls= new ArrayList<>();
                for (String photo : coachHdUrls) {
                    if (!StringUtils.isBlank(photo)) {
                        coachHdImgUrls.add(coachImageUrlPrefix  +coachDisplayDetailInfo.getCityId()+"/"+coachDisplayDetailInfo.getSchoolId()+"/coach/hd/"+ photo);
                    }
                }
                coachDisplayDetailInfo.setCoachHdUrls(coachHdImgUrls);
                coachDisplayDetailInfo.setCoachImgUrl(coachImgUrls);
                coachDisplayDetailInfo.setCoachAvator(coachImageUrlPrefix +coachDisplayDetailInfo.getCityId()+"/"+coachDisplayDetailInfo.getSchoolId()+"/coach/avator/" + coachDisplayDetailInfo.getCoachAvator());//
            }
        }
        return coachDisplayDetailInfos;
    }

    public CoachDisplayDetailInfo getCoachDisplayDetailInfoByCoachId(int coachId) {
        CoachDisplayDetailInfo coachDisplayDetailInfo =
                coachMapper.getCoachDisplayDetailInfoByCoachId(coachId);
        if (coachDisplayDetailInfo != null) {
            String coachUploadPhoto = coachDisplayDetailInfo.getCoachUploadPhoto() == null ? "" : coachDisplayDetailInfo.getCoachUploadPhoto();
            String[] coachUploadPhotos = coachUploadPhoto.split(";");
            List<String> coachImgUrls = new ArrayList<>();
            for (String photo : coachUploadPhotos) {
                if (!StringUtils.isBlank(photo)) {
                    coachImgUrls.add(coachImageUrlPrefix +coachDisplayDetailInfo.getCityId()+"/"+coachDisplayDetailInfo.getSchoolId()+"/coach/_thumb_495/"+ photo);//
                }
            }
            String coachHdUrl = coachDisplayDetailInfo.getCoachHd() == null?"":coachDisplayDetailInfo.getCoachHd();
            String[] coachHdUrls = coachHdUrl.split(";");
            List<String>  coachHdImgUrls= new ArrayList<>();
            for (String photo : coachHdUrls) {
                if (!StringUtils.isBlank(photo)) {
                    coachHdImgUrls.add(coachImageUrlPrefix + coachDisplayDetailInfo.getCityId()+"/"+coachDisplayDetailInfo.getSchoolId()+"/coach/hd/"+ photo);
                }
            }
            coachDisplayDetailInfo.setCoachHdUrls(coachHdImgUrls);
            coachDisplayDetailInfo.setCoachImgUrl(coachImgUrls);
            coachDisplayDetailInfo.setCoachAvator(coachImageUrlPrefix +coachDisplayDetailInfo.getCityId()+"/"+coachDisplayDetailInfo.getSchoolId()+"/coach/avator/"+ coachDisplayDetailInfo.getCoachAvator());//
        }
        return coachDisplayDetailInfo;
    }

    public void insertCoachUploadPhotos(int coachId, String coachUploadPhotoImages) throws BusinessException {
        CoachUploadPhoto coachUploadPhoto = new CoachUploadPhoto();
        coachUploadPhoto.setCoachId(coachId);

//        for (int i = 0; i < Math.min(coachUploadPhotoImages.length, 19); i++) {
//            try {
//                BeanUtils.setProperty(coachUploadPhoto, String.format("setUploadPhoto%s", i + 1),
//                        coachUploadPhotoImages[i]);
//            } catch (IllegalAccessException | InvocationTargetException e) {
//                throw new BusinessException(e);
//            }
//        }
        coachUploadPhoto.setUploadPhoto(coachUploadPhotoImages);
        coachUploadPhoto.setCreatedAt(new Date());
        coachUploadPhotoMapper.insert(coachUploadPhoto);
    }
}
