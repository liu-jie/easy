package com.eirture.easy.edit;

import com.eirture.easy.base.bus.SingleBus;
import com.eirture.easy.edit.data.EditR;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by eirture on 16-12-23.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EditP {

    @Bean
    EditR editR;
    @Bean
    SingleBus bus;

    public void readJournal(int journalId) {
    }

}
