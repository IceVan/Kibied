package com.ice.kibi3;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
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
public class Decrypt {
    public static void AES(Key key, String file, String iv)
    {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        Cipher c = null;
        try {
            fos = new FileOutputStream(file+".txt");
            fis = new FileInputStream(file);
            c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec init = new IvParameterSpec(iv.getBytes("UTF-8"));
            c.init(Cipher.DECRYPT_MODE, key, init);
            CipherInputStream cis = new CipherInputStream(fis , c);
            byte[] block = new byte[8];
            int i;
            while ((i = cis.read(block)) != -1) {
                fos.write(block, 0, i);
            }
            cis.close();
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
