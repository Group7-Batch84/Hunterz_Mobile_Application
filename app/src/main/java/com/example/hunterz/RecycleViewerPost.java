package com.example.hunterz;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import static android.widget.Toast.LENGTH_LONG;


public class RecycleViewerPost extends RecyclerView.Adapter<RecycleViewerPost.ViewHolder> {

    ArrayList<Post> post= new ArrayList<>();
    Context context;

    private Uri mImageUri; // Image URI
    Dialog myDialog;
    String postId;
    Fragment fragment;


    public RecycleViewerPost(ArrayList<Post> post, Context context,Fragment fragment) {
        this.post = post;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecycleViewerPost.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View postDetail = layoutInflater.inflate(R.layout.post_view, parent, false);
        final ViewHolder viewHolder = new ViewHolder(postDetail);

        viewHolder.relativeLayoutPost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.post_details);

                final ImageView postImage;
                TextView postDate;
                EditText postSubject,postDescription;
                Button deletePostBtn;

                postImage = myDialog.findViewById(R.id.post_imageU);

                postDate = myDialog.findViewById(R.id.post_dateM);
                postSubject = myDialog.findViewById(R.id.post_subject);
                postDescription = myDialog.findViewById(R.id.post_description);
                deletePostBtn = myDialog.findViewById(R.id.post_delete_btn);

                final String postID = post.get(viewHolder.getAdapterPosition()).getPost_id();
                postImage.setImageBitmap(post.get(viewHolder.getAdapterPosition()).getPost_image());
                postDate.setText(post.get(viewHolder.getAdapterPosition()).getPost_date());
                postSubject.setText(post.get(viewHolder.getAdapterPosition()).getPost_subject());
                postDescription.setText(post.get(viewHolder.getAdapterPosition()).getPost_message());

                myDialog.show();

                // Click to delete the post
                deletePostBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        delete(postID);
                        myDialog.dismiss();
                        Toast.makeText(context,"Post Deleted!", LENGTH_LONG).show();
                    }
                });

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewerPost.ViewHolder holder, int position) {

        postId = post.get(position).getPost_id();
        holder.postSubject.setText(post.get(position).getPost_subject());
        holder.postImage.setImageBitmap(post.get(position).getPost_image());
        holder.postDate.setText(post.get(position).getPost_date());
        holder.postDescription.setText(post.get(position).getPost_message());
    }

    @Override
    public int getItemCount() {
        return post.size();
    }


    public void delete(String value) // Delete Pending member
    {
        DatabaseHandler db = new DatabaseHandler(context);
        db.deletePost(value);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView postSubject,postDescription,postDate;
        ImageView postImage, updateImage;
        RelativeLayout relativeLayoutPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postSubject = itemView.findViewById(R.id.post_subject);
            postDescription = itemView.findViewById(R.id.post_description);
            postDate = itemView.findViewById(R.id.post_date);
            postImage = itemView.findViewById(R.id.post_image);
            relativeLayoutPost = itemView.findViewById(R.id.post_view);
            //
            updateImage = itemView.findViewById(R.id.post_imageU);
        }
    }


}
