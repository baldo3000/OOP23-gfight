package gfight.view.impl;

import java.awt.Graphics2D;
import java.util.List;

import gfight.common.api.Position2D;
import gfight.engine.graphics.api.GraphicsComponent;
import gfight.engine.graphics.api.ViewCamera;
import gfight.engine.graphics.api.PolygonGraphicsComponent;

/**
 * The renderer for PolygonGraphicsComponent.
 */
public final class PolygonGraphicsRenderer extends AbstractGraphicsComponentRenderer {

    @Override
    boolean isCompValid(final GraphicsComponent gComp) {
        return gComp instanceof PolygonGraphicsComponent;
    }

    @Override
    void renderComp(final Graphics2D g, final ViewCamera camera) {
        final var gComp = getGraphicsComponent();
        final List<Position2D> pointList = camera.getScreenPositions(gComp.getPositions(), gComp.getType());
        final int[] xPoints = new int[pointList.size()];
        final int[] yPoints = new int[pointList.size()];
        for (int i = 0; i < pointList.size(); i++) {
            xPoints[i] = (int) pointList.get(i).getX();
            yPoints[i] = (int) pointList.get(i).getY();
        }
        g.fillPolygon(xPoints, yPoints, pointList.size());
    }

}
