package com.example.carpicker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private CarBrandList cbl;
    RadioGroup rg;
    SQLiteConnection lite;

    private void createRadioButtons(RadioGroup rg, List<String> brandsList) {
        int size = brandsList.size();
        for(int i=0; i<size; i++){
            RadioButton  rb = new RadioButton(this);
            rb.setText(brandsList.get(i));
            rb.setId(i + 100);
            rg.addView(rb);
        }
    }


    public void PickCarOnClick(View v) {
        Spinner dropDown = (Spinner) findViewById(R.id.cars);
        String brand = String.valueOf(dropDown.getSelectedItem());

        List<String> brandsList = cbl.getAllModels(brand);
        rg.removeAllViews();

        if(brandsList.size() == 0){
            Toast.makeText(getApplicationContext(), "No models in this brand!", Toast.LENGTH_SHORT).show();
        } else{
            rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
            createRadioButtons(rg, brandsList);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rb=(RadioButton)findViewById(checkedId);
                    Toast.makeText(getApplicationContext(), "Number of row inserted: "+lite.insert(brand, rb.getText().toString(), "just"), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cbl = new CarBrandList(getAssets());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner dropDown = (Spinner) findViewById(R.id.cars);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cbl.getAllBrands());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(arrayAdapter);

        lite = new SQLiteConnection(this);

        rg = (RadioGroup) findViewById(R.id.car_radio);

    }
}