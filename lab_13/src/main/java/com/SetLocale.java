package com;

import java.util.Locale;

public abstract class SetLocale {
    public static void set() {
        Locale.setDefault(Locale.getDefault());
    }

    public static void set(String languageTag) {
        String[] tokens = languageTag.split("_");
        Locale.setDefault(new Locale(tokens[0], tokens[1]));
    }
}
