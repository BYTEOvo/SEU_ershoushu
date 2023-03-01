package com.example.seu_ershoushu.dao;

import com.example.seu_ershoushu.pojo.Complaint;
import com.example.seu_ershoushu.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintDAO extends JpaRepository<Complaint,Integer> {
    public List<Complaint> findAllByUsername(String username);
    public Complaint findByOrder_Id(int oid);
}

