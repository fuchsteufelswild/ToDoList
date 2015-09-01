package com.android.project.todolist.menues;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;


import com.android.project.todolist.communicator.Communicator;
import com.android.project.todolist.dialogs.DialogAddListObject;
import com.android.project.todolist.domain.ListObject;
import com.android.project.todolist.adapter.ListObjectAdapter;
import com.android.project.todolist.R;


import java.util.ArrayList;


public class MainMenu extends ActionBarActivity implements Communicator, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private GridView main_menu_gridView;
    private ArrayList<ListObject> listObjects;
    private ListObjectAdapter listObjectAdapter;
    private ListObject listObject;


    private static final int REQUEST_CODE_OPEN_SUBMENU = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGUI();
        connectData();
    }

    private void connectData() {
        listObjects = new ArrayList<>();
        listObjectAdapter = new ListObjectAdapter(this, listObjects);
        main_menu_gridView.setAdapter(listObjectAdapter);
    }

    private void setupGUI() {
        setContentView(R.layout.activity_main_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        main_menu_gridView = (GridView) findViewById(R.id.gridView);
        main_menu_gridView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_settings:
                return true;

            case R.id.action_addListObject:
                addNewListObject();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addNewListObject() {
        DialogAddListObject dialog = new DialogAddListObject();
        dialog.show(getFragmentManager(), "Add List Object Dialog");

    }


    @Override
    public void getInputTextFromDialog(String inputText) {
        ListObject newListObject = new ListObject(1, inputText, 0, 1);

        listObjects.add(newListObject);
        listObjectAdapter.notifyDataSetChanged();
    }


    @Override
    public void getDate(DatePicker view, int year, int month, int day) {

    }



    //ClickListener für die einzelnen ListObjects
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listObject = (ListObject) parent.getItemAtPosition(position);
        String listObjectTitle = listObject.getTitle();
        Intent intent = new Intent(MainMenu.this, SubMenu.class);
        intent.putExtra("name", listObjectTitle);
        startActivityForResult(intent, REQUEST_CODE_OPEN_SUBMENU);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Aktualisiert das Textview für die Anzahl der ListItems in der Liste
        if(requestCode == REQUEST_CODE_OPEN_SUBMENU) {
            listObject.setNumOfListItems(data.getExtras().getInt("NumOfListItems"));
            listObjectAdapter.notifyDataSetChanged();
        }
    }



}
