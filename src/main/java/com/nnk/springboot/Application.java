package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nnk.springboot.config.DotenvLoader;
/**
 * Point d'entrée principal de l'application Spring Boot.
 * <p>
 * Cette classe contient la méthode {@code main} qui démarre l'application.
 * Avant de lancer Spring Boot, elle charge les variables d'environnement
 * définies dans un fichier `.env` via la classe {@link DotenvLoader}.
 * </p>
 *
 * @author [Nicolas B]
 */
@SpringBootApplication
public class Application {

    /**
     * Méthode principale qui lance l'application Spring Boot.
     * <p>
     * Elle commence par charger les variables d'environnement depuis un fichier
     * .env, puis démarre le contexte Spring.
     * </p>
     *
     * @param args les arguments passés en ligne de commande
     */
	public static void main(String[] args) {
        DotenvLoader.loadEnv();
		SpringApplication.run(Application.class, args);
	}
}
