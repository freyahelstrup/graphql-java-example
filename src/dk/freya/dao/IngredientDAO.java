package dk.freya.dao;

import dk.freya.dto.IngredientDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientDAO {
    private static IngredientDAO instance;
    private Map<Integer, IngredientDTO> ingredientMap;
    private int nextId;

    private IngredientDAO() {
        ingredientMap = new HashMap<>();
        addIngredient(new IngredientDTO(0, "kartoffel"));
        addIngredient(new IngredientDTO(0, "fisk"));
        addIngredient(new IngredientDTO(0, "mel"));
    }

    public static IngredientDAO getInstance() {
        if (instance == null)
            instance = new IngredientDAO();
        return instance;
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
        ingredient.id = nextId++;
        ingredientMap.put(ingredient.id, ingredient);
        return getIngredientFromId(ingredient.id);
    }

    public boolean deleteIngredient(int id) {
        return ingredientMap.remove(id) != null;
    }

}
