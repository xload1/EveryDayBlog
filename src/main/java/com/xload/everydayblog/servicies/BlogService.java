package com.xload.everydayblog.servicies;

import com.xload.everydayblog.BlogRepository;
import com.xload.everydayblog.entities.BlogText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    private BlogRepository blogRepository;
    @Autowired
    public void setBlogRepository(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public void update(BlogText blogText){
        blogRepository.save(blogText);
    }
    public BlogText getByDate(String date){
        if(blogRepository.findById(date).isEmpty()) {
            blogRepository.save(new BlogText(date, null));
        }
        return blogRepository.findById(date).get();
    }
    public List<LocalDate> getDates(){
//        List<String> list = new ArrayList<>();
        LocalDate from = LocalDate.now().minusDays(5);
        LocalDate to = LocalDate.now().plusDays(5);
        return from.datesUntil(to).collect(Collectors.toList());
    }
}
