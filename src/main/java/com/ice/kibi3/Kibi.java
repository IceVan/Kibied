package com.ice.kibi3;


import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Hello world!
 *
 */
public class Kibi
{
    private static void init() {
        FileOutputStream fos = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(null,null);

            KeyGenerator KeyGen = KeyGenerator.getInstance("AES");
            KeyGen.init(128);

            javax.crypto.SecretKey mySecretKey = KeyGen.generateKey();
            KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(mySecretKey);
            keyStore.setEntry("1", skEntry, new KeyStore.PasswordProtection("senatus".toCharArray()));

            KeyGen.init(128);
            mySecretKey = KeyGen.generateKey();
            skEntry = new KeyStore.SecretKeyEntry(mySecretKey);
            keyStore.setEntry("2", skEntry, new KeyStore.PasswordProtection("populusque".toCharArray()));

            KeyGen.init(128);
            mySecretKey = KeyGen.generateKey();
            skEntry = new KeyStore.SecretKeyEntry(mySecretKey);
            keyStore.setEntry("3", skEntry, new KeyStore.PasswordProtection("romanus".toCharArray()));

            fos = new FileOutputStream("keystore.ks");
            keyStore.store(fos, "senatus".toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fos != null) try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args )
    {
        if(args.length == 5 && args[4].compareTo("-init") == 0) init();

        String keyStorePath = args[0];
        String mode = args[1];
        String filePath = args[2];
        String keyIndex = args[3];

        String IV = "RandomInitVector";

        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        console.printf("Password%n");
        char passwordArray[] = console.readPassword("Enter password: ");


        FileInputStream fis = null;
        try {
            KeyStore ks = KeyStore.getInstance("JCEKS");
            fis = new FileInputStream(keyStorePath);
            ks.load(fis,"senatus".toCharArray());
            Key keyEntry = ks.getKey(keyIndex, passwordArray);
            if(mode.compareToIgnoreCase("encrypt") == 0)    Encrypt.AES(keyEntry, filePath, IV);
            else if (mode.compareToIgnoreCase("decrypt") == 0) Decrypt.AES(keyEntry, filePath, IV);
            else    console.printf("Wrong mode.");

            console.printf("Done");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }finally{
            if(fis != null) try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
