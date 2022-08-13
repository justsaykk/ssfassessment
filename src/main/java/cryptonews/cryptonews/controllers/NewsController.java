package cryptonews.cryptonews.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cryptonews.cryptonews.models.Article;
import cryptonews.cryptonews.services.NewsService;

@Controller
@RequestMapping(path = "/")
public class NewsController {

    @Autowired
    private NewsService newsSvc;

    @GetMapping(path = { "/", "/index" })
    public String getArticles(Model model) {
        List<Article> resList = newsSvc.getArticles();
        model.addAttribute("list", resList);
        return "index";
    }

    @PostMapping(path = "/articles")
    public String saveArticles(
            @RequestBody MultiValueMap<String, String> form,
            Model model) {
        List<String> listOfIds = form.get("save");
        newsSvc.save(listOfIds);
        return "redirect:index";
    }
}
