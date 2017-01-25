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
	private int centerX;
	private volatile Thread p;
	
	public Vision(){
		camera = CameraServer.getInstance().addAxisCamera("10.13.39.11");
		camera.setResolution(640, 480);
		cvSink = CameraServer.getInstance().getVideo();
		pl = new Pipeline();
	}

	public void start(){
		p = new Thread(this, "Vision Thread");
		p.start();
	}

	public int getCenterX(){
		return centerX;
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
				
				if(output.size() != 0){
					Rect r = Imgproc.boundingRect(pl.filterContoursOutput().get(0));
					synchronized (imgLock) {
						centerX = r.x + (r.width / 2);
					}
				}
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}

	public void stop(){
		p = null;
	}
}