package com.xload.everydayblog.controllers;

import com.xload.everydayblog.entities.BlogText;
import com.xload.everydayblog.servicies.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {
    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/")
    public String blogs(Model model) {
        List<Pair<LocalDate, String>> pairList = new ArrayList<>();
        blogService.getDates().forEach(date -> {
            if(date.equals(LocalDate.now())) pairList.add(Pair.of
                    (date, blogService.getByDate(date.toString()).getHeading()+" (TODAY)"));
            else pairList.add(Pair.of
                    (date, blogService.getByDate(date.toString()).getHeading()));
        });
        model.addAttribute("pairList", pairList);
        return "blogs";
    }

    @GetMapping("/{date}")
    public String text(@PathVariable String date, Model model) {
        model.addAttribute("date", date);
        model.addAttribute("text", blogService.getByDate(date).getText());
        model.addAttribute("heading", blogService.getByDate(date).getHeading());
        return "text";
    }

    @PostMapping("/{date}")
    public String text(@RequestParam("text") String text, @RequestParam("heading") String heading, @PathVariable String date, Model model) {
        blogService.update(new BlogText(date, text, heading));
        model.addAttribute("date", date);
        model.addAttribute("text", blogService.getByDate(date).getText());
        model.addAttribute("heading", blogService.getByDate(date).getHeading());
        return "text";
    }

    @GetMapping("/searchDate")
    public String search(@RequestParam("redirectDate") String date) {
//        if(date==null) return "redirect:/blog/";
        return "redirect:/blog/" + date;
    }

    @PostMapping("/{date}/clear")
    public String clearText(@PathVariable String date) {
        blogService.update(new BlogText(date, "", ""));
        return "redirect:/blog/" + date;
    }

    @GetMapping("/{date}/next")
    public String nextDate(@PathVariable String date) {
        return "redirect:/blog/" + LocalDate.parse(date).plusDays(1);
    }

    @GetMapping("/{date}/previous")
    public String previousDate(@PathVariable String date) {
        return "redirect:/blog/" + LocalDate.parse(date).minusDays(1);
    }
}
