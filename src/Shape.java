import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Shape implements ShapeComponent {
  private Color initialColor;
  private Color currentColor;
  private Point2D position;
  private boolean moveable;
  private Circle circle;
  private ShapeContainer daddy;

  public Shape(Point2D position, Color color, boolean moveable) {
      initialColor = color;
      currentColor = color;
      this.position = position;
      this.moveable = moveable;
      circle = new Circle();
      circle.setCenterX(position.getX());
      circle.setCenterY(position.getY());
      circle.setRadius(15);
      circle.setFill(color);
      circle.setStroke(Color.BLACK);
      circle.setStrokeWidth(3);
      // Event Handling
      circle.setOnMouseEntered((e) -> {
    	  circle.setFill(initialColor);
      });
      
      circle.setOnMouseExited((e) -> {
    	  circle.setFill(currentColor);
      });
  }

  public Circle getCircle() {
    return circle;
  }
  
  public void setColor(Color color) {
	  currentColor = color;
	  circle.setFill(color);
  }
  
  public void setDaddy(ShapeContainer sc) {
	  daddy = sc;
  }
  
  public ShapeContainer getDaddy() {
	  return daddy;
  }

  public void move(double deltaX, double deltaY) {
    position.add(new Point2D(deltaX, deltaY));
    circle.setCenterX(circle.getCenterX() + deltaX);
    circle.setCenterY(circle.getCenterY() + deltaY);
  }
}
