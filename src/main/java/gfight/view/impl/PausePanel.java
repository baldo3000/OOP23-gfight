package gfight.view.impl;

import gfight.engine.api.Engine;
import gfight.engine.api.Engine.EngineStatus;
import gfight.engine.input.api.InputEventListener;
import gfight.engine.input.api.InputEventValue;
import gfight.engine.input.impl.InputEventFactoryImpl;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Box;

/**
 * The panel that generates the view for the pause.
 */
public final class PausePanel extends JPanel {

    /** Serializable UID. */
    private static final long serialVersionUID = 1675654981775835696L;

    private static final String PATH_STRING = "images";
    private static final int TITLE_FONT_DIM = 55;
    private static final int BUTTON_FONT_DIM = 22;
    private static final int TILE_SPACING = 5;
    private static final int RADIUS = 10;
    /** The BackgroundImage for Pause. */
    private final ImageIcon backgroundImage;

    /**
     * It creates a pause panel view.
     * 
     * @param engine the game engine.
     */
    public PausePanel(final Engine engine) {
        setFocusable(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(PATH_STRING + "/Pause.png"));
        final JLabel titleLabel = new JLabel("Paused");
        titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_DIM));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JButton continueButton = new JButton("  Continue   ");
        continueButton.setFont(new Font("Arial", Font.ITALIC, BUTTON_FONT_DIM));
        continueButton.setBackground(Color.WHITE);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setBorder(new RoundedBorder(RADIUS));
        continueButton.addActionListener(e -> engine.changeStatus(EngineStatus.GAME));

        final JButton goToMenuButton = new JButton("Go to Menu");
        goToMenuButton.setFont(new Font("Arial", Font.ITALIC, BUTTON_FONT_DIM));
        goToMenuButton.setBackground(Color.WHITE);
        goToMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        goToMenuButton.setBorder(new RoundedBorder(RADIUS));
        goToMenuButton.addActionListener(e -> engine.changeStatus(EngineStatus.MENU));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                if (e.getKeyCode() == InputEventValue.Value.PAUSE.getKey()) {
                    engine.changeStatus(EngineStatus.GAME);
                    if (engine instanceof InputEventListener listener) {
                        listener.notifyInputEvent(
                                new InputEventFactoryImpl().pressedValue(InputEventValue.Value.RESET));
                    }
                }
            }
        });

        this.add(Box.createVerticalGlue());
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(TILE_SPACING));
        this.add(continueButton);
        this.add(Box.createVerticalStrut(TILE_SPACING));
        this.add(goToMenuButton);
        this.add(Box.createVerticalGlue());

    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
