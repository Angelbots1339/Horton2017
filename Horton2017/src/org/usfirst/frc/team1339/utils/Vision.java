package org.usfirst.frc.team1339.utils;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.first.wpilibj.CameraServer;

public class Vision implements Runnable{

	private AxisCamera camera;
	private Object imgLock = new Object();
	private CvSink cvSink;
	private Pipeline pl;
	private ArrayList<Integer> centerX;
	private ArrayList<Integer> heights;
	private volatile Thread p;

	public Vision(){
		camera = CameraServer.getInstance().addAxisCamera("10.13.39.11");
		camera.setResolution(640, 480);
		cvSink = CameraServer.getInstance().getVideo();
		pl = new Pipeline();
		centerX = new ArrayList<Integer>();
		heights = new ArrayList<Integer>();
	}

	public void start(){
		p = new Thread(this, "Vision Thread");
		p.start();
	}

	public synchronized int[] getCenterX(){
		int length = centerX.size();
		int[] output = new int[length];
		for(int x = 0; x < length; x++){
			output[x] = centerX.get(x);
		}
		return output;
	}

	public synchronized int[] getHeight(){
		int length = heights.size();
		int[] output = new int[length];
		for(int x = 0; x < length; x++){
			output[x] = heights.get(x);
		}
		return output;
	}

	public void run() {
		Mat source = new Mat();
		Thread thisThread = Thread.currentThread();
		while(p == thisThread){
			cvSink.grabFrame(source);
			pl.setsource0(source);
			try{
				pl.process();
				ArrayList<MatOfPoint> output = pl.filterContoursOutput();
				synchronized(imgLock){
					centerX.clear();
					heights.clear();
					for(int x = 0; x < output.size(); x++){
						Rect r = Imgproc.boundingRect(pl.filterContoursOutput().get(x));
						centerX.add(r.x + (r.width / 2));
						heights.add(r.height);
					}
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}

	public void stop(){
		centerX.clear();
		p = null;
	}
}