package com.example.back.entity;

public class Element {
    private String openid;
    private String src;
    private double x;
    private double y;
    private int width;
    private int height;
    private double angle;
    private String canvasid;

    public String getCanvasid() {
        return canvasid;
    }

    public void setCanvasid(String canvasid) {
        this.canvasid = canvasid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }



    // Getter和Setter方法
    public String getSrc() { return src; }
    public void setSrc(String src) { this.src = src; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public double getAngle() { return angle; }
    public void setAngle(double angle) { this.angle = angle; }
    @Override
    public String toString() {
        return "Element{" +
                "src='" + src + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", angle=" + angle +
                '}';
    }
}
