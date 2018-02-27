
package com.rbs.cn.tools.valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式验证工具类
 * @author
 */
public class PatternValidUtils {
    private static Logger logger = LoggerFactory.getLogger(PatternValidUtils.class);
    // yyyyMMddhhmmss
    private static String DATE_TIME_REGEX_1 = "^[1-9]\\d{3}(0?[1-9]|1[0-2])(0?[1-9]|[12]\\d|3[01])(0?[1-9]|1\\d|2[0-3])((0?[1-9]|[1-5]\\d)){2}$";

    // 验证YYYY-MM-DD 00:00:00
    private static String DATE_TIME_REGEX_2 = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";

    // 验证YYYY-MM-DD
    private static String DATE_REGEX = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";

    // 验证手机号码
    private static String PHONE_REGEX1 = "^[1][3,4,5,6,7,8,9][0-9]{9}$";

    // 验证固定电话
    private static String PHONE_REGEX2 = "^([0][1-9][0-9]{1,2}(-)?)?[2-9][0-9]{6,7}$";

    /**
     * 验证字符串是否日期或者日期时间,支持三种格式的验证：<br/>
     * <ul>
     *     <li>yyyyMMddhhmmss</li>
     *     <li>yyyy-MM-dd hh:mm:ss</li>
     *     <li>yyyy-MM-dd</li>
     * </ul>
     * @param str 要验证的字符串
     * @return 是否为日期/日期时间
     */
    public static boolean isDate(String str) {
        if (matchReg(DATE_TIME_REGEX_1, str) || matchReg(DATE_TIME_REGEX_2, str) || matchReg(DATE_REGEX, str))
            return true;
        else
            return false;
    }

    /**
     * 验证是否为电话号码（包括固定电话和手机号码）
     * @param str 要验证的字符串
     * @return 是否为电话号码
     */
    public static boolean isPhoneNo(String str){
        if(matchReg(PHONE_REGEX1, str) || matchReg(PHONE_REGEX2, str)){
            return true;
        }
        return false;
    }


    /**
     *
     * @param regex
     * @param str
     * @return
     */
    private static boolean matchReg(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
