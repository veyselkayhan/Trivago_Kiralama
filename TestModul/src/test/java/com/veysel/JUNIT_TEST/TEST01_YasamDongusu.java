package com.veysel.JUNIT_TEST;

import com.bilgeadam.utility.Islemler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TEST01_YasamDongusu {

    /**
     * Uygulamaları geliştirirken en çok düşünülen sorunlardan birini gidermek için yapılır.Kodun
     * düzenlenmesi ve revizyonu.
     * Test bir method için yazılır.Ancak bir metodun tüm ihtimalleri için ayrı ayrı testler yazılmalıdır.
     * Testlerin erişim belirteçleri olmaz.
     * İşlem yapıp bittikleri için geri değer dönmelerine gerek yoktur.
     * Bir methodun test olarak çalışması için üzerine @Test anatasyonu eklenir.
     *
     */
    @Test
    void Ilktest(){
        Islemler islemler=new Islemler();
        int toplam= islemler.toplam(46,64);
        Assertions.assertEquals(toplam,77);
        System.out.println("Test Yaptik");
    }





}
