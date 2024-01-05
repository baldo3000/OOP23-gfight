package gfight.view;

import gfight.common.Position2D;
import gfight.common.Rotation2D;

/**
 * GraphicsComponent for Text visualization.
 */
public class TextGraphicsComponent extends AbstractGraphicsComponent {

    private String text;

    /**
     * Contructor of a TextGraphicsComponent.
     * @param color
     * @param pos
     * @param rot
     * @param text
     */
    TextGraphicsComponent(final EngineColor color, final Position2D pos, final Rotation2D rot, final String text) {
        super(color, pos, rot);
        this.text = text;
    }

    /**
     * @return text to be printed.
     */
    public String getText() {
        return text;
    }

    /**
     * A setter for the text of the GraphicsComponent.
     * @param text
     */
    public void setText(final String text) {
        this.text = text;
    }

}
