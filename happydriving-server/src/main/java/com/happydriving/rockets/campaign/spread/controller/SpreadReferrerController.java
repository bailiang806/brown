package com.happydriving.rockets.campaign.spread.controller;

import com.happydriving.rockets.campaign.spread.entity.SpreadReferrer;
import com.happydriving.rockets.campaign.spread.entity.SpreadStudent;
import com.happydriving.rockets.campaign.spread.service.SpreadReferrerService;
import com.happydriving.rockets.campaign.spread.service.SpreadStudentService;
import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.common.json.ResponseJsonObject;
import com.happydriving.rockets.server.component.message.SmsAuthorizedTools;
import com.happydriving.rockets.server.component.tools.HttpRelatedComponent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Controller
@RequestMapping("/campaign/spreadReferrer")
public class SpreadReferrerController {

    public static final int DOWNLOAD_RETRY_TIMES = 3;

    private static final Log LOG = LogFactory.getLog(SpreadReferrerController.class);

    @Autowired
    private SpreadReferrerService spreadReferrerService;

    @Autowired
    private SpreadStudentService spreadStudentService;

    @Autowired
    private SmsAuthorizedTools smsAuthorizedTools;

    @Autowired
    private HttpRelatedComponent httpRelatedComponent;

    @Value("#{drivingConfigProperties.localGenerateCodeLocation}")
    private String localGenerateCodePath;

    public static final String GENERATE_CODE_TEMPLATE =
            "http://qr.liantu.com/api.php?bg=ffffff&fg=3d516b&el=h&w=500&m=10&text=http://www.ejiapei.com?%s&logo=http://www.ejiapei.com/images/ejiapei_300_300_LOGO.jpg";

