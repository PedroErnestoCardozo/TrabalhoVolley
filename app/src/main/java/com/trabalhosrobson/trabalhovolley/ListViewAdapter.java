package com.trabalhosrobson.trabalhovolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trabalhosrobson.trabalhovolley.UsuarioConst;

import java.util.List;

/**
 * Created by Liver on 05/05/2018.
 */

public class ListViewAdapter extends ArrayAdapter<UsuarioConst> {


    private List<UsuarioConst> userList;
    private Context mCtx;

    public ListViewAdapter(List<UsuarioConst> userList, Context mCtx) {
        super(mCtx, R.layout.list_view, userList);
        this.userList = userList;
        this.mCtx = mCtx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View listViewItem = inflater.inflate(R.layout.list_view, null, true);

        TextView txtNome = listViewItem.findViewById(R.id.txtNome);
        TextView txtEmail = listViewItem.findViewById(R.id.txtEmail);
        ImageView img = listViewItem.findViewById(R.id.img);


        UsuarioConst usuarioConst = userList.get(position);

        txtNome.setText(usuarioConst.getNome() + " " +usuarioConst.getSobrenome());
        txtEmail.setText(usuarioConst.getEmail());
        img.setImageBitmap(usuarioConst.getImg());

        return listViewItem;
    }
}
