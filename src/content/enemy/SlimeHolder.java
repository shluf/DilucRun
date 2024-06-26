package content.enemy;

import content.ObjectID;
import content.block.Square;

public class SlimeHolder extends Square {

    public SlimeHolder(float x, float y, float width, float height, int scale) {
        super(x, y, ObjectID.SLIMEHOLDER, width, height, scale);
    }
}
