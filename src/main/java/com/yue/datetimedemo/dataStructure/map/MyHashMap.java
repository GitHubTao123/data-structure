package com.yue.datetimedemo.dataStructure.map;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 使用尾插法，数组 + 链表形式存储数据
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K,V> {

    //哈希表初始容量
    static int initial_capacity = 4;
    //扩容因子
    float load_factor = 0.75f;
    //entry数量
    int count = 0;

    Entry<K,V>[] table;

    public int size(){
        return count;
    }

    public boolean isEmpty(){
        return count == 0;
    }

    public K put(K key,V value){
        Entry<K,V> newEntry = new Entry(key,value);
        if(table == null){
            table = new Entry[initial_capacity];
        }
        count++;
        if(count > initial_capacity * load_factor){
            resize();
        }
        int hash = hash(key);
        Entry<K,V> entry = table[hash];
        if(entry == null){
            table[hash] = newEntry;
        }else{
            newEntry.next = table[hash];
            table[hash] = newEntry;
        }
        return key;
    }

    public V get(K key){
        Entry<K, V> entry = getEntry(key);
        return entry.value;
    }

    public boolean containsKey(K key){
        return getEntry(key) != null;
    }

    public void remove(K key){
        int hash = hash(key);
        Entry entry = table[hash],pre = null;
        while(entry != null){
            if(entry.key.hashCode() == key.hashCode() && entry.key.equals(key)) {
                if (pre == null) {
                    table[hash] = entry.next;
                } else {
                    pre.next = entry.next;
                }
                count--;
                return;
            }
            pre = entry;
            entry = entry.next;
        }
    }

    public Set<K> keySet(){
        Set<K> kSet = new HashSet<>();
        for(int i = 0;i<table.length;i++){
            Entry<K,V> entry = table[i];
            while(entry != null){
                kSet.add(entry.key);
                entry = entry.next;
            }
        }
        return kSet;
    }

    public Set<V> values() {
        Set<V> values = new HashSet<>();
        for(int i = 0;i<table.length;i++){
            Entry<K,V> entry = table[i];
            while(entry != null){
                values.add(entry.value);
                entry = entry.next;
            }
        }
        return values;
    }

    private Entry<K,V> getEntry(K key){
        int hash = hash(key);
        Entry entry = table[hash];
        if(key == null){
            while (entry != null){
                if(entry.value == null){
                    return entry;
                }
            }
        }else{
            while(entry != null){
                if(entry.key.hashCode() == key.hashCode() && entry.key.equals(key)){
                    return entry;
                }
            }
        }
        return null;
    }

    private void resize() {
        int newCapacity = initial_capacity << 2;//初始容量 <<2 为2^2 = 4 即4*4 = 16
        initial_capacity = newCapacity;
        Entry<K,V>[] newTable = new Entry[initial_capacity];
        transfer(newTable,true);
    }

    //更新hash算法，重排数据
    private void transfer(Entry<K,V>[] newTable, boolean rehash) {
        int hash;
        for(Entry<K,V> entry : table){
            while(entry != null){
                if(rehash){
                    hash = hash(entry.key);
                    if(newTable[hash] == null){
                        newTable[hash] = entry;
                    }else{
                        entry.next = newTable[hash];
                        newTable[hash] = entry;
                    }
                }
                entry = entry.next;
            }
        }
        table = newTable;
    }

    private int hash(K key) {
        int h;
        return ((key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)) % initial_capacity;//保证计算结果在Entry 数组容量内
    }

    @Data
    class Entry<K,V>{
        int hash;
        K key;
        V value;
        Entry<K,V> next;

        public Entry() {
        }

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
