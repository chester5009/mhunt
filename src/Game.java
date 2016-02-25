
public class Game implements Runnable {

	// int width,int height,String title,int _clearColor ,int numBuffers

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static final String TITLE = "MHunt";

	public static final int CLEAR_COLOR = 0xffffff;

	public static final int NUM_BUFFERS = 3;

	public static final float UPDATE_RATE = 60.f;
	public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;

	public static final long IDLE_TIME = 1;

	private boolean running;
	private Thread gameThread;

	public Game() {
		running = false;
		Display.createWindow(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {

			gameThread.join();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		unload();

	}

	public void update() {

	}

	public void render() {

	}

	@Override
	public void run() {
		float delta = 0f;
		long lastTime = Time.getTime();
		while (running) {

			long nowTime = Time.getTime();
			long elapsed = nowTime - lastTime;
			lastTime = nowTime;

			boolean render = false;
			delta += (elapsed / UPDATE_INTERVAL);

			while (delta > 1) {
				update();
				delta--;
				render = true;
			}
			if (render) {
				render();
			} else {
				try {
					Thread.sleep(IDLE_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private void unload() {
		Display.destroy();
	}
}
