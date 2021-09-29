package com.ziytang.reflectionjson.model;

import java.util.ArrayList;
import java.util.List;

public class ListNode implements Node{
    private List<Object> list = new ArrayList<>();

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
