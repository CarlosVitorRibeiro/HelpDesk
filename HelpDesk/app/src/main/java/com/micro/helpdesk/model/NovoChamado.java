package com.micro.helpdesk.model;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import com.micro.helpdesk.R;
import com.micro.helpdesk.TabsActivity;
import com.micro.helpdesk.api.APIService;
import com.micro.helpdesk.api.ApiUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class NovoChamado extends AppCompatActivity {

    private APIService mAPIService;
    private TextView mResponseTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_novo_chamado);

        final EditText escreverMsg = (EditText) findViewById(R.id.edt_mensagem);

        Button botaoEnviarMsg = (Button) findViewById(R.id.btn_enviar_mensagem);


        mAPIService = ApiUtils.getService();

        botaoEnviarMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String mensagem = escreverMsg.getText().toString().trim();

                Chamado chamados = new Chamado();

                chamados.setDecricao(mensagem);


                if (!TextUtils.isEmpty(mensagem)) {

                    novoChamado(chamados, getApplicationContext());

                }



            }
        });
    }


    public void novoChamado(Chamado chamado, final Context context) {



        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date dataEnvio = new Date();
        mAPIService = ApiUtils.getService();


        Map<String, String> jsonParams = new ArrayMap<>();

        //O Array espera receber os campos desejados em String então passamos o nome conforme servidor e qual atributo escolhido .
        //correspondente a ele no modelo de Chamados.
        jsonParams.put("descricao", chamado.getDecricao());
        jsonParams.put("status", Status.FECHADO.toString());
        jsonParams.put("dataAbertura", sdf.format(dataEnvio.getTime())+ "");



        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());

        Call<ResponseBody> response = mAPIService.salvarMensagem(body);

        response.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> rawResponse) {

                try {
                    Toast.makeText(context, "Chamado aberto com sucesso!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NovoChamado.this, TabsActivity.class);
                    startActivity(intent);
                }
                //Caso dê errado ele exibirá o erro
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

                Toast.makeText(context, "A abertura do chamado falhou!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
