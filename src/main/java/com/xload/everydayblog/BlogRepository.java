package com.xload.everydayblog;

import com.xload.everydayblog.entities.BlogText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogText, String> {
}
