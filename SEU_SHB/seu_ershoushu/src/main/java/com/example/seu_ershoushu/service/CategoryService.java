package com.example.seu_ershoushu.service;

import com.example.seu_ershoushu.dao.CategoryDAO;
import com.example.seu_ershoushu.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    public List<Category> list()
    {
         return categoryDAO.findAll(Sort.by(Sort.Direction.DESC,"id")) ;

    }

    public Category get(int id) {
        Category c= categoryDAO.findById(id).orElse(null);
        return c;
    }

    public int addCategory(Category category){
        String name=category.getName();
        if(categoryDAO.findCategoryByName(name)!=null)
            return 2;
        categoryDAO.save(category);
        return 1;
    }
}
