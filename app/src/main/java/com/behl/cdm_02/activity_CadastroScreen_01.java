package com.behl.cdm_02;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.snackbar.Snackbar;

public class activity_CadastroScreen_01 extends AppCompatActivity implements View.OnClickListener {

    TextView cadastro_tilte;
    Button btn_avancar;
    EditText edit_email;
    EditText edit_senha;
    EditText edit_user;

    Typeface font;

    int height_screen;
    int width_screen;

    String username;
    String password;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        cadastro_tilte = findViewById(R.id.cadastro_title);
        btn_avancar = findViewById(R.id.cadastro_btn_avancar);
        edit_user = findViewById(R.id.cadastro_usuario);
        edit_email = findViewById(R.id.cadastro_email);
        edit_senha = findViewById(R.id.cadastro_senha);

        font = ResourcesCompat.getFont(getApplicationContext(), R.font.pacifico);
        cadastro_tilte.setTypeface(font);

        height_screen = height_width_SCREEN()[1];
        width_screen = height_width_SCREEN()[0];

        setMargins(cadastro_tilte, 0, (int) (height_screen*0.10), 0, (int) (height_screen*0.05));

        btn_avancar.setOnClickListener(this);

    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    private int[] height_width_SCREEN(){

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        int[] height_width = {width, height};

        return height_width;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cadastro_btn_avancar:

                if(verifica_campos()){
                    try{
                        HerokuService heroku = new HerokuService(this);
                        if (heroku.post_register(username, email, password) != null){
                            Intent intent = new Intent(getApplicationContext(), activity_PrincipalScreen.class);
                            startActivity(intent);
                        }
                    }
                    catch (Exception e){
                        Snackbar.make(edit_email, e.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                    }
                    Intent intent = new Intent(getApplicationContext(), activity_PrincipalScreen.class);
                    startActivity(intent);
                }
                else{
                    Snackbar.make(edit_email, "Pelo menos um campo está vazio!", Snackbar.LENGTH_LONG).show();
                }

        }
    }

    private boolean verifica_campos(){
        email = edit_email.getText().toString();
        password = edit_senha.getText().toString();
        username = edit_user.getText().toString();

        if(password.length() < 1 && email.length() < 1 && username.length() < 1){
            return false;
        }
        return true;
    }
}
