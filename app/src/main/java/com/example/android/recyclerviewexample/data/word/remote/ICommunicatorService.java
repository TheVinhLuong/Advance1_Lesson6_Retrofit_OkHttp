package com.example.android.recyclerviewexample.data.word.remote;

import com.example.android.recyclerviewexample.data.word.Word;
import com.example.android.recyclerviewexample.utils.Constant;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by VinhTL on 16/10/2017.
 */

public interface ICommunicatorService {
    
    @FormUrlEncoded
    @POST(Constant.API_TOKENIZE)
    Call<Word> getTokenizedString(@Field(Constant.FIELD_INPUT) String input);
}
