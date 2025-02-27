package view.UII;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class addButtonBlackgroundPicture {
    public addButtonBlackgroundPicture(JButton Button,String pathname){
        // 加载背景图片
        try {
            BufferedImage backgroundImage =
                    ImageIO.read(new File(pathname));


            int ButtonWidth = Button.getWidth(); // 修改为你的Button宽度
            int ButtonHeight = Button.getHeight(); // 修改为你的Button高度

            // 计算背景图片的宽度和高度
            int imageWidth = backgroundImage.getWidth();
            int imageHeight = backgroundImage.getHeight();

            // 计算缩放比例，使图片适应Button的大小
            double scale = Math.max((double) ButtonWidth / imageWidth, (double) ButtonHeight / imageHeight);
            int scaledWidth = (int) (imageWidth * scale);
            int scaledHeight = (int) (imageHeight * scale);

            int imageX=(int)(ButtonWidth-scaledWidth)/2;
            int imageY=(int)(ButtonHeight-scaledHeight)/2;



            // 创建一个新的BufferedImage，大小与Button一样，并使用缩放后的图片填充它
            //TYPE_INT_ARGB表示将8位RGBA颜色组件打包成整数像素的图像
            BufferedImage resizedImage = new BufferedImage(ButtonWidth, ButtonHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(backgroundImage, imageX,imageY, scaledWidth, scaledHeight, null);
            //在调用dispose之后，不能使用图形对象
            g.dispose();




            Button.setIcon(new ImageIcon(resizedImage));
            Button.setOpaque(true);  // 让Button不透明，这样背景图片可见





        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
