package com.grattieri.massimo.cylinderdrive;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "CylinderDrive";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /* -------------   Verifica se Disclaimer già accettato --------------------- */
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean accepted = settings.getBoolean("accepted", false);
        if( accepted ){
            // Disclaimer già accettato.
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Disclaimer");
            alertDialog.setMessage(getResources().getString(R.string.str_disclaimer));
            alertDialog.setCancelable(false);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Accetto",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("accepted", true);
                            editor.commit();
                            editor.apply();
                            dialog.dismiss();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Esci",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();		    	        }
                    });
            alertDialog.show();
        }

        /* -------------  Istanzia oggetti UI ---------------------- */
        final EditText et_D = (EditText) findViewById(R.id.et_D) ;
        final EditText et_L = (EditText) findViewById(R.id.et_L) ;
        final EditText et_Da = (EditText) findViewById(R.id.et_Da) ;
        final EditText et_Db = (EditText) findViewById(R.id.et_Db) ;
        final EditText et_da = (EditText) findViewById(R.id.et_da) ;
        final EditText et_db = (EditText) findViewById(R.id.et_db) ;
        final EditText et_La = (EditText) findViewById(R.id.et_La) ;
        final EditText et_Lb = (EditText) findViewById(R.id.et_Lb) ;
        final EditText et_Mc = (EditText) findViewById(R.id.et_Mc) ;
        final EditText et_Ml = (EditText) findViewById(R.id.et_Ml) ;
        final EditText et_Eol = (EditText) findViewById(R.id.et_Eol) ;
        final EditText et_Dol = (EditText) findViewById(R.id.et_Dol) ;
        final EditText et_Drive = (EditText) findViewById(R.id.et_Drive) ;
        final RadioButton rb_CilindroDoppioStelo = (RadioButton) findViewById(R.id.rb_CilindroDoppioStelo) ;
        final RadioButton rb_CilindroSingoloStelo = (RadioButton) findViewById(R.id.rb_CilindroSingoloStelo) ;
        final ImageView im_Cilindro = (ImageView) findViewById(R.id.imCilindro) ;

        /* ----------------  Default tipo cilindro ----------------------- */
        rb_CilindroDoppioStelo.setChecked(true) ;
        rb_CilindroSingoloStelo.setChecked(false) ;
        im_Cilindro.setImageResource(R.drawable.im_cilindro_doppio);
        et_Da.setEnabled(true);

        /* ------------  Aggiungi listener radio buttons scelta tipo cilindro doppio -------------- */
        rb_CilindroDoppioStelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // --- OnClick   -----
                rb_CilindroDoppioStelo.setChecked(true) ;
                rb_CilindroSingoloStelo.setChecked(false) ;
                im_Cilindro.setImageResource(R.drawable.im_cilindro_doppio);
                et_Da.setEnabled(true);
                MyFunctionClass myFunctions = new MyFunctionClass();
                String str_Risultato = myFunctions.calcolaFrequenza();
                Button btn = (Button) findViewById(R.id.btn_Calcola) ;
                btn.setText(str_Risultato);
            }
        });

        /* ------------  Aggiungi listener radio buttons scelta tipo cilindro singolo ----------- */
        rb_CilindroSingoloStelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // --- OnClick   -----
                rb_CilindroDoppioStelo.setChecked(false) ;
                rb_CilindroSingoloStelo.setChecked(true) ;
                im_Cilindro.setImageResource(R.drawable.im_cilindro_singolo);
                et_Da.setEnabled(false);
                MyFunctionClass myFunctions = new MyFunctionClass();
                String str_Risultato = myFunctions.calcolaFrequenza();
                Button btn = (Button) findViewById(R.id.btn_Calcola) ;
                btn.setText(str_Risultato);
            }
        });

        /* ------------- Aggiungi TextWatcher per verifica coerenza valori campi -------- */
        TextWatcher watch = new TextWatcher(){
            @Override
            public void afterTextChanged(Editable arg0) {
                MyFunctionClass myFunctions = new MyFunctionClass();
                Button btn = (Button) findViewById(R.id.btn_Calcola) ;
                int n = 0;
                if (!myFunctions.verifica(et_D)) { n+=1 ; }
                if (!myFunctions.verifica(et_L)) { n+=1 ; }
                if (!myFunctions.verifica(et_Da)) { n+=1 ; }
                if (!myFunctions.verifica(et_Db)) { n+=1 ; }
                if (!myFunctions.verifica(et_da)) { n+=1 ; }
                if (!myFunctions.verifica(et_db)) { n+=1 ; }
                if (!myFunctions.verifica(et_La)) { n+=1 ; }
                if (!myFunctions.verifica(et_Lb)) { n+=1 ; }
                if (!myFunctions.verifica(et_Mc)) { n+=1 ; }
                if (!myFunctions.verifica(et_Ml)) { n+=1 ; }
                if (!myFunctions.verifica(et_Eol)) { n+=1 ; }
                if (!myFunctions.verifica(et_Dol)) { n+=1 ; }
                if (!myFunctions.verifica(et_Drive)) { n+=1 ; }

                if (n==0){
                    btn.setEnabled(true);
                    String str_Risultato = myFunctions.calcolaFrequenza();
                    btn.setText(str_Risultato);


                } else {
                    btn.setEnabled(false);
                    btn.setBackgroundColor(Color.parseColor("#FFFFFF")) ;
                    String str_Risultato = "Verificare i parametri" ;
                    btn.setText(str_Risultato);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,  int arg3) { }
            @Override
            public void onTextChanged(CharSequence s, int a, int b, int c) {  }
        };

        et_D.addTextChangedListener(watch);
        et_L.addTextChangedListener(watch);
        et_Da.addTextChangedListener(watch);
        et_Db.addTextChangedListener(watch);
        et_da.addTextChangedListener(watch);
        et_db.addTextChangedListener(watch);
        et_La.addTextChangedListener(watch);
        et_Lb.addTextChangedListener(watch);
        et_Mc.addTextChangedListener(watch);
        et_Ml.addTextChangedListener(watch);
        et_Eol.addTextChangedListener(watch);
        et_Dol.addTextChangedListener(watch);
        et_Drive.addTextChangedListener(watch);

        // Aggiungi button listener
        final Button btn = (Button) findViewById(R.id.btn_Calcola) ;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // --- OnClick Verifica e Calcola  -----
                MyFunctionClass myFunctions = new MyFunctionClass();
                String str_Risultato = myFunctions.calcolaFrequenza();
                btn.setText(str_Risultato);
            }
        });
        // -------------------------------------------------------------------------
        MyFunctionClass myFunctions = new MyFunctionClass();
        String str_Risultato = myFunctions.calcolaFrequenza();
        btn.setText(str_Risultato);
        btn.setTransformationMethod(null);
    }


    public class MyFunctionClass {
        EditText et_D = (EditText) findViewById(R.id.et_D) ;
        EditText et_L = (EditText) findViewById(R.id.et_L) ;
        EditText et_Da = (EditText) findViewById(R.id.et_Da) ;
        EditText et_Db = (EditText) findViewById(R.id.et_Db) ;
        EditText et_da = (EditText) findViewById(R.id.et_da) ;
        EditText et_db = (EditText) findViewById(R.id.et_db) ;
        EditText et_La = (EditText) findViewById(R.id.et_La) ;
        EditText et_Lb = (EditText) findViewById(R.id.et_Lb) ;
        EditText et_Mc = (EditText) findViewById(R.id.et_Mc) ;
        EditText et_Ml = (EditText) findViewById(R.id.et_Ml) ;
        EditText et_Eol = (EditText) findViewById(R.id.et_Eol) ;
        EditText et_Dol = (EditText) findViewById(R.id.et_Dol) ;
        EditText et_Drive = (EditText) findViewById(R.id.et_Drive) ;
        RadioButton rb_CilindroSingoloStelo = (RadioButton) findViewById(R.id.rb_CilindroSingoloStelo) ;
        ImageView im_Graph = (ImageView) findViewById(R.id.imGraph);
        Drawable dim_graph = getResources().getDrawable( R.drawable.im_graph );
        float iAltezza   = dim_graph.getIntrinsicHeight();
        float iLarghezza = dim_graph.getIntrinsicWidth();

        Bitmap bmp= convertToBitmap(dim_graph, (int)iLarghezza, (int)iAltezza);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        double D , L, Da, Db, da, db, La, Lb, Mc, Ml, Eol, Dol, Drive ;
        double Aa, Ab, r, Va, Vb, Fmin, Fmax, PosMin, PosMax, posizione, VA, VB, Ca, Cb, C, Ma, Mb, M, Wo, Fo = 0.0;
        double Frmin, Frmax, T, Kv, Tv = 0.0 ;
        double Womin, Womax = 0.0 ;
        double [] aFo = new double[101] ;
        double [] aPos = new double[101] ;

        public Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
            Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mutableBitmap);
            drawable.setBounds(0, 0, widthPixels, heightPixels);
            drawable.draw(canvas);

            return mutableBitmap;
        }

        public boolean verifica(EditText et ) {
            try {
                float n = Float.valueOf(et.getText().toString()) ;
                if (n > 0.0) {
                    et.setBackgroundColor(Color.TRANSPARENT) ;
                    return true  ;
                }  else {
                    et.setBackgroundColor(Color.parseColor("#FF9999")) ;
                    return false ;
                }
            } catch(Exception e) {
                et.setBackgroundColor(Color.parseColor("#FF0000")) ;
                return false ;
            }
        }

        public String calcolaFrequenza() {

            // Conversione in numeri
            D = Float.valueOf(et_D.getText().toString()) ;
            L = Float.valueOf(et_L.getText().toString()) ;
            if (rb_CilindroSingoloStelo.isChecked()){
                Da = 0.0;
            } else {
                Da = Float.valueOf(et_Da.getText().toString()) ;
            }
            Db = Float.valueOf(et_Db.getText().toString()) ;
            da = Float.valueOf(et_da.getText().toString()) ;
            db = Float.valueOf(et_db.getText().toString()) ;
            La = Float.valueOf(et_La.getText().toString()) ;
            Lb = Float.valueOf(et_Lb.getText().toString()) ;
            Mc = Float.valueOf(et_Mc.getText().toString()) ;
            Ml = Float.valueOf(et_Ml.getText().toString()) ;
            Eol = Float.valueOf(et_Eol.getText().toString()) ;
            Dol = Float.valueOf(et_Dol.getText().toString()) ;
            Drive = Float.valueOf(et_Drive.getText().toString()) ;

            // Calcolo area A e B
            Aa = ((Math.pow(D, 2.0) * Math.PI) - (Math.pow(Da, 2.0) * Math.PI) ) / ( 400.0 ) ;
            Ab = ((Math.pow(D, 2.0) * Math.PI) - (Math.pow(Db, 2.0) * Math.PI) ) / ( 400.0 ) ;
            r = Aa / Ab ;
            Va = (Math.pow(da, 2.0) * Math.PI / 4.0 * La) / 1000.0 ;
            Vb = (Math.pow(db, 2.0) * Math.PI / 4.0 * Lb) / 1000.0 ;
            // Calcolo frequenze minime e massime
            Fmin = 1000000000.0;
            Fmax = 0.0;
            PosMin = 0.0 ;
            PosMax = 0.0 ;
            for(int x=0; x<=100; x++){
                posizione = L / 100.0 * x ;
                VA = Va + Aa * posizione / 10.0 ;
                VB = Vb + Ab * (L - posizione) / 10.0 ;
                Ca = Math.pow(Aa, 2.0) * Eol * 1000.0 / VA ;
                Cb = Math.pow(Ab, 2.0) * Eol * 1000.0 / VB ;
                C = Ca + Cb ;
                Ma = VA * Dol / 1000000.0 ;
                Mb = VB * Dol / 1000000.0 ;
                M = Mc + Ml + Ma + Mb;
                Wo = Math.sqrt(C / M);
                Fo = Wo / (2.0 * Math.PI) ;
                if (Fo < Fmin) { Fmin = Fo;	PosMin = posizione; Womin = Wo  ;	}
                if (Fo > Fmax) { Fmax = Fo; PosMax = posizione; Womax = Wo  ;	}
                aFo[x] = Fo;
                aPos[x] = posizione;
            }
            Frmin = Drive / Fmin;
            Frmax = Drive / Fmax;
            T = 1000.0 / (Drive * 2.0 / 3.0 * 2.0 * Math.PI) ;
            Kv = 0.75 * (2.0 * Math.PI * Drive) / 166.0 ;
            Tv = 1000.0 /  ( 2.0 * Math.PI * Drive ) ;


            float iPlotX , iLastPlotX = 0 ;
            float iPlotY , iLastPlotY = 0 ;
            float fontSize = 10;

            float offSetY = (float)(iAltezza * 0.17);
            float offSetX = (float)(iLarghezza * 0.17) ;
            double factorScalingY = iAltezza * 0.8 / (Fmax - Fmin ) ;
            double factorScalingX = iLarghezza * 0.8  / 100.0 ;

            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(3);
            for(int x=0; x<=100; x++){
                iPlotX = (float)(x * factorScalingX) + offSetX ;
                iPlotY = (float)((aFo[x] - Fmin)* factorScalingY) + offSetY;
                if (x != 0){
                    canvas.drawLine( iLastPlotX , iAltezza - iLastPlotY, iPlotX  , iLarghezza - iPlotY   , paint);
                }
                iLastPlotX = iPlotX;
                iLastPlotY = iPlotY;
                paint.setColor(Color.RED);
                if (((Drive / aFo[x]) >= 0.8) && ((Drive / aFo[x]) <= 1.5)) { paint.setColor(Color.GREEN);}
                if (((Drive / aFo[x]) > 1.5 ) && ((Drive / aFo[x]) <= 3.0)) { paint.setColor(Color.YELLOW);}
                if ((Drive /  aFo[x]) > 3.0 ) { paint.setColor(Color.RED);}
                if ((Drive /  aFo[x]) < 0.8 ) { paint.setColor(Color.YELLOW);}
            }

            paint.setTextSize(fontSize);
            paint.setStrokeWidth(1);
            paint.setColor(Color.BLACK);
            canvas.drawText("0", offSetX , iAltezza -  (float)(iAltezza * 0.1)   , paint);
            canvas.drawText(String.format("%.0f", L) , iLarghezza - fontSize * 4 , iAltezza -  (float)(iAltezza * 0.1)  , paint);
            canvas.drawText(String.format("%.0f", Fmin) , offSetX / 2 , iAltezza - offSetY    , paint);
            canvas.drawText(String.format("%.0f", Fmax) , offSetX / 2 , fontSize *2   , paint);

            im_Graph.setImageBitmap(bmp);
            String str_R = "\n";
            str_R += String.format("RateMax %.2f (Fo %.2fHz alla posizione di %.0fmm) \nRateMin %.2f (Fo %.2fHz alla posizione di %.0fmm)  \n", Frmin, Fmin, PosMin, Frmax, Fmax , PosMax);
            str_R += String.format("AreaA %.2fcm2 AreaB %.2fcm2  A/B %.2fcm2 \n", Aa , Ab , r );
            str_R += String.format("Vol.tubazioni A %.2fcm3 Vol.tubazioni B %.2fcm3 \n", Va , Vb);
            str_R += String.format("%.2f > Wo > %.2f\n", Womin , Womax );
            str_R += String.format("T1 %.2fms\n", T);
            str_R += String.format("Kv %.2fm/min/mm\n", Kv);
            str_R += String.format("Tv %.2fms\n", Tv);

            return str_R;
        }
    }



}
