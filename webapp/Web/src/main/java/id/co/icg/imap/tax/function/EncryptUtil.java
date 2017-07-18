package id.co.icg.imap.tax.function;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {

    public static String MD5(String string) {
        StringBuilder hexString = new StringBuilder();
        String md5 = "";
        try {
            MessageDigest mdAlgorithm = MessageDigest.getInstance("MD5");
            mdAlgorithm.update(string.getBytes());
            byte[] digest = mdAlgorithm.digest();
            for (int i = 0; i < digest.length; i++) {
                string = Integer.toHexString(0xFF & digest[i]);
                if (string.length() < 2) {
                    string = "0" + string;
                }
                hexString.append(string);
            }
            md5 = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            md5 = string;
        }
        return md5;
    }

    public static String getHash(String txt, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(txt.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
