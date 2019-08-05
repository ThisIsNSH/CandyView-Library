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

    /**
     * CreamAdapter stores the adapter instance which is
     * initialized in the CandyView constructor.
     */
    CreamAdapter creamAdapter;

    /**
     * SugarListener provides additional functionalities
     * like to find if the views are created.
     */
    SugarListener listener;

    /**
     * Simple constructor to initialize CandyView.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     */
    public CandyView(@NonNull Context context) {
        super(context);
        this.listener = null;
    }

    /**
     * Simple constructor to initialize CandyView.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     */
    public CandyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.listener = null;
    }

    /**
     * Simple constructor to initialize CandyView.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     */
    public CandyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.listener = null;
    }

    /**
     * Simple method to make and use CandyView.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     * @param adapterLayout Integer to store adapter layout ID.
     * @param data Every time this is similar to something.
     * @param model This is the class of your data model.
     */
    public void make(@NonNull Context context, @NonNull int adapterLayout, @NonNull List<?> data, @NonNull Class model) {
        creamAdapter = new CreamAdapter(context, adapterLayout, getDataMap(data, model));
        creamAdapter.setCreamAdapterListener(this);
        setAdapter(creamAdapter);
        setLayoutManager(new LinearLayoutManager(context));
    }

    /**
     * Simple method to make and use CandyView.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     * @param adapterLayout Integer to store adapter layout ID.
     * @param data Every time this is similar to something.
     * @param model This is the class of your data model.
     * @param layoutManager The LayoutManager specifies the orientation for the
     *                      arrangement of the views in the CandyView.
     */
    public void make(@NonNull Context context, @NonNull int adapterLayout, @NonNull List<?> data, @NonNull Class model, @NonNull LayoutManager layoutManager) {
        this.creamAdapter = new CreamAdapter(context, adapterLayout, getDataMap(data, model));
        this.creamAdapter.setCreamAdapterListener(this);
        this.setAdapter(creamAdapter);
        this.setLayoutManager(layoutManager);
    }

    /**
     * Simple method to make and use CandyView.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     * @param adapterLayout Integer to store adapter layout ID.
     * @param data Every time this is similar to something.
     * @param model This is the class of your data model.
     * @param isHorizontal The LayoutManager specified is LinearLayoutManager
     *                     the orientation is horizontal if the param is true.
     */
    public void make(@NonNull Context context, @NonNull int adapterLayout, @NonNull List<?> data, @NonNull Class model, @NonNull boolean isHorizontal) {
        this.creamAdapter = new CreamAdapter(context, adapterLayout, getDataMap(data, model));
        this.creamAdapter.setCreamAdapterListener(this);
        this.setAdapter(creamAdapter);
        if (!isHorizontal)
            this.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        else
            this.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     * Method to set SugarListener on CandyView object.
     *
     * @param listener The Listener is set to the member variable listener
     *                 which was earlier set to null.
     */
    public void addSugarListener(SugarListener listener) {
        this.listener = listener;
    }

    /**
     * Method provides current position of the recycled view
     * & child views in that adapter view.
     *
     * @param view The View gives all the views recycled for
     *        a position.
     * @param position The int gives the current position of
     *                 the recycled view.
     */
    @Override
    public void onNewPosition(View view, int position) {
        listener.onCandyRecycled(view, position);
    }

    /**
     * Method which is called as soon as the layout is
     * inflated and child views are added to viewList.
     */
    @Override
    public void onFinishInit() {
        listener.onCandyMade();
    }

    /**
     * Method returns the View of the requested ID.
     *
     * @param id The int is the ID of some view
     *           whose View is to be used for some logic.
     */
    public View getViewById(@NonNull final int id) {
        return creamAdapter.getViewById(id);
    }

    /**
     * Simple interface for SugarListener.
     */
    public interface SugarListener {

        /**
         * Method provides current position of the candy view
         * & child views in that adapter view.
         *
         * @param view The View gives all the views recycled for
         *        a position.
         * @param position The int gives the current position of
         *                 the candy view.
         */
        void onCandyRecycled(View view, int position);

        /**
         * Method which is called as soon as the layout is
         * inflated and child views are added to viewList.
         */
        void onCandyMade();
    }

    /**
     * Method which takes in data in List of Model form but get result
     * in List of Map form. The views when added to viewList is circulated in on group.
     *
     * The model class is searched for all the fields and the name and type bhi done.
     *
     * @param data The List of ? is very romantic. The data list is iterated for all
     *             fields in the model class and put them in a list of maps.
     *
     * @param model The Class specifies the class of List Model.
     */
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

    /**
     * Method creates new Maps as they are call by reference so more than relation can be a problem.
     */
    public Map<String, Object> getMap() {
        return new HashMap<>();
    }

}
