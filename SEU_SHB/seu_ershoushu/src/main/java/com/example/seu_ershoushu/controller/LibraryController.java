package com.example.seu_ershoushu.controller;

import com.example.seu_ershoushu.pojo.Book;
import com.example.seu_ershoushu.pojo.Category;
import com.example.seu_ershoushu.service.BookService;

import com.example.seu_ershoushu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {
    @Autowired
    BookService bookService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/api/books")
    @CrossOrigin
    public List<Book> list() throws Exception {
        return bookService.list();
    }

    @GetMapping("/api/books/unsold")
    @CrossOrigin
    public List<Book> listUnSold() throws Exception {
        return bookService.listUnSold();
    }

    @GetMapping("/api/books/library")
    @CrossOrigin
    public List<Book> listUnSoldAndChecked() throws Exception {
        return bookService.listUnSoldAndChecked();
    }

    @GetMapping("/api/books/sold")
    @CrossOrigin
    public List<Book> listSold() throws Exception {
        return bookService.listSold();
    }

    @GetMapping("/api/books/unchecked")
    @CrossOrigin
    public List<Book> listUnChecked() throws Exception {
        return bookService.listUnCheck();
    }

    @GetMapping("/api/books/checked")
    @CrossOrigin
    public List<Book> listChecked() throws Exception {
        return bookService.listCheck();
    }

    @GetMapping("/api/categories/{cid}/library/books")
    @CrossOrigin
    public List<Book> listByCategory(@PathVariable("cid") int cid) throws Exception {
        if (0 != cid) {
            return bookService.listByLibraryCategory(cid);
        } else {
            return listUnSoldAndChecked();
        }
    }

    @CrossOrigin
    @PostMapping("/api/search")
    public List<Book> searchResult(@RequestParam("keywords") String keywords) throws Exception
    {
        if("".equals(keywords)){
            return bookService.list();
        }
            return bookService.Search(keywords);
    }

    @CrossOrigin
    @PostMapping("/api/library/search")
    public List<Book> searchLibraryResult(@RequestParam("keywords") String keywords) throws Exception
    {
        if("".equals(keywords)){
            return bookService.listUnSoldAndChecked();
        }
        return bookService.SearchUnsoldAndChecked(keywords);
    }

    @CrossOrigin
    @GetMapping("/api/category")
    public List<Category> listCategory(){
        return categoryService.list();
    }

    @GetMapping("/api/books/{id}")
    @CrossOrigin
    public Book findBookById(@PathVariable("id") int id) throws Exception {
        return bookService.finById(id);
    }
}

