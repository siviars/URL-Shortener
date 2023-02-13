package com.example.urlshortener;

import com.example.urlshortener.Objects.Links;
import com.example.urlshortener.Objects.Visits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/links")
    @ResponseStatus(HttpStatus.OK)
    public Links save(@RequestBody Links longLink) {
        return urlShortenerService.saveLinks(longLink);
    }

    @GetMapping("/visits")
    public ResponseEntity<List<Visits>> getVisits() {
        return new ResponseEntity<>(urlShortenerService.getVisits(), HttpStatus.OK);
    }

    @GetMapping(value = "/{redirectUrl}")
    public RedirectView redirect(@PathVariable("redirectUrl") String redirectUrl) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(urlShortenerService.getRedirectURL(redirectUrl));
        return redirectView;
    }

}