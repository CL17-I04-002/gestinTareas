package com.example.gestion.tareas.D_infraestructure.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class FilesHandler {
    public FilesHandler(){

    }
    public static String loadKey(String name, String key) throws IOException{
        Properties properties = new Properties();
        InputStream iStream = FilesHandler.class.getClassLoader().getResourceAsStream(name);
        properties.load(iStream);

        String keyFound = properties.getProperty(key);
        return keyFound;
    }
}
