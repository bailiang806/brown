package com.happydriving.rockets.campaign.spread.service;

import com.happydriving.rockets.campaign.spread.entity.SpreadReferrer;
import com.happydriving.rockets.campaign.spread.entity.SpreadReferrerExample;
import com.happydriving.rockets.campaign.spread.mapper.SpreadReferrerMapper;
import com.happydriving.rockets.campaign.spread.model.CityStatisticInfo;
import com.happydriving.rockets.campaign.spread.model.SpreadReferrerStatisticInfo;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author mazhiqiang
 */
@Service
public class SpreadReferrerService {

    @Resource
    private SpreadReferrerMapper spreadReferrerMapper;

    public SpreadReferrer getSpreadReferrerByPhone(String phone){
        SpreadReferrerExample example = new SpreadReferrerExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<SpreadReferrer> spreadReferrers = spreadReferrerMapper.selectByExample(example);
        return spreadReferrers.isEmpty() ? null : spreadReferrers.get(0);
    }

    public SpreadReferrer insertSpreadReferrer(SpreadReferrer spreadReferrer) {
        spreadReferrer.setCreatedAt(new Date());
        spreadReferrerMapper.insertAndGetId(spreadReferrer);
        return spreadReferrer;
    }

    public List<SpreadReferrerStatisticInfo> getSpreadReferrerStatisticInfoByCityId(int cityId) {
        return spreadReferrerMapper.getSpreadReferrerStatisticInfoByCityId(cityId);
    }

    public List<CityStatisticInfo> getSpreadCityStatisticInfos() {
        List<Map> statisticCityReferrers = spreadReferrerMapper.getStatisticCityReferrerCount();
        Map<Integer, CityStatisticInfo> cityIdToStatisticInfos = new HashMap<>();
        for (Map statisticCityReferrer : statisticCityReferrers) {
            int cityId = (int) statisticCityReferrer.get("cityId");
            String cityName = (String) statisticCityReferrer.get("cityName");
            long referrerCount = (long) statisticCityReferrer.get("referrerCount");
            long totalStudentCount = (long) statisticCityReferrer.get("totalStudentCount");
            long transferStudentCount = statisticCityReferrer.get("transferStudentCount") == null ? 0L :
                    ((BigDecimal) statisticCityReferrer.get("transferStudentCount")).longValue();

            CityStatisticInfo cityStatisticInfo = new CityStatisticInfo();
            cityStatisticInfo.setCityId(cityId);
            cityStatisticInfo.setCityName(cityName);
            cityStatisticInfo.setReferrerCount(referrerCount);
            cityStatisticInfo.setTotalStudentCount(totalStudentCount);
            cityStatisticInfo.setTransferStudentCount(transferStudentCount);

            cityIdToStatisticInfos.put(cityId, cityStatisticInfo);
        }

        Date currentDate = new Date();
        DateTime yesterdayDateTime = DateTime.now().plusDays(-1);
        Date yesterdayDate = new DateTime(
                yesterdayDateTime.getYear(),
                yesterdayDateTime.getMonthOfYear(),
                yesterdayDateTime.getDayOfMonth(),
                23,
                59, 59).toDate();
        List<Map> dailyCityTransferCount = spreadReferrerMapper.getDailyCityTransferCount(yesterdayDate, currentDate);
        for (Map todayTransfer : dailyCityTransferCount) {
            int cityId = (int) todayTransfer.get("cityId");
            long transferCount = todayTransfer.get("transferCount") == null ? 0L :
                    ((BigDecimal) todayTransfer.get("transferCount")).longValue();

            cityIdToStatisticInfos.get(cityId).setTodayTransferCount(transferCount);
        }

        DateTime beforeYesterdayDateTime = DateTime.now().plusDays(-2);
        Date beforeYesterdayDate = new DateTime(beforeYesterdayDateTime.getYear(),
                beforeYesterdayDateTime.getMonthOfYear(),
                beforeYesterdayDateTime.getDayOfMonth(),
                23,
                59, 59).toDate();
        List<Map> yesterdayDailyCityTransferCount =
                spreadReferrerMapper.getDailyCityTransferCount(beforeYesterdayDate, yesterdayDate);
        for (Map todayTransfer : yesterdayDailyCityTransferCount) {
            int cityId = (int) todayTransfer.get("cityId");
            long transferCount = todayTransfer.get("transferCount") == null ? 0L :
                    ((BigDecimal)todayTransfer.get("transferCount")).longValue();

            cityIdToStatisticInfos.get(cityId).setYesterdayTransferCount(transferCount);
        }
        return new ArrayList<>(cityIdToStatisticInfos.values());
    }

}
