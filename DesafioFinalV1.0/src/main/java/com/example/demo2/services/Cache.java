package com.example.demo2.services;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class Cache<T> implements ICache<T> {

    private class Node {
        private Node previous;
        private Integer key;
        private T value;
        private Node next;

        public Node(Integer key,T value) {
            this.key = key;
            this.value = value;

        }
    }
    private Node first = null;
    private Node last = null;
    private Integer size,capacity;
    private final Map<Integer, Node> hashmap = new HashMap<>();

    public Cache() {
    }

    public Cache(Integer capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    @Override
    public T cacheGetSearch(Integer id){
        if(!hashmap.containsKey(id))
            return null;
        moveNodeToFront(hashmap.get(id));
        return hashmap.get(id).value;

    }
    @Override
    public void cachePutSearch(Integer key,T value){
        Node currentNode = hashmap.get(key);
        if(currentNode != null) {
            currentNode.value = value;
            moveNodeToFront(currentNode);
            return;
        }

        if(size == capacity){
            hashmap.remove(last.key);
            removeNodeFromRear(last);
            size--;
        }

        Node node = new Node(key,value);
        addNodeToFront(node);
        hashmap.put(node.key,node);
        size++;
        System.out.println("Element added!.");
    }
    private void addNodeToFront(final Node node) {
        if(last == null) {
            first = last = node;
            return;
        }

        node.next = first;
        first.previous = node;
        first = node;
    }

    public void moveNodeToFront(Node node){

        if(first == node) return;

        if(last == node){
            last = last.previous;
            last.next = null;
        }

        else{
            node.previous.next = node.next;
            node.next.previous = node.previous;
        }
        node.previous = null;
        node.next = first;
        first.previous = node;
        first = node;
    }

    public void removeNodeFromRear(Node node){
        if(last == null) return;
        if(first == last){
            first = last = null;
        }
        else{
            last = last.previous;
            last.next = null;
        }
        System.out.println("Deleting key: "+ node.key);
        return;
    }

}
