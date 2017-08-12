package com.samayu.prodcast.prodcastd.service;

import com.samayu.prodcast.prodcastd.dto.CustomerListDTO;
import com.samayu.prodcast.prodcastd.dto.LoginDTO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sarathan732 on 8/10/2017.
 */

public interface ProdcastDistributorService {
    @POST("prodcast/global/loginp")
    @FormUrlEncoded
    public Call<LoginDTO> authenticate(@Field("userid") String userId , @Field("password") String password);

    @GET("prodcast/global/customers")
    public Call<CustomerListDTO> getCustomers(@Query("employeeId") String employeeId );
}
