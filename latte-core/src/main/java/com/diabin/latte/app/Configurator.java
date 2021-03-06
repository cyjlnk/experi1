package com.diabin.latte.app;


import android.app.Application;
import android.os.Handler;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by fei on 2017/7/30.
 */

public class Configurator {

    private static final HashMap<Object,Object> LATTE_CONFIGS=new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS =new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS=new ArrayList<>();
    private static final Handler HANDLER = new Handler();

    private Configurator(){
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY,false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap<Object,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }

    private static class Holder{
        private static final Configurator INSTANCE=new Configurator();
    }

    public final void configure(){
        initIcons();
        Logger.addLogAdapter(new AndroidLogAdapter());
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY, true);
        Utils.init(Latte.getApplicationContext());

    }

    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }

    public final Configurator withLoaderDelayed(long delayed) {
        LATTE_CONFIGS.put(ConfigKeys.LOADER_DELAYED, delayed);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    private void initIcons(){
        if(ICONS.size()>0){
            final Iconify.IconifyInitializer initializer=Iconify.with(ICONS.get(0));
            for(int i=1;i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    private void checkConfiguration(){
        final boolean isReady= (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if(!isReady){
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }


    final <T> T getConfiguration(Object key){
        checkConfiguration();
        final Object value=LATTE_CONFIGS.get(key);
        if(value==null){
            throw new NullPointerException(key.toString()+"IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }

}
