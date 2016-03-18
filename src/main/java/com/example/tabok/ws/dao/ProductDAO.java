package com.example.tabok.ws.dao;

import com.example.tabok.ws.entity.Product;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductDAO extends PagingAndSortingRepository<Product, String> {}