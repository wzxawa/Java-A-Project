package model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard(int randomSeed) {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces(randomSeed);
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces(int randomSeed) {
        Random random = new Random(randomSeed);

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                Random rand = new Random();
                int randomNum = rand.nextInt(4) + 1; // 生成1到4之间的随机整数
                grid[i][j].setPiece(new ChessPiece(randomNum));
                //grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }
        //排除有可消除情况
        for(int i=1;i<Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i++){
            for(int j=1;j<Constant.CHESSBOARD_COL_SIZE.getNum()-1;j++){
                if(grid[i][j].getPiece().getNum()==grid[i][j-1].getPiece().getNum()&&grid[i][j].getPiece().getNum()==grid[i][j+1].getPiece().getNum()){
                    initPieces(randomSeed+1);//或许使用其他方法改变randomseed
                }
                if(grid[i][j].getPiece().getNum()==grid[i-1][j].getPiece().getNum()&&grid[i][j].getPiece().getNum()==grid[i+1][j].getPiece().getNum()){
                    initPieces(randomSeed+1);//或许使用其他方法改变randomseed
                }
            }
        }
        for(int j=1;j<Constant.CHESSBOARD_COL_SIZE.getNum()-1;j++){
            if(grid[0][j].getPiece().getNum()==grid[0][j-1].getPiece().getNum()&&grid[0][j].getPiece().getNum()==grid[0][j+1].getPiece().getNum()){
                initPieces(randomSeed+1);//或许使用其他方法改变randomseed
            }
        }
        int lastrow=Constant.CHESSBOARD_ROW_SIZE.getNum()-1;
        for(int j=1;j<Constant.CHESSBOARD_COL_SIZE.getNum()-1;j++){
            if(grid[lastrow][j].getPiece().getNum()==grid[lastrow][j-1].getPiece().getNum()&&grid[lastrow][j].getPiece().getNum()==grid[lastrow][j+1].getPiece().getNum()){
                initPieces(randomSeed+1);//或许使用其他方法改变randomseed
            }
        }
        for(int i=1;i<Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i++){
            if(grid[i][0].getPiece().getNum()==grid[i-1][0].getPiece().getNum()&&grid[i][0].getPiece().getNum()==grid[i+1][0].getPiece().getNum()){
                initPieces(randomSeed+1);//或许使用其他方法改变randomseed
            }
        }
        int lastcol=Constant.CHESSBOARD_COL_SIZE.getNum()-1;
        for(int i=1;i<Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i++){
            if(grid[i][lastcol].getPiece().getNum()==grid[i-1][lastcol].getPiece().getNum()&&grid[i][lastcol].getPiece().getNum()==grid[i+1][lastcol].getPiece().getNum()){
                initPieces(randomSeed+1);//或许使用其他方法改变randomseed
            }
        }
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }//wzx将private改为public

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        var p1 = getChessPieceAt(point1);
        var p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }
    public void Shuffle(){
        int[] boardNum=new int[5];
        for(int i:boardNum){boardNum[i]=0;}
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                boardNum[grid[i][j].getPiece().getNum()]++;
            }
        }
    }
    public void Shuffle11(){
        Cell[][] initGrid=getGrid();
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                grid[j][7-i]=initGrid[i][j];
            }
        }
    }
    private boolean ShuffleConfirm(int[] boardNum){//aaa！太复杂了！线程爆了！
        int[] boardNumNow=new int[5];
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                boardNumNow[grid[i][j].getPiece().getNum()]++;
            }
        }
        boolean Confirm=true;
        for(int i=1;i<=4;i++){
            if(boardNumNow[i]!=boardNum[i]){
                Confirm=false;break;
            }
        }
        return Confirm;
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public void setGrid(Cell[][] grid){
        this.grid=grid;
    }
    public boolean CheckEmptyAll(){
        boolean empty=false;
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if(grid[i][j].getPiece()==null){
                    empty=true;return empty;
                }
            }
        }
        return empty;
    }
    public boolean CheckEmptyIn(){
        boolean empty=false;
        for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
            for(int i=Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i>=0;i--){
                if(grid[i][j].getPiece()==null){
                    int start=i;
                    for(int k=i;k>=0;k--){
                        if(grid[k][j].getPiece()==null){
                            start=k;
                        }
                        else{
                            break;
                        }
                    }
                    if(start!=0){
                        empty=true;return empty;
                    }//是否可加break来减少循环量?
                }
            }
        }
        return empty;
    }
    public Set<ChessboardPoint> Elimination(){
        Set<ChessboardPoint> eliminate=new HashSet<>();
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<=Constant.CHESSBOARD_COL_SIZE.getNum()-3;j++){
                if(grid[i][j].getPiece().getNum()==grid[i][j+1].getPiece().getNum()&&grid[i][j].getPiece().getNum()==grid[i][j+2].getPiece().getNum()){
                    eliminate.add(new ChessboardPoint(i,j));
                    eliminate.add(new ChessboardPoint(i,j+1));
                    eliminate.add(new ChessboardPoint(i,j+2));
                }
            }
        }
        for(int i=0;i<=Constant.CHESSBOARD_ROW_SIZE.getNum()-3;i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if(grid[i][j].getPiece().getNum()==grid[i+1][j].getPiece().getNum()&&grid[i][j].getPiece().getNum()==grid[i+2][j].getPiece().getNum()){
                    eliminate.add(new ChessboardPoint(i,j));
                    eliminate.add(new ChessboardPoint(i+1,j));
                    eliminate.add(new ChessboardPoint(i+2,j));
                }
            }
        }
        return eliminate;
    }
    public void FallDown(){  //上方原来有的fall down
        for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
            for(int i=Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i>=0;i--){
                if(grid[i][j].getPiece()==null){
                    int k;
                    for(k=i;k>=0;k--){
                        if(grid[k][j].getPiece()!=null){
                            break;
                        }
                    }
                    if(k>=0){
                        grid[i][j].setPiece(grid[k][j].getPiece());
                        grid[k][j].removePiece();
                    }
                }
            }
        }
    }
    public void Generate(){
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if(grid[i][j].getPiece()==null){
                    Random rand = new Random();
                    int randomNum = rand.nextInt(4) + 1; // 生成1到4之间的随机整数
                    grid[i][j].setPiece(new ChessPiece(randomNum));
                    //grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
                }
            }
        }
    }
    public Set<ChessboardPoint> DetectDeadAndSuggestion(){
        Set<ChessboardPoint> todo=new HashSet<>();
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum()-1;j++){
                swapChessPiece(new ChessboardPoint(i,j),new ChessboardPoint(i,j+1));
                todo.addAll(Elimination());
                swapChessPiece(new ChessboardPoint(i,j),new ChessboardPoint(i,j+1));
            }
        }
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                swapChessPiece(new ChessboardPoint(i,j),new ChessboardPoint(i+1,j));
                todo.addAll(Elimination());
                swapChessPiece(new ChessboardPoint(i,j),new ChessboardPoint(i+1,j));
            }
        }
        return todo;
    }
    public Set<ChessboardPoint> BigBomb(ChessboardPoint chessboardPoint1, ChessboardPoint chessboardPoint2){
        Set<ChessboardPoint> deletePoint=new HashSet<>();
        for(int i=chessboardPoint1.getRow()-1;i<=chessboardPoint1.getRow()+1;i++){
            for(int j=chessboardPoint1.getCol()-1;j<=chessboardPoint1.getCol()+1;j++){
                if(i>=0&&i<=7&&j>=0&&j<=7){
                    deletePoint.add(new ChessboardPoint(i,j));
                }
            }
        }
        for(int i=chessboardPoint2.getRow()-1;i<=chessboardPoint2.getRow()+1;i++){
            for(int j=chessboardPoint2.getCol()-1;j<=chessboardPoint2.getCol()+1;j++){
                if(i>=0&&i<=7&&j>=0&&j<=7){
                    deletePoint.add(new ChessboardPoint(i,j));
                }
            }
        }
        return deletePoint;
    }
    public Set<ChessboardPoint> RowBomb(ChessboardPoint chessboardPoint1, ChessboardPoint chessboardPoint2){
        Set<ChessboardPoint> deletePoint=new HashSet<>();
        if(chessboardPoint1.getRow()==chessboardPoint2.getRow()){
            int i=chessboardPoint1.getRow();
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                deletePoint.add(new ChessboardPoint(i,j));
            }
        }
        if(chessboardPoint1.getCol()==chessboardPoint2.getCol()){
            int j=chessboardPoint1.getCol();
            for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
                deletePoint.add(new ChessboardPoint(i,j));
            }
        }
        return deletePoint;
    }

}
