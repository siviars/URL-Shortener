package com.example.urlshortener;

import com.example.urlshortener.Objects.Links;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepositoryLinks extends JpaRepository<Links, Integer> {

    @Query("SELECT l FROM Links l " +
            "WHERE l.shortURL = ?1 ")
    Links getRedirectURL(String redirectURL);

}
