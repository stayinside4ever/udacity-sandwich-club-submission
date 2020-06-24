package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();
        JSONObject jsonObject = new JSONObject(json);
        JSONObject sandwichName = jsonObject.getJSONObject("name");

        sandwich.setMainName(sandwichName.getString("mainName"));
        JSONArray akaJsonArray = sandwichName.getJSONArray("alsoKnownAs");
        List<String> akaList = new ArrayList<>();

        for (int i = 0; i < akaJsonArray.length(); i++) {
            akaList.add(akaJsonArray.getString(i));
        }

        sandwich.setAlsoKnownAs(akaList);
        sandwich.setPlaceOfOrigin(jsonObject.getString("placeOfOrigin"));
        sandwich.setDescription(jsonObject.getString("description"));
        sandwich.setImage(jsonObject.getString("image"));

        List<String> ingredientsList = new ArrayList<>();

        JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");

        for (int i = 0; i < ingredientsJsonArray.length(); i++) {
            ingredientsList.add(ingredientsJsonArray.getString(i));
        }

        sandwich.setIngredients(ingredientsList);

        return sandwich;
    }
}
