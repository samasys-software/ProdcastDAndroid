package com.samayu.prodcast.prodcastd.service;

import com.samayu.prodcast.prodcastd.dto.AdminDTO;
import com.samayu.prodcast.prodcastd.dto.Area;
import com.samayu.prodcast.prodcastd.dto.AreaDTO;
import com.samayu.prodcast.prodcastd.dto.CountryDTO;
import com.samayu.prodcast.prodcastd.dto.CustomerDTO;
import com.samayu.prodcast.prodcastd.dto.CustomerListDTO;
import com.samayu.prodcast.prodcastd.dto.LoginDTO;
import com.samayu.prodcast.prodcastd.dto.ProdcastDTO;
import com.samayu.prodcast.prodcastd.dto.StoreType;

import java.util.List;

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
    public Call<CustomerListDTO> getCustomers(@Query ("employeeId") long employeeId );

    @POST("prodcast/global/changePassword")
    @FormUrlEncoded
    public Call<ProdcastDTO> changePassword(@Field("employeeId") String employeeId , @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    @GET("prodcast/global/getStoreType")
    public Call<AdminDTO<List<StoreType>>> getStoreTypes();

    @GET("prodcast/global/getCountries")
    public Call<CountryDTO> getCountries();

    @GET("prodcast/global/areas")
    public Call<AreaDTO> getAreasForEmployee(@Query("employeeId") long employeeId);

    @GET("prodcast/distributor/getAreas")
    public Call<AdminDTO<List<Area>>> getAreasForDistributor(@Query("employeeId") long employeeId );

    @POST("prodcast/global/collection")
    @FormUrlEncoded
    public Call<CustomerDTO> makePayment(@Field("paymentType") int paymentType, @Field("employeeId")long employeeId, @Field("billId")long billId, @Field("amount") double amount, @Field("customerId") long customerId, @Field("refNo") String checkNumber, @Field("refDetail") String checkComments);

    @POST("prodcast/global/saveCustomer")
    @FormUrlEncoded
    public Call<ProdcastDTO> saveCustomer(@Field("employeeId") String employeeId,
                                          @Field("customerName") String customerName,
                                          @Field("customerType") String customerType,
                                          @Field("areaId") Long areaId,
                                          @Field("weekDay") String weekDay,
                                          @Field("firstName") String firstName,
                                          @Field("lastName") String lastName,
                                          @Field("emailAddress") String emailAddress,
                                          @Field("cellPhone") String cellPhoneNumber,
                                          @Field("phoneNumber") String phoneNumber,
                                          @Field("unitNumber") String unitNumber,
                                          @Field("billingAddress1") String billingAddress1,
                                          @Field("billingAddress2") String billingAddress2,
                                          @Field("billingAddress3") String billingAddress3,
                                          @Field("city") String city,
                                          @Field("state") String state,
                                          @Field("country") String countryId, String nte, String smsAllow, boolean activ);
}
