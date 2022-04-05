package com.example.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    public Customer getCustomerByName(String name){
        return  customerDao.getCustomerByName(name);
    }
}
