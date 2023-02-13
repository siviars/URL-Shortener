package com.example.urlshortener;

import com.example.urlshortener.Objects.Links;
import com.example.urlshortener.Objects.Visits;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
class UrlShortenerService {

    private final UrlShortenerRepositoryLinks urlShortenerRepositoryLinks;

    private final UrlShortenerRepositoryVisits urlShortenerRepositoryVisits;

    private final String hostURL = "http://localhost:8080/";

    public UrlShortenerService(UrlShortenerRepositoryLinks urlShortenerRepositoryLinks,
                               UrlShortenerRepositoryVisits urlShortenerRepositoryVisits) {
        this.urlShortenerRepositoryLinks = urlShortenerRepositoryLinks;
        this.urlShortenerRepositoryVisits = urlShortenerRepositoryVisits;
    }

    public Links saveLinks(Links link) {
        if (link.getUrl() != null && !link.getUrl().equals("")) {
            if (longUrlTest(link.getUrl())) {
                String generateLink = hostURL + urlGenerator();
                link.setShortURL(generateLink);
                link.setCreateDate(getNowDateTime());
                urlShortenerRepositoryLinks.save(link);
                return link;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public String getRedirectURL(String redirectURL) {
        try {
            Links returnURL = urlShortenerRepositoryLinks.getRedirectURL(hostURL + redirectURL);
            saveVisits(returnURL);
            checkExpiration(returnURL);
            return returnURL.getUrl();
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private String urlGenerator() {
        Random random = new Random();
        String randomURL = "";
        int charInterval = 65;
        for (int count = 0; count < 6; count++) {
            if (Math.random() > 0.5) {
                charInterval = 97;
            }
            randomURL = randomURL + (char) (random.nextInt(25) + charInterval);
        }
        return randomURL;
    }

    public List<Visits> getVisits() {
        return urlShortenerRepositoryVisits.findAll();
    }

    private boolean longUrlTest(String url) {
        if (url.length() > 12) {
            if (url.trim().startsWith("https://www.") && url.trim().startsWith("http://www.")) {
                return true;
            }
        }
        if (url.length() > 4) {
            if (url.trim().startsWith("www.")) {
                return true;
            }
        }
        try {
            (new java.net.URL(url)).openStream().close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private LocalDateTime getNowDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        String dateTimeText = dateTimeNow.format(formatter);
        return LocalDateTime.parse(dateTimeText, formatter);
    }

    private void saveVisits(Links visitsURL) {
        Visits saveVisits = new Visits();
        saveVisits.setUrl(visitsURL.getUrl());
        saveVisits.setShortUrl(visitsURL.getShortURL());
        saveVisits.setVisitDate(getNowDateTime());
        urlShortenerRepositoryVisits.save(saveVisits);
    }

    private void checkExpiration(Links expirationURL) {
        if (expirationURL.getUnit().equals("hours")) {
            if (expirationURL.getCreateDate().plusHours(expirationURL.getAmount()).compareTo(getNowDateTime()) < 0) {
                urlShortenerRepositoryLinks.deleteById(expirationURL.getId());
            }
        } else {
            if (expirationURL.getCreateDate().plusDays(expirationURL.getAmount()).compareTo(getNowDateTime()) < 0) {
                urlShortenerRepositoryLinks.deleteById(expirationURL.getId());
            }
        }
    }

}
