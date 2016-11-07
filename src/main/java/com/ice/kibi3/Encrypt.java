package com.ice.kibi3;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Bartek on 2016-11-06.
 */
public class Encrypt {
    public static void AES(Key key, String file, String iv){

        FileOutputStream fos = null;
        FileInputStream fis = null;
        Cipher c = null;
        try {
            fos = new FileOutputStream(file+"enc");
            fis = new FileInputStream(file);
            IvParameterSpec init = new IvParameterSpec(iv.getBytes("UTF-8"));
            c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, key, init);
            CipherOutputStream cos = new CipherOutputStream(fos , c);
            byte[] block = new byte[8];
            int i;
            while ((i = fis.read(block)) != -1) {
                cos.write(block, 0, i);
            }
            cos.close();
            fis.close();
            fos.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

    }
}
