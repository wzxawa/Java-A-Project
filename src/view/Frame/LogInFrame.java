package view.Frame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import view.UII.*;

public class LogInFrame extends JFrame  {
    private final int WIDTH=800;
    private final int HEIGTH=800;

    private JTextField name;
    private JTextField code;

    private JLabel nameLabel;
    private JLabel codeLabel;

    private JButton comfirm;
    private Container container=getContentPane();
    private Frame frame;
    public LogInFrame(Frame frame){
        //setTitle("");
        this.frame=frame;
        setSize(WIDTH,HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        initName();
        initCode();

        initNameLabel();
        initCodeLabel();

        initComfirm();

        new addBlackgroundPicture(this,"src\\Image\\Blackground\\电子动感键盘.jpg");
        setVisible(true);
    }

    public void initName(){
        name=new JTextField(20);

        name.setFont(new Font("宋体",Font.BOLD,20));

        name.setSize(WIDTH/7*2, HEIGTH/8);
        name.setLocation((int)( WIDTH*5/14), HEIGTH / 10 );
        container.add(name);

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
        codeLabel.setLocation((int)( WIDTH*5/14-WIDTH/10*2), HEIGTH / 10*4 );
        container.add(codeLabel);

    }

    public void initCode(){
        code=new JTextField(20);

        code.setFont(new Font("宋体",Font.BOLD,20));

        code.setSize(WIDTH/7*2, HEIGTH/8);
        code.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *4);
        container.add(code);
    }

    public void initComfirm(){
        comfirm=new JButton();

        comfirm.setSize(WIDTH/7*2, HEIGTH/8);
        comfirm.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *7);

        new addButtonBlackgroundPicture(comfirm,"src\\Image\\Button\\Login\\确定.jpg");
        container.add(comfirm);

        comfirm.addActionListener((e) -> {
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");
            frame.testLogin();
            });
    }
    public JTextField getLoginName(){
        return this.name;
    }
    public JTextField getCode(){
        return this.code;
    }

}
