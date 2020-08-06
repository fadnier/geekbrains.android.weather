package org.sochidrive.weather;

import java.io.Serializable;

public class SingletonSave implements Serializable {
    public static Boolean checkBoxWindSpeed;
    public static Boolean checkBoxPressure;

    private static SingletonSave instance;

    private SingletonSave() {}

    static SingletonSave getInstance() {
        if(instance == null) {
            instance = new SingletonSave();
        }

        return instance;
    }
}
