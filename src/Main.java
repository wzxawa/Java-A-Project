import controller.GameController;
import view.Frame.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame frame=new Frame();
            /*GameDifficultyChooseFrame gameChoose=new GameDifficultyChooseFrame();
            gameChoose.setVisible(true);*/
            /*ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(0));
            mainFrame.setVisible(true);*/
        });


    }
}
