package com.trabalhosrobson.trabalhovolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liver on 05/05/2018.
 */

public class ListViewAdapter extends ArrayAdapter<UsuarioConst> {


    private List<UsuarioConst> userList;
    private List<UsuarioConst> orig;
    private Context mCtx;

    public ListViewAdapter(List<UsuarioConst> userList, Context mCtx) {
        super(mCtx, R.layout.list_view, userList);
        this.userList = userList;
        this.mCtx = mCtx;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<UsuarioConst> results = new ArrayList<UsuarioConst>();
                if (orig == null) {
                    orig = userList;
                }
                if (constraint != null) {
                    constraint = constraint.toString().toLowerCase();
                    if (orig != null && orig.size() > 0) {
                        for (final UsuarioConst g : orig) {
                            if ((g.getNome().toLowerCase().contains(constraint.toString())) ||
                                    (g.getSobrenome().toLowerCase().contains(constraint.toString())) ||
                                    g.getEmail().toLowerCase().contains(constraint.toString())) {
                                results.add(g);
                            }
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                userList = (ArrayList<UsuarioConst>) results.values;
                notifyDataSetChanged();
            }
        };
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
        //img.setImageBitmap(usuarioConst.getImg());

        return listViewItem;
    }
}
