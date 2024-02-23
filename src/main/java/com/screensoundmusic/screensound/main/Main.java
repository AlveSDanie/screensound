package com.screensoundmusic.screensound.main;

import com.screensoundmusic.screensound.model.Artist;
import com.screensoundmusic.screensound.model.ArtistType;
import com.screensoundmusic.screensound.model.Music;
import com.screensoundmusic.screensound.repository.ArtistRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final Scanner read = new Scanner(System.in);
    private final ArtistRepository repository;

    public Main(ArtistRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {

        int option = 0;
        while (option != 9) {
            System.out.println("""
                                                
                    1 - Cadastrar artistas
                    2 - Cadastrar músicas
                    3 - Listar músicas
                    4 - Buscar músicas por artistas
                                                
                    9 - Sair
                    """);

            option = read.nextInt();
            read.nextLine();

            switch (option) {
                case 1:
                    registerArtist();
                    break;
                case 2:
                    registerMusics();
                    break;
                case 3:
                    showMusicsList();
                    break;
                case 4:
                    seachMusicByArtist();
                case 9:
                    System.out.println("\nFinalizando a aplicação!");
                    break;
                default:
                    System.out.println("\nOpção inválida!");
                    break;
            }
        }
    }

    private void seachMusicByArtist() {
        System.out.println("\nInforme o nome do artista: ");
        String artistName = read.nextLine();
        Optional<Artist> artist = repository.findArtist(artistName);

        if (artist.isPresent()) {
            artist.get().getMusics().forEach(m -> System.out.println(
                    m.getName() + " - (" + m.getArtist().getName() + ")"
            ));
        } else {
            System.out.println("\nArtista não encontrado!");
        }
    }

    private void showMusicsList() {
        List<Artist> artists = repository.findAll();
        artists.forEach(a -> a.getMusics().forEach(m -> System.out.println(
                m.getName() + " - (" + m.getArtist().getName() + ")"
        )));
    }

    private void registerMusics() {
        String registerNewOne = "S";

        while (!registerNewOne.equalsIgnoreCase("n")) {
            System.out.println("\nCadastrar música de qual artista? ");
            String artistName = read.nextLine();
            Optional<Artist> artist = repository.findArtist(artistName);

            if (artist.isPresent()) {
                System.out.println("\nInforme o nome da música: ");
                String name = read.nextLine();
                Music music = new Music(name, artist);
                artist.get().getMusics().add(music);
                repository.save(artist.get());
            } else {
                System.out.println("\nArtista não encontrado!");
            }

            System.out.println("\nDeseja cadastrar uma nova música? (S/N) ");
            registerNewOne = read.nextLine();
        }
    }

    private void registerArtist() {
        String registerNewOne = "S";

        while (!registerNewOne.equalsIgnoreCase("n")) {
            System.out.println("Informe o nome do artista: ");
            String name = read.nextLine();
            System.out.println("Informe o tipo do artista - (solo, dupla, banda): ");
            String type = read.nextLine();
            ArtistType artistType = ArtistType.valueOf(type.toUpperCase());
            Artist artist = new Artist(name, artistType);
            repository.save(artist);

            System.out.println("\nGostaria de registrar um novo artista? (S/N) ");
            registerNewOne = read.nextLine();
        }

    }

}
