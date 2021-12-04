package resLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private  static String defaultAddress;

    private FileInputStream fileInputStream;
    private final Properties properties;

    public ConfigLoader(String name){
        properties = new Properties();
        defaultAddress = "./src/main/resources/config/"+name+".properties";
        try {
            fileInputStream = new FileInputStream(defaultAddress);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readString(String name){
        return properties.getProperty(name);
    }

    public boolean checkIfIsEmpty(String name){
        return properties.getProperty(name).isEmpty();
    }

    public int readInteger(String name){
        return Integer.parseInt(properties.getProperty(name));
    }

    public boolean readBoolean (String name){
        return Boolean.parseBoolean(properties.getProperty(name));
    }

}

