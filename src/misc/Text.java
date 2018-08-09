package misc;

import java.awt.Font;

public class Text {

    public static final Font font = new Font("Verdana", Font.TRUETYPE_FONT, 20);

    public static String wordWrap(String text, int maxWidth) {
        String temp = text;
        text = "";
        int width = 0;
        for (String word: temp.split(" ")) {
            if (width + word.length() * 12 <= maxWidth) {
                text += word + " ";
                width += word.length() * 12;
            } else {
                text += "\n" + word + " ";
                width = word.length() * 12;
            }
        }
        return text;
    }

}
