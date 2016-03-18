package com.example.tabok.ws.dao;

import com.example.tabok.ws.TabokwsApplication;
import com.example.tabok.ws.entity.Product;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import javax.transaction.Transactional;

/**
 * Created by Fadhlan on 2/23/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TabokwsApplication.class)
@Transactional
@Sql(scripts = {"/mysql/delete-data.sql", "/mysql/sample-product.sql"})
public class ProductDAOTests {
    @Autowired private ProductDAO pd;

    @Test
    public void testSave() {
        Product p = new Product();
        p.setCode("T-001");
        p.setName("Test Product 001");
        p.setPrice(new BigDecimal("100000.01"));

        Assert.assertNull(p.getId());
        pd.save(p);
        Assert.assertNotNull(p.getId());
    }

    @Test
    public void testFindById() {
        Product p = pd.findOne("abc123");
        Assert.assertNotNull(p);
        Assert.assertEquals("P-001", p.getCode());
        Assert.assertEquals("Product 001", p.getName());
        Assert.assertEquals(BigDecimal.valueOf(101000.01), p.getPrice());

        Assert.assertNull(pd.findOne("notexist"));
    }
}
