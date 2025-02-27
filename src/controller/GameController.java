package controller;

import listener.GameListener;
import model.Chessboard;
import model.ChessboardPoint;
import model.Constant;
import view.Auto_NextStep;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;
import model.*;
import view.UII.SoundEffect;

import javax.swing.*;
import java.io.*;
import java.util.Set;
import java.util.Timer;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener {

    private Chessboard model;//注意此处的model只代表最重要的chessboard
    private ChessboardComponent view;//注意此处的view只代表最重要的chessboardComponent;
    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;
    private final int pointsInitiallyNeed;
    private int pointsNowGet;
    private final int movesRestrict;
    private int movesNow;
    private final String player;
    private boolean BigBombUse=false;
    private boolean RowBombUse=false;

    public int getPointsInitiallyNeed() {
        return pointsInitiallyNeed;
    }

    public int getMovesRestrict() {
        return movesRestrict;
    }
    public void setPointsNowGet(int pointsNowGet){
        this.pointsNowGet=pointsNowGet;
    }
    public void setMovesNow(int movesNow){
        this.movesNow=movesNow;
    }

    public Chessboard getModel() {
        return model;
    }

    public GameController(ChessboardComponent view, Chessboard model, int pointsNeeded, int movesRestrict, String player) {
        this.view = view;
        this.model = model;
        this.pointsInitiallyNeed=pointsNeeded;
        this.movesRestrict=movesRestrict;
        this.movesNow=0;
        this.pointsNowGet=0;
        this.player=player;

        view.registerController(this);
        view.initiateChessComponent(model);
        SwingUtilities.invokeLater(() -> {
            view.repaint();
        });
        Detect();

    }
    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if(component.getComponentCount()==0){
            System.out.println("This is a empty Cell.Please wait.");
        }
    }

    @Override
    public void onPlayerSwapChess(){
        System.out.println("Swapping Components: ");
        int col_selectedpoint1 = (selectedPoint == null)? -1 :selectedPoint.getCol();
        int row_selectedpoint1 = (selectedPoint == null)? -1 :selectedPoint.getRow();
        int col_selectedpoint2 = (selectedPoint2 == null)? -1 :selectedPoint2.getCol();
        int row_selectedpoint2 = (selectedPoint2 == null)? -1 :selectedPoint2.getRow();
        System.out.println("Current selectedPoint 1 is Col: " + col_selectedpoint1 + " Row: " + row_selectedpoint1);
        System.out.println("Current selectedPoint 2 is Col: " + col_selectedpoint2 + " Row: " + row_selectedpoint2);
        if(selectedPoint!=null&&selectedPoint2!=null){
            movesNow++;
            view.getChessGameFrame().setGameSituation(pointsNowGet,movesNow);
            model.swapChessPiece(selectedPoint,selectedPoint2);
            view.swapChessBoardComponent(selectedPoint,selectedPoint2);
            if(model.Elimination().isEmpty()){
                view.getChessGameFrame().addNoUse();
                model.swapChessPiece(selectedPoint,selectedPoint2);
                view.swapChessBoardComponent(selectedPoint,selectedPoint2);
                new SoundEffect("src\\Music\\SoundEffect\\交换失败.wav");
                CheckWinOrLose();
                System.out.println("Swap failed");
            }
            else{
                new SoundEffect("src\\Music\\SoundEffect\\交换成功.wav");
                Eliminate();
                System.out.println("Swap succeeded.");
            }
            //Clean up the component and the selected point.
            selectedPoint = null;
            selectedPoint2 = null;
        }
    }
    private void Eliminate(){
        Set<ChessboardPoint> pointsToEliminate= model.Elimination();
        pointsNowGet+=pointsToEliminate.size()*10;
        for (ChessboardPoint chessboardPoint : pointsToEliminate) {
            model.getGridAt(chessboardPoint).removePiece();
            view.removeChessComponentAtGrid(chessboardPoint);
        }
        view.repaint();
        view.getChessGameFrame().setGameSituation(pointsNowGet,movesNow);
        CheckWinOrLose();
    }
    private void CheckWinOrLose(){
        if(movesNow<movesRestrict){
            if(pointsNowGet>=pointsInitiallyNeed){
                view.getChessGameFrame().Windialog();
                new SoundEffect("src\\Music\\SoundEffect\\游戏胜利.wav");
            }
        }
        else{
            if(pointsNowGet>=pointsInitiallyNeed){
                view.getChessGameFrame().Windialog();
                new SoundEffect("src\\Music\\SoundEffect\\游戏胜利.wav");
            }
            else{
                view.getChessGameFrame().Losedialog();
                new SoundEffect("src\\Music\\SoundEffect\\游戏失败.wav");
            }
        }
    }
    private void WriteElimination(){

    }

    @Override
    public void onPlayerNextStep(){
        System.out.println("Implement your next step here.");
        if(model.CheckEmptyAll()){
            if(model.CheckEmptyIn()){
                model.FallDown();
                view.setAgain(model);
                //view.repaint();
                SwingUtilities.invokeLater(() -> {
                    view.repaint();
                });
            }
            else{
                model.Generate();
                view.setAgain(model);
                //view.repaint();
                SwingUtilities.invokeLater(() -> {
                    view.repaint();
                });
            }
        }
        else{
            if(!model.Elimination().isEmpty()){
                Eliminate();
            }
        }
    }


    private Timer timer; // 用于创建动画的计时器

    public void onPlayerSwapChess_auto() {
        onPlayerSwapChess();
        Timer timer = new Timer();
        timer.schedule(new Auto_NextStep(this), 0, 1000); // 每1s执行一次
        if (!model.CheckEmptyAll()){
            timer.cancel(); // 停止计时器
        }
    }

    public void Detect(){
        if(model.DetectDeadAndSuggestion().isEmpty()){
            JOptionPane.showMessageDialog(null,"This leads to a deadEnd!");
        }
    }
    public void Suggestion(){
        if(!model.DetectDeadAndSuggestion().isEmpty()){
            System.out.println("");
            //高亮显示方块
        }
    }
    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        System.out.println("Click on Col: "+ point.getCol() + " Row: " + point.getRow());
        int col_selectedpoint1 = (selectedPoint == null)? -1 :selectedPoint.getCol();
        int row_selectedpoint1 = (selectedPoint == null)? -1 :selectedPoint.getRow();
        int col_selectedpoint2 = (selectedPoint2 == null)? -1 :selectedPoint2.getCol();
        int row_selectedpoint2 = (selectedPoint2 == null)? -1 :selectedPoint2.getRow();
        System.out.println("Current selectedPoint 1 is Col: " + col_selectedpoint1 + " Row: " + row_selectedpoint1);
        System.out.println("Current selectedPoint 2 is Col: " + col_selectedpoint2 + " Row: " + row_selectedpoint2);
        var distance2point1 = (selectedPoint == null)? null : Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
        var distance2point2 = (selectedPoint2 == null)? null : Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
        var point1 = (selectedPoint == null)? null : (ChessComponent)view.getGridComponentAt(selectedPoint).getComponent(0);
        var point2 = (selectedPoint2 == null)? null : (ChessComponent)view.getGridComponentAt(selectedPoint2).getComponent(0);
        //Selected Point 1 and 2 are selected
        if(selectedPoint != null && selectedPoint2 != null){  //1，2都有
            if(distance2point1 == 0 && point1!= null){
                point1.setSelected(false);
                SwingUtilities.invokeLater(() -> {
                    point1.repaint();
                });
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            }else if(distance2point2 == 0 && point2!= null){
                point2.setSelected(false);
                SwingUtilities.invokeLater(() -> {
                    point2.repaint();
                });

                selectedPoint2 = null;
            }else if(distance2point1 == 1 && point2!= null){
                point2.setSelected(false);
                SwingUtilities.invokeLater(() -> {
                    point2.repaint();
                });
                selectedPoint2 = point;
                component.setSelected(true);
                SwingUtilities.invokeLater(() -> {
                    component.repaint();
                });
            }else if(distance2point2 == 1 && point1!= null){
                point1.setSelected(false);
                SwingUtilities.invokeLater(() -> {
                    point1.repaint();
                });
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                SwingUtilities.invokeLater(() -> {
                    component.repaint();
                });
            }
            return;
        }
        //None of the Selected Point of 1 and 2 are selected
        if (selectedPoint == null && selectedPoint2 == null) { //1，2都没有
            selectedPoint = point;
            component.setSelected(true);
            SwingUtilities.invokeLater(() -> {
                component.repaint();
            });
            return;
        }

        //If selected Pointed is not null and selected point 2 is null
        if(selectedPoint != null){
            if(distance2point1 == 0){
                selectedPoint = null;
                component.setSelected(false);
                SwingUtilities.invokeLater(() -> {
                    component.repaint();
                });
            }else if(distance2point1 == 1){
                selectedPoint2 = point;
                component.setSelected(true);
                SwingUtilities.invokeLater(() -> {
                    component.repaint();
                });
            }else{  //离1太远 使新点击的为新
                if(point1 == null) return;
                point1.setSelected(false);
                SwingUtilities.invokeLater(() -> {
                    point1.repaint();
                });
                selectedPoint = point;
                component.setSelected(true);
                SwingUtilities.invokeLater(() -> {
                    component.repaint();
                });
            }
        }
    }
    public void onPlayerClickSave(){
        File currentDir=new File("./userdata");
        File subDir=new File(currentDir,player);
        subDir.mkdir();
        File[] files=subDir.listFiles();
        int n=1;
        if(files!=null){
            for(File file:files){
                if(file.isFile()){
                    char lastChar=file.getName().charAt(file.getName().length()-5);
                    int numm=Integer.parseInt(String.valueOf(lastChar));
                    System.out.println(lastChar);
                    if(numm>n){
                        n=numm;
                    }
                }
            }
        }
        n++;
        File saveFile=new File(subDir,player+n+".txt");
        try{
            saveFile.createNewFile();
            System.out.println("文件已创建"+saveFile.getAbsolutePath());
        }catch(IOException e){
            System.err.println("创建文件时出错： " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            // 写入文本数据
            writer.write(pointsInitiallyNeed+" "+movesRestrict);
            writer.newLine();
            int auto=0;
            if(view.getChessGameFrame().getAutomatic()){
                auto=1;
            }
            writer.write(pointsNowGet+" "+movesNow+" "+auto);
            writer.newLine();
            Cell[][] grid=model.getGrid();
            for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
                for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                    writer.write(grid[i][j].getPiece().getNum()+" ");
                }
                writer.newLine();
            }
            // 刷新缓冲区，确保数据被写入文件
            writer.flush();
        } catch (IOException e) {
            // 异常处理
            e.printStackTrace();
        }
    }
    public void onPlayerClickBigBomb(){
        if(!BigBombUse){
            if(selectedPoint!=null&&selectedPoint2!=null){
                Set<ChessboardPoint> deletePoint=model.BigBomb(selectedPoint,selectedPoint2);
                for (ChessboardPoint chessboardPoint : deletePoint) {
                    model.getGridAt(chessboardPoint).removePiece();
                    view.removeChessComponentAtGrid(chessboardPoint);
                }
                //view.repaint();
                SwingUtilities.invokeLater(() -> {
                    view.repaint();
                });
            }
            BigBombUse=true;
            selectedPoint=null;
            selectedPoint2=null;
        }
    }
    public void onPlayerClickRowBomb(){
        if(!RowBombUse){
            if(selectedPoint!=null&&selectedPoint2!=null){
                Set<ChessboardPoint> deletePoint=model.RowBomb(selectedPoint,selectedPoint2);
                for (ChessboardPoint chessboardPoint : deletePoint) {
                    model.getGridAt(chessboardPoint).removePiece();
                    view.removeChessComponentAtGrid(chessboardPoint);
                }
                //view.repaint();
                SwingUtilities.invokeLater(() -> {
                    view.repaint();
                });
            }
            RowBombUse=true;
            selectedPoint=null;
            selectedPoint2=null;
        }
    }
}
