package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        for (String name : Objects.requireNonNull(dir.list())) {
            System.out.println(name);
        }
        System.out.println(dir.list());

        try (FileInputStream fis = new FileInputStream(filePath);) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        printDirectoryDeeply(dir, 0);
    }

    public static void printDirectoryDeeply(File dir, int count) {
        File[] files = dir.listFiles();
        count++;
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < count; i++) {
            prefix.append(" ");
        }

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(prefix.toString() + "File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println(prefix.toString() + "Directory: " + file.getName());
                    printDirectoryDeeply(file, count);
                }
            }
        }
    }
}
