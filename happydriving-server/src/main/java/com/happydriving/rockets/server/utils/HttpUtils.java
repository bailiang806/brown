package com.happydriving.rockets.server.utils;

import com.happydriving.rockets.server.service.PublicService;
import com.happydriving.rockets.server.service.WeixinPaymentService;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * 所有与Http请求，参数格式转换相关的服务类
 *
 * Created by jasonzhu on 9/7/15.
 */
@Service
public class HttpUtils {

    @Autowired
    PublicService publicService;
    private static final Log logger = LogFactory.getLog(WeixinPaymentService.class);


    /**
     * 从map生成对应的xml
     *
     * @param items
     * @return
     */
    public String mapToXml(Map<String, String> items)   {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, "<xml>\n");

        Map<String, String> sortedByKeyMap = new TreeMap<String, String>(items);
        for(Map.Entry<String, String> entry : sortedByKeyMap.entrySet()) {
            sb.append(createElementInXml(entry.getKey(), entry.getValue())).append("\n");
        }

        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 生成xml中的项
     *
     * @param key
     * @param value
     * @return
     */
    private String createElementInXml(String key, String value)    {
        if(publicService.isNumeric(value)) {
            return String.format("<%s>%s</%s>", key, value, key);
        }
        else    {
            return String.format("<%s><![CDATA[%s]]></%s>", key, value, key);
        }
    }


    /**
     * xml转Map
     *
     * @param xml xml字符串，其中可以带CDATA。样例：
     *             <xml><appid><![CDATA[wx1e810861e20c0c30]]></appid>
    <attach><![CDATA[附加信息——test]]></attach>
    <bank_type><![CDATA[CFT]]></bank_type>
    <cash_fee><![CDATA[1]]></cash_fee>
    <fee_type><![CDATA[CNY]]></fee_type>
    <is_subscribe><![CDATA[Y]]></is_subscribe>
    <mch_id><![CDATA[1242315302]]></mch_id>
    <nonce_str><![CDATA[2m36jq7u9z7k5qs5b8ppegpmqin8nbvt]]></nonce_str>
    <openid><![CDATA[ogGCluNRaxBTNFWZzS_kH-rRez_Q]]></openid>
    <out_trade_no><![CDATA[nraxbtnfwzzskhrrezq1435741343940]]></out_trade_no>
    <result_code><![CDATA[SUCCESS]]></result_code>
    <return_code><![CDATA[SUCCESS]]></return_code>
    <sign><![CDATA[2971EF8227D6EFBA85DDC39D069E0BAB]]></sign>
    <time_end><![CDATA[20150701170252]]></time_end>
    <total_fee>1</total_fee>
    <trade_type><![CDATA[JSAPI]]></trade_type>
    <transaction_id><![CDATA[1009750415201507010331921517]]></transaction_id>
    </xml>
     *
     * @return
     */
    public Map<String, String> xmlToMap(String xml) throws ParserConfigurationException, IOException, SAXException {

        InputStream is = new ByteArrayInputStream(xml.getBytes());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(is);
        return createMap(document.getDocumentElement());
    }

    private Map<String, String> createMap(Node node) {
        Map<String, String> map = new HashMap<String, String>();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.hasAttributes()) {
                for (int j = 0; j < currentNode.getAttributes().getLength(); j++) {
                    Node item = currentNode.getAttributes().item(i);
                    map.put(item.getNodeName(), item.getTextContent());
                }
            }
            if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == Node.ELEMENT_NODE) {
                map.putAll(createMap(currentNode));
            } else if (node.getFirstChild().getNodeType() == Node.TEXT_NODE || node.getFirstChild().getNodeType() == Node.CDATA_SECTION_NODE) {
                map.put(node.getLocalName(), node.getTextContent());
            }
        }
        return map;
    }

    /**
     * post一段xml数据到目标url
     *
     * @param url 目标url
     * @param xmlRequestData xml字符串
     * @return 返回的字符串内容
     */
    public String postXml(String url, String xmlRequestData) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity se = new StringEntity(xmlRequestData, CharEncoding.UTF_8);
        se.setContentType("application/xml");
        post.setEntity(se);

        HttpResponse response = client.execute(post);
//        System.out.println("\nSending 'POST' request to URL : " + api);
//        System.out.println("Post parameters : " + post.getEntity());
//        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), CharEncoding.UTF_8));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }


    /**
     * post一段json数据到目标url
     *
     * @param url 目标url
     * @param jsonRequestData json字符串
     * @return 返回的字符串内容
     */
    public String postJson(String url, String jsonRequestData) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity se = new StringEntity(jsonRequestData, CharEncoding.UTF_8);
        se.setContentType("application/json");
        post.setEntity(se);

        HttpResponse response = client.execute(post);
//        System.out.println("\nSending 'POST' request to URL : " + api);
//        System.out.println("Post parameters : " + post.getEntity());
//        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), CharEncoding.UTF_8));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

}
