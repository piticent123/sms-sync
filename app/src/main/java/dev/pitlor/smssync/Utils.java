package dev.pitlor.smssync;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static <T> List<T> nonNull(List<T> t) {
        return t != null ? t : new ArrayList<>();
    }
}
