/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa_algorithm;

/**
 *
 * @author enamul
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class RSA_KeyPairGenerator {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public RSA_KeyPairGenerator() throws NoSuchAlgorithmException {
        
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        RSA_KeyPairGenerator keyPairGenerator = new RSA_KeyPairGenerator();

        keyPairGenerator.writeToFile("RSA/publicKey", keyPairGenerator.getPublicKey().getEncoded());
        keyPairGenerator.writeToFile("RSA/privateKey", keyPairGenerator.getPrivateKey().getEncoded());

        String publicKey = Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded());
        System.out.println("publicKey = " + publicKey);

        String privatekey = Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded());
        System.out.println("privatekey = " + privatekey);

    }
    
    
}
