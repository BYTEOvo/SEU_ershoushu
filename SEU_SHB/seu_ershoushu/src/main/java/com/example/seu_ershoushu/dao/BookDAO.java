package com.example.seu_ershoushu.dao;

import com.example.seu_ershoushu.pojo.Book;
import com.example.seu_ershoushu.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDAO extends JpaRepository<Book,Integer> {
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByTitleOrAuthor(String keyword1,String keyword2);
    List<Book> findAllByUsernameAndSold(String Username,int sold);
    Book findBookById(int id);
    List<Book> findAllBySold(int sold);
    List<Book> findAllByChecked(int checked);
    List<Book> findAllBySoldAndChecked(int sold,int checked);
    List<Book> findAllBySoldAndCheckedAndCategory(int sold,int checked,Category category);
}
