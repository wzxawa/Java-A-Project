package view;


import controller.GameController;
import model.ChessPiece;
import model.*;
import view.UII.SoundEffect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;


/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ChessComponent extends JComponent {

    private boolean selected;

    private ChessPiece chessPiece;
    private int WIDTH;
    private int HEIGHT;
    private JLabel lightLabel;
    private JLabel darkLabel;
    private boolean Hover;

    private GameController gameController;
    private ChessboardPoint point;

    public ChessComponent(int size, ChessPiece chessPiece,GameController gameController,ChessboardPoint point) {
        this.selected = false;
        this.Hover=false;
        WIDTH=size;
        HEIGHT=size;
        setSize(WIDTH,HEIGHT);
        setLocation(0,0);
        setVisible(true);
        this.chessPiece = chessPiece;
        repaint();
        this.gameController=gameController;
        this.point=point;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Hover = true;
                repaint(); // 重绘组件以显示新的图片
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Hover = false;
                repaint(); // 重绘组件以显示默认图片
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.print("One chess here and ");
                new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");
                gameController.onPlayerClickChessPiece
                        (point, ChessComponent.this);

            }
        });



    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);//清除背景
        if (selected){
            g.drawImage(returnDark(), 0, 0, this); // 在组件上绘制图像
        } else if (Hover) {
            g.drawImage(returnDark(), 0, 0, this); // 绘制鼠标滑过时的图片
        }else {
            g.drawImage(returnLight(), 0, 0, this); // 绘制默认图片
        }


    }

    public BufferedImage returnLight(){
        BufferedImage light=null;
        // 加载背景图片
        try {
            BufferedImage backgroundImage =
                    ImageIO.read(new File(chessPiece.getImageLightPath()));

            // 获取chess的大小
            int chessWidth = WIDTH;
            int chessHeight = HEIGHT;

            // 计算背景图片的宽度和高度
            int imageWidth = backgroundImage.getWidth();
            int imageHeight = backgroundImage.getHeight();

            // 计算缩放比例，使图片适应JFrame的大小
            double scale = Math.max((double) chessWidth/ imageWidth, (double) chessWidth / imageHeight);
            int scaledWidth = (int) (imageWidth * scale);
            int scaledHeight = (int) (imageHeight * scale);

            int imageX=(int)(chessWidth-scaledWidth)/2;
            int imageY=(int)(chessHeight-scaledHeight)/2;



            // 创建一个新的BufferedImage，大小与JFrame一样，并使用缩放后的图片填充它
            //TYPE_INT_ARGB表示将8位RGBA颜色组件打包成整数像素的图像
            BufferedImage resizedImage = new BufferedImage(chessWidth, chessHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(backgroundImage, imageX,imageY, scaledWidth, scaledHeight, null);
            //在调用dispose之后，不能使用图形对象
            g.dispose();
            light=resizedImage;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return light;
    }


    public BufferedImage returnDark(){
        BufferedImage dark=null;
        // 加载背景图片
        try {
            BufferedImage backgroundImage =
                    ImageIO.read(new File(chessPiece.getImageDarkPath()));

            // 获取chess的大小
            int chessWidth = WIDTH;
            int chessHeight = HEIGHT;

            // 计算背景图片的宽度和高度
            int imageWidth = backgroundImage.getWidth();
            int imageHeight = backgroundImage.getHeight();

            // 计算缩放比例，使图片适应JFrame的大小
            double scale = Math.max((double) chessWidth/ imageWidth, (double) chessWidth / imageHeight);
            int scaledWidth = (int) (imageWidth * scale);
            int scaledHeight = (int) (imageHeight * scale);

            int imageX=(int)(chessWidth-scaledWidth)/2;
            int imageY=(int)(chessHeight-scaledHeight)/2;



            // 创建一个新的BufferedImage，大小与JFrame一样，并使用缩放后的图片填充它
            //TYPE_INT_ARGB表示将8位RGBA颜色组件打包成整数像素的图像
            BufferedImage resizedImage = new BufferedImage(chessWidth, chessHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(backgroundImage, imageX,imageY, scaledWidth, scaledHeight, null);
            //在调用dispose之后，不能使用图形对象
            g.dispose();

            dark=resizedImage;







        } catch (IOException e) {
            e.printStackTrace();
        }
        return dark;
    }



    public void paintChess(){


        if (isSelected()) { // 显示dark图片
            // 加载背景图片
            try {
                BufferedImage backgroundImage =
                        ImageIO.read(new File(chessPiece.getImageDarkPath()));

                // 获取chess的大小
                int chessWidth = WIDTH;
                int chessHeight = HEIGHT;

                // 计算背景图片的宽度和高度
                int imageWidth = backgroundImage.getWidth();
                int imageHeight = backgroundImage.getHeight();

                // 计算缩放比例，使图片适应JFrame的大小
                double scale = Math.max((double) chessWidth/ imageWidth, (double) chessWidth / imageHeight);
                int scaledWidth = (int) (imageWidth * scale);
                int scaledHeight = (int) (imageHeight * scale);

                int imageX=(int)(chessWidth-scaledWidth)/2;
                int imageY=(int)(chessHeight-scaledHeight)/2;



                // 创建一个新的BufferedImage，大小与JFrame一样，并使用缩放后的图片填充它
                //TYPE_INT_ARGB表示将8位RGBA颜色组件打包成整数像素的图像
                BufferedImage resizedImage = new BufferedImage(chessWidth, chessHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(backgroundImage, imageX,imageY, scaledWidth, scaledHeight, null);
                //在调用dispose之后，不能使用图形对象
                g.dispose();

                // 创建一个JLabel并设置背景图片


                darkLabel = new JLabel(new ImageIcon(resizedImage));




                darkLabel.setOpaque(true); // 让标签不透明，这样背景图片可见
                darkLabel.setBounds(0,0,chessWidth,chessHeight);
                lightLabel.remove(this);

                add(darkLabel);







            } catch (IOException e) {
                e.printStackTrace();
            }


        }else {  //显示Light图片
            // 加载背景图片
            try {
                BufferedImage backgroundImage =
                        ImageIO.read(new File(chessPiece.getImageLightPath()));

                // 获取chess的大小
                int chessWidth = WIDTH;
                int chessHeight = HEIGHT;

                // 计算背景图片的宽度和高度
                int imageWidth = backgroundImage.getWidth();
                int imageHeight = backgroundImage.getHeight();

                // 计算缩放比例，使图片适应JFrame的大小
                double scale = Math.max((double) chessWidth/ imageWidth, (double) chessWidth / imageHeight);
                int scaledWidth = (int) (imageWidth * scale);
                int scaledHeight = (int) (imageHeight * scale);

                int imageX=(int)(chessWidth-scaledWidth)/2;
                int imageY=(int)(chessHeight-scaledHeight)/2;



                // 创建一个新的BufferedImage，大小与JFrame一样，并使用缩放后的图片填充它
                //TYPE_INT_ARGB表示将8位RGBA颜色组件打包成整数像素的图像
                BufferedImage resizedImage = new BufferedImage(chessWidth, chessHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = resizedImage.createGraphics();
                g.drawImage(backgroundImage, imageX,imageY, scaledWidth, scaledHeight, null);
                //在调用dispose之后，不能使用图形对象
                g.dispose();

                // 创建一个JLabel并设置背景图片


                lightLabel = new JLabel(new ImageIcon(resizedImage));




                lightLabel.setOpaque(true); // 让标签不透明，这样背景图片可见
                lightLabel.setBounds(0,0,chessWidth,chessHeight);

                add(lightLabel);







            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setHover(boolean Hover){
        this.Hover=Hover;
    }
    public boolean isHover(){
        return Hover;
    }





}
