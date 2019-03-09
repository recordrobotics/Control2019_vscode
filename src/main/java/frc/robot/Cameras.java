package frc.robot;

import org.opencv.imgproc.Imgproc;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Point;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.MjpegServer;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Cameras {
    final int width;
    final int height;
    private boolean usingForward = true;

    public Cameras(int w, int h) {
        width = w;
        height = h;

        start_automatic();
    }

    public void start_crosshair() {
        new Thread(() -> {
            UsbCamera forward = new UsbCamera("Forward Camera", 0);
            UsbCamera down = new UsbCamera("Down Camera", 1);
            forward.setResolution(width, height);
            down.setResolution(width, height);
        
            CvSink forwardSink = CameraServer.getInstance().getVideo(forward);
            CvSink downSink = CameraServer.getInstance().getVideo(down);
            CvSource outputStream = CameraServer.getInstance().putVideo("Live", width, height);
        
            Mat source = new Mat();
        
            while(!Thread.interrupted()) {
                if(OI.getCameraSwitch()) {
                    usingForward = !usingForward;
                }

                if(usingForward) {
                    forwardSink.grabFrame(source);
                } else {
                    downSink.grabFrame(source);
                }
                Imgproc.drawMarker(source, new Point(width / 2, height / 2), new Scalar(0, 0, 255), Imgproc.MARKER_CROSS, 10, 2);
                outputStream.putFrame(source);
            }
        }).start();
    }

    public void start_automatic() {
        CameraServer.getInstance().startAutomaticCapture("Forward Camera", 0);
        CameraServer.getInstance().startAutomaticCapture("Down Camera", 1);
    }

    public void start_sink() {
        new Thread(() -> {
            UsbCamera forward = new UsbCamera("Forward Camera", 0);
            UsbCamera down = new UsbCamera("Down Camera", 1);
            MjpegServer server = CameraServer.getInstance().startAutomaticCapture(forward);
            
            while(!Thread.interrupted()) {
                if(OI.getCameraSwitch()) {
                    if(usingForward) {
                        server.setSource(down);
                    } else {
                        server.setSource(forward);
                    }
                    usingForward = !usingForward;
                }
            }
        }).start();
    }
}