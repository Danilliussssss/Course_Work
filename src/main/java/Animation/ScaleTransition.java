package Animation;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScaleTransition {
    private TranslateTransition tt;
    public ScaleTransition(Node node,double trans)
    {
        tt = new TranslateTransition(Duration.millis(1000),node);
        tt.setByX(trans);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);
    }
    public void Play(){
        tt.playFromStart();
    }
}
