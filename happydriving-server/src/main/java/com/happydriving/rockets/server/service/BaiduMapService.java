package com.happydriving.rockets.server.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.happydriving.rockets.server.common.BusinessException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http://developer.baidu.com/map/index.php?title=webapi/guide/webservice-geocoding
 *
 * @author mazhiqiang
 */
@Service
public class BaiduMapService {

    public static final Logger LOG = LoggerFactory.getLogger(BaiduMapService.class);

    /**
     * 注册为百度开发者的时候可以使用
     */
    public static final String AK = "2bf7a691d99ab7b08a3ec667d48bf22d";

    /**
     * 根据详细地址，以及城市名称得到对应的Location
     *
     * @param address
     * @param city
     * @return
     * @throws BusinessException
     */
    public BaiduMapLocation getLocation(String address, String city) throws BusinessException {

        String url = String.format(
                "http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=%s", address, AK);
        if (StringUtils.isNotBlank(city)) {
            url += String.format("&city=%s", city);
        }

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            String returnJson = IOUtils.toString(con.getInputStream());
            JSONObject jsonObject = (JSONObject) JSON.parse(returnJson);
            int status = jsonObject.getIntValue("status");
            if (status == 0) {
                JSONObject result = jsonObject.getJSONObject("result");
                JSONObject location = result.getJSONObject("location");
                return new BaiduMapLocation(location.getFloatValue("lat"), location.getFloatValue("lng"),
                        result.getIntValue("precise"), result.getIntValue("confidence"),
                        result.getString("level"));
            } else {
                String message = jsonObject.getString("msg");
                throw new BusinessException(message);
            }
        } catch (IOException e) {
            throw new BusinessException(e);
        } finally {
            IOUtils.close(con);
        }
    }

    /**
     * 根据经纬度逆编码得到对应的城市信息，用于定位服务的城市
     *
     * 如果成功，返回的json字符串类型下面：
     * renderReverse&&renderReverse({"status":0,"result":{"location":{"lng":116.32298703399,"lat":39.983424051248},"formatted_address":"北京市海淀区中关村大街27号1101-08室","business":"中关村,人民大学,苏州街","addressComponent":{"city":"北京市","country":"中国","direction":"附近","distance":"7","district":"海淀区","province":"北京市","street":"中关村大街","street_number":"27号1101-08室","country_code":0},"poiRegions":[],"sematic_description":"北京远景国际公寓(中关村店)内0米","cityCode":131}})
     *
     * 最终从该返回的json字符串中得到其 result - address_component - city 字段
     *
     * @param lag － 经度，此经度和纬度可以用从微信公众平台中直接获取到的信息
     * @param lng － 纬度
     * @return - 城市
     * @throws BusinessException
     */
    public String getCityFromLocation(float lag, float lng) throws BusinessException {
        String url = String.format(
                "http://api.map.baidu.com/geocoder/v2/?ak=%s&callback=renderReverse&location=%s,%s&output=json&pois=0",
                AK, lag, lng);
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            String jsonpString = IOUtils.toString(con.getInputStream());
            String jsonString = jsonpString.substring(0, jsonpString.length() - 1).replaceAll(
                    "renderReverse&&renderReverse\\(", "").replaceAll("renderReverse&&renderReverse\\(", "");
            JSONObject jsonObject = (JSONObject)JSON.parse(jsonString);
            int status = jsonObject.getIntValue("status");
            if (status == 0) {
                JSONObject successJsonObject = jsonObject.getJSONObject("result");
                JSONObject addressComponentJsonObject = successJsonObject.getJSONObject("addressComponent");
                return addressComponentJsonObject.getString("city");
            } else {
                String message = jsonObject.getString("msg");
                throw new BusinessException(message);
            }
        } catch (IOException e) {
            throw new BusinessException(e);
        }

    }

    /**
     * 在百度地图中的地理位置信息
     */
    public static class BaiduMapLocation {
        /**
         * 纬度值
         */
        private final float lag;
        /**
         * 经度值
         */
        private final float lng;

        /**
         * 位置的附加信息，是否精确查找。1为精确查找，0为不精确。
         */
        private final int precise;

        /**
         * 可信度
         */
        private final int confidence;

        /**
         * 地址类型
         */
        private final String level;

        public BaiduMapLocation(float lag, float lng, int precise, int confidence, String level) {
            this.lag = lag;
            this.lng = lng;
            this.precise = precise;
            this.confidence = confidence;
            this.level = level;
        }

        public float getLag() {
            return lag;
        }

        public float getLng() {
            return lng;
        }

        public int getPrecise() {
            return precise;
        }

        public int getConfidence() {
            return confidence;
        }

        public String getLevel() {
            return level;
        }

        @Override
        public String toString() {
            return "BaiduMapLocation{" +
                    "lag=" + lag +
                    ", lng=" + lng +
                    ", precise=" + precise +
                    ", confidence=" + confidence +
                    ", level='" + level + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) throws BusinessException {
        BaiduMapService service = new BaiduMapService();
//        BaiduMapLocation location = service.getLocation("闽峰驾校", "福州市");
//        System.out.println(location);

        String cityFromLocation = service.getCityFromLocation(26.155943f, 119.298126f);
        System.out.println(cityFromLocation);
    }


}
