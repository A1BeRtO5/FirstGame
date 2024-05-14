package com.example.gamejavafx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView background1, background2, player, enemy, enemy1;
    private final int background1_Width = 587;
    private ParallelTransition parallelTransition;
    private TranslateTransition enemyTransition;
    private TranslateTransition enemyTransition2;
    private static int backgroundSpeed = 5000;
    public static boolean right = false;
    public static boolean left = false;
    public static boolean jump = false;
//    private int playerSpeed = 1, jumpDownSpeed = 2;
    AnimationTimer timer = new AnimationTimer() {
    private int playerSpeedLeft = 1;
    private double playerSpeedRight = 0.7;
    private int playerSpeedJump = 1;

    private static double playerSpeedDown = 1.2;
        @Override
        public void handle(long l) {//виконується постійно
            if (jump && player.getLayoutY() >70f) {
                player.setLayoutY(player.getLayoutY() - playerSpeedJump);//up
            } else if (player.getLayoutY() <= 165f) {
                jump=false;
                player.setLayoutY(player.getLayoutY() + playerSpeedDown);//down
            }
            if (right && player.getLayoutX() < 350f) {// if(right == true)
                player.setLayoutX(player.getLayoutX() + playerSpeedRight);//right
            }
            if (left && player.getLayoutX() > -8f) {// if(right == true)
                player.setLayoutX(player.getLayoutX() - playerSpeedLeft);//left
            }
            if (isPause && !labelPause.isVisible()) {
                parallelTransition.pause();
                playerSpeedRight = 0;
                playerSpeedLeft = 0;
                playerSpeedJump = 0;
                playerSpeedDown = 0;
                enemyTransition.pause();
                enemyTransition2.pause();
                labelPause.setVisible(true); // робить видимим лейбл
            } else if (!isPause && labelPause.isVisible()) {
                parallelTransition.play();
                playerSpeedRight = 0.7;
                playerSpeedLeft = 1;
                playerSpeedJump = 1;
                playerSpeedDown = 1.2;
                enemyTransition.play();
                enemyTransition2.play();
                labelPause.setVisible(false);
                
            }
            if (player.getBoundsInParent().intersects(enemy.getBoundsInParent())) {//умова якщо плеєр і ворог зустручаються
                labelLose.setVisible(true);
                parallelTransition.pause();
                playerSpeedRight = 0;
                playerSpeedLeft = 0;
                playerSpeedJump = 0;
                playerSpeedDown = 0;
                enemyTransition.pause();
                enemyTransition2.pause();

            }
        }
    };
    @FXML
    private Label labelPause, labelLose;
    public static boolean isPause = false;
    @FXML
    void initialize() {

        TranslateTransition backgroundAnimation = new TranslateTransition(Duration.millis(backgroundSpeed), background1);
        backgroundAnimation.setFromX(0);
        backgroundAnimation.setToX(background1_Width * -1);
        backgroundAnimation.setInterpolator(Interpolator.LINEAR);

        TranslateTransition backgroundAnimation2 = new TranslateTransition(Duration.millis(backgroundSpeed), background2);
        backgroundAnimation2.setFromX(0);
        backgroundAnimation2.setToX(background1_Width * -1);
        backgroundAnimation2.setInterpolator(Interpolator.LINEAR);

        enemyTransition = new TranslateTransition(Duration.millis(3500), enemy);
        enemyTransition.setFromX(0);
        enemyTransition.setToX(background1_Width * -1 - 200);// бомба виходить за межі кеана на 100<--
        enemyTransition.setInterpolator(Interpolator.LINEAR);
        enemyTransition.setCycleCount(Animation.INDEFINITE);// бесконечне спавнення бомби
        enemyTransition.play();

        enemyTransition2 = new TranslateTransition(Duration.millis(4500), enemy1);
        enemyTransition2.setFromX(200);
        enemyTransition2.setFromY(-120);
        enemyTransition2.setToX(background1_Width * -1 - 200);// бомба виходить за межі кеана на 100<--
        enemyTransition2.setInterpolator(Interpolator.LINEAR);
        enemyTransition2.setCycleCount(Animation.INDEFINITE);// бесконечне спавнення бомби
        enemyTransition2.play();

        parallelTransition = new ParallelTransition(backgroundAnimation, backgroundAnimation2);// синхронізуємо анімацію фону
        parallelTransition.setCycleCount(Animation.INDEFINITE);// повторяєм анімацію безкінечно
        parallelTransition.play();
        timer.start();
    }

}
