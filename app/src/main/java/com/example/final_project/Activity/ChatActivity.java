package com.example.final_project.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.final_project.R;
import com.example.final_project.adapter.MessagsAdapter;
import com.example.final_project.model.Messages;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private String messageReceiverId,  messageSenderId;
    private TextView userName;
    private CircleImageView userImage;
    private Toolbar chatToolbar;
    private ImageButton sendMessageButton, sendFilesButton;
    private EditText messageInputText;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private ProgressDialog loadingBar;

    private final List<Messages> messagesList=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessagsAdapter messagsAdapter;
    private RecyclerView userMessageList;

    private String saveCurrentTime = null, saveCurrentDate;
    private String checker="", myUrl="";
    private StorageTask uploadTask;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth=FirebaseAuth.getInstance();
        messageSenderId=mAuth.getCurrentUser().getUid();
        RootRef= FirebaseDatabase.getInstance().getReference();

        Intent intent=getIntent();
        messageReceiverId=intent.getStringExtra("ID");



        intializeControllers();

        loadDoctorDetails();



        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        sendFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[]=new CharSequence[]{
                        "Images",

                };
                AlertDialog.Builder builder=new AlertDialog.Builder(ChatActivity.this);
                builder.setTitle("Select the File");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            checker ="image";

                            Intent intent=new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent,"Select Image"),438);
                        }


                    }
                });
                builder.show();
            }
        });


    }

    private void intializeControllers() {

        chatToolbar=(Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(chatToolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_chat_bar,null);
        actionBar.setCustomView(actionBarView);

        userImage=(CircleImageView) findViewById(R.id.custom_profile_image);
        userName=(TextView) findViewById(R.id.custom_profile_name);

        sendMessageButton=(ImageButton) findViewById(R.id.send_message_btn);
        sendFilesButton=(ImageButton) findViewById(R.id.send_files_btn);
        messageInputText=(EditText) findViewById(R.id.input_message);

        messagsAdapter =new MessagsAdapter(messagesList);
        userMessageList=(RecyclerView) findViewById(R.id.privet_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessageList.setLayoutManager(linearLayoutManager);
        userMessageList.setAdapter(messagsAdapter);

        loadingBar=new ProgressDialog(this);


        Calendar calendar= Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
        saveCurrentTime=currentTime.format(calendar.getTime());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loadingBar.setTitle("Sending File");
        loadingBar.setMessage("Please Wait, we are sending that file...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        if (requestCode == 438 && resultCode == RESULT_OK && data != null && data.getData() != null) {



            fileUri=data.getData();


             if (checker.equals("image")){

                StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Image Files");

                final String messageSenderRef="Messages/" + messageSenderId + "/" + messageReceiverId;
                final String messageReceiverRef="Messages/" + messageReceiverId + "/" + messageSenderId;

                DatabaseReference userMessageKeyRef=RootRef.child("Messages")
                        .child(messageSenderId).child(messageReceiverId).push();

                final String messagePushId=userMessageKeyRef.getKey();

                StorageReference filePath=storageReference.child(messagePushId + "." + "jpg");
                uploadTask=filePath.putFile(fileUri);

                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {

                        if(!task.isSuccessful()){
                            throw task.getException();
                        }

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){

                            Uri downloadUrl = task.getResult();
                            myUrl=downloadUrl.toString();

                            Map messageTextBody = new HashMap();
                            messageTextBody.put("message", myUrl);
                            messageTextBody.put("name", fileUri.getLastPathSegment());
                            messageTextBody.put("type", checker);
                            messageTextBody.put("from", messageSenderId);
                            messageTextBody.put("to", messageReceiverId);
                            messageTextBody.put("messageId", messagePushId);
                            messageTextBody.put("time", saveCurrentTime);
                            messageTextBody.put("date", saveCurrentDate);

                            Map messageBodyDetails = new HashMap();
                            messageBodyDetails.put(messageSenderRef + "/" + messagePushId, messageTextBody);
                            messageBodyDetails.put(messageReceiverRef + "/" + messagePushId, messageTextBody);

                            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()){
                                        loadingBar.dismiss();
                                        Toast.makeText(ChatActivity.this, "message sent successfully...", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                    messageInputText.setText("");
                                }
                            });
                        }
                    }
                });
            }
            else{
                loadingBar.dismiss();
                Toast.makeText(this, "Nothing Selected, Error.", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();

        RootRef.child("Messages").child(messageSenderId).child(messageReceiverId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Messages messages=snapshot.getValue(Messages.class);

                        messagesList.add(messages);
                        messagsAdapter.notifyDataSetChanged();

                        userMessageList.smoothScrollToPosition(userMessageList.getAdapter().getItemCount());
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void sendMessage(){
        String messageText=messageInputText.getText().toString();

        if (TextUtils.isEmpty(messageText)){
            Toast.makeText(this, "first write your message", Toast.LENGTH_SHORT).show();
        }
        else {
            String messageSenderRef="Messages/" + messageSenderId + "/" + messageReceiverId;
            String messageReceiverRef="Messages/" + messageReceiverId + "/" + messageSenderId;

            DatabaseReference userMessageKeyRef=RootRef.child("Messages")
                    .child(messageSenderId).child(messageReceiverId).push();

            String messagePushId=userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderId);
            messageTextBody.put("to", messageReceiverId);
            messageTextBody.put("messageId", messagePushId);
            messageTextBody.put("time", saveCurrentTime);
            messageTextBody.put("date", saveCurrentDate);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushId, messageTextBody);
            messageBodyDetails.put(messageReceiverRef + "/" + messagePushId, messageTextBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ChatActivity.this, "message sent successfully...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ChatActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    messageInputText.setText("");
                }
            });
        }
    }

    private void loadDoctorDetails() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(messageReceiverId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String doctorName = ""+snapshot.child("name").getValue();
                        String address = ""+snapshot.child("address").getValue();
                        String phone = ""+snapshot.child("phone").getValue();
                        String aboutDoctor = ""+snapshot.child("about").getValue();
                        String profileImage = ""+snapshot.child("profileImage").getValue();


                        userName.setText(doctorName);
                        Glide.with(ChatActivity.this)
                                .load(profileImage)
                                .placeholder(R.drawable.profile_image)
                                .into(userImage);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}