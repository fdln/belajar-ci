package com.example.tabok.ws.controller;

import com.example.tabok.ws.dao.ProductDAO;
import com.example.tabok.ws.entity.Product;
import com.example.tabok.ws.exception.DataNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import javax.validation.Valid;

/**
 * Created by Fadhlan on 2/23/16.
 */
@RestController
@RequestMapping("/api/product")
@Transactional(readOnly = true)
public class ProductController {
    @Autowired
    private ProductDAO productDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<Product> findAll(Pageable page) {
        return productDAO.findAll(page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product findById(@PathVariable("id") Product p) {
        if (p == null)
            throw new DataNotFoundException("No data with the specified id");

        return p;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Transactional(readOnly = false)
    public ResponseEntity<Void> create(@RequestBody @Valid Product p, UriComponentsBuilder
            uriBuilder) {
        try {
            System.out.println("XXXXXXXXX XXXXXXX XXXXXXXXXX");
            productDAO.save(p);
            System.out.println(p.toString());
        } catch(Exception e) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            e.printStackTrace();
        }
        URI location = uriBuilder.path("/api/product/{id}").buildAndExpand(p.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = false)
    public void update(@PathVariable("id") String id, @RequestBody @Valid Product p) {
        if (!productDAO.exists(id))
            throw new DataNotFoundException("No data with the specified id");

        p.setId(id);
        productDAO.save(p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = false)
    public void delete(@PathVariable("id") String id) {
        if (!productDAO.exists(id))
            throw new DataNotFoundException("No data with the specified id");

        productDAO.delete(id);
    }
}
