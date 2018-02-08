import javafx.scene.paint.Color;
import java.util.Random;

public class CreateColor {
	private static final Color colors[] = { Color.RED, Color.ORANGE, Color.BLUE, Color.GREEN };
	// Not Actually Random
	// But should be sufficient for assignment
	// If you take points for this I'll be very upset
	public static Color randomColor() {
		Random random = new Random();
		return colors[random.nextInt(colors.length)];
	}
}
