package com.example.rotomugear.logic.Cryption;

import com.example.rotomugear.logic.Cryption.Cryption;


public class Polybius extends Cryption {

    @Override
    public String encryption(String p, String k) {
        if (!setKey(k)){
            return "密钥输入错误";
        }
        this.plaintext = p;

        return trans();
    }

    public boolean setKey(String key) {
        //密钥输入检验
        //1.输入密钥的长度为5
        if (key.length()!=5) {
            return false;
        }

        //2.密钥的每个元素不能相同
        for (int i=0;i<key.length()-1;i++) {
            for(int j=i+1;j<key.length();j++) {
                if (key.charAt(i)==key.charAt(j)) {
                    return false;
                }
            }
        }

        this.key = key;
        return true;
    }

    public String trans() {
        //密钥的升序排序处理
        char[] sortKey = key.toCharArray();
        for(int i=0;i<sortKey.length-1;i++) {
            for(int j=0;j<sortKey.length-i-1;j++) {
                if (sortKey[j]>sortKey[j+1]) {
                    char temp = sortKey[j];
                    sortKey[j] = sortKey[j+1];
                    sortKey[j+1] = temp;
                }
            }
        }

        //按密钥解读出矩阵列数的排列顺序
        int[] sort = new int[sortKey.length];
        for (int i=0;i<sort.length;i++) {
            sort[i] = key.indexOf(sortKey[i]);
        }

        //更新密码表
        PolybiusChecker.setChecker(sort);

        //通过明文进行密文的转化
        char[] plainArray = plaintext.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c: plainArray) {
            if (c==' ') {
                //用S表示明文中的空格
                sb.append("S");
            }else if (c=='j'){
                sb.append(PolybiusChecker.match('i'));
            }else {
                sb.append(PolybiusChecker.match(c));
            }
        }
        ciphertext = sb.toString();
        return ciphertext;
    }

    @Override
    public String decryption(String c, String k) {
        if (!setKey(k)){
            return "密钥输入错误";
        }
        this.ciphertext = c;
        return inTrans();
    }

    public String inTrans() {
        //密钥的升序排序处理
        char[] sortKey = key.toCharArray();
        for(int i=0;i<sortKey.length-1;i++) {
            for(int j=0;j<sortKey.length-i-1;j++) {
                if (sortKey[j]>sortKey[j+1]) {
                    char temp = sortKey[j];
                    sortKey[j] = sortKey[j+1];
                    sortKey[j+1] = temp;
                }
            }
        }

        //按密钥解读出矩阵列数的排列顺序
        int[] sort = new int[sortKey.length];
        for (int i=0;i<sort.length;i++) {
            sort[i] = key.indexOf(sortKey[i]);
        }

        //更新密码表
        PolybiusChecker.setChecker(sort);

        StringBuilder sb = new StringBuilder();
        for (int i=0;i<ciphertext.length();i++) {
            if (ciphertext.charAt(i)=='S') {
                sb.append(" ");
                continue;
            }
            char temp = PolybiusChecker.inMatch(ciphertext.substring(i, i+2));
            if (temp == 'i') {
                sb.append("i(j)");
            }else {
                sb.append(temp);
            }
            i++;
        }
        plaintext = sb.toString();
        return plaintext;
    }
}
