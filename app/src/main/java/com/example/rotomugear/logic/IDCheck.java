package com.example.rotomugear.logic;

import java.util.Calendar;

public class IDCheck {

    //1.验证身份证格式的正确性
    //判断字符串是否为18位
    public static boolean checkLength(String idNum){
        int length = idNum.length();

        if (length == 18){
            return true;
        }else{
            return false;
        }
    }

    //判断字符串的数据类型是否正确
    public static boolean checkType(String idNum){
        boolean flag = true;

        String front = idNum.substring(0, 17);
        char[] frontChar = front.toCharArray();
        for (char c: frontChar){
            if (!Character.isDigit(c)){
                flag = false;
            }
        }

        char[] totalChar = idNum.toCharArray();
        char lastChar = totalChar[17];
        if (!(lastChar=='X') && !(Character.isDigit(lastChar))){
            flag = false;
        }

        return flag;
    }

    //2.获取和检验出生信息
    //获取出生年份
    public static String resolveYear(String idNum){
        String year = idNum.substring(6, 10);
        return year;
    }
    //判断出生年份的正确性
    public static boolean checkYear(String idNum){
        int year = Integer.parseInt(resolveYear(idNum));

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        //这里不考虑同年的情况
        if (year < currentYear){
            return true;
        }else {
            return false;
        }
    }

    //获取出生月份
    public static String resolveMonth(String idNum){
        String month = idNum.substring(10, 12);
        return month;
    }
    //判断出生月份的正确性
    public static boolean checkMonth(String idNum){
        int month = Integer.parseInt(resolveMonth(idNum));

        if (month>0 && month<13){
            return true;
        }else{
            return false;
        }
    }

    //获取出生日期
    public static String resolveDate(String idNum){
        String date = idNum.substring(12, 14);
        return date;
    }
    //判断出生日期的正确性
    public static boolean checkDate(String idNum){
        //这里暂不考虑闰年的情况
        int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int date = Integer.parseInt(resolveDate(idNum));
        int month = Integer.parseInt(resolveMonth(idNum));

        if (date>0 && date<=days[month-1]){
            return true;
        }else {
            return false;
        }
    }

    //判断年龄
    public static int resolveAge(String idNum){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int year = Integer.parseInt(resolveYear(idNum));

        return currentYear-year;
    }

    //判断生肖
    public static String resolveZodiac(String idNum){
        int year = Integer.parseInt(resolveYear(idNum));
        String[] Zodiacs =  {"鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"};

        return Zodiacs[(year-795)%12-1];
    }

    //判断性别
    public static String resolveSex(String idNum){
        char[] rawChar = idNum.toCharArray();
        int sex = rawChar[16];
        if (sex%2 == 0){
            return "女";
        }else{
            return "男";
        }
    }

    //输出最后的结果
    public static String resolveResult(String idNum){
        StringBuilder result = new StringBuilder();
        if (!checkLength(idNum)){
            result.append("身份证号码长度不正确");
        }else if (!checkType(idNum)){
            result.append("身份证号码的字符类型有误");
        }else {
            result.append("解析成功.");
            result.append("\n性别: "+resolveSex(idNum));
            if (!(checkYear(idNum) && checkMonth(idNum) && checkDate(idNum))){
                result.append("\n日期信息解析错误.");
            }else{
                result.append("\n出生年份: "+resolveYear(idNum))
                        .append("\n出生月份: "+resolveMonth(idNum))
                        .append("\n出生日期: "+resolveDate(idNum))
                        .append("\n年龄: "+resolveAge(idNum))
                        .append("\n生肖: "+resolveZodiac(idNum));
            }
        }
        return result.toString();
    }
}
