package com.example.aspire.tiquets.test;

import com.example.aspire.tiquets.Verificar;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mathcrap on 22/8/2017.
 */
public class VerificarTest {
    @Test
    public void validateEmail() throws Exception {

        Verificar verificar = new Verificar();
        assertEquals(true, verificar.validateEmail("example@gmail.com"));
    }
    @Test
    public void validateEmail2() throws Exception {
        Verificar verificar = new Verificar();
        assertEquals(false, verificar.validateEmail("example.example@@gmail.com"));
    }
    @Test
    public void validateEmail3() throws Exception {
        Verificar verificar = new Verificar();
        assertEquals(false, verificar.validateEmail("exampleexample.com"));
    }
    @Test
    public void validateEmail4() throws Exception {
        Verificar verificar = new Verificar();
        assertEquals(false, verificar.validateEmail("example@gmailcom"));
    }
    @Test
    public void validateEmail5() throws Exception {
        Verificar verificar = new Verificar();
        assertEquals(true, verificar.validateEmail("example.example@gmail.com"));
    }

    @Test
    public void validateEmail6() throws Exception {
        Verificar verificar = new Verificar();
        assertEquals(true, verificar.validateEmail("example.example@gmail.otro.com"));
    }

}