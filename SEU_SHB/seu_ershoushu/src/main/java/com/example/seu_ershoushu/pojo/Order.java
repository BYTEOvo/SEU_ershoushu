package com.example.seu_ershoushu.pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="orders")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "bid")
    private Book book;

    String seller;
    String buyer;
    String state;
    String send;
    String receive;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH-mm-ss")
    Date begin;

    @DateTimeFormat(pattern ="yyyy-MM-dd HH-mm-ss")
    Date end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getSeller() {
        return seller;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getReceive() {
        return receive;
    }

    public String getSend() {
        return send;
    }

    public String getState() {
        return state;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public void setState(String state) {
        this.state = state;
    }
}
