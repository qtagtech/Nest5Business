//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.nest5.businessClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class Initialactivity_
    extends Initialactivity
    implements HasViews
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    private void init_(Bundle savedInstanceState) {
        myRestService = new MyRestService_();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static Initialactivity_.IntentBuilder_ intent(Context context) {
        return new Initialactivity_.IntentBuilder_(context);
    }

    public static Initialactivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new Initialactivity_.IntentBuilder_(fragment);
    }

    public static Initialactivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new Initialactivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void notifySuccess() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                Initialactivity_.super.notifySuccess();
            }

        }
        );
    }

    @Override
    public void receivedZReport(final double ventas, final double descuentos, final double impuestos, final double propinas, final double domicilios, final double llevar, final double tarjeta, final double efectivo, final int contEfectivo, final int contTarjeta, final int contDomicilio, final int contLlevar) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                Initialactivity_.super.receivedZReport(ventas, descuentos, impuestos, propinas, domicilios, llevar, tarjeta, efectivo, contEfectivo, contTarjeta, contDomicilio, contLlevar);
            }

        }
        );
    }

    @Override
    public void informUser(final int message) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                Initialactivity_.super.informUser(message);
            }

        }
        );
    }

    @Override
    public void saveSale(final String method, final Double value, final Double discount, final int delivery, final int togo, final int tip) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    Initialactivity_.super.saveSale(method, value, discount, delivery, togo, tip);
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    @Override
    public void getAllDailySales(final DailySaleDao dsd) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    Initialactivity_.super.getAllDailySales(dsd);
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    @Override
    public void fetchSales() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    Initialactivity_.super.fetchSales();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    @Override
    public void connectStarMicronics() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 700, "") {


            @Override
            public void execute() {
                try {
                    Initialactivity_.super.connectStarMicronics();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, Initialactivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, Initialactivity_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, Initialactivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public Initialactivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent_, requestCode);
            } else {
                if (fragment_!= null) {
                    fragment_.startActivityForResult(intent_, requestCode);
                } else {
                    if (context_ instanceof Activity) {
                        ((Activity) context_).startActivityForResult(intent_, requestCode);
                    } else {
                        context_.startActivity(intent_);
                    }
                }
            }
        }

    }

}
