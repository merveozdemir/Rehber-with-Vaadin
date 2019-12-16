package com.vaadin.domain;

    public class Kisi {
        String adi;
        String soyadi;
        String telefon;
        int id;

        public Kisi(String ad, String soyad, String telefon) {
            this.adi = ad;
            this.soyadi = soyad;
            this.telefon = telefon;
        }

        public int getId() {
            return id;
        }

        public String getAdi() {
            return adi;
        }

        public void setAdi(String adi) {
            this.adi = adi;
        }

        public String getSoyadi() {
            return soyadi;
        }

        public void setSoyadi(String soyadi) {
            this.soyadi = soyadi;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTelefon() {
            return telefon;
        }
    }
