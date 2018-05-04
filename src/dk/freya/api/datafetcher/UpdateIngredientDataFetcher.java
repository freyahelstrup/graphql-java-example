package dk.freya.api.datafetcher;

import dk.freya.dao.IngredientDAO;
import dk.freya.dto.IngredientDTO;
import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class UpdateIngredientDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment data) {
        IngredientDAO ingredientDAO = IngredientDAO.instance;

        try {
            int id = data.getArgument("id");
            IngredientDTO ingredient = new IngredientDTO(id, data.getArgument("name"));
            return ingredientDAO.setIngredient(ingredient);
        } catch (Exception e) {
            throw new GraphQLException(e.getMessage());
        }
    }
}
