package view.Frame;

import javax.swing.*;
import java.awt.*;
import view.UII.*;

public class RegisterFrame extends JFrame{
    private final int WIDTH=800;
    private final int HEIGTH=800;

    private JTextField name;
    private JTextField code;
    private JTextField codeComfirm;

    private JLabel nameLabel;
    private JLabel codeLabel;
    private JLabel codeAgainLabel;

    private JButton comfirm;
    private Container container=getContentPane();
    private Frame frame;

    public RegisterFrame(Frame frame){
        //setTitle("");
        this.frame=frame;
        setSize(WIDTH,HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        initName();
        initCode();
        initCodeComfirm();
        initComfirm();

        initNameLabel();
        initCodeLabel();
        initCodeAgainLabel();

        new addBlackgroundPicture(this,"src\\Image\\Blackground\\电子动感键盘.jpg");



        setVisible(true);
    }

    public void initNameLabel(){
        nameLabel=new JLabel("请输入你的用户名:");
        nameLabel.setFont(new Font("宋体",Font.BOLD,17));
        nameLabel.setSize(WIDTH/10*2, HEIGTH/8);
        nameLabel.setLocation((int)( WIDTH*5/14-WIDTH/10*2), HEIGTH / 10 );
        container.add(nameLabel);

    }
    public void initCodeLabel(){
        codeLabel=new JLabel("请输入你的密码:");
        codeLabel.setFont(new Font("宋体",Font.BOLD,17));
        codeLabel.setSize(WIDTH/10*2, HEIGTH/8);
        codeLabel.setLocation((int)( WIDTH*5/14-WIDTH/10*2), HEIGTH / 10*3 );
        container.add(codeLabel);

    }
    public void initCodeAgainLabel(){
        codeAgainLabel=new JLabel("再次输入你的密码以确定:");
        codeAgainLabel.setFont(new Font("宋体",Font.BOLD,17));
        codeAgainLabel.setSize(WIDTH/10*2+50,  HEIGTH/8);
        codeAgainLabel.setLocation((int)( WIDTH*5/14+30-(WIDTH/10*3)),  HEIGTH / 10*5 );
        container.add(codeAgainLabel);

    }

    public void initName(){
        name=new JTextField(20);

        name.setFont(new Font("宋体",Font.BOLD,19));

        name.setSize(WIDTH/7*2, HEIGTH/8);
        name.setLocation((int)( WIDTH*5/14), HEIGTH / 10 );
        container.add(name);

    }

    public void initCode(){
        code=new JTextField(20);

        code.setFont(new Font("宋体",Font.BOLD,19));

        code.setSize(WIDTH/7*2, HEIGTH/8);
        code.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *3);
        container.add(code);
    }
    public void initCodeComfirm(){
        codeComfirm=new JTextField(20);

        codeComfirm.setFont(new Font("宋体",Font.BOLD,19));

        codeComfirm.setSize(WIDTH/7*2, HEIGTH/8);
        codeComfirm.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *5);
        container.add(codeComfirm);
    }

    public void initComfirm(){
        comfirm=new JButton();

        comfirm.setSize(WIDTH/7*2, HEIGTH/8);
        comfirm.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *7);

        new addButtonBlackgroundPicture(comfirm,"src\\Image\\Button\\Register\\确定.jpg");
        container.add(comfirm);

        comfirm.addActionListener((e)->{
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");
            frame.testRegister();
            });
    }
    public JTextField getThisName(){
        return name;
    }
    public JTextField getCode(){
        return code;
    }
    public JTextField getCodeComfirm(){
        return codeComfirm;
    }
}
