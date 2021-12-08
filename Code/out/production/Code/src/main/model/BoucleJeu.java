package main.model;

public class BoucleJeu implements Runnable{

    private boolean running;
    private final double updateRate = 1./60.;

    private long nextStatTime;
    private int fps, ups;

    @Override
    public void run() {
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while(running){
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000;
            accumulator += lastRenderTimeInSeconds;
            lastUpdate = currentTime;

            while(accumulator > updateRate){
                update();
                accumulator -= updateRate;
            }
            render();
            printStats();
        }
    }

    private void printStats() {
        if(System.currentTimeMillis() > nextStatTime){
            System.out.println("FPS : " + fps + ", UPS : " + ups);
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void render() {
        fps++;
    }

    private void update() {
        ups++;

    }
    
}
