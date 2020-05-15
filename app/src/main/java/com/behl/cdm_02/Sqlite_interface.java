package com.behl.cdm_02;

interface Sqlite_interface {
    Boolean insereDado(String nome, String email, String pass);

    String[] carregaDados();
}
