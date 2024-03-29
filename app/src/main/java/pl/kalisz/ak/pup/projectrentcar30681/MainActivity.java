package pl.kalisz.ak.pup.projectrentcar30681;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity{
    private ShareActionProvider shareActionProvider;
    private CarSQLiteOpenHelper bazaDanych;
    private CharSequence komunikat;
    private Snackbar snackbar;
    private SQLiteDatabase db;
    private int czas = Snackbar.LENGTH_LONG;
    private Cursor cursor;
    private ListView listViewCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baza_danych);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewCars = (ListView) findViewById(R.id.zapiszNowaPozycje);
        try {
            bazaDanych = new CarSQLiteOpenHelper(this);
            db = bazaDanych.getWritableDatabase();
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Baza danych niedostępna", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listViewCars, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, BazaDanychAktualizacja.class);
                intent.putExtra(BazaDanychAktualizacja.EXTRA_CAR_ID, (int) id);
                startActivity(intent);
            }
        };

        listViewCars.setOnItemClickListener(itemClickListener);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BazaDanychDopisz.class);
                startActivity(intent);
            }
        });

    }
//    @Override
//    public void onClick(View v) {
//        LayoutInflater inflater = getLayoutInflater();
//        View customToastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.root_layout));
//
//        TextView txtMessage = customToastLayout.findViewById(R.id.txt_message);
//        txtMessage.setText("dsfsdf");
//
//        Toast mToast = new Toast(getApplicationContext());
//        mToast.setDuration(Toast.LENGTH_LONG);
//        mToast.setView(customToastLayout);
//        mToast.setGravity(Gravity.BOTTOM, 0, 0);
//        mToast.show();
//
//    }
    @Override
    public  boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                displayAboutMessage("Autorem jest Weronika Graczyk");
                return true;
            case R.id.licence:
                displayAboutMessage("Creative Commons Attribution 4.0 (CC BY 4.0) to rodzaj licencji Creative Commons, która pozwala na swobodne wykorzystanie, modyfikowanie i dzielenie się utworami, pod warunkiem, że jest podana odpowiednia informacja o autorstwie. Oznacza to, że możesz edytować i modyfikować kod na własne potrzeby, pod warunkiem, że podasz źródło (autorstwo) pierwotnego kodu. Jest to licencja, która zapewnia duży zakres swobody w korzystaniu z utworów, zachowując jednocześnie pewne zasady etyki i szacunku dla twórców.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void displayAboutMessage(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View customToastLayout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.root_layout));

        TextView txtMessage = customToastLayout.findViewById(R.id.txt_message);
        txtMessage.setText(message);

        Toast mToast = new Toast(getApplicationContext());
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(customToastLayout);
        mToast.setGravity(Gravity.BOTTOM, 0, 0);
        mToast.show();
    }
    @Override
    public void onResume() {
        super.onResume();
        cursor = db.query("CAR", new String[]{"_id", "Name", "Color", "Engine", "Year", "CostForDay"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
                    cursor,
                    new String[]{"Name", "Engine", "CostForDay", "Color", "Year"},
                    new int[]{android.R.id.text1, android.R.id.text2},
                    0);
            listViewCars.setAdapter(listAdapter);
        }
    }
}