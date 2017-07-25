package edu.aulas.jena.simples;
  
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

public class JenaHello {
    public static void main (String[] args) {
       System.out.println("** EXEMPLO JENA - Hello **");
       Model m = ModelFactory.createDefaultModel();
       String NS =  "http://example.com/test/";
       
       Resource r = m.createResource(NS + "r");
       Property p = m.createProperty(NS + "p");
       
       r.addProperty(p,"hello world",XSDDatatype.XSDstring);
       
       m.write(System.out, "N-TRIPLE"); // "RDF/XML-ABBREV"); //, "Turtle");
       
    }
}
