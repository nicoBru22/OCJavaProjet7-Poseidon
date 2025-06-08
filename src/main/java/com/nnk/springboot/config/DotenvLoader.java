package com.nnk.springboot.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Charge les variables d'environnement depuis un fichier `.env` dans les propriétés système.
 * 
 * Cette classe utilise la bibliothèque Dotenv pour récupérer les variables d'environnement
 * définies dans le fichier `.env` à la racine du projet, puis les charge dans
 * les propriétés système via {@link System#setProperty(String, String)}.
 * 
 * Cela permet d'accéder facilement aux variables d'environnement avec
 * {@link System#getProperty(String)} dans toute l'application.
 */
public class DotenvLoader {

    /**
     * Charge les variables d'environnement depuis le fichier `.env` dans les propriétés système.
     * 
     * Cette méthode doit être appelée au démarrage de l'application
     * avant l'accès aux variables d'environnement.
     */
    public static void loadEnv() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );
    }
}
