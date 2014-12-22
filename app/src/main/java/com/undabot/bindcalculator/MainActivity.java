package com.undabot.bindcalculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.btn_click)
    Button btnClick;
    @InjectView(R.id.tv_result)
    TextView tvResultLabel;

    //Inital values
    private int mNumOne = 0;
    private int mNumTwo = 1;
    private int rez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        Observable<Button> btnClickObservable = ViewObservable.clicks(btnClick, false);
        btnClickObservable.subscribe(new Action1<Button>() {
            @Override
            public void call(Button button) {
                tvResultLabel.setText(String.valueOf(getResult()));
            }
        });
    }

    /**
     * Get sum of last two results and set new values
     *
     * @return int
     */
    public int getResult() {
        rez = mNumOne + mNumTwo;
        mNumOne = mNumTwo;
        mNumTwo = rez;
        return rez;
    }

}
