package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.entity.CoachAuditInfo;
import com.happydriving.rockets.server.entity.CoachAuditInfoExample;
import com.happydriving.rockets.server.mapper.CoachAuditInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Use this for coach administrator service related.
 * @author mazhiqiang
 */
@Service
public class CoachAdminService {

    @Resource
    private CoachAuditInfoMapper coachAuditInfoMapper;

    @Autowired
    private CoachService coachService;

    public List<CoachAuditInfo> getCoachAuditInfosByCoachId(int coachId) {
        CoachAuditInfoExample example = new CoachAuditInfoExample();
        example.createCriteria().andUserIdEqualTo(coachId);
        example.setOrderByClause("audit_at");
        return coachAuditInfoMapper.selectByExample(example);
    }

    public CoachAuditInfo getLatestCoachAuditInfo(int coachId) {
        CoachAuditInfoExample example = new CoachAuditInfoExample();
        example.createCriteria().andUserIdEqualTo(coachId);
        example.setOrderByClause("audit_at desc");
        List<CoachAuditInfo> coachAuditInfos = coachAuditInfoMapper.selectByExample(example);
        return coachAuditInfos.isEmpty() ? null : coachAuditInfos.get(0);
    }

//    public void addCoachAuditInfo(CoachAuditInfo coachAuditInfo) throws BusinessException {
//        coachAuditInfoMapper.insert(coachAuditInfo);
//        CoachBasicInfos coachBasicInfo = coachService.getSingleCoachBasicInfo(coachAuditInfo.getUserId());
//        coachBasicInfo.setShenhe(coachAuditInfo.getAuditStatus());
//        coachService.insertUpdateCoachBasicInfo(coachBasicInfo);
//    }

}
