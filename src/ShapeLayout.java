import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.geometry.Point2D;
import java.util.LinkedList;
import java.util.List;

public class ShapeLayout extends Application {
  private List<Shape> shapes = new LinkedList<Shape>();
  private List<ShapeContainer> shapeContainers = new LinkedList<ShapeContainer>();
  private boolean circleHasBeenDragged = false;
  private boolean containerHasBeenDragged = false;
  private Point2D lastPosition;
  private Color defaultColor = Color.BLACK;
  
  private void createShape(Point2D point, Color color, AnchorPane root) {
	  Shape shape = new Shape(point, color, true);
	  shapes.add(shape);
	  root.getChildren().add(shape.getCircle());
  }
  
  private void createShapeContainer(Point2D point, AnchorPane root) {
	  ShapeContainer shapeContainer = new ShapeContainer(point, true);
	  shapeContainers.add(shapeContainer);
	  root.getChildren().add(shapeContainer.getRect());
  }
  
  private Shape shapeIntersectionWithClick(Point2D click) {
	  for(Shape shape : shapes) {
 		 if(shape.getCircle().contains(click)) {
 	        return shape;
 	     }
 	  }
	  return null;
  }
  
  private ShapeContainer shapeContainerIntersectionWithClick(Point2D click) {
	  for(ShapeContainer shapeContainer : shapeContainers) {
 		 if(shapeContainer.getRect().contains(click)) {
 	        return shapeContainer;
 	     }
 	  }
	  return null;
  }
  
  private ShapeContainer shapeContainerIntersectionWithShape(Shape shape) {
	  for(ShapeContainer shapeContainer : shapeContainers) {
	 		 if(shapeContainer.containsCircle(shape.getCircle())) {
	 	        return shapeContainer;
	 	     }
	 	  }
		  return null;
  }

  EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {
    public void handle(MouseEvent e) {
      Point2D clickPoint = new Point2D(e.getX(), e.getY());
      System.out.println(clickPoint.getX() + " " + clickPoint.getY());

     if(lastPosition != null) {
    	 Shape intersectingShape = shapeIntersectionWithClick(clickPoint);
    	 ShapeContainer intersectingShapeContainer = shapeContainerIntersectionWithClick(clickPoint); 
    	 if(intersectingShape != null) {
    		 circleHasBeenDragged = true;
	         double deltaX = clickPoint.getX() - lastPosition.getX();
	         double deltaY = clickPoint.getY() - lastPosition.getY();
	         intersectingShape.move(deltaX, deltaY);
    	 }
    	 else if(intersectingShapeContainer != null) {
    		 containerHasBeenDragged = true;
	         double deltaX = clickPoint.getX() - lastPosition.getX();
	         double deltaY = clickPoint.getY() - lastPosition.getY();
	         intersectingShapeContainer.move(deltaX, deltaY);
	      }
      }
      lastPosition = clickPoint;
    }
  };
  
  EventHandler<MouseEvent> releaseHandler = new EventHandler<MouseEvent>() {
	  public void handle(MouseEvent e) {
		  System.out.println("Click Released at: " + e.getX() + ", " + e.getY());
		  Point2D clickPoint = new Point2D(e.getX(), e.getY());
		  Shape intersectingShape = shapeIntersectionWithClick(clickPoint);
		  if(intersectingShape != null && circleHasBeenDragged) {
			  ShapeContainer intersectingShapeContainer = shapeContainerIntersectionWithShape(intersectingShape);
			  if(intersectingShapeContainer != null) {
				  intersectingShapeContainer.add(intersectingShape);
				  intersectingShape.setColor(intersectingShapeContainer.getColor());
				  intersectingShape.getCircle().toFront();
				  intersectingShape.setDaddy(intersectingShapeContainer);
			  }
			  else {
				  ShapeContainer daddy = intersectingShape.getDaddy();
				  if(daddy != null) {
					  daddy.remove(intersectingShape);
					  intersectingShape.setDaddy(null);
					  intersectingShape.setColor(defaultColor);
				  }
			  }
		  }
		  lastPosition = null;
	  }
  };

  public void start(Stage primaryStage) {
    try {
			AnchorPane root = new AnchorPane();
			Scene scene = new Scene(root, 500, 500);
			scene.setOnMouseDragged(dragHandler);
			scene.setOnMouseReleased(releaseHandler);
			
			scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					Point2D clickPoint = new Point2D(e.getX(), e.getY());
					if(shapeIntersectionWithClick(clickPoint) != null) return;
					else circleHasBeenDragged = false;
					if(shapeContainerIntersectionWithClick(clickPoint) != null) return;
					else containerHasBeenDragged = false;
					
					MouseButton button = e.getButton();
					// Right Click creates a shape
					if(button == MouseButton.PRIMARY && !circleHasBeenDragged && !containerHasBeenDragged) {
						createShape(clickPoint, defaultColor, root);
						circleHasBeenDragged = false;
					}
					// Left Click creates a shape container
					else if(button == MouseButton.SECONDARY) {
						createShapeContainer(clickPoint, root);
					}
				}
			});
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lab 3");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
  }
  
  public void setDefaultColor(Color c) {
	  defaultColor = c;
  }
  
  public static void main(String[] args) {
	  launch(args);
  }
}
