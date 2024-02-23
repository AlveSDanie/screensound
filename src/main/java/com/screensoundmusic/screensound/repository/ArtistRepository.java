package com.screensoundmusic.screensound.repository;

import com.screensoundmusic.screensound.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query("SELECT a FROM Artist a WHERE LOWER(a.name) = LOWER(:name)")
    Optional<Artist> findArtist(@Param("name") String name);
}
