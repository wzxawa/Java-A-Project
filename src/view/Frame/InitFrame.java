package view.Frame;

import javax.swing.*;
import java.awt.*;
import view.UII.*;

public class InitFrame extends JFrame{
    private final int WIDTH=800;
    private final int HEIGHT=800;
    public InitFrame(Frame frame){
        setTitle("Match-3 Game");
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        Container container=getContentPane();

//        JLabel choose=new JLabel("hh");
//        choose.setFont(new Font("宋体",Font.BOLD,20));
//        choose.setSize(WIDTH/3,HEIGHT/4);
//        choose.setLocation(WIDTH/3,HEIGHT/10);

        JButton login=new JButton();
        JButton register=new JButton();
        login.setSize(WIDTH/5,HEIGHT/10);
        register.setSize(WIDTH/5,HEIGHT/10);
        login.setLocation(WIDTH/10*2,HEIGHT/2);
        register.setLocation(WIDTH/10*6,HEIGHT/2);

        //container.add(choose);
        container.add(login);
        container.add(register);

        new addBlackgroundPicture(this,"src\\Image\\Blackground\\电子动感键盘.jpg");
        new addButtonBlackgroundPicture(login,"src\\Image\\Button\\init\\登录.jpg");
        new addButtonBlackgroundPicture(register,"src\\Image\\Button\\init\\注册.jpg");

        new PlayBGM("src\\Music\\欢快BGM.wav");

        setVisible(true);

        login.addActionListener((e) ->{ frame.playerClickLoginButton();
                    new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");}
               );
        register.addActionListener((e)->{frame.playerClickRegisterButton();
        new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");});

    }
}
