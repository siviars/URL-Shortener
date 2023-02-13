package com.example.urlshortener;

import com.example.urlshortener.Objects.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepositoryVisits extends JpaRepository<Visits, Integer> {

}
