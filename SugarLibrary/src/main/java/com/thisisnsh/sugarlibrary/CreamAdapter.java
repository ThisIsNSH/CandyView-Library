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

    /**
     * Integer to store adapter layout ID.
     */
    private int adapterLayout;

    /**
     * Context object to store the context of calling Activity.
     */
    private Context context;

    /**
     * List of View which takes in all the child views of inflated adapterLayout.
     */
    private List<View> viewList;

    /**
     * List of Map stores the data map which is sent by the CandyView.
     *
     * @see com.thisisnsh.sugarlibrary.CandyView#getDataMap(List, Class)
     */
    private List<Map<String, Object>> dataMap;

    /**
     * Listener object which provides two methods
     * onNewPosition() & onFinishInit()
     *
     * @see CreamAdapter.CreamAdapterListener#onNewPosition(View, int)
     * @see CreamAdapterListener#onFinishInit()
     */
    private CreamAdapterListener listener;

    /**
     * Constructor to use when creating an adapter.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     * @param adapterLayout The ID of the layout for the adapter.
     * @param context The List of Maps which contains the data stored as List of Model in calling Activity.
     */
    public CreamAdapter(Context context, int adapterLayout, List<Map<String, Object>> dataMap) {
        this.adapterLayout = adapterLayout;
        this.context = context;
        this.dataMap = dataMap;
        this.listener = null;
    }

    /**
     * Override method of RecyclerView.Adapter class.
     * The layout to be inflated is passed in the LayoutInflator and the view is
     * returned as SuperViewHolder class instance.
     *
     * @see CreamAdapter.SuperViewHolder
     */
    @NonNull
    @Override
    public SuperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(adapterLayout, viewGroup, false);
        return new SuperViewHolder(view);
    }

    /**
     * Override method of RecyclerView.Adapter class.
     *
     * Whenever the views are recycled or are about to
     * recycle this method is called. The viewList is iterated
     * and the data present at the position i is put in the views.
     *
     * Map contains the view id and data. So the iteration puts the
     * data in the correct views by checking their IDs.
     */
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

    /**
     * Override method of RecyclerView.Adapter class.
     *
     * This returns the count of views required.
     */
    @Override
    public int getItemCount() {
        return dataMap.size();
    }

    /**
     * Class to hold views inflated by the adapterLayout.
     */
    public class SuperViewHolder extends RecyclerView.ViewHolder {

        public SuperViewHolder(@NonNull View itemView) {
            super(itemView);
            viewList = new ArrayList<>();
            viewList.addAll(getAllChildren(itemView));
            listener.onFinishInit();
        }
    }

    /**
     * Method to find and return all child views present in
     * adapterLayout to finally add them in viewList.
     *
     * @param v The View is the view generated by the LayoutInflator
     *          in onBindViewHolder, through which we get all the
     *          child views in the layout and add them to viewList.
     *
     * @return result The child view list is returned.
     */
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

    /**
     * Incomplete Method to get viewList.
     */
    private <T extends View> Map<String, T> getViewsList() {
        Map<String, T> views = new HashMap<>();
        for (int i = 0; i < viewList.size(); i++) {
            T view = (T) viewList.get(i);
            views.put(viewList.get(i).getResources().getResourceEntryName(viewList.get(i).getId()), view);
        }
        return views;
    }

    /**
     * Method to set CreamAdapterListener on CreamAdapter object.
     *
     * @param listener The Listener is set to the member variable listener
     *                 which was earlier set to null.
     */
    public void setCreamAdapterListener(CreamAdapterListener listener) {
        this.listener = listener;
    }

    /**
     * Public interface to create CreamAdapterListner.
     */
    public interface CreamAdapterListener {

        /**
         * Method provides current position of the candy view
         * & child views in that adapter view.
         *
         * @param view The View gives all the views recycled for
         *        a position.
         * @param position The int gives the current position of
         *                 the candy view.
         */
        void onNewPosition(View view, int position);

        /**
         * Method which is called as soon as the layout is
         * inflated and child views are added to viewList.
         */
        void onFinishInit();
    }

    /**
     * Method to return View of the required view_id.
     *
     * @param id The ID of the view whose view is to
     *           be returned.
     */
    public View getViewById(@NonNull final int id) {
        for (int i = 0; i < viewList.size(); i++) {
            if (viewList.get(i).getId() == id)
                return viewList.get(i);
        }
        return null;
    }

}
