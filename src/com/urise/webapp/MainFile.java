package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        iterateDir("./src");
//        String filePath = ".\\.gitignore";
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//        File dir = new File("./src/com/urise/webapp");
//        System.out.println(dir.isDirectory());
//        for (String name : Objects.requireNonNull(dir.list())) {
//            System.out.println(name);
//        }
//        System.out.println(dir.list());
//
//
//        try (FileInputStream fis = new FileInputStream(filePath);) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void iterateDir(String filePath) {
        File file = new File(filePath);
        System.out.println(file.getName());
        if (file.isDirectory()) {
            for (File iter : file.listFiles()) {
                iterateDir(iter.getAbsolutePath());
            }
        }
    }
}
