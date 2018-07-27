package com.ztnh.publicdisk.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author liusir_xy
 * @date 2017/7/4 17:54
 * @desc {加密}
 */
public class AesUtils {

    public static final String AES_KEY = "xTSzE4WQ4DR22qfx";

    // 加密
    public static String Encrypt(String sSrc) {
        byte[] encrypted = new byte[0];
        try {
            byte[] raw = AES_KEY.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64EncoderUtils.encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }
}
