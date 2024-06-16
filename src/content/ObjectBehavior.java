package content;

import java.awt.*;

public interface ObjectBehavior {
    Rectangle getBoundsAttackRight();
    Rectangle getBoundsAttackLeft();
    void decreaseLives();
    int getLives();
    ObjectAction getAction();
}