    /**
     * 注册为推荐人，如果手机号已经被注册过，则注册失败!
     *
     * @param request - 需要参数 phone, name, cityId
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject register(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String name = request.getParameter("name");
        int cityId = Integer.parseInt(request.getParameter("cityId"));


        if (StringUtils.isBlank(phone) || StringUtils.isBlank(name)) {
            return new ResponseJsonObject(false, "用户名和手机号均不能为空!");
        }

        String inputCode = request.getParameter("inputCode");
        if (!smsAuthorizedTools.validateSmsInfo(phone, inputCode)) {
            return new ResponseJsonObject(false, "手机验证码不正确或已经过期!");
        }

        SpreadReferrer spreadReferrerByPhone = spreadReferrerService.getSpreadReferrerByPhone(phone);
        if (spreadReferrerByPhone != null) {
            return new ResponseJsonObject(false, String.format("该手机号: %s 已经被注册!", phone));
        }

        SpreadReferrer spreadReferrer = new SpreadReferrer();
        spreadReferrer.setName(name);
        spreadReferrer.setPhone(phone);
        spreadReferrer.setCityId(cityId);
        spreadReferrer = spreadReferrerService.insertSpreadReferrer(spreadReferrer);

//        String httpUrl = String.format(GENERATE_CODE_TEMPLATE, spreadReferrer.getId());
//        String savedPath = String.format("%s/%s.jpg", localGenerateCodePath, spreadReferrer.getId());
//        try {
//            tryDownloadGeneratedCode(httpUrl, savedPath, DOWNLOAD_RETRY_TIMES);
//        } catch (BusinessException e) {
//            LOG.error(e);
//            ResponseJsonObject responseJsonObject = new ResponseJsonObject(true);
//            responseJsonObject.setMessage(String.format("二维码生成错误: %s ", e.getMessage()));
//            responseJsonObject.setReturnObject(spreadReferrer);
//            return responseJsonObject;
//        }

        return new ResponseJsonObject(true, spreadReferrer);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject login(HttpServletRequest request) {
        String phone = request.getParameter("phone");

        String inputCode = request.getParameter("inputCode");
        if (!smsAuthorizedTools.validateSmsInfo(phone, inputCode)) {
            return new ResponseJsonObject(false, "手机验证码不正确或已经过期!");
        }

        SpreadReferrer spreadReferrer = spreadReferrerService.getSpreadReferrerByPhone(phone);

//        if (spreadReferrer != null) {
//            String savedPath = String.format("%s/%s.jpg", localGenerateCodePath, spreadReferrer.getId());
//            if (!new File(savedPath).exists()) {
//                String httpUrl = String.format(GENERATE_CODE_TEMPLATE, spreadReferrer.getId());
//                try {
//                    tryDownloadGeneratedCode(httpUrl, savedPath, DOWNLOAD_RETRY_TIMES);
//                } catch (BusinessException e) {
//                    LOG.error(e);
//                    ResponseJsonObject responseJsonObject = new ResponseJsonObject(true);
//                    responseJsonObject.setMessage(String.format("二维码生成错误: %s ", e.getMessage()));
//                    responseJsonObject.setReturnObject(spreadReferrer);
//                    return responseJsonObject;
//                }
//            }
//        }

        return spreadReferrer != null ? new ResponseJsonObject(true, spreadReferrer)
                : new ResponseJsonObject(false, String.format("该手机号: %s 并没有被注册!", phone));
    }

    /**
     * 手动重新下载生成的二维码，根据对应的推荐人（如果之前有二维码，则重新生成)
     *
     * @param request
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/manualTryDownloadGenerateCode", method = RequestMethod.GET)
    @ResponseBody
    public ResponseJsonObject manualTryDownloadGenerateCode(HttpServletRequest request) throws BusinessException {
        String referrerId = request.getParameter("referrerId");
        boolean mandatory = Boolean.valueOf(request.getParameter("mandatory"));

        String savedPath = String.format("%s/%s.jpg", localGenerateCodePath, referrerId);
        if (mandatory || !new File(savedPath).exists()) {
            String httpUrl = String.format(GENERATE_CODE_TEMPLATE, referrerId);
            tryDownloadGeneratedCode(httpUrl, savedPath, DOWNLOAD_RETRY_TIMES);
        }

        return new ResponseJsonObject(true);
    }

    private void tryDownloadGeneratedCode(String httpUrl, String savedPath, int maxRetryTimes)
            throws BusinessException {
        int retryTimes = maxRetryTimes;
        while (retryTimes > 0) {
            retryTimes--;
            LOG.info(String.format("开始第: %s 次尝试下载: %s", 4 - retryTimes, httpUrl));
            try {
                File imageFile = new File(savedPath);
                if (imageFile.exists()) {
                    try {
                        FileUtils.forceDelete(imageFile);
                    } catch (IOException e) {
                        throw new BusinessException(String.format("删除原有文件: %s 失败", savedPath), e);
                    }
                }
                httpRelatedComponent.getMethodDownloadFile(httpUrl, savedPath);
                if (imageFile.length() < 38000L) {
                    LOG.error(String.format("下载的文件: %s 大小为: %s (<38000) 需要重试!", httpUrl, imageFile.length()));
                    continue;
                }
                LOG.info(String.format("下载文件: %s 成功！", httpUrl));
                return;
            } catch (BusinessException e) {
                LOG.error(String.format("下载文件: %s 失败，需要重试！", httpUrl), e);
            }
        }
        throw new BusinessException(String.format("下载文件重试 %s 次仍未成功, 请手动重试或联系相关工作人员!", maxRetryTimes));
    }

    @RequestMapping(value = "/mySpreadStudents", method = RequestMethod.POST)
    @ResponseBody
    public ResponseJsonObject getAllSpreadStudents(HttpServletRequest request) {
        int referredId = Integer.parseInt(request.getParameter("referrerId"));
        List<SpreadStudent> spreadStudents = spreadStudentService.getSpreadStudentByReferrerId(referredId);
        return new ResponseJsonObject(true, spreadStudents);
    }


}
