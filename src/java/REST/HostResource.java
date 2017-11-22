/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import com.google.gson.Gson;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import util.HostDetails;
import util.Network;

/**
 * REST Web Service
 *
 * @author Fluffy
 */
@Path("Host")
public class HostResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of HostResource
     */
    public HostResource() {
    }

    /**
     * Retrieves representation of an instance of REST.HostResource
     * @return an instance of java.lang.String
     */
    
    //calculate factor based on current threshold and reply
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
       // Network.getHost("192.168.1.4").portHandler(10001, HostDetails.PORT_OPEN);
       return  new Gson().toJson(Network.getHost((String) Network.getAllIP().toArray()[0] ));
    }

    /**
     * PUT method for updating or creating an instance of HostResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
