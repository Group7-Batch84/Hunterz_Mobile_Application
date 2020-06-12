package com.example.hunterz;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyleViewerPayment extends RecyclerView.Adapter<RecyleViewerPayment.ViewHolder> {

    ArrayList<Payment> payment = new ArrayList<>();
    Context context;

    private Uri mImageUri; // Image URI
    Dialog myDialog;
    String type;

    DatabaseHandler db;


    public RecyleViewerPayment(ArrayList<Payment> payment,String type,Context context) {
        this.payment = payment;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyleViewerPayment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(type.equals("adminPay"))
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View paymentDetail = layoutInflater.inflate(R.layout.payment_view, parent, false);
            ViewHolder viewHolder = new ViewHolder(paymentDetail);

            return viewHolder;
        }
        else if(type.equals("memberPay"))
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View paymentDetail = layoutInflater.inflate(R.layout.member_payment_view, parent, false);
            ViewHolder viewHolder = new ViewHolder(paymentDetail);

            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(type.equals("adminPay"))
        {
            db = new DatabaseHandler(context);

            holder.paymentId.setText(payment.get(position).getPaymentId());
            holder.paymentMonth.setText(payment.get(position).getMonth());
            holder.paymentDate.setText(payment.get(position).getPaymentDate());
            holder.paymentMemberID.setText(payment.get(position).getMemberid());
            holder.paymentAmount.setText(String.valueOf(payment.get(position).getAmount()));

            Cursor res = db.getMember("SELECT member_image FROM member_Table WHERE member_id='" + payment.get(position).getMemberid() + "'");

            if (res.moveToFirst()) {
                holder.memberImage.setImageBitmap(printImage(res.getBlob(0)));
            }
        }
        else if(type.equals("memberPay"))
        {
            holder.paymentIdM.setText(payment.get(position).getPaymentId());
            holder.paymentMonthM.setText(payment.get(position).getMonth());
            holder.paymentDateM.setText(payment.get(position).getPaymentDate());
            holder.paymentAmountM.setText(String.valueOf(payment.get(position).getAmount()));
        }

    }

    @Override
    public int getItemCount() {
        return payment.size();
    }

    private Bitmap printImage(byte[] imageValue)  // To print the image. Convert byte to Bitmap
    {
        byte[] image = imageValue;
        Bitmap bmImage = BitmapFactory.decodeByteArray(image,0,image.length);
        return bmImage;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView paymentId,paymentMonth,paymentDate,paymentAmount,paymentMemberID;
        CircleImageView memberImage;

        TextView paymentIdM,paymentMonthM,paymentDateM,paymentAmountM;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //
            paymentId = itemView.findViewById(R.id.payment_id);
            paymentMonth = itemView.findViewById(R.id.pay_month);
            paymentDate = itemView.findViewById(R.id.pay_date);
            paymentAmount = itemView.findViewById(R.id.pay_amount);
            paymentMemberID = itemView.findViewById(R.id.member_idP);

            memberImage = itemView.findViewById(R.id.member_imageP);

            //
            paymentIdM = itemView.findViewById(R.id.payment_idM);
            paymentMonthM = itemView.findViewById(R.id.pay_monthM);
            paymentDateM = itemView.findViewById(R.id.pay_dateM);
            paymentAmountM = itemView.findViewById(R.id.pay_amountM);

        }
    }


}
