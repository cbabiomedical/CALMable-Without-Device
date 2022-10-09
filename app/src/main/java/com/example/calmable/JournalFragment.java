//package com.example.calmable;
//
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.io.ObjectInputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//public class JournalFragment extends Fragment {
//
//    static ArrayList<String> listOfNotes = new ArrayList<>();
//    static ArrayAdapter listViewNoteAdapter;
//
//    FirebaseUser mUser;
//
//    ListView listViewJournal;
//
//    List<Object> listOfJournalDate = new ArrayList<>();
//    List<Object> listOfJournalBody = new ArrayList<>();
//    HashMap<String, Object> journalNote = new HashMap<>();
//
//    public JournalFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_journal, container, false);
//
//
//        listViewJournal = (ListView) view.findViewById(R.id.listViewJournal);
//
//        // permanent save notes
//        //SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.calmable", Context.MODE_PRIVATE);
//
////        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("lostOfNotes", null);
////
////        UpdateDB();
////
////        if (set == null) {
////            //listOfNotes.add("test 001");
////        } else {
////            listOfNotes = new ArrayList(set);
////        }
//
//        listViewNoteAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                listOfNotes
//        );
//
//
//        listViewJournal.setAdapter(listViewNoteAdapter);
//
//
//        listViewJournal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getActivity(), NoteEditorActivity.class);
//                intent.putExtra("noteId", i);
//
//                Log.i("TAG", "btn create success");
//
//                startActivity(intent);
//            }
//        });
//
//
//        // long press delete notes
//        listViewJournal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final int itemDelte = i;
//
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("Are you sure?")
//                        .setMessage("Do you want to delete this note?")
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                listOfNotes.remove(itemDelte);
//                                listViewNoteAdapter.notifyDataSetChanged();
//
//                                Toast.makeText(getActivity(), "Delete Successfully!", Toast.LENGTH_SHORT).show();
//
//                                // permanent save notes
//                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.calmable", Context.MODE_PRIVATE);
//                                HashSet<String> set = new HashSet(JournalFragment.listOfNotes);
//                                sharedPreferences.edit().putStringSet("listOfNotes", set).apply();
//                            }
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
//
//                return true;
//
//            }
//
//        });
//
//        return view;
//
//    }
//
//
//    //  Enable option menu in this fragment
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//
//        MenuInflater menuInflater = getActivity().getMenuInflater();
//        menuInflater.inflate(R.menu.add_note_menu, menu);
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//
//    // handle the menu item clicks
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if (item.getItemId() == R.id.add_note) {
//            Intent intent = new Intent(getActivity(), NoteEditorActivity.class);
//
//            Toast.makeText(getActivity(), "Add New Note", Toast.LENGTH_SHORT).show();
//
//            startActivity(intent);
//
//            return true;
//
//        }
//        return false;
//    }
//
//
////    public void UpdateDB() {
////
////
////
////        String edJournalBody = editTextNote.getText().toString().trim();
////
////        listOfJournalBody.add(edJournalBody);
////
////
////        mUser = FirebaseAuth.getInstance().getCurrentUser();
////
////        journalNote.put("journalNote", listOfJournalBody);
////        FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).updateChildren(journalNote)
////                .addOnCompleteListener(new OnCompleteListener<Void>() {
////                    @Override
////                    public void onComplete(@NonNull Task<Void> task) {
////
////                        Toast.makeText(NoteEditorActivity.this, "Successful", Toast.LENGTH_SHORT).show();
////                        //Intent intent=new Intent(UserPreferences.this,ProfileActivity.class);
////                        //startActivity(intent);
////                    }
////                });
////        Log.d("User", mUser.getUid());
////
////    }
//}
