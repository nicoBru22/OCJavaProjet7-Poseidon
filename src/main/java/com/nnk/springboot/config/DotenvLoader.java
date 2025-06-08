package com.nnk.springboot.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvLoader {
    public static void loadEnv() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}