package com.example.wanandroid.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.ColorInt;

/**
 * 字符串处理类
 */
public class StringUtils {
    private static final char[] numChars = {'一', '二', '三', '四', '五', '六', '七', '八', '九'};
    private static final String[] units = {"千", "百", "十", ""};// 个位
    //    private static final String[] bigUnits = {"万", "亿"};
    private static char numZero = '零';
    private static Pattern niceMomoidPattern = Pattern.compile("^[0-9a-zA-Z]+$");

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 判断是否是空白string
     */
    public static boolean isBlank(final CharSequence str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    /**
     * 判断是否全是数字
     * 不要使用{@link android.text.TextUtils#isDigitsOnly(CharSequence)}
     *
     * @return str为null或者length为0或者存在非数字字符, 返回false
     */
    public static boolean isDigitsOnly(CharSequence str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNiceMomoid(CharSequence str) {
        if (str == null) return false;
        Matcher matcher = niceMomoidPattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str
     * @return
     */
    public static boolean notEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 将字符串转换成字符串组数,按照指定的标记进行转换
     */
    public static String[] str2Arr(String value, String tag) {
        if (!isEmpty(value)) {
            return value.split(tag);
        }
        return null;
    }

    /**
     * 将一个字符串数组组合成一个以指定分割符分割的字符串
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, "", 0, array.length);
    }

    /**
     * 将一个字符串数组组合成一个以指定分割符分割的字符串
     */
    public static String join(Object[] array, String ch, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, ch, 0, array.length);
    }

    /**
     * 将一个字符串数组的某一部分组合成一个以指定分割符分割的字符串
     */
    public static String join(Object[] array, String separator, String ch, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }

        // 开始位置大于结束位置
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return "";
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length());

        StringBuilder buf = new StringBuilder(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (array[i] != null) {
                if (i > startIndex) {
                    buf.append(separator);
                }
                buf.append(ch + array[i] + ch);
            }
        }
        return buf.toString();
    }

    /**
     * 将一个集合组合成以指定分割符分割的字符串
     */
    public static String join(Collection collection, String separator) {
        if (collection == null) {
            return "";
        }
        return join(collection.iterator(), separator);
    }

    public static int toInt(String value) {
        int v = 0;
        try {
            v = Integer.parseInt(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    public static float toFloat(String value) {
        float v = 0;
        try {
            v = Float.parseFloat(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    public boolean isIp(String IP) {//判断是否是一个IP
        boolean b = false;
        if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = IP.split("\\.");
            if (Integer.parseInt(s[0]) < 255)
                if (Integer.parseInt(s[1]) < 255)
                    if (Integer.parseInt(s[2]) < 255)
                        if (Integer.parseInt(s[3]) < 255)
                            b = true;
        }
        return b;
    }

    /**
     * 根据迭代器，迭代的元素将组合成以指定分割符分割的字符串
     */
    public static String join(Iterator iterator, String separator) {

        // 空的迭代器，返回 null
        if (iterator == null) {
            return null;
        }
        // 空元素，返回 null
        if (!iterator.hasNext()) {
            return "";
        }

        Object first = iterator.next();
        // 只有一个元素
        if (!iterator.hasNext()) {
            if (first != null) {
                return first.toString();
            } else {
                return "";
            }
        }

        StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }

        return buf.toString();
    }

    public static String md5(String s) {
        if (isEmpty(s)) {
            return "";
        }
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static byte[] md5(byte[] bytes) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
            byte[] messageDigest = digest.digest();
            return messageDigest;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] hash256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            return md.digest();
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] hash256(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);
            return md.digest();
        } catch (Exception e) {
            return null;
        }
    }

    public static String trimBlank(String string) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }

