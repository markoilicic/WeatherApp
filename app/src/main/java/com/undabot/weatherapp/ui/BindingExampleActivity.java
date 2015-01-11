package com.undabot.weatherapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.undabot.weatherapp.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;


public class BindingExampleActivity extends Activity {

    @InjectView(R.id.btn_click)
    Button btnClick;
    @InjectView(R.id.tv_result)
    TextView tvResultLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_example);

        ButterKnife.inject(this);

        //Create button click observable
        Observable<Button> btnClickObservable = ViewObservable.clicks(btnClick, false);

        //create calculator observable
        final Observable<Integer> calculator = Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> s) {
                int f1 = 1, f2 = 1, fn = 0;
                s.onNext(1);
                while (!s.isUnsubscribed() || fn >= 0) {
                    fn = f1 + f2;
                    f1 = f2;
                    f2 = fn;
                    s.onNext(fn);
                }
            }

        });

        //Subscribe for button click
        btnClickObservable.subscribe(new Action1<Button>() {
            int btnClickCount = 1;

            @Override
            public void call(Button button) {

                //Subscribe for calculator
                calculator.take(btnClickCount).last().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer result) {
                        if (result >= 0) {
                            tvResultLabel.setText(String.valueOf(result));
                            btnClickCount++;
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.reset_calculator_text, Toast.LENGTH_SHORT).show();
                            tvResultLabel.setText(R.string.tv_result_default);
                            btnClickCount = 1;
                        }

                    }
                });

            }

        });

    }

}
