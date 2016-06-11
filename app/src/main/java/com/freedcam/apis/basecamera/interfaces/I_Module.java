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

package com.freedcam.apis.basecamera.interfaces;

import com.freedcam.apis.basecamera.modules.AbstractModuleHandler;

/**
 * Created by troop on 15.08.2014.
 */
public interface I_Module
{
    void SetCaptureStateChangedListner(AbstractModuleHandler.CaptureStateChanged captureStateChangedListner);
    /**
     * holds the modulename
     * @return the name of the module
     */
    String ModuleName();

    /**
     * Let the Module start its work
     */
    boolean DoWork();

    /**
     * The workstate of the module
     * @return true if it has work to process
     */
    boolean IsWorking();

    /**
     * Short name of the module
     * @return
     */
    String LongName();
    /**
     * Full name of the module
     * @return
     */
    String ShortName();


    /**
     * geht thrown when the module gets loaded
     */
    void InitModule();

    /**
     * get thrown when the module get unloaded and a new gets loaded
     */
    void DestroyModule();

}
