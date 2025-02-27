package view;


import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import view.Frame.*;
import view.*;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    //存储棋盘信息
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();//hashset 无序且不可重复

    private GameController gameController;
    private ChessGameFrame chessGameFrame;
    public ChessGameFrame getChessGameFrame(){
        return this.chessGameFrame;
    }
    public ChessboardComponent(int chessSize,ChessGameFrame chessGameFrame) {
        CHESS_SIZE = chessSize;
        this.chessGameFrame=chessGameFrame;
        int width = CHESS_SIZE * 8;
        int height = CHESS_SIZE * 8;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur//启用后或许可以添加一些新的功能
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();

        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                Hover = true;
//                repaint(); // 重绘组件以显示新的图片
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                Hover = false;
//                repaint(); // 重绘组件以显示默认图片
//            }

            @Override
            public void mouseClicked(MouseEvent e) {
                processMouseEvent(e);

            }
        });
    }
    public void Shuffle(){
        gameController.getModel().Shuffle11();
        this.setAgain(gameController.getModel());
        this.repaint();
    }
    public void swapChessAutomatic(){
        gameController.onPlayerSwapChess_auto();;
    }

    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard
                //gridComponents[i][j]=new CellComponent(grid[i][j].getPiece().getColor(),new Point(i,j),CHESS_SIZE);
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    gridComponents[i][j].add(new ChessComponent(CHESS_SIZE, chessPiece,gameController,new ChessboardPoint(i,j)));
                }
            }
        }

    }
    public void initiateGridComponents() {

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);//画上河
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.WHITE, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();//更新显示
        chess.setSelected(false);
        return chess;
    }
    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }//勿管
    public void swapChessBoardComponent(ChessboardPoint point1,ChessboardPoint point2){
        ChessComponent chess1=removeChessComponentAtGrid(point1);
        ChessComponent chess2=removeChessComponentAtGrid(point2);
        setChessComponentAtGrid(point1,chess2);
        setChessComponentAtGrid(point2,chess1);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void swapChess(){
        gameController.onPlayerSwapChess();
    }

    public void nextStep(){
        gameController.onPlayerNextStep();
    }
    public void Suggestion(){gameController.Suggestion();}
    public void saveTheGame(){gameController.onPlayerClickSave();}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//开启抗锯齿
    }
    public void onPlayerClickBigBomb(){
        gameController.onPlayerClickBigBomb();
    }
    public void onPlayerClickRowBomb(){
        gameController.onPlayerClickRowBomb();
    }
//    @Override
//    protected void processMouseEvent(MouseEvent e) {
//        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
//            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
//            if (clickedComponent.getComponentCount() == 0) {
//                System.out.print("None chess here and ");
//                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
//            } else {
//                System.out.print("One chess here and ");
//                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
//            }
//        }
//
////        if (e.getID() == MouseEvent.MOUSE_ENTERED){
////            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
////            if (clickedComponent.getComponentCount() == 0) {
////                System.out.print("None chess here and ");
////                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
////            } else {
////                ChessComponent component=((ChessComponent) clickedComponent.getComponents()[0]);
////                component.setHover(true);
////                component.repaint();
////
////            }
////        }
//
//
//
//    }
    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }


    public void setAgain(Chessboard chessboard){
        for(int i=0;i< CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j< CHESSBOARD_COL_SIZE.getNum();j++){
                if(gridComponents[i][j].getComponentCount()!=0){
                    removeChessComponentAtGrid(new ChessboardPoint(i,j));
                }

            }
        }
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    gridComponents[i][j].add(new ChessComponent(CHESS_SIZE, chessPiece,gameController,new ChessboardPoint(i,j)));
                }
            }
        }
    }

}