        return string.trim().replaceAll(" ", "");
    }

    public static String removeEmoji(String string) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        return string.trim().replaceAll("([\ue000-\ue5ff])", "");
    }

    /**
     * 一个字符串是否包含另一字符串
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean contains(String str1, String str2) {
        if (isEmpty(str1) && isEmpty(str2)) {
            return true;
        } else if (isEmpty(str1)) {
            return false;
        } else if (isEmpty(str2)) {
            return false;
        } else {
            return (str1).contains(str2) || (str2).contains((str1));
        }
    }

    /**
     * 一个字符串是否包含另一字符串，忽略大小写
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean containsIgnoreCase(String str1, String str2) {
        if (isEmpty(str1) && isEmpty(str2)) {
            return true;
        } else if (isEmpty(str1)) {
            return false;
        } else if (isEmpty(str2)) {
            return false;
        } else {
            str1 = str1.toLowerCase();
            str2 = str2.toLowerCase();
            return (str1).contains(str2) || (str2).contains((str1));
        }
    }

    /**
     * 一个字符串是以另一字符串开头
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean startsWith(String str1, String str2) {
        if (isEmpty(str1) && isEmpty(str2)) {
            return true;
        } else if (isEmpty(str1)) {
            return false;
        } else if (isEmpty(str2)) {
            return false;
        } else {
            return str1.startsWith(str2) || str2.startsWith(str1);
        }
    }

    /**
     * 字符串替换，从头到尾查询一次，替换后的字符串不检查
     *
     * @param str    源字符串
     * @param oldStr 目标字符串
     * @param newStr 替换字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(String str, String oldStr, String newStr) {
        int i = str.indexOf(oldStr);
        while (i != -1) {
            str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
            i = str.indexOf(oldStr, i + newStr.length());
        }
        return str;
    }

    private static char numberCharArab2CN(char onlyArabNumber) {

        if (onlyArabNumber == '0') {
            return numZero;
        }

        if (onlyArabNumber > '0' && onlyArabNumber <= '9') {
            return numChars[onlyArabNumber - '0' - 1];
        }

        return onlyArabNumber;
    }

    /**
     * Getting file name from url without extension
     *
     * @param url string
     * @return file name
     */
    public static String getFileName(String url) {
        if (isEmpty(url)) {
            return "";
        }
        String fileName;
        int slashIndex = url.lastIndexOf('/');
        int qIndex = url.lastIndexOf('?');
        if (qIndex > slashIndex) {//if has parameters
            fileName = url.substring(slashIndex + 1, qIndex);
        } else {
            fileName = url.substring(slashIndex + 1);
        }
        if (fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        }
        return fileName;
    }


    /**
     * 判断字符串是否是以http或https开始
     *
     * @param inputStr
     * @return
     */
    public static boolean isStartWithHttpOrHttps(String inputStr) {
        return isNotEmpty(inputStr) && (inputStr.startsWith("http://") || inputStr.startsWith("https://"));
    }

    /**
     * 获取字符串中第一个中文字符串
     * 比如截取goto中的描述：[附近群组|goto_grouplist|]
     *
     * @param content 待检查字符串
     * @return 中文字符串，没匹配到返回“”
     */
    public static String getChineseStr(String content) {
        String result = "";
        if (isNotBlank(content)) {
            Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                result = matcher.group();
            }
        }
        return result;
    }

    /**
     * 获取字符串长度   汉字按1   其他按0.5
     *
     * @param s
     * @return
     */
    public static double getLength(String s) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
        }
        //进位取整
        return Math.ceil(valueLength);
    }

    /**
     * 获取字符串长度   汉字按1   其他按0.5
     *
     * @param s
     * @return
     */
    public static String getLengthString(String s, double length) {
        if (StringUtils.isEmpty(s)) return "";
        double valueLength = 0;
        String value = s;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
            if (valueLength >= length) {
                if ((i + 1) == s.length()) {
                    value = s.substring(0, i + 1);
                } else {
                    value = s.substring(0, i) + "...";
                }
                break;
            }
        }
        //进位取整
        return value;
    }


    /**
     * 获取字符串长度   汉字按1   其他按0.5
     *
     * @param s
     * @return
     */
    public static String getLengthStringSplit(String s, double length) {
        if (StringUtils.isEmpty(s)) return "";
        double valueLength = 0;
        String value = s;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
            if (valueLength > length) {
                value = s.substring(0, i + 1);
                break;
            }
        }
        //进位取整
        return value;
    }

    /**
     * 获取字符串中的中文字符集合
     *
     * @param content
     * @return
     */
    public static List<String> getChineseStrs(String content) {
        List<String> result = new ArrayList<>();
        if (isNotBlank(content)) {
            Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                for (int i = 0, size = matcher.groupCount(); i < size; i++) {
                    result.add(matcher.group(i));
                }
            }
        }
        return result;
    }


    public static String formatNum(String nums) {
        StringBuilder sb = new StringBuilder();
        int num = 0;

        try {
            num = Integer.parseInt(nums);
        } catch (Exception e) {
            return "0";
        }

//        BigDecimal b0 = new BigDecimal("1000");
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);

        String formatNumStr = "";
        String nuit = "";


        // 以万为单位处理
        if (b3.compareTo(b1) == -1) {
            sb.append(b3.toString());
        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
                || b3.compareTo(b2) == -1) {
            formatNumStr = b3.divide(b1).toString();
            nuit = "万";
        } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            formatNumStr = b3.divide(b2).toString();
            nuit = "亿";
        }
        if (!"".equals(formatNumStr)) {
            int i = formatNumStr.indexOf('.');
            if (i == -1) {
                sb.append(formatNumStr).append(nuit);
            } else {
                i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                if (!v.equals("0")) {
                    sb.append(formatNumStr.substring(0, i + 1)).append(nuit);
                } else {
                    sb.append(formatNumStr.substring(0, i - 1)).append(nuit);
                }
            }
        }
        if (sb.length() == 0)
            return "0";
        return sb.toString();
    }

    public static boolean isEquals(String str1, String str2) {
        return isNotEmpty(str1) && isNotEmpty(str2) && str1.equals(str2);
    }


    public static String filterEmoji(String source) {


        source = source.replace(" ", "");
        //到这里铁定包含
        StringBuilder buf = new StringBuilder();

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            Pattern p = Pattern.compile("[0-9|a-zA-Z|\u4e00-\u9fa5]+");
            Matcher m = p.matcher(String.valueOf(codePoint));
            if (m.matches()) {
                buf.append(codePoint);
            }
        }

        return buf.toString();

    }

    public static String getPersonValues(long value) {
        if (value < 10_0000) {
            return value + "";
        } else {
            long personValue = value / 100;
            long wanValue = value / 1_0000;
            long qianValue = personValue / 10 % 10;
            long baiValue = personValue % 10;
            StringBuilder sb = new StringBuilder();
            sb.append(wanValue);
            sb.append(".");
            sb.append(qianValue);
            sb.append(baiValue);
            sb.append("万");
            return sb.toString();

        }

    }

    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }

        int len = source.length();

        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);

            if (isEmojiCharacter(codePoint)) {
                //do nothing，判断到了这里表明，确认有表情字符
                return true;
            }
        }

        return false;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    public static String subZeroAndDot(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }
        if (s.indexOf('.') > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static SpannableString highlight(String text, String target, @ColorInt int color) {

        SpannableString spannableString = new SpannableString(text);

        Pattern pattern = Pattern.compile(target);

        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {

            ForegroundColorSpan span = new ForegroundColorSpan(color);

            spannableString.setSpan(span, matcher.start(), matcher.end(),

                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        return spannableString;

    }

    public static String formateTimer(Long millis) {
        Long ms = millis / 1000;
        Long MM = ms / 60;
        Long ss = ms % 60;
        String MMStr = (MM < 10 ? "0" : "") + MM;
        String ssStr = (ss < 10 ? "0" : "") + ss;
        return MMStr + ":" + ssStr;
    }

}
