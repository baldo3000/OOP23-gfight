package gfight.engine;

import gfight.view.EngineView;
import gfight.view.SwingView;

/**
 * Implementation of the game engine.
 */
public class EngineImpl implements Engine {

    private static final int FRAME_RATE = 60;
    private static final long FRAME_LENGHT = 1000 / FRAME_RATE;

    private EngineView view;

    @Override
    public void initialize() {
        view = new SwingView(this);
        view.initialize();
    }

    @Override
    public void mainLoop() {
        long prevFrameStartTime = System.currentTimeMillis();
        while (isAppRunning()) {
            final long frameStartTime = System.currentTimeMillis();
            final long deltaTime = frameStartTime - prevFrameStartTime;
            processInput();
            update(deltaTime);
            render();
            waitNextFrame(frameStartTime);
            prevFrameStartTime = frameStartTime;
        }
    }

    /*Needs to be changed--------------------- */
    private void waitNextFrame(final long frameStartTime) {
        final long dt = System.currentTimeMillis() - frameStartTime;
        if (dt < FRAME_LENGHT) {
            try {
                Thread.sleep(FRAME_LENGHT - dt);
            } catch (InterruptedException e) {
                System.exit(1);
            }
        }
    }

    private void render() {
        view.render();
    }

    private void update(final long deltaTime) {
    }

    private void processInput() {
    }

    private boolean isAppRunning() {
        return true;
    }

}
