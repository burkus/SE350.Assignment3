import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.List;
import java.util.LinkedList;

public class ShapeContainer implements ShapeComponent {
  private Rectangle rect;
  private Color color;
  private Point2D position;
  private boolean moveable;
  private List<ShapeComponent> components = new LinkedList<ShapeComponent>();

  public ShapeContainer(Point2D position, boolean moveable) {
    this.position = position;
    this.color = CreateColor.randomColor();
    this.moveable = moveable;
    rect = new Rectangle();
    rect.setX(position.getX());
    rect.setY(position.getY());
    rect.setWidth(100);
    rect.setHeight(100);
    rect.setStroke(color);
    rect.setStrokeWidth(2);
    rect.setFill(color);
  }

  public Rectangle getRect() {
    return rect;
  }
  
  public void add(ShapeComponent gc) {
	  if(components.contains(gc)) return;
	  components.add(gc);
  }
  
  public void remove(ShapeComponent gc) {
	  if(components.contains(gc)) {
		  components.remove(gc);
	  }
  }
  
  public Color getColor() {
	  return color;
  }
  
  public boolean containsCircle(Circle circle) {
	  Point2D circleCenter = new Point2D(circle.getCenterX(), circle.getCenterY());
	  return rect.contains(circleCenter);
  }

  public void move(double deltaX, double deltaY) {
    rect.setX(rect.getX() + deltaX);
    rect.setY(rect.getY() + deltaY);
    for(ShapeComponent comp : components) {
      comp.move(deltaX, deltaY);
    }
  }
}
