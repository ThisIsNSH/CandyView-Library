package com.thisisnsh.sugarlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandyView extends RecyclerView implements CreamAdapter.CreamAdapterListener {

    CreamAdapter creamAdapter;
    SugarListener listener;

    public void make(@NonNull Context context, @NonNull int adapterLayout, @NonNull List<?> data, @NonNull Class model) {
        creamAdapter = new CreamAdapter(context, adapterLayout, getDataMap(data, model));
        creamAdapter.setCreamAdapterListener(this);
        setAdapter(creamAdapter);
        setLayoutManager(new LinearLayoutManager(context));
    }

    public void make(@NonNull Context context, @NonNull int adapterLayout, @NonNull List<?> data, @NonNull Class model, @NonNull LayoutManager layoutManager) {
        this.creamAdapter = new CreamAdapter(context, adapterLayout, getDataMap(data, model));
        this.creamAdapter.setCreamAdapterListener(this);
        this.setAdapter(creamAdapter);
        this.setLayoutManager(layoutManager);
    }

    public void make(@NonNull Context context, @NonNull int adapterLayout, @NonNull List<?> data, @NonNull Class model, @NonNull boolean isHorizontal) {
        this.creamAdapter = new CreamAdapter(context, adapterLayout, getDataMap(data, model));
        this.creamAdapter.setCreamAdapterListener(this);
        this.setAdapter(creamAdapter);
        if (!isHorizontal)
            this.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        else
            this.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    public CandyView(@NonNull Context context) {
        super(context);
        this.listener = null;
    }

    public CandyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.listener = null;
    }

    public CandyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.listener = null;
    }

    public void addSugarListener(SugarListener listener) {
        this.listener = listener;
    }

    @Override
    public void onNewPosition(View view, int position) {
        listener.onCandyRecycled(view, position);
    }

    @Override
    public void onFinishInit() {
        listener.onCandyMade();
    }

    public View getViewById(@NonNull final int id) {
        return creamAdapter.getViewById(id);
    }

    public interface SugarListener {
        void onCandyRecycled(View view, int position);
        void onCandyMade();
    }

    public List<Map<String, Object>> getDataMap(List<?> data, Class model) {
        List<Map<String, Object>> dataMap = new ArrayList<>();
        try {
            for (int j = 0; j < data.size(); j++) {
                Map<String, Object> map = getMap();
                Field[] allFields = model.getDeclaredFields();
                for (Field field : allFields) {
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(data.get(j)));
                    map.put(field.getName() + ":type", field.getGenericType());
                }
                dataMap.add(map);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    public Map<String, Object> getMap() {
        return new HashMap<>();
    }

}
