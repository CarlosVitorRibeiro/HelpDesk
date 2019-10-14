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

public class ChamadosAberto extends Fragment {

    ListView listChamadosAbertos;


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
        //Lista de chamados com o nome de lista.
        final List<Chamado> lista = new ArrayList<Chamado>();

        ChamadoTask chamadoTask = new ChamadoTask(viewMetodo.getContext(), new OnEventListener<String>() {
            @Override
            //Em caso de sucesso ao se comunicar com o servidor.
            public void onSuccess(String result) {

                Gson gson = new Gson();
                Chamado[] chamados = gson.fromJson(result, Chamado[].class);

                for (Chamado chamado : chamados) {
                    //Verifico se são nulos, em caso de não serem realizo a operação abaixo.
                    if (chamado.getStatus() != null) {
                        //Pego o status por meio do get, faço ficar minusculo e verifico se é aberto.
                        if (chamado.getStatus().toLowerCase().equals("aberto")) {
                            //Caso seja eu coloco ele na nova lista que criei, lista.
                            lista.add(chamado);

                        }


                    }

                }
                //Crio um ArrayAdapter para dizer qual é o formatao que alista que que ir.
                ArrayAdapter<Chamado> adapter = new ArrayAdapter<Chamado>(getActivity(), android.R.layout.simple_list_item_1, lista);
                //Mostro na tela, onde o id lista é do xml dos chamados abertos
                listChamadosAbertos = (ListView) viewMetodo.findViewById(R.id.lista);
                //seto o adapter que criei a cima passando aquela lista onde somente estão os chamados abertos.
                listChamadosAbertos.setAdapter(adapter);

            }

            @Override
            public void onFailure(Exception e) {
                //Caso dê erro ele irá monstrar a mensagem "ERROR" + as mensagens de erro
                Toast.makeText(viewMetodo.getContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        chamadoTask.execute();
    }
}

