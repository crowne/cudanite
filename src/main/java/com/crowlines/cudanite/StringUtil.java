package com.crowlines.cudanite;

public class StringUtil {

    /**
     * Creates a String from a zero-terminated string in a byte array
     * 
     * @param bytes
     *            The byte array
     * @return The String
     */
    public static String createString(byte bytes[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            char c = (char) bytes[i];
            if (c == 0) {
                break;
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
