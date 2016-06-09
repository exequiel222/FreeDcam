/*
 *
 *     Copyright (C) 2015 Ingo Fuchs
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * /
 */

package com.freedcam.apis.camera1.parameters.modes;

import android.hardware.Camera.Parameters;

import com.freedcam.apis.basecamera.modules.I_ModuleEvent;
import com.freedcam.apis.basecamera.parameters.modes.AbstractModeParameter;
import com.freedcam.apis.basecamera.parameters.modes.AbstractModeParameter.I_ModeParameterEvent;
import com.freedcam.apis.camera1.CameraHolder;
import com.freedcam.utils.Logger;

import java.util.ArrayList;

/**
 * Created by troop on 17.08.2014.
 * That class handel basic parameter logic and
 * expect a key_value String like "antibanding" and a values String "antibanding-values"
 *
 */
public class BaseModeParameter extends AbstractModeParameter implements I_ModuleEvent, I_ModeParameterEvent
{
    /*
    The Key to set/get a value from the parameters
     */
    protected String key_value;
    /*
    The Key to get the supported values from the parameters
     */
    protected String key_values;
    boolean isSupported = false;
    boolean isVisible = true;
    protected Parameters  parameters;
    protected CameraHolder cameraHolder;
    private static String TAG = BaseModeParameter.class.getSimpleName();

    /*
    The stored StringValues from the parameter
     */
    protected String[] valuesArray;

    public BaseModeParameter(Parameters  parameters, CameraHolder cameraHolder)
    {
        this.parameters = parameters;
        this.cameraHolder = cameraHolder;
    }

    /***
     *
     * @param parameters
     * Hold the Camera Parameters
     * @param cameraHolder
     * Hold the camera object
     * @param key_value
     * The String to get/set the key_value from the parameters
     * @param key_values
     * the string to get the values avail/supported for @param key_value
     */
    public BaseModeParameter(Parameters  parameters, CameraHolder cameraHolder, String key_value, String key_values)
    {
        this(parameters,cameraHolder);
        this.key_value = key_value;
        this.key_values = key_values;
        if (parameters != null && !key_value.isEmpty() && parameters.get(key_value) != null && parameters.get(key_values) != null)
        {
            String tmp = parameters.get(key_value);
            if (!tmp.isEmpty())
            {
                isSupported = true;
                valuesArray = parameters.get(key_values).split(",");
                ArrayList<String> tmpl  = new ArrayList<>();
                for (String s : valuesArray)
                {
                    if (!tmpl.contains(s))
                        tmpl.add(s);
                }
                valuesArray = new String[tmpl.size()];
                tmpl.toArray(valuesArray);
            }
        }
        else
            isSupported =false;
        isVisible = isSupported;
        Logger.d(TAG, key_value + ":" +isSupported);
    }

    @Override
    public boolean IsSupported()
    {
        return isSupported;
    }

    @Override
    public boolean IsVisible() {
        return isVisible;
    }

    @Override
    public void SetValue(String valueToSet,  boolean setToCam)
    {
        if (valueToSet == null)
            return;
        parameters.set(key_value, valueToSet);
        Logger.d(TAG, "set " + key_value + " to " + valueToSet);
        BackgroundValueHasChanged(valueToSet);
        if (setToCam) {
            try {
                cameraHolder.SetCameraParameters(parameters);

            } catch (Exception ex) {
                Logger.exception(ex);
            }
        }
    }


    @Override
    public String GetValue()
    {
        return parameters.get(key_value);
    }
    @Override
    public String[] GetValues()
    {
        return valuesArray;
    }

    @Override
    public void ModuleChanged(String module) {

    }

    @Override
    public void onValueChanged(String val) {

    }

    @Override
    public void onIsSupportedChanged(boolean isSupported) {

    }

    @Override
    public void onIsSetSupportedChanged(boolean isSupported) {

    }

    @Override
    public void onValuesChanged(String[] values) {

    }

    @Override
    public void onVisibilityChanged(boolean visible) {

    }
}