package org.example.deployment;

import io.quarkus.test.QuarkusUnitTest;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.example.Person;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JaxbLegacyExtensionTest {
    @RegisterExtension
    static final QuarkusUnitTest config = new QuarkusUnitTest()
            .setArchiveProducer(() ->
                    ShrinkWrap.create(JavaArchive.class));

    @Test
    void should() throws JAXBException {
        final JAXBContext context = JAXBContext.newInstance(Person.class);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        context.createMarshaller().marshal(new Person("Damien", 41), baos);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><person><age>41</age><name>Damien</name></person>",
                baos.toString());
    }
}
