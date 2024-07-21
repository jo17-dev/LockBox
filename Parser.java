/*
 * utility class: perform only the conversion between String and byte
 */

import java.util.ArrayList;

public class Parser {
    public static ArrayList<Byte> convertStringToBytes(String target){
        ArrayList<Byte> result = new ArrayList<Byte>();
        for(int i=0; i<target.length(); i++){
            result.add((byte) target.charAt(i));
        }

        return result;
    }

    public static String convertBytesToString(ArrayList<Byte> target){
        String result = new String();
        for(byte item : target){
            result += ((char) item);
        }
        return result;
    }
}
