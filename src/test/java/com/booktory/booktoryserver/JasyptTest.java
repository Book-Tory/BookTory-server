package com.booktory.booktoryserver;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTest {

    @Test
    public void jasyptTest() {
        String value = "";
        String result = jasyptEncoding(value);
        System.out.println("result : " + result);
    }

    public String jasyptEncoding(String value){
        String key = "Ovjq2iCAuYQRToyoj0PV0lUsETH5ebfi";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}
