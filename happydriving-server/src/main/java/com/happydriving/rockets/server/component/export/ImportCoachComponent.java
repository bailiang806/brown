package com.happydriving.rockets.server.component.export;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.*;
import com.happydriving.rockets.server.service.*;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author mazhiqiang
 */
@Component
@Deprecated
public class ImportCoachComponent {

    private static final Logger LOG = Logger.getLogger(ImportCoachComponent.class);

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

//    public void importCoachFromCoachList(List<CoachImportData> coachImportDatas) {
//        for (CoachImportData coachImportData : coachImportDatas) {
//
//            Users user = null;
//            try {
//                user = userService.insertCoachInfo(coachImportData.getPhone(), DEFAULT_PASSWORD);
//                CoachBasicInfos coachBasicInfos = new CoachBasicInfos();
//                coachBasicInfos.setUserId(user.getId());
//                coachBasicInfos.setName(coachImportData.getUserName());
//                coachBasicInfos.setSchoolName(coachImportData.getSchoolName());
//                coachBasicInfos.setQq(coachImportData.getQq());
//                coachService.insertUpdateCoachBasicInfo(coachBasicInfos);
//            } catch (BusinessException e) {
//                LOG.error(e);
//            }
//
//        }
//    }
//
//    public static List<InputObject> parseInputObjects(List<String> inputFilePathList) throws BusinessException {
//        List<InputObject> result = new ArrayList<>();
//        for (String inputFilePath : inputFilePathList) {
//            result.add(parseInputExcelFile(inputFilePath));
//        }
//        return result;
//    }

    private void parseInputExcelFile(String inputFilePath) throws BusinessException {
//        InputObject inputObject = new InputObject();
        File inputFile = new File(inputFilePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(inputFile);
            Workbook hssfWorkbook = new XSSFWorkbook(fis);
            Sheet commonSheet = hssfWorkbook.getSheet(SHEET_NAME_COMMON);
            String cityName = commonSheet.getRow(2).getCell(2).getStringCellValue();
            String provinceName = commonSheet.getRow(3).getCell(2).getStringCellValue();

            int provinceId = regionService.getSingleProvinceByName(provinceName).getId();
            int cityId = regionService.getSingleCityByName(cityName).getId();

            int serviceCityId = serviceCityService.getCityByName(cityName).getId();

            Sheet drivingSchoolSheet = hssfWorkbook.getSheet(SHEET_NAME_DRIVING_SCHOOL);
            for (int i = 1; i < drivingSchoolSheet.getPhysicalNumberOfRows(); i++) {
                Row row = drivingSchoolSheet.getRow(i);
                if (row.getCell(2) == null || StringUtils.isBlank(row.getCell(2).getStringCellValue())) {
                    break;
                }

                String schoolName = row.getCell(2).getStringCellValue();
                String schoolAddress = row.getCell(3).getStringCellValue();
                String schoolDetail = row.getCell(4).getStringCellValue();
                String schoolAvator = row.getCell(5).getStringCellValue();
                //TODO split to multiple images
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
                if (drivingSchool == null) {
                    drivingSchool = new DrivingSchool();
                    drivingSchoolDetail.setId(drivingSchool.getId());
                }
                drivingSchoolDetail.setSchoolDetail(schoolDetail);
                drivingSchoolDetail.setSchoolAvator(schoolAvator);
                drivingSchoolDetail.setSchoolImgurl(schoolImgUrl);
                drivingSchoolService.insertUpdateDrivingSchoolDetail(drivingSchoolDetail);
            }

            Sheet coachSheet = hssfWorkbook.getSheet(SHEET_NAME_COACH);
            for (int i = 1; i < coachSheet.getPhysicalNumberOfRows(); i++) {
                Row row = coachSheet.getRow(i);
                if (row.getCell(2) == null || row.getCell(2).getNumericCellValue() == 0.0f) {
                    break;
                }
                String phone = String.valueOf(Double.valueOf(row.getCell(2).getNumericCellValue()).intValue());
                String coachName = row.getCell(3).getStringCellValue();
                String coachSex = row.getCell(4).getStringCellValue();
                String countyName = row.getCell(5).getStringCellValue();
                County county = regionService.getSingleCountryByName(countyName);
                String address = row.getCell(6).getStringCellValue();
                String comment = row.getCell(7).getStringCellValue();

                String drivingSchoolName = row.getCell(8).getStringCellValue();
                DrivingSchool drivingSchool =
                        drivingSchoolService.getDrivingSchoolByName(serviceCityId, drivingSchoolName);

                String qq = row.getCell(9).getStringCellValue();

                User user = userService.getUserByPhoneNumber(phone);
                if (user == null) {
                    user = userService.insertUser(phone, CommonConstants.ROLE_COACH);
                }

                Coach coach = coachService.getCoachByCoachId(user.getId());
                coach.setName(coachName);
                coach.setSex(coachSex);
                coach.setAddress(address);
                coach.setComment(comment);
                coach.setT1(String.valueOf(provinceId));
                coach.setT2(String.valueOf(cityId));
                coach.setT3(String.valueOf(county.getId()));
                coach.setQq(qq);
                coach.setSchoolId(drivingSchool.getId());
                coach.setIsHide("N");
                coachService.insertCoach(coach);

                String carTypeName = row.getCell(10).getStringCellValue();
                String coachAvator = row.getCell(11).getStringCellValue();

                coachService.insertUpdateCoachPersonalPhoto(user.getId(), coachAvator);

                String coachUploadImages = row.getCell(12).getStringCellValue().replace("；", ";");
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
