package me.Spleevtv.NoCloud.Base.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

public class Config {

    private Properties properties;
    private File file;
    private FileInputStream input;
    private FileOutputStream output;
    private Boolean isloadet;

    public Config(File f) {
        this.properties = new Properties();
        this.file = f;
        this.isloadet = false;
    }
    public void load() {
        try {
            this.input = new FileInputStream(this.file);
            this.properties.load(this.input);
            this.isloadet = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Boolean isLoaded() {
        return this.isloadet;
    }
    public String get(String key) {
        return this.properties.getProperty(key);
    }
    public Integer getInt(String key) {
        return Integer.parseInt(this.properties.getProperty(key));
    }
    public void set(String key, String value) {
        this.properties.setProperty(key, value);
    }
    public void remove(String key) {
        this.properties.remove(key);
    }
    public void save() {
        try {
            this.output = new FileOutputStream(this.file);
            this.properties.store(this.output, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void unload() {
        try {
            if(this.output != null) {
                this.output.close();
            }
            if(this.input != null) {
                this.input.close();
            }
            this.output = null;
            this.input = null;
            this.isloadet = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
