/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_algorithm;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author enamul
 */
public class RAS_Encrypt_Decrypt {
     public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPUnhfeqHK+Ks5fkSFKjFnrd2jYrQr34s8YC+/h2NJTwM0invIa686yGbmXR/ixgyt9ykBIghK9NgGm3T8zKniwWdpxd7VKy9h/xPS8JizXs4Zp56bWrQSqxNmsfAciL0Hi6M2slenF23T3oOppR2Cy0FG84KC3jQPET8v1WiulQIDAQAB";
   
    public static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI9SeF96ocr4qzl+RIUqMWet3aNitCvfizxgL7+HY0lPAzSKe8hrrzrIZuZdH+LGDK33KQEiCEr02AabdPzMqeLBZ2nF3tUrL2H/E9LwmLNezhmnnptatBKrE2ax8ByIvQeLozayV6cXbdPeg6mlHYLLQUbzgoLeNA8RPy/VaK6VAgMBAAECgYAnhX5cFdz3s4rD1+XdMr1c8bzkiRtV3dbvVzbTgeoHFRq1Klrn8Ynok8qMS7G3PxXQAw/zmAbhZJMNf2DLNUTDLbIvm0LBnPLRhptkxGq3QKiKOrr8j3E2ktoWqyJT4OCxFhzk7RPAwNEjVgqynsbBxAI9t1OK6j9B6Pdkgef9AQJBAPbitbBfmufVp+lu2hTF7pbh46fM8svpEGVABDwo6CnZyS4wWRkhvc/uMcAknUaVE1Ff/SfrtwALb9MbMcCWPo0CQQCUnP47/mwER4DV4umtBxKWSh2zl05uL+VRQxnbwY1l9l4c4eFscBPTFmjJvulUxH1gNkHXIVxGReoIoguMbdIpAkA/XOgsoBztq1QxARZPho05hDgddOhLlUFz+v4bQpRzSUXs6NO1M4e3ufZizgLffps6o1whxsls5YKJt80JFIZxAkA2Y5KEAGQl2Mlk/evJgGokCLIAFSMGHo9Ng0JGc/q800K0TVLD7ezDd1MYar9YX6UG/jRweGwd5950FGInndWpAkByUk2jo8agLqF7X0edCRBcIB5OB2ywUL4+5ynrsY2rI8ZHD7n3G2XvazzF6enM8KIbpa7xM194f+ccwL1AWSB8";
   
    public static PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    
     public static String get_decrypt(String data) {
       
        String rValue= "";
        try{
        String s =decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(privateKey));
       
        rValue = s;
        }catch (Exception e){
            e.printStackTrace();
        }
        return rValue;
    }
   
    
    
      public static String encodeParam(String param){
        String uuid = UUID.randomUUID().toString();

        String s= uuid.replaceAll("[-]*", "");
        String id = s.substring(0, 20);
      
        return  id;
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
           
        
            String encryptedString = Base64.getEncoder().encodeToString(encrypt("Hello", publicKey));
            System.out.println("encryptedString = " + encryptedString);
            
            String decryptedString = RAS_Encrypt_Decrypt.get_decrypt(encryptedString);
            System.out.println("decryptedString = " + decryptedString);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RAS_Encrypt_Decrypt.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
