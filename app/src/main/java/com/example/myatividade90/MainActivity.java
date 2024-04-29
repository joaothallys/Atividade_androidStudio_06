package com.example.myatividade90;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editNome, editTel, editEmail;
    private Button btn_limpar, btn_salvar, btn_excluir;
    private ListView listaPessoas;
    private ArrayAdapter<Pessoa> adapter;
    private List<Pessoa> listaPessoasDados;
    private PessoaDAO pessoaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = findViewById(R.id.edit_nome);
        editTel = findViewById(R.id.edit_tel);
        editEmail = findViewById(R.id.edit_email);
        btn_limpar = findViewById(R.id.btn_limpar);
        btn_salvar = findViewById(R.id.btn_salvar);
        btn_excluir = findViewById(R.id.btn_excluir);
        listaPessoas = findViewById(R.id.lista_pessoas);

        pessoaDAO = new PessoaDAO(this);
        listaPessoasDados = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPessoasDados);
        listaPessoas.setAdapter(adapter);

        listarPessoas();

        listaPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pessoa pessoaSelecionada = listaPessoasDados.get(position);
                // Aqui você pode abrir uma nova Activity para editar os detalhes da pessoa selecionada
                Toast.makeText(MainActivity.this, "Selecionado: " + pessoaSelecionada.getNome(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPessoa();
            }
        });

        btn_excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirPessoa();
            }
        });
    }

    private void listarPessoas() {
        listaPessoasDados.clear();
        listaPessoasDados.addAll(pessoaDAO.getAllPessoas());
        adapter.notifyDataSetChanged();
    }

    private void limparCampos() {
        editNome.setText("");
        editTel.setText("");
        editEmail.setText("");
    }

    private void salvarPessoa() {
        String nome = editNome.getText().toString().trim();
        String tel = editTel.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        if (!nome.isEmpty() && !tel.isEmpty() && !email.isEmpty()) {
            Pessoa pessoa = new Pessoa(nome, tel, email);
            pessoaDAO.addPessoa(pessoa);
            limparCampos();
            listarPessoas();
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void excluirPessoa() {
        int posicaoSelecionada = listaPessoas.getCheckedItemPosition();
        if (posicaoSelecionada != ListView.INVALID_POSITION) {
            Pessoa pessoaSelecionada = listaPessoasDados.get(posicaoSelecionada);
            pessoaDAO.deletePessoa(pessoaSelecionada);
            Toast.makeText(this, "Pessoa excluída: " + pessoaSelecionada.getNome(), Toast.LENGTH_SHORT).show();
            listarPessoas(); // Atualizar a lista após a exclusão
        } else {
            Toast.makeText(this, "Nenhuma pessoa selecionada", Toast.LENGTH_SHORT).show();
        }
    }
}
