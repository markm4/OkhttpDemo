package com.hxf.okhttpdemo

import android.util.Base64
import android.util.Log
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException

class ARSAUtils {

    val TAG:String = "ARSAUtils";


    companion object {
        val TAG = "ARSAUtils";
        // 构建Cipher实例时所传入的的字符串，默认为"RSA/NONE/PKCS1Padding"
        val RSA_TRANSFORM = "RSA/NONE/PKCS1Padding";
        // 进行Base64转码时的flag设置，默认为Base64.DEFAULT
        val BASE64_MODE = Base64.DEFAULT;

        /**
         * 将字符串形式的公钥转换为公钥对象
         * 
         * @param publicKeyStr
         * @return
         */
        fun keyStrToPublicKey(publicKeyStr: String): PublicKey? {
            Log.e(TAG,"publicKeyStr:"+publicKeyStr)
            var publicKey: PublicKey? = null;
            var keyBytes = Base64.decode(publicKeyStr, BASE64_MODE);//未经过Base64编码的就省略，直接getBytes()
//            var keyBytes = publicKeyStr.toByteArray();//未经过Base64编码的就省略，直接getBytes()
            val keySpec = X509EncodedKeySpec(keyBytes);
            Log.e(TAG,keySpec.toString())
            try {
                val keyFactory = KeyFactory.getInstance("RSA")
                publicKey = keyFactory.generatePublic(keySpec)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace();
            } catch (e: InvalidKeySpecException) {
                e.printStackTrace();
            }
            return publicKey;
        }

        /**
         * 将字符串形式的私钥，转换为私钥对象
         *
         * @param privateKeyStr
         * @return
         */
        fun keyStrToPrivate(privateKeyStr: String): PrivateKey? {
            var privateKey:PrivateKey? = null;
            var keyBytes = Base64 . decode (privateKeyStr, BASE64_MODE);//未经过Base64编码的就省略，直接getBytes()
            var keySpec: PKCS8EncodedKeySpec = PKCS8EncodedKeySpec(keyBytes);
            try {
                var keyFactory:KeyFactory = KeyFactory . getInstance ("RSA");
                privateKey = keyFactory.generatePrivate(keySpec);
            } catch ( e:NoSuchAlgorithmException) {
                e.printStackTrace();
            } catch ( e:InvalidKeySpecException) {
                e.printStackTrace();
            }
            return privateKey;
        }

        /**
         * 加密或解密数据的通用方法
         * 
         * @param srcData
         *            待处理的数据 明文 or 密文原始数据
         * @param key
         *            公钥或者私钥
         * @param mode
         *            指定是加密还是解密，值为Cipher.ENCRYPT_MODE or Cipher.DECRYPT_MODE
         * @return
         */
        fun processData(srcData:ByteArray,key:Key,mode:Int):ByteArray?{
            var destData:ByteArray? = null;
            try {
                // 获取Cipher实例
                var cipher: Cipher = Cipher.getInstance(RSA_TRANSFORM);
                // 初始化Cipher，mode指定是加密还是解密，key为公钥或私钥
                cipher.init(mode, key);
                destData = cipher.doFinal(srcData); // 处理数据
            } catch ( e:NoSuchAlgorithmException) {
                // e.printStackTrace();
                Log.e(TAG, "processData: " + e, e);
            } catch ( e: NoSuchPaddingException) {
                // e.printStackTrace();
                Log.e(TAG, "processData: " + e, e);
            } catch ( e:InvalidKeyException) {
                // e.printStackTrace();
                Log.e(TAG, "processData: " + e, e);
            } catch ( e: BadPaddingException) {
                // e.printStackTrace();
                Log.e(TAG, "processData: " + e, e);
            } catch ( e: IllegalBlockSizeException) {
                // e.printStackTrace();
                Log.e(TAG, "processData: " + e, e);
            }
            return destData;
        }

        /**
         * 使用公钥加密数据，结果用Base64转码
         *
         * @param srcData
         * @param publicKey
         * @return
         */
        fun encryptDataByPublicKey(srcData:ByteArray,publicKey: PublicKey?):String{
            var resultBytes:ByteArray? =
                publicKey?.let { processData(srcData, it, Cipher.ENCRYPT_MODE) };
            return encodeHeadInfo(Base64.encodeToString(resultBytes, BASE64_MODE));
        }

        /**
         * 使用私钥加密，结果用Base64转码
         *
         * @param srcData
         * @param privateKey
         * @return
         */
        fun encryptDataByPrivateKey(srcData:ByteArray,privateKey:PrivateKey?):String{
            var resultBytes:ByteArray? =
                privateKey?.let {
                    processData(srcData,privateKey,Cipher.ENCRYPT_MODE)
                }
            return Base64.encodeToString(resultBytes, BASE64_MODE)
        }

        fun encodeHeadInfo (str:String):String {
            var stringBuffer:StringBuffer = StringBuffer();
            for (i in 0 until str.length) {
                var c:Char = str.get(i);
                if (c <= '\u001f' || c >= '\u007f') {
                } else {
                    stringBuffer.append(c);
                }
            }
            return stringBuffer.toString();
        }
    }

    /**
     *byte[] resultBytes = processData(srcData, privateKey, Cipher.ENCRYPT_MODE);
    return Base64.encodeToString(resultBytes, BASE64_MODE);
     */
}