package com.micro.helpdesk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.micro.helpdesk.api.ChamadoTask;
import com.micro.helpdesk.api.OnEventListener;
import com.micro.helpdesk.model.Chamado;

import java.util.ArrayList;
import java.util.List;

public class ChamadosFechado extends Fragment {

    ListView listChamadosFechados;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View viewFragmente = inflater.inflate(R.layout.chamadosabertos, container, false);


        final FloatingActionButton botaoAtualizar = (FloatingActionButton) viewFragmente.findViewById(R.id.botao01);
        atualizarChamados(viewFragmente);


        botaoAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizarChamados(viewFragmente);

            }
        });
        return viewFragmente;
    }

    private void atualizarChamados(final View viewMetodo) {
        final List<Chamado> lista = new ArrayList<Chamado>();
        ChamadoTask chamadoTask = new ChamadoTask(viewMetodo.getContext(), new OnEventListener<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Chamado[] chamados = gson.fromJson(result, Chamado[].class);

                for (Chamado chamado : chamados) {
                    if (chamado.getStatus() != null) {
                        if (chamado.getStatus().toLowerCase().equals("fechado")) {
                            lista.add(chamado);

                        }


                    }

                }
                ArrayAdapter<Chamado> adapter = new ArrayAdapter<Chamado>(getActivity(), android.R.layout.simple_list_item_1, lista);
                listChamadosFechados = (ListView) viewMetodo.findViewById(R.id.lista);
                listChamadosFechados.setAdapter(adapter);

            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(viewMetodo.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        chamadoTask.execute();
    }
}
