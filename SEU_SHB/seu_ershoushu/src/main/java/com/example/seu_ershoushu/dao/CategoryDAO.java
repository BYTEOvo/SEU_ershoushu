package com.example.seu_ershoushu.dao;

import com.example.seu_ershoushu.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category,Integer> {
    Category findCategoryByName(String name);
}
