package com.freedcam.apis.camera2.camera.modules;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.freedcam.apis.camera2.camera.CameraHolderApi2;
import com.freedcam.apis.basecamera.camera.modules.AbstractModule;
import com.freedcam.apis.basecamera.camera.modules.ModuleEventHandler;
import com.freedcam.apis.basecamera.camera.parameters.AbstractParameterHandler;
import com.freedcam.utils.AppSettingsManager;


/**
 * Created by troop on 12.12.2014.
 */
public abstract class AbstractModuleApi2 extends AbstractModule implements I_PreviewWrapper
{
    protected CameraHolderApi2 cameraHolder;
    protected AbstractParameterHandler ParameterHandler;

    protected boolean isWorking = false;

    protected Point displaySize;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public AbstractModuleApi2(CameraHolderApi2 cameraHandler, ModuleEventHandler eventHandler, Context context, AppSettingsManager appSettingsManager)
    {
        super(cameraHandler,eventHandler,context,appSettingsManager);
        this.cameraHolder = cameraHandler;
        this.ParameterHandler = cameraHolder.GetParameterHandler();
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        displaySize = new Point();
        display.getRealSize(displaySize);
    }

    @Override
    public String ModuleName() {
        return name;
    }

    @Override
    public boolean DoWork() {
        return true;
    }

    @Override
    public boolean IsWorking() {
        return isWorking;
    }

    @Override
    public void LoadNeededParameters()
    {
        cameraHolder.ModulePreview = this;
    }

    @Override
    public void UnloadNeededParameters() {

    }
}
