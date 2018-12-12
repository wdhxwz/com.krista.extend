package com.krista.extend.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 签名工具类
 *
 * @author lijianwei
 * @date 2016-11-25
 */
public class SignUtil {

    private static Logger LOG = LoggerFactory.getLogger(SignUtil.class);
    private static final String SIGN_FIELD = "sign";

    /**
     * 获取签名
     *
     * @param param
     * @return
     */
    public static String getSign(Map<String, String> param, String signKey) {
        Map<String, String> filterMap = paraFilter(param);
        String srcString = createLinkString(filterMap);
        return EncryptUtil.toMd5LowerCase(srcString + "&sysKey=" + signKey);
    }

    /**
     * 验证签名
     *
     * @param
     */
    public static void verifySign(String sign, String key, Map<String, String> param) throws Exception {
        String serverSign = getSign(param, key);
        if (!serverSign.equalsIgnoreCase(sign)) {
            LOG.info("oringin sign:" + sign);
            LOG.info("caculate sign:" + serverSign);
            LOG.info("key:" + key);

            throw new Exception("签名[sign]不配");
        }
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder("");
        for (String key : keys) {
            String value = params.get(key);
            stringBuilder = stringBuilder.append(key).append("=").append(value).append("&");
        }

        return stringBuilder.toString().substring(0, stringBuilder.length() - 1);
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>(sArray.size());
        if (MapUtils.isEmpty(sArray)) {
            return result;
        }
        sArray.forEach((key, value) -> {
            if (StringUtils.isNotEmpty(value) && !SIGN_FIELD.equalsIgnoreCase(key)) {
                result.put(key, value);
            }
        });

        return result;
    }

    /**
     * 获取请求参数的map对象
     *
     * @param request
     * @return
     */
    public static Map<String, String> getParam(HttpServletRequest request) {
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<String, String>(requestParams.size());

        requestParams.forEach((key, value) -> {
            String valueStr = "";
            for (int index = 0; index < value.length; index++) {
                valueStr = (index == value.length - 1) ? valueStr + value[index]
                        : valueStr + value[index] + ",";
            }
            params.put(key, valueStr);
        });

        return params;
    }

    public static String getSign(String key, String... singParam) {
        StringBuilder sb = new StringBuilder();
        if (singParam != null) {
            for (String param : singParam) {
                sb.append(param);
            }
        }
        return EncryptUtil.toMd5LowerCase(sb.toString() + key);
    }

    public static void main(String[] args) {
        String aa = String.valueOf(System.currentTimeMillis());
        System.out.println(aa);
        Map<String, String> params = new HashMap<String, String>();
        params.put("reqId", "ownSystem123456ss");
        params.put("sysId", "ownSystem");
        params.put("yyuid", "231513412");
        params.put("point", "100");
        params.put("sourceId", "1");
        params.put("sysTime", aa);

        Map<String, String> map = new HashMap<>();
        map.put("sysId", "yy");
        map.put("yyuid", "231513412");
        map.put("sysTime", aa);
        System.out.println(getSign(map, "6ddeffe3e0394eff9796b2488101ca41"));

        String servSign = getSign(params, "6ddeffe3e0394eff9796b2488101ca41");
        System.out.println(servSign);
        //reqId=ownSystem123456ss&sysId=ownSystem&yyuid=231513412&point=100&sourceId=1&sysTime=1492756877853&sign=45946482af837eaf2d90c174a79cc87f
    }
}
