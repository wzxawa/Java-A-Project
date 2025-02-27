package model;


import java.awt.*;

public class ChessPiece {
    // Diamond, Circle, ...
    private String name;

    private Color color;
    private String imageLightPath;
    private String imageDarkPath;
    private int num;

    public ChessPiece(int num) {
        this.name = name;
        this.color = Constant.colorMap.get(name);
        this.num=num;
        this.imageLightPath=Constant.imageLightMap.get(num);
        this.imageDarkPath=Constant.imageDarkMap.get(num);
    }

    public String getName() {
        return name;
    }

    public Color getColor(){return color;}
    public String getImageLightPath(){
        return imageLightPath;
    }
    public String getImageDarkPath(){
        return imageDarkPath;
    }
    public int getNum(){
        return num;
    }

}
