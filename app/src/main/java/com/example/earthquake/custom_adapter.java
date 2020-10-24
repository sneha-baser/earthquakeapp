package com.example.earthquake;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
class custom_adapter extends ArrayAdapter<custom_info> {

private static final String LOG_TAG = custom_adapter.class.getSimpleName();
public custom_adapter(Activity context, ArrayList<custom_info> a) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, a);
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if(listItemView == null) {
        listItemView = LayoutInflater.from(getContext()).inflate(
        R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        custom_info currentwords = getItem(position);



        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.id_mag);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        String formattedMagnitude = formatMagnitude(currentwords.getMag());
        // Display the magnitude of the current earthquake in that TextView
        GradientDrawable magnitudeCircle = (GradientDrawable) nameTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentwords.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        nameTextView.setText(formattedMagnitude);


        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.id_place);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        String st = currentwords.getPlace();
        if(st.contains("of")) {
                String[] parts = st.split("of");
                numberTextView.setText(parts[0]);
                TextView numberTextView1 = (TextView) listItemView.findViewById(R.id.id_place1);

                numberTextView1.setText(parts[1]);
        }
        else{
                numberTextView.setText("Near the");
                TextView numberTextView1 = (TextView) listItemView.findViewById(R.id.id_place1);

                numberTextView1.setText(st);
        }


        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        Date dateObject = new Date(currentwords.getTime());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);


        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
        }

        private String formatDate(Date dateObject) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
                return dateFormat.format(dateObject);
        }

        /**
         * Return the formatted date string (i.e. "4:30 PM") from a Date object.
         */
        private String formatTime(Date dateObject) {
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                return timeFormat.format(dateObject);
        }
        private String formatMagnitude(double magnitude) {
                DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
                return magnitudeFormat.format(magnitude);
        }
        private  int getMagnitudeColor(double mag){
                int m = (int) Math.floor(mag);

                int color;
                switch(m){


                        case 1:
                                color = R.color.mag1;
                                break;
                        case 2:
                                color = R.color.mag2;
                                break;
                        case 3:
                                color = R.color.mag3;
                                break;
                        case 4:
                                color = R.color.mag4;
                                break;
                        case 5:
                                color = R.color.mag5;
                                break;
                        case 6:
                                color = R.color.mag6;
                                break;
                        case 7:
                                color = R.color.mag7;
                                break;
                        case 8:
                                color = R.color.mag8;
                                break;
                        case 9:
                                color = R.color.mag9;
                                break;
                        case 10:
                                color = R.color.mag10;
                                break;
                        default:
                                color = R.color.mag10;
                                break;



                }
                return ContextCompat.getColor(getContext(), color);


        }

        }

