package com.happydriving.rockets.server.controller;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.mapper.CoachScheduleMapper;
import com.happydriving.rockets.server.model.CoachDisplayDetailInfo;
import com.happydriving.rockets.server.service.CoachService;
import com.happydriving.rockets.server.service.StudentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/coach")
public class CoachController {

    private static final Log LOG = LogFactory.getLog(CoachController.class);

    public static final String PERSONAL_PIC_SUFFIX = "personal";
    public static final String JIASHIZHENG_PIC_SUFFIX = "jiashizheng";
    public static final String XINGSHIZHENG_PIC_SUFFIX = "xingshizheng";
    public static final String SHENFENZHENG_PIC_SUFFIX = "shenfenzheng";
    public static final String JIAOLIANZHENG_PIC_SUFFIX = "jiaolianzheng";
    public static final String YUNYINGZHENG_PIC_SUFFIX = "yunyingzheng";

    @Autowired
    private CoachService coachService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CoachScheduleMapper coachScheduleMapper;

    @Value("#{drivingConfigProperties.localImageLocation}")
    private String localImageLocation;

    @Value("#{drivingConfigProperties.imageUrlPrefix}")
    private String imageUrlPrefix;

    @RequestMapping("/coach_basic")
    public String getNewCoachUrl() {
        return "coach_basic";
    }

    /**
     * 用于教练注册完成后，第一次提交个人信息，其中的个人信息包括:
     * <p/>
     * <li>姓名，紧急联系人及联系方式</li>
     * <li>教练所在地域</li>
     * <li>车型，班型，价格（只有一个）等信息</li>
     *
     * @param request
     * @return
     * @throws BusinessException
     */
//    @RequestMapping(value = "/doUploadBasic", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject doUploadBasicInfo(HttpServletRequest request) throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        String username = request.getParameter("username");
//        String urgentname = request.getParameter("urgentname");
//        String urgentphone = request.getParameter("urgentphone");
//        Coach coach = coachService.getCoachByUserId(Integer.parseInt(userId));
//        coach.setName(username);
//        coach.setUrgentName(urgentname);
//        coach.setUrgentPhone(urgentphone);
//
//        coach.setT1(request.getParameter("province"));
//        coach.setT2(request.getParameter("city"));
//        coach.setT3(request.getParameter("county"));
//        coach.setT4(request.getParameter("town"));
//        coach.setAddress(request.getParameter("detailAddress"));
//
//        coach.setUpdatedAt(new Date());
//
//        CoachProduct coachProduct = new CoachProduct();
//        coachProduct.setUserId(Integer.parseInt(userId));
//        coachProduct.setClasstypeId(Integer.parseInt(request.getParameter("classType")));
//        coachProduct.setCartypeId(Integer.parseInt(request.getParameter("carType")));
//        coachProduct.setPrice(new BigDecimal(request.getParameter("price")).setScale(CommonConstants.DEFAULT_SCALE,
//                CommonConstants.DEFAULT_ROUNDING_MODE));
//        coachProduct.setCreatedAt(new Date());
//        coachProduct.setUpdatedAt(new Date());
//
//        coach.setUpdatedAt(new Date());
//        coachService.insertUpdateCoachProduct(coach, coachProduct);
//        return new ResponseJsonObject(true);
//    }

//    @RequestMapping("/getEditCoachUrl")
//    public ModelAndView getEditCoachUrl(HttpServletRequest request) throws BusinessException {
//        String coachId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        CoachDetailInfo coachDetailInfo = coachService.getCoachDetailInfoByUserId(Integer.parseInt(coachId));
//        if (coachDetailInfo != null) {
//            ModelAndView modelAndView = new ModelAndView("coach_edit");
//            modelAndView.addObject("coachDetailInfo", coachDetailInfo);
//            return modelAndView;
//        }
//        throw new BusinessException(String.format("教练 : %s 相关信息不存在!", coachId));
//    }

