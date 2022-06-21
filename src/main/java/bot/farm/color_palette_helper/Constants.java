package bot.farm.color_palette_helper;

public class Constants {
    public static final String START = "Welcome to the bot. I can accept hex color code (for example - #B22222 or #87CEFA) and return different color palettes.";
    public static final String MENU = "Choose the type of color combination";
    public static final String WRONG_HEX = "Wrong hex code. Please enter correct hex color code";
    public static final String HEX_CODE_COLOR_REGEX = "^#(?:[\\da-fA-F]{3}){1,2}$";

    public static final String COMPLEMENTARY = "complementary";
    public static final String MONOCHROMATIC = "monochromatic";
    public static final String ANALOGOUS = "analogous";
    public static final String TRIADIC = "triadic";
    public static final String TETRADIC = "tetradic";
    public static final String HELP = """
            Palette types:
            1. Complementary color palettes are formed by using two colors on opposite sides of the color wheel. They are great for conveying a sense of balance.
            2. Monochromatic color schemes consist of different shades and depths of the same color. In such schemes, all colors are derived from a single color.
            3. Analogs are created by pairing one color with two others on either side of it on the color wheel. These palettes express uniformity and consistency.
            4. Triadic. The combination of three colors at the corners of an equilateral triangle. This option is suitable if you need more variety in colors.
            5. Tetradic. The combination of four colors in the corners of the square. In fact, here is a combination of two pairs of complementary colors. Suitable if you need to create a colorful and varied image.

            A little tip: Use colors in the proportion of 60% + 30% + 10% - you can never go wrong. 60% belongs to your dominant shade, 30% to your secondary and 10% to your accents. It's always best to use this rule to keep things visually compelling and balanced.""";
}
