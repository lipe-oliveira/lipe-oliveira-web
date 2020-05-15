package com.behl.cdm_02;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class activity_InicialScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] boats;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final HerokuService heroku = new HerokuService(this);

        try{
            Sqlite sqlite = new Sqlite(this);
            final String[] a = sqlite.carregaDados();

            heroku.post_authenticate(a[2], a[1], new HerokuService.post_authenticate_interface() {
                @Override
                public void onSucess() {
                    if(heroku.get_response() != null){
                        //Intent intent = new Intent(getApplicationContext(), activity_PrincipalScreen.class);
                        //startActivity(intent);
                    }
                    else{
                        requestWindowFeature(Window.FEATURE_NO_TITLE);
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                        getSupportActionBar().hide();

                        setContentView(R.layout.inicial_screen);

                        Button btnNext = findViewById(R.id.btnAvancar);
                        final Button btnBack = findViewById(R.id.btnVoltar);

                        viewPager = findViewById(R.id.slideViewPager);
                        linearLayout = findViewById(R.id.linear);

                        sliderAdapter = new SliderAdapter(getApplicationContext());
                        viewPager.setAdapter(sliderAdapter);

                        addDots(0);
                        viewPager.addOnPageChangeListener(viewListener);
 
                        btnNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(currentPage == 2){
                                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                                    Intent intentTo_loginScreen = new Intent(getApplicationContext(), activity_LoginScreen.class);
                                    startActivity(intentTo_loginScreen);
                                }
                                viewPager.setCurrentItem(currentPage+1);
                            }
                        });

                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                viewPager.setCurrentItem(currentPage-1);
                            }
                        });
                    }

                }
            });

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();

            setContentView(R.layout.inicial_screen);

            Button btnNext = findViewById(R.id.btnAvancar);
            final Button btnBack = findViewById(R.id.btnVoltar);

            viewPager = findViewById(R.id.slideViewPager);
            linearLayout = findViewById(R.id.linear);

            sliderAdapter = new SliderAdapter(getApplicationContext());
            viewPager.setAdapter(sliderAdapter);

            addDots(0);
            viewPager.addOnPageChangeListener(viewListener);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(currentPage == 2){
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                        Intent intentTo_loginScreen = new Intent(getApplicationContext(), activity_LoginScreen.class);
                        startActivity(intentTo_loginScreen);
                    }
                    viewPager.setCurrentItem(currentPage+1);
                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(currentPage-1);
                }
            });

        }
        catch (Exception e){
            Log.d("ERRROR: ", e.getLocalizedMessage());
        }

    }


    public void addDots(int position){
        boats = new TextView[3];
        linearLayout.removeAllViews();
        for(int i = 0; i < boats.length;i++) {

            boats[i] = new TextView(this);
            boats[i].setText(Html.fromHtml("&#8226;"));
            boats[i].setTextSize(35);
            boats[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            linearLayout.addView(boats[i]);
        }

        if(boats.length > 0){
            boats[position].setTextColor(getResources().getColor(R.color.colorAccent));

        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            currentPage = i;
            Button btnBack = findViewById(R.id.btnVoltar);
            Button btnNext = findViewById(R.id.btnAvancar);

            if(i==0)
            {
                btnNext.setEnabled(true);
                btnBack.setEnabled(false);
                btnBack.setVisibility(View.GONE);

                btnNext.setText("Próxima");
            }
            else if(i==1){
                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setText("Próxima");
            }
            else{

                btnNext.setEnabled(true);
                btnBack.setEnabled(true);
                btnBack.setVisibility(View.VISIBLE);
                btnNext.setText("Avançar");

            }

            addDots(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
