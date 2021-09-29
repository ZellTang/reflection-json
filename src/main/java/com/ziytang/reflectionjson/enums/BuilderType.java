package com.ziytang.reflectionjson.enums;

public enum BuilderType {
    MyType(0),
    JacksonType(1);

    private int id;

    BuilderType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static BuilderType getType(int id) {
        BuilderType res = null;
        switch (id) {
            case 0:
                res = BuilderType.MyType;
                break;
            case 1:
                res = BuilderType.JacksonType;
                break;
            default:
                break;
        }
        if (res == null) {
            throw new RuntimeException(String.format("No such BuilderType for given id: %d", id));
        }
        return res;
    }
}
