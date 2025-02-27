package view.UII;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class addBlackgroundPicture {
    private JFrame frame;

    public addBlackgroundPicture(JFrame frame,String filePath){
        this.frame=frame;



        // 加载背景图片
        try {
            BufferedImage backgroundImage =
                    ImageIO.read(new File(filePath));

            // 获取JFrame的大小
            int frameWidth = frame.getWidth(); // 修改为你的JFrame宽度
            int frameHeight = frame.getHeight(); // 修改为你的JFrame高度

            // 计算背景图片的宽度和高度
            int imageWidth = backgroundImage.getWidth();
            int imageHeight = backgroundImage.getHeight();

            // 计算缩放比例，使图片适应JFrame的大小
            double scale = Math.max((double) frameWidth / imageWidth, (double) frameHeight / imageHeight);
            int scaledWidth = (int) (imageWidth * scale);
            int scaledHeight = (int) (imageHeight * scale);

            int imageX=(int)(frameWidth-scaledWidth)/2;
            int imageY=(int)(frameHeight-scaledHeight)/2;



            // 创建一个新的BufferedImage，大小与JFrame一样，并使用缩放后的图片填充它
            //TYPE_INT_ARGB表示将8位RGBA颜色组件打包成整数像素的图像
            BufferedImage resizedImage = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(backgroundImage, imageX,imageY, scaledWidth, scaledHeight, null);
            //在调用dispose之后，不能使用图形对象
            g.dispose();

            // 创建一个JLabel并设置背景图片

            JLabel label = new JLabel(new ImageIcon(resizedImage));




            label.setOpaque(true); // 让标签不透明，这样背景图片可见
            label.setBounds(0,0,frameWidth,frameHeight);



            frame.add(label);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
