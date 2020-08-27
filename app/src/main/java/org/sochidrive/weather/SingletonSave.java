package org.sochidrive.weather;

import java.io.Serializable;

public class SingletonSave implements Serializable {
    public static Boolean checkBoxWindSpeed = false;
    public static Boolean checkBoxPressure = false;
    public static String city = "Москва";

    private static SingletonSave instance;

    private SingletonSave() {}

    public static SingletonSave getInstance() {
        if(instance == null) {
            instance = new SingletonSave();
        }

        return instance;
    }
}
