package com.challange.singledigit.util;

import com.challange.singledigit.exception.ApplicationException;
import com.challange.singledigit.exception.ApplicationExceptionType;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.challange.singledigit.exception.ApplicationExceptionType.*;

public class RSAUtil {

    private static final int BIT_LENGTH = 2048;

    public static PublicKey getPublicKey(String base64PublicKey){
        RSAPublicKey publicKey;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            if (publicKey.getModulus().bitLength() != BIT_LENGTH)
                throw new ApplicationException(INVALID_RSA_KEY);

            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationException(INTERNAL_ERROR);
        } catch (InvalidKeySpecException e) {
            throw new ApplicationException(INVALID_RSA_KEY);
        }
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationException(INTERNAL_ERROR);
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new ApplicationException(INVALID_RSA_KEY);
        }
        return privateKey;
    }

    public static String encrypt(String data, String publicKey)  {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException e) {
            throw new ApplicationException(INTERNAL_ERROR);
        } catch (IllegalBlockSizeException e) {
            throw new ApplicationException(TOO_LONG_DATA_TO_BE_ENCRYPTED);
        } catch (InvalidKeyException e) {
            throw new ApplicationException(INVALID_RSA_KEY);
        }
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(data));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new ApplicationException(INTERNAL_ERROR);
        } catch (BadPaddingException e) {
            throw new ApplicationException(DECRYPTION_ERROR);
        } catch (IllegalBlockSizeException e) {
            throw new ApplicationException(TOO_LONG_DATA_TO_BE_DECRYPTED);
        } catch (InvalidKeyException e) {
            throw new ApplicationException(INVALID_RSA_KEY);
        }
    }

    public static String decrypt(String data, String base64PrivateKey) {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }
}
