package edu.aulas.jena.exemplos;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 * Claudio Martins
 */
public class ListPackagesInRdfServlet {

    private static final DateFormat df = new SimpleDateFormat("yyyy-MMdd'T'HH:mm:ss");
    // @EJB
    private TourPackageDAO tourPackageDAO;
    private HttpServletResponse resp;

    // protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
    protected void doGet() throws IOException {
        // resp.setContentType("text/xml");
        List<TourPackage> packs = tourPackageDAO.retrieveAll();
        Model model = ModelFactory.createDefaultModel();
        String myNS = "http://localhost:8080/CDITravel/data/TourPackage/";
        String grNS = "http://purl.org/goodrelations/v1#";
        model.setNsPrefix("gr", grNS);
        Resource grOffering = ResourceFactory.createResource(grNS + "Offering");
        Resource grPriceSpecification = ResourceFactory.createResource(grNS
                + "PriceSpecification");
        Property gravailabilityStarts = ResourceFactory.createProperty(grNS
                + "availabilityStarts");
        Property gravailabilityEnds = ResourceFactory.createProperty(grNS
                + "availabilityEnds");
        Property grhasPriceSpecification = ResourceFactory.createProperty(grNS
                + "hasPriceSpecification");
        Property grhasCurrencyValue = ResourceFactory.createProperty(grNS
                + "hasCurrencyValue");
        for (TourPackage pack : packs) {
            model.createResource(myNS + pack.getId())
                    .addProperty(RDF.type, grOffering)
                    .addProperty(RDFS.label, pack.getName())
                    .addProperty(RDFS.comment, pack.getDescription())
                    .addLiteral(gravailabilityStarts,
                            ResourceFactory.createTypedLiteral(df.format(pack.getBegin()),
                                    XSDDatatype.XSDdateTime))
                    .addLiteral(gravailabilityEnds,
                            ResourceFactory.createTypedLiteral(df.format(pack.getEnd()),
                                    XSDDatatype.XSDdateTime))
                    .addProperty(grhasPriceSpecification, model.createResource()
                            .addProperty(RDF.type, grPriceSpecification)
                            .addLiteral(grhasCurrencyValue, pack.getPrice().floatValue()));
        }
        try (PrintWriter out = resp.getWriter()) {
            model.write(out, "RDF/XML");
        }
    }
}
