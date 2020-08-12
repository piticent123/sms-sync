package dev.pitlor.permissions;

import java.util.List;

public class Utilities {
    public static String[] FromList(List<String> list) {
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }
}
