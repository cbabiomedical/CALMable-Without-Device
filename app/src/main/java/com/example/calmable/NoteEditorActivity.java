//package com.example.calmable;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Locale;
//
//public class NoteEditorActivity extends AppCompatActivity {
//
//    int noteId;
//
//    private TextView tvDate;
//    private EditText editTextNote;
//
//    ListView listViewJournal;
//
//    FirebaseUser mUser;
//
//    List<Object> listOfJournalDate = new ArrayList<>();
//    List<Object> listOfJournalBody = new ArrayList<>();
//    HashMap<String, Object> journalNote = new HashMap<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_note_editor);
//
//        editTextNote = (EditText) findViewById(R.id.editTextTextMultiLine);
//        tvDate = (TextView) findViewById(R.id.tvDate);
//
//
//        Date realDate = Calendar.getInstance().getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//        String date = sdf.format(realDate);
//        tvDate.setText(date);
//
//        Intent intent = getIntent();
//        noteId = intent.getIntExtra("noteId", -1);
//
//        // check valid note id
//        if (noteId != -1) {
//            editTextNote.setText(Journal.listOfNotes.get(noteId));
//        } else {
//            Journal.listOfNotes.add("");
//            noteId = Journal.listOfNotes.size() - 1;
//            Journal.listViewNoteAdapter.notifyDataSetChanged();
//
//        }
//
//
//        editTextNote.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                Journal.listOfNotes.set(noteId, String.valueOf(charSequence));
//                Journal.listViewNoteAdapter.notifyDataSetChanged();
//
//                // permanent save notes
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable" , Context.MODE_PRIVATE);
//                HashSet<String> set = new HashSet(Journal.listOfNotes);
//                sharedPreferences.edit().putStringSet("lostOfNotes" , set).apply();
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//    }
//}