package view.Frame;

import controller.GameController;
import model.Chessboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

import view.*;
import view.UII.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH=1100;
    private final int HEIGTH=800;

    private final int ONE_CHESS_SIZE;
    private ChessboardComponent chessboardComponent;
    private final int PointsInitiallyNeeded;
    private final int MovesRestricted;
    public int PointsGet;
    public int MovesNow;
    private int ShuffleTime;


    private int blackGroundNum =1;
    private JLabel blackGround1;
    private JLabel blackGround2;
    private String player;
    private final boolean automatic;
    public boolean getAutomatic(){
        return automatic;
    }
    public ChessGameFrame(int PointsInitiallyNeeded,int MovesRestricted,String player,boolean automatic) {
        setTitle("Match-3 Game Chessboard"); //设置标题
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        this.PointsInitiallyNeeded=PointsInitiallyNeeded;
        this.MovesRestricted=MovesRestricted;
        PointsGet=0;MovesNow=0;ShuffleTime=0;
        this.player=player;
        this.automatic=automatic;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        /*addLabel();*/
        /*addHelloButton();*/

        addSwitchSkinButton();
        addSaveButton();
        addSuggestionButton();
        addSwapConfirmButton();
        addNextStepButton();
        addRestartButton();
        addShuffleButton();
        addHomeButton();
        addBigBombButton();
        addRowBombButton();


        addGameSituation();
        addGameDifficulty();

        blackGround1=new JLabel(new ImageIcon(returnBufferedImage("src\\Image\\Blackground\\电子动感键盘.jpg")));
        blackGround2=new JLabel(new ImageIcon(returnBufferedImage("src\\Image\\Blackground\\极简.jpg")));

        blackGround1.setOpaque(true); // 让标签不透明，这样背景图片可见
        blackGround1.setBounds(0,0,getWIDTH(),getHEIGTH());
        blackGround2.setOpaque(true); // 让标签不透明，这样背景图片可见
        blackGround2.setBounds(0,0,getWIDTH(),getHEIGTH());

        add(blackGround1);

    }

    public void changeBlackGround(){
        if (blackGroundNum ==1){
            remove(blackGround1);
            blackGroundNum =2;
            add(blackGround2);
//            frame.revalidate(); // 重新布局组件
            repaint(); // 重新绘制组件

        }else {
            remove(blackGround2);
            blackGroundNum =1;
            add(blackGround1);
//            frame.revalidate(); // 重新布局组件
            repaint(); // 重新绘制组件
        }
    }

    public BufferedImage returnBufferedImage(String inmagePath){
        BufferedImage image=null;
        // 加载背景图片
        try {
            BufferedImage backgroundImage =
                    ImageIO.read(new File(inmagePath));

            // 获取chess的大小
            int chessWidth = getWIDTH();
            int chessHeight = getHEIGTH();

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
            image=resizedImage;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }



    public int getWIDTH(){
        return WIDTH;
    }
    public int getHEIGTH(){
        return HEIGTH;
    }



    JLabel GameSituation;


    public void addSwitchSkinButton(){
        JButton button = new JButton();
        button.addActionListener((e) -> {changeBlackGround();
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");});
        button.setLocation(HEIGTH, HEIGTH / 10 - 40);
        button.setSize(200, 60);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\ChessGame\\切换皮肤.jpg");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    public void addSaveButton(){
        JButton button = new JButton();
        button.addActionListener((e) -> {chessboardComponent.saveTheGame();
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");});
        button.setLocation(HEIGTH, HEIGTH / 10 + 40);
        button.setSize(200, 60);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\ChessGame\\保存.jpg");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    public void setGameSituation(int PointsGet,int MovesNow){
        this.PointsGet=PointsGet;this.MovesNow=MovesNow;
        GameSituation.setText("PointsGeted:"+PointsGet+"  MovesNow:"+MovesNow);
    }
    public void addGameSituation(){
        GameSituation=new JLabel("PointsGet:"+PointsGet+"  MovesNow:"+MovesNow);
        GameSituation.setLocation(HEIGTH/3, HEIGTH / 10 /8);
        GameSituation.setSize(600, 60);
        GameSituation.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(GameSituation);
    }
    public void addGameDifficulty(){
        JLabel gamedifficulty;
        gamedifficulty =new JLabel("PointsNeeded:"+PointsInitiallyNeeded+" MovesRestricted:"+MovesRestricted);
        gamedifficulty.setLocation(HEIGTH/3, HEIGTH / 10 /3);
        gamedifficulty.setSize(600, 60);
        gamedifficulty.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(gamedifficulty);
    }
    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }
    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE,this);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
*/    /*private void addLabel() {
        JLabel statusLabel = new JLabel("Sample label");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }*/

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    /*private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }*/

    private void addSwapConfirmButton() {
        JButton button = new JButton();
        button.addActionListener((e) -> {
            if(!automatic)chessboardComponent.swapChess();
            else{
                chessboardComponent.swapChessAutomatic();
            }
            });
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\ChessGame\\确认交换.jpg");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton();
        button.addActionListener((e) -> {chessboardComponent.nextStep();
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");});
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\ChessGame\\下一步.jpg");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addSuggestionButton() {
        JButton button = new JButton();
        button.addActionListener((e) -> {chessboardComponent.Suggestion();
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");});
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\ChessGame\\建议.jpg");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    public void addNoUse(){
        JDialog dialog=new JDialog(this,"No use of your swap!");
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(200,60);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    public void addDeadEnd(){
        JDialog dialog=new JDialog(this,"Sorry to leading to a dead end,please use the shuffle button!");
        dialog.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        dialog.setSize(200,60);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    public void Windialog(){
        LoadingPlayerData(PointsGet,1);
        JDialog dialog =new JDialog(this,"Congratulates for winning the game!");
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(400,200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        JButton button1=new JButton("Next Level");
        button1.setLocation(200,40);
        button1.setSize(200,30);
        button1.setFont(new Font("Rockwell", Font.BOLD, 10));
        button1.addActionListener((e)->{
            ChessGameFrame chessGameFrame=new ChessGameFrame(PointsInitiallyNeeded+100,MovesRestricted,player,automatic);
            GameController gameController = new GameController(chessGameFrame.getChessboardComponent(), new Chessboard(0),PointsInitiallyNeeded,MovesRestricted,player);
            chessGameFrame.setVisible(true);
        });
        dialog.getContentPane().add(button1);
    }
    public void Losedialog(){//back to the windows
        LoadingPlayerData(0,0);
        JDialog dialog = new JDialog(this,"Sorry for lose the game");
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(400,200);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        JButton button1=new JButton("Home");
        button1.setLocation(200,40);
        button1.setSize(200,30);
        button1.setFont(new Font("Rockwell", Font.BOLD, 10));
        dialog.getContentPane().add(button1);
        button1.addActionListener((e)->{
            this.setVisible(false);
            GameDifficultyChooseFrame gameDifficultyChooseFrame=new GameDifficultyChooseFrame();
            gameDifficultyChooseFrame.setPlayer(player);
            gameDifficultyChooseFrame.setVisible(true);
        });
    }
    private void addRestartButton(){
        JButton button =new JButton();
        button.addActionListener((e) -> {ReStartGame();
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");});
        button.setLocation(HEIGTH,HEIGTH/10+350);
        button.setSize(200,60);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\ChessGame\\重新开始.jpg");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    public void ReStartGame(){
        ChessGameFrame chessGameFrame=new ChessGameFrame(PointsInitiallyNeeded,MovesRestricted,player,automatic);
        GameController gameController = new GameController(chessGameFrame.getChessboardComponent(), new Chessboard(0),PointsInitiallyNeeded,MovesRestricted,player);
        chessGameFrame.setVisible(true);
        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                super.dispose();  // 关闭主窗口
            }
        });*///awa怎么关闭原来的窗口ＱＡＱ之后再解决吧 但是两个会一起关掉（尽管我还不知道为什么）
    }
    public void addShuffleButton(){
        JButton button =new JButton();
        button.addActionListener((e) -> {
            if(ShuffleTime<=2){
                chessboardComponent.Shuffle();
                chessboardComponent.repaint();
                ShuffleTime++;
                new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");
            }
            else{
                JOptionPane.showMessageDialog(null,"Sorry!You can't shuffle!");
            }
        });
        button.setLocation(HEIGTH,HEIGTH/10+450);
        button.setSize(200,60);

        new addButtonBlackgroundPicture(button,"src\\Image\\Button\\ChessGame\\洗牌.jpg");
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    public void addHomeButton(){
        JButton button=new JButton("Home");
        button.addActionListener((e)->{
            this.setVisible(false);
            GameDifficultyChooseFrame gameDifficultyChooseFrame=new GameDifficultyChooseFrame();
            gameDifficultyChooseFrame.setPlayer(player);
            gameDifficultyChooseFrame.setVisible(true);
        });
        button.setLocation(HEIGTH,HEIGTH/10+520);
        button.setSize(200,30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
//    private void addLoadButton() {
//        JButton button = new JButton("Load");
//        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
//        button.setSize(200, 60);
//        button.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(button);
//
//        button.addActionListener(e -> {
//            System.out.println("Click load");
//            String path = JOptionPane.showInputDialog(this,"Input Path here");
//            gameController.loadGameFromFile(path);
//        });
//    }
    private void LoadingPlayerData(int score,int winChess){
        File currentDir=new File("./user.txt");
        ArrayList<String> lists=new ArrayList<>();
        String SpecialLine="";
        try (BufferedReader reader = new BufferedReader(new FileReader(currentDir.getAbsolutePath()))) {
            String line;
            while((line=reader.readLine())!=null){
                String[] lineSplit=line.split(" ");
                if(lineSplit[0].equals(player)){
                    SpecialLine=line;
                }
                else{
                    lists.add(line);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentDir.getAbsolutePath()))) {
            // 写入空内容，覆盖原有文件内容
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] lineSplit=SpecialLine.split(" ");
        int ScoreNow=Integer.parseInt(lineSplit[2])+score;
        int WinChess = Integer.parseInt(lineSplit[3])+winChess;
        int SumChess=Integer.parseInt(lineSplit[4]);
        SumChess++;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentDir.getAbsolutePath()))) {
            for(String usersData:lists){
                writer.write(usersData);
                writer.newLine();
            }
            writer.write(lineSplit[0]+" "+lineSplit[1]+" "+ScoreNow+" "+WinChess+" "+SumChess);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addBigBombButton(){
        JButton button=new JButton("BigBomb");
        button.addActionListener((e)->{
            chessboardComponent.onPlayerClickBigBomb();
        });
        button.setLocation(HEIGTH,HEIGTH/10+560);
        button.setSize(200,30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    public void addRowBombButton(){
        JButton button=new JButton("Row/Col Bomb");
        button.addActionListener((e)->{
            chessboardComponent.onPlayerClickRowBomb();
        });
        button.setLocation(HEIGTH,HEIGTH/10+600);
        button.setSize(200,30);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
}
