package com.vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.db.DBOperations;
import com.vaadin.domain.Kisi;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.vaadin.MyAppWidgetset")
public class MyUI extends UI {
    List<Kisi> kisiList = new ArrayList<>();
    BeanItemContainer<Kisi> container = new BeanItemContainer<>(Kisi.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final HorizontalLayout mainLayout = new HorizontalLayout();
        final FormLayout formLayout = new FormLayout();
        final HorizontalLayout listeleLayout = new HorizontalLayout();
        final FormLayout tableAndListeleLayout = new FormLayout();
        final FormLayout filtreFormLayout = new FormLayout();

        tableAndListeleLayout.setCaption("REHBER");
        formLayout.setCaption("KİŞİ EKLE");
        filtreFormLayout.setCaption("FİLTRELE");

        DBOperations dbOperations = new DBOperations();
        kisiList = dbOperations.tumRehberListele();
        container.addAll(kisiList);
        Grid myGrid = new Grid(container);
        myGrid.setColumnOrder("id", "adi", "soyadi", "telefon");

        TextField nameField = new TextField();
        nameField.setCaption("İsim:");

        TextField surnameField = new TextField();
        surnameField.setCaption("Soyisim:");

        TextField telNoField = new TextField();
        telNoField.setCaption("Telefon:");


        Button rehbereEkleButton = new Button("Rehbere Ekle");
        Button tumKisilerButton = new Button("Tüm kişileri listele");
        Button kisiSilButton  = new Button("     Kişiyi Sil    ");
        Button kisiGuncelle = new Button("Kişiyi güncelle");

        TextField filtreField = new TextField();
        nameField.setCaption("Adı giriniz:");

        Button adaGoreListeleButton = new Button("   Ada göre filtrele   ");
        Button soyadaGoreListeleButton = new Button(" Soyada göre filtrele ");
        Button telefonaGoreListeleButton = new Button("Tel. no'suna göre filtrele");

        rehbereEkleButton.addClickListener(event -> {
            String adi = nameField.getValue();
            String soyadi = surnameField.getValue();
            String telNo = telNoField.getValue();
            Kisi kisi = new Kisi(adi,soyadi, telNo);
            dbOperations.rehbereEkle(kisi);

            nameField.setValue("");
            surnameField.setValue("");
            telNoField.setValue("");

            kisiList = dbOperations.tumRehberListele();
            resetContainer();
        });

        tumKisilerButton.addClickListener(e -> {
            kisiList = dbOperations.tumRehberListele();
            resetContainer();
        });

        kisiSilButton.addClickListener(e -> {
            Kisi kisi = (Kisi) myGrid.getSelectedRow();
            dbOperations.rehberdenSil(kisi.getId());
            container.removeAllItems();
            kisiList = dbOperations.tumRehberListele();
            container.addAll(kisiList);
        });

        kisiGuncelle.addClickListener(event -> {
            Kisi kisi = (Kisi) myGrid.getSelectedRow();
            GuncelleWindow guncelleSayfasi = new GuncelleWindow(this, kisi.getId());
            this.addWindow(guncelleSayfasi);

        });

        adaGoreListeleButton.addClickListener(event -> {
            String filtre = filtreField.getValue();
            kisiList = dbOperations.adaGoreFiltrele(filtre);
            resetContainer();
        });

        soyadaGoreListeleButton.addClickListener(event -> {
            String filtre = filtreField.getValue();
            kisiList = dbOperations.soyadaGoreListele(filtre);
            resetContainer();
        });

        telefonaGoreListeleButton.addClickListener(event -> {
            String filtre = filtreField.getValue();
            kisiList = dbOperations.telNosunaGoreListele(filtre);
            resetContainer();
        });

        mainLayout.addComponents(formLayout, tableAndListeleLayout, filtreFormLayout);
        tableAndListeleLayout.addComponents(listeleLayout, myGrid);
        listeleLayout.addComponents(tumKisilerButton, kisiSilButton, kisiGuncelle);
        filtreFormLayout.addComponents(filtreField,adaGoreListeleButton,soyadaGoreListeleButton, telefonaGoreListeleButton);
        formLayout.addComponents(nameField, surnameField, telNoField, rehbereEkleButton);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        setContent(mainLayout);
    }

     public void resetContainer() {
        container.removeAllItems();
        container.addAll(kisiList);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
