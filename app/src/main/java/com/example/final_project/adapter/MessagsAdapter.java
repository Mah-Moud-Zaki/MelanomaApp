package com.example.final_project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.final_project.Activity.ChatActivity;
import com.example.final_project.Activity.ChatsDoctorActivity;
import com.example.final_project.Activity.ImageViewerActivity;
import com.example.final_project.R;
import com.example.final_project.model.Messages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagsAdapter extends RecyclerView.Adapter<MessagsAdapter.MessageViewHolder> {

    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private Context context;
    View view;

    public MessagsAdapter(List<Messages> userMessagesList){
        this.userMessagesList=userMessagesList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{

        public TextView senderMessageText, receiverMessageText;
        public CircleImageView receiverProfileImage;
        public ImageView messageSenderPicture, messageReceiverPicture;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMessageText=(TextView) itemView.findViewById(R.id.sender_message_text);
            receiverMessageText=(TextView) itemView.findViewById(R.id.receiver_message_text);
            receiverProfileImage=(CircleImageView) itemView.findViewById(R.id.message_profile_image);
            messageReceiverPicture=itemView.findViewById(R.id.message_receiver_image_view);
            messageSenderPicture=itemView.findViewById(R.id.message_sender_image_view);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_messages_layout,parent,false);

        mAuth=FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String messagesSenderId=mAuth.getCurrentUser().getUid();
        Messages messages=userMessagesList.get(position);

        String fromUserId=messages.getFrom();
        String fromMessageType=messages.getType();

        userRef= FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("profileImage")){
                    String receiverImage=snapshot.child("profileImage").getValue().toString();

                    Glide.with(view)
                            .load(receiverImage)
                            .placeholder(R.drawable.profile_image)
                            .into(holder.receiverProfileImage);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.receiverMessageText.setVisibility(View.GONE);
        holder.receiverProfileImage.setVisibility(View.GONE);
        holder.senderMessageText.setVisibility(View.GONE);
        holder.messageSenderPicture.setVisibility(View.GONE);
        holder.messageReceiverPicture.setVisibility(View.GONE);

        if (fromMessageType.equals("text")){


            if (fromUserId.equals(messagesSenderId)){
                holder.senderMessageText.setVisibility(View.VISIBLE);
                holder.senderMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
                holder.senderMessageText.setTextColor(Color.BLACK);
                holder.senderMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
            }
            else{

                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.receiverMessageText.setVisibility(View.VISIBLE);

                holder.receiverMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
                holder.receiverMessageText.setTextColor(Color.BLACK);
                holder.receiverMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
            }
        }
        else if (fromMessageType.equals("image")){

            if (fromUserId.equals(messagesSenderId)){
                holder.messageSenderPicture.setVisibility(View.VISIBLE);

                Picasso.get().load(messages.getMessage()).into(holder.messageSenderPicture);
            }
            else {
                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.messageReceiverPicture.setVisibility(View.VISIBLE);

                Picasso.get().load(messages.getMessage()).into(holder.messageReceiverPicture);
            }

        }

        if (fromUserId.equals(messagesSenderId)){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     if (userMessagesList.get(position).getType().equals("text") ){
                        CharSequence options[]=new CharSequence[]{
                                "Delete for me",
                                "Cancel",
                                "Delete for Everyone"
                        };

                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Delete Message?");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0){
                                    deleteSentMessage(position,holder);
                                    Intent intent=new Intent(holder.itemView.getContext(), ChatsDoctorActivity.class);
                                    holder.itemView.getContext().startActivity(intent);

                                }

                                else if (which==2){
                                    deleteMessageForEveryone(position,holder);
                                    Intent intent=new Intent(holder.itemView.getContext(),ChatsDoctorActivity.class);
                                    holder.itemView.getContext().startActivity(intent);
                                }

                            }
                        });
                        builder.show();
                    }
                    else if (userMessagesList.get(position).getType().equals("image") ){
                        CharSequence options[]=new CharSequence[]{
                                "Delete for me",
                                "View this Image",
                                "Cancel",
                                "Delete for Everyone"
                        };

                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Delete Message?");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0){
                                    deleteSentMessage(position,holder);
                                    Intent intent=new Intent(holder.itemView.getContext(),ChatsDoctorActivity.class);
                                    holder.itemView.getContext().startActivity(intent);
                                }
                                else if (which==1){
                                    Intent intent=new Intent(holder.itemView.getContext(), ImageViewerActivity.class);
                                    intent.putExtra("url", userMessagesList.get(position).getMessage());
                                    holder.itemView.getContext().startActivity(intent);
                                }

                                else if (which==3){
                                    deleteMessageForEveryone(position,holder);
                                    Intent intent=new Intent(holder.itemView.getContext(),ChatsDoctorActivity.class);
                                    holder.itemView.getContext().startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                    }
                }
            });
        }
        else{
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (userMessagesList.get(position).getType().equals("text") ){
                        CharSequence options[]=new CharSequence[]{
                                "Delete for me",
                                "Cancel",
                        };

                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Delete Message?");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0){
                                    deleteReceiveMessage(position,holder);
                                    Intent intent=new Intent(holder.itemView.getContext(),ChatsDoctorActivity.class);
                                    holder.itemView.getContext().startActivity(intent);
                                }

                            }
                        });
                        builder.show();
                    }
                    else if (userMessagesList.get(position).getType().equals("image") ){
                        CharSequence options[]=new CharSequence[]{
                                "Delete for me",
                                "View this Image",
                                "Cancel",

                        };

                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                        builder.setTitle("Delete Message?");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which==0){
                                    deleteReceiveMessage(position,holder);
                                    Intent intent=new Intent(holder.itemView.getContext(),ChatsDoctorActivity.class);
                                    holder.itemView.getContext().startActivity(intent);
                                }
                                else if (which==1){
                                    Intent intent=new Intent(holder.itemView.getContext(),ImageViewerActivity.class);
                                    intent.putExtra("url", userMessagesList.get(position).getMessage());
                                    holder.itemView.getContext().startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }

    private void deleteSentMessage(final int position, final MessageViewHolder holder){

        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
        rootRef.child("Messages")
                .child(userMessagesList.get(position).getFrom())
                .child(userMessagesList.get(position).getTo())
                .child(userMessagesList.get(position).getMessageId())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(holder.itemView.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteReceiveMessage(final int position, final MessageViewHolder holder){

        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
        rootRef.child("Messages")
                .child(userMessagesList.get(position).getTo())
                .child(userMessagesList.get(position).getFrom())
                .child(userMessagesList.get(position).getMessageId())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(holder.itemView.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void deleteMessageForEveryone(final int position, final MessageViewHolder holder){

        DatabaseReference rootRef=FirebaseDatabase.getInstance().getReference();
        rootRef.child("Messages")
                .child(userMessagesList.get(position).getTo())
                .child(userMessagesList.get(position).getFrom())
                .child(userMessagesList.get(position).getMessageId())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            rootRef.child("Messages")
                                    .child(userMessagesList.get(position).getFrom())
                                    .child(userMessagesList.get(position).getTo())
                                    .child(userMessagesList.get(position).getMessageId())
                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(holder.itemView.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                        }
                        else{
                            Toast.makeText(holder.itemView.getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

