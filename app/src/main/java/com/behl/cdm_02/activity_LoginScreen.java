package com.behl.cdm_02;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class activity_LoginScreen extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener, TextWatcher {

    TextView cdm_title;
    EditText edit_username;
    EditText edit_pass;
    LinearLayout linear;
    Button btn_logar;
    Button btn_cadastrar;

    Typeface font;

    int height_screen;
    int width_screen;

    String username;
    String password;

    Boolean text_changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        linear = findViewById(R.id.linear_main);
        cdm_title = findViewById(R.id.text_nomeCDM);
        edit_username = findViewById(R.id.edit_user);
        edit_pass = findViewById(R.id.edit_pass);
        btn_logar = findViewById(R.id.btn_logar);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);

        font = ResourcesCompat.getFont(getApplicationContext(), R.font.pacifico);

        cdm_title.setTypeface(font);

        height_screen = height_width_SCREEN()[1];
        width_screen = height_width_SCREEN()[0];

        setMargins(cdm_title, 0, (int) (height_screen*0.05), 0, (int) (height_screen*0.05));
        setMargins(edit_username, 0, 0, 0, (int) (height_screen * 0.02));
        setMargins(edit_pass, 0, 0,0, (int) (height_screen * 0.02));
        setMargins(btn_logar, 0, (int) (height_screen * 0.04), 0, (int) (height_screen * 0.02));

        btn_logar.setOnClickListener(this);
        btn_cadastrar.setOnClickListener(this);
        edit_username.setOnKeyListener(this);
        edit_pass.setOnKeyListener(this);
        edit_username.addTextChangedListener(this);
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
        if(v.getId() == R.id.btn_logar){
            logar();
        }

        if(v.getId() == R.id.btn_cadastrar){
            Intent intent = new Intent(getApplicationContext(), activity_CadastroScreen_01.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        Log.i("KEYEVENT", "KEY");

        switch (keyCode){
            case KeyEvent.KEYCODE_ENTER:
                if(v.getId() == edit_pass.getId( )){
                    logar();
                }
        }
        return false;
    }

    private void logar(){
        if(validaCampos()){
            final HerokuService herokuService = new HerokuService(this);
            herokuService.post_authenticate(username, password, new HerokuService.post_authenticate_interface() {
                @Override
                public void onSucess() {
                    if(herokuService.get_response() != null){
                        Sqlite sql = new Sqlite(getApplicationContext());
                        sql.insereDado(herokuService.get_response()[0], username, password);

                        Intent intent = new Intent(getApplicationContext(), activity_PrincipalScreen.class);
                        startActivity(intent);
                    }
                }
            });
        }
        else{
            text_changed = true;
        }

    }

    private boolean validaCampos(){
        String edit_user = edit_username.getText().toString();
        String edit_passw = edit_pass.getText().toString();

        if(edit_user.length() < 5 & edit_user.length() > 0){
            edit_username.setText(null);
            edit_username.clearComposingText();
            edit_username.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.atencao_26px, 0, 0, 0);
            edit_username.setHint(" O usuário precisa ter mais de 4 letras!");
            edit_username.setHintTextColor(getResources().getColor(R.color.colorAccent));
            text_changed = true;
        }
        else if (edit_user.length() < 1){
            edit_username.clearComposingText();
            edit_username.setText(null);
            edit_username.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.atencao_26px, 0, 0, 0);
            edit_username.setHint(" O campo está vazio!");
            edit_username.setHintTextColor(getResources().getColor(R.color.colorAccent));
            text_changed = true;
        }

        if (edit_passw.length() < 1){
            edit_username.clearComposingText();
            edit_pass.setText(null);
            edit_pass.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.atencao_26px, 0, 0, 0);
            edit_pass.setHint(" O campo está vazio!");
            edit_pass.setHintTextColor(getResources().getColor(R.color.colorAccent));
            text_changed = true;
        }

        else if (edit_passw.length() > 0 & edit_passw.length() < 5){
            edit_username.clearComposingText();
            edit_pass.setText(null);
            edit_pass.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.atencao_26px, 0, 0, 0);
            edit_pass.setHint(" A senha precisa ter mais de 4 letras!");
            edit_pass.setHintTextColor(getResources().getColor(R.color.colorAccent));
            text_changed = true;
        }

        if (text_changed == true){
            text_changed = false;
            return false;
        }

        username = edit_user;
        password = edit_passw;

        text_changed = false;
        return true;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(text_changed){
            edit_username.clearComposingText();
            edit_username.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            edit_pass.clearComposingText();
            edit_pass.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

        text_changed = false;


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
