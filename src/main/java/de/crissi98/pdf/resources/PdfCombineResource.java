package de.crissi98.pdf.resources;

import de.crissi98.pdf.file.FileTypeException;
import de.crissi98.pdf.scan.PdfCombineService;
import de.crissi98.pdf.scan.model.CombineScannerDocument;
import de.crissi98.pdf.scan.model.PdfSizeException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/pdf/scan")
public class PdfCombineResource {

    @Inject
    PdfCombineService pdfCombineService;

    @POST
    @Path("/combine")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response combine(CombineScannerDocument inputDoc) throws IOException, FileTypeException, PdfSizeException {
        pdfCombineService.combine(inputDoc);
        return Response.noContent()
                .build();
    }

    @POST
    @Path("/append")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response append(CombineScannerDocument inputDoc) throws IOException, FileTypeException {
        pdfCombineService.append(inputDoc);
        return Response.noContent()
                .build();
    }
}