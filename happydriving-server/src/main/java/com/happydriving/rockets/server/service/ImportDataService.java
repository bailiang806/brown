package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.Coach;
import com.happydriving.rockets.server.entity.DrivingSchool;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author mazhiqiang
 */
@Service
public class ImportDataService {

    private static final Logger LOG = Logger.getLogger(ImportDataService.class);

    public static final String SHEET_NAME_COMMON = "公共信息";
    public static final String SHEET_NAME_COACH = "教练";
    public static final String SHEET_NAME_DRIVING_SCHOOL = "驾校";
    public static final String SHEET_NAME_TRAINING_SPACE = "训练场";
    public static final String SHEET_NAME_STORE_SPACE = "门店";

    @Autowired
    private UserService userService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private ServiceCityService serviceCityService;

    @Autowired
    private DrivingSchoolService drivingSchoolService;

    public void insertInputExcelFile(String inputFilePath) throws BusinessException {
//        InputObject inputObject = new InputObject();
        File inputFile = new File(inputFilePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(inputFile);
            Workbook hssfWorkbook = new XSSFWorkbook(fis);
            Sheet commonSheet = hssfWorkbook.getSheet(SHEET_NAME_COMMON);
            String cityName = commonSheet.getRow(1).getCell(1).getStringCellValue();
            String provinceName = commonSheet.getRow(2).getCell(1).getStringCellValue();

//            int provinceId = regionService.getSingleProvinceByName(provinceName).getId();
//            int cityId = regionService.getSingleCityByName(cityName).getId();

            int serviceCityId = serviceCityService.getCityByName(cityName).getId();

            Sheet drivingSchoolSheet = hssfWorkbook.getSheet(SHEET_NAME_DRIVING_SCHOOL);
            for (int i = 1; i < drivingSchoolSheet.getPhysicalNumberOfRows(); i++) {
                Row row = drivingSchoolSheet.getRow(i);
                if (row.getCell(1) == null || StringUtils.isBlank(row.getCell(1).getStringCellValue())) {
                    break;
                }

                String schoolName = row.getCell(1).getStringCellValue();
                String schoolAddress = row.getCell(2).getStringCellValue();
                String schoolDetail = row.getCell(3).getStringCellValue();
//                long price = Double.valueOf(row.getCell(4).getNumericCellValue()).longValue() * 100L;
                String schoolAvator = row.getCell(5) == null ? null : row.getCell(5).getStringCellValue();
                String schoolImgUrl = row.getCell(6).getStringCellValue();
                DrivingSchool drivingSchool =
                        drivingSchoolService.getDrivingSchoolByName(serviceCityId, schoolName);
                if (drivingSchool == null) {
                    drivingSchool =
                            drivingSchoolService.addDrivingSchool(cityName, schoolName, schoolAddress);
                } else {
                    drivingSchool.setAddress(schoolAddress);
                    drivingSchoolService.updateDrivingSchool(drivingSchool);
                }

                DrivingSchool drivingSchoolDetail =
                        drivingSchoolService.getSingleDrivingSchoolDetailBySchoolId(drivingSchool.getId());
                if (drivingSchoolDetail == null) {
                    drivingSchoolDetail = new DrivingSchool();
                    drivingSchoolDetail.setId(drivingSchool.getId());
                }
                drivingSchoolDetail.setSchoolDetail(schoolDetail);
                drivingSchoolDetail.setSchoolAvator(schoolAvator);
                drivingSchoolDetail.setSchoolImgurl(schoolImgUrl.replaceAll("；", ";"));

                drivingSchoolService.insertUpdateDrivingSchoolDetail(drivingSchoolDetail);
            }

            Sheet coachSheet = hssfWorkbook.getSheet(SHEET_NAME_COACH);
            for (int i = 1; i < coachSheet.getPhysicalNumberOfRows(); i++) {
                Row row = coachSheet.getRow(i);
                if (row.getCell(0) == null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                    break;
                }
//                String phone = String.valueOf(Double.valueOf(row.getCell(1).getNumericCellValue()).intValue());
                String coachName = row.getCell(2).getStringCellValue();
                String coachSex = row.getCell(3).getStringCellValue();
                String countyName = row.getCell(4).getStringCellValue();
//                County county = regionService.getSingleCountryByName(countyName);
                String address = row.getCell(5).getStringCellValue();
//                String comment = row.getCell(6).getStringCellValue();

                String drivingSchoolName = row.getCell(7).getStringCellValue();
                DrivingSchool drivingSchool =
                        drivingSchoolService.getDrivingSchoolByName(serviceCityId, drivingSchoolName);

//                String qq = String.valueOf(Double.valueOf(row.getCell(8).getNumericCellValue()).intValue());

                Coach coach = new Coach();
//                coach.setPhone(phone);
                coach.setName(coachName);
                coach.setSex(coachSex);
                coach.setAddress(address);
//                coach.setComment(comment);
//                coach.setT1(String.valueOf(provinceId));
//                coach.setT2(String.valueOf(cityId));
//                coach.setT3(String.valueOf(county.getId()));
//                coach.setQq(qq);
                coach.setSchoolId(drivingSchool.getId());
                coach.setCityId(serviceCityId);
                coach.setUpdatedAt(new Date());
                coachService.insertCoach(coach);

//                String carTypeName = row.getCell(9).getStringCellValue();
                String coachAvator = row.getCell(9).getStringCellValue();

                coachService.insertUpdateCoachPersonalPhoto(coach.getId(), coachAvator);

                String coachUploadImages = row.getCell(10).getStringCellValue().replace("；", ";");
//                String[] coachUploadImageArray = coachUploadImages.split(";");

                coachService.insertCoachUploadPhotos(coach.getId(), coachUploadImages);
            }
        } catch (IOException e) {
            throw new BusinessException(e);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

}
