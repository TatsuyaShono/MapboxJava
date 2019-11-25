package com.example.code_for_ube.mapboxjava;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * RealmRecyclerViewAdapterクラスのコンストラクタ
 * 書式：RealmRecyclerViewAdapter(OrderedRealmCollection<T>data, boolean autoUpdate)
 * 引数：data        アダプターにセットするデータ
 *      autoUpdate  自動更新のON,OFFを指定
 */
public class SpotRealmAdapter extends RealmRecyclerViewAdapter<Spot,
        SpotRealmAdapter.SpotViewHolder> {

    Context context;

    public  static  class SpotViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView bodyText;
        protected ImageView photo;

        public SpotViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title);
            bodyText = itemView.findViewById(R.id.body);
            photo = itemView.findViewById(R.id.spot_photo);
        }
    }

    public SpotRealmAdapter(@NonNull Context context,
                            @Nullable OrderedRealmCollection<Spot> data,
                            boolean autoUpdate){
        super(data, autoUpdate);
        this.context = context;
    }

    /**
     * onCreateViewHolderメソッド
     * 機能：RecycleViewが新しいRecycleView.ViewHolderを必要とするときに呼び出されます
     * 書式：VH onCreateViewHolder(ViewGroup parent, int viewType)
     * @param parent    新しいビューがアダプターの場所にバインドされた時に追加する先のViewGroup
     * @param viewType  新しいビューのビュータイプ
     * @return
     */
    @Override
    public SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        final SpotViewHolder holder = new SpotViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                /**
                 * getAdapterPositionメソッド
                 * 機能：このViewHolderが表すアイテムのアダプター位置を返します
                 * 書式：int getAdapterPosition()
                 */
                int position = holder.getAdapterPosition();
                Spot spot = getData().get(position);
                long spotId = spot.id;

                Intent intent = new Intent(context, ShowSpotActivity.class);
                intent.putExtra(ShowSpotActivity.SPOT_ID, spotId);
                context.startActivity(intent);
            }
        });

        return holder;
    }

    /**
     * onBindViewHolderメソッド
     * 機能：指定された位置にデータを表示するためにRecycleViewによって呼び出されます
     * 書式：void onBindViewHolder(VH holder, int position)
     * @param holder    データセット内の指定された位置にある項目の内容を表示するために更新されるViewHolder
     * @param position  アダプターのセットデータ内の項目の位置
     */
    @Override
    public  void onBindViewHolder(SpotViewHolder holder, int position){
        Spot spot = getData().get(position);
        holder.title.setText(spot.title);
        holder.bodyText.setText(spot.bodyText);
        if(spot.image != null && spot.image.length != 0){
            Bitmap bmp = MyUtils.getImageFromByte(spot.image);
            /**
             * setImageBitmapメソッド
             * 機能：BitmapをImageViewに設定して表示します
             * 書式：void setImageBitmap(Bitmap bm)
             * 引数：bm    ImageViewに表示するBitmap
             */
            holder.photo.setImageBitmap(bmp);
        }
    }
}
