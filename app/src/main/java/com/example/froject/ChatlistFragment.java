package com.example.froject;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.bitmap.CenterCrop;
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
//import com.bumptech.glide.request.RequestOptions;
//import gujc.directtalk9.R;
//import gujc.directtalk9.chat.ChatActivity;
//import gujc.directtalk9.model.UserModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

public class ChatlistFragment extends Fragment {
    private FirestoreAdapter firestoreAdapter;

    public ChatlistFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firestoreAdapter != null) {
            firestoreAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firestoreAdapter != null) {
            firestoreAdapter.stopListening();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatlist, container, false);

        firestoreAdapter = new RecyclerViewAdapter(FirebaseFirestore.getInstance().collection("users").orderBy("name"));

        RecyclerView recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager( new LinearLayoutManager((inflater.getContext())));
        recyclerView.setAdapter(firestoreAdapter);

        return view;
    }

    class RecyclerViewAdapter extends FirestoreAdapter<CustomViewHolder> {
        //final private RequestOptions requestOptions = new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(90));
        //private StorageReference storageReference;
        private String myUid = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        RecyclerViewAdapter(Query query) {
            super(query);
            //storageReference  = FirebaseStorage.getInstance().getReference();
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CustomViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_othermsgobx, parent, false));
        }

        @Override
        public void onBindViewHolder(CustomViewHolder viewHolder, int position) {
            DocumentSnapshot documentSnapshot = getSnapshot(position);
            final Info user = documentSnapshot.toObject(Info.class);

            if (myUid.equals(user.getEmail())) {
                viewHolder.itemView.setVisibility(View.INVISIBLE);
                viewHolder.itemView.getLayoutParams().height = 0;
                return;
            }
            viewHolder.user_name.setText(user.getname());
            viewHolder.user_msg.setText(user.getuniv());

            /*if (user.getUserphoto()==null) {
                Glide.with(getActivity()).load(R.drawable.user)
                        .apply(requestOptions)
                        .into(viewHolder.user_photo);
            } else{
                Glide.with(getActivity())
                        .load(storageReference.child("userPhoto/"+user.getUserphoto()))
                        .apply(requestOptions)
                        .into(viewHolder.user_photo);
            }*/

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(getView().getContext(), WriteActivity.class);
                    Intent intent = new Intent(getView().getContext(), ChatActivity.class);
                    intent.putExtra("you_email", user.getEmail());
                    startActivity(intent);
                }
            });

        }
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;
        public TextView user_msg;

        CustomViewHolder(View view) {
            super(view);
            user_name = view.findViewById(R.id.msg_name);
            user_msg = view.findViewById(R.id.msg_item);
        }
    }
}