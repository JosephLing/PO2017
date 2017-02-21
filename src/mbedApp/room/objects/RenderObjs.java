package mbedApp.room.objects;

import mbedApp.devices.Device;
import mbedApp.room.Canvas;

/**
 * Created by Joe on 21/02/2017.
 */
public enum RenderObjs {

    LIGHT   (Device.LIGHT, (Canvas canvas)->{});

    private String name;
    private InterfaceRender interfaceRender;

    RenderObjs(String name, InterfaceRender interfaceRender){
        this.name = name;
        this.interfaceRender = interfaceRender;
    }

    public InterfaceRender getInterfaceRender(){
        return interfaceRender;
    }

    public String getName(){
        return name;
    }


}
