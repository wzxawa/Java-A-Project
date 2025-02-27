package view.Frame;
import java.awt.*;
import javax.swing.*;
import controller.GameController;
import listener.GameListener;
import model.Chessboard;
import java.io.*;
public class Frame{
    //private GameController gameController;
    private InitFrame initFrame=new InitFrame(this);
    private  LogInFrame logInFrame=new LogInFrame(this);
    private  ModeChooseFrame modeChooseFrame=new ModeChooseFrame(this);
    private RegisterFrame registerFrame=new RegisterFrame(this);
    //private ChessGameFrame chessGameFrame=new ChessGameFrame(this);
    private GameDifficultyChooseFrame gameDifficultyChooseFrame=new GameDifficultyChooseFrame();
    public Frame(){
        initFrame.setVisible(true);
        //listFrame.setVisible(false);
        logInFrame.setVisible(false);
        modeChooseFrame.setVisible(false);
        registerFrame.setVisible(false);
        gameDifficultyChooseFrame.setVisible(false);
    }

    /*public GameController getGameController() {
        return gameController;
    }*/

    public void playerClickLoginButton(){
        logInFrame.setVisible(true);
    }
    public void playerClickRegisterButton(){
        registerFrame.setVisible(true);
    }
    public void testLogin(){
        String name=logInFrame.getLoginName().getText();
        String code=logInFrame.getCode().getText();
        String filename="user.txt";
        boolean have=false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] thisLine=line.split(" ");
                if(thisLine[0].equals(name)&&thisLine[1].equals(code)){
                    have=true;break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(have){
            JOptionPane.showMessageDialog(null,"LoginSuccessfully!");
            logInFrame.setVisible(false);
            modeChooseFrame.setVisible(true);
            modeChooseFrame.setPlayer(name);
        }
        else{
            JOptionPane.showMessageDialog(null,"Login Failed!");
        }
        //JFrame fframe=new JFrame();
        //frame.pack();
        //frame.setVisible(true);
    }
    public void testRegister(){
        String name=registerFrame.getThisName().getText();
        String code=registerFrame.getCode().getText();
        String confirmCode=registerFrame.getCodeComfirm().getText();
        if(!code.equals(confirmCode)){
            JOptionPane.showMessageDialog(null,"The codes are different, please try again!");
            return;
        }else{
            String filename="user.txt";
            boolean have=false;
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] thisLine=line.split(" ");
                    if(thisLine[0].equals(name)&&thisLine[1].equals(code)){
                        have=true;break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(have){
                JOptionPane.showMessageDialog(null,"The user has already have!");
            }
            else{
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename,true))) {
                    // 写入文本数据
                    writer.write(name+" "+code+" 0 0 0");
                    writer.newLine(); // 写入一个新行

                    // 刷新缓冲区，确保数据被写入文件
                    writer.flush();
                } catch (IOException e) {
                    // 异常处理
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"Register Successfully!");
                registerFrame.setVisible(false);
                initFrame.setVisible(true);
            }
        }
    }
    public void playerClickOrdinary(){
        modeChooseFrame.setVisible(false);
        gameDifficultyChooseFrame.setVisible(true);
        gameDifficultyChooseFrame.setAutomatic(false);
    }
    public void playerClickAutomatic(){
        modeChooseFrame.setVisible(false);
        gameDifficultyChooseFrame.setVisible(true);
        gameDifficultyChooseFrame.setAutomatic(true);
    }
    public void playerClickListFrame(){
        SwingUtilities.invokeLater(() -> {
            ListFrame listFrame=new ListFrame(this);
            listFrame.setVisible(true);
        });
    }

    public InitFrame getInitFrame() {
        return initFrame;
    }

    /*public ListFrame getListFrame() {
        return listFrame;
    }*/

    public RegisterFrame getRegisterFrame() {
        return registerFrame;
    }

    public LogInFrame getLogInFrame() {
        return logInFrame;
    }

    public ModeChooseFrame getModeChooseFrame() {
        return modeChooseFrame;
    }

    public GameDifficultyChooseFrame getGameDifficultyChooseFrame() {
        return gameDifficultyChooseFrame;
    }
}
