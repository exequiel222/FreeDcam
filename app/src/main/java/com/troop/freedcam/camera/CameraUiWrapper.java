package com.troop.freedcam.camera;

import android.hardware.Camera;
import android.os.Build;
import android.view.SurfaceHolder;

import com.troop.freedcam.camera.modules.ModuleHandler;

import com.troop.freedcam.camera.parameters.CamParametersHandler;
import com.troop.freedcam.camera.parameters.I_ParametersLoaded;
import com.troop.freedcam.i_camera.AbstractCameraUiWrapper;
import com.troop.freedcam.i_camera.modules.I_ModuleHandler;
import com.troop.freedcam.ui.AppSettingsManager;
import com.troop.freedcam.ui.TextureView.ExtendedSurfaceView;

import java.lang.annotation.Target;

/**
 * Created by troop on 16.08.2014.
 */
public class CameraUiWrapper extends AbstractCameraUiWrapper implements SurfaceHolder.Callback, I_ParametersLoaded, Camera.ErrorCallback
{
    protected ExtendedSurfaceView preview;

    public AppSettingsManager appSettingsManager;



    public CameraUiWrapper(){};

    public CameraUiWrapper(ExtendedSurfaceView preview, AppSettingsManager appSettingsManager, I_error errorHandler)
    {
        this.preview = preview;
        this.appSettingsManager = appSettingsManager;
        //attache the callback to the Campreview
        preview.getHolder().addCallback(this);

        this.errorHandler = errorHandler;
        BaseCameraHolder baseCameraHolder =new BaseCameraHolder();
        cameraHolder = baseCameraHolder;
        baseCameraHolder.errorHandler = errorHandler;
        camParametersHandler = new CamParametersHandler(cameraHolder, appSettingsManager);
        baseCameraHolder.ParameterHandler = camParametersHandler;
        camParametersHandler.ParametersEventHandler.AddParametersLoadedListner(this);
        preview.ParametersHandler = camParametersHandler;

        moduleHandler = new ModuleHandler(cameraHolder, appSettingsManager);
        Focus = new FocusHandler(this);
        baseCameraHolder.Focus = Focus;

    }




    private boolean openCamera()
    {
        if (Camera.getNumberOfCameras() > 0) {
            cameraHolder.OpenCamera(appSettingsManager.GetCurrentCamera());

            return true;
        }

        return false;
    }

    public void StartPreviewAndCamera() {
        if (openCamera())
        {
            BaseCameraHolder baseCameraHolder = (BaseCameraHolder) cameraHolder;
            while (!baseCameraHolder.isRdy)
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            cameraHolder.GetCamera().setErrorCallback(this);
            cameraHolder.SetSurface(preview.getHolder());
            CamParametersHandler camParametersHandler1 = (CamParametersHandler) camParametersHandler;
            camParametersHandler1.LoadParametersFromCamera();

        }
    }


    public void StopPreviewAndCamera()
    {
        cameraHolder.StopPreview();
        cameraHolder.CloseCamera();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        StartPreviewAndCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        StopPreviewAndCamera();
    }

    @Override
    public void ParametersLoaded() {
        cameraHolder.StartPreview();
    }

    @Override
    public void onError(int i, Camera camera)
    {
        errorHandler.OnError("Got Error from camera: " + i);
        /*try
        {
            StopPreviewAndCamera();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }*/
        try
        {
            cameraHolder.CloseCamera();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
