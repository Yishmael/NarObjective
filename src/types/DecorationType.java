package types;

public enum DecorationType {
    meal1(32, 32, 0 * 32, 0 * 32),
    table1(32, 32, 1 * 32, 0 * 32),
    chair1(32, 32, 4 * 32, 1 * 32),
    desk1(64, 32, 9 * 32, 2 * 32),
    oven1(32, 32, 11 * 32, 2 * 32),
    roof1(64, 32, 11 * 32, 0 * 32),
    roof3(32, 32, 0 * 32, 1 * 32),
    fence1(32, 32, 2 * 32, 2 * 32),
    torch1(32, 32, 9 * 32, 1 * 32),
    window1(32, 32, 8 * 32, 0 * 32),
    flag1(32, 32, 15 * 32, 1 * 32),
    anchor1(32, 32, 3 * 32, 2 * 32),
    rope1(32, 32, 5 * 32, 2 * 32),
    chimney1(32, 32, 2 * 32, 1 * 32),
    bug1(10, 10, 15 * 32, 0 * 32),

    nil(0, 0, 0, 0),;
    private int imageWidth, imageHeight, spriteX, spriteY;

    private DecorationType(int imageWidth, int imageHeight, int spriteX, int spriteY) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.spriteX = spriteX;
        this.spriteY = spriteY;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getSpriteX() {
        return spriteX;
    }

    public int getSpriteY() {
        return spriteY;
    }

}
