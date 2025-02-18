package com.messieyawo.medhack2020.Home;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.allyants.notifyme.NotifyMe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.messieyawo.medhack2020.MainActivity;
import com.messieyawo.medhack2020.R;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.prush.typedtextview.TypedTextView;
import com.tuann.floatingactionbuttonexpandable.FloatingActionButtonExpandable;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateSlotFragment extends Fragment {
    private EditText nameEditTxt,verseDescripton,verseText,dateText;
     private Spinner mSpinner;
private RecyclerView mRecyclerView;
     ProgressBar progressBar;
     //notification
     private final String CHANNEL_ID = "personal_notification";
    private final int NOTIFICATION_ID = 001;
    String user;
    public UpdateSlotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_daily_slots, container, false);


        mRecyclerView = view.findViewById(R.id.recycler_verses);
        progressBar = view.findViewById(R.id.loading_verses);

        new FirebaseDatabaseHelper().readVerses(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<DailySlots> dailyVers, List<String> keys) {
                progressBar.setVisibility(View.GONE);
                new RecyclerViewConfig().setConfg(mRecyclerView,getActivity(), dailyVers,keys);
            }

            @Override
            public void DataInserted() {

            }

            @Override
            public void DataDeleted() {

            }

            @Override
            public void DataUpdated() {

            }
        });


       FloatingActionButtonExpandable fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialogBox();
            }

        });


        //typewriter

        TypedTextView typedTextView = view.findViewById( R.id.typedTextView );
            //Using Builder
        TypedTextView.Builder builder = new TypedTextView.Builder( typedTextView )
                .setTypingSpeed( 175 )
                .splitSentences( true )
                .setSentencePause( 1500 )
                .setCursorBlinkSpeed( 530 )
                .randomizeTypingSpeed( true )
                .showCursor( true )
                .playKeyStrokesAudio(false )
                .randomizeTypeSeed( 250 );

        typedTextView = builder.build();

        typedTextView.setTypedText( "Welcome to MedHack2020.We are here to find how best we can stop the spread of COV19.Therefore I thought of designing an android app that can be used by patients to book reservations with the clinic before going to the clinic on the appropriate time.This can avoid waiting rooms being crowded.The hospital can post available time for the users of the app to see with real time notification. " );
        return view;
    }



    private void openDialogBox() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_two, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
        final EditText etEmail = alertLayout.findViewById(R.id.et_code);
        final CheckBox cbToggle = alertLayout.findViewById(R.id.cb_show_pass);

        cbToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // to encode password in dots
                    etEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // to display the password in normal text
                    etEmail.setTransformationMethod(null);
                }
            }
        });



        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("No let me Go back", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(getContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                DynamicToast.makeSuccess(getActivity(), "Good, That's fine, wait it will be updated soon...", Toast.LENGTH_LONG).show();
            }
        });

        alert.setPositiveButton("That's fine", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                 user = etUsername.getText().toString().trim();
                String pass = etEmail.getText().toString().trim();
               // Toast.makeText(getContext(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();

                if (user.contains("Word") && pass.contentEquals("1234") || user.contains("Paul") && pass.contentEquals("6789") ||
                        user.contains("Bride") && pass.contains("1111")){


                    displayInputDialog();



                }else {

                   // Toast.makeText(getContext(), "You are a liar " , Toast.LENGTH_SHORT).show();
                    DynamicToast.makeWarning(getContext(), "You've entered the wrong information.Seems you are not part of the hospital team...",Toast.LENGTH_LONG).show();

                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        }
    }



    //DISPLAY INPUT DIALOG
    private void displayInputDialog() {

        Dialog d = new Dialog(getActivity());
        d.setContentView(R.layout.input_dialog);

        nameEditTxt =  d.findViewById(R.id.nameEditText);
        verseText =  d.findViewById(R.id.verseEditText);
        verseDescripton =  d.findViewById(R.id.descEditText);
        dateText = d.findViewById(R.id.dateEditText);
        mSpinner = d.findViewById(R.id.choiceSpinner);



        Button saveBtn =  d.findViewById(R.id.saveBtn);
        Button cansel_btn = d.findViewById(R.id.dismiss_dialog);
        cansel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();

            }
        });

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference().getRoot().child("WordBride");
                dbNode.setValue(null);


                //GET DATA

           DailySlots dailySlots = new DailySlots();
           dailySlots.setDate(dateText.getText().toString());
           dailySlots.setDescription(verseDescripton.getText().toString());
           dailySlots.setName(nameEditTxt.getText().toString());
           dailySlots.setVerse(verseText.getText().toString());
           dailySlots.setType(mSpinner.getSelectedItem().toString());




           new FirebaseDatabaseHelper().addDailyVerses(dailySlots, new FirebaseDatabaseHelper.DataStatus() {
               @Override
               public void DataIsLoaded(List<DailySlots> dailyVers, List<String> keys) {

               }

               @Override
               public void DataInserted() {
                   DynamicToast.makeSuccess(getActivity(), "Another slot is added successfully..", Toast.LENGTH_SHORT).show();
                   d.dismiss();
                  // sendNotification();
                  // EventNotify();
                 NotificationChannel();

               }

               @Override
               public void DataDeleted() {

               }

               @Override
               public void DataUpdated() {

               }
           });


            }
        });

        d.show();
        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    private void sendNotification() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);


            channel.setDescription("Welcome to MedHack 2020.");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setVibrationPattern(new long[]{0,1000,500,1000});
            channel.enableVibration(true);

            assert manager != null;
            manager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(),"n")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.medhackb)
                .setContentTitle("MedHack 2020 real time update")
                .setContentText("New slot has been added");

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getActivity());
        managerCompat.notify(999,notificationBuilder.build());
    }

    public void EventNotify(){

        Intent activityIntent = new Intent(getActivity(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(),
                0, activityIntent, 0);


        NotifyMe notifyMe = new NotifyMe.Builder(getContext())
                .title("MedHack 2020 real time update.")
                .content("New slot has been added.")
                .color(255,0,0,255)
                .led_color(255,255,255,255)
                .small_icon(R.drawable.medhackb)

                .small_icon(R.drawable.medhackb)
                .addAction(new Intent(activityIntent),"You can check it out.",false,true)

                .build();





    }

    public void NotificationChannel(){

        int color = ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark);
        int color1 = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
       String tiTle = "MedHack 2020 real time update";
       String body = "New slot has been added by: "+user;

        Intent YesIntent = new Intent(getActivity(), MainActivity.class);
        //we are going to specify some flag for the intent
        YesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //now we need to define an object this intent using pending intent
        PendingIntent YesPendingIntent = PendingIntent.getActivity(getActivity(), 0, YesIntent, PendingIntent.FLAG_ONE_SHOT);


//        Intent NoIntent = new Intent(getActivity(),MainActivity.class);
//        //we are going to specify some flag for the intent
//        NoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        //now we need to define an object this intent using pending intent
//        PendingIntent NoPendingIntent = PendingIntent.getActivity(getActivity(), 0, NoIntent, PendingIntent.FLAG_ONE_SHOT);
//        //NoPendingIntent.getBroadcast(getContext(), 0, NoIntent, PendingIntent.FLAG_UPDATE_CURRENT).cancel();



        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            CharSequence name = "MedHack 2020 real time update";
            String description = "New slot has been added: "+user;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager =(NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);


            PowerManager pm = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
            boolean isScreenOn = Build.VERSION.SDK_INT >= 20 ? pm.isInteractive() : pm.isScreenOn(); // check if screen is on
            if (!isScreenOn) {
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "myApp:WordBrideFellowshipLock");
                wl.acquire(3000); //set your time in milliseconds
            }

        }


            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID);
            builder.setSmallIcon(R.drawable.hospitald);
            builder.setColor(Color.RED);
            builder.setContentTitle(HtmlCompat.fromHtml("<font color=\"" + color + "\">" + tiTle + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
             builder.setContentText(HtmlCompat.fromHtml("<font color=\"" + color1 + "\">" + body + user +"</font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.addAction(R.drawable.ic_remove_red_eye_black_24dp, "View it", YesPendingIntent);
            builder.setAutoCancel(true);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.medhackb);

            builder.setLargeIcon(bitmap);
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));


            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getActivity());
            notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());



    }



}
