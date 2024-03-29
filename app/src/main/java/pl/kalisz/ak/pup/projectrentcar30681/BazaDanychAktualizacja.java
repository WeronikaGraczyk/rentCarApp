package pl.kalisz.ak.pup.projectrentcar30681;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BazaDanychAktualizacja extends AppCompatActivity {
    public static final String EXTRA_CAR_ID = " carId";
    private SQLiteDatabase db;
    private Cursor cursor;
    private int carId;
    private EditText nazwa1, color1, engine1, year1, costForDay1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baza_danych_aktualizacja);

        carId = (Integer) getIntent().getExtras().get(EXTRA_CAR_ID);
        try {
            SQLiteOpenHelper carSQLiteOpenHelper = new CarSQLiteOpenHelper(this);
            db = carSQLiteOpenHelper.getWritableDatabase();
            cursor = db.query("Car", new String[]{"_id", "Name", "Color", "Engine", "Year", "CostForDay"}, "_id = ?", new String[]{Integer.toString(carId)}, null, null, null);
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                String color = cursor.getString(2);
                String engine = cursor.getString(3);
                String year = cursor.getString(4);
                String CostForDay = cursor.getString(5);
                nazwa1 = (EditText) findViewById(R.id.editTextName);
                nazwa1.setText(name);
                color1 = (EditText) findViewById(R.id.editTextColor);
                color1.setText(color);
                engine1 = (EditText) findViewById(R.id.editTextEngine);
                engine1.setText(engine);
                year1 = (EditText) findViewById(R.id.editTextYear);
                year1.setText(year);
                costForDay1 = (EditText) findViewById(R.id.editTextCostForDay);
                costForDay1.setText(CostForDay);
            }
            cursor.close();
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }

    public void onClickZapisz(View view) {
        ContentValues obiektValues = new ContentValues();
        obiektValues.put("Name", nazwa1.getText().toString());
        obiektValues.put("Color", color1.getText().toString());
        obiektValues.put("Engine", engine1.getText().toString());
        obiektValues.put("Year", year1.getText().toString());
        obiektValues.put("CostForDay", costForDay1.getText().toString());
        db.update("Car", obiektValues, "_id = ?", new String[]{Integer.toString(carId)});
        db.close();
        finish();
    }

    public void onClickUsun(View view) {
        db.delete("Car", "_id = ?", new String[]{Integer.toString(carId)});
        db.close();
        finish();
    }
}
