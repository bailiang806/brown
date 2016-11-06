package com.happydriving.rockets.server.service;

import com.happydriving.rockets.server.entity.BankInfo;
import com.happydriving.rockets.server.entity.BankInfoExample;
import com.happydriving.rockets.server.mapper.BankInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mazhiqiang
 */
@Service
public class BankService {

    @Resource
    private BankInfoMapper bankInfoMapper;

    public List<BankInfo> getBankInfos() {
        return bankInfoMapper.selectByExample(new BankInfoExample());
    }

}
