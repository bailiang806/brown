package com.happydriving.rockets.campaign.spread.service;

import com.happydriving.rockets.campaign.spread.entity.SpreadAdmin;
import com.happydriving.rockets.campaign.spread.entity.SpreadAdminExample;
import com.happydriving.rockets.campaign.spread.mapper.SpreadAdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Service
public class SpreadAdminService {

    @Resource
    private SpreadAdminMapper spreadAdminMapper;

    public SpreadAdmin getSpreadAdminByPhone(String phone) {
        SpreadAdminExample example = new SpreadAdminExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<SpreadAdmin> spreadAdmins = spreadAdminMapper.selectByExample(example);
        return spreadAdmins.isEmpty() ? null : spreadAdmins.get(0);
    }
}
