![](https://github.com/ThisIsNSH/CandyView/blob/master/asset/main.jpg?raw=true)


The easiest way to implement RecyclerView with just 1 LOC.

* Eliminate creation of big adapters & declaration of views by `findViewById` calls for adapter views. 
* Get rid of different listeners and manage everything with single `SugarListener`.
* Operate on single or all instances of views of adapter at once using the single listener.
* Forget about RecyclerView and make delicious `Candies`.


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
    implementation 'com.github.ThisIsNSH:CandyView:1.1'
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
        
        // Create RecyclerView using make(Context, Adapter Layout, Data List, Data Model Class)
        candyView.make(this, R.layout.adapter_layout, data_list, Model.class);
    
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


