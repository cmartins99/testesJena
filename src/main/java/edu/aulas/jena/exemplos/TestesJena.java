package edu.aulas.jena.exemplos;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;

public class TestesJena {

    public static void main(String[] args) {

    }

    public void suggestDescription() {
        String name = "John"; // pack.getName();
        if (name != null && name.length() > 3) {
            String query = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
                    + "PREFIX dbpprop: <http://dbpedia.org/property/> "
                    + "SELECT ?desc "
                    + "WHERE { "
                    + "?x a dbpedia-owl:Place ; "
                    + "dbpprop:name ?name ; "
                    + "dbpedia-owl:abstract ?desc . "
                    + "FILTER (lcase(str(?name)) = \"" + name.toLowerCase() + "\") "
                    + "FILTER (langMatches(lang(?desc), \"EN\")) "
                    + "}";
            QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
            ResultSet results = queryExecution.execSelect();
            if (results.hasNext()) {
                QuerySolution querySolution = results.next();
                Literal literal = querySolution.getLiteral("desc");
               // pack.setDescription("" + literal.getValue());
                System.out.println("" + literal.getValue());
            }
        }
    }
}
