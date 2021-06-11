package com.example.demo_bootcamp_spring.services;


import com.example.demo_bootcamp_spring.models.Product;

import java.util.List;

public interface IWareHouseService {
   void saveBatch(List<Product> lista);
}
