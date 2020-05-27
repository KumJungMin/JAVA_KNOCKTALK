package com.cookandroid.listmembers;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.listmembers.models.Member;
import com.cookandroid.listmembers.models.Message;
import com.cookandroid.listmembers.models.NotificationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private String destinationUid;
    private Button button;
    private EditText editText;
    private RadioGroup reason_rg;
    private RadioButton checkedReason;

    private String uid;
    private String msgRoomUid;
    private Member destinationMember;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        destinationUid = getIntent().getStringExtra("destinationUid");
        button = findViewById(R.id.messageActivity_btn);
        editText = findViewById(R.id.messageActivity_et);
        reason_rg = findViewById(R.id.message2_reason_rg);





        recyclerView = findViewById(R.id.messageActivity_recycler);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.users.put(uid, true);
                message.users.put(destinationUid, true);

                if(msgRoomUid == null){
                    button.setEnabled(false);
//                    데이터가 입력되었다고 할 때 데이터를 체크하도록
                    FirebaseDatabase.getInstance().getReference().child("msgRooms").push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkMsgRoom();
                        }
                    });

                } else{
                    Message.Comment comment = new Message.Comment();
                    checkedReason = findViewById(reason_rg.getCheckedRadioButtonId());
                    final String reason = checkedReason.getText().toString();
                    comment.uid = uid;
                    comment.messages = editText.getText().toString();
                    comment.time = ServerValue.TIMESTAMP;
                    if(reason.equals("미래설계상담")){
                        comment.reason = "미래설계상담";
                    } else if(reason.equals("추천서")){
                        comment.reason = "추천서";
                    } else if(reason.equals("수업질문")){
                        comment.reason = "수업질문";
                    } else {
                        comment.reason = "기타";
                    }
//                    Log.d(TAG, "onClick: "+ comment);
                    FirebaseDatabase.getInstance().getReference().child("msgRooms").child(msgRoomUid).child("comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            sendFcm();
                            editText.setText("");
                        }
                    });

                }

            }
        });
        checkMsgRoom();
    }

    void sendFcm(){
        Gson gson = new Gson();

        String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.to = destinationMember.pushToken;
        notificationModel.notification.title = userName;
        notificationModel.notification.text = editText.getText().toString();
        notificationModel.data.title = userName;
        notificationModel.data.text = editText.getText().toString();

        RequestBody requestBody = RequestBody.create(gson.toJson(notificationModel), MediaType.parse("application/json; charset=utf8"));

        Request request = new Request.Builder()
                .header("Content-Type", "application/json")
                .addHeader("Authorization", "key=AIzaSyAhvc2wdsQDOWck9DxljfzSjZjNTglY4ig")
                .url("https://fcm.googleapis.com/fcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }







    void checkMsgRoom(){
        FirebaseDatabase.getInstance().getReference().child("msgRooms").orderByChild("users/"+uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    Message message = item.getValue(Message.class);
                    if(message.users.containsKey(destinationUid)){
                        msgRoomUid = item.getKey();
                        button.setEnabled(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                        recyclerView.setAdapter(new RecyclerViewAdapter());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<Message.Comment> commentList;


        public RecyclerViewAdapter() {
            commentList = new ArrayList<>();

            FirebaseDatabase.getInstance().getReference().child("users").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    destinationMember = dataSnapshot.getValue(Member.class);
                    getMessageList();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        void getMessageList(){
            FirebaseDatabase.getInstance().getReference().child("msgRooms").child(msgRoomUid).child("comments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    commentList.clear();

                    for(DataSnapshot item : dataSnapshot.getChildren()){
                        commentList.add(item.getValue(Message.Comment.class));
                    }
//                    메시지 갱신
                    notifyDataSetChanged();
                    recyclerView.scrollToPosition(commentList.size()-1);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new MessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MessageViewHolder messageViewHolder = ((MessageViewHolder)holder);
// 내가 보낸 메시지
            if(commentList.get(position).uid.equals(uid)){
                messageViewHolder.textView_message.setText(commentList.get(position).messages);
                messageViewHolder.textView_reason.setText(commentList.get(position).reason);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.rightbubble);
                messageViewHolder.linearLayout_destination.setVisibility(View.INVISIBLE);
                messageViewHolder.textView_message.setTextSize(25);
                messageViewHolder.linearLayout_main.setGravity(Gravity.RIGHT);
            } else{
//
                messageViewHolder.textView_name.setText(destinationMember.name);
                messageViewHolder.textView_dp.setText(destinationMember.dp);
                messageViewHolder.linearLayout_destination.setVisibility(View.VISIBLE);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.leftbubble);
                messageViewHolder.textView_message.setText(commentList.get(position).messages);
                messageViewHolder.textView_reason.setText(commentList.get(position).reason);
                messageViewHolder.textView_message.setTextSize(25);
                messageViewHolder.linearLayout_main.setGravity(Gravity.LEFT);


            }

            long unixTime = (long) commentList.get(position).time;
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat.format(date);
            messageViewHolder.textView_time.setText(time);
        }

        @Override
        public int getItemCount() {
            return commentList.size();
        }

        private class MessageViewHolder extends RecyclerView.ViewHolder {
            public TextView textView_message;
            public TextView textView_name;
            public TextView textView_dp;
            public ImageView imageView_profile;
            public LinearLayout linearLayout_destination;
            public LinearLayout linearLayout_main;
            public TextView textView_time, textView_reason;

            public MessageViewHolder(View view) {
                super(view);
                textView_message = view.findViewById(R.id.messageItem_tv_message);
                textView_reason = view.findViewById(R.id.messageItem_tv_reason);
                textView_name = view.findViewById(R.id.messageItem_textview_name);
                textView_dp = view.findViewById(R.id.messageItem_textview_dp);
                imageView_profile = view.findViewById(R.id.message_item_imageview_profile);
                linearLayout_destination = view.findViewById(R.id.message_item_linearlayout_destination);
                linearLayout_main = view.findViewById(R.id.message_item_linearlayout_main);
                textView_time = view.findViewById(R.id.messageItem_tv_timestamp);

            }
        }
    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.fromleft, R.anim.toright);
    }

}
