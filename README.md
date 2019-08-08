![](https://github.com/ThisIsNSH/CandyView/blob/master/asset/main.jpg?raw=true)


The easiest way to implement RecyclerView with just 1 LOC.

* Eliminate creation of big adapter classes & usage of  `findViewById` for declaration of adapter views. 
* Get rid of different listeners and manage all view methods with single `SugarListener`.
* Automatically put data from Model class to variable without any linking code.
* Forget about lengthy RecyclerView and make delicious `CandyView`.


Download via Gradle
--------


Add it in your Project build.gradle at the end of repositories:
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```


Add the dependency in your App build.gradle:
```groovy
dependencies {
    implementation 'com.github.ThisIsNSH:CandyView:1.2'
    // add these for internal functioning
    implementation 'com.squareup.picasso:picasso:{latest version}'
    implementation 'com.android.support:recyclerview-v7:{build version}'
}
```

Get Started
--------


Add the code to create `RecyclerView` using CandyView:   
```java
class ExampleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
    
        // Initialize CandyView object
        CandyView candyView = findViewById(R.id.candy);
        
        // Create RecyclerView using make(Context content, int Layout, List<Model> Data, Model Class)
        candyView.make(this, R.layout.adapter, dataList, Model.class);
    
    }
    
}
```


Add `SugarListener` for extended functionalities:   
```java
class ExampleActivity extends Activity implements CandyView.SugarListener {
    
    TextView textView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        candyView.addSugarListener(this);
    }
    
    @Override
    public void onCandyMade() {
    
        // Initialize required adapter views 
        textView = (TextView) candyView.getViewById(R.id.text);
    
    }
    
    @Override
    public void onCandyRecycled(View view, int position) {
        
        // Set attributes of adapter views for all positions
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        
        // Set attributes of adapter views for particular position
        if (position == 1)
            textView.setTextColor(getResources().getColor(R.color.colorAccent));
    
    }
    
}
```


Set data in views without any code:

Create `Model Class` with data members having name same as ID of views in adapter view.

```java
public class Model {

    // Declare data variables 
    String image;
    String name;
    
    public Model(String image, String name) {
    this.image = image;
    this.name = name;
    }

    // Getter & Setter for the variables
    public String getImage() {
    return image;
    }

    public void setImage(String image) {
    this.image = image;
    }

    public String getName() {
    return name;
    }

    public void setName(String name) {
    this.name = name;
    }

}
```


Passing the above Model class and below adapter view automatically sets the value of `name` (String) in `name` (TextView). Not only this but `image` (String URL) is set to `image` (ImageView). `R.drawable.id` format is also supported for ImageView. Make sure the variable name in Model class and id in Layout is same.  

```XML
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="vertical">

    <android.support.v7.widget.CardView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardBackgroundColor="#fff"
    app:cardCornerRadius="16dp"
    app:cardElevation="4dp"
    app:cardPreventCornerOverlap="true"
    app:cardUseCompatPadding="true">

        <RelativeLayout
        android:layout_width="match_parent"
        android:id="@+id/relative"
        android:layout_height="wrap_content"
        android:gravity="center_vertical">

            <ImageView
            android:id="@+id/image"
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:layout_margin="8dp"
            android:scaleType="centerCrop" />

            <TextView
            android:id="@+id/name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_margin="8dp"
            android:layout_toRightOf="@id/image"
            android:textColor="#000"
            android:textSize="18sp"/>

    </RelativeLayout>

    </android.support.v7.widget.CardView>

</LinearLayout>
```




