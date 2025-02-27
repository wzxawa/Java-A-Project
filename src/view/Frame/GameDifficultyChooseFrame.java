package view.Frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import controller.GameController;
import model.Chessboard;
import view.UII.*;
public class GameDifficultyChooseFrame extends JFrame{
    private final int WIDTH=800;
    private final int HEIGTH=800;
    private Container container=getContentPane();
    private String player;
    public void setPlayer(String player){
        this.player=player;
    }
    private boolean automatic=false;
    public boolean getAutomatic(){
        return automatic;
    }
    public void setAutomatic(boolean auto){
        this.automatic=auto;
    }
    public GameDifficultyChooseFrame(){
        setTitle("Match-3 Games");
        setSize(WIDTH,HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addEasyButton();
        addMediumButton();
        addDifficultButton();

        new addBlackgroundPicture(this,"src\\Image\\Blackground\\电子动感键盘.jpg");

        setVisible(true);

    }
    public void StartGame(int i,int j){
        ChessGameFrame chessGameFrame=new ChessGameFrame(i,j,player,automatic);
        GameController gameController = new GameController(chessGameFrame.getChessboardComponent(), new Chessboard(0),i,j,player);
        chessGameFrame.setVisible(true);
    }

    private void addEasyButton() {    //监听器ActionListener
        JButton button = new JButton();
        //为按钮添加鼠标单击事件
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartGame(100,8);
                new SoundEffect("src\\Music\\SoundEffect\\难度选择.wav");
            }
        });
        button.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *2);
        button.setSize(WIDTH/7*2, HEIGTH/8);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\DifficultyChoose\\Easy.jpg");



        container.add(button);
    }

    private void addMediumButton() {    //监听器ActionListener
        JButton button = new JButton();
        //为按钮添加鼠标单击事件
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartGame(200,8);
                new SoundEffect("src\\Music\\SoundEffect\\难度选择.wav");
            }
        });

        button.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *5);
        button.setSize(WIDTH/7*2, HEIGTH/8);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\DifficultyChoose\\Medium.jpg");


        container.add(button);
    }

    private void addDifficultButton() {    //监听器ActionListener
        JButton button = new JButton();
        //为按钮添加鼠标单击事件
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartGame(300,8);
                new SoundEffect("src\\Music\\SoundEffect\\难度选择.wav");
            }
        });
        button.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *8);
        button.setSize(WIDTH/7*2, HEIGTH/8);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\DifficultyChoose\\Hard.jpg");


        container.add(button);
    }
}
