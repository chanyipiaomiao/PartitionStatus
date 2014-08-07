package com.linux178;

import it.sauronsoftware.base64.*;

class DecryptPassword {

    /** 解密字符串 */
    public static String Decrypt(String encrypt_password){
        return Base64.decode(encrypt_password);
    }
}
