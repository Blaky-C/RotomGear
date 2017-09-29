package com.example.rotomugear.logic.Cryption;

public class Caesar extends Cryption{

    int size;

    @Override
    public String encryption(String plaintext, String key) {
        if (!setPlaintext(plaintext)){
            return "输入明文错误";
        }
        this.key = key;
        transCipherText();
        return "生成密文：\n"+ciphertext;
    }

    public boolean setPlaintext(String plaintext) {
        char[] plaintextArray = plaintext.toCharArray();

        for(int i=0;i<plaintextArray.length;i++) {
            //判断明文是否由字母构成
            if (!Character.isLetter(plaintextArray[i])) {
                return false;
            }
            //将明文统一转换至小写
            if (Character.isUpperCase(plaintextArray[i])) {
                plaintextArray[i] = Character.toLowerCase(plaintextArray[i]);
            }
        }

        this.plaintext = new String(plaintextArray);
        size = plaintext.length();
        return true;
    }

    public void transCipherText() {
        char[] text = plaintext.toCharArray();
        int transValue=Integer.parseInt(key)-32;
        int temp;
        for(int i=0;i<text.length;i++) {
            temp = text[i]+transValue;
            if (temp>90) {
                temp =temp-90+65-1;
            }
            text[i] = (char)(temp);
        }
        this.ciphertext = new String(text);
    }

    @Override
    public String decryption(String ciphertext, String key) {
        this.key = key;
        if (!setCipherText(ciphertext)){
            return "输入密文错误";
        }
        return traverse();
    }

    public boolean setCipherText(String ciphertext) {
        char[] arr = ciphertext.toCharArray();

        for(int i=0;i<arr.length;i++) {
            //密文必须由英文字母构成
            if (!Character.isLetter(arr[i])) {
                return false;
            }
            //将密文统一转化为大写字母
            if (Character.isLowerCase(arr[i])) {
                arr[i] = Character.toUpperCase(arr[i]);
            }
        }
        this.ciphertext = new String(arr);
        return true;
    }

    public String cipherTrans(int key) {
        char[] arr = ciphertext.toCharArray();
        int temp;

        for(int i=0;i<ciphertext.length();i++) {
            temp = arr[i]+32-key;

            if(temp<97) {
                temp += 26;
            }
            arr[i] = (char)temp;
        }

        plaintext = new String(arr);
        return plaintext;
    }

    public String traverse() {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<26;i++) {
            sb.append("When key="+i+", plaintext is:    \n"+cipherTrans(i)).append("\n");
        }
        return sb.toString();
    }
}
