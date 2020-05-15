package com.behl.cdm_02;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {R.mipmap.ic_launcher,
            R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher};

    public String[] slide_headings = {"Bem-Vindo ao FeedCare!","Entenda como funciona a plataforma!", "É muito fácil! Vamos nessa!"};
    public String[] slide_descs = {"O FeedCare é uma plataforma simples. Nós conectamos diversas outras pessoas com restrições alimentares à diversas outras pessoas que possuem as mesmas restrições que você!" +
            " Assim, criamos a melhor comunidade de todas!!!",
            "O FeedCare é dividido em 3 seções básicas:\n -Receitas e restaurantes.\n -Rede.\n  -Notificacões. ",
            "Agora só falta você entrar. Vamos lá!"};

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_inicial_screen, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView slideHeading = (TextView) view.findViewById(R.id.head);
        TextView slideDEscription = (TextView) view.findViewById(R.id.descs);

        slideDEscription.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);

        imageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDEscription.setText(slide_descs[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
