package com.example.demo_bootcamp_spring.services;

public interface ICache<T>{
    T cacheGetSearch(Integer id);
    void cachePutSearch(Integer key,T value);
}
