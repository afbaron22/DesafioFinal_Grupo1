package com.example.demo2.services;

public interface ICache<T>{
    T cacheGetSearch(Integer id);
    void cachePutSearch(Integer key,T value);
}
