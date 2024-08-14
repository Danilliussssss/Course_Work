package Animation;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScaleTransition {
    private TranslateTransition tt;
    public ScaleTransition(Node node,double transX,double transY,double FromBorderY,double ToBorderY)
    {
        tt = new TranslateTransition(Duration.millis(500),node);
        tt.setByX(transX);
        tt.setByY(transY);
        tt.setCycleCount(1);
        tt.setFromY(FromBorderY);
        tt.setToY(ToBorderY);

        tt.setAutoReverse(false);
    }
    public void Play(){
        tt.playFromStart();
    }
}
