package com.micro.helpdesk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.micro.helpdesk.model.NovoChamado;

public class TabsActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChamadosAberto(),"Chamados Aberto");
        adapter.addFragment(new ChamadosFechado(),"Chamados Fechado");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        Button btnNovaMensagem = (Button) findViewById(R.id.btn_nova_mensagem);

        btnNovaMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabsActivity.this, NovoChamado.class);
                startActivity(intent);
            }
        });
    }

}
