package dk.freya;

import dk.freya.api.GraphQLService;

import static spark.Spark.path;
import static spark.Spark.port;

public class Main {
    public static void main(String[] args) {
        port( 8080 );
        path("/graphql", GraphQLService::setupRoutes);
    }
}
