package dk.freya.dao;

import dk.freya.dto.IngredientDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientDAO {
    private static Map<Integer, IngredientDTO> ingredientMap;

    public IngredientDAO() {
        ingredientMap = new HashMap<>();
        ingredientMap.put(1, new IngredientDTO(1, "kartoffel"));
        ingredientMap.put(2, new IngredientDTO(2, "majs"));
        ingredientMap.put(3, new IngredientDTO(3, "mel"));
    }

    public List<IngredientDTO> getIngredientList() {
        return new ArrayList<>(ingredientMap.values());
    }

    public IngredientDTO getIngredientFromId(int id) {
        return ingredientMap.get(id);
    }

    public IngredientDTO getIngredientFromName(String name) {
        for (IngredientDTO i : ingredientMap.values()) {
            if (i.name.equals(name))
                return i;
        }
        return null;
    }

    public IngredientDTO setIngredient(IngredientDTO ingredient) {
        getIngredientFromId(ingredient.id).name = ingredient.name;
        return getIngredientFromId(ingredient.id);
    }

    public IngredientDTO addIngredient(IngredientDTO ingredient) {
        ingredientMap.put(ingredient.id, ingredient);
        return getIngredientFromId(ingredient.id);
    }

    public boolean deleteIngredient(int id) {
        return ingredientMap.remove(id) != null;
    }

}
