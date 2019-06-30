package com.wfq.manager.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 */
@Slf4j
public class MyUtil {

    /**
     * 获取当前网络ip
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("获取IP地址异常:", e.getMessage());
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

//    /**
//     * hash计算获取表名
//     *
//     * @param tableName 表名
//     * @param code      hash字段
//     * @param count     表总数
//     * @return
//     */
//    public static String getTableName(String tableName, String code, int count) {
//        int hash = HashUtil.FNVHash(code);
//        int i = hash % count;
//        return tableName + "_" + (i + 1);
//    }

    /**
     * properties文件转换成Properties对象
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static Properties getProp(String path) throws Exception {
        Properties prop = new Properties();
        if (path.startsWith("http://")) {
            //new一个URL对象
            URL url = new URL(path);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            prop.load(inStream);
        } else {
            FileInputStream input = new FileInputStream(path);
            //Properties是Map的子类，以put方式装进prop
            prop.load(input);
        }

        return prop;
    }

    /**
     * yaml文件换成成map
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getYaml(String path) throws Exception {
        Yaml yaml = new Yaml();
        Map<String, Object> map = new HashMap<String, Object>();
        if (path.startsWith("http://")) {
            //new一个URL对象
            URL url = new URL(path);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            map = yaml.loadAs(inStream, HashMap.class);
        } else {
            FileInputStream input = new FileInputStream(path);
            map = yaml.loadAs(input, HashMap.class);
        }

        return map;
    }

    /**
     * 转换驼峰命名
     *
     * @param name
     * @return
     */
    public static String covertTfName(String name) {
        if (StringUtils.indexOf(name, "-") != -1) {
            String res = "";
            String[] names = StringUtils.split(name, "-");
            for (String s : names) {
                res += StringUtils.substring(s, 0, 1).toUpperCase() + StringUtils.substring(s, 1);
            }
            return res;
        }
        return StringUtils.substring(name, 0, 1).toUpperCase() + StringUtils.substring(name, 1);
    }

    /**
     * 生成名字（中文）
     * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
     * @date 2019/3/25 20:31
     */
    public static String getRandomJianHan(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            // 定义高低位
            int hightPos, lowPos;
            Random random = new Random();
            // 获取高位值
            hightPos = (176 + Math.abs(random.nextInt(39)));
            // 获取低位值
            lowPos = (161 + Math.abs(random.nextInt(93)));
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                // 转成中文
                str = new String(b, "GBK");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    /**
     * 生成随机用户名，数字和字母组成,
     * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
     * @date 2019/3/25 20:31
     */
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 解析地址信息
     * @param address
     * @return
     */
    public static Map<String, String> addressResolution(String address) {
        //正则验证表达式
        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)?" +
                "(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)" +
                "(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)" +
                "?(?<town>[^区]+区|.+镇)?" +
                "(?<village>.*)";
        //Pattern创建正则，matcher验证正则
        Matcher m = Pattern.compile(regex).matcher(address);
        //接收对象
        String province = null;
        String city = null;
        String county = null;
        String town = null;
        String village = null;
        Map<String, String> map = null;
        //find方法：在目标字符串里查找下一个匹配子串。如果匹配成功，则可以通过 start、end 和 group 方法获取更多信息。
        while (m.find()) {
            map = new LinkedHashMap<String, String>();
            //group方法：返回当前查找而获得的与组匹配的所有子串内容。
            province = m.group("province");
            map.put("province", province == null ? "" : province.trim());
            city = m.group("city");
            map.put("city", city == null ? "" : city.trim());
            county = m.group("county");
            map.put("county", county == null ? "" : county.trim());
            town = m.group("town");
            map.put("town", town == null ? "" : town.trim());
            village = m.group("village");
            map.put("village", village == null ? "" : village.trim());
        }
        return map;
    }
}
