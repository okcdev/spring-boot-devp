package com.rbs.cn.tools.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * @author
 */
public class EncryptionUtils {
    static Logger logger = LoggerFactory.getLogger(EncryptionUtils.class);

    /**
     * 计算字符串的MD5值
     * @param str 原始数据
     * @return MD5 hash值
     */
    public static String md5Hex(String str){
        return DigestUtils.md5Hex(str);
    }

    /**
     * 计算字符串的SHA1 Hash值
     * @param str 原始数据
     * @return SHA1 hash值
     */
    public static String sha1Hex(String str){
        return DigestUtils.sha1Hex(str);
    }

    /**
     * 计算字符串的SHA256 Hash值
     * @param str 原始数据
     * @return SHA256 hash值
     */
    public static String sha256Hex(String str){
        return DigestUtils.sha256Hex(str);
    }


    /**
     * 计算字符串的Base64 值
     * @param str 原始数据
     * @return Base64加密值
     */
    public static  String base64Hex(String str){
        return new String(Base64.encodeBase64(str.getBytes()));
    }

    /**
     * 实现DES加密
     * @param data
     * @param keyStr
     * @return
     */
    public static String encryptBasedDes(String data,String keyStr) {

        String encryptedData = null;
        try {

            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(keyStr.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 加密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key, sr);
            // 加密，并把字节数组编码成字符串
            encryptedData = Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
//            encryptedData = new sun.misc.BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("加密错误，错误信息：", e);
        }
        return encryptedData;
    }


    /**
     * DES解密
     * @param cryptData
     * @param keyStr
     * @return
     */
    public static String decryptBasedDes(String cryptData,String keyStr) {
        String decryptedData = null;
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(keyStr.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(deskey);
            // 解密对象
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key, sr);
            // 把字符串解码为字节数组，并解密
            decryptedData = new String(cipher.doFinal(Base64.decodeBase64(cryptData)));
        } catch (Exception e) {

            throw new RuntimeException("解密错误，错误信息：", e);
        }
        return decryptedData;
    }
}
