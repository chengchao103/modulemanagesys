package com.chinamobile.iot.utils;

/**
 * Created by dds on 2016/6/13.
 */
public class StringUtils {
    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values)
    {
        boolean result = true;
        if (values == null || values.length == 0)
        {
            result = false;
        }
        else
        {
            for (String value : values)
            {
                result &= !isEmpty(value);
            }
        }
        return result;
    }
    /**
     * 检查指定的字符串是否不为空。
     */
    public static boolean isEmpty(String value)
    {
        int strLen;
        if (value == null || (strLen = value.length()) == 0)
        {
            return true;
        }
        for (int i = 0; i < strLen; i++)
        {
            if ((Character.isWhitespace(value.charAt(i)) == false))
            {
                return false;
            }
        }
        return true;
    }
}
