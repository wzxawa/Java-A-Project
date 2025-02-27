package view;

import java.util.TimerTask;

import controller.GameController;

public class Auto_NextStep extends TimerTask {

    private GameController gameController;

    public Auto_NextStep(GameController gameController){
        this.gameController=gameController;
    }


    @Override
    public void run() {

        gameController.onPlayerNextStep();
    }
}
