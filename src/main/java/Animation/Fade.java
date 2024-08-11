package Animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Fade {

    private FadeTransition fade;

    public Fade(Node node_param, double trans,double SetFromValue, double SetToValue) {
        // Инициализация объекта FadeTransition
        fade = new FadeTransition();

        // Привязка узла и настройка параметров анимации
        fade.setNode(node_param);
        fade.setDuration(Duration.millis(trans));
        fade.setFromValue(SetFromValue);
        fade.setToValue(SetToValue);
        fade.setCycleCount(1);
    }

    public void Play() {
        fade.playFromStart();
    }
}
