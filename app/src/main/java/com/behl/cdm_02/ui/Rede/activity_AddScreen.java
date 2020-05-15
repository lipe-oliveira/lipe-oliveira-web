
package com.behl.cdm_02.ui.Rede;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.behl.cdm_02.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class activity_AddScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_screen);

        final ListView lv_add = findViewById(R.id.add_lv);
        lv_add.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        ArrayList<String> users_array = new ArrayList<String>();
        for (String username : getIntent().getStringArrayListExtra("users")){
            users_array.add(username);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, users_array);
        lv_add.setAdapter(adapter);


    }
}
