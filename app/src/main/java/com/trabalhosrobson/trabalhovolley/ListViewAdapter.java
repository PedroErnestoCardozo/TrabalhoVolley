package com.trabalhosrobson.trabalhovolley;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        final ImageView img = listViewItem.findViewById(R.id.img);


        UsuarioConst usuarioConst = userList.get(position);

        txtNome.setText(usuarioConst.getNome() + " " +usuarioConst.getSobrenome());
        txtEmail.setText(usuarioConst.getEmail());
        String link = usuarioConst.getImg();

        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);

        // Initialize a new ImageRequest
        ImageRequest imageRequest = new ImageRequest(
                link, // Image URL
                new Response.Listener<Bitmap>() { // Bitmap listener
                    @Override
                    public void onResponse(Bitmap response) {
                        // Do something with response
                        img.setImageBitmap(response);

                    }
                },
                0, // Image width
                0, // Image height
                ImageView.ScaleType.CENTER_CROP, // Image scale type
                Bitmap.Config.RGB_565, //Image decode configuration
                new Response.ErrorListener() { // Error listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with error response
                        error.printStackTrace();
                    }
                }
        );
        
        requestQueue.add(imageRequest);

        return listViewItem;
    }
}
