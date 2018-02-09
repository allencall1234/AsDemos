package com.zlt.whiteblackchess;

/**
 * Created by 524202 on 2018/2/9.
 */

public class Cell {
    int x = 0;
    int y = 0;
    boolean isBlack = true;
    int id = 0;

    public Cell() {

    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean isBlack) {
        this.isBlack = isBlack;
    }
}
