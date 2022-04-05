package com.example.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class CustomerDao extends JdbcDaoSupport {

    @Autowired
    private JdbcTemplate jdbctemplate;

    @PostConstruct
   private void initialize() {
       setJdbcTemplate(new JdbcTemplate());
   }

    public Customer getCustomerByName(String name){

        String query = "SELECT * FROM customers WHERE customer_name =? ";

        Customer customer = jdbctemplate.queryForObject(query, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNumber) throws SQLException {
                Customer cus = new Customer(rs.getInt("customer_id"),
                        rs.getString("customer_name"),
                        rs.getString("customer_mail"));
                return cus;
            }
        }, name);

        return customer;
    }
}
