package pl.kalisz.ak.pup.projectrentcar30681;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CarSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "RentCar";
    private static final Integer DB_VERSION = 1;

    CarSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private static void addCar(SQLiteDatabase db, String name, String color, String engine, Integer year, Integer costForDay) {
        ContentValues obiektValues = new ContentValues();
        obiektValues.put("Name", name);
        obiektValues.put("Color", color);
        obiektValues.put("Engine", engine);
        obiektValues.put("Year", year);
        obiektValues.put("CostForDay", costForDay);

        db.insert("Car", null, obiektValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE  TABLE CAR (_id INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Color TEXT, Engine TEXT, Year INTEGER, CostForDay INTEGER);");
            addCar(db, "Fiat Seicento", "Czerwony", "1.0 benzyna", 2002, 100);
            addCar(db, "Opel Insignia", "Czarny", "2.0 disel", 2020, 200);
            addCar(db, "Toyota Yaris", "Biały", "1.0 benzyna", 2012, 120);
            addCar(db, "Kia Rio", "Biały", "1.4 benzyna", 2012, 150);
            addCar(db, "Fiat Seicento", "Czerwony", "1.2 benzyna", 2004, 120);

        }
    }
}
