package com.example.jose.cicacau;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ListCursosEventos extends ActionBarActivity {

    static ArrayList<String>cursos = new ArrayList<String>();
    static ArrayList<String> titulo = new ArrayList<String>();
    static ArrayList<String>data = new ArrayList<String>();
    static int index;
    String html;
    static String htmlCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_cursos_eventos);
        html = MenuPrincipal.html;
        titulo = cursosTitulo(html);
        data = cursosData(html);
        cursos = carregaCursos(html);
        ListView listview;
        listview =  (ListView) findViewById(R.id.MyListCursosEventos);
        MeuArrayAdapter adapterCodigos = new MeuArrayAdapter(this, titulo, data);
        listview.setAdapter(adapterCodigos);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                index = position;
                htmlCompleto = carregaHtml(null);
                cursoCompleto(null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_cursos_eventos, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public class MeuArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<String> titulo;
        private final ArrayList<String> data;

        public MeuArrayAdapter(Context context, ArrayList<String> titulo, ArrayList<String> data) {
            super(context, R.layout.listviewcursoseventos, titulo);
            this.context = context;
            this.titulo = titulo;
            this.data = data;

        }

        @Override
        public View getView(int posicao, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listviewcursoseventos, parent, false);
            TextView textTitulo = (TextView) rowView.findViewById(R.id.titulo);
            textTitulo.setText(titulo.get(posicao).toString().substring(32));
            TextView textData = (TextView) rowView.findViewById(R.id.data);
            textData.setText(data.get(posicao).toString().substring(14));
            return rowView;
        }
    }

    public static ArrayList<String> cursosTitulo(String html){
        ArrayList<String> listTitulo = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("cicacau_curso-evento.php?id=", 0);;
            while (inicio != (-1)) {
                fim = html.indexOf("</a>", inicio);
                linha = html.substring(inicio, fim);
                listTitulo.add(linha);
                inicio = html.indexOf("cicacau_curso-evento.php?id=", fim);
            }

            for (int i = 0; i < listTitulo.size(); i++) {
                String temp = tratarSubLinha(listTitulo.get(i));
                listTitulo.set(i, temp);
            }
        }

        return listTitulo;
    }

    public static ArrayList<String> cursosData(String html){
        ArrayList<String> listData = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("titulooutros><h4>", 0);
            while (inicio != (-1)) {
                fim = html.indexOf("<h3", inicio);
                linha = html.substring(inicio, fim);
                listData.add(linha);
                inicio = html.indexOf("titulooutros><h4>", fim);
            }

            for (int i = 0; i < listData.size(); i++) {
                String temp = tratarSubLinha(listData.get(i));
                listData.set(i, temp);
            }
        }

        return listData;
    }

    public static ArrayList<String> carregaCursos(String html){
        ArrayList<String> listCursos = new ArrayList<String>();
        String linha = null;
        int inicio, fim;

        if (html != null) {
            inicio = html.indexOf("cicacau_curso-evento.php?id=", 0);
            while (inicio != (-1)) {
                fim = html.indexOf('"' + ">", inicio);
                linha = html.substring(inicio, fim);
                listCursos.add(linha);
                inicio = html.indexOf("cicacau_curso-evento.php?id=", fim);
            }

            for (int i = 0; i < listCursos.size(); i++) {
                String temp = tratarSubLinha(listCursos.get(i));
                listCursos.set(i, temp);
            }
        }

        return listCursos;
    }

    private static String tratarSubLinha(String string) {
        // TODO Auto-generated method stub
        String nova = Html.fromHtml(string).toString();
        return nova;
    }

    public void cursoCompleto(View v){
        Intent intent = new Intent(this, CursosEventosCompleto.class);
        startActivity(intent);
    }

    public String carregaHtml (View v){

        InputStream content = null;
        BufferedReader leitor;
        StringBuilder str;
        String line = null;
        String html = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute( new HttpGet("http://nbcgib.uesc.br/cicacau/" + cursos.get(index).toString()));
            content = response.getEntity().getContent();
            leitor = new BufferedReader(new InputStreamReader(content));
            str = new StringBuilder();

            while((line = leitor.readLine()) != null) {
                str.append(line);
            }

            content.close();
            html = str.toString();



        } catch (Exception e) {
            Toast.makeText(this, "Verifique a conexão com a internet", Toast.LENGTH_LONG).show();
            Log.e("[GET REQUEST]", "Network exception", e);
        }
        return html;
    }
}
