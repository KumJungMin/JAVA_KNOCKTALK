package com.cookandroid.listmembers.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.listmembers.MessageActivity;
import com.cookandroid.listmembers.R;
import com.cookandroid.listmembers.models.Member;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MemberFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_member_recyclerView );
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new MemberFragmentRecyclerViewAdapter());

        return view;
    }

    class MemberFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<Member> memberList;

        public MemberFragmentRecyclerViewAdapter() {
            memberList = new ArrayList<>();
            final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    memberList.clear();
//                    중복데이터가 누적되어 쌓이지 않게
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                        Member member = snapshot.getValue(Member.class);

                        if(member.uid.equals(myUid)){
                            continue;
                        }else {
                            memberList.add(member);
                        }

                    }
                    notifyDataSetChanged();
//                    새로고침 해주는 기능
                }

                @Override
                public void onCancelled( DatabaseError databaseError){
                }

            });
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_professor, parent, false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@Nullable RecyclerView.ViewHolder holder, final int position) {
//            Glide.with
//                    (holder.itemView.getContext())
//                    .load(memberList.get(position).profile)
//                    .apply(new RequestOptions().circleCrop())
//                    .into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).textView_prof.setText(memberList.get(position).btnStatus);
            ((CustomViewHolder)holder).textView_name.setText(memberList.get(position).name);
            ((CustomViewHolder)holder).textView_dp.setText(memberList.get(position).dp);
            ((CustomViewHolder)holder).textView_office.setText(memberList.get(position).office);

           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(v.getContext(), MessageActivity.class);
                   intent.putExtra("destinationUid", memberList.get(position).uid);
                   ActivityOptions activityOptions = null;
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                       activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(), R.anim.fromright, R.anim.toleft);
                       startActivity(intent, activityOptions.toBundle());
                   }
               }

           });

        }

        @Override
        public int getItemCount() {
            return memberList.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView_prof, textView_name, textView_dp, textView_office;

            public CustomViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.memberItem_imageView);
                textView_prof = view.findViewById(R.id.memberItem_prof);
                textView_name = view.findViewById(R.id.memberItem_name);
                textView_dp = view.findViewById(R.id.memberItem_dp);
                textView_office = view.findViewById(R.id.memberItem_office);

            }
        }
    }
}
