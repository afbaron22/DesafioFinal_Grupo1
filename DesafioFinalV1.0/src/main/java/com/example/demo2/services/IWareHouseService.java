package com.example.demo2.services;


import com.example.demo2.models.Product;

import java.util.List;

public interface IWareHouseService {
   void saveBatch(List<Product> lista);
}
