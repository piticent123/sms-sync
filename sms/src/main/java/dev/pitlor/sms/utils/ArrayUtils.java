package dev.pitlor.sms.utils;

public class ArrayUtils {
    public static String[] Concat(String[]... args) {
        int[] lengths = new int[args.length + 1];
        for (int i = 0; i < args.length; i++) {
            lengths[i + 1] = args[i].length + lengths[i];
        }

        String[] result = new String[lengths[lengths.length - 1]];
        for (int i = 0; i < args.length; i++) {
            System.arraycopy(args[i], 0, result, lengths[i], args[i].length);
        }

        return result;
    }
}
