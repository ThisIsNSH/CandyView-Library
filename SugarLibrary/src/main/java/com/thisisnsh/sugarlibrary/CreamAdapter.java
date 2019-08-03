package com.thisisnsh.sugarlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreamAdapter extends RecyclerView.Adapter<CreamAdapter.SuperViewHolder> {

    private int adapterLayout;
    private Context context;
    private List<View> viewList;
    private List<Map<String, Object>> dataMap;
    private CreamAdapterListener listener;

    public CreamAdapter(Context context, int adapterLayout, List<Map<String, Object>> dataMap) {
        this.adapterLayout = adapterLayout;
        this.context = context;
        this.dataMap = dataMap;
        this.listener = null;
    }

    @NonNull
    @Override
    public SuperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(adapterLayout, viewGroup, false);
        return new SuperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuperViewHolder superViewHolder, int i) {
        for (int counter = 0; counter < viewList.size(); counter++) {
            listener.onNewPosition(viewList.get(counter), i);

            if (viewList.get(counter) instanceof TextView) {
                TextView textView = (TextView) viewList.get(counter);
                if (dataMap.get(i).get(textView.getResources().getResourceEntryName(textView.getId())) != null) {
                    textView.setText(String.valueOf(dataMap.get(i).get(textView.getResources().getResourceEntryName(textView.getId()))));
                }
            } else if (viewList.get(counter) instanceof ImageView) {
                ImageView imageView = (ImageView) viewList.get(counter);
                if (dataMap.get(i).get(imageView.getResources().getResourceEntryName(imageView.getId())) != null) {
                    if (String.valueOf(dataMap.get(i).get(imageView.getResources().getResourceEntryName(imageView.getId()) + ":type")).toLowerCase().contains("int")) {
                        imageView.setImageDrawable(context.getResources().getDrawable((int) dataMap.get(i).get(imageView.getResources().getResourceEntryName(imageView.getId()))));
                    } else if (String.valueOf(dataMap.get(i).get(imageView.getResources().getResourceEntryName(imageView.getId()) + ":type")).toLowerCase().contains("string")) {
                        //todo allow different image options
                        Picasso.get().load((String) dataMap.get(i).get(imageView.getResources().getResourceEntryName(imageView.getId()))).into(imageView);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataMap.size();
    }

    public class SuperViewHolder extends RecyclerView.ViewHolder {

        public SuperViewHolder(@NonNull View itemView) {
            super(itemView);
            viewList = new ArrayList<>();
            viewList.addAll(getAllChildren(itemView));
            listener.onFinishInit();
        }
    }

    public List<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();
        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            result.addAll(getAllChildren(child));
        }
        return result;
    }

    public <T extends View> Map<String, T> getViewsList() {
        Map<String, T> views = new HashMap<>();
        for (int i = 0; i < viewList.size(); i++) {
            T view = (T) viewList.get(i);
            views.put(viewList.get(i).getResources().getResourceEntryName(viewList.get(i).getId()), view);
        }
        return views;
    }

    public void setCreamAdapterListener(CreamAdapterListener listener) {
        this.listener = listener;
    }

    public interface CreamAdapterListener {
        void onNewPosition(View view, int position);
        void onFinishInit();
    }

    public View getViewById(@NonNull final int id) {
        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i).getId() == id)
                return viewList.get(i);
        }
        return null;
    }

}
