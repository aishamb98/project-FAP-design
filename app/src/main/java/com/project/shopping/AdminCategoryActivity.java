package com.project.shopping;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategoryActivity extends AppCompatActivity {
    private TextView homecenter, Ikea;
    private TextView potteryBarn, westelm;

    private Button LogoutBtn, CheckOrdersBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);


        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });



        homecenter = (TextView) findViewById(R.id.homecenter);
        Ikea = (TextView) findViewById(R.id.Ikea);

        potteryBarn = (TextView) findViewById(R.id.potteryBarn);
        westelm = (TextView) findViewById(R.id.westelm);


        homecenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.project.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "home Center");
                startActivity(intent);
            }
        });
        Ikea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.project.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Ikea");
                startActivity(intent);
            }
        });


        potteryBarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.project.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Pottery Barn");
                startActivity(intent);
            }
        });



        westelm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.project.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Westelm");
                startActivity(intent);
            }
        });



    }
}
