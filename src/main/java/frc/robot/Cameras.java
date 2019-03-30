package frc.robot;

import org.opencv.imgproc.Imgproc;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Point;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Cameras {
    final int width;
    final int height;
    private UsbCamera fCam = null;
    private UsbCamera uCam = null;
    private VideoSink server = null;
    private boolean usingForward = true;

    public Cameras(int w, int h) {
        width = w;
        height = h;

        start_mjpeg();
    }

    /*public void start_crosshair() {
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
               // Imgproc.drawMarker(source, new Point(width / 2, height / 2), new Scalar(0, 0, 255), Imgproc.MARKER_CROSS, 10, 2);
                outputStream.putFrame(source);
                System.out.println("put frame");
            }
        }).start();
    }*/

    public void start() {
        fCam = CameraServer.getInstance().startAutomaticCapture("Forward", 0);
        uCam = new UsbCamera("Up", 1);

        fCam.setResolution(width, height);
        uCam.setResolution(width, height);

        fCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        uCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        server = CameraServer.getInstance().getServer();
        usingForward = true;
    }

    public void start_mjpeg() {
        fCam = new UsbCamera("Forward", 0);
        uCam = new UsbCamera("Up", 1);

        fCam.setResolution(width, height);
        uCam.setResolution(width, height);

        fCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        uCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        server = new MjpegServer("Camera stream", 1181);
        server.setSource(fCam);
        usingForward = true;
    }

    public boolean usingForward() { 
        return usingForward;
    }

    public void toggle() {
        System.out.println("toggling cameras");
        if(server == null) {
            return;
        }

        if(usingForward && uCam != null) {
            server.setSource(uCam);
            usingForward = false;
        }
        else if(!usingForward && fCam != null) {
            server.setSource(fCam);
            usingForward = true;
        }
    }

    /*public void start_sink() {
        new Thread(() -> {
            UsbCamera forward = new UsbCamera("Forward Camera", 0);
            UsbCamera down = new UsbCamera("Down Camera", 1);
            MjpegServer server = CameraServer.getInstance().startAutomaticCapture(forward);
            System.out.println(server.getListenAddress() + ":" + server.getPort());
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
    }*/
}