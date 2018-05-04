package dk.freya.api.datafetcher;

import dk.freya.dao.IngredientDAO;
import dk.freya.dto.IngredientDTO;
import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class CreateIngredientDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment data) {
        IngredientDAO ingredientDAO = IngredientDAO.instance;
        try {
            IngredientDTO ingredient = new IngredientDTO(
                    0,
                    data.getArgument("name")
            );
            return ingredientDAO.addIngredient(ingredient);
        } catch (Exception e) {
            throw new GraphQLException(e.getMessage());
        }
    }
}
