package com.example.ej6;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView display; // Pantalla de la calculadora
    private StringBuilder currentInput = new StringBuilder(); // Para almacenar los valores ingresados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajuste de insets como en tu código original
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vinculando el TextView que actúa como pantalla
        display = findViewById(R.id.display);

        // Vincular los botones y definir sus acciones
        setUpButtons();
    }

    private void setUpButtons() {
        // Vincular botones numéricos y el símbolo "+"
        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.buttonPlus
        };

        // Crear el listener para los botones de números y el botón "+"
        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                currentInput.append(b.getText().toString()); // Añadir el número o símbolo al input actual
                display.setText(currentInput.toString()); // Mostrar el input en la pantalla
            }
        };

        // Asignar el listener a los botones
        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(buttonListener);
        }

        // Vincular el botón "=" y su acción
        Button equalsButton = findViewById(R.id.buttonEquals);
        equalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = calculate(currentInput.toString()); // Llamar a la función recursiva calculate
                display.setText(result); // Mostrar el resultado
                currentInput.setLength(0); // Limpiar el input actual
            }
        });

        // Vincular el botón "C" para limpiar la pantalla
        Button clearButton = findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput.setLength(0);
                display.setText("");
            }
        });
    }

    // Función recursiva para calcular una expresión simple de suma
    private String calculate(String input) {
        if (!input.contains("+")) {
            try {
                return String.valueOf(Integer.parseInt(input)); // Convertir el número y devolverlo como string
            } catch (NumberFormatException e) {
                return "-1";
            }
        } else {

            String[] parts = input.split("\\+", 2);
            return String.valueOf(Integer.parseInt(calculate(parts[0])) + Integer.parseInt(calculate(parts[1])));
        }
    }
}
