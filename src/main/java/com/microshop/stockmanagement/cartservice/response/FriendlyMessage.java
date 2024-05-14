package com.microshop.stockmanagement.cartservice.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FriendlyMessage {

    private String title; // error succes gibi mesajları içerir.
    private String description; // record succesfully created gibi hata veya başarılı kayıtların açıklamasını içerir
    private String buttonPositive; //UI tarafında butonun nerede bulunacğaını belirtir.
}
