package com.thebasicapp.EasyResumeBuilder;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import Helper.Constants;

public class TemplatesDialog extends AppCompatActivity {

    ImageView img1;
//    img2, img3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates_dialog);

        img1 = (ImageView) findViewById(R.id.img1);
//        img2 = (ImageView) findViewById(R.id.img2);
//        img2.setVisibility(View.GONE);
   //     img3 = (ImageView) findViewById(R.id.img3);
        Intent i = getIntent();
        final String DialogType = i.getStringExtra("DialogType");
//        1.share
//        2.view
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.TEMPLATE_NO = 1;
                if(DialogType.equals("share")){
                    Generate.template_set_share = true;
                    Generate.template_set_view = false;
                }else{
                    Generate.template_set_view = true;
                    Generate.template_set_share = false;
                }

                finish();
            }
        });
//        img2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Constants.TEMPLATE_NO = 2;
//                if(DialogType.equals("share")){
//                    Generate.template_set_share = true;
//                    Generate.template_set_view = false;
//                }else{
//                    Generate.template_set_view = true;
//                    Generate.template_set_share = false;
//                }
//                finish();
//            }
//        });
//        img3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Constants.TEMPLATE_NO = 3;
//                if(DialogType.equals("share")){
//                    Generate.template_set_share = true;
//                    Generate.template_set_view = false;
//                }else{
//                    Generate.template_set_view = true;
//                    Generate.template_set_share = false;
//                }
//                finish();
//            }
//        });
        Generate.template_set_view = false;
        Generate.template_set_share = false;
    }
}
