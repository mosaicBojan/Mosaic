/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mosaicserver;

/**
 *
 * @author Asus
 */
public class ActivationKey {

    private final String value;   //activation key value

    ActivationKey(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivationKey) {
            ActivationKey toCompare = (ActivationKey) obj;
            return this.value.equals(toCompare.value);
        }
        return false;
    }
}
