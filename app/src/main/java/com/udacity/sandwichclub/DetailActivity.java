package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mAlsoKnownAsTextView;
    TextView mOriginTextView;
    TextView mDescriptionTextView;
    TextView mIngredientsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        mOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        mIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;

        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        StringBuilder builder = new StringBuilder();
        List<String> arrayList = sandwich.getAlsoKnownAs();

        if (arrayList.isEmpty()) {
            builder.append("-");
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                builder.append(arrayList.get(i));
                if (i < arrayList.size() - 1) {
                    builder.append(", ");
                }
            }
        }

        mAlsoKnownAsTextView.setText(builder.toString());

        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());

        builder = new StringBuilder();
        arrayList = sandwich.getIngredients();

        if (arrayList.isEmpty()) {
            builder.append("-");
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                builder.append(i == 0 ? arrayList.get(i) : arrayList.get(i).toLowerCase());
                if (i < arrayList.size() - 1) {
                    builder.append(", ");
                }
            }
        }

        mIngredientsTextView.setText(builder.toString());
    }
}
