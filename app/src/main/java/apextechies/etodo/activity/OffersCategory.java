package apextechies.etodo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import apextechies.etodo.R;
import apextechies.etodo.common.CustomItemAnimator;

/**
 * Created by Shankar on 10/5/2017.
 */

public class OffersCategory extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);
        initwidget();
    }

    private void initwidget() {
         RecyclerView listrecycler;
        listrecycler = (RecyclerView) findViewById(R.id.listrecycler);
        listrecycler.setLayoutManager(new LinearLayoutManager(this));
        listrecycler.setItemAnimator(new CustomItemAnimator());
    }
}
