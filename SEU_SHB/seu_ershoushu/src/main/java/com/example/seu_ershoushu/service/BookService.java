package com.example.seu_ershoushu.service;

import com.example.seu_ershoushu.dao.BookDAO;
import com.example.seu_ershoushu.pojo.Book;
import com.example.seu_ershoushu.pojo.Category;
import com.example.seu_ershoushu.utils.CategoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDAO bookDAO;
    @Autowired
    CategoryService categoryService;

    public List<Book> list() {
        return bookDAO.findAll(Sort.by(Sort.Direction.DESC,"id")) ;
    }

    public void addOrUpdate(Book book) {
        bookDAO.save(book);
    }

    public void deleteById(int id) {
        bookDAO.deleteById(id);
    }

    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return bookDAO.findAllByCategory(category);
    }
    public List<Book> listByUserName(String username){return bookDAO.findAllByUsernameAndSold(username,0);}
    public List<Book> Search(String keywords)
    {
        return bookDAO.findAllByTitleOrAuthor(keywords,keywords);
    }
    public Book finById(int id){return bookDAO.findBookById(id);}
    public List<Book> listUnSold(){return bookDAO.findAllBySold(0);}
    public List<Book> listSold(){return bookDAO.findAllBySold(1);}
    public List<Book> listCheck(){return bookDAO.findAllByChecked(1);}
    public List<Book> listUnCheck(){return bookDAO.findAllByChecked(0);}
    public List<Book> listUnSoldAndChecked(){return bookDAO.findAllBySoldAndChecked(0,1);}
    public List<Book> listByLibraryCategory(int cid){
        Category category=categoryService.get(cid);
        return bookDAO.findAllBySoldAndCheckedAndCategory(0,1,category);}
    public List<Book> SearchUnsoldAndChecked(String keyword){
        List<Book> bookList=bookDAO.findAllByTitleOrAuthor(keyword,keyword);
        for(int i=0;i<bookList.size();i++)
        {
            Book book=bookList.get(i);
            if(book.getChecked()==0||book.getSold()==1){
                bookList.remove(i);
            }
        }
        return bookList;
    }

    public List<Book> UserSearch(String username,String keywords){
        List<Book> bookList=bookDAO.findAllByTitleOrAuthor(keywords,keywords);
        for(int i=0;i<bookList.size();i++)
        {
            Book book=bookList.get(i);
            if(!book.getUsername().trim().equals(username)){
                bookList.remove(i);
            }
        }
        return bookList;
    }

    public int getBookNumber() {
        return listUnSoldAndChecked().size();
    }

    public CategoryUtils[] getBookCategoryNumber() {
        List<Category> categories=categoryService.list();
        List<Book> bookList=listUnSoldAndChecked();
        CategoryUtils[] nums=new CategoryUtils[categories.size()];
        for(int i=0;i<categories.size()-1;i++){
            nums[i]=new CategoryUtils();
            nums[i].setName(categories.get(i).getName());
        }
        for(int i=0;i< bookList.size();i++){
            for(int j=0;j< categories.size();j++){
                if(bookList.get(i).getCategory().getName().equals(categories.get(j).getName())){
                    nums[j].setValue(nums[j].getValue()+1);
                    break;
                }
            }
        }
        return nums;
    }
}
