package content.block;

import content.ObjectID;

public class SlimeHolder extends Square {
    private static final ObjectID type = ObjectID.SLIMEHOLDER;

    public SlimeHolder(float x, float y, float width, float height, int scale) {
        super(x, y, type, width, height, scale);
    }
}
