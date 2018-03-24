package com.example.easts.park.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.easts.park.ParkingdetailClass;
import com.example.easts.park.R;
import com.example.easts.park.db.Parking;
import java.util.List;

/**
 * Created by lenovo on 2017/11/6.
 * 查询停车场界面 配置器
 */

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.ViewHolder> {

    private Context mContext;
    private List<Parking> mParkingList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View parkingView;
        CardView cardView;//布局控件
        ImageView parking_image1;//停车场图1
        TextView parking_name;//停车场名
        TextView parking_location;//停车场地点
        TextView freight_basis;//运费基准
        ImageView thumbs_up_view;//点赞控件
        TextView thumbs_up;//点赞量
        RatingBar rtbProductRating;//星控件
        TextView assess_level;//星等级
        TextView assess;//评价人数
        TextView residual_lot;//剩余车位数
        public ViewHolder(View view){
            super(view);
            parkingView=view;
//            cardView=(CardView)view;
            parking_image1=(ImageView)view.findViewById(R.id.parking_image1);
            parking_name=(TextView) view.findViewById(R.id.parking_name);
            parking_location=(TextView)view.findViewById(R.id.parking_location);
            freight_basis=(TextView)view.findViewById(R.id.freight_basis);
            thumbs_up_view=(ImageView)view.findViewById(R.id.thumbs_up_view);
            thumbs_up=(TextView)view.findViewById(R.id.thumbs_up);
//            rtbProductRating=(RatingBar)view.findViewById(R.id.rtbProductRating);
            assess_level=(TextView)view.findViewById(R.id.assess_level);
            assess=(TextView)view.findViewById(R.id.assess);
            residual_lot=(TextView)view.findViewById(R.id.residual_lot);
        }
        }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.parking_item_view,null,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.parking_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Parking p=mParkingList.get(position);
//                Toast.makeText(v.getContext(),p.getParking_name()+p.getParking_location(),Toast.LENGTH_LONG).show();
                Intent i=new Intent(v.getContext(), ParkingdetailClass.class);
                i.putExtra("Parking",p);
                v.getContext().startActivity(i);
            }
        });
        return holder;
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Parking parking =mParkingList.get(position);
        holder.parking_name.setText(parking.getParking_name());
        holder.parking_location.setText(parking.getParking_location());
//        Glide.with(mContext).load(parting.getParking_image1()).into(holder.parking_image1);
        /***
         * load()参数可以是urL地址or本地路径or资源id
         */
        Glide.with(mContext).load(R.drawable.parkingimages).into(holder.parking_image1);
        holder.freight_basis.setText(String.valueOf(parking.getFreight_basis()));
        Glide.with(mContext).load(R.id.thumbs_up_view).into(holder.thumbs_up_view);
        holder.freight_basis.setText("¥"+ parking.getFreight_basis()+"/h");
        holder.thumbs_up.setText(String.valueOf(parking.getThumbs_up()));
//        holder.star_level.setText(parking.getAssess_level()+"");
        holder.assess_level.setText("3");
        holder.assess.setText(String.valueOf(parking.getAssess()));

        if (parking.getThumbs_up()>0){
            Glide.with(mContext).load(R.drawable.thumbs_up1).into(holder.thumbs_up_view);
        }else {
            Glide.with(mContext).load(R.drawable.thumbs_up0).into(holder.thumbs_up_view);
        }
//        holder.residual_lot.setText(parking.getResidual_lot());
//        holder.rtbProductRating.setRating(parking.getAssess_level());
//        holder.rtbProductRating.setRating(3);

    }

    @Override
    public int getItemCount() {
        return mParkingList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public ParkingAdapter(List<Parking> parkingList){
             mParkingList= parkingList;
    }
    public void setData(List<Parking> newParkingList){
        this.mParkingList=newParkingList;
    }

}
