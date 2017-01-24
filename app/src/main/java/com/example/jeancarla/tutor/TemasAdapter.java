package com.example.jeancarla.tutor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Jean Carla on 30/11/2016.
 */
public class TemasAdapter extends RecyclerView.Adapter<TemasAdapter.MyViewHolder>
        implements ItemClickListener {

    private Context mContext;
    private List<Tema> albumList;
    private Dialog dialog;

    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        public TextView title, count;
        public ImageView overflow, thumbnail;
        public ItemClickListener listener;

        public MyViewHolder(View view, ItemClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            this.listener = listener;

            view.setOnClickListener(this);
           // overflow = (ImageView) view.findViewById(R.id.overflow);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }


    public TemasAdapter(Context mContext, List<Tema> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tema, parent, false);

        return new MyViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Tema album = albumList.get(position);
        holder.title.setText(album.getNombre());
        holder.count.setText(album.getDescripcion());
        //holder.thumbnail.setText(album.getNro()+"");
        // loading album cover using Glide library
        Glide.with(mContext).load(album.getNro()).into(holder.thumbnail);


    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {

        Log.e("CLICK ====>", albumList.get(position).getId());
        final String id, idu, nusuario;
        id = albumList.get(position).getId();
        idu = albumList.get(position).getUser();
        nusuario = albumList.get(position).getId_user();

        if(albumList.get(position).getNro() == R.drawable.unob ||
                albumList.get(position).getNro() == R.drawable.dosb ||
                albumList.get(position).getNro() == R.drawable.tresb ||
                albumList.get(position).getNro() == R.drawable.cuatrob ||
                albumList.get(position).getNro() == R.drawable.cincob ||
                albumList.get(position).getNro() == R.drawable.seisb ||
                albumList.get(position).getNro() == R.drawable.sieteb){

            dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_no);
            dialog.show();

            Button ir = (Button) dialog.findViewById(R.id.si_boton);

            ir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        else {
            Intent i;
            if(albumList.get(position).getEstilo().equals("3")||albumList.get(position).getEstilo().equals("4")){
                i = new Intent(mContext, Contenido.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id", id);
                bundle.putSerializable("user", idu);
                bundle.putSerializable("nusuario", nusuario);
                i.putExtras(bundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
            else{
                i = new Intent(mContext, ContenidoVideo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id", id);
                bundle.putSerializable("user", idu);
                bundle.putSerializable("nusuario", nusuario);
                i.putExtras(bundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }

        }

    }
    /**
     * Click listener for popup menu items

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }*/

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}


interface ItemClickListener {
    void onItemClick(View view, int position);
}