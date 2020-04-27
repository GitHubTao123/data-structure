package com.yue.datetimedemo.dataStructure.arraylist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class MyArrayList<T> {

    private static final Logger log = LoggerFactory.getLogger(MyArrayList.class);

    int size;
    private Object[] array = null;
    private static final Object[] EMPTY_ARRAY = {};

    public MyArrayList() {
        this(10);
    }

    public MyArrayList(int initCapacity) {
        if(initCapacity < 0){
            throw new IllegalArgumentException("initCapacity必须大于零");
        }else if(initCapacity == 0){
            array = EMPTY_ARRAY;
        }else{
            array = new Object[initCapacity];
        }
    }

    public MyArrayList(Collection<? extends T> c) {
        array = c.toArray();
        if((size = array.length) != 0){
            if(array.getClass() == Object[].class){
                array = Arrays.copyOf(array,size,Object[].class);
            }else{
                array = EMPTY_ARRAY;
            }
        }
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void add(Object obj){
        ensureCapacity();
        array[size++] = obj;
    }

    public void add(int index,Object obj){
        rangeCheck(index);
        ensureCapacity();
        System.arraycopy(array,index,array,index+1,size-index);
        array[index] = obj;
        size++;
    }

    public Object remove(int index){
        rangeCheck(index);
        Object oldObject = array[index];
        System.arraycopy(array,index+1,array,index,size-index-1);
        log.info("remove index ,and it's size : " + size);
        array[size--] = null;
        log.info(""+array[size]);
        return oldObject;
    }

    public boolean remove(Object object){
        if(object == null){
            for(int i = 0;i < size;i++){
                if(array[i] == null){
                    remove(i);
                    return true;
                }
            }
        }else{
            for(int i = 0;i < size;i++){
                if(object.equals(array[i])){
                    remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public Object get(int index){
        rangeCheck(index);
        return array[index];
    }

    public void set(int index,Object obj){
        rangeCheck(index);
        array[index] = obj;
    }

    private void rangeCheck(int index) {
        if(size <= index || index < 0){
            throw new IndexOutOfBoundsException("超出索引范围");
        }
    }

    private void ensureCapacity() {
        if(size == array.length){
            log.info("grow capacity,new size : " + (int)1.5*size);
            Object[] newArray = new Object[(int)1.5*size];
            System.arraycopy(array,0,newArray,0,array.length);
            array = newArray;
        }
    }
}
