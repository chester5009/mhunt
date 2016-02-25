import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

import javax.swing.JFrame;

public abstract class Display {
	private static boolean isEnable=false;
	private static JFrame window;
	private static Canvas canvas;
	
	private static BufferedImage buffer;
	private static int[] bufferData;
	private static Graphics bufferGraphics;
	private static int clearColor;
	
	private static float delta=0;
	
	private static BufferStrategy bufferStrategy;
	
	
	public static void createWindow(int width, int height, String title, int _clearColor, int numBuffers){
		if(isEnable)
			return;
		
		window=new JFrame(title);
		canvas=new Canvas();
		Dimension size=new Dimension(width, height);
		
		canvas.setPreferredSize(size);
		
		window.getContentPane().add(canvas);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		buffer=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		bufferData=((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
		bufferGraphics=buffer.getGraphics();
		((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);;
		clearColor=_clearColor;
		
		canvas.createBufferStrategy(numBuffers);
		bufferStrategy=canvas.getBufferStrategy();
		
		
		isEnable=true;
	}
	
	public static void clear(){
		Arrays.fill(bufferData, clearColor);
	}
	
	
	
	public static void swapBuffers(){
		Graphics g=bufferStrategy.getDrawGraphics();
		g.drawImage(buffer, 0,0,null);
		bufferStrategy.show();
	}
	
	public static Graphics2D getGraphics(){
		return (Graphics2D)bufferGraphics;
	}
	
	public static void destroy(){
		if(!isEnable){
			return;
		}
		window.dispose();
	}

}