    /**
     * 用于编辑教练基本信息，在教练登录完成后进行改动
     *
     * @param request
     * @return
     * @throws BusinessException
     */
//    @RequestMapping(value = "/doEditCoachBasic", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject doEditCoachBasicInfo(HttpServletRequest request) throws BusinessException {
//        String coachId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        String username = request.getParameter("username");
//        String urgentname = request.getParameter("urgentname");
//        String urgentphone = request.getParameter("urgentphone");
//        Coach coach = coachService.getCoachByUserId(Integer.parseInt(coachId));
//        coach.setName(username);
//        coach.setUrgentName(urgentname);
//        coach.setUrgentPhone(urgentphone);
//        coach.setUpdatedAt(new Date());
//        coach.setT1(request.getParameter("province"));
//        coach.setT2(request.getParameter("city"));
//        coach.setT3(request.getParameter("county"));
//        coach.setT4(request.getParameter("town"));
//        coach.setAddress(request.getParameter("detailAddress"));
//
//        CoachProduct coachProduct = new CoachProduct();
//        coachProduct.setUpdatedAt(new Date());
//        coachProduct.setUserId(Integer.parseInt(coachId));
//        coachProduct.setClasstypeId(Integer.parseInt(request.getParameter("classType")));
//        coachProduct.setCartypeId(Integer.parseInt(request.getParameter("carType")));
//        coachProduct.setPrice(new BigDecimal(request.getParameter("price")).setScale(CommonConstants.DEFAULT_SCALE,
//                CommonConstants.DEFAULT_ROUNDING_MODE));
//
//        coach.setKaihh(request.getParameter("banktype"));
//        coach.setKaihm(request.getParameter("bankusername"));
//        coach.setKahao(request.getParameter("cardnumber"));
//        coach.setUpdatedAt(new Date());
//        coachService.insertUpdateCoachProduct(coach, coachProduct);
//        return showCoachDetailInfo(request);
//    }

//    @RequestMapping(value = "/doEditCoachInfo", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject doEditCoachInfo(@RequestBody CoachDetailInfo coachDetailInfo,
//                                              HttpServletRequest request) {
//        int userId = Integer.parseInt(request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString());
//        Coach coach = coachService.getCoachByUserId(userId);
//        coach.setName(coachDetailInfo.getCoachName());
//        coach.setUrgentName(coachDetailInfo.getUrgentName());
//        coach.setUrgentPhone(coachDetailInfo.getUrgentPhoneNumber());
//        coach.setT1(String.valueOf(coachDetailInfo.getProvinceId()));
//        coach.setT2(String.valueOf(coachDetailInfo.getCityId()));
//        coach.setT3(String.valueOf(coachDetailInfo.getCountyId()));
//        coach.setT4(String.valueOf(coachDetailInfo.getTownId()));
//        coach.setAddress(coachDetailInfo.getDetailAddress());
//        coach.setComment(coachDetailInfo.getComment());
//
//        List<CoachProduct> coachProducts = new ArrayList<>();
//        for (CoachDetailInfo.CoachDetailProductInfo coachDetailProductInfo : coachDetailInfo.getCoachProductInfos()) {
//            CoachProduct coachProduct = new CoachProduct();
//            coachProduct.setId(coachDetailProductInfo.getId());
//            coachProduct.setCartypeId(coachDetailProductInfo.getCarTypeId());
//            coachProduct.setClasstypeId(coachDetailProductInfo.getClassTypeId());
//            coachProduct.setPrice(coachDetailProductInfo.getPrice());
//            coachProduct.setUpdatedAt(new Date());
//            coachProduct.setUserId(userId);
//            coachProducts.add(coachProduct);
//        }
//
//        coach.setKaihh(coachDetailInfo.getBankId());
//        coach.setKaihm(coachDetailInfo.getCardUserName());
//        coach.setKahao(coachDetailInfo.getCardNumber());
//        coach.setUpdatedAt(new Date());
//
//        try {
//            coachService.insertUpdateCoachProducts(coach, coachProducts);
//            return new ResponseJsonObject(true);
//        } catch (BusinessException e) {
//            LOG.error(e);
//            return new ResponseJsonObject(false, e.getMessage());
//        }
//    }

//    @RequestMapping("/getCoachCard")
//    public String getCoachCardUrl() {
//        return "coach_card";
//    }

