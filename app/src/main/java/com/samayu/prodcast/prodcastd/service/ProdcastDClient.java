package com.samayu.prodcast.prodcastd.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sarathan732 on 8/10/2017.
 */

public class ProdcastDClient {

   public ProdcastDistributorService getClient(){


       OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
       httpClientBuilder.interceptors().add(new Interceptor() {
           @Override
           public Response intercept(Chain chain) throws IOException {
               try{
               Request request = chain.request();
               Response response = chain.proceed(request);
               String body = response.body().string();

               return response.newBuilder().body(ResponseBody.create(response.body().contentType(), body)).build();
           }
           catch(Exception er ){
               er.printStackTrace();
               return null;
           }

           }
       });
       Gson gson = new GsonBuilder()
               .setDateFormat("yyyy-MM-dd")
               .create();
       Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://ec2-52-91-5-22.compute-1.amazonaws.com:8080")
               .addConverterFactory( GsonConverterFactory.create(gson));

       Retrofit retrofit = builder.client( httpClientBuilder.build()).build();

       ProdcastDistributorService client = retrofit.create(ProdcastDistributorService.class);

       return client;

   }

}
