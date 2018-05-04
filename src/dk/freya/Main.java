package dk.freya;

import dk.freya.api.GraphQLService;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port( 8080 );

        path("/graphql", () -> {
            try {
                GraphQLService.setupRoutes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
