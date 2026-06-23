package my.utem.bitp3123.library_service;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs // Turns on the Spring SOAP Web Services engine
@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true); // Automatically fixes URL paths inside the WSDL contract
        return new ServletRegistrationBean<>(servlet, "/ws/*"); // Route for all SOAP traffic
    }

    @Bean(name = "library") // Exposes your contract online at: http://localhost:8082/ws/library.wsdl
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema librarySchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("LibraryPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://utem.my/bitp3123/library_service");
        wsdl11Definition.setSchema(librarySchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema librarySchema() {
        return new SimpleXsdSchema(new ClassPathResource("library.xsd")); // Links directly to your XSD resource file
    }
}