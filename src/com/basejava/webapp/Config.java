package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("/Users/kamilaklassen/Desktop/BaseJava/basejava/config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final String dburl;
    private final String dbuser;
    private final String dbpasswort;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            dburl = props.getProperty("db.url");
            dbuser = props.getProperty("db.user");
            dbpasswort = props.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDburl() {
        return dburl;
    }

    public String getDbuser() {
        return dbuser;
    }

    public String getDbpasswort() {
        return dbpasswort;
    }
}
