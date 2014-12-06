package com.catinthedark.ld31.lib.util;

import com.badlogic.gdx.physics.box2d.Contact;

/**
 * Created by over on 04.12.14.
 */
public class ContactUtils {

    public static <A,B> Tuple<A,B>  query(Class<A> data1Clazz, Class<B> data2Clazz, Contact contact){
        if(contact.getFixtureA().getUserData() == null ||
                contact.getFixtureB().getUserData() == null)
            return null;

//        System.out.println(String.format("c1:%s c2:%s", contact.getFixtureA().getUserData().getClass().toString(),
//                contact.getFixtureB().getUserData().getClass().toString()));

        if(contact.getFixtureA().getUserData().getClass() == data1Clazz) {
            A a = (A) contact.getFixtureA().getUserData();
            if(contact.getFixtureB().getUserData().getClass() == data2Clazz) {
                B b = (B) contact.getFixtureB().getUserData();
                return new Tuple<>(a, b);
            }
        }

        if(contact.getFixtureA().getUserData().getClass() == data2Clazz) {
            B b = (B) contact.getFixtureA().getUserData();
            if(contact.getFixtureB().getUserData().getClass() == data1Clazz) {
                A a = (A) contact.getFixtureB().getUserData();
                return new Tuple<>(a, b);
            }
        }

        return null;
    }
}
