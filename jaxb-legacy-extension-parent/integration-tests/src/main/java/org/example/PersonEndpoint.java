package org.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import java.io.ByteArrayOutputStream;

@Path("person")
public class PersonEndpoint {

    @GET
    public String get() throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(Person.class);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        context.createMarshaller().marshal(new Person("Damien", 41), baos);
        return baos.toString();
    }
}
