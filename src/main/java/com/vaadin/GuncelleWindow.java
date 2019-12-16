package com.vaadin;

import com.vaadin.db.DBOperations;
import com.vaadin.ui.*;


public class GuncelleWindow extends Window {

    public GuncelleWindow(MyUI myUI, int kisiId){
        super("Kişiyi Güncelle");
        this.center();
        this.setWidth("450px");

        DBOperations dbOperations = new DBOperations();
        FormLayout guncelleLayout = new FormLayout();

        TextField nameField = new TextField();
        nameField.setCaption("İsim:");

        TextField surnameField = new TextField();
        surnameField.setCaption("Soyisim:");

        TextField telNoField = new TextField();
        telNoField.setCaption("Telefon:");

        Button guncelleButton = new Button("Kişiyi Güncelle");

        guncelleButton.addClickListener(event -> {
            dbOperations.kisiGuncelle(kisiId, nameField.getValue(), surnameField.getValue(),telNoField.getValue());
            myUI.kisiList = dbOperations.tumRehberListele();
            myUI.resetContainer();
            close();

        });

        guncelleLayout.addComponents(nameField,surnameField,telNoField,guncelleButton);
        setContent(guncelleLayout);
    }

}
