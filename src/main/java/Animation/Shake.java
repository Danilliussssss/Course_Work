package Animation;

import javafx.animation.Animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition tt;


    public Shake(Node node,double mls)
    {

       tt = new TranslateTransition(Duration.millis(mls),node);
       tt.setFromX(0f);
       tt.setByX(10f);
       tt.setCycleCount(3);
       tt.setAutoReverse(true);
    }
    public void Play(){
        tt.playFromStart();
    }
}
