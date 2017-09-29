package com.example.rotomugear.logic.Cryption;

public class Substitution extends Cryption{

    int row, col;
    char[][] plainMatrix;
    @Override
    public String encryption(String p, String k) {
        if (!setPlainText(p)){
            return "明文输入错误";
        }
        if (!setKey(k)){
            return "密钥输入错误";
        }
        return "密文生成成功.\n"+transText();
    }

    public boolean setPlainText(String plaintext) {
        String[] plainArray = plaintext.split(" ");

        //确定矩阵的行数和列数
        row = plainArray.length;
        col = 0;
        for(int i=0;i<row;i++) {
            if (col < plainArray[i].length()) {
                col = plainArray[i].length();
            }
        }

        //以空格数据初始化矩阵
        plainMatrix = new char[row][col];
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                plainMatrix[i][j] = ' ';
            }
        }
        //将明文内容填入矩阵
        for (int i=0;i<plainArray.length;i++) {
            for(int j=0;j<plainArray[i].length();j++) {
                plainMatrix[i][j] = plainArray[i].charAt(j);
            }
        }

        //显示矩阵
		/*for (int i=0;i<plainMatrix.length;i++) {
			for(char c:plainMatrix[i]) {
				System.out.print(c);
			}
			System.out.println();
		}*/
        return true;
    }

    public boolean setKey(String key) {

        //密钥输入检验
        //1.输入密钥的长度必须大于字符数组的最大宽度
        if (key.length()<col) {
            return false;
        }

        //2.密钥的每个元素不能相同
        for (int i=0;i<key.length()-1;i++) {
            for(int j=i+1;j<key.length();j++) {
                if (key.charAt(i)==key.charAt(j)) {
                    System.out.println("ERROR!Element of key is not allowed to be same.");
                    return false;
                }
            }
        }

        this.key = key;
        return true;
    }

    public String transText() {
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
        //System.out.println(sortKey);

        //按密钥解读出矩阵列数的排列顺序
        int[] sort = new int[sortKey.length];
        for (int i=0;i<sort.length;i++) {
            sort[i] = key.indexOf(sortKey[i])+1;
            //System.out.print(sort[i]);
        }

        char[][] afterTrans = new char[row][col];
        for(int i=0;i<row;i++) {
            for(int j=0;j<col;j++) {
                afterTrans[i][j] = ' ';
            }
        }

        for (int j=0;j<col;j++) {
            int aim = sort[j]-1;
            for (int i=0;i<row;i++) {
                afterTrans[i][j] = plainMatrix[i][aim];
            }
        }

        //显示矩阵
		/*for (int i=0;i<afterTrans.length;i++) {
			for(char c:afterTrans[i]) {
				System.out.print(c);
			}
			System.out.println();
		}*/

        //生成密文
        StringBuilder sb = new StringBuilder();
        for(int j=0;j<col;j++) {
            int aim = sort[j]-1;
            for(int i=0;i<row;i++) {
                sb.append(afterTrans[i][aim]);
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
        return inTransText();
    }

    public String inTransText(){
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
            sort[i] = key.indexOf(sortKey[i])+1;
            //System.out.print(sort[i]);
        }

        int col = key.length();
        int row = ciphertext.length()/col;
        char[][] rawText = new char[row][col];
        for(int i=0;i<ciphertext.length();i++){
            int aim = sort[i/row]-1;
            rawText[i%row][aim] = ciphertext.charAt(i);
        }

        //生成最原始的明文矩阵
        char[][] originText = new char[row][col];
        for(int j=0;j<col;j++) {
            int aim = sort[j]-1;
            for (int i=0;i<row;i++) {
                originText[i][aim] = rawText[i][j];
            }
        }

        //生成明文
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<originText.length;i++) {
            for(int j=0;j<originText[i].length;j++) {
                if (originText[i][j]!=' ') {
                    sb.append(originText[i][j]);
                }
            }
            sb.append(" ");
        }

        plaintext = sb.toString();
        return plaintext;
    }
}
