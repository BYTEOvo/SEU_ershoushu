package com.example.seu_ershoushu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "book")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne
    @JoinColumn(name = "cid")
    private Category category;

    double newness;
    String username;
    String title;
    String synopsis;
    double price;
    String author;
    String cover;
    int checked;
    int sold;

    public void setTitle(String name) {
        this.title = name;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getCover() {return cover;}

    public String getSynopsis() {
        return synopsis;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCover(String cover) {this.cover = cover;}

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public double getNewness() {
        return newness;
    }

    public void setNewness(double newness) {
        this.newness = newness;
    }


    public int getSold() {
        return sold;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
