package frc.robot;

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

    public void start_mjpeg() {
        fCam = new UsbCamera("Forward", 0);
        uCam = new UsbCamera("Back", 1);

        fCam.setResolution(width, height);
        uCam.setResolution(width, height);

        fCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        uCam.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        server = new MjpegServer("Camera stream", 1181);
        server.setSource(fCam);
        usingForward = true;

        SmartDashboard.putBoolean("Using Forward", usingForward);
    }

    public boolean usingForward() { 
        return usingForward;
    }

    public void toggle() {
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

        SmartDashboard.putBoolean("Using Forward", usingForward);
    }
}