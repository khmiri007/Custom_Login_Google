package ziedkhmiri.custom_login_google;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final TimeInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final TimeInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    ImageView image;
    EditText email,passwd;
    TextView account,signeout;
    Button signe;
    LinearLayout linear,linear2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear=(LinearLayout)findViewById(R.id.linear);
        linear2=(LinearLayout)findViewById(R.id.linear2);
        image=(ImageView)findViewById(R.id.image);
        email=(EditText)findViewById(R.id.email);
        passwd=(EditText)findViewById(R.id.passwd);
        account=(TextView)findViewById(R.id.account);
        signeout=(TextView)findViewById(R.id.signout);
        signe =(Button)findViewById(R.id.signe);
        signe.setOnClickListener(this);
        signeout.setOnClickListener(this);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signe:
                if (isEmpty(email) || isEmpty(passwd)){
                    GradientDrawable backgroundGradient = (GradientDrawable) email.getBackground();
                    backgroundGradient.setStroke(1, Color.RED);
                    GradientDrawable backgroundGradient2 = (GradientDrawable) passwd.getBackground();
                    backgroundGradient2.setStroke(1, Color.RED);
                    Toast.makeText(getApplicationContext(),
                            "E-mail or Passwrd is empty", Toast.LENGTH_LONG).show();
              }else
                animation(image,R.drawable.myphoto);
                break;
            case R.id.signout :
                image.setImageResource(R.drawable.google);
                linear.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.GONE);
                animateViewFromBottomToTop(linear);
                email.setText("");
                passwd.setText("");
                GradientDrawable backgroundGradient = (GradientDrawable) email.getBackground();
                backgroundGradient.setStroke(1, Color.BLACK);
                GradientDrawable backgroundGradient2 = (GradientDrawable) passwd.getBackground();
                backgroundGradient2.setStroke(1, Color.BLACK);

        }

    }
    private void animation(final ImageView image, final int resId ) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(image, "rotation", 0f, 360f*10);
        rotationAnim.setDuration(2000);
        rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(image, "scaleX", 0.2f, 1f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(image, "scaleY", 0.2f, 1f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
        bounceAnimY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                image.setImageResource(resId);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                linear.setVisibility(View.GONE);
                linear2.setVisibility(View.VISIBLE);
                animateViewFromBottomToTop(linear2);
            }
        });
        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
        animatorSet.start();
    }

    public static void animateViewFromBottomToTop(final View view){

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {

                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                final int TRANSLATION_Y = view.getHeight();
                view.setTranslationY(TRANSLATION_Y);
                view.setVisibility(View.GONE);
                view.animate()
                        .translationYBy(-TRANSLATION_Y)
                        .setDuration(500)
                        .setStartDelay(200)
                        .setListener(new AnimatorListenerAdapter() {

                            @Override
                            public void onAnimationStart(final Animator animation) {

                                view.setVisibility(View.VISIBLE);
                            }
                        })
                        .start();
            }
        });
    }
    private boolean isEmpty(EditText myeditText) {
        return myeditText.getText().toString().trim().length() == 0;
    }
}
