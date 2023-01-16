package com.xload.everydayblog.controllers;

import com.xload.everydayblog.entities.BlogText;
import com.xload.everydayblog.servicies.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blog")
public class BlogController {
    private BlogService blogService;
    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/")
    public String blogs(Model model){
          model.addAttribute("dates", blogService.getDates());
          return "blogs";
    }
    @GetMapping("/{date}")
    public String text(@PathVariable String date, Model model){
        model.addAttribute("date", date);
        model.addAttribute("text", blogService.getByDate(date).getText());
        return "text";
    }
    @PostMapping("/{date}")
    public String text(@RequestParam("text") String text, @PathVariable String date, Model model){
        blogService.update(new BlogText(date, text));
        model.addAttribute("date", date);
        model.addAttribute("text", blogService.getByDate(date).getText());
        return "text";
    }
    @GetMapping("/searchDate")
    public String search(@RequestParam("redirectDate")String date){
//        if(date==null) return "redirect:/blog/";
        return "redirect:/blog/"+date;
    }
    @PostMapping("/{date}/clear")
    public String clearText(@PathVariable String date){
        blogService.update(new BlogText(date, null));
        return "redirect:/blog/"+date;
    }
}
