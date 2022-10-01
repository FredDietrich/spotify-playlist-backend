package org.dietrich.helper;

import java.util.Base64;

public class Base64Helper {
    
   private static Base64.Encoder encoder = Base64.getEncoder();
   private static Base64.Decoder decoder = Base64.getDecoder();

    public static String encode(String string) {
        return encoder.encodeToString(string.getBytes());
    }

    public static String decode(String encodedString) {
        return new String(decoder.decode(encodedString));
    }

}
