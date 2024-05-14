package com.example.ac2_programacao_dispositivos_moveis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ac2_programacao_dispositivos_moveis.expenses.fragment.ExpensesFragment;

// Nome: Vinicius do Prado Vieira
// RA: 155118


public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpensesFragment expensesFragment = new ExpensesFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, expensesFragment)
                .commit();
        }
}