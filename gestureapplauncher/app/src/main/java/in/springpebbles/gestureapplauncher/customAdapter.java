package in.springpebbles.gestureapplauncher;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sourav on 26/05/2017.
 */

public class customAdapter extends ArrayAdapter<String> {


    public final Activity context;
    public final String[] appName;
    public final Drawable[] icon;
    public final String[] packageName;

    public customAdapter(Activity context, String[] appName,Drawable[] icon,String[] packageName){

        super(context,R.layout.app_list,appName);

        this.context=context;
        this.appName=appName;
        this.icon = icon;
        this.packageName = packageName;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.app_list,null,true);

        //if(icon()>position)
        {

            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView textView = (TextView) rowView.findViewById(R.id.text);
            TextView textView2 = (TextView) rowView.findViewById(R.id.text2);

            imageView.setImageDrawable(icon[position]);
            textView.setText(appName[position]);
            //textView2.setText(packageName[position]);



        }

        return  rowView;

    }



}