    /**
     * 教练第一次注册过程中，教练需要填写个人银行卡等相关信息
     *
     * @param request
     * @return
     * @throws BusinessException
     */
//    @RequestMapping(value = "/doUploadCard", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject doUploadCoachCard(HttpServletRequest request) throws BusinessException {
//        String coachId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        Coach coach = coachService.getCoachByUserId(Integer.parseInt(coachId));
//
//        String cardnumber = request.getParameter("cardnumber");
//        String confirmcardnumber = request.getParameter("confirmcardnumber");
//        if (!cardnumber.equals(confirmcardnumber)) {
//            throw new BusinessException("两次输入的卡号不一致!");
//        }
//        coach.setKaihh(request.getParameter("banktype"));
//        coach.setKaihm(request.getParameter("bankusername"));
//        coach.setKahao(cardnumber);
//        coachService.updateCoach(coach);
//        return new ResponseJsonObject(true);
////        return getUploadTxUrl(request);
//    }

//    @RequestMapping(value = "/getUploadImgUrl", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getUploadImgUrl(HttpServletRequest request) {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        CoachCertificatePhoto coachCertificatePhoto = coachService.getCoachCertificatePhotoByCoachId(
//                Integer.parseInt(userId));
//        return new ResponseJsonObject(true, coachCertificatePhoto);
//    }


//    @RequestMapping("/getUploadTxUrl")
//    public ModelAndView getUploadTxUrl(HttpServletRequest request) throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        CoachCertificatePhoto coachCertificatePhoto = coachService.getCoachCertificatePhotoByCoachId(
//                Integer.parseInt(userId));
//        ModelAndView modelAndView = new ModelAndView("uploadtx");
//        modelAndView.addObject("imgUrl",
//                coachCertificatePhoto != null && StringUtils.isNotBlank(coachCertificatePhoto.getPersonalPhoto()) ?
//                        coachCertificatePhoto.getPersonalPhoto() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/doUploadTx", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject doUploadTx(HttpServletRequest request,
//                                         @RequestParam("picImgUrl") MultipartFile pictureImgUrl)
//            throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        String picUrl = transferPicFile(pictureImgUrl, localImageLocation, userId, PERSONAL_PIC_SUFFIX);
//        coachService.insertUpdateCoachPersonalPhoto(Integer.parseInt(userId), picUrl);
//        return new ResponseJsonObject(true);
////        return new ModelAndView("redirect:/coach/getUploadSfzUrl");
//    }
//
//    @RequestMapping("/getUploadSfzUrl")
//    public ModelAndView getUploadSfzUrl(HttpServletRequest request) throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        CoachCertificatePhoto coachCertificatePhoto = coachService.getCoachCertificatePhotoByCoachId(
//                Integer.parseInt(userId));
//        ModelAndView modelAndView = new ModelAndView("uploadsfz");
//        modelAndView.addObject("positiveImgUrl",
//                coachCertificatePhoto != null && StringUtils
//                        .isNotBlank(coachCertificatePhoto.getIdCardPositivePhoto()) ?
//                        coachCertificatePhoto.getIdCardPositivePhoto() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//        modelAndView.addObject("negativeImgUrl",
//                coachCertificatePhoto != null && StringUtils
//                        .isNotBlank(coachCertificatePhoto.getIdCardNegativePhoto()) ?
//                        coachCertificatePhoto.getIdCardNegativePhoto() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//        return modelAndView;
//    }
//
//    @RequestMapping("/doUploadSfz")
//    @ResponseBody
//    public ResponseJsonObject doUploadSfz(HttpServletRequest request,
//                                          @RequestParam("positivePicImgUrl") MultipartFile positivePicImgUrl,
//                                          @RequestParam("negativePicImgUrl") MultipartFile negativePicImgUrl)
//            throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        String positiveServerImgUrl = transferPicFile(positivePicImgUrl, localImageLocation, userId,
//                SHENFENZHENG_PIC_SUFFIX);
//        String negativeServerImgUrl =
//                transferPicFile(negativePicImgUrl, localImageLocation, userId, SHENFENZHENG_PIC_SUFFIX);
//        coachService.insertUpdateCoachIdCardPhoto(Integer.parseInt(userId), positiveServerImgUrl,
//                negativeServerImgUrl);
////        return new ModelAndView("redirect:/coach/getUploadJszUrl");
//        return new ResponseJsonObject(true);
//    }
//
//    @RequestMapping("/getUploadJszUrl")
//    public ModelAndView getUploadJszUrl(HttpServletRequest request) throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        CoachCertificatePhoto coachCertificatePhoto = coachService.getCoachCertificatePhotoByCoachId(
//                Integer.parseInt(userId));
//        ModelAndView modelAndView = new ModelAndView("uploadjsz");
//        modelAndView.addObject("imgUrl",
//                coachCertificatePhoto != null && StringUtils
//                        .isNotBlank(coachCertificatePhoto.getDrivingLicencePhoto()) ?
//                        coachCertificatePhoto.getDrivingLicencePhoto() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/doUploadJsz", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject doUploadJiashizheng(HttpServletRequest request,
//                                                  @RequestParam("picImgUrl") MultipartFile pictureImgUrl)
//            throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        String picUrl = transferPicFile(pictureImgUrl, localImageLocation, userId, JIASHIZHENG_PIC_SUFFIX);
//        coachService.insertUpdateCoachDrivingLicencePhoto(Integer.parseInt(userId), picUrl);
//        return new ResponseJsonObject(true);
////        return new ModelAndView("redirect:/coach/getUploadXszUrl");
//    }
//
//    @RequestMapping("/getUploadXszUrl")
//    public ModelAndView getUploadXszUrl(HttpServletRequest request) throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        CoachCertificatePhoto coachCertificatePhoto = coachService.getCoachCertificatePhotoByCoachId(
//                Integer.parseInt(userId));
//        ModelAndView modelAndView = new ModelAndView("uploadxsz");
//        modelAndView.addObject("imgUrl",
//                coachCertificatePhoto != null && StringUtils.isNotBlank(coachCertificatePhoto.getCarRunningPhoto()) ?
//                        coachCertificatePhoto.getCarRunningPhoto() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/doUploadXsz", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject doUploadXsz(HttpServletRequest request,
//                                          @RequestParam("picImgUrl") MultipartFile pictureImgUrl)
//            throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        String picUrl = transferPicFile(pictureImgUrl, localImageLocation, userId, XINGSHIZHENG_PIC_SUFFIX);
//        coachService.insertUpdateCoachCarRunningPhoto(Integer.parseInt(userId), picUrl);
//        return new ResponseJsonObject(true);
////        return new ModelAndView("redirect:/coach/getUploadJlzUrl");
//    }
//
//    @RequestMapping(value = "/getUploadJlzUrl")
//    public ModelAndView getUploadJlzUrl(HttpServletRequest request) throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        CoachCertificatePhoto coachCertificatePhoto = coachService.getCoachCertificatePhotoByCoachId(
//                Integer.parseInt(userId));
//        ModelAndView modelAndView = new ModelAndView("uploadjlz");
//        modelAndView.addObject("imgUrl",
//                coachCertificatePhoto != null && StringUtils.isNotBlank(
//                        coachCertificatePhoto.getCoachCertificatePhoto()) ?
//                        coachCertificatePhoto.getCoachCertificatePhoto() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/doUploadJlz", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject doUploadJlz(HttpServletRequest request,
//                                          @RequestParam("picImgUrl") MultipartFile pictureImgUrl)
//            throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        String picUrl = transferPicFile(pictureImgUrl, localImageLocation, userId, JIAOLIANZHENG_PIC_SUFFIX);
//        coachService.insertUpdateCoachCertificatePhoto(Integer.parseInt(userId), picUrl);
//        return new ResponseJsonObject(true);
////        return new ModelAndView("redirect:/coach/getUploadYyzUrl");
//    }
//
//    @RequestMapping("/getUploadYyzUrl")
//    public ModelAndView getUploadYyzUrl(HttpServletRequest request) throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        CoachCertificatePhoto coachCertificatePhoto = coachService.getCoachCertificatePhotoByCoachId(
//                Integer.parseInt(userId));
//        ModelAndView modelAndView = new ModelAndView("uploadyyz");
//        modelAndView.addObject("imgUrl",
//                coachCertificatePhoto != null && StringUtils.isNotBlank(
//                        coachCertificatePhoto.getOperationCertificatePhoto()) ?
//                        coachCertificatePhoto.getOperationCertificatePhoto() : CommonConstants.DEFALUT_IMG_PLACEHOLDER);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/doUploadYyz", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject doUploadYyz(HttpServletRequest request,
//                                          @RequestParam("picImgUrl") MultipartFile pictureImgUrl)
//            throws BusinessException {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        String picUrl = transferPicFile(pictureImgUrl, localImageLocation, userId, YUNYINGZHENG_PIC_SUFFIX);
//        coachService.insertUpdateCoachYunyingzhengInfo(Integer.parseInt(userId), picUrl);
//        //return getAddCommentUrl(request);
//        return new ResponseJsonObject(true);
//    }
//
//    private String transferPicFile(MultipartFile multipartFile, String imagesPath, String coachId, String suffix)
//            throws BusinessException {
//        if (!multipartFile.isEmpty()) {
//            try {
//                String fileName = String.format("%s-%s.jpg", coachId, suffix);
//                String fileUrl = String.format("%s/%s", imagesPath, fileName);
//                multipartFile.transferTo(new File(fileUrl));
//                return String.format("%s/%s", imageUrlPrefix, fileName);
//            } catch (IOException e) {
//                throw new BusinessException(e);
//            }
//        }
//        throw new BusinessException("Multifile is empty!");
//    }

//    @RequestMapping(value = "/getAddCommentUrl", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getAddCommentUrl(HttpServletRequest request) throws BusinessException {
//        String coachId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        Coach coach = coachService.getCoachByUserId(Integer.parseInt(coachId));
////        ModelAndView modelAndView = new ModelAndView("coach_comment");
////        modelAndView.addObject("comment", coach.getComment());
//        return new ResponseJsonObject(true, coach.getComment());
//    }
//
//
//    @RequestMapping("/doAddComment")
//    @ResponseBody
//    public ResponseJsonObject addCoachComment(HttpServletRequest request) throws BusinessException {
//        String coachId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        String comment = request.getParameter("comment");
//        Coach coach = coachService.getCoachByUserId(Integer.parseInt(coachId));
//        coach.setComment(comment);
//        coachService.updateCoach(coach);
//        return showCoachDetailInfo(request);
//    }
//
//    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONPObject getCoachProducts(HttpServletRequest request) {
//        String userId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//        List<CoachProduct> coachProducts = coachService.getCoachProductByUserId(Integer.parseInt(userId));
//        return new JSONPObject(CommonConstants.JSONP_CALLBACK_FUNCTION, coachProducts);
//    }
//
//    /**
//     * @param request
//     * @return
//     * @throws BusinessException
//     */
//    @RequestMapping(value = "/show_detail", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject showCoachDetailInfo(HttpServletRequest request) throws BusinessException {
//        Object userid = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        ResponseJsonObject result = new ResponseJsonObject(true);
//        if (userid == null) {
//            result.setRedirectUrl("/jsp/coachGuide.jsp");
//            return result;
//        }
//        String coachId = userid.toString();
//        CoachDetailInfo coachDetailInfo = coachService.getCoachDetailInfoByUserId(Integer.parseInt(coachId));
//        if (coachDetailInfo != null) {
////            ModelAndView modelAndView = new ModelAndView();
////            modelAndView.addObject("coachDetailInfo", coachDetailInfo);
////            modelAndView.setViewName("coach_info");
////            return modelAndView;
//            result.setReturnObject(coachDetailInfo);
//            result.setRedirectUrl("coach_info");
//            return result;
//        }
//        result.setRedirectUrl("coach_basic");
//        return result;
////        return new ModelAndView("coach_basic");
//    }
//
//    @RequestMapping(value = "/showDetail", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getCoachDetailsByCoachId(HttpServletRequest request) {
//        int coachId = Integer.parseInt(request.getParameter("coachId"));
//        CoachDetailInfo coachDetailInfo = coachService.getCoachDetailInfoByUserId(coachId);
//        if (coachDetailInfo == null) {
//            return new ResponseJsonObject(false,
//                    String.format("教练: id=%s 不存在!", coachId));
//        }
//        ResponseJsonObject responseJsonObject = new ResponseJsonObject(true);
//        responseJsonObject.setReturnObject(coachDetailInfo);
//        return responseJsonObject;
//    }

