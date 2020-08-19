package de.crissi98.pdf.intrastructure;

import de.crissi98.pdf.file.FileTypeException;
import de.crissi98.pdf.scan.model.PdfSizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationExceptionMapper.class);

    @Override
    public Response toResponse(Throwable e) {
        Response response;
        LOG.error(e.getMessage(), e);
        if (e instanceof IOException) {
            response = Response.status(Response.Status.NOT_FOUND.getStatusCode(),
                    "The file you selected doesn't exist").build();
        } else if (e instanceof FileTypeException) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        } else if (e instanceof PdfSizeException) {
            response = Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            response = Response.serverError().build();
        }
        return response;
    }
}
