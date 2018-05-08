package dk.freya.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dk.freya.api.datafetcher.CreateIngredientDataFetcher;
import dk.freya.api.datafetcher.DeleteIngredientDataFetcher;
import dk.freya.api.datafetcher.IngredientDataFetcher;
import dk.freya.api.datafetcher.UpdateIngredientDataFetcher;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;
import static spark.Spark.*;

public class GraphQLService {
    private static GraphQL graphQL;
    private static Gson gson;

    public static void setupRoutes() {
        graphQL = initializeGraphQL();
        gson = new GsonBuilder().setPrettyPrinting().create();

        before("", (request, response) -> System.out.println("Received API call on GraphQLService"));

        get("", (request, response) -> {
            ExecutionResult result = graphQL.execute(request.queryParams("query"));
            response.type("application/json");
            return gson.toJson(result.toSpecification());
        });

        post("", (request, response) -> {
            String query = gson.fromJson(request.body(), JsonObject.class).get("query").getAsString();
            ExecutionResult result = graphQL.execute(query);
            response.type("application/json");
            return gson.toJson(result.toSpecification());
        });
    }

    private static GraphQL initializeGraphQL() {
        //Create TypeDefinitionRegistry
        String schema;
        try {
            schema = new String(Files.readAllBytes(Paths.get("resources/schema.graphqls")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        TypeDefinitionRegistry typeDefinitionRegistry = new SchemaParser().parse(schema);

        //Create RuntimeWiring
        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("ingredients", new IngredientDataFetcher()))
                .type("Mutation", builder -> builder.dataFetcher("createIngredient", new CreateIngredientDataFetcher()))
                .type("Mutation", builder -> builder.dataFetcher("updateIngredient", new UpdateIngredientDataFetcher()))
                .type("Mutation", builder -> builder.dataFetcher("deleteIngredient", new DeleteIngredientDataFetcher()))
                .build();

        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
        return GraphQL.newGraphQL(graphQLSchema).build();
    }
}
