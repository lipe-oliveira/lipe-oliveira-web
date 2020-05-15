package com.behl.cdm_02.ui.Rede;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.behl.cdm_02.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RedeFragment extends Fragment implements View.OnClickListener {

    private RedelViewModel dashboardViewModel;

    FloatingActionButton fab;
    FloatingActionButton fab_add;
    FloatingActionButton fab_compartilhar;

    ArrayList<String> array_users = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    ListView lv;

    Boolean isFABOpen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(RedelViewModel.class);
        View root = inflater.inflate(R.layout.rede_fragment_screen, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isFABOpen = false;

        fab = getView().findViewById(R.id.rede_fab);
        fab_add = getView().findViewById(R.id.rede_fab_add);
        fab_compartilhar = getView().findViewById(R.id.rede_fab_compartilhar);

        lv = getView().findViewById(R.id.rede_lv);

        fab.setOnClickListener(this);
        fab_add.setOnClickListener(this);
        fab_compartilhar.setOnClickListener(this);


        /*
        array_users.add("Felipeme");
        array_users.add("FEFEFE");

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_checked, array_users);

        listView.setAdapter(arrayAdapter);
         */


    }

    private void showFABMenu(){
        isFABOpen=true;
        fab_add.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        fab_compartilhar.animate().translationY(-getResources().getDimension(R.dimen.standard_125));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab_add.animate().translationY(0);
        fab_compartilhar.animate().translationY(0);
    }

    public void onBackPressed() {
        if(!isFABOpen){
            return;
        }else{
            closeFABMenu();
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rede_fab){

            if(!isFABOpen){
                showFABMenu();
            }else{
                closeFABMenu();
            }

        }
        if(v.getId() == R.id.rede_fab_add){
            users_return();
            //arrayAdapter = new ArrayAdapter<String>(getContext() ,android.R.layout.simple_list_item_1, array_users);
            //lv.setAdapter(arrayAdapter);

        }

    }

    public void users_return(){

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("_User");
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("updatedAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    Log.i("REDE_USERS_RETURN", "NONQUERYERRO");
                    if (objects.size() > 0){
                        Log.i("REDE_USERS_RETURN", "OBJECTSSIZE > 0");
                        if (!array_users.isEmpty()){
                            array_users.clear();
                        }
                        for (ParseObject object : objects){
                            array_users.add(String.valueOf(object.get("username")));
                        }
                        Log.i("REDE_FAB_ADD_ACTION", array_users.toString());
                        Intent intent_add = new Intent(getContext(), activity_AddScreen.class);
                        intent_add.putStringArrayListExtra("users", array_users);
                        startActivity(intent_add);
                    }
                    else {
                        Log.i("REDE_USERS_RETURN", String.valueOf(objects.size()));
                    }
                }
                else{
                    Log.i("REDE_USERS_RETURN", e.getLocalizedMessage());
                }
            }
        });

    }
}