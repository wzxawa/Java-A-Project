package model;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum Constant {
    CHESSBOARD_ROW_SIZE(8),CHESSBOARD_COL_SIZE(8);

    private final int num;
    Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    static Map<String, Color> colorMap = new HashMap<>(){{
        put("💎",Color.blue);
        put("⚪",Color.white);
        put("▲",Color.green);
        put("🔶",Color.orange);
    }};

    static Map<Integer, String> imageLightMap = new HashMap<>(){{
        put(1,"src\\Image\\Chess_Light\\1_狮子_Light.jpg");
        put(2,"src\\Image\\Chess_Light\\2_猪_Light.jpg");
        put(3,"src\\Image\\Chess_Light\\3_猴_Light.jpg");
        put(4,"src\\Image\\Chess_Light\\4_羊_Light.jpg");
    }};

    static Map<Integer, String> imageDarkMap = new HashMap<>(){{
        put(1,"src\\Image\\Chess_Dark\\1_狮子_Dark.jpg");
        put(2,"src\\Image\\Chess_Dark\\2_猪_Dark.jpg");
        put(3,"src\\Image\\Chess_Dark\\3_猴_Dark.jpg");
        put(4,"src\\Image\\Chess_Dark\\4_羊_Dark.jpg");
    }};


}
