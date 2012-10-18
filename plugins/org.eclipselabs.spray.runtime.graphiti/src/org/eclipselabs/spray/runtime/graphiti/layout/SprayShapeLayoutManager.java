package org.eclipselabs.spray.runtime.graphiti.layout;

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipselabs.spray.runtime.graphiti.layout.SprayLayoutService.SprayAlignment;

/**
 * A special layout manager for single non container shapes
 * 
 * @author jos
 */
public class SprayShapeLayoutManager implements ISprayLayoutManager {

    static final private int MARGIN = 5;
    private Shape            shape;

    public SprayShapeLayoutManager(Shape shape) {
        this.shape = shape;
    }

    @Override
    public boolean isFlexible() {
        return false;
    }

    @Override
    public void layout() {
        SprayAbstractLayoutManager.level++;
        System.out.println(SprayAbstractLayoutManager.indent() + "ShapeLayoutManager.layout() " + SprayLayoutService.getId(shape) + "  " + shape.getGraphicsAlgorithm().toString());

        SprayLayoutData data = SprayLayoutService.getLayoutData(shape);
        GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
        if (data.isVisible()) {
            if (ga instanceof Text) {
                Text text = (Text) ga;
                IDimension dim = GraphitiUi.getUiLayoutService().calculateTextSize(text.getValue(), text.getFont());
                int width = Math.max(dim.getWidth(), data.getMinimumWidth());
                int newWidth = width + (2 * MARGIN);
                layoutService.setSize(ga, newWidth, dim.getHeight() + 2 * MARGIN);
            } else if (ga instanceof Polyline) {
                Polyline pl = (Polyline) ga;
                pl.setLineWidth(4);
                IDimension dim = layoutService.calculateSize(pl);
                System.out.print("ShapeLayout ");
                printPolyLine(pl);
                layoutService.setSize(ga, dim.getWidth(), (dim.getHeight() < 1 ? 4 : dim.getHeight()));
            } else if (ga instanceof Rectangle) {
                Rectangle pl = (Rectangle) ga;
                layoutService.calculateSize(pl);
                layoutService.setSize(ga, data.getMinimumWidth(), data.getMinimumHeight());
            }
        } else {
            layoutService.setSize(shape.getGraphicsAlgorithm(), 0, 0);
        }
        SprayAbstractLayoutManager.level--;
    }

    public void printPolyLine(Polyline line) {
        System.out.print("POLYLINE ");
        for (Point p : line.getPoints()) {
            System.out.print("(" + p.getX() + ", " + p.getY() + ") ");
        }
        System.out.println("");
    }

    @Override
    public void stretchWidthTo(int newWidth) {
        SprayAbstractLayoutManager.level++;
        System.out.println("Stretch ShapeLayout " + shape.getGraphicsAlgorithm().toString());
        layoutService.setWidth(shape.getGraphicsAlgorithm(), newWidth);
        SprayAbstractLayoutManager.level--;
    }

    @Override
    public void stretchHeightTo(int newHeight) {
        layoutService.setHeight(shape.getGraphicsAlgorithm(), newHeight);
    }

    @Override
    public int getMargin() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setMargin(int margin) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getSpacing() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setSpacing(int spacing) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAlignment(SprayAlignment alignment) {
        // TODO Auto-generated method stub

    }

    @Override
    public SprayAlignment getAlignment() {
        // TODO Auto-generated method stub
        return null;
    }
}
