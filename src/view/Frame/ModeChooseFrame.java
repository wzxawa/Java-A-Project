package view.Frame;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import controller.GameController;
import model.Cell;
import model.ChessPiece;
import model.Chessboard;
import model.Constant;
import view.UII.*;




public class ModeChooseFrame extends JFrame {
    private final int WIDTH=800;
    private final int HEIGTH=800;

    private JButton ordinary;
    private JButton automatic;
    private JButton continueLast;

    private JButton achievementList;
    private Container container=getContentPane();
    private Frame frame;
    private String player;

    public void setPlayer(String player) {
        this.player = player;
    }

    public ModeChooseFrame(Frame frame){
        //setTitle("");
        this.frame=frame;
        setSize(WIDTH,HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        initOrdinary();
        initAutomatic();
        initContinueLast();
        initAchievementList();

        new addBlackgroundPicture(this,"src\\Image\\Blackground\\电子动感键盘.jpg");

        setVisible(true);
    }

    public void initOrdinary(){
        ordinary=new JButton();


        ordinary.setSize(WIDTH/7*2, HEIGTH/8);
        ordinary.setLocation((int)( WIDTH*5/14), HEIGTH / 10 );

        new addButtonBlackgroundPicture(ordinary,"src\\Image\\Button\\ModeChoose\\普通模式.jpg");

        container.add(ordinary);
        ordinary.addActionListener((e)->{frame.playerClickOrdinary();
            frame.getGameDifficultyChooseFrame().setPlayer(player);
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");});
    }

    public void initAutomatic(){
        automatic=new JButton();

        automatic.setSize(WIDTH/7*2, HEIGTH/8);
        automatic.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *3);

        new addButtonBlackgroundPicture(automatic,"src\\Image\\Button\\ModeChoose\\自动模式.jpg");
        container.add(automatic);
        automatic.addActionListener((e)->{frame.playerClickAutomatic();
            frame.getGameDifficultyChooseFrame().setPlayer(player);
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");});
    }

    public void initContinueLast(){
        continueLast=new JButton();

        continueLast.setSize(WIDTH/7*2, HEIGTH/8);
        continueLast.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *5);

        new addButtonBlackgroundPicture(continueLast,"src\\Image\\Button\\ModeChoose\\继续上一次.jpg");
        container.add(continueLast);

        // 创建 JFileChooser 实例
        JFileChooser fileChooser = new JFileChooser();
        File userfile=new File("./userdata/"+player);
        // 设置 JFileChooser 的当前目录
        fileChooser.setCurrentDirectory(userfile);
        // 创建一个按钮，当点击时会调用 JFileChooser
        continueLast.addActionListener(e -> {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // 用户选择了文件，你可以在这里处理文件
                File selectedFile = fileChooser.getSelectedFile();
                if(selectedFile.isFile()){
                    boolean correct=true;
                    if(selectedFile.getName().endsWith(".txt")){
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()))) {
                            // 跳过前两行
                            String line1=reader.readLine();
                            String line2=reader.readLine();
                            // 读取第三行及以下内容
                            String line;
                            while ((line = reader.readLine()) != null) {
                                String[] lineSplit=line.split(" ");
                                if(lineSplit.length!=8) {
                                    correct = false;
                                    JOptionPane.showMessageDialog(null, "棋盘形状错误 102");
                                    break;
                                }
                            }
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()))) {
                            reader.readLine();reader.readLine();
                            String line;
                            boolean correctPiece=true;
                            while((line=reader.readLine())!=null){
                                String[] lineSplit=line.split(" ");
                                for(String pieces:lineSplit){
                                    if(Integer.parseInt(pieces)<1||Integer.parseInt(pieces)>4){
                                        correct=false;
                                        JOptionPane.showMessageDialog(null,"棋子错误 103");
                                        break;
                                    }
                                }
                            }
                        }catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        if(correct){
                            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()))) {
                                String line1=reader.readLine();
                                String line2=reader.readLine();
                                String line;
                                String[] line1Split=line1.split(" ");
                                String[] line2Split=line2.split(" ");
                                System.out.println(line1);
                                System.out.println(line2);
                                ChessGameFrame chessGameFrame;
                                if(Integer.parseInt(line2Split[2])==1){
                                    chessGameFrame=new ChessGameFrame(Integer.parseInt(line1Split[0]),Integer.parseInt(line1Split[1]),player,true);
                                }
                                else{
                                    chessGameFrame=new ChessGameFrame(Integer.parseInt(line1Split[0]),Integer.parseInt(line1Split[1]),player,false);
                                }
                                chessGameFrame.setGameSituation(Integer.parseInt(line2Split[0]),Integer.parseInt(line2Split[1]));
                                Cell[][] grid=new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

                                for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
                                    for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                                        grid[i][j]=new Cell();
                                    }
                                }
                                int i=0;
                                while((line=reader.readLine())!=null){
                                    String[] lineSplit=line.split(" ");
                                    for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                                        grid[i][j].setPiece(new ChessPiece(Integer.parseInt(lineSplit[j])));
                                    }
                                    i++;
                                }
                                Chessboard chessboard=new Chessboard(0);
                                chessboard.setGrid(grid);
                                GameController gameController = new GameController(chessGameFrame.getChessboardComponent(), chessboard,Integer.parseInt(line1Split[0]),Integer.parseInt(line1Split[1]),player);
                                gameController.setPointsNowGet(Integer.parseInt(line2Split[0]));
                                gameController.setMovesNow(Integer.parseInt(line2Split[1]));
                                chessGameFrame.setVisible(true);
                            }catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"文件格式类型错误 101");
                    }
                }
                System.out.println("你选择的文件是： " + selectedFile.getAbsolutePath());
            }
        });

    }

    public void initAchievementList(){
        achievementList=new JButton();

        achievementList.setSize(WIDTH/7*2, HEIGTH/8);
        achievementList.setLocation((int)( WIDTH*5/14), HEIGTH / 10 *7);

        new addButtonBlackgroundPicture(achievementList,"src\\Image\\Button\\ModeChoose\\成绩排行榜.jpg");
        container.add(achievementList);
        achievementList.addActionListener((e)->{
            frame.playerClickListFrame();
            new SoundEffect("src\\Music\\SoundEffect\\ChessClick.wav");});
    }
}
