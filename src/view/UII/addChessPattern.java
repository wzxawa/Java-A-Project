package view.UII;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class addChessPattern {
    public addChessPattern(JComponent component,String pathName){

        // 加载背景图片
        try {
            BufferedImage backgroundImage =
                    ImageIO.read(new File(pathName));

            // 获取component的大小
            int componentWidth = component.getWidth(); // 修改为你的component宽度
            int componentHeight =component.getHeight(); // 修改为你的component高度

            // 计算背景图片的宽度和高度
            int imageWidth = backgroundImage.getWidth();
            int imageHeight = backgroundImage.getHeight();

            // 计算缩放比例，使图片适应JFrame的大小
            double scale = Math.max((double)componentWidth / imageWidth, (double) componentHeight / imageHeight);
            int scaledWidth = (int) (imageWidth * scale);
            int scaledHeight = (int) (imageHeight * scale);

            int imageX=(int)(componentWidth-scaledWidth)/2;
            int imageY=(int)(componentHeight-scaledHeight)/2;



            // 创建一个新的BufferedImage，大小与component一样，并使用缩放后的图片填充它
            //TYPE_INT_ARGB表示将8位RGBA颜色组件打包成整数像素的图像
            BufferedImage resizedImage = new BufferedImage(componentWidth, componentHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(backgroundImage, imageX,imageY, scaledWidth, scaledHeight, null);
            //在调用dispose之后，不能使用图形对象
            g.dispose();

            // 创建一个JLabel并设置背景图片

            JLabel label = new JLabel(new ImageIcon(resizedImage));




            label.setOpaque(true); // 让标签不透明，这样背景图片可见
            label.setBounds(0,0,componentWidth,componentHeight);

            component.add(label);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
