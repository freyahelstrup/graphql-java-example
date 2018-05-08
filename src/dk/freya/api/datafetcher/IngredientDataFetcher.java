package dk.freya.api.datafetcher;

import dk.freya.dao.IngredientDAO;
import dk.freya.dto.IngredientDTO;
import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.ArrayList;
import java.util.List;

public class IngredientDataFetcher implements DataFetcher {
    @Override
    public Object get(DataFetchingEnvironment data) {
        IngredientDAO ingredientDAO = IngredientDAO.getInstance();

        try {
            List<IngredientDTO> ingredientList;
            if (data.containsArgument("id")) {
                ingredientList = new ArrayList<>();
                ingredientList.add(ingredientDAO.getIngredientFromId(data.getArgument("id")));
            } else if (data.containsArgument("name")) {
                ingredientList = new ArrayList<>();
                ingredientList.add(ingredientDAO.getIngredientFromName(data.getArgument("name")));
            } else {
                ingredientList = ingredientDAO.getIngredientList();
            }
            return ingredientList;
        } catch (Exception e) {
            throw new GraphQLException(e.getMessage());
        }
    }
}
