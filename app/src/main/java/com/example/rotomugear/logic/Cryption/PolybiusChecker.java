package com.example.rotomugear.logic.Cryption;

public class PolybiusChecker {
    //棋盘密码的棋盘采用字母A、D、F、G、X
    public static final char[][] form = {
            {'a', 'b', 'c', 'd', 'e'},
            {'f', 'g', 'h', 'i', 'k'},
            {'l', 'm', 'n', 'o', 'p'},
            {'q', 'r', 's', 't', 'u'},
            {'v', 'w', 'x', 'y', 'z'}
    };

    public static char[][] checker;
    private static int[] keySort;

    public static void setChecker(int[] key) {
        //通过给定的密钥数组生成新的密码表
        keySort = key;
        checker = new char[5][5];
        for(int j=0;j<5;j++) {
            int aim = key[j];
            for(int i=0;i<5;i++) {
                checker[i][j] = form[i][aim];
            }
        }
    }

    public static String match(char c) {

        char a;
        //将输入明文转换为小写
        if (Character.isUpperCase(c)) {
            a = Character.toLowerCase(c);
        }else {
            a = c;
        }

        //查找元素在数组中的位置
        int row=0;
        int col=0;
        if (a == 'j') {
            row = 0;
            col = 3;
        }
        for(int i=0;i<checker.length;i++) {
            for(int j=0;j<checker[i].length;j++) {
                if (checker[i][j]==a) {
                    row = i;
                    col = j;
                }
            }
        }

        return trans(row, col);
    }

    public static char inMatch(String s) {
        //检查输入字符串格式
        //长度检查
        if (s.length()!=2) {
            System.out.println("ERROR!Length of cipher is wrong.");
            System.exit(1);
        }

        char[] st = s.toCharArray();
        //同一转换为大写字母
        for(int i=0;i<st.length;i++) {
            if (Character.isLowerCase(st[i])) {
                st[i] = Character.toUpperCase(st[i]);
            }
        }

        int row = "ADFGX".indexOf(st[0]);
        int col = inTrans(st[1]);

        return checker[row][col];
    }

    public static String trans(int row, int col) {
        //给定行和列，范围对应的索引
        char[] index = {'A', 'D', 'F', 'G', 'X'};
        StringBuilder r = new StringBuilder();
        r.append(index[row]).append(index[keySort[col]]);
        return r.toString();
    }

    public static int inTrans(char s) {
        //给定结果，返回所在的行和列
        String index = "ADFGX";
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<5;i++) {
            sb.append(index.charAt(keySort[i]));
        }
        String index2 = sb.toString();
        return index2.indexOf(s);
    }
}
