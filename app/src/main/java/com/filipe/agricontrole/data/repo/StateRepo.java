package com.filipe.agricontrole.data.repo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.filipe.agricontrole.data.DatabaseManager;
import com.filipe.agricontrole.data.model.State;

import java.util.ArrayList;
import java.util.List;

public class StateRepo {
    private final String TAG = StateRepo.class.getSimpleName().toString();

    //private Farm farm;
    private State state;

    public StateRepo(){
        state = new State();
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS " + State.TABLE  + " ("
                + State.KEY_StateId  + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                + State.KEY_Name + " TEXT NOT NULL, "
                + State.KEY_UF + " TEXT NOT NULL);";
    }

    public static String insertStates(){
        return "INSERT INTO " + State.TABLE + " values "
                +"(1, 'Acre', 'AC'), "
                +"(2, 'Alagoas', 'AL'), "
                +"(3, 'Amazonas', 'AM'), "
                +"(4, 'Amapá', 'AP'), "
                +"(5, 'Bahia', 'BA'), "
                +"(6, 'Ceará', 'CE'), "
                +"(7, 'Distrito Federal', 'DF'), "
                +"(8, 'Espírito Santo', 'ES'), "
                +"(9, 'Goiás', 'GO'), "
                +"(10, 'Maranhão', 'MA'), "
                +"(11, 'Minas Gerais', 'MG'), "
                +"(12, 'Mato Grosso do Sul', 'MS'), "
                +"(13, 'Mato Grosso', 'MT'), "
                +"(14, 'Pará', 'PA'), "
                +"(15, 'Paraíba', 'PB'), "
                +"(16, 'Pernambuco', 'PE'), "
                +"(17, 'Piauí', 'PI'), "
                +"(18, 'Paraná', 'PR'), "
                +"(19, 'Rio de Janeiro', 'RJ'), "
                +"(20, 'Rio Grande do Norte', 'RN'), "
                +"(21, 'Rondônia', 'RO'), "
                +"(22, 'Roraima', 'RR'), "
                +"(23, 'Rio Grande do Sul', 'RS'), "
                +"(24, 'Santa Catarina', 'SC'), "
                +"(25, 'Sergipe', 'SE'), "
                +"(26, 'São Paulo', 'SP'), "
                +"(27, 'Tocantins', 'TO');";
    }

    public List<State> findAll(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(State.TABLE, new String[] {}, null, null, null, null, null);

        List<State> stateList = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                State state = new State();
                stateList.add(state);

                state.setId(c.getInt(0));
                state.setName(c.getString(1));
                state.setUf(c.getString(2));
            }while (c.moveToNext());
        }
        DatabaseManager.getInstance().closeDatabase();
        return stateList;
    }

    public List<State> findAllNames(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor c = db.query(State.TABLE, new String[] {}, null, null, null, null, null);

        List<State> stateList = new ArrayList<State>();
        if(c.moveToFirst()){
            do{
                State state = new State();

                state.setId(c.getInt(0));
                state.setName(c.getString(1));
                state.setUf(c.getString(2));
                stateList.add(state);
            }while (c.moveToNext());
        }
        return stateList;
    }


}
