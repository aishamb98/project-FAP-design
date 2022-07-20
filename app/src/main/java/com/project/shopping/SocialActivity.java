package com.project.shopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageException;
import com.project.shopping.Adapter.PostAdapter;
import com.project.shopping.Model.Post;
import com.project.shopping.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.shopping.Prevalent.Prevalent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class SocialActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private Toolbar mainToolbar;
    private FirebaseFirestore firestore;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private PostAdapter adapter;
    private List<Post> list;
    private Query query;
    private ListenerRegistration listenerRegistration;
    private List<Users> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SocialActivity.this));
        list = new ArrayList<>();
        usersList = new ArrayList<>();
        adapter = new PostAdapter(SocialActivity.this);

        mRecyclerView.setAdapter(adapter);

        fab = findViewById(R.id.floatingActionButton);
//        setSupportActionBar(mainToolbar);
//        getSupportActionBar().setTitle("Fap");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SocialActivity.this, AddPostActivity.class));
            }
        });
//        if (firebaseAuth.getCurrentUser() != null) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Boolean isBottom = !mRecyclerView.canScrollVertically(1);
                    if (isBottom)
                        Toast.makeText(SocialActivity.this, "Reached Bottom", Toast.LENGTH_SHORT).show();
                }
            });

//
//        }else{
//            System.out.println(">>>>>>>>>>>>> NULL USER");
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        query = firestore.collection("Posts");
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                list.clear();
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    System.out.println(">>>>>>>> LOOP");
                    //5wF0FSynbM0NumEvarP3
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        try{
                            System.out.println(">>>>>>>>>>POST " + doc.getDocument());
                            Post post = new Post();
                            post.setUser(doc.getDocument().get("user").toString());
                            post.setCaption(doc.getDocument().get("caption").toString());
                            post.setImage(doc.getDocument().get("image").toString());
                            Date date = new Date(Long.parseLong(doc.getDocument().get("time").toString()));
                            post.setTime(date);
                            post.setUserName(doc.getDocument().get("userName").toString());
                            post.setUserImage(String.valueOf(doc.getDocument().get("userImage")));
                            post.PostId = doc.getDocument().getId();
                            list.add(post);
                        }catch (Exception e){
                            if(doc.getDocument().getId().equals("5wF0FSynbM0NumEvarP3")){
                                System.out.println(">>>>>>>>>>>CATCH  "+ e.getMessage());
                            }else{
                                System.out.println(">>>>>>>>>>> catch  "+ e.getMessage());
                            }
                        }
//                            System.out.println(">>>>>>>> ADDED");
//                            String postId = doc.getDocument().getId();
//                            final Post post = doc.getDocument().toObject(Post.class).withId(postId);
//                            String postUserId = doc.getDocument().getString("user");
//                            if(post.getUser() != null) {
//                                firestore.collection("Users").document(postUserId).get()
//                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                if (task.isSuccessful()) {
//                                                    System.out.println(">>>>>>>>>>> "+ task.getResult().toString());
//                                                    Users users = task.getResult().toObject(Users.class);
//                                                    usersList.add(users);
//                                                    list.add(post);
//                                                    adapter.addList(list, usersList);
//                                                } else {
//                                                    Toast.makeText(SocialActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
                    }

//                        } else {
//                            System.out.println(">>>>>>>>>>> NO TYPE");
//                            adapter.notifyDataSetChanged();
//                        }
                }
                System.out.println(">>>>>>>>>> List>> "+ list.size());
                System.out.println(">>>>>>>>>> current user>> "+ Prevalent.currentOnlineUser.getPhone());
                System.out.println(">>>>>>>>>> current user2>> "+ Paper.book().read(Prevalent.UserPhoneKey));
                Collections.sort(list, new Comparator<Post>() {
                    @Override
                    public int compare(Post post, Post t1) {
                        return t1.getTime().compareTo(post.getTime());
                    }
                });
                adapter.addList(list);
            }
        });
    }
}

