package com.yue.datetimedemo.dataStructure.set;

import java.util.HashMap;

public class MyHashSet {

    HashMap map;

    private static final Object PRESENT = new Object();

    public MyHashSet(){map = new HashMap();}

    public int size(){
        return map.size();
    }

    public boolean isEmpty(){
        return map.size() == 0;
    }

    public void add(Object obj){
        map.put(obj, PRESENT);
    }

    public boolean remove(Object obj){
        if(map.remove(obj) != null){
            return true;
        }else{
            return false;
        }
    }
}
