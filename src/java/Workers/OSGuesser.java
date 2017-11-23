 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Workers;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.nmap4j.Nmap4j;
import org.nmap4j.core.flags.Flag;
import org.nmap4j.parser.NMapRunHandlerImpl;

/**
 *
 * @author Fluffy
 */
public class OSGuesser extends Thread implements Runnable{
    
    private String ip;

    public OSGuesser(String ip) {
        this.ip = ip;
    }

    
    
    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
            Nmap4j nmap4j = new Nmap4j( "C:/Program Files (x86)/Nmap" ) ;
            NMapRunHandlerImpl h=new NMapRunHandlerImpl();

        //nmap4j.includeHosts(" -T4 -A -Pn -oX - 192.168.1.7");
        nmap4j.includeHosts(ip) ;
        nmap4j.addFlags(Flag.INSANE_TIMING.toString()+" "+Flag.A_FLAG.toString()+" "+Flag.TREAT_HOSTS_AS_ONLINE.toString());

                System.out.println("Start Execution");
         try{
          nmap4j.execute() ; 
        }catch(Exception e){
         System.out.println("error execute");

        }
         System.out.println("Execution Completed ");    
        try {
                    JSONObject xmlJSONObj = XML.toJSONObject(nmap4j.getOutput());
                    String json = xmlJSONObj.toString(4);
                    System.out.println(json);
                } catch (JSONException je) {
                    System.out.println(je.toString());
                }
        
        
        //use json to get data
        
        
    }
    
    
    
}
