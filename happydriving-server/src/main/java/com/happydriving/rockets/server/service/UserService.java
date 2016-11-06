package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.common.BusinessException;
import com.happydriving.rockets.server.entity.User;
import com.happydriving.rockets.server.entity.UserBridge;
import com.happydriving.rockets.server.entity.UserBridgeExample;
import com.happydriving.rockets.server.entity.UserExample;
import com.happydriving.rockets.server.mapper.CoachMapper;
import com.happydriving.rockets.server.mapper.StudentInfoMapper;
import com.happydriving.rockets.server.mapper.UserBridgeMapper;
import com.happydriving.rockets.server.mapper.UserMapper;
import com.happydriving.rockets.server.utils.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mazhiqiang
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CoachMapper coachMapper;

    @Resource
    private StudentInfoMapper studentInfoMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CoachService coachService;

    @Autowired
    private WeixinOauthService weixinOauthService;

    @Autowired
    private UserBridgeMapper userBridgeMapper;

    public User getUserById(int userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phoneNumber);
        List<User> userList = userMapper.selectByExample(example);
        return userList.size() == 1 ? userList.get(0) : null;
    }

    public boolean isUserRegistered(String phoneNumber) throws BusinessException {
        return getUserByPhoneNumber(phoneNumber) != null;
    }

    /**
     * 向用户表中插入手机号码和角色，同时向coach或student表中插入相应关联记录（主要属性都设置为空），如果openId不为空，关联openId和手机号码
     *
     * @param phone  - 手机号码
     * @param role   - 角色: coach student
     * @param openId - 微信回调的openId, 如果该openId为null，则不进行关联openId
     * @return - 已经插入的用户记录，带主键
     * @throws BusinessException
     */
    public User insertUserAndOpenId(String phone, String role, Object openId) throws BusinessException {
        if (isUserRegistered(phone)) {
            throw new BusinessException(String.format("该手机号 : %s 已经被注册!", phone));
        }
        User user = insertUser(phone, role);
        if (openId != null) {
            insertUpdateUserBridge(openId.toString(), phone);
        }
        return user;
    }

    /**
     * 向用户表user中插入手机号码和相应的角色，同时向coach或student表中插入相应关联记录（主要属性都设置为空）
     *
     * @param phone - 手机号码
     * @param role  - 角色: coach student
     * @return - 已经插入的用户记录，带主键
     * @throws BusinessException
     */
    public User insertUser(String phone, String role)
            throws BusinessException {
        User user = new User();
        user.setPhone(phone);
        user.setRole(role);
        //just ignore password and decrypt gen-salt
//        String gensalt = BCrypt.gensalt(CommonConstants.BCRYPT_SALT_ROUND);
//        String decryptPassword = BCrypt.hashpw(password, gensalt);
//        coach.setPasswordDigest(decryptPassword);
//        coach.setRememberToken(gensalt);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userMapper.insertAndGetId(user);
        if (CommonConstants.ROLE_COACH.equals(role)) {
            coachService.insertCoach(user.getId());
        } else {
            studentService.insertStudent(user.getId());
        }
        return user;
    }

    /**
     * 向微信关联表中插入openId phone的记录，user_bridge表中openId是唯一的，
     * <p/>
     * 如果openId已经关联了phone，则update该手机字段
     *
     * @param openId
     * @param phone
     */
    public void insertUpdateUserBridge(String openId, String phone) {
        UserBridge userBridge = getUserBridgeByOpenId(openId);
        if (userBridge != null) {
            userBridge.setPhone(phone);
            userBridge.setUpdatedAt(new Date());
            userBridgeMapper.updateByPrimaryKey(userBridge);
        } else {
            userBridge = new UserBridge();
            userBridge.setOpenId(openId);
            userBridge.setPhone(phone);
            userBridge.setCreatedAt(new Date());
            userBridge.setUpdatedAt(new Date());
            userBridgeMapper.insert(userBridge);
        }
    }

    private UserBridge getUserBridgeByOpenId(String openId) {
        UserBridgeExample userBridgeExample = new UserBridgeExample();
        userBridgeExample.createCriteria().andOpenIdEqualTo(openId);
        List<UserBridge> userBridges = userBridgeMapper.selectByExample(userBridgeExample);
        return userBridges.isEmpty() ? null : userBridges.get(0);
    }

    /**
     * 根据openId获取用户信息，返回内容包括：
     * <p/>
     * openId   = [openId]
     * userType =[guest|coach|student]
     * userId   = userId
     * phone    = [phone]
     * <p/>
     * E.G.返回的Map内容
     * openId=ogGCluDahhjSpXgaj9IrGM34fqyg
     * userType=student
     * userId=42
     * phone=15201278248
     *
     * @param openId
     * @return
     */
    public Map<String, String> getIdentity(String openId) {
        Map<String, String> rtn = new LinkedHashMap<>(3);
        rtn.put("openId", openId);

        //查看userBridge表是否存在对应的openId
        UserBridgeExample userBridgeExample = new UserBridgeExample();
        userBridgeExample.createCriteria().andOpenIdEqualTo(openId);
        List<UserBridge> userBridges = userBridgeMapper.selectByExample(userBridgeExample);
        if (userBridges.size() > 0) {
            //存在的话，查看这个phone是否在student/coach表又关联的用户
            String phone = userBridges.get(0).getPhone();

            User user = getUserByPhoneNumber(phone);
            if (user != null) {
                rtn.put("userType", user.getRole());
                rtn.put("userId", Integer.toString(user.getId()));
                rtn.put("phone", phone);
                return rtn;
            }
        }
        //--以guest身份返回--
        rtn.put("userType", "guest");
        rtn.put("phone", "");
        return rtn;
    }

    /**
     * @param pics
     * @param type
     * @param userId
     */
    public void updatePersonalTest(String pics, String type, String userId) {
        if (CommonConstants.ROLE_STUDENT.equalsIgnoreCase(type)) {
            studentInfoMapper.updatePersonalTest(pics, userId);
        }
        if (CommonConstants.ROLE_COACH.equalsIgnoreCase(type)) {
            coachMapper.updatePersonalTest(pics, userId);
        }
    }
}
