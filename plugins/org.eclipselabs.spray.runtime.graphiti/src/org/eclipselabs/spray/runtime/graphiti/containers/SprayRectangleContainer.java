package org.eclipselabs.spray.runtime.graphiti.containers;

import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Color;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.IColorConstant;

public class SprayRectangleContainer implements ISprayContainer {

    protected IPeCreateService peCreateService = null;

    protected IGaService       gaService       = null;

    protected Diagram          targetDiagram   = null;

    protected SprayLayout      layout          = new SprayLayout(ISprayColorConstants.BLACK, ISprayColorConstants.WHITE, 1);

    public SprayRectangleContainer() {
        peCreateService = Graphiti.getPeCreateService();
        gaService = Graphiti.getGaService();
    }

    public void setLayout(SprayLayout newlayout) {
        layout = newlayout;
    }

    /*
     * (non-Javadoc) * @see org.sample.common.ISprayContainer#addShape(org.eclipse.graphiti.features.context.IAddContext, java.lang.Object)
     */
    @Override
    public ContainerShape createContainer(IAddContext context, Object addedModelElement) {
        targetDiagram = (Diagram) context.getTargetContainer();

        // check whether the context has a size (e.g. from a create feature)
        // otherwise define a default size for the shape
        int width = context.getWidth() <= 0 ? DEFAULT_WIDTH : context.getWidth();
        int height = context.getHeight() <= 0 ? DEFAULT_HEIGHT : context.getHeight();

        ContainerShape mainShape = createContainerShape(addedModelElement, targetDiagram, context.getX(), context.getY(), width, height, true, layout);
        Graphiti.getPeService().setPropertyValue(mainShape, CONCEPT_SHAPE_KEY, "TopContainer");

        ContainerShape textContainer = createContainerShape(addedModelElement, mainShape, 0, 0, width, height, false, layout);
        Graphiti.getPeService().setPropertyValue(textContainer, CONCEPT_SHAPE_KEY, TEXTBOX);

        Graphiti.getPeService().sendToFront(mainShape);
        return mainShape;
    }

    private int cornerWidth  = 0;
    private int cornerHeight = 0;

    private ContainerShape createContainerShape(Object addedModelElement, ContainerShape parentShape, int x, int y, int width, int height, boolean active, SprayLayout layout) {
        ContainerShape c = peCreateService.createContainerShape(parentShape, active);
        {
            RoundedRectangle rectangle = gaService.createRoundedRectangle(c, getCornerWidth(), getCornerHeight());
            if (layout == null) {
                rectangle.setTransparency(100.00);
                rectangle.setLineVisible(false);
                rectangle.setLineWidth(0);
            } else {
                rectangle.setForeground(manageColor(layout.getLine()));
                rectangle.setBackground(manageColor(layout.getFill()));
                rectangle.setLineWidth(layout.getLineWidth());
            }
            gaService.setLocationAndSize(rectangle, x, y, width, height);
        }
        return c;
    }

    /**
     * Manage color. (Copied from Graphiti)
     * 
     * @param colorConstant
     *            the color constant
     * @return the color
     */
    protected Color manageColor(IColorConstant colorConstant) {
        return Graphiti.getGaService().manageColor(targetDiagram, colorConstant);
    }

    private static final int MIN_HEIGHT = 60;

    private static final int MIN_WIDTH  = 100;

    /*
     * (non-Javadoc)
     * @see org.sample.common.ISprayContainer#layoutConcept(org.eclipse.graphiti.features.context.ILayoutContext)
     */
    @Override
    public boolean layoutContainer(ILayoutContext context) {
        boolean anythingChanged = false;
        ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
        GraphicsAlgorithm containerGa = containerShape.getGraphicsAlgorithm();

        // height
        if (containerGa.getHeight() < MIN_HEIGHT) {
            containerGa.setHeight(MIN_HEIGHT);
            anythingChanged = true;
        }

        // width
        if (containerGa.getWidth() < MIN_WIDTH) {
            containerGa.setWidth(MIN_WIDTH);
            anythingChanged = true;
        }

        IGaService gaService = Graphiti.getGaService();
        int width = containerGa.getWidth();
        int height = containerGa.getHeight();
        int leftMargin = 3;
        int y = 0;

        Shape shape = SprayContainerService.findTextShape(containerShape);
        GraphicsAlgorithm graphicsAlgorithm = shape.getGraphicsAlgorithm();
        RoundedRectangle rectangle = (RoundedRectangle) graphicsAlgorithm;
        gaService.setLocationAndSize(rectangle, 0, 0, width, height);
        ContainerShape textBox = (ContainerShape) shape;
        for (Shape sh : textBox.getChildren()) {
            String textType = Graphiti.getPeService().getPropertyValue(sh, CONCEPT_SHAPE_KEY);
            String id = Graphiti.getPeService().getPropertyValue(sh, "ID");
            if (textType.equalsIgnoreCase(TEXT)) {
                GraphicsAlgorithm ga = sh.getGraphicsAlgorithm();
                AbstractText text = (AbstractText) ga;
                text.setWidth(width);
                text.setX(0);
                text.setY(y);
                if (ga instanceof MultiText) {
                    MultiText mtext = (MultiText) ga;
                    mtext.setHeight(TEXT_LINE_HEIGHT * 3);
                    y += TEXT_LINE_HEIGHT * 3;
                    mtext.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
                } else if (ga instanceof Text) {
                    text.setHeight(TEXT_LINE_HEIGHT);
                    y += TEXT_LINE_HEIGHT;
                }
            } else if (textType.equalsIgnoreCase("line")) {
                GraphicsAlgorithm ga = sh.getGraphicsAlgorithm();
                Polyline polyline = (Polyline) ga;
                Point newFirstPoint = gaService.createPoint(0, y);
                Point newSecondPoint = gaService.createPoint(width, y);
                polyline.getPoints().set(0, newFirstPoint);
                polyline.getPoints().set(1, newSecondPoint);
                gaService.setLocation(polyline, 0, 0);
                anythingChanged = true;
                y += 4;
            }
        }
        graphicsAlgorithm.setHeight(y);
        IDimension size = gaService.calculateSize(graphicsAlgorithm, true);
        containerGa.setHeight(y);
        return anythingChanged;
    }

    public int getCornerWidth() {
        return cornerWidth;
    }

    public void setCornerWidth(int cornerWidth) {
        this.cornerWidth = cornerWidth;
    }

    public int getCornerHeight() {
        return cornerHeight;
    }

    public void setCornerHeight(int cornerHeight) {
        this.cornerHeight = cornerHeight;
    }

}