    /**
     * 添加教练周历(schedule)之前对参数的检查:
     * <p/>
     * 1. 时间开始时间在当前时刻之后
     * 2. 时间段与当前用户的其他schedule不冲突
     * 3. user_id强制赋值为session['userId']
     * <p/>
     * <p/>
     * Created by jasonzhu on 24/6/15.
     *
     * @param request
     * @param coachSchedule starttime, endtime
     * @return 1. success:                 [result:true, message: "", id: 1, returnObject: null]
     * 2. 日期需要大于当前时间        [result:false, message: "传入时间段不在当前时间之后", id:-1, returnObject: null]
     * 3. 日期和其他schedule冲突     [result:false, message: "日期和其他安排冲突", id:-1, returnObject: 冲突的CoachSchedule对象]
     * 4. Exception                [result:false, message:exception.message, id:-1, returnObject: null]
     */
//    @RequestMapping(value = "/schedule/verifyDate", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject preAddScheduleCheck(HttpServletRequest request,
//                                                  @RequestBody CoachSchedule coachSchedule) {
//        LOG.info(coachSchedule.getAddrInDetail());
//        try {
//            //starttime, endtime按时间先后顺序做检查，颠倒的话则互换
//            if (coachSchedule.getStarttime().compareTo(coachSchedule.getEndtime()) > 0) {
//                Date tmp = coachSchedule.getStarttime();
//                coachSchedule.setStarttime(coachSchedule.getEndtime());
//                coachSchedule.setEndtime(tmp);
//            }
//
//            //设置当前教练userId
//            String coachId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//            coachSchedule.setUserId(Integer.parseInt(coachId, 10));
//
//            //检查schedule设置的时间是否在当前时间之后
//            if (coachService.isDateLaterThanNow(coachSchedule.getStarttime()) == false) {
//                return new ResponseJsonObject(false, "传入时间段不在当前时间之后", -1);
//            }
//
//            //检查时间段是否与当前用户其他schedule冲突
//            CoachSchedule csRtn = coachScheduleMapper.selectIfScheduleConflict(coachSchedule);
//            if (csRtn != null) {
//                return new ResponseJsonObject(false, "日期和其他安排冲突", -1, csRtn);
//            }
//
//            return new ResponseJsonObject(true, "");
//
//        } catch (Exception e) {
//            LOG.error("Exception=[" + ExceptionUtils.getStackTrace(e) + "]");
//            return new ResponseJsonObject(false, e.getMessage(), -1);
//        }
//    }
//
//
//    /**
//     * 添加教练周历（schedule）
//     * <p/>
//     * <p/>
//     * Created by jasonzhu on 24/6/15.
//     *
//     * @param request
//     * @param coachSchedule
//     * @return 1. success:                 [result:true, message: "", id: 1, returnObject: null]
//     * 2. inherit from /schedule/verifyDate API.
//     * 3. insert table失败          [result:false, message:"insert into table=[coach_schedule] error.", id:-1, returnObject: null]
//     * 4. Exception                [result:false, message:exception.message, id:-1, returnObject: null]
//     */
//    @RequestMapping(value = "/schedule/add", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseJsonObject addSchedule(HttpServletRequest request,
//                                          @RequestBody CoachSchedule coachSchedule) {
//        try {
//            //设置当前教练userId
//            String coachId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//            coachSchedule.setUserId(Integer.parseInt(coachId, 10));
//
//            //检查coachSchedule的相关参数正确性与完整性
//            ResponseJsonObject checkRtn = preAddScheduleCheck(request, coachSchedule);
//            if (checkRtn.isResult() == false) {
//                return checkRtn;
//            }
//
//            //增加createdAt, updatedAt参数
//            coachSchedule.setCreatedAt(new Date());
//            coachSchedule.setUpdatedAt(new Date());
//
//            //插入coach_schedule表
//            if (coachScheduleMapper.insert(coachSchedule) > 0) {
//                return new ResponseJsonObject(true, "");
//            } else {
//                LOG.error("insert into table=[coach_schedule] error. CoachSchedule=[" + coachSchedule.toString() + "]");
//                return new ResponseJsonObject(false,
//                        "insert into table=[coach_schedule] error. CoachSchedule=[" + coachSchedule.toString() + "]",
//                        -1);
//            }
//        } catch (Exception e) {
//            LOG.error("Exception=[" + ExceptionUtils.getStackTrace(e) + "]");
//            return new ResponseJsonObject(false, e.getMessage(), -1);
//        }
//    }
//
//
//    /**
//     * 返回当前教练所有周历安排
//     *
//     * @param request
//     * @return 1. success:                 [result:true, message: "", id: 1, returnObject: List<CoachSchedule>]
//     * 2. Exception                [result:false, message:exception.message, id:-1, returnObject: null]
//     */
//    @RequestMapping(value = "/schedule/public/list", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject listSchedule(HttpServletRequest request) {
//        try {
//            if (request.getSession().getAttribute(CommonConstants.SESSION_ROLE) == null) {
//                return new ResponseJsonObject(false, "需要登录才能显示周历！");
//            }
//            if (CommonConstants.ROLE_GUEST.equals(request.getSession().getAttribute(CommonConstants.SESSION_ROLE))) {
//                return new ResponseJsonObject(true, Collections.emptyList());
//            }
//
//            String coachId = null;
//
//            //TODO jason4zhu: To test on dev.
//            if (CommonConstants.ROLE_STUDENT.equals(request.getSession().getAttribute(CommonConstants.SESSION_ROLE))) {
//                String studentId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//                LOG.info("JUDKING_DEBUG. studentId=[" + studentId + "]");
//                List<Map> coachByStudentId = studentService.getCoachByStudentId(Integer.parseInt(studentId, 10));
//                if (coachByStudentId.size() == 0) {
//                    return new ResponseJsonObject(true, Collections.emptyList());
//                }
//                LOG.info("JUDKING_DEBUG. coach=[" + coachByStudentId.get(0) + "]");
//                coachId = coachByStudentId.get(0).get("user_id").toString();
//            }
//            if (CommonConstants.ROLE_COACH.equals(request.getSession().getAttribute(CommonConstants.SESSION_ROLE))) {
//                coachId = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID).toString();
//            }
//
//            List<LinkedHashMap> linkedHashMaps = coachScheduleMapper.selectScheduleLists(Integer.parseInt(coachId, 10));
//            return new ResponseJsonObject(true, linkedHashMaps);
//
//        } catch (Exception e) {
//            LOG.error("Exception=[" + ExceptionUtils.getStackTrace(e) + "]");
//            return new ResponseJsonObject(false, e.getMessage(), -1);
//        }
//    }
//
//    @RequestMapping("/coach_calendar")
//    public ModelAndView coach_calendar(HttpServletRequest request) {
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("coach_calendar");
//        return modelAndView;
//    }
//
//    @RequestMapping("/coach_task")
//    public ModelAndView coach_task(HttpServletRequest request) {
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("coach_task");
//        return modelAndView;
//    }
//
//
//    /**
//     * 教练钱包
//     * <p/>
//     * <p/>
//     * <p/>
//     * <p/>
//     * Created by jasonzhu on 7/7/15.
//     */
//    @RequestMapping(value = "/balance/test", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject balanceTest(HttpServletRequest request) {
//        try {
//
//            return new ResponseJsonObject(true);
//
//        } catch (Exception e) {
//            LOG.error("Exception=[" + ExceptionUtils.getStackTrace(e) + "]");
//            return new ResponseJsonObject(false, e.getMessage(), -1);
//        }
//    }
//
//
//    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
//    public String index(HttpServletRequest request) {
//
//        return "coachIndex";
//    }
//
//    @RequestMapping(value = {"/studentlist"}, method = RequestMethod.GET)
//    public String studentlist(HttpServletRequest request) {
//        Object userid = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (userid == null) {
//            return "redirect:/jsp/coachGuide.jsp";
//        }
//        return "coach_studentlist";
//    }
//
//    @RequestMapping(value = "/getStudentInfoList", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getStudentInfoListByCoachId(HttpServletRequest request) {
//        Object coachIdObject = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (coachIdObject == null) {
//            return new ResponseJsonObject(false, "User is not login");
//        }
//        int coachId = Integer.parseInt(coachIdObject.toString());
//        List<StudentQueryInfo> studentInfoList = studentService.getStudentInfoListByCoachId(coachId);
//        //TODO: mazhqa 后续需要判断该学员是否为已付费学员！当前没有区分
//        return new ResponseJsonObject(true, studentInfoList);
//    }
//
//    /**
//     * 获取该登录教练的详细信息
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/getCoachDetail", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getCoachDetail(HttpServletRequest request) {
//        Object coachIdObject = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (coachIdObject == null) {
//            return new ResponseJsonObject(false, "User is not login");
//        }
//        int coachId = Integer.parseInt(coachIdObject.toString());
////        int coachId = 136;
//        return new ResponseJsonObject(true, coachService.getCoachDetailInfoByUserId(coachId));
//    }
//
//    @RequestMapping(value = "/getCoachDisplayDetail", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseJsonObject getCoachDisplayDetail(HttpServletRequest request) {
///*        Object coachIdObject = request.getSession().getAttribute(CommonConstants.SESSION_USER_ID);
//        if (coachIdObject == null) {
//            return new ResponseJsonObject(false, "User is not login");
//        }*/
//        int userId = Integer.parseInt(request.getParameter("userId"));
//        //int userId = Integer.parseInt(coachIdObject.toString());
////        int userId = 1218;
//        CoachPersonalDisplayInfo coachPersonalDisplayInfo =
//                coachService.getCoachPersonalDisplayInfoByUserId(userId);
//        return new ResponseJsonObject(true, coachPersonalDisplayInfo);
//    }

    /**
     * 根据城市的不同，选择查看其对应的所有教练列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCoachDetailByCity", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getCoachDetailByCity(HttpServletRequest request) {
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        List<CoachDisplayDetailInfo> coachDisplayDetailInfos =
                coachService.getCoachDisplayDetailInfoByCity(cityId);
        return new ResponseJsonObject(true, coachDisplayDetailInfos);
    }

    /**
     * 根据教练id得到该教练的详细信息，如果该教练不存在返回null
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCoachDetailByCoachId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject getCoachDetailByCoachId(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        CoachDisplayDetailInfo coachDisplayDetailInfos =
                coachService.getCoachDisplayDetailInfoByCoachId(userId);
        return new ResponseJsonObject(true, coachDisplayDetailInfos);
    }

}
