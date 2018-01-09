package util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fluffy
 */
public class ThresholdFactor {
    
    private Float uptime=1F;
    private Float OS=1F;
    private Float port=1F;
    private Float route=1F;
    
    
    public static final double BREAKPOINT=100.0;

    public Float getUptime() {
        return uptime;
    }

    public void setUptime(Float uptime) {
        this.uptime = uptime;
    }

    public Float getOS() {
        return OS;
    }

    public void setOS(Float OS) {
        this.OS = OS;
    }

    public Float getPort() {
        return port;
    }

    public void setPort(Float port) {
        this.port = port;
    }

    public Float getRoute() {
        return route;
    }

    public void setRoute(Float route) {
        this.route = route;
    }

    public void decPort(){
    if(port>=2)
        port-=1F;
    }
    
    public void incPort(){
    
        port*=2F;
    }
    
    public void decRoute(){
    if(route>=2)
        route-=1F;
    }
    
    public void incRoute(){
    
        route*=2F;
    }
    
    public void decOS(){
    if(OS>=2)
        OS-=1F;
    }
    
    public void incOS(){
    
        OS*=2F;
    }
    
    public void decUptime(){
    if(uptime>=2)
        uptime-=1F;
    }
    
    public void incUptime(){
    
        uptime*=2F;
    }
}
