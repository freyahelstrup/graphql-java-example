package dk.freya.api.datafetcher;

import dk.freya.dao.IngredientDAO;
import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class DeleteIngredientDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment data) {
        IngredientDAO ingredientDAO = IngredientDAO.instance;

        try {
            return ingredientDAO.deleteIngredient(data.getArgument("id"));
        } catch (Exception e) {
            throw new GraphQLException(e.getMessage());
        }
    }
}
