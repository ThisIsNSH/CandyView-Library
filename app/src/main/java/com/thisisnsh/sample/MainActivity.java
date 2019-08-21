package com.thisisnsh.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thisisnsh.sugarlibrary.CandyView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CandyView.SugarListener {

    CandyView candyView;
    TextView tv;
    RelativeLayout relative;
    RatingBar ratingBar;

    List<Model> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        candyView = findViewById(R.id.recycle);
        setData();

        candyView.make(this, R.layout.adapter, list, Model.class);
        candyView.addSugarListener(this);
    }

    public void setData() {
        list.add(new Model("Buy Now","https://s7d9.scene7.com/is/image/zumiez/pdp_hero/Champion-Men-s-Rally-Pro-Black-%26-White-Shoes-_298256.jpg", "Some Shoes", 100.99f, R.drawable.icon));
        list.add(new Model("Add to Cart","https://images2.drct2u.com/plp_full_width_1/products/lf/lf283/p01lf283500s.jpg", "Some Shirt", 50.89f, R.drawable.icon));
        list.add(new Model("Save","https://images-na.ssl-images-amazon.com/images/I/71vdopEovDL._UX342_.jpg", "Some Jeans", 70.99f, R.drawable.icon));
        list.add(new Model("Order Now","https://assets.adidas.com/images/w_600,h_600,f_auto,q_auto:sensitive,fl_lossy/b0bdabaeb51f4c0bbfc5a92e014df707_9366/Trefoil_Baseball_Cap_Pink_DV0173_01_standard.jpg", "Some Cap", 30.99f, R.drawable.icon));
        list.add(new Model("Buy","https://media.sssports.com/630x630/media/catalog/product/4/0/4060507114620_1.jpg", "Some Jacket", 130.99f, R.drawable.icon));
        list.add(new Model("Save for Later","https://s7d9.scene7.com/is/image/zumiez/pdp_hero/Champion-Men-s-Rally-Pro-Black-%26-White-Shoes-_298256.jpg", "Some Shoes", 100.99f, R.drawable.icon));
        list.add(new Model("Contact Seller","https://images2.drct2u.com/plp_full_width_1/products/lf/lf283/p01lf283500s.jpg", "Some Shirt", 50.89f, R.drawable.icon));
        list.add(new Model("Bargain","https://images-na.ssl-images-amazon.com/images/I/71vdopEovDL._UX342_.jpg", "Some Jeans", 70.99f, R.drawable.icon));
        list.add(new Model("Add to Cart","https://assets.ad6idas.com/images/w_600,h_600,f_auto,q_auto:sensitive,fl_lossy/b0bdabaeb51f4c0bbfc5a92e014df707_9366/Trefoil_Baseball_Cap_Pink_DV0173_01_standard.jpg", "Some Cap", 30.99f, R.drawable.icon));
        list.add(new Model("Buy Now","https://media.sssports.com/630x630/media/catalog/product/4/0/4060507114620_1.jpg", "Some Jacket", 130.99f, R.drawable.icon));
    }

    @Override
    public void onCandyRecycled(View view, int position) {

        if (position == 1)
            ratingBar.setRating(2f);
        else if (position == 2)
            ratingBar.setRating(1f);

//        tv.setTextColor(getResources().getColor(R.color.colorAccent));
//        relative.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

    }

    @Override
    public void onCandyMade() {
        relative = (RelativeLayout) candyView.getViewById(R.id.relative);
        tv = (TextView) candyView.getViewById(R.id.name);
        ratingBar = (RatingBar) candyView.getViewById(R.id.rating);
    }

}

