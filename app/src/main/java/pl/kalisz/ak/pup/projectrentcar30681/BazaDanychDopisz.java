package pl.kalisz.ak.pup.projectrentcar30681;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BazaDanychDopisz extends AppCompatActivity {
    private EditText name2, color2, engine2, year2, costForDay2;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baza_danych_dopisz);

        try {
            SQLiteOpenHelper carSQLiteOpenHelper = new CarSQLiteOpenHelper(this);
            db = carSQLiteOpenHelper.getWritableDatabase();
            name2 = (EditText) findViewById(R.id.EditTextName2);
            color2 = (EditText) findViewById(R.id.EditTextColor2);
            engine2 = (EditText) findViewById(R.id.EditTextEngine2);
            year2 = (EditText) findViewById(R.id.EditTextYear2);
            costForDay2 = (EditText) findViewById(R.id.EditTextCostForDay2);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
    }

    public void zapiszNoweAuto(View view) {
        ContentValues obiektValues = new ContentValues();
        obiektValues.put("Name", name2.getText().toString());
        obiektValues.put("Color", color2.getText().toString());
        obiektValues.put("Engine", engine2.getText().toString());
        obiektValues.put("Year", year2.getText().toString());
        obiektValues.put("CostForDAy", costForDay2.getText().toString());
        db.insert("CAR", null, obiektValues);
        finish();
    }
}
