package my.utem.bitp3123.library_service;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint // Marks this class as a SOAP data handler component
public class LibraryEndpoint {
    
    // Matches the target Namespace URI string declared inside your library.xsd file
    private static final String NAMESPACE_URI = "http://utem.my/bitp3123/library_service";

    // Intercepts XML tags named exactly <BookBookRequest>
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "BookBookRequest")
    @ResponsePayload // Tells Spring to convert the returned Java object back into XML text
    public BookBookResponse handleBookBooking(@RequestPayload BookBookRequest request) {
        
        System.out.println("=== [SOAP Request Received] ===");
        System.out.println("Student ID: " + request.getStudentId());
        System.out.println("Target Book ISBN: " + request.getIsbn());

        // Create the response object generated from your XSD rules
        BookBookResponse response = new BookBookResponse();
        
        // Mocking a successful database transactional assignment
        response.setBookingId(777); 
        response.setStatus("CONFIRMED");
        response.setConfirmationMessage("Book with ISBN " + request.getIsbn() + " successfully checked out to Student " + request.getStudentId());

        return response;
    }
}