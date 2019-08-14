/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.transactions;

import jtps.jTPS_Transaction;
import tam.data.TAData;

/**
 *
 * @author Jase
 */
public class Add implements jTPS_Transaction{
    private String name;
    private String email;
    private TAData data;
    
    public Add(String name, String email, TAData data){
        this.name = name;
        this.email = email;
        this.data = data;
    }
    
    @Override
    public void doTransaction() {
        data.addTA(name, email);
    }

    @Override
    public void undoTransaction() {
        data.removeTA(name);
    }
    
}
