package cryptonews.cryptonews.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cryptonews.cryptonews.services.NewsService;

@RestController
@RequestMapping(path = "/news")
public class NewsRESTController {

    @Autowired
    private NewsService newsSvc;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getId(
            @PathVariable(value = "id") String id) {
        String responseObj = newsSvc.getArticles(id).toString();
        return new ResponseEntity<String>(responseObj, HttpStatus.OK);
    }
}
