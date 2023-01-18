package com.c2psi.businessmanagement.repositories.stock.product;

import com.c2psi.businessmanagement.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
